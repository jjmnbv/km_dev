package com.pltfm.app.action;

import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.Action;
import com.pltfm.app.service.ProductImageService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.service.ViewProductSkuService;
import com.pltfm.app.service.ViewSkuAttrService;
import com.pltfm.app.util.FileUploadUtils;
import com.pltfm.app.util.ProductListType;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ViewProductSku;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

@Controller("productImageAction")
@Scope(value = "prototype")
public class ProductImageAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private ProductImageService productImageService;

	@Resource
	private ViewProductSkuService viewProductSkuService;

	@Resource
	private ViewSkuAttrService viewSkuAttrService;

	@Resource
	private ProductSkuService productSkuService;

	private Page page = new Page();

	private ViewProductSku viewProductSku = new ViewProductSku();

	private ViewProductSku viewProductSkuCondition = new ViewProductSku();
	
	private File file;

	private Long productSkuId;

	private String fileFileName;

	private String fileContentType;

	private ProductImage image = new ProductImage();

	private Long picId;

	private String productNo;

	private Long productId;

	private String uploadpreview; // 用于指定在哪个Div层预览

	private String type;

	private List<Long> imageId;
	
	private ProductSku productSku;

	// 图片预览时的绝对路径
	private String imagePath = ConfigurationUtil.getString("picturePreviewPath");

	// 图片的像素大小，长#高，从大到小
	private final String[] imageSizeArray = ConfigurationUtil.getString("picResolution").split(",");

    private void initPage() {
        if (page == null) {
            page = new Page();
        }
        if (viewProductSkuCondition == null) {
            viewProductSkuCondition = new ViewProductSku();
        }
    }

    /**
     * sku列表
     * @return
     */
	public String findAllSkuProduct() {
		try {
            initPage();
            viewProductSkuService.searchPageByUserId(page, viewProductSkuCondition, "ALL", getLoginUserId());
            setProductStatusMap();
		} catch (Exception e) {
            logger.error("sku列表失败，", e);
			return ERROR;
		}

		if (ProductListType.STOCK.equals(type)) {
			return ProductListType.STOCK;
		}

		return Action.SUCCESS;
	}

	/**
	 * 单独的产品图片上传
	 * 
	 * @return
	 */
	public String toUploadImage() {
		try {
			viewProductSku = viewProductSkuService.findByProductSkuId(productSkuId);
			getRequest().setAttribute("attrList", viewSkuAttrService.findBySkuId(productSkuId));
		} catch (Exception e) {
			e.printStackTrace();
            return ERROR;
		}
		return Action.SUCCESS;
	}

	public String showPicture() {
		super.showPicture(imagePath);
		return null;
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
			if (productImageService.isLimitImage(productSkuId)) {
				errorCode = 999;
				throw new Exception();
			}

			// 图片上传时的绝对路径
			String savePath = FileUploadUtils.createProductSavePath(productId.toString(),productSkuId.toString());
			// 图片预览时的相对路径
			String relativePath = savePath.substring(ConfigurationUtil.getString("pictureUploadPath").length());
			// 获取文件后缀名
			String fileExt = fileFileName.substring(fileFileName.lastIndexOf(".") + 1).toLowerCase();
			// 文件名
			String fileName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS")
					+ new Random().nextInt(1000) + "_" + productSkuId.toString() ;
			File deskFile = new File(savePath, fileName + "." + fileExt);
			// 上传文件
			FileUtils.copyFile(file, deskFile);
			image.setImgPath(relativePath + fileName + "." + fileExt);
			Short maxSortNo = (short) productImageService.findMaxSortNoBySkuId(productSkuId);
			image.setProductNo(productNo);
			image.setSkuId(productSkuId);
			image.setProductId(productId);
			image.setIsDefault("1");
			image.setSortno(++maxSortNo);

			//按照像素上传
			String destFilePath = FileUploadUtils.handleProductSkuImage(imageSizeArray, fileName, fileExt, savePath,
					relativePath, file, image, null, Boolean.FALSE);
			Long imageId = productImageService.saveImage(image);
			strWriteJson(imageId + "," + productSkuId + "," + destFilePath + "," + uploadpreview);
		} catch (FileNotFoundException e) {
			getResponse().setStatus(404);
			logger.error("图片上传失败：", e);
		} catch (Exception e) {
			getResponse().setStatus(errorCode);
			logger.error("图片上传失败：", e);
		}
		return null;
	}

	/**
	 * 删除图片
	 * 
	 * @return
	 */
	public String deleteImage() {
		try {
            productImageService.deleteImageById(picId);
            strWriteJson("success");
		} catch (Exception e) {
            logger.error("删除图片失败，", e);
            strWriteJson("fail");
		}
		return null;
	}

	/**
	 * 修改默认的图片
	 * 
	 * @return
	 */
	public String modifyImageDefault() {
		try {
			productImageService.modifyImageDefault(productSkuId, picId);
            strWriteJson("success");
		} catch (Exception e) {
            logger.error("修改默认的图片失败，", e);
		}
		return null;
	}

	/**
	 * 更新前的操作
	 * 
	 * @return
	 */
	public String toUpdateImage() {

		try {
			viewProductSku = viewProductSkuService.findByProductSkuId(productSkuId);
			getRequest().setAttribute("attrList", viewSkuAttrService.findBySkuId(productSkuId));
			List<ProductImage> list = productImageService.findAllBySkuId(productSkuId);
			getRequest().setAttribute("imageList", list);
		} catch (Exception e) {
            logger.error("更新前的操作失败，", e);
            return Action.ERROR;
		}
		return Action.SUCCESS;
	}

	/**
	 * 根据SkuId查找图片
	 * 
	 * @return
	 */
	public String findSomeImageBySkuId() {
		try {
			List<ProductImage> imageList = productImageService.findAllBySkuId(productSkuId);
            getRequest().setAttribute("imageList", imageList);
		} catch (Exception e) {
			logger.error("根据SkuId查找图片失败，", e);
		}

		return Action.SUCCESS;
	}
	
	/**
	 * 根据SkuId查找商品介绍
	 * 
	 * @return
	 */
	public String findIntroduceBySkuId() {
		
		try {
			productSku = productSkuService.findProductSkuBySkuId(productSkuId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Action.SUCCESS;
	}

	/**
	 * 修改图片顺序
	 * 
	 * @return
	 */
	public String updateImageSortNo() {
        if(CollectionUtils.isEmpty(imageId)) {
            strWriteJson("<script type='text/javascript' charset='utf-8' >alert('没有找到图片信息！');</script>");
            return null;
        }

        List<ProductImage> list = new ArrayList<ProductImage>();
        ProductImage pi = null;
        for (int i = 1; i <= imageId.size(); i++) {
            pi = new ProductImage();
            if (i == 1) {
                pi.setIsDefault("0");
            } else {
                pi.setIsDefault("1");
            }
            pi.setImageId(imageId.get(i - 1));
            pi.setSortno((short) i);
            list.add(pi);
        }
		String script = "";
		if (productImageService.updateProductImageBatch(list)) {
			script = "<script type='text/javascript' charset='utf-8' >alert('操作成功！');</script>";
		} else {
			script = "<script type='text/javascript' charset='utf-8' >alert('操作失败！');</script>";
		}
		strWriteJson(script);
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
	
	private boolean checkImgPixel(){
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (Exception e) {
			logger.error("加载图片失败，", e);
            return false;
		}
        return bi.getWidth() >= 800 && bi.getHeight() >= 800;
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

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public ViewProductSku getViewProductSku() {
		return viewProductSku;
	}

	public void setViewProductSku(ViewProductSku viewProductSku) {
		this.viewProductSku = viewProductSku;
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

	public ViewProductSku getViewProductSkuCondition() {
		return viewProductSkuCondition;
	}

	public void setViewProductSkuCondition(ViewProductSku viewProductSkuCondition) {
		this.viewProductSkuCondition = viewProductSkuCondition;
	}

	public ProductSku getProductSku() {
		return productSku;
	}

	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}

}
