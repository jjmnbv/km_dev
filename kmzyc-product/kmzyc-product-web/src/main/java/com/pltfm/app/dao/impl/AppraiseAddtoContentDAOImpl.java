package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.AppraiseAddtoContentDAO;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.vobject.AppraiseAddtoContent;
import com.pltfm.app.vobject.AppraiseAddtoContentExample;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author tanyunxing
 *
 */
@Repository("appraiseAddtoContentDao")
public class AppraiseAddtoContentDAOImpl extends BaseDao<AppraiseAddtoContent> implements AppraiseAddtoContentDAO {

    public int countByExample(AppraiseAddtoContentExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("APPRAISE_ADDTO_CONTENT.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(AppraiseAddtoContentExample example) throws SQLException {
        int rows = sqlMapClient.delete("APPRAISE_ADDTO_CONTENT.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long addContentId) throws SQLException {
        AppraiseAddtoContent key = new AppraiseAddtoContent();
        key.setAddContentId(addContentId);
        int rows = sqlMapClient.delete("APPRAISE_ADDTO_CONTENT.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(AppraiseAddtoContent record) throws SQLException {
        sqlMapClient.insert("APPRAISE_ADDTO_CONTENT.ibatorgenerated_insert", record);
    }

    public void insertSelective(AppraiseAddtoContent record) throws SQLException {
        sqlMapClient.insert("APPRAISE_ADDTO_CONTENT.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(AppraiseAddtoContentExample example) throws SQLException {
        List list = sqlMapClient.queryForList("APPRAISE_ADDTO_CONTENT.ibatorgenerated_selectByExample", example);
        return list;
    }

    public AppraiseAddtoContent selectByPrimaryKey(Long addContentId) throws SQLException {
        AppraiseAddtoContent key = new AppraiseAddtoContent();
        key.setAddContentId(addContentId);
        AppraiseAddtoContent record = (AppraiseAddtoContent) sqlMapClient.queryForObject("APPRAISE_ADDTO_CONTENT.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(AppraiseAddtoContent record, AppraiseAddtoContentExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("APPRAISE_ADDTO_CONTENT.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(AppraiseAddtoContent record, AppraiseAddtoContentExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("APPRAISE_ADDTO_CONTENT.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(AppraiseAddtoContent record) throws SQLException {
        int rows = sqlMapClient.update("APPRAISE_ADDTO_CONTENT.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(AppraiseAddtoContent record) throws SQLException {
        int rows = sqlMapClient.update("APPRAISE_ADDTO_CONTENT.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends AppraiseAddtoContentExample {
        private Object record;

        public UpdateByExampleParms(Object record, AppraiseAddtoContentExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

	@Override
	public List<AppraiseAddtoContent> findValidData(AppraiseAddtoContent record,int skip,int max) throws SQLException {
		return sqlMapClient.queryForList("APPRAISE_ADDTO_CONTENT.findValidAddContent", record, skip, max);
	}

	@Override
	public int findValidDataCount(AppraiseAddtoContent record) throws SQLException {
		Integer count = (Integer)  sqlMapClient.queryForObject("APPRAISE_ADDTO_CONTENT.findValidAddContentCount", record);
        return count.intValue();
	}

	@Override
	public void deleteByPrimaryKeyBatch(Long[] addContentIds) throws SQLException {
		sqlMapClient.delete("APPRAISE_ADDTO_CONTENT.deleteByPrimaryKeyBatch", addContentIds);
	}

	@Override
	public void updateAddContentStatusPassByPrimaryKeyBatch(Long[] addContentIds) throws SQLException {
		sqlMapClient.update("APPRAISE_ADDTO_CONTENT.updateAddContentStatusPassByPrimaryKeyBatch", addContentIds);
	}

	@Override
	public void updateAddContentStatusUnPassByPrimaryKeyBatch(Long[] addContentIds) throws SQLException {
		sqlMapClient.update("APPRAISE_ADDTO_CONTENT.updateAddContentStatusUnPassByPrimaryKeyBatch", addContentIds);
	}
}