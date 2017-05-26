package com.pltfm.activity.dao.impl;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.pltfm.activity.dao.ActivityPaymentDAO;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivityPayment;
import com.pltfm.app.vobject.ActivityPaymentExample;

@Repository("activityPaymentDao")
public class ActivityPaymentDAOImpl extends BaseDao<ActivityPayment> implements ActivityPaymentDAO {

	@Override
	public Long saveActivityPaymentForRefundment(ActivityPayment activityPayment)
			throws SQLException {
		return (Long) sqlMapClient.insert("ACTIVITY_PAYMENT.insertForRefundment", activityPayment);
	}

	@Override
	public ActivityInfo getPaymentBySupplierEntryId(Long supplierEntryId)
			throws SQLException {
		return (ActivityInfo) sqlMapClient.queryForObject("ACTIVITY_PAYMENT.getPaymentBySupplierEntryId", supplierEntryId);
	}

	@Override
	public ActivityPayment getRefundmentBySupplierEntryId(Long supplierEntryId)
			throws SQLException {
		return (ActivityPayment) sqlMapClient.queryForObject("ACTIVITY_PAYMENT.getRefundmentBySupplierEntryId", supplierEntryId);
	}

    @Override
    public ActivityPayment selectByExample(ActivityPaymentExample example) throws SQLException {
        return (ActivityPayment) sqlMapClient.queryForObject("ACTIVITY_PAYMENT.ibatorgenerated_selectByExample", example);
    }

    @Override
    public Long insertSelective(ActivityPayment activityPayment) throws SQLException {
        return (Long) sqlMapClient.insert("ACTIVITY_PAYMENT.ibatorgenerated_insertSelective", activityPayment);
    }

    @Override
    public int updateById(ActivityPayment activityPayment) throws SQLException {
        return sqlMapClient.update("ACTIVITY_PAYMENT.ibatorgenerated_updateByPrimaryKeySelective", activityPayment);
    }
	
}