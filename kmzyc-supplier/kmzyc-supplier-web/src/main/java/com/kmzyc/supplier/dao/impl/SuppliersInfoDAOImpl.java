package com.kmzyc.supplier.dao.impl;

import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.model.example.SuppliersInfoExample;
import com.kmzyc.supplier.dao.SuppliersInfoDAO;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository("suppliersInfoDAO")
public class SuppliersInfoDAOImpl extends BaseDAO implements SuppliersInfoDAO {

	public int countByExample(SuppliersInfoExample example) throws SQLException {
		Integer count = (Integer) sqlMapClient.queryForObject("SUPPLIERS_INFO.countByExample", example);
		return count.intValue();
	}

	public int deleteByExample(SuppliersInfoExample example) throws SQLException {
		return sqlMapClient.delete("SUPPLIERS_INFO.deleteByExample", example);
	}

	public int deleteByPrimaryKey(Long supplierId) throws SQLException {
		SuppliersInfo _key = new SuppliersInfo();
		_key.setSupplierId(supplierId);
		return sqlMapClient.delete("SUPPLIERS_INFO.deleteByPrimaryKey", _key);
	}

	public void insert(SuppliersInfo record) throws SQLException {
		sqlMapClient.insert("SUPPLIERS_INFO.insert", record);
	}

	public void insertSelective(SuppliersInfo record) throws SQLException {
		sqlMapClient.insert("SUPPLIERS_INFO.insertSelective", record);
	}

	public List selectByExample(SuppliersInfoExample example) throws SQLException {
        return sqlMapClient.queryForList("SUPPLIERS_INFO.selectByExample", example);
	}

	public SuppliersInfo selectByPrimaryKey(Long supplierId) throws SQLException {
		SuppliersInfo _key = new SuppliersInfo();
		_key.setSupplierId(supplierId);
        return (SuppliersInfo) sqlMapClient.queryForObject("SUPPLIERS_INFO.selectByPrimaryKey", _key);
	}

	public int updateByExampleSelective(SuppliersInfo record, SuppliersInfoExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		return sqlMapClient.update("SUPPLIERS_INFO.updateByExampleSelective", parms);
	}

	public int updateByExample(SuppliersInfo record, SuppliersInfoExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		return sqlMapClient.update("SUPPLIERS_INFO.updateByExample", parms);
	}

	public int updateByPrimaryKeySelective(SuppliersInfo record) throws SQLException {
		return sqlMapClient.update("SUPPLIERS_INFO.updateByPrimaryKeySelective", record);
	}

	public int updateByPrimaryKey(SuppliersInfo record) throws SQLException {
		return sqlMapClient.update("SUPPLIERS_INFO.updateByPrimaryKey", record);
	}

	protected static class UpdateByExampleParms extends SuppliersInfoExample {
		private Object record;

		public UpdateByExampleParms(Object record, SuppliersInfoExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
	
	public SuppliersInfo selectByMerchantId(SuppliersInfo supplier) throws SQLException{
		return (SuppliersInfo)sqlMapClient.queryForObject("SUPPLIERS_INFO.selectByMerchantId",supplier);
	}

}