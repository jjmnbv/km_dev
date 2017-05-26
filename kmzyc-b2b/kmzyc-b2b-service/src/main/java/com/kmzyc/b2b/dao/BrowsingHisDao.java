package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.BrowsingHis;

public interface BrowsingHisDao extends Dao {
  /**
   * 添加浏览记录
   * 
   * @param browsingHis
   * @throws SQLException
   */
  void addBrowsingHis(BrowsingHis browsingHis) throws SQLException;

  /**
   * 根据登录id查询对应的浏览记录的商品code，以及记录的的浏览时间
   * 
   * @param loginId
   * @return
   * @throws SQLException
   */
  List<BrowsingHis> queryBrowsingHis(Map<String, Object> map) throws SQLException;

  /**
   * 根据登录id清空删除对应浏览记录
   * 
   * @param loginId
   * @throws SQLException
   */
  void deleteBrowingHisById(int loginId) throws SQLException;

  /**
   * 获取是否已存在记录
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  int queryByContentCode(Map<String, Object> map) throws SQLException;

  /**
   * 修改浏览记录
   * 
   * @param map
   * @throws SQLException
   */
  void updateBrowsingHis(Map<String, Object> map) throws SQLException;

  /**
   * 根据浏览ID删除
   * 
   * @param map
   * @throws SQLException
   */
  public void deleteBrowingHisByBrowingId(Map<String, Object> map) throws SQLException;

  public Long queryBrowsingHisCount(Map<String, Object> map) throws SQLException;
}
