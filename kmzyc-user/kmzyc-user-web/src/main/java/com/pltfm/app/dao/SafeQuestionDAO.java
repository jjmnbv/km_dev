package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.SafeQuestion;


/**
 * 安全问题数据处理接口
 * 
 * @author ljh
 * @since 2013-7-12
 */

public interface SafeQuestionDAO {
  /**
   * 添加安全问题信息
   * 
   * @param safeQuestion 安全问题信息
   * @return 返回值 安全问题主键
   * @throws SQLException 异常
   * 
   */

  public Integer insertSafeQuestion(SafeQuestion safeQuestion) throws SQLException;


  /**
   * 根据主键id 查询出一条安全问题信息
   * 
   * @param id 安全问题主键
   * @return 一条安全问题信息
   * @throw SQLException 异常
   * 
   * 
   */

  public SafeQuestion queryOneQuestion(int id) throws SQLException;

  /**
   * 根据主键id 修改相应的安全问题信息
   * 
   * @param id 安全问题主键
   * @throw SQLException 异常
   * 
   */

  public Integer updateOneQuestion(SafeQuestion safeQuestion) throws SQLException;



  /**
   * 根据安全问题的主键删除安全问题记录 根据id 删除 相应的安全问题信息
   * 
   * @param i 安全问题主键
   * @throws SQLException 异常
   */



  public Integer delSafeQuestion(int i) throws SQLException;

  /**
   * 通过安全问题名称查询出同一个名称的问题集合
   * 
   * @param safeQuestion 安全问题实体
   * @param page 分页实体
   * @return 安全问题集合
   * @throws SQLException 异常
   */

  public List<SafeQuestion> queryQuestionByName(SafeQuestion safeQuestion, Page page)
      throws SQLException;

  /**
   * 根据名称查询总的条数
   * 
   * @param safeQuestion 安全问题实体
   * @return 总条数
   * @throws SQLException 异常
   */

  public int countItem(SafeQuestion safeQuestion) throws SQLException;

  /**
   * 按条件查询安全问题
   */
  public List selectByAll() throws SQLException;



}
