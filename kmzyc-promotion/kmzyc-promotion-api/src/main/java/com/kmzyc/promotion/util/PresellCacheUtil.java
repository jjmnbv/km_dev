package com.kmzyc.promotion.util;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.promotion.app.vobject.PromotionPresell;
import com.kmzyc.promotion.constant.PresellConstant;
import com.kmzyc.promotion.optimization.vo.PresellProductVO;

import redis.clients.jedis.JedisCluster;

public class PresellCacheUtil {

    @Resource
    private JedisCluster jedisCluster;

    /**
     * 获取预售产品信息
     * 
     * @param skuId
     * @return presellProductVO
     */
    public PresellProductVO getPresellCache(Long skuId) {
        if (null == skuId) {
            return null;
        }
        PresellProductVO ppv = null;
        String key = PresellConstant.R_PRODUCT_PRESELL + skuId;
        Set<String> presellIds = jedisCluster.zrange(key, -2, -1);
        Iterator<String> iter = presellIds.iterator();
        while (iter.hasNext()) {
            String presellId = iter.next();
            key = PresellConstant.R_PRESELL + presellId;
            Set<String> presells = jedisCluster.zrange(key, -2, -1);
            Iterator<String> iterPresells = presells.iterator();
            while (iterPresells.hasNext()) {
                String presell = iterPresells.next();
                PromotionPresell presellProduct =
                        JSONObject.parseObject(presell, PromotionPresell.class);
                ppv = new PresellProductVO();
                // 定金支付开始时间
                ppv.setDepositStartTime(presellProduct.getDepositStartTime());
                ppv.setDepositEndTime(presellProduct.getDepositEndTime());
                // 尾款支付开始时间
                ppv.setFinalpayStartTime(presellProduct.getFinalpayStartTime());
                ppv.setFinalpayEndTime(presellProduct.getFinalpayEndTime());
                // 发货开始时间
                ppv.setDeliveryStartTime(presellProduct.getDeliveryStartTime());
                ppv.setDeliveryEndTime(presellProduct.getDeliveryEndTime());
                ppv.setPresellId(presellProduct.getPresellId());
            }
            key = PresellConstant.R_PRESELL_RESTR + presellId + "_" + skuId;
            Map<String, String> presellProductList = jedisCluster.hgetAll(key);
            if (ppv != null && presellProductList != null) {
                // 预售价格
                ppv.setPresellPrice(
                        new BigDecimal(presellProductList.get(PresellConstant.PRESELL_PRICE)));
                // 尾款
                ppv.setFinalPrice(
                        new BigDecimal(presellProductList.get(PresellConstant.FINAL_PRICE)));
                // 定金
                ppv.setDepositPrice(
                        new BigDecimal(presellProductList.get(PresellConstant.DEPOSIT_PRICE)));
                // 预售可以用库存
                ppv.setAvailableQuantity(
                        Long.valueOf(presellProductList.get(PresellConstant.AVAILABLE_QUANTITY)));
                // 预收库存
                ppv.setPrsellStock(
                        Long.valueOf(presellProductList.get(PresellConstant.PRESELL_STOCK)));
                // 预收库存
                ppv.setPrsellStock(
                        Long.valueOf(presellProductList.get(PresellConstant.PRESELL_STOCK)));
                ppv.setByLimit(Long.valueOf(presellProductList.get(PresellConstant.MAX)));
            }
        }
        return ppv;
    }
}
