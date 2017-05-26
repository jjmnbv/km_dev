package com.kmzyc.supplier.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.km.framework.page.Pagination;
import com.kmzyc.supplier.model.BnesAcctEnchashment;

public interface BnesAcctEnchashmentDao {

    /**
     * 余额提现列表
     *
     * @param page
     * @return
     * @throws SQLException
     */
    Pagination findBnesAcctEnchashmentListByPage(Pagination page) throws SQLException;

    /**
     * 查看提款状态信息
     *
     * @param enchashmentId
     * @return
     * @throws SQLException
     */
    BnesAcctEnchashment findBnesAcctEnchashmentById(BigDecimal enchashmentId) throws SQLException;


    /**
     * 查询待审核的列表
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    List findPendingAuditByUseId(Long userId) throws SQLException;

}