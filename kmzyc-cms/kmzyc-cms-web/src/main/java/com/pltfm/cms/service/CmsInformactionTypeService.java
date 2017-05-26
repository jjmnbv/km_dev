package com.pltfm.cms.service;


import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.CmsInformationType;

import java.sql.SQLException;

public interface CmsInformactionTypeService {

    /**
     * 分页查询资讯数据信息
     *
     * @param information 资讯类别数据信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    Page queryForPage(CmsInformationType information, Page page) throws Exception;

    /**
     * 添加资讯数据信息
     *
     * @param information 资讯类别数据信息实体
     * @throws Exception 异常
     */
    void add(CmsInformationType information) throws Exception;

    /**
     * 添加资讯数据信息
     *
     * @param id 资讯类别数据信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    CmsInformationType byid(Integer id) throws Exception;


    Integer update(CmsInformationType information) throws Exception;

    Integer delete(Integer id) throws Exception;

    /*  删除前判断  */
    Integer byInforId(Integer id) throws Exception;

    Integer countByName(CmsInformationType information) throws Exception;

    CmsInformationType queryInfoTypeByTypeCode(CmsInformationType cmsInformationType) throws SQLException;


}


