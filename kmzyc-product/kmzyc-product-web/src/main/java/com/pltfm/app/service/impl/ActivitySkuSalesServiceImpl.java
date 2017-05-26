package com.pltfm.app.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.service.ActivitySkuSalesService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("activitySkuSalesService")
public class ActivitySkuSalesServiceImpl implements ActivitySkuSalesService {

	@Resource
	private JedisCluster jedisCluster;

	private static final String SUPPLIER_ENTRY_SKU_KEY = "supplierEntrySku_";// SKU缓存Key

	private static final String SUPPLIER_ENTRY_ORDER_KEY = "supplierEntryOrder_";// SKU订单缓存Key

	private Logger logger = LoggerFactory.getLogger(ActivitySkuSalesServiceImpl.class);

	@Override
	public Map<Long, Integer> getActivitySkuSales(Long supplierEntryId, List<Long> skuIdList) throws ServiceException {
		logger.debug("开始获取活动SKU销量,参数:{0},{1}", supplierEntryId, JSONObject.toJSONString(skuIdList));
		Map<Long, Integer> resultMap = new HashMap<Long, Integer>();

		try {
			if (!CollectionUtils.isEmpty(skuIdList)) {
				for (Long skuId : skuIdList) {
					// 获取缓存SKU已售数量
					String saleQuantity = jedisCluster.get(SUPPLIER_ENTRY_SKU_KEY + supplierEntryId + "_" + skuId);
					resultMap.put(skuId, Integer.valueOf(saleQuantity == null ? "0" : saleQuantity));
				}
			}
		} catch (Exception e) {
			logger.error("获取活动SKU销量异常", e);
			throw new ServiceException(e);
		}
		logger.debug("获取活动SKU销量返回结果", JSONObject.toJSONString(resultMap));
		return resultMap;
	}

}