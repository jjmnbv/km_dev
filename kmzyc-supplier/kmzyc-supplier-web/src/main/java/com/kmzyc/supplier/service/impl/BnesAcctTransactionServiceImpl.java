package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.km.framework.page.Pagination;
import com.kmzyc.supplier.model.RechargeDetails;
import com.kmzyc.supplier.dao.BnesAcctTransactionDAO;
import com.kmzyc.supplier.service.BnesAcctTransactionService;

@Service("bnesAcctTransactionService")
public class BnesAcctTransactionServiceImpl implements BnesAcctTransactionService {

    @Resource
    private BnesAcctTransactionDAO bnesAcctTransactionDAO;

    public Pagination findAcctBalanceByUserId(Pagination page, long loginId, RechargeDetails rechargeDetails)
            throws ServiceException {
        // sql查询条件对象
        Map<String, Object> condition = new HashMap<String, Object>();
        // 加载用户充值明细记录,此处要用account_id
        condition.put("loginId", loginId);
        if (null != rechargeDetails.getAginCreateDate()) {
            condition.put("startDate", rechargeDetails.getAginCreateDate());
        }
        if (null != rechargeDetails.getEndCreateDate()) {
            condition.put("endDate", rechargeDetails.getEndCreateDate());
        }
        // 流水号不为空
        if (StringUtils.isNotEmpty(rechargeDetails.getAccountNumber())) {
            condition.put("accountNumber", rechargeDetails.getAccountNumber().trim());
        }
        // 状态不为空
        if (null != rechargeDetails.getStatus() && rechargeDetails.getStatus() > -1) {
            condition.put("status", rechargeDetails.getStatus());
        }
        // 交易类型不为空
        if (null != rechargeDetails.getType() && rechargeDetails.getType() > -1) {
            condition.put("type", rechargeDetails.getType());
        }
        // 设置查询条件
        page.setObjCondition(condition);
        try {
            page = bnesAcctTransactionDAO.findBnesAcctTransactionListByPage(page);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return page;
    }

}