package com.pltfm.app.dao;

import com.pltfm.app.vobject.SpreaderInfo;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface SpreaderInfoDAO {
  /**
   * 获取微商号
   * 
   * @return
   * @throws SQLException
   */
  BigDecimal querySeqVsnumber() throws SQLException;

  /**
   * 删除微商信息
   * 
   * @param sid
   * @return
   * @throws SQLException
   */
  int deleteByPrimaryKey(BigDecimal sid) throws SQLException;

  /**
   * 添加微商信息
   * 
   * @param record
   * @throws SQLException
   */
  BigDecimal insert(SpreaderInfo record) throws SQLException;

  /**
   * 查询微商信息
   * 
   * @param sid
   * @return
   * @throws SQLException
   */
  SpreaderInfo selectByPrimaryKey(SpreaderInfo record) throws SQLException;

  /**
   * 修改微商信息
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  int updateByPrimaryKeySelective(SpreaderInfo record) throws SQLException;
}
