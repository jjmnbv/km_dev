package com.kmzyc.promotion.remote.service.impl;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.bean.ResultMessage;
import com.kmzyc.promotion.app.dao.ProductSkuDAO;
import com.kmzyc.promotion.app.dao.PromotionInfoDao;
import com.kmzyc.promotion.app.dao.PromotionProductDAO;
import com.kmzyc.promotion.app.dao.PromotionRuleDataDAO;
import com.kmzyc.promotion.app.enums.PromotionShopSort;
import com.kmzyc.promotion.app.enums.PromotionStatus;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.app.enums.SuppliersType;
import com.kmzyc.promotion.app.service.CouponService;
import com.kmzyc.promotion.app.service.ProductPriceCacheService;
import com.kmzyc.promotion.app.service.PromotionInfoService;
import com.kmzyc.promotion.app.service.PromotionProductService;
import com.kmzyc.promotion.app.service.PromotionRuleDataService;
import com.kmzyc.promotion.app.service.PromotionTypeService;
import com.kmzyc.promotion.app.util.Constants;
import com.kmzyc.promotion.app.util.ListUtils;
import com.kmzyc.promotion.app.util.PromotionInfoUtil;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.OrderProduct;
import com.kmzyc.promotion.app.vobject.ProductAndSku;
import com.kmzyc.promotion.app.vobject.ProductPromotionInfo;
import com.kmzyc.promotion.app.vobject.ProductSkuPrice;
import com.kmzyc.promotion.app.vobject.ProductSkuPriceCache;
import com.kmzyc.promotion.app.vobject.ProductStock;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.app.vobject.PromotionProduct;
import com.kmzyc.promotion.app.vobject.PromotionRuleData;
import com.kmzyc.promotion.exception.ServiceException;
import com.kmzyc.promotion.optimization.cache.PromotionProcess;
import com.kmzyc.promotion.optimization.cache.PromotionProductCacheProcess;
import com.kmzyc.promotion.optimization.vo.ProductPromotion;
import com.kmzyc.promotion.remote.service.PromotionRemoteService;
import com.kmzyc.promotion.util.PromotionCacheUtil;

/**
 * 
 * @author KM
 *
 */
@Service("promotionRemoteService")
public class PromotionRemoteServiceImpl implements PromotionRemoteService {
    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(PromotionRemoteServiceImpl.class);
    @Resource
    private PromotionTypeService promotionTypeService;
    @Resource
    private PromotionInfoService promotionInfoService;
    @Resource
    private PromotionInfoDao promotionInfoDao;
    @Resource
    private PromotionRuleDataDAO promotionRuleDataDAO;
    @Resource
    ProductPriceCacheService productPriceCacheService;
    @Resource
    private ProductSkuDAO productSkuDAO;
    @Resource
    private PromotionProductDAO promotionProductDao;
    @Resource
    private CouponService couponService;
    @Resource
    private PromotionRuleDataService promotionRuleDataService;
    @Resource
    private PromotionProductService promotionProductService;

    @Resource
    PromotionCacheUtil promotionCacheUtil;
    @Resource
    private PromotionProcess promotionProcess;

    @Resource
    private PromotionProductCacheProcess promotionProductCacheProcess;

    /**
     * 根据订单明细获取促销活动信息 退商品专用
     * 
     * @param shopCar
     * @param date
     * @return
     * @throws Exception
     */
    @Override
    public ProductPromotionInfo selectPromotionByOrder(List<OrderProduct> orderProductList,
            Date date) throws Exception {
        ProductPromotionInfo productPromotionInfo = new ProductPromotionInfo();
        if (orderProductList == null)
            return null;
        // 当前时间当前渠道的所有活动
        List<PromotionInfo> infoList = promotionInfoService.getAllPromotionListByTime(date);
        BigDecimal allMoney = BigDecimal.ZERO;
        BigDecimal reductionMoney = BigDecimal.ZERO;
        List<PromotionInfo> meetPromotionInfoList = new ArrayList<PromotionInfo>();
        List<PromotionInfo> orderList = new ArrayList<PromotionInfo>();
        List<String> codesList = new ArrayList<String>();
        for (OrderProduct op : orderProductList) {
            codesList.add(op.getProductSkuCode());
        }
        Map<Long, ProductAndSku> productAndSkuMap = productSkuDAO.findAllProducts(codesList);
        for (OrderProduct op : orderProductList) {
            Long suitId = op.getSuitId();
            op.setExclueAll(false);
            if (suitId != null) {// 套装
                return null;
            }
            // 非套餐
            String productSkuCode = op.getProductSkuCode();
            // ProductAndSku sku =
            // productSkuDAO.findProductBySkuCode(op.getProductSkuCode());
            ProductAndSku sku = productAndSkuMap.get(op.getProductID());
            if (sku == null) {
                throw new ServiceException(0, "sku code:" + productSkuCode + " not exit!");
            }
            List<PromotionInfo> joinPromotionList =
                    promotionInfoService.getAllPromotionInfoByProductSku(sku, infoList);
            op.setMutualIds("");
            if (joinPromotionList != null) {
                ProductSkuPrice productSkuPrice = new ProductSkuPrice();
                productSkuPrice.setUnitPrice(op.getCostPrice());
                productSkuPrice = promotionInfoService.getProductSkuPricePromotionInfoByDB(
                        productSkuPrice, date, joinPromotionList);
                op.setLastPrice(productSkuPrice.getUnitPrice());
                Map<Long, PromotionInfo> promotionInfoMap = getPromotionInfoMap(joinPromotionList);
                op.setPromotionInfoMap(promotionInfoMap);
                PromotionInfo sale = productSkuPrice.getSalePromotionInfo();
                PromotionInfo discount = productSkuPrice.getDiscountPromotionInfo();
                if (sale != null) {
                    op.setMutualIds(op.getMutualIds() + sale.getMutexPromotionId());
                    productPromotionInfo.addAllMeetPromotionInfo(sale);
                }
                if (discount != null) {
                    op.setMutualIds(op.getMutualIds() + discount.getMutexPromotionId());
                    productPromotionInfo.addAllMeetPromotionInfo(discount);
                }
                if (productSkuPrice.getPromotionInfoList() != null
                        && !productSkuPrice.getPromotionInfoList().isEmpty()) {
                    orderList.addAll(productSkuPrice.getPromotionInfoList());
                }
            } else {
                op.setLastPrice(op.getCostPrice());
            }
            allMoney = allMoney.add(op.getLastPrice().multiply(BigDecimal.valueOf(op.getAmount())));
        }
        productPromotionInfo.setAllMoney(allMoney);
        if (orderList != null && !orderList.isEmpty()) {
            orderList = PromotionInfoUtil.sort(orderList);
            Set<Long> set = new HashSet<Long>();
            for (PromotionInfo pi : orderList) {
                Long pid = pi.getPromotionId();
                if (set.contains(pid)) {
                    continue;
                } else {
                    set.add(pid);
                }
                if (pi.getPromotionType().equals(PromotionTypeEnums.SALE.getValue()))
                    continue;
                if (pi.getPromotionType().equals(PromotionTypeEnums.DISCOUNT.getValue()))
                    continue;
                if (pi.getMeetData() == null) {
                    promotionInfoService.initRuleDatas(pi);
                }
                if (pi.getMeetData() == null) {
                    continue;
                }
                BigDecimal total = BigDecimal.ZERO;
                for (OrderProduct op : orderProductList) {
                    if (op.getMutualIds().contains(Constants.PROMOTION_MUTEX_ALL_PROMOTION_FLAG)
                            || op.getMutualIds().contains(pid.toString())) {
                        continue;
                    }
                    Map<Long, PromotionInfo> promotionInfoMap = op.getPromotionInfoMap();
                    if (!promotionInfoMap.containsKey(pid)) {
                        continue;
                    }
                    total = total
                            .add(op.getLastPrice().multiply(BigDecimal.valueOf(op.getAmount())));
                }
                if (total.compareTo(pi.getMeetData()) >= 0) {
                    // 满足条件 活动生效了
                    List<PromotionRuleData> list = pi.getRuleDatas();
                    BigDecimal prize = BigDecimal.valueOf(0);
                    for (int i = list.size() - 1; i >= 0; i--) {
                        PromotionRuleData data = list.get(i);
                        if (total.compareTo(data.getMeetData()) >= 0) {
                            prize = new BigDecimal(data.getPrizeData());
                            Long entityId = data.getEntityId();
                            // 赠品的id 优惠券或产品
                            pi.setPrivilegeData(BigDecimal.valueOf(entityId));
                            break;
                        }
                    }
                    if (pi.getPromotionType().equals(PromotionTypeEnums.REDUCTION.getValue())) {
                        reductionMoney = reductionMoney.add(prize);
                    }
                    meetPromotionInfoList.add(pi);
                    for (OrderProduct op : orderProductList) {
                        if (op.getMutualIds().contains(Constants.PROMOTION_MUTEX_ALL_PROMOTION_FLAG)
                                || op.getMutualIds().contains(pid.toString())) {
                            continue;
                        }
                        Map<Long, PromotionInfo> promotionInfoMap = op.getPromotionInfoMap();
                        if (!promotionInfoMap.containsKey(pid)) {
                            continue;
                        }
                        op.setMutualIds(op.getMutualIds() + pid + ",");

                        if (Constants.PROMOTION_MUTEX_ALL_PROMOTION_FLAG
                                .equals(pi.getMutexPromotionId())) {
                            op.setExclueAll(true);
                        } else {
                            op.removePromotionInMap(pi.getPromotionType());
                            op.removePromotionInMap(pi.getMutexPromotionId());
                        }
                    }
                    // 活动生效了
                }
            }
        }
        productPromotionInfo.setReductionMoney(reductionMoney);
        if (!meetPromotionInfoList.isEmpty()) {
            productPromotionInfo.setMeetPromotionInfos(meetPromotionInfoList);
        }
        return productPromotionInfo;
    }

    private Map<Long, PromotionInfo> getPromotionInfoMap(List<PromotionInfo> joinPromotionList) {
        Map<Long, PromotionInfo> promotionInfoMap = new ConcurrentHashMap<Long, PromotionInfo>();
        for (PromotionInfo pi : joinPromotionList) {
            promotionInfoMap.put(pi.getPromotionId(), pi);
        }
        return promotionInfoMap;
    }

    @Override
    public List<ProductSkuPriceCache> getProductSkuPriceBatch(List<ProductSkuPriceCache> list,
            Date date) {
        // Auto-generated method stub
        if (list == null || list.isEmpty())
            return null;
        // Date date = new Date();
        List<ProductSkuPriceCache> newList = new ArrayList<ProductSkuPriceCache>();
        for (ProductSkuPriceCache t : list) {
            try {
                t = this.getProductSkuPrice(t, date);
                newList.add(t);
            } catch (Exception e) {
                // : handle exception
            }
        }
        return newList;
    }

    /** 远程接口 cms获取价格和活动标签 */
    @Override
    public ProductSkuPriceCache getProductSkuPrice(ProductSkuPriceCache productSkuPriceCache,
            Date date) {
        // Auto-generated method stub
        ProductAndSku sku = null;
        try {
            sku = productSkuDAO.findProduct(productSkuPriceCache.getProductSkuId());
        } catch (SQLException e) {
            // Auto-generated catch block
            logger.error("skuId:{}远程接口 cms获取价格和活动标签！sku查询出错！",
                    productSkuPriceCache.getProductSkuId(), e);
        }
        if (sku == null || sku.getPrice() == null) {
            return productSkuPriceCache;
        }
        Double price = sku.getPrice();
        productSkuPriceCache.setPromotionPrice(price);
        ProductPromotion pp = null;
        try {
            pp = promotionCacheUtil.getProductPromtoionInfoCahce(sku.getProductSkuId().toString());
        } catch (Exception e) {
            logger.error("skuId:{}远程接口 cms获取价格和活动标签！查询活动出错！",
                    productSkuPriceCache.getProductSkuId(), e);
        }
        if (pp == null) {
            return productSkuPriceCache;
        }
        if (price != null) {
            productSkuPriceCache.setPromotionPrice(
                    Double.valueOf(pp.calculateFinalPrice(BigDecimal.valueOf(price)).toString()));
        }
        String[] lebal = pp.getLabelArray();
        if (lebal != null) {
            productSkuPriceCache.setPromotionInfoLebal(lebal);
        }
        return productSkuPriceCache;
    }

    @Override
    public ProductSkuPriceCache getCurrentTimeProductSkuPrice(ProductSkuPriceCache t) {
        ProductSkuPriceCache pspc = t;
        pspc = this.getProductSkuPrice(t, new Date());
        return pspc;
    }

    @Override
    public Map<Integer, String> getPromotionTypeMap() {
        return promotionTypeService.getPromotionTypeMap();
    }

    @Override
    public void updateProductPromotionCache(Long productSkuId) {

        List<PromotionInfo> list = null;
        logger.info("产品上线-更新活动缓存skuId：{}", productSkuId);
        // 根据skuId查询可参与的活动信息
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productSkuId", productSkuId);

        try {
            list = this.promotionInfoDao.queryProductEnterablePromotion(map);
            if (null != list && list.size() > 0) {
                this.promotionProductCacheProcess.updateProductPromotionCache(productSkuId, list);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    @Override
    public PromotionInfo getDiscountPromotionInfo(Date date) {
        List<PromotionInfo> list = promotionInfoService.getAllPromotionListByTime(date);
        if (list == null || list.isEmpty())
            return null;
        PromotionInfo discountPromotionInfo = null;
        for (PromotionInfo info : list) {
            // 全场打折
            if (0 == info.getPromotionType().compareTo(PromotionTypeEnums.DISCOUNT.getValue())
                    && info.getProductFilterType() == 1 && 1 == info.getShopSort()
                    && (discountPromotionInfo == null
                            || info.compareTo(discountPromotionInfo) < 0)) {
                discountPromotionInfo = info;
            }
        }
        if (discountPromotionInfo == null) {
            return null;
        }
        discountPromotionInfo.setPrivilegeData(discountPromotionInfo.getPromotionData());
        return discountPromotionInfo;
    }

    /**
     * 供应商类型发生变化后更新缓存 supplierId：供应商id beforeSuppliersType： 修改之前的供应商类型 afterSuppliersType：修改之后的供应商类型
     * 逻辑：当自营改成代销或代销改为自营时return不发生变化
     */
    @Override
    public void updateCacheBySupplier(Long supplierId, String beforeSuppliersType,
            String afterSuppliersType) {
        String emterAndSupport = SuppliersType.EMTER + "," + SuppliersType.SUPPORT;
        // 当自营改成代销或代销改为自营时return不发生变化
        if (emterAndSupport.contains(beforeSuppliersType)
                && emterAndSupport.contains(afterSuppliersType)) {
            return;
        }
        try {
            // 当供应商修改之前的状态为入驻时
            if (SuppliersType.SELL.getStatus().toString().equals(beforeSuppliersType)) {
                if (supplierId == null) {
                    return;
                }
                // 根据供应商id查询出相关活动
                List<PromotionInfo> promotionInfoList =
                        promotionInfoService.getAllPromotionInfoBySupplierId(supplierId);
                for (PromotionInfo promotionInfo : promotionInfoList) {
                    // 更新缓存，并将活动索引状态设置成未更新
                    productPriceCacheService.updatePriceCacheByPromotion(promotionInfo);
                    promotionInfoService.updateIsNotSyncIndex(promotionInfo.getPromotionId());
                }
            } else {
                // 当供应商修改之前的状态为自营或代销时
                // 查询出自营代销的活动列表
                List<PromotionInfo> promotionInfoList =
                        promotionInfoService.getAllPromotionInfoByType("3");
                for (PromotionInfo promotionInfo : promotionInfoList) {
                    // 更新缓存，并将活动索引状态设置成未更新
                    productPriceCacheService.updatePriceCacheByPromotion(promotionInfo);
                    promotionInfoService.updateIsNotSyncIndex(promotionInfo.getPromotionId());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 
     * @param stockList 库存发生变化的产品列表
     * @param channel 订单渠道
     * @param type 操作类型 ： "0" 为下单 ，"1" 为取消订单时
     * @return
     */
    @Override
    public boolean updatePromotionStock(List<ProductStock> stockList, String type) {
        if (stockList == null || type == null) {
            return false;
        }
        try {
            if (type.trim().equals("0")) {
                promotionInfoService.batchUpdatePromotionSell(stockList);
            } else {
                promotionInfoService.batchUpdatePromotionSellMin(stockList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /***
     * 根据活动ID获取活动信息 参数：活动ID
     * 
     * @throws Exception
     */
    @Override
    public PromotionInfo getPromotionById(Long promotionId) throws Exception {
        return promotionInfoService.getPromotionById(promotionId);
    }

    /***
     * 获取活动规则数据 参数：活动ID
     * 
     * @throws Exception
     */
    @Override
    public List<PromotionRuleData> getPromotionRuleInfoList(Long promtionId) throws Exception {
        List<PromotionRuleData> promotionRuleDataList =
                promotionRuleDataDAO.selectPromotionRuleList(promtionId);
        return promotionRuleDataList;
    }

    /***
     * 根据商家类型查询有效活动
     */
    @Override
    public List<PromotionInfo> getPromtionBySupplierType(Long shopSort) {
        List<PromotionInfo> PromotionList = null;
        try {
            PromotionList = promotionInfoDao.getPromtionBySupplierType(shopSort);
        } catch (Exception e) {
            logger.error("根据商家类型查询有效活动发生异常", e);
        }
        return PromotionList;
    }

    /***
     * 根据SkuId查询限购活动 参数：产品skuId
     */
    @Override
    public PromotionInfo getPurchasePromontionBySkuId(Long skuId) throws Exception {
        PromotionInfo promotionInfo = null;
        try {
            List<PromotionInfo> promList = promotionInfoDao.getPurchasePromontionBySkuId(skuId);
            if (null != promList && !promList.isEmpty()) {
                for (PromotionInfo p : promList) {
                    if (null == promotionInfo || p.compareTo(promotionInfo) < 0) {
                        promotionInfo = p;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("根据SkuId查询限购活动发生异常", e);
            throw e;
        }
        return promotionInfo;
    }

    /***
     * 查询商家有效活动 参数：1渠道ID ,2活动商家ID
     */
    @Override
    public List<PromotionInfo> getValidPromotionListBySupplierId(Long supplierId,
            List<Long> sellerIds) throws Exception {
        List<PromotionInfo> promotionInfoList = null;
        try {
            promotionInfoList =
                    promotionInfoDao.getValidPromotionListBySupplierId(supplierId, sellerIds);
        } catch (Exception e) {
            logger.error("查询商家有效活动发生异常", e);
        }
        return promotionInfoList;
    }

    /**
     * 判断商品是否参加活动
     */
    @Override
    public boolean inPromotionInfo(Long skuId, PromotionInfo info) throws Exception {
        boolean isIn = false;
        try {
            ProductAndSku sku = productSkuDAO.findProduct(skuId);
            isIn = promotionInfoService.inPromotionInfoByPAS(sku, info);
        } catch (Exception e) {
            logger.error("判断商品是否参加活动", e);
        }
        return isIn;
    }

    /**
     * 分页获取活动列表
     */
    @Override
    public Page getPromotionInfoListByPage(Page page, PromotionInfo promotion) {
        return promotionInfoService.queryPromotionListFroRemote(page, promotion);
    }

    /**
     * 查询存在时间冲突的活动列表
     */
    @Override
    public List<PromotionInfo> queryMutexPromotionList(PromotionInfo promotion) {
        // 当活动类型为全部商家时，互斥活动选择列表查询所有商家类型活动
        if (promotion != null && promotion.getShopSort() != null && promotion.getShopSort()
                .intValue() == PromotionShopSort.ALL.getValue().intValue()) {
            promotion.setShopSort(null);
        }
        return promotionInfoService.queryMutexPromotionList(promotion);
    }

    /**
     * 复制活动 return ： 0 成功 1失败
     */

    @Override
    public Integer copyPromotion(Long promotionId) {
        Integer result = 1;
        try {
            promotionInfoService.copyPromotion(promotionId);
            result = 0;
        } catch (Exception e) {
            logger.error("复制活动发生异常！", e);
        }
        return result;
    }

    /**
     * 批量删除活动 return ： 0 成功 1失败
     */
    @Override
    public Integer deletePromotions(String promotionIds) {
        Integer result = 1;
        PromotionInfo promotion = new PromotionInfo();
        try {
            promotion.setPromotionIds(promotionIds);
            promotionInfoService.deletePromotion(promotion);
            result = 0;
        } catch (Exception e) {
            logger.info("删除活动失败 promotionId{}", promotion.getPromotionId(), e);
        }
        return result;
    }

    /**
     * 审核或者撤销审核一个活动
     */
    @Override
    public int updateIssuePromotion(Long promotionId) {
        /*
         * 0：操作成功 1：操作失败，活动已上线，无法进行该操作，如果活动有问题，你可以撤销活动！ 2：操作失败，活动存在特价价格小于等于0的商品
         */
        int code = 0;
        try {
            code = promotionInfoService.updataPromotionStatus(promotionId);
        } catch (Exception e) {
            logger.info("审核活动失败promotionId：{}", promotionId, e);
            code = 5;

        }
        return code;

    }

    /**
     * 修改更新活动优先级序号
     */
    @Override
    public Integer updatePromotionPriority(PromotionInfo promotion) {
        Integer result = 1;
        try {
            if (promotionInfoService.checkPromotionPriorityIsVaild(promotion)) {
                promotionInfoService.updatePromotion(promotion);
                result = 0;
            }
        } catch (Exception e) {
            logger.info("更新优先级活动序号失败promotionId：{}", promotion.getPromotionId(), e);
        }
        return result;
    }

    /**
     * 撤销活动 return ： 0 成功 1失败
     */
    @Override
    public Integer updatePromotionEndTime(PromotionInfo promotion) {
        Integer result = 1;
        promotion.setEndTime(new Date());
        try {
            promotionInfoService.updatePromotion(promotion);
            productPriceCacheService.notifyByPromotionInfoId(promotion.getPromotionId());
            result = 0;
        } catch (Exception e) {
            logger.info("撤销活动失败 promotionId：{}", promotion.getPromotionId(), e);
        }
        return result;

    }

    /**
     * 添加活动 return ： 0 成功 1失败
     */
    @Override
    public Long addPromotionNew(PromotionInfo p, String ruleData, String meetDataType) {
        Long id = 0l;
        try {
            // 添加活动
            id = promotionInfoService.addPromotionNewForRemote(p, ruleData, meetDataType);
        } catch (Exception e) {
            logger.error("操作失败", e);
            id = 0l;
        }
        return id;
    }

    /**
     * 查询互斥活动
     */
    @Override
    public Map<Long, String> selectExclusionPromotion(Long promotionId) {
        Map<Long, String> exclusionPromotionMap = null;
        try {
            PromotionInfo promotion = promotionInfoService.getPromotionById(promotionId);
            exclusionPromotionMap =
                    promotionInfoService.selectExclusionPromotion(promotion.getMutexPromotionId());
        } catch (Exception e) {
            logger.info("更新失败 promotionId：{}", promotionId, e);

        }
        return exclusionPromotionMap;
    }

    /**
     * 
     */
    @Override
    public boolean checkPromoitonTimeInCoupon(PromotionInfo promotion) {
        Date start = promotion.getStartTime();
        Date end = promotion.getEndTime();
        Long pid = promotion.getPromotionId();
        long startTime = start.getTime();
        long endTime = end.getTime();
        boolean isSuccess = false;
        try {
            // pe.createCriteria().andPromotionRuleIdEqualTo(promotion.getPromotionRuleId());
            List<PromotionRuleData> list = promotionRuleDataService.selectPromotionRuleList(pid);
            int i = 0;
            if (ListUtils.isNotEmpty(list)) {
                long couponStartTime = 0;
                long couponEndTime = 0;
                do {
                    PromotionRuleData ruleDate = list.get(i);
                    Long couponId = ruleDate.getEntityId();
                    Coupon coupon = couponService.queryCouponById(couponId);
                    if (coupon.getStarttime() != null && coupon.getEndtime() != null) {
                        couponStartTime = coupon.getStarttime().getTime();
                        couponEndTime = coupon.getEndtime().getTime();
                        isSuccess = startTime >= couponStartTime && startTime <= couponEndTime
                                && endTime >= couponStartTime && endTime <= couponEndTime;
                    } else {
                        isSuccess = true;
                    }
                    i++;
                } while (isSuccess && i < list.size());

            }

        } catch (NumberFormatException e) {
            logger.error("操作失败NumberFormatException", e);
        } catch (Exception e) {
            logger.error("操作失败", e);
        }
        return isSuccess;
    }

    /**
     * 修改促销活动 return ： 0 成功 1失败
     */
    @Override
    public Integer updatePromotion(PromotionInfo promotion) {
        Integer result = 1;
        try {
            if (promotion.getMutexPromotionId() != null) {
                StringBuffer mutexPromotionId = new StringBuffer();
                if ((mutexPromotionId.toString()).indexOf("all") > 0) {
                    promotion.setMutexPromotionId("all");
                } else {
                    promotion.setMutexPromotionId(mutexPromotionId.toString());
                }
            }
            if (promotion.getProductFilterType() == null) {
                promotion.setProductFilterSql(null);
            }
            PromotionInfo promotionDb =
                    promotionInfoDao.getPromotionInfoById(promotion.getPromotionId());
            if (!promotionDb.getStatus().equals(PromotionStatus.ISSUE.getValue())
                    && promotion.getProductFilterType() != null) {
                if (promotionDb.getProductFilterType() == 2
                        && promotion.getProductFilterType() != 2) {
                    promotionProductService.deletePromotionProduct(promotion.getPromotionId());
                }
            }
            promotionInfoService.updatePromotion(promotion);
            // 更新活动信息后，如果活动已审核应该更新活动缓存
            if (promotionDb.getStatus().equals(PromotionStatus.ISSUE.getValue())) {
                promotionProcess.updatePromotionInfoNew(promotionDb.getPromotionId());
            }
        } catch (Exception e) {
            logger.error("操作失败", e);
        }
        result = 0;
        return result;
    }

    @Override
    public Page queryPromotionProductList(Page page, PromotionInfo promotion) {
        Page page1 = new Page();
        try {

            PromotionProduct promotionProduct = new PromotionProduct();
            if (promotion.getPromotionName() != null && !promotion.getPromotionName().equals("")) {
                promotionProduct.setProductSkuCode(promotion.getPromotionName());
            }
            promotionProduct.setPromotionId(promotion.getPromotionId());
            page1 = promotionProductService.queryPromotionProductList(page, promotionProduct);
        } catch (Exception e) {
            logger.error("queryPromotionProductList方法异常：", e);

        }
        return page1;
    }

    /**
     * 添加活动商品 0 成功 1失败
     */
    @Override
    public Integer addPromotionProduct(Long promotionId, String productSkuIds) {
        PromotionProduct promotionProduct = new PromotionProduct();
        promotionProduct.setPromotionId(promotionId);
        promotionProduct.setProductSkuIds(productSkuIds);

        Integer result = 1;
        try {
            PromotionInfo info =
                    promotionInfoService.getPromotionById(promotionProduct.getPromotionId());
            if (info == null) {
                return 1;
            }
            // if (info.getStatus().equals(PromotionStatus.ISSUE.getValue())) {// 已审核无法修改
            // return 0;
            // }
            BigDecimal defaultPrice = info.getPromotionData();
            Double price = null;
            if (defaultPrice != null
                    && info.getPromotionType().equals(PromotionTypeEnums.SALE.getValue())) {
                price = defaultPrice.doubleValue();
            }
            promotionProductService.addPromotionProduct(promotionProduct, price, info);
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return result;
    }

    /**
     * 更新商品销售数据 返回 ：0 成功 1 最小购买数必须小于最大购买数 2 最小购买数必须小于最大购买数 3操作失败
     */
    @Override
    public Integer updatePromotionProductForXianGou(PromotionProduct p) {
        Integer result = 1;
        PrintWriter out = null;
        try {
            ResultMessage rmsg = promotionProductService.updatePromotionProductForXianGou(p);
            result = rmsg.getMark();

        } catch (Exception e) {
            e.printStackTrace();
            result = 1;
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
        return result;
    }

    @Override
    public Integer deletePromotionProducts(PromotionProduct promotionProduct) {
        try {
            promotionProductService.deletePromotionProduct(promotionProduct);
        } catch (Exception e) {
            logger.error("接口：删除活动失败", e);
            return 1;
        }
        return 0;
    }

    @Override
    public void synPromotionCom(long promotionid) throws ServiceException {
        try {
            promotionInfoService.synPromotionCom(promotionid);
        } catch (Exception e) {
            logger.error("接口：同步商品缓存信息失败", e);
        }
    }

    @Override
    public Integer updateIssuePromotionProduct(Long promotionProductId) throws ServiceException {
        try {
            return promotionProductService.updataPromotionProductStatus(promotionProductId);
        } catch (Exception e) {
            logger.error("接口：修改商品上线状态失败", e);
        }
        return 1;
    }

    /**
     * 
     * @param
     * @return 0成功 1失败
     */


    @Override
    public Integer createPromotion(List<PromotionProduct> list, PromotionInfo p) {
        try {
            return promotionInfoService.createPromotion(list, p);
        } catch (Exception e) {
            logger.error("接口：根据sku创建特价活动失败", e);
        }
        return 1;

    }
}
