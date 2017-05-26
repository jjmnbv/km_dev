package com.pltfm.app.dao;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public interface ExpressTrackDAO {
  /**
   * 查询物流跟踪详细信息
   * 
   * @param paramMap 查询参数
   * @return
   * @throws Exception
   */
  List queryExpressTrackList(Map<String, String> paramMap) throws Exception;

  /**
   * 根据物流跟踪详细信息数量
   * 
   * @param paramMap
   * @return
   * @throws Exception
   */
  int queryExpressTrackCount(Map<String, String> paramMap) throws Exception;
}
