package com.kmzyc.supplier.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.model.SuppliersWarehouse;
import com.kmzyc.supplier.model.example.SuppliersWarehouseExample;
import com.kmzyc.supplier.dao.SuppliersWarehouseDAO;

import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@Repository("suppliersWarehouseDAO")
public class SuppliersWarehouseDAOImpl implements SuppliersWarehouseDAO {
    
    @Resource
    private SqlMapClient sqlMapClient;

    public int countByExample(SuppliersWarehouseExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("SUPPLIERS_WAREHOUSE.countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(SuppliersWarehouseExample example) throws SQLException {
        return sqlMapClient.delete("SUPPLIERS_WAREHOUSE.deleteByExample", example);
    }

    public int deleteByPrimaryKey(Long supWarehouseId) throws SQLException {
        SuppliersWarehouse _key = new SuppliersWarehouse();
        _key.setSupWarehouseId(supWarehouseId);
        return sqlMapClient.delete("SUPPLIERS_WAREHOUSE.deleteByPrimaryKey", _key);
    }

    public void insert(SuppliersWarehouse record) throws SQLException {
        sqlMapClient.insert("SUPPLIERS_WAREHOUSE.insert", record);
    }

    public void insertSelective(SuppliersWarehouse record) throws SQLException {
        sqlMapClient.insert("SUPPLIERS_WAREHOUSE.insertSelective", record);
    }

    public List selectByExample(SuppliersWarehouseExample example) throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERS_WAREHOUSE.selectByExample", example);
    }

    public SuppliersWarehouse selectByPrimaryKey(Long supWarehouseId) throws SQLException {
        SuppliersWarehouse _key = new SuppliersWarehouse();
        _key.setSupWarehouseId(supWarehouseId);
        return (SuppliersWarehouse) sqlMapClient.queryForObject("SUPPLIERS_WAREHOUSE.selectByPrimaryKey", _key);
    }

    public int updateByExampleSelective(SuppliersWarehouse record, SuppliersWarehouseExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("SUPPLIERS_WAREHOUSE.updateByExampleSelective", parms);
    }

    public int updateByExample(SuppliersWarehouse record, SuppliersWarehouseExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("SUPPLIERS_WAREHOUSE.updateByExample", parms);
    }

    public int updateByPrimaryKeySelective(SuppliersWarehouse record) throws SQLException {
        return sqlMapClient.update("SUPPLIERS_WAREHOUSE.updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(SuppliersWarehouse record) throws SQLException {
        return sqlMapClient.update("SUPPLIERS_WAREHOUSE.updateByPrimaryKey", record);
    }

    protected static class UpdateByExampleParms extends SuppliersWarehouseExample {
        private Object record;

        public UpdateByExampleParms(Object record, SuppliersWarehouseExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}