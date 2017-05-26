package com.pltfm.cms.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.cms.dao.CmsWindowDAO;
import com.pltfm.cms.vobject.CmsWindow;
import com.pltfm.cms.vobject.CmsWindowExample;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

/**
 * 窗口DAO层接口现实类
 *
 * @author cjm
 * @since 2013-9-10
 */
@Component(value = "cmsWindowDAO")
public class CmsWindowDAOImpl implements CmsWindowDAO {
    /**
     * 数据库操作对象
     */
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;


    /**
     * 添加窗口信息
     *
     * @param record 窗口信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    public Integer insert(CmsWindow record) throws SQLException {
        Integer rows = (Integer) sqlMapClient.insert("CMS_WINDOW.abatorgenerated_insert", record);
        return rows;
    }

    /**
     * 修改窗口信息
     *
     * @param record 窗口信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    public int updateByPrimaryKey(CmsWindow record) throws SQLException {
        int rows = sqlMapClient.update("CMS_WINDOW.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * 动态修改窗口信息
     *
     * @param record 窗口信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    public int updateByPrimaryKeySelective(CmsWindow record) throws SQLException {
        int rows = sqlMapClient.update("CMS_WINDOW.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * 按窗口信息条件查询
     *
     * @param example 窗口信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    public List selectByExample(CmsWindowExample example) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_WINDOW.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * 根据窗口主键查询单条窗口基本信息
     *
     * @param windowDataId 窗口主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    public CmsWindow selectByPrimaryKey(Integer windowId) throws SQLException {
        CmsWindow key = new CmsWindow();
        key.setWindowId(windowId);
        CmsWindow record = (CmsWindow) sqlMapClient.queryForObject("CMS_WINDOW.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * 按窗口信息条件进行删除
     *
     * @param example 窗口信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    public int deleteByExample(CmsWindowExample example) throws SQLException {
        int rows = sqlMapClient.delete("CMS_WINDOW.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * 根据窗口主键删除窗口信息
     *
     * @param windowId 窗口主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    public int deleteByPrimaryKey(Integer windowId) throws SQLException {
        CmsWindow key = new CmsWindow();
        key.setWindowId(windowId);
        int rows = sqlMapClient.delete("CMS_WINDOW.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * 按窗口信息条件查询总条数
     *
     * @param example 窗口信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    public int countByExample(CmsWindowExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("CMS_WINDOW.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * 动态按窗口信息条件进行修改
     *
     * @param record  窗口信息实体
     * @param example 窗口信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    public int updateByExampleSelective(CmsWindow record, CmsWindowExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("CMS_WINDOW.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * 按窗口信息按条件进行修改
     *
     * @param record  窗口信息实体
     * @param example 窗口信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    public int updateByExample(CmsWindow record, CmsWindowExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("CMS_WINDOW.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * 窗口信息总数量
     *
     * @param cmsWindowData 窗口信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public Integer countByCmsWindowData(CmsWindow cmsWindow)
            throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("CMS_WINDOW.abatorgenerated_countByWindow", cmsWindow);
        return count;
    }

    /**
     * 分页查询窗口信息
     *
     * @param cmsWindow 窗口信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public List queryForPage(CmsWindow cmsWindow) throws SQLException {
        return sqlMapClient.queryForList("CMS_WINDOW.abatorgenerated_pageByWindow", cmsWindow);
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table CMS_WINDOW
     * 条件类
     *
     * @abatorgenerated Tue Sep 03 08:56:38 CST 2013
     */
    private static class UpdateByExampleParms extends CmsWindowExample {
        private Object record;

        public UpdateByExampleParms(Object record, CmsWindowExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
    public List getWindowByPageId_NotIn(CmsWindow cmsWindow) throws SQLException {
        // TODO Auto-generated method stub
        return this.sqlMapClient.queryForList("CMS_WINDOW.abatorgenerated_getWindowByPageId_Not_In", cmsWindow);
    }

    @Override
    public int countByPageId_NotIn(CmsWindow cmsWindow) throws SQLException {
        // TODO Auto-generated method stub
        Integer count = (Integer) sqlMapClient.queryForObject("CMS_WINDOW.abatorgenerated_countByPageId_Not_In", cmsWindow);
        return count;
    }

    @Override
    public int countByPageId_In(Integer pageId) throws SQLException {
        // TODO Auto-generated method stub
        Integer count = (Integer) sqlMapClient.queryForObject("CMS_WINDOW.abatorgenerated_countByPageId_In", pageId);
        return count;
    }

    @Override
    public List getWindowByPageId_In(CmsWindow cmsWindow) throws SQLException {
        // TODO Auto-generated method stub
        return this.sqlMapClient.queryForList("CMS_WINDOW.abatorgenerated_getWindowByPageId_In", cmsWindow);
    }

    @Override
    public List getWindow(CmsWindow cmsWindow) throws SQLException {
        // TODO Auto-generated method stub
        return this.sqlMapClient.queryForList("CMS_WINDOW.abatorgenerated_getWindow", cmsWindow);
    }

    @Override
    /***
     *跟据站点和NAME来查询
     * */
    public CmsWindow getWindows(CmsWindow cmsWindow) throws SQLException {
        // TODO Auto-generated method stub
        CmsWindow record = (CmsWindow) sqlMapClient.queryForObject("CMS_WINDOW.abatorgenerated_getWindow", cmsWindow);
        return record;
    }

}