package com.pltfm.crowdsourcing.dao;


import com.km.crowdsourcing.model.WithdrawalsInfo;


public interface WithdrawalsDao {

  int insertWithdrawalsInfo(WithdrawalsInfo info) throws Exception;

  WithdrawalsInfo selectByPrimaryKey(String institutionCode) throws Exception;



}
