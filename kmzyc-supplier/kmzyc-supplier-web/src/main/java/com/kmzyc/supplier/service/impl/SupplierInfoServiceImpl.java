package com.kmzyc.supplier.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.dao.SuppliersInfoDAO;
import com.kmzyc.supplier.service.SupplierInfoService;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.sql.SQLException;


/**
 * 供应商信息Service实现类
 *
 * @author luoyi
 * @crateDate 2014/01/11
 */
@Service("supplierInfoService")
public class SupplierInfoServiceImpl implements SupplierInfoService {

    private Logger logger = LoggerFactory.getLogger(SupplierInfoServiceImpl.class);

    @Resource
    private SuppliersInfoDAO suppliersInfoDAO;

    @Override
    public SuppliersInfo findSuppliersInfo(long merchantId) throws ServiceException {
        SuppliersInfo supplier = new SuppliersInfo();
        supplier.setMerchantId(merchantId);
        try {
            return suppliersInfoDAO.selectByMerchantId(supplier);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public SuppliersInfo selectByPrimaryKey(Long supplierId) throws ServiceException {
        try {
            return suppliersInfoDAO.selectByPrimaryKey(supplierId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public boolean updateShopBrowseStatus(Long supplierId, Short status) throws ServiceException {
        SuppliersInfo condition = new SuppliersInfo();
        condition.setSupplierId(supplierId);
        condition.setShopBrowseStatus(status);
        try {
            suppliersInfoDAO.updateByPrimaryKeySelective(condition);
            return true;
        } catch (SQLException e) {
            logger.error("根据供应商id更新供应商店铺浏览量状失败{}。", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }
}
