package com.pltfm.activity.dao;

import java.sql.SQLException;

import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivityPayment;
import com.pltfm.app.vobject.ActivityPaymentExample;


public interface ActivityPaymentDAO {
	
	/**
	 * 保存退款记录
	 * @return
	 * @throws SQLException
	 */
	Long saveActivityPaymentForRefundment(ActivityPayment activityPayment) throws SQLException;
	
	/**
	 * 根据商家报名ID获取款项相关信息
	 * @param supplierEntryId
	 * @return
	 * @throws SQLException
	 */
	ActivityInfo getPaymentBySupplierEntryId(Long supplierEntryId) throws SQLException;
	
	/**
	 * 根据商家报名ID获退款项相关信息
	 * @param supplierEntryId
	 * @return
	 * @throws SQLException
	 */
	ActivityPayment getRefundmentBySupplierEntryId(Long supplierEntryId) throws SQLException;
	
	/**
     * 获取最后一次有效的首次缴费
     * @param supplierEntryId
     * @return
     * @throws SQLException
     */
	ActivityPayment selectByExample(ActivityPaymentExample example) throws SQLException;
	
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
	
}