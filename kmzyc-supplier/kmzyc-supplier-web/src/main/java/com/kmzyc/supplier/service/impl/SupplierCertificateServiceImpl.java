package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.supplier.model.SuppliersCertificate;
import com.kmzyc.supplier.model.example.SuppliersCertificateExample;
import com.kmzyc.supplier.model.example.SuppliersCertificateExample.Criteria;
import com.kmzyc.supplier.dao.SuppliersCertificateDAO;
import com.kmzyc.supplier.dao.impl.SuppliersCertificateDAOImpl;
import com.kmzyc.supplier.service.SupplierCertificateService;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 供应商资质文件Service
 *
 * @author luoyi
 * @crateDate 2014/01/03
 */

@Service(value = "supplierCertificateService")
public class SupplierCertificateServiceImpl implements SupplierCertificateService {

    @Resource
    private SuppliersCertificateDAO suppliersCertificateDAO;

    private Logger logger = LoggerFactory.getLogger(SuppliersCertificateDAOImpl.class);

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void saveSupplierCertificate(SuppliersCertificate suppliersCertificate) throws ServiceException {
        try {
            suppliersCertificateDAO.insertSelective(suppliersCertificate);
            logger.info("保存供应商资质文件成功!");
        } catch (SQLException e) {
            logger.error("保存供应商资质文件出错!", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<SuppliersCertificate> findCertificateListBySupplierId(Long supplierId) throws ServiceException {
        try {
            SuppliersCertificateExample example = new SuppliersCertificateExample();
            Criteria criteria = example.createCriteria();
            criteria.andSupplierIdEqualTo(supplierId);
            example.setOrderByClause(" sc_Id ASC");
            return suppliersCertificateDAO.selectByExample(example);
        } catch (SQLException e) {
            logger.error("根据供应商ID查询已上传的资质文件集合出错!", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public SuppliersCertificate findCertificateByScId(Long scId) throws ServiceException {
        try {
            return suppliersCertificateDAO.selectByPrimaryKey(scId);
        } catch (SQLException e) {
            logger.error("根据资质文件ID查询已上传的资质文件出错!", e);
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteSupplierCertificate(Long scId) throws ServiceException {
        try {
            suppliersCertificateDAO.deleteByPrimaryKey(scId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}
