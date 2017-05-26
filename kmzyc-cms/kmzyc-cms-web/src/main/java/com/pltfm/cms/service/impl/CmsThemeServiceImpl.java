package com.pltfm.cms.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.exception.FileUploadException;
import com.kmzyc.commons.page.Page;
import com.pltfm.cms.dao.CmsThemeDAO;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsThemeService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.vobject.CmsTheme;
import com.pltfm.cms.vobject.UploadFile;

@Component(value = "cmsThemeService")
public class CmsThemeServiceImpl implements CmsThemeService {
    @Resource(name = "cmsThemeDAO")
    CmsThemeDAO cmsThemeDao;

    public CmsThemeDAO getCmsThemeDao() {
        return cmsThemeDao;
    }

    public void setCmsThemeDao(CmsThemeDAO cmsThemeDao) {
        this.cmsThemeDao = cmsThemeDao;
    }


    @Override
    public List queryThemeList(CmsTheme cmsTheme) throws SQLException {

        return cmsThemeDao.queryThemeList(cmsTheme);
    }

    @Override
    public Page queryThemeListForPage(CmsTheme cmsTheme, Page page) throws SQLException {

        if (page == null) {
            page = new Page();
        }

        int totalNum = cmsThemeDao.queryThemeCount(cmsTheme);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsTheme.setStartIndex(page.getStartIndex());
            cmsTheme.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsThemeDao.queryThemeListForPage(cmsTheme));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    @Override
    public void add(UploadFile fileImage, CmsTheme cmsTheme) throws Exception {
        if (fileImage != null) {
            String imageName = getImageName(fileImage, cmsTheme.getSiteId());
            if (StringUtils.isNotEmpty(imageName)) {
                cmsTheme.setPicture(imageName);
            }
        }
        cmsThemeDao.insert(cmsTheme);


    }

    @Override
    public void update(UploadFile fileImage, CmsTheme cmsTheme)
            throws Exception {
        if (fileImage != null) {
            String imageName = getImageName(fileImage, cmsTheme.getSiteId());
            if (StringUtils.isNotEmpty(imageName)) {
                cmsTheme.setPicture(imageName);
            }
        }

        cmsThemeDao.update(cmsTheme);


    }

    //获取图片名称
    public String getImageName(UploadFile fileImage, Integer siteId) throws FileUploadException {
        String imagePath = PathConstants.imagePut();
        String imagename = "";
        String path = PathConstants.advPath(siteId);
        FileOperateUtils.checkAndCreateDirs(path);

        path = PathConstants.advTempPath(siteId);
        FileOperateUtils.checkAndCreateDirs(path);
        if (fileImage != null) {
            imagename = FileOperateUtils.upload(fileImage, imagePath);
            //
        }

        return imagename;
    }

    @Override
    public int del(CmsTheme record) throws SQLException {

        return cmsThemeDao.del(record);
    }

    @Override
    public void delDatas(List<Integer> ids) throws SQLException {
        for (int i = 0; i < ids.size(); i++) {
            CmsTheme cmsTheme = new CmsTheme();
            cmsTheme.setThemeId(ids.get(i));
            cmsThemeDao.del(cmsTheme);
        }

    }


}
