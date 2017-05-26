package com.pltfm.cms.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.FileUploadException;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.cms.dao.CmsAppManageDAO;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsAppManageService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.vobject.CmsAppManage;
import com.pltfm.cms.vobject.UploadFile;

/**
 * 手机端应用业务逻辑lei
 *
 * @author cjm
 * @since 2014-4-18
 */
@Component(value = "cmsAppManageService")
public class CmsAppManageServiceImpl implements CmsAppManageService {
	private static Logger logger = LoggerFactory.getLogger(CmsAppManageServiceImpl.class);
    /**
     * 手机端应用管理DAO接口
     */
    @Resource(name = "cmsAppManageDAO")
    private CmsAppManageDAO cmsAppManageDAO;

    /**
     * 添加手机端应用
     */
    @Override
    @Transactional
    public void addAppmanage(UploadFile file, CmsAppManage cmsAppManage) throws FileUploadException, SQLException {
        String appPut = PathConstants.appPut();//app存放 目录路径

        String appPath = PathConstants.appPath();//app访问 目录路径
        String name = FileOperateUtils.uploadSource(file, appPut);
        cmsAppManage.setDownload(appPath + "/" + name);

        cmsAppManageDAO.insert(cmsAppManage);
    }

    /**
     * 添加手机端应用 ios
     */
    @Override
    @Transactional
    public void addAppmanage(CmsAppManage cmsAppManage) {
        try {
            cmsAppManageDAO.insert(cmsAppManage);
        } catch (SQLException e) {
        	logger.error("CmsAppManageServiceImpl.addAppmanage异常：" + e.getMessage(), e);
        }
    }

    /**
     * 删除手机端应用
     */
    @Override
    @Transactional
    public Integer deleteByAppmanageId(Integer appmanageId) throws Exception {
        CmsAppManage cmsAppManage = cmsAppManageDAO.selectByPrimaryKey(appmanageId);
        String appPut = PathConstants.appPut(); //app存放 目录路径
        //删除文件
        String fileName = cmsAppManage.getDownload().substring(cmsAppManage.getDownload().lastIndexOf("/") + 1, cmsAppManage.getDownload().length());
        String appPath = appPut + "/" + fileName;
        FileOperateUtils.delFile(appPath);
        return cmsAppManageDAO.deleteByPrimaryKey(appmanageId);
    }

    /**
     * 分页查询手机端应用信息
     *
     * @param cmsAppManage 手机端应用信息实体
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    public Page queryForPage(CmsAppManage cmsAppManage, Page page) throws Exception {
        if (page == null) {
            page = new Page();
        }
        if (cmsAppManage == null) {
            cmsAppManage = new CmsAppManage();
        }
        // 根据条件获取窗口数据信息总条数
        int totalNum = cmsAppManageDAO.countByCmsAppManage(cmsAppManage);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsAppManage.setStartIndex(page.getStartIndex());
            cmsAppManage.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsAppManageDAO.queryForPage(cmsAppManage));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    /**
     * 根据id查询单条手机端应用
     */
    @Override
    public CmsAppManage selectById(Integer appmanageId) throws Exception {
        return cmsAppManageDAO.selectByPrimaryKey(appmanageId);
    }

    /**
     * 修改手机端应用
     */
    @Override
    public void updateAppmanage(UploadFile file, CmsAppManage cmsAppManage)
            throws Exception {
        if (file != null) {

            String appPut = PathConstants.appPut();//app存放 目录路径

            String appPath = PathConstants.appPath();//app访问 目录路径

            //删除文件
            String fileName = cmsAppManage.getDownload().substring(cmsAppManage.getDownload().lastIndexOf("/") + 1, cmsAppManage.getDownload().length());
            String appPathDel = appPut + "/" + fileName;
            FileOperateUtils.delFile(appPathDel);
            FileOperateUtils.checkAndCreateDirs(appPath);

            String name = FileOperateUtils.uploadSource(file, appPut);
            cmsAppManage.setDownload(appPath + "/" + name);
        }
        cmsAppManageDAO.updateByPrimaryKeySelective(cmsAppManage);
    }

    /**
     * 修改手机端应用ios
     */
    @Override
    public void updateAppmanage(CmsAppManage cmsAppManage)
            throws Exception {
        cmsAppManageDAO.updateByPrimaryKeySelective(cmsAppManage);
    }

    /**
     * 查询同一应用名称、版本名、版本号 记录数
     */
    @Override
    public Integer countByNameAndVer(CmsAppManage cmsAppManage) throws ServiceException {
        Integer count = 0;
        try {
            count = cmsAppManageDAO.countByNameAndVer(cmsAppManage);
        } catch (SQLException e) {
            throw new ServiceException(e.getErrorCode(), e.getMessage(), e);
        }
        return count;
    }


    public CmsAppManageDAO getCmsAppManageDAO() {
        return cmsAppManageDAO;
    }

    public void setCmsAppManageDAO(CmsAppManageDAO cmsAppManageDAO) {
        this.cmsAppManageDAO = cmsAppManageDAO;
    }

}
