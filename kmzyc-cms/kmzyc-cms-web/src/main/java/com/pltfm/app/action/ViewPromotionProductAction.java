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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 活动商品信息Action类
 *
 * @author cjm
 * @since 2013-9-10
 */
@Component(value = "viewPromotionProductAction")
@Scope(value = "prototype")
public class ViewPromotionProductAction extends BaseAction {
    private static final long serialVersionUID = 5445943419626656730L;
    private static Logger logger = LoggerFactory.getLogger(ViewPromotionProductAction.class);
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
    public String queryViewPromotionProduct() {
        Integer siteId = (Integer) session.get("siteId");
        try {
            CmsSite cmsSite = cmsSiteService.selectByPrimaryKey(siteId);

            listPromotionType = new ArrayList<PromotionType>();
            //调用接口，返回活动类别
            categoryMap = viewPromotionService.getPromotionCategory();
            Iterator<Map.Entry<Integer,String>> itr = categoryMap.entrySet().iterator();
            PromotionType promotionType = null;
            while (itr.hasNext()) {
                Map.Entry<Integer,String> entry=itr.next();
                Integer key = entry.getKey();
                String value = entry.getValue();
                //只展示 "特价":10  "打折":8  "app特价":12    等产品级活动
                if (key == 8 || key == 10 || key == 12) {
                    promotionType = new PromotionType();
                    promotionType.setPromotionTypeId(key);
                    promotionType.setPromotionTypeName(value);
                    listPromotionType.add(promotionType);
                }
            }

            List<Integer> promotionTypeIdList;
            if (viewPromotion == null) {
                viewPromotion = new ViewPromotion();
                promotionTypeIdList = new ArrayList<Integer>();
                //只展示 "特价":10  "打折":8  "app特价":12    等产品级活动
                promotionTypeIdList.add(8);
                promotionTypeIdList.add(10);
                promotionTypeIdList.add(12);
                viewPromotion.setPromotionTypeIdList(promotionTypeIdList);
            } else {
                Integer promotionTypeId = viewPromotion.getPromotionTypeId();
                if (promotionTypeId != null) {
                    promotionTypeIdList = new ArrayList<Integer>();
                    promotionTypeIdList.add(promotionTypeId);
                    viewPromotion.setPromotionTypeIdList(promotionTypeIdList);
                } else {
                    promotionTypeIdList = new ArrayList<Integer>();
                    //只展示 "特价":10  "打折":8  "app特价":12    等产品级活动
                    promotionTypeIdList.add(8);
                    promotionTypeIdList.add(10);
                    promotionTypeIdList.add(12);
                    viewPromotion.setPromotionTypeIdList(promotionTypeIdList);
                }
            }


            page = viewPromotionService.queryForPagePro(viewPromotion, page);

            return "viewPromotionProductSccuess";
        } catch (Exception e) {
            logger.error("ViewPromotionProductAction.queryViewPromotionProduct异常：" + e.getMessage(), e);
            return "viewPromotionProductError";
        }
    }

    /**
     * 跳转数据列表页面
     */
    public String openViewPromotionProductList() {
        return "openViewPromotionProductList";
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
