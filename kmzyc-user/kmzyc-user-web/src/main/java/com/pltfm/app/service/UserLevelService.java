package com.pltfm.app.service;

import java.math.BigDecimal;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dataobject.UserLevelDO;
import com.pltfm.app.vobject.UserLevel;

/**
 * 客户的等级业务逻辑接口
 * 
 * @author zhl
 * @since 2013-07-08
 */
public interface UserLevelService {
  /**
   * 添加客户等级信息
   * 
   * @param userLevel 客户等级vo
   * @return 返回值
   * @throws Exception 异常
   */
  public Integer addUserLevel(UserLevel userLevel) throws Exception;

  /**
   * 修改客户等级信息
   * 
   * @param userLevel 客户等级vo
   * @return 返回值
   * @throws Exception 异常
   */
  public Integer updateUserLevel(UserLevel userLevel) throws Exception;

  /**
   * 多条删除客户等级信息
   * 
   * @param levelIds 客户等级id集合
   * @return
   * @throws Exception 异常
   */
  public Integer deleteUserLevel(List<String> levelIds) throws Exception;

  /**
   * 单条删除客户等级信息
   * 
   * @param levelIds 客户等级id
   * @return
   * @throws Exception 异常
   */
  public Integer deleteByPrimaryKey(Integer levelIds) throws Exception;

  /**
   * 获取客户等级列表信息
   * 
   * @param userLevel 客户等级vo
   * @return 返回值
   * @throws Exception 异常
   */
  public List<UserLevel> getUserLevellist(UserLevel userLevel) throws Exception;

  /**
   * 分页查询客户等级信息
   * 
   * @param userLevel 客户等级实体
   * @return
   * @throws Exception 异常
   */
  public Page queryForPage(UserLevel userLevel, Page page) throws Exception;

  /**
   * 通过主键查询客户等级信息
   * 
   * @param userLevelId 客户等级主键
   * @return
   * @throws Exception 异常
   */
  public UserLevel searchByPrimaryKey(Integer userLevelId) throws Exception;

  /**
   * 通过登录账号查询客户等级并更新客户信息中等级字段
   * 
   * @param userLevelDO
   * @return 受影响行数
   * @throws Exception
   */
  public Integer searchUserLevelUpdateInfo(UserLevelDO userLevelDO) throws Exception;

  /**
   * 通过客户类型获取消费金额最大值
   * 
   * @param userLevel 客户等级实体
   * @return
   * @throws Exception 异常
   */
  public Integer getMaxExpendCustomer(Integer typeId) throws Exception;

  /**
   * 通过主键查询某个等级上信息
   * 
   * @param userLevel 客户等级实体
   * @return
   * @throws Exception 异常
   */
  public List<UserLevel> getmaxUserLevellist(BigDecimal userLevelID) throws Exception;
}
