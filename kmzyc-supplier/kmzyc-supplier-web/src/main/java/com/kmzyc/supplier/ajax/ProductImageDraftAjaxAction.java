package com.kmzyc.supplier.ajax;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.service.ProductDraftSkuService;
import com.kmzyc.supplier.service.ProductImageDraftService;
import com.kmzyc.supplier.service.ProductImageService;
import com.kmzyc.supplier.service.ProductMainService;
import com.kmzyc.supplier.util.FileUploadUtils;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.DraftType;
import com.pltfm.app.vobject.ProductImageDraft;
import com.pltfm.app.vobject.ProductSkuDraft;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

@Controller("productImageDraftAjaxAction")
@Scope(value = "prototype")
public class ProductImageDraftAjaxAction extends SupplierBaseAction {

    private static Logger log = LoggerFactory.getLogger(ProductImageDraftAjaxAction.class);

    @Resource
    private ProductImageDraftService productImageDraftService;

    @Resource
    private ProductImageService productImageService;

    @Resource
    private ProductMainService productMainService;

    @Resource
    private ProductDraftSkuService productDraftSkuService;

    private ProductSkuDraft productSkuDraft;

    private File file;

    private Long productSkuId;

    private String fileFileName;

    private String fileContentType;

    private ProductImageDraft image = new ProductImageDraft();

    private Long picId;

    private String productNo;

    private Long productId;

    private String uploadpreview; // 用于指定在哪个Div层预览

    private String type;

    private List<Long> imageId;

    // 图片预览时的绝对路径
    private String imagePath = ConfigurationUtil.getString("picturePreviewPath");

    // 图片的像素大小，长#高，从大到小
    private final String[] imageSizeArray = ConfigurationUtil.getString("picResolution").split(",");

    private File[] imgFile;

    private String[] imgFileFileName;

    private List<String> upFileName;

    /**
     * 用于实现上传功能
     */
    public String uploadFile() {


        return null;
    }

    /**
     * 商品草稿sku管理图片
     *
     * @return
     */
    public String uploadImage() {
        ResultMessage rm = new ResultMessage();
        try {
            //1.校验图片
            rm = FileUploadUtils.checkProductSkuImg(imgFile, imgFileFileName);
            if (!rm.getIsSuccess()) {
                writeJson(rm);
                return null;
            }

            StringBuilder msg = new StringBuilder();
            // 图片上传时的绝对路径
            String savePath = FileUploadUtils.createProductSavePath(productId.toString(), productSkuId.toString());
            // 图片预览时的相对路径
            String relativePath = savePath.substring(ConfigurationUtil.getString("pictureUploadPath").length());
            // 获取文件后缀名
            String fileExt = "";
            // 文件名
            String fileName = "";
            File destFile = null;
            File srcFile = null;
            Short maxSortNo = 0;
            for (int j = 0; j < imgFile.length; j++) {
                srcFile = imgFile[j];
                fileExt = imgFileFileName[j].substring(imgFileFileName[j].lastIndexOf(".") + 1).toLowerCase();
                fileName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS")
                        + new Random().nextInt(1000) + "_" + productSkuId.toString() ;
                destFile = new File(savePath, fileName + "." + fileExt);
                // 上传文件
                FileUtils.copyFile(srcFile, destFile);
                image.setImgPath(relativePath + fileName + "." + fileExt);
                maxSortNo = (short) productImageDraftService.findMaxSortNoBySkuId(productSkuId);
                // 需要修改的地方，和产品Id关联
                image.setProductNo(productNo);
                image.setSkuId(productSkuId);
                image.setProductId(productId);
                image.setIsDefault("1");
                if (maxSortNo == 0) {
                    image.setIsDefault("0");
                }
                image.setSortno(++maxSortNo);
                image.setOpType(DraftType.ADD.getStatus());

                //按照像素上传
                String destFilePath = FileUploadUtils.handleSkuImage(imageSizeArray, fileName, fileExt, savePath,
                        relativePath, srcFile, null, image, Boolean.TRUE);
                Long imageId = productImageDraftService.saveImage(image);
                msg.append("@" + imageId + "," + destFilePath);
            }
            rm.setIsSuccess(Boolean.TRUE);
            rm.setMessage(msg.substring(1));
        } catch (Exception e) {
            rm.setIsSuccess(false);
            rm.setMessage("系统发生错误，请稍后再试！");
            log.error("商品草稿sku管理图片上传失败:", e);
        }
        writeJson(rm);
        return null;
    }

    /**
     * 删除图片
     *
     * @return
     */
    public String deleteImage() {
        Map<String, Object> jsonMap = new HashMap();
        try {
            productImageDraftService.deleteImageById(picId);
            jsonMap.put("msg", "success");
        } catch (Exception e) {
            log.error("删除图片失败：", e);
            jsonMap.put("msg", "systemError");
        }

        writeJson(jsonMap);
        return null;
    }

    /**
     * 修改默认的图片
     *
     * @return
     */
    public String modifyImageDefault() {
        Map<String, Object> jsonMap = new HashMap();
        String msg = "fail";
        try {
            productImageDraftService.modifyImageDefault(productSkuId, picId);
            msg = "success";
        } catch (Exception e) {
            log.error("修改默认的图片失败：", e);
        }

        jsonMap.put("msg", msg);
        writeJson(jsonMap);
        return null;
    }

    /**
     * 修改图片顺序
     *
     * @return
     */
    public String updateImageSortNo() {
        Map<String, Object> jsonMap = new HashMap();
        List<ProductImageDraft> list = null;
        if (imageId != null && imageId.size() > 0) {
            list = new ArrayList();
            ProductImageDraft pi = null;
            for (int i = 1; i <= imageId.size(); i++) {
                pi = new ProductImageDraft();
                if (i == 1) {
                    pi.setIsDefault("0");
                } else {
                    pi.setIsDefault("1");
                }
                pi.setImageId(imageId.get(i - 1));
                pi.setSortno((short) i);
                list.add(pi);
            }
        }

        try {
            boolean haveDone = productImageService.updateProductImageBatch(list);
            jsonMap.put("msg", haveDone ? "操作成功！" : "操作失败！");
        } catch (ServiceException e) {
            log.error("修改图片顺序失败，", e);
        }

        writeJson(jsonMap);
        return null;
    }

    public String updateSkuIntroduce() {
        Map<String, Object> jsonMap = new HashMap();
        try {
            boolean haveDone = productDraftSkuService.updateSkuIntroduce(productSkuDraft);
            jsonMap.put("flag", haveDone);
            jsonMap.put("msg", haveDone ? "保存成功!" : "保存失败!");
        } catch (Exception e) {
            log.error("updateSkuIntroduce error", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "保存失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    /**
     * 根据路径删除文件
     *
     * @param path
     */
    private void deleteFile(String path) {
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long picId) {
        this.picId = picId;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getUploadpreview() {
        return uploadpreview;
    }

    public void setUploadpreview(String uploadpreview) {
        this.uploadpreview = uploadpreview;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Long> getImageId() {
        return imageId;
    }

    public void setImageId(List<Long> imageId) {
        this.imageId = imageId;
    }

    public ProductSkuDraft getProductSkuDraft() {
        return productSkuDraft;
    }

    public void setProductSkuDraft(ProductSkuDraft productSkuDraft) {
        this.productSkuDraft = productSkuDraft;
    }

    public File[] getImgFile() {
        File[] temp = imgFile;
        return temp;
    }

    public void setImgFile(File[] imgFile) {
        this.imgFile = imgFile;
    }

    public String[] getImgFileFileName() {
        String[] temp = imgFileFileName;
        return temp;
    }

    public void setImgFileFileName(String[] imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }

    public List<String> getUpFileName() {
        return upFileName;
    }

    public void setUpFileName(List<String> upFileName) {
        this.upFileName = upFileName;
    }

}