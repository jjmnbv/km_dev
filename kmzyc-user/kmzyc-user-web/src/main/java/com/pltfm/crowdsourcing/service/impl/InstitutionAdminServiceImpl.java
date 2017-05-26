package com.pltfm.crowdsourcing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.km.commons.general.exception.KmServiceException;
import com.km.commons.general.model.ResultData;
import com.km.crowdsourcing.model.InstitutionInfo;
import com.km.crowdsourcing.service.InstitutionAdminService;
import com.pltfm.app.dao.AccountInfoDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.service.PersonalBasicInfoService;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.LoginInfoExample;
import com.pltfm.crowdsourcing.dao.InstitutionAdminDao;
import com.pltfm.crowdsourcing.dao.InstitutionInfoDao;


/**
 * 
 * @ClassName: InstitutionAdminServiceImpl
 * @Description: 机构审批记录接口实现类
 * @author xys
 * @date 2016年3月23日
 * @version 1.0
 */
@Service(value = "institutionAdminService")
public class InstitutionAdminServiceImpl implements InstitutionAdminService {
  @Resource
  private InstitutionAdminDao institutionAdminDao;

  @Resource
  private InstitutionInfoDao institutionInfoDao;

  @Resource(name = "loginInfoDAO")
  private LoginInfoDAO loginInfoDAO;

  @Resource(name = "accountInfoDAO")
  private AccountInfoDAO accountInfoDAO;

  /**
   * 个人信息业务逻辑接口
   */
  @Resource(name = "personalBasicInfoService")
  private PersonalBasicInfoService personalBasicInfoService;

  @Override
  public ResultData<Map<String, Object>> login(boolean isMobile, String name, String password)
      throws KmServiceException {
    ResultData<Map<String, Object>> resultData = new ResultData<Map<String, Object>>();
    Map<String, String> params = new HashMap<String, String>();
    try {
      params.put("name", name);
      params.put("password", password);
      resultData.setData(institutionAdminDao.queryUserInfoForLogin(params));
      resultData.setCode("200");
      return resultData;
    } catch (Exception e) {
      throw new KmServiceException(e);
    }
  }

  @Override
  @Transactional(rollbackFor = KmServiceException.class)
  public void insActivate(String institutionCode, String password, String mobile)
      throws KmServiceException {
    Map<String, String> params = new HashMap<String, String>();
    try {
      params.put("institutionCode", institutionCode);
      params.put("password", password);
      params.put("mobile", mobile);
      institutionAdminDao.insActivateLoginInfo(params);// 修改login_info
      institutionAdminDao.insActivateAccountInfo(params);// 修改account_info
      institutionAdminDao.insActivateInsInfo(params);// 修改institution_info
      // 修改的信息处理同步

     /*删除推送总部会员系统   String loginAccountStr =
          institutionAdminDao.queryLoginAccountByInstitutioncode(institutionCode);
      if (null != loginAccountStr && !"".equals(loginAccountStr)) {
        List<String> lstAccountLogin = new ArrayList<String>();
        lstAccountLogin.add(loginAccountStr);
        personalBasicInfoService.syncPersonalBasicInfo2Base(lstAccountLogin);
      }*/
    } catch (Exception e) {
      throw new KmServiceException(e);
    }
  }


  @Override
  public void updatePassword(String mobile, String password) throws KmServiceException {
    Map<String, String> params = new HashMap<String, String>();
    try {
      params.put("password", password);
      params.put("mobile", mobile);
      institutionAdminDao.updatePassword(params);// 修改login_info
    } catch (Exception e) {
      throw new KmServiceException(e);
    }
  }


  @Override
  @Transactional(rollbackFor = KmServiceException.class)
  public void updateVmobile(String mobile, String mobileOld) throws KmServiceException {
    Map<String, String> params = new HashMap<String, String>();
    try {
      params.put("mobileOld", mobileOld);
      params.put("mobile", mobile);
      institutionAdminDao.updateLIVmobile(params);// 修改login_info
      institutionAdminDao.updateAIVmobile(params);// 修改account_info
      // 修改的信息处理同步
     /*删除推送总部会员系统  AccountInfo accountInfo = accountInfoDAO.selectByPrimaryLoginInfo("", mobile);
      if (null != accountInfo.getLoginAccount()) {
        List<String> lstAccountLogin = new ArrayList<String>();
        lstAccountLogin.add(accountInfo.getLoginAccount());
        personalBasicInfoService.syncPersonalBasicInfo2Base(lstAccountLogin);
      }*/
    } catch (Exception e) {
      throw new KmServiceException(e);
    }
  }

  @Override
  public void updateBankInfo(InstitutionInfo institutionInfo) throws KmServiceException {
    try {
      institutionInfoDao.updateInsInfoSelective(institutionInfo);
    } catch (Exception e) {
      throw new KmServiceException(e);
    }

  }

  @Override
  public boolean checkUserExists(String mobile) throws Exception {
    LoginInfoExample example = new LoginInfoExample();
    example.createCriteria().andMobileEqualTo(mobile);
    List<LoginInfo> list = loginInfoDAO.selectByExample(example);
    if (list != null && list.size() > 0) {
      return false;
    }
    return true;
  }

  @Override
  public Map<String, String> selectInsInfoForHomePage(String insCode) throws Exception {
    return institutionAdminDao.selectInsInfoForHomePage(insCode);
  }


}
