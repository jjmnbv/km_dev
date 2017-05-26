package com.pltfm.app.action;

import com.google.common.collect.Lists;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.maps.ProductBrandMap;
import com.pltfm.app.service.CmsProductUpShelfService;
import com.pltfm.app.service.ProdBrandService;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.util.FileUploadUtils;
import com.pltfm.app.vobject.ProdBrand;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

/**
 * 品牌
 *
 * @author tanyunxing
 */
@Controller("prodBrandAction")
@Scope(value = "prototype")
public class ProdBrandAction extends BaseAction implements ModelDriven {

    private ProdBrand prodBrand = new ProdBrand();

    String rtnMessage; // 返回的信息

    private String[] delId; // 删除时的brandId

    private String name;

    private Long prodBrandId;

    private String path = ConfigurationUtil.getString("picturePreviewPath");

    @Resource
    private ProdBrandService prodBrandService;

    @Resource
    private ProductService productService;

    @Resource
    private CmsProductUpShelfService cmsProductUpShelfService;

    //品牌的Logo图片
    private File logoFile;

    //展馆首页的主图
    private File pavilionFile;

    //品牌的介绍文件
    private File introduceFile;

    //荣誉证书
    private File[] certificateFiles;

    private String[] certificateFilesPath;

    //依旧存在的荣誉证书
    private String[] oldCertificateFilesPath;

    //表示是否删除了荣誉证书	0:未删除，1:删除了
    private int isDeleteCertificate;

    private String[] contactTypes;

    private String[] contactValues;

    //上传logo图片判断分辨率的提示信息
    private String message;

    private String brandLogoWidth = ConfigurationUtil.getString("brandLogoWidth");

    private String brandLogoHeight = ConfigurationUtil.getString("brandLogoHeight");

    /**
     * 获取数据显示
     *
     * @return
     */
    public String show() {
        try {
            prodBrandService.searchPage(page, prodBrand);
        } catch (Exception e) {
            logger.error("获取数据显示失败，", e);
            return ERROR;
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
            logger.error("删除品牌失败，", e);
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
        try {
            BufferedImage bi = ImageIO.read(logoFile);
            return bi.getWidth() == Integer.parseInt(brandLogoWidth)
                    && bi.getHeight() == Integer.parseInt(brandLogoHeight);
        } catch (Exception e) {
            logger.error("读取品牌图片失败，", e);
        }
        return false;
    }

    private String handleFile(String path, File file, String lastPath) {
        try {
            // 获取文件后缀名
            String fileExt = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
            // 文件名
            String fileName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS") + "_" + new Random().nextInt(1000) + "." + fileExt;
            // 绝对路径，上传时使用
            String savePath = FileUploadUtils.createSavePath("productBrand");
            // 相对路径，预览时使用
            String pictureUploadPath = ConfigurationUtil.getString("pictureUploadPath");
            String relativePath = savePath.substring(pictureUploadPath.length());
            File deskFile = new File(savePath, fileName);
            //上传文件
            FileUtils.copyFile(file, deskFile);
            if (StringUtils.isNotEmpty(lastPath)) {
                File f = new File(pictureUploadPath + lastPath);
                if (f.exists()) {
                    f.delete();
                }
            }
            return relativePath + fileName;
        } catch (Exception e) {
            logger.error("处理图片失败，", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 增加品牌
     *
     * @return
     */
    public String saveProdBrand() {
        try {
            //品牌的Logo图片
            if (logoFile != null) {
                if (!checkImgPixel()) {//判断图片的分辨率大小
                    setMessage("请选择宽度为118,高为46的logo图片！");
                    return "toAddProdBrands";
                }
                prodBrand.setLogoPath(handleFile(prodBrand.getLogoPath(), logoFile, null));
            }

            //展馆首页的主图
            if (pavilionFile != null) {
                prodBrand.setPavilionPicPath(handleFile(prodBrand.getPavilionPicPath(), pavilionFile, null));
            }
            //品牌的介绍文件
            if (introduceFile != null) {
                prodBrand.setIntroduceFilePath(handleFile(prodBrand.getIntroduceFilePath(), introduceFile, null));
            }
            //荣誉证书
            if (ArrayUtils.isNotEmpty(certificateFiles)) {
                StringBuffer certificatePath = new StringBuffer();
                for (int i = 0; i < certificateFiles.length; i++) {
                    certificatePath.append(handleFile(certificateFilesPath[i], certificateFiles[i], null)).append(";");
                }
                prodBrand.setCertificateHonor(certificatePath.toString());
            }

            handleContact();
            Long tempBrandId = prodBrandService.addProdBrand(prodBrand);
            ProductBrandMap.setValue(tempBrandId, prodBrand.getBrandName());
        } catch (Exception e) {
            logger.error("添加品牌失败，", e);
            return Action.ERROR;
        }

        prodBrand = new ProdBrand();
        setRtnMessage("新增品牌成功！");
        return show();
    }

    /**
     * 处理品牌的联系方式
     */
    private void handleContact() {
        if (contactTypes != null && contactTypes.length > 0) {
            StringBuilder contact = new StringBuilder();
            for (int i = 0; i < contactTypes.length; i++) {
                contact.append(contactTypes[i]).append("：").append(contactValues[i]).append(";");
            }
            prodBrand.setContactInfo(contact.toString());
        }
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
            getProductBrand();
        } catch (Exception e) {
            logger.error("查看品牌数据失败，", e);
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
            getProductBrand();
        } catch (Exception e) {
            logger.error("更新品牌前的操作失败，", e);
            return Action.ERROR;
        }
        return Action.SUCCESS;
    }

    private void getProductBrand() {
        prodBrand = prodBrandService.findProdBrandById(prodBrand.getBrandId());
        if (StringUtils.isNotEmpty(prodBrand.getCertificateHonor())) {
            getRequest().setAttribute("certifiMap", StringUtils.split(prodBrand.getCertificateHonor(), ";"));
        }

        if (StringUtils.isNotEmpty(prodBrand.getContactInfo())) {
            String[] contact = StringUtils.split(prodBrand.getContactInfo(), ";");
            Map<String, String> contactMap = new LinkedHashMap();
            if (ArrayUtils.isNotEmpty(contact)) {
                for (String str : contact) {
                    String[] s = str.split("：");
                    if (s.length != 0) {
                        contactMap.put(s[0], s.length == 1 ? "" : s[1]);
                    }
                }
            }
            getRequest().setAttribute("contactMap", contactMap);
        }
    }

    /**
     * 更新操作
     *
     * @return
     */
    public String updateProdBrand() {
        try {
            ProdBrand pb = prodBrandService.findProdBrandById(prodBrand.getBrandId());

            //Logo图片
            if (logoFile != null) {
                if (!checkImgPixel()) {//判断图片的分辨率大小
                    setMessage("请选择宽度为118,高为46的logo图片！");
                    return "updateProdBrands";
                }
                prodBrand.setLogoPath(handleFile(prodBrand.getLogoPath(), logoFile, pb.getLogoPath()));
            }
            //展馆首页的主图
            if (pavilionFile != null) {
                prodBrand.setPavilionPicPath(handleFile(prodBrand.getPavilionPicPath(), pavilionFile, pb.getPavilionPicPath()));
            }
            //品牌的介绍文件
            if (introduceFile != null) {
                prodBrand.setIntroduceFilePath(handleFile(prodBrand.getIntroduceFilePath(), introduceFile, pb.getIntroduceFilePath()));
            }

            //比较是否删除的荣誉证书
            StringBuffer certificatePath = null;
            if (isDeleteCertificate == 0) {//0表示没有删除操作过，1表示有操作过删除
                certificatePath = new StringBuffer(StringUtils.isNotBlank(pb.getCertificateHonor()) ? pb.getCertificateHonor() : "");
            } else {
                certificatePath = new StringBuffer(compareCertificateHonor(pb));
            }
            //荣誉证书
            if (ArrayUtils.isNotEmpty(certificateFiles)) {
                for (int i = 0; i < certificateFiles.length; i++) {
                    certificatePath.append(handleFile(certificateFilesPath[i], certificateFiles[i], null)).append(";");
                }
            }
            if (isDeleteCertificate == 0 && StringUtils.isEmpty(certificatePath.toString())) {
                prodBrand.setCertificateHonor(null);
            } else {
                prodBrand.setCertificateHonor(certificatePath.toString());
            }

            handleContact();

            if ("1".equals(prodBrand.getIsValid())) {
                ProductBrandMap.setValue(prodBrand.getBrandId(), prodBrand.getBrandName());
            } else if ("0".equals(prodBrand.getIsValid())) {
                ProductBrandMap.removeValue(prodBrand.getBrandId());
            }

            prodBrandService.updateProdBrand(prodBrand);
            //如果品牌是来自供应商，同步修改品牌草稿数据
            if (prodBrandService.checkProdBrandIsFromSupplier(prodBrand.getBrandId())) {
                prodBrandService.updateProdBrandDraft(prodBrand);
            }
        } catch (Exception e) {
            logger.error("修改品牌数据失败，", e);
            return Action.ERROR;
        }
        setRtnMessage("更新品牌成功！");

        try {
            List<Integer> productIdList = productService.getProductIdByBrandId(prodBrand.getBrandId());
            if (CollectionUtils.isNotEmpty(productIdList)) {
                //CMS上架接口
                cmsProductUpShelfService.productUpShelfByCms(productIdList);
            }
        } catch (Exception e) {
            logger.error("修改品牌数据成功后，上架所有该品牌下的所有商品失败，", e);
        }
        prodBrand = new ProdBrand();
        return show();
    }

    /**
     * 查找是否有相同的品牌
     *
     * @return
     */
    public String findRepeatName() {
        try {
            int count = prodBrandService.findRepeatName(name);
            strWriteJson(""+count);
        } catch (Exception e) {
            logger.error("查找是否有相同的品牌失败，", e);
        }
        return null;
    }

    /**
     * 修改时查找是否同名品牌
     * @return
     */
    public String findUpdateRepeatName() {
        try {
            int count = prodBrandService.findUpdateRepeatName(prodBrandId, name);
            strWriteJson(""+count);
        } catch (Exception e) {
            logger.error("修改时查找是否同名品牌失败，", e);
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

    /**
     * 计算剩余的旧的荣誉证书地址
     *
     * @param brand
     * @return
     */
    private String compareCertificateHonor(ProdBrand brand) {
        //比较图片是否被删除了
        String certificateFilePath = "";

        //1.若之前就没有图片
        String certificateHonor = brand.getCertificateHonor();
        if (StringUtils.isEmpty(certificateHonor)) {
            return "";
        }

        //常量
        String[] preCertificateHonorPath = certificateHonor.split(";");
        String pictureUploadPath = ConfigurationUtil.getString("pictureUploadPath");
        List<String> preHonorList = Lists.newArrayList(preCertificateHonorPath);

        //2.还有剩下的老荣誉图片
        if (ArrayUtils.isNotEmpty(oldCertificateFilesPath)) {
            //移除还存在的荣誉证书
            preHonorList.removeAll(Lists.newArrayList(oldCertificateFilesPath));
            certificateFilePath = StringUtils.join(oldCertificateFilesPath, ";");
        }

        //3.去除图片
        preHonorList.stream().forEach(path -> {
            File f = new File(pictureUploadPath + path);
            if (f.exists()) {
                f.delete();
            }
        });

        return certificateFilePath;
    }

    public String findAllBrandForJson() {
        try {
            Map<Long, String> m = ProductBrandMap.getMap();
            Map<String, String> mp = new LinkedHashMap<String, String>();
            for (Map.Entry<Long, String> entry : m.entrySet()) {
                mp.put(entry.getKey().toString(), entry.getValue());
            }
            super.writeJson(mp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ProdBrand getModel() {
        return prodBrand;
    }

    public Page getPage() {
        return page;
    }

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
