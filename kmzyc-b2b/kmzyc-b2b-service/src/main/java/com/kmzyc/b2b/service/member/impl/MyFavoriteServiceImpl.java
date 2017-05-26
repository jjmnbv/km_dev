package com.kmzyc.b2b.service.member.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.dao.member.MyFavoriteDao;
import com.kmzyc.b2b.model.Favorite;
import com.kmzyc.b2b.service.OrderAssessDetailService;
import com.kmzyc.b2b.service.ProductPriceService;
import com.kmzyc.b2b.service.ProductStockService;
import com.kmzyc.b2b.service.member.MyFavoriteService;
import com.kmzyc.zkconfig.ConfigurationUtil;

@Service
public class MyFavoriteServiceImpl implements MyFavoriteService {

    // private static Logger logger = Logger.getLogger(MyFavoriteServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(MyFavoriteServiceImpl.class);

    @Resource(name = "myFavoriteDaoImpl")
    private MyFavoriteDao myFavoriteDao;

    @Resource(name = "productPriceService")
    private ProductPriceService productPriceService;

    @Resource(name = "productStockServiceImpl")
    private ProductStockService productStockService;

    @Resource(name = "orderAssessDetailService")
    private OrderAssessDetailService orderAssessDetailService;

    // private static String channel = ConfigurationUtil.getString("CHANNEL");

    /**
     * 查询收藏的店铺
     * 
     * @param page
     * @return
     */
    @Override
    public Pagination findFavoriteShopByPage(Pagination page) throws SQLException {
        page =
                myFavoriteDao.findByPage("Favorite.findFavoriteShopByPage",
                        "Favorite.countFavoriteShopByPage", page);
        return page;
    }

    /**
     * app查询收藏的商品
     * 
     * @param page
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public Pagination appFindFavoriteProductByPage(Pagination page) throws SQLException {
        Map<String, Object> conditon = (Map<String, Object>) page.getObjCondition();
        logger.info("开始查询的商品收藏列表,userId:" + conditon.get("userId") + ",keyword:"
                + conditon.get("keyword"));
        long startTime = System.currentTimeMillis();
        // 使用客户数据源

        List<Favorite> favs = null;

        page =
                myFavoriteDao.findByPage("Favorite.appFindFavoriteProductByPage",
                        "Favorite.countFavoriteProductByPage", page);
        favs = page.getRecordList();
        if (favs != null && favs.size() > 0) {
            try {
                favs = productPriceService.getPriceBatch(favs);
            } catch (Exception e) {
                logger.error("获取缓存价格发生异常", e);
            }
        }

        if (favs != null && favs.size() > 0) {
            for (Favorite f : favs) {
                f.setRealStock(productStockService.queryRealStockBySkuId(f.getProductSkuId()));
                BigDecimal temPrice = f.getPriceCopy().subtract(f.getFinalPrice());
                temPrice = temPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
                f.setSpreadPrice(temPrice);
            }
        }
        page.setRecordList(favs);
        logger.info("查询用户的收藏列表结束，耗时" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
        return page;
    }

    /**
     * 查询收藏的商品
     * 
     * @param page
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public Pagination findFavoriteProductByPage(Pagination page) throws SQLException {
        Map<String, Object> conditon = (Map<String, Object>) page.getObjCondition();
        logger.info("开始查询的商品收藏列表,userId:" + conditon.get("userId") + ",keyword:"
                + conditon.get("keyword"));
        long startTime = System.currentTimeMillis();
        // 使用客户数据源

        List<Favorite> favs = null;

        if (conditon.get("depreciate") == null && conditon.get("promotion") == null
                && conditon.get("inStock") == null) {
            page =
                    myFavoriteDao.findByPage("Favorite.findFavoriteProductByPage",
                            "Favorite.countFavoriteProductByPage", page);
            favs = page.getRecordList();
            if (favs != null && favs.size() > 0) {
                try {
                    favs = productPriceService.getPriceBatch(favs);
                } catch (Exception e) {
                    logger.error("获取缓存价格发生异常", e);
                }
            }
        } else {
            List<Favorite> recourseList =
                    myFavoriteDao.findByProperty("Favorite.findAllFavorite", page);
            if (recourseList != null && recourseList.size() > 0) {
                try {
                    recourseList = productPriceService.getPriceBatch(recourseList);
                } catch (Exception e) {
                    logger.error("获取缓存价格发生异常", e);
                }
                if (conditon.get("promotion") != null) {
                    favs = new ArrayList<Favorite>();
                    for (Favorite fav : recourseList) {
                        if (fav.getPromotionInfoLebal() != null
                                && fav.getPromotionInfoLebal().length > 0) {
                            favs.add(fav);
                        }
                    }
                }
                if (conditon.get("depreciate") != null) {
                    List<Favorite> depList = new ArrayList<Favorite>();
                    if (favs == null) {
                        favs = recourseList;
                    }
                    for (Favorite fav : favs) {
                        if (fav.getFinalPrice().subtract(fav.getPriceCopy()).doubleValue() < 0) {
                            depList.add(fav);
                        }
                    }
                    favs = depList;
                }
                if (conditon.get("inStock") != null) {
                    List<Favorite> stockList = new ArrayList<Favorite>();
                    if (favs == null) {
                        favs = recourseList;
                    }
                    for (Favorite fav : favs) {
                        if (productStockService.queryRealStockBySkuId(fav.getProductSkuId()) > 0) {
                            stockList.add(fav);
                        }
                    }
                    favs = stockList;
                }
                page.setTotalRecords(favs.size());
                int totalPage =
                        favs.size() % page.getNumperpage() == 0 ? favs.size()
                                / page.getNumperpage() : favs.size() / page.getNumperpage() + 1;
                page.setTotalpage(Long.valueOf(totalPage));
                List<Favorite> finalList = new ArrayList<Favorite>();
                if (favs.size() > 0) {
                    for (int i = page.getStartindex(); i <= page.getEndindex(); i++) {
                        finalList.add(favs.get(i - 1));
                        if (i == favs.size()) {
                            break;
                        }
                    }
                }
                favs = finalList;
            }
        }

        if (favs != null && favs.size() > 0) {
            for (Favorite f : favs) {
                f.setRealStock(productStockService.queryRealStockBySkuId(f.getProductSkuId()));
                BigDecimal temPrice = f.getPriceCopy().subtract(f.getFinalPrice());
                temPrice = temPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
                f.setSpreadPrice(temPrice);
            }
        }
        page.setRecordList(favs);
        logger.info("查询用户的收藏列表结束，耗时" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
        return page;
    }

    /**
     * 删除收藏记录
     * 
     * @param favoriteId
     * @throws Exception
     */
    @Override
    public void deleteFavorite(long favoriteId) throws SQLException {
        logger.info("删除收藏记录,参数favoriteId:" + favoriteId);
        // 使用客户数据源

        myFavoriteDao.deleteById("Favorite.deleteById", (int) favoriteId);
    }

    /**
     * 删除收藏记录
     * 
     * @throws Exception
     */
    @Override
    public void deleteFavoriteByCode(Map<String, Object> map) throws SQLException {
        logger.info("删除收藏记录,参数favoriteId:" + map.get("skuCode"));
        // 使用客户数据源

        myFavoriteDao.deleteMyfavoriteBySkucode(map);
    }

    /**
     * 插入收藏记录
     * 
     * @param favorite
     * @throws SQLException
     */
    @Override
    public void insertFavorite(Favorite favorite) throws SQLException {
        logger.info("插入收藏记录Favorite:" + favorite);
        // 使用客户数据源

        myFavoriteDao.save("Favorite.insertFavorite", favorite);
    }

    /**
     * 查询用户收藏夹中同类目商品N个
     * 
     * @param userId
     * @param categoryId 该值为空，则从收藏夹中的所有商品取
     * @param number 该值若为空，则获取所有满足条件记录
     * @throws SQLException
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Favorite> findFavoritesByUserAndCategory(Long userId, Long categoryId,
            Integer number) throws SQLException {
        logger.info("查询用户收藏夹中同类目商品,查询条件:\r\nuserId:" + userId + ";categoryId:" + categoryId
                + ";number:" + number);
        // 使用客户数据源

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userId", userId);
        condition.put("categoryId", categoryId);
        condition.put("number", number);
        String channel = ConfigurationUtil.getString("CHANNEL");
        condition.put("channel", channel);
        return myFavoriteDao.findByProperty("Favorite.findFavoritesByUserAndCategory", condition);
    }

    /**
     * 判断用户是否已收藏该商品
     * 
     * @param userId
     * @param productSkuCode
     * @return
     * @throws SQLException
     */
    @Override
    public boolean isSavedFavorite(Long userId, String productSkuCode) throws SQLException {
        logger.info("查询用户是否已收藏该商品,查询条件:\r\nuserId:" + userId + ";productSkuCode:" + productSkuCode);
        // 使用客户数据源

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userId", userId);
        condition.put("productSkuCode", productSkuCode);
        Integer num = (Integer) myFavoriteDao.findById("Favorite.isSavedFavorite", condition);
        return num > 0;
    }

    /**
     * 判断用户是否已收藏该店铺
     * 
     * @param userId
     * @param shopCode
     * @return
     * @throws SQLException
     */
    @Override
    public boolean isSavedFavoriteShop(Long userId, String shopCode) throws SQLException {
        logger.info("查询用户是否已收藏该商品,查询条件:\r\nuserId:" + userId + ";shopCode:" + shopCode);
        // 使用客户数据源

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userId", userId);
        condition.put("shopCode", shopCode);
        Integer num = (Integer) myFavoriteDao.findById("Favorite.isSavedFavoriteShop", condition);
        return num > 0;
    }

    /**
     * 收藏该店铺的用户数量
     * 
     * @param shopCode
     * @return
     * @throws SQLException
     */
    @Override
    public int findFavoriteShopUserCount(String shopCode) throws SQLException {
        logger.info("查询收藏该店铺的用户数量,查询条件:\r\nshopCode:" + shopCode);
        // 使用客户数据源

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("shopCode", shopCode);
        Integer num =
                (Integer) myFavoriteDao.findById("Favorite.findFavoriteShopUserCount", condition);
        return num;
    }

    /**
     * 查询收藏夹中现货商品数量
     * 
     * @param userId
     * @return
     * @throws SQLException
     */
    @Override
    public Long countInStockSku(Long userId) throws SQLException {
        logger.info("查询收藏夹中现货商品数量,查询条件:userId:" + userId);
        Map<String, Object> newConditon = new HashMap<String, Object>();
        // 解析并组装查询条件
        newConditon.put("userId", userId);
        String channel = ConfigurationUtil.getString("CHANNEL");
        newConditon.put("channel", channel);
        // 使用客户数据源

        return (Long) myFavoriteDao.findById("Favorite.countInStockSku", newConditon);
    }

    @Override
    public Long countCutPriceSku(Long userId, List<Favorite> recourseList) throws SQLException {

        logger.info("countCutPriceSku方法,查询条件:recourseList:" + recourseList);
        Long counts = 0l;
        if (recourseList != null && recourseList.size() > 0) {
            for (Favorite fav : recourseList) {
                if (fav.getFinalPrice().compareTo(fav.getPriceCopy()) < 0) {
                    ++counts;
                }
            }
        }
        return counts;
    }

    @Override
    public Long countPromotionSku(Long userId, List<Favorite> recourseList) throws SQLException {
        logger.info("countPromotionSku方法：参数recourseList" + recourseList + ";userId" + userId);
        Long counts = 0l;
        if (recourseList != null && recourseList.size() > 0) {
            for (Favorite fav : recourseList) {
                if (fav.getPromotionInfoLebal() != null && fav.getPromotionInfoLebal().length > 0) {
                    ++counts;
                }
            }
        }
        return counts;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Favorite> findAllFavoriteGoods(Long userId) throws SQLException {
        logger.info("获取我的收藏列表：参数userId" + userId);
        Pagination page = new Pagination();
        Map<String, Object> newConditon = new HashMap<String, Object>();
        // 解析并组装查询条件
        newConditon.put("userId", userId);
        String channel = ConfigurationUtil.getString("CHANNEL");
        newConditon.put("channel", channel);
        page.setObjCondition(newConditon);

        List<Favorite> recourseList =
                myFavoriteDao.findByProperty("Favorite.findAllFavorite", page);
        try {
            return productPriceService.getPriceBatch(recourseList);
        } catch (Exception e) {
            logger.error("获取缓存价格发生异常", e);
        }
        return null;
    }

    @Override
    public Pagination findFavoriteGoodsByUser(Long userId, Pagination page) throws SQLException {
        Map<String, Object> newConditon = new HashMap<String, Object>();
        logger.info("获取用户所收藏的列表：参数userId" + userId);
        // 解析并组装查询条件
        newConditon.put("userId", userId);
        newConditon.put("channel", ConfigurationUtil.getString("CHANNEL"));
        page.setObjCondition(newConditon);

        page =
                myFavoriteDao.findByPage("Favorite.findFavoriteProductByPage",
                        "Favorite.countFavoriteProductByPage", page);
        return page;
    }

    @Override
    public Float showSupplierPoint(Long supplierId) throws Exception {
        BigDecimal avg = BigDecimal.ZERO;
        if (null != supplierId) {
            Map<String, Object> rsMap =
                    orderAssessDetailService.queryShopScore(supplierId);
            Object obj = null;
            if (null != rsMap && null != (obj = rsMap.get("avergScore"))) {
                avg = new BigDecimal(obj.toString());
            }
        }
        return null == avg ? 0f : avg.floatValue();
    }
}
