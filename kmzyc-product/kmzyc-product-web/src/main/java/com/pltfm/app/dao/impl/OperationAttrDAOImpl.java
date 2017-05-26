package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.OperationAttrDAO;
import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.OperationAttrExample;

@Repository("operationAttrDao")
public class OperationAttrDAOImpl extends BaseDao<OperationAttr> implements OperationAttrDAO {

	public int countByExample(OperationAttrExample example) throws SQLException {
		Integer count = (Integer) sqlMapClient.queryForObject(
				"OPERATION_ATTR.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	public int deleteByExample(OperationAttrExample example) throws SQLException {
		int rows = sqlMapClient.delete(
				"OPERATION_ATTR.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	public int deleteByPrimaryKey(Long operationAttrId) throws SQLException {
		OperationAttr key = new OperationAttr();
		key.setOperationAttrId(operationAttrId);
		int rows = sqlMapClient.delete(
				"OPERATION_ATTR.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	public void insert(OperationAttr record) throws SQLException {
		sqlMapClient.insert("OPERATION_ATTR.ibatorgenerated_insert", record);
	}

	public void insertSelective(OperationAttr record) throws SQLException {
		sqlMapClient.insert("OPERATION_ATTR.ibatorgenerated_insertSelective",
				record);
	}

	public List selectByExample(OperationAttrExample example) throws SQLException {
		List list = sqlMapClient.queryForList(
				"OPERATION_ATTR.ibatorgenerated_selectByExample", example);
		return list;
	}

	public List selectByExample(OperationAttrExample example, int skip, int max) throws SQLException {
		List list = sqlMapClient.queryForList(
				"OPERATION_ATTR.ibatorgenerated_selectByExample", example,
				skip, max);
		return list;
	}

	public OperationAttr selectByPrimaryKey(Long operationAttrId) throws SQLException {
		OperationAttr key = new OperationAttr();
		key.setOperationAttrId(operationAttrId);
		OperationAttr record = (OperationAttr) sqlMapClient.queryForObject(
				"OPERATION_ATTR.ibatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	public int updateByExampleSelective(OperationAttr record, OperationAttrExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"OPERATION_ATTR.ibatorgenerated_updateByExampleSelective",
				parms);
		return rows;
	}

	public int updateByExample(OperationAttr record, OperationAttrExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"OPERATION_ATTR.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	public int updateByPrimaryKeySelective(OperationAttr record) throws SQLException {
		int rows = sqlMapClient.update(
				"OPERATION_ATTR.ibatorgenerated_updateByPrimaryKeySelective",
				record);
		return rows;
	}

	public int updateByPrimaryKey(OperationAttr record) throws SQLException {
		int rows = sqlMapClient.update(
				"OPERATION_ATTR.ibatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	private static class UpdateByExampleParms extends OperationAttrExample {
		private Object record;

		public UpdateByExampleParms(Object record, OperationAttrExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

	@Override
	public String findRelationAttr(Long operationAttrId) throws SQLException {
		Object obj = sqlMapClient.queryForObject(
				"OPERATION_ATTR.findRelationAttr", operationAttrId);
		return obj == null ? null : obj.toString();
	}

	@Override
	public void deleteBatch(String[] operationAttrIds) throws SQLException {
		sqlMapClient.delete("OPERATION_ATTR.deleteByPrimaryKeyBatch", operationAttrIds);
	}
}