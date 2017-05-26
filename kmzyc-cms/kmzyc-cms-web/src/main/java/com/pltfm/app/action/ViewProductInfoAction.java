package com.pltfm.app.action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pltfm.app.service.CategoryService;
import com.pltfm.app.service.ProdBrandService;
import com.pltfm.app.service.ViewProductInfoService;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.vobject.CmsWindowData;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

@Scope(value = "prototype")
@Component(value = "viewProductInfoAction")
public class ViewProductInfoAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(ViewProductInfoAction.class);
    /**
     * 窗口数据接口
     */
    @Resource(name = "cmsWindowDataService")
    private CmsWindowDataService cmsWindowDataService;
    /**
     * 产品视图业务逻辑接口
     */
    @Resource(name = "viewProductInfoService")
    private ViewProductInfoService viewProductInfoService;
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
     * 品牌信息业务逻辑层接口
     */
    @Resource(name = "prodBrandService")
    private ProdBrandService prodBrandService;

    /**
     * 类目业务逻辑层接口
     */
    @Resource(name = "categoryService")
    private CategoryService categoryService;

    /**
     * 品牌信息集合
     */
    private List<ProdBrand> prodList;

    /**
     * 类目集合
     */
    private List<Category> categoryList;


    /**
     * 产品信息实体
     */
    private ViewProductInfo viewProductInfo;


    /**
     * 弹出产品信息列表
     */
    public String queryViewProductInfo() {
        try {
            Integer siteId = (Integer) session.get("siteId");
//			Map session = ActionContext.getContext().getSession();
//			CmsSite cmsSite = null;
//			if(session!=null&&session.get("siteId")!=null){
//				 cmsSite = PathConstants.getCmsSite((Integer)session.get("siteId"));
//			}
            if (viewProductInfo == null) {
                viewProductInfo = new ViewProductInfo();
            }


            prodList = prodBrandService.selectByExample(null);

            categoryList = categoryService.querySubCategory();


            //空格处理
            if (null != viewProductInfo) {
                if (null != viewProductInfo.getProductSkuCode())
                    viewProductInfo.setProductSkuCode(viewProductInfo.getProductSkuCode().trim());
                if (null != viewProductInfo.getProcuctName())
                    viewProductInfo.setProcuctName(viewProductInfo.getProcuctName().trim());
                if (null != viewProductInfo.getProductId())
                    viewProductInfo.setProductId(viewProductInfo.getProductId());
                if (null != viewProductInfo.getProductSkuId())
                    viewProductInfo.setProductSkuId(viewProductInfo.getProductSkuId());
            }
            page = viewProductInfoService.queryForPage(viewProductInfo, page);
            List<CmsWindowData> dataList = this.cmsWindowDataService.queryByWindowIdDataType(windowId, 0, siteId);
            List list = page.getDataList();
            //剔除已选择的数据
            if (dataList.size() != 0 && null != list) {
                for (int i = 0; i < list.size(); i++) {
                    ViewProductInfo productInfo = (ViewProductInfo) list.get(i);
                    for (int j = 0; j < dataList.size(); j++) {
                        CmsWindowData cmsWinData = dataList.get(j);
                        if (productInfo.getProductId().equals(cmsWinData.getDataId())) {
                            if (cmsWinData.getProductSkuId() != null) {
                                if (productInfo.getProductSkuId().equals(cmsWinData.getProductSkuId())) {
                                    productInfo.setFlag(1);//1表示已选择
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return "viewProductInfoSccuess";
        } catch (Exception e) {
        	logger.error("ViewProductInfoAction.queryViewProductInfo异常：" + e.getMessage(), e);
            return "viewProductInfoError";
        }
    }

    /**
     * 跳转数据列表页面
     */
    public String openViewProductInfoList() {
        return "openViewProductInfoList";
    }

    public ViewProductInfoService getViewProductInfoService() {
        return viewProductInfoService;
    }

    public void setViewProductInfoService(
            ViewProductInfoService viewProductInfoService) {
        this.viewProductInfoService = viewProductInfoService;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
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

    public ViewProductInfo getViewProductInfo() {
        return viewProductInfo;
    }

    public void setViewProductInfo(ViewProductInfo viewProductInfo) {
        this.viewProductInfo = viewProductInfo;
    }

    public CmsWindowDataService getCmsWindowDataService() {
        return cmsWindowDataService;
    }

    public void setCmsWindowDataService(CmsWindowDataService cmsWindowDataService) {
        this.cmsWindowDataService = cmsWindowDataService;
    }

    public ProdBrandService getProdBrandService() {
        return prodBrandService;
    }

    public void setProdBrandService(ProdBrandService prodBrandService) {
        this.prodBrandService = prodBrandService;
    }

    public List<ProdBrand> getProdList() {
        return prodList;
    }

    public void setProdList(List<ProdBrand> prodList) {
        this.prodList = prodList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
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
