package com.kmzyc.supplier.service;

import java.sql.SQLException;

import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.RechargeDetails;

public interface BnesAcctTransactionService {

    /**
     * 分页查询账户余额明细信息
     *
     * @param page
     * @return
     * @throws SQLException
     */
    Pagination findAcctBalanceByUserId(Pagination page, long loginId, RechargeDetails rechargeDetails)
            throws ServiceException;
}