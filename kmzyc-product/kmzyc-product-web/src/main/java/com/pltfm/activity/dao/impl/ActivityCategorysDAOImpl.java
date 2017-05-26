package com.pltfm.activity.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.pltfm.activity.dao.ActivityCategorysDAO;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.vobject.ActivityCategorys;
import com.pltfm.app.vobject.Category;
import com.kmzyc.commons.page.Page;

@Repository("activityCategorysDao")
public class ActivityCategorysDAOImpl extends BaseDao<ActivityCategorys>
    implements
      ActivityCategorysDAO {

  @Override
  public List<Category> selectCategorysList(Category categorys, Page page) throws SQLException {
    return sqlMapClient.queryForList("CATEGORY.queryCategoryOneLevelList", categorys,
        (page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
  }

  @Override
  public int countItemCategorys() throws SQLException {
    return 0;
  }

	@Override
	public int deleteActivityCategorysByActivityId(Long activityId)
			throws SQLException {
		return sqlMapClient.delete("ACTIVITY_CATEGORYS.deleteActivityCategorysByActivityId", activityId);
	}

	@Override
	public int batchSaveActivityCategorys(List<ActivityCategorys> activityCategorysList)
			throws SQLException {
		return super.batchinsertNt(activityCategorysList, "ACTIVITY_CATEGORYS.ibatorgenerated_insert");
	}

}
