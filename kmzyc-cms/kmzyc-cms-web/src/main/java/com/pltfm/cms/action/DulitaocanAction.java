package com.pltfm.cms.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.cms.remote.service.RemoteDulitaocanService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.pltfm.app.service.ViewProductInfoService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductRelation;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.app.vobject.ViewSkuAttr;
import com.pltfm.cms.parse.DefaultHtmlBuilder;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsPageService;
import com.pltfm.cms.service.CmsSiteService;
import com.pltfm.cms.service.DulitaocanService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.util.HtmlCompressor;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsSite;
import com.pltfm.cms.vobject.ProductRelationDetail;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Component("pageDulitaocan")
@Scope("prototype")
public class DulitaocanAction extends ActionSupport {
	private static Logger logger = LoggerFactory.getLogger(DulitaocanAction.class);
    private static final long serialVersionUID = -2526125638862446742L;
    @Resource(name = "htmlBuilder")
    DefaultHtmlBuilder defaultHtmlBuilder;
    @Resource(name = "cmsPageService")
    private CmsPageService cmsPageService;// cmsPageService接口
    // 获取模板存放路径
    /*@Resource(name = "templateConfig")
	private Map<String, String> templateConfig;*/
    @Resource(name = "dulitaocanService")
    private DulitaocanService dulitaocanService;
    @Resource(name = "cmsSiteService")
    private CmsSiteService cmsSiteService;// 站点信息接口
    private ProductRelation productRelation;
    // 产品相关信息接口
    @Resource(name = "viewProductInfoService")
    private ViewProductInfoService viewProductInfoService;

    @Resource(name = "remoteDulitaocanService")
    private RemoteDulitaocanService remoteDulitaocanService;

    /**
     * 渠道
     */
//	private String channel;

    /**
     * 查询条件渠道
     */
    private String channelQuery;


    /**
     * 进入发布套餐页面功能页面
     */
    @SuppressWarnings("unchecked")
    public String preDuli() {
        try {
            Map session = ActionContext.getContext().getSession();
            CmsSite site = null;
            if (session != null && session.get("siteId") != null) {
                site = PathConstants.getCmsSite((Integer) session.get("siteId"));
                //页面的渠道查询条件
                channelQuery = site.getEngName();
            }



        } catch (Exception e) {
            logger.error("DulitaocanAction.preDuli异常：" + e.getMessage(), e);
        }
        return "preDetail";
    }

    /**
     * 套餐的发布接口
     */
    @SuppressWarnings("unchecked")
    public String pageDuli() {
        try {
            Map session = ActionContext.getContext().getSession();
            Assert.notNull(session);
            Assert.notNull(session.get("siteId"));
            CmsSite site = PathConstants.getCmsSite((Integer) session.get("siteId"));
            //页面的渠道查询条件
            Integer siteId = site.getSiteId();

            List<ProductRelation> ProductRelationList = dulitaocanService
                    .selectRelation(productRelation);

            if (ListUtils.isNotEmpty(ProductRelationList)) {
                for (int ji = 0; ji < ProductRelationList.size(); ji++) {
                    // 遍历套餐主表
                    ProductRelation p = ProductRelationList.get(ji);

                    CmsPage cmsPage = new CmsPage();
                    //	CmsSite cmsSite = new CmsSite();
                    //	cmsSite.setEngName(p.getWebSite());
                    //	CmsSite cmsSite1 = cmsSiteService.querySite(cmsSite);


                    if (p.getRelationType() == 4) {
                        cmsPage.setPublicType(23);//组方发布
                    } else {
                        cmsPage.setPublicType(14);//套餐发布类型
                    }
                    cmsPage.setSiteId(siteId);
                    List<ProductRelationDetail> list = new ArrayList();
                    List<ProductRelationDetail> productRelationDetailList = dulitaocanService
                            .selectRelationDetail(p.getRelationId()); //跟据 RelationId 查询子表
                    if (ListUtils.isNotEmpty(productRelationDetailList)) {

                        for (ProductRelationDetail productRelationDetail : productRelationDetailList) {
                            //遍历子表
                            productRelationDetail.setTotalRelationPrice(p
                                    .getTotalRelationPrice());
                            List<ViewSkuAttr> v = dulitaocanService
                                    .selectSku(productRelationDetail
                                            .getRelationSkuId());//跟据 RelationSkuId 查询  ViewSkuAttr

                            if (ListUtils.isNotEmpty(v)) {
                                productRelationDetail.setViewSkuAttrList(v);
                            }

                            int id = productRelationDetail.getRelationSkuId().intValue();
                            ViewProductInfo viewProductInfo = new ViewProductInfo();
                            viewProductInfo.setProductSkuId(id);
                            ViewProductInfo product = viewProductInfoService.selectSkuId(viewProductInfo);//根据产品主键查询单条产品
                            if (product != null) {
                                productRelationDetail.setProduct(product);
                            }


                            List<ProductImage> Img = dulitaocanService
                                    .selectImg(productRelationDetail
                                            .getRelationSkuId());//根据RelationSkuId查询ProductImage

                            if (ListUtils.isNotEmpty(Img)) {
                                productRelationDetail.setProductImageList(Img);
                            }
                            productRelationDetail.setRelationName(p.getRelationName());
                            productRelationDetail.setTotalRelationPrice(p.getTotalRelationPrice());
                            productRelationDetail.setProductRelation(p);
                            list.add(productRelationDetail);
                        }

                    }
                    Map<String, Object> productRelationDetailMap = new HashMap<String, Object>();
                    productRelationDetailMap.put("productRelationDetail", list);//输出集合到 productRelationDetail
                    productRelationDetailMap.put("productRelation", p);//输出集合到 productRelation

                    List<CmsPage> pageList = cmsPageService
                            .selectByPrimaryPublishType2(cmsPage);//

                    for (int i = 0; i < pageList.size(); i++) { //遍历 pageList集合发布
                        CmsPage cmsPage1 = pageList.get(i);
                        cmsPage = cmsPageService.getCmsPageById(cmsPage1
                                .getPageId());
                        if (cmsPage.getOutputPath() != null
                                && cmsPage.getOutputPath().contains("/")) {
                            cmsPage.setJsCssPath(cmsPage.getOutputPath()
                                    .substring(
                                            cmsPage.getOutputPath()
                                                    .lastIndexOf('/') + 1,
                                            cmsPage.getOutputPath()
                                                    .lastIndexOf('.')));
                        }

                        cmsPage.setTemplatePath(PathConstants
                                .pageTemPath(cmsPage.getSiteId())
                                + "/"
                                + cmsPage.getPageId()
                                + ".html");

                        String text = defaultHtmlBuilder
                                .buildProductRelationDetailHtml(cmsPage,
                                        productRelationDetailMap);
                        if (null != text && !text.equals("")) {
                            // 修改状态
                            CmsPage page = cmsPageService
                                    .getCmsPageById(cmsPage.getPageId());
                            page.setStatus(1);
                            cmsPageService.updateCmsPage(page);
                        }
                        String outPath = "";
                        if (cmsPage.getOutputPath() != null) {
                            String path = cmsPage.getOutputPath().substring(0,
                                    cmsPage.getOutputPath().lastIndexOf("/"));
                            if (!path.equals("")) {
                                outPath = PathConstants.dulitaocanPublishPath(siteId)
                                        + "/" + path;
                            } else {
                                outPath = PathConstants.dulitaocanPublishPath(siteId);
                            }
                        }
                        // 判断pages文件夹是否存在，若不存在，则新建一个
                        FileOperateUtils.checkAndCreateDirs(outPath);
                        String fileOutPath = outPath + "/" + p.getRelationId() + cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf("."));

                        FileOperateUtils.writer(fileOutPath, "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor.compress(text) : text);

                        String s = outPath;
                        String arg[] = s.split("/");
                        String last = arg[arg.length - 1];

                        String url = PathConstants.pageViewPath(siteId).substring(0, PathConstants.pageViewPath(siteId).length() - 4)
                                + last + "/" + p.getRelationId() + ".html";
                    }
                }
            }
            this.addActionMessage(ConfigurationUtil.getString("publish.dulitaocan.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("publish.dulitaocan.fail"));
            logger.error("DulitaocanAction.pageDuli异常：" + e.getMessage(), e);
        }

        return preDuli();
    }


    public RemoteDulitaocanService getRemoteDulitaocanService() {
        return remoteDulitaocanService;
    }

    public void setRemoteDulitaocanService(
            RemoteDulitaocanService remoteDulitaocanService) {
        this.remoteDulitaocanService = remoteDulitaocanService;
    }

    public DefaultHtmlBuilder getDefaultHtmlBuilder() {
        return defaultHtmlBuilder;
    }

    public void setDefaultHtmlBuilder(DefaultHtmlBuilder defaultHtmlBuilder) {
        this.defaultHtmlBuilder = defaultHtmlBuilder;
    }

    public CmsPageService getCmsPageService() {
        return cmsPageService;
    }

    public void setCmsPageService(CmsPageService cmsPageService) {
        this.cmsPageService = cmsPageService;
    }

	/*public Map<String, String> getTemplateConfig() {
		return templateConfig;
	}

	public void setTemplateConfig(Map<String, String> templateConfig) {
		this.templateConfig = templateConfig;
	}*/

    public DulitaocanService getDulitaocanService() {
        return dulitaocanService;
    }

    public void setDulitaocanService(DulitaocanService dulitaocanService) {
        this.dulitaocanService = dulitaocanService;
    }

    public CmsSiteService getCmsSiteService() {
        return cmsSiteService;
    }

    public void setCmsSiteService(CmsSiteService cmsSiteService) {
        this.cmsSiteService = cmsSiteService;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public ProductRelation getProductRelation() {
        return productRelation;
    }

    public void setProductRelation(ProductRelation productRelation) {
        this.productRelation = productRelation;
    }
//	public String getChannel() {
//		return channel;
//	}
//
//	public void setChannel(String channel) {
//		this.channel = channel;
//	}

    public String getChannelQuery() {
        return channelQuery;
    }

    public ViewProductInfoService getViewProductInfoService() {
        return viewProductInfoService;
    }

    public void setViewProductInfoService(
            ViewProductInfoService viewProductInfoService) {
        this.viewProductInfoService = viewProductInfoService;
    }

    public void setChannelQuery(String channelQuery) {
        this.channelQuery = channelQuery;
    }
}
