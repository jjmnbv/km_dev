package com.kmzyc.supplier.ajax;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.service.ProductImageService;
import com.kmzyc.supplier.util.FileUploadUtils;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.ProductImage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;


@Controller("productImageAjaxAction")
@Scope(value = "prototype")
public class ProductImageAjaxAction extends SupplierBaseAction {

    private static Logger log = LoggerFactory.getLogger(ProductImageAjaxAction.class);

    @Resource
    private ProductImageService productImageService;

    private Long productSkuId;

    private Long productId;

    private String productNo;

    private Long picId;

    private File[] imgFile;

    private String[] imgFileFileName;

    private ProductImage image = new ProductImage();

    private File[] checkCertiFile;

    // 图片的像素大小，长#高，从大到小
    private final String[] imageSizeArray = ConfigurationUtil.getString("picResolution").split(",");

    /**
     * 正式商品管理图片，SKU图片上传
     *
     * @return
     */
    public String uploadOfficialImage() {
        ResultMessage rm = new ResultMessage();
        try {
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
                fileName = DateFormatUtils.format(new java.util.Date(), "yyyyMMddHHmmssSSS")
                                + new Random().nextInt(1000) + "_" + productSkuId.toString();
                destFile = new File(savePath, fileName + "." + fileExt);
                // 上传文件
                FileUtils.copyFile(srcFile, destFile);
                image.setImgPath(relativePath + fileName + "." + fileExt);
                maxSortNo = productImageService.findMaxSortNoBySkuId(productSkuId);

                // 需要修改的地方，和产品Id关联
                image.setProductNo(productNo);
                image.setSkuId(productSkuId);
                image.setProductId(productId);
                image.setIsDefault("1");
                if (maxSortNo == 0) {
                    image.setIsDefault("0");
                }
                image.setSortno(++maxSortNo);

                //按照像素上传
                String destFilePath = FileUploadUtils.handleSkuImage(imageSizeArray, fileName, fileExt, savePath,
                        relativePath, srcFile, image, null, Boolean.FALSE);
                Long imageId = productImageService.saveImage(image);
                msg.append("@" + imageId + "," + destFilePath);
            }
            rm.setMessage(msg.substring(1));
            rm.setIsSuccess(Boolean.TRUE);
        } catch (Exception e) {
            rm.setIsSuccess(false);
            rm.setMessage("系统发生错误，请稍后再试！");
            log.error("优化功能（图片上传）失败，", e);
        }

        writeJson(rm);
        return null;
    }

    public String modifyProductImageDefault() {
        try {
            productImageService.modifyImageDefault(productSkuId, picId);
            writeStr("success");
        } catch (Exception e) {
            log.error("modifyProductImageDefault error:"+e.getMessage(),e);
        }

        return null;
    }

    /**
     * 校验商品资质认证上传图片的大小，像素
     *
     * @return
     * @author Administrator
     */
    public String checkCertiFileImage() {
        ResultMessage rm = new ResultMessage();
        String msg = "";
        if (checkCertiFile == null) {
            rm.setMessage("请上传资质文件，图片尺寸800px*800px以上，大小1M以内，格式png/jpg/jpeg格式，可上传多张");
            writeJson(rm);
            return null;
        }

        try {
            BufferedImage bi = null;
            Long certiFileLogoSize = ConfigurationUtil.getLongValue("certiFileLogoSize");
            for (int i = 0; i < checkCertiFile.length; i++) {
                bi = ImageIO.read(checkCertiFile[i]);
                if (bi.getWidth() < 800 && bi.getHeight() < 800) {
                    msg = "像素不足800*800，请重新选择图片！";
                    break;
                }
                if (checkCertiFile[i].length() > certiFileLogoSize) {
                    msg = "图片大小不能超过1M!";
                    break;
                }
            }

            rm.setMessage(msg);
            rm.setIsSuccess(StringUtils.isEmpty(msg));
        } catch (Exception e) {
            rm.setMessage("系统发生错误，请稍后再试！");
            log.error("校验商品资质认证上传图片的大小，像素异常：", e);
        }
        writeJson(rm);
        return null;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
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

    public ProductImage getImage() {
        return image;
    }

    public void setImage(ProductImage image) {
        this.image = image;
    }

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long picId) {
        this.picId = picId;
    }

    public File[] getCheckCertiFile() {
        File[] temp = checkCertiFile;
        return temp;
    }

    public void setCheckCertiFile(File[] checkCertiFile) {
        this.checkCertiFile = checkCertiFile;
    }

}
