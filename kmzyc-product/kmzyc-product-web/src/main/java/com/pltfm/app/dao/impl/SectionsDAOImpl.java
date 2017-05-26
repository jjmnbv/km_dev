package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.SectionsDAO;
import com.pltfm.app.vobject.Sections;
import com.pltfm.app.vobject.SectionsExample;
import com.pltfm.sys.model.SysModelUtil;
import com.kmzyc.commons.page.Page;

@Repository("sectionsDAO")
public class SectionsDAOImpl implements SectionsDAO {

	@Resource(name="sqlMapClient")
    private SqlMapClient sqlMapClient;

    public int countByExample(SectionsExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("SECTIONS.ibatorgenerated_countByExample", example);
        return count.intValue();
    }
    
    public int deleteByExample(SectionsExample example) throws SQLException {
        int rows = sqlMapClient.delete("SECTIONS.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long sectionsId) throws SQLException {
        Sections key = new Sections();
        key.setSectionsId(sectionsId);
        int rows = sqlMapClient.delete("SECTIONS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(Sections record) throws SQLException {
        sqlMapClient.insert("SECTIONS.ibatorgenerated_insert", record);
    }

    public void insertSelective(Sections record) throws SQLException {
        sqlMapClient.insert("SECTIONS.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(SectionsExample example) throws SQLException {
        List list = sqlMapClient.queryForList("SECTIONS.ibatorgenerated_selectByExample", example);
        return list;
    }
    
    public Sections selectByPrimaryKey(Long sectionsId) throws SQLException {
        Sections key = new Sections();
        key.setSectionsId(sectionsId);
        Sections record = (Sections) sqlMapClient.queryForObject("SECTIONS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(Sections record, SectionsExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SECTIONS.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(Sections record, SectionsExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SECTIONS.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(Sections record) throws SQLException {
        int rows = sqlMapClient.update("SECTIONS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(Sections record) throws SQLException {
        int rows = sqlMapClient.update("SECTIONS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends SectionsExample {
        private Object record;

        public UpdateByExampleParms(Object record, SectionsExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

	@Override
	public List selectByExample(SectionsExample example, int skip, int max)
			throws SQLException {
        return sqlMapClient.queryForList("SECTIONS.ibatorgenerated_selectByExample", example,skip,max);
	}
	
	@Override
	public List<Long> checkSectionsNameByModify(String sectionsName)
			throws SQLException {
		return sqlMapClient.queryForList("SECTIONS.checkSectionsNameByModify", sectionsName);
	}

	@Override
	public List<Long> checkIdentificationByModify(String identification)
			throws SQLException {
		return sqlMapClient.queryForList("SECTIONS.checkIdentificationByModify", identification);
	}
}