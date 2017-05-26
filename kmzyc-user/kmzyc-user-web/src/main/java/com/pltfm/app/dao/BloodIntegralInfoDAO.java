package com.pltfm.app.dao;

import com.pltfm.app.vobject.BloodIntegralInfo;

import java.sql.SQLException;
import java.util.List;

/****
 * 
 * 经验明细DAO接口
 **/
public interface BloodIntegralInfoDAO {
  /***
   * 删除经验明细
   **/
  int delete(BloodIntegralInfo bloodIntegralInfo) throws SQLException;

  /***
   * 添加经验明细
   **/
  int insert(BloodIntegralInfo bloodIntegralInfo) throws SQLException;

  /**
   * 按条件查询经验明细总数量
   * 
   * @param vo
   * @return 返回值
   */
  Integer selectCountByVo(BloodIntegralInfo vo) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List selectPageByVo(BloodIntegralInfo vo) throws SQLException;
}
