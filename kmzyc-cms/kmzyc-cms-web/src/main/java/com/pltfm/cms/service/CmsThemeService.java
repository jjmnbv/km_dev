package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.CmsTheme;
import com.pltfm.cms.vobject.UploadFile;

import java.sql.SQLException;
import java.util.List;

public interface CmsThemeService {

    public List queryThemeList(CmsTheme cmsTheme) throws SQLException;

    public Page queryThemeListForPage(CmsTheme cmsTheme, Page page) throws SQLException;

    public void add(UploadFile fileImage, CmsTheme cmsTheme) throws Exception;

    public void update(UploadFile fileImage, CmsTheme cmsTheme) throws Exception;

    public int del(CmsTheme record) throws SQLException;

    public void delDatas(List<Integer> ids) throws SQLException;

}
