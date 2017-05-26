package com.kmzyc.supplier.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.kmzyc.supplier.model.OrderMain;
import com.pltfm.app.entities.Invoice;
import com.pltfm.app.entities.InvoiceItemExample;
import com.pltfm.app.entities.OrderItemExample;
import com.pltfm.app.entities.OrderOperateStatementExample;
import com.pltfm.app.entities.OrderPayStatementExample;
import com.pltfm.app.entities.OrderPreferentialExample;
import com.pltfm.app.vobject.OrderMainVo;

/**
 * 订单相关查询
 *
 * @author KM
 */
public interface OrderMainDAO {

    /**
     * 主要用于分页查询符合条件的订单总数
     *
     * @param map 条件封存对象
     * @return
     * @throws SQLException
     */
    Integer countByMap(Map<String, Object> map) throws SQLException;

    /**
     * 主要用于分页查询符合条件的订单
     *
     * @param map
     * @return
     * @throws SQLException
     */
    List<OrderMain> selectByMap(Map<String, Object> map) throws SQLException;

    /**
     * 根据订单号查询子订单(包含主订单)
     *
     * @param orderCode
     * @return
     * @throws SQLException
     */
    List<OrderMainVo> selectRootAndLeafsByOrderCode(String orderCode) throws SQLException;

    /**
     * 以订单编码查询订单实体
     *
     * @param orderCode 订单编码
     * @return
     * @throws SQLException
     */
    OrderMain selectByOrderCode(String orderCode) throws SQLException;

    /**
     * 依据条件查询各种方式已支付金额
     *
     * @param map
     * @return
     * @throws SQLException
     */
    BigDecimal getOrderPay(Map map) throws SQLException;

    /**
     * 依据条件查询订单下面的商品
     *
     * @param example
     * @return
     * @throws SQLException
     */
    List selectOrderItemByExample(OrderItemExample example) throws SQLException;

    /**
     * 依据条件查询订单操作流水
     *
     * @param example
     * @return
     * @throws SQLException
     */
    List selectOrderOpStatementByExample(OrderOperateStatementExample example) throws SQLException;

    /**
     * 依据条件查询订单支付操作流水
     *
     * @param example
     * @return
     * @throws SQLException
     */
    List selectOrderPayStatementByExample(OrderPayStatementExample example) throws SQLException;

    /**
     * 依据条件查询订单优惠
     *
     * @param example
     * @return
     * @throws SQLException
     */
    List selectOrderPreferentialByExample(OrderPreferentialExample example) throws SQLException;

    List queryOrderLevelPreferential(String orderCode) throws SQLException;

    /**
     * 依据id查询订单发票信息
     *
     * @param invoiceId
     * @return
     * @throws SQLException
     */
    Invoice selectByPrimaryKeyForInvoice(Long invoiceId) throws SQLException;

    /**
     * 检查是否需要补单
     *
     * @param map
     * @return
     * @throws SQLException
     */
    Boolean checkIsAdditional(Map map) throws SQLException;

    /**
     * 获取物流信息修改次数
     *
     * @param orderCode
     * @return
     * @throws SQLException
     */
    Integer queryUpdateLogisticCount(String orderCode) throws SQLException;

    /**
     * 依据条件查询订单发票明细
     *
     * @param example
     * @return
     * @throws SQLException
     */
    List selectInvoiceItemByExample(InvoiceItemExample example) throws SQLException;

    /**
     * 获取预售活动尾款支付截止时间
     *
     * @param orderCode 订单号
     * @return
     * @throws SQLException
     */
    String getFinallyPayEndTime(String orderCode) throws SQLException;

}
