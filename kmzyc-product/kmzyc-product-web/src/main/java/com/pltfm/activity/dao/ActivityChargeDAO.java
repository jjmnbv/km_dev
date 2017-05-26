package com.pltfm.activity.dao;

import java.sql.SQLException;

import com.pltfm.app.vobject.ActivityCharge;


public interface ActivityChargeDAO {
	
	/**
	   * 根据活动ID删除活动收费值
	   * @param activityId
	   * @return
	   * @throws Exception
	   */
	  int deleteActivityChargeByActivityId(Long activityId) throws SQLException;
	  
	  /**
	   * 保存活动收费值
	   * @param activityCharge
	   * @return
	   * @throws SQLException
	   */
	  Long saveActivityCharge(ActivityCharge activityCharge) throws SQLException;
	  
	  /**
	   * 修改活动收费值
	   * @param activityCharge
	   * @return
	   * @throws SQLException
	   */
	  int updateActivityCharge(ActivityCharge activityCharge) throws SQLException;
}