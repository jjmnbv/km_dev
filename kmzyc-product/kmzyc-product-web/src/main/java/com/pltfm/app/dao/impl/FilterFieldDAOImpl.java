package com.pltfm.app.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.FilterFieldDAO;
import com.pltfm.app.vobject.FilterField;
import com.pltfm.app.vobject.FilterFieldExample;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

/**
 * 咨询回复审核关键字表
 */
@Repository("filterFieldDao")
public class FilterFieldDAOImpl extends BaseDao<FilterField> implements FilterFieldDAO {

    public int countByExample(FilterFieldExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("FILTER_FIELD.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(FilterFieldExample example) throws SQLException {
        return sqlMapClient.delete("FILTER_FIELD.ibatorgenerated_deleteByExample", example);
    }

    public int deleteByPrimaryKey(BigDecimal fieldId) throws SQLException {
        FilterField key = new FilterField();
        key.setFieldId(fieldId);
        return sqlMapClient.delete("FILTER_FIELD.ibatorgenerated_deleteByPrimaryKey", key);
    }

    public void insert(FilterField record) throws SQLException {
        sqlMapClient.insert("FILTER_FIELD.ibatorgenerated_insert", record);
    }

    public void insertSelective(FilterField record) throws SQLException {
        sqlMapClient.insert("FILTER_FIELD.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(FilterFieldExample example) throws SQLException {
        return sqlMapClient.queryForList("FILTER_FIELD.ibatorgenerated_selectByExample", example);
    }

    public FilterField selectByPrimaryKey(BigDecimal fieldId) throws SQLException {
        FilterField key = new FilterField();
        key.setFieldId(fieldId);
        FilterField record = (FilterField) sqlMapClient.queryForObject("FILTER_FIELD.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(FilterField record, FilterFieldExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("FILTER_FIELD.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByExample(FilterField record, FilterFieldExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("FILTER_FIELD.ibatorgenerated_updateByExample", parms);
    }

    public int updateByPrimaryKeySelective(FilterField record) throws SQLException {
        return sqlMapClient.update("FILTER_FIELD.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(FilterField record) throws SQLException {
        return sqlMapClient.update("FILTER_FIELD.ibatorgenerated_updateByPrimaryKey", record);
    }

    private static class UpdateByExampleParms extends FilterFieldExample {
        private Object record;

        public UpdateByExampleParms(Object record, FilterFieldExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
    public List<FilterField> selectAllFilter() throws SQLException {
        return sqlMapClient.queryForList("FILTER_FIELD.ibatorgenerated_selectAll");
    }

}