package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.AppraisePropValDAO;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.vobject.AppraisePropVal;
import com.pltfm.app.vobject.AppraisePropValExample;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author tanyunxing
 *
 */
@Repository("appraisePropValDao")
public class AppraisePropValDAOImpl extends BaseDao<AppraisePropVal> implements AppraisePropValDAO {

    public int countByExample(AppraisePropValExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("APPRAISE_PROP_VAL.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(AppraisePropValExample example) throws SQLException {
        int rows = sqlMapClient.delete("APPRAISE_PROP_VAL.ibatorgenerated_deleteByExample", example);
        return rows;
    }
    
    public int deleteByPrimaryKey(Long appraisePropValId) throws SQLException {
        AppraisePropVal key = new AppraisePropVal();
        key.setAppraisePropValId(appraisePropValId);
        int rows = sqlMapClient.delete("APPRAISE_PROP_VAL.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }
    
    @Override
	public void deleteByExampleBatch(Long[] propIds) throws SQLException {
    	sqlMapClient.delete("APPRAISE_PROP_VAL.deleteByAppPropIdBatch", propIds);
	}

    public void insert(AppraisePropVal record) throws SQLException {
        sqlMapClient.insert("APPRAISE_PROP_VAL.ibatorgenerated_insert", record);
    }
    
	public void insertBatch(List<AppraisePropVal> records) throws SQLException {
		super.batchInsertData(records, "APPRAISE_PROP_VAL.ibatorgenerated_insert");
	}

    public void insertSelective(AppraisePropVal record) throws SQLException {
        sqlMapClient.insert("APPRAISE_PROP_VAL.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(AppraisePropValExample example) throws SQLException {
        List list = sqlMapClient.queryForList("APPRAISE_PROP_VAL.ibatorgenerated_selectByExample", example);
        return list;
    }

    public AppraisePropVal selectByPrimaryKey(Long appraisePropValId) throws SQLException {
        AppraisePropVal key = new AppraisePropVal();
        key.setAppraisePropValId(appraisePropValId);
        AppraisePropVal record = (AppraisePropVal) sqlMapClient.queryForObject("APPRAISE_PROP_VAL.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(AppraisePropVal record, AppraisePropValExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("APPRAISE_PROP_VAL.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(AppraisePropVal record, AppraisePropValExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("APPRAISE_PROP_VAL.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(AppraisePropVal record) throws SQLException {
        int rows = sqlMapClient.update("APPRAISE_PROP_VAL.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }
    
	public int updateByPrimaryKeySelectiveBatch(List<AppraisePropVal> records) throws SQLException {
    	int rows = super.batchUpdateData("APPRAISE_PROP_VAL.ibatorgenerated_updateByPrimaryKeySelective", records);
		return rows;
	}

    public int updateByPrimaryKey(AppraisePropVal record) throws SQLException {
        int rows = sqlMapClient.update("APPRAISE_PROP_VAL.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends AppraisePropValExample {
        private Object record;

        public UpdateByExampleParms(Object record, AppraisePropValExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }


}