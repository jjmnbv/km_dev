package com.pltfm.cms.parse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pltfm.cms.service.CmsPageService;
import com.pltfm.cms.vobject.CmsPage;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

/**
 * 定时发布类
 *
 * @author cjm
 * @since 2013-10-12
 */
@Component(value = "timingPublish")
public class TimingPublish {
	private static Logger logger = LoggerFactory.getLogger(TimingPublish.class);
    /**
     * 页面业务逻辑接口
     */
    @Resource(name = "cmsPageService")
    private CmsPageService cmsPageService;

    @Resource(name = "pagePublish")
    private PagePublish pagePublish;

    /**
     * 调度器方法
     */
    public void publish() {
        List<CmsPage> cmsPageList = null;
        try {
            CmsPage page = new CmsPage();
            page.setPublicType(1);
            cmsPageList = cmsPageService.selectByPrimaryPublishType(page);
            if (cmsPageList != null) {
                for (CmsPage cmsPage : cmsPageList) {
                    pagePublish.rankPublish(cmsPage);
                }
            }
        } catch (Exception e) {
        	logger.error("TimingPublish.publish异常：" + e.getMessage(), e);
        }

    }

    public CmsPageService getCmsPageService() {
        return cmsPageService;
    }

    public void setCmsPageService(CmsPageService cmsPageService) {
        this.cmsPageService = cmsPageService;
    }

    public PagePublish getPagePublish() {
        return pagePublish;
    }

    public void setPagePublish(PagePublish pagePublish) {
        this.pagePublish = pagePublish;
    }
}
