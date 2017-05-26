package com.pltfm.app.action;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.Action;
import com.pltfm.app.enums.DraftType;
import com.pltfm.app.service.ProductImageDraftService;
import com.pltfm.app.service.ProductImageModifyService;
import com.pltfm.app.service.ProductSkuDraftService;
import com.pltfm.app.util.FileUploadUtils;
import com.pltfm.app.vobject.ProductImageDraft;
import com.pltfm.app.vobject.ProductSkuDraft;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

/**
 * 
 * @author tanyunxing
 *
 */
@Controller("productImageDraftAction")
@Scope(value="prototype")
public class ProductImageDraftAction extends BaseAction {

	private File file;
	
	private ProductImageDraft image = new ProductImageDraft();
	
	private ProductSkuDraft productSkuDraft = new ProductSkuDraft();
	
	@Resource
	private ProductImageDraftService productImageDraftService;

	@Resource
	private ProductSkuDraftService productSkuDraftService;

	@Resource
	private ProductImageModifyService productImageModifyService;
	
	private Long productSkuId;
	
	private String fileFileName;
	
	private String uploadpreview; // 用于指定在哪个Div层预览
	
	private String productNo;

	private Long productId;
	
	private Long picId;
	
	private String type;
	
	private List<Long> imageId;
	
	private String rtnMessage; // 返回的信息
	
	// 图片预览时的绝对路径
	private String imagePath = ConfigurationUtil.getString("picturePreviewPath");

	// 图片的像素大小，长#高，从大到小
	private final String[] imageSizeArray = ConfigurationUtil.getString("picResolution").split(",");

	public String toUpdateProdImageDraft(){
		try {
			productSkuDraft = productSkuDraftService.findSingleProductSku(productSkuId);
			getRequest().setAttribute("imageList", productImageDraftService.findAllBySkuId(productSkuId));
		} catch (Exception e) {
            logger.error("toUpdateProdImageDraft失败，", e);
            return ERROR;
		}
		return Action.SUCCESS;
	}

    /**
     * 添加商品介绍前操作
     *
     * @return
     */
	public String toUpdateIntroduceDraft(){
		try {
			productSkuDraft = productSkuDraftService.findSingleProductSkuForIntroduce(productSkuId);
		} catch (Exception e) {
			logger.error("添加商品介绍前操作失败，" + e.getMessage(), e);
		}
		return SUCCESS;
	}
	
	public String updateSkuIntroduce(){
		try {
			 productSkuId = productSkuDraft.getProductSkuId();
			 int count = productSkuDraftService.updateSkuIntroduce(productSkuDraft);
			 if(count>0){
				 this.setRtnMessage("SKU商品介绍保存成功！");
			 }else{
				 this.setRtnMessage("SKU商品介绍保存失败！");
			 }
			 
		} catch (Exception e) {
            logger.error("updateSkuIntroduce失败，", e);
        }
		return this.toUpdateIntroduceDraft();
	}
	
	/**
	 * 用于实现上传功能
	 */
	public String uploadFile() {
		int errorCode = 500;
		try {
			if(!FileUploadUtils.checkSkuImgPixel(file)){
				errorCode = 800;
				throw new Exception();
			}
			if (productImageDraftService.isLimitImage(productSkuId)) {
				errorCode = 999;
				throw new Exception();
			}

			// 图片上传时的绝对路径
			String savePath = FileUploadUtils.createProductSavePath(productId.toString(),productSkuId.toString());
			// 图片预览时的相对路径
			String relativePath = savePath.substring(ConfigurationUtil.getString("pictureUploadPath").length());
			// 获取文件后缀名
			String fileExt = this.getFileFileName().substring(this.getFileFileName().lastIndexOf(".") + 1).toLowerCase();
			// 文件名
			String fileName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS")
					+ new Random().nextInt(1000) + "_" + productSkuId.toString();
			File deskFile = new File(savePath, fileName + "." + fileExt);
			FileUtils.copyFile(file, deskFile);
			image.setImgPath(relativePath + fileName + "." + fileExt);
			Short maxSortNo = (short) productImageDraftService.findMaxSortNoBySkuId(productSkuId);
			image.setProductNo(productNo);
			image.setSkuId(productSkuId);
			image.setProductId(productId);
			image.setIsDefault("1");
			image.setSortno(++maxSortNo);
			image.setOpType(DraftType.ADD.getStatus());

			//按照像素上传
			String destFilePath = FileUploadUtils.handleProductSkuImage(imageSizeArray, fileName, fileExt, savePath,
					relativePath, file, null, image, Boolean.TRUE);
			Long imageId = productImageDraftService.saveImage(image);
			strWriteJson(imageId + "," + productSkuId + "," + destFilePath + "," + uploadpreview);
		} catch (FileNotFoundException e) {
			super.getResponse().setStatus(404);
			e.printStackTrace();
		} catch (Exception e) {
			super.getResponse().setStatus(errorCode);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除图片
	 * 
	 * @return
	 */
	public String deleteImage() {
		String msg = "fail";
		try {
			productImageDraftService.deleteImageById(picId);
			msg = "success";
		} catch (Exception e) {
			logger.error("删除图片失败，", e);
		}
        strWriteJson(msg);
		return null;
	}

	/**
	 * 修改默认的图片
	 * 
	 * @return
	 */
	public String modifyImageDefault() {
		try {
			productImageDraftService.modifyImageDefault(productSkuId, picId);
            strWriteJson("success");
		} catch (Exception e) {
			e.printStackTrace();
            logger.error("修改默认的图片失败，", e);
		}
		return null;
	}
	
	/**
	 * 根据SkuId查找图片
	 * 
	 * @return
	 */
	public String findImagesBySkuId() {
		try {
			List<ProductImageDraft> imageList = productImageDraftService.findAllBySkuId(productSkuId);
			getRequest().setAttribute("imageList", imageList);
		} catch (Exception e) {
			logger.error("根据SkuId查找图片出错，" + e.getMessage(), e);
            return Action.ERROR;
		}

		return Action.SUCCESS;
	}
	
	/**
	 * 根据SkuId查找商品介绍
	 * 
	 * @return
	 */
	public String findDraftIntroduceBySkuId() {
		try {
			productSkuDraft = productSkuDraftService.findSingleProductSkuForIntroduce(productSkuId);
		} catch (Exception e) {
            logger.error("根据SkuId查找商品介绍出错，" + e.getMessage(), e);
            return ERROR;
		}
		
		return Action.SUCCESS;
	}
	
	public String updateImageSortNo(){
		List<ProductImageDraft> list = null;
		if (imageId != null && imageId.size() > 0) {
			list = new ArrayList<ProductImageDraft>();
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
		String script = "";
		if (productImageDraftService.updateProductImageBatch(list)) {
			script = "<script type='text/javascript' charset='utf-8' >alert('操作成功！');</script>";
		} else {
			script = "<script type='text/javascript' charset='utf-8' >alert('操作失败！');</script>";
		}
		try {
			super.getResponse().getWriter().print(script);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String modifyProductImage(){
		try {
            productImageModifyService.modifyImage();
		} catch (Exception e) {
			logger.error("修改图片失败，", e);
		}
		
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

	public ProductImageDraft getImage() {
		return image;
	}

	public void setImage(ProductImageDraft image) {
		this.image = image;
	}

	public Long getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getUploadpreview() {
		return uploadpreview;
	}

	public void setUploadpreview(String uploadpreview) {
		this.uploadpreview = uploadpreview;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
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

	public Long getPicId() {
		return picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}

	public ProductSkuDraft getProductSkuDraft() {
		return productSkuDraft;
	}

	public void setProductSkuDraft(ProductSkuDraft productSkuDraft) {
		this.productSkuDraft = productSkuDraft;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Long> getImageId() {
		return imageId;
	}

	public void setImageId(List<Long> imageId) {
		this.imageId = imageId;
	}

	public String getRtnMessage() {
		return rtnMessage;
	}

	public void setRtnMessage(String rtnMessage) {
		this.rtnMessage = rtnMessage;
	}

}