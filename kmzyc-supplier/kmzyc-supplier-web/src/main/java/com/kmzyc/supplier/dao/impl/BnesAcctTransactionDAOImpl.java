package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.page.Pagination;
import com.kmzyc.supplier.dao.BnesAcctTransactionDAO;

@Repository("bnesAcctTransactionDAO")
public class BnesAcctTransactionDAOImpl extends BaseDAO<Object> implements BnesAcctTransactionDAO {

    @Resource
    private SqlMapClient sqlMapClient;

    public Pagination findBnesAcctTransactionListByPage(Pagination page) throws SQLException {
        return findPaginationByPage(sqlMapClient, "BnesAcctTransaction.searchBnesAcctTransPageByUserId",
                "BnesAcctTransaction.searchCountBnesAcctTransPageByUserId", page);
    }
}
