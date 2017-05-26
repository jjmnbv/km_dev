package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.AreaDictDAO;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.vobject.AreaDict;
import com.pltfm.app.vobject.AreaDictExample;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 地区
 *
 * @author tanyunxing
 */
@Repository("areaDictDao")
public class AreaDictDAOImpl extends BaseDao implements AreaDictDAO {

    public int countByExample(AreaDictExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("AREA_DICT.ibatorgenerated_countByExample", example);
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
        List list = sqlMapClient.queryForList("AREA_DICT.ibatorgenerated_selectByExample", example);
        return list;
    }

    public AreaDict selectByPrimaryKey(Integer areaId) throws SQLException {
        AreaDict key = new AreaDict();
        key.setAreaId(areaId);
        AreaDict record = (AreaDict) sqlMapClient.queryForObject("AREA_DICT.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(AreaDict record, AreaDictExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("AREA_DICT.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(AreaDict record, AreaDictExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("AREA_DICT.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(AreaDict record) throws SQLException {
        int rows = sqlMapClient.update("AREA_DICT.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(AreaDict record) throws SQLException {
        int rows = sqlMapClient.update("AREA_DICT.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
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