package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.CmsWindow;

import java.sql.SQLException;
import java.util.List;

/**
 * 窗口业务逻辑层接口
 *
 * @author cjm
 * @since 2013-9-10
 */
public interface CmsWindowService {
    /**
     * 分页查询窗口信息
     *
     * @param cmsWindowData 窗口信息实体
     * @param page          分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    Page queryForPage(CmsWindow cmsWindow, Page page) throws Exception;

    /**
     * 根据窗口信息主键查询单条活动信息
     *
     * @param viewPromotionId 窗口信息主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    CmsWindow selectByPrimaryKey(Integer cmsWindowId) throws Exception;

    /**
     * 添加窗口实体
     */
    public int addCmsWindow(CmsWindow cmsWindow) throws Exception;

    /**
     * 根据Id查询CmsWindow对象
     */
    public CmsWindow getWindowById(Integer id) throws Exception;

    /**
     * 更新CmsWindow对象
     */
    public int updateWindow(CmsWindow cmsWindow) throws Exception;

    /**
     * 根据cmsWindowId删除CmsWindow对象
     */
    public int delWindowById(Integer cmsWindowId) throws Exception;

    /**
     * 删除选中的CmsWindow对象
     */
    public int delAllWindow(String ids) throws Exception;

    /**
     * 返回不在page_window表的记录
     */
    public Page getWindow_NotIn(CmsWindow cmsWindow, Page page, Integer pageId) throws Exception;

    /**
     * 返回已绑定的窗口
     */
    public Page getWindow_In(CmsWindow cmsWindow, Page page, Integer pageId) throws Exception;

    /**
     * 根据name查询
     */
    public List getByName(CmsWindow cmsWindow) throws Exception;

    /**
     * 窗口名字唯一性
     */
    public String checkName(CmsWindow cmsWindow) throws Exception;
}
