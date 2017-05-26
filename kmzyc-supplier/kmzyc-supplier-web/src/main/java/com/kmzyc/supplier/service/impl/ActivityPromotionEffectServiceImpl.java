package com.kmzyc.supplier.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.OrderMain;
import com.kmzyc.supplier.dao.ActivityPromotionEffectDAO;
import com.kmzyc.supplier.service.ActivityPromotionEffectService;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.OrderItemVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("activityPromotionEffectService")
public class ActivityPromotionEffectServiceImpl implements ActivityPromotionEffectService {

	@Resource
	private ActivityPromotionEffectDAO activityPromotionEffectDAO;

	private Logger logger = LoggerFactory.getLogger(ActivityPromotionEffectServiceImpl.class);

	private static final String SUPPLIER_ENTRY_SKU_KEY = "supplierEntrySku_"; // SKU销量缓存Key

	private static final String SUPPLIER_ENTRY_ORDER_KEY = "supplierEntryOrder_"; // SKU订单缓存Key

    @Resource
    private JedisCluster jedisCluster;

	/**
     * 处理活动状态
     *
     * @param objCondition   查询条件
     * @param activityStatus 状态
     */
    private void handleActivityStatusCondition(Map<String, Object> objCondition, Integer activityStatus) {
        switch (activityStatus) {
            case 5:
                objCondition.put("activityIn", true);
                break;
            case 6:
                objCondition.put("activityEnd", true);
                break;
            case 8:
            default:
                objCondition.put("activityStatus", activityStatus);
                break;
        }
    }

    
	@Override
	public Pagination queryPromotionEffectList(Long supplierId, ActivityInfo activityParam, Pagination page)
			throws ServiceException {
		Map<String, Object> condition = new HashMap<String, Object>();
		// 商家ID
		condition.put("supplierId", supplierId.toString());
		// 活动ID
		if (null != activityParam.getActivityId()) {
			condition.put("activityId", activityParam.getActivityId());
		}
		// 活动名称
		if (StringUtils.isNotBlank(activityParam.getActivityName())) {
			condition.put("activityName", activityParam.getActivityName());
		}
		// 活动状态
		if (activityParam.getActivityStatus() != null) {
            handleActivityStatusCondition(condition, activityParam.getActivityStatus());
        }
		// 活动类型
		if (null != activityParam.getActivityType()) {
			condition.put("activityType", activityParam.getActivityType());
		}
		// 报名开始时间
		if (null != activityParam.getEntryStartTime()) {
			condition.put("entryStartTime", activityParam.getEntryStartTime());
		}
		
		// 报名结束时间
		if (null != activityParam.getEntryEndTime()) {
			condition.put("entryEndTime", activityParam.getEntryEndTime());
		}
		// 活动开始时间
		if (null != activityParam.getActivityStartTime()) {
			condition.put("activityStartTime", activityParam.getActivityStartTime());
		}
		// 活动结束时间
		if (null != activityParam.getActivityEndTime()) {
			condition.put("activityEndTime", activityParam.getActivityEndTime());
		}
		page.setObjCondition(condition);
		try {
			this.activityPromotionEffectDAO.selectPromotionEffect(page);
		} catch (Exception e) {
			logger.error("查询活动推广效果列表出错：查询参数{}，错误信息：{}", new Object[] { JSONObject.toJSONString(activityParam), e });
			throw new ServiceException(e);
		}
		return page;
	}

	@Override
	public Pagination querySPAndTxtActivitySalesList(Long supplierId, Pagination page, ActivitySku activitySkuParam)
			throws ServiceException {
		Map<String, Object> condition = new HashMap<String, Object>();
		// 商家ID
		condition.put("supplierId", supplierId.toString());
		// 活动ID
		if (null != activitySkuParam.getActivityId()) {
			condition.put("activityId", activitySkuParam.getActivityId());
		}
		// SKU编码
		if (activitySkuParam.getProductSkuCode() != null) {
			condition.put("productSkuCode", activitySkuParam.getProductSkuCode());
		}
		// 商品名称
		if (StringUtils.isNotBlank(activitySkuParam.getProductName())) {
			condition.put("productName", activitySkuParam.getProductName());
		}
		page.setObjCondition(condition);

		try {
            int count = this.activityPromotionEffectDAO.countSPAndTxtProductSales(page);
            List<ActivitySku> list = this.activityPromotionEffectDAO.selectSPAndTxtProductSales(page);
			if (CollectionUtils.isNotEmpty(list)) {
				for (ActivitySku activitySku : list) {
					// 从缓存中取最新的销量
					String saleQuantity = jedisCluster.get(SUPPLIER_ENTRY_SKU_KEY + activitySku.getSupplierEntryId() + "_"
							+ activitySku.getProductSkuId());
					activitySku.setSaleQuantity(saleQuantity == null ? Integer.valueOf(0) : Integer.valueOf(saleQuantity));
				}
			}
            page.setTotalRecords(count);
            page.setRecordList(list);
            return page;
		} catch (Exception e) {
			logger.error("查询促销/图文活动销量出错：查询参数{}，错误信息：{}",
					new Object[] { supplierId, JSONObject.toJSONString(activitySkuParam), e });
			throw new ServiceException(e);
		}
	}

	@Override
	public Pagination queryChannelActivitySalesList(Long supplierId, Pagination page, ActivitySku activitySkuParam)
			throws ServiceException {
		Map<String, Object> condition = new HashMap<String, Object>();
		// 商家ID
		condition.put("supplierId", supplierId.toString());
		// 活动ID
		if (null != activitySkuParam.getActivityId()) {
			condition.put("activityId", activitySkuParam.getActivityId());
		}
		// 商品名称
		if (StringUtils.isNotBlank(activitySkuParam.getProductName())) {
			condition.put("productName", activitySkuParam.getProductName());
		}
		// SKU编码
		if (activitySkuParam.getProductSkuCode() != null) {
			condition.put("productSkuCode", activitySkuParam.getProductSkuCode());
		}
		page.setObjCondition(condition);
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			page.setTotalRecords(activityPromotionEffectDAO.countChannelProductSales(page));
            List<ActivitySku> list = activityPromotionEffectDAO.selectChannelProductSales(page);
			if (CollectionUtils.isNotEmpty(list)) {
				for (ActivitySku activitySku : list) {
					param.put("activityId", activitySkuParam.getActivityId());
					param.put("supplierId", supplierId);
					param.put("productSkuId", activitySku.getProductSkuId());
					// 查询首次预售数量
					Integer firstPreSaleQuantity = this.activityPromotionEffectDAO.countFirstPreSaleQuantity(param);
					if (null == firstPreSaleQuantity) {
						firstPreSaleQuantity = 0;
					}
					// 查询追加数量
					Integer appendPreSaleQuantity = this.activityPromotionEffectDAO.countAppendPreSaleQuantity(param);
					if (null == appendPreSaleQuantity) {
						appendPreSaleQuantity = 0;
					}
					activitySku.setAppendPreSaleQuantity(appendPreSaleQuantity);
					// 总的预售数量(包含追加数量)
					int totalPreSaleQuantity = firstPreSaleQuantity + appendPreSaleQuantity;
					activitySku.setPreSaleQuantity(totalPreSaleQuantity);

					// 从缓存中取最新的销量
					String saleQuantity = jedisCluster.get(SUPPLIER_ENTRY_SKU_KEY + activitySku.getSupplierEntryId() + "_"
							+ activitySku.getProductSkuId());
					activitySku.setSaleQuantity(saleQuantity == null ? Integer.valueOf(0) : Integer.valueOf(saleQuantity));
				}
				page.setRecordList(list);
			}
            return page;
		} catch (Exception e) {
			logger.error("查询渠道活动销量出错：查询参数{}，错误信息：{}",
					new Object[] { supplierId, JSONObject.toJSONString(activitySkuParam), e });
			throw new ServiceException(e);
		}
	}

	@Override
	public Pagination queryAppendProductDetail(Long supplierId, Pagination page, ActivitySku activitySkuParam)
			throws ServiceException {
		Map<String, Object> condition = new HashMap<String, Object>();
		// 商家ID
		condition.put("supplierId", supplierId.toString());
		// 活动ID
		if (null != activitySkuParam.getActivityId()) {
			condition.put("activityId", activitySkuParam.getActivityId());
		}
		//skuId
		if (null != activitySkuParam.getProductSkuId()) {
			condition.put("skuId", activitySkuParam.getProductSkuId());
		}
		// 商品名称
		if (StringUtils.isNotBlank(activitySkuParam.getProductName())) {
			condition.put("productName", activitySkuParam.getProductName());
		}
		// SKU编码
		if (activitySkuParam.getProductSkuCode() != null) {
			condition.put("productSkuCode", activitySkuParam.getProductSkuCode());
		}
		page.setObjCondition(condition);

		try {
            int count = this.activityPromotionEffectDAO.countAppendProduct(page);
            List<ActivitySku> list = this.activityPromotionEffectDAO.selectAppendProductList(page);
            page.setTotalRecords(count);
            page.setRecordList(list);
            return page;
		} catch (Exception e) {
			logger.error("查询追加推广明细出错：查询参数{}，错误信息：{}",
					new Object[] { supplierId, JSONObject.toJSONString(activitySkuParam), e });
			throw new ServiceException(e);
		}
	}

	@Override
	public Pagination querySkuSalesDetail(String supplierEntryId, String skuId, Pagination page, Map<String, Object> map)
            throws ServiceException {
		// 商家ID
		// 获取缓存订单号
		List<String> orderCodeList = jedisCluster.lrange(SUPPLIER_ENTRY_ORDER_KEY + supplierEntryId + "_" + skuId, 0, -1);
		int count = 0;
		List<OrderMain> productSalesDetail = new ArrayList<OrderMain>();
		if (CollectionUtils.isNotEmpty(orderCodeList)) {
			map.put("orderCodeArr", orderCodeList);
			page.setObjCondition(map);
			try {
				count = activityPromotionEffectDAO.countProductSalesDetail(page);
				productSalesDetail = activityPromotionEffectDAO.selectProductSalesDetail(page);
				if (!CollectionUtils.isEmpty(productSalesDetail)) {
					for (com.kmzyc.supplier.model.OrderMain order : productSalesDetail) {
						List<OrderItemVo> orderItemList = order.getOrderItemList();
						List<OrderItemVo> orderItemTemp= new ArrayList<OrderItemVo>();
						if (!CollectionUtils.isEmpty(orderItemList)) {
							for (int i = 0; i < orderItemList.size(); i++) {
								OrderItemVo activitySku = orderItemList.get(i);
								if (activitySku.getProductSkuId().longValue() == Long.parseLong(skuId)) {
									orderItemTemp.add(activitySku);
								}
							}
						}
						order.setOrderItemList(orderItemTemp);
					}
				}
			} catch (Exception e) {
				logger.error("查询活动商家销售明细,错误信息{}:", e);
				throw new ServiceException(e);
			}
		}
		page.setTotalRecords(count);
		page.setRecordList(productSalesDetail);
		return page;
	}

}
