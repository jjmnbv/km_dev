package com.pltfm.activity.dao.impl;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.pltfm.activity.dao.ActivityChargeDAO;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.vobject.ActivityCharge;
import com.pltfm.app.vobject.ActivitySupplierScore;

@Repository("activityChargeDao")
public class ActivityChargeDAOImpl extends BaseDao<ActivityCharge> implements ActivityChargeDAO {

	@Override
	public int deleteActivityChargeByActivityId(Long activityId)
			throws SQLException {
		return sqlMapClient.delete("ACTIVITY_CHARGE.deleteActivityChargeByActivityId", activityId);
	}
	
	@Override
	public Long saveActivityCharge(
			ActivityCharge activityCharge) throws SQLException {
		return (Long) sqlMapClient.insert("ACTIVITY_CHARGE.ibatorgenerated_insert", activityCharge);
	}

	@Override
	public int updateActivityCharge(ActivityCharge activityCharge)
			throws SQLException {
		return sqlMapClient.update("ACTIVITY_CHARGE.ibatorgenerated_updateByPrimaryKeySelective", activityCharge);
	}
}