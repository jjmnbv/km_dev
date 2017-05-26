package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;
import com.pltfm.app.vobject.HealthYgenericInfo;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.MdicalExcusieInfo;
import com.pltfm.app.vobject.PersonalBasicInfo;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.Rank;
import com.pltfm.app.vobject.UserLevel;

/**
 * 专家基本业务逻辑接口
 * 
 * @author gwl
 * 
 */
public interface PersonalBasicInfoService {
  /**
   * anthor lijianjun date：150420 条件查询个人信息
   * 
   * @param vo
   * @return
   * @throws Exception
   */
  List<PersonalBasicInfo> queryPersonalInfoList(PersonalBasicInfo vo) throws Exception;

  /**
   * 根据vo条件查询专家分布信息page
   * 
   * @param GWL 分页实体
   * @param vo 专家基本信息实体
   * @return 返回值
   * @throws Exception
   */
  public Page PagePersonalBasicInfo(Page pageParam, PersonalBasicInfo vo) throws Exception;

  /**
   * 跟据个人id查登录信息
   * 
   * @param 登录信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public LoginInfo getLogin_Id(Integer n_LoginId) throws SQLException;

  /**
   * 跟据专家id查询
   * 
   * @param Rank 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public PersonalBasicInfo getParentId(Integer personalId) throws SQLException;

  /**
   * 跟据id查询医务专属信息
   * 
   * @param MdicalExcusieInfo 医务专属信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public MdicalExcusieInfo getMedicalMattersExclusive_id(Integer medicalMattersExclusive_id)
      throws SQLException;

  /**
   * 获取客户等级列表信息
   * 
   * @param userLevel 客户等级vo
   * @return 返回值
   * @throws Exception 异常
   */
  public List<UserLevel> getUserLevellist(int nCustomerTypeId) throws Exception;

  /**
   * 添加专家信息
   * 
   * @param 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer insertPersonalBasicInfo(PersonalBasicInfo p, LoginInfo loginInfo,
      MdicalExcusieInfo mdicalExcusieInfo, PersonalityInfo personalityInfo,
      HealthYgenericInfo healthYgenericInfo, AccountInfo accountInfo) throws SQLException;

  /**
   * 查询专家下的子级类别集合
   */
  public List<BnesCustomerTypeQuery> queryByComm();

  /**
   * 修改专家信息
   * 
   * @param 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer udpatePersonalBasicInfo(PersonalBasicInfo p, LoginInfo loginInfo,
      MdicalExcusieInfo mdicalExcusieInfo, PersonalityInfo personalityInfo,
      HealthYgenericInfo healthYgenericInfo, AccountInfo accountInfo) throws SQLException;

  /**
   * 获取专家基本信息总条数
   * 
   * @param example 专家基本信息实体
   * @return 返回值
   * @throws SQLException 异常
   */
  Integer countByExample(PersonalBasicInfo example) throws Exception;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 个人基本信息实体
   * @return 返回值
   * @throws Exception
   */
  Page searchPageByPersonalBasicInfo(Page pageParam, PersonalBasicInfo vo) throws Exception;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 专家基本信息实体
   * @return 返回值
   * @throws Exception
   */
  Page searchPageByVo(Page pageParam, PersonalBasicInfo vo) throws Exception;

  /**
   * 添加专家信息
   * 
   * @param Rank 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public void insertPersonalBasicInfo(PersonalBasicInfo p) throws SQLException;

  /**
   * 删除专家信息
   * 
   * @param Rank 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer deletePersonalBasicInfo(List<Integer> personalIds) throws SQLException;

  /**
   * 修改专家信息
   * 
   * @param Rank 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer udpatePersonalBasicInfo(PersonalBasicInfo p) throws SQLException;

  /**
   * 跟据专家id查询
   * 
   * @param Rank 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public PersonalBasicInfo getPersonalId(Integer personalId) throws SQLException;

  /**
   * 添加个人信息
   * 
   * @param personalBasicInfo 个人实体
   * @param loginInfo 登录实体
   * @param personalityInfo 个人个性实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer insertPersonalBasicInfos(PersonalBasicInfo personalBasicInfo, LoginInfo loginInfo,
      PersonalityInfo personalityInfo, HealthYgenericInfo healthYgenericInfo,
      AccountInfo accountInfo) throws Exception;

  /**
   * 修改个人信息
   * 
   * @param personalBasicInfo 个人实体
   * @param loginInfo 登录实体
   * @param personalityInfo 个人个性实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer udpatePersonalBasicInfos(PersonalBasicInfo personalBasicInfo, LoginInfo loginInfo,
      PersonalityInfo personalityInfo, HealthYgenericInfo healthYgenericInfo,
      AccountInfo accountInfo) throws Exception;

  /**
   * 查询账户号
   * 
   * @param accountLogin
   * @return
   * @throws Exception
   */
  Integer checkLoginAccount(String loginAccount) throws Exception;

  /**
   * 查询邮件地址
   * 
   * @param accountLogin
   * @return
   * @throws Exception
   */
  Integer checkLoginEmail(String email, Integer loginid) throws Exception;

  /**
   * 根据登录主键查询单条登录信息
   * 
   * @param n_LoginId 登录信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  LoginInfo selectByLoginInfo(Integer n_LoginId) throws Exception;

  /**
   * 根据登录主键查询单条账户信息
   * 
   * @param n_LoginId 登录信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  AccountInfo selectByAccountInfo(Integer n_LoginId) throws Exception;

  /**
   * 根据登录主键查询单条健康信息
   * 
   * @param n_LoginId 登录信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  HealthYgenericInfo selectByHealthYgenericInfo(Integer n_LoginId) throws Exception;

  /**
   * 根据主键查询单条个人个性信息
   * 
   * @param n_LoginId 登录信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  PersonalityInfo selectByPersonalityInfo(Integer n_LoginId) throws Exception;

  /**
   * 删除个人信息
   * 
   * @param personalIds 个人信息ID
   * @return
   * @throws SQLException
   */
  Integer deletePersonalBasicInfoList(List<Integer> personalIds) throws Exception;

  /**
   * 查询个人客户的头衔
   * 
   * @return
   */
  List<Rank> queryByPersonalRank() throws Exception;

  /**
   * 查询个人客户的等级
   * 
   * @return
   */
  List<UserLevel> queryByPersonalUserLevel() throws Exception;

  /**
   * 根据专家类别客户查询专家客户的头衔
   * 
   * @return
   */
  public List<Rank> queryPersonalRank(Integer customerTypeId);

  /**
   * 根据专家类别客户查询专家客户的等级
   * 
   * @return
   */
  public List<UserLevel> queryPersonalserLevel(Integer customerTypeId);

  public PersonalBasicInfo getLogin(Integer login) throws SQLException;

  Page searchPageByVoForRebate(Page page, PersonalBasicInfo personalBasicInfo) throws SQLException;

  /**
   * 同步个人信息到总部会员系统
   * 
   * @param lstAccountLogin
   * @return
   * @throws ServiceException
   */
  /*删除总部会员业务 int syncPersonalBasicInfo2Base(List<String> lstAccountLogin) throws ServiceException;
*/
  /**
   * 处理总部会员信息到个人信息
   * 
   * @param userInfo
   * @return
   * @throws ServiceException
   */
  /*删除总部会员业务 int processBase2PersonalBasicInfo(String userInfo) throws ServiceException; */
  
  /**
   * 用户系统优化，新的分页查询方法
   * @param pageParam
   * @param vo
   * @return
   * @throws Exception
   */
  Page selectPageByVo(Page pageParam, PersonalBasicInfo vo) throws Exception;
  
  /**
   * 新获取用户信息的接口
   * @param loginId
   * @return
   * @throws SQLException
   */
  PersonalBasicInfo getPersonalInfoByLogId(Integer loginId) throws SQLException;
  
  /**
   * 
   * @param mobile
   * @param loginid
   * @return
   * @throws Exception
   */
  Integer checkLoginMobile(String mobile, Integer loginid) throws Exception ;
  

}
