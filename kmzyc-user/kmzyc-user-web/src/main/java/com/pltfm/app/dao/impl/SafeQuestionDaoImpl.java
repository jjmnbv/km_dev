package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.SafeQuestionDAO;
import com.pltfm.app.vobject.SafeQuestion;



/**
 * 安全问题处理类
 * 
 * @author ljh
 * @since 2013-7-13
 */

@Component(value = "safeQuestionDao")
public class SafeQuestionDaoImpl implements SafeQuestionDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

  /**
   * 添加安全问题信息
   * 
   * @param safeQuestion 安全问题
   * @return 返回值 主键id
   * @throws SQLException sql异常
   */
  @Override
  public Integer insertSafeQuestion(SafeQuestion safeQuestion) throws SQLException {
    Object newKey = sqlMapClient.insert("APP_SAFEQUESTION.ibatorgenerated_insert", safeQuestion);
    return (Integer) newKey;
  }

  /**
   * 根据主键id 查询出一条安全问题信息
   * 
   * @param id 安全问题主键
   * @return 一条安全问题信息
   * @throw SQLException 异常
   * 
   * 
   */

  @Override
  public SafeQuestion queryOneQuestion(int id) throws SQLException {
    return (SafeQuestion) sqlMapClient
        .queryForObject("APP_SAFEQUESTION.ibatorgenerated_getOneSafeQuestion", id);
  }


  /**
   * 根据主键id 修改相应的安全问题信息
   * 
   * @param id 安全安全主键
   * @throw SQLException 异常
   * 
   */

  @Override
  public Integer updateOneQuestion(SafeQuestion safeQuestion) throws SQLException {

    return sqlMapClient.update("APP_SAFEQUESTION.ibatorgenerated_updateOneSafeQuestion",
        safeQuestion);

  }


  /**
   * 根据安全问题的主键删除安全问题记录 根据id 删除 相应的安全问题信息
   * 
   * @param i 安全问题主键
   * @throws SQLException 异常
   */


  @Override
  public Integer delSafeQuestion(int id) throws SQLException {
    Integer rows = 0;
    rows = sqlMapClient.delete("APP_SAFEQUESTION.ibatorgenerated_delSafeQuestion", id);
    return rows;
  }

  /**
   * 通过安全问题名称查询出同一个名称的问题集合
   * 
   * @param safeQuestion 安全问题实体
   * @param page 分页实体
   * @return 安全问题的集合
   * @throws SQLException 异常
   */

  @Override
  public List<SafeQuestion> queryQuestionByName(SafeQuestion safeQuestion, Page page)
      throws SQLException {
    return sqlMapClient.queryForList("APP_SAFEQUESTION.ibatorgenerated_queryQuestionByName",
        safeQuestion, (page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
  }

  /**
   * 根据名称查询总的条数
   * 
   * @param safeQuestion 安全问题实体
   * @return 总条数
   * @throws SQLException 异常
   */

  @Override
  public int countItem(SafeQuestion safeQuestion) throws SQLException {

    return (Integer) sqlMapClient.queryForObject("APP_SAFEQUESTION.ibatorgenerated_Count",
        safeQuestion);


  }

  /**
   * 按条件查询安全问题
   */
  @Override
public List selectByAll() throws SQLException {
    List list = sqlMapClient.queryForList("APP_SAFEQUESTION.abatorgenerated_selectByExample");
    return list;
  }



}
