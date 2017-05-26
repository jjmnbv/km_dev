package com.kmzyc.supplier.service;

import java.math.BigDecimal;

import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.BnesAcctEnchashmentCriteria;
import com.kmzyc.supplier.model.BnesAcctEnchashment;

/**
 * 余额提现的服务类
 *
 * @author YaoChao
 */
public interface SupplierEnchashmentService {

    /**
     * 余额提现列表查询
     *
     * @param bnesAcctEnchashmentCriteria
     * @param page
     * @return
     * @throws ServiceException
     */
    Pagination searchEnchashmentPage(BnesAcctEnchashmentCriteria bnesAcctEnchashmentCriteria, Pagination page)
            throws ServiceException;

    /**
     * 根据enchashmentId查看提款状态信息
     *
     * @param enchashmentId
     * @return
     * @throws ServiceException
     */
    BnesAcctEnchashment searchBnesAcctEnchashmentById(BigDecimal enchashmentId) throws ServiceException;


    /**
     * 查询是否有待审核的
     *
     * @param userId
     * @return
     * @throws ServiceException
     */
    BnesAcctEnchashment searchPendingAuditByUserId(Long userId) throws ServiceException;
}
