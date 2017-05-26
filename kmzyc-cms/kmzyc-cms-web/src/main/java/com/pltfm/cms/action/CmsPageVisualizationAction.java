package com.pltfm.cms.action;

import java.io.File;
import java.util.ArrayList;
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
import com.pltfm.app.util.Token;
import com.pltfm.cms.service.CmsPageVisualizationService;
import com.pltfm.cms.vobject.CmsPageVisualization;
import com.pltfm.cms.vobject.CmsWindow;
import com.pltfm.cms.vobject.UploadFile;
import com.pltfm.sys.model.SysUser;

/**
 * 页面可视化数据绑定Action类
 *
 * @author cjm
 * @since 2014-8-21
 */
@Component("cmsPageVisualizationAction")
@Scope(value = "prototype")
public class CmsPageVisualizationAction extends BaseAction {
    /**
     * 风格id
     */
    private Integer stylesId;

    /**
     * 可视化Id
     */
    private Integer visualizationId;

    /**
     * 分页对象
     */
    private Page page;


    /**
     * 窗口实体类
     */
    private CmsWindow cmsWindow;
    /**
     * 封装文件集
     */
    private List<UploadFile> uploadFileList;

    /**
     * 上传文件集
     */
    private List<File> files;
    /**
     * 文件类型
     */
    private List<String> filesContentType;
    /**
     * 文件名
     */
    private List<String> filesFileName;

    /**
     * 窗口数据集
     */
    private List<CmsPageVisualization> cmsPageVisualizations;

    /**
     * 页面可视化窗口数据绑定业务逻辑接口
     */
    @Resource(name = "cmsPageVisualizationService")
    private CmsPageVisualizationService cmsPageVisualizationService;
    //日志
    private static Logger logger = LoggerFactory.getLogger(CmsPageVisualizationAction.class);
    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();
    /**
     * 根据页面Id查询该页面所有窗口
     */
    public String queryForPage() {
        Integer siteId = (Integer) session.get("siteId");
        CmsPageVisualization cmsPageVisualization = new CmsPageVisualization();
        cmsPageVisualization.setSiteId(siteId);
        cmsPageVisualization.setStylesId(stylesId);
        try {
            page = cmsPageVisualizationService.queryForPage(cmsPageVisualization, page);
        } catch (Exception e) {
            logger.error("CmsPageVisualizationAction.queryForPage报错：" + e);
        }
        return "queryForPage";
    }

    public String delVisualizationAction() {
        try {
            cmsPageVisualizationService.delCmsPageVisualization(visualizationId);
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            logger.error("CmsPageVisualizationAction.delVisualizationAction报错：" + e);
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
        }
        return this.queryForPage();
    }

    public String gotoAddVisualization() {
        return "gotoAddVisualization";
    }

    /**
     * 保存自定义数据
     */
    @Token
    public String saveDate() {
        try {
            SysUser sysuser = (SysUser) session.get("sysUser");
            uploadFileList = new ArrayList<UploadFile>();
            UploadFile uploadFile = null;
            File file = null;
            //将文件封装为UploadFile对象
            if (files != null) {
                for (int i = 0; i < files.size(); i++) {
                    file = files.get(i);
                    uploadFile = new UploadFile(file,this.filesFileName.get(i),this.filesContentType.get(i));
                    //将封装好的UploadFile对象放置uploadFileList集合
                    uploadFileList.add(uploadFile);


                }
            }

            Integer siteId = (Integer) session.get("siteId");
            cmsPageVisualizationService.addCmsPageVisualization(cmsPageVisualizations, uploadFileList, stylesId, sysuser.getUserId(), siteId);
            this.addActionMessage(ConfigurationUtil.getString("add.success"));
        } catch (Exception e) {
            logger.error("CmsPageVisualizationAction.saveDate报错：" + e);
            this.addActionError(ConfigurationUtil.getString("add.fail"));
        }
        return this.queryForPage();
    }


    public CmsWindow getCmsWindow() {
        return cmsWindow;
    }

    public void setCmsWindow(CmsWindow cmsWindow) {
        this.cmsWindow = cmsWindow;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<String> getFilesContentType() {
        return filesContentType;
    }

    public void setFilesContentType(List<String> filesContentType) {
        this.filesContentType = filesContentType;
    }

    public List<String> getFilesFileName() {
        return filesFileName;
    }

    public void setFilesFileName(List<String> filesFileName) {
        this.filesFileName = filesFileName;
    }

    public List<CmsPageVisualization> getCmsPageVisualizations() {
        return cmsPageVisualizations;
    }

    public void setCmsPageVisualizations(
            List<CmsPageVisualization> cmsPageVisualizations) {
        this.cmsPageVisualizations = cmsPageVisualizations;
    }

    public CmsPageVisualizationService getCmsPageVisualizationService() {
        return cmsPageVisualizationService;
    }

    public void setCmsPageVisualizationService(
            CmsPageVisualizationService cmsPageVisualizationService) {
        this.cmsPageVisualizationService = cmsPageVisualizationService;
    }

    public Integer getStylesId() {
        return stylesId;
    }

    public void setStylesId(Integer stylesId) {
        this.stylesId = stylesId;
    }

    public Integer getVisualizationId() {
        return visualizationId;
    }

    public void setVisualizationId(Integer visualizationId) {
        this.visualizationId = visualizationId;
    }


}
