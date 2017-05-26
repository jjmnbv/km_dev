package com.pltfm.app.dao;

import com.pltfm.app.vobject.BnesAnswerInfo;
import com.pltfm.app.vobject.BnesAnswerInfoExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 安全问题答案信息处理接口
 * 
 * @author cjm
 * @since 2013-8-6
 */
public interface BnesAnswerInfoDAO {
  /**
   * 添加安全问题答案信息
   * 
   * @param record 安全问题答案信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  Integer insert(BnesAnswerInfo record) throws SQLException;

  /**
   * 根据安全问题id账户查id询单些问题是否已存在
   * 
   * @param nCommercialTenantId 安全问题id
   * @return 返回值
   * @throws SQLException sql异常
   */
  public List selectByPrimaryKey(BnesAnswerInfo bnesAnswerInfo) throws Exception;

  /**
   * 修改安全问题答案信息
   * 
   * @param record 安全问题答案信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKey(BnesAnswerInfo record) throws SQLException;

  /**
   * 动态修改安全问题答案信息
   * 
   * @param record 安全问题答案信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKeySelective(BnesAnswerInfo record) throws SQLException;

  /**
   * 按安全问题答案信息条件查询
   * 
   * @param example 安全问题答案信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  List selectByExample(BnesAnswerInfoExample example) throws SQLException;

  /**
   * 根据安全问题答案主键查询单条安全问题答案基本信息
   * 
   * @param nCommercialTenantId 安全问题答案主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  BnesAnswerInfo selectByPrimaryKey(Integer answerInfoId) throws SQLException;

  /**
   * 根据安全问题答案主键查询单条安全问题答案基本信息
   * 
   * @param nCommercialTenantId 安全问题答案主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByExample(BnesAnswerInfoExample example) throws SQLException;

  /**
   * 按安全问题答案信息条件进行删除
   * 
   * @param example 安全问题答案信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByPrimaryKey(Integer answerInfoId) throws SQLException;

  /**
   * 根据安全问题答案主键删除商户基本信息
   * 
   * @param nCommercialTenantId 安全问题答案主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  int countByExample(BnesAnswerInfoExample example) throws SQLException;

  /**
   * 按安全问题答案信息条件查询总条数
   * 
   * @param example 安全问题答案信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExampleSelective(BnesAnswerInfo record, BnesAnswerInfoExample example)
      throws SQLException;

  /**
   * 动态按安全问题答案信息条件进行修改
   * 
   * @param record 安全问题答案信息实体
   * @param example 安全问题答案信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExample(BnesAnswerInfo record, BnesAnswerInfoExample example) throws SQLException;

  /**
   * 按条件查询安全问题答案信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  int selectCountByVo(BnesAnswerInfo vo) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 安全问题答案信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  List selectPageByVo(BnesAnswerInfo vo) throws SQLException;
}
