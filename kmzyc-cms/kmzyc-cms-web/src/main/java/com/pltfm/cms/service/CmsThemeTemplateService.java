package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.CmsTemplate;
import com.pltfm.cms.vobject.CmsThemeTemplate;

import java.sql.SQLException;
import java.util.List;

public interface CmsThemeTemplateService {
    public Page queryThemeTempList(CmsThemeTemplate cmsThemeTemplate, Page page) throws SQLException;

    public Integer queryThemeTempCount(CmsThemeTemplate cmsThemeTemplate) throws SQLException;

    /**
     * 根据主题Id和模板类型查询模板Id
     *
     * @param cmsThemeTemplate 主题模板
     * @return 主题模板的集合
     */
    public List<CmsThemeTemplate> queryByThemeTemp(CmsThemeTemplate cmsThemeTemplate) throws Exception;


    public void insert(List<CmsTemplate> tempList, CmsThemeTemplate record)
            throws SQLException;

    public int deleteByPrimaryKey(Integer themeTemplateId) throws SQLException;

    public void delBandDatas(List<Integer> ids) throws SQLException;
}
