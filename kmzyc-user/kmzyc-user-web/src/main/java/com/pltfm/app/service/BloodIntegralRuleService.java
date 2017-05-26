package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.BloodIntegralRule;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;

/***
 * 
 * 经验规则Service接口
 */
public interface BloodIntegralRuleService {
  /***
   * 
   * id删除经验规则
   */
  int delete(List<Integer> integralRuleIds) throws SQLException;

  /**
   * 查询专家下的子级类别集合
   */
  public List<BnesCustomerTypeQuery> queryByComm();

  /***
   * 
   * 添加经验规则
   */
  int insert(BloodIntegralRule bloodIntegralRule) throws SQLException;

  /**
   * 多条件查询经验规则信息
   */
  public List queryIntegralRule(BloodIntegralRule bloodIntegralRule) throws Exception;

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

  /***
   * 
   * 修改经验规则
   */
  int update(BloodIntegralRule bloodIntegralRule) throws SQLException;

  /**
   * 分页查询经验规则
   * 
   * @param 经验规则实体
   * @return
   * @throws Exception 异常
   */
  public Page searchPageByVo(Page pageParam, BloodIntegralRule vo) throws Exception;

  /**
   * 查询经验规则总数量
   * 
   * @param vo
   * @return 返回值
   */
  public Integer selectCountByVo() throws SQLException;
}
