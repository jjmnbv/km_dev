package com.pltfm.cms.dao;

import com.pltfm.cms.vobject.CmsStyles;
import com.pltfm.cms.vobject.CmsStylesExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 风格DAO
 *
 * @author cjm
 * @since 2014-8-22
 */
public interface CmsStylesDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    int countByExample(CmsStylesExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    int deleteByExample(CmsStylesExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    int deleteByPrimaryKey(Integer stylesId) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    void insert(CmsStyles record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    void insertSelective(CmsStyles record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    List selectByExample(CmsStylesExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    CmsStyles selectByPrimaryKey(Integer stylesId) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    int updateByExampleSelective(CmsStyles record, CmsStylesExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    int updateByExample(CmsStyles record, CmsStylesExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    int updateByPrimaryKeySelective(CmsStyles record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:49:25 CST 2014
     */
    int updateByPrimaryKey(CmsStyles record) throws SQLException;

    /**
     * 分页查询风格数据绑定信息
     *
     * @param cmsStyles 风格信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    public List queryForPage(CmsStyles cmsStyles) throws SQLException;

    /**
     * 风格数据绑定信息总数量
     *
     * @param CmsStyles 风格信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    public Integer countByCmsStyles(CmsStyles msStyles) throws SQLException;
}