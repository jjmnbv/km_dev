package com.pltfm.activity.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.supplier.model.OrderMain;
import com.pltfm.activity.dao.ActivityPromotionEffectDAO;
import com.pltfm.activity.service.ActivityPromotionEffectService;
import com.pltfm.app.enums.ActivityStatus;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivityInfoExample;
import com.pltfm.app.vobject.ActivityInfoExample.Criteria;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySupplierEntry;
import com.pltfm.app.vobject.OrderItemVo;
import com.pltfm.app.vobject.SkuSalesDetailVO;

import redis.clients.jedis.JedisCluster;

@Service
public class ActivityPromotionEffectServiceImpl implements ActivityPromotionEffectService {

    @Resource
    private ActivityPromotionEffectDAO promotionEffectDao;

    @Resource
    private JedisCluster jedisCluster;

    private Logger logger = LoggerFactory.getLogger(ActivityPromotionEffectServiceImpl.class);

    private static final String SUPPLIER_ENTRY_SKU_KEY = "supplierEntrySku_"; // SKU销量缓存Key

    private static final String SUPPLIER_ENTRY_ORDER_KEY = "supplierEntryOrder_"; // SKU订单缓存Key

    private static final String SUPPLIER_ENTRY_TOTAL_SALES_KEY = "supplierEntryTotalSales_";// 商家总销量Key

    @Override
    public void queryPromotionEffectListForPage(ActivityInfo activityInfo, Page page)
            throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ActivityInfoExample aie = new ActivityInfoExample();
        Criteria criteria = aie.createCriteria();
        // ID查询
        if (null != activityInfo.getActivityId()) {
            aie.setActivityId(activityInfo.getActivityId());
        }
        // 活动名称查询
        if (StringUtils.isNotBlank(activityInfo.getActivityName())) {
            criteria.andActivityNameLike("%" + activityInfo.getActivityName().trim() + "%");
        }
        // 活动类型查询
        if (null != activityInfo.getActivityType()) {
            criteria.andActivityTypeEqualTo(activityInfo.getActivityType());
        }
        // 活动状态查询
        if (null != activityInfo.getActivityStatus()
                && ActivityStatus.DRAFT.getStatus().intValue() != activityInfo.getActivityStatus().intValue()
                && ActivityStatus.PRE_AUDIT.getStatus().intValue() != activityInfo.getActivityStatus().intValue()
                && ActivityStatus.ACTIVITY_CANCELL.getStatus().intValue() != activityInfo.getActivityStatus().intValue()
                && ActivityStatus.ACTIVITY_STOP.getStatus().intValue() != activityInfo.getActivityStatus().intValue()) {

            Timestamp newTime = new Timestamp(System.currentTimeMillis());
            // 未到报名时间
            if (ActivityStatus.NOT_ENTRY_TIME.getStatus().intValue() == activityInfo.getActivityStatus().intValue()) {
                criteria.andEntryStartTimeGreaterThan(newTime);
                // 草稿状态、已撤销状态需要屏蔽
                criteria.andActivityStatusNotEqualTo(ActivityStatus.DRAFT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.PRE_AUDIT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_CANCELL.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_STOP.getStatus());
            } else if (ActivityStatus.ENTRY_IN.getStatus().intValue() == activityInfo.getActivityStatus().intValue()) {// 报名中
                criteria.andEntryStartTimeLessThanOrEqualTo(newTime);
                criteria.andEntryEndTimeGreaterThanOrEqualTo(newTime);
                criteria.andActivityStatusNotEqualTo(ActivityStatus.DRAFT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.PRE_AUDIT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_CANCELL.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_STOP.getStatus());
            } else if (ActivityStatus.ACTIVITY_NOT_START.getStatus().intValue() == activityInfo.getActivityStatus()
                    .intValue()) {// 活动未开始
                criteria.andEntryEndTimeLessThan(newTime);
                criteria.andActivityStatusNotEqualTo(ActivityStatus.DRAFT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.PRE_AUDIT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_CANCELL.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_STOP.getStatus());
            } else if (ActivityStatus.ACTIVITY_IN.getStatus().intValue() == activityInfo.getActivityStatus()
                    .intValue()) {// 活动进行中
                criteria.andActivityStartTimeLessThanOrEqualTo(newTime);
                criteria.andActivityEndTimeGreaterThanOrEqualTo(newTime);
                criteria.andActivityStatusNotEqualTo(ActivityStatus.DRAFT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.PRE_AUDIT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_CANCELL.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_STOP.getStatus());
            } else if (ActivityStatus.ACTIVITY_END.getStatus().intValue() == activityInfo.getActivityStatus()
                    .intValue()) {// 活动已结束
                criteria.andActivityEndTimeLessThan(newTime);
                criteria.andActivityStatusNotEqualTo(ActivityStatus.DRAFT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.PRE_AUDIT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_CANCELL.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_STOP.getStatus());
            }
        } else if (null != activityInfo.getActivityStatus()) {
            criteria.andActivityStatusEqualTo(activityInfo.getActivityStatus());
            // 报名开始结束时间
            if (activityInfo.getEntryStartTime() != null && activityInfo.getEntryEndTime() != null) {
                criteria.andEntryStartTimeGreaterThanOrEqualTo(activityInfo.getEntryStartTime());
                criteria.andEntryEndTimeLessThan(activityInfo.getEntryEndTime());
            }
            // 活动开始结束时间
            if (activityInfo.getActivityStartTime() != null && activityInfo.getActivityEndTime() != null) {
                criteria.andActivityStartTimeGreaterThanOrEqualTo(activityInfo.getActivityStartTime());
                criteria.andActivityEndTimeLessThan(activityInfo.getActivityEndTime());
            }
        } else {
            // 报名开始结束时间
            if (activityInfo.getEntryStartTime() != null && activityInfo.getEntryEndTime() != null) {
                criteria.andEntryStartTimeGreaterThanOrEqualTo(activityInfo.getEntryStartTime());
                criteria.andEntryEndTimeLessThan(activityInfo.getEntryEndTime());
            }
            // 活动开始结束时间
            if (activityInfo.getActivityStartTime() != null && activityInfo.getActivityEndTime() != null) {
                criteria.andActivityStartTimeGreaterThanOrEqualTo(activityInfo.getActivityStartTime());
                criteria.andActivityEndTimeLessThan(activityInfo.getActivityEndTime());
            }
        }
        aie.setOrderByClause("CREATE_TIME DESC");

        try {
            page.setRecordCount(promotionEffectDao.countPromotionEffect(aie));
            page.setDataList(promotionEffectDao.selectPromotionEffect(aie, skip, max));
        } catch (Exception e) {
            logger.error("查询活动推广效果,错误信息{}:", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void queryActivitySuppliersSales(Page page, ActivitySupplierEntry activitySupplierEntry)
            throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        Map<String, Object> map = new HashMap<String, Object>();
        // 活动Id
        map.put("activityId", activitySupplierEntry.getActivityId());
        // 公司名称
        if (StringUtils.isNotEmpty(activitySupplierEntry.getCompanyShowName())) {
            map.put("companyShowName", activitySupplierEntry.getCompanyShowName());
        }
        // 店铺名称
        if (StringUtils.isNotEmpty(activitySupplierEntry.getShopName())) {
            map.put("shopName", activitySupplierEntry.getShopName());
        }
        // 登陆账号
        if (StringUtils.isNotEmpty(activitySupplierEntry.getLoginAccount())) {
            map.put("loginAccount", activitySupplierEntry.getLoginAccount());
        }

        try {
            int count = promotionEffectDao.countByEntryExample(map);
            List<ActivitySupplierEntry> list = promotionEffectDao.selectByEntryExamples(map, skip, max);
            if (!CollectionUtils.isEmpty(list)) {
                for (ActivitySupplierEntry supplierEntry : list) {
                    // 首次缴费
                    Double firstPayAmount = promotionEffectDao.getActivityFirstPay(supplierEntry.getActivityId(),
                            supplierEntry.getSupplierId());
                    // 追加缴费
                    Double appendPayAmount = promotionEffectDao.getActivityAppendPay(supplierEntry.getActivityId(),
                            supplierEntry.getSupplierId());
                    Double totalActivityFee = (null == firstPayAmount ? 0 : firstPayAmount)
                            + (null == appendPayAmount ? 0 : appendPayAmount);
                    DecimalFormat df = new DecimalFormat("0.00");
                    BigDecimal bd = new BigDecimal(df.format(totalActivityFee));
                    supplierEntry.setTotalPayAmount(bd);
                    // 获取商家总销量
                    String totalSalesQuantity = jedisCluster.get(SUPPLIER_ENTRY_TOTAL_SALES_KEY + supplierEntry.getSupplierEntryId());
                    supplierEntry.setTotalSalesQuantity(
                            null == totalSalesQuantity ? Integer.valueOf(0) : Integer.valueOf(totalSalesQuantity));
                }
            }
            page.setDataList(list);
            page.setRecordCount(count);
        } catch (Exception e) {
            logger.error("查询活动商家销量,错误信息{}:", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void getSPAndTxtSupplierProductSales(Page page, ActivitySku activitySkuParam)
            throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("activityId", activitySkuParam.getActivityId());
        map.put("supplierEntryId", activitySkuParam.getSupplierEntryId());
        if (StringUtils.isNotEmpty(activitySkuParam.getProductName())) {
            map.put("productName", activitySkuParam.getProductName());
        }
        if (StringUtils.isNotEmpty(activitySkuParam.getBrandName())) {
            map.put("brandName", activitySkuParam.getBrandName());
        }
        if (activitySkuParam.getProductSkuCode() != null) {
            map.put("productSkuCode", activitySkuParam.getProductSkuCode());
        }
        try {
            int count = promotionEffectDao.countSPAndTxtSupplierProductSales(map);
            List<ActivitySku> list = promotionEffectDao.selectSPAndTxtSupplierProductSales(map, skip, max);
            if (CollectionUtils.isNotEmpty(list)) {
                for (ActivitySku activitySku : list) {
                    // 从缓存中取最新的销量
                    String saleQuantity = jedisCluster.get(SUPPLIER_ENTRY_SKU_KEY + activitySkuParam.getSupplierEntryId() + "_"
                            + activitySku.getProductSkuId());
                    activitySku.setSaleQuantity(saleQuantity == null ? Integer.valueOf(0) : Integer.valueOf(saleQuantity));
                }
            }
            page.setRecordCount(count);
            page.setDataList(list);
        } catch (SQLException e) {
            logger.error("查询促销/图文活动商家已报商品销量,错误信息{}:", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void getChannelSupplierProductSales(Page page, ActivitySku activitySkuParam)
            throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("activityId", activitySkuParam.getActivityId());
        map.put("supplierEntryId", activitySkuParam.getSupplierEntryId());
        // map.put("productSkuId", activitySkuParam.getProductSkuId());
        if (StringUtils.isNotEmpty(activitySkuParam.getProductName())) {
            map.put("productName", activitySkuParam.getProductName());
        }
        if (activitySkuParam.getProductSkuCode() != null) {
            map.put("productSkuCode", activitySkuParam.getProductSkuCode());
        }
        if (StringUtils.isNotEmpty(activitySkuParam.getBrandName())) {
            map.put("brandName", activitySkuParam.getBrandName());
        }
        try {
            int count = promotionEffectDao.countChannelSupplierProductSales(map);
            List<ActivitySku> list = promotionEffectDao.selectChannelSupplierProductSales(map, skip, max);
            Map<String, Object> param = new HashMap<String, Object>();
            if (CollectionUtils.isNotEmpty(list)) {
                for (ActivitySku activitySku : list) {
                    param.put("activityId", activitySkuParam.getActivityId());
                    param.put("supplierEntryId", activitySkuParam.getSupplierEntryId());
                    param.put("productSkuId", activitySku.getProductSkuId());
                    Integer firstPreSaleQuantity = promotionEffectDao.countSupplierFristPreSaleQuantity(param);
                    Integer appendPreSaleQuantity = promotionEffectDao.countSupplierAppendPreSaleQuantity(param);
                    activitySku.setAppendPreSaleQuantity(appendPreSaleQuantity);
                    int totalPreSaleQuantity = (null == firstPreSaleQuantity ? 0 : firstPreSaleQuantity)
                            + (null == appendPreSaleQuantity ? 0 : appendPreSaleQuantity);
                    activitySku.setPreSaleQuantity(totalPreSaleQuantity);
                    // 从缓存中取最新的销量
                    String saleQuantity = jedisCluster.get(SUPPLIER_ENTRY_SKU_KEY + activitySkuParam.getSupplierEntryId() + "_"
                            + activitySku.getProductSkuId());
                    activitySku.setSaleQuantity(saleQuantity == null ? Integer.valueOf(0) : Integer.valueOf(saleQuantity));
                }
            }
            page.setRecordCount(count);
            page.setDataList(list);
        } catch (Exception e) {
            logger.error("查询渠道活动商家已报商品销量,错误信息{}:", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void querySupplierAppendProductList(Page page, ActivitySku activitySkuParam)
            throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("activityId", activitySkuParam.getActivityId());
        map.put("supplierEntryId", activitySkuParam.getSupplierEntryId());
        map.put("skuId", activitySkuParam.getProductSkuId());
        if (StringUtils.isNotEmpty(activitySkuParam.getProductName())) {
            map.put("productName", activitySkuParam.getProductName());
        }
        if (activitySkuParam.getProductSkuCode() != null) {
            map.put("productSkuCode", activitySkuParam.getProductSkuCode());
        }
        try {
            int count = promotionEffectDao.countSupplierAppendProduct(map);
            List<ActivitySku> list = promotionEffectDao.selectSupplierAppendProductList(map, skip, max);
            page.setRecordCount(count);
            page.setDataList(list);
        } catch (Exception e) {
            logger.error("查询活动商家追加商品,错误信息{}:", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void querySkuSalesDetail(String supplierEntryId, String skuId, Page page, Map<String, Object> map)
            throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        // 获取缓存订单号
        List<String> orderCodeList = jedisCluster.lrange(SUPPLIER_ENTRY_ORDER_KEY + supplierEntryId + "_" + skuId, 0, -1);
        List<OrderMain> productSalesDetail = null;
        List<SkuSalesDetailVO> orderSalesDetailList = new ArrayList<SkuSalesDetailVO>();
        if (CollectionUtils.isNotEmpty(orderCodeList)) {
            map.put("orderCodeArr", orderCodeList);
            try {
                productSalesDetail = promotionEffectDao.selectProductSalesDetail(map, skip, max);
                // 将不等于前置SKU订单的去掉
                if (CollectionUtils.isNotEmpty(productSalesDetail)) {
                    for (OrderMain order : productSalesDetail) {
                        List<OrderItemVo> orderItemList = order.getOrderItemList();
                        if (CollectionUtils.isNotEmpty(orderItemList)) {
                            for (int i = 0; i < orderItemList.size(); i++) {
                                OrderItemVo activitySku = orderItemList.get(i);
                                if (activitySku.getProductSkuId().longValue() == Long.parseLong(skuId)) {
                                    SkuSalesDetailVO skuOrderDetail = new SkuSalesDetailVO();
                                    skuOrderDetail.setOrderCode(order.getOrderCode());
                                    skuOrderDetail.setOrderCreateTime(order.getCreateDate());
                                    skuOrderDetail.setOrderStatus(order.getOrderStatusStr());
                                    skuOrderDetail.setProductTitle(orderItemList.get(i).getCommodityTitle());
                                    skuOrderDetail.setBrandName(activitySku.getBrandName());
                                    skuOrderDetail.setSkuCode(orderItemList.get(i).getCommoditySku());
                                    skuOrderDetail.setPrice(orderItemList.get(i).getCommodityUnitPrice());
                                    skuOrderDetail.setQuantity(orderItemList.get(i).getCommodityNumber());

                                    orderSalesDetailList.add(skuOrderDetail);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("查询活动商家销售明细,错误信息{}:", e);
                throw new ServiceException(e);
            }
        }
        page.setRecordCount(orderSalesDetailList.size());
        page.setDataList(orderSalesDetailList);
    }

}
