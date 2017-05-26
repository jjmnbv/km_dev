package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ProductCertificate;

public interface ProductCertificateService {

    /**
     * 根据产品Id获取资质文件
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    List<ProductCertificate> findByProductId(Long productId) throws ServiceException;

    /**
     * 删除
     *
     * @param list
     * @throws ServiceException
     */
    void deleteProductCertificate(List<ProductCertificate> list) throws ServiceException;

    /**
     * 正式删除资质文件（只删除数据库记录）
     *
     * @param productId
     * @throws ServiceException
     */
    void deleteByProductIdWithNotFile(Long productId) throws ServiceException;

}