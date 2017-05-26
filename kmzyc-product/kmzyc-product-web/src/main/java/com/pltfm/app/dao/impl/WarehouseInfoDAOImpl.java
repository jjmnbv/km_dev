package com.pltfm.app.dao.impl;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.WarehouseInfoDAO;
import com.pltfm.app.vobject.WarehouseInfo;
import com.pltfm.app.vobject.WarehouseInfoExample;

@Repository("warehouseInfoDao")
public class WarehouseInfoDAOImpl extends BaseDao implements WarehouseInfoDAO {

    public int countByExample(WarehouseInfoExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("WAREHOUSE_INFO.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(WarehouseInfoExample example) throws SQLException {
        return sqlMapClient.delete("WAREHOUSE_INFO.ibatorgenerated_deleteByExample", example);
    }

    public Long insert(WarehouseInfo record) throws SQLException {
        return (Long) getSqlMapClientTemplate().insert("WAREHOUSE_INFO.ibatorgenerated_insert", record);
    }

    public void insertSelective(WarehouseInfo record) throws SQLException {
        sqlMapClient.insert("WAREHOUSE_INFO.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(WarehouseInfoExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("WAREHOUSE_INFO.ibatorgenerated_selectByExample", example, skip, max);
    }

    public int updateByExampleSelective(WarehouseInfo record, WarehouseInfoExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("WAREHOUSE_INFO.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByExample(WarehouseInfo record, WarehouseInfoExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("WAREHOUSE_INFO.ibatorgenerated_updateByExample", parms);
    }

    public int updateByPrimaryKeySelective(WarehouseInfo record) throws SQLException {
        return sqlMapClient.update("WAREHOUSE_INFO.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(WarehouseInfo record) throws SQLException {
        return sqlMapClient.update("WAREHOUSE_INFO.ibatorgenerated_updateByPrimaryKey", record);
    }

    private static class UpdateByExampleParms extends WarehouseInfoExample {
        private Object record;

        public UpdateByExampleParms(Object record, WarehouseInfoExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    public int deleteByPrimaryKey(Long warehouseId) throws SQLException {
        WarehouseInfo key = new WarehouseInfo();
        key.setWarehouseId(warehouseId);
        return sqlMapClient.delete("WAREHOUSE_INFO.ibatorgenerated_deleteByPrimaryKey", key);
    }

    public WarehouseInfo selectByPrimaryKey(Long warehouseId) throws SQLException {
        WarehouseInfo key = new WarehouseInfo();
        key.setWarehouseId(warehouseId);
        WarehouseInfo record = (WarehouseInfo) sqlMapClient.queryForObject("WAREHOUSE_INFO.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int batchUpdateForWarehouseInfo(String statementName, List<Object> parameterList)
            throws SQLException {
        return super.batchUpdateData(statementName, parameterList);
    }

    public List selectByExample(WarehouseInfoExample example) throws SQLException {
        return sqlMapClient.queryForList("WAREHOUSE_INFO.ibatorgenerated_selectByExample", example);
    }

    public int checkWarehouseInfoByName(String warehouseName) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("WAREHOUSE_INFO.checkWarehouseInfoName", warehouseName);
        return count.intValue();
    }

    public List<Long> checkWarehouseNameByModify(String warehouseName) throws SQLException {
        return sqlMapClient.queryForList("WAREHOUSE_INFO.checkWarehouseNameByModify", warehouseName);
    }

    public List<WarehouseInfo> getWarehouseInfoByList(List<Object> warehouseInfoList) throws SQLException {
        return sqlMapClient.queryForList("WAREHOUSE_INFO.selectWarehouseByList", warehouseInfoList);
    }

}