package com.pltfm.remote.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.cms.remote.service.RemoteDulitaocanService;
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

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Component(value = "remoteDulitaocanService")
public class RemoteDulitaocanServiceImpl implements RemoteDulitaocanService {
	private static Logger logger = LoggerFactory.getLogger(RemoteDulitaocanServiceImpl.class);
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
    // 产品相关信息接口
    @Resource(name = "viewProductInfoService")
    private ViewProductInfoService viewProductInfoService;

    /**
     * 套餐的发布接口
     *
     * @author gwl 返回 Map key套餐ID 值为URL
     */
    public Map remoteParse(List<ProductRelation> RelationList)
            throws Exception {
        Map map = new HashMap();
        try {
            for (int is = 0; is < RelationList.size(); is++) {
                ProductRelation pr = RelationList.get(is);
                List<ProductRelation> ProductRelationList = dulitaocanService.selectRelation(pr);
                if (ListUtils.isNotEmpty(ProductRelationList)) {
                    for (int ji = 0; ji < ProductRelationList.size(); ji++) {
                        // 遍历套餐主表
                        ProductRelation p = ProductRelationList.get(ji);
                        CmsPage cmsPage = new CmsPage();
                        CmsSite cmsSite = new CmsSite();
                        cmsSite.setEngName(p.getWebSite());
                        CmsSite cmsSite1 = cmsSiteService.querySite(cmsSite);
                        Integer siteId = cmsSite1.getSiteId();
                        cmsPage.setPublicType(14);//套餐发布类型
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

                                ViewProductInfo product = viewProductInfoService
                                        .selectSkuId(viewProductInfo);//根据产品主键查询单条产品

                                if (product != null) {
                                    productRelationDetail.setProduct(product);
                                }

                                List<ProductImage> Img = dulitaocanService
                                        .selectImg(productRelationDetail
                                                .getRelationSkuId());//根据RelationSkuId查询ProductImage

                                if (ListUtils.isNotEmpty(Img)) {
                                    productRelationDetail.setProductImageList(Img);
                                }

                                productRelationDetail.setRelationName(p
                                        .getRelationName());

                                productRelationDetail.setTotalRelationPrice(p
                                        .getTotalRelationPrice());

                                productRelationDetail.setProductRelation(p);

                                list.add(productRelationDetail);
                            }

                        }
                        Map<String, Object> productRelationDetailMap = new HashMap<String, Object>();
                        productRelationDetailMap.put("productRelationDetail", list);//输出集合到 productRelationDetail

                        List<CmsPage> pageList = cmsPageService
                                .selectByPrimaryPublishType2(cmsPage);//查询套餐的发布类型 14

                        for (int i = 0; i < pageList.size(); i++) {//遍历 pageList集合发布
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
                                    outPath = PathConstants
                                            .dulitaocanPublishPath(siteId)
                                            + "/" + path;
                                } else {
                                    outPath = PathConstants
                                            .dulitaocanPublishPath(siteId);
                                }
                            }
                            // 判断pages文件夹是否存在，若不存在，则新建一个
                            FileOperateUtils.checkAndCreateDirs(outPath);
                            String fileOutPath = outPath
                                    + "/"
                                    + p.getRelationId()
                                    + cmsPage.getOutputPath().substring(
                                    cmsPage.getOutputPath()
                                            .lastIndexOf("."));

                            FileOperateUtils
                                    .writer(
                                            fileOutPath,
                                            "true"
                                                    .equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor
                                                    .compress(text)
                                                    : text);

                            String s = outPath;
                            String arg[] = s.split("/");
                            String last = arg[arg.length - 1];
                            String url = PathConstants.pageViewPath(siteId)
                                    .substring(
                                            0,
                                            PathConstants.pageViewPath(siteId)
                                                    .length() - 4)
                                    + last
                                    + "/"
                                    + p.getRelationId()
                                    + ".html";
                            map.put(p.getRelationId(), url);
                        }
                    }
                }
            }
        } catch (Exception e) {
        	logger.error("RemoteDulitaocanServiceImpl.remoteParse异常：" + e.getMessage(), e);
        }
        return map;

    }

    /**
     * 套餐的预览接口
     *
     * @author gwl 返回 URL
     */
    public String remoteViewdulitaocanParse(ProductRelation p) throws Exception {
        String url = "";
        try {
            CmsPage cmsPage = new CmsPage();
            CmsSite cmsSite = new CmsSite();
            cmsSite.setEngName(p.getWebSite());
            CmsSite cmsSite1 = cmsSiteService.querySite(cmsSite);
            Integer siteId = cmsSite1.getSiteId();
            cmsPage.setPublicType(14);
            cmsPage.setSiteId(siteId);
            List<ProductRelationDetail> list = new ArrayList();
            List<ProductRelationDetail> productRelationDetailList = dulitaocanService
                    .selectRelationDetail(p.getRelationId());
            if (ListUtils.isNotEmpty(productRelationDetailList)) {

                for (ProductRelationDetail productRelationDetail : productRelationDetailList) {

                    productRelationDetail.setTotalRelationPrice(productRelationDetail
                            .getTotalRelationPrice());
                    List<ViewSkuAttr> v = dulitaocanService
                            .selectSku(productRelationDetail.getRelationSkuId());

                    if (ListUtils.isNotEmpty(v)) {
                        productRelationDetail.setViewSkuAttrList(v);
                    }

                    int id = productRelationDetail.getRelationSkuId().intValue();
                    ViewProductInfo viewProductInfo = new ViewProductInfo();
                    viewProductInfo.setProductSkuId(id);
                    ViewProductInfo product = viewProductInfoService
                            .selectSkuId(viewProductInfo);
                    if (product != null) {
                        productRelationDetail.setProduct(product);
                    }

                    List<ProductImage> Img = dulitaocanService
                            .selectImg(productRelationDetail.getRelationSkuId());

                    if (ListUtils.isNotEmpty(Img)) {
                        productRelationDetail.setProductImageList(Img);
                    }
                    productRelationDetail.setRelationName(productRelationDetail.getRelationName());
                    productRelationDetail.setTotalRelationPrice(productRelationDetail
                            .getTotalRelationPrice());
                    productRelationDetail.setProductRelation(p);
                    list.add(productRelationDetail);
                }

            }
            Map<String, Object> productRelationDetailMap = new HashMap<String, Object>();
            productRelationDetailMap.put("productRelationDetail", list);

            List<CmsPage> pageList = cmsPageService
                    .selectByPrimaryPublishType2(cmsPage);

            for (int i = 0; i < pageList.size(); i++) {
                CmsPage cmsPage1 = pageList.get(i);
                cmsPage = cmsPageService.getCmsPageById(cmsPage1.getPageId());
                if (cmsPage.getOutputPath() != null
                        && cmsPage.getOutputPath().contains("/")) {
                    cmsPage.setJsCssPath(cmsPage.getOutputPath().substring(
                            cmsPage.getOutputPath().lastIndexOf('/') + 1,
                            cmsPage.getOutputPath().lastIndexOf('.')));
                }

                cmsPage.setTemplatePath(PathConstants.pageTemPath(cmsPage
                        .getSiteId())
                        + "/" + cmsPage.getPageId() + ".html");

                String text = defaultHtmlBuilder
                        .buildProductRelationDetailHtml(cmsPage,
                                productRelationDetailMap);
                if (null != text && !text.equals("")) {
                    // 修改状态
                    CmsPage page = cmsPageService.getCmsPageById(cmsPage
                            .getPageId());
                    page.setStatus(1);
                    cmsPageService.updateCmsPage(page);
                }
                String outPath = "";
                if (cmsPage.getOutputPath() != null) {
                    String path = cmsPage.getOutputPath().substring(0,
                            cmsPage.getOutputPath().lastIndexOf("/"));
                    if (!path.equals("")) {
                        outPath = PathConstants.viewdulitaocan(siteId)
                                + "/" + path;
                    } else {
                        outPath = PathConstants.viewdulitaocan(siteId);
                    }
                }
                // 判断pages文件夹是否存在，若不存在，则新建一个
                FileOperateUtils.checkAndCreateDirs(outPath);

                String fileOutPath = outPath
                        + "/"
                        + p.getRelationId()
                        + cmsPage.getOutputPath().substring(
                        cmsPage.getOutputPath().lastIndexOf("."));

                FileOperateUtils
                        .writer(
                                fileOutPath,
                                "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor
                                        .compress(text)
                                        : text);

                String s = outPath;
                String arg[] = s.split("/");
                String last = arg[arg.length - 1];

                url = PathConstants.pageViewPath(siteId).substring(0,
                        PathConstants.pageViewPath(siteId).length() - 4)
                        + last + "/" + p.getRelationId() + ".html";
            }
        } catch (Exception e) {
        	logger.error("RemoteDulitaocanServiceImpl.remoteViewdulitaocanParse异常：" + e.getMessage(), e);
        }
        return url;
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
    }
*/
    public DulitaocanService getDulitaocanService() {
        return dulitaocanService;
    }

    public CmsSiteService getCmsSiteService() {
        return cmsSiteService;
    }

    public void setCmsSiteService(CmsSiteService cmsSiteService) {
        this.cmsSiteService = cmsSiteService;
    }

    public void setDulitaocanService(DulitaocanService dulitaocanService) {
        this.dulitaocanService = dulitaocanService;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public ViewProductInfoService getViewProductInfoService() {
        return viewProductInfoService;
    }

    public void setViewProductInfoService(
            ViewProductInfoService viewProductInfoService) {
        this.viewProductInfoService = viewProductInfoService;
    }
}
