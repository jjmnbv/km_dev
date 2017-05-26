package com.pltfm.cms.dao;

import com.pltfm.cms.vobject.CmsPageVisualization;
import com.pltfm.cms.vobject.CmsPageVisualizationExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 页面可视化窗口数据绑定DAO
 *
 * @author cjm
 * @since 2014-8-21
 */
public interface CmsPageVisualizationDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_PAGE_VISUALIZATION
     *
     * @ibatorgenerated Thu Aug 21 10:53:06 CST 2014
     */
    int countByExample(CmsPageVisualizationExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_PAGE_VISUALIZATION
     *
     * @ibatorgenerated Thu Aug 21 10:53:06 CST 2014
     */
    int deleteByExample(CmsPageVisualizationExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_PAGE_VISUALIZATION
     *
     * @ibatorgenerated Thu Aug 21 10:53:06 CST 2014
     */
    int deleteByPrimaryKey(Integer pageVisualizationId) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_PAGE_VISUALIZATION
     *
     * @ibatorgenerated Thu Aug 21 10:53:06 CST 2014
     */
    void insert(CmsPageVisualization record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_PAGE_VISUALIZATION
     *
     * @ibatorgenerated Thu Aug 21 10:53:06 CST 2014
     */
    void insertSelective(CmsPageVisualization record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_PAGE_VISUALIZATION
     *
     * @ibatorgenerated Thu Aug 21 10:53:06 CST 2014
     */
    List selectByExample(CmsPageVisualizationExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_PAGE_VISUALIZATION
     *
     * @ibatorgenerated Thu Aug 21 10:53:06 CST 2014
     */
    CmsPageVisualization selectByPrimaryKey(Integer pageVisualizationId) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_PAGE_VISUALIZATION
     *
     * @ibatorgenerated Thu Aug 21 10:53:06 CST 2014
     */
    int updateByExampleSelective(CmsPageVisualization record, CmsPageVisualizationExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_PAGE_VISUALIZATION
     *
     * @ibatorgenerated Thu Aug 21 10:53:06 CST 2014
     */
    int updateByExample(CmsPageVisualization record, CmsPageVisualizationExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_PAGE_VISUALIZATION
     *
     * @ibatorgenerated Thu Aug 21 10:53:06 CST 2014
     */
    int updateByPrimaryKeySelective(CmsPageVisualization record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_PAGE_VISUALIZATION
     *
     * @ibatorgenerated Thu Aug 21 10:53:06 CST 2014
     */
    int updateByPrimaryKey(CmsPageVisualization record) throws SQLException;

    /**
     * 分页查询页面可视化窗口数据绑定信息
     *
     * @param cmsPageVisualization 页面可视化窗口信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    public List queryForPage(CmsPageVisualization cmsPageVisualization) throws SQLException;

    /**
     * 页面可视化窗口数据绑定信息总数量
     *
     * @param cmsWindowData 页面可视化窗口信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    public Integer countByCmsPageVisualization(CmsPageVisualization cmsPageVisualization) throws SQLException;

    /**
     * 根据风格Id查询窗口
     */
    List<CmsPageVisualization> queryByStylesId(Integer stylesId) throws SQLException;
}