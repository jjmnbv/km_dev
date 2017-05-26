package com.kmzyc.supplier.service;

import java.sql.SQLException;

import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.AccountBasics;
import com.kmzyc.supplier.model.RechargeDetails;

/**
 * 供应商余额明细Service类
 *
 * @author luoyi
 * @crateDate 2014/01/07
 */
public interface RechargeDetailsService {

    /**
     * 根据登录id查询账号余额，状态，以及充值记录
     * @param page
     * @param userId
     * @param rechargeDetails
     * @return
     * @throws ServiceException
     */
    RechargeDetails queryRechargeDetailsById(Pagination page, long userId, RechargeDetails rechargeDetails)
            throws ServiceException;

    /**
     * 查询账户基本信息通过用户id
     * @param userId
     * @return
     */
    AccountBasics findAccountBasicsByUserId(Long merchantId) throws ServiceException;

}