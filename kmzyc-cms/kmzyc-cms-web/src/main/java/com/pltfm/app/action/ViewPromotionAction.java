package com.pltfm.app.action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pltfm.app.service.ViewPromotionService;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.PromotionType;
import com.pltfm.app.vobject.ViewPromotion;
import com.pltfm.cms.service.CmsSiteService;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.vobject.CmsSite;
import com.pltfm.cms.vobject.CmsWindowData;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 活动信息Action类
 *
 * @author cjm
 * @since 2013-9-10
 */
@Component(value = "viewPromotionAction")
@Scope(value = "prototype")
public class ViewPromotionAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(ViewPromotionAction.class);
    /**
     * 窗口数据接口
     */
    @Resource(name = "cmsWindowDataService")
    private CmsWindowDataService cmsWindowDataService;
    /**
     * 活动信息业务逻辑层接口
     */
    @Resource(name = "viewPromotionService")
    private ViewPromotionService viewPromotionService;

    /**
     * 站点接口
     */
    @Resource(name = "cmsSiteService")
    private CmsSiteService cmsSiteService;


    /**
     * 分页对象
     */
    private Page page;
    /**
     * 选中的checked
     */
    private String checkeds;

    /**
     * 数据类型
     */
    private Integer dataType;

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
     * 活动信息实体
     */
    private ViewPromotion viewPromotion;
    /**
     * 活动信息集合
     */
    private List<PromotionType> listPromotionType;

    /**
     * 活动类目
     */
    private Map categoryMap;


    /**
     * 弹出活动信息列表
     */
    public String queryViewromotion() {
        Integer siteId = (Integer) session.get("siteId");
        try {
            CmsSite cmsSite = cmsSiteService.selectByPrimaryKey(siteId);

            listPromotionType = new ArrayList<PromotionType>();
            //调用接口，返回活动类别
            categoryMap = viewPromotionService.getPromotionCategory();
            Iterator<Map.Entry<Integer,String>> itr = categoryMap.entrySet().iterator();
            PromotionType promotionType;
            while (itr.hasNext()) {
                Map.Entry<Integer,String> entry=itr.next();
                promotionType = new PromotionType();
                promotionType.setPromotionTypeId(entry.getKey());
                promotionType.setPromotionTypeName(entry.getValue());
                listPromotionType.add(promotionType);
            }
            if (viewPromotion == null)
                viewPromotion = new ViewPromotion();


            page = viewPromotionService.queryForPage(viewPromotion, page);
            List<CmsWindowData> dataList = this.cmsWindowDataService.queryByWindowIdDataType(windowId, 1, siteId);
            List list = page.getDataList();
            //剔除已选择的数据
            if (dataList.size() != 0) {
                for (int i = 0; i < list.size(); i++) {
                    ViewPromotion viewPromotion = (ViewPromotion) list.get(i);
                    for (int j = 0; j < dataList.size(); j++) {
                        CmsWindowData cmsWinData = dataList.get(j);
                        if (viewPromotion.getPromotionId().equals(cmsWinData.getDataId())) {
                            viewPromotion.setFlag(1);//1表示已选择
                            break;
                        }
                    }
                }
            }
            return "viewPromotionSccuess";
        } catch (Exception e) {
            logger.error("ViewPromotionAction.queryViewromotion异常：" + e.getMessage(), e);
            return "viewPromotionError";
        }
    }

    /**
     * 跳转数据列表页面
     */
    public String openViewPromotionList() {
        return "openViewPromotionList";
    }

    public ViewPromotionService getViewPromotionService() {
        return viewPromotionService;
    }

    public void setViewPromotionService(ViewPromotionService viewPromotionService) {
        this.viewPromotionService = viewPromotionService;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ViewPromotion getViewPromotion() {
        return viewPromotion;
    }

    public void setViewPromotion(ViewPromotion viewPromotion) {
        this.viewPromotion = viewPromotion;
    }

    public List<PromotionType> getListPromotionType() {
        return listPromotionType;
    }

    public void setListPromotionType(List<PromotionType> listPromotionType) {
        this.listPromotionType = listPromotionType;
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

    public CmsWindowDataService getCmsWindowDataService() {
        return cmsWindowDataService;
    }

    public void setCmsWindowDataService(CmsWindowDataService cmsWindowDataService) {
        this.cmsWindowDataService = cmsWindowDataService;
    }

    public Map getCategoryMap() {
        return categoryMap;
    }

    public void setCategoryMap(Map categoryMap) {
        this.categoryMap = categoryMap;
    }

    public CmsSiteService getCmsSiteService() {
        return cmsSiteService;
    }

    public void setCmsSiteService(CmsSiteService cmsSiteService) {
        this.cmsSiteService = cmsSiteService;
    }

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
