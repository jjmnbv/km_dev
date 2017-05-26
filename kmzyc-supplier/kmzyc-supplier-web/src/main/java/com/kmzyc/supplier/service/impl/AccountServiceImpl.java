package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.dao.AccountDao;
import com.kmzyc.supplier.service.AccountService;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.supplier.model.AccountInfo;
import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.SupplierAccountInfo;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao accountDao;

    public AccountInfo findAccountByUserId(long userId) throws ServiceException {
        try {
            return accountDao.findAccountInfoByUserId(userId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public SupplierAccountInfo findSupplierAccountInfoByUserId(long userId) throws ServiceException {
        try {
            return accountDao.findSupplierAccountInfoByUserId(userId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public MerchantInfo findSupplierBaseInfoByUserId(long userId) throws ServiceException {
        try {
            return accountDao.findSupplierBaseInfo(userId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}