package com.pltfm.cms.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.service.CmsLogService;
import com.pltfm.cms.vobject.CmsLog;
import com.pltfm.cms.vobject.CmsPage;


@Component("cmsLogAction")
@Scope("prototype")
public class CmsLogAction extends BaseAction {
    @Resource(name = "cmsLogService")
    private CmsLogService cmsLogService;//cmsLogService接口
    private CmsLog cmsLog;//日志实体
    private CmsPage cmsPage;//页面组件
    private Page page;//分页组件
    private String objectCmsLog;//分页方法对象;
    private static Logger logger = LoggerFactory.getLogger(CmsLogAction.class);


    //分页列表
    public String queryForPage() {
        try {
            page = cmsLogService.searchPageByVo(page, cmsLog);
        } catch (Exception e) {
            logger.error("CmsLogAction.queryForPagen异常:" + e.getMessage(), e);
        }
        return "pageList";
    }





    public CmsLog getCmsLog() {
        return cmsLog;
    }

    public void setCmsLog(CmsLog cmsLog) {
        this.cmsLog = cmsLog;
    }

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

    public String getObjectCmsLog() {
        return objectCmsLog;
    }

    public void setObjectCmsLog(String objectCmsLog) {
        this.objectCmsLog = objectCmsLog;
    }
}
