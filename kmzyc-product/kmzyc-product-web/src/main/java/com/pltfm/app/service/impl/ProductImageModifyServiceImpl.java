package com.pltfm.app.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;

import com.pltfm.app.service.ProductImageDraftService;
import com.pltfm.app.service.ProductImageModifyService;
import com.pltfm.app.service.ProductImageService;
import com.pltfm.app.threadHandler.ProductImageDraftModifyHandler;
import com.pltfm.app.threadHandler.ProductImageModifyHandler;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageDraft;

@Service("productImageModifyService")
public class ProductImageModifyServiceImpl implements ProductImageModifyService {

    private Logger log = LoggerFactory.getLogger(ProductImageModifyServiceImpl.class);

    @Resource
    private TaskExecutor taskExecutor;

    @Resource
    private ProductImageService productImageService;

    @Resource
    private ProductImageDraftService productImageDraftService;

    @Override
    public void modifyImage() throws ServiceException {
        try {
            List<ProductImage> list = productImageService.findAllImage();
            List<ProductImageDraft> listDraft = productImageDraftService.findAllImage();

            int totalSize = list.size() + listDraft.size();
            log.info("图片修改Begin：" + new Date());
            int i = 1;
            for (ProductImage pi : list) {
                taskExecutor.execute(new ProductImageModifyHandler(pi));
                i++;
            }

            for (ProductImageDraft pid : listDraft) {
                taskExecutor.execute(new ProductImageDraftModifyHandler(pid));
                i++;
            }

            log.info("图片修改End：" + new Date() + "totalSize = " + totalSize + ",i=" + i);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

}
