package com.pltfm.cms.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.cms.dao.CmsThemeDAO;
import com.pltfm.cms.vobject.CmsTheme;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component("cmsThemeDAO")
public class CmsThemeDAOImpl implements CmsThemeDAO {

    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }


    public void insert(CmsTheme record) throws SQLException {
        sqlMapClient.insert("CMS_THEME.abatorgenerated_insert", record);
    }


    public int update(CmsTheme record) throws SQLException {
        int rows = sqlMapClient.update("CMS_THEME.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }


    public int del(CmsTheme record) throws SQLException {
        int rows = sqlMapClient.delete("CMS_THEME.abatorgenerated_deleteByPrimaryKey", record);
        return rows;
    }
    
    /*
 
   /*
    public List selectByExample(CmsThemeExample example) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_THEME.abatorgenerated_selectByExample", example);
        return list;
    }
*/

    public CmsTheme selectByPrimaryKey(Integer themeId) throws SQLException {
        CmsTheme key = new CmsTheme();
        key.setThemeId(themeId);
        CmsTheme record = (CmsTheme) sqlMapClient.queryForObject("CMS_THEME.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public List queryThemeListForPage(CmsTheme cmsTheme) throws SQLException {

        return sqlMapClient.queryForList("CMS_THEME.abatorgenerated_themeListForPage", cmsTheme);
    }

    public List queryThemeList(CmsTheme cmsTheme) throws SQLException {

        return sqlMapClient.queryForList("CMS_THEME.abatorgenerated_themeList", cmsTheme);
    }

    public List queryThemeListAll(CmsTheme cmsTheme) throws SQLException {

        return sqlMapClient.queryForList("CMS_THEME.abatorgenerated_themeTypeAll", cmsTheme);
    }


    public CmsTheme queryThemeType(CmsTheme cmsTheme) throws SQLException {

        return (CmsTheme) sqlMapClient.queryForObject("CMS_THEME.abatorgenerated_themeType", cmsTheme);
    }


    public CmsTheme selectThemeType(CmsTheme cmsTheme) throws SQLException {

        return (CmsTheme) sqlMapClient.queryForObject("CMS_THEME.QY_THEMETYPE", cmsTheme);
    }

    public Integer queryThemeCount(CmsTheme cmsTheme) throws SQLException {

        return (Integer) sqlMapClient.queryForObject("CMS_THEME.abatorgenerated_themeCount", cmsTheme);
    }
    /*
    public int deleteByExample(CmsThemeExample example) throws SQLException {
        int rows = sqlMapClient.delete("CMS_THEME.abatorgenerated_deleteByExample", example);
        return rows;
    }
    


	@Override
	public int deleteByPrimaryKey(Integer themeId) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
  
    */

  /*
    public int countByExample(CmsThemeExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("CMS_THEME.abatorgenerated_countByExample", example);
        return count.intValue();
    }

  
    public int updateByExampleSelective(CmsTheme record, CmsThemeExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("CMS_THEME.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }


    public int updateByExample(CmsTheme record, CmsThemeExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("CMS_THEME.abatorgenerated_updateByExample", parms);
        return rows;
    }
*/

}