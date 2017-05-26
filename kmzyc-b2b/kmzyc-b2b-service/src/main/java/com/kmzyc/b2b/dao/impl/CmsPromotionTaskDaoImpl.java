package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.CmsPromotionTaskDao;
import com.kmzyc.b2b.model.CmsPromotionTask;
import com.km.framework.persistence.impl.DaoImpl;

@Component("cmsPromotionTaskDao")
public class CmsPromotionTaskDaoImpl extends DaoImpl implements CmsPromotionTaskDao {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Override
  public CmsPromotionTask findCmsPromotionTaskByID(Integer id) throws SQLException {
    CmsPromotionTask cmsPromotionTask =
        (CmsPromotionTask) this.sqlMapClient.queryForObject("CmsPromotionTask.findById", id);
    return cmsPromotionTask;
  }

  @Override
  public Map<Long, CmsPromotionTask> queryCmsPromotionTaskByID(List<Long> codeList)
      throws SQLException {
    Map rs =
        this.sqlMapClient.queryForMap("CmsPromotionTask.queryCmsPromotionTaskByID", codeList, "id");
    return rs;
  }

  /**
   * 过期活动
   * 
   * */
  public List queryExpirePromotion() throws SQLException {
    return sqlMapClient.queryForList("CmsPromotionTask.queryExpirePromotion");
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

}
