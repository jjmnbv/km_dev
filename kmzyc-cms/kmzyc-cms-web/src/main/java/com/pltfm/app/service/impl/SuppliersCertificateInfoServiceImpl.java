/**
 * project : B2B 康美健康商城 module : km-cms-web package : com.pltfm.app.service.impl date:
 * 2016年9月14日下午5:39:23 Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.SuppliersCertificateInfoDAO;
import com.pltfm.app.service.SuppliersCertificateInfoService;
import com.pltfm.cms.vobject.SuppliersCertificateInfo;

/**
 * 供应商资质信息 Service <br/> 
 *
 * @author jerrmy
 * @date 2016年9月14日 下午5:39:23
 * @version
 * @see
 */
@Service(value = "suppliersCertificateInfoService")
public class SuppliersCertificateInfoServiceImpl implements SuppliersCertificateInfoService {

    @Resource
    private SuppliersCertificateInfoDAO suppliersCertificateInfoDAO;

    @Override
    public List<SuppliersCertificateInfo> findListBySupplierId(Long supplierId) throws ServiceException {
        try {
            return suppliersCertificateInfoDAO.findListBySupplierId(supplierId);
        } catch (SQLException e) {
            throw new ServiceException(e.getErrorCode(), e.getMessage(), e);
        }
    }
}
