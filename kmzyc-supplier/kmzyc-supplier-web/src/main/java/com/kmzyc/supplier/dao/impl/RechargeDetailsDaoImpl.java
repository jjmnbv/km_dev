package com.kmzyc.supplier.dao.impl;


import com.km.framework.page.Pagination;
import com.kmzyc.supplier.model.AccountBasics;
import com.kmzyc.supplier.model.RechargeDetails;
import com.kmzyc.supplier.dao.RechargeDetailsDao;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * 供应商余额明细Dao实现类
 *
 * @author luoyi
 * @crateDate 2014/01/07
 */
@Repository(value = "rechargeDetailsDao")
public class RechargeDetailsDaoImpl extends BaseDAO implements RechargeDetailsDao {

    public RechargeDetails queryRechargeDetailsByLoginId(Pagination page) throws SQLException {
        return (RechargeDetails) sqlMapClient.queryForObject("RechargeDetails.queryRechargeDetails", page);
    }

    @Override
    public AccountBasics selectAccountBasicsByUserId(Long merchantId) throws SQLException {
        return (AccountBasics) sqlMapClient.queryForObject("RechargeDetails.selectAccountBasicByUserId", merchantId);
    }

}