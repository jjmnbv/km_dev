package com.pltfm.app.dao;

import com.pltfm.app.vobject.LatestLogin;
import com.pltfm.app.vobject.LatestLoginExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 最近登录信息处理接口
 * 
 * @author cjm
 * @since 2013-7-24
 */
public interface LatestLoginDAO {
  /**
   * 添加最近登录信息
   * 
   * @param record 最近登录实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  Integer insert(LatestLogin record) throws SQLException;

  /**
   * 修改最近登录信息
   * 
   * @param record 最近登录实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKey(LatestLogin record) throws SQLException;

  /**
   * 动态修改最近登录信息
   * 
   * @param record 最近登录实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKeySelective(LatestLogin record) throws SQLException;

  /**
   * 按最近登录信息条件查询
   * 
   * @param example 最近登录条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  List selectByExample(LatestLoginExample example) throws SQLException;

  /**
   * 根据最近登录主键查询单条手机随机码信息
   * 
   * @param nId 最近登录主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  LatestLogin selectByPrimaryKey(Integer nId) throws SQLException;

  /**
   * 按最近登录信息条件进行删除
   * 
   * @param example 最近登录条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByExample(LatestLoginExample example) throws SQLException;

  /**
   * 根据最近登录主键删除手机随机码信息
   * 
   * @param nCommercialTenantId 最近登录主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByPrimaryKey(Integer nId) throws SQLException;

  /**
   * 按最近登录信息条件查询总条数
   * 
   * @param example 最近登录条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int countByExample(LatestLoginExample example) throws SQLException;

  /**
   * 动态按最近登录信息条件进行修改
   * 
   * @param record 最近登录实体
   * @param example 最近登录条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExampleSelective(LatestLogin record, LatestLoginExample example) throws SQLException;

  /**
   * 按最近登录信息条件进行修改
   * 
   * @param record 最近登录实体
   * @param example 最近登录条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExample(LatestLogin record, LatestLoginExample example) throws SQLException;

  /**
   * 按条件查询最近登录信息总数量
   * 
   * @param vo 最近登录信息类
   * @return 返回值
   */
  int selectCountByVo(LatestLogin vo) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 最近登录信息类
   * @return 返回值
   * @throws SQLException sql异常
   */
  List selectPageByVo(LatestLogin vo) throws SQLException;
}
