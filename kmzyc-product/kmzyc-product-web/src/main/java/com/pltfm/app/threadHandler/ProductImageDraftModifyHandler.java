package com.pltfm.app.threadHandler;


import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.service.ProductImageDraftService;
import com.pltfm.app.util.FileUploadUtils;
import com.pltfm.app.vobject.ProductImageDraft;
import com.pltfm.sys.util.AppApplicationContextUtil;

import org.apache.log4j.Logger;

import java.io.File;

public class ProductImageDraftModifyHandler implements Runnable {

    private Logger log = Logger.getLogger(ProductImageDraftModifyHandler.class);

    private ProductImageDraft productImageDraft;

    private String AP = ConfigurationUtil.getString("pictureUploadPath");


    public ProductImageDraftModifyHandler(ProductImageDraft productImageDraft) {
        this.productImageDraft = productImageDraft;
    }

    @Override
    public void run() {
        try {
            File file = new File(AP + productImageDraft.getImgPath());
            if (!file.exists()) {
                log.info("ISNOTEXSIT = " + productImageDraft.getImageId());
                return;
            }

            String deskFilePath = productImageDraft.getImgPath().substring(0, productImageDraft.getImgPath().lastIndexOf(".")) + "_8" + productImageDraft.getImgPath().substring(productImageDraft.getImgPath().lastIndexOf("."));
            File deskFile = new File(AP + deskFilePath);
            FileUploadUtils.copyFile(130, 130, file, deskFile);
            productImageDraft.setImgPath8(deskFilePath);
            ProductImageDraftService productImageDraftService = (ProductImageDraftService) AppApplicationContextUtil.getApplicationContext().getBean("productImageDraftService");
            productImageDraftService.updateImageById(productImageDraft);
            log.info("SUCCESS = " + productImageDraft.getImageId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
