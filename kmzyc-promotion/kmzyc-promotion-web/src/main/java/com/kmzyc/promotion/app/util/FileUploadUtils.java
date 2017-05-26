package com.kmzyc.promotion.app.util;

import java.io.File;
import java.util.Random;

import org.apache.commons.lang.time.DateFormatUtils;

import com.kmzyc.zkconfig.ConfigurationUtil;

public class FileUploadUtils {

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
     * 创建产品图片上传路径
     * 
     * @return
     */
    public static String createProductSavePath(String productIdDictory, String skuIdDictory) {
        String uploadDir =
                "upload" + File.separatorChar + "product" + File.separatorChar + productIdDictory
                        + File.separatorChar + skuIdDictory + File.separatorChar;
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
     * 创建产品图片上传路径
     * 
     * @return
     */
    public static String createProductDraftSavePath(String productIdDictory, String skuIdDictory) {
        String uploadDir =
                "upload" + File.separatorChar + "productDraft" + File.separatorChar
                        + productIdDictory + File.separatorChar + skuIdDictory + File.separatorChar;
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

    public static String createCertificateSavePath() {
        String uploadDir =
                "upload" + File.separatorChar + "productCertificate" + File.separatorChar
                        + DateFormatUtils.format(new java.util.Date(), "yyyyMMddHHmmssSSS")
                        + new Random().nextInt(1000) + File.separatorChar;

        String ctxDir = ConfigurationUtil.getString("certificateFilePath");
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
}
