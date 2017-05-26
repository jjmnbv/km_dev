package com.kmzyc.supplier.util;

import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleRender;
import com.alibaba.simpleimage.render.WriteRender;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageDraft;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class FileUploadUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);

    private static String ctxDir = ConfigurationUtil.getString("pictureUploadPath");

    /**
     * 创建图片上传路径
     *
     * @return
     */
    public static String createSavePath(String dictory) {
        String uploadDir = "upload" + File.separatorChar + dictory + File.separatorChar;
        String ctxDir = ConfigurationUtil.getString("pictureUploadPath");
        if (!ctxDir.endsWith(String.valueOf(File.separatorChar))) {
            ctxDir = ctxDir + File.separatorChar;
        }
        File savePath = new File(ctxDir + uploadDir);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        String saveDirectory = ctxDir + uploadDir;

        return saveDirectory;
    }

    /**
     * 创建图片上传路径
     *
     * @return
     */
    public static String createSupplierSavePath(String dictory) {
        String uploadDir = "upload" + File.separatorChar + dictory
                + File.separatorChar;
        String ctxDir = ConfigurationUtil.getString("SUPPLIER_CERTIFICATE_PATH");
        if (!ctxDir.endsWith(String.valueOf(File.separatorChar))) {
            ctxDir = ctxDir + File.separatorChar;
        }
        File savePath = new File(ctxDir + uploadDir);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        String saveDirectory = ctxDir + uploadDir;

        return saveDirectory;
    }

    /**
     * 创建产品图片上传路径
     *
     * @return
     */
    public static String createProductSavePath(String productIdDictory, String skuIdDictory) {
        String uploadDir = "upload" + File.separatorChar + "product"
                + File.separatorChar + productIdDictory + File.separatorChar
                + skuIdDictory + File.separatorChar;
//		String ctxDir = ConfigurationUtil.getString("pictureUploadPath");
        if (!ctxDir.endsWith(String.valueOf(File.separatorChar))) {
            ctxDir = ctxDir + File.separatorChar;
        }
        File savePath = new File(ctxDir + uploadDir);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        String saveDirectory = ctxDir + uploadDir;

        return saveDirectory;
    }

    /**
     * 创建产品图片上传路径
     *
     * @return
     */
    public static String createProductDraftSavePath(String productIdDictory, String skuIdDictory) {
        String uploadDir = "upload" + File.separatorChar + "productDraft"
                + File.separatorChar + productIdDictory + File.separatorChar
                + skuIdDictory + File.separatorChar;
//		String ctxDir = ConfigurationUtil.getString("pictureUploadPath");
        if (!ctxDir.endsWith(String.valueOf(File.separatorChar))) {
            ctxDir = ctxDir + File.separatorChar;
        }
        File savePath = new File(ctxDir + uploadDir);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        String saveDirectory = ctxDir + uploadDir;

        return saveDirectory;
    }

    /**
     * 删除正式表图片
     *
     * @param pi
     */
    public static void deleteProductImageFile(ProductImage pi) {
        String AP_PATH = ConfigurationUtil.getString("pictureUploadPath");

        if (pi.getImgPath() != null && !pi.getImgPath().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath());
        }
        if (pi.getImgPath1() != null && !pi.getImgPath1().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath1());
        }
        if (pi.getImgPath2() != null && !pi.getImgPath2().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath2());
        }
        if (pi.getImgPath3() != null && !pi.getImgPath3().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath3());
        }
        if (pi.getImgPath4() != null && !pi.getImgPath4().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath4());
        }
        if (pi.getImgPath5() != null && !pi.getImgPath5().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath5());
        }
        if (pi.getImgPath6() != null && !pi.getImgPath6().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath6());
        }
        if (pi.getImgPath7() != null && !pi.getImgPath7().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath7());
        }
        if (pi.getImgPath8() != null && !pi.getImgPath8().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath8());
        }
        if (pi.getImgPath9() != null && !pi.getImgPath9().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath9());
        }
        if (pi.getImgPath10() != null && !pi.getImgPath10().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath10());
        }
    }

    /**
     * 删除草稿表图片
     *
     * @param pi
     */
    public static void deleteProductImageDraftFile(ProductImageDraft pi) {
        String AP_PATH = ConfigurationUtil.getString("pictureUploadPath");

        if (pi.getImgPath() != null && !pi.getImgPath().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath());
        }
        if (pi.getImgPath1() != null && !pi.getImgPath1().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath1());
        }
        if (pi.getImgPath2() != null && !pi.getImgPath2().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath2());
        }
        if (pi.getImgPath3() != null && !pi.getImgPath3().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath3());
        }
        if (pi.getImgPath4() != null && !pi.getImgPath4().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath4());
        }
        if (pi.getImgPath5() != null && !pi.getImgPath5().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath5());
        }
        if (pi.getImgPath6() != null && !pi.getImgPath6().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath6());
        }
        if (pi.getImgPath7() != null && !pi.getImgPath7().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath7());
        }
        if (pi.getImgPath8() != null && !pi.getImgPath8().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath8());
        }
        if (pi.getImgPath9() != null && !pi.getImgPath9().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath9());
        }
        if (pi.getImgPath10() != null && !pi.getImgPath10().isEmpty()) {
            FileUploadUtils.deleteFile(AP_PATH + pi.getImgPath10());
        }
    }

    /**
     * 根据路径删除文件
     *
     * @param path
     */
    public static void deleteFile(String path) {
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
    }

    public static ResultMessage checkProductSkuImg(File[] imgFile, String[] imgFileFileName) {
        ResultMessage rm = new ResultMessage();
        try {
            //1.校验图片大小，不超过1M
            long size = 1 * 1024 * 1024;
            long bigSizeCount = Arrays.stream(imgFile).filter(file -> file.length() > size).count();
            if (bigSizeCount > 0) {
                rm.setMessage("请使用jpg\\jpeg\\png等格式、单张大小不超过1M的图片");
                return rm;
            }

            //2.校验图片像素都不小于800
            BufferedImage bi = null;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < imgFile.length; i++) {
                bi = ImageIO.read(imgFile[i]);
                if (bi.getWidth() < 800 && bi.getHeight() < 800) {
                    sb.append("【" + imgFileFileName[i] + "】");
                }
            }

            String msg = sb.toString();
            if (StringUtils.isNotEmpty(msg)) {
                rm.setMessage(msg + "像素不足800*800，请重新选择图片！");
                return rm;
            }

            rm.setIsSuccess(true);
        } catch (Exception e) {
            logger.error("checkImgPixelNew error ", e);
            rm.setMessage("图片加载失败，请重新选择！");
        }

        return rm;
    }

    public static void copyFile(int maxWidth, int maxHeight, File srcFile, File destFile) {
        ScaleParameter scaleParam = new ScaleParameter(maxWidth, maxHeight);
        WriteRender wr = null;
        try(FileInputStream inStream =new FileInputStream(srcFile);
            FileOutputStream outStream = new FileOutputStream(destFile)) {
            ImageRender rr = new ReadRender(inStream);
            ImageRender sr = new ScaleRender(rr, scaleParam);
            wr = new WriteRender(sr, outStream);
            wr.render();                            //触发图像处理
        } catch(Exception e) {
            logger.error("切图片失败：", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 按照像素上传
     *
     * @param imageSizeArray    像素集合
     * @param fileName          上传文件名称
     * @param fileExt           文件后缀
     * @param savePath          保存路径
     * @param relativePath      查看路径
     * @param srcFile           源文件
     * @param image             正式商品对象对象
     * @param imageDraft        草稿商品图片对象
     * @param isDraft           ture草稿商品/false正式商品
     * @return
     */
    public static String handleSkuImage(String[] imageSizeArray, String fileName, String fileExt, String savePath,
                                        String relativePath, File srcFile, ProductImage image, ProductImageDraft imageDraft,
                                        boolean isDraft) {
        String destFilePath = "";
        for (int i = 0; i < imageSizeArray.length; i++) {
            String[] imageSize = imageSizeArray[i].split("[*]");
            String finalFileName = fileName + "_" + (i + 1) + "." + fileExt;
            File destFile = new File(savePath, finalFileName);
            copyFile(Integer.parseInt(imageSize[0]), Integer.parseInt(imageSize[1]), srcFile, destFile);
            if (isDraft) {
                imageDraft.setImagePath(relativePath + finalFileName, i + 1);
            } else {
                image.setImagePath(relativePath + finalFileName, i + 1);
            }
            if (i == 4) {
                destFilePath = ConfigurationUtil.getString("picturePreviewPath") + relativePath + finalFileName;
            }
        }
        return destFilePath;
    }

}