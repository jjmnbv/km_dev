package com.pltfm.activity.dao.impl;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.pltfm.activity.dao.ActivitySupplierScoreDAO;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.vobject.ActivitySupplierScore;

@Repository("activitySupplierScoreDao")
public class ActivitySupplierScoreDAOImpl extends BaseDao<ActivitySupplierScore> implements ActivitySupplierScoreDAO {

	@Override
	public int deleteActivitySupplierScoreByActivityId(Long activityId)
			throws SQLException {
		return sqlMapClient.delete("ACTIVITY_SUPPLIER_SCORE.deleteActivitySupplierScoreByActivityId", activityId);
	}

	@Override
	public Long saveActivitySupplierScore(
			ActivitySupplierScore activitySupplierScore) throws SQLException {
		return (Long) sqlMapClient.insert("ACTIVITY_SUPPLIER_SCORE.ibatorgenerated_insert", activitySupplierScore);
	}

	@Override
	public int updateActivitySupplierScore(
			ActivitySupplierScore activitySupplierScore) throws SQLException {
		return sqlMapClient.update("ACTIVITY_SUPPLIER_SCORE.ibatorgenerated_updateByPrimaryKeySelective", activitySupplierScore);
	}
	
}