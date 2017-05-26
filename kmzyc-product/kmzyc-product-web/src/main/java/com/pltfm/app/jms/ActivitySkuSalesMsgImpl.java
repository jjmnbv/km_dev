package com.pltfm.app.jms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.activity.dao.ActivityPromotionEffectDAO;
import com.pltfm.app.enums.ActivityChannl;
import com.pltfm.app.enums.ActivityType;
import com.pltfm.app.util.RedisLock;
import com.pltfm.app.vobject.ActivityInfo;

import redis.clients.jedis.JedisCluster;

@Service("activitySkuSalesMsg")
public class ActivitySkuSalesMsgImpl implements ActivitySkuSalesMsgCustomer, MessageListener {
    
    private Logger logger = LoggerFactory.getLogger(ActivitySkuSalesMsgImpl.class);

	@Resource
	private JedisCluster jedisCluster;

	@Resource
	private ActivityPromotionEffectDAO promotionEffectDAO;

	@Resource
	private RedisLock redisLock;

	private static final String SUPPLIERENTRY_SKU_KEY = "supplierEntrySku_"; // SKU销量缓存Key

	private static final String SUPPLIERENTRY_ORDER_KEY = "supplierEntryOrder_"; // SKU订单缓存Key

	private static final String SUPPLIERENTRY_TOTALSALES_KEY = "supplierEntryTotalSales_";// 商家总销量Key

	private static final String ACTIVITY_SKU_QUANTITY_KEY = "activity_sku_quantity_"; // 活动SKU预售数量

	private static final Map<String, String> ACTIVITY_CHANNEL_MAP = new HashMap<String, String>();

	private static final String PREFIX_LOCK = "/skuSales_locks";

	static {
		ACTIVITY_CHANNEL_MAP.put("yunshang", ActivityChannl.KMYD.getChannl());// 康美云店
        ACTIVITY_CHANNEL_MAP.put("era", ActivityChannl.KMB2B.getChannl());// 康美中药城
		ACTIVITY_CHANNEL_MAP.put("51fanli", ActivityChannl.FLW.getChannl());// 返利网
	}

	/**
	 * 去重
	 * 
	 * @param originalList
	 * @return
	 */
	private List<String> duplicateRemoval(List<String> originalList) {
		List<String> tempList = new ArrayList<String>();
        originalList.stream().filter(code -> !tempList.contains(code)).forEach(tempList::add);
		return tempList;
	}

	@Override
	public void updateActivitySkuSales(String orderInfo) {
		logger.info("------开始准备更新活动SKU销量------,参数:" + JSONObject.toJSONString(orderInfo));
		List<ActivityInfo> activityList = null;
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject order = JSONObject.parseObject(orderInfo);
		String salesChannel = order.getString("userSource");// 销售渠道
		String orderCode = order.getString("orderCode");// 订单号
		map.put("payTime", order.getString("payTime"));// 支付时间
		JSONArray orderList = order.getJSONArray("oList");// 订单SKU商品集合
		List<String> orderCodeList = new ArrayList<String>();// 订单号集合
		orderCodeList.add(orderCode);
		try {
			if (orderList != null && orderList.size() > 0) {
				for (int i = 0; i < orderList.size(); i++) {
					Long skuId = orderList.getJSONObject(i).getLong("skuId");
					Long productQuantity = orderList.getJSONObject(i).getInteger("commodityNumber").longValue();// SKU商品数量
					map.put("skuId", skuId);
					// 查询skuId是否是活动进行中
					logger.info("--------开始获取正在活动进行中的列表--------,参数:" + JSONObject.toJSONString(map));
					activityList = this.promotionEffectDAO.selectActivityProgressList(map);
					logger.info("--------获取正在活动进行中的列表返回成功--------,结果：" + JSONObject.toJSONString(activityList));
					if (CollectionUtils.isNotEmpty(activityList)) {
						for (ActivityInfo activity : activityList) {
							// 去掉重复的订单号
							List<String> tempOrderList = this.duplicateRemoval(orderCodeList);
							// 如果活动类型是促销/图文,直接更新SKU销量;如果是渠道推广且渠道属于康美中药城、康美云店、返利网才更新SKU销量
							if (ActivityType.PROMOTION_TYPE.getType().intValue() == activity.getActivityType()
									.intValue()
									|| ActivityType.GRAPHIC_TYPE.getType().intValue() == activity.getActivityType()
											.intValue()) {
								/** 更新促销,图文活动SKU销量 */
								updatePromotionAndTelTextSkuCache(jedisCluster, activity, tempOrderList, skuId,
										productQuantity);
							} else {
								if (activity.getActivityChannel().contains(ACTIVITY_CHANNEL_MAP.get(salesChannel))) {
									/** 更新渠道活动SKU销量 */
									updateChannelSkuCache(jedisCluster, activity, tempOrderList, skuId, productQuantity);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("更新活动推广Sku销量,错误信息{}:", e);
			throw new ServiceException(e);
		}
		logger.debug("------更新活动SKU销量结束------");
	}

	/**
	 * 更新促销/图文活动SKU销量
	 * 
	 * @param jedis
	 * @param activity
	 * @param tempOrderList 订单号
	 * @param skuId
	 * @param productQuantity 商品数量
	 * @throws Exception
	 */
	private void updatePromotionAndTelTextSkuCache(JedisCluster jedis, ActivityInfo activity,
			List<String> tempOrderList, Long skuId, Long productQuantity) {
		logger.info("--------更新促销/图文活动SKU销量开始--------");

		// 更新缓存SKU已售数量
		String cacheSkuKey = SUPPLIERENTRY_SKU_KEY + activity.getSupplierEntryId() + "_" + skuId;
		logger.info("--------开始更新促销/图文活动SKU销量--------,key:" + cacheSkuKey + ";数量：" + productQuantity);
		jedis.incrBy(cacheSkuKey, productQuantity);
		logger.info("--------更新促销/图文活动SKU销量结束--------");

		// 更新缓存SKU订单号
		String cacheOrderKey = SUPPLIERENTRY_ORDER_KEY + activity.getSupplierEntryId() + "_" + skuId;
		logger.info("--------开始更新促销/图文活动SKU订单号--------,key:" + cacheOrderKey + ";订单号："
				+ JSONObject.toJSONString(tempOrderList));
		jedis.lpush(cacheOrderKey, tempOrderList.toArray(new String[tempOrderList.size()]));
		logger.info("--------更新促销/图文活动SKU订单号结束--------");

		// 更新缓存商家总销量
		String cacheTotalSaleKey = SUPPLIERENTRY_TOTALSALES_KEY + activity.getSupplierEntryId();
		logger.info("--------开始更新促销/图文活动商家总销量--------,key:" + cacheTotalSaleKey + ";数量：" + productQuantity);
		jedis.incrBy(cacheTotalSaleKey, productQuantity);
		logger.info("--------更新促销/图文活动商家总销量结束--------");

		logger.info("--------更新促销/图文活动SKU销量结束--------");
	}

	/**
	 * 更新缓存Sku销量,订单号
	 * 
	 * @param jedis
	 * @param activity
	 * @param tempOrderList
	 * @param skuId
	 * @param productQuantity
	 * @throws Exception
	 */
	private void updateChannelSkuCache(JedisCluster jedis, ActivityInfo activity, List<String> tempOrderList,
			Long skuId, Long productQuantity) throws Exception {
		logger.info("--------更新渠道活动SKU销量开始--------");
		// 缓存已销数量key
		String skuKey = SUPPLIERENTRY_SKU_KEY + activity.getSupplierEntryId() + "_" + skuId;
		// 缓存订单号key
		String orderKey = SUPPLIERENTRY_ORDER_KEY + activity.getSupplierEntryId() + "_" + skuId;
		// 缓存商家总销量key
		String totalSalesKey = SUPPLIERENTRY_TOTALSALES_KEY + activity.getSupplierEntryId();
		// 总预售销量key
		String totalPreSaleQuantityKey = ACTIVITY_SKU_QUANTITY_KEY + activity.getSupplierEntryId() + "_" + skuId;
		// 总预售销量
		logger.info("--------开始获取渠道活动缓存SKU总预售销量--------,key:" + totalPreSaleQuantityKey);
		String totalPreSaleQuantity = jedis.get(totalPreSaleQuantityKey);
		int cachePreSales = totalPreSaleQuantity == null ? 0 : Integer.parseInt(totalPreSaleQuantity);
		logger.info("--------获取渠道活动缓存SKU总预售销量结束--------,结果:" + totalPreSaleQuantity);

		// SKU已销数量
		logger.info("--------开始获取渠道活动缓存SKU已销数量--------,key:" + skuKey);
		String saleQuantity = jedis.get(skuKey);
		int cacheSales = saleQuantity == null ? 0 : Integer.parseInt(saleQuantity);
		logger.info("--------获取渠道活动缓存SKU已销数量结束--------,结果:" + saleQuantity);

		String nodeKey = PREFIX_LOCK + "/" + activity.getSupplierEntryId() + "/" + skuId;

		// 将要销售销量
		int preSales = cacheSales + productQuantity.intValue();
		logger.info("--------获取将要销售销量--------,结果:" + preSales);
		// 获取锁
		logger.info("--------线程" + Thread.currentThread().getName() + "开始去获取锁" + nodeKey + "--------");
		try {
			if (!redisLock.tryLock(nodeKey)) {
				logger.error("线程[{}],没有获取到锁{}.", new Object[] { Thread.currentThread().getName(), nodeKey });
				logger.info("--------线程" + Thread.currentThread().getName() + "没有获取到锁" + nodeKey + "--------");
				return;
			} else {
				logger.info("--------线程" + Thread.currentThread().getName() + "已经获取到锁" + nodeKey + "--------");
				if (preSales <= cachePreSales) { // 如果将要销售销量小于等于总预售数量则更新缓存
					// 更新缓存SKU已售数量
					logger.info("--------开始更新渠道活动缓存SKU销量--------,key:" + skuKey + ";商品数量:" + productQuantity);
					jedis.incrBy(skuKey, productQuantity);
					logger.info("--------更新渠道活动缓存SKU销量结束--------");
					// 更新缓存商家总销量
					logger.info(
							"--------开始更新渠道活动缓存商家总销量--------,key:" + totalSalesKey + ";商品数量:" + productQuantity);
					jedis.incrBy(totalSalesKey, productQuantity);
					logger.info("--------更新缓存商家总销量结束--------");
					// 更新缓存SKU订单号
					logger.info("--------开始更新渠道活动缓存订单号--------,key:" + orderKey + ";订单号:"
							+ JSONObject.toJSONString(tempOrderList));
					jedis.lpush(orderKey, tempOrderList.toArray(new String[tempOrderList.size()]));
					logger.info("--------更新渠道活动缓存订单号结束--------");
				} else if (preSales > cachePreSales) {
					// 预更新销售数量
					int preUpdateSales = cachePreSales - cacheSales;
					// 更新缓存SKU已售数量
					logger.info("--------开始更新渠道活动缓存SKU销量--------,key:" + skuKey + ";商品数量:" + preUpdateSales);
					jedis.incrBy(skuKey, preUpdateSales);
					logger.info("--------更新渠道活动缓存SKU销量结束--------");
					// 更新缓存商家总销量
					System.out
							.println("--------开始更新渠道活动缓存商家总销量--------,key:" + totalSalesKey + ";商品数量" + preUpdateSales);
					jedis.incrBy(totalSalesKey, preUpdateSales);
					logger.info("--------更新渠道活动缓存商家总销量结束--------");
					// 更新缓存SKU订单号
					logger.info("--------开始更新渠道活动缓存订单号--------,key:" + orderKey + ";订单号:"
							+ JSONObject.toJSONString(tempOrderList));
					jedis.lpush(orderKey, tempOrderList.toArray(new String[tempOrderList.size()]));
					logger.info("------更新渠道活动缓存订单号结束------");
				}
			}
		} catch (Exception e) {
			logger.error("更新渠道活动SKU销量失败", e.getMessage(), e);
		} finally {
			redisLock.release(nodeKey);
			logger.info("线程[{}]释放锁[{}].", new Object[] { Thread.currentThread().getName(), nodeKey });
			logger.info("--------线程" + Thread.currentThread().getName() + "释放锁" + nodeKey + "--------");
		}

		logger.info("--------更新渠道活动SKU销量结束--------");
	}

	@Override
	public void onMessage(Message message) {
		try {
			String textMessage = (String) ((ObjectMessage) message).getObject();
			updateActivitySkuSales(textMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
