package com.kmzyc.supplier.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.model.AccountInfo;
import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.SupplierAccountInfo;
import com.kmzyc.supplier.dao.AccountDao;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;

import javax.annotation.Resource;

@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {

    @Resource
	private SqlMapClient sqlMapClient;
	
	public AccountInfo findAccountInfoByUserId(long userId) throws SQLException{
        return (AccountInfo) sqlMapClient.queryForObject("AccountInfo.findByUserId", userId);
	}
	
	public SupplierAccountInfo findSupplierAccountInfoByUserId(long userId) throws SQLException{
        return (SupplierAccountInfo) sqlMapClient.queryForObject("AccountInfo.findSupplierInfoByUserId", userId);
	}

	public MerchantInfo findSupplierBaseInfo(long userId) throws SQLException{
        return (MerchantInfo) sqlMapClient.queryForObject("AccountInfo.findSupplierBaseInfoByUserId", userId);
	}
	
}