package com.pltfm.activity.dao.impl;


import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.pltfm.activity.dao.ActivityBrandDAO;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.vobject.ActivityBrand;

@Repository("activityBrandDao")
public class ActivityBrandDAOImpl extends BaseDao<ActivityBrand> implements ActivityBrandDAO {

	@Override
	public int deleteActivityBrandByActivityId(Long activityId)
			throws SQLException {
		return sqlMapClient.delete("ACTIVITY_BRAND.deleteActivityBrandByActivityId", activityId);	
	}

	@Override
	public int batchSaveActivityBrand(List<ActivityBrand> activityBrandList)
			throws SQLException {
		
		return super.batchinsertNt(activityBrandList, "ACTIVITY_BRAND.ibatorgenerated_insert");
	}
	
}