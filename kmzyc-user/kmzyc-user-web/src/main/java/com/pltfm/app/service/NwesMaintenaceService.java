package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.NwesMaintenace;

/***
 * 
 * 维护记录Service接口
 */
public interface NwesMaintenaceService {
  /***
   * 
   * 删除维护记录
   */
  int delete(List<Integer> maintenaceIds) throws SQLException;


  /***
   * 
   * 添加维护记录
   */
  Integer insert(NwesMaintenace nwesMaintenace) throws SQLException;

  /***
   * 
   * 跟据id查询维护记录
   */
  NwesMaintenace selectByPrimaryKey(Integer maintenaceId) throws SQLException;

  /***
   * 
   * 修改维护记录
   */
  Integer update(NwesMaintenace nwesMaintenace) throws SQLException;

  /**
   * 分页查询维护记录
   * 
   * @param 维护记录实体
   * @return
   * @throws Exception 异常
   */
  public Page searchPageByVo(Page pageParam, NwesMaintenace vo) throws Exception;
}
