package com.pltfm.cms.vobject;

import com.kmzyc.commons.page.Page;

public class CmsPageDetail {
    private CmsPage cmsPage;//页面组件
    private Page page;//分页组件
    private CmsTemplate cmsTemplate;//模板组件
    private CmsWindow cmsWindow;//窗口组件
    private String user_Cre;//创造人
    private String user_Mod;//修改人

    public CmsPage getCmsPage() {
        return cmsPage;
    }

    public void setCmsPage(CmsPage cmsPage) {
        this.cmsPage = cmsPage;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public CmsTemplate getCmsTemplate() {
        return cmsTemplate;
    }

    public void setCmsTemplate(CmsTemplate cmsTemplate) {
        this.cmsTemplate = cmsTemplate;
    }

    public CmsWindow getCmsWindow() {
        return cmsWindow;
    }

    public void setCmsWindow(CmsWindow cmsWindow) {
        this.cmsWindow = cmsWindow;
    }

    public String getUser_Cre() {
        return user_Cre;
    }

    public void setUser_Cre(String userCre) {
        user_Cre = userCre;
    }

    public String getUser_Mod() {
        return user_Mod;
    }

    public void setUser_Mod(String userMod) {
        user_Mod = userMod;
    }

}
