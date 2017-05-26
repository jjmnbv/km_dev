package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.kmzyc.supplier.dao.SuppliersAvailableCategorysDAO;
import com.kmzyc.supplier.service.SupplierCategorysService;


@Service("supplierCategorysService")
public class SupplierCategorysServiceImpl implements SupplierCategorysService {

    private Logger logger = LoggerFactory.getLogger(SupplierCategorysServiceImpl.class);

    @Resource
    private SuppliersAvailableCategorysDAO suppliersAvailableCategorysDAO;

    public List<SuppliersAvailableCategorys> findSupplierCategoriesBySupplierId(long supplierId) throws ServiceException {
        try {
            return suppliersAvailableCategorysDAO.findSuppliersAvailableCategorysBySUpplierId(supplierId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public List<String> findSupplierCategory(Long supplierId) throws ServiceException {
        try {
            return suppliersAvailableCategorysDAO.findSupplierCategory(supplierId);
        } catch (SQLException e) {
            logger.error("获取当前供应商{}，的一级类目失败，错误信息：{}", new Object[]{supplierId, e});
            throw new ServiceException(e);
        }
    }
}
