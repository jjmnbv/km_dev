package com.kmzyc.supplier.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.model.OrderMain;
import com.kmzyc.supplier.dao.OrderMainDAO;
import com.pltfm.app.entities.Invoice;
import com.pltfm.app.entities.InvoiceItemExample;
import com.pltfm.app.entities.OrderItemExample;
import com.pltfm.app.entities.OrderOperateStatementExample;
import com.pltfm.app.entities.OrderPayStatementExample;
import com.pltfm.app.entities.OrderPreferentialExample;
import com.pltfm.app.vobject.OrderMainVo;

@Repository("orderMainDAO")
public class OrderMainDAOImpl implements OrderMainDAO {

    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public List<OrderMain> selectByMap(Map<String, Object> map) throws SQLException {
        return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.selectByMap", map);
    }

    @Override
    public Integer countByMap(Map<String, Object> map) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.countByMap", map);
    }


    @Override
    public OrderMain selectByOrderCode(String orderCode) throws SQLException {
        OrderMain key = new OrderMain();
        key.setOrderCode(orderCode);
        OrderMain record = (OrderMain) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.ibatorgenerated_selectByOrderCode", key);
        return record;
    }

    @Override
    public List<OrderMainVo> selectRootAndLeafsByOrderCode(String orderCode) throws SQLException {
        return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.selectRootAndLeafsByOrderCode", orderCode);
    }

    @Override
    public BigDecimal getOrderPay(Map map) throws SQLException {
        BigDecimal pay = (BigDecimal) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.getOrderPay", map);
        return null == pay ? BigDecimal.ZERO : pay;
    }

    @Override
    public List selectOrderItemByExample(OrderItemExample example) throws SQLException {
        return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.selectByExampleForItem", example);
    }

    @Override
    public List selectOrderOpStatementByExample(OrderOperateStatementExample example) throws SQLException {
        return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.selectByExampleForOrderOpStatement", example);
    }

    @Override
    public List selectOrderPayStatementByExample(OrderPayStatementExample example) throws SQLException {
        return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.selectByExampleForOrderPayStatement",example);
    }

    @Override
    public List selectOrderPreferentialByExample(OrderPreferentialExample example) throws SQLException {
        return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.selectByExampleForOrderPreferential", example);
    }

    @Override
    public Invoice selectByPrimaryKeyForInvoice(Long invoiceId) throws SQLException {
        Invoice key = new Invoice();
        key.setInvoiceId(invoiceId);
        return (Invoice) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.selectByPrimaryKeyForInvoice", key);
    }

    @Override
    public List selectInvoiceItemByExample(InvoiceItemExample example) throws SQLException {
        return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.selectByExampleForInvoiceItem",  example);
    }

    @Override
    public String getFinallyPayEndTime(String orderCode) throws SQLException {
        return (String) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.getFinallyPayEndTime", orderCode);
    }

    @Override
    public Boolean checkIsAdditional(Map map) throws SQLException {
        Integer result = (Integer) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.checkIsAdditional", map);
        return null == result ? Boolean.FALSE : (0 != result.intValue());
    }

    @Override
    public Integer queryUpdateLogisticCount(String orderCode) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("KMORDER_ORDER_MAIN.queryUpdateLogisticCount", orderCode);
    }

    @Override
    public List queryOrderLevelPreferential(String orderCode) throws SQLException {
        return sqlMapClient.queryForList("KMORDER_ORDER_MAIN.queryOrderLevelPReferential", orderCode);
    }
}
