package com.pltfm.cms.dao;


import com.pltfm.cms.vobject.CmsTheme;

import java.sql.SQLException;
import java.util.List;

public interface CmsThemeDAO {

    void insert(CmsTheme record) throws SQLException;


    int update(CmsTheme record) throws SQLException;

    CmsTheme selectThemeType(CmsTheme cmsTheme) throws SQLException;

    CmsTheme queryThemeType(CmsTheme cmsTheme) throws SQLException;

    // public CmsTheme queryThemeType(CmsTheme cmsTheme) throws SQLException;
    public List queryThemeListAll(CmsTheme cmsTheme) throws SQLException;

    /*
    CmsTheme selectByPrimaryKey(Integer themeId) throws SQLException;


    int deleteByPrimaryKey(Integer themeId) throws SQLException;

   */
    public List queryThemeList(CmsTheme cmsTheme) throws SQLException;

    public List queryThemeListForPage(CmsTheme cmsTheme) throws SQLException;

    public Integer queryThemeCount(CmsTheme cmsTheme) throws SQLException;

    public int del(CmsTheme record) throws SQLException;
}