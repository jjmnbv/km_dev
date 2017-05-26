package com.pltfm.app.dao;

import com.pltfm.app.vobject.NwesCustomService;

import java.sql.SQLException;
import java.util.List;

/***
 * 服务信息DAO
 * 
 */
public interface NwesCustomServiceDAO {
  /***
   * 
   * 删除服务信息
   */
  int delete(NwesCustomService nwesCustomService) throws SQLException;

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
  Integer update(NwesCustomService nwesCustomServic) throws SQLException;

  /**
   * 按条件查询服务信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  Integer selectCountByVo(NwesCustomService vo) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List selectPageByVo(NwesCustomService vo) throws SQLException;
}
