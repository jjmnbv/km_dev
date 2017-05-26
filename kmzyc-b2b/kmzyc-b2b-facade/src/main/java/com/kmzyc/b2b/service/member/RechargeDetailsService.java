package com.kmzyc.b2b.service.member;

import java.sql.SQLException;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.RechargeDetails;

public interface RechargeDetailsService {

  /**
   * 根据登录id查询账号余额，状态，以及充值记录
   * 
   * @param RechargeDetails
   * @return
   * @throws SQLException
   */
  public RechargeDetails queryRechargeDetailsById(Pagination page) throws SQLException;

}
