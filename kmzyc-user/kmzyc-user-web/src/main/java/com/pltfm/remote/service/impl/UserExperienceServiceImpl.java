package com.pltfm.remote.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.user.remote.service.UserExperienceService;
import com.pltfm.app.dao.BloodIntegralInfoDAO;
import com.pltfm.app.dao.BloodIntegralRuleDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dao.PersonalityInfoDAO;
import com.pltfm.app.dao.RankDAO;
import com.pltfm.app.dataobject.RankDO;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.BloodIntegralInfo;
import com.pltfm.app.vobject.BloodIntegralRule;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.LoginInfoExample;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.Rank;

/**
 * 专家经验远程接口实现类
 * 
 * @author gwl
 * @since 2013-08-06
 */
@Component(value = "userExperienceService")
public class UserExperienceServiceImpl implements UserExperienceService {
  private static final long serialVersionUID = 6788615363791043973L;
  // 经验规则DAO
  @Resource(name = "bloodIntegralRuleDAO")
  private BloodIntegralRuleDAO bloodIntegralRuleDAO;
  // 经验明细dao
  @Resource(name = "bloodIntegralInfoDAO")
  private BloodIntegralInfoDAO bloodIntegralInfoDAO;
  // 个人信息
  @Resource(name = "personalityInfoDAO")
  private PersonalityInfoDAO personalityInfoDAO;
  // 客户头衔
  @Resource(name = "rankDAO")
  private RankDAO rankDAO;
  // 客户登录DAO
  @Resource(name = "loginInfoDAO")
  private LoginInfoDAO loginInfoDAO;

  /**
   * 更新专家经验远程接口
   * 
   * @param ruleCode 规则编号 rule003在线回复 rule002 完善资料 rule001 个人注册 满意度(rule0011 rule0012 rule0013
   *        rule0014 rule0015 )
   * @param loginAccount 登录账号
   * @return 0 代表出现异常失败 1代表执行成功
   * @throws Exception 异常信息
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer updateUserIntegralRule(String ruleCode, String loginAccount) throws Exception {
    LoginInfoExample loginInfoExample = new LoginInfoExample();
    loginInfoExample.createCriteria().andLoginAccountEqualTo(loginAccount);
    List infoList = loginInfoDAO.selectByExample(loginInfoExample);
    if (ListUtils.isNotEmpty(infoList)) {
      LoginInfo loginInfo = (LoginInfo) infoList.get(0);
      // 通过规则编号获得经验规则信息，计算所得经验数量
      BloodIntegralRule example = new BloodIntegralRule();
      example.setCode(ruleCode);
      List list = bloodIntegralRuleDAO.selectByExample(example);
      if (ListUtils.isNotEmpty(list)) {
        BloodIntegralRule bloodIntegralRule = (BloodIntegralRule) list.get(0);
        Integer integralnumber = 0;
        if (bloodIntegralRule != null) {
          integralnumber = bloodIntegralRule.getIntegralnumber();
        }
        // 新增经验明细信息
        BloodIntegralInfo b = new BloodIntegralInfo();
        b.setLoginId(loginInfo.getN_LoginId());
        if (bloodIntegralRule != null) {
        b.setDiscribe(bloodIntegralRule.getDiscribe());
        }
        b.setCreateDate(new Date());
        bloodIntegralInfoDAO.insert(b);
        // 通过主键获取个人个性信息更新客户头衔信息
        PersonalityInfo infoNew =
            personalityInfoDAO.selectByPersonalityInfo(loginInfo.getN_LoginId());
        if (infoNew != null) {
          RankDO rankDO = new RankDO();
          rankDO.setCustomerTypeId(loginInfo.getN_CustomerTypeId());
          rankDO.setIntegralnumber(infoNew.getN_EmpiricalValue() + integralnumber);
          rankDO.setPersonalityId(infoNew.getN_PersonalityId());
          Rank rank = rankDAO.getRankiId(rankDO);// 跟据rankDO数据查对应该头衔
          if (rank != null) {
            PersonalityInfo personInfoNew = new PersonalityInfo();
            personInfoNew.setN_RankId(rank.getRankId());
            personInfoNew.setN_EmpiricalValue(infoNew.getN_EmpiricalValue() + integralnumber);
            personInfoNew.setN_PersonalityId(infoNew.getN_PersonalityId());
            personalityInfoDAO.updateByPrimaryKeySelective(personInfoNew);//
          }
        }
        return 1;
      } else {
        return 0;

      }
    } else {
      return 0;
    }
  }

  public BloodIntegralRuleDAO getBloodIntegralRuleDAO() {
    return bloodIntegralRuleDAO;
  }

  public void setBloodIntegralRuleDAO(BloodIntegralRuleDAO bloodIntegralRuleDAO) {
    this.bloodIntegralRuleDAO = bloodIntegralRuleDAO;
  }

  public BloodIntegralInfoDAO getBloodIntegralInfoDAO() {
    return bloodIntegralInfoDAO;
  }

  public void setBloodIntegralInfoDAO(BloodIntegralInfoDAO bloodIntegralInfoDAO) {
    this.bloodIntegralInfoDAO = bloodIntegralInfoDAO;
  }

  public PersonalityInfoDAO getPersonalityInfoDAO() {
    return personalityInfoDAO;
  }

  public void setPersonalityInfoDAO(PersonalityInfoDAO personalityInfoDAO) {
    this.personalityInfoDAO = personalityInfoDAO;
  }

  public RankDAO getRankDAO() {
    return rankDAO;
  }

  public void setRankDAO(RankDAO rankDAO) {
    this.rankDAO = rankDAO;
  }

  public LoginInfoDAO getLoginInfoDAO() {
    return loginInfoDAO;
  }

  public void setLoginInfoDAO(LoginInfoDAO loginInfoDAO) {
    this.loginInfoDAO = loginInfoDAO;
  }
}
