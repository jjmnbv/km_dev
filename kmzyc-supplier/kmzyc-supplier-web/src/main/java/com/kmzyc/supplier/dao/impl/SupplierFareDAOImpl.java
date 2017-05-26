package com.kmzyc.supplier.dao.impl;

import com.km.framework.page.Pagination;
import com.kmzyc.supplier.model.SupplierFare;
import com.kmzyc.supplier.dao.SupplierFareDAO;
import com.kmzyc.supplier.model.example.SupplierFareExample;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Repository;

@Repository("supplierFareDAO")
public class SupplierFareDAOImpl extends BaseDAO implements SupplierFareDAO {

    public int countByExample(SupplierFareExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("SUPPLIER_FARE.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(SupplierFareExample example) throws SQLException {
        return sqlMapClient.delete("SUPPLIER_FARE.ibatorgenerated_deleteByExample", example);
    }

    public int deleteByPrimaryKey(Long fareId) throws SQLException {
        SupplierFare key = new SupplierFare();
        key.setFareId(fareId);
        return sqlMapClient.delete("SUPPLIER_FARE.ibatorgenerated_deleteByPrimaryKey", key);
    }

    public void insert(SupplierFare record) throws SQLException {
        sqlMapClient.insert("SUPPLIER_FARE.ibatorgenerated_insert", record);
    }

    public Long insertSelective(SupplierFare record) throws SQLException {
        return (Long) sqlMapClient.insert("SUPPLIER_FARE.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(SupplierFareExample example) throws SQLException {
        return sqlMapClient.queryForList("SUPPLIER_FARE.ibatorgenerated_selectByExample", example);
    }

    public SupplierFare selectByPrimaryKey(Long fareId) throws SQLException {
        SupplierFare key = new SupplierFare();
        key.setFareId(fareId);
        return (SupplierFare) sqlMapClient.queryForObject("SUPPLIER_FARE.ibatorgenerated_selectByPrimaryKey", key);
    }

    public int updateByExampleSelective(SupplierFare record, SupplierFareExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("SUPPLIER_FARE.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByExample(SupplierFare record, SupplierFareExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("SUPPLIER_FARE.ibatorgenerated_updateByExample", parms);
    }

    public int updateByPrimaryKeySelective(SupplierFare record) throws SQLException {
        return sqlMapClient.update("SUPPLIER_FARE.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(SupplierFare record) throws SQLException {
        return sqlMapClient.update("SUPPLIER_FARE.ibatorgenerated_updateByPrimaryKey", record);
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

    @Override
    public Pagination findFareListByPage(Pagination page) throws SQLException {
        return findPaginationByPage(sqlMapClient, "SUPPLIER_FARE.selectFareListBySupplierId",
                "SUPPLIER_FARE.selectCountForFareBySupplierId", page);
    }

    @Override
    public int countFareBySupplierId(Long supplierId) throws SQLException {
        Map paramMap = new HashMap();
        paramMap.put("supplierId", supplierId);
        return (Integer) sqlMapClient.queryForObject("SUPPLIER_FARE.countFareBySupplierId", paramMap);
    }

}