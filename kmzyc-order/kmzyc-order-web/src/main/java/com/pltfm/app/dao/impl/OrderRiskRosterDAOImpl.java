package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.OrderRiskRosterDAO;
import com.pltfm.app.entities.OrderRiskBackList;
import com.pltfm.app.entities.OrderRiskWhiteList;
import com.pltfm.app.util.OrderRiskKey;

import redis.clients.jedis.JedisCluster;

@SuppressWarnings("unchecked")
@Component
public class OrderRiskRosterDAOImpl extends BaseDAO implements OrderRiskRosterDAO {

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    /**
     * 分页获取风控黑名单
     */
    @Override
    public Page queryBlackListByPage(Page page, Map<String, String> params) throws SQLException {
        page.setDataList(sqlMapClient
                .queryForList("KMORDER_ORDER_RISK_ROSTER.SQL_QUERY_BLACK_LIST_BY_PAGE", params));
        page.setRecordCount(
                queryCount("KMORDER_ORDER_RISK_ROSTER.SQL_QUERY_BLACK_LIST_COUNT", params));
        return page;
    }

    /**
     * 添加黑名单
     */
    @Override
    public boolean saveBlackList(OrderRiskBackList black) throws SQLException {
        sqlMapClient.insert("KMORDER_ORDER_RISK_ROSTER.SQL_INSERT_BLACK_LIST", black);
        jedisCluster.sadd(OrderRiskKey.ORDER_RISK_BLACK_LIST + '_' + black.getType(),
                black.getContent());
        return true;
    }

    /**
     * 添加黑名单
     */
    @Override
    public boolean saveBlackList(List<OrderRiskBackList> list) throws SQLException {
        batchInsertData("KMORDER_ORDER_RISK_ROSTER.SQL_INSERT_BLACK_LIST", list);

        for (OrderRiskBackList black : list) {
            jedisCluster.sadd(OrderRiskKey.ORDER_RISK_BLACK_LIST + '_' + black.getType(),
                    black.getContent());
        }
        return true;
    }

    /**
     * 删除黑名单
     */
    @Override
    public boolean deleteBlackList(Long bid) throws SQLException {
        OrderRiskBackList orbl = queryOrderRiskBackListById(bid);
        if (null != orbl) {
            sqlMapClient.update("KMORDER_ORDER_RISK_ROSTER.SQL_DELETE_BLACK_LIST_BY_ID", bid);

            jedisCluster.srem(OrderRiskKey.ORDER_RISK_BLACK_LIST + '_' + orbl.getType(),
                    orbl.getContent());
        }
        return true;
    }

    /**
     * 查询黑名单
     */
    @Override
    public OrderRiskBackList queryOrderRiskBackListById(Long bid) throws SQLException {
        return (OrderRiskBackList) sqlMapClient
                .queryForObject("KMORDER_ORDER_RISK_ROSTER.SQL_QUERY_ORDER_RISK_BACK_LIST_BY_ID",
                        bid);
    }

    /**
     * 分页获取风控白名单
     */
    @Override
    public Page queryWhiteListByPage(Page page, Map<String, String> params) throws SQLException {
        page.setDataList(sqlMapClient
                .queryForList("KMORDER_ORDER_RISK_ROSTER.SQL_QUERY_WHITE_LIST_BY_PAGE", params));
        page.setRecordCount(
                queryCount("KMORDER_ORDER_RISK_ROSTER.SQL_QUERY_WHITE_LIST_COUNT", params));
        return page;
    }

    /**
     * 添加白名单
     */
    @Override
    public boolean saveWhiteList(List<OrderRiskWhiteList> list) throws SQLException {
        batchInsertData("KMORDER_ORDER_RISK_ROSTER.SQL_INSERT_WHITE_LIST", list);
        for (OrderRiskWhiteList white : list) {
            jedisCluster.sadd(OrderRiskKey.ORDER_RISK_WHITE_LIST + '_' + white.getType(),
                    white.getContent());
        }
        return true;
    }

    /**
     * 添加白名单
     */
    @Override
    public boolean saveWhiteList(OrderRiskWhiteList orwl) throws SQLException {
        sqlMapClient.insert("KMORDER_ORDER_RISK_ROSTER.SQL_INSERT_WHITE_LIST", orwl);
        jedisCluster
                .sadd(OrderRiskKey.ORDER_RISK_WHITE_LIST + '_' + orwl.getType(), orwl.getContent());
        return true;
    }

    /**
     * 删除白名单
     */
    @Override
    public boolean deleteWhiteList(Long wid) throws SQLException {
        OrderRiskWhiteList orw = queryOrderRiskWhiteListById(wid);
        if (null != orw) {
            sqlMapClient.update("KMORDER_ORDER_RISK_ROSTER.SQL_DELETE_WHITE_LIST_BY_ID", wid);

            jedisCluster.srem(OrderRiskKey.ORDER_RISK_WHITE_LIST + '_' + orw.getType(),
                    orw.getContent());
        }
        return true;
    }

    /**
     * 查询白名单
     */
    @Override
    public OrderRiskWhiteList queryOrderRiskWhiteListById(Long wid) throws SQLException {
        return (OrderRiskWhiteList) sqlMapClient
                .queryForObject("KMORDER_ORDER_RISK_ROSTER.SQL_QUERY_ORDER_RISK_WHITE_LIST_BY_ID",
                        wid);
    }

    /**
     * 查询条数
     */
    private Integer queryCount(final String sql, Map<String, String> params) throws SQLException {
        Long rs = ((Long) sqlMapClient.queryForObject(sql, params));
        return null == rs ? 0 : rs.intValue();
    }

    @Override
    public List<OrderRiskBackList> queryBlackList(Map<String, Object> params) throws SQLException {
        List<OrderRiskBackList> list
                ;
        list = sqlMapClient
                .queryForList("KMORDER_ORDER_RISK_ROSTER.SQL_QUERY_ORDER_RISK_BACK_LIST", params);
        return list;
    }

    @Override
    public List<OrderRiskWhiteList> queryWhiteList(Map<String, Object> params) throws SQLException {
        List<OrderRiskWhiteList> list;
        list = sqlMapClient
                .queryForList("KMORDER_ORDER_RISK_ROSTER.SQL_QUERY_ORDER_RISK_WHITE_LIST", params);
        return list;
    }

    @Override
    public Long queryLoginInfo(String content) throws SQLException {
        Map<String, Object> params = new HashMap<>();
        params.put("content", content);
        return (Long) sqlMapClient
                .queryForObject("KMORDER_ORDER_RISK_ROSTER.SQL_QUERY_LOGIN_INFO_COUNT", params);
    }

    @Override
    public Long queryCorporate(String content) throws SQLException {
        Map<String, Object> params = new HashMap<>();
        params.put("content", content);
        return (Long) sqlMapClient
                .queryForObject("KMORDER_ORDER_RISK_ROSTER.SQL_QUERY_CORPORATE_COUNT", params);
    }

    @Override
    public boolean queryBlackListByLoginId(long loginId) throws SQLException {
        // TODO Auto-generated method stub

        Map<String, Object> params = new HashMap<>();
        params.put("loginId", loginId);
        List list = sqlMapClient
                .queryForList("KMORDER_ORDER_RISK_ROSTER.SQL_QUERY_BLACK_LIST_BY_LOGINID", params);
        boolean result = false;
        if (list != null && list.size() > 0) result = true;
        return result;

    }


}
