package com.pltfm.app.dao;

import com.pltfm.app.vobject.BnesAuthenticationInfo;
import com.pltfm.app.vobject.BnesAuthenticationInfoExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 实名认证信息处理接口
 * 
 * @author cjm
 * @since 2013-8-1
 */
public interface BnesAuthenticationInfoDAO {
  /**
   * 添加实名认证信息
   * 
   * @param record 实名认证信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  Integer insert(BnesAuthenticationInfo record) throws SQLException;

  /**
   * 修改实名认证信息
   * 
   * @param record 实名认证信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKey(BnesAuthenticationInfo record) throws SQLException;

  /**
   * 动态修改实名认证信息
   * 
   * @param record 实名认证信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKeySelective(BnesAuthenticationInfo record) throws SQLException;

  /**
   * 按实名认证信息条件查询
   * 
   * @param example 实名认证信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  List selectByExample(BnesAuthenticationInfoExample example) throws SQLException;

  /**
   * 根据实名认证主键查询单条实名认证信息
   * 
   * @param nCommercialTenantId 实名认证主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  BnesAuthenticationInfo selectByPrimaryKey(Integer authenticationId) throws SQLException;

  /**
   * 根据实名认证主键查询单条实名认证信息
   * 
   * @param nCommercialTenantId 实名认证主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByExample(BnesAuthenticationInfoExample example) throws SQLException;

  /**
   * 按实名认证信息条件进行删除
   * 
   * @param example 实名认证信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByPrimaryKey(Integer authenticationId) throws SQLException;

  /**
   * 根据实名认证主键删除实名认证信息
   * 
   * @param nCommercialTenantId 实名认证主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  int countByExample(BnesAuthenticationInfoExample example) throws SQLException;

  /**
   * 按实名认证信息条件查询总条数
   * 
   * @param example 实名认证信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExampleSelective(BnesAuthenticationInfo record, BnesAuthenticationInfoExample example)
      throws SQLException;

  /**
   * 动态按实名认证信息条件进行修改
   * 
   * @param record 实名认证信息实体
   * @param example 实名认证信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExample(BnesAuthenticationInfo record, BnesAuthenticationInfoExample example)
      throws SQLException;

  /**
   * 按条件查询实名认证信息总数量
   * 
   * @param vo 实名认证信息实体
   * @return 返回值
   */
  int selectCountByVo(BnesAuthenticationInfo vo) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 实名认证信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  List selectPageByVo(BnesAuthenticationInfo vo) throws SQLException;
}
