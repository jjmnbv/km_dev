package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.page.Pagination;
import com.kmzyc.supplier.dao.CouponDAO;

@Repository("couponDAO")
public class CouponDAOImpl extends BaseDAO implements CouponDAO {

	@Override
	public Pagination findCouponListByPage(Pagination page) throws SQLException {
		return findPaginationByPage(sqlMapClient, "COUPON.selectCouponList", "COUPON.selectCouponCount", page);
	}

}