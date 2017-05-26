package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.BrowsingHisDao;
import com.kmzyc.b2b.model.BrowsingHis;
import com.km.framework.persistence.impl.DaoImpl;

@Service
public class BrowsingHisDaoImpl extends DaoImpl implements BrowsingHisDao {
  @javax.annotation.Resource(name = "sqlMapClient")
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  /**
   * 添加浏览记录
   */
  public void addBrowsingHis(BrowsingHis browsingHis) throws SQLException {
    sqlMapClient.insert("BNES_BROWSING_HIS.SQL_INSERT_MY_BROW_HISTORY", browsingHis);
  }

  /**
   * 根据登录id查询对应的浏览记录的商品code，以及记录的的浏览时间
   */
  @SuppressWarnings("unchecked")
  public List<BrowsingHis> queryBrowsingHis(Map<String, Object> map) throws SQLException {
    return (List<BrowsingHis>) sqlMapClient.queryForList(
        "BNES_BROWSING_HIS.ibatorgenerated_selectByPrimaryKey", map);

  }

  /**
   * 根据对应登录id清楚删除对应浏览记录
   */

  public void deleteBrowingHisById(int loginId) throws SQLException {
    sqlMapClient.delete("BNES_BROWSING_HIS.deleteBrowingHisById", loginId);
  }

  /**
   * 根据浏览ID删除
   * 
   * @param map
   * @throws SQLException
   */
  public void deleteBrowingHisByBrowingId(Map<String, Object> map) throws SQLException {
    sqlMapClient.delete("BNES_BROWSING_HIS.deleteBrowingHisByBrowsId", map);
  }

  /**
   * 获取是否已存在记录
   */
  public int queryByContentCode(Map<String, Object> map) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("BNES_BROWSING_HIS.queryByContentCode", map);

  }

  /**
   * 修改浏览历史记录
   */
  public void updateBrowsingHis(Map<String, Object> map) throws SQLException {
    sqlMapClient.update("BNES_BROWSING_HIS.updateBrowingHis", map);

  }

  public Long queryBrowsingHisCount(Map<String, Object> map) throws SQLException {
    return (Long) sqlMapClient.queryForObject("BNES_BROWSING_HIS.queryBROWSINGCOUNT", map);
  }
}
