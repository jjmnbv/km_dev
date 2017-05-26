package com.pltfm.app.dao;

import com.pltfm.app.vobject.BloodIntegralRule;

import java.sql.SQLException;
import java.util.List;

/***
 * 
 * 经验规则DAO接口
 */
public interface BloodIntegralRuleDAO {
  /***
   * 
   * id删除经验规则
   */
  int delete(Integer integralRuleId) throws SQLException;

  /***
   * 
   * 添加经验规则
   */
  int insert(BloodIntegralRule bloodIntegralRule) throws SQLException;

  /***
   * 
   * ID查询经验规则
   */
  BloodIntegralRule selectByPrimaryKey(Integer integralRuleId) throws SQLException;

  /**
   * 查询最新记录ID
   * 
   * @param vo
   * @return 返回值
   */
  public BloodIntegralRule maxIntegralRuleId() throws SQLException;

  /**
   * 多条件查询经验规则信息
   */
  public List selectByExample(BloodIntegralRule bloodIntegralRule) throws Exception;

  /***
   * 
   * 修改经验规则
   */
  int update(BloodIntegralRule bloodIntegralRule) throws SQLException;

  /**
   * 按条件查询调查信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  Integer selectCountByVo(BloodIntegralRule vo) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List selectPageByVo(BloodIntegralRule vo) throws SQLException;

}
