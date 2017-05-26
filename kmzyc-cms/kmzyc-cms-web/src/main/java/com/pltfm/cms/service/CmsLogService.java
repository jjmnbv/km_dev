package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.CmsLog;

import java.sql.SQLException;
import java.util.List;

public interface CmsLogService {

    Page searchPageByVo(Page pageParam, CmsLog record) throws Exception;

    Integer delete(List<Integer> cmsLogIds) throws SQLException;

    Integer insert(CmsLog record) throws Exception;

    CmsLog selectByPrimaryKey(Integer cmsLogId) throws SQLException;

    /***
     *
     * 记录绑定窗口、数据
     * id id值
     * */

    Integer add(CmsLog cms, int windowId) throws Exception;

    /***
     *
     * 记录删除窗口、数据
     * id id值
     * */
    Integer del(CmsLog cms, int windowId) throws Exception;

    /***
     *
     * 记录修改数据
     * */

    Integer update(Object cms, int windowId) throws Exception;
}
