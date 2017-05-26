package com.pltfm.app.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.model.SuppliersWarehouse;
import com.kmzyc.supplier.model.example.SuppliersWarehouseExample;
import com.pltfm.app.dao.SuppliersWarehouseDAO;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component("suppliersWarehouseDAO")
public class SuppliersWarehouseDAOImpl implements SuppliersWarehouseDAO {

	@Resource(name="sqlMapClient")
    private SqlMapClient sqlMapClient;

    public int countByExample(SuppliersWarehouseExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("SUPPLIERS_WAREHOUSE.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(SuppliersWarehouseExample example) throws SQLException {
        int rows = sqlMapClient.delete("SUPPLIERS_WAREHOUSE.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long supWarehouseId) throws SQLException {
        SuppliersWarehouse key = new SuppliersWarehouse();
        key.setSupWarehouseId(supWarehouseId);
        int rows = sqlMapClient.delete("SUPPLIERS_WAREHOUSE.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(SuppliersWarehouse record) throws SQLException {
        sqlMapClient.insert("SUPPLIERS_WAREHOUSE.ibatorgenerated_insert", record);
    }

    public void insertSelective(SuppliersWarehouse record) throws SQLException {
        sqlMapClient.insert("SUPPLIERS_WAREHOUSE.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(SuppliersWarehouseExample example) throws SQLException {
        List list = sqlMapClient.queryForList("SUPPLIERS_WAREHOUSE.ibatorgenerated_selectByExample", example);
        return list;
    }

    public SuppliersWarehouse selectByPrimaryKey(Long supWarehouseId) throws SQLException {
        SuppliersWarehouse key = new SuppliersWarehouse();
        key.setSupWarehouseId(supWarehouseId);
        SuppliersWarehouse record = (SuppliersWarehouse) sqlMapClient.queryForObject("SUPPLIERS_WAREHOUSE.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(SuppliersWarehouse record, SuppliersWarehouseExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SUPPLIERS_WAREHOUSE.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(SuppliersWarehouse record, SuppliersWarehouseExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SUPPLIERS_WAREHOUSE.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(SuppliersWarehouse record) throws SQLException {
        int rows = sqlMapClient.update("SUPPLIERS_WAREHOUSE.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(SuppliersWarehouse record) throws SQLException {
        int rows = sqlMapClient.update("SUPPLIERS_WAREHOUSE.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends SuppliersWarehouseExample {
        private Object record;

        public UpdateByExampleParms(Object record, SuppliersWarehouseExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

	@Override
	public List<SuppliersWarehouse> selectByPrimarySupplierId(Long supplierId)
			throws SQLException {
		SuppliersWarehouse war=new SuppliersWarehouse();
		war.setSupplierId(supplierId);
		return sqlMapClient.queryForList("SUPPLIERS_WAREHOUSE.ibatorgenerated_selectBySupplierId",war);
	}
}