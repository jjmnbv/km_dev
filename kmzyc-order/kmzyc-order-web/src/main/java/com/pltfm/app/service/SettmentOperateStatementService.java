package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.entities.SettmentOperateStatement;
import com.pltfm.app.entities.SettmentOperateStatementExample;

@SuppressWarnings("unchecked")
public interface SettmentOperateStatementService {

  /**
   * 结算单操作流水记录数查询
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  int countByExample(SettmentOperateStatementExample example) throws SQLException;

  /**
   * 删除结算单操作流水
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  int deleteByExample(SettmentOperateStatementExample example) throws SQLException;

  /**
   * 新增结算单操作流水
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  Long insert(SettmentOperateStatement record) throws SQLException;

  /**
   * 查询结算单操作流水
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  List selectByExample(SettmentOperateStatementExample example) throws SQLException;

  /**
   * 修改结算单操作流水
   * 
   * @param record
   * @param example
   * @return
   * @throws SQLException
   */
  int updateByExample(SettmentOperateStatement record, SettmentOperateStatementExample example)
      throws SQLException;

}
