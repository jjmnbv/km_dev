package com.pltfm.app.dao;

import com.pltfm.app.vobject.HealthYgenericInfo;
import com.pltfm.app.vobject.HealthYgenericInfoExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 健康信息处理接口
 * 
 * @author cjm
 * @since 2013-7-18
 */
public interface HealthYgenericInfoDAO {
  /**
   * 记录分布信息的总数
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int countByExample(HealthYgenericInfoExample example) throws SQLException;

  /**
   * 删除健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByExample(HealthYgenericInfoExample example) throws SQLException;

  /**
   * 跟据主键删除健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int deleteHealthYgenericId(Integer n_HealthYgenericId) throws SQLException;

  /**
   * 跟据id删除健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByPrimaryKey(int nHealthYgenericId) throws SQLException;

  /**
   * 添加健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  void insert(HealthYgenericInfo record) throws SQLException;


  /**
   * 添加健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  void insertSelective(HealthYgenericInfo record) throws SQLException;

  /**
   * 健康信息列表
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  List selectByExample(HealthYgenericInfoExample example) throws SQLException;

  /**
   * 查询健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  HealthYgenericInfo selectByPrimaryKey(int nHealthYgenericId) throws SQLException;

  /**
   * 跟据登录id查询健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  HealthYgenericInfo selectByPrimaryFk(int nLoginId) throws SQLException;

  /**
   * 修改健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKeySelective(HealthYgenericInfo record) throws SQLException;

  int insertHealthInfo(HealthYgenericInfo record) throws SQLException;

  /**
   * 修改健康信息
   * 
   * @param userLevel 健康实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKey(HealthYgenericInfo record) throws SQLException;
}
