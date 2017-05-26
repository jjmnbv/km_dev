package com.pltfm.activity.dao;

import java.sql.SQLException;

import com.pltfm.app.vobject.ActivitySupplierScore;


public interface ActivitySupplierScoreDAO {
	
	 /**
	  * 根据活动ID删除活动商家评分
	  * @param activityId
	  * @return
	  * @throws Exception
	  */
	int deleteActivitySupplierScoreByActivityId(Long activityId) throws SQLException;
	  
	 /**
	  * 保存商家评分
	  * @param activitySupplierScore
	  * @return
	  * @throws SQLException
	  */
	Long saveActivitySupplierScore(ActivitySupplierScore activitySupplierScore) throws SQLException;
	
	
	/**
	 * 修改商家评分
	 * @param activitySupplierScore
	 * @return
	 * @throws SQLException
	 */
	int updateActivitySupplierScore(ActivitySupplierScore activitySupplierScore) throws SQLException;
}