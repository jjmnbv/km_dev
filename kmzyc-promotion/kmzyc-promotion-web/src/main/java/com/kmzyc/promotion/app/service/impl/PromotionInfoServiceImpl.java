package com.kmzyc.promotion.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.CategoryDAO;
import com.kmzyc.promotion.app.dao.ProdBrandDAO;
import com.kmzyc.promotion.app.dao.PromotionInfoDao;
import com.kmzyc.promotion.app.dao.PromotionProductDAO;
import com.kmzyc.promotion.app.dao.PromotionProductDataDAO;
import com.kmzyc.promotion.app.dao.PromotionRuleDataDAO;
import com.kmzyc.promotion.app.dao.SuppliersInfoDAO;
import com.kmzyc.promotion.app.enums.PromotionStatus;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.app.enums.SuppliersType;
import com.kmzyc.promotion.app.maps.SellUpTypeMap;
import com.kmzyc.promotion.app.service.ProductPriceCacheService;
import com.kmzyc.promotion.app.service.PromotionInfoService;
import com.kmzyc.promotion.app.service.PromotionProductService;
import com.kmzyc.promotion.app.util.CollectionUtils;
import com.kmzyc.promotion.app.util.Constants;
import com.kmzyc.promotion.app.util.ListUtils;
import com.kmzyc.promotion.app.util.PromotionInfoUtil;
import com.kmzyc.promotion.app.vobject.BaseProduct;
import com.kmzyc.promotion.app.vobject.Category;
import com.kmzyc.promotion.app.vobject.Message;
import com.kmzyc.promotion.app.vobject.ProdBrand;
import com.kmzyc.promotion.app.vobject.ProductAndSku;
import com.kmzyc.promotion.app.vobject.ProductSku;
import com.kmzyc.promotion.app.vobject.ProductSkuPrice;
import com.kmzyc.promotion.app.vobject.ProductStock;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.app.vobject.PromotionProduct;
import com.kmzyc.promotion.app.vobject.PromotionRuleData;
import com.kmzyc.promotion.app.vobject.PromotionRuleDataExample;
import com.kmzyc.promotion.constant.PromotionConstant;
import com.kmzyc.promotion.exception.ServiceException;
import com.kmzyc.promotion.optimization.cache.PromotionProcess;
import com.kmzyc.promotion.optimization.vo.PromotionProductData;
import com.kmzyc.promotion.sys.util.RedisCacheUtil;
import com.kmzyc.promotion.util.RedisTemplate;
import com.kmzyc.supplier.model.SuppliersInfo;

import redis.clients.jedis.JedisCluster;

@Service("promotionInfoService")
@SuppressWarnings("unchecked")
public class PromotionInfoServiceImpl implements PromotionInfoService {
    @Resource
    private PromotionInfoDao promotionInfoDao;

    @Resource
    private PromotionRuleDataDAO promotionRuleDataDAO;
    @Resource
    private PromotionProductService promotionProductService;
    @Resource
    private ProductPriceCacheService productPriceCacheService;
    @Resource
    private CategoryDAO categoryDAO;

    @Resource
    private ProdBrandDAO prodBrandDAO;

    @Resource
    private SuppliersInfoDAO suppliersInfoDAO;

    @Resource
    private PromotionProductDAO promotionProductDAO;
    @Resource
    private PromotionProductDataDAO promotionProductDataDAO;
    @Resource
    private PromotionProcess promotionProcess;
    @Resource
    private JedisCluster jedisCluster;
    @Resource
    private RedisTemplate redisTemplate;
    protected Logger logger = LoggerFactory.getLogger(PromotionInfoServiceImpl.class);

    /**
     * 查询当前时间的所有发布活动
     * 
     * @param date为空 ，取所有时间
     * @return
     */
    @Override
    public List<PromotionInfo> getAllPromotionListByTime(Date date) throws ServiceException {
        Map<String, Object> conditionMapNow = new HashMap<String, Object>();
        conditionMapNow.put("queryDate", date);
        List<PromotionInfo> allPromotionList = null;
        try {
            allPromotionList = promotionInfoDao.getPromotionInfoList(conditionMapNow);
        } catch (Exception e) {
            logger.error("查询当前时间的所有发布活动发生异常", e);
            throw new ServiceException(e);
        }
        return allPromotionList;
    }

    /**
     * 获取结束时间大于指定时间指定渠道所有发布的促销活动，用于创建缓存
     * 
     * @param date为空 ，取所有时间
     * @return
     */
    @Override
    public List<PromotionInfo> getExpiryPromotionListByTime(Date date) throws ServiceException {
        Map<String, Object> conditionMapNow = new HashMap<String, Object>();
        conditionMapNow.put("queryEndDate", date);
        List<PromotionInfo> allPromotionList = null;
        try {
            allPromotionList = promotionInfoDao.getPromotionInfoList(conditionMapNow);
        } catch (Exception e) {
            logger.error("获取结束时间大于指定时间指定渠道所有发布的促销活动发生异常", e);
            throw new ServiceException(e);
        }
        return allPromotionList;
    }

    /**
     * 根据活动ID查询活动
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    @Override
    public PromotionInfo getPromotionById(Long promotionInfoId) throws ServiceException {
        PromotionInfo promotionInfo = null;
        try {
            promotionInfo = promotionInfoDao.getPromotionInfoById(promotionInfoId);
            initRuleDatas(promotionInfo);
            // 设置规则数据10特价 8打折 6满减 5加价购 4送券 3满赠
            int PromotionInfoType = promotionInfo.getPromotionType().intValue();
            if (PromotionInfoType == PromotionTypeEnums.SALE.getValue().intValue()) {
                promotionInfo.setPromotionNote(promotionInfo.getPromotionData() == null ? "无统一特价。"
                        : "统一特价:" + promotionInfo.getPromotionData() + "。");
                if (promotionInfo.getSellUpType() != null) {
                    promotionInfo.setPromotionNote(promotionInfo.getPromotionNote() + "   活动库存卖光后"
                            + SellUpTypeMap.getMap().get(promotionInfo.getSellUpType().toString())
                            + "。");
                }
            } else if (PromotionInfoType == PromotionTypeEnums.DISCOUNT.getValue().intValue()) {

                promotionInfo.setPromotionNote("统一折扣:" + promotionInfo.getPromotionData() + "折。");
                if (promotionInfo.getSellUpType() != null) {
                    promotionInfo.setPromotionNote(promotionInfo.getPromotionNote() + "   活动库存卖光后"
                            + SellUpTypeMap.getMap().get(promotionInfo.getSellUpType().toString())
                            + "。");
                }
            } else {
                List<PromotionRuleData> list =
                        promotionRuleDataDAO.selectPromotionRuleList(promotionInfoId);
                promotionInfo.setRuleDatas(list);

            }
            String filterSql = promotionInfo.getProductFilterSql();
            if (null != filterSql && filterSql.length() > 0) {
                StringBuffer sb = new StringBuffer();
                String[] ids = filterSql.split(",");
                List<Long> idList = new ArrayList<Long>();
                for (String id : ids) {
                    if (id.length() > 0) {
                        idList.add(Long.parseLong(id));
                    }
                }
                switch (promotionInfo.getProductFilterType()) {
                    case Constants.PROMOTION_FILTER_TYPE_APPOINT_CATEGORY:
                        List<Category> cList = categoryDAO.queryBatchCategory(idList);
                        if (null != cList && !cList.isEmpty()) {
                            for (Category c : cList) {
                                sb.append(c.getCategoryId()).append(':').append(c.getCategoryName())
                                        .append(',');
                            }
                        }
                        break;
                    case Constants.PROMOTION_FILTER_TYPE_APPOINT_BRAND:
                        List<ProdBrand> pbList = prodBrandDAO.queryBatchBrand(idList);
                        if (null != pbList && !pbList.isEmpty()) {
                            for (ProdBrand pb : pbList) {
                                sb.append(pb.getBrandId()).append(':').append(pb.getBrandName())
                                        .append(',');
                            }
                        }
                        break;
                    default:
                        break;
                }
                promotionInfo.setPromotionFilterProductName(sb.toString());
            }
        } catch (Exception e) {
            logger.error("根据活动ID查询活动发生异常", e);
            throw new ServiceException(e);
        }
        return promotionInfo;
    }

    /**
     * 从指定列表中获取该商品参加的活动
     * 
     * @param sku
     * @param infoList
     * @return
     */
    @Override
    public List<PromotionInfo> getAllPromotionInfoByProductSku(ProductAndSku sku,
            List<PromotionInfo> infoList) throws ServiceException {
        List<PromotionInfo> productInPromotionInfoList = new ArrayList<PromotionInfo>();
        for (PromotionInfo info : infoList) {
            if (inPromotionInfoByPAS(sku, info)) {
                // 设置活动的规则数据
                initRuleDatas(info);
                productInPromotionInfoList.add(info);
            }
        }
        return productInPromotionInfoList;
    }

    /**
     * 从指定列表中获取该商品参加的活动
     * 
     * @param sku
     * @param infoList
     * @return
     */
    @Override
    public List<PromotionInfo> getAllPromotionInfoByProduct(ProductSku sku,
            List<PromotionInfo> infoList) throws ServiceException {
        List<PromotionInfo> productInPromotionInfoList = new ArrayList<PromotionInfo>();
        for (PromotionInfo info : infoList) {
            if (inPromotionInfoBySku(sku, info)) {
                // 设置活动的规则数据
                initRuleDatas(info);
                productInPromotionInfoList.add(info);
            }
        }
        return productInPromotionInfoList;
    }

    /**
     * 初始化规则数据，打折、特价无规则数据
     */
    @Override
    public void initRuleDatas(PromotionInfo info) throws ServiceException {
        int type = info.getPromotionType().intValue();
        if (type == PromotionTypeEnums.SALE.getValue().intValue()
                || type == PromotionTypeEnums.DISCOUNT.getValue().intValue()
                || ListUtils.isNotEmpty(info.getRuleDatas())) {
            return;
        }
        List<PromotionRuleData> list = null;
        try {
            list = promotionRuleDataDAO.selectPromotionRuleList(info.getPromotionId());
        } catch (Exception e) {
            logger.error(" 初始化规则数据，打折、特价无规则数据发生异常", e);
            throw new ServiceException(e);
        }
        if (!ListUtils.isNotEmpty(list)) {
            return;
        }
        info.setRuleDatas(list);
    }

    /**
     * 获取商品在指定时间内所有的活动列表
     * 
     * @param sku
     * @param date
     * @return
     */
    @Override
    public List<PromotionInfo> getAllPromotionInfoByProductSku(ProductAndSku sku, Date date)
            throws ServiceException {
        List<PromotionInfo> infoList = getAllPromotionListByTime(date);
        if (infoList == null || infoList.isEmpty())
            return null;
        List<PromotionInfo> inList = getAllPromotionInfoByProductSku(sku, infoList);
        if (inList == null || inList.isEmpty())
            return null;
        return inList;
    }

    /**
     * 判断商品是否参加活动
     * 
     * @param pas
     * @param info
     * @return
     */
    @Override
    public boolean inPromotionInfoByPAS(ProductAndSku pas, PromotionInfo info)
            throws ServiceException {

        boolean rs = false;
        try {
            String value = RedisCacheUtil.getProductInPromotionCache(jedisCluster,
                    pas.getProductSkuId(), info.getPromotionId());
            if (StringUtils.isBlank(value)) {
                rs = innerinInPromotionInfoByPAS(pas, info);
                RedisCacheUtil.createProductInPromotionCache(jedisCluster, pas.getProductSkuId(),
                        info, rs);
            } else if (rs) {
                rs = Boolean.parseBoolean(value);
                if (rs && Constants.PROMOTION_FILTER_TYPE_APPOINT_PRODUCT == info
                        .getProductFilterType().intValue()
                        && info.getPromotionType().equals(PromotionTypeEnums.SALE.getValue())
                        && pas.getProductSkuId() > 0) {
                    try {
                        PromotionProduct pp = promotionProductDAO.getPromotionProductPrice(
                                pas.getProductSkuId(), info.getPromotionId());
                        if (null != pp && pp.getPrice() != null) {
                            info.setPrivilegeData(pp.getPrice());
                        }
                    } catch (Exception e) {
                        logger.error("根据活动id、skuid获取价格发生异常", e);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取产品是否参加活动缓存", e);
        }

        return rs;
    }

    /**
     * 判断商品是否参加活动
     * 
     * @param sku
     * @param info
     * @return
     */
    private boolean innerinInPromotionInfoByPAS(ProductAndSku sku, PromotionInfo info)
            throws ServiceException {
        int shopSort = info.getShopSort().intValue();
        Long promotionId = info.getPromotionId();
        String shopCode = sku.getShopCode();
        if (Constants.PROMOTION_FULL_COURT_FLAG.equals(shopCode)) {
            // 全场
            return shopSort == 1;
        } else if (shopSort == Constants.PROMOTION_APPOINT_SELLER_FLAG
                && !info.getSupplierId().toString().equals(shopCode)) {
            // 指定入驻商家
            return false;
        } else if (shopSort == Constants.PROMOTION_SELF_AND_PROXY_SELLER_FLAG
                && !Constants.SELF_SELLER_SHOP_CODE.equals(shopCode)) {// 自营代销
            try {
                SuppliersInfo sup = suppliersInfoDAO.selectByPrimaryKey(Long.valueOf(shopCode));
                if (sup.getSupplierType().intValue() != 3) {
                    return false;// 代销
                }
            } catch (Exception e) {
                logger.error("判断商品是否参与活动时查询供应商失败", e);
                return false;
            }
        }
        String filter = info.getProductFilterSql();
        if (filter == null) {
            filter = "";
        }
        filter = "," + filter + ",";
        int productFilterType = info.getProductFilterType().intValue();
        // shopSort商家类别 ：1所有；2指定商家；3后期商家参与
        switch (productFilterType) {
            case Constants.PROMOTION_FILTER_TYPE_FULL_COURT:
                return true;
            case Constants.PROMOTION_FILTER_TYPE_APPOINT_PRODUCT:
                if (sku.getProductSkuId().intValue() <= 0) {
                    return false;
                }
                PromotionProduct pp = null;
                try {
                    pp = promotionProductDAO.getPromotionProductPrice(sku.getProductSkuId(),
                            promotionId);
                } catch (Exception e) {
                    logger.error("根据活动id、skuid获取价格发生异常", e);
                    return false;
                }
                boolean isIn = null != pp;
                if (!isIn)
                    return isIn;
                if (info.getPromotionType().equals(PromotionTypeEnums.SALE.getValue())) {
                    if (pp.getPrice() == null)
                        return false;
                    info.setPrivilegeData(pp.getPrice());
                }
                return true;// 不为空说明存在
            case Constants.PROMOTION_FILTER_TYPE_APPOINT_CATEGORY:
                if (sku.getProductSkuId().intValue() <= 0) {
                    return false;
                }
                if (filter.contains("," + sku.getCategoryId() + ",")) {
                    return true;
                }
                break;
            case Constants.PROMOTION_FILTER_TYPE_APPOINT_BRAND:
                if (sku.getProductSkuId().intValue() <= 0) {
                    return false;
                }
                if (filter.contains("," + sku.getBrandId() + ",")) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 判断商品是否参加活动
     * 
     * @param sku
     * @param info
     * 
     * @return
     */
    private boolean inPromotionInfoBySku(ProductSku sku, PromotionInfo info)
            throws ServiceException {
        boolean rs = false;
        try {
            // 获取缓存中存放的商品是否参加缓存的标识
            String value = RedisCacheUtil.getProductInPromotionCache(jedisCluster,
                    sku.getProductSkuId(), info.getPromotionId());
            if (StringUtils.isBlank(value)) {
                rs = innerinPromotionInfoBySku(sku, info);
                RedisCacheUtil.createProductInPromotionCache(jedisCluster, sku.getProductSkuId(),
                        info, rs);
            } else {
                rs = Boolean.valueOf(value);
            }
        } catch (Exception e) {
            logger.error("获取产品是否参加活动缓存", e);
        }

        return rs;
    }

    /**
     * 判断商品是否参加活动
     * 
     * @param sku
     * @param info
     * 
     * @return
     */
    private boolean innerinPromotionInfoBySku(ProductSku sku, PromotionInfo info)
            throws ServiceException {

        int shopSort = info.getShopSort().intValue();
        Long promotionId = info.getPromotionId();
        String shopCode = sku.getShopCode();
        if (Constants.PROMOTION_FULL_COURT_FLAG.equals(shopCode)) {
            // 全场
            return shopSort == 1;
        } else if (shopSort == Constants.PROMOTION_APPOINT_SELLER_FLAG
                && !info.getSupplierId().toString().equals(shopCode)) {
            // 指定入驻商家
            return false;
        } else if (shopSort == Constants.PROMOTION_SELF_AND_PROXY_SELLER_FLAG
                && !Constants.SELF_SELLER_SHOP_CODE.equals(shopCode)) {// 自营代销
            try {
                SuppliersInfo sup = suppliersInfoDAO.selectByPrimaryKey(Long.valueOf(shopCode));
                if (sup.getSupplierType().intValue() != 3) {
                    return false;// 代销
                }
            } catch (Exception e) {
                logger.error("判断商品是否参与活动时查询供应商失败", e);
                return false;
            }
        }
        String filter = info.getProductFilterSql();
        if (filter == null) {
            filter = "";
        }
        filter = "," + filter + ",";
        int productFilterType = info.getProductFilterType().intValue();
        // shopSort商家类别 ：1所有；2指定商家；3后期商家参与
        switch (productFilterType) {
            case Constants.PROMOTION_FILTER_TYPE_FULL_COURT:
                return true;
            case Constants.PROMOTION_FILTER_TYPE_APPOINT_PRODUCT:
                if (sku.getProductSkuId().intValue() <= 0) {
                    return false;
                }
                PromotionProduct pp = null;
                try {
                    pp = promotionProductDAO.getPromotionProductPrice(sku.getProductSkuId(),
                            promotionId);
                } catch (Exception e) {
                    logger.error("根据活动id、skuid获取价格发生异常", e);
                    return false;
                }
                boolean isIn = null != pp;
                if (!isIn)
                    return isIn;
                if (info.getPromotionType().equals(PromotionTypeEnums.SALE.getValue())) {
                    if (pp.getPrice() == null)
                        return false;
                    info.setPrivilegeData(pp.getPrice());
                }
                return true;// 不为空说明存在
            case Constants.PROMOTION_FILTER_TYPE_APPOINT_CATEGORY:
                if (sku.getProductSkuId().intValue() <= 0) {
                    return false;
                }
                if (filter.contains("," + sku.getCategoryId() + ",")) {
                    return true;
                }
                break;
            case Constants.PROMOTION_FILTER_TYPE_APPOINT_BRAND:
                if (sku.getProductSkuId().intValue() <= 0) {
                    return false;
                }
                if (filter.contains("," + sku.getBrandId() + ",")) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 判断商品（已有特价、打折活动）是否参加活动
     * 
     * @param bp
     * @param pid
     * @return
     * @throws ServiceException
     */
    @Override
    public boolean inPromotionInfo(BaseProduct bp, Long pid) {
        PromotionInfo sale = bp.getSalePromotionInfo();
        PromotionInfo dis = bp.getDiscountPromotionInfo();
        StringBuffer mutax = new StringBuffer();
        if (sale != null) {
            mutax.append(sale.getMutexPromotionId());
        }
        if (dis != null) {
            mutax.append(dis.getMutexPromotionId());
        }
        boolean notIn = mutax.indexOf(Constants.PROMOTION_MUTEX_ALL_PROMOTION_FLAG) >= 0
                || mutax.indexOf(pid.toString()) >= 0 || !bp.getPromotionInfoMap().containsKey(pid);
        return !notIn;
    }

    /**
     * 判断商品是否参加活动
     * 
     * @param sku
     * @param info
     * @return
     */
    @Override
    public boolean inPromotionInfoAndInitRule(BaseProduct bp, PromotionInfo info)
            throws ServiceException {
        // 为false，不设置产品属性，所以直接返回
        Boolean rs = false;
        try {
            BaseProduct temp = RedisCacheUtil.getPPCache(jedisCluster, bp.getProductSkuId(), info);
            if (null != temp && null != temp.getProductSkuId()) {
                if (info.getPromotionType().compareTo(PromotionTypeEnums.SALE.getValue()) == 0
                        || info.getPromotionType()
                                .compareTo(PromotionTypeEnums.DISCOUNT.getValue()) == 0) {
                    if (Constants.PROMOTION_FILTER_TYPE_APPOINT_PRODUCT == info
                            .getProductFilterType().intValue()) {
                        try {
                            PromotionProduct pp = promotionProductDAO.getPromotionProductPrice(
                                    bp.getProductSkuId(), info.getPromotionId());
                            if (null == pp) {
                                return false;
                            }
                            if (pp.getPrice() == null) {
                                info.setPrivilegeData(bp.getPrice());
                            } else {
                                info.setPrivilegeData(pp.getPrice());
                            }
                        } catch (Exception e) {
                            logger.error("根据活动id、skuid获取价格发生异常", e);
                            return false;
                        }
                    }
                    // 取得最终价格
                    bp.setFinalPrice(temp.getFinalPrice());
                    bp.setMinBuy(temp.getMinBuy());
                    bp.setMaxBuy(temp.getMaxBuy());
                    bp.setPromotionStock(temp.getPromotionStock());
                    bp.setPromotionSell(temp.getPromotionSell());
                    bp.setPromoStartTime(info.getStartTime());
                    bp.setPromoEndTime(info.getEndTime());
                }
                rs = true;
            } else if (null == temp) {
                rs = InPromotionInfoAndInitRule(bp, info);
                RedisCacheUtil.createPPCache(jedisCluster, bp, info, rs);
            }
        } catch (Exception e) {
            logger.error("获取产品是否参加活动缓存", e);
        }
        return rs;
    }

    /**
     * 判断商品是否参加活动
     * 
     * @param sku
     * @param info
     * @return
     */
    private boolean InPromotionInfoAndInitRule(BaseProduct bp, PromotionInfo info)
            throws ServiceException {
        // 活动商家code
        Integer shopSort = info.getShopSort().intValue();
        Long promotionId = info.getPromotionId();
        // 产品商家code
        String shopCode = bp.getSupplierCode();
        Long infoSellerId = info.getSellerId();
        // 商家类别 ：1所有；2指定入驻商家；3康美自营代销；4后期
        if (shopSort == Constants.PROMOTION_APPOINT_SELLER_FLAG) {
            // 指定入驻商家
            boolean isIn = infoSellerId.compareTo(Long.valueOf(shopCode)) == 0;
            if (isIn == false) {
                return false;
            }
        } else if (shopSort == Constants.PROMOTION_SELF_AND_PROXY_SELLER_FLAG) {// 自营代销
            try {
                SuppliersInfo sup = suppliersInfoDAO.selectByPrimaryKey(Long.valueOf(shopCode));
                if (0 == sup.getSupplierType().compareTo(SuppliersType.SELL.getStatus())) {
                    return false;// 代销
                }
            } catch (Exception e) {
                logger.error("判断商品是否参与活动时查询供应商失败", e);
                return false;
            }
        }
        String filter = info.getProductFilterSql();
        if (filter == null) {
            filter = "";
        }
        filter = "," + filter + ",";
        int productFilterType = info.getProductFilterType().intValue();
        // shopSort商家类别 ：1所有；2指定商家；3后期商家参与
        switch (productFilterType) {
            case Constants.PROMOTION_FILTER_TYPE_FULL_COURT:
                return true;
            case Constants.PROMOTION_FILTER_TYPE_APPOINT_PRODUCT:
                PromotionProduct pp = null;
                try {
                    pp = promotionProductDAO.getPromotionProductPrice(bp.getProductSkuId(),
                            promotionId);
                } catch (Exception e) {
                    logger.error("根据活动id、skuid获取价格发生异常", e);
                    return false;
                }
                boolean isIn = null != pp;
                if (!isIn)
                    return isIn;
                if (info.getPromotionType().compareTo(PromotionTypeEnums.SALE.getValue()) == 0
                        || info.getPromotionType()
                                .compareTo(PromotionTypeEnums.DISCOUNT.getValue()) == 0) {
                    if (pp.getPrice() == null) {
                        info.setPrivilegeData(bp.getPrice());
                    } else {
                        info.setPrivilegeData(pp.getPrice());
                    }
                    // 取得最终价格
                    bp.setFinalPrice(pp.getPrice());
                    bp.setMinBuy(pp.getMinBuy());
                    bp.setMaxBuy(pp.getMaxBuy());
                    bp.setPromotionStock(pp.getPromotionStock());
                    bp.setPromotionSell(pp.getPromotionSell());
                    bp.setPromoStartTime(info.getStartTime());
                    bp.setPromoEndTime(info.getEndTime());
                }
                return true;// 不为空说明存在
            case Constants.PROMOTION_FILTER_TYPE_APPOINT_CATEGORY:
                if (filter.contains("," + bp.getCategoryId() + ",")) {
                    return true;
                }
                break;
            case Constants.PROMOTION_FILTER_TYPE_APPOINT_BRAND:
                if (filter.contains("," + bp.getBrandId() + ",")) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 获取指定时间的活动和价格信息
     * 
     * @param productSkuPrice 需要设置初始价格
     * @param middDate 指定时间
     * @param promotionInfoList 商品可以参与的活动
     * @return
     */
    @Override
    public ProductSkuPrice getProductSkuPricePromotionInfoByDB(ProductSkuPrice productSkuPrice,
            Date middDate, List<PromotionInfo> promotionInfoList) throws ServiceException {
        /** 原价 */
        BigDecimal price = productSkuPrice.getUnitPrice();
        List<PromotionInfo> orderPromotionInfo = new ArrayList<PromotionInfo>();
        PromotionInfo sale = null;
        PromotionInfo discount = null;
        String lebal = "";
        // 互斥ID
        String mutualIds = "";
        for (PromotionInfo info : promotionInfoList) {
            Date start = info.getStartTime();
            Date end = info.getEndTime();
            if (start == null) {
                start = new Date();
            }
            if (middDate.before(start))
                continue;
            if (middDate.after(end))
                continue;
            // productSkuPrice.setSalePromotionInfo(salePromotionInfo);
            int type = info.getPromotionType();
            // 活动类型10特价；8打折；6满额减免；5换购；4满额送券；3满赠
            switch (type) {
                case 10:
                    if (sale == null || sale.compareTo(info) > 0) {
                        sale = info;
                    }
                    break;
                case 8:
                    if (discount == null || discount.compareTo(info) > 0) {
                        discount = info;
                    }
                    break;
                default:
                    orderPromotionInfo.add(info);
                    break;
            }
        }

        // 有特价
        if (sale != null) {
            // 设置特价价格
            if (sale.getPrivilegeData() != null)
                productSkuPrice.setUnitPrice(sale.getPrivilegeData());
            else
                productSkuPrice.setUnitPrice(sale.getPromotionData());
            // 获取特价互斥活动ID
            mutualIds = sale.getMutexPromotionId() == null ? "" : sale.getMutexPromotionId();
            // 设置特价价格
            productSkuPrice.setSalePromotionInfo(sale);
            // 设置特价标签
            lebal = PromotionTypeEnums.SALE.getTitle() + ",";
        }
        if (mutualIds.contains(Constants.PROMOTION_MUTEX_ALL_PROMOTION_FLAG)) {// 特价为强制排他，其他活动不计算了
            productSkuPrice.setPromotionInfoLebal(lebal);
            return productSkuPrice;
        }
        if (discount != null) {
            Integer productSqlType = discount.getProductFilterType();
            // {1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌',5:'指定商家'}
            if (discount.getMutexPromotionId() == null) {
                discount.setMutexPromotionId("");
            }
            if (discount.getMutexPromotionId()
                    .contains(Constants.PROMOTION_MUTEX_ALL_PROMOTION_FLAG)) {
                // 打折强制排他 其他活动不能生效，设为空
                productSkuPrice.setSalePromotionInfo(null);
                if (!productSqlType.equals(1)) {
                    lebal = PromotionTypeEnums.DISCOUNT.getTitle();
                }
                mutualIds = discount.getMutexPromotionId();
                productSkuPrice.setDiscountPromotionInfo(discount);
                productSkuPrice.setPromotionInfoLebal(lebal);
                productSkuPrice.setUnitPrice(price.multiply(BigDecimal.valueOf(0.1))
                        .multiply(discount.getPromotionData()));
                return productSkuPrice;
            } else if (!mutualIds.contains(discount.getPromotionId().toString())) {
                // 如果特价互动的互斥ID不包含该打折活动
                if (!productSqlType.equals(1)) {
                    lebal = lebal + PromotionTypeEnums.DISCOUNT.getTitle() + ",";
                }
                // 获取打折互斥活动ID
                mutualIds += discount.getMutexPromotionId();
                if (productSkuPrice.getUnitPrice() == null) {
                    productSkuPrice.setUnitPrice(BigDecimal.ZERO);
                }
                productSkuPrice.setDiscountPromotionInfo(discount);
                productSkuPrice.setUnitPrice(productSkuPrice.getUnitPrice()
                        .multiply(BigDecimal.valueOf(0.1)).multiply(discount.getPromotionData()));
            }
        }

        if (orderPromotionInfo.isEmpty()) {
            initLebal(productSkuPrice, lebal);
            return productSkuPrice;
        }
        List<PromotionInfo> newOrderPromotionInfo = new ArrayList<PromotionInfo>();
        Collections.sort(orderPromotionInfo);
        for (PromotionInfo p : orderPromotionInfo) {
            Long id = p.getPromotionId();
            if (mutualIds.contains(id + "")) {// 该订单活动为特价或者打折互斥，不能放入商品订单活动信息列表中
                continue;
            }
            newOrderPromotionInfo.add(p);
            Integer filterProductType = p.getProductFilterType();
            if (filterProductType.equals(1)) {// 该全场活动不创建活动label
                continue;
            }
            Integer type = p.getPromotionType();
            String typeName = PromotionInfoUtil.typeNameMap.get(type);

            if (!lebal.contains(typeName)) {
                lebal = lebal + typeName + ",";
            }
        }
        initLebal(productSkuPrice, lebal);
        if (ListUtils.isNotEmpty(newOrderPromotionInfo)) {
            productSkuPrice.setPromotionInfoList(newOrderPromotionInfo);// 设置商品订单活动列表
        }
        return productSkuPrice;
    }

    /**
     * 设置活动标签
     * 
     * @param productSkuPrice
     * @param lebal
     */
    private void initLebal(ProductSkuPrice productSkuPrice, String lebal) {
        if (lebal.startsWith(",")) {
            lebal = lebal.substring(1, lebal.length());
        }
        if (lebal.endsWith(",")) {
            lebal = lebal.substring(0, lebal.length() - 1);
        }
        productSkuPrice.setPromotionInfoLebal(lebal);
    }

    /**
     * 设置限购
     * 
     * @param promotionId
     * @param productSkuId
     * @param productSkuPrice
     * @return
     * @throws ServiceException
     */
    @Override
    public boolean setRestriction(Long promotionId, Long productSkuId,
            ProductSkuPrice productSkuPrice) throws ServiceException {
        boolean hasRestriction = false;
        try {
            PromotionProduct promotionProduct =
                    promotionProductDAO.getPromotionProductPrice(productSkuId, promotionId);
            if (promotionProduct != null) {
                productSkuPrice.setMinBuy(promotionProduct.getMinBuy());
                productSkuPrice.setMaxBuy(promotionProduct.getMaxBuy());
                productSkuPrice.setPromotionStock(promotionProduct.getPromotionStock());
                productSkuPrice.setSalesVolume(promotionProduct.getPromotionSell());
                hasRestriction =
                        promotionProduct.getMinBuy() != null || promotionProduct.getMaxBuy() != null
                                || promotionProduct.getPromotionStock() != null;
            }
        } catch (Exception e) {
            logger.error("设置限购发生异常", e);
            throw new ServiceException(e);
        }
        return hasRestriction;
    }

    /**
     * 根据供应商id获取活动列表
     * 
     * @param supplierId
     * @return
     */
    @Override
    public List<PromotionInfo> getAllPromotionInfoBySupplierId(Long supplierId)
            throws ServiceException {
        Map<String, Object> conditionMapNow = new HashMap<String, Object>();
        conditionMapNow.put("supplierId", supplierId);
        List<PromotionInfo> allPromotionList = null;
        try {
            allPromotionList = promotionInfoDao.getPromotionInfoList(conditionMapNow);
        } catch (Exception e) {
            logger.error("设置限购发生异常", e);
            throw new ServiceException(e);
        }
        return allPromotionList;
    }

    /**
     * 根据商家类别获取活动列表
     * 
     * @param supplierId
     * @return
     */
    @Override
    public List<PromotionInfo> getAllPromotionInfoByType(String shopSort) throws ServiceException {
        Map<String, Object> conditionMapNow = new HashMap<String, Object>();
        conditionMapNow.put("shopSort", Long.valueOf(shopSort.trim()));
        List<PromotionInfo> allPromotionList = null;
        try {
            allPromotionList = promotionInfoDao.getPromotionInfoList(conditionMapNow);
        } catch (Exception e) {
            logger.error("设置限购发生异常", e);
            throw new ServiceException(e);
        }
        return allPromotionList;
    }

    /**
     * 查询存在时间冲突的活动列表
     * 
     * @param p
     * @return
     */
    @Override
    public List<PromotionInfo> queryMutexPromotionList(PromotionInfo p) throws ServiceException {
        List<PromotionInfo> list = null;
        try {
            list = promotionInfoDao.selectMutexPromotion(p);
        } catch (Exception e) {
            logger.error("查询存在时间冲突的活动列表发生异常", e);
            throw new ServiceException(e);
        }
        return list;
    }

    /**
     * 更新撤销活动 0：操作成功 1：操作失败，活动已上线，无法进行该操作，如果活动有问题，你可以撤销活动！ 2：操作失败，活动存在特价价格小于等于0的商品
     * 3：操作失败，该加价购活动组合商品更新失败
     * 
     * @param promotionId
     * @return
     * @throws ServiceException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updataPromotionStatus(Long PromotionInfoId) throws ServiceException {
        PromotionInfo PromotionInfoDb = null;
        try {
            PromotionInfoDb = promotionInfoDao.getPromotionInfoById(PromotionInfoId);
        } catch (SQLException e) {
            logger.error("活动查询失败", e);
            throw new ServiceException("活动审核更新失败", e);
        }
        if (PromotionInfoDb.getStatus().equals(PromotionStatus.ISSUE.getValue())) {// 撤销操作
            if (PromotionInfoDb.getStartTime().before(new Date())) {
                // PromotionInfoDb.setStartTime(new Date());
                return 1;
            }
            issueNotPromotionInfo(PromotionInfoId);
        } else {
            if (StringUtils.isEmpty(PromotionInfoDb.getPromotionName())
                    || PromotionInfoDb.getStartTime() == null
                    || PromotionInfoDb.getEndTime() == null) {
                return 3;
            }
            if (PromotionInfoDb.getPromotionType().equals(PromotionTypeEnums.SALE.getValue())
                    || PromotionInfoDb.getPromotionType()
                            .equals(PromotionTypeEnums.SALE_APP.getValue())) {
                boolean isExist = false;
                try {
                    isExist = promotionProductDAO.selectPriceIsIllegalityCount(PromotionInfoId) > 0;
                } catch (Exception e) {
                    logger.error("查询特价活动商品价格小于等于0的商品数量 发生异常", e);
                }
                if (isExist) {
                    return 2;
                }
            }

            // 校验当活动是指定商品时 是否选择了参加活动的商品
            Integer promotionProductCount = 0;
            if (PromotionInfoDb.getProductFilterType() != null
                    && PromotionInfoDb.getProductFilterType() == 2) {
                PromotionProduct p = new PromotionProduct();
                p.setPromotionId(PromotionInfoDb.getPromotionId());
                Integer count = null;
                try {
                    count = promotionProductDAO.selectCountByVo(p);
                    if (count != null)
                        promotionProductCount = count;
                } catch (Exception e) {
                    logger.error("校验当活动是指定商品时 是否选择了参加活动的商品", e);
                }
                if (count == null || count < 1) {
                    return 4;
                }
            }
            // 附赠赠品校验
            if (PromotionInfoDb.getPromotionType().equals(PromotionTypeEnums.GANT.getValue())
                    && PromotionInfoDb.getPromotionData() == null) {
                Integer count = 0;
                try {
                    count = promotionProductDataDAO
                            .selectCountByPromotionId(PromotionInfoDb.getPromotionId());

                } catch (Exception e) {
                    logger.error("存在活动商品未设置附赠赠品", e);
                }
                if (promotionProductCount > count) {
                    return 5;
                }
            }
            PromotionInfo PromotionInfo = new PromotionInfo();
            PromotionInfo.setPromotionId(PromotionInfoId);
            Date now = new Date();
            if (PromotionInfoDb.getStartTime().before(now)) {
                PromotionInfo.setStartTime(now);
            }
            if (PromotionInfoDb.getSlogan() != null) {
                PromotionInfo.setSlogan(PromotionInfoDb.getSlogan());
            }
            if (PromotionInfoDb.getPromotionDescribe() != null) {
                PromotionInfo.setPromotionDescribe(PromotionInfoDb.getPromotionDescribe());
            }
            boolean updataIncreaseSuccess = issuePromotionInfo(PromotionInfo);
            if (!updataIncreaseSuccess) {
                logger.error("活动审核失败,PromotionInfoId:" + PromotionInfoId);
                throw new ServiceException("操作失败，活动审核更新失败");
            }
        }
        // 活动状态变更后更新缓存
        promotionProcess.createOrUpdatePromotionCacheTask(PromotionInfoId);
        // productPriceCacheService.notifyByPromotionInfoId(PromotionInfoId);
        return 0;
    }

    /**
     * 审核活动
     * 
     * @throws SQLException
     **/
    private boolean issuePromotionInfo(PromotionInfo PromotionInfo) throws ServiceException {
        PromotionInfo.setIsSycnCache(0);
        PromotionInfo.setIsSycnIndex(0);
        PromotionInfo.setStatus(PromotionStatus.ISSUE.getValue());
        try {
            return promotionInfoDao.updateByPromotion(PromotionInfo) == 1;
        } catch (Exception e) {
            logger.error("活动审核更新发生异常", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 撤销审核活动
     * 
     * @throws SQLException
     **/
    private boolean issueNotPromotionInfo(Long PromotionInfoId) throws ServiceException {
        PromotionInfo PromotionInfo = new PromotionInfo();
        PromotionInfo.setIsSycnCache(0);
        PromotionInfo.setIsSycnIndex(0);
        PromotionInfo.setStatus(PromotionStatus.NOT_ISSUE.getValue());
        PromotionInfo.setPromotionId(PromotionInfoId);
        boolean isSuccess;
        try {
            isSuccess = promotionInfoDao.updateByPromotion(PromotionInfo) == 1;
        } catch (Exception e) {
            logger.error("撤销审核活动发生异常", e);
            throw new ServiceException(e);
        }
        return isSuccess;
    }

    /**
     * 设置更新索引状态为已更新
     * 
     * @param p
     * @return
     */
    @Override
    public boolean updateIsSyncIndex(Long PromotionId) throws ServiceException {
        PromotionInfo newPromotionInfo = new PromotionInfo();
        newPromotionInfo.setPromotionId(PromotionId);
        newPromotionInfo.setIsSycnIndex(1);
        try {
            return 1 == promotionInfoDao.updateByPromotion(newPromotionInfo);
        } catch (Exception e) {
            logger.error("设置更新索引状态为已更新", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 设置更新索引状态为未更新
     * 
     * @param p
     * @return
     */
    @Override
    public boolean updateIsNotSyncIndex(Long PromotionInfoId) throws ServiceException {
        PromotionInfo newPromotionInfo = new PromotionInfo();
        newPromotionInfo.setPromotionId(PromotionInfoId);
        newPromotionInfo.setIsSycnIndex(0);
        try {
            return promotionInfoDao.updateByPromotion(newPromotionInfo) > 0;
        } catch (SQLException e) {
            logger.error("设置更新索引状态为未更新发生异常", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 复制一条新的活动数据
     * 
     * @param promotionId
     * @throws ServiceException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void copyPromotion(Long PromotionId) throws ServiceException {
        PromotionInfo p = null;
        try {
            p = promotionInfoDao.getPromotionInfoById(PromotionId);
            String newName = null;
            if (p.getPromotionName() != null && !p.getPromotionName().isEmpty()) {
                newName = "copy data-" + p.getPromotionName().trim();
                p.setPromotionName(newName);
            }
            if (p.getPromotionTitle() != null && !p.getPromotionTitle().isEmpty()) {
                String newTitle = "copy data-" + p.getPromotionTitle().trim();
                p.setPromotionTitle(newTitle);
            }


            p.setPromotionId(null);
            p.setCreatetime(new Date());
            p.setIsSycnCache(0);
            p.setIsSycnIndex(0);
            p.setOnlineStatus(1);
            p.setStatus(PromotionStatus.NOT_ISSUE.getValue());
            Long newId = promotionInfoDao.addPromotion(p);
            promotionProductDAO.copyPromotionProduct(newId, PromotionId);
            // 复制附赠活动赠品数据
            if (p.getPromotionType().equals(PromotionTypeEnums.GANT.getValue())) {
                if (p.getPromotionData() == null) {// 无统一赠品
                    List<PromotionProduct> ppdl = promotionProductDAO.queryListByPromotionId(newId);
                    for (PromotionProduct ppd : ppdl) {
                        promotionProductDataDAO.copyPromotionProductData(newId,
                                ppd.getPromotionProductId(), ppd.getProductSkuId(), PromotionId);
                    }
                } else {// 有统一赠品
                    promotionProductDataDAO.copyPromotionProductDataByPromotionId(newId,
                            PromotionId);
                }
            }
        } catch (Exception e) {
            logger.error("设置更新索引状态为未更新发生异常", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 根据互斥ID查询
     * 
     * @param mutexPromotionId
     * @return
     */
    @Override
    public Map<Long, String> selectExclusionPromotion(String mutexPromotionInfoId) {
        if (StringUtils.isNotEmpty(mutexPromotionInfoId)) {
            try {
                return promotionInfoDao.selectExclusionPromotion(mutexPromotionInfoId);
            } catch (SQLException e) {
                logger.error("设置更新索引状态为未更新发生异常", e);
                throw new ServiceException(e);
            }
        }
        return null;
    }

    /**
     * 
     * 新增活动数据,增加了订单级活动满足条件类型，满 元/件数
     * 
     * @param promotion
     * @param ruleData
     * @param meetDataType
     * @throws ServiceException
     * @return_type void
     * @author songmiao
     * @date 2015-7-27 下午02:42:30
     * @exception @since 1.0.0
     */
    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void addPromotionNew(PromotionInfo p, String prizeData, String meetDataType)
            throws ServiceException {
        initNewPromotionInfo(p);
        List<JSONObject> dataList = null;
        int PromotionInfoType = p.getPromotionType().intValue();
        if (PromotionInfoType == PromotionTypeEnums.SALE.getValue().intValue()
                || PromotionInfoType == PromotionTypeEnums.DISCOUNT.getValue().intValue()
                || PromotionInfoType == PromotionTypeEnums.SALE_APP.getValue().intValue()) {
            if (PromotionInfoType == PromotionTypeEnums.DISCOUNT.getValue().intValue()) {
                if (PromotionInfoUtil.isNumeric(prizeData)) {
                    p.setPromotionData(new BigDecimal(prizeData));
                } else {
                    throw new ServiceException("打折折扣数据不正确");
                }
            } else if (!prizeData.equals("")) {
                if (PromotionInfoUtil.isNumeric(prizeData)) {
                    p.setPromotionData(new BigDecimal(prizeData));
                } else {
                    throw new ServiceException("特价价格不正确");
                }
            }
        } else if (PromotionInfoType == PromotionTypeEnums.GANT.getValue().intValue()) {
            if (!prizeData.equals("-2")) { // 无统一赠品
                p.setPromotionData(new BigDecimal(-1));
                dataList = (List<JSONObject>) JSONObject.parse(prizeData);
            }
        } else {
            dataList = (List<JSONObject>) JSONObject.parse(prizeData);
        }
        Integer promotionType = p.getPromotionType();
        boolean bl = true;
        if (dataList != null) {
            for (JSONObject data : dataList) {
                BigDecimal meetData = data.getBigDecimal("meetData");
                String prizeData1 = data.getString("prizeData");
                Long entity = data.getLong("entity");
                int num = data.getInteger("num");
                if (num == 0 && (promotionType == PromotionTypeEnums.GIFT.getValue().intValue()
                        || promotionType == PromotionTypeEnums.INCREASE.getValue().intValue())) {
                    bl = false;
                }
                if (Double.valueOf(prizeData1) == 0 && (promotionType == PromotionTypeEnums.INCREASE
                        .getValue().intValue()
                        || promotionType == PromotionTypeEnums.REDUCTION.getValue().intValue())) {
                    bl = false;
                }
                if (Double.valueOf(meetData.toString()) == 0
                        && (promotionType != PromotionTypeEnums.DISCOUNT.getValue().intValue()
                                && promotionType != PromotionTypeEnums.SALE.getValue().intValue()
                                && promotionType != PromotionTypeEnums.GANT.getValue()
                                        .intValue())) {
                    bl = false;
                }
                if (Double.valueOf(entity.toString()) == 0
                        && (promotionType == PromotionTypeEnums.INCREASE.getValue().intValue()
                                || promotionType == PromotionTypeEnums.GIFT.getValue().intValue()
                                || promotionType == PromotionTypeEnums.COUPON.getValue()
                                        .intValue())) {
                    bl = false;
                }
            }
            if (!bl) {
                throw new ServiceException("订单级别活动促销数据不正确");
            }
        }
        Long id = null;
        try {
            id = promotionInfoDao.addPromotion(p);
        } catch (SQLException e) {
            logger.error("新增活动数据异常：", e);
        }

        if (dataList != null) {
            if (promotionType == PromotionTypeEnums.GANT.getValue().intValue()) {// 附赠活动有统一赠品
                List<PromotionProductData> promotionProductDataList =
                        new ArrayList<PromotionProductData>();
                for (JSONObject data : dataList) {
                    Long num = data.getLong("num");
                    Long entity = data.getLong("entity");

                    PromotionProductData ppd = new PromotionProductData();
                    ppd.setNum(num);
                    ppd.setProductSkuId(entity);
                    ppd.setPromotionId(id);
                    promotionProductDataList.add(ppd);
                }
                try {
                    promotionProductDataDAO.addPromotionProductDataList(promotionProductDataList);
                } catch (Exception e) {
                    logger.error("批量新增附赠赠品发生异常", e);
                    throw new ServiceException(e);
                }
            } else {
                List<PromotionRuleData> PromotionRuleDataList = new ArrayList<PromotionRuleData>();
                for (JSONObject data : dataList) {

                    BigDecimal meetData = data.getBigDecimal("meetData");
                    String prizeData1 = data.getString("prizeData");
                    Long entity = data.getLong("entity");
                    int num = data.getInteger("num");

                    PromotionRuleData PromotionRuleData = new PromotionRuleData();
                    PromotionRuleData.setPromotionId(id);
                    PromotionRuleData.setMeetData(meetData);// 满足条件
                    PromotionRuleData.setPrizeData(prizeData1);// 优惠数值 价格
                    PromotionRuleData.setNum1(num); // 赠品库存
                    PromotionRuleData.setEntityId(entity);// skuId或者优惠券Id
                    if (meetDataType != null && !meetDataType.equals("")) {
                        PromotionRuleData.setMeetDataType(Integer.valueOf(meetDataType));
                    }
                    PromotionRuleDataList.add(PromotionRuleData);
                }
                try {
                    promotionRuleDataDAO.batchInsertData(PromotionRuleDataList);// 批量新增规则数据
                } catch (Exception e) {
                    logger.error("批量新增规则数据发生异常", e);
                    throw new ServiceException(e);
                }
            }
        }
    }

    /**
     * 新增活动数据
     * 
     * @param p
     * @param prizeData
     * @throws Exception
     */
    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public Long addPromotionNewForRemote(PromotionInfo p, String prizeData, String meetDataType)
            throws ServiceException {
        initNewPromotionInfo(p);
        List<JSONObject> dataList = null;
        Long promotionId = 0l;
        int PromotionInfoType = p.getPromotionType().intValue();
        if (PromotionInfoType == PromotionTypeEnums.SALE.getValue().intValue()
                || PromotionInfoType == PromotionTypeEnums.DISCOUNT.getValue().intValue()) {
            if (PromotionInfoType == PromotionTypeEnums.DISCOUNT.getValue().intValue()) {
                if (PromotionInfoUtil.isNumeric(prizeData)) {
                    p.setPromotionData(new BigDecimal(prizeData));
                } else {
                    throw new ServiceException("打折折扣数据不正确");
                }
            } else if (!prizeData.equals("")) {
                if (PromotionInfoUtil.isNumeric(prizeData)) {
                    p.setPromotionData(new BigDecimal(prizeData));
                } else {
                    throw new ServiceException("特价价格不正确");
                }
            }
        } else {
            dataList = (List<JSONObject>) JSONObject.parse(prizeData);
        }
        Long id = null;
        try {
            id = promotionInfoDao.addPromotion(p);
            promotionId = id;
        } catch (SQLException e) {
            logger.error("addPromotionNewForRemote新增活动数据异常", e);
        }
        if (dataList != null) {
            List<PromotionRuleData> PromotionRuleDataList = new ArrayList<PromotionRuleData>();
            for (JSONObject data : dataList) {
                BigDecimal meetData = data.getBigDecimal("meetData");
                String prizeData1 = data.getString("prizeData");
                Long entity = data.getLong("entity");
                int num = data.getInteger("num");
                PromotionRuleData PromotionRuleData = new PromotionRuleData();
                PromotionRuleData.setPromotionId(id);
                if (meetDataType != null && !meetDataType.isEmpty()) {
                    PromotionRuleData.setMeetDataType(Integer.valueOf(meetDataType));
                }
                PromotionRuleData.setMeetData(meetData);// 满足条件
                PromotionRuleData.setPrizeData(prizeData1);// 优惠数值 价格
                PromotionRuleData.setNum1(num); // 赠品库存
                PromotionRuleData.setEntityId(entity);// skuId或者优惠券Id
                PromotionRuleDataList.add(PromotionRuleData);
            }
            try {
                promotionRuleDataDAO.batchInsertData(PromotionRuleDataList);// 批量新增规则数据
            } catch (Exception e) {
                logger.error("批量新增规则数据发生异常", e);
                throw new ServiceException(e);
            }
        }
        return promotionId;
    }

    /**
     * 初始化活动数据
     * 
     * @param p
     * @throws ServiceException
     */
    private void initNewPromotionInfo(PromotionInfo p) throws ServiceException {
        p.setCreatetime(new Date());
        p.setPromotionPriority(0);
        p.setProductFilterType(2);
        p.setNature(1);
        p.setIsSycnCache(0);
        p.setIsSycnIndex(0);
        p.setOnlineStatus(1);
        p.setStatus(PromotionStatus.NOT_ISSUE.getValue());
    }

    /**
     * 订单结算后，更新促销活动商品销售数，同时更新部分缓存
     * 
     * @param stockList
     * @return
     */
    @Override
    public boolean batchUpdatePromotionSell(List<ProductStock> stockList) throws ServiceException {
        try {
            // 更新数据库和缓存活动销量
            List<ProductStock> updateList = promotionProductDAO.batchUpdatePromotionSell(stockList,
                    "PRODUCT_STOCK.batchUpdatePromotionInfoSell");
            productPriceCacheService.notifyByProductSkuIdList(updateList);
        } catch (Exception e) {
            logger.error("更新促销活动商品销售数发生异常", e);
            return false;
        }
        return true;
    }

    /**
     * 订单取消后后用于更新促销活动销售数的方法
     */
    @Override
    public boolean batchUpdatePromotionSellMin(List<ProductStock> stockList)
            throws ServiceException {
        try {
            // 更新数据库和缓存活动销量
            List<ProductStock> updateList = promotionProductDAO.batchUpdatePromotionSell(stockList,
                    "PRODUCT_STOCK.batchUnlockUpdatePromotionInfoSellForB2B");
            productPriceCacheService.notifyByProductSkuIdList(updateList);

        } catch (Exception e) {
            logger.error("更新促销活动商品销售数发生异常", e);
            return false;
        }
        return true;
    }

    /**
     * 查询是否存在相同的优先级
     * 
     * @param record @return @throws
     */
    @Override
    public boolean checkPromotionPriorityIsVaild(PromotionInfo p) throws ServiceException {
        try {
            return 0 == promotionInfoDao.selectCountPromotionPriority(p);
        } catch (Exception e) {
            logger.error("查询是否存在相同的优先级发生异常", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 删除活动
     * 
     * @param Message
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public Message deletePromotion(PromotionInfo promotion) throws ServiceException {
        try {
            Message message = new Message();
            String[] promotionIds = promotion.getPromotionIds().split(",");
            for (String promotionId : promotionIds) {
                Long pId = Long.valueOf(promotionId);
                PromotionInfo promotionTemp = promotionInfoDao.getPromotionInfoById(pId);
                if (promotionTemp.getStatus().intValue() == PromotionStatus.ISSUE.getValue()
                        .intValue()) {
                    message.setModule("删除促销活动失败,活动已审核");
                    message.setCode(1);// 失败
                    return message;
                }
                PromotionRuleDataExample ex = new PromotionRuleDataExample();
                ex.createCriteria().andPromotionIdEqualTo(pId);
                promotionRuleDataDAO.deleteByExample(ex);
                promotionProductService.deletePromotionProductAll(pId);
                promotionInfoDao.deletePromotionByPromotionId(pId);
                // 删除附赠商品
                if (promotionTemp.getPromotionType().equals(PromotionTypeEnums.GANT.getValue())) {
                    promotionProductDataDAO.deleteByPromotionId(promotion.getPromotionId());
                }
            }
            message.setModule("删除促销活动成功");
            message.setCode(0);// 成功
            return message;
        } catch (Exception e) {
            logger.error("删除活动发生异常", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 分页查询
     * 
     * @param page
     * @param promotion
     * @return
     */
    @Override
    public Page queryPromotionList(Page page, PromotionInfo promotion) throws ServiceException {
        try {
            return promotionInfoDao.queryPage(page, promotion);
        } catch (SQLException e) {
            logger.error("分页查询活动失败", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 分页查询
     * 
     * @param page
     * @param promotion
     * @return
     */
    @Override
    public Page queryPromotionListFroRemote(Page page, PromotionInfo promotion)
            throws ServiceException {
        try {
            return promotionInfoDao.queryPageForRemote(page, promotion);
        } catch (SQLException e) {
            logger.error("分页查询活动失败", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 根据活动种类查询活动
     * 
     * @param promotion
     * @return
     * @throws ServiceException
     */
    public List<PromotionInfo> selectPromotionByNature(PromotionInfo promotion)
            throws ServiceException {
        try {
            return promotionInfoDao.selectPromotionByNature(promotion);
        } catch (SQLException e) {
            logger.error("根据活动种类查询活动发生异常", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 更新
     * 
     * @param p
     */
    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void updatePromotion(PromotionInfo p) throws ServiceException {
        try {
            PromotionInfo promotionDb = promotionInfoDao.getPromotionInfoById(p.getPromotionId());
            p.setIsSycnCache(0);
            p.setIsSycnIndex(0);
            p.setIsSycnCache(PromotionConstant.PROM_ISSYCNCACHE_0);
            if (!promotionDb.getStatus().equals(PromotionStatus.ISSUE.getValue())
                    && p.getProductFilterType() != null) {
                // 活动范围由指定商品变为非指定商品或者由非指定商品变为指定商品时删除promotionproduct表遗留数据
                if (promotionDb.getProductFilterType() == 2 && p.getProductFilterType() != 2) {
                    promotionProductService.deletePromotionProduct(p.getPromotionId()); // 删除指定商品
                } else if (promotionDb.getProductFilterType() != 2
                        && p.getProductFilterType() == 2) {
                    promotionProductService.deletePromotionExceptionProduct(p.getPromotionId());// 删除例外商品
                }
            }
            promotionInfoDao.updateByPromotion(p);
            // 更新活动信息后，如果活动已审核应该更新活动缓存
            if (promotionDb.getStatus().equals(PromotionStatus.ISSUE.getValue())) {
                promotionProcess.updatePromotionInfoNew(promotionDb.getPromotionId());
            }
        } catch (Exception e) {
            logger.error("更新活动发生异常", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 查询规则个数
     * 
     * @param ruldId
     * @return
     * @throws ServiceException
     */
    @Override
    public Integer queryCoutPromotionByRuleId(Long ruldId) throws ServiceException {
        try {
            return promotionInfoDao.queryCoutPromotionByRuleId(ruldId);
        } catch (Exception e) {
            logger.error("根据活动规则ID查询活动个数发生异常", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 加载活动到商品中并设置规则数据
     * 
     * @param <T>
     * @param t
     * @param info
     * @throws ServiceException
     */
    @Override
    public <T extends BaseProduct> void setPromotionInfoToBaseProduct(T t, PromotionInfo info)
            throws ServiceException {
        PromotionInfo tOrginfo = null;
        switch (info.getPromotionType()) {
            case 10:// 特价
                tOrginfo = t.getSalePromotionInfo();
                if (tOrginfo == null || tOrginfo.compareTo(info) > 0) {
                    t.setSalePromotionInfo(info);
                }
                return;
            case 8:// 打折
                tOrginfo = t.getDiscountPromotionInfo();
                if (tOrginfo == null || tOrginfo.compareTo(info) > 0) {
                    t.setDiscountPromotionInfo(info);
                }
            default:
                if (t.getPromotionInfoList() == null) {
                    t.setPromotionInfoList(new ArrayList<PromotionInfo>());
                }
                t.getPromotionInfoList().add(info);
        }
        initRuleDatas(info);
    }

    /**
     * 查询商家有效活动
     * 
     * @param sellerIds
     * @return
     * @throws ServiceException
     */
    @Override
    public Map<Long, List<PromotionInfo>> queryAblePromotions(List<Long> sellerIds)
            throws ServiceException {
        if (null == sellerIds || sellerIds.isEmpty()) {
            return null;
        }
        Map<Long, List<PromotionInfo>> rsMap = new HashMap<Long, List<PromotionInfo>>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopSort", 1);// 全范围的活动
        Long selfKey = Constants.SELF_AND_PROXY_KEY;
        boolean hasSelf = sellerIds.indexOf(selfKey) >= 0;// 是否有自营、代销
        sellerIds.remove(selfKey);
        try {
            List<PromotionInfo> allScorePromotionList =
                    promotionInfoDao.queryAblePromotionsByType(params);
            if (hasSelf) {
                params.put("shopSort", 3);// 自营、代销
                List<PromotionInfo> selfPromotionList =
                        promotionInfoDao.queryAblePromotionsByType(params);
                if (null == selfPromotionList) {
                    selfPromotionList = new ArrayList<PromotionInfo>();
                }
                if (null != allScorePromotionList && !allScorePromotionList.isEmpty()) {
                    selfPromotionList.addAll(CollectionUtils.deepCopy(allScorePromotionList));
                }
                rsMap.put(selfKey, selfPromotionList);
            }
            if (sellerIds.size() > 0) {
                if (sellerIds.size() == 1) {
                    params.put("sellerId", sellerIds.get(0));
                } else {
                    params.put("sellerIds", sellerIds);
                }
                List<PromotionInfo> enterPromotionList =
                        promotionInfoDao.queryAblePromotionsIds(params);
                List<PromotionInfo> promotionList = new ArrayList<PromotionInfo>();// 用于临时存放
                if (null != enterPromotionList && !enterPromotionList.isEmpty()) {
                    Long tempSellerId = null;
                    for (PromotionInfo p : enterPromotionList) {
                        if (null != tempSellerId && !p.getSellerId().equals(tempSellerId)) {
                            rsMap.put(tempSellerId, promotionList);
                            promotionList = new ArrayList<PromotionInfo>();
                            tempSellerId = p.getSellerId();
                        } else if (null == tempSellerId) {
                            tempSellerId = p.getSellerId();
                        }
                        promotionList.add(p);
                    }
                    if (null != tempSellerId) {
                        rsMap.put(tempSellerId, promotionList);
                        promotionList = new ArrayList<PromotionInfo>();
                    }
                }
                if (null != allScorePromotionList && !allScorePromotionList.isEmpty()) {
                    promotionList = new ArrayList<PromotionInfo>();
                    for (Long sellderId : sellerIds) {
                        promotionList = rsMap.get(sellderId);
                        if (null == promotionList) {
                            promotionList = new ArrayList<PromotionInfo>();
                        }
                        promotionList.addAll(CollectionUtils.deepCopy(allScorePromotionList));
                        rsMap.put(sellderId, promotionList);
                    }
                }
            }
        } catch (Exception e) {
            throw new ServiceException(0, "查询商家有效活动发生异常", e);
        }
        return rsMap;

    }

    /**
     * 更新索引状态
     * 
     * @param p
     * @return
     */
    @Override
    public void updateIsSyncIndex(List<Long> pids, Integer syncIndex) throws ServiceException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("syncIndex", syncIndex);
        map.put("pids", pids);
        try {
            promotionInfoDao.updateIsSyncIndex(map);
        } catch (Exception e) {
            throw new ServiceException(0, "查询商家有效活动发生异常", e);
        }
    }

    /**
     * mq使用,根据sku 和 进行中活动列表获取当前价格 add by songmiao 2015 - 5 - 26
     */
    @Override
    public BigDecimal getPriceByPromotionAndProduct(ProductSku sku, Date middDate,
            List<PromotionInfo> list) {
        /** 原价 */
        BigDecimal price = BigDecimal.valueOf(sku.getPrice());
        PromotionInfo sale = null;
        PromotionInfo discount = null;
        // 互斥ID
        String mutualIds = "";
        for (PromotionInfo info : list) {
            if (inPromotionInfoBySku(sku, info)) {
                // 设置活动的规则数据
                initRuleDatas(info);
                Date start = info.getStartTime();
                Date end = info.getEndTime();
                if (middDate.before(start))
                    continue;
                if (middDate.after(end))
                    continue;
                // productSkuPrice.setSalePromotionInfo(salePromotionInfo);
                int type = info.getPromotionType();
                // 活动类型10特价；8打折；6满额减免；5换购；4满额送券；3满赠
                switch (type) {
                    case 10:
                        if (sale == null || sale.compareTo(info) > 0) {
                            sale = info;
                        }
                        break;
                    case 8:
                        if (discount == null || discount.compareTo(info) > 0) {
                            discount = info;
                        }
                        break;
                    default:
                        break;
                }

            }
        }
        // 有特价
        if (sale != null) {
            // 设置特价价格
            if (sale.getPrivilegeData() != null)
                price = sale.getPrivilegeData();
            else
                price = sale.getPromotionData();
            // 获取特价互斥活动ID
            mutualIds = sale.getMutexPromotionId() == null ? "" : sale.getMutexPromotionId();
        }
        if (mutualIds.contains(Constants.PROMOTION_MUTEX_ALL_PROMOTION_FLAG)) {// 特价为强制排他，其他活动不计算了
            return price;
        }
        if (discount != null) {
            if (discount.getMutexPromotionId() == null) {
                discount.setMutexPromotionId("");
            }
            if (discount.getMutexPromotionId()
                    .contains(Constants.PROMOTION_MUTEX_ALL_PROMOTION_FLAG)) {
                // 打折强制排他 其他活动不能生效，设为空
                mutualIds = discount.getMutexPromotionId();
                price = price.multiply(BigDecimal.valueOf(0.1))
                        .multiply(discount.getPromotionData());
                return price;
            } else if (!mutualIds.contains(discount.getPromotionId().toString())) {
                // 获取打折互斥活动ID
                mutualIds += discount.getMutexPromotionId();
                if (price == null) {
                    price = BigDecimal.ZERO;
                }
                price = price.multiply(BigDecimal.valueOf(0.1))
                        .multiply(discount.getPromotionData());
            }
        }
        return price;
    }

    @Override
    public void synPromotionCom(long promotionid) throws ServiceException {
        try {
            promotionProcess.createOrUpdatePromotionCacheTask(promotionid);
        } catch (Exception e) {

            throw new ServiceException(0, "同步促销活动关联的商品缓存失败", e);
        }
    }

    @Override
    public List<PromotionInfo> queryProductEnterablePromotion(Map<String, Object> map)
            throws ServiceException {
        try {
            return this.promotionInfoDao.queryProductEnterablePromotion(map);
        } catch (Exception e) {

            throw new ServiceException(0, "查询商品可参加的促销活动失败", e);
        }
    }

    @Override
    public PromotionInfo promotionFilterProductName(PromotionInfo promotion) {
        String filterSql = promotion.getProductFilterSql();
        try {
            if (null != filterSql && filterSql.length() > 0) {
                StringBuffer sb = new StringBuffer();
                String[] ids = filterSql.split(",");
                List<Long> idList = new ArrayList<Long>();
                for (String id : ids) {
                    if (id.length() > 0) {
                        idList.add(Long.parseLong(id));
                    }
                }
                switch (promotion.getProductFilterType()) {
                    case Constants.PROMOTION_FILTER_TYPE_APPOINT_CATEGORY:
                        List<Category> cList = categoryDAO.queryBatchCategory(idList);
                        if (null != cList && !cList.isEmpty()) {
                            for (Category c : cList) {
                                sb.append(c.getCategoryId()).append(':').append(c.getCategoryName())
                                        .append(',');
                            }
                        }
                        break;
                    case Constants.PROMOTION_FILTER_TYPE_APPOINT_BRAND:
                        List<ProdBrand> pbList = prodBrandDAO.queryBatchBrand(idList);
                        if (null != pbList && !pbList.isEmpty()) {
                            for (ProdBrand pb : pbList) {
                                sb.append(pb.getBrandId()).append(':').append(pb.getBrandName())
                                        .append(',');
                            }
                        }
                        break;
                    default:
                        break;
                }
                promotion.setPromotionFilterProductName(sb.toString());
            } else {
                promotion.setPromotionFilterProductName("");
            }
        } catch (Exception e) {
            logger.error("设置PromotionFilterProductName属性异常", e);
            throw new ServiceException(e);
        }
        return promotion;
    }

    @SuppressWarnings("finally")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createPromotion(List<PromotionProduct> list, PromotionInfo p)
            throws ServiceException {
        Integer result = 1;
        try {
            if (list == null) {
                return 1;
            }
            // 添加活动
            Long id = promotionInfoDao.addPromotion(p);
            Date now = new Date();
            for (PromotionProduct pp : list) {
                pp.setPromotionId(id);
                if (pp.getPrice() == null) {
                    result = 1;
                    break;
                }
                pp.setModifyTime(now);
                pp.setCreateTime(now);
                promotionProductDAO.insertSelective(pp);
                // 活动状态变更后更新缓存
                promotionProcess.createOrUpdatePromotionCacheTask(id);
                result = Integer.valueOf(id.toString());
            }
        } catch (Exception e) {
            logger.error("接口：根据产品创建特价活动失败", e);
        } finally {
            if (result == 1) { // 如果操作失败 抛出异常回滚
                throw new ServiceException("接口：根据产品创建特价活动失败");
            }
            return result;
        }
    }

    @Override
    public Integer promotionIsLock(PromotionInfo promotion) throws ServiceException, SQLException {
        return promotionInfoDao.promotionIsLock(promotion);
    }
}
