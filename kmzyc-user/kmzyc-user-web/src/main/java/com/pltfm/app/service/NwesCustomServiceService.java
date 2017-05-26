package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.NwesCustomService;

/***
 * 
 * 服务信息
 */
public interface NwesCustomServiceService {
  /***
   * 
   * 删除服务信息
   */
  int delete(List<Integer> customServiceIds) throws SQLException;


  /***
   * 
   * 添加服务信息
   */
  Integer insert(NwesCustomService nwesCustomService) throws SQLException;

  /***
   * 
   * 跟据id查询服务信息
   */
  NwesCustomService selectByPrimaryKey(Integer customServiceId) throws SQLException;

  /***
   * 
   * 修改服务信息
   */
  Integer update(NwesCustomService nwesCustomService) throws SQLException;

  /**
   * 分页查询服务信息信息
   * 
   * @param 服务信息信息实体
   * @return
   * @throws Exception 异常
   */
  public Page searchPageByVo(Page pageParam, NwesCustomService vo) throws Exception;
}
