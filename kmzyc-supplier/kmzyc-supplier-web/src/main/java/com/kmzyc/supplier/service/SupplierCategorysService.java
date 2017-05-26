package com.kmzyc.supplier.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.SuppliersAvailableCategorys;

public interface SupplierCategorysService {
	/**
	 * 查找供应商的供应范围
	 * @param supplierId
	 * @return
	 * @throws ServiceException
	 */
	List<SuppliersAvailableCategorys> findSupplierCategoriesBySupplierId(long supplierId) throws ServiceException;

    List<String> findSupplierCategory(Long supplierId) throws ServiceException;
}