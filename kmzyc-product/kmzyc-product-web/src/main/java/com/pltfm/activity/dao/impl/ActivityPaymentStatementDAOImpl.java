package com.pltfm.activity.dao.impl;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.pltfm.activity.dao.ActivityPaymentStatementDAO;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.vobject.ActivityPaymentStatement;

@Repository("activityPaymentStatementDao")
public class ActivityPaymentStatementDAOImpl extends BaseDao<ActivityPaymentStatement> implements ActivityPaymentStatementDAO {

	@Override
	public Long insertActivityPaymentStatement(
			ActivityPaymentStatement activityPaymentStatement)
			throws SQLException {
		return (Long) sqlMapClient.insert("ACTIVITY_PAYMENT_STATEMENT.ibatorgenerated_insert", activityPaymentStatement);
	}
	
}