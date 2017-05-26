package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface Live800Dao {

  public long getUserId(String userName) throws SQLException;

  /**
   * 获取用户的基本信息
   * 
   * @param userName 登录用户名
   * @return
   */
  public Map<String, Object> getCustomInfo(long userId) throws SQLException;

  /**
   * 获取用户收获地址信息
   * 
   * @param userId 用户ID
   * @return
   */
  @Deprecated
  public List<Map<String, Object>> getGoodsReceiptAddress(long userId) throws SQLException;

}
