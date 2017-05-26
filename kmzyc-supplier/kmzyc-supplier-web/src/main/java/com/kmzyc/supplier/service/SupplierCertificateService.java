package com.kmzyc.supplier.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.SuppliersCertificate;

/**
 * 供应商资质文件Service
 *
 * @author luoyi
 * @crateDate 2014/01/03
 */
public interface SupplierCertificateService {

    /**
     * 保存供应商资质文件
     *
     * @param suppliersCertificate
     * @throws SQLException
     */
    void saveSupplierCertificate(SuppliersCertificate suppliersCertificate) throws ServiceException;

    /**
     * 根据供应商ID查询已上传的资质文件集合
     *
     * @param supplierId 供应商ID
     * @return
     */
    List<SuppliersCertificate> findCertificateListBySupplierId(Long supplierId) throws ServiceException;

    /**
     * 根据资质文件ID查询已上传的资质文件
     *
     * @param scId 资质文件ID
     * @return
     */
    SuppliersCertificate findCertificateByScId(Long scId) throws ServiceException;

    /**
     * 根据资质文件ID删除上传但未审核的文件
     *
     * @param scId 资质文件ID
     * @throws SQLException
     */
    void deleteSupplierCertificate(Long scId) throws ServiceException;
}