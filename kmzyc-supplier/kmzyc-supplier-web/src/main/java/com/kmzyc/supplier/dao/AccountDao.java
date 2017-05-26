package com.kmzyc.supplier.dao;

import com.kmzyc.supplier.model.AccountInfo;
import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.SupplierAccountInfo;

import java.sql.SQLException;


public interface AccountDao{
	
	/**
	 * 查找账户信息(单表查询)
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	AccountInfo findAccountInfoByUserId(long userId) throws SQLException;
	
	/**
	 * 查找账户余额和级别信息(三表查询)
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	SupplierAccountInfo findSupplierAccountInfoByUserId(long userId) throws SQLException;
	
	/**
	 * 查找账户供应商基本信息
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	MerchantInfo findSupplierBaseInfo(long userId) throws SQLException;

}