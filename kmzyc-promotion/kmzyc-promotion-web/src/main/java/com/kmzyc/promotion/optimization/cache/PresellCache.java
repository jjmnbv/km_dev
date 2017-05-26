package com.kmzyc.promotion.optimization.cache;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.mq.bean.KmMsg;
import com.kmzyc.promotion.app.util.CodeUtils;
import com.kmzyc.promotion.app.vobject.PromotionPresell;
import com.kmzyc.promotion.constant.PresellConstant;
import com.kmzyc.promotion.presell.dao.PresellManagerDao;
import com.kmzyc.promotion.sys.util.SenderUtil;

import redis.clients.jedis.JedisCluster;

@Component
public class PresellCache {

    @Resource
    private JedisCluster jedisCluster;

    @Resource
    private PresellManagerDao presellManagerDao;
    @Resource
    private com.kmzyc.promotion.app.jms.MessageSender messageSender;

    @Resource(name = "presellJmsTemplate")
    private JmsTemplate presellJmsTemplate;

    @Resource(name = "presellReviewQueue")
    private Destination presellDestinationName;


    private static final Logger logger = LoggerFactory.getLogger(PresellCache.class);

    public boolean setPresellCache(PromotionPresell promotionPresell,
            List<PromotionPresell> presellList) {
        if (null == promotionPresell || null == presellList) {
            return false;
        }
        boolean flag = false;
        Long presellId = promotionPresell.getPresellId();
        long endTime = promotionPresell.getFinalpayEndTime().getTime() / 1000;
        JSONObject json = null;
        String key = PresellConstant.R_PRESELL + presellId;
        json = new JSONObject();
        json.put("presellId", presellId);
        json.put("initialPresellNum", promotionPresell.getInitialPresellNum());
        json.put("depositStartTime", promotionPresell.getDepositStartTime());
        json.put("depositEndTime", promotionPresell.getDepositEndTime());
        json.put("finalpayStartTime", promotionPresell.getFinalpayStartTime());
        json.put("finalpayEndTime", promotionPresell.getFinalpayEndTime());
        json.put("deliveryStartTime", promotionPresell.getDeliveryStartTime());
        json.put("deliveryEndTime", promotionPresell.getDeliveryEndTime());
        Long fl = jedisCluster.zadd(key, endTime, json.toJSONString());
        jedisCluster.expireAt(key, endTime);
        if (fl.longValue() >= 0) {
            for (PromotionPresell pp : presellList) {
                String skuId = pp.getProductSkuId();
                key = PresellConstant.R_PRODUCT_PRESELL + skuId;
                fl = jedisCluster.zadd(key, endTime, presellId.toString());
                jedisCluster.expireAt(key, endTime);
                if (fl.longValue() >= 0) {
                    key = PresellConstant.R_PRESELL_RESTR + presellId + "_" + skuId;
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("skuId", skuId);
                    map.put("max", promotionPresell.getByLimit() == null ? "0"
                            : promotionPresell.getByLimit().toString());
                    map.put("presellStock",
                            pp.getPresellStock() == null ? "0" : pp.getPresellStock().toString());
                    map.put("presellPrice",
                            pp.getPresellPrice() == null ? "0" : pp.getPresellPrice().toString());
                    map.put("depositPrice",
                            pp.getDepositPrice() == null ? "0" : pp.getDepositPrice().toString());
                    map.put("finalPrice",
                            pp.getFinalPrice() == null ? "0" : pp.getFinalPrice().toString());
                    map.put("alreadySell", "0");
                    map.put("availableQuantity",
                            pp.getPresellStock() == null ? "0" : pp.getPresellStock().toString());
                    jedisCluster.hmset(key, map);
                    jedisCluster.expireAt(key, endTime);
                }
            }
            flag = true;
        }
        return flag;
    }


    /**
     * 根据skuid 删除缓存（预售商品对应的活动限购信息、预售商品对应的所有预售活动信息）
     */
    public void delPresellSkuidsCache(Long presellId, List<Long> skuids) {
        String r_presell_restr_key = "";// 预售商品对应的活动限购信息key
        String r_product_presell_key = "";// 预售商品对应的所有预售活动信息key
        for (int i = 0; i < skuids.size(); i++) {
            r_presell_restr_key = PresellConstant.R_PRESELL_RESTR + presellId + "_" + skuids.get(i);
            r_product_presell_key = PresellConstant.R_PRODUCT_PRESELL + skuids.get(i);
            jedisCluster.del(r_presell_restr_key);
            jedisCluster.del(r_product_presell_key);
        }
    }

    /**
     * 根据presellId 删除缓存（预售活动基本信息）
     */
    public void delPresellPresellIdCache(Long presellId) {

        String r_presell_key = PresellConstant.R_PRESELL + presellId;// 活动基本信息key
        jedisCluster.del(r_presell_key);
    }

    /**
     * 根据skuId 修改预售活动限购信息中的 已售数量alreadySell、availableQuantity
     * 
     * @throws Exception
     */
    public String updateProductOrderQuantityCache(Long presellId, Long skuId, int count)
            throws Exception {
        String key = PresellConstant.R_PRESELL_RESTR + presellId + "_" + skuId;
        String resultStr = "";
        try {
            if (jedisCluster.hexists(key, PresellConstant.ALREADY_SELL)) {
                // 修改数据库
                presellManagerDao.addPreselledCount(skuId, presellId, count);
                int alreadySellCount = count;
                int availableQuantityCount = 0 - count;
                // 处理缓存
                jedisCluster.hincrBy(key, PresellConstant.ALREADY_SELL, alreadySellCount);// 已售数量
                Long newAvailableQuantity = jedisCluster.hincrBy(key,
                        PresellConstant.AVAILABLE_QUANTITY, availableQuantityCount);// 剩余数量
                resultStr = presellId + "-" + skuId + "修改预售库存成功，修改后库存为：" + newAvailableQuantity;
            }

        } catch (Exception e) {
            logger.error("修改预售库存失败,presellId-skuId为:{}", presellId + "-" + skuId, e);
            resultStr = "修改预售库存失败,presellId-skuId为：" + presellId + "-" + skuId;
            return resultStr;
        }

        return resultStr;
    }

    /**
     * 发送预售价格
     * 
     * @throws Exception
     */
    public void sendPresellInfo(Map<String, Map<String, Object>> presellMap) {
        if (presellMap != null && presellMap.size() > 0) {
            KmMsg kmmsg = new KmMsg("2001", false);
            try {
                kmmsg.getMsgData().put("code", 2001);
                kmmsg.getMsgData().put("data", presellMap);
                messageSender.sendQueueMessage(CodeUtils.DESTINATION_PROMOTION_SEARCH, kmmsg);
            } catch (Exception e) {
                logger.error("mq发送预售价格异常", e);
            }
            logger.info("发送需要更新预售商品数据到MQ,data:" + presellMap + ",code:" + "2001");
        } else {
            logger.info("暂时没有需要预售更新商品数据到MQ。" + "code:" + "2001");
        }
    }


    /**
     * 发送预售时间
     * 
     * @throws Exception
     */
    public void sendPresellInfoMq(List<Long> productList, PromotionPresell promotionPresell) {
        if (productList != null && productList.size() > 0) {
            KmMsg kmmsg = new KmMsg("2001", false);
            try {
                kmmsg.getMsgData().put("data", productList);
                kmmsg.getMsgData().put("presellId", promotionPresell.getPresellId());
                long end = new Timestamp(System.currentTimeMillis()).getTime();
                // 延迟时间，以毫秒计算
                long delay = promotionPresell.getFinalpayEndTime().getTime() - end + 1000;
                SenderUtil.sendMessage(kmmsg, presellDestinationName, presellJmsTemplate, delay);
            } catch (Exception e) {
                logger.error("发送预售商品设置过期时间异常", e);
            }
            logger.info("发送预售商品设置过期时间到MQ,data:" + productList + ",code:" + "2001");
        } else {
            logger.info("暂时没有需要预售商品数据到MQ。" + "code:" + "2001");
        }
    }
}
