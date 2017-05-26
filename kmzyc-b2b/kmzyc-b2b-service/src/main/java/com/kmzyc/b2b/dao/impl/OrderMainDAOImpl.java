package com.kmzyc.b2b.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.OrderMainDAO;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.supplier.model.SupplierFare;
import com.kmzyc.util.StringUtil;

import redis.clients.jedis.JedisCluster;


@Repository
@SuppressWarnings("unchecked")
public class OrderMainDAOImpl implements OrderMainDAO {
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    @Override
    public List<OrderMain> findByOrderCode(Map<String, String> map) throws SQLException {
        return sqlMapClient.queryForList("OrderMain.findByOrderCode", map);
    }

    @Override
    public List<OrderMain> findPaySuccessLaterStages(Map<String, String> map) throws SQLException {
        return sqlMapClient.queryForList("OrderMain.findPaySuccessLaterStages", map);
    }

    @Override
    public BigDecimal findNeedToPayMoney(String orderCode) throws SQLException {
        BigDecimal needToPayMoney =
                (BigDecimal) sqlMapClient.queryForObject("OrderMain.findNeedToPayMoney", orderCode);
        if (null == needToPayMoney || needToPayMoney.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        } else {
            return needToPayMoney;
        }
    }

    @Override
    public BigDecimal queryPayMoney(String orderCode) throws SQLException {
        return (BigDecimal) sqlMapClient.queryForObject("OrderMain.queryPayMoney", orderCode);
    }

    @Override
    public BigDecimal findPaiedMoney(String orderCode) throws SQLException {
        BigDecimal paiedMoney =
                (BigDecimal) sqlMapClient.queryForObject("OrderMain.findPaiedMoney", orderCode);
        if (null == paiedMoney || paiedMoney.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        } else {
            return paiedMoney;
        }
    }

    /**
     * 查询活动期间某用户购买指定产品数量
     * 
     * @return
     * @throws SQLException
     */
    @Override
    public BigDecimal querySumUserBuySKUNum(Map map) throws SQLException {
        BigDecimal bd =
                (BigDecimal) sqlMapClient.queryForObject("OrderMain.querySumUserBuySKUNum", map);
        return null == bd ? BigDecimal.ZERO : bd;
    }

    /**
     * 查询某个抽奖号的订单数量
     * 
     * @return
     * @throws SQLException
     */

    @Override
    public Integer byPrizeNumberCount(OrderMain orderMain) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("OrderMain.findByPrizeNumbers", orderMain);
    }

    @Override
    public Integer queryUserBuyProductAccount(Map<String, Object> map) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("OrderMain.queryUserBuyProductAccount", map);
    }

    /**
     * 查询自营邮费信息
     * 
     * @param channel
     * @return
     * @throws SQLException
     */
    @Override
    public SupplierFare querySelfFare(String channel) throws SQLException {
        return (SupplierFare) sqlMapClient.queryForObject("OrderMain.SQL_QUERY_SELF_FREE_FARE",
                channel);
    }

    @Override
    public Long getSalesVolume(String skuId) throws SQLException {
        return (Long) sqlMapClient.queryForObject("OrderMain.getSaleVolume", skuId);
    }

    /**
     * 查询用户购买次数
     * 
     * @param loginId
     * @return
     * @throws SQLException
     */
    @Override
    public Integer queryUserBuyNum(Long loginId) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("OrderMain.SQL_QUERY_USER_BUY_NUM", loginId);
    }

    /**
     * 校验订单、用户是否匹配
     * 
     * @param params
     * @return
     * @throws SQLException
     */
    @Override
    public Integer checkOrderUser(Map<String, Object> params) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("OrderMain.SQL_QUERY_CHECK_ORDER_USER",
                params);
    }

    /**
     * 查询支付回调订单信息
     * 
     * @param orderCode
     * @return
     * @throws SQLException
     */
    @Override
    public Map<String, Object> queryOrderMainForPayCall(String orderCode) throws SQLException {
        return (Map<String, Object>) sqlMapClient
                .queryForObject("OrderMain.SQL_QUERY_ORDER_MAIN_FOR_PAY_CALL", orderCode);
    }

    @Override
    public int queryYesterdayOrderCount(Map<String, String> params) throws Exception {

        Integer orderCount;
        String key = "B2B_orderyesterdaycount_" + params.get("createDateBegin");
        String str;
        if (!StringUtil.isEmpty(str = jedisCluster.get(key))) {
            orderCount = Integer.valueOf(str);
        } else {
            orderCount = (Integer) sqlMapClient
                    .queryForObject("OrderMain.SQL_QUERY_YESTERDAY_ORDER_COUNT", params);
            if (null == orderCount) {
                orderCount = 9999;// 默认值
            }
            jedisCluster.set(key, String.valueOf(orderCount));
            jedisCluster.expire(key, 24 * 60 * 60);// 有效期一天
        }

        return (orderCount * 103) > 9999999 ? (orderCount * 103 / 10) : (orderCount * 103);
    }

    /**
     * 查询预售支付回调订单信息
     * 
     * @param mapCondition
     * @return
     * @throws SQLException
     */
    @Override
    public Map<String, Object> queryPayCallForYs(Map<String, String> mapCondition)
            throws SQLException {
        return (Map<String, Object>) sqlMapClient
                .queryForObject("OrderMain.SQL_QUERY_PAY_CALL_FOR_YS", mapCondition);
    }

    @Override
    public BigDecimal findNeedToPayDeposit(String orderCode) throws SQLException {
        BigDecimal needToPayDeposit = (BigDecimal) sqlMapClient
                .queryForObject("OrderMain.findNeedToPayDeposit", orderCode);
        if (null == needToPayDeposit || needToPayDeposit.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        } else {
            return needToPayDeposit;
        }
    }
}
