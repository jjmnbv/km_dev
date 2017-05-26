package com.pltfm.crowdsourcing.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.crowdsourcing.model.WithdrawalsInfo;
import com.pltfm.crowdsourcing.dao.WithdrawalsDao;

@Component(value = "withdrawalsDao")
public class WithdrawalsDaoImpl implements WithdrawalsDao {

  @Resource(name = "sqlMapClient")
  /**
   * 数据库操作对象
   */
  private SqlMapClient sqlMapClient;

  @Override
  public int insertWithdrawalsInfo(WithdrawalsInfo info) throws Exception {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public WithdrawalsInfo selectByPrimaryKey(String institutionCode) throws Exception {
    return (WithdrawalsInfo) sqlMapClient.queryForObject(
        "CrowdInstitutionWithdrawals.ibatorgenerated_selectByPrimaryKey", institutionCode);
  }
}
