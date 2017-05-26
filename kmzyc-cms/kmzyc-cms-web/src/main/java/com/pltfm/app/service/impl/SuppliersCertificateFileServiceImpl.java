/**
 * project : B2B 康美健康商城 module : km-cms-web package : com.pltfm.app.service.impl date:
 * 2016年9月14日下午5:38:59 Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.SuppliersCertificateFileDAO;
import com.pltfm.app.service.SuppliersCertificateFileService;
import com.pltfm.cms.vobject.SuppliersCertificateFile;

/**
 * 供应商资质文件(新) Service <br/> 
 *
 * @author jerrmy
 * @date 2016年9月14日 下午5:38:59
 * @version
 * @see
 */
@Service(value = "suppliersCertificateFileService")
public class SuppliersCertificateFileServiceImpl implements SuppliersCertificateFileService {

    @Resource
    private SuppliersCertificateFileDAO suppliersCertificateFileDAO;

    @Override
    public List<SuppliersCertificateFile> findListByInfoId(Long infoId) throws ServiceException {
        try {
            return suppliersCertificateFileDAO.findListByInfoId(infoId);
        } catch (SQLException e) {
            throw new ServiceException(e.getErrorCode(), e.getMessage(), e);
        }
    }

}
