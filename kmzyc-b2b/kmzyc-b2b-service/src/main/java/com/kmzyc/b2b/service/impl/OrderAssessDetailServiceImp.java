package com.kmzyc.b2b.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.dao.OrderAssessDetailDao;
import com.kmzyc.b2b.dao.SupplierNewsDao;
import com.kmzyc.b2b.model.OrderAssessDetail;
import com.kmzyc.b2b.model.OrderAssessInfo;
import com.kmzyc.b2b.service.OrderAssessDetailService;
import com.kmzyc.b2b.vo.CommercialTenantBasicInfo;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

import redis.clients.jedis.JedisCluster;

/**
 * 对应的订单详细评价Service
 *
 * @author houl
 */
@SuppressWarnings("unchecked")
@Service("orderAssessDetailService")
public class OrderAssessDetailServiceImp implements OrderAssessDetailService {

    @Resource
    private OrderAssessDetailDao orderAssessDetailDao;

    @Resource(name = "supplierNewsDaoImpl")
    private SupplierNewsDao supplierNewsDao;

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    /**
     * 根据ordercode查询某个订单明细的订单评分
     */
    @Override
    public List<OrderAssessDetail> findOrderAssessDetailByCondition(
            OrderAssessDetail orderDetailConditon) throws Exception {
        // 设置要连接的数据源为订单系统

        return orderAssessDetailDao.findOrderAssessDetaiByCondition(orderDetailConditon);
    }

    /**
     * 判断此订单是否已经评分
     */
    @Override
    public boolean orderAssessComplete(String orderCode) throws Exception {
        boolean flag = false;
        OrderAssessDetail orderAssessCondition = new OrderAssessDetail();
        orderAssessCondition.setOrderCode(orderCode);
        List<OrderAssessDetail> orderAssessDlist =
                this.findOrderAssessDetailByCondition(orderAssessCondition);
        if (orderAssessDlist.size() == 4) {
            flag = true;
        }
        return flag;
    }

    /**
     * 查询订单的评论信息
     *
     * @author luoyi
     * @date 2013/12/18
     */
    @Override
    public OrderAssessInfo findAssessInfoByOrderCode(String orderCode) throws Exception {
        // 设置要连接的数据源为订单系统

        return orderAssessDetailDao.findAssessInfoByCode(orderCode);
    }

    /**
     * 查询店铺评分
     */
    @Override
    public Map<String, Object> queryShopScore(Long supplierId) throws ServiceException {
        Map<String, Object> resultMap = null, avgMap = null;
        try {

            String key = ConfigurationUtil.getString("b2b_shop_assess_prex")
                    .concat(String.valueOf(supplierId));
            String data;
            if (!StringUtil.isEmpty(data = jedisCluster.get(key))) {
                resultMap = JSONObject.parseObject(data, Map.class);
            }

            if (null == resultMap) {
                resultMap = new HashMap<>();
            }

            CommercialTenantBasicInfo com = supplierNewsDao.queryCommercialBySupplierId(supplierId);
            BigDecimal atc, at1, at2, at3, at4, max = new BigDecimal(5);// 总评、描述、发货、配送、售后、最大值
            if (resultMap.isEmpty()) {
                Map<String, BigDecimal> map = orderAssessDetailDao.queryShopScore(supplierId);
                if (null != map && BigDecimal.ZERO.compareTo((atc = map.get("ASSESSCOUNT"))) < 0) {
                    at1 = map.get("ASSESS_TYPE_ONE").divide(atc, 2, BigDecimal.ROUND_HALF_DOWN);
                    at2 = map.get("ASSESS_TYPE_TWO").divide(atc, 2, BigDecimal.ROUND_HALF_DOWN);
                    at3 = map.get("ASSESS_TYPE_THREE").divide(atc, 2, BigDecimal.ROUND_HALF_DOWN);
                    at4 = map.get("ASSESS_TYPE_FOUR").divide(atc, 2, BigDecimal.ROUND_HALF_DOWN);
                    if (at1.compareTo(max) > 0) {
                        at1 = max;
                    }
                    if (at2.compareTo(max) > 0) {
                        at2 = max;
                    }
                    if (at3.compareTo(max) > 0) {
                        at3 = max;
                    }
                    if (at4.compareTo(max) > 0) {
                        at4 = max;
                    }
                    resultMap.put("assess_Type_one", at1);
                    resultMap.put("assess_Type_two", at2);
                    resultMap.put("assess_Type_three", at3);
                    resultMap.put("assess_Type_four", at4);
                    resultMap
                            .put("avergScore",
                                    at1.add(at2).add(at3).add(at4)
                                            .divide(new BigDecimal(4), 2,
                                                    BigDecimal.ROUND_HALF_DOWN)
                                            .setScale(1, BigDecimal.ROUND_HALF_UP).floatValue());
                } else {
                    resultMap.put("assess_Type_one", 5);
                    resultMap.put("assess_Type_two", 5);
                    resultMap.put("assess_Type_three", 5);
                    resultMap.put("assess_Type_four", 5);
                    resultMap.put("avergScore", 5);
                }
                jedisCluster.setex(key, 24 * 60 * 60, JSONObject.toJSONString(resultMap));
            }
            key = ConfigurationUtil.getString("b2b_shop_assess_prex").concat("avg");
            if (!StringUtil.isEmpty(data = jedisCluster.get(key))) {
                avgMap = JSONObject.parseObject(data, Map.class);
            }

            if (null == avgMap || avgMap.isEmpty()) {
                avgMap = new HashMap<>();
                Map<String, BigDecimal> avg = orderAssessDetailDao.queryAvgShopScore();
                if (null != avg
                        && BigDecimal.ZERO.compareTo((atc = avg.get("ASSESSCOUNT_ALL"))) < 0) {
                    at1 = avg.get("ASSESS_TYPE_ONE_ALL").divide(atc, 2, BigDecimal.ROUND_HALF_DOWN);
                    at2 = avg.get("ASSESS_TYPE_TWO_ALL").divide(atc, 2, BigDecimal.ROUND_HALF_DOWN);
                    at3 = avg.get("ASSESS_TYPE_THREE_ALL").divide(atc, 2,
                            BigDecimal.ROUND_HALF_DOWN);
                    at4 = avg.get("ASSESS_TYPE_FOUR_ALL").divide(atc, 2,
                            BigDecimal.ROUND_HALF_DOWN);
                    if (at1.compareTo(max) > 0) {
                        at1 = max;
                    }
                    if (at2.compareTo(max) > 0) {
                        at2 = max;
                    }
                    if (at3.compareTo(max) > 0) {
                        at3 = max;
                    }
                    if (at4.compareTo(max) > 0) {
                        at4 = max;
                    }
                    avgMap.put("avg_one", at1);
                    avgMap.put("avg_two", at2);
                    avgMap.put("avg_three", at3);
                    avgMap.put("avg_four", at4);
                    avgMap.put("avg",
                            at1.add(at2).add(at3).add(at4)
                                    .divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_DOWN)
                                    .setScale(1, BigDecimal.ROUND_HALF_UP).floatValue());
                } else {
                    avgMap.put("avg_one", 5);
                    avgMap.put("avg_two", 5);
                    avgMap.put("avg_three", 5);
                    avgMap.put("avg_four", 5);
                    avgMap.put("avg", 5);
                }
                jedisCluster.setex(key, 24 * 60 * 60, JSONObject.toJSONString(avgMap));
            }
            resultMap.putAll(avgMap);
            resultMap.put("commercialTenantBasicInfo", com);
            return resultMap;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
