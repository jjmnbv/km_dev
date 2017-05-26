package com.pltfm.cms.action;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.cms.remote.service.SupplierRemoteService;
import com.kmzyc.commons.page.Page;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsAdcolumnDataService;
import com.pltfm.cms.service.CmsSiteService;
import com.pltfm.cms.service.CmsSupplierAdcolumnService;
import com.pltfm.cms.service.CmsSupplyService;
import com.pltfm.cms.service.CmsTemplateServce;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.vobject.CmsAdcolumn;
import com.pltfm.cms.vobject.CmsSite;
import com.pltfm.cms.vobject.CmsSupplierAdcolumn;
import com.pltfm.cms.vobject.CmsTemplate;
import com.pltfm.sys.model.SysUser;

@Scope(value = "prototype")
@Component("cmsSupplierAdcolumnAction")
public class CmsSupplierAdcolumnAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(CmsSupplierAdcolumnAction.class);
    CmsSupplierAdcolumn cmsSupplierAdcolumn;
    List<CmsSupplierAdcolumn> cmsSupplierAdcolumnList;

    @Resource(name = "cmsSupplierAdcolumnService")
    CmsSupplierAdcolumnService cmsSupplierAdcolumnService;

    @Resource(name = "cmsSiteService")
    CmsSiteService cmsSiteService;

    @Resource(name = "supplierRemoteService")
    SupplierRemoteService supplierRemoteService;

    @Resource(name = "cmsAdcolumnDataService")
    private CmsAdcolumnDataService cmsAdcolumnDataService;
    @Resource(name = "cmsSupplyService")
    private CmsSupplyService cmsSupplyService;

    public Page page;//分页组

    public List<CmsAdcolumn> cmsAdcolumnList;

    public List<ShopMain> shopMainList;
    public List templateList;
    private CmsAdcolumn cmsAdcolumn;
    @Resource(name = "cmsTemplateServce")
    private CmsTemplateServce cmsTemplateServce;

    private CmsTemplate cmsTemplate;
    private ShopMain shopMain;
    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();

    //列表
    public String queryAdcolumnList() {
        try {
            if (cmsSupplierAdcolumn == null) {
                cmsSupplierAdcolumn = new CmsSupplierAdcolumn();
            }
            Integer siteId = (Integer) session.get("siteId");
            CmsSite site = cmsSiteService.selectByPrimaryKey(siteId);
            cmsSupplierAdcolumn.setSiteCode(site.getEngName());
            //	 CmsSite site=new CmsSite();
            //	 site.setEngName(shopSite);
            //= cmsSiteService.querySite(site);
            //	cmsAdv.setSiteId(siteId);//新加的站点ID
            //过滤广告位
            CmsAdcolumn cmsAdcolumn = new CmsAdcolumn();
            cmsAdcolumn.setOutput("shopMain");
            cmsAdcolumnList = cmsAdcolumnDataService.queryAdcolumnListBySupplier(cmsAdcolumn);
            //店铺
            shopMainList = cmsSupplyService.queryShopMainList();
            page = cmsSupplierAdcolumnService.queryList(page, cmsSupplierAdcolumn);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("供应商管理--列表出现异常", e);
        }
        return "list";
    }

    //详情
    public String byId() {
        try {
            cmsSupplierAdcolumn = cmsSupplierAdcolumnService.byId(cmsSupplierAdcolumn);
            //供应商渠道与本系统渠道转化
            String shopSite = cmsSupplierAdcolumn.getSiteCode();
            CmsSite site = new CmsSite();
            site.setEngName(shopSite);
            site = cmsSiteService.querySite(site);
            //1.模版/template/adv/supplier下读取文件
            String path = PathConstants.advSupplierPath(site.getSiteId());
            File file = new File(path, cmsSupplierAdcolumn.getSupplierAdcolumnId() + ".html");
            if (file.exists()) {
                cmsSupplierAdcolumn.setContent(FileUtils.readFileToString(file, "utf-8"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("供应商管理--详情出现异常", e);
        }
        return "edit";
    }

    //添回
    public String gotoAdd() {
        //店铺
        try {
            Integer siteId = (Integer) session.get("siteId");
            shopMainList = cmsSupplyService.queryShopMainList();
            CmsTemplate cmsTemplate = new CmsTemplate();
            cmsTemplate.setType(Integer.valueOf(2));
            cmsTemplate.setSiteId(siteId);
            templateList = cmsTemplateServce.getByName(cmsTemplate);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("供应商管理--添回出现异常", e);
        }
        return "gotoAdd";
    }

    //增加供应商广告位
    public String Add() {
        try {

            cmsTemplate = cmsTemplateServce.getTemplateById(cmsTemplate.getTemplateId());
            shopMain = cmsSupplyService.getShopMain(shopMain.getShopId());
            Long ShopMainId = shopMain.getShopId();
            CmsAdcolumn cmsAdcolumn = new CmsAdcolumn();
            // 名称
            cmsAdcolumn.setName(shopMain.getShopName() + "_" + cmsTemplate.getTheme());
            String templateName = cmsTemplate.getName();
            String shopMainPath = templateName.replace("shopMain", "shopMain" + ShopMainId);
            // 生成路径

            cmsAdcolumn.setOutput("/portal/adv/supplier/" + shopMainPath + ".shtml");
            cmsAdcolumn.setSiteId(cmsTemplate.getSiteId());

            cmsAdcolumn.setCreateDate(DateTimeUtils
                    .getCalendarInstance().getTime());
            cmsAdcolumn.setModifyDate(DateTimeUtils
                    .getCalendarInstance().getTime());
            cmsAdcolumn.setStatus((short)0);

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
            this.addActionMessage(ConfigurationUtil.getString("add.success"));

        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("add.fail"));
            logger.error("供应商管理--增加供应商广告位出现异常", e);
            return queryAdcolumnList();
        }


        return queryAdcolumnList();
    }

    //修改
    public String update() {
        try {


            Map session = ActionContext.getContext().getSession();
            SysUser sysUser = (SysUser) session.get("sysUser");
            cmsSupplierAdcolumn.setModified(sysUser.getUserId());
            cmsSupplierAdcolumn.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());

            cmsSupplierAdcolumn.setCreated(sysUser.getUserId());
            cmsSupplierAdcolumn.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
            //修改成未发布
            cmsSupplierAdcolumn.setStatus(Integer.valueOf(0));
            //广告位路径
            CmsAdcolumn cmsAdcolumn = new CmsAdcolumn();
            cmsAdcolumn.setOutput(cmsSupplierAdcolumn.getContentPath());
            cmsAdcolumn.setAdcolumnId(cmsSupplierAdcolumn.getAdcolumnId());
            cmsAdcolumnDataService.update(cmsAdcolumn);
            cmsSupplierAdcolumnService.update(cmsSupplierAdcolumn);

            //供应商渠道与本系统渠道转化
            String shopSite = cmsSupplierAdcolumn.getSiteCode();
            CmsSite site = new CmsSite();
            site.setEngName(shopSite);
            site = cmsSiteService.querySite(site);
            String path = PathConstants.advSupplierPath(site.getSiteId());
            FileOperateUtils.checkAndCreateDirs(path);
            File file = new File(path);
            File tempFile = new File(file, cmsSupplierAdcolumn.getSupplierAdcolumnId() + ".html");
            FileUtils.writeStringToFile(tempFile, cmsSupplierAdcolumn.getContent(), "utf-8");
            this.addActionMessage(ConfigurationUtil.getString("update.success"));
            cmsSupplierAdcolumn = null;

            //return "list";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("供应商管理--修改出现异常", e);
            this.addActionMessage(ConfigurationUtil.getString("update.fail"));
        }

        return queryAdcolumnList();
    }

    public CmsSupplierAdcolumnService getCmsSupplierAdcolumnService() {
        return cmsSupplierAdcolumnService;
    }

    public void setCmsSupplierAdcolumnService(
            CmsSupplierAdcolumnService cmsSupplierAdcolumnService) {
        this.cmsSupplierAdcolumnService = cmsSupplierAdcolumnService;
    }

    public CmsSupplierAdcolumn getCmsSupplierAdcolumn() {
        return cmsSupplierAdcolumn;
    }

    public void setCmsSupplierAdcolumn(CmsSupplierAdcolumn cmsSupplierAdcolumn) {
        this.cmsSupplierAdcolumn = cmsSupplierAdcolumn;
    }

    public List<CmsSupplierAdcolumn> getCmsSupplierAdcolumnList() {
        return cmsSupplierAdcolumnList;
    }

    public void setCmsSupplierAdcolumnList(
            List<CmsSupplierAdcolumn> cmsSupplierAdcolumnList) {
        this.cmsSupplierAdcolumnList = cmsSupplierAdcolumnList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
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

    public List<CmsAdcolumn> getCmsAdcolumnList() {
        return cmsAdcolumnList;
    }

    public void setCmsAdcolumnList(List<CmsAdcolumn> cmsAdcolumnList) {
        this.cmsAdcolumnList = cmsAdcolumnList;
    }

    public CmsAdcolumnDataService getCmsAdcolumnDataService() {
        return cmsAdcolumnDataService;
    }

    public void setCmsAdcolumnDataService(
            CmsAdcolumnDataService cmsAdcolumnDataService) {
        this.cmsAdcolumnDataService = cmsAdcolumnDataService;
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

    public CmsAdcolumn getCmsAdcolumn() {
        return cmsAdcolumn;
    }

    public void setCmsAdcolumn(CmsAdcolumn cmsAdcolumn) {
        this.cmsAdcolumn = cmsAdcolumn;
    }

    public CmsTemplateServce getCmsTemplateServce() {
        return cmsTemplateServce;
    }

    public void setCmsTemplateServce(CmsTemplateServce cmsTemplateServce) {
        this.cmsTemplateServce = cmsTemplateServce;
    }

    public List getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List templateList) {
        this.templateList = templateList;
    }

    public CmsTemplate getCmsTemplate() {
        return cmsTemplate;
    }

    public void setCmsTemplate(CmsTemplate cmsTemplate) {
        this.cmsTemplate = cmsTemplate;
    }

    public ShopMain getShopMain() {
        return shopMain;
    }

    public void setShopMain(ShopMain shopMain) {
        this.shopMain = shopMain;
    }


}
