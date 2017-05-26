package com.pltfm.cms.dao;

import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsPageWindow;
import com.pltfm.cms.vobject.CmsPageWindowExample;
import com.pltfm.cms.vobject.CmsPageWindowQry;

import java.sql.SQLException;
import java.util.List;

public interface CmsPageWindowDAO {
    public List queryWindowList(CmsPageWindowQry qry) throws SQLException;

    void insert(CmsPageWindow record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE_WINDOW
     *
     * @abatorgenerated Wed Sep 04 10:38:36 CST 2013
     */
    int updateByPrimaryKey(CmsPageWindow record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE_WINDOW
     *
     * @abatorgenerated Wed Sep 04 10:38:36 CST 2013
     */
    int updateByPrimaryKeySelective(CmsPageWindow record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE_WINDOW
     *
     * @abatorgenerated Wed Sep 04 10:38:36 CST 2013
     */
    List selectByExample(CmsPageWindowExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE_WINDOW
     *
     * @abatorgenerated Wed Sep 04 10:38:36 CST 2013
     */
    CmsPageWindow selectByPrimaryKey(Integer pageWindowId) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE_WINDOW
     *
     * @abatorgenerated Wed Sep 04 10:38:36 CST 2013
     */
    int deleteByExample(CmsPageWindowExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE_WINDOW
     *
     * @abatorgenerated Wed Sep 04 10:38:36 CST 2013
     */
    int deleteByPrimaryKey(Integer pageWindowId) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE_WINDOW
     *
     * @abatorgenerated Wed Sep 04 10:38:36 CST 2013
     */
    int countByExample(CmsPageWindowExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE_WINDOW
     *
     * @abatorgenerated Wed Sep 04 10:38:36 CST 2013
     */
    int updateByExampleSelective(CmsPageWindow record, CmsPageWindowExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table CMS_PAGE_WINDOW
     *
     * @abatorgenerated Wed Sep 04 10:38:36 CST 2013
     */
    int updateByExample(CmsPageWindow record, CmsPageWindowExample example) throws SQLException;

    /**
     * 根据页面ID和窗口ID查询
     */
    public List getPageWindowByPwId(CmsPageWindow record) throws SQLException;

    /**
     * 根据页面ID和窗口ID删除
     */
    public int delPageWindowByPwId(CmsPageWindow record) throws SQLException;

    //返回窗口列表
    public List selectByPageId(CmsPage page) throws SQLException;
//查询窗口绑定窗口的数据

    public List selectWindInWind(String str) throws SQLException;
}