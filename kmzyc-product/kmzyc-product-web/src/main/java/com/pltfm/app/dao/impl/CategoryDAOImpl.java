package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.pltfm.app.vobject.CategoryPv;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.CategoryDAO;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.CategoryExample;
import com.kmzyc.commons.page.Page;

@Repository("categoryDao")
public class CategoryDAOImpl extends BaseDao<Category> implements CategoryDAO {

	public int countByExample(CategoryExample example) throws SQLException {
		Integer count = (Integer) sqlMapClient.queryForObject("CATEGORY.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	public int deleteByExample(CategoryExample example) throws SQLException {
		int rows = sqlMapClient.delete("CATEGORY.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	public int deleteByPrimaryKey(Long categoryId) throws SQLException {
		Category key = new Category();
		key.setCategoryId(categoryId);
		int rows = sqlMapClient.delete("CATEGORY.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	public Long insert(Category record) throws SQLException {
		return (Long) sqlMapClient.insert("CATEGORY.ibatorgenerated_insert", record);
	}

	public void insertSelective(Category record) throws SQLException {
		sqlMapClient.insert("CATEGORY.ibatorgenerated_insertSelective", record);
	}

	public List selectByExample(CategoryExample example) throws SQLException {
		List list = sqlMapClient.queryForList("CATEGORY.ibatorgenerated_selectByExample", example);
		return list;
	}

	public Category selectByPrimaryKey(Long categoryId) throws SQLException {
		Category key = new Category();
		key.setCategoryId(categoryId);
		Category record = (Category) sqlMapClient.queryForObject("CATEGORY.ibatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	public int updateByExampleSelective(Category record, CategoryExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update("CATEGORY.ibatorgenerated_updateByExampleSelective", parms);
		return rows;
	}

	public int updateByExample(Category record, CategoryExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update("CATEGORY.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	public int updateByPrimaryKeySelective(Category record) throws SQLException {
		int rows = sqlMapClient.update("CATEGORY.ibatorgenerated_updateByPrimaryKeySelective", record);
		return rows;
	}

	public int updateByPrimaryKey(Category record) throws SQLException {
		int rows = sqlMapClient.update("CATEGORY.ibatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	public List<Category> queryCategoryList(Category category) throws SQLException {
		List<Category> list = sqlMapClient.queryForList("CATEGORY.queryCategoryList", category);
		return list;
	}

	public List<Category> queryCategoryRebateList(Category category) throws SQLException {
		List<Category> list = sqlMapClient.queryForList("CATEGORY.queryCategoryRebateList", category);
		return list;
	}

	public List<Category> queryCategoryParentList(Category category) throws SQLException {
		List<Category> list = sqlMapClient.queryForList("CATEGORY.queryCategoryParentList", category);
		return list;
	}

	private static class UpdateByExampleParms extends CategoryExample {
		private Object record;

		public UpdateByExampleParms(Object record, CategoryExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

	@Override
	public String queryMaxCategoryNoByParentId(Long parentId) throws SQLException {
		Object obj = sqlMapClient.queryForObject("CATEGORY.queryMaxCategoryNoByParentId", parentId);
		return obj == null ? null : obj.toString();
	}

	@Override
	public int queryRepeatName(Category record) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("CATEGORY.queryRepeatName", record);
	}

	@Override
	public List<String> findIsRelationCateId(Long categoryId) throws SQLException {
		return sqlMapClient.queryForList("CATEGORY.isRelationCateId", categoryId);
	}

	@Override
	public int relationDelete(Long categoryId) throws SQLException {
		return sqlMapClient.delete("CATEGORY.relationDelete", categoryId);
	}

	@Override
	public List<Category> findSomePhyCategories() throws SQLException {
		return sqlMapClient.queryForList("CATEGORY.findSomePhyCategories");
	}

	@Override
	public Category findCategoryId(Long categoryId) throws SQLException {
        return (Category) sqlMapClient.queryForObject("CATEGORY.ibatorgenerated_selectByCategoryId", categoryId);
	}

	@Override
	public void delRebateCategory() throws SQLException {
		sqlMapClient.delete("CATEGORY.delRebateCategory");
	}

	@Override
	public void addRebateCategory(List<Category> listCategories) throws SQLException {
		super.batchInsertData(listCategories, "CATEGORY.insertRebateCategory");
	}

	@Override
	public List<Category> queryCategoryOneLevelList(Category category, Page page) throws SQLException {
		return sqlMapClient.queryForList("CATEGORY.queryCategoryOneLevelList", category,
				(page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
	}

	@Override
	public int countItemCategories(Category category) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("CATEGORY.countItemCategories", category);
	}

    @Override
    public List<CategoryPv> queryPVCategoryList() throws SQLException {
        return sqlMapClient.queryForList("CATEGORY.queryPVCategoryList");
    }

    @Override
    public void delPvCategory() throws SQLException {
        sqlMapClient.delete("CATEGORY.delPvCategory");
    }

    @Override
    public void addPvCategory(List<Category> categoryList) throws SQLException {
        super.batchInsertData(categoryList, "CATEGORY.addPvCategory");
    }
}
