package com.kmzyc.supplier.dao;
/**
 * 供应商余额明细Dao类
 * @author luoyi
 * @crateDate 2014/01/07
 */
import com.km.framework.page.Pagination;
import com.kmzyc.supplier.model.AccountBasics;
import com.kmzyc.supplier.model.RechargeDetails;

import java.sql.SQLException;

public interface RechargeDetailsDao{

	/**
	 * 根据登录id查询账号余额，状态，以及充值记录
	 * @param page
	 * @return
	 * @throws SQLException
	 */
    RechargeDetails queryRechargeDetailsByLoginId(Pagination page) throws SQLException;
    
	AccountBasics selectAccountBasicsByUserId(Long merchantId) throws SQLException ;
		
}