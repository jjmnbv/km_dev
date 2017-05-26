package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.OrderItemDAO;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderItemExample;
import com.pltfm.app.entities.OrderItemExt;

@Repository("orderItemDAO")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class OrderItemDAOImpl extends BaseDAO implements OrderItemDAO {


    /**
     * 插入订单明细
     * 
     * @param record
     * @return
     * @throws SQLException
     */
    @Override
    public Long insertSelective(OrderItem oi) throws SQLException {
        Long itemId = (Long) sqlMapClient
                .insert("KMORDER_ORDER_ITEM.ibatorgenerated_insertSelective", oi);
        OrderItemExt oie = oi.getOrderItemExt();
        if (null == oie) {
            oie = new OrderItemExt();
        }
        oie.setOrderItemId(itemId);
        insertOrderItemExt(oie);
        return itemId;
    }

    /**
     * 插入订单明细
     * 
     * @param record
     * @return
     * @throws SQLException
     */
    @Override
    public int insertList(final List<OrderItem> dataList) throws SQLException {
        if (null == dataList || dataList.isEmpty()) {
            return 0;
        }
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            @Override
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                for (OrderItem oi : dataList) {
                    Long itemId = (Long) executor
                            .insert("KMORDER_ORDER_ITEM.ibatorgenerated_insertSelective", oi);
                    OrderItemExt oie = oi.getOrderItemExt();
                    if (null == oie) {
                        oie = new OrderItemExt();
                    }
                    oie.setOrderItemId(itemId);
                    insertOrderItemExt(oie);
                }
                return executor.executeBatch();
            }
        });
        return 1;
    }

    /**
     * 查询订单明细
     * 
     * @param example
     * @return
     * @throws SQLException
     */
    @Override
    public List selectByExample(OrderItemExample example) throws SQLException {
        return sqlMapClient.queryForList("KMORDER_ORDER_ITEM.ibatorgenerated_selectByExample",
                example);
    }

    /**
     * 查询订单明细加明细扩展
     * 
     * @param example
     * @return
     * @throws SQLException
     */
    @Override
    public List selectOutOrderByExample(String orderCode) throws SQLException {
        return sqlMapClient.queryForList(
                "KMORDER_ORDER_ITEM.ibatorgenerated_selectOutOrderByExample", orderCode);
    }

    /**
     * 查询订单明细
     * 
     * @param orderItemId
     * @return
     * @throws SQLException
     */
    @Override
    public OrderItem selectByPrimaryKey(Long orderItemId) throws SQLException {
        return (OrderItem) sqlMapClient.queryForObject(
                "KMORDER_ORDER_ITEM.ibatorgenerated_selectByPrimaryKey", orderItemId);
    }

    /**
     * 查询订单明细
     * 
     * @param orderCode
     * @return
     * @throws SQLException
     */
    @Override
    public List selectOrderTree(String orderCode) throws SQLException {
        return sqlMapClient.queryForList("KMORDER_ORDER_ITEM.orderTree", orderCode);
    }

    /**
     * 查询商品销售分析表数据条数
     * 
     * @param map
     * @return
     */
    @Override
    public Integer queryGoodsCount(Map<String, String> map) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("KMREPORT.SQL_QUERY_GOODS_COUNT_NEW", map);
    }

    /**
     * 查询商品销售分析表数据
     * 
     * @param map
     * @return
     * @throws SQLException
     */
    @Override
    public List<Map<String, Object>> queryGoodsReport(Map<String, String> map) throws SQLException {
        return (List<Map<String, Object>>) sqlMapClient
                .queryForList("KMREPORT.SQL_QUERY_GOODS_REPORT_NEW", map);
    }

    /**
     * 是否有套餐
     * 
     * @param orderCode
     * @return
     * @throws SQLException
     */
    @Override
    public Boolean queryIsSuit(String orderCode) throws SQLException {
        int i = (Integer) sqlMapClient.queryForObject("KMORDER_ORDER_ITEM.queryIsSuit", orderCode);
        return i > 0;
    }

    /**
     * 修改订单明细批次号
     * 
     * @param record
     * @return
     * @throws SQLException
     */
    @Override
    public int updateBatchNoByOrderCode(OrderItem record) throws SQLException {
        int rows = sqlMapClient.update("KMORDER_ORDER_ITEM.updateBatchNoByOrderCode", record);
        return rows;
    }

    /**
     * 未退换数量
     * 
     * @param itemId
     * @return
     * @throws SQLException
     */
    @Override
    public Long selectOverplusNum(Long itemId) throws SQLException {
        Long result =
                (Long) sqlMapClient.queryForObject("KMORDER_ORDER_ITEM.selectOverplusNum", itemId);
        return null == result ? 0L : result;
    }

    /**
     * 批量根据订单号查询订单明细
     * 
     * @param orderCodes
     * @return
     * @throws SQLException
     */
    public List<OrderItem> queryOrderItemByOrderCodes(List<String> orderCodes) throws SQLException {
        return sqlMapClient.queryForList("KMORDER_ORDER_ITEM.SQL_QUERY_ORDER_ITEM_BY_ORDER_CODES",
                orderCodes);
    }

    /**
     * 根据订单号查询订单明细
     * 
     * @param orderCodes
     * @return
     * @throws SQLException
     */
    @Override
    public List<OrderItem> queryOrderItemBySingleOrderCodes(String orderCode) throws SQLException {
        return (List<OrderItem>) sqlMapClient.queryForList(
                "KMORDER_ORDER_ITEM.SQL_QUERY_ORDER_ITEM_BY_SINGLE_ORDER_CODES", orderCode);
    }

    /**
     * 新增订单明细扩展
     * 
     * @param oie
     * @return
     * @throws SQLException
     */
    private Long insertOrderItemExt(OrderItemExt oie) throws SQLException {
        return (Long) sqlMapClient.insert("KMORDER_ORDER_ITEM.SQL_INSERT_ORDER_ITEM_EXT_INFO", oie);
    }

    /**
     * 通过订单号集合，查询出订单项集合
     * 
     * @param list
     * @return
     * @throws SQLException
     */
    @Override
    public Map<String, List<OrderItem>> queryExecOrderItem(List<String> list) throws SQLException {
        return sqlMapClient.queryForMap("KMORDER_ORDER_ITEM.SQL_QUERY_EXEC_ORDER_ITEM", list,
                "orderCode", "list");
    }

    /**
     * 查询仓库下各sku_id对应的库存数  
     * 
     * @param list
     * @return
     * @throws SQLException
     */
    @Override
    public Map<String, Integer> queryOrderItemStock(List<String> list) throws SQLException {
        return (Map<String, Integer>) sqlMapClient.queryForMap(
                "KMORDER_ORDER_ITEM.SQL_QUERY_ORDER_ITEM_STOCK", list, "WSID", "STOCK");
    }

    @Override
    public HashMap<String, Object> BatchinsertOrderItem(final List<OrderItem> records)
            throws SQLException {
        if (null == records || records.size() == 0) {
            return null;
        }
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            @Override
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                Map<String, Object> itemMap = new HashMap<String, Object>();
                for (OrderItem oi : records) {
                    Long itemId = (Long) executor
                            .insert("KMORDER_ORDER_ITEM.ibatorgenerated_insertSelective", oi);
                    if (oi.getCommodityType() == 1) {
                        itemMap.put(itemId.toString(), oi);
                    }
                    OrderItemExt oie = oi.getOrderItemExt();
                    if (null == oie) {
                        oie = new OrderItemExt();
                    }
                    oie.setOrderItemId(itemId);
                    insertOrderItemExt(oie);
                }
                executor.executeBatch();
                return itemMap;
            }
        });
        return null;
    }

    @Override
    public List<OrderItem> listByOrderCopdeForPresell(String orderCode) throws SQLException {
        return (List<OrderItem>) sqlMapClient
                .queryForList("KMORDER_ORDER_ITEM.ibatorgenerated_selectByOrderCode_ys", orderCode);
    }

    @Override
    public Map<String, Object> listGoodsMoney(Map<String, String> map) throws SQLException {
        return (Map<String, Object>) sqlMapClient
                        .queryForObject("KMREPORT.SQL_QUERY_GOODS_MONEY", map);
    }
}
