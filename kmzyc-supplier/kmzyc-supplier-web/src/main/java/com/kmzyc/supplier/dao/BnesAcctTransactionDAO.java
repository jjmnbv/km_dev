package com.kmzyc.supplier.dao;

import java.sql.SQLException;

import com.km.framework.page.Pagination;

public interface BnesAcctTransactionDAO {

    /**
     * 分页查找余额明细
     *
     * @param page
     * @return
     * @throws SQLException
     */
    Pagination findBnesAcctTransactionListByPage(Pagination page) throws SQLException;
}
