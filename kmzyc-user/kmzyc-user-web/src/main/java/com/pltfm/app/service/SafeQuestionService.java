package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.SafeQuestion;

/**
 * 安全问题业务逻辑接口
 * 
 * @author ljh
 * @since 2013-07-12
 */

public interface SafeQuestionService {
  /**
   * 添加一条安全问题记录
   * 
   * @param safeQuestion 安全问题实体
   * @return 安全问题主键
   * @throws Exception 异常
   */

  public Integer addSafeQuestion(SafeQuestion safeQuestion) throws Exception;

  /**
   * 根据安全问题主键查询出安全问题记录
   * 
   * @param id 安全问题主键
   * @return 安全问题记录
   * @throws Exception 异常
   */

  public SafeQuestion queryOneQuestion(int id) throws Exception;

  /**
   * 更新一条安全问题记录
   * 
   * @param safeQuestion 安全问题实体
   * @return 安全问题主键
   * @throws Exception 异常
   */
  public Integer updateOneQuestion(SafeQuestion safeQuestion) throws Exception;


  /**
   * 根据安全问题名称查询安全问题集合
   * 
   * @param safeQuestion 安全问题实体
   * @param page 分页实体
   * @return 安全问题实体
   * @throws Exception 异常
   */
  public List<SafeQuestion> queryQuestionByName(SafeQuestion safeQuestion, Page page)
      throws Exception;

  /**
   * 计算总的条数
   * 
   * @param safeQuestion 安全问题实体
   * @return 总条数
   * @throws Exception 异常
   */
  public int countItem(SafeQuestion safeQuestion) throws Exception;

  /**
   * 删除所选的安全问题集合
   * 
   * @param questionId 安全问题主键集合
   * @throws Exception 异常
   */
  public Integer delBySelected(List<Integer> questionId) throws Exception;

  /**
   * 查询全部安全问题
   * 
   * @param example
   * @return
   * @throws Exception
   */
  public List selectByAll() throws Exception;
}
