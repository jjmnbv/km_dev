package com.pltfm.cms.service;

import com.pltfm.cms.vobject.CmsPageWindow;
import com.pltfm.cms.vobject.CmsPageWindowQry;

import java.sql.SQLException;
import java.util.List;

public interface CmsPageWindowService {
    //查询窗口列表
    public List queryWindowList(CmsPageWindowQry qry) throws SQLException;

    //插入页面窗口数据
    public void add(CmsPageWindow cmsPageWindow) throws Exception;

    //插入所有页面窗口对应数据
    public void addAll(String ids, CmsPageWindow cmsPageWindow) throws Exception;

    /**
     * 根据windowId查询所有的记录
     */
    public List queryByWindowId(CmsPageWindow cmsPageWindow) throws Exception;

    /**
     * 删除cmsPageWindow记录
     */
    public int delRecord(CmsPageWindow cmsPageWindow) throws Exception;

    /**
     * 删除所选的数据
     */
    public int delAllRecord(String ids, CmsPageWindow cmsPageWindow) throws Exception;

//查询窗口绑定窗口的数据

    public List selectWindInWind(String str) throws SQLException;
}
