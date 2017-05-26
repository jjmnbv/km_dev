package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.dao.AreaDictDAO;
import com.pltfm.app.vobject.AreaDict;
import com.pltfm.app.vobject.AreaDictExample;

/**
 * 地区
 *
 */
@Repository("areaDictDao")
public class AreaDictDAOImpl extends BaseDAO implements AreaDictDAO {


	@Resource
	private SqlMapClient sqlMapClient;
	
    public int countByExample(AreaDictExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("AREA_DICT.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(AreaDictExample example) throws SQLException {
        int rows = sqlMapClient.delete("AREA_DICT.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer areaId) throws SQLException {
        AreaDict key = new AreaDict();
        key.setAreaId(areaId);
        int rows = sqlMapClient.delete("AREA_DICT.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(AreaDict record) throws SQLException {
        sqlMapClient.insert("AREA_DICT.ibatorgenerated_insert", record);
    }

    public void insertSelective(AreaDict record) throws SQLException {
        sqlMapClient.insert("AREA_DICT.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(AreaDictExample example) throws SQLException {
        return sqlMapClient.queryForList("AREA_DICT.ibatorgenerated_selectByExample", example);
    }

    public AreaDict selectByPrimaryKey(Integer areaId) throws SQLException {
        AreaDict key = new AreaDict();
        key.setAreaId(areaId);
        return (AreaDict) sqlMapClient.queryForObject("AREA_DICT.ibatorgenerated_selectByPrimaryKey", key);
    }

    public int updateByExampleSelective(AreaDict record, AreaDictExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("AREA_DICT.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByExample(AreaDict record, AreaDictExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("AREA_DICT.ibatorgenerated_updateByExample", parms);
    }

    public int updateByPrimaryKeySelective(AreaDict record) throws SQLException {
        return sqlMapClient.update("AREA_DICT.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(AreaDict record) throws SQLException {
        return sqlMapClient.update("AREA_DICT.ibatorgenerated_updateByPrimaryKey", record);
    }

    private static class UpdateByExampleParms extends AreaDictExample {
        private Object record;

        public UpdateByExampleParms(Object record, AreaDictExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}