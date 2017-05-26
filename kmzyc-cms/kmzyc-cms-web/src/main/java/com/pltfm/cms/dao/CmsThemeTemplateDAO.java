package com.pltfm.cms.dao;


import com.pltfm.cms.vobject.CmsThemeTemplate;

import java.sql.SQLException;
import java.util.List;


public interface CmsThemeTemplateDAO {
    public List queryThemeTempList(CmsThemeTemplate cmsThemeTemplate) throws SQLException;

    public Integer queryThemeTempCount(CmsThemeTemplate cmsThemeTemplate) throws SQLException;

    /**
     * 根据主题Id和模板类型查询模板Id
     *
     * @param cmsThemeTemplate 主题模板
     * @return 主题模板的集合
     */
    public List queryByThemeTemp(CmsThemeTemplate cmsThemeTemplate) throws SQLException;

    /****
     *根据主题Id和模板类型查询页面模板Id
     * */
    public CmsThemeTemplate getByThemeTemp(CmsThemeTemplate cmsThemeTemplate)
            throws SQLException;

    public void insert(CmsThemeTemplate record) throws SQLException;

    /*
    
    int updateByPrimaryKey(Integer record) throws SQLException;

 

  
 */
    public int deleteByPrimaryKey(Integer themeTemplateId) throws SQLException;


}