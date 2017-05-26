package com.kmzyc.b2b.service;

import java.sql.SQLException;

import com.km.framework.page.Pagination;

public interface FAQService {
  /**
   * 查询常见问题列表
   * 
   * @param page
   * @return
   * @throws SQLException
   */
  public Pagination findFAQListByPage(Pagination page) throws SQLException;

}
