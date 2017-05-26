package com.pltfm.cms.action;

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
import com.pltfm.app.util.Token;
import com.pltfm.cms.service.CmsAdvService;
import com.pltfm.cms.service.CmsPageService;
import com.pltfm.cms.service.CmsPromotionService;
import com.pltfm.cms.service.CmsSiteDataService;
import com.pltfm.cms.service.CmsUserSiteService;
import com.pltfm.cms.service.CmsWindowService;
import com.pltfm.cms.vobject.CmsAdv;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsPromotionTask;
import com.pltfm.cms.vobject.CmsSite;
import com.pltfm.cms.vobject.CmsSiteData;
import com.pltfm.cms.vobject.CmsUserSite;
import com.pltfm.cms.vobject.CmsUserSiteShow;
import com.pltfm.cms.vobject.CmsWindow;
import com.pltfm.sys.model.SysUser;

/**
 * 站点数据Action类
 *
 * @author cjm
 * @since 2013-11-19
 */
@Component("cmsSiteDataAction")
@Scope(value = "prototype")
public class CmsSiteDataAction extends BaseAction {
    Logger logger= LoggerFactory.getLogger(CmsSiteDataAction.class);
    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();
    /**
     * 站点数据业务逻辑接口
     */
    @Resource(name = "cmsSiteDataService")
    CmsSiteDataService cmsSiteDataService;

    /**
     * 页面业务逻辑接口
     */
    @Resource(name = "cmsPageService")
    CmsPageService cmsPageService;

    /**
     * 窗口业务逻辑层接口
     */
    @Resource(name = "cmsWindowService")
    CmsWindowService cmsWindowService;

    /**
     * 广告业务逻辑接口
     */
    @Resource(name = "cmsAdvService")
    CmsAdvService cmsAdvService;

    /**
     * 活动业务逻辑接口
     */
    @Resource(name = "cmsPromotionService")
    CmsPromotionService cmsPromotionService;
    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 数据集合
     */
    private List<Integer> dataIds;

    /**
     * 删除Id集合
     */
    private List<Integer> userSiteDataIds;

    /**
     * 站点Id
     */
    private Integer siteId;

    /**
     * 数据类型
     */
    private Integer dataType;

    /**
     * 数据Id
     */
    private Integer dataId;

    /**
     * 分页对象
     */
    private Page page;

    private CmsSiteData cmsSiteData;

    /**
     * 用户站点业务逻辑接口
     */
    @Resource(name = "cmsUserSiteService")
    private CmsUserSiteService cmsUserSiteService;

    private CmsUserSiteShow cmsUserSiteShow;

    /**
     * 删除站点数据
     */
    @Token
    public String deleteData() {
        try {
            cmsSiteDataService.delCmsSiteData(userSiteDataIds);
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsSiteDataAction.deleteData异常：" + e.getMessage(), e);
        }
        return this.warrant();
    }

    /**
     * 跳转授权页面
     */
    public String warrant() {
        try {
            if (cmsSiteData == null) {
                cmsSiteData = new CmsSiteData();
            }

            cmsSiteData.setUserId(userId);
            cmsSiteData.setSiteId(siteId);
            page = cmsSiteDataService.queryForPage(cmsSiteData, page);
        } catch (Exception e) {
            logger.error("CmsSiteDataAction.warrant异常：" + e.getMessage(), e);
        }
        return "warrant";
    }

    public String detailData() {
        try {
            CmsUserSite cmsUserSite = new CmsUserSite();
            cmsUserSite.setUserId(userId);
            page = cmsUserSiteService.queryForPage(cmsUserSite, page);
            if (page.getDataList().size() > 0) {
                cmsUserSiteShow = (CmsUserSiteShow) page.getDataList().get(0);
            }
            for (CmsSite cs : cmsUserSiteShow.getSiteList()) {
                if (siteId.equals(cs.getSiteId())) {
                    cs.setIsCss(1);
                    break;
                }
            }

            if (cmsSiteData == null) {
                cmsSiteData = new CmsSiteData();
            }

            cmsSiteData.setUserId(userId);
            cmsSiteData.setSiteId(siteId);
            page = cmsSiteDataService.queryForPage(cmsSiteData, page);
        } catch (Exception e) {
            logger.error("CmsSiteDataAction.detailData异常：" + e.getMessage(), e);
        }
        return "detailData";
    }

    /**
     * 数据授权
     */
    @Token
    public String add() {
        SysUser sysuser = (SysUser) session.get("sysUser");
        try {
            cmsSiteDataService.addCmsSiteData(dataIds, dataType, userId, siteId, sysuser.getUserId());
            this.addActionMessage(ConfigurationUtil.getString("add.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("add.fail"));
            logger.error("CmsSiteDataAction.add异常：" + e.getMessage(), e);
        }
        return this.warrant();
    }

    /**
     * 用户站点数据详细
     */
    public String detail() {

        try {
            CmsUserSite cmsUserSite = new CmsUserSite();
            cmsUserSite.setUserId(userId);
            page = cmsUserSiteService.queryForPage(cmsUserSite, page);
            if (page.getDataList().size() > 0) {
                cmsUserSiteShow = (CmsUserSiteShow) page.getDataList().get(0);
            }
            cmsUserSiteShow.getSiteList().get(0).setIsCss(1);
            CmsSiteData cmsSiteData = new CmsSiteData();
            cmsSiteData.setUserId(userId);
            cmsSiteData.setSiteId(cmsUserSiteShow.getSiteList().get(0).getSiteId());
            page = cmsSiteDataService.queryForPage(cmsSiteData, page);
        } catch (Exception e) {
            logger.error("CmsSiteDataAction.detail异常：" + e.getMessage(), e);
        }
        return "detail";
    }

    /**
     * 数据加载
     */
    public void queryData() {
        Object obj = null;
        try {
            switch (dataType) {
                case 1:
                    CmsPage cmsPage = cmsPageService.getCmsPageById(dataId);
                    if (cmsPage.getModifyDate() != null) {
                        cmsPage.setModifyDateStr(DateTimeUtils.getDateTime(cmsPage.getModifyDate()));
                    }
                    obj = cmsPage;
                    break;
                case 2:
                    CmsAdv cmsAdv = cmsAdvService.byid(dataId);
                    if (cmsAdv.getBeginTime() != null) {
                        cmsAdv.setBeginTimeStr(DateTimeUtils.getDateTime(cmsAdv.getBeginTime()));
                    }

                    if (cmsAdv.getEndTime() != null) {
                        cmsAdv.setEndTimeStr(DateTimeUtils.getDateTime(cmsAdv.getEndTime()));
                    }
                    obj = cmsAdv;
                    break;
                case 3:
                    CmsPromotionTask cmsPromotionTask = cmsPromotionService.cmspByIdAndSiteId(dataId, siteId);
                    if (cmsPromotionTask.getBeginTime() != null) {
                        cmsPromotionTask.setBeginTimeStr(DateTimeUtils.getDateTime(cmsPromotionTask.getBeginTime()));
                    }

                    if (cmsPromotionTask.getEndTime() != null) {
                        cmsPromotionTask.setEndTimeStr(DateTimeUtils.getDateTime(cmsPromotionTask.getEndTime()));
                    }
                    obj = cmsPromotionTask;
                    break;
                case 4:
                    CmsWindow cmsWindow = cmsWindowService.selectByPrimaryKey(dataId);
                    obj = cmsWindow;
                    break;
                default:
                    throw new IllegalArgumentException("dataType err");
            }
        } catch (Exception e) {
            logger.error("queryData fail",e);
        }
        super.writeJson(obj);
    }


    public CmsSiteDataService getCmsSiteDataService() {
        return cmsSiteDataService;
    }

    public void setCmsSiteDataService(CmsSiteDataService cmsSiteDataService) {
        this.cmsSiteDataService = cmsSiteDataService;
    }


    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getSiteId() {
        return siteId;
    }


    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public CmsPageService getCmsPageService() {
        return cmsPageService;
    }

    public void setCmsPageService(CmsPageService cmsPageService) {
        this.cmsPageService = cmsPageService;
    }

    public CmsAdvService getCmsAdvService() {
        return cmsAdvService;
    }

    public void setCmsAdvService(CmsAdvService cmsAdvService) {
        this.cmsAdvService = cmsAdvService;
    }

    public CmsPromotionService getCmsPromotionService() {
        return cmsPromotionService;
    }

    public void setCmsPromotionService(CmsPromotionService cmsPromotionService) {
        this.cmsPromotionService = cmsPromotionService;
    }

    public List<Integer> getDataIds() {
        return dataIds;
    }

    public void setDataIds(List<Integer> dataIds) {
        this.dataIds = dataIds;
    }

    public List<Integer> getUserSiteDataIds() {
        return userSiteDataIds;
    }

    public void setUserSiteDataIds(List<Integer> userSiteDataIds) {
        this.userSiteDataIds = userSiteDataIds;
    }

    public CmsUserSiteService getCmsUserSiteService() {
        return cmsUserSiteService;
    }

    public void setCmsUserSiteService(CmsUserSiteService cmsUserSiteService) {
        this.cmsUserSiteService = cmsUserSiteService;
    }

    public CmsUserSiteShow getCmsUserSiteShow() {
        return cmsUserSiteShow;
    }

    public void setCmsUserSiteShow(CmsUserSiteShow cmsUserSiteShow) {
        this.cmsUserSiteShow = cmsUserSiteShow;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public CmsSiteData getCmsSiteData() {
        return cmsSiteData;
    }

    public void setCmsSiteData(CmsSiteData cmsSiteData) {
        this.cmsSiteData = cmsSiteData;
    }

}
