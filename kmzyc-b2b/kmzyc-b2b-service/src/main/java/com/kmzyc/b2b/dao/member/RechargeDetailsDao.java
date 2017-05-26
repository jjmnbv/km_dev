package com.kmzyc.b2b.dao.member;

import java.sql.SQLException;

import com.km.framework.page.Pagination;
import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.RechargeDetails;

public interface RechargeDetailsDao extends Dao {
  /**
   * 根据登录id查询账号余额，状态，以及充值记录
   * 
   * @param RechargeDetails
   * @return
   * @throws SQLException
   */
  public RechargeDetails queryRechargeDetailsByLoginId(Pagination page) throws SQLException;
}
