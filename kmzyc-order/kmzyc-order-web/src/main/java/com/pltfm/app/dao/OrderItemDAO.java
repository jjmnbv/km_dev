package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderItemExample;

@SuppressWarnings("unchecked")
public interface OrderItemDAO {

    /**
     * 插入订单明细
     * 
     * @param record
     * @return
     * @throws SQLException
     */
    Long insertSelective(OrderItem record) throws SQLException;

    /**
     * 插入订单明细
     * 
     * @param records
     * @throws SQLException
     */
    int insertList(List<OrderItem> records) throws SQLException;

    /**
     * 查询订单明细
     * 
     * @param example
     * @return
     * @throws SQLException
     */
    List selectByExample(OrderItemExample example) throws SQLException;

    /**
     * 查询订单明细
     * 
     * @param orderItemId
     * @return
     * @throws SQLException
     */
    OrderItem selectByPrimaryKey(Long orderItemId) throws SQLException;

    /**
     * 查询订单明细
     * 
     * @param orderCode
     * @return
     * @throws SQLException
     */
    List selectOrderTree(String orderCode) throws SQLException;

    /**
     * 查询商品销售分析表数据条数
     * 
     * @param map
     * @return
     */
    Integer queryGoodsCount(Map<String, String> map) throws SQLException;

    /**
     * 查询商品销售分析表数据
     * 
     * @param map
     * @return
     * @throws SQLException
     */
    List<Map<String, Object>> queryGoodsReport(Map<String, String> map) throws SQLException;

    /**
     * 是否有套餐
     * 
     * @param orderCode
     * @return
     * @throws SQLException
     */
    Boolean queryIsSuit(String orderCode) throws SQLException;

    /**
     * 修改订单明细批次号
     * 
     * @param record
     * @return
     * @throws SQLException
     */
    int updateBatchNoByOrderCode(OrderItem record) throws SQLException;

    /**
     * 未退换数量
     * 
     * @param itemId
     * @return
     * @throws SQLException
     */
    Long selectOverplusNum(Long itemId) throws SQLException;

    /**
     * 批量根据订单号查询订单明细
     * 
     * @param orderCodes
     * @return
     * @throws SQLException
     */
    public List<OrderItem> queryOrderItemByOrderCodes(List<String> orderCodes) throws SQLException;

    /**
     * 根据订单号查询订单明细
     * 
     * @param orderCodes
     * @return
     * @throws SQLException
     */
    public List<OrderItem> queryOrderItemBySingleOrderCodes(String orderCode) throws SQLException;

    /**
     * 通过订单号集合，查询出订单项集合
     * 
     * @param list
     * @return
     * @throws SQLException
     */
    public Map<String, List<OrderItem>> queryExecOrderItem(List<String> list) throws SQLException;

    /**
     * 查询订单明细库存
     * 
     * @param list
     * @return
     * @throws SQLException
     */
    public Map<String, Integer> queryOrderItemStock(List<String> list) throws SQLException;

    public List selectOutOrderByExample(String orderCode) throws SQLException;

    /**
     * 批量插入订单明细表
     * 
     * @param records
     * @return
     * @throws SQLException
     */
    public HashMap<String, Object> BatchinsertOrderItem(List<OrderItem> records)
            throws SQLException;

    /**
     * 单个查询加入了预售字段 TODO 描述这个方法的作用<br/>
     *
     * @author KM
     * @param orderCode
     * @return
     * @throws SQLException
     */
    List<OrderItem> listByOrderCopdeForPresell(String orderCode) throws SQLException;

    Map<String, Object> listGoodsMoney(Map<String, String> map) throws SQLException;
}
