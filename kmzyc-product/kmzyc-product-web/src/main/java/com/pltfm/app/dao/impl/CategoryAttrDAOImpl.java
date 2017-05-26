package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.pltfm.app.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.CategoryAttrDAO;
import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.CategoryAttrExample;
import com.pltfm.sys.model.SysModelUtil;
import com.kmzyc.commons.page.Page;

@Repository("categoryAttrDao")
public class CategoryAttrDAOImpl extends BaseDao<CategoryAttr> implements CategoryAttrDAO {

    public int countByExample(CategoryAttrExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("CATEGORY_ATTR.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(CategoryAttrExample example) throws SQLException {
        int rows = sqlMapClient.delete("CATEGORY_ATTR.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long categoryAttrId) throws SQLException {
        CategoryAttr key = new CategoryAttr();
        key.setCategoryAttrId(categoryAttrId);
        int rows = sqlMapClient.delete("CATEGORY_ATTR.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(CategoryAttr record) throws SQLException {
        sqlMapClient.insert("CATEGORY_ATTR.ibatorgenerated_insert", record);
    }

    public void insertSelective(CategoryAttr record) throws SQLException {
        sqlMapClient.insert("CATEGORY_ATTR.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(CategoryAttrExample example) throws SQLException {
        List list = sqlMapClient.queryForList("CATEGORY_ATTR.ibatorgenerated_selectByExample", example);
        return list;
    }

    public CategoryAttr selectByPrimaryKey(Long categoryAttrId) throws SQLException {
        CategoryAttr key = new CategoryAttr();
        key.setCategoryAttrId(categoryAttrId);
        CategoryAttr record = (CategoryAttr) sqlMapClient.queryForObject("CATEGORY_ATTR.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(CategoryAttr record, CategoryAttrExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("CATEGORY_ATTR.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(CategoryAttr record, CategoryAttrExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("CATEGORY_ATTR.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(CategoryAttr record) throws SQLException {
        int rows = sqlMapClient.update("CATEGORY_ATTR.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(CategoryAttr record) throws SQLException {
        int rows = sqlMapClient.update("CATEGORY_ATTR.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
	@Override
	public Page selectPageByVo(Page page, CategoryAttr vo) throws SQLException {
		//用List接收
		List list = sqlMapClient.queryForList("CATEGORY_ATTR.getCategoryAttrCount", vo);
		
    	SysModelUtil countResult = (SysModelUtil)list.get(0);
    	//总条数
		int recs = countResult.getTheCount().intValue();
		
		int pagecount = 1;
		//总页数
		if (recs > 1){
			pagecount = (recs-1) / page.getPageSize() + 1;  
		}
		page.setRecordCount(recs);  
		page.setPageCount(pagecount);  
		
		List pageList = sqlMapClient.queryForList("CATEGORY_ATTR.searchPageByVo",vo);
		
		page.setDataList(pageList);
		return page;
	}

    private static class UpdateByExampleParms extends CategoryAttrExample {
        private Object record;

        public UpdateByExampleParms(Object record, CategoryAttrExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

	@Override
	public int findRepeatAttrName(CategoryAttr categoryAttr) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("CATEGORY_ATTR.findRepeatAttrName", categoryAttr);
	}

	@Override
	public String checkIsRelationAttr(Long categoryAttrId) throws SQLException {
		Object obj = sqlMapClient.queryForObject(
				"CATEGORY_ATTR.isRelationAttr", categoryAttrId);
		return obj == null ? null : obj.toString();
	}

}