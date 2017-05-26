package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.AppraiseReplyDAO;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.vobject.AppraiseReply;
import com.pltfm.app.vobject.AppraiseReplyExample;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("appraiseReplyDao")
public class AppraiseReplyDAOImpl extends BaseDao<AppraiseReply> implements AppraiseReplyDAO {

    public int countByExample(AppraiseReplyExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("APPRAISE_REPLY.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(AppraiseReplyExample example) throws SQLException {
        int rows = sqlMapClient.delete("APPRAISE_REPLY.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long apprReplyId) throws SQLException {
        AppraiseReply key = new AppraiseReply();
        key.setApprReplyId(apprReplyId);
        int rows = sqlMapClient.delete("APPRAISE_REPLY.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(AppraiseReply record) throws SQLException {
    	record.setReplyStatus((short)0);
        sqlMapClient.insert("APPRAISE_REPLY.ibatorgenerated_insert", record);
    }

    public void insertSelective(AppraiseReply record) throws SQLException {
        sqlMapClient.insert("APPRAISE_REPLY.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(AppraiseReplyExample example) throws SQLException {
        List list = sqlMapClient.queryForList("APPRAISE_REPLY.ibatorgenerated_selectByExample", example);
        return list;
    }
    
    public List selectByExample(AppraiseReplyExample example,int skip,int max) throws SQLException {
        List list = sqlMapClient.queryForList("APPRAISE_REPLY.ibatorgenerated_selectByExample", example,skip,max);
        return list;
    }

    public AppraiseReply selectByPrimaryKey(Long apprReplyId) throws SQLException {
        AppraiseReply key = new AppraiseReply();
        key.setApprReplyId(apprReplyId);
        AppraiseReply record = (AppraiseReply) sqlMapClient.queryForObject("APPRAISE_REPLY.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(AppraiseReply record, AppraiseReplyExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("APPRAISE_REPLY.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(AppraiseReply record, AppraiseReplyExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("APPRAISE_REPLY.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(AppraiseReply record) throws SQLException {
        int rows = sqlMapClient.update("APPRAISE_REPLY.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(AppraiseReply record) throws SQLException {
        int rows = sqlMapClient.update("APPRAISE_REPLY.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends AppraiseReplyExample {
        private Object record;

        public UpdateByExampleParms(Object record, AppraiseReplyExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

	@Override
	public void deleteByPrimaryKeyBatch(Long[] apprReplyIds) throws SQLException {
		sqlMapClient.delete("APPRAISE_REPLY.deleteByPrimaryKeyBatch", apprReplyIds);
	}

	@Override
	public void updateReplyStatusPassByPrimaryKeyBatch(Long[] apprReplyIds) throws SQLException {
		sqlMapClient.update("APPRAISE_REPLY.updateReplyStatusPassByPrimaryKeyBatch", apprReplyIds);
	}

	@Override
	public void updateReplyStatusUnPassByPrimaryKeyBatch(Long[] apprReplyIds) throws SQLException {
		sqlMapClient.update("APPRAISE_REPLY.updateReplyStatusUnPassByPrimaryKeyBatch", apprReplyIds);
	}
}