package com.pltfm.activity.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.supplier.model.User;
import com.pltfm.app.vobject.ActivityPaymentDetail;
import com.pltfm.app.vobject.ActivityPaymentDetailExample;


public interface ActivityPaymentDetailDAO {
	
	/**
	 * 批量保存退款SKU明细记录
	 * @return
	 * @throws SQLException
	 */
	int batchSaveActivityPaymentForReturn(List<ActivityPaymentDetail> activityPaymentDetailList) throws SQLException;
	
	/**
     * 根据付款id获取付款详细
     *
     * @param example 付款id
     * @return
     * @throws SQLException
     */
    List<ActivityPaymentDetail> getPaymentDetails(ActivityPaymentDetailExample example) throws SQLException;
    
    /**
     * 批量新增首次付款明细
     *
     * @param list
     * @return
     * @throws SQLException
     */
    void batchInsertSelective(List<ActivityPaymentDetail> list) throws SQLException;
    
    /**
     * 根据用户ID查询用户信息
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    User selectUserByUserId(User user) throws SQLException;
    
    /**
     * 根据供应商ID查询用户信息
     * 
     * @param supplierId
     * @return
     * @throws SQLException
     */
    User selectUserBySupplierId(Long supplierId) throws SQLException;
}