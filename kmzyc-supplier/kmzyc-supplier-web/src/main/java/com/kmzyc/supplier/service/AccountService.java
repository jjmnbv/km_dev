package com.kmzyc.supplier.service;

import java.sql.SQLException;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.AccountInfo;
import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.SupplierAccountInfo;

public interface AccountService {

	/**
	 * 根据用户id查询对应的账户信息
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	AccountInfo findAccountByUserId(long userId) throws ServiceException;
	
	/**
	 * 根据用户id查询对应的账户余额级别信息
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	SupplierAccountInfo findSupplierAccountInfoByUserId(long userId) throws ServiceException;

	/**
	 * 根据用户id查找供应商的基本信息
	 * @param userId
	 * @throws SQLException
	 */
	MerchantInfo findSupplierBaseInfoByUserId(long userId) throws ServiceException;
	
}
