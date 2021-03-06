package com.pltfm.cms.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.cms.dao.CmsPageDAO;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsPageExample;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component("cmsPageDAO")
public class CmsPageDAOImpl implements CmsPageDAO {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table CMS_PAGE
     *
     * @abatorgenerated Tue Sep 03 08:55:46 CST 2013
     */
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    public CmsPageDAOImpl() {
    }//无参构造函数供反射调用

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE
     *
     * @abatorgenerated Tue Sep 03 08:55:46 CST 2013
     */
    public CmsPageDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE
     *
     * @abatorgenerated Tue Sep 03 08:55:46 CST 2013
     */
    public void insert(CmsPage record) throws SQLException {
        sqlMapClient.insert("CMS_PAGE.abatorgenerated_insert", record);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE
     *
     * @abatorgenerated Tue Sep 03 08:55:46 CST 2013
     */
    public int updateByPrimaryKey(CmsPage record) throws SQLException {
        int rows = sqlMapClient.update("CMS_PAGE.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE
     *
     * @abatorgenerated Tue Sep 03 08:55:46 CST 2013
     */
    public int updateByPrimaryKeySelective(CmsPage record) throws SQLException {
        int rows = sqlMapClient.update("CMS_PAGE.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE
     *
     * @abatorgenerated Tue Sep 03 08:55:46 CST 2013
     */
    public List selectByExample(CmsPageExample example) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_PAGE.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE
     *
     * @abatorgenerated Tue Sep 03 08:55:46 CST 2013
     */
    public CmsPage selectByPrimaryKey(Integer pageId) throws SQLException {
        CmsPage key = new CmsPage();
        key.setPageId(pageId);
        CmsPage record = (CmsPage) sqlMapClient.queryForObject("CMS_PAGE.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * 根据发布类型查询页面
     *
     * @param publishType 发布类型
     */
    @Override
    public List<CmsPage> selectByPrimaryPublishType(CmsPage page)
            throws SQLException {
        List cmsPageList = null;
        cmsPageList = sqlMapClient.queryForList("CMS_PAGE.abatorgenerated_selectByPrimaryPublishType", page);
        return cmsPageList;
    }

    /**
     * 供应商根据发布类型查询页面
     *
     * @param publishType 发布类型
     */
    @Override
    public List<CmsPage> selectByPrimaryPublishType1(CmsPage page)
            throws SQLException {
        List cmsPageList = null;
        cmsPageList = sqlMapClient.queryForList("CMS_PAGE.abatorgenerated_selectByPrimaryPublishType1", page);
        return cmsPageList;
    }

    /**
     * 套餐发布类型查询页面
     */
    @Override
    public List<CmsPage> selectByPrimaryPublishType2(CmsPage page)
            throws SQLException {
        List cmsPageList = null;
        cmsPageList = sqlMapClient.queryForList("CMS_PAGE.abatorgenerated_selectByPrimaryPublishType2", page);
        return cmsPageList;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE
     *
     * @abatorgenerated Tue Sep 03 08:55:46 CST 2013
     */
    public int deleteByExample(CmsPageExample example) throws SQLException {
        int rows = sqlMapClient.delete("CMS_PAGE.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE
     *
     * @abatorgenerated Tue Sep 03 08:55:46 CST 2013
     */
    public int deleteByPrimaryKey(Integer pageId) throws SQLException {
        CmsPage key = new CmsPage();
        key.setPageId(pageId);
        int rows = sqlMapClient.delete("CMS_PAGE.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE
     *
     * @abatorgenerated Tue Sep 03 08:55:46 CST 2013
     */
    public int countByExample(CmsPageExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("CMS_PAGE.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE
     *
     * @abatorgenerated Tue Sep 03 08:55:46 CST 2013
     */
    public int updateByExampleSelective(CmsPage record, CmsPageExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("CMS_PAGE.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE
     *
     * @abatorgenerated Tue Sep 03 08:55:46 CST 2013
     */
    public int updateByExample(CmsPage record, CmsPageExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("CMS_PAGE.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table CMS_PAGE
     *
     * @abatorgenerated Tue Sep 03 08:55:46 CST 2013
     */
    private static class UpdateByExampleParms extends CmsPageExample {
        private Object record;

        public UpdateByExampleParms(Object record, CmsPageExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
    public int countByCmsPage(CmsPage cmsPage) throws SQLException {
        // TODO Auto-generated method stub
        Integer count = (Integer) this.sqlMapClient.queryForObject("CMS_PAGE.abatorgenerated_countByPage", cmsPage);
        return count.intValue();
    }

    @Override
    public List getAllPage(CmsPage cmsPage) throws SQLException {
        // TODO Auto-generated method stub
        return this.sqlMapClient.queryForList("CMS_PAGE.abatorgenerated_getAllPage", cmsPage);
    }

    public List findAllPage() throws SQLException {
        // TODO Auto-generated method stub
        return this.sqlMapClient.queryForList("CMS_PAGE.abatorgenerated_queryAllPage");
    }

    public List selectBpageId(CmsPage cmsPage) throws SQLException {
        // TODO Auto-generated method stub
        return this.sqlMapClient.queryForList("CMS_PAGE.abatorgenerated_selectBpageId", cmsPage);
    }

    @Override
    public List getPage(CmsPage cmsPage) throws SQLException {
        // TODO Auto-generated method stub
        return this.sqlMapClient.queryForList("CMS_PAGE.abatorgenerated_getPage", cmsPage);
    }

    @Override
    public int delPageById(Integer pageId) throws SQLException {
        // TODO Auto-generated method stub
        int res = this.sqlMapClient.delete("CMS_PAGE.abatorgenerated_delByPageId", pageId);
        return res;
    }

    // 备份线上数据
    public void insertPageOnline(CmsPage page) throws SQLException {
        // TODO Auto-generated method stub
        this.sqlMapClient.insert("CMS_PAGE.abatorgenerated_insert_page_online", page);
    }

    public void deletePageOnline(CmsPage page) throws SQLException {
        // TODO Auto-generated method stub
        this.sqlMapClient.insert("CMS_PAGE.abatorgenerated_delete_page_online", page);
    }

    @Override
    public int countOutPut(CmsPage page) throws SQLException {
        // TODO Auto-generated method stub
        Integer count = (Integer) this.sqlMapClient.queryForObject("CMS_PAGE.abatorgenerated_countOutPut", page);
        return count.intValue();
    }


}