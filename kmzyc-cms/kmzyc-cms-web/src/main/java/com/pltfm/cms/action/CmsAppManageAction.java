package com.pltfm.cms.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.cms.service.CmsAppManageService;
import com.pltfm.cms.service.CmsLogService;
import com.pltfm.cms.vobject.CmsAppManage;
import com.pltfm.cms.vobject.CmsLog;
import com.pltfm.cms.vobject.UploadFile;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysUser;

/**
 * 手机端应用Action类
 *
 * @author cjm
 * @since 2014-4-18
 */
@Scope("prototype")
@Component(value = "cmsAppManageAction")
public class CmsAppManageAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(CmsAppManageAction.class);

    /**
     * 手机端应用业务逻辑接口
     */
    @Resource(name = "cmsAppManageService")
    private CmsAppManageService cmsAppManageService;

    private CmsLog cmsLog = new CmsLog();//日志实体

    @Resource(name = "cmsLogService")
    private CmsLogService cmsLogService;//cmsLogService接口

    /**
     * 手机端应用管理实体
     */
    private CmsAppManage cmsAppManage;

    // myFileContentType属性用来封装上传文件的类型
    private UploadFile file;
    // myFileContentType属性用来封装上传文件的类型
    private String resumefileContentType;
    // myFileFileName属性用来封装上传文件的文件名
    private String resumefileFileName;
    // myFile属性用来封装上传的文件
    private File resumefile;

    private Page page;

    private String checks;

    /**
     * 页面id
     */
    private String manageId;

    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();
    /**
     * 删除单条手机端应用
     */
    public String del() {
        try {
            cmsAppManageService.deleteByAppmanageId(Integer.valueOf(manageId));

            SysUser sysuser = (SysUser) session.get("sysUser");
            //日志记录
            cmsLog.setModuleName("手机端应用删除");
            cmsLog.setIds(manageId);
            cmsLog.setConsoleOperator(sysuser.getUserId());

            log.info(cmsLogService.insert(cmsLog));
        } catch (Exception e) {
            logger.error("删除单条手机端应用出现异常", e);
        }
        cmsAppManage = null;
        this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        return this.queryPageList();
    }

    /**
     * 多条删除手机端应用
     */
    public String delAll() {
        try {
            String[] arry = checks.split(",");
            for (int i = 0; i < arry.length; i++) {
                cmsAppManageService.deleteByAppmanageId(Integer.valueOf(arry[i]));
                SysUser sysuser = (SysUser) session.get("sysUser");
                //日志记录
                cmsLog.setModuleName("手机端应用删除");
                cmsLog.setIds(arry[i]);
                cmsLog.setConsoleOperator(sysuser.getUserId());
                log.info(cmsLogService.insert(cmsLog));
            }
        } catch (Exception e) {
            logger.error("多条删除手机端应用出现异常", e);
        }
        this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        return this.queryPageList();
    }


    /**
     * 根据手机端应用查询数据信息
     */
    public String queryPageList() {
        try {
            page = cmsAppManageService.queryForPage(cmsAppManage, page);
            sysUserMap = new HashMap();
            SysUserBean bean = SysUserBean.instance();
            SysUser vo = new SysUser();
            List<SysUser> list = bean.getSysUserList(vo);
            for (SysUser user : list) {
                sysUserMap.put(user.getUserId() + "", user.getUserName());
            }
            return "querySuccess";
        } catch (Exception e) {
            logger.error("根据手机端应用查询数据信息出现异常", e);
            return "queryError";
        }
    }

    /**
     * 跳转新增页面
     */
    public String gotoAddAppManage() {
        return "gotoAddAppManage";
    }

    /**
     * 跳转修改页面
     */
    public String gotoEditAppManage() {
        try {
            cmsAppManage = cmsAppManageService.selectById(Integer.valueOf(manageId));
        } catch (Exception e) {
            logger.error("跳转修改页面出现异常", e);
        }
        return "gotoEditAppManage";
    }

    /**
     * 跳转详细页面
     */
    public String gotoDetailAppManage() {
        try {
            cmsAppManage = cmsAppManageService.selectById(Integer.valueOf(manageId));
        } catch (Exception e) {
            logger.error("跳转详细页面出现异常", e);
        }
        return "gotoDetailAppManage";
    }


    /**
     * 修改手机端应用
     */
    public String editAppManage() {
        Integer siteId = (Integer) session.get("siteId");
        SysUser sysuser = (SysUser) session.get("sysUser");
        try {
            CmsAppManage tempCmsAppManage = cmsAppManageService.selectById(cmsAppManage.getAppmanageId());
            if (!(tempCmsAppManage.getName().equals(cmsAppManage.getName())
                    && tempCmsAppManage.getVersion().equals(cmsAppManage.getVersion())
                    && tempCmsAppManage.getVersioncode().equals(cmsAppManage.getVersioncode()))) {
                Integer existCount = cmsAppManageService.countByNameAndVer(cmsAppManage);
                if (existCount > 0) {
                    this.addActionMessage("修改失败,该应用名称版本名下已存在相同版本号！");
                    cmsAppManage = tempCmsAppManage;
                    return "gotoEditAppManage";
                }
            }

            boolean isIosFlag = true;
            if (resumefile != null) {
                file = new UploadFile(resumefile,resumefileFileName,resumefileContentType);
                isIosFlag = false;
            }
            cmsAppManage.setSiteId(siteId);
            cmsAppManage.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
            cmsAppManage.setModified(sysuser.getUserId());

            if (isIosFlag) {
                cmsAppManageService.updateAppmanage(cmsAppManage);
            } else {
                cmsAppManageService.updateAppmanage(file, cmsAppManage);
            }
        } catch (Exception e) {

            logger.error("修改手机端应用出现异常", e);

        }
        // 日志记录
        cmsLog.setModuleName("手机端应用修改");
        cmsLog.setIds(cmsAppManage.getAppmanageId().toString());
        cmsLog.setConsoleOperator(sysuser.getUserId());

        try {
            log.info(cmsLogService.insert(cmsLog));
        } catch (Exception e) {
            logger.error("修改手机端应用出现异常", e);
        }

        this.addActionMessage(ConfigurationUtil.getString("update.success"));
        cmsAppManage = null;
        return this.queryPageList();
    }

    /**
     * 添加手机端应用
     */
    public String addAppManage() {
        Integer siteId = (Integer) session.get("siteId");
        SysUser sysuser = (SysUser) session.get("sysUser");
        try {
            Integer existCount = cmsAppManageService.countByNameAndVer(cmsAppManage);
            if (existCount > 0) {
                this.addActionMessage("添加失败,该应用名称版本名下已存在相同版本号！");
                cmsAppManage = null;
                return "gotoAddAppManage";
            }

            boolean isIosFlag = true;
            if (resumefile != null) {
                file = new UploadFile(resumefile,resumefileFileName,resumefileContentType);
                isIosFlag = false;
            }

            cmsAppManage.setSiteId(siteId);
            cmsAppManage.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
            cmsAppManage.setModified(sysuser.getUserId());

            if (isIosFlag) {
                cmsAppManageService.addAppmanage(cmsAppManage);
            } else {
                cmsAppManageService.addAppmanage(file, cmsAppManage);
            }
        } catch (Exception e) {
            logger.error("添加手机端应用出现异常", e);
        }

        // 日志记录
        cmsLog.setModuleName("手机端应用添加");
        cmsLog.setIds(cmsAppManage.getAppmanageId().toString());
        cmsLog.setConsoleOperator(sysuser.getUserId());

        try {
            log.info(cmsLogService.insert(cmsLog));
        } catch (Exception e) {
            logger.error("添加手机端应用出现异常", e);
        }

        this.addActionMessage(ConfigurationUtil.getString("add.success"));
        cmsAppManage = null;
        return this.queryPageList();
    }

    public void setCmsAppManageService(CmsAppManageService cmsAppManageService) {
        this.cmsAppManageService = cmsAppManageService;
    }

    public CmsAppManage getCmsAppManage() {
        return cmsAppManage;
    }

    public void setCmsAppManage(CmsAppManage cmsAppManage) {
        this.cmsAppManage = cmsAppManage;
    }

    public UploadFile getFile() {
        return file;
    }

    public void setFile(UploadFile file) {
        this.file = file;
    }

    public String getResumefileContentType() {
        return resumefileContentType;
    }

    public void setResumefileContentType(String resumefileContentType) {
        this.resumefileContentType = resumefileContentType;
    }

    public String getResumefileFileName() {
        return resumefileFileName;
    }

    public void setResumefileFileName(String resumefileFileName) {
        this.resumefileFileName = resumefileFileName;
    }

    public File getResumefile() {
        return resumefile;
    }

    public void setResumefile(File resumefile) {
        this.resumefile = resumefile;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public CmsLogService getCmsLogService() {
        return cmsLogService;
    }

    public void setCmsLogService(CmsLogService cmsLogService) {
        this.cmsLogService = cmsLogService;
    }

    public String getChecks() {
        return checks;
    }

    public void setChecks(String checks) {
        this.checks = checks;
    }

    public CmsAppManageService getCmsAppManageService() {
        return cmsAppManageService;
    }

    public String getManageId() {
        return manageId;
    }

    public void setManageId(String manageId) {
        this.manageId = manageId;
    }

}
