package com.kmzyc.promotion.app.action;

import java.io.File;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.maps.ProductBrandMap;
import com.kmzyc.promotion.app.service.CmsProductUpShelfService;
import com.kmzyc.promotion.app.service.ProdBrandService;
import com.kmzyc.promotion.app.service.ProductService;
import com.kmzyc.promotion.app.service.SupplierAuditService;
import com.kmzyc.promotion.app.util.FileUploadUtils;
import com.kmzyc.promotion.app.vobject.ProdBrand;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 品牌
 * 
 * @author tanyunxing
 * 
 */
@SuppressWarnings("rawtypes")
@Controller("prodBrandAction")
@Scope(value = "prototype")
public class ProdBrandAction extends BaseAction implements ModelDriven {
    private Logger logger = LoggerFactory.getLogger(ProdBrandAction.class);
    private static final long serialVersionUID = 1L;
    private ProdBrand prodBrand = new ProdBrand();
    String rtnMessage; // 返回的信息
    private String[] delId; // 删除时的brandId
    private String name;

    private Long prodBrandId;

    @Resource
    private ProdBrandService prodBrandService;

    @Resource
    private ProductService productService;

    @Resource
    private CmsProductUpShelfService cmsProductUpShelfService;
    @Resource
    private SupplierAuditService supplierAuditService;
    // 品牌的Logo图片
    private File logoFile;

    // 展馆首页的主图
    private File pavilionFile;

    // 品牌的介绍文件
    private File introduceFile;

    // 荣誉证书
    private File[] certificateFiles;
    private String[] certificateFilesPath;
    // 依旧存在的荣誉证书
    private String[] oldCertificateFilesPath;

    // 表示是否删除了荣誉证书 0:未删除，1:删除了
    private int isDeleteCertificate;

    private String[] contactTypes;

    private String[] contactValues;

    // 上传logo图片判断分辨率的提示信息
    private String message;

    // private String brandLogoWidth =
    // ConfigurationUtil.getString("brandLogoWidth");
    // private String brandLogoHeight =
    // ConfigurationUtil.getString("brandLogoHeight");

    /**
     * 获取数据显示
     * 
     * @return
     */
    public String show() {

        try {
            prodBrandService.searchPage(page, prodBrand);
        } catch (Exception e) {
            logger.error("show方法获取数据显示异常：", e);
        }

        return Action.SUCCESS;
    }

    /**
     * 删除
     * 
     * @return
     */
    public String deleteProdBrand() {
        try {
            prodBrandService.deleteByPrimaryKey(delId);
            this.setRtnMessage("删除品牌成功!");
        } catch (Exception e) {
            logger.error("deleteProdBrand方法删除品牌异常：", e);
            this.setRtnMessage("删除品牌失败！");
        }
        return this.show();
    }

    /**
     * 限制图片上传的分辨率大小
     * 
     * @return
     */
    private boolean checkImgPixel() {
        return false;
    }

    /**
     * 增加品牌
     * 
     * @return
     */
    public String saveProdBrand() {

        Long index = new Long(0);

        try {

            // 品牌的Logo图片
            if (logoFile != null) {

                if (this.checkImgPixel() == false) {// 判断图片的分辨率大小
                    this.setMessage("请选择宽度为118,高为46的logo图片！");
                    return "toAddProdBrands";
                }
                // 获取文件后缀名
                String fileExt = prodBrand.getLogoPath()
                        .substring(prodBrand.getLogoPath().lastIndexOf(".") + 1).toLowerCase();
                // 文件名
                String fileName = DateFormatUtils.format(new java.util.Date(), "yyyyMMddHHmmssSSS")
                        + "_" + new Random().nextInt(1000) + "." + fileExt;
                // 绝对路径，上传时使用
                String savePath = FileUploadUtils.createSavePath("productBrand");
                // 相对路径，预览时使用
                String relativePath = savePath
                        .substring(ConfigurationUtil.getString("pictureUploadPath").length());
                File deskFile = new File(savePath, fileName);
                // 上传文件
                Thumbnails.of(logoFile).scale(1f).outputQuality(1f).toFile(deskFile);
                prodBrand.setLogoPath(relativePath + fileName);
            }
            // 展馆首页的主图
            if (pavilionFile != null) {
                // 获取文件后缀名
                String fileExt = prodBrand.getPavilionPicPath()
                        .substring(prodBrand.getPavilionPicPath().lastIndexOf(".") + 1)
                        .toLowerCase();
                // 文件名
                String fileName = DateFormatUtils.format(new java.util.Date(), "yyyyMMddHHmmssSSS")
                        + "_" + new Random().nextInt(1000) + "." + fileExt;
                // 绝对路径，上传时使用
                String savePath = FileUploadUtils.createSavePath("productBrand");
                // 相对路径，预览时使用
                String relativePath = savePath
                        .substring(ConfigurationUtil.getString("pictureUploadPath").length());
                File deskFile = new File(savePath, fileName);
                // 上传文件
                Thumbnails.of(pavilionFile).scale(1f).outputQuality(1f).toFile(deskFile);
                prodBrand.setPavilionPicPath(relativePath + fileName);
            }
            // 品牌的介绍文件
            if (introduceFile != null) {
                // 获取文件后缀名
                String fileExt = prodBrand.getIntroduceFilePath()
                        .substring(prodBrand.getIntroduceFilePath().lastIndexOf(".") + 1)
                        .toLowerCase();
                // 文件名
                String fileName = DateFormatUtils.format(new java.util.Date(), "yyyyMMddHHmmssSSS")
                        + "_" + new Random().nextInt(1000) + "." + fileExt;
                // 绝对路径，上传时使用
                String savePath = FileUploadUtils.createSavePath("productBrand");
                // 相对路径，预览时使用
                String relativePath = savePath
                        .substring(ConfigurationUtil.getString("pictureUploadPath").length());
                File deskFile = new File(savePath, fileName);

                FileUtils.copyFile(introduceFile, deskFile);
                prodBrand.setIntroduceFilePath(relativePath + fileName);
            }
            // 荣誉证书
            if (certificateFiles != null && certificateFiles.length > 0) {
                String certificateFilePath = "";
                for (int i = 0; i < certificateFiles.length; i++) {
                    // 获取文件后缀名
                    String fileExt = certificateFilesPath[i]
                            .substring(certificateFilesPath[i].lastIndexOf(".") + 1).toLowerCase();
                    // 文件名
                    String fileName =
                            DateFormatUtils.format(new java.util.Date(), "yyyyMMddHHmmssSSS") + "_"
                                    + new Random().nextInt(1000) + "." + fileExt;
                    // 绝对路径，上传时使用
                    String savePath = FileUploadUtils.createSavePath("productBrand");
                    // 相对路径，预览时使用
                    String relativePath = savePath
                            .substring(ConfigurationUtil.getString("pictureUploadPath").length());
                    File deskFile = new File(savePath, fileName);
                    // 上传文件
                    Thumbnails.of(certificateFiles[i]).scale(1f).outputQuality(1f).toFile(deskFile);
                    certificateFilePath += relativePath + fileName + ";";
                }
                prodBrand.setCertificateHonor(certificateFilePath);
            }

            if (contactTypes != null && contactTypes.length > 0) {
                String contact = "";
                for (int i = 0; i < contactTypes.length; i++) {
                    contact += contactTypes[i] + "：" + contactValues[i] + ";";
                }
                prodBrand.setContactInfo(contact);
            }

            index = prodBrandService.addProdBrand(prodBrand);
        } catch (Exception e) {
            logger.error("saveProdBrand方法新增品牌异常：", e);
            return Action.ERROR;
        }

        ProductBrandMap.setValue(Long.valueOf(index), prodBrand.getBrandName());
        prodBrand = new ProdBrand();
        this.setRtnMessage("新增品牌成功！");
        return this.show();
    }

    /**
     * 添加品牌前的操作
     * 
     * @return
     */
    public String toAddProdBrand() {
        return Action.SUCCESS;
    }

    /**
     * 查看
     * 
     * @return
     */
    public String gotoView() {

        try {
            this.prodBrand = prodBrandService.findProdBrandById(prodBrand.getBrandId());

            if (prodBrand.getCertificateHonor() != null) {
                String certifis = prodBrand.getCertificateHonor();
                String[] certifi = certifis.split(";");
                super.getRequest().setAttribute("certifiMap", certifi);
            } else {
                super.getRequest().setAttribute("certifiMap", null);
            }

            if (prodBrand.getContactInfo() != null) {
                String contacts = prodBrand.getContactInfo();
                String[] contact = contacts.split(";");

                Map<String, String> contactMap = new LinkedHashMap<String, String>();
                if (contact != null && contact.length > 0) {
                    for (String str : contact) {
                        String[] s = str.split("：");
                        if (s.length != 0) {
                            if (s.length == 1) {
                                contactMap.put(s[0], "");
                            } else {
                                contactMap.put(s[0], s[1]);
                            }
                        }
                    }
                }
                super.getRequest().setAttribute("contactMap", contactMap);
            } else {
                super.getRequest().setAttribute("contactMap", null);
            }
        } catch (Exception e) {
            logger.error("gotoView方法异常：", e);
            return Action.ERROR;
        }

        return Action.SUCCESS;
    }

    /**
     * 更新前的操作
     * 
     * @return
     */
    public String gotoUpdate() {
        try {
            this.prodBrand = prodBrandService.findProdBrandById(prodBrand.getBrandId());

            if (prodBrand.getCertificateHonor() != null) {
                String certifis = prodBrand.getCertificateHonor();
                String[] certifi = certifis.split(";");
                super.getRequest().setAttribute("certifiMap", certifi);
            } else {
                super.getRequest().setAttribute("certifiMap", null);
            }

            if (prodBrand.getContactInfo() != null) {
                String contacts = prodBrand.getContactInfo();
                String[] contact = contacts.split(";");

                Map<String, String> contactMap = new LinkedHashMap<String, String>();
                if (contact != null && contact.length > 0) {
                    for (String str : contact) {
                        String[] s = str.split("：");
                        if (s.length != 0) {
                            if (s.length == 1) {
                                contactMap.put(s[0], "");
                            } else {
                                contactMap.put(s[0], s[1]);
                            }
                        }
                    }
                }
                super.getRequest().setAttribute("contactMap", contactMap);
            } else {
                super.getRequest().setAttribute("contactMap", null);
            }
        } catch (Exception e) {
            logger.error("gotoUpdate方法新前的操作异常：", e);
            return Action.ERROR;
        }
        return Action.SUCCESS;
    }

    /**
     * 更新操作
     * 
     * @return
     */
    public String updateProdBrand() {

        try {
            ProdBrand pb = prodBrandService.findProdBrandById(prodBrand.getBrandId());

            // Logo图片
            if (logoFile != null) {

                if (this.checkImgPixel() == false) {// 判断图片的分辨率大小
                    this.setMessage("请选择宽度为118,高为46的logo图片！");
                    return "updateProdBrands";
                }
                // 获取文件后缀名
                String fileExt = prodBrand.getLogoPath()
                        .substring(prodBrand.getLogoPath().lastIndexOf(".") + 1).toLowerCase();

                // 创建文件名 例如：20130808113311_12.jpg
                String fileName = DateFormatUtils.format(new java.util.Date(), "yyyyMMddHHmmss")
                        + "_" + new Random().nextInt(1000) + "." + fileExt;
                // 绝对路径，上传时使用
                String savePath = FileUploadUtils.createSavePath("productBrand");
                // 相对路径，预览时使用
                String relativePath = savePath
                        .substring(ConfigurationUtil.getString("pictureUploadPath").length());
                File deskFile = new File(savePath, fileName);
                // 上传文件
                Thumbnails.of(logoFile).scale(1f).outputQuality(1f).toFile(deskFile);
                prodBrand.setLogoPath(relativePath + fileName);

                File f = new File(
                        ConfigurationUtil.getString("pictureUploadPath") + pb.getLogoPath());

                if (f.exists()) {
                    f.delete();
                }
            }
            // 展馆首页的主图
            if (pavilionFile != null) {
                // 获取文件后缀名
                String fileExt = prodBrand.getPavilionPicPath()
                        .substring(prodBrand.getPavilionPicPath().lastIndexOf(".") + 1)
                        .toLowerCase();
                // 文件名
                String fileName = DateFormatUtils.format(new java.util.Date(), "yyyyMMddHHmmssSSS")
                        + "_" + new Random().nextInt(1000) + "." + fileExt;
                // 绝对路径，上传时使用
                String savePath = FileUploadUtils.createSavePath("productBrand");
                // 相对路径，预览时使用
                String relativePath = savePath
                        .substring(ConfigurationUtil.getString("pictureUploadPath").length());
                File deskFile = new File(savePath, fileName);
                // 上传文件
                Thumbnails.of(pavilionFile).scale(1f).outputQuality(1f).toFile(deskFile);
                prodBrand.setPavilionPicPath(relativePath + fileName);

                File f = new File(
                        ConfigurationUtil.getString("pictureUploadPath") + pb.getPavilionPicPath());

                if (f.exists()) {
                    f.delete();
                }
            }
            // 品牌的介绍文件
            if (introduceFile != null) {
                // 获取文件后缀名
                String fileExt = prodBrand.getIntroduceFilePath()
                        .substring(prodBrand.getIntroduceFilePath().lastIndexOf(".") + 1)
                        .toLowerCase();
                // 文件名
                String fileName = DateFormatUtils.format(new java.util.Date(), "yyyyMMddHHmmssSSS")
                        + "_" + new Random().nextInt(1000) + "." + fileExt;
                // 绝对路径，上传时使用
                String savePath = FileUploadUtils.createSavePath("productBrand");
                // 相对路径，预览时使用
                String relativePath = savePath
                        .substring(ConfigurationUtil.getString("pictureUploadPath").length());
                File deskFile = new File(savePath, fileName);

                FileUtils.copyFile(introduceFile, deskFile);
                prodBrand.setIntroduceFilePath(relativePath + fileName);
                File f = new File(ConfigurationUtil.getString("pictureUploadPath")
                        + pb.getIntroduceFilePath());

                if (f.exists()) {
                    f.delete();
                }
            }

            String certificateFilePath = "";

            if (isDeleteCertificate != 0) {
                // 比较是否删除的荣誉证书
                certificateFilePath = this.compareCertificateHonor(pb);
            } else {
                certificateFilePath = pb.getCertificateHonor();
            }

            // 荣誉证书
            if (certificateFiles != null && certificateFiles.length > 0) {
                for (int i = 0; i < certificateFiles.length; i++) {
                    // 获取文件后缀名
                    String fileExt = certificateFilesPath[i]
                            .substring(certificateFilesPath[i].lastIndexOf(".") + 1).toLowerCase();
                    // 文件名
                    String fileName =
                            DateFormatUtils.format(new java.util.Date(), "yyyyMMddHHmmssSSS") + "_"
                                    + new Random().nextInt(1000) + "." + fileExt;
                    // 绝对路径，上传时使用
                    String savePath = FileUploadUtils.createSavePath("productBrand");
                    // 相对路径，预览时使用
                    String relativePath = savePath
                            .substring(ConfigurationUtil.getString("pictureUploadPath").length());
                    File deskFile = new File(savePath, fileName);
                    // 上传文件
                    Thumbnails.of(certificateFiles[i]).scale(1f).outputQuality(1f).toFile(deskFile);
                    certificateFilePath += relativePath + fileName + ";";
                }
            }
            if (isDeleteCertificate == 0 && StringUtils.isEmpty(certificateFilePath)) {
                prodBrand.setCertificateHonor(null);
            } else {
                prodBrand.setCertificateHonor(certificateFilePath);
            }

            if (contactTypes != null && contactTypes.length > 0) {
                String contact = "";
                for (int i = 0; i < contactTypes.length; i++) {
                    contact += contactTypes[i] + "：" + contactValues[i] + ";";
                }
                prodBrand.setContactInfo(contact);
            }

            if (prodBrand.getIsValid().equals("1")) {
                ProductBrandMap.setValue(prodBrand.getBrandId(), prodBrand.getBrandName());
            }
            if (prodBrand.getIsValid().equals("0")) {
                ProductBrandMap.removeValue(prodBrand.getBrandId());
            }

            prodBrandService.updateProdBrand(prodBrand);
        } catch (Exception e) {
            logger.error("updateProdBrand方法异常：", e);
            return Action.ERROR;
        }
        this.setRtnMessage("更新品牌成功！");

        // if(StringUtils.isNotBlank(prodBrand.getBrandIntroduceLazy())){
        try {
            List<Integer> productIdList =
                    productService.getProductIdByBrandId(prodBrand.getBrandId());
            if (productIdList != null && !productIdList.isEmpty()) {
                // CMS上架接口
                cmsProductUpShelfService.productUpShelfByCms(productIdList);
            }
        } catch (Exception e) {
            logger.error("cmsProductUpShelfService.productUpShelfByCms方法异常：", e);
        }
        // }

        prodBrand = new ProdBrand();
        return this.show();
    }

    /**
     * 查找是否有相同的品牌
     * 
     * @return
     */
    public String findRepeatName() {

        try {
            int count = prodBrandService.findRepeatName(name);

            PrintWriter out = super.getPrintWriter();
            out.print(count);
            out.flush();
            out.close();

        } catch (Exception e) {
            logger.error("findRepeatName方法异常：", e);
        }
        return null;
    }

    public String findUpdateRepeatName() {
        try {
            int count = prodBrandService.findUpdateRepeatName(prodBrandId, name);

            PrintWriter out = super.getPrintWriter();
            out.print(count);
            out.flush();
            out.close();

        } catch (Exception e) {
            logger.error("findUpdateRepeatName方法异常：", e);
        }

        return null;
    }

    /**
     * 显示图片
     * 
     * @return
     */
    public String pictureShow() {
        // super.showPicture(path);
        return null;
    }

    private String compareCertificateHonor(ProdBrand pb) {
        // 比较图片是否被删除了 begin
        String certificateFilePath = "";
        if (oldCertificateFilesPath != null && oldCertificateFilesPath.length > 0) {
            String[] cFiles = pb.getCertificateHonor().split(";");
            boolean flag = true;
            for (String oldPath : cFiles) {
                flag = true;
                for (String exsitPath : oldCertificateFilesPath) {
                    if (oldPath.equals(exsitPath)) {
                        certificateFilePath += oldPath + ";";
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    File f = new File(ConfigurationUtil.getString("pictureUploadPath") + oldPath);

                    if (f.exists()) {
                        f.delete();
                    }
                }
            }
        } else {
            String[] cFiles = pb.getCertificateHonor().split(";");
            for (String pa : cFiles) {
                File f = new File(ConfigurationUtil.getString("pictureUploadPath") + pa);
                if (f.exists()) {
                    f.delete();
                }
            }
        }
        // 比较图片是否被删除了 end
        return certificateFilePath;
    }

    public String findAllBrandForJson() {
        try {
            Map<Long, String> m = ProductBrandMap.getMap();
            Map<String, String> mp = new LinkedHashMap<String, String>();
            for (Long key : m.keySet()) {
                mp.put(key.toString(), m.get(key));
            }
            super.writeJson(mp);
        } catch (Exception e) {
            logger.error("findAllBrandForJson方法异常：", e);
        }
        return null;
    }


    /**
     * 获取到所有的供应商列表 20140928 maliqun add
     * 
     * @return
     */
    public String findAllSuppliersForJson() {
        try {
            Map<String, String> supplierMap = supplierAuditService.supplierIdAndMerchantNameMap();
            Map<String, String> mp = new LinkedHashMap<String, String>();
            for (String key : supplierMap.keySet()) {
                mp.put(key, supplierMap.get(key));
            }
            super.writeJson(mp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // setter and getter

    @Override
    public ProdBrand getModel() {
        return prodBrand;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public String getRtnMessage() {
        return rtnMessage;
    }

    public void setRtnMessage(String rtnMessage) {
        this.rtnMessage = rtnMessage;
    }

    public String[] getDelId() {
        return delId;
    }

    public void setDelId(String[] delId) {
        this.delId = delId;
    }

    public File getLogoFile() {
        return logoFile;
    }

    public void setLogoFile(File logoFile) {
        this.logoFile = logoFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getPavilionFile() {
        return pavilionFile;
    }

    public void setPavilionFile(File pavilionFile) {
        this.pavilionFile = pavilionFile;
    }

    public File getIntroduceFile() {
        return introduceFile;
    }

    public void setIntroduceFile(File introduceFile) {
        this.introduceFile = introduceFile;
    }

    public File[] getCertificateFiles() {
        return certificateFiles;
    }

    public void setCertificateFiles(File[] certificateFiles) {
        this.certificateFiles = certificateFiles;
    }

    public String[] getContactTypes() {
        return contactTypes;
    }

    public void setContactTypes(String[] contactTypes) {
        this.contactTypes = contactTypes;
    }

    public String[] getContactValues() {
        return contactValues;
    }

    public void setContactValues(String[] contactValues) {
        this.contactValues = contactValues;
    }

    public String[] getCertificateFilesPath() {
        return certificateFilesPath;
    }

    public void setCertificateFilesPath(String[] certificateFilesPath) {
        this.certificateFilesPath = certificateFilesPath;
    }

    public String[] getOldCertificateFilesPath() {
        return oldCertificateFilesPath;
    }

    public void setOldCertificateFilesPath(String[] oldCertificateFilesPath) {
        this.oldCertificateFilesPath = oldCertificateFilesPath;
    }

    public int getIsDeleteCertificate() {
        return isDeleteCertificate;
    }

    public void setIsDeleteCertificate(int isDeleteCertificate) {
        this.isDeleteCertificate = isDeleteCertificate;
    }

    public Long getProdBrandId() {
        return prodBrandId;
    }

    public void setProdBrandId(Long prodBrandId) {
        this.prodBrandId = prodBrandId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
