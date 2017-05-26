package com.pltfm.app.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.service.CategoryService;
import com.pltfm.app.vobject.Category;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.vobject.CmsWindowData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Component(value = "categoryAction")
@Scope(value = "prototype")
public class CategoryAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(CategoryAction.class);

    @Resource(name = "categoryService")
    private CategoryService categoryService;
    /**
     * 窗口数据业务逻辑层接口
     */
    @Resource(name = "cmsWindowDataService")
    private CmsWindowDataService cmsWindowDataService;
    /**
     * 按层级查询全部的类目
     */
    private String nodes;

    /**
     * 选中的checkbox
     */
    private String checkeds;

    /**
     * 数据类型
     */
    private Integer dataType;


	/*
	 * 渠道
	 */
//	private String channel;

    /**
     * 查询条件渠道
     */
//	private String channelQuery;

    private String checkDataIdS;


    /**
     * 窗口Id
     */
    private Integer windowId;

    /**
     * 页面Id
     */
    private Integer pageId;
    /**
     * 角色区分
     */
    private Integer adminType;

    /**
     * 跳转排行榜产品数据列表页面
     */
    public String rankingList() {
        return "rankingList";
    }

    /**
     * 运营类目列表
     */
    public String findAllCategory() {
        try {
            Map session = ActionContext.getContext().getSession();
//			CmsSite cmsSite = null;
//			if(session!=null&&session.get("siteId")!=null){
//				 cmsSite = PathConstants.getCmsSite((Integer)session.get("siteId"));
//			}

            Category c = new Category();
            c.setParentId(0);
            c.setIsPhy(2);
            c.setStatus(1);

            //当channel为空时从站点中去渠道数据
//			if(channel== null || channel.isEmpty()){
//				channel = cmsSite.getChannel();
//			}

            //页面的渠道查询条件
//			channelQuery = cmsSite.getChannel();

//			c.setChannel(StringUtil.packChannel(channel));
            List<Category> list = categoryService.queryCategoryPhyList(c);

            nodes = JSON.toJSONString(list);
            CmsWindowData cmsWindowData = new CmsWindowData();
            cmsWindowData.setWindowId(windowId);
            cmsWindowData.setDataType(2);
            cmsWindowData.setSiteId((Integer) session.get("siteId"));
            checkDataIdS = cmsWindowDataService.queryByWindowData(cmsWindowData);
        } catch (Exception e) {
        	logger.error("CategoryAction.findAllCategory异常：" + e.getMessage(), e);
        }
        return Action.SUCCESS;
    }

    /**
     * 产品排行榜所需物理类目
     */
    public String rankingListCategory() {
        try {
            Category c = new Category();
            c.setParentId(0);
            c.setIsPhy(1);
            c.setStatus(1);
            List<Category> list = categoryService.queryCategoryList(c);

            nodes = JSON.toJSONString(list);

            CmsWindowData cmsWindowData = new CmsWindowData();
            cmsWindowData.setWindowId(windowId);
            cmsWindowData.setDataType(7);
            cmsWindowData.setSiteId((Integer) session.get("siteId"));
            checkDataIdS = cmsWindowDataService.queryByWindowData(cmsWindowData);


        } catch (Exception e) {
        	logger.error("CategoryAction.rankingListCategory异常：" + e.getMessage(), e);
        }
        return "rankingListCategory";
    }

    /**
     * 跳转数据列表页面
     */
    public String openCategoryList() {
        return "openCategoryList";
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getCheckeds() {
        return checkeds;
    }

    public void setCheckeds(String checkeds) {
        this.checkeds = checkeds;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public CmsWindowDataService getCmsWindowDataService() {
        return cmsWindowDataService;
    }

    public void setCmsWindowDataService(CmsWindowDataService cmsWindowDataService) {
        this.cmsWindowDataService = cmsWindowDataService;
    }

    public String getCheckDataIdS() {
        return checkDataIdS;
    }

    public void setCheckDataIdS(String checkDataIdS) {
        this.checkDataIdS = checkDataIdS;
    }

    //	public String getChannel() {
//		return channel;
//	}
//	public void setChannel(String channel) {
//		this.channel = channel;
//	}
//	public String getChannelQuery() {
//		return channelQuery;
//	}
//	public void setChannelQuery(String channelQuery) {
//		this.channelQuery = channelQuery;
//	}
    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getAdminType() {
        return adminType;
    }

    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
    }


}
