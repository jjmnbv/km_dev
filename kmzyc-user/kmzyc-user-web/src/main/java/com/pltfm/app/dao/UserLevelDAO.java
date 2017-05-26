package com.pltfm.app.dao;

import com.pltfm.app.dataobject.UserLevelDO;
import com.pltfm.app.vobject.UserLevel;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * 客户等级数据处理接口
 * 
 * @author zhl
 * @since 2013-07-08
 */
public interface UserLevelDAO {
  /**
   * 添加客户等级信息
   * 
   * @param userLevel 客户等级实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer insertUserLevel(UserLevel userLevel) throws SQLException;

  /**
   * 删除客户等级信息
   * 
   * @param userLevel 客户等级实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer deleteUserLevel(UserLevel userLevel) throws SQLException;

  /**
   * 查询客户等级信息
   * 
   * @param userLevel 客户等级实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public List<UserLevel> queryUserLevelList(UserLevel userLevel) throws SQLException;

  /**
   * 分页查询客户等级信息
   * 
   * @param userLevel 客户等级实体
   * @return
   * @throws SQLException 异常
   */
  public List queryForPage(UserLevel userLevel) throws SQLException;

  /**
   * 客户等级总数量
   * 
   * @param userlevel 客户等级实体
   * @return
   * @throws SQLException 异常
   */
  public Integer countByLevel(UserLevel userlevel) throws SQLException;

  /**
   * 通过主键获取客户等级信息
   * 
   * @param userLevelId 客户等级主键
   * @return
   * @throws SQLException 异常
   */
  public UserLevel selectByPrimaryKey(Integer userLevelId) throws SQLException;

  /**
   * 通过主键修改客户等级信息
   * 
   * @param userLevel 客户等级信息
   * @return
   * @throws SQLException 异常
   */
  public int updateByPrimaryKey(UserLevel userLevel) throws SQLException;

  /**
   * 通过数据访问类查询客户等级信息
   * 
   * @param userLevelDO 客户等级数据访问类
   * @throws SQLException 异常
   */
  public UserLevel selectByUserLevelDO(UserLevelDO userLevelDO) throws SQLException;

  /**
   * 通过客户类型获取客户等级消费金额最大范围值
   * 
   * @param typeId 客户类型主键
   * @return
   * @throws SQLException 异常
   */
  public Integer getCustomerMaxExpend(UserLevel userLevel) throws SQLException;

  /**
   * 查询客户等级信息
   * 
   * @param userLevel 客户等级实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public List<UserLevel> getNewUserLevelList(UserLevel userLevel) throws SQLException;

  /**
   * 通过主键查询某个等级上信息
   * 
   * @param userLevel 客户等级实体
   * @return
   * @throws Exception 异常
   */
  public List<UserLevel> getmaxUserLevellist(BigDecimal userLevelID) throws Exception;

}
