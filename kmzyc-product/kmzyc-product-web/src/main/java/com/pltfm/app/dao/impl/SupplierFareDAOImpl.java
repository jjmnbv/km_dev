package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.model.example.SupplierFareExample;
import com.kmzyc.supplier.model.SupplierFare;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.SupplierFareDAO;

@Repository("supplierFareDao")
public class SupplierFareDAOImpl extends BaseDao implements SupplierFareDAO {

    public int countByExample(SupplierFareExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("SUPPLIER_FARE.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(SupplierFareExample example) throws SQLException {
        int rows = sqlMapClient.delete("SUPPLIER_FARE.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long fareId) throws SQLException {
        SupplierFare key = new SupplierFare();
        key.setFareId(fareId);
        int rows = sqlMapClient.delete("SUPPLIER_FARE.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(SupplierFare record) throws SQLException {
        sqlMapClient.insert("SUPPLIER_FARE.ibatorgenerated_insert", record);
    }

    public Long insertSelective(SupplierFare record) throws SQLException {
        return (Long)sqlMapClient.insert("SUPPLIER_FARE.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(SupplierFareExample example) throws SQLException {
        List list = sqlMapClient.queryForList("SUPPLIER_FARE.ibatorgenerated_selectByExample", example);
        return list;
    }

    public SupplierFare selectByPrimaryKey(Long fareId) throws SQLException {
        SupplierFare key = new SupplierFare();
        key.setFareId(fareId);
        SupplierFare record = (SupplierFare) sqlMapClient.queryForObject("SUPPLIER_FARE.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(SupplierFare record, SupplierFareExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SUPPLIER_FARE.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(SupplierFare record, SupplierFareExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SUPPLIER_FARE.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(SupplierFare record) throws SQLException {
        int rows = sqlMapClient.update("SUPPLIER_FARE.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(SupplierFare record) throws SQLException {
        int rows = sqlMapClient.update("SUPPLIER_FARE.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends SupplierFareExample {
        private Object record;

        public UpdateByExampleParms(Object record, SupplierFareExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    public SupplierFare selectBySupplierId(Long supplierId) throws SQLException{
    	Map paramMap = new HashMap();
    	paramMap.put("supplierId", supplierId);
    	return (SupplierFare)sqlMapClient.queryForObject("SUPPLIER_FARE.findFareBySupplierId",paramMap);
    }
    
    public List<SupplierFare> selectSupplierFareInfoList(List<Long> supplierIds)throws SQLException{
    	return sqlMapClient.queryForList("SUPPLIER_FARE.selectSupplierFareInfoList",supplierIds);
    }
}