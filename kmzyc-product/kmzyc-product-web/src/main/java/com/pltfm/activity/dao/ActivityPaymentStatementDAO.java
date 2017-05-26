package com.pltfm.activity.dao;

import java.sql.SQLException;

import com.pltfm.app.vobject.ActivityPaymentStatement;


public interface ActivityPaymentStatementDAO {
	
	Long insertActivityPaymentStatement(ActivityPaymentStatement activityPaymentStatement) throws SQLException;
	
}