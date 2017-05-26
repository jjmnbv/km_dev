package com.pltfm.activity.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.ActivityBrand;


public interface ActivityBrandDAO {
    
	/**
	   * 根据活动ID删除活动品牌
	   * @param activityId
	   * @return
	   * @throws Exception
	   */
	  public int deleteActivityBrandByActivityId(Long activityId) throws SQLException;
	  
	  /**
	   * 保存活动品牌
	   * @param activityBrand
	   * @return
	   * @throws SQLException
	   */
	  public int batchSaveActivityBrand(List<ActivityBrand> activityBrandList) throws SQLException;
}