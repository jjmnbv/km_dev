package com.kmzyc.b2b.service;

import java.sql.SQLException;
import java.util.List;

import com.km.framework.page.Pagination;

/**
 * Description: User: lishiming Date: 13-12-11 下午5:18 Since: 1.0
 */
public interface CmsPromotionTaskService {

  public Pagination queryForPage(Pagination page) throws SQLException;

  /**
   * 过期活动
   * 
   * @return
   * @throws SQLException
   */
  public List queryExpirePromotion() throws SQLException;
}
