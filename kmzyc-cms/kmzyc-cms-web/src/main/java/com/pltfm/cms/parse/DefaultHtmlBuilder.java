package com.pltfm.cms.parse;

import com.kmzyc.supplier.model.ShopMain;
import com.pltfm.app.dao.ProductmainDAO;
import com.pltfm.app.service.SuppliersCertificateFileService;
import com.pltfm.app.service.SuppliersCertificateInfoService;
import com.pltfm.app.service.SuppliersRecheckService;
import com.pltfm.app.service.ViewProductInfoService;
import com.pltfm.app.util.FreeMarkerUtil;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.app.vobject.ViewPromotion;
import com.pltfm.cms.service.CmsSiteService;
import com.pltfm.cms.service.CmsSupplyService;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsPromotionTask;
import com.pltfm.cms.vobject.CmsSite;
import com.pltfm.cms.vobject.CmsWindow;
import com.pltfm.cms.vobject.SuppliersCertificateFile;
import com.pltfm.cms.vobject.SuppliersCertificateInfo;
import com.pltfm.cms.vobject.SuppliersRecheck;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

@Component(value = "htmlBuilder")
@Scope(value = "prototype")
public class DefaultHtmlBuilder implements HtmlBuilder {

    //日志
	private static Logger logger = LoggerFactory.getLogger(DefaultHtmlBuilder.class);
    @Resource(name = "productmainDAO")
    private ProductmainDAO productmainDAO;
    @Resource(name = "cmsSiteService")
    private CmsSiteService cmsSiteService;// 站点信息接口
    // 产品相关信息接口
    @Resource(name = "viewProductInfoService")
    private ViewProductInfoService viewProductInfoService;


    // 文件路径分格符
    private static final String SEPARATOR = "/";

    @Resource(name = "dataHandler")
    private DataHandler dataHandler;
/*	@Resource(name="templateConfig")
    private Map<String, String> templateConfig; */
    /**
     * 供应商信息业务逻辑接口
     */
    @Resource(name = "cmsSupplyService")
    private CmsSupplyService cmsSupplyService;

    @Resource
    private SuppliersCertificateInfoService suppliersCertificateInfoService;
    @Resource
    private SuppliersCertificateFileService suppliersCertificateFileService;
    @Resource
    private SuppliersRecheckService suppliersRecheckService;


    //页面类型 是cms页面还是给portal页面
    private String pageType;

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    @Override
    public String buildHtml(CmsPage page) throws Exception {
        String html = null;
        if (null == page) {
            throw new IllegalArgumentException();

        } else if (StringUtils.isBlank(page.getTemplatePath())) {

            logger.error(page.getName() + "页面的模板路径为空，无法生成网页。");
            return html;
        }

        Map<String, Object> data = getPageData(page);
        return windowHtml(page.getTemplatePath(), data, page);
    }

    //线上页面解析方法
    public String buildHtmlOnline(CmsPage page) throws Exception {
        String html = null;
        if (null == page) {
            throw new IllegalArgumentException();
        } else if (StringUtils.isBlank(page.getTemplatePath())) {

            logger.error(page.getName() + "页面的模板路径为空，无法生成网页。");

            return html;
        }

        Map<String, Object> data = getPageData(page);
        return windowHtmlOnline(page.getTemplatePath(), data, page);
    }

    //页面数据集
    private Map<String, Object> getPageData(CmsPage page) {
        if (null != page) {
            Map<String, Object> data = new HashMap<String, Object>();
            String pageType = String.valueOf(page.getPublicType());
            if ("2".equals(pageType)) {
                pageType = "portal";
            } else if ("3".equals(pageType)) {
                pageType = "mail";
            } else if ("5".equals(pageType)) {
                pageType = "search";
            }
            this.setPageType(pageType);


            //所有窗口
            List<Object> winList = dataHandler.getWindows(page);
            data.put(DataType.window.name(), winList);
            data = addPath(data, page.getSiteId());
            //标题
            String title = page.getTitle();
            title = null == title ? "" : title;
            data.put(DataType.title.name(), title);
            //关键词
            String keywords = page.getKeywords();
            String seo = "";
            if (page.getSeo() != null) {
                seo = page.getSeo();
            }
            keywords = null == keywords ? "" : keywords;
            data.put(DataType.keywords.name(), keywords + seo);

            //关键词
            String jsCssPath = page.getJsCssPath();
            jsCssPath = null == jsCssPath ? "" : jsCssPath;
            data.put(DataType.jsCssPath.name(), jsCssPath);

            // 描述
            String description = page.getDescribe();
            description = null == description ? "" : description;
            data.put(DataType.description.name(), description);

            // 店铺信息
            if (page.getCmsShopId() != null) {
                try {
                    ShopMain shopMain = cmsSupplyService.getShopMain(page.getCmsShopId());
                    data.put(DataType.productShopMain.name(), shopMain);
                    //显示产品
                    CmsSite cmsSite = cmsSiteService.selectByPrimaryKey(page.getSiteId());
                    if (!page.getLineNumber().equals(0)) {

                        List<ViewProductInfo> productmain = viewProductInfoService.
                                selectByShopCode(page.getCmsShop().getSupplierId().toString(), 0, page.getLineNumber());
                        List<ViewProductInfo> product = new ArrayList<ViewProductInfo>();

                        for (int f = 0; f < productmain.size(); f++) {
                            ViewProductInfo viewProductInfo = (ViewProductInfo) productmain
                                    .get(f);
                            if (viewProductInfo.getSkudesc() != null) {
                                String skuDesc = viewProductInfo.getProductTitle()
                                        + viewProductInfo.getSkudesc();
                                viewProductInfo.setProductTitle(skuDesc);
                            }
                            product.add(viewProductInfo);
                        }

                        data.put(DataType.productmain.name(), product);


                        Long supplierId = page.getCmsShop().getSupplierId().longValue();
                        //查询供应商资质重审信息
                        List<SuppliersRecheck> suppliersRecheckList = suppliersRecheckService.findListBySupplierId(supplierId);
                        SuppliersRecheck suppliersRecheck;
                        if (suppliersRecheckList != null && suppliersRecheckList.size() > 0) {
                            suppliersRecheck = suppliersRecheckList.get(0);
                            data.put("suppliersRecheck", suppliersRecheck);
                            Long reviewPass = 2L;
                            if (suppliersRecheck != null && reviewPass.equals(suppliersRecheck.getStatus())) {
                                //查询供应商资质信息及资质文件信息
                                List<SuppliersCertificateInfo> suppliersCertificateInfoList = suppliersCertificateInfoService.findListBySupplierId(supplierId);
                                logger.info("##########getPageData suppliersCertificateInfoList size:" + suppliersCertificateInfoList.size());
                                List<SuppliersCertificateFile> suppliersCertificateFileList;
                                Long infoId;
                                for (int i = 0; i < suppliersCertificateInfoList.size(); i++) {
                                    infoId = suppliersCertificateInfoList.get(i).getInfoId();
                                    suppliersCertificateFileList = suppliersCertificateFileService.findListByInfoId(infoId);
                                    suppliersCertificateInfoList.get(i).setSuppliersCertificateFiles(suppliersCertificateFileList);
                                }
                                data.put("suppliersCertificateInfoList", suppliersCertificateInfoList);
                            }
                        }
                    }


                    // 店铺页面是可视还是发布
                    int themeType = page.getThemeType();
                    data.put(DataType.themeType.name(), themeType);
                } catch (Exception e) {
                    logger.error("getPageData页面data", e);

                }
            }

            //店名模板类型
            if (page.getShopType() != null) {
                data.put(DataType.shopType.name(), page.getShopType());
            }
            // 排行信息
            if (page.getPublicType() != null && page.getPublicType().equals(21)) {
                try {

                    List<ViewProductInfo> productmain = viewProductInfoService.pageRanking();

                    List<ViewProductInfo> product = new ArrayList<ViewProductInfo>();

                    for (int f = 0; f < productmain.size(); f++) {

                        ViewProductInfo viewProductInfo = (ViewProductInfo) productmain
                                .get(f);

                        if (viewProductInfo.getSkudesc() != null) {
                            String skuDesc = viewProductInfo.getProductTitle()
                                    + viewProductInfo.getSkudesc();

                            viewProductInfo.setProductTitle(skuDesc);
                        }
                        product.add(viewProductInfo);
                    }

                    data.put(DataType.productmain.name(), product);
                } catch (Exception e) {
                    logger.error("PagePublish.preVisualization报错：" + e);

                }
            }


            return data;
        }
        return null;
    }

    /**
     * 获取窗口的HTML
     */
    public String windowHtml(String path, Map<String, Object> data, CmsPage page) throws Exception {
        if (data.isEmpty()) {
            return getHtml(path, MapUtils.EMPTY_MAP);
        }
        if (data.containsKey(DataType.window.name())) {

            List<Object> windows = (List<Object>) data.remove(DataType.window.name());
            for (int i = 0; i < windows.size(); i++) {
                String html = "";
                CmsWindow window = (CmsWindow) windows.get(i);
                if (null != window) {
                    if (page.getShopType() != null) { //发布店铺时店铺模板类型是否为空
                        window.setShopType(page.getShopType());
                    }
                    //其它页面变量
                    Map<String, Object> windowData = dataHandler.getWindowData(window);
                    windowData = addPath(windowData, page.getSiteId());
                    //页面类型
                    windowData.put(DataType.pageType.name(), page.getPublicType());
                    String templatePath = PathConstants.windowTemPath(page.getSiteId()) + "/" + window.getWindowId() + ".html";
                    html = windowHtml(templatePath, windowData, page);
                    data.put(window.getName(), html);
                }

            }
        }

        return getHtml(path, data);
    }

    /**
     * 获取线上的窗口的HTML
     */
    public String windowHtmlOnline(String path, Map<String, Object> data, CmsPage cmsPage) throws Exception {
        if (data.isEmpty()) {
            return getHtml(path, MapUtils.EMPTY_MAP);
        }
        if (data.containsKey(DataType.window.name())) {

            List<Object> windows = (List<Object>) data.remove(DataType.window.name());
            for (int i = 0; i < windows.size(); i++) {
                String html = "";
                CmsWindow window = (CmsWindow) windows.get(i);
                if (null != window) {
                    Map<String, Object> windowData = dataHandler.getWindowData(window);
                    windowData = addPath(windowData, cmsPage.getSiteId());
                    //页面类型
                    windowData.put(DataType.pageType.name(), cmsPage.getPublicType());
                    String templatePath = PathConstants.windowOnlineTemPath(cmsPage.getSiteId()) + "/" + window.getWindowId() + ".html";
                    html = windowHtmlOnline(templatePath, windowData, cmsPage);
                    data.put(window.getName(), html);
                }

            }
        }

        return getHtml(path, data);
    }


    /**
     * 生成详细页面
     *
     * @param page 详细页面模板
     * @param obj  详细内容实体
     * @return 详细页面html内容
     */
    public String buildDetailHtml(CmsPage page, Object obj) throws Exception {
        String html = null;
        if (null == page) {
            throw new IllegalArgumentException();
        } else if (StringUtils.isBlank(page.getTemplatePath())) {

            return html;
        }
        Map<String, Object> data = getPageDetailData(page, obj);
        return detailWindowHtml(page.getTemplatePath(), data, obj, page);
    }

    /**
     * 生成供应商页面
     *
     * @param page        供应商页面模板
     * @param supplierMap 供应商相关实体
     * @return 生成供应商页面html内容
     */
    public String buildSupplierHtml(CmsPage page, Map<String, Object> supplierMap) throws Exception {
        String html = null;
        if (null == page) {
            throw new IllegalArgumentException();
        } else if (StringUtils.isBlank(page.getTemplatePath())) {

            logger.error(page.getName() + "页面的模板路径为空，无法生成网页。");
            return html;
        }
        Map<String, Object> data = getPageSupplierData(page, supplierMap);
        return supplierWindowHtml(page.getTemplatePath(), data, page);
    }

    /**
     * 生成套餐页面
     *
     * @param page                     套餐模板
     * @param productRelationDetailMap 套餐相关实体
     * @return 生成套餐页面html内容
     */
    public String buildProductRelationDetailHtml(CmsPage page, Map<String, Object> productRelationDetailMap) throws Exception {
        String html = null;
        if (null == page) {
            throw new IllegalArgumentException();
        } else if (StringUtils.isBlank(page.getTemplatePath())) {

            logger.error(page.getName() + "页面的模板路径为空，无法生成网页。");
            return html;
        }
        Map<String, Object> data = getPageRelationDetailData(page, productRelationDetailMap);
        return pageRelationDetailWindowHtml(page.getTemplatePath(), data, page);
    }

    /**
     * 获取供应商页面数据信息
     */
    private Map<String, Object> getPageSupplierData(CmsPage page, Map<String, Object> map) {
        Map<String, Object> data = new HashMap<String, Object>();
        //所有窗口
        List<Object> winList = dataHandler.getWindows(page);
        data.put(DataType.window.name(), winList);
        for (Entry<String, Object> supplierMap : map.entrySet()) {
            data.put(supplierMap.getKey(), supplierMap.getValue());
		    
		/*	 if(DataType.suppliersCertificate.name().equals(supplierMap.getKey())){
				data.put(DataType.suppliersCertificate.name(), supplierMap.getValue());
			}else if(DataType.basicInfo.name().equals(supplierMap.getKey())){
				data.put(DataType.basicInfo.name(), supplierMap.getValue());
			}else if(DataType.product.name().equals(supplierMap.getKey())){
				data.put(DataType.product.name(), supplierMap.getValue());
			}else if(DataType.shopMain.name().equals(supplierMap.getKey())){
				data.put(DataType.shopMain.name(), supplierMap.getValue());
			}else if(DataType.supplyType.name().equals(supplierMap.getKey())){
				data.put(DataType.supplyType.name(), supplierMap.getValue());	
			}*/

        }

        data = addPath(data, page.getSiteId());
        //标题
        String title = page.getTitle();
        title = null == title ? "" : title;
        data.put(DataType.title.name(), title);
        //关键词
        String keywords = page.getKeywords();
        String seo = "";
        if (page.getSeo() != null) {
            seo = page.getSeo();
        }
        keywords = null == keywords ? "" : keywords;
        data.put(DataType.keywords.name(), keywords + seo);

        //页面类型 是cms页面还是给portal页面
        String pageType = String.valueOf(page.getPublicType());
        if ("2".equals(pageType)) {
            pageType = "portal";
        } else if ("3".equals(pageType)) {
            pageType = "mail";
        } else if ("7".equals(pageType)) {
            pageType = "portal";
        } else if ("8".equals(pageType)) {
            pageType = "search";
        }
        this.setPageType(pageType);
        // 描述
        String description = page.getDescribe();
        description = null == description ? "" : description;
        data.put(DataType.description.name(), description);

        return data;
    }

    /**
     * 获取套餐页面数据信息
     */
    private Map<String, Object> getPageRelationDetailData(CmsPage page, Map<String, Object> map) {
        Map<String, Object> data = new HashMap<String, Object>();
        //所有窗口
        List<Object> winList = dataHandler.getWindows(page);
        data.put(DataType.window.name(), winList);
        for (Entry<String, Object> supplierMap : map.entrySet()) {
            if (DataType.productRelationDetail.name().equals(supplierMap.getKey())) {
                data.put(DataType.productRelationDetail.name(), supplierMap.getValue());
            }

            if (DataType.productRelation.name().equals(supplierMap.getKey())) {
                data.put(DataType.productRelation.name(), supplierMap.getValue());
            }
        }

        data = addPath(data, page.getSiteId());
        //标题
        String title = page.getTitle();
        title = null == title ? "" : title;
        data.put(DataType.title.name(), title);
        //关键词
        String keywords = page.getKeywords();
        String seo = "";
        if (page.getSeo() != null) {
            seo = page.getSeo();
        }
        keywords = null == keywords ? "" : keywords;
        data.put(DataType.keywords.name(), keywords + seo);

        //页面类型 是cms页面还是给portal页面
        String pageType = String.valueOf(page.getPublicType());
        if ("2".equals(pageType)) {
            pageType = "portal";
        } else if ("3".equals(pageType)) {
            pageType = "mail";
        } else if ("7".equals(pageType)) {
            pageType = "portal";
        } else if ("8".equals(pageType)) {
            pageType = "search";
        }
        this.setPageType(pageType);
        // 描述
        String description = page.getDescribe();
        description = null == description ? "" : description;
        data.put(DataType.description.name(), description);

        return data;
    }

    /**
     * 获取详细页面数据信息
     *
     * @param page 详细页面模板信息
     * @return 详细页面模板数据
     */
    private Map<String, Object> getPageDetailData(CmsPage page, Object obj) {
        Map<String, Object> data = new HashMap<String, Object>();
        //所有窗口
        List<Object> winList = dataHandler.getWindows(page);
        data.put(DataType.window.name(), winList);
        data.put(DataType.product.name(), obj);
        data = addPath(data, page.getSiteId());
        //标题
        String title = page.getTitle();
        title = null == title ? "" : title;
        data.put(DataType.title.name(), title);
        //关键词
        String keywords = page.getKeywords();
        String seo = "";
        if (page.getSeo() != null) {
            seo = page.getSeo();
        }
        keywords = null == keywords ? "" : keywords;
        data.put(DataType.keywords.name(), keywords + seo);

        //页面类型 是cms页面还是给portal页面
        String pageType = String.valueOf(page.getPublicType());
        if ("2".equals(pageType)) {
            pageType = "portal";
        } else if ("3".equals(pageType)) {
            pageType = "mail";
        }
        this.setPageType(pageType);
        // 描述
        String description = page.getDescribe();
        description = null == description ? "" : description;
        data.put(DataType.description.name(), description);

        return data;
    }

    /**
     * 获取窗口的HTML
     */
    public String supplierWindowHtml(String path, Map<String, Object> data, CmsPage page) throws Exception {
        if (data.isEmpty()) {
            return getHtml(path, MapUtils.EMPTY_MAP);
        }
        if (data.containsKey(DataType.window.name())) {
            List<Object> windows = (List<Object>) data.remove(DataType.window.name());
            for (int i = 0; i < windows.size(); i++) {
                String html = "";
                CmsWindow window = (CmsWindow) windows.get(i);
                if (null != window) {
                    Map<String, Object> windowData = addPath(dataHandler.getSupplierWindowData(window, data), page.getSiteId());
                    String templatePath = PathConstants.windowTemPath(page.getSiteId()) + "/" + window.getWindowId() + ".html";
                    html = supplierWindowHtml(templatePath, windowData, page);
                    data.put(window.getName(), html);
                }

            }
        }
        return getHtml(path, data);
    }

    /**
     * 获取套餐窗口的HTML
     */
    public String pageRelationDetailWindowHtml(String path, Map<String, Object> data, CmsPage page) throws Exception {
        if (data.isEmpty()) {
            return getHtml(path, MapUtils.EMPTY_MAP);
        }
        if (data.containsKey(DataType.window.name())) {
            List<Object> windows = (List<Object>) data.remove(DataType.window.name());
            for (int i = 0; i < windows.size(); i++) {
                String html = "";
                CmsWindow window = (CmsWindow) windows.get(i);
                if (null != window) {
                    Map<String, Object> windowData = addPath(dataHandler.getproductRelationDetailWindowData(window, data), page.getSiteId());
                    String templatePath = PathConstants.windowTemPath(page.getSiteId()) + "/" + window.getWindowId() + ".html";
                    html = pageRelationDetailWindowHtml(templatePath, windowData, page);
                    data.put(window.getName(), html);
                }

            }
        }
        return getHtml(path, data);
    }


    /**
     * 获取窗口的HTML
     */
    public String detailWindowHtml(String path, Map<String, Object> data, Object obj, CmsPage page) throws Exception {
        if (data.isEmpty()) {
            return getHtml(path, MapUtils.EMPTY_MAP);
        }
        if (data.containsKey(DataType.window.name())) {
            List<Object> windows = (List<Object>) data.remove(DataType.window.name());
            for (int i = 0; i < windows.size(); i++) {
                String html = "";
                CmsWindow window = (CmsWindow) windows.get(i);
                if (null != window) {
                    Map<String, Object> windowData = new HashMap<String, Object>();
                    if (window.getParamsType() == 0) {
                        windowData = dataHandler.getSonwindowData(window, obj);
                    } else {
                        windowData = dataHandler.getWindowData(window);
                    }
                    windowData = addPath(windowData, page.getSiteId());
                    String templatePath = PathConstants.windowTemPath(page.getSiteId()) + "/" + window.getWindowId() + ".html";
                    html = detailWindowHtml(templatePath, windowData, obj, page);
                    data.put(window.getName(), html);
                }

            }
        }
        return getHtml(path, data);
    }

    /**
     * 路径
     */
    public Map<String, Object> addPath(Map<String, Object> windowData, Integer siteId) {
        //js、css样式存储路径
        windowData.put(DataType.cssAndJsPath.name(), PathConstants.getSitePath("cssAndJsPath", this.getPageType(), siteId));
        //picture 存储路径
        windowData.put(DataType.picPath.name(), PathConstants.getSitePath("picPath", this.getPageType(), siteId));
        //详细页面 存储路径
        windowData.put(DataType.detailPath.name(), PathConstants.getSitePath("detailPath", this.getPageType(), siteId));
        //广告路径
        windowData.put(DataType.advPath.name(), PathConstants.getSitePath("advPath", this.getPageType(), siteId));
        //搜索路径
        windowData.put(DataType.searchPath.name(), PathConstants.getSitePath("searchPath", this.getPageType(), siteId));
        //搜索路径
        windowData.put(DataType.cmsPath.name(), PathConstants.getSitePath("cmsPath", this.getPageType(), siteId));
        //静态页面存放路径
        windowData.put(DataType.staticPath.name(), PathConstants.getSitePath("staticPath", this.getPageType(), siteId));
        //portal路径
        windowData.put(DataType.portalPath.name(), PathConstants.getSitePath("portalPath", this.getPageType(), siteId));
        //供应商图路径
        windowData.put(DataType.gysPicPath.name(), PathConstants.getSitePath("gysPicPath", this.getPageType(), siteId));
        //供应商路径
        windowData.put(DataType.gysPortalPath.name(), PathConstants.getSitePath("gysPortalPath", this.getPageType(), siteId));

        return windowData;
    }

    /**
     * 生成HTML页面
     */
    public String getHtml(String path, Map<String, Object> data) throws Exception {
        Configuration config = getConfiguration(path);
        if (null == config)
            return null;
        try {
            String templetName = getTempletName(path);
            Template template = config.getTemplate(templetName);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Writer out = new OutputStreamWriter(stream);
            template.process(data, out);
            out.flush();
            String html = stream.toString();
            return html;
        } catch (IOException e) {

            logger.error("使用 " + path + " 模板生成页面失败。", e);

            return null;
        } catch (TemplateException e) {
            logger.error("使用 " + path + " 模板生成页面失败。", e);
            return null;
        }
    }

    private Configuration getConfiguration(String path) {
        // 获取目录
        String pageFolder = getPageFolder(path);
        if (StringUtils.isBlank(pageFolder))
            return null;
        Configuration cfg = null;
        try {
            cfg = FreeMarkerUtil.getFolderCfg(pageFolder);
        } catch (IOException e) {

            logger.error("获取目录：" + pageFolder + " 的FREEMARKER Configuration对象失败", e);
        }
        return cfg;
    }

    /**
     * 获取目录
     */
    private String getPageFolder(String path) {
        int index = path.lastIndexOf(SEPARATOR);
        if (index < 0) {
            logger.error("获取" + path + "模板目录失败。");
            return null;
        }
        return path.substring(0, index);
    }

    /**
     * 获取文件名称
     */
    private String getTempletName(String path) {
        int index = path.lastIndexOf(SEPARATOR);
        if (index < 0) {

            logger.error("获取" + path + "模板目录失败。");
            return null;
        }
        return path.substring(index + 1);
    }

    /**
     * 活动页解析
     */
    public String promotionParse(ViewPromotion promotion, CmsPromotionTask cmsPromotionTask) throws Exception {
        String html = null;


        Map<String, Object> data = getPromotionData(promotion, cmsPromotionTask);

        if (data.get(DataType.activity.name()) == null) {
            logger.error("获取页面数据失败");

            return html;
        }
        return this.getHtml(promotion.getPath(), data);
    }

    /**
     * 获取活动发布数据
     */
    public Map<String, Object> getPromotionData(ViewPromotion promotion, CmsPromotionTask cmsPromotionTask) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, List> data = null;
        this.setPageType("0");

        //获取活动页相关的数据
        data = dataHandler.getActivityData(promotion);
        //图片路径
        dataMap.put(DataType.picPath.name(), PathConstants.getSitePath("picPath", this.getPageType(), cmsPromotionTask.getSiteId()));

        //页面cms图片存放地址
        dataMap.put(DataType.cmsPath.name(), PathConstants.getSitePath("cmsPath", this.getPageType(), cmsPromotionTask.getSiteId()));
        //数据变量
        dataMap.put(DataType.activity.name(), data);
        //详细页面 存储路径
        dataMap.put(DataType.detailPath.name(), PathConstants.getSitePath("detailPath", this.getPageType(), cmsPromotionTask.getSiteId()));
        //活动数据，存有图片路径

        dataMap.put(DataType.promotionData.name(), cmsPromotionTask);

        dataMap.put(DataType.cssAndJsPath.name(), PathConstants.getSitePath("cssAndJsPath", this.getPageType(), cmsPromotionTask.getSiteId()));
        //供应商图路径
        dataMap.put(DataType.gysPicPath.name(), PathConstants.getSitePath("gysPicPath", this.getPageType(), cmsPromotionTask.getSiteId()));
        //供应商路径
        dataMap.put(DataType.gysPortalPath.name(), PathConstants.getSitePath("gysPortalPath", this.getPageType(), cmsPromotionTask.getSiteId()));

        return dataMap;
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }

    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    /*public Map<String, String> getTemplateConfig() {
        return templateConfig;
    }

    public void setTemplateConfig(Map<String, String> templateConfig) {
        this.templateConfig = templateConfig;
    }*/
    public CmsSupplyService getCmsSupplyService() {
        return cmsSupplyService;
    }

    public ProductmainDAO getProductmainDAO() {
        return productmainDAO;
    }

    public CmsSiteService getCmsSiteService() {
        return cmsSiteService;
    }

    public void setCmsSiteService(CmsSiteService cmsSiteService) {
        this.cmsSiteService = cmsSiteService;
    }

    public void setProductmainDAO(ProductmainDAO productmainDAO) {
        this.productmainDAO = productmainDAO;
    }

    public void setCmsSupplyService(CmsSupplyService cmsSupplyService) {
        this.cmsSupplyService = cmsSupplyService;
    }

    public ViewProductInfoService getViewProductInfoService() {
        return viewProductInfoService;
    }

    public void setViewProductInfoService(
            ViewProductInfoService viewProductInfoService) {
        this.viewProductInfoService = viewProductInfoService;
    }
}
