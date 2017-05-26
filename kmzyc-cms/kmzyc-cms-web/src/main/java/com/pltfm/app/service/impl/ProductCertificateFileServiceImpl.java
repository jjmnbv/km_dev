/**
 * project : B2B 康美健康商城 module : km-cms-web package : com.pltfm.app.service.impl date:
 * 2016年9月14日下午5:38:24 Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.ProductCertificateFileDAO;
import com.pltfm.app.service.ProductCertificateFileService;
import com.pltfm.cms.vobject.ProductCertificateFile;

/**
 * 产品正式资质文件 Service <br/> 
 *
 * @author jerrmy
 * @date 2016年9月14日 下午5:38:24
 * @version
 * @see
 */
@Service(value = "productCertificateFileService")
public class ProductCertificateFileServiceImpl implements ProductCertificateFileService {

    @Resource
    private ProductCertificateFileDAO productCertificateFileDAO;

    @Override
    public List<ProductCertificateFile> findListByProductId(Long productId) throws ServiceException {
        try {
            return productCertificateFileDAO.findListByProductId(productId);
        } catch (SQLException e) {
            throw new ServiceException(e.getErrorCode(), e.getMessage(), e);
        }
    }
}
