package com.kmzyc.supplier.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.page.Pagination;
import com.kmzyc.supplier.model.BnesAcctEnchashment;
import com.kmzyc.supplier.dao.BnesAcctEnchashmentDao;

@Repository("bnesAcctEnchashmentDao")
public class BnesAcctEnchashmentDaoImpl extends BaseDAO implements BnesAcctEnchashmentDao {

    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public Pagination findBnesAcctEnchashmentListByPage(Pagination page) throws SQLException {
        return findPaginationByPage(sqlMapClient, "SupplierEnchashment.selectBnesAcctEnchashmentList",
                "SupplierEnchashment.selectBnesAcctEnchashmentCount", page);
    }

    @Override
    public BnesAcctEnchashment findBnesAcctEnchashmentById(BigDecimal enchashmentId) throws SQLException {
        return (BnesAcctEnchashment) sqlMapClient.queryForObject("SupplierEnchashment.selectBnesAcctEnchashmentById", enchashmentId);
    }

    @Override
    public List findPendingAuditByUseId(Long userId) throws SQLException {
        return sqlMapClient.queryForList("SupplierEnchashment.selectBnesAcctEnchashmentByUserId", userId);
    }

}