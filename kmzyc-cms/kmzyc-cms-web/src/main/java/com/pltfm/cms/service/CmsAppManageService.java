package com.pltfm.cms.service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.CmsAppManage;
import com.pltfm.cms.vobject.UploadFile;

/**
 * 手机端应用业务逻辑接口
 *
 * @author cjm
 * @since 2014-4-18
 */
public interface CmsAppManageService {
    /**
     * 分页查询手机端应用信息
     *
     * @param cmsAppManage 手机端应用信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    public Page queryForPage(CmsAppManage cmsAppManage, Page page) throws Exception;

    /**
     * 删除手机端应用
     */
    public Integer deleteByAppmanageId(Integer appmanageId) throws Exception;

    /**
     * 添加手机端应用
     */
    public void addAppmanage(UploadFile file, CmsAppManage cmsAppManage) throws Exception;

    public void addAppmanage(CmsAppManage cmsAppManage) throws Exception;

    public CmsAppManage selectById(Integer appmanageId) throws Exception;

    public void updateAppmanage(UploadFile file, CmsAppManage cmsAppManage) throws Exception;

    public void updateAppmanage(CmsAppManage cmsAppManage) throws Exception;

    /**
     * 查询同一应用名称、版本名、版本号 记录数
     */
    public Integer countByNameAndVer(CmsAppManage cmsAppManage) throws ServiceException;

}
