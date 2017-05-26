package com.kmzyc.supplier.dao.impl;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.kmzyc.supplier.model.example.SuppliersAvailableCategorysExample;
import com.kmzyc.supplier.dao.SuppliersAvailableCategorysDAO;

@Repository("suppliersAvailableCategorysDAO")
public class SuppliersAvailableCategorysDAOImpl implements SuppliersAvailableCategorysDAO {

    @Resource
    private SqlMapClient sqlMapClient;

    public SuppliersAvailableCategorysDAOImpl() {

    }

    public int countByExample(SuppliersAvailableCategorysExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("SUPPLIERS_AVAILABLE_CATEGORYS.countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(SuppliersAvailableCategorysExample example) throws SQLException {
        return sqlMapClient.delete("SUPPLIERS_AVAILABLE_CATEGORYS.deleteByExample", example);
    }

    public int deleteByPrimaryKey(Long sacId) throws SQLException {
        SuppliersAvailableCategorys _key = new SuppliersAvailableCategorys();
        _key.setSacId(sacId);
        return sqlMapClient.delete("SUPPLIERS_AVAILABLE_CATEGORYS.deleteByPrimaryKey", _key);
    }

    public void insert(SuppliersAvailableCategorys record) throws SQLException {
        sqlMapClient.insert("SUPPLIERS_AVAILABLE_CATEGORYS.insert", record);
    }

    public void insertSelective(SuppliersAvailableCategorys record) throws SQLException {
        sqlMapClient.insert("SUPPLIERS_AVAILABLE_CATEGORYS.insertSelective", record);
    }

    public List selectByExample(SuppliersAvailableCategorysExample example) throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERS_AVAILABLE_CATEGORYS.selectByExample", example);
    }

    public SuppliersAvailableCategorys selectByPrimaryKey(Long sacId) throws SQLException {
        SuppliersAvailableCategorys _key = new SuppliersAvailableCategorys();
        _key.setSacId(sacId);
        return (SuppliersAvailableCategorys) sqlMapClient.queryForObject("SUPPLIERS_AVAILABLE_CATEGORYS.selectByPrimaryKey", _key);
    }

    public int updateByExampleSelective(SuppliersAvailableCategorys record, SuppliersAvailableCategorysExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("SUPPLIERS_AVAILABLE_CATEGORYS.updateByExampleSelective", parms);
    }

    public int updateByExample(SuppliersAvailableCategorys record, SuppliersAvailableCategorysExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("SUPPLIERS_AVAILABLE_CATEGORYS.updateByExample", parms);
    }

    public int updateByPrimaryKeySelective(SuppliersAvailableCategorys record) throws SQLException {
        return sqlMapClient.update("SUPPLIERS_AVAILABLE_CATEGORYS.updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(SuppliersAvailableCategorys record) throws SQLException {
        return sqlMapClient.update("SUPPLIERS_AVAILABLE_CATEGORYS.updateByPrimaryKey", record);
    }

    public List<SuppliersAvailableCategorys> findSuppliersAvailableCategorysBySUpplierId(Long supplierId) throws SQLException {
        List<SuppliersAvailableCategorys> list = null;
        return sqlMapClient.queryForList("SUPPLIERS_AVAILABLE_CATEGORYS.selectBySupplierId", supplierId);
    }

    protected static class UpdateByExampleParms extends SuppliersAvailableCategorysExample {
        private Object record;
        public UpdateByExampleParms(Object record, SuppliersAvailableCategorysExample example) {
            super(example);
            this.record = record;
        }
        public Object getRecord() {
            return record;
        }
    }

    @Override
    public List<SuppliersAvailableCategorys> findSupplierCategorys(SuppliersAvailableCategorys cate) throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERS_AVAILABLE_CATEGORYS.categorys_selectBysupplierId", cate);
    }

    @Override
    public List<SuppliersAvailableCategorys> findSupplierIdCategorys(SuppliersAvailableCategorys cate) throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERS_AVAILABLE_CATEGORYS.categorys_findSupplierIdCategorys", cate);
    }

    @Override
    public void delSupplierIdCategorys(Long supplierId) throws SQLException {
        sqlMapClient.delete("SUPPLIERS_AVAILABLE_CATEGORYS.ibatorgenerated_delSupplierIdCategorys", supplierId);
    }

    @Override
    public void insertSupplierIdCategorys(Map map) throws SQLException {
        sqlMapClient.insert("SUPPLIERS_AVAILABLE_CATEGORYS.ibatorgenerated_insertSupplierIdCategorys", map);
    }

    @Override
    public List<String> findSupplierCategory(Long supplierId) throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERS_AVAILABLE_CATEGORYS.findSupplierCategory", supplierId);
    }
}