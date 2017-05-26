package com.pltfm.cms.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.service.CmsSiteDataService;
import com.pltfm.cms.service.CmsUserSiteService;
import com.pltfm.cms.vobject.CmsUserSite;
import com.pltfm.cms.vobject.CmsUserSiteShow;

/**
 * 用户与站点Action类
 *
 * @author cjm
 * @since 2013-11-19
 */
@Component(value = "cmsUserSiteAction")
@Scope(value = "prototype")
public class CmsUserSiteAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(CmsUserSiteAction.class);

    /**
     * 分页对象
     */
    private Page page;

    /**
     * 用户与站点
     */
    private CmsUserSite cmsUserSite;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 用户站点
     */
    private CmsUserSiteShow cmsUserSiteShow;


    /**
     * 用户与站点业务逻辑接口
     */
    @Resource(name = "cmsUserSiteService")
    private CmsUserSiteService cmsUserSiteService;


    @Resource(name = "cmsSiteDataService")
    private CmsSiteDataService cmsSiteDataService;

    /**
     * 分页列表
     */
    public String queryForPage() {
        try {
            page = cmsUserSiteService.queryForPage(cmsUserSite, page);
        } catch (Exception e) {
            logger.error("授权管理--数据授权--分页查询用户与站点信息出现异常", e);
        }
        return "queryForPage";
    }


    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public CmsUserSite getCmsUserSite() {
        return cmsUserSite;
    }

    public void setCmsUserSite(CmsUserSite cmsUserSite) {
        this.cmsUserSite = cmsUserSite;
    }

    public CmsUserSiteService getCmsUserSiteService() {
        return cmsUserSiteService;
    }

    public void setCmsUserSiteService(CmsUserSiteService cmsUserSiteService) {
        this.cmsUserSiteService = cmsUserSiteService;
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

    public CmsUserSiteShow getCmsUserSiteShow() {
        return cmsUserSiteShow;
    }

    public void setCmsUserSiteShow(CmsUserSiteShow cmsUserSiteShow) {
        this.cmsUserSiteShow = cmsUserSiteShow;
    }


}
