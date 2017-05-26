package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.pltfm.app.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.CategoryAttrValueDAO;
import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.CategoryAttrValueExample;

@Repository("categoryAttrValueDao")
public class CategoryAttrValueDAOImpl extends BaseDao<CategoryAttrValue> implements CategoryAttrValueDAO {

    public int countByExample(CategoryAttrValueExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("CATEGORY_ATTR_VALUE.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(CategoryAttrValueExample example) throws SQLException {
        int rows = sqlMapClient.delete("CATEGORY_ATTR_VALUE.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long categoryAttrValueId) throws SQLException {
        CategoryAttrValue key = new CategoryAttrValue();
        key.setCategoryAttrValueId(categoryAttrValueId);
        int rows = sqlMapClient.delete("CATEGORY_ATTR_VALUE.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(CategoryAttrValue record) throws SQLException {
        sqlMapClient.insert("CATEGORY_ATTR_VALUE.ibatorgenerated_insert", record);
    }

    public void insertSelective(CategoryAttrValue record) throws SQLException {
        sqlMapClient.insert("CATEGORY_ATTR_VALUE.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(CategoryAttrValueExample example) throws SQLException {
        List list = sqlMapClient.queryForList("CATEGORY_ATTR_VALUE.ibatorgenerated_selectByExample", example);
        return list;
    }

    public CategoryAttrValue selectByPrimaryKey(Long categoryAttrValueId) throws SQLException {
        CategoryAttrValue key = new CategoryAttrValue();
        key.setCategoryAttrValueId(categoryAttrValueId);
        CategoryAttrValue record = (CategoryAttrValue) sqlMapClient.queryForObject("CATEGORY_ATTR_VALUE.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(CategoryAttrValue record, CategoryAttrValueExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("CATEGORY_ATTR_VALUE.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(CategoryAttrValue record, CategoryAttrValueExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("CATEGORY_ATTR_VALUE.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(CategoryAttrValue record) throws SQLException {
        int rows = sqlMapClient.update("CATEGORY_ATTR_VALUE.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(CategoryAttrValue record) throws SQLException {
        int rows = sqlMapClient.update("CATEGORY_ATTR_VALUE.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    @Override
    public String getCategoryAttrValueByValueIds(String[] categoryAttrValueIds) throws SQLException {
        return (String) sqlMapClient.queryForObject("CATEGORY_ATTR_VALUE.getCategoryAttrValueByValueIds", categoryAttrValueIds);
    }

    private static class UpdateByExampleParms extends CategoryAttrValueExample {
        private Object record;

        public UpdateByExampleParms(Object record, CategoryAttrValueExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}