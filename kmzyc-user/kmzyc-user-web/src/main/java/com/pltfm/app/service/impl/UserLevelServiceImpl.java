package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dao.UserLevelDAO;
import com.pltfm.app.dataobject.UserLevelDO;
import com.pltfm.app.service.UserLevelService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.UserLevel;

/**
 * 客户等级业务逻辑处理类
 * 
 * @author zhl
 * @since 2013-07-08
 */
@Component(value = "userLevelService")
public class UserLevelServiceImpl implements UserLevelService {
  @Resource(name = "userLevelDAO")
  private UserLevelDAO userLevelDAO;
  @Resource(name = "loginInfoDAO")
  private LoginInfoDAO loginInfoDAO;

  /**
   * 添加客户等级信息
   * 
   * @param userLevel 客户等级vo
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
public Integer addUserLevel(UserLevel userLevel) throws Exception {
    return userLevelDAO.insertUserLevel(userLevel);
  }

  /**
   * 删除客户等级信息
   * 
   * @param userLevel 客户等级vo
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
public Integer deleteUserLevel(List<String> levelIds) throws Exception {
    Integer count = 0;
    if (ListUtils.isNotEmpty(levelIds)) {
      for (String id : levelIds) {
        UserLevel userLevel = new UserLevel();
        userLevel.setN_level_id(Integer.valueOf(id));
        count += userLevelDAO.deleteUserLevel(userLevel);
      }
    }
    return count;
  }

  /**
   * 单挑删除客户等级信息
   * 
   * @param levelIds 客户等级id
   * @return
   * @throws Exception 异常
   */
  @Override
public Integer deleteByPrimaryKey(Integer levelIds) throws Exception {
    UserLevel userLevel = new UserLevel();
    userLevel.setN_level_id(levelIds);
    return userLevelDAO.deleteUserLevel(userLevel);
  }

  /**
   * 获取客户等级列表信息
   * 
   * @param userLevel 客户等级vo
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
public List<UserLevel> getUserLevellist(UserLevel userLevel) throws Exception {
    return userLevelDAO.queryUserLevelList(userLevel);
  }

  /**
   * 修改客户等级信息
   * 
   * @param userLevel 客户等级vo
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
public Integer updateUserLevel(UserLevel userLevel) throws Exception {
    return userLevelDAO.updateByPrimaryKey(userLevel);
  }

  /**
   * 分页查询客户等级信息
   * 
   * @param userLevel 客户等级实体
   * @return
   * @throws Exception 异常
   */
  public Page queryForPage(UserLevel userLevel, Page page) throws Exception {
    // 获取客户积分规则总数
    int totalNum = userLevelDAO.countByLevel(userLevel);
    if (totalNum != 0) {
      page.setRecordCount(totalNum);
      // 设置查询开始结束索引
      userLevel.setStartIndex(page.getStartIndex());
      userLevel.setEndIndex(page.getStartIndex() + page.getPageSize());
      page.setDataList(userLevelDAO.queryForPage(userLevel));
    } else {
      page.setRecordCount(0);
      page.setDataList(null);
    }
    return page;
  }

  /**
   * 通过主键查询客户等级信息
   * 
   * @param userLevelId 客户等级主键
   * @return
   * @throws Exception 异常
   */
  @Override
public UserLevel searchByPrimaryKey(Integer userLevelId) throws Exception {
    return userLevelDAO.selectByPrimaryKey(userLevelId);
  }

  /**
   * 通过登录账号查询客户等级并更新客户信息中等级字段
   * 
   * @param userLevelDO
   * @return 受影响行数
   * @throws Exception
   */
  @Override
public Integer searchUserLevelUpdateInfo(UserLevelDO userLevelDO) throws Exception {
    Integer count = 0;
    UserLevel userLevel = userLevelDAO.selectByUserLevelDO(userLevelDO);
    if (userLevel != null) {
      LoginInfo info = new LoginInfo();
      info.setN_LevelId(userLevel.getN_level_id());
      info.setN_LoginId(userLevelDO.getLoginId());
      count = loginInfoDAO.updateByPrimaryKeySelective(info);
    }
    return count;
  }

  /**
   * 通过客户类型获取消费金额最大值
   * 
   * @param userLevel 客户等级实体
   * @return
   * @throws Exception 异常
   */
  @Override
public Integer getMaxExpendCustomer(Integer typeId) throws Exception {
    UserLevel userLevel = new UserLevel();
    userLevel.setN_customer_type_id(typeId);
    Integer number = userLevelDAO.getCustomerMaxExpend(userLevel);
    if (number != null) {
      number += 1;
    } else {
      number = 0;
    }
    return number;
  }

  /**
   * 通过主键查询某个等级上信息
   * 
   * @param userLevel 客户等级实体
   * @return
   * @throws Exception 异常
   */
  @Override
public List<UserLevel> getmaxUserLevellist(BigDecimal userLevelID) throws Exception {
    return userLevelDAO.getmaxUserLevellist(userLevelID);
  }

  public UserLevelDAO getUserLevelDAO() {
    return userLevelDAO;
  }

  public void setUserLevelDAO(UserLevelDAO userLevelDAO) {
    this.userLevelDAO = userLevelDAO;
  }

  public LoginInfoDAO getLoginInfoDAO() {
    return loginInfoDAO;
  }

  public void setLoginInfoDAO(LoginInfoDAO loginInfoDAO) {
    this.loginInfoDAO = loginInfoDAO;
  }

}
