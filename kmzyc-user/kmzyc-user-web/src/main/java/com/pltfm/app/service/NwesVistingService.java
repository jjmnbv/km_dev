package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.NwesVisting;

/***
 * 拜访信息Service接口
 */
public interface NwesVistingService {
  /***
   * 
   * 删除拜访记录
   */
  int delete(List<Integer> vistingIds) throws SQLException;

  /***
   * 
   * 添加拜访记录
   */
  Integer insert(NwesVisting nwesVisting) throws SQLException;

  /***
   * 
   * 跟据id查询拜访记录
   */
  NwesVisting selectByPrimaryKey(Integer vistingId) throws SQLException;

  /***
   * 
   * 修改拜访记录
   */
  Integer update(NwesVisting nwesVisting) throws SQLException;

  /**
   * 分页查询拜访记录
   * 
   * @param 拜访记录实体
   * @return
   * @throws Exception 异常
   */
  public Page searchPageByVo(Page pageParam, NwesVisting vo) throws Exception;
}
