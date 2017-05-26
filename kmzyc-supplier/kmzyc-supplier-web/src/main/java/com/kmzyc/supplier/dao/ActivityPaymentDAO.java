package com.kmzyc.supplier.dao;

import com.pltfm.app.vobject.ActivityPayment;
import com.pltfm.app.vobject.ActivityPaymentExample;

import java.sql.SQLException;
import java.util.Map;

/**
 * 功能：付款DAO
 *
 * @author Zhoujiwei
 * @since 2016/3/28 14:06
 */
public interface ActivityPaymentDAO {

    /**
     * 新增付款
     *
     * @param activityPayment
     * @return
     * @throws SQLException
     */
    Long insertSelective(ActivityPayment activityPayment) throws SQLException;

    /**
     * 修改付款通过主键
     *
     * @param activityPayment
     * @return
     * @throws SQLException
     */
    int updateById(ActivityPayment activityPayment) throws SQLException;

    /**
     * 根据查询参数查询商家报名付款信息
     *
     * @param activityPaymentExample    查询参数
     * @return
     * @throws SQLException
     */
    ActivityPayment selectByExample(ActivityPaymentExample activityPaymentExample) throws SQLException;

    /**
     * 付款
     *
     * @param param    查询参数
     * @throws SQLException
     */
    int updateExample(Map<String, Object> param) throws SQLException;

}