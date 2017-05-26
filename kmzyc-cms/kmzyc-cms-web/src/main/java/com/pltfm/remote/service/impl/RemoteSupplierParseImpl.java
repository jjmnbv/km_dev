package com.pltfm.remote.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.kmzyc.cms.remote.service.RemoteSupplierParseService;
import com.kmzyc.cms.remote.service.SupplierRemoteService;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.service.CategoryService;
import com.pltfm.app.service.ProdBrandService;
import com.pltfm.app.service.ProductAttrService;
import com.pltfm.app.service.ViewProductDetailInfoService;
import com.pltfm.app.service.ViewProductInfoService;
import com.pltfm.app.util.Constants;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.cms.dao.CmsShopDataDAO;
import com.pltfm.cms.dao.CmsThemeDAO;
import com.pltfm.cms.dao.CmsThemeTemplateDAO;
import com.pltfm.cms.dao.CmsWindowDAO;
import com.pltfm.cms.dao.CmsWindowDataDAO;
import com.pltfm.cms.parse.DefaultHtmlBuilder;
import com.pltfm.cms.parse.PagePublish;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsAdcolumnDataService;
import com.pltfm.cms.service.CmsAdvService;
import com.pltfm.cms.service.CmsPageService;
import com.pltfm.cms.service.CmsSiteService;
import com.pltfm.cms.service.CmsSupplierAdcolumnService;
import com.pltfm.cms.service.CmsSupplyService;
import com.pltfm.cms.service.CmsTemplateServce;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.util.HtmlCompressor;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsShopData;
import com.pltfm.cms.vobject.CmsSite;
import com.pltfm.cms.vobject.CmsTemplate;
import com.pltfm.cms.vobject.CmsTheme;
import com.pltfm.cms.vobject.CmsThemeTemplate;
import com.pltfm.cms.vobject.CmsWindow;
import com.pltfm.cms.vobject.CmsWindowData;

@Component(value = "remoteSupplierParseService")
public class RemoteSupplierParseImpl implements RemoteSupplierParseService {
	private static Logger logger = LoggerFactory.getLogger(RemoteSupplierParseImpl.class);
    private static final long serialVersionUID = -2526125638862446742L;
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
    @Resource(name = "prodBrandService")
    private ProdBrandService prodBrandService;
    @Resource(name = "cmsSiteService")
    private CmsSiteService cmsSiteService;// 站点信息接口
    @Resource(name = "cmsSupplierAdcolumnService")
    CmsSupplierAdcolumnService cmsSupplierAdcolumnService;
    @Resource(name = "supplierRemoteService")
    private SupplierRemoteService supplierRemoteService;
    @Resource(name = "cmsAdcolumnDataService")
    CmsAdcolumnDataService cmsAdcolumnDataService;
    @Resource(name = "categoryService")
    private CategoryService categoryService;
    // 获取模板存放路径
/*	@Resource(name = "templateConfig")
    private Map<String, String> templateConfig;*/
    /**
     * 产品品牌集合
     **/
    private List<ProdBrand> brandList;
    private ViewProductInfo viewProductInfo;
    // 窗口数据对象
    @Resource(name = "cmsTemplateServce")
    private CmsTemplateServce cmsTemplateServce;//cmsTemplateService接口


    @Resource(name = "cmsWindowDataService")
    private CmsWindowDataService cmsWindowDataService;
    @Resource(name = "productAttrService")
    private ProductAttrService productAttrService;
    @Resource(name = "cmsAdvService")
    CmsAdvService cmsAdvService;

    @Resource(name = "pagePublish")
    private PagePublish pagePublish;

    private String checkeds;// 多选框
    private Integer adminType;// 运营

    private Integer ashop_Type;// 模板类型


    // cms关联店铺表
    @Resource(name = "cmsShopDataDAO")
    private CmsShopDataDAO cmsShopDataDAO;
    /**
     * 窗口数据DAO接口
     */
    @Resource(name = "cmsWindowDataDAO")
    private CmsWindowDataDAO cmsWindowDataDAO;

    /**
     * 供应商信息业务逻辑接口
     */
    @Resource(name = "cmsSupplyService")
    private CmsSupplyService cmsSupplyService;
    /**
     * 供应商模板查询
     */
    @Resource(name = "cmsThemeDAO")
    private CmsThemeDAO cmsThemeDAO;

    /**
     * 窗口DAO层接口
     */
    @Resource(name = "cmsWindowDAO")
    private CmsWindowDAO cmsWindowDAO;

    /**
     * 模板DAO层接口
     */
    @Resource(name = "cmsThemeTemplateDAO")
    private CmsThemeTemplateDAO cmsThemeTemplateDAO;

    /**
     * 主题类型 :6供应商动态头部
     **/
    static final Integer CMSTHEME_TYPE_SIX = 6;

    /**
     * 页面变量模板
     **/

    static final Integer TEMPLATE_TYPE_TWELVE = 12;

    /**
     * CMS_SHOP_DATA表 PAGE_WINDOW1为页面
     ***/

    static final Integer PAGEWINDOW_ONE = 1;

    /**
     * CMS_SHOP_DATA表  THEMETYPE  模板：2默认 3简版 4其他, 5头部6，动态头部
     ***/
    static final Integer CMSSHOPDATA_THEMETYPE_FIVE = 5;

    //CMS_WINDOW_DATA 表里数据

    /***自定义数据
     * */
    static final Integer DARATYPE_SIX = 6;

    /***
     * 供应商信息
     * */
    static final Integer DARATYPE_ELEVEN = 11;

    /***
     * cms关联供应商信息
     * */
    static final Integer DARATYPE_TWELVE = 12;

    /***
     * 产品显示行数
     * 初始化店铺默认模板下方新产品的默认显示的行数
     * */
    public int lineNumber(CmsShopData cmsShop) throws Exception {
        int lineNumber = 0;
        if (cmsShop.getThemeType().equals(2)) { //判断模板类型：2默认 3简版 4其他, 5头部6，动态头部
            lineNumber = 16;
            CmsWindow window = new CmsWindow();
            String tilet = "whole" + cmsShop.getPageId().toString();
            window.setName(tilet);
            window.setSiteId(cmsShop.getSiteId());
            window = cmsWindowDAO.getWindows(window); //跟据站点和NAME来取得供应商排行窗口
            if (window != null) {
                CmsWindowData cmsWindowData = new CmsWindowData();
                cmsWindowData.setWindowId(window.getWindowId());
                cmsWindowData.setDataType(DARATYPE_TWELVE);
                cmsWindowData = cmsWindowDataDAO.queryWindowData(cmsWindowData);
                if (cmsWindowData != null) {
                    if (cmsWindowData.getDataId() != null) {
                        cmsShop = cmsShopDataDAO.select(cmsWindowData.getDataId());
                        lineNumber = cmsShop.getLineNumber();// 取得数据库里产品显示行数
                    }
                }
            }

        }
        return lineNumber;//初始化行数 16
    }

    /***
     * portal 头部
     * 因为CMS这边要出版头部到搜索那边，因此CMS这边的静头部的所以有数据必须复制到搜索头部
     * */
    public void pageWind(ShopMain shopMain, CmsShopData cmsShop, CmsPage page) throws Exception {
        CmsPage page2 = new CmsPage();
        page2.setSiteId(cmsShop.getSiteId());
        page2.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
        CmsTheme cmsThemes = new CmsTheme();
        cmsThemes.setSiteId(cmsShop.getSiteId());
        cmsThemes.setType(CMSTHEME_TYPE_SIX);//付值头部变量

        CmsTheme cmsTheme = cmsThemeDAO.selectThemeType(cmsThemes);//供应商模板查询

        CmsThemeTemplate t = new CmsThemeTemplate();
        t.setType(TEMPLATE_TYPE_TWELVE);
        t.setThemeId(cmsTheme.getThemeId());
        t = cmsThemeTemplateDAO.getByThemeTemp(t);
        CmsTemplate cmsTemplate = this.cmsTemplateServce.getTemplateById(t.getTemplateId());//跟据ID查询模板

        page2.setContent(cmsTemplate.getContent());
        String ur = cmsTheme.getName();

        ur = ur.substring(0, ur.lastIndexOf("/")) + "/" + shopMain.getShopId() + ur.substring(ur.lastIndexOf("/"));//截取URL路径

        page2.setOutputPath(ur);

        CmsShopData cms = new CmsShopData();
        cms.setShopId(shopMain.getShopId().intValue());
        cms.setSiteId(cmsShop.getSiteId());
        cms.setSupplierId(shopMain.getSupplierId().intValue());
        cms.setPageWindow(PAGEWINDOW_ONE);//付值变量为1代表页面
        cms.setThemeType(cmsTheme.getType());
        CmsShopData cmsShopDataList = cmsShopDataDAO.select2(cms);//查询 CmsShopData 表的数据

        CmsShopData cms1 = new CmsShopData();
        cms1.setShopId(shopMain.getShopId().intValue());
        cms1.setSiteId(cmsShop.getSiteId());
        cms1.setSupplierId(Long.valueOf(shopMain.getSupplierId()).intValue());
        cms1.setPageWindow(PAGEWINDOW_ONE);//付值变量为1代表页面
        cms1.setThemeType(CMSSHOPDATA_THEMETYPE_FIVE);//THEMETYPE 模板：2默认 3简版 4其他, 5头部6，动态头部
        CmsShopData cm = cmsShopDataDAO.select2(cms1);


        if (cmsShopDataList == null) { //查询数据库里是否存在此店铺信息

            Integer id = this.cmsPageService.addOthTop(page2, cm.getPageId());
            cms.setPageId(id);
            page2.setPageId(id);
            cmsShopDataDAO.insert(cms);//添加头部信息


        } else {
            page2.setPageId(cmsShopDataList.getPageId());
            cmsPageService.upOthTop(page2, cm.getPageId());//更新头部信息
        }


    }


    /**
     * 新供应商店铺页面发布
     */
    @Override
    public String remoteParse(ShopMain shopMain, int themeType) throws Exception {
        String url = null;
        CmsPage page = new CmsPage();
        CmsSite cmsSite = new CmsSite();
        try {

            if(null != shopMain && StringUtil.isEmpty(shopMain.getShopSite())){
                shopMain.setShopSite(Constants.SET_B2B);
            }

            cmsSite.setEngName(shopMain.getShopSite());
            cmsSite = cmsSiteService.querySite(cmsSite);

            CmsShopData cmsShop = new CmsShopData();
            cmsShop.setShopId(shopMain.getShopId().intValue());
            cmsShop.setSiteId(Long.valueOf(cmsSite.getSiteId()).intValue());
            cmsShop.setThemeType(themeType);
            page.setSiteId(cmsSite.getSiteId());

            pageWind(shopMain, cmsShop, page);//调用  pageWind（）方法

            List cmsShopList = cmsShopDataDAO.selectAll3(cmsShop);
            if (cmsShopList != null) {
                for (int i = 0; i < cmsShopList.size(); i++) {
                    cmsShop = (CmsShopData) cmsShopList.get(i);
                    page.setSiteId(cmsShop.getSiteId());
                    page = cmsPageService.getCmsPageById(cmsShop.getPageId());
                    Assert.notNull(page);
                    if (page.getPageId() != null) {

                        if (page.getOutputPath() != null) {
                            page.setJsCssPath(page.getOutputPath().substring(
                                    page.getOutputPath().lastIndexOf('/') + 1,
                                    page.getOutputPath().lastIndexOf('.')));
                        }
                        Integer siteId = null;
                        if (page.getSiteId() != null) {
                            siteId = page.getSiteId();
                        }
                        page.setCmsShopId(shopMain.getShopId());
                        page.setTemplatePath(PathConstants.pageTemPath(siteId)
                                + "/" + page.getPageId() + ".html");


                        page.setThemeType(1);//正常发布的页面

                        int lineNumber = this.lineNumber(cmsShop);//取得产品行数
                        page.setCmsShop(cmsShop);
                        page.setLineNumber(lineNumber);
                        if (cmsShop.getThemeType().equals(6)) { //是否动态头部
                            page.setPublicType(5);//发给search
                        }
                        page.setShopType(themeType);//店铺模板类型
                        String text = defaultHtmlBuilder.buildHtml(page);
                        String outPath = "";
                        if (page.getOutputPath() != null) {

                            String path = page.getOutputPath().substring(0,
                                    page.getOutputPath().lastIndexOf("/"));


                            if (!path.equals("")) {
                                outPath = PathConstants.publishPath(siteId)
                                        + "/" + path;
                            } else {
                                outPath = PathConstants.publishPath(siteId);
                            }

                        }
                        // 判断pages文件夹是否存在，若不存在，则新建一个
                        FileOperateUtils.checkAndCreateDirs(outPath);

                        String fileOutPath = outPath
                                + "/"
                                + page.getOutputPath().substring(
                                page.getOutputPath().lastIndexOf("/") + 1,
                                page.getOutputPath().lastIndexOf("."))
                                + page.getOutputPath().substring(
                                page.getOutputPath().lastIndexOf("."));
                        FileOperateUtils
                                .writer(
                                        fileOutPath,
                                        "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor
                                                .compress(text)
                                                : text);


                        // 发布备份所有相关表和文件
                        //cmsPageService.insertPageOnline(page);
                        // url =  PathConstants.pageViewPath(siteId).substring(0, PathConstants.pageViewPath(siteId).length() - 4)+"supply/index_"+ shopMain.getShopId() + ".html";
                        url = PathConstants.pageViewPath(siteId).substring(0, PathConstants.pageViewPath(siteId).length() - 4) + "supply/" + shopMain.getShopId() + "/index.html";
                    }

                }
            }
        } catch (Exception e) {
        	logger.error("RemoteSupplierParseImpl.remoteParse异常：" + e.getMessage(), e);
        }

        return url;
    }


    /**
     * 预览给供应商
     *
     * @author gwl
     *
     * 参数1：ShopMain 对象  参数 2int themeType 模板  2默认 3简版 key店铺ID 值为URL
     */
    @Override
    public String remoteViweParse(ShopMain shopMain, int themeType) {
        String url = null;

        try {

            if(null != shopMain && StringUtil.isEmpty(shopMain.getShopSite())){
                shopMain.setShopSite(Constants.SET_B2B);
            }

            CmsSite cmsSite = new CmsSite();
            cmsSite.setEngName(shopMain.getShopSite());
            cmsSite = cmsSiteService.querySite(cmsSite);

            CmsShopData cmsShop = new CmsShopData();
            cmsShop.setShopId(shopMain.getShopId().intValue());
            cmsShop.setSiteId(Long.valueOf(cmsSite.getSiteId()).intValue());
            cmsShop.setThemeType(themeType);
            List cmsShopList = cmsShopDataDAO.selectAll3(cmsShop);//查询CmsShopData表
            if (cmsShopList != null) {//返回数据集合是否为空
                CmsPage page;
                for (int i = 0; i < cmsShopList.size(); i++) {
                    cmsShop = (CmsShopData) cmsShopList.get(i);
                    page = cmsPageService.getCmsPageById(cmsShop.getPageId());
                    Assert.notNull(page);

                    if (page.getPageId() != null) {

                        if (page.getOutputPath() != null) {
                            page.setJsCssPath(page.getOutputPath().substring(
                                    page.getOutputPath().lastIndexOf('/') + 1,
                                    page.getOutputPath().lastIndexOf('.')));
                        }
                        Integer siteId = null;
                        if (page.getSiteId() != null) {
                            siteId = page.getSiteId();
                        }
                        page.setCmsShopId(shopMain.getShopId());

                        page.setTemplatePath(PathConstants.pageTemPath(siteId)
                                + "/" + page.getPageId() + ".html");


                        page.setThemeType(3);//正常预览的页面

                        int lineNumber = this.lineNumber(cmsShop);
                        page.setCmsShop(cmsShop);
                        page.setLineNumber(lineNumber);
                        if (cmsShop.getThemeType().equals(6)) { //是否动态头部
                            page.setPublicType(5);//发给search
                        }
                        page.setShopType(themeType);//店铺模板类型
                        String text = defaultHtmlBuilder.buildHtml(page);
                        String outPath = "";
                        if (page.getOutputPath() != null) {
                            String path = "/view" + page.getOutputPath().substring(0,
                                    page.getOutputPath().lastIndexOf("/"));
                            if (!path.equals("")) {
                                outPath = PathConstants.publishPath(siteId)
                                        + "/" + path;
                            } else {
                                outPath = PathConstants.publishPath(siteId);
                            }
                        }
                        // 判断pages文件夹是否存在，若不存在，则新建一个
                        FileOperateUtils.checkAndCreateDirs(outPath);
                        String fileOutPath = outPath
                                + "/"
                                + page.getOutputPath().substring(
                                page.getOutputPath().lastIndexOf("/") + 1,
                                page.getOutputPath().lastIndexOf("."))
                                + page.getOutputPath().substring(
                                page.getOutputPath().lastIndexOf("."));
                        FileOperateUtils
                                .writer(
                                        fileOutPath,
                                        "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor
                                                .compress(text)
                                                : text);

                        // 发布备份所有相关表和文件
                        //cmsPageService.insertPageOnline(page);
                        //url =  PathConstants.pageViewPath(siteId).substring(0, PathConstants.pageViewPath(siteId).length() - 4)+"view/supply/index_"+ shopMain.getShopId() + ".html";

                        url = PathConstants.pageViewPath(siteId).substring(0, PathConstants.pageViewPath(siteId).length() - 4) + "view/supply/" + shopMain.getShopId() + "/index.html";
                    }

                }
            }
        } catch (Exception e) {
        	logger.error("RemoteSupplierParseImpl.remoteViweParse异常：" + e.getMessage(), e);
        }

        return url;
    }

    /**
     * 选择供应商店铺模板
     */
    @Override
    public String remoteAddTheme(ShopMain shopMain, int themeType) throws Exception {
        String url = null;
        try {

            if(null != shopMain && StringUtil.isEmpty(shopMain.getShopSite())){
                shopMain.setShopSite(Constants.SET_B2B);
            }

            this.ashop_Type = themeType;//付给店铺类型变量

            CmsPage cmsPage2 = new CmsPage();// 页面组件

            cmsPage2.setCreated(Constants.USER_B2B_ID);

            CmsSite cmsSite = new CmsSite();
            cmsSite.setEngName(shopMain.getShopSite());
            cmsSite = cmsSiteService.querySite(cmsSite);
            int siteId = cmsSite.getSiteId();

            cmsPage2.setSiteId(siteId);
            cmsPage2.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());

            CmsTheme cmsTheme = new CmsTheme();
            cmsTheme.setSiteId(siteId);
            cmsTheme.setType(themeType);
            List<CmsTheme> cmsThemeList = cmsThemeDAO.queryThemeListAll(cmsTheme);//（店铺头部和当时模板）

            for (int i = 0; i < cmsThemeList.size(); i++) {//遍历出LIST集合的数据
                cmsTheme = cmsThemeList.get(i);
                CmsThemeTemplate t = new CmsThemeTemplate();
                t.setType(TEMPLATE_TYPE_TWELVE);//（12页面模板）
                t.setThemeId(cmsTheme.getThemeId());
                t = cmsThemeTemplateDAO.getByThemeTemp(t);//（查询页面模板）
                CmsTemplate cmsTemplate = this.cmsTemplateServce.getTemplateById(t.getTemplateId());//查询CmsTemplate表

                cmsPage2.setContent(cmsTemplate.getContent());//付值模板里的内容
                String ur = cmsTheme.getName();
                //路径
                ur = ur.substring(0, ur.lastIndexOf("/")) + "/" + shopMain.getShopId() + ur.substring(ur.lastIndexOf("/"));
                //ur=ur.substring(0 ,ur.lastIndexOf("."))+"_"+shopMain.getShopId()+".html";
                cmsPage2.setOutputPath(ur);

                CmsShopData cmsShop = new CmsShopData();
                cmsShop.setShopId(shopMain.getShopId().intValue());
                cmsShop.setSiteId(siteId);
                cmsShop.setSupplierId(shopMain.getSupplierId().intValue());
                cmsShop.setPageWindow(PAGEWINDOW_ONE);
                cmsShop.setThemeType(cmsTheme.getType());

                List cmsShopDataList = cmsShopDataDAO.selectAll(cmsShop);//查询店铺信息

                if (cmsShopDataList.size() == 0) { //是否已经存在此店铺信息

                    if (this.cmsPageService.addShopTheme(cmsPage2, cmsTheme)) {

                        cmsShop.setPageId(cmsPage2.getPageId());

                        if (this.cmsShopDataDAO.insert(cmsShop) != 0) {

                            if (cmsTheme.getType().equals(5)) { //初始化头部信息
                                CmsWindow cmsWindow = new CmsWindow();
                                String name = "shopName" + cmsPage2.getPageId().toString();// 取得头部供应商信息窗口名称
                                cmsWindow.setName(name);
                                cmsWindow.setSiteId(siteId);

                                cmsWindow = cmsWindowDAO.getWindows(cmsWindow);

                                CmsWindowData cmsWindowDataNmae = new CmsWindowData();
                                cmsWindowDataNmae.setSiteId(siteId);
                                cmsWindowDataNmae.setDataType(DARATYPE_ELEVEN);// 供应商信息
                                cmsWindowDataNmae.setWindowId(cmsWindow.getWindowId());
                                cmsWindowDataNmae.setDataId(shopMain.getShopId().intValue());
                                cmsWindowDataDAO.insert(cmsWindowDataNmae);
                                //}


                                CmsWindow window = new CmsWindow();
                                String tilet = "shopTitle" + cmsPage2.getPageId().toString();// 取得供应商标题窗口
                                window.setName(tilet);
                                window.setSiteId(siteId);
                                window = cmsWindowDAO.getWindows(window);

                                CmsWindowData cmsWindowData0 = new CmsWindowData();

                                //String	indexRrl = PathConstants.pageViewPath(siteId).substring(0, PathConstants.pageViewPath(siteId).length() - 4)+"supply/index_"
                                //	   + shopMain.getShopId() + ".html";
                                String indexRrl = PathConstants.pageViewPath(siteId).substring(0,
                                        PathConstants.pageViewPath(siteId).length() - 4) + "supply/" + shopMain.getShopId() + "/index.html";
                                cmsWindowData0.setSiteId(siteId);
                                cmsWindowData0.setDataType(DARATYPE_SIX);// 自定义标题
                                cmsWindowData0.setUser_defined_name("首页");
                                cmsWindowData0.setUser_defined_url(indexRrl);
                                cmsWindowData0.setSort(0);
                                cmsWindowData0.setWindowId(window.getWindowId());
                                cmsWindowDataDAO.insert(cmsWindowData0);

                                CmsWindowData cmsWindowData1 = new CmsWindowData();

                                String rl = PathConstants.pageViewPath(siteId).substring(0,
                                        PathConstants.pageViewPath(siteId).length() - 4) + "supply/" + shopMain.getShopId() + "/about.html";

                                cmsWindowData1.setSiteId(siteId);
                                cmsWindowData1.setDataType(DARATYPE_SIX);// 自定义标题
                                cmsWindowData1.setUser_defined_name("店铺简介");
                                cmsWindowData1.setUser_defined_url(rl);
                                cmsWindowData1.setSort(1);
                                cmsWindowData1.setWindowId(window.getWindowId());
                                cmsWindowDataDAO.insert(cmsWindowData1);

                                CmsWindowData cmsWindowData2 = new CmsWindowData();

                                String ur2 = PathConstants.searchPath_B2B(siteId)
                                        + "/shopProducts?shopid=" + shopMain.getShopId();
                                cmsWindowData2.setSiteId(siteId);
                                cmsWindowData2.setDataType(DARATYPE_SIX);// 自定义标题
                                cmsWindowData2.setUser_defined_name("所有商品");
                                cmsWindowData2.setUser_defined_url(ur2);
                                cmsWindowData2.setSort(2);
                                cmsWindowData2.setWindowId(window.getWindowId());
                                cmsWindowDataDAO.insert(cmsWindowData2);
                            }
                        }
                    }
                    url = this.remotePreShopMain(shopMain, cmsTheme.getType());// 可视化
                } else {
                    for (int q = 0; q < cmsShopDataList.size(); q++) {
                        CmsShopData cms = (CmsShopData) cmsShopDataList.get(q);
                        cmsPage2.setPageId(cms.getPageId());
                    }
                    cmsPageService.upPageTheme(cmsPage2, cmsTheme);//更新文件
                    url = this.remotePreShopMain(shopMain, cmsTheme.getType());// 可视化
                }
            }
        } catch (Exception e) {
        	logger.error("RemoteSupplierParseImpl.remoteAddTheme异常：" + e.getMessage(), e);
        }

        return url;
    }


    /**
     * 可视化供应商店铺模板
     */
    @Override
    public String remotePreShopMain(ShopMain shopMain, int themeType) throws Exception {
        String url = null;
        try {

            if(null != shopMain && StringUtil.isEmpty(shopMain.getShopSite())){
                shopMain.setShopSite(Constants.SET_B2B);
            }

            CmsSite cmsSite = new CmsSite();
            cmsSite.setEngName(shopMain.getShopSite());
            cmsSite = cmsSiteService.querySite(cmsSite);
            CmsShopData cmsShop = new CmsShopData();
            cmsShop.setShopId(shopMain.getShopId().intValue());
            cmsShop.setSiteId(cmsSite.getSiteId());
            cmsShop.setThemeType(themeType);
            cmsShop = cmsShopDataDAO.select2(cmsShop);//查询CmsShopData表里是否存在记录
            if (cmsShop != null) {
                CmsPage cmsPage = new CmsPage();// 页面组件
                cmsPage.setPageId(cmsShop.getPageId());//页面id
                cmsPage.setSiteId(cmsShop.getSiteId());//站点id

                if (cmsPage.getPageId() != null) {
                    cmsPage = cmsPageService
                            .getCmsPageById(cmsPage.getPageId());
                    Assert.notNull(cmsPage);
                    if (cmsPage.getOutputPath() != null) {
                        cmsPage.setJsCssPath(cmsPage.getOutputPath().substring(
                                cmsPage.getOutputPath().lastIndexOf('/') + 1,
                                cmsPage.getOutputPath().lastIndexOf('.')));
                    }
                    Integer siteId = null;
                    if (cmsPage.getSiteId() != null) {
                        siteId = cmsPage.getSiteId();
                    }
                    cmsPage.setCmsShopId(shopMain.getShopId());

                    cmsPage.setTemplatePath(PathConstants.pageTemPath(siteId)
                            + "/" + cmsPage.getPageId() + ".html");
                    int lineNumber = this.lineNumber(cmsShop);
                    cmsPage.setCmsShop(cmsShop);
                    cmsPage.setLineNumber(lineNumber);
                    if (cmsShop.getThemeType().equals(6)) { //是否动态头部
                        cmsPage.setPublicType(5);//发给search
                    }
                    cmsPage.setThemeType(0);//可视化的页面
                    cmsPage.setShopType(ashop_Type);//店铺模板类型
                    String text = defaultHtmlBuilder.buildHtml(cmsPage);
                    String outPath = "";
                    String savePath = "";
                    if (cmsPage.getOutputPath() != null) {
                        String path = cmsPage.getOutputPath().substring(0,
                                cmsPage.getOutputPath().lastIndexOf("/"));
                        if (!path.equals("")) {
                            outPath = PathConstants
                                    .visualizationPagePublishPath(siteId)
                                    + path;
                            savePath = path;
                        } else {
                            outPath = PathConstants
                                    .visualizationPagePublishPath(siteId);
                        }
                    }
                    // 判断文件夹是否存在，若不存在，则新建一个
                    FileOperateUtils.checkAndCreateDirs(outPath);
                    //	if(cmsShop.getThemeType().equals(2)||cmsShop.getThemeType().equals(3)){
                    //	text += "<script type='text/javascript'>seajs.use('<!--#echo var='cssAndJsPath' -->/<!--#echo var='channel' -->/<!--#echo var='jsUrl' -->/view/admin2.js');</script>";
                    //	     }
                    // 预览
                    // 写入文件
                    FileOperateUtils.writer(outPath + cmsPage.getOutputPath().substring(
                            cmsPage.getOutputPath().lastIndexOf("/")), "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor.compress(text) : text);
                    url = PathConstants.pageVisualizationPath(siteId) + "/supply/" + shopMain.getShopId() + "/index.html";

                }
            }
        } catch (Exception e) {
        	logger.error("RemoteSupplierParseImpl.remotePreShopMain异常：" + e.getMessage(), e);
        }

        return url;
    }


    public SupplierRemoteService getSupplierRemoteService() {
        return supplierRemoteService;
    }

    public void setSupplierRemoteService(
            SupplierRemoteService supplierRemoteService) {
        this.supplierRemoteService = supplierRemoteService;
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

    public ViewProductInfoService getViewProductInfoService() {
        return viewProductInfoService;
    }

    public void setViewProductInfoService(
            ViewProductInfoService viewProductInfoService) {
        this.viewProductInfoService = viewProductInfoService;
    }

    public ViewProductDetailInfoService getViewProductDetailInfoService() {
        return viewProductDetailInfoService;
    }

    public void setViewProductDetailInfoService(
            ViewProductDetailInfoService viewProductDetailInfoService) {
        this.viewProductDetailInfoService = viewProductDetailInfoService;
    }

    public ProdBrandService getProdBrandService() {
        return prodBrandService;
    }

    public void setProdBrandService(ProdBrandService prodBrandService) {
        this.prodBrandService = prodBrandService;
    }

    public CmsSiteService getCmsSiteService() {
        return cmsSiteService;
    }

    public void setCmsSiteService(CmsSiteService cmsSiteService) {
        this.cmsSiteService = cmsSiteService;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /*	public Map<String, String> getTemplateConfig() {
            return templateConfig;
        }

        public void setTemplateConfig(Map<String, String> templateConfig) {
            this.templateConfig = templateConfig;
        }
    */

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

    public void setCmsWindowDataService(
            CmsWindowDataService cmsWindowDataService) {
        this.cmsWindowDataService = cmsWindowDataService;
    }

    public ProductAttrService getProductAttrService() {
        return productAttrService;
    }

    public void setProductAttrService(ProductAttrService productAttrService) {
        this.productAttrService = productAttrService;
    }

    public String getCheckeds() {
        return checkeds;
    }

    public void setCheckeds(String checkeds) {
        this.checkeds = checkeds;
    }

    public Integer getAdminType() {
        return adminType;
    }

    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
    }

    public CmsThemeDAO getCmsThemeDAO() {
        return cmsThemeDAO;
    }

    public void setCmsThemeDAO(CmsThemeDAO cmsThemeDAO) {
        this.cmsThemeDAO = cmsThemeDAO;
    }

    public CmsTemplateServce getCmsTemplateServce() {
        return cmsTemplateServce;
    }

    public void setCmsTemplateServce(CmsTemplateServce cmsTemplateServce) {
        this.cmsTemplateServce = cmsTemplateServce;
    }

    public CmsThemeTemplateDAO getCmsThemeTemplateDAO() {
        return cmsThemeTemplateDAO;
    }

    public void setCmsThemeTemplateDAO(CmsThemeTemplateDAO cmsThemeTemplateDAO) {
        this.cmsThemeTemplateDAO = cmsThemeTemplateDAO;
    }

    public CmsSupplyService getCmsSupplyService() {
        return cmsSupplyService;
    }

    public void setCmsSupplyService(CmsSupplyService cmsSupplyService) {
        this.cmsSupplyService = cmsSupplyService;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public PagePublish getPagePublish() {
        return pagePublish;
    }

    public void setPagePublish(PagePublish pagePublish) {
        this.pagePublish = pagePublish;
    }

    public CmsSupplierAdcolumnService getCmsSupplierAdcolumnService() {
        return cmsSupplierAdcolumnService;
    }

    public void setCmsSupplierAdcolumnService(
            CmsSupplierAdcolumnService cmsSupplierAdcolumnService) {
        this.cmsSupplierAdcolumnService = cmsSupplierAdcolumnService;
    }

    public CmsAdcolumnDataService getCmsAdcolumnDataService() {
        return cmsAdcolumnDataService;
    }

    public void setCmsAdcolumnDataService(
            CmsAdcolumnDataService cmsAdcolumnDataService) {
        this.cmsAdcolumnDataService = cmsAdcolumnDataService;
    }

    public CmsAdvService getCmsAdvService() {
        return cmsAdvService;
    }

    public void setCmsAdvService(CmsAdvService cmsAdvService) {
        this.cmsAdvService = cmsAdvService;
    }

    public CmsShopDataDAO getCmsShopDataDAO() {
        return cmsShopDataDAO;
    }

    public void setCmsShopDataDAO(CmsShopDataDAO cmsShopDataDAO) {
        this.cmsShopDataDAO = cmsShopDataDAO;
    }

    public CmsWindowDataDAO getCmsWindowDataDAO() {
        return cmsWindowDataDAO;
    }

    public CmsWindowDAO getCmsWindowDAO() {
        return cmsWindowDAO;
    }

    public Integer getAshop_Type() {
        return ashop_Type;
    }

    public void setAshop_Type(Integer ashopType) {
        ashop_Type = ashopType;
    }

    public void setCmsWindowDAO(CmsWindowDAO cmsWindowDAO) {
        this.cmsWindowDAO = cmsWindowDAO;
    }

    public void setCmsWindowDataDAO(CmsWindowDataDAO cmsWindowDataDAO) {
        this.cmsWindowDataDAO = cmsWindowDataDAO;
    }
}
