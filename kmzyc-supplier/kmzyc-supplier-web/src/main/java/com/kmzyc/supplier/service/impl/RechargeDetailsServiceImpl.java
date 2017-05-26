package com.kmzyc.supplier.service.impl;


import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.AccountBasics;
import com.kmzyc.supplier.model.RechargeDetails;
import com.kmzyc.supplier.dao.RechargeDetailsDao;
import com.kmzyc.supplier.service.RechargeDetailsService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 供应商余额明细Service实现类
 *
 * @author luoyi
 * @crateDate 2014/01/07
 */
@Service(value = "rechargeDetailsService")
public class RechargeDetailsServiceImpl implements RechargeDetailsService {

    @Resource
    private RechargeDetailsDao rechargeDetailsDao;

    public RechargeDetails queryRechargeDetailsById(Pagination page, long userId, RechargeDetails rechargeDetails)
            throws ServiceException {
        // sql查询条件对象
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("loginId", userId);

        if (null != rechargeDetails.getAginCreateDate()) {
            condition.put("startDate", rechargeDetails.getAginCreateDate());
        }
        if (null != rechargeDetails.getEndCreateDate()) {
            condition.put("endDate", rechargeDetails.getEndCreateDate());
        }
        // 流水号不为空
        if (StringUtils.isNotEmpty(rechargeDetails.getAccountNumber())) {
            condition.put("accountNumber", rechargeDetails.getAccountNumber());
        }
        // 状态不为空
        if (null != rechargeDetails.getStatus() && rechargeDetails.getStatus() > -1) {
            condition.put("status", rechargeDetails.getStatus());
        }
        // 设置查询条件
        page.setObjCondition(condition);
        try {
            return rechargeDetailsDao.queryRechargeDetailsByLoginId(page);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public AccountBasics findAccountBasicsByUserId(Long merchantId) throws ServiceException {
        try {
            return rechargeDetailsDao.selectAccountBasicsByUserId(merchantId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}