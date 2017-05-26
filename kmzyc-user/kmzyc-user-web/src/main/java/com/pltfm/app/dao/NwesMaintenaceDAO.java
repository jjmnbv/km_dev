package com.pltfm.app.dao;

import com.pltfm.app.vobject.NwesMaintenace;

import java.sql.SQLException;
import java.util.List;

/***
 * 
 * 维护记录DAO接口
 */
public interface NwesMaintenaceDAO {
  /***
   * 
   * 删除维护记录
   */
  int delete(NwesMaintenace nwesMaintenace) throws SQLException;


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
   * 按条件查询维护信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  Integer selectCountByVo(NwesMaintenace vo) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List selectPageByVo(NwesMaintenace vo) throws SQLException;
}
