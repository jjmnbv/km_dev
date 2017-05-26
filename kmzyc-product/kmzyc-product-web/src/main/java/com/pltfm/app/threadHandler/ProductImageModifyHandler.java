package com.pltfm.app.threadHandler;


import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.service.ProductImageService;
import com.pltfm.app.util.FileUploadUtils;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.sys.util.AppApplicationContextUtil;

import org.apache.log4j.Logger;

import java.io.File;

public class ProductImageModifyHandler implements Runnable {

    private Logger log = Logger.getLogger(ProductImageModifyHandler.class);

    private ProductImage productImage;

    private String AP = ConfigurationUtil.getString("pictureUploadPath");


    public ProductImageModifyHandler(ProductImage productImage) {
        this.productImage = productImage;
    }

    @Override
    public void run() {
        try {
            File file = new File(AP + productImage.getImgPath());
            if (!file.exists()) {
                log.info("ISNOTEXSIT = " + productImage.getImageId());
                return;
            }
            String deskFilePath = productImage.getImgPath().substring(0, productImage.getImgPath().lastIndexOf(".")) + "_8" + productImage.getImgPath().substring(productImage.getImgPath().lastIndexOf("."));
            File deskFile = new File(AP + deskFilePath);

            FileUploadUtils.copyFile(130, 130, file, deskFile);

            productImage.setImgPath8(deskFilePath);
            ProductImageService productImageService = (ProductImageService) AppApplicationContextUtil.getApplicationContext().getBean("productImageService");
            productImageService.updateImageById(productImage);

            log.info("SUCCESS = " + productImage.getImageId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}