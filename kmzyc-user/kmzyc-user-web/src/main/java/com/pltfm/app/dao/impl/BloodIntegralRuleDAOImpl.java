package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BloodIntegralRuleDAO;
import com.pltfm.app.vobject.BloodIntegralRule;
import com.pltfm.sys.model.SysModelUtil;

/***
 * 
 * 经验规则DAOIMLP
 */
@Component(value = "bloodIntegralRuleDAO")
public class BloodIntegralRuleDAOImpl implements BloodIntegralRuleDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /***
   * 
   * 删除经验规则
   */
  public int delete(Integer integralRuleId) throws SQLException {
    return sqlMapClient.delete("BLOOD_INTEGRAL_RULE.ibatorgenerated_deleteByPrimaryKey",
        integralRuleId);
  }

  /***
   * 
   * 添加经验规则
   */
  public int insert(BloodIntegralRule bloodIntegralRule) throws SQLException {
    Object keyObject =
        sqlMapClient.insert("BLOOD_INTEGRAL_RULE.ibatorgenerated_insert", bloodIntegralRule);
    return (Integer) keyObject;
  }

  /***
   * 
   * 跟据id查询经验规则
   */
  public BloodIntegralRule selectByPrimaryKey(Integer integralRuleId) throws SQLException {
    return (BloodIntegralRule) sqlMapClient
        .queryForObject("BLOOD_INTEGRAL_RULE.ibatorgenerated_selectByPrimaryKey", integralRuleId);
  }

  /**
   * 查询最新记录ID
   * 
   * @param vo
   * @return 返回值
   */
  public BloodIntegralRule maxIntegralRuleId() throws SQLException {
    return (BloodIntegralRule) sqlMapClient
        .queryForObject("BLOOD_INTEGRAL_RULE.ibatorgenerated_maxIntegralRuleId");
  }

  /**
   * 多条件查询经验规则信息
   */
  public List selectByExample(BloodIntegralRule bloodIntegralRule) throws Exception {
    List list =
        sqlMapClient.queryForList("BLOOD_INTEGRAL_RULE.selectIntegralRule", bloodIntegralRule);
    return list;
  }

  /***
   * 
   * 修改经验规则
   */
  public int update(BloodIntegralRule bloodIntegralRule) throws SQLException {
    Object keyObject =
        sqlMapClient.update("BLOOD_INTEGRAL_RULE.ibatorgenerated_update", bloodIntegralRule);
    return (Integer) keyObject;
  }

  /**
   * 按条件查询经验规则总数量
   * 
   * @param vo
   * @return 返回值
   */
  public Integer selectCountByVo(BloodIntegralRule vo) throws SQLException {
    List list = sqlMapClient.queryForList("BLOOD_INTEGRAL_RULE.selectCountByVo", vo);

    SysModelUtil countResult = (SysModelUtil) list.get(0);
    // 总条数
    int recs = countResult.getTheCount().intValue();
    return recs;
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List selectPageByVo(BloodIntegralRule vo) throws SQLException {
    List pageList = sqlMapClient.queryForList("BLOOD_INTEGRAL_RULE.searchPageByVo", vo);
    return pageList;
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }
}
