package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dao.UserLevelDAO;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.service.LoginInfoService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.UserLevel;
import com.pltfm.sys.util.ErrorCode;

@Component(value = "loginInfoService")
public class LoginInfoServiceImlp implements LoginInfoService {
  private static Logger logger = Logger.getLogger(LoginInfoServiceImlp.class);


  @Resource(name = "loginInfoDAO")
  private LoginInfoDAO loginInfoDAO;
  @Resource(name = "userLevelDAO")
  private UserLevelDAO userLevelDAO;

  // 电商会员卡号的 默认前缀
  private static final String cardNumPrefix = "8031";

  public UserLevelDAO getUserLevelDAO() {
    return userLevelDAO;
  }

  /**
   * 添加登录信息
   * 
   * @param 登录实体
   * @return 返回值
   * @throws Exception异常
   */
  @Override
public Integer addLoginInfo(LoginInfo l) throws SQLException {
    return loginInfoDAO.insert(l);
  }

  /**
   * 按等级id查询是否存在头衔
   * 
   * @param levelId 等级id
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int countLevel(Integer levelId) throws Exception {
    return loginInfoDAO.countLevel(levelId);
  }

  /**
   * 删除登录信息
   * 
   * @param 登录信息实体
   * @return 返回值
   * @throws Exception异常
   */
  @Override
public Integer deleteLoginInfo(List<String> n_LoginIds) throws SQLException {
    Integer count = 0;
    if (ListUtils.isNotEmpty(n_LoginIds)) {
      for (String id : n_LoginIds) {
        count += loginInfoDAO.deleteByPrimaryKey(Integer.valueOf(id));
      }
    }
    return count;
  }

  /**
   * 分页查询登录信息
   * 
   * @param 登录信息实体
   * @return
   * @throws Exception 异常
   */
  public Page searchPageByVo(Page pageParam, LoginInfo l) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (l == null) {
      l = new LoginInfo();
    }
    int totalNum = loginInfoDAO.selectCountByVo(l);
    pageParam.setRecordCount(totalNum);
    l.setSkip(pageParam.getStartIndex());
    l.setMax(pageParam.getStartIndex() + pageParam.getPageSize());

    pageParam.setDataList(loginInfoDAO.selectPageByVo(l));
    return pageParam;
  }

  /**
   * 获取客户等级列表信息
   * 
   * @param userLevel 客户等级vo
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
public List<UserLevel> getUserLevellist(int nCustomerTypeId) throws Exception {
    UserLevel userLevel = new UserLevel();
    userLevel.setN_customer_type_id(nCustomerTypeId);
    return userLevelDAO.queryUserLevelList(userLevel);
  }

  /**
   * 动态修改登录信息
   * 
   * @param 登录信息实体
   * @return 返回值
   * @throws Exception异常
   */
  @Override
public Integer udpateLoginInfo(LoginInfo l) throws SQLException {
    return loginInfoDAO.updateByPrimaryKeySelective(l);
  }

  /**
   * 跟据个人id查登录信息
   * 
   * @param MdicalExcusieInfo 登录信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public LoginInfo getLoginId(Integer n_LoginId) throws SQLException {
    return loginInfoDAO.selectByPrimaryKey(n_LoginId);
  }

  /**
   * 获取客户基本信息
   * 
   * @param userInfoDO 客户数据方位实体
   * @return
   * @throws Exception 异常
   */
  public Page queryPageBasicUserInfo(Page page, UserInfoDO userInfoDO) throws Exception {
    // 获取客户基本信息总数
    int count = loginInfoDAO.byCountBasicUserInfo(userInfoDO);
    page.setRecordCount(count);
    userInfoDO.setStartIndex(page.getStartIndex());
    userInfoDO.setEndIndex(page.getStartIndex() + page.getPageSize());
    page.setDataList(loginInfoDAO.queryBasicUserInfo(userInfoDO));
    return page;
  }


  @Override
public String generateUserCardNum() throws ServiceException {
    try {
      return cardNumPrefix + loginInfoDAO.generateUserCardNum();
    } catch (Exception ex) {
      logger.error("生成用户卡号失败:" + ex.getMessage());
      throw new ServiceException(ErrorCode.SYNC_PERSONAL_INFO_ERROR, "生成用户卡号失败:" + ex.getMessage());
    }
  }

  @Override
  public String queryUserCardNumByLoginAccount(String accountLogin) throws ServiceException {
    try {
      return loginInfoDAO.queryUserCardNumByLoginAccount(accountLogin);
    } catch (Exception e) {
      logger.error("根据用户登录账号查询用户卡号失败" + e.getMessage());
      throw new ServiceException(ErrorCode.SYNC_PERSONAL_INFO_ERROR,
          "根据用户登录账号查询用户卡号失败:" + e.getMessage());
    }
  }

  /**
   * 获取客户基本信息
   * 
   * @param userInfoDO 客户数据方位实体
   * @return
   * @throws Exception 异常
   */
  @Override
public List<LoginInfo> getLoginInfo(UserInfoDO userInfoDO) throws Exception {
    return loginInfoDAO.getLoginInfo(userInfoDO);
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

  @Override
  public List getLoginAll() throws SQLException {
    return loginInfoDAO.getLoginAll();
  }

  @Override
  public List getAllMobileUser() throws Exception {
    return loginInfoDAO.getAllMobileUser();
  }

}
