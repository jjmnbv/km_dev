package com.pltfm.activity.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.ActivityCategorys;
import com.pltfm.app.vobject.Category;
import com.kmzyc.commons.page.Page;


public interface ActivityCategorysDAO {
  
  /**
   * 根据条件查询列表
   * @param prod  
   * @param page
   * @return
   */
  
  List<Category>  selectCategorysList (Category categorys,Page page) throws SQLException;
  /**
   * 根据条件计算总条数
   * @param prod
   * @return
   * @throws SQLException
   */
  int countItemCategorys ()throws SQLException;
  
  /**
   * 根据活动ID删除活动类目
   * @param activityId
   * @return
   * @throws Exception
   */
  public int deleteActivityCategorysByActivityId(Long activityId) throws SQLException;
  
  /**
   * 保存活动类目
   * @param activityCategorys
   * @return
   * @throws SQLException
   */
  public int batchSaveActivityCategorys(List<ActivityCategorys> activityCategorysList) throws SQLException;
	
}