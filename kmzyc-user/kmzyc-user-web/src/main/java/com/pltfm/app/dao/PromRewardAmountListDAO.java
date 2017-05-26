package com.pltfm.app.dao;

import com.pltfm.app.vobject.PromRewardAmountList;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface PromRewardAmountListDAO {
  /**
   * 删除
   * 
   * @param rewardAmountListId
   * @return
   * @throws SQLException
   */
  int deleteByPrimaryKey(BigDecimal rewardAmountListId) throws SQLException;

  /**
   * 添加
   * 
   * @param record
   * @throws SQLException
   */
  void insert(PromRewardAmountList record) throws SQLException;

  /**
   * 查询
   * 
   * @param rewardAmountListId
   * @return
   * @throws SQLException
   */
  PromRewardAmountList selectByPrimaryKey(PromRewardAmountList record) throws SQLException;

  /**
   * 修改
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  int updateByPrimaryKeySelective(PromRewardAmountList record) throws SQLException;
}
