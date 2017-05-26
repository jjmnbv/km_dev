package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.CmsTemplate;

import java.sql.SQLException;
import java.util.List;

public interface CmsTemplateServce {
    /**
     * 查询所有模板
     */
    public List queryTemplateList() throws Exception;

    /**
     * 分页查找
     */
    public Page queryForPage(CmsTemplate cmsTemplate, Page page) throws Exception;

    /**
     * 添加模板
     */
    public void addTemplate(CmsTemplate cmsTemplate) throws Exception;

    /**
     * 根据id查询某个模板
     */
    public CmsTemplate getTemplateById(Integer templateId) throws Exception;

    /**
     * 修改模板
     */
    public void updateTemplate(CmsTemplate cmsTemplate) throws Exception;

    /**
     * 根据id删除模板
     */
    public void deleteTemplateById(Integer templateId) throws Exception;

    /**
     * 删除选中的模板
     */
    public void deleteAll(String ids) throws Exception;

    /**
     * 根据name查询
     */
    public List getByName(CmsTemplate cmsTemplate) throws Exception;

    /**
     * 名字校验
     */
    public String check(CmsTemplate cmsTemplate) throws Exception;

    /**
     * 通过站点以及模板类型查询模板信息
     */
    public List selectBySiteIdType(CmsTemplate cmsTemplate) throws Exception;

    /*
     * 查询包含in的指定模板数据
     */

    public List querySeletedTemplateList(String sql) throws Exception;

    public Page queryTemplateFilterBandTheme(CmsTemplate cmsTemplate, Page page)
            throws Exception;

    public Page queryVarTemplateList(CmsTemplate cmsTemplate, Page page)
            throws SQLException;

    public Page queryTemplateFilterBandWindow(CmsTemplate cmsTemplate, Page page)
            throws Exception;

    public Page queryBandWindowList(CmsTemplate cmsTemplate, Page page) throws Exception;

}

