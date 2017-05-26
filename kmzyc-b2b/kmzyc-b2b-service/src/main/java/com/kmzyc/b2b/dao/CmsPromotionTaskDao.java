package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.CmsPromotionTask;

public interface CmsPromotionTaskDao extends Dao {

  CmsPromotionTask findCmsPromotionTaskByID(Integer id) throws SQLException;

  Map<Long, CmsPromotionTask> queryCmsPromotionTaskByID(List<Long> codeList) throws SQLException;

  /**
   * 过期活动
   * 
   * @return
   * @throws SQLException
   */
  public List queryExpirePromotion() throws SQLException;
}
