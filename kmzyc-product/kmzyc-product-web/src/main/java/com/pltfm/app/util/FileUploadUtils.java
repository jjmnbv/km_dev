package com.pltfm.app.util;

import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleRender;
import com.alibaba.simpleimage.render.WriteRender;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageDraft;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;


public class FileUploadUtils {

	private static Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);

	/**
	 * 创建图片上传路径
	 *
	 * @return
	 */
	public static String createSavePath(String dictory) {
		String uploadDir = "upload" + File.separatorChar + dictory
				+ File.separatorChar;
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
	 * 创建供应商上传路径
	 *
	 * @return
	 */
	public static String createSupplierSavePath(String dictory) {
		String uploadDir = "upload" + File.separatorChar + dictory
				+ File.separatorChar;
		String ctxDir = ConfigurationUtil.getString("supplierCertificatePath");
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
		String uploadDir = "upload" + File.separatorChar + "productDraft"
				+ File.separatorChar + productIdDictory + File.separatorChar
				+ skuIdDictory + File.separatorChar;
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


	public static String createCertificateSavePath(){
		String uploadDir = "upload" + File.separatorChar + "productCertificate" + File.separatorChar + DateFormatUtils.format(new java.util.Date(),
		"yyyyMMddHHmmssSSS") + new Random().nextInt(1000) + File.separatorChar;

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

	public static boolean checkSkuImgPixel(File file){
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (Exception e) {
			logger.error("读取图片失败，", e);
			return false;
		}
		return bi.getWidth() >= 800 && bi.getHeight() >= 800;
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
	public static String handleProductSkuImage(String[] imageSizeArray, String fileName, String fileExt, String savePath,
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
			if (i == 7) {
				destFilePath = ConfigurationUtil.getString("picturePreviewPath") + relativePath + finalFileName;
			}
		}
		return destFilePath;
	}
}
