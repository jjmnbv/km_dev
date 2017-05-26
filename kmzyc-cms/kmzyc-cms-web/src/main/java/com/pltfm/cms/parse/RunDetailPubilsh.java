package com.pltfm.cms.parse;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.supplier.model.ShopMain;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.pltfm.app.service.ProdBrandService;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ProdBrandExample;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.cms.service.CmsSupplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

/**
 * 详细页发布多线程
 *
 * @author cjm
 * @since 2014-5-8
 */
@Component(value = "runDetailPubilsh")
public class RunDetailPubilsh extends ActionSupport {

    // 日志
	private static Logger logger = LoggerFactory.getLogger(RunDetailPubilsh.class);
    /**
     * 页面发布
     */
    @Resource(name = "pagePublish")
    private PagePublish pagePublish;
    /**
     * 供应商信息业务逻辑接口
     */
    @Resource(name = "cmsSupplyService")
    private CmsSupplyService cmsSupplyService;

    @Resource(name = "runDetailPubilsh")
    private RunDetailPubilsh runDetailPubilsh;

    private ViewProductInfo viewProductInfo;


    public String shopName;
    public List<ShopMain> shopMainList;
    /**
     * 产品品牌集合
     **/
    private List<ProdBrand> brandList;

    @Resource(name = "prodBrandService")
    private ProdBrandService prodBrandService;

    private Integer templateType;
    private Date promotionsEndDate;

    public String runnableDetailPubilsh() {
        pagePublish.setViewProductInfo(viewProductInfo);
        pagePublish.setShopName(shopName);
        pagePublish.setTemplateType(templateType);
        pagePublish.setPromotionsEndDate(promotionsEndDate);
        // 正式
        pagePublish.setPublishType(Integer.valueOf(1));
        // 创建一个固定大小的线程池a
        ExecutorService service = Executors.newFixedThreadPool(30);
        try {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    pagePublish.detailPublish();
                }
            };
            // 在未来某个时间执行给定的命令
            service.execute(run);
            // 关闭启动线程
            service.shutdown();
//            Map session = ActionContext.getContext().getSession();
//            CmsSite site = null;
//            if (session != null && session.get("siteId") != null) {
//                site = PathConstants.getCmsSite((Integer) session.get("siteId"));
//            }
            brandList = prodBrandService.selectByExample(new ProdBrandExample());
        } catch (Exception e) {
            logger.error("runnableDetailPubilsh详情页多线程发布报错：" + e);
            this.addActionMessage(ConfigurationUtil.getString("publish.detail.fail"));
            return "runnableDetailPubilsh";
        }
        logger.info("runnableDetailPubilsh详情页多线程发布成功：");
        this.addActionMessage(ConfigurationUtil.getString("publish.detail.success"));
        return "runnableDetailPubilsh";
    }

    public String getShopName() {
        return shopName;
    }

    public PagePublish getPagePublish() {
        return pagePublish;
    }

    public void setPagePublish(PagePublish pagePublish) {
        this.pagePublish = pagePublish;
    }

    public ViewProductInfo getViewProductInfo() {
        return viewProductInfo;
    }

    public void setViewProductInfo(ViewProductInfo viewProductInfo) {
        this.viewProductInfo = viewProductInfo;
    }


    public List<ProdBrand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<ProdBrand> brandList) {
        this.brandList = brandList;
    }

    public ProdBrandService getProdBrandService() {
        return prodBrandService;
    }

    public void setProdBrandService(ProdBrandService prodBrandService) {
        this.prodBrandService = prodBrandService;
    }


    public RunDetailPubilsh getRunDetailPubilsh() {
        return runDetailPubilsh;
    }

    public void setRunDetailPubilsh(RunDetailPubilsh runDetailPubilsh) {
        this.runDetailPubilsh = runDetailPubilsh;
    }

    public CmsSupplyService getCmsSupplyService() {
        return cmsSupplyService;
    }

    public void setCmsSupplyService(CmsSupplyService cmsSupplyService) {
        this.cmsSupplyService = cmsSupplyService;
    }

    public List<ShopMain> getShopMainList() {
        return shopMainList;
    }

    public void setShopMainList(List<ShopMain> shopMainList) {
        this.shopMainList = shopMainList;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public Date getPromotionsEndDate() {
        return promotionsEndDate;
    }

    public void setPromotionsEndDate(Date promotionsEndDate) {
        this.promotionsEndDate = promotionsEndDate;
    }


}
