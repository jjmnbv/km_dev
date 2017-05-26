package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.PromRewardAmountListDAO;
import com.pltfm.app.vobject.PromRewardAmountList;

@Component(value = "promRewardAmountListDAO")
public class PromRewardAmountListDAOImpl implements PromRewardAmountListDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  // 删除
  public int deleteByPrimaryKey(BigDecimal rewardAmountListId) throws SQLException {
    PromRewardAmountList key = new PromRewardAmountList();
    key.setRewardAmountListId(rewardAmountListId);
    int rows =
        sqlMapClient.delete("PROM_REWARD_AMOUNT_LIST.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  // 添加
  public void insert(PromRewardAmountList record) throws SQLException {
    sqlMapClient.insert("PROM_REWARD_AMOUNT_LIST.ibatorgenerated_insert", record);
  }

  // 查询
  public PromRewardAmountList selectByPrimaryKey(PromRewardAmountList record) throws SQLException {
    record = (PromRewardAmountList) sqlMapClient
        .queryForObject("PROM_REWARD_AMOUNT_LIST.ibatorgenerated_selectByPrimaryKey", record);
    return record;
  }

  // 修改
  public int updateByPrimaryKeySelective(PromRewardAmountList record) throws SQLException {
    int rows = sqlMapClient
        .update("PROM_REWARD_AMOUNT_LIST.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }
}
