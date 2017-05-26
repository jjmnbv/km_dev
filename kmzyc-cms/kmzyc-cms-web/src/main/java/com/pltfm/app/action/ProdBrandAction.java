package com.pltfm.app.action;

import com.pltfm.app.service.ProdBrandService;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.vobject.CmsWindowData;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 品牌信息Action类
 *
 * @author cjm
 * @since 2013-9-10
 */
@Component(value = "prodBrandAction")
@Scope(value = "prototype")
public class ProdBrandAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(ProdBrandAction.class);
    /**
     * 窗口数据接口
     */
    @Resource(name = "cmsWindowDataService")
    private CmsWindowDataService cmsWindowDataService;
    /**
     * 活动信息业务逻辑层接口
     */
    @Resource(name = "prodBrandService")
    private ProdBrandService prodBrandService;


    /**
     * 数据类型
     */
    private Integer dataType;

    /**
     * 分页对象
     */
    private Page page;
    /**
     * 选中的checked
     */
    private String checkeds;
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
     * 品牌信息实体
     */
    private ProdBrand prodBrand;



    /**
     * 弹出活动信息列表
     */
    public String queryProdBrand() {
        try {
            Integer siteId = (Integer) session.get("siteId");
            //空格处理
            if (null != prodBrand) {
                if (null != prodBrand.getBrandName())
                    prodBrand.setBrandName(prodBrand.getBrandName().trim());
                if (null != prodBrand.getNation())
                    prodBrand.setNation(prodBrand.getNation().trim());
                if (null != prodBrand.getEngName())
                    prodBrand.setEngName(prodBrand.getEngName().trim());
                if (null != prodBrand.getChnSpell())
                    prodBrand.setChnSpell(prodBrand.getChnSpell().trim());
            }
            page = prodBrandService.queryForPage(prodBrand, page);
            List<CmsWindowData> dataList = this.cmsWindowDataService.queryByWindowIdDataType(windowId, 3, siteId);
            List list = page.getDataList();
            if (dataList.size() != 0) {
                for (int i = 0; i < list.size(); i++) {
                    ProdBrand prodBrand = (ProdBrand) list.get(i);
                    for (int j = 0; j < dataList.size(); j++) {
                        CmsWindowData cmsWinData = dataList.get(j);
                        if (prodBrand.getBrandId().equals(cmsWinData.getDataId())) {
                            prodBrand.setFlag(1);
                            break;
                        }
                    }
                }
            }
            return "prodBrandSccuess";
        } catch (Exception e) {
            logger.error("ProdBrandAction.queryProdBrand异常：" + e.getMessage(), e);
            return "prodBrandError";
        }
    }

    /**
     * 弹出活动信息列表
     */
    public void queryProdBrandJson() {
        try {
            super.writeJson(prodBrandService.selectByExample(null));
        } catch (Exception e) {
            logger.error("ProdBrandAction.queryProdBrandJson异常：" + e.getMessage(), e);
        }
    }

    /**
     * 跳转数据列表页面
     */
    public String openProdBrandList() {
        return "openProdBrandList";
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

    public ProdBrandService getProdBrandService() {
        return prodBrandService;
    }

    public void setProdBrandService(ProdBrandService prodBrandService) {
        this.prodBrandService = prodBrandService;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public ProdBrand getProdBrand() {
        return prodBrand;
    }

    public void setProdBrand(ProdBrand prodBrand) {
        this.prodBrand = prodBrand;
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
