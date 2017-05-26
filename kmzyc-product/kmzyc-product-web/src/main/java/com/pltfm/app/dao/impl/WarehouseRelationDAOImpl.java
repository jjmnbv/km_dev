package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.WarehouseRelationDAO;
import com.pltfm.app.vobject.WarehouseRelation;
import com.pltfm.app.vobject.WarehouseRelationExample;

@Repository("warehouseRelationDao")
public class WarehouseRelationDAOImpl  extends BaseDao implements WarehouseRelationDAO {

    public int countByExample(WarehouseRelationExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("WAREHOUSE_RELATION.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(WarehouseRelationExample example) throws SQLException {
        return sqlMapClient.delete("WAREHOUSE_RELATION.ibatorgenerated_deleteByExample", example);
    }

    public int deleteByPrimaryKey(Long warehouseRelationId) throws SQLException {
        WarehouseRelation key = new WarehouseRelation();
        key.setWarehouseRelationId(warehouseRelationId);
        return sqlMapClient.delete("WAREHOUSE_RELATION.ibatorgenerated_deleteByPrimaryKey", key);
    }

    public Long insert(WarehouseRelation record) throws SQLException {
        return (Long)sqlMapClient.insert("WAREHOUSE_RELATION.ibatorgenerated_insert", record);
    }

    public void insertSelective(WarehouseRelation record) throws SQLException {
        sqlMapClient.insert("WAREHOUSE_RELATION.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(WarehouseRelationExample example) throws SQLException {
        return sqlMapClient.queryForList("WAREHOUSE_RELATION.ibatorgenerated_selectByExample", example);
    }

    public WarehouseRelation selectByPrimaryKey(Long warehouseRelationId) throws SQLException {
        WarehouseRelation key = new WarehouseRelation();
        key.setWarehouseRelationId(warehouseRelationId);
        return (WarehouseRelation) sqlMapClient.queryForObject("WAREHOUSE_RELATION.ibatorgenerated_selectByPrimaryKey", key);
    }

    public int updateByExampleSelective(WarehouseRelation record, WarehouseRelationExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("WAREHOUSE_RELATION.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByExample(WarehouseRelation record, WarehouseRelationExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("WAREHOUSE_RELATION.ibatorgenerated_updateByExample", parms);
    }

    public int updateByPrimaryKeySelective(WarehouseRelation record) throws SQLException {
        return sqlMapClient.update("WAREHOUSE_RELATION.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(WarehouseRelation record) throws SQLException {
        return sqlMapClient.update("WAREHOUSE_RELATION.ibatorgenerated_updateByPrimaryKey", record);
    }

    private static class UpdateByExampleParms extends WarehouseRelationExample {
        private Object record;

        public UpdateByExampleParms(Object record, WarehouseRelationExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}