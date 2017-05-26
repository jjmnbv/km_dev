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

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.cms.service.CmsStylesService;
import com.pltfm.cms.vobject.CmsStyles;
import com.pltfm.cms.vobject.UploadFile;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysUser;

/**
 * 风格Action类
 *
 * @author cjm
 * @since 2014-8-22
 */
@Component("cmsStylesAction")
@Scope(value = "prototype")
public class CmsStylesAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(CmsStylesAction.class);

    /**
     * 风格业务逻辑接口
     */
    @Resource(name = "cmsStylesService")
    private CmsStylesService cmsStylesService;

    /**
     * 风格实体
     */
    private CmsStyles cmsStyles;

    /**
     * 风格Id
     */
    private Integer stylesId;

    /**
     * 分页控件
     */
    private Page page;

    UploadFile fileImage;//主题图片
    // myFile属性用来封装上传的文件
    private File resumefile;

    // myFileContentType属性用来封装上传文件的类型  
    private String resumefileContentType;

    // myFileFileName属性用来封装上传文件的文件名  
    private String resumefileFileName;

    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();
    /**
     * 根据页面Id查询该页面所有窗口
     */
    public String queryForPage() {
        Integer siteId = (Integer) session.get("siteId");
        if (null == cmsStyles) {
            cmsStyles = new CmsStyles();
        }
        cmsStyles.setSiteId(siteId);
        try {
            page = cmsStylesService.queryForPage(cmsStyles, page);
            //获取对应的管理员
            sysUserMap = new HashMap();
            SysUserBean bean = SysUserBean.instance();
            SysUser vo = new SysUser();
            List<SysUser> list = bean.getSysUserList(vo);
            for (SysUser user : list) {
                sysUserMap.put(user.getUserId() + "", user.getUserName());
            }
        } catch (Exception e) {
            logger.error("CmsStylesAction.queryForPage异常：" + e.getMessage(), e);
        }


        return "queryForPage";
    }

    /**
     * 跳转添加风格
     */
    public String gotoStylesAdd() {
        return "gotoStylesAdd";
    }

    /**
     * 添加风格
     */
    public String add() {
        SysUser sysuser = (SysUser) session.get("sysUser");
        Integer siteId = (Integer) session.get("siteId");
        if (cmsStyles == null) {
            cmsStyles = new CmsStyles();
        }
        if (resumefile != null) {
            fileImage = new UploadFile(resumefile,resumefileFileName,resumefileContentType);
        }
        cmsStyles.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
        cmsStyles.setCreated(sysuser.getUserId());
        cmsStyles.setSiteId(siteId);
        try {
            cmsStylesService.addStyles(cmsStyles, fileImage);
            this.addActionMessage(ConfigurationUtil.getString("add.success"));
        } catch (Exception e) {
            this.addActionError(ConfigurationUtil.getString("add.fail"));
            logger.error("CmsStylesAction.add异常：" + e.getMessage(), e);
        }
        return this.queryForPage();
    }

    /**
     * 跳转修改风格
     */
    public String gotoStylesUpdate() {
        try {
            cmsStyles = cmsStylesService.queryByCmsStylesId(stylesId);
        } catch (Exception e) {
            logger.error("CmsStylesAction.gotoStylesUpdate异常：" + e.getMessage(), e);
        }
        return "gotoStylesUpdate";
    }

    /**
     * 修改风格
     */
    public String update() {
        SysUser sysuser = (SysUser) session.get("sysUser");
        if (cmsStyles == null) {
            cmsStyles = new CmsStyles();
        }
        if (resumefile != null) {
            fileImage = new UploadFile(resumefile,resumefileFileName,resumefileContentType);
        }
        cmsStyles.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
        cmsStyles.setModified(sysuser.getUserId());
        try {
            cmsStylesService.updateStyles(cmsStyles, fileImage);
            this.addActionMessage(ConfigurationUtil.getString("update.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("update.fail"));
            logger.error("CmsStylesAction.update异常：" + e.getMessage(), e);
        }
        return this.queryForPage();
    }

    /**
     * 删除风格
     */
    public String delStyles() {
        try {
            cmsStylesService.delStyles(stylesId);
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            logger.error("CmsStylesAction.delStyles异常：" + e.getMessage(), e);
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
        }
        return this.queryForPage();
    }

    public CmsStylesService getCmsStylesService() {
        return cmsStylesService;
    }

    public void setCmsStylesService(CmsStylesService cmsStylesService) {
        this.cmsStylesService = cmsStylesService;
    }

    public CmsStyles getCmsStyles() {
        return cmsStyles;
    }

    public void setCmsStyles(CmsStyles cmsStyles) {
        this.cmsStyles = cmsStyles;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Integer getStylesId() {
        return stylesId;
    }

    public void setStylesId(Integer stylesId) {
        this.stylesId = stylesId;
    }

    public UploadFile getFileImage() {
        return fileImage;
    }

    public void setFileImage(UploadFile fileImage) {
        this.fileImage = fileImage;
    }

    public File getResumefile() {
        return resumefile;
    }

    public void setResumefile(File resumefile) {
        this.resumefile = resumefile;
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
}
