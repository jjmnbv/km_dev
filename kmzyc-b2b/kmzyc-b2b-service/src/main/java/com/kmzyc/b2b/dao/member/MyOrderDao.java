package com.kmzyc.b2b.dao.member;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.OrderPreferential;
import com.kmzyc.b2b.model.ProductImage;
import com.kmzyc.b2b.vo.ExeOrderData;
import com.kmzyc.framework.exception.ServiceException;

public interface MyOrderDao extends Dao {
    /**
     * 根据订单号查询根订单
     * 
     * @param orderCode
     * @return
     * @throws SQLException
     */
    public String findRootOrderCode(String orderCode) throws SQLException;

    /**
     * 根据skuId查找产品的默认图片信息（主图）
     * 
     * @param sqlId
     * @param skuCode
     * @throws SQLException
     * @return
     */
    public ProductImage findDefaultProductImageBySkuCode(String sqlId, String skuCode)
            throws SQLException;

    /**
     * 根据订单号查询子订单
     * 
     * @param orderCode
     * @return
     * @throws SQLException
     */
    public List<OrderMain> queryChildOrderByOrderCode(List<String> orderCode) throws SQLException;

    /**
     * 根据订单号查询订单
     * 
     * @param orderCode
     * @return
     * @throws SQLException
     */
    public OrderMain queryOrderMainByOrderCode(String orderCode) throws SQLException;

    /**
     * 根据订状态统计我的订单数量
     * 
     * @param map
     * @return
     * @throws ServiceException
     */
    public Map<String, BigDecimal> queryMyOrderStatusCount(Map<String, Object> map)
            throws SQLException;

    /**
     * 根据订状态统计我的订单数量
     * 
     * @param map
     * @return
     * @throws ServiceException
     */
    public Map<String, BigDecimal> queryMyOrderAppraiseCount(Map<String, Object> map)
            throws SQLException;

    /**
     * OMS查询结转订单
     * 
     * @return
     * @throws SQLException
     */
    public List<ExeOrderData> queryExeOrder(Map<String, String> params) throws SQLException;

    /**
     * OMS查询结转订单
     * 
     * @return
     * @throws SQLException
     */
    public Integer queryExeOrderCount(Map<String, String> params) throws SQLException;

    /**
     * 查询用户启用支付密码条件
     * 
     * @param uid
     * @return
     * @throws SQLException
     */
    public Map<String, String> queryEnablePayPWDCondition(Long uid) throws SQLException;

    /**
     * 统计用户pv值
     * 
     * @param customerId
     */
    public BigDecimal sumPvByCustomerId(long customerId) throws SQLException;

    /**
     * 根据订单编码获得对应支付时间
     * 
     * @return
     * @throws SQLException
     */
    public List<Map<String, Object>> findReserveByOrderCode(String orederCode) throws SQLException;

    /**
     * 根据订单编码获得对应支付各金额
     * 
     * @return
     * @throws SQLException
     */
    public List<Map<String, Object>> findReserveByOrderMoney(String orederCode) throws SQLException;

    /**
     * 根据订单编码获得对应支付手机号
     * 
     * @return
     * @throws SQLException
     */
    public String findReserveByOrderPhone(String orederCode) throws SQLException;

    /**
     * 根据订单编码获得所有流水状态
     * 
     * @return
     * @throws SQLException
     */
    public List<Map<String, Object>> findStateByOrderCode(String orederCode) throws SQLException;

    /**
     * 根据订单号查询预售活动状态
     * 
     * @param orderCode
     * @return
     * @throws SQLException
     */
    public Integer findPresellStatusByOrderCode(String orderCode) throws SQLException;

    /**
     * 根据订单编码判断是否可以支付定金
     * 
     * @return
     * @throws SQLException
     */
    public String judgeDepositTimeByOrderMoney(String orederCode) throws SQLException;

    /**
     * 根据订单号查询是否支付定金和尾款
     * 
     * @return
     * @throws SQLException
     */
    public String findDepositTailByOrderCode(String orederCode) throws SQLException;


    public List<OrderPreferential> orderPreferentialList(Map map) throws Exception;
}
