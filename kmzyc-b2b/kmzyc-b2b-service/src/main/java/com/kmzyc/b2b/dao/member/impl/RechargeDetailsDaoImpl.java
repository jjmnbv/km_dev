package com.kmzyc.b2b.dao.member.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.member.RechargeDetailsDao;
import com.kmzyc.b2b.model.RechargeDetails;
import com.km.framework.page.Pagination;
import com.km.framework.persistence.impl.DaoImpl;

@Component
public class RechargeDetailsDaoImpl extends DaoImpl implements RechargeDetailsDao {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 根据登录id查询账号余额，状态，以及充值记录
   */

  public RechargeDetails queryRechargeDetailsByLoginId(Pagination page) throws SQLException {
    return (RechargeDetails) sqlMapClient.queryForObject("RechargeDetails.queryRechargeDetails",
        page);

  }

}
