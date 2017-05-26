package com.pltfm.remote.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.kmzyc.cms.remote.service.SupplierRemoteService;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.util.Constants;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.ListUtils;
import com.pltfm.cms.parse.DataType;
import com.pltfm.cms.parse.HtmlBuilder;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsAdcolumnDataService;
import com.pltfm.cms.service.CmsAdvService;
import com.pltfm.cms.service.CmsPageService;
import com.pltfm.cms.service.CmsSiteService;
import com.pltfm.cms.service.CmsSupplierAdcolumnService;
import com.pltfm.cms.service.CmsTemplateServce;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.util.HtmlCompressor;
import com.pltfm.cms.vobject.CmsAdcolumn;
import com.pltfm.cms.vobject.CmsAdv;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsSite;
import com.pltfm.cms.vobject.CmsSupplierAdcolumn;
import com.pltfm.cms.vobject.CmsTemplate;

/**
 * 提供给供应商的接口
 *
 * @author gy
 *
 *         供应商第一期（已废弃）： 1.自动添加供应商对供广告位 2.会关联供应商和与广告位 供应商第二期 1.店铺关闭
 */

@Component(value = "supplierRemoteService")
public class SupplierRemoteServiceImpl implements SupplierRemoteService {
	private static Logger logger = LoggerFactory.getLogger(SupplierRemoteServiceImpl.class);
    @Resource(name = "cmsPageService")
    CmsPageService cmsPageService;
    @Resource(name = "cmsAdcolumnDataService")
    CmsAdcolumnDataService cmsAdcolumnDataService;
    @Resource(name = "cmsSupplierAdcolumnService")
    CmsSupplierAdcolumnService cmsSupplierAdcolumnService;
    @Resource(name = "cmsAdvService")
    CmsAdvService cmsAdvService;
    private CmsPage cmsPage;
    @Resource(name = "cmsTemplateServce")
    private CmsTemplateServce cmsTemplateService;
    @Resource(name = "cmsSiteService")
    private CmsSiteService cmsSiteService;

    // cmsAdvAction
    @Resource(name = "htmlBuilder")
    private HtmlBuilder htmlBuilder;

    private CmsTemplate cmsTemplate;

    @Resource(name = "cmsTemplateServce")
    private CmsTemplateServce cmsTemplateServce;

    //店铺关闭(ShopId)
    @Override
    public String closeShop(ShopMain shopMain) {
        try {

            if(null != shopMain && StringUtil.isEmpty(shopMain.getShopSite())){
                shopMain.setShopSite(Constants.SET_B2B);
            }

            // 供应商渠道与本系统渠道转化
            String shopSite = shopMain.getShopSite();
            CmsSite site = new CmsSite();
            site.setEngName(shopSite);
            site = cmsSiteService.querySite(site);
            String shopMainPath = PathConstants.shopMainPath(site.getSiteId());
            String colsedShopMainPath = PathConstants.colsedShopPath(site.getSiteId());

            File oldFile = new File(shopMainPath + "/" + shopMain.getShopId() + "/");
            File newFile = new File(colsedShopMainPath + "/");
            //supply/121/index.html
            FileOperateUtils.checkAndCreateDirs(colsedShopMainPath);


            FileUtils.copyDirectoryToDirectory(oldFile, newFile);


            FileUtils.deleteDirectory(oldFile);
        } catch (Exception e) {
        	logger.error("SupplierRemoteServiceImpl.closeShop异常：" + e.getMessage(), e);
            return "fail";
        }
        return "success";

    }

    //重开店铺
    @Override
    public String reopenShop(ShopMain shopMain) {
        String newPath = "";
        try {

            if(null != shopMain && StringUtil.isEmpty(shopMain.getShopSite())){
                shopMain.setShopSite(Constants.SET_B2B);
            }

            // 供应商渠道与本系统渠道转化
            String shopSite = shopMain.getShopSite();
            CmsSite site = new CmsSite();
            site.setEngName(shopSite);
            site = cmsSiteService.querySite(site);
            String reopenShopPath = PathConstants.shopMainPath(site.getSiteId());
            String closedShopMainPath = PathConstants.colsedShopPath(site.getSiteId());
            FileOperateUtils.checkAndCreateDirs(closedShopMainPath);
            File oldFile = new File(closedShopMainPath + "/" + shopMain.getShopId() + "/");

            File newFile = new File(reopenShopPath + "/");

            //supply/index_121.html


            FileUtils.copyDirectoryToDirectory(oldFile, newFile);
            FileUtils.deleteDirectory(oldFile);


        } catch (Exception e) {
        	logger.error("SupplierRemoteServiceImpl.reopenShop异常：" + e.getMessage(), e);
            return "fail";
        }
        return "success";
    }

    /****************************已废弃*********************************/

    // 发布时调用的接口
    @Override
    public String addSupplierRelation(List<ShopMain> shopMainList) {
        if (ListUtils.isNotEmpty(shopMainList)) {
            for (int j = 0; j < shopMainList.size(); j++) {
                ShopMain shopMain = shopMainList.get(j);
                Long ShopMainId = shopMain.getShopId();

                // 查验该店铺是否是第一次发布
                CmsAdcolumn adcolumn = new CmsAdcolumn();
                adcolumn.setOutput("shopMain" + ShopMainId);
                List<CmsPage> pageList = null;
                try {
                    List cmsAdcolumnList = cmsAdcolumnDataService
                            .queryAdcolumnListBySupplier(adcolumn);
                    if (null == cmsAdcolumnList || cmsAdcolumnList.size() == 0) {
                        // 批量添加广告位
                        CmsPage page = new CmsPage();
                        // 供应商类型
                        page.setPublicType(6);

                        pageList = cmsPageService.selectByPrimaryPublishType(page);
                        if (ListUtils.isNotEmpty(pageList)) {
                            cmsPage = pageList.get(0);
                            int siteId = cmsPage.getSiteId();
                            // 广告位个数
                            String remark = cmsPage.getRemark();
                            // 获取模版内容
                            CmsTemplate cmsTemplate = new CmsTemplate();
                            //供应商广告模版
                            cmsTemplate.setType(2);
                            cmsTemplate.setSiteId(siteId);
                            List<CmsTemplate> templateList = cmsTemplateService.selectBySiteIdType(cmsTemplate);
                            if (remark != null && !"".equals(remark)) {
                                int adcolumnCount = Integer.parseInt(remark);
                                String templateName = "";
                                String shopMainPath = "";
                                for (int i = 1; i <= adcolumnCount; i++) {
                                    CmsAdcolumn cmsAdcolumn = new CmsAdcolumn();
                                    //逐一取得广告模板
                                    if (ListUtils.isNotEmpty(templateList)) {
                                        cmsTemplate = templateList.get(i - 1);

                                    }
                                    // 名称
                                    cmsAdcolumn.setName(shopMain.getShopName() + "_" + cmsTemplate.getTheme());
                                    templateName = cmsTemplate.getName();
                                    shopMainPath = templateName.replace("shopMain", "shopMain" + ShopMainId);
                                    // 生成路径
                                    //	cmsAdcolumn.setOutput("/portal/adv/supplier/"+ "shopMain" + ShopMainId + "_" + i+ ".shtml");
                                    cmsAdcolumn.setOutput("/portal/adv/supplier/" + shopMainPath + ".shtml");
                                    cmsAdcolumn.setSiteId(cmsPage.getSiteId());

                                    cmsAdcolumn.setCreateDate(DateTimeUtils
                                            .getCalendarInstance().getTime());
                                    cmsAdcolumn.setModifyDate(DateTimeUtils
                                            .getCalendarInstance().getTime());
                                    cmsAdcolumn.setStatus(Short.valueOf("0"));

                                    CmsSupplierAdcolumn cmsSupplierAdcolumn = new CmsSupplierAdcolumn();
                                    cmsSupplierAdcolumn.setSupplierId(Integer
                                            .parseInt(shopMain.getSupplierId()
                                                    + ""));
                                    cmsSupplierAdcolumn.setSiteCode(shopMain
                                            .getShopSite());
                                    cmsSupplierAdcolumn
                                            .setCreateDate(DateTimeUtils
                                                    .getCalendarInstance()
                                                    .getTime());
                                    cmsSupplierAdcolumn
                                            .setModifyDate(DateTimeUtils
                                                    .getCalendarInstance()
                                                    .getTime());
                                    cmsSupplierAdcolumn.setStatus(Integer.valueOf(0));

                                    cmsSupplierAdcolumnService
                                            .insertSupplierAdcolumn(
                                                    cmsTemplate, cmsAdcolumn,
                                                    cmsSupplierAdcolumn);
                                }
                            }

                        }
                    }
                    return "success";
                } catch (Exception e) {
                	logger.error("SupplierRemoteServiceImpl.addSupplierRelation异常：" + e.getMessage(), e);
                    return "fail";
                }

            }
        }
        return "success";
    }

    // 提供广告位列表(返回广告位对象列表)接口
    @Override
    public List queryAdvColumnListBySupplierId(ShopMain ShopMain) {
        List list = null;
        try {

            if(null != ShopMain && StringUtil.isEmpty(ShopMain.getShopSite())){
                ShopMain.setShopSite(Constants.SET_B2B);
            }

            list = cmsSupplierAdcolumnService.queryAdcolumnByShopMainId(ShopMain);
        } catch (Exception e) {
        	logger.error("SupplierRemoteServiceImpl.queryAdvColumnListBySupplierId异常：" + e.getMessage(), e);
        }
        return list;
    }


    // 提供广告列表(返回广告对象列表)接口
    // public List queryAdvListBySupplierId(ShopMain ShopMain){

    @Override
    public List queryAdvListBySupplierId(CmsAdv cmsAdv) {
        List list = null;
        try {
            // list= cmsSupplierAdcolumnService.queryAdvByShopMainId(ShopMain);

            list = cmsAdvService.queryAdvList(cmsAdv);
        } catch (Exception e) {
        	logger.error("SupplierRemoteServiceImpl.queryAdvListBySupplierId异常：" + e.getMessage(), e);
        }
        return list;
    }

    // 广告详情页
    @Override
    public CmsAdv queryAdvById(CmsAdv cmsAdv) {
        CmsAdv adv = null;
        try {
            adv = cmsAdvService.byId(cmsAdv.getAdvId());
        } catch (Exception e) {
        	logger.error("SupplierRemoteServiceImpl.queryAdvById异常：" + e.getMessage(), e);
        }
        return adv;

    }

    //
    // 广告上传或修改接口
    @Override
    public String uploadAdv(ShopMain shopMain, CmsAdv cmsAdv) {
        Assert.notNull(cmsAdv);

        if(null != shopMain && StringUtil.isEmpty(shopMain.getShopSite())){
            shopMain.setShopSite(Constants.SET_B2B);
        }

        // 供应商渠道与本系统渠道转化
        String shopSite = shopMain.getShopSite();
        CmsSite site = new CmsSite();
        site.setEngName(shopSite);
        try {
            site = cmsSiteService.querySite(site);
            if (cmsAdv.getAdvId() == null) {
                cmsAdv.setSiteId(site.getSiteId());

                cmsAdvService.addSupplierCmsAdv(cmsAdv);
            } else {
                cmsAdv.setStatus(0);
                cmsAdvService.updateSupplierAdv(cmsAdv);
            }
            return "success";
        } catch (Exception e) {
        	logger.error("SupplierRemoteServiceImpl.uploadAdv异常：" + e.getMessage(), e);
        }
        return "fail";
    }

    // 广告发布接口

    @Override
    public String parseAdv(CmsAdv cmsAdv) {

        Map<String, Object> result = new HashMap<String, Object>();
        // 对应广告
        try {

            // CmsAdv cmsAdv=new CmsAdv();
            // cmsAdv.setAdvId(id);
            cmsAdv = cmsAdvService.byIdToSupplier(cmsAdv.getAdvId());
            Integer siteId = cmsAdv.getSiteId();
            result.put(DataType.advert.name(), cmsAdv);
            result.put(DataType.cssAndJsPath.name(), PathConstants.getSitePath(
                    "cssAndJsPath", "mail", cmsAdv.getSiteId()));
            CmsAdcolumn adcolumn = cmsAdcolumnDataService.byid(cmsAdv
                    .getAdcolumnId());
            // 选择对应模版
            String path = PathConstants.advTempPath(siteId) + "/"
                    + cmsAdv.getAdvId() + ".html";
            String content = htmlBuilder.getHtml(path, result);
            if (null != content && !content.equals("")) {
                // 更新广告状态(已发布)
                cmsAdv = cmsAdvService.findAdv(cmsAdv.getAdvId());
                cmsAdv.setStatus(1);
                cmsAdvService.updateSupplierAdv(cmsAdv);
            }
            // 广告位输出路径

            String outPath = PathConstants.publishPath(siteId) + "/"
                    + adcolumn.getOutput();

            // 判断pages文件夹是否存在，若不存在，则新建一个

            String outPathFile = "";
            if (adcolumn.getOutput() != null) {
                String outPutPath = adcolumn.getOutput().substring(0,
                        adcolumn.getOutput().lastIndexOf("/"));
                if (!outPutPath.equals("")) {
                    outPathFile = PathConstants.publishPath(siteId)
                            + "/" + outPutPath;
                } else {
                    outPathFile = PathConstants.publishPath(siteId);
                }
            }
            // 判断pages文件夹是否存在，若不存在，则新建一个
            FileOperateUtils.checkAndCreateDirs(outPathFile);

            // 写入广告位路径
            // FileOperateUtils.writer(outPath, content);
            // js格式
            if (outPath.endsWith("js")) {
                content = java.net.URLEncoder.encode(content);
                content = "document.write(decodeURIComponent(\"" + content
                        + "\").replace(/\\+/g,' '));";
                FileOperateUtils
                        .writer(
                                outPath,
                                "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor
                                        .compress(content)
                                        : content);
                // shtml格式
            } else {
                FileOperateUtils
                        .writer(
                                outPath,
                                "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor
                                        .compress(content)
                                        : content);
            }
            return "success";
        } catch (Exception e) {
        	logger.error("SupplierRemoteServiceImpl.parseAdv异常：" + e.getMessage(), e);
        }

        return "fail";

    }

    //多选发布
    @Override
    public String parseSelAdv(Integer[] ids) {
        try {
            if (null != ids && ids.length > 0) {
                for (int i = 0; i < ids.length; i++) {
                    CmsAdv cmsAdv = new CmsAdv();
                    cmsAdv.setAdvId(ids[i]);
                    parseAdv(cmsAdv);
                }
            }

            return "success";
        } catch (Exception e) {
        	logger.error("SupplierRemoteServiceImpl.parseSelAdv异常：" + e.getMessage(), e);
        }
        return "fail";
    }

    // 删除
    @Override
    public String delAdv(CmsAdv cmsAdv) {
        try {
            cmsAdvService.delete(cmsAdv.getAdvId());
            return "success";
        } catch (Exception e) {
        	logger.error("SupplierRemoteServiceImpl.delAdv异常：" + e.getMessage(), e);
        }
        return "fail";
    }

    //多选删除
    @Override
    public String delSelAdv(Integer[] ids) {
        try {
            if (null != ids && ids.length > 0) {
                //		for(int i=0;i<ids.length;i++){
                //			cmsAdvService.delete(ids[i]);
                //		}
                cmsAdvService.delSelAdv(ids);
            }


            return "success";
        } catch (Exception e) {
        	logger.error("SupplierRemoteServiceImpl.delSelAdv异常：" + e.getMessage(), e);
        }
        return "fail";
    }


    //该供应商下广告位是否已上传广告 shopId,supplierId,ShopCode
    @Override
    public boolean checkAdvIsUpload(ShopMain shopMain) {

        if(null != shopMain && StringUtil.isEmpty(shopMain.getShopSite())){
            shopMain.setShopSite(Constants.SET_B2B);
        }

        CmsSupplierAdcolumn supplierAdcolumn = new CmsSupplierAdcolumn();
        supplierAdcolumn.setSupplierId(shopMain.getSupplierId().intValue());
        supplierAdcolumn.setSiteCode(shopMain.getShopSite());
        CmsAdcolumn adcolumn = new CmsAdcolumn();
        adcolumn.setOutput("shopMain" + shopMain.getShopId());
        try {
            //广告位数
            List cmsAdcolumnList = cmsAdcolumnDataService.queryAdcolumnListBySupplier(adcolumn);
            if (null != cmsAdcolumnList && cmsAdcolumnList.size() > 0) {
                int adcolumnCount = cmsAdcolumnList.size();
                //广告个数
                int advCount = cmsSupplierAdcolumnService.checkAdvIsUpload(supplierAdcolumn);
                //广告位少至或等于广告数
                if (adcolumnCount <= advCount) {
                    return true;
                }
            } else {
                return false;
            }

        } catch (SQLException e) {
        	logger.error("SupplierRemoteServiceImpl.checkAdvIsUpload异常：" + e.getMessage(), e);
            return false;
        }
        return false;
    }

    /**
     * 根据广告查询条件获取记录数
     * @param cmsAdv 广告
     * @return 记录数
     * @throws Exception
     */
    /*@Override
	public Integer countByAdv(CmsAdv cmsAdv) throws Exception {
		return cmsAdvService.countByAdv(cmsAdv);
	}*/

    /**
     * 供应商店铺装修登录退出
     */
    @Override
    public void supplyLogOut() {
        // 构造httpclient的实例
        HttpClient htpc = new HttpClient();
        // 创建Get方法的实例
        // url需要传递参数并包含中文时，可以将参数转码（URLEncoder.encode(参数,"UTF-8")），与服务器端一样的编码格式
        GetMethod getMethod = new GetMethod(ConfigurationUtil.getString("cmsSysLogOutPath")); // 链接的路径如：http://www.baidu.com
        System.out.println(ConfigurationUtil.getString("cmsSysLogOutPath"));
        // 使用系统提供的默认的恢复策略,此处HttpClient的恢复策略可以自定义（通过实现接口HttpMethodRetryHandler来实现）。
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        try {
            htpc.executeMethod(getMethod);
            // 读取内容
            byte[] responseBody = getMethod.getResponseBody();
            // 处理内容
            System.out.println(new String(responseBody));
        } catch (HttpException e) {
        	logger.error("SupplierRemoteServiceImpl.supplyLogOut异常：" + e.getMessage(), e);
        } catch (IOException e) {
        	logger.error("SupplierRemoteServiceImpl.supplyLogOut异常：" + e.getMessage(), e);
        } finally {
            // 释放连接
            getMethod.releaseConnection();
        }
    }


    public CmsPageService getCmsPageService() {
        return cmsPageService;
    }

    public void setCmsPageService(CmsPageService cmsPageService) {
        this.cmsPageService = cmsPageService;
    }

    public CmsPage getCmsPage() {
        return cmsPage;
    }

    public void setCmsPage(CmsPage cmsPage) {
        this.cmsPage = cmsPage;
    }

    public CmsAdcolumnDataService getCmsAdcolumnDataService() {
        return cmsAdcolumnDataService;
    }

    public void setCmsAdcolumnDataService(
            CmsAdcolumnDataService cmsAdcolumnDataService) {
        this.cmsAdcolumnDataService = cmsAdcolumnDataService;
    }

    public CmsSupplierAdcolumnService getCmsSupplierAdcolumnService() {
        return cmsSupplierAdcolumnService;
    }

    public void setCmsSupplierAdcolumnService(
            CmsSupplierAdcolumnService cmsSupplierAdcolumnService) {
        this.cmsSupplierAdcolumnService = cmsSupplierAdcolumnService;
    }

    public CmsAdvService getCmsAdvService() {
        return cmsAdvService;
    }

    public void setCmsAdvService(CmsAdvService cmsAdvService) {
        this.cmsAdvService = cmsAdvService;
    }

    public HtmlBuilder getHtmlBuilder() {
        return htmlBuilder;
    }

    public void setHtmlBuilder(HtmlBuilder htmlBuilder) {
        this.htmlBuilder = htmlBuilder;
    }

    public CmsTemplateServce getCmsTemplateService() {
        return cmsTemplateService;
    }

    public void setCmsTemplateService(CmsTemplateServce cmsTemplateService) {
        this.cmsTemplateService = cmsTemplateService;
    }

    public CmsSiteService getCmsSiteService() {
        return cmsSiteService;
    }

    public void setCmsSiteService(CmsSiteService cmsSiteService) {
        this.cmsSiteService = cmsSiteService;
    }

    public CmsTemplate getCmsTemplate() {
        return cmsTemplate;
    }

    public void setCmsTemplate(CmsTemplate cmsTemplate) {
        this.cmsTemplate = cmsTemplate;
    }

    public CmsTemplateServce getCmsTemplateServce() {
        return cmsTemplateServce;
    }

    public void setCmsTemplateServce(CmsTemplateServce cmsTemplateServce) {
        this.cmsTemplateServce = cmsTemplateServce;
    }

    @Override
    public Integer countByAdv(CmsAdv cmsAdv) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


}
