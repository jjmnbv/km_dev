package com.kmzyc.product.remote.service;

import java.math.BigDecimal;
import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.SupplierFare;


/**
 * 供应商类目接口
 * @author xgh
 */
public interface SuppliersRemoteService {

	/**
	 * 根据供应商id,和类目id
	 * 
	 * @param product_sku_id
	 * @return
	 * @throws Exception
	 */
	BigDecimal getCommissionRatio(Long product_sku_id) throws ServiceException;

	/**
	 * 根据供应商id查询供应商信息
	 * 
	 * @return
	 * @throws Exception
	 */
	BigDecimal getSupplierFareInfo(Long supplierId, BigDecimal orderAmount) throws ServiceException;
	
	/**
	 * 根据商家ID查询免邮运费集合
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	List<SupplierFare> queryFreeFareList(List<Long> sid) throws ServiceException;
}