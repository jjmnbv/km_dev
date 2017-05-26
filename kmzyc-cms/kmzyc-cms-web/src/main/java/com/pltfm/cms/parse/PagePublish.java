package com.pltfm.cms.parse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.kmzyc.cms.remote.service.SupplierRemoteService;
import com.kmzyc.product.remote.service.ProductAppraiseRemoteService;
import com.kmzyc.promotion.optimization.vo.PresellProductVO;
import com.kmzyc.promotion.remote.service.PresellInfoRemoteService;
import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.pltfm.app.service.CategoryService;
import com.pltfm.app.service.ProdBrandService;
import com.pltfm.app.service.ProductAttrService;
import com.pltfm.app.service.ProductCertificateFileService;
import com.pltfm.app.service.SuppliersCertificateFileService;
import com.pltfm.app.service.SuppliersCertificateInfoService;
import com.pltfm.app.service.SuppliersRecheckService;
import com.pltfm.app.service.ViewProductDetailInfoService;
import com.pltfm.app.service.ViewProductInfoService;
import com.pltfm.app.service.ViewShopMainService;
import com.pltfm.app.util.AjaxUtil;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ProdBrandExample;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuInfo;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.app.vobject.ViewShopMain;
import com.pltfm.app.vobject.ViewSkuAttr;
import com.pltfm.cms.service.CmsPageService;
import com.pltfm.cms.service.CmsSiteService;
import com.pltfm.cms.service.CmsSupplyService;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.service.DulitaocanService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.util.HtmlCompressor;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsProductRelation;
import com.pltfm.cms.vobject.CmsSite;
import com.pltfm.cms.vobject.CommercialTenantBasicInfo;
import com.pltfm.cms.vobject.ProductCertificateFile;
import com.pltfm.cms.vobject.SuppliersCertificateFile;
import com.pltfm.cms.vobject.SuppliersCertificateInfo;
import com.pltfm.cms.vobject.SuppliersRecheck;

//import com.caucho.hessian.client.HessianProxyFactory;


/**
 * 页面发布
 *
 * @author gongyan
 * @since 2013-09-12
 */
@Component(value = "pagePublish")
@Scope(value = "prototype")
public class PagePublish extends ActionSupport implements Runnable, Preparable {
	private static Logger logger = LoggerFactory.getLogger(PagePublish.class);
    private static final long serialVersionUID = -2526125638862446742L;

    private static final Integer TEMPLATE_TYPE_ONE = 4; //基本模板
    private static final Integer TEMPLATE_TYPE_TWO = 20;//秒杀模板
    private static final Integer TEMPLATE_TYPE_TWREE = 22;//处方模板
    private static final Integer TEMPLATE_TYPE_FOUR = 30;//预售模板

    private static final Integer PUBLISH_TYPE_ONE = 1;// 正式发布
    private static final Integer PUBLISH_TYPE_TWO = 2;//正式预览
    private static final Integer PUBLISH_TYPE_TWREE = 3;//草稿预览


    @Resource(name = "htmlBuilder")
    DefaultHtmlBuilder defaultHtmlBuilder;
    @Resource(name = "cmsPageService")
    private CmsPageService cmsPageService;// cmsPageService接口
    // 产品相关信息接口
    @Resource(name = "viewProductInfoService")
    private ViewProductInfoService viewProductInfoService;
    // 产品相关信息接口
    @Resource(name = "viewProductDetailInfoService")
    private ViewProductDetailInfoService viewProductDetailInfoService;

    // 套餐信息接口
    @Resource(name = "dulitaocanService")
    private DulitaocanService dulitaocanService;

    @Resource
    private PresellInfoRemoteService presellInfoRemoteService;

    @Resource
    private ProductAppraiseRemoteService productAppraiseRemoteService;


    @Resource(name = "prodBrandService")
    private ProdBrandService prodBrandService;
    @Resource(name = "cmsSiteService")
    private CmsSiteService cmsSiteService;// 站点信息接口
    /**
     * 供应商信息业务逻辑接口
     */
    @Resource(name = "cmsSupplyService")
    private CmsSupplyService cmsSupplyService;

    @Resource(name = "viewShopMainService")
    private ViewShopMainService viewShopMainService;

    @Resource(name = "supplierRemoteService")
    private SupplierRemoteService supplierRemoteService;

    @Resource(name = "categoryService")
    private CategoryService categoryService;
    // 获取模板存放路径
   /* @Resource(name = "templateConfig")
    private Map<String, String> templateConfig;*/
    private CmsPage cmsPage;// 页面组件
    /**
     * 产品品牌集合
     **/
    private List<ProdBrand> brandList;
    private ViewProductInfo viewProductInfo;
    // 窗口数据对象
    @Resource(name = "cmsWindowDataService")
    private CmsWindowDataService cmsWindowDataService;
    @Resource(name = "productAttrService")
    private ProductAttrService productAttrService;
    private String checkeds;//多选框
    private Integer adminType;//运营

    @Resource(name = "dataHandler")
    private DataHandler dataHandler;
    private String viewPath;//
    private Integer publishType;

    @Resource
    private ProductCertificateFileService productCertificateFileService;
    @Resource
    private SuppliersCertificateInfoService suppliersCertificateInfoService;
    @Resource
    private SuppliersCertificateFileService suppliersCertificateFileService;
    @Resource
    private SuppliersRecheckService suppliersRecheckService;

    /**
     * 渠道
     */
//    private String channel;

    /**
     * 查询条件渠道
     */
//    private String channelQuery;

    public List<ShopMain> shopMainList;

    public List<Integer> shopIds;

    private Integer templateType;
    private Date promotionsEndDate;
    private String shopName;

    /**
     * 页面发布
     */
    public boolean parse(CmsPage cmsPage) throws Exception {
        if (cmsPage.getPageId() != null) {
            cmsPage = cmsPageService.getCmsPageById(cmsPage.getPageId());
            Assert.notNull(cmsPage);
            Integer siteId = null;
            if (cmsPage.getSiteId() != null) {
                siteId = cmsPage.getSiteId();
            }
            if (cmsPage.getOutputPath() != null && cmsPage.getOutputPath().contains("/")) {
                cmsPage.setJsCssPath(cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf('/') + 1, cmsPage.getOutputPath().lastIndexOf('.')));
            }

            cmsPage.setTemplatePath(PathConstants.pageTemPath(cmsPage.getSiteId()) + "/" + cmsPage.getPageId() + ".html");

            String text = defaultHtmlBuilder.buildHtml(cmsPage);
            if (null != text && !text.equals("")) {
                //修改状态
                CmsPage page = cmsPageService.getCmsPageById(cmsPage.getPageId());
                page.setStatus(1);
                cmsPageService.updateCmsPage(page);
            }
            String outPath = "";
            if (cmsPage.getOutputPath() != null) {
                String path = cmsPage.getOutputPath().substring(0, cmsPage.getOutputPath().lastIndexOf("/"));
                if (!path.equals("")) {
                    outPath = PathConstants.publishPath(siteId) + "/" + path;
                } else {
                    outPath = PathConstants.publishPath(siteId);
                }
            }
            // 判断pages文件夹是否存在，若不存在，则新建一个
            FileOperateUtils.checkAndCreateDirs(outPath);
            String fileOutPath = outPath + "/" + cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf("/"));


            FileOperateUtils.writer(fileOutPath, "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor.compress(text) : text);

//            if (fileOutPath.contains("/index.html")) {
//                //获取用户
//                Map session = ActionContext.getContext().getSession();
//                SysUser sysUser = (SysUser) session.get("sysUser");
//            }

            //发布备份所有相关表和文件
            //  cmsPageService.insertPageOnline(cmsPage);
            return true;
        } else
            return false;
    }


    /**
     * 供应商页面发布
     */
    @SuppressWarnings("unchecked")

    public boolean remoteParse(List<ShopMain> shopMainList) {
//        String url = "";
//        Map map = new HashMap();

        try {
            for (int ji = 0; ji < shopMainList.size(); ji++) {
                ShopMain shopMain = shopMainList.get(ji);
                CmsPage cmsPage = new CmsPage();
                CmsSite cmsSite = new CmsSite();
                cmsSite.setEngName(shopMain.getShopSite());
                CmsSite cmsSite1 = cmsSiteService.querySite(cmsSite);
                Integer siteId = cmsSite1.getSiteId();
                cmsPage.setPublicType(6);
                cmsPage.setSiteId(siteId);
                Map<String, Object> supplierMap = new HashMap<String, Object>();
                supplierMap.put("product", viewProductInfoService.selectByShopCode(shopMain.getSupplierId().toString(), /*cmsSite1.getChannel(),*/ 0, 15));
                supplierMap.put("basicInfo", cmsSupplyService.getUserId(shopMain.getSupplierId()));
                supplierMap.put("suppliersCertificate", cmsSupplyService.getCertificateList(shopMain.getSupplierId()));
                supplierMap.put("shopMain", cmsSupplyService.getShopMain(shopMain.getShopId()));
                List<CmsPage> pageList = cmsPageService.selectByPrimaryPublishType1(cmsPage);
                for (int i = 0; i < pageList.size(); i++) {
                    CmsPage cmsPage1 = pageList.get(i);

                    cmsPage = cmsPageService.getCmsPageById(cmsPage1.getPageId());
                    supplierMap.put("supplyType", cmsPage.getPublicType());
                    if (cmsPage.getOutputPath() != null && cmsPage.getOutputPath().contains("/")) {
                        cmsPage.setJsCssPath(cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf('/') + 1, cmsPage.getOutputPath().lastIndexOf('.')));
                    }

                    cmsPage.setTemplatePath(PathConstants.pageTemPath(cmsPage.getSiteId()) + "/" + cmsPage.getPageId() + ".html");

                    String text = defaultHtmlBuilder.buildSupplierHtml(cmsPage, supplierMap);
                    if (null != text && !text.equals("")) {
                        //修改状态
                        CmsPage page = cmsPageService.getCmsPageById(cmsPage.getPageId());
                        page.setStatus(1);
                        cmsPageService.updateCmsPage(page);
                    }
                    String outPath = "";
                    if (cmsPage.getOutputPath() != null) {
                        String path = cmsPage.getOutputPath().substring(0, cmsPage.getOutputPath().lastIndexOf("/"));
                        if (!path.equals("")) {
                            outPath = PathConstants.publishPath(siteId) + "/" + path;
                        } else {
                            outPath = PathConstants.publishPath(siteId);
                        }
                    }
                    // 判断pages文件夹是否存在，若不存在，则新建一个
                    FileOperateUtils.checkAndCreateDirs(outPath);
                    //String fileOutPath=outPath+ "/"+cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf("/"));
                    String fileOutPath = outPath + "/" +
                            cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf("/") + 1, cmsPage.getOutputPath().lastIndexOf(".")) + "_"
                            + shopMain.getShopId() + cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf("."));
                    FileOperateUtils.writer(fileOutPath, "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor.compress(text) : text);
                    //发布备份所有相关表和文件
                    //cmsPageService.insertPageOnline(cmsPage);
                }
//                url = PathConstants.pageViewPath(siteId).substring(0, PathConstants.pageViewPath(siteId).length() - 4)
//                        + "supply/index_" + shopMain.getShopId() + ".html";
//                map.put(shopMain.getShopId(), url);
            }
            logger.info("供应商页面发布PagePublish.remoteParse成功");
            return true;
        } catch (Exception e) {
            logger.error("供应商页面发布PagePublish.remoteParse报错：" + e);
            return false;
        }

    }

    //商家获取列表
    public String queryShopMainList() {
        try {
            List list = cmsSupplyService.queryShopMainList();
            //  Map<Long,String> m = ProductBrandMap.getMap();
            Map<String, String> mp = new LinkedHashMap<String, String>();
            mp.put("221", "康美自营");
            if (null != list && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    MerchantInfoOrSuppliers MerchantInfoOrSuppliers = (MerchantInfoOrSuppliers) list.get(i);
                    if (MerchantInfoOrSuppliers.getSupplierId().intValue() == 221) continue;
                    mp.put(MerchantInfoOrSuppliers.getSupplierId() + "", MerchantInfoOrSuppliers.getCorporateName());

                }

            }

            AjaxUtil.writeJSONToResponse(mp);
            //super.writeJson(mp);
        } catch (Exception e) {
        	logger.error("PagePublish.queryShopMainList异常：" + e.getMessage(), e);
        }

        return null;

    }

    public boolean parseAll(String checkeds) {

        String id[] = checkeds.split(",");
        int err = 0;
        for (int i = 0; i < id.length; i++) {
            try {
                byIdParse(Integer.parseInt(id[i]));
            } catch (Exception e) {
                err++;
                logger.error("PagePublish.parseAll报错：" + e);
            }
        }

        if (err > 0) {
            return false;

        } else {
            return true;


        }


    }


    public String byIdParse(int id) throws Exception {
        //try{
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageId(id);
        if (cmsPage.getPageId() != null) {
            cmsPage = cmsPageService.getCmsPageById(cmsPage.getPageId());
            if (null != cmsPage) {
                Integer siteId = null;
                if (cmsPage.getSiteId() != null) {
                    siteId = cmsPage.getSiteId();
                }
                if (cmsPage.getOutputPath() != null) {
                    cmsPage.setJsCssPath(cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf('/') + 1, cmsPage.getOutputPath().lastIndexOf('.')));
                }
                cmsPage.setTemplatePath(PathConstants.pageTemPath(siteId) + "/" + cmsPage.getPageId() + ".html");

                String text = defaultHtmlBuilder.buildHtml(cmsPage);
                if (null != text && !text.equals("")) {
                    //修改状态
                    CmsPage page = cmsPageService.getCmsPageById(cmsPage.getPageId());
                    page.setStatus(1);
                    cmsPageService.updateCmsPage(page);
                }
                String outPath = "";
                if (cmsPage.getOutputPath() != null) {
                    String path = cmsPage.getOutputPath().substring(0, cmsPage.getOutputPath().lastIndexOf("/"));
                    if (!path.equals("")) {
                        outPath = PathConstants.publishPath(siteId) + "/" + path;
                    } else {
                        outPath = PathConstants.publishPath(siteId);
                    }
                }
                // 判断pages文件夹是否存在，若不存在，则新建一个
                FileOperateUtils.checkAndCreateDirs(outPath);
                String fileOutPath = outPath + "/" + cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf("/"));

//                if (fileOutPath.contains("/index.html")) {
                //获取用户
//                    Map session = ActionContext.getContext().getSession();
//                    SysUser sysUser = (SysUser) session.get("sysUser");
//                }

                FileOperateUtils.writer(fileOutPath, "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor.compress(text) : text);
                //发布备份所有相关表和文件
                //  cmsPageService.insertPageOnline(cmsPage);
            }
        }

        return null;
    }


    /**
     * 进入发布详细页面功能页面
     */
    @SuppressWarnings("unchecked")
    public String preDetailPublish() {
        try {
            brandList = prodBrandService
                    .selectByExample(new ProdBrandExample());

//            Map session = (Map)ActionContext.getContext().getSession();
//            CmsSite site = null;
//            if(session!=null&&session.get("siteId")!=null){
//                site = PathConstants.getCmsSite((Integer)session.get("siteId"));
//            }
            //页面的渠道查询条件
//            channelQuery = site.getChannel();

        } catch (Exception e) {
            logger.error("PagePublish.preDetailPublish报错：" + e);
        }
        return "preDetail";
    }

    @Override
    public void prepare() throws Exception {
        // 店铺
        shopMainList = cmsSupplyService.queryShopMainList();
    }

    // 获得商品基本信息
    public ViewProductInfo getProductBaseInfo(ViewProductInfo productInfo) throws Exception {

        // 判断产品是否拥有到货属性信息

        // 查询供应商信息
        SuppliersInfo suppliers = cmsSupplyService.getSuppliers(productInfo.getSupplierId());
        if (suppliers != null) {
            productInfo.setSuppliers(suppliers);

            Long supplierId = suppliers.getSupplierId();
            //查询供应商资质重审信息
            List<SuppliersRecheck> suppliersRecheckList = suppliersRecheckService.findListBySupplierId(supplierId);
            SuppliersRecheck suppliersRecheck;
            if (suppliersRecheckList != null && suppliersRecheckList.size() > 0) {
                suppliersRecheck = suppliersRecheckList.get(0);
                productInfo.setSuppliersRecheck(suppliersRecheck);
                Long reviewPass = 2L;
                if (suppliersRecheck != null && reviewPass.equals(suppliersRecheck.getStatus())) {
                    //查询供应商资质信息及资质文件信息
                    List<SuppliersCertificateInfo> suppliersCertificateInfoList = suppliersCertificateInfoService.findListBySupplierId(supplierId);
                    logger.info("########suppliersCertificateInfoList size:" + suppliersCertificateInfoList.size());
                    List<SuppliersCertificateFile> suppliersCertificateFileList;
                    Long infoId;
                    for (int i = 0; i < suppliersCertificateInfoList.size(); i++) {
                        infoId = suppliersCertificateInfoList.get(i).getInfoId();
                        suppliersCertificateFileList = suppliersCertificateFileService.findListByInfoId(infoId);
                        suppliersCertificateInfoList.get(i).setSuppliersCertificateFiles(suppliersCertificateFileList);
                    }
                    productInfo.setSuppliersCertificateInfos(suppliersCertificateInfoList);
                }
            }
        }

        // 供应商信息
        CommercialTenantBasicInfo obj = cmsSupplyService.getUserId(productInfo.getSupplierId());
        if (obj != null) {
            productInfo.setCommercialTenantBasicInfo(obj);
        }
        // 店铺信息(gy150410 修复，通过产品视图获取店铺所有信息)
        ViewShopMain viewShopMain = new ViewShopMain();
        viewShopMain.setShopId(productInfo.getShopId());
        List shopMainlist = viewShopMainService.queryShopMianList(viewShopMain);
        if (null != shopMainlist && shopMainlist.size() > 0) {
            ViewShopMain obj1 = (ViewShopMain) shopMainlist.get(0);
            if (obj1 != null) {
                productInfo.setShopMain(obj1);
            }
        }
        // 通过产品id获取产品品牌信息
        ProdBrand prodBrand = prodBrandService.selectByPrimaryKey(productInfo.getBrandId());
        if (prodBrand != null) {
            productInfo.setProdBrand(prodBrand);
        }
        // 通过产品id获取产品同类品牌信息
        ProdBrand brand = new ProdBrand();
        brand.setCategoryId(productInfo.getCategoryId());
        List otherBrandList = prodBrandService.selectByCategoryIdAndChannel(brand);
        if (ListUtils.isNotEmpty(otherBrandList)) {
            productInfo.setOtherBrandList(otherBrandList);
        }
        // 草稿信息
        if (publishType.equals(PUBLISH_TYPE_TWREE)) {
            // 获取产品草稿除sku、基本属性以外的属性信息
            List detailInfoListDraft = viewProductDetailInfoService.findAttrAndValueByProductIdDraft(Long.valueOf(productInfo.getProductId()));
            if (ListUtils.isNotEmpty(detailInfoListDraft)) {
                productInfo.setProductDetailInfo(detailInfoListDraft);
            }
            // 通过产品主键获取该产品所有sku信息集合
            List<CategoryAttr> listDraft = viewProductInfoService.queryProductDraftSkuInfo(productInfo.getProductId());
            if (ListUtils.isNotEmpty(listDraft)) {
                productInfo.setCategoryAttrList(listDraft);
            }
            // 正式信息
        } else {
            // 获取产品除sku、基本属性以外的属性信息,包括药品属性信息
            List detailInfoList = viewProductDetailInfoService
                    .findAttrAndValueByProductId(Long.valueOf(productInfo.getProductId()));
            if (ListUtils.isNotEmpty(detailInfoList)) {
                productInfo.setProductDetailInfo(detailInfoList);

                productInfo.setProductLists(detailInfoList);
            }

            // 通过产品主键获取该产品所有sku信息集合
            List<CategoryAttr> list =
                    viewProductInfoService.queryProductSkuInfo(productInfo.getProductId());
            if (ListUtils.isNotEmpty(list)) {
                productInfo.setCategoryAttrList(list);
            }

        }
        return productInfo;
    }

    /**
     * 详细页面发布
     */
    @SuppressWarnings("unchecked")
    public synchronized void detailPublish() {
        try {
            //按渠道查询
//            viewProductInfo.setChannel(channel);
            //按照查询条件获取相关产品信息
            List<ViewProductInfo> productList = viewProductInfoService.queryViewProductByExample(viewProductInfo);
            if (ListUtils.isNotEmpty(productList)) {
                for (ViewProductInfo productInfo : productList) {
                    //获取商品基本信息
                    productInfo = getProductBaseInfo(productInfo);

                    //获取产品正式资质文件
                    SuppliersRecheck suppliersRecheck = productInfo.getSuppliersRecheck();
                    Long reviewPass = 2L;
                    if (suppliersRecheck != null && reviewPass.equals(suppliersRecheck.getStatus())) {
                        Long productId = productInfo.getProductId().longValue();
                        List<ProductCertificateFile> productCertificateFileList = productCertificateFileService.findListByProductId(productId);
                        logger.info("#####productCertificateFileList size:" + productCertificateFileList.size());
                        productInfo.setProductCertificateFiles(productCertificateFileList);
                    }

                    //添加秒杀时间
                    if (null != promotionsEndDate) {
                        productInfo.setPromotionsEndDate(promotionsEndDate);
                    }

                    //查询商品下所有SKU的其本属性和图片
                    List<ProductSku> productSkuList = viewProductInfoService.queryProductSkuImage(productInfo.getProductId());
                    if (ListUtils.isNotEmpty(productSkuList)) {
                        //获取每个sku所对象的sku信息集合值
                        List<ProductSkuInfo> skuInfoList = new ArrayList<ProductSkuInfo>();
                        if (null != skuInfoList) {
                            //产品下所有SKUID和类目属性
                            for (ProductSku sku : productSkuList) {
                                ProductSkuInfo skuInfo = new ProductSkuInfo();

                                //SKU所有属性
                                List<ViewSkuAttr> skuViewList = viewProductInfoService.findBySkuId(sku.getProductSkuId());
                                StringBuilder sb = new StringBuilder();
                                if (ListUtils.isNotEmpty(skuViewList)) {
                                    for (int i = 0; i < skuViewList.size(); i++) {
                                        ViewSkuAttr attr = skuViewList.get(i);
                                        if (i != skuViewList.size() - 1) {
                                            sb.append(attr.getCategoryAttrId()).append("_").append(attr.getCategoryAttrValueId()).append(",");
                                        } else {
                                            sb.append(attr.getCategoryAttrId()).append("_").append(attr.getCategoryAttrValueId());
                                        }
                                    }

                                }
                                skuInfo.setCategoryAtrr(sb.toString());
                                skuInfo.setSkuId(sku.getProductSkuId());
                                skuInfoList.add(skuInfo);
                            }
                            productInfo.setSkuInfoList(skuInfoList);

                            for (ProductSku sku : productSkuList) {
                                if (sku.getProductSkuId() == productInfo.getProductSkuId().longValue()) {
                                    //调用促销接口
                                    PresellProductVO PresellProductVO = presellInfoRemoteService.getPresellInfo(sku.getProductSkuId());
                                    if (PresellProductVO != null) {
                                        templateType = TEMPLATE_TYPE_FOUR;
                                    }
                                    //SKU所有属性
                                    List<ViewSkuAttr> viewList = viewProductInfoService.findBySkuId(sku.getProductSkuId());
                                    productInfo.setViewSkuAttrList(viewList != null ? viewList : null);
                                    if (CollectionUtils.isNotEmpty(viewList)) {
                                        sku.setSkuIntroduce(viewList.get(0).getSkuIntroduce());
                                        //仓库
                                        sku.setWarehouseName(viewList.get(0).getWarehouseName());
                                        productInfo.setSkuIntroduce(sku.getSkuIntroduce() != null ? sku.getSkuIntroduce() : null);
                                        //仓库
                                        productInfo.setWarehouseName(sku.getWarehouseName() != null ? sku.getWarehouseName() : null);
                                    }
                                    //是否包邮
                                    productInfo.setFreeStatus(sku.getFreeStatus() != null ? sku.getFreeStatus() : null);
                                    //运费
                                    productInfo.setFreight(sku.getFreight() != null ? sku.getFreight() : new Double(0.00));
                                    //Sku描述
                                    productInfo.setSkudesc(sku.getSkuAttrs() != null ? sku.getSkuAttrs() : null);

                                    if (sku.getProductSkuId() != null) {
                                        productInfo.setProductSkuId(sku.getProductSkuId().intValue());
                                        productInfo.setProductSkuCode(sku.getProductSkuCode());
                                        //通过商品信息查询商品自动标签标签信息
                                        List<String> productAutoTags = cmsWindowDataService.selectProductAutoTags(productInfo);
                                        productInfo.setProductAutoTags(productAutoTags != null ? productAutoTags : null);
                                        //通过商品信息查询商品手动标签标签信息
                                        if (ListUtils.isNotEmpty(productInfo.getProductAttrList())) {
                                            List productTags = cmsWindowDataService.selectProductTags(productInfo.getProductAttrList());
                                            productInfo.setProductTags(productTags);
                                        } else {
                                            productInfo.setProductTags(null);
                                        }

                                        productInfo.setProductSku(sku);
                                        //sku对应图片信息
                                        productInfo.setProductImageList(sku.getProductSkuImages() != null ? sku.getProductSkuImages() : null);
                                        //sku详细套餐
                                        List<CmsProductRelation> relationList1 = dulitaocanService
                                                .selectRelationDetailAll(sku.getProductSkuId(),
                                                        "B2B" /* productInfo.getChannel() */);
                                        productInfo.setCmsRelationList(relationList1 != null && relationList1.size() > 0 ? relationList1 : null);
                                        //sku人气组合信息
                                        List packageList = viewProductInfoService.queryProductPackage(sku.getProductSkuId());
                                        productInfo.setPackageList(packageList != null && packageList.size() > 0 ? packageList : null);
                                        //预售信息
                                        if (null != templateType && templateType.equals(TEMPLATE_TYPE_FOUR)) {

                                            if (PresellProductVO != null) {
                                                //预售价
                                                productInfo.setPresellPrice(PresellProductVO.getPresellPrice().doubleValue());
                                                //定金
                                                productInfo.setDepositPrice(PresellProductVO.getDepositPrice().doubleValue());
                                                //尾款
                                                productInfo.setFinalPrice(PresellProductVO.getFinalPrice().doubleValue());
                                                //支付定金时间段
                                                productInfo.setDepositStartTime(PresellProductVO.getDepositStartTime());
                                                productInfo.setDepositEndTime(PresellProductVO.getDepositEndTime());
                                                //尾款支付时间段
                                                productInfo.setFinalpayStartTime(PresellProductVO.getFinalpayStartTime());
                                                productInfo.setFinalpayEndTime(PresellProductVO.getFinalpayEndTime());
                                                //发货时间段
                                                productInfo.setDeliveryStartTime(PresellProductVO.getDeliveryStartTime());
                                                productInfo.setDeliveryEndTime(PresellProductVO.getDeliveryEndTime());
                                            }


                                        }
                                        //  调取产品接口，评价显示第一页
                                        Map<String, Object> productAppraiseMap = productAppraiseRemoteService.queryProductAppraise(Boolean.TRUE, "desc", sku.getProductSkuId(), 1);
                                        if (productAppraiseMap != null && productAppraiseMap.size() > 0) {
                                            if ((boolean) productAppraiseMap.get("isSuccess")) {
                                                productInfo.setProductAppraiseMap(productAppraiseMap);
                                            }
                                        }

                                        List<CmsSite> siteList = PathConstants.cmsSiteList;
                                        CmsPage cmsPage_in = null;
                                        //   CmsPage cmsPage_out=null;
                                        for (CmsSite cmsSite : siteList) {
                                            // if(productInfo.getChannel().contains(cmsSite.getChannel())){
                                            // 获取详细页面模板路径
                                            cmsPage_in = new CmsPage();
                                            cmsPage_in.setPublicType(templateType);
                                            Integer siteId = cmsSite.getSiteId();
                                            cmsPage_in.setSiteId(siteId);
                                            //获取商品对应的一级物理类目
                                            Integer bCategoryId = Integer.parseInt(ConfigurationUtil.getString("bCategoryId"));
                                            //处方药
                                            if (productInfo.getBcategoryId().intValue() == bCategoryId.intValue()) {
                                                //处方药模板
                                                cmsPage_in.setPublicType(this.TEMPLATE_TYPE_TWREE);
                                            }
                                            List<CmsPage> pageList = cmsPageService.selectByPrimaryPublishType(cmsPage_in);
                                            if (ListUtils.isNotEmpty(pageList)) {
                                                publishPage(pageList, productInfo, sku);
                                                //暂时这样处理
                                            } else {
                                                // 获取详细页面模板路径
                                                cmsPage_in = new CmsPage();
                                                cmsPage_in.setPublicType(this.TEMPLATE_TYPE_ONE);
                                                cmsPage_in.setSiteId(cmsSite.getSiteId());
                                                pageList = cmsPageService.selectByPrimaryPublishType(cmsPage_in);

                                                if (ListUtils.isNotEmpty(pageList)) {
                                                    publishPage(pageList, productInfo, sku);
                                                }
                                            }

                                            //}
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                logger.info("detailPublish成功");
            }else{
                logger.info("detailPublish失败:"+viewProductInfo.toString());
            }

        } catch (Exception e) {
            logger.error("detailPublish报错：", e);


        }
    }

    public synchronized void publishPage(List<CmsPage> pageList, ViewProductInfo productInfo, ProductSku sku) throws Exception {
        Assert.notNull(publishType);
        //  CmsPage cmsPage_out=null;
        CmsPage cmsPage_out = pageList.get(0);
        cmsPage_out.setTemplatePath(PathConstants.pageTemPath(cmsPage_out.getSiteId()) + "/" + cmsPage_out.getPageId() + ".html");
        String text = defaultHtmlBuilder.buildDetailHtml(cmsPage_out, productInfo);

        String outPath = PathConstants.detailPublishPath(cmsPage_out.getSiteId());
        //详情秒杀类型，输出路径与基本的地址不一样
        if (null != templateType && templateType.equals(TEMPLATE_TYPE_TWO)) {
            outPath = PathConstants.promotionPublishPath(cmsPage_out.getSiteId());
        }
        //预览类型
        if (publishType.equals(PUBLISH_TYPE_TWO)) {
            outPath = PathConstants.viewPagePublishPath(cmsPage_out.getSiteId());
        }
        // 判断pages文件夹是否存在，若不存在，则新建一个
        FileOperateUtils.checkAndCreateDirs(outPath);
        //预览类型
        if (publishType.equals(PUBLISH_TYPE_TWO)) {
            // 只返回B2B的页面
            viewPath = ConfigurationUtil.getString("viewPath_B2B") + "/"
                    + sku.getProductSkuId() + ".shtml";
            FileOperateUtils.writer(outPath + "/"
                    + sku.getProductSkuId() + ".shtml", "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor.compress(text) : text);
        } else {
            FileOperateUtils.writer(outPath + "/"
                    + sku.getProductSkuId() + ".shtml", "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor.compress(text) : text);

        }
//        if (PathConstants.getUpholdMap().containsKey(sku.getProductSkuId())) {
//            FileOperateUtils.writer(outPath + "/"
//                    + PathConstants.getUpholdMap().get(sku.getProductSkuId()) + ".shtml", "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor.compress(text) : text);
//        }
        logger.info("publishPage成功skuId:" + sku.getProductId() + "输出路径：" + outPath + "skucode:" + sku.getProductSkuCode());

    }


    /**
     * 产品预售时调时的发布接口
     *
     * @param info 产品实体对象
     * @return success 成功  fail 失败
     */
    @SuppressWarnings("unchecked")
    public String remotePublishYsPage(ViewProductInfo info) {

        try {
            viewProductInfo = info;
            //一般使用预售模板
            templateType = TEMPLATE_TYPE_FOUR;
            //是正式类型类
            publishType = PUBLISH_TYPE_ONE;
            detailPublish();
            logger.info("remotePublishYsPage产品预售时调时的发布接口成功");
            return "success";
        } catch (Exception e) {
            logger.error("remotePublishYsPage产品预售时调时的发布接口：", e);
            return "fail";
        }

    }

    /**
     * 预售结束时调时的发布接口
     *
     * @param info 产品实体对象
     * @return success 成功  fail 失败
     */
    @SuppressWarnings("unchecked")
    public String remotePublishNormalPage(ViewProductInfo info) {

        try {
            viewProductInfo = info;
            //一般使用默认模板
            templateType = TEMPLATE_TYPE_ONE;
            //是正式类型类
            publishType = PUBLISH_TYPE_ONE;
            detailPublish();
            logger.info("remotePublishNormalPage预售结束时调时的发布接口成功");
            return "success";
        } catch (Exception e) {
            logger.error("remotePublishNormalPage预售结束时调时的发布接口：" + e);
            return "fail";
        }

    }


    /**
     * 产品上下架时调时的发布接口
     *
     * @param info 产品实体对象
     * @return success 成功  fail 失败
     */
    @SuppressWarnings("unchecked")
    public String remotePublishDetailPage(ViewProductInfo info) {

        try {
            viewProductInfo = info;

            //一般使用默认模板
            templateType = TEMPLATE_TYPE_ONE;
            //是正式类型类
            publishType = PUBLISH_TYPE_ONE;


            detailPublish();
            logger.info("remotePublishDetailPage产品上下架时调时的发布接口");
            return "success";
        } catch (Exception e) {
            logger.error("remotePublishDetailPage产品上下架时调时的发布接口报错：", e);
            return "fail";
        }

    }

    /**
     * 远程接口预览产品信息
     *
     * @param info 产品实体对象
     * @return success 成功  fail 失败
     */
    public String remoteViewDetailPage(ViewProductInfo info) {
        try {
            viewPath = "";
            viewProductInfo = info;
            //一般使用默认模板
            templateType = TEMPLATE_TYPE_ONE;
            //是正式预览类型类
            publishType = PUBLISH_TYPE_TWO;
            detailPublish();
            logger.info("remoteViewDetailPage远程接口预览产品信息成功");
            return viewPath;
        } catch (Exception e) {
            logger.error("remoteViewDetailPage远程接口预览产品信息报错：" + e);
            return "";
        }

    }

    /**
     * 预览产品草稿状态信息
     */
    public String remoteViewProductDraftPublish(ViewProductInfo info) {
        //页面存放路径
        String pagePath = null;
        //是草稿预览类型类
        publishType = PUBLISH_TYPE_TWREE;
        try {
            //按照查询条件获取相关产品信息
            List<ViewProductInfo> productList = viewProductInfoService.queryViewpProductDraftInfo(info);
            if (ListUtils.isNotEmpty(productList)) {
                for (ViewProductInfo productInfo : productList) {
                    productInfo = getProductBaseInfo(productInfo);
                    //通过产品主键获取产品所有sku以及对应产品图片信息
                    List<ProductSku> skuImage = viewProductInfoService.queryProductDraftSkuImage(productInfo.getProductId());
                    if (ListUtils.isNotEmpty(skuImage)) {
                        //获取每个sku所对象的sku信息集合值
                        List<ProductSkuInfo> skuInfoList = new ArrayList<ProductSkuInfo>();
                        for (ProductSku sku : skuImage) {
                            ProductSkuInfo skuInfo = new ProductSkuInfo();
                            //通过产品sku主键查询产品默认sku信息集合
                            List<ViewSkuAttr> skuViewList = viewProductInfoService.findBySkuDraftId(sku.getProductSkuId());
                            String returnString = "";
                            if (ListUtils.isNotEmpty(skuViewList)) {
                                for (int i = 0; i < skuViewList.size(); i++) {
                                    ViewSkuAttr attr = skuViewList.get(i);
                                    if (i != skuViewList.size() - 1) {
                                        returnString += attr.getCategoryAttrId() + "_" + attr.getCategoryAttrValueId() + ",";
                                    } else {
                                        returnString += attr.getCategoryAttrId() + "_" + attr.getCategoryAttrValueId();
                                    }
                                }
                                sku.setSkuIntroduce(skuViewList.get(0).getSkuIntroduce());
                                sku.setWarehouseName(skuViewList.get(0).getWarehouseName());
                            }
                            skuInfo.setCategoryAtrr(returnString);
                            skuInfo.setSkuId(sku.getProductSkuId());
                            skuInfoList.add(skuInfo);
                        }
                        productInfo.setSkuInfoList(skuInfoList);
                        for (ProductSku sku : skuImage) {
                            //通过产品sku主键查询产品默认sku信息集合
                            List<ViewSkuAttr> viewList = viewProductInfoService.findBySkuDraftId(sku.getProductSkuId());
                            if (ListUtils.isNotEmpty(viewList)) {
                                productInfo.setViewSkuAttrList(viewList);
                                sku.setSkuIntroduce(viewList.get(0).getSkuIntroduce());
                                //仓库
                                sku.setWarehouseName(viewList.get(0).getWarehouseName());
                                productInfo.setSkuIntroduce(sku.getSkuIntroduce() != null ? sku.getSkuIntroduce() : null);
                                //仓库
                                productInfo.setWarehouseName(sku.getWarehouseName() != null ? sku.getWarehouseName() : null);

                            } else {
                                productInfo.setViewSkuAttrList(null);
                            }

                            if (sku.getProductSkuId() != null) {
                                productInfo.setProductSkuId(sku.getProductSkuId().intValue());
                                productInfo.setProductSkuCode(sku.getProductSkuCode());
                                //通过商品信息查询商品自动标签标签信息
                                List<String> productAutoTags = cmsWindowDataService.selectProductAutoTags(productInfo);
                                if (ListUtils.isNotEmpty(productAutoTags)) {
                                    productInfo.setProductAutoTags(productAutoTags);
                                } else {
                                    productInfo.setProductAutoTags(null);
                                }
                                //通过商品信息查询商品手动标签标签信息
                                if (ListUtils.isNotEmpty(productInfo.getProductAttrList())) {
                                    List<String> productTags = cmsWindowDataService.selectProductTags(productInfo.getProductAttrList());
                                    productInfo.setProductTags(productTags);
                                } else {
                                    productInfo.setProductTags(null);
                                }

                                productInfo.setProductSku(sku);
                                //获取每个sku对应的产品图片信息
                                if (ListUtils.isNotEmpty(sku.getProductSkuImages())) {
                                    productInfo.setProductImageList(sku.getProductSkuImages());
                                } else {
                                    productInfo.setProductImageList(null);
                                }

                                List<CmsProductRelation> relationList1 = dulitaocanService
                                        .selectRelationDetailAll(sku.getProductSkuId(),
                                                "B2B" /* productInfo.getChannel() */);
                                if (ListUtils.isNotEmpty(relationList1)) {
                                    productInfo.setCmsRelationList(relationList1);
                                } else {
                                    productInfo.setCmsRelationList(null);
                                }
                                List packageList = viewProductInfoService.queryProductPackage(sku.getProductSkuId());
                                //获取每个sku对应的产品人气组合信息
                                if (ListUtils.isNotEmpty(packageList)) {
                                    productInfo.setPackageList(packageList);
                                } else {
                                    productInfo.setPackageList(null);
                                }

                                List<CmsSite> siteList = PathConstants.cmsSiteList;
                                for (CmsSite cmsSite : siteList) {
                                    //if(productInfo.getChannel().contains(cmsSite.getChannel())){
                                    // 获取详细页面模板路径
                                    CmsPage page = new CmsPage();
                                    page.setSiteId(cmsSite.getSiteId());
                                    page.setPublicType(4);


                                    //获取商品对应的一级物理类目
                                    Integer bCategoryId = Integer.parseInt(ConfigurationUtil.getString("bCategoryId"));
                                    //处方药
                                    if (productInfo.getBcategoryId().intValue() == bCategoryId.intValue()) {
                                        //处方药模板
                                        page.setPublicType(this.TEMPLATE_TYPE_TWREE);
                                    }
                                    List<CmsPage> pageList = cmsPageService.selectByPrimaryPublishType(page);
                                    if (ListUtils.isNotEmpty(pageList)) {
                                        cmsPage = pageList.get(0);
                                        cmsPage.setTemplatePath(PathConstants.pageTemPath(cmsPage.getSiteId()) + "/" + cmsPage.getPageId() + ".html");
                                        String text = defaultHtmlBuilder.buildDetailHtml(cmsPage,
                                                productInfo);
                                        String outPath = PathConstants.viewPagePublishPath(cmsPage.getSiteId());
                                        // 判断pages文件夹是否存在，若不存在，则新建一个
                                        FileOperateUtils.checkAndCreateDirs(outPath);
                                        FileOperateUtils.writer(outPath + "/"
                                                + sku.getProductSkuId() + ".shtml", "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor.compress(text) : text);
                                        if (pagePath == null) {
                                            // 预览取B2B
                                            pagePath = ConfigurationUtil.getString("viewPath_B2B") + "/"
                                                    + sku.getProductSkuId() + ".shtml";
                                        }

                                    }
                                    //}
                                }
                            }
                        }
                    }
                }
            }

            return pagePath;
        } catch (Exception e) {
            logger.error("PagePublish.remoteViewProductDraftPublish报错：" + e);
            return "fail";
        }
    }


    /**
     * 根据产品列表搜索所有需重新发布的页面
     */
    public String remotePublishAllPage(String sqlParam, List<Integer> cmsPageList) {
        //涉及到产品的页面列表
        List<Integer> listPage = null;
        try {
            //所有页面
            if (null != cmsPageList && 0 < cmsPageList.size()) {
                listPage = cmsPageList;
                //部分页面
            } else {
                listPage = cmsWindowDataService.queryAllPage(sqlParam);
            }

            if (null != listPage && listPage.size() > 0) {
                for (Integer pageId : listPage) {
                    cmsPage = cmsPageService.getCmsPageById(pageId);
                    if (null != cmsPage) {
                        parse(cmsPage);
                    }
                }
                logger.info("搜索所有需重新发布的页面PagePublish.remotePublishAllPage成功");
            }

        } catch (Exception e) {
            logger.error("PagePublish.remotePublishAllPage报错：" + e);
        }
        return "fail";
    }


    /**
     * 首页页面发布
     */
    public void rankPublish(CmsPage cmsPage) {
        try {
            if (cmsPage != null) {
                Integer siteId = null;
                if (cmsPage.getSiteId() != null) {
                    siteId = cmsPage.getSiteId();
                }
                cmsPage.setTemplatePath(PathConstants.pageTemPath(siteId) + "/" + cmsPage.getPageId() + ".html");
                String text = defaultHtmlBuilder.buildHtml(cmsPage);
                String outPath = "";
                if (cmsPage.getOutputPath() != null) {
                    String path = cmsPage.getOutputPath().substring(0, cmsPage.getOutputPath().lastIndexOf("/"));
                    if (!path.equals("")) {
                        outPath = PathConstants.publishPath(siteId) + "/" + path;
                    } else {
                        outPath = PathConstants.publishPath(siteId);
                    }
                }
                // 判断pages文件夹是否存在，若不存在，则新建一个
                FileOperateUtils.checkAndCreateDirs(outPath);
                FileOperateUtils.writer(outPath + "/" + cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf("/")), "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor.compress(text) : text);
            }
        } catch (Exception e) {
            logger.error("PagePublish.rankPublish报错：" + e);
        }
    }

    /**
     * 页面预览
     */
    public void preview() {
        HttpServletResponse response = ServletActionContext.getResponse();
        try {
            if (cmsPage.getPageId() != null) {
                cmsPage = cmsPageService.getCmsPageById(cmsPage.getPageId());
                if (cmsPage.getOutputPath() != null) {
                    cmsPage.setJsCssPath(cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf('/') + 1, cmsPage.getOutputPath().lastIndexOf('.')));
                }
                Integer siteId = null;
                if (cmsPage.getSiteId() != null) {
                    siteId = cmsPage.getSiteId();
                }
                cmsPage.setTemplatePath(PathConstants.pageTemPath(siteId) + "/" + cmsPage.getPageId() + ".html");
                String text = defaultHtmlBuilder.buildHtml(cmsPage);
                String outPath = "";
                String savePath = "";
                if (cmsPage.getOutputPath() != null) {
                    String path = cmsPage.getOutputPath().substring(0, cmsPage.getOutputPath().lastIndexOf("/"));
                    if (!path.equals("")) {
                        outPath = PathConstants.viewPagePublishPath(siteId) + path;
                        savePath = path;
                    } else {
                        outPath = PathConstants.viewPagePublishPath(siteId);
                    }
                }
                // 判断文件夹是否存在，若不存在，则新建一个
                FileOperateUtils.checkAndCreateDirs(outPath);
                //预览
                if (StringUtils.isNotEmpty(savePath)) {
                    //写入文件
                    FileOperateUtils.writer(outPath + cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf("/")), "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor.compress(text) : text);
                    response.sendRedirect(PathConstants.pageViewPath(siteId) + savePath + cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf("/")));
                } else {
                    //写入文件
                    FileOperateUtils.writer(outPath + cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf("/")), "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor.compress(text) : text);
                    response.sendRedirect(PathConstants.pageViewPath(siteId) + cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf("/")));
                }
            }

        } catch (Exception e) {
            logger.error("PagePublish.preview报错：" + e);
        }
    }

    /**
     * 页面可视化
     */
    public void preVisualization() {
        HttpServletResponse response = ServletActionContext.getResponse();
        try {
            if (cmsPage.getPageId() != null) {
                cmsPage = cmsPageService.getCmsPageById(cmsPage.getPageId());
                if (cmsPage.getOutputPath() != null) {
                    cmsPage.setJsCssPath(cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf('/') + 1, cmsPage.getOutputPath().lastIndexOf('.')));
                }
                Integer siteId = null;
                if (cmsPage.getSiteId() != null) {
                    siteId = cmsPage.getSiteId();
                }
                cmsPage.setTemplatePath(PathConstants.pageTemPath(siteId) + "/" + cmsPage.getPageId() + ".html");
                String text = defaultHtmlBuilder.buildHtml(cmsPage);
                String outPath = "";
                String savePath = "";
                if (cmsPage.getOutputPath() != null) {
                    String path = cmsPage.getOutputPath().substring(0, cmsPage.getOutputPath().lastIndexOf("/"));
                    if (!path.equals("")) {
                        outPath = PathConstants.visualizationPagePublishPath(siteId) + path;
                        savePath = path;
                    } else {
                        outPath = PathConstants.visualizationPagePublishPath(siteId);
                    }
                }
                // 判断文件夹是否存在，若不存在，则新建一个
                FileOperateUtils.checkAndCreateDirs(outPath);
                text += "<script type='text/javascript'>seajs.use('<!--#echo var='cssAndJsPath' -->/<!--#echo var='channel' -->/<!--#echo var='jsUrl' -->/view/admin.js');</script>";
                //预览 
                if (!"".equals(savePath)) {
                    //写入文件
                    FileOperateUtils.writer(outPath + cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf("/")), "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor.compress(text) : text);
                    response.sendRedirect(PathConstants.pageVisualizationPath(siteId) + savePath + cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf("/")));
                } else {
                    //写入文件
                    FileOperateUtils.writer(outPath + cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf("/")), "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor.compress(text) : text);
                    response.sendRedirect(PathConstants.pageVisualizationPath(siteId) + cmsPage.getOutputPath().substring(cmsPage.getOutputPath().lastIndexOf("/")));
                }
            }
        } catch (Exception e) {
            logger.error("PagePublish.preVisualization报错：" + e);
        }
    }


    public DefaultHtmlBuilder getDefaultHtmlBuilder() {
        return defaultHtmlBuilder;
    }

    public void setDefaultHtmlBuilder(DefaultHtmlBuilder defaultHtmlBuilder) {
        this.defaultHtmlBuilder = defaultHtmlBuilder;
    }

    public CmsPage getCmsPage() {
        return cmsPage;
    }

    public void setCmsPage(CmsPage cmsPage) {
        this.cmsPage = cmsPage;
    }

    public CmsPageService getCmsPageService() {
        return cmsPageService;
    }

    public void setCmsPageService(CmsPageService cmsPageService) {
        this.cmsPageService = cmsPageService;
    }

    /* public Map<String, String> getTemplateConfig() {
         return templateConfig;
     }

     public void setTemplateConfig(Map<String, String> templateConfig) {
         this.templateConfig = templateConfig;
     }
 */
    public ViewProductInfoService getViewProductInfoService() {
        return viewProductInfoService;
    }

    public void setViewProductInfoService(
            ViewProductInfoService viewProductInfoService) {
        this.viewProductInfoService = viewProductInfoService;
    }

    public ProdBrandService getProdBrandService() {
        return prodBrandService;
    }

    public void setProdBrandService(ProdBrandService prodBrandService) {
        this.prodBrandService = prodBrandService;
    }

    public List<ProdBrand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<ProdBrand> brandList) {
        this.brandList = brandList;
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


    public CategoryService getCategoryService() {
        return categoryService;
    }


    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public String getCheckeds() {
        return checkeds;
    }

    public void setCheckeds(String checkeds) {
        this.checkeds = checkeds;
    }


    public ProductAttrService getProductAttrService() {
        return productAttrService;
    }


    public void setProductAttrService(ProductAttrService productAttrService) {
        this.productAttrService = productAttrService;
    }


    public Integer getAdminType() {
        return adminType;
    }


    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
    }

//    public String getChannel() {
//        return channel;
//    }
//    public void setChannel(String channel) {
//        this.channel = channel;
//    }


//    public String getChannelQuery() {
//        return channelQuery;
//    }
//
//
//    public void setChannelQuery(String channelQuery) {
//        this.channelQuery = channelQuery;
//    }


    public CmsSupplyService getCmsSupplyService() {
        return cmsSupplyService;
    }


    public void setCmsSupplyService(CmsSupplyService cmsSupplyService) {
        this.cmsSupplyService = cmsSupplyService;
    }

    public CmsSiteService getCmsSiteService() {
        return cmsSiteService;
    }


    public void setCmsSiteService(CmsSiteService cmsSiteService) {
        this.cmsSiteService = cmsSiteService;
    }

    public SupplierRemoteService getSupplierRemoteService() {
        return supplierRemoteService;
    }


    public void setSupplierRemoteService(SupplierRemoteService supplierRemoteService) {
        this.supplierRemoteService = supplierRemoteService;
    }


    @Override
    public void run() {
        this.detailPublish();

    }


    public List<ShopMain> getShopMainList() {
        return shopMainList;
    }


    public void setShopMainList(List<ShopMain> shopMainList) {
        this.shopMainList = shopMainList;
    }


    public List<Integer> getShopIds() {
        return shopIds;
    }


    public void setShopIds(List<Integer> shopIds) {
        this.shopIds = shopIds;
    }

    public ViewProductDetailInfoService getViewProductDetailInfoService() {
        return viewProductDetailInfoService;
    }


    public void setViewProductDetailInfoService(
            ViewProductDetailInfoService viewProductDetailInfoService) {
        this.viewProductDetailInfoService = viewProductDetailInfoService;
    }


    public DulitaocanService getDulitaocanService() {
        return dulitaocanService;
    }


    public void setDulitaocanService(DulitaocanService dulitaocanService) {
        this.dulitaocanService = dulitaocanService;
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


    public ViewShopMainService getViewShopMainService() {
        return viewShopMainService;
    }


    public void setViewShopMainService(ViewShopMainService viewShopMainService) {
        this.viewShopMainService = viewShopMainService;
    }


    public String getShopName() {
        return shopName;
    }


    public void setShopName(String shopName) {
        this.shopName = shopName;
    }


    public String getViewPath() {
        return viewPath;
    }


    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }


    public Integer getPublishType() {
        return publishType;
    }


    public void setPublishType(Integer publishType) {
        this.publishType = publishType;
    }


}
