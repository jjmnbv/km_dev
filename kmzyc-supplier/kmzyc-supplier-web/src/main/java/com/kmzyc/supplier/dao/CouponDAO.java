package com.kmzyc.supplier.dao;

import java.sql.SQLException;

import com.km.framework.page.Pagination;

public interface CouponDAO {
	
	Pagination findCouponListByPage(Pagination page) throws SQLException;
}
