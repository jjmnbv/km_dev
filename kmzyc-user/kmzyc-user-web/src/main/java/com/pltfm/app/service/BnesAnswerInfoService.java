package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.BnesAnswerInfo;
import com.pltfm.app.vobject.SafeQuestion;

/**
 * 安全问题答案信息业务逻辑接口
 * 
 * @author cjm
 * @since 2013-8-6
 */
public interface BnesAnswerInfoService {
  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 账户信息实体
   * @return 返回值
   * @throws Exception
   */
  Page searchPageByVo(Page pageParam, BnesAnswerInfo vo) throws Exception;

  /**
   * 添加安全问题答案信息
   * 
   * @param record 安全问题答案信息实体
   * @return 返回值
   * @throws Exception 异常
   */
  Integer addBnesAnswerInfo(BnesAnswerInfo bnesAnswerInfo) throws Exception;

  /**
   * 根据安全问题id账户查id询单些问题是否已存在
   * 
   * @param nCommercialTenantId 安全问题id
   * @return 返回值
   * @throws SQLException sql异常
   */
  public List<BnesAnswerInfo> selectByPrimaryKey(Integer safeQuestionId, Integer accountId)
      throws Exception;

  /**
   * 修改安全问题答案信息
   * 
   * @param record 安全问题答案信息实体
   * @return 返回值
   * @throws Exception 异常
   */
  Integer updateBnesAnswerInfo(BnesAnswerInfo bnesAnswerInfo) throws Exception;

  /**
   * 查询全部安全问题
   * 
   * @return
   * @throws Exception
   */
  List<SafeQuestion> qeuryBySafeQuestionAll() throws Exception;

  /**
   * 根据安全问题ID查询安全问题答案
   * 
   * @return
   * @throws Exception
   */
  List<BnesAnswerInfo> qeueryBySafeQuestionId(Integer safeQuestionId) throws Exception;
}
