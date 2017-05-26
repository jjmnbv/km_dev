package com.kmzyc.supplier.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.dao.BnesAcctEnchashmentDao;
import com.kmzyc.supplier.service.SupplierEnchashmentService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.km.framework.page.Pagination;
import com.kmzyc.supplier.model.BnesAcctEnchashmentCriteria;
import com.kmzyc.supplier.model.BnesAcctEnchashment;

@Service("bnesAcctDrawingService")
public class SupplierEnchashmentServiceImpl implements SupplierEnchashmentService {

    @Resource
    private BnesAcctEnchashmentDao bnesAcctEnchashmentDao;

    @Override
    public Pagination searchEnchashmentPage(BnesAcctEnchashmentCriteria bnesAcctEnchashmentCriteria, Pagination page)
            throws ServiceException {
        Map<String, Object> condition = new HashMap<String, Object>();
        // 加载用户充值明细记录,此处要用account_id
        condition.put("nLoginId", bnesAcctEnchashmentCriteria.getnLoginId());
        if (StringUtils.isNotBlank(bnesAcctEnchashmentCriteria.getLoginAccount())) {
            condition.put("loginAccount", bnesAcctEnchashmentCriteria.getLoginAccount().trim());
        }
        if (StringUtils.isNotBlank(bnesAcctEnchashmentCriteria.getEnchashmentDepict())) {
            condition.put("enchashmentDepict", bnesAcctEnchashmentCriteria.getEnchashmentDepict().trim());
        }
        if (null != bnesAcctEnchashmentCriteria.getAccountTransactionId()) {
            condition.put("accountTransactionId", bnesAcctEnchashmentCriteria.getAccountTransactionId());
        }
        if (null != bnesAcctEnchashmentCriteria.getStartDate()) {
            condition.put("startDate", bnesAcctEnchashmentCriteria.getStartDate());
        }
        if (null != bnesAcctEnchashmentCriteria.getEndDate()) {
            condition.put("endDate", bnesAcctEnchashmentCriteria.getEndDate());
        }
        condition.put("status", bnesAcctEnchashmentCriteria.getEnchashmentStatus());
        page.setObjCondition(condition);
        try {
            page = bnesAcctEnchashmentDao.findBnesAcctEnchashmentListByPage(page);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return page;
    }

    @Override
    public BnesAcctEnchashment searchBnesAcctEnchashmentById(BigDecimal enchashmentId) throws ServiceException {
        try {
            return bnesAcctEnchashmentDao.findBnesAcctEnchashmentById(enchashmentId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public BnesAcctEnchashment searchPendingAuditByUserId(Long userId) throws ServiceException {
        try {
            List list = bnesAcctEnchashmentDao.findPendingAuditByUseId(userId);
            BnesAcctEnchashment bnesAcctEnchashment = null;
            if (list.size() > 0) {
                bnesAcctEnchashment = (BnesAcctEnchashment) list.get(0);
            }
            return bnesAcctEnchashment;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}