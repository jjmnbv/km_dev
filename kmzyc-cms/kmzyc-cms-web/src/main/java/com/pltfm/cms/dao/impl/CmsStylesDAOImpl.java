package com.pltfm.cms.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.cms.dao.CmsStylesDAO;
import com.pltfm.cms.vobject.CmsStyles;
import com.pltfm.cms.vobject.CmsStylesExample;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

/**
 * 风格DAO实现类
 *
 * @author cjm
 * @since 2014-8-22
 */
@Component(value = "cmsStylesDAO")
public class CmsStylesDAOImpl implements CmsStylesDAO {
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;


    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:51:51 CST 2014
     */
    public int countByExample(CmsStylesExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("CMS_STYLES.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:51:51 CST 2014
     */
    public int deleteByExample(CmsStylesExample example) throws SQLException {
        int rows = sqlMapClient.delete("CMS_STYLES.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:51:51 CST 2014
     */
    public int deleteByPrimaryKey(Integer stylesId) throws SQLException {
        CmsStyles key = new CmsStyles();
        key.setStylesId(stylesId);
        int rows = sqlMapClient.delete("CMS_STYLES.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:51:51 CST 2014
     */
    public void insert(CmsStyles record) throws SQLException {
        sqlMapClient.insert("CMS_STYLES.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:51:51 CST 2014
     */
    public void insertSelective(CmsStyles record) throws SQLException {
        sqlMapClient.insert("CMS_STYLES.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:51:51 CST 2014
     */
    public List selectByExample(CmsStylesExample example) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_STYLES.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:51:51 CST 2014
     */
    public CmsStyles selectByPrimaryKey(Integer stylesId) throws SQLException {
        CmsStyles key = new CmsStyles();
        key.setStylesId(stylesId);
        CmsStyles record = (CmsStyles) sqlMapClient.queryForObject("CMS_STYLES.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:51:51 CST 2014
     */
    public int updateByExampleSelective(CmsStyles record, CmsStylesExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("CMS_STYLES.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:51:51 CST 2014
     */
    public int updateByExample(CmsStyles record, CmsStylesExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("CMS_STYLES.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:51:51 CST 2014
     */
    public int updateByPrimaryKeySelective(CmsStyles record) throws SQLException {
        int rows = sqlMapClient.update("CMS_STYLES.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:51:51 CST 2014
     */
    public int updateByPrimaryKey(CmsStyles record) throws SQLException {
        int rows = sqlMapClient.update("CMS_STYLES.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table CMS_STYLES
     *
     * @ibatorgenerated Fri Aug 22 10:51:51 CST 2014
     */
    private static class UpdateByExampleParms extends CmsStylesExample {
        private Object record;

        public UpdateByExampleParms(Object record, CmsStylesExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * 风格数据绑定信息总数量
     *
     * @param CmsStyles 风格信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public Integer countByCmsStyles(CmsStyles cmsStyles) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject(
                "CMS_STYLES.abatorgenerated_countByStyles", cmsStyles);
        return count;
    }

    /**
     * 分页查询风格数据绑定信息
     *
     * @param cmsStyles 风格信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public List queryForPage(CmsStyles cmsStyles) throws SQLException {
        return sqlMapClient.queryForList("CMS_STYLES.abatorgenerated_byStyles", cmsStyles);
    }
}