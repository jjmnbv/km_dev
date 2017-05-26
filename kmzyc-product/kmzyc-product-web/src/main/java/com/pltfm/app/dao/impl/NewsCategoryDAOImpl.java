package com.pltfm.app.dao.impl;

import com.kmzyc.supplier.model.NewsCategory;
import com.kmzyc.supplier.model.example.NewsCategoryExample;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.NewsCategoryDAO;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository("newsCategoryDao")
public class NewsCategoryDAOImpl extends BaseDao implements NewsCategoryDAO {

    public int countByExample(NewsCategoryExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("NEWS_CATEGORY.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(NewsCategoryExample example) throws SQLException {
        int rows = sqlMapClient.delete("NEWS_CATEGORY.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long newsCategoryId) throws SQLException {
        NewsCategory key = new NewsCategory();
        key.setNewsCategoryId(newsCategoryId);
        int rows = sqlMapClient.delete("NEWS_CATEGORY.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(NewsCategory record) throws SQLException {
        sqlMapClient.insert("NEWS_CATEGORY.ibatorgenerated_insert", record);
    }

    public Long insertSelective(NewsCategory record) throws SQLException {
        return (Long) sqlMapClient.insert("NEWS_CATEGORY.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(NewsCategoryExample example) throws SQLException {
        List list = sqlMapClient.queryForList("NEWS_CATEGORY.ibatorgenerated_selectByExample", example);
        return list;
    }

    public NewsCategory selectByPrimaryKey(Long newsCategoryId) throws SQLException {
        NewsCategory key = new NewsCategory();
        key.setNewsCategoryId(newsCategoryId);
        NewsCategory record = (NewsCategory) sqlMapClient.queryForObject("NEWS_CATEGORY.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(NewsCategory record, NewsCategoryExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("NEWS_CATEGORY.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(NewsCategory record, NewsCategoryExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("NEWS_CATEGORY.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(NewsCategory record) throws SQLException {
        int rows = sqlMapClient.update("NEWS_CATEGORY.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(NewsCategory record) throws SQLException {
        int rows = sqlMapClient.update("NEWS_CATEGORY.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends NewsCategoryExample {
        private Object record;

        public UpdateByExampleParms(Object record, NewsCategoryExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    public Integer selectNewCategorySupplierIdCount(Long supplierId) throws SQLException {

        return (Integer) sqlMapClient.queryForObject("NEWS_CATEGORY.selectNewCategorySupplierId", supplierId);
    }
}