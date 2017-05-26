package com.pltfm.cms.dao;

import com.pltfm.cms.vobject.CmsWindow;
import com.pltfm.cms.vobject.CmsWindowExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 窗口DAO层接口
 *
 * @author cjm
 * @since 2013-9-10
 */
public interface CmsWindowDAO {
    /**
     * 添加窗口信息
     *
     * @param record 窗口信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    Integer insert(CmsWindow record) throws SQLException;

    /***
     *跟据站点和NAME来查询
     * */
    public CmsWindow getWindows(CmsWindow cmsWindow) throws SQLException;

    /**
     * 修改窗口信息
     *
     * @param record 窗口信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    int updateByPrimaryKey(CmsWindow record) throws SQLException;

    /**
     * 动态修改窗口信息
     *
     * @param record 窗口信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    int updateByPrimaryKeySelective(CmsWindow record) throws SQLException;

    /**
     * 按窗口信息条件查询
     *
     * @param example 窗口信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    List selectByExample(CmsWindowExample example) throws SQLException;

    /**
     * 根据窗口主键查询单条窗口基本信息
     *
     * @param windowDataId 窗口主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    CmsWindow selectByPrimaryKey(Integer windowId) throws SQLException;

    /**
     * 按窗口信息条件进行删除
     *
     * @param example 窗口信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    int deleteByExample(CmsWindowExample example) throws SQLException;

    /**
     * 根据窗口主键删除窗口信息
     *
     * @param windowId 窗口主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    int deleteByPrimaryKey(Integer windowId) throws SQLException;

    /**
     * 按窗口信息条件查询总条数
     *
     * @param example 窗口信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    int countByExample(CmsWindowExample example) throws SQLException;

    /**
     * 动态按窗口信息条件进行修改
     *
     * @param record  窗口信息实体
     * @param example 窗口信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    int updateByExampleSelective(CmsWindow record, CmsWindowExample example) throws SQLException;

    /**
     * 按窗口信息按条件进行修改
     *
     * @param record  窗口信息实体
     * @param example 窗口信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    int updateByExample(CmsWindow record, CmsWindowExample example) throws SQLException;

    /**
     * 窗口信息总数量
     *
     * @param cmsWindowData 窗口信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    Integer countByCmsWindowData(CmsWindow cmsWindow) throws SQLException;

    /**
     * 分页查询窗口信息
     *
     * @param cmsWindow 窗口信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    List queryForPage(CmsWindow cmsWindow) throws SQLException;

    /**
     * 根据pageId查询不在page_window表的数据
     */
    public List getWindowByPageId_NotIn(CmsWindow cmsWindow) throws SQLException;

    /**
     * 计算不在page_window数据的总数
     */
    public int countByPageId_NotIn(CmsWindow cmsWindow) throws SQLException;

    /**
     * 根据ID查询已绑定的窗口
     */
    public List getWindowByPageId_In(CmsWindow cmsWindow) throws SQLException;

    /**
     * 根据ID计算已绑定窗口的总数
     */
    public int countByPageId_In(Integer pageId) throws SQLException;

    /**
     * 返回cmsWindow对象
     */
    public List getWindow(CmsWindow cmsWindow) throws SQLException;
}