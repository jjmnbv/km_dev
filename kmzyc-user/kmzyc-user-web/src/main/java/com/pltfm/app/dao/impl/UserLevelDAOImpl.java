package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.UserLevelDAO;
import com.pltfm.app.dataobject.UserLevelDO;
import com.pltfm.app.vobject.UserLevel;

/**
 * 客户等级数据处理类
 * 
 * @author zhl
 * @since 2013-07-08
 */
@Component(value = "userLevelDAO")
public class UserLevelDAOImpl implements UserLevelDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 添加客户等级信息
   * 
   * @param userLevel 客户等级实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer insertUserLevel(UserLevel userLevel) throws SQLException {
    Object newKey = sqlMapClient.insert("APP_USERLEVEL.ibatorgenerated_insert", userLevel);
    return (Integer) newKey;
  }

  /**
   * 删除客户等级信息
   * 
   * @param userLevel 客户等级实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer deleteUserLevel(UserLevel userLevel) throws SQLException {
    Object newKey = sqlMapClient.delete("APP_USERLEVEL.ibatorgenerated_delete", userLevel);
    return (Integer) newKey;
  }

  /**
   * 查询客户等级信息
   * 
   * @param userLevel 客户等级实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @SuppressWarnings("unchecked")
  public List<UserLevel> queryUserLevelList(UserLevel userLevel) throws SQLException {
    return sqlMapClient.queryForList("APP_USERLEVEL.ibatorgenerated_getUserLevelList", userLevel);
  }

  /**
   * 分页查询客户等级信息
   * 
   * @param userLevel 客户等级实体
   * @return
   * @throws SQLException 异常
   */
  public List queryForPage(UserLevel userLevel) throws SQLException {
    return sqlMapClient.queryForList("APP_USERLEVEL.abatorgenerated_pageByRule", userLevel);
  }

  /**
   * 客户等级总数量
   * 
   * @param userlevel 客户等级实体
   * @return
   * @throws SQLException 异常
   */
  public Integer countByLevel(UserLevel userlevel) throws SQLException {
    Integer count = (Integer) sqlMapClient
        .queryForObject("APP_USERLEVEL.abatorgenerated_countByRule", userlevel);
    return count;
  }

  /**
   * 通过主键获取客户等级信息
   * 
   * @param userLevelId 客户等级主键
   * @return
   * @throws SQLException 异常
   */
  public UserLevel selectByPrimaryKey(Integer userLevelId) throws SQLException {
    UserLevel key = new UserLevel();
    key.setN_level_id(userLevelId);
    UserLevel record = (UserLevel) sqlMapClient
        .queryForObject("APP_USERLEVEL.abatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * 通过主键修改客户等级信息
   * 
   * @param userLevel 客户等级信息
   * @return
   * @throws SQLException 异常
   */
  public int updateByPrimaryKey(UserLevel userLevel) throws SQLException {
    int rows = sqlMapClient.update("APP_USERLEVEL.abatorgenerated_updateByPrimaryKey", userLevel);
    return rows;
  }

  /**
   * 通过数据访问类查询客户等级信息
   */
  public UserLevel selectByUserLevelDO(UserLevelDO userLevelDO) throws SQLException {
    UserLevel record = (UserLevel) sqlMapClient
        .queryForObject("APP_USERLEVEL.abatorgenerated_selectByUserLevelDO", userLevelDO);
    return record;
  }

  /**
   * 通过客户类型获取客户等级消费金额最大范围值
   * 
   * @param typeId 客户类型主键
   * @return
   * @throws SQLException 异常
   */
  public Integer getCustomerMaxExpend(UserLevel userLevel) throws SQLException {
    Integer count = (Integer) sqlMapClient
        .queryForObject("APP_USERLEVEL.abatorgenerated_getMaxExpend", userLevel);
    return count;
  }


  /**
   * 查询客户等级信息
   * 
   * @param userLevel 客户等级实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @SuppressWarnings("unchecked")
  public List<UserLevel> getNewUserLevelList(UserLevel userLevel) throws SQLException {
    return sqlMapClient.queryForList("APP_USERLEVEL.ibatorgenerated_getYearUserLevelList",
        userLevel);
  }

  /**
   * 通过主键查询某个等级上信息
   * 
   * @param userLevel 客户等级实体
   * @return
   * @throws Exception 异常
   */
  @SuppressWarnings("unchecked")
  public List<UserLevel> getmaxUserLevellist(BigDecimal userLevelID) throws Exception {
    return sqlMapClient.queryForList("APP_USERLEVEL.itge_getUserLevelMaxList", userLevelID);
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }


}
