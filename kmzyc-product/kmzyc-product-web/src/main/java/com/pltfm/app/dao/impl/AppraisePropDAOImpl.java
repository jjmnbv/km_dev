package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.AppraisePropDAO;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.vobject.AppraiseProp;
import com.pltfm.app.vobject.AppraisePropExample;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * @author tanyunxing
 */
@Repository("appraisePropDao")
public class AppraisePropDAOImpl extends BaseDao<AppraiseProp> implements AppraisePropDAO {

    public int countByExample(AppraisePropExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("APPRAISE_PROP.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(AppraisePropExample example) throws SQLException {
        int rows = sqlMapClient.delete("APPRAISE_PROP.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long appraisePropId) throws SQLException {
        AppraiseProp key = new AppraiseProp();
        key.setAppraisePropId(appraisePropId);
        int rows = sqlMapClient.delete("APPRAISE_PROP.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void deleteByExampleBatch(Long[] delIds) throws SQLException {
        sqlMapClient.delete("APPRAISE_PROP.deleteByPrimaryKeyBatch", delIds);
    }

    public Long insert(AppraiseProp record) throws SQLException {
        return (Long) sqlMapClient.insert("APPRAISE_PROP.ibatorgenerated_insert", record);
    }

    public void insertSelective(AppraiseProp record) throws SQLException {
        sqlMapClient.insert("APPRAISE_PROP.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(AppraisePropExample example) throws SQLException {
        List list = sqlMapClient.queryForList("APPRAISE_PROP.ibatorgenerated_selectByExample", example);
        return list;
    }

    public List selectByExample(AppraisePropExample example, int skip, int max) throws SQLException {
        List list = sqlMapClient.queryForList("APPRAISE_PROP.ibatorgenerated_selectByExample", example, skip, max);
        return list;
    }

    public List selectByCategoryId(Long cateId) throws SQLException {
        List list = sqlMapClient.queryForList("APPRAISE_PROP.selectByCategoryId", cateId);
        return list;
    }

    public AppraiseProp selectByPrimaryKey(Long appraisePropId) throws SQLException {
        AppraiseProp key = new AppraiseProp();
        key.setAppraisePropId(appraisePropId);
        AppraiseProp record = (AppraiseProp) sqlMapClient.queryForObject("APPRAISE_PROP.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(AppraiseProp record, AppraisePropExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("APPRAISE_PROP.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(AppraiseProp record, AppraisePropExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("APPRAISE_PROP.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(AppraiseProp record) throws SQLException {
        int rows = sqlMapClient.update("APPRAISE_PROP.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(AppraiseProp record) throws SQLException {
        int rows = sqlMapClient.update("APPRAISE_PROP.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends AppraisePropExample {
        private Object record;

        public UpdateByExampleParms(Object record, AppraisePropExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

}