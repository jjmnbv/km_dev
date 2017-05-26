package com.pltfm.app.dao;

import com.pltfm.app.vobject.SpreaderApplyRecord;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface SpreaderApplyRecordDAO {
  /**
   * 删除微商推广着记录
   * 
   * @param aid
   * @return
   * @throws SQLException
   */
  int deleteByPrimaryKey(BigDecimal aid) throws SQLException;

  /**
   * 添加微商推广着记录
   * 
   * @param record
   * @throws SQLException
   */
  BigDecimal insert(SpreaderApplyRecord record) throws SQLException;

  /**
   * 查询微商推广着记录
   * 
   * @param aid
   * @return
   * @throws SQLException
   */
  SpreaderApplyRecord selectByPrimaryKey(BigDecimal aid) throws SQLException;

  /**
   * 修改微商推广着记录
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  int updateByPrimaryKeySelective(SpreaderApplyRecord record) throws SQLException;

}
