package com.kmzyc.supplier.service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageDraft;

import java.util.List;


/**
 * 产品图片业务接口
 */
public interface ProductImageService {

    /**
     * 批量更新
     *
     * @param list
     * @throws Exception
     */
    boolean updateProductImageBatch(List<ProductImageDraft> list) throws ServiceException;

    short findMaxSortNoBySkuId(Long productSkuId) throws ServiceException;

    Long saveImage(ProductImage image) throws ServiceException;

    void modifyImageDefault(Long productSkuId, Long imageId) throws ServiceException;

    void deleteImageWithFileByImageIds(List<Long> imageIds) throws ServiceException;

    void updateImageBatch(List<ProductImage> list) throws ServiceException;

}