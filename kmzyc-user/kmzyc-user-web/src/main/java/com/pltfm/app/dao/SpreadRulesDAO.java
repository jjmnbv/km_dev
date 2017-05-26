package com.pltfm.app.dao;

import com.pltfm.app.vobject.SpreadRules;

import java.sql.SQLException;

/**
 * 
 * @author lijianjun
 * @since 15/06/09
 */
public interface SpreadRulesDAO {
  /**
   * 查询微商全局设置
   * 
   * @param srid
   * @return
   * @throws SQLException
   */
  SpreadRules selectByPrimaryKey(SpreadRules key) throws SQLException;
}
