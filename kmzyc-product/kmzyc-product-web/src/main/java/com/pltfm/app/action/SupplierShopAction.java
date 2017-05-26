package com.pltfm.app.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Lists;
import com.kmzyc.zkconfig.ConfigurationUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.kmzyc.supplier.model.ShopMain;
import com.pltfm.app.enums.MsgOperation;
import com.pltfm.app.enums.SuppliersShopStatus;
import com.pltfm.app.maps.SuppliersShopStatusMap;
import com.pltfm.app.service.SupplierShopService;
import com.kmzyc.commons.page.Page;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller(value = "supplierShopAction")
@Scope(value = "prototype")
public class SupplierShopAction extends BaseAction {

    private static Logger logger = Logger.getLogger(SupplierShopAction.class);

    @Resource
    private SupplierShopService supplierShopService;

    private Page page;//分页

    private ShopMain searchShopMain;//查询条件

    private Long shopId;//店铺id

    private ShopMain showShopMain = new ShopMain();//用于保存店铺信息

    private String ckType;//判断类型

    private String describe;//审核不通过原因

    // 图片预览时的绝对路径
    private String imagePaths = ConfigurationUtil.getString("picturePreviewPath");

    /**
     * 供应商管理-供应商店铺列表
     *
     * @return
     */
    public String supplierShopList() {
        try {
            if (page == null) {
                page = new Page();
            }
            if (searchShopMain == null) {
                searchShopMain = new ShopMain();
            }

            getRequest().setAttribute("suppliersStatusMap", SuppliersShopStatusMap.getMap());//审核状态集合
            supplierShopService.supplierShopListShow(page, searchShopMain);
        } catch (Exception e) {
            logger.error("供应商管理-查询供应商店铺列表失败" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    public String gotoSupplierShopView() {
        try {
            showShopMain = supplierShopService.supplierShopView(shopId);
        } catch (Exception e) {
            logger.error("供应商店铺审核显示出现异常" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    public String updateSupplierShopStatus() throws Exception {
        ShopMain shopMain = new ShopMain();
        shopMain.setShopId(shopId);
        if (null == ckType) {//关闭店铺
            shopMain.setAuditStatus(SuppliersShopStatus.UNAUDIT.getStatus().toString());//关闭状态
            shopMain.setStatus("0");
        } else {//启用店铺
            shopMain.setAuditStatus("1");
        }

        try {
            boolean haveDone = supplierShopService.updateSupplierShopStatus(shopMain);
            String result = haveDone ? "1" : "0";
            strWriteJson(result);
        } catch (Exception e) {
            logger.error("启用或停用店铺出现异常" + e.getMessage(), e);
            throw e;
        }
        return null;
    }

    public String supplierShopForCause() {
        return SUCCESS;
    }

    public String auditSupplierShop() throws Exception {
        ShopMain shopMain = new ShopMain();
        shopMain.setShopId(shopId);
        if (ckType == null) {//审核不通过状态
            String newDescribe = getRequest().getParameter("describe"); //获取页面上的用户名
            newDescribe = URLDecoder.decode(newDescribe, "UTF-8"); //进行解码
            shopMain.setRemark(newDescribe);
            shopMain.setAuditStatus(SuppliersShopStatus.AUDIT.getStatus().toString());
        } else {
            shopMain.setAuditStatus(SuppliersShopStatus.APPLYING.getStatus().toString());//审核通过状态
        }

        try {
            boolean haveDone = supplierShopService.auditSupplierShopSer(shopMain, ckType);
            if (!haveDone) {
                strWriteJson("0");
                return null;
            }

            List<Long> shopIdList = Lists.newArrayList(shopMain.getShopId());
            supplierShopService.changeShopMainNotify(shopIdList, MsgOperation.UPDATE.getType());
            strWriteJson("1");
        } catch (Exception e) {
            logger.error("店铺审核出现异常" + e.getMessage(), e);
            throw e;
        }

        return null;
    }


    /**
     * 去修改店铺信息
     *
     * @return
     * @throws Exception
     */
    public String gotoSupplierShopUpdate() throws Exception {
        try {
            showShopMain = supplierShopService.supplierShopView(shopId);
        } catch (Exception e) {
            logger.error("供应商店铺修改显示出现异常" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 审核之前检查店铺状态，只有提交审核才能进行审核操作
     *
     * @return
     * @throws Exception
     */
    public String ckSupplierShopStatus() throws Exception {
        try {
            showShopMain = supplierShopService.supplierShopView(shopId);
            if ("0".equals(showShopMain.getAuditStatus())) {//不能进行审核
                strWriteJson("0");
            }
        } catch (Exception e) {
            logger.error("供应商店铺修改显示出现异常" + e.getMessage(), e);
            throw e;
        }
        return null;
    }

    /**
     * 导出店铺信息
     *
     * @return
     */
    public String exportSupplierShopList() {
        if (page == null) {
            page = new Page();
        }
        if (searchShopMain == null) {
            searchShopMain = new ShopMain();
        }

        FileInputStream f = null;
        ByteArrayInputStream bais = null;
        try {
            supplierShopService.exportExcelForSupplierShopList(searchShopMain);
            File file = new File(ConfigurationUtil.getString("exportExcelPath") + File.separator + "supplierShopList.xls");
            f = new FileInputStream(file);
            byte[] fb = new byte[f.available()];
            f.read(fb);
            ServletActionContext.getResponse().setHeader("Content-disposition", "attachment; filename=" + new String("供应商店铺列表.xls".getBytes("gb2312"), "iso8859-1"));
            bais = new ByteArrayInputStream(fb);
            int b;
            while ((b = bais.read()) != -1) {
                ServletActionContext.getResponse().getOutputStream().write(b);
            }
            ServletActionContext.getResponse().getOutputStream().flush();

            f.close();
            bais.close();
        } catch (Exception e) {
            logger.error("导出供应商店铺列表异常" + e.getMessage(), e);
            return ERROR;
        } finally {
            try {
                if (f != null) f.close();
                if (bais != null) bais.close();
            } catch (IOException e) {
                logger.error("导出供应商店铺列表关闭流文件流异常" + e.getMessage(), e);
            }
        }
        return null;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ShopMain getSearchShopMain() {
        return searchShopMain;
    }

    public void setSearchShopMain(ShopMain searchShopMain) {
        this.searchShopMain = searchShopMain;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public ShopMain getShowShopMain() {
        return showShopMain;
    }

    public void setShowShopMain(ShopMain showShopMain) {
        this.showShopMain = showShopMain;
    }

    public String getCkType() {
        return ckType;
    }

    public void setCkType(String ckType) {
        this.ckType = ckType;
    }

    public String getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(String imagePaths) {
        this.imagePaths = imagePaths;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
