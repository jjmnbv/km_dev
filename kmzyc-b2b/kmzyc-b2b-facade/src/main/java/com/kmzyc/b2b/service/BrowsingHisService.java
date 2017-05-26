package com.kmzyc.b2b.service;

import java.util.List;
import java.util.Map;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.BrowsingHis;

public interface BrowsingHisService {
  /**
   * 添加浏览记录
   * 
   * @param browsingHis
   * @throws Exception
   */
  public void addBrowsingHis(BrowsingHis browsingHis) throws Exception;

  /**
   * 根据用户id查询浏览商品code和记录时间
   * 
   * @param loginId
   * @return
   * @throws Exception
   */
  public Pagination queryBrowsingHis(Pagination page) throws Exception;

  public List<BrowsingHis> queryBrowsingHis(Map<String, Object> map) throws Exception;

  /**
   * 根据登录用户id清空删除对应浏览记录
   * 
   * @param loginId
   * @throws Exception
   */
  public void deleteBrowingHisById(int loginId) throws Exception;

  /**
   * 获取是否已存在记录
   * 
   * @param map
   * @return
   * @throws Exception
   */
  public int queryBycontentCode(Map<String, Object> map) throws Exception;

  /**
   * 修改浏览历史记录
   * 
   * @param map
   * @throws Exception
   */
  public void updateBrowsingHis(Map<String, Object> map) throws Exception;

  /**
   * 根据登录id清空对应的浏览记录ID
   */

  public void deleteBrowingHisByBrowingId(Map<String, Object> map) throws Exception;

  /**
   * 根据id查询浏览记录的总个数
   */
  public Long queryBrowsingHisCount(Map<String, Object> map) throws Exception;
}
