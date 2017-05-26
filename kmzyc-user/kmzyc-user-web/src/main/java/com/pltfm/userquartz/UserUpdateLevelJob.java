package com.pltfm.userquartz;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderQryRemoteService;
import com.opensymphony.xwork2.ActionSupport;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dao.PersonalityInfoDAO;
import com.pltfm.app.dao.UserLevelDAO;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.UserLevel;

@SuppressWarnings({"serial", "unchecked"})
@Component(value = "userUpdateLevelJob")
@Scope(value = "prototype")
public class UserUpdateLevelJob extends ActionSupport {
  @Resource(name = "loginInfoDAO")
  private LoginInfoDAO loginInfoDAO;
  @Resource(name = "personalityInfoDAO")
  private PersonalityInfoDAO personalityInfoDAO;
  @Resource(name = "userLevelDAO")
  private UserLevelDAO userLevelDAO;
  @Autowired
  private OrderQryRemoteService orderQryRemoteService;

  // private WebApplicationContext ctx;

  public UserLevelDAO getUserLevelDAO() {
    return userLevelDAO;
  }

  public void setUserLevelDAO(UserLevelDAO userLevelDAO) {
    this.userLevelDAO = userLevelDAO;
  }

  public PersonalityInfoDAO getPersonalityInfoDAO() {
    return personalityInfoDAO;
  }

  public void setPersonalityInfoDAO(PersonalityInfoDAO personalityInfoDAO) {
    this.personalityInfoDAO = personalityInfoDAO;
  }


  /**
   * 定时任务处理用户上一年度消费金额
   * 
   * @return
   */
  public String executeUpdate() {
    UserInfoDO userInfoDO = new UserInfoDO();
    userInfoDO.setStatus(1);


    try {
      // 查询所用用户信息
      List<UserInfoDO> listUserInfoDO = loginInfoDAO.selectUserInfoDOByLoginId(userInfoDO);


      for (UserInfoDO userInfo : listUserInfoDO) {

        // System.out.println("=====================+++++++-------LoginAccount--"+userInfo.getLoginAccount());
        String LoginAccount = userInfo.getLoginAccount();
        // 查询用户上一年度消费金额
        if (LoginAccount != null) {
          double lastYearAmount = this.getLastYearAmount(LoginAccount);
          // System.out.println("=========lastYearAmount========"+lastYearAmount);
          personalityInfoDAO.updateLastYearByLoginId(userInfo.getLoginId(), lastYearAmount);
          int nLoginId = userInfo.getLoginId();
          PersonalityInfo personalityInfo = personalityInfoDAO.selectByPersonalityInfo(nLoginId);
          if (personalityInfo != null) {
            if (userInfo.getLevelId() != null) {
              int levelId = userInfo.getLevelId();
              // System.out.println("=========nLoginId========"+nLoginId);

              UserLevel userLevel = userLevelDAO.selectByPrimaryKey(levelId);
              if (userLevel.getYearMin() != null) {
                double yearMin = userLevel.getYearMin();
                // System.out.println("=========yearMin========"+yearMin+"----------------======amountConsume---"+amountConsume);
                if (lastYearAmount < yearMin) {
                  // System.out.println("=========nLoginId========"+nLoginId);
                  // System.out.println("=========levelId========"+levelId);
                  // System.out.println("=========yearMin========"+yearMin+"----------------======amountConsume---"+amountConsume+"-------------==============lastYearAmount===="+lastYearAmount);
                  UserLevel userLevel1 = new UserLevel();
                  userLevel1.setN_customer_type_id(1);

                  userLevel1.setYearMin(lastYearAmount);

                  // 根据上一年最低消费金额查询用户等级
                  List<UserLevel> listUserLevel = userLevelDAO.getNewUserLevelList(userLevel1);
                  if (listUserLevel.size() > 0) {
                    // System.out.println("====sdfsadff========"+listUserLevel.get(0).getLevel_name()+"n_level_id"+listUserLevel.get(0).getN_level_id());
                    // 更新用户等级
                    LoginInfo login = new LoginInfo();
                    login.setN_LoginId(nLoginId);
                    login.setN_LevelId(listUserLevel.get(0).getN_level_id());
                    // 更新用户等级名称
                    loginInfoDAO.updateByPrimaryKeySelective(login);
                    PersonalityInfo personalityInfoNew = new PersonalityInfo();
                    personalityInfoNew.setN_PersonalityId(personalityInfo.getN_PersonalityId());
                    double max = listUserLevel.get(0).getExpend_max().doubleValue();
                    double yearminMount = listUserLevel.get(0).getYearMin();
                    double feeNum = max - yearminMount;
                    if (feeNum < 0) {
                      feeNum = 0.00;
                    }
                    // 用户等级金额最大值
                    double amountConsume = personalityInfo.getAmountConsume().doubleValue()- feeNum;
                    if (amountConsume < 0) {
                      amountConsume = 0.00;
                    }
                    // 更新等级判断金额
                    personalityInfoNew.setAmountConsume(new BigDecimal(amountConsume));
                    personalityInfoDAO.updateByPrimaryKeySelective(personalityInfoNew);
                  }



                }
              }
            }

          }

          // personalityInfo.
          // 根据登录ID 更新
          /*
           * int row =personalityInfoDAO.updateLastYearByLoginId(userInfo.getLoginId(),
           * lastYearAmount);
           * 
           * 
           * 
           * System.out.println("--------row====personalityInfoDAO.updateLastYearByLoginId:==="+row)
           * ;
           */



        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;

  }

  /**
   * 通过登录账号从订单系统获取上一年度消费金额
   * 
   * @throws ParseException
   * @throws ServiceException
   */
  public double getLastYearAmount(String LoginAccount) throws ParseException, ServiceException {
    double LastYearAmount = 0.00;


    try {
      Map paramsMap = new HashMap();

      Calendar cal = Calendar.getInstance();
      /*
       * int day = cal.get(Calendar.DATE); int month = cal.get(Calendar.MONTH) + 1;
       */
      int year = cal.get(Calendar.YEAR);
      /*
       * int dow = cal.get(Calendar.DAY_OF_WEEK); int dom = cal.get(Calendar.DAY_OF_MONTH); int doy
       * = cal.get(Calendar.DAY_OF_YEAR);
       */
      // System.out.println("=======year------"+year);
      year = 2015;
      SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String sDate = year - 1 + "-01-01 00:00:00";
      String eDate = year + "-01-01 00:00:00";
      // System.out.println("-----------------=======sDate:----"+sDate);
      // System.out.println("-----------------=======eDate:----"+eDate);
      Date startDate = simFormat.parse(sDate);
      Date endDate = simFormat.parse(eDate);
      long status = 6;
      paramsMap.put("startDate", startDate);
      paramsMap.put("endDate", endDate);
      paramsMap.put("account", LoginAccount);
      paramsMap.put("status", status);
      BigDecimal bigDecimal = orderQryRemoteService.getPersonalConsume(paramsMap);
      if (bigDecimal != null) {
        LastYearAmount = bigDecimal.doubleValue();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }



    return LastYearAmount;
  }

  public LoginInfoDAO getLoginInfoDAO() {
    return loginInfoDAO;
  }


  public void setLoginInfoDAO(LoginInfoDAO loginInfoDAO) {
    this.loginInfoDAO = loginInfoDAO;
  }


}
