package com.pltfm.app.dao;

import com.pltfm.app.vobject.NwesVisting;

import java.sql.SQLException;
import java.util.List;

/***
 * 拜访信息DAO接口
 */
public interface NwesVistingDAO {
  /***
   * 
   * 删除拜访记录
   */
  int delete(NwesVisting example) throws SQLException;


  /***
   * 
   * 添加拜访记录
   */
  Integer insert(NwesVisting record) throws SQLException;

  /***
   * 
   * 跟据id查询拜访记录
   */
  NwesVisting selectByPrimaryKey(Integer vistingId) throws SQLException;

  /***
   * 
   * 修改拜访记录
   */
  Integer update(NwesVisting record) throws SQLException;

  /**
   * 按条件查询拜访信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  Integer selectCountByVo(NwesVisting vo) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List selectPageByVo(NwesVisting vo) throws SQLException;
}
