package com.pltfm.app.dao;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public interface ExpressLogDAO {
  /**
   * 查询日志
   * 
   * @param paramMap 查询参数
   * @return
   * @throws Exception
   */
  List queryExpressLogList(Map<String, String> paramMap) throws Exception;

  /**
   * 根据查询参数查询日志数量
   * 
   * @param paramMap
   * @return
   * @throws Exception
   */
  int queryExpressLogCount(Map<String, String> paramMap) throws Exception;

}
