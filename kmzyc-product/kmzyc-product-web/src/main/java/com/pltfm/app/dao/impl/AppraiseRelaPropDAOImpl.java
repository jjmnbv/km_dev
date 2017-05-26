package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.AppraiseRelaPropDAO;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.vobject.AppraiseRelaProp;
import com.pltfm.app.vobject.AppraiseRelaPropExample;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author tanyunxing
 *
 */
@Repository("appraiseRelaPropDao")
public class AppraiseRelaPropDAOImpl extends BaseDao<AppraiseRelaProp> implements AppraiseRelaPropDAO {

    public int countByExample(AppraiseRelaPropExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("APPRAISE_RELA_PROP.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(AppraiseRelaPropExample example) throws SQLException {
        int rows = sqlMapClient.delete("APPRAISE_RELA_PROP.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long appraiseRelaPropId) throws SQLException {
        AppraiseRelaProp key = new AppraiseRelaProp();
        key.setAppraiseRelaPropId(appraiseRelaPropId);
        int rows = sqlMapClient.delete("APPRAISE_RELA_PROP.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }
    
	public int deleteBatchByCagetoryId(String[] cagetoryIds) throws SQLException {
    	int rows = sqlMapClient.delete("APPRAISE_RELA_PROP.deleteByCategoryIdBatch", cagetoryIds);
		return rows;
	}
    
	public int deleteBatchByAppraisePropId(Long[] appraisePropIds) throws SQLException {
		int rows = sqlMapClient.delete("APPRAISE_RELA_PROP.deleteByAppraisePropIdBatch", appraisePropIds);
		return rows;
	}

    public void insert(AppraiseRelaProp record) throws SQLException {
        sqlMapClient.insert("APPRAISE_RELA_PROP.ibatorgenerated_insert", record);
    }
    
	public void insertBatch(List<AppraiseRelaProp> records) throws SQLException {
    	super.batchInsertData(records, "APPRAISE_RELA_PROP.ibatorgenerated_insert");
	}

    public void insertSelective(AppraiseRelaProp record) throws SQLException {
        sqlMapClient.insert("APPRAISE_RELA_PROP.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(AppraiseRelaPropExample example) throws SQLException {
        List list = sqlMapClient.queryForList("APPRAISE_RELA_PROP.ibatorgenerated_selectByExample", example);
        return list;
    }

    public AppraiseRelaProp selectByPrimaryKey(Long appraiseRelaPropId) throws SQLException {
        AppraiseRelaProp key = new AppraiseRelaProp();
        key.setAppraiseRelaPropId(appraiseRelaPropId);
        AppraiseRelaProp record = (AppraiseRelaProp) sqlMapClient.queryForObject("APPRAISE_RELA_PROP.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(AppraiseRelaProp record, AppraiseRelaPropExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("APPRAISE_RELA_PROP.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(AppraiseRelaProp record, AppraiseRelaPropExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("APPRAISE_RELA_PROP.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(AppraiseRelaProp record) throws SQLException {
        int rows = sqlMapClient.update("APPRAISE_RELA_PROP.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(AppraiseRelaProp record) throws SQLException {
        int rows = sqlMapClient.update("APPRAISE_RELA_PROP.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends AppraiseRelaPropExample {
        private Object record;

        public UpdateByExampleParms(Object record, AppraiseRelaPropExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

}