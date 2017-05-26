package com.pltfm.app.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.CommercialTenantBasicInfoService;
import com.pltfm.app.service.MdicalExcusieInfoService;
import com.pltfm.app.service.PersonalBasicInfoService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;
import com.pltfm.app.vobject.HealthYgenericInfo;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.MdicalExcusieInfo;
import com.pltfm.app.vobject.PersonalBasicInfo;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.Rank;
import com.pltfm.app.vobject.UserLevel;
import com.pltfm.sys.model.SysUser;

@Component(value = "personalBasicAction")
@Scope(value = "prototype")
public class PersonalBasicInfoAction extends BaseAction implements ModelDriven {
  private static final long serialVersionUID = -579941341825593085L;
  @Resource(name = "personalBasicInfoService")
  /**
   * 个人信息业务逻辑接口
   */
  private PersonalBasicInfoService personalBasicInfoService;
  /**
   * 商户信息业务逻辑接口
   */
  @Resource(name = "commercialTenantBasicInfoService")
  private CommercialTenantBasicInfoService commercialTenantBasicInfoService;
  /**
   * 医属信息业务逻辑接口
   */
  @Resource(name = "mdicalExcusieInfoService")
  private MdicalExcusieInfoService mdicalExcusieInfoService;
  /**
   * 医属信息实体
   */
  private MdicalExcusieInfo mdicalExcusieInfo;
  /**
   * 账户信息实体
   */
  private AccountInfo accountInfo;

  /**
   * 健康信息实体
   */

  private HealthYgenericInfo healthYgenericInfo;
  /**
   * 专家下的子级类别集合
   */
  private List<BnesCustomerTypeQuery> customerTypeList;
  /**
   * 个人性信息实体
   */
  private PersonalityInfo personalityInfo;

  /**
   * 分页类
   */
  private Page page;
  /**
   * 个人信息实体
   */
  private PersonalBasicInfo personalBasicInfo;

  /**
   * 个人信息主键
   */
  private Integer personalId;
  /**
   * 多条删除个人信息主键集合
   */
  private List<Integer> personalIds;

  /**
   * 个人信息集合
   */
  private List<PersonalBasicInfo> PersonalBasicInfoList;
  private int customerTypeId; // 客户类型;
  /**
   * 客户等级信息实体
   */
  private UserLevel userLevel;
  /**
   * 客户等级信息集合
   */
  public List<UserLevel> userLevelList;
  /**
   * 登录信息实体
   */
  private LoginInfo loginInfo;
  private static final int BUFFER_SIZE = 16 * 1024;
  private File myFile; // 上传文件
  private String contentType;// 上传文件类型
  private String fileName; // 上传文件名
  private String imageFileName; // 修改后的文件
  private String caption;// 文件说明，与页面属性绑定

  private Integer loginId;
  /**
   * 个人客户的头衔
   */
  private List<Rank> rankList;

  /**
   * 个人客户的等级
   */
  private List<UserLevel> levelList;
  private static final String uploadImages =
      ConfigurationUtil.getString("uploadImages");
  private static final String userImageUrl =
      ConfigurationUtil.getString("userImageUrl");

  /**
   * 专家列表
   * 
   * @return
   */
  public String pageList() {
    try {
      // personalBasicInfo.setN_CustomerTypeId(customerTypeId);
      // rankList = personalBasicInfoService.queryByPersonalRank();
      // levelList = personalBasicInfoService.queryByPersonalUserLevel();
      // customerTypeList = personalBasicInfoService.queryByComm();
      page = personalBasicInfoService.PagePersonalBasicInfo(page, personalBasicInfo);
      rankList = personalBasicInfoService.queryPersonalRank(2);
      levelList = personalBasicInfoService.queryPersonalserLevel(2);
      return "pageSuccess";
    } catch (Exception e) {
      this.addActionError(ConfigureUtils.getMessageConfig("specialist.query.fail"));
      return "queryFail";
    }

  }

  /**
   * 根据专家类别客户查询专家客户的头衔
   */
  public void ajaxOperateCustomerRank() {
    try {
      rankList = personalBasicInfoService.queryPersonalRank(customerTypeId);
      super.writeJson(rankList);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 根据专家类别客户查询专家客户的等级
   */
  public void ajaxOperateCustomerUserLevel() {
    try {
      levelList = personalBasicInfoService.queryPersonalserLevel(customerTypeId);
      super.writeJson(levelList);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 查询登录账号状态
   */
  public void checkStatus() {
    try {
      loginInfo = personalBasicInfoService.selectByLoginInfo(loginId);
      super.writeJson(loginInfo.getN_Status());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 文件上传
   * 
   * @param src
   * @param dst
   */
  private static void copy(File src, File dst) {
    try {
      InputStream in = null;
      OutputStream out = null;
      try {
        in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
        out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
        byte[] buffer = new byte[BUFFER_SIZE];
        while (in.read(buffer) > 0) {
          out.write(buffer);
        }
      } finally {
        if (null != in) {
          in.close();
        }
        if (null != out) {
          out.close();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 截取后缀名
   * 
   * @param fileName
   * @return
   */
  private static String getExtention(String fileName) {
    int pos = fileName.lastIndexOf(".");
    return fileName.substring(pos);
  }

  /**
   * 进入添加专家信息页面
   * 
   * @return
   */
  public String preAdd() {
    try {
      customerTypeList = personalBasicInfoService.queryByComm();
      personalBasicInfo = new PersonalBasicInfo();
      personalityInfo = new PersonalityInfo();
      loginInfo = new LoginInfo();
      healthYgenericInfo = new HealthYgenericInfo();
      accountInfo = new AccountInfo();
      mdicalExcusieInfo = new MdicalExcusieInfo();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "preAddSuccess";
  }

  /**
   * 查询登录账号
   */
  public void checkLoginAccount() {
    try {

      super.writeJson(
          commercialTenantBasicInfoService.checkLoginAccount(personalBasicInfo.getLoginAccount()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Token
  public String add() {
    try {
      SysUser sysuser = (SysUser) session.get("sysUser");
      if (fileName != null) {
        imageFileName = new Date().getTime() + getExtention(fileName);
        File imageFile = new File(uploadImages + imageFileName);
        copy(myFile, imageFile);
        personalityInfo.setHeadSculpture(imageFileName);
      }
      personalBasicInfo.setN_Created(sysuser.getUserId());

      loginInfo.setN_Created(sysuser.getUserId());

      mdicalExcusieInfo.setN_created(sysuser.getUserId());

      personalityInfo.setN_Created(sysuser.getUserId());

      healthYgenericInfo.setN_Created(sysuser.getUserId());

      int rows = personalBasicInfoService.insertPersonalBasicInfo(personalBasicInfo, loginInfo,
          mdicalExcusieInfo, personalityInfo, healthYgenericInfo, accountInfo);


      if (rows > 0) {
        this.addActionMessage(ConfigureUtils.getMessageConfig("specialist.add.success"));
        personalBasicInfo = new PersonalBasicInfo();
        personalityInfo = new PersonalityInfo();
        loginInfo = new LoginInfo();
        healthYgenericInfo = new HealthYgenericInfo();
        accountInfo = new AccountInfo();
        mdicalExcusieInfo = new MdicalExcusieInfo();
        return this.pageList();
      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      this.addActionError(ConfigureUtils.getMessageConfig("specialist.add.fail"));

    }
    return this.preAdd();
  }

  /**
   * 进入个人信息详情页面
   * 
   * @return
   */
  public String preDetail() {
    try {
      customerTypeList = personalBasicInfoService.queryByComm();
      personalBasicInfo = personalBasicInfoService.getParentId(personalId);
      loginInfo = personalBasicInfoService.getLogin_Id(personalBasicInfo.getN_LoginId());
      mdicalExcusieInfo = personalBasicInfoService
          .getMedicalMattersExclusive_id(personalBasicInfo.getN_PersonalId());
      personalityInfo =
          personalBasicInfoService.selectByPersonalityInfo(personalBasicInfo.getN_LoginId());
      healthYgenericInfo =
          personalBasicInfoService.selectByHealthYgenericInfo(personalBasicInfo.getN_LoginId());
      loginInfo = personalBasicInfoService.selectByLoginInfo(personalBasicInfo.getN_LoginId());
      accountInfo = personalBasicInfoService.selectByAccountInfo(personalBasicInfo.getN_LoginId());
      personalityInfo.setHeadSculpture(userImageUrl + personalityInfo.getHeadSculpture());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "preDetailSuccess";
  }

  /**
   * 根据登录用户ID 得到专家信息
   * 
   * @return
   */
  public String preExpertDetail() {
    try {
      personalBasicInfo = personalBasicInfoService.getLogin(loginId);
      customerTypeList = personalBasicInfoService.queryByComm();

      // PersonalBasicInfo personalBasicInfo =new PersonalBasicInfo();
      // personalBasicInfo.setN_PersonalId(personalId);

      personalBasicInfo = personalBasicInfoService.getParentId(personalBasicInfo.getN_PersonalId());
      loginInfo = personalBasicInfoService.getLogin_Id(personalBasicInfo.getN_LoginId());
      mdicalExcusieInfo = personalBasicInfoService
          .getMedicalMattersExclusive_id(personalBasicInfo.getN_PersonalId());
      personalityInfo =
          personalBasicInfoService.selectByPersonalityInfo(personalBasicInfo.getN_LoginId());
      healthYgenericInfo =
          personalBasicInfoService.selectByHealthYgenericInfo(personalBasicInfo.getN_LoginId());
      loginInfo = personalBasicInfoService.selectByLoginInfo(personalBasicInfo.getN_LoginId());
      accountInfo = personalBasicInfoService.selectByAccountInfo(personalBasicInfo.getN_LoginId());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "preDetailSuccess";
  }

  @Token
  public String detele() {
    try {
      personalBasicInfoService.deletePersonalBasicInfo(personalIds);
      this.addActionMessage(ConfigureUtils.getMessageConfig("specialist.delete.success"));
      return this.pageList();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      this.addActionError(ConfigureUtils.getMessageConfig("specialist.delete.fail"));
      return this.pageList();
    }
  }

  @Token
  public String update() {
    try {
      SysUser sysuser = (SysUser) session.get("sysUser");
      if (fileName != null) {
        imageFileName = new Date().getTime() + getExtention(fileName);
        File imageFile = new File(uploadImages + imageFileName);
        copy(myFile, imageFile);
        personalityInfo.setHeadSculpture(imageFileName);
      }

      personalBasicInfo.setN_Modified(sysuser.getUserId());
      loginInfo.setN_Modified(sysuser.getUserId());
      mdicalExcusieInfo.setN_modified(sysuser.getUserId());
      personalityInfo.setN_Modified(sysuser.getUserId());
      healthYgenericInfo.setN_Modified(sysuser.getUserId());
      int rows = personalBasicInfoService.udpatePersonalBasicInfo(personalBasicInfo, loginInfo,
          mdicalExcusieInfo, personalityInfo, healthYgenericInfo, accountInfo);
      if (rows > 0) {
        this.addActionMessage(ConfigureUtils.getMessageConfig("specialist.update.success"));
        personalBasicInfo = new PersonalBasicInfo();
        personalityInfo = new PersonalityInfo();
        loginInfo = new LoginInfo();
        healthYgenericInfo = new HealthYgenericInfo();
        accountInfo = new AccountInfo();
        mdicalExcusieInfo = new MdicalExcusieInfo();
        return this.pageList();
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      this.addActionError(ConfigureUtils.getMessageConfig("specialist.update.fail"));

    }
    return preUpdate();
  }

  /**
   * 进入专家信息详细
   * 
   * @return
   */
  public String preUpdate() {
    try {
      // userLevelList = personalBasicInfoService.getUserLevellist(customerTypeId);
      customerTypeList = personalBasicInfoService.queryByComm();
      personalBasicInfo = personalBasicInfoService.getParentId(personalId);
      loginInfo = personalBasicInfoService.getLogin_Id(personalBasicInfo.getN_LoginId());
      mdicalExcusieInfo = personalBasicInfoService
          .getMedicalMattersExclusive_id(personalBasicInfo.getN_PersonalId());
      personalityInfo =
          personalBasicInfoService.selectByPersonalityInfo(personalBasicInfo.getN_LoginId());
      healthYgenericInfo =
          personalBasicInfoService.selectByHealthYgenericInfo(personalBasicInfo.getN_LoginId());
      loginInfo = personalBasicInfoService.selectByLoginInfo(personalBasicInfo.getN_LoginId());
      accountInfo = personalBasicInfoService.selectByAccountInfo(personalBasicInfo.getN_LoginId());
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "updateSuccess";
  }

  public HealthYgenericInfo getHealthYgenericInfo() {
    return healthYgenericInfo;
  }

  public List<BnesCustomerTypeQuery> getCustomerTypeList() {
    return customerTypeList;
  }

  public void setCustomerTypeList(List<BnesCustomerTypeQuery> customerTypeList) {
    this.customerTypeList = customerTypeList;
  }

  public void setHealthYgenericInfo(HealthYgenericInfo healthYgenericInfo) {
    this.healthYgenericInfo = healthYgenericInfo;
  }

  public PersonalityInfo getPersonalityInfo() {
    return personalityInfo;
  }

  public void setPersonalityInfo(PersonalityInfo personalityInfo) {
    this.personalityInfo = personalityInfo;
  }

  public MdicalExcusieInfoService getMdicalExcusieInfoService() {
    return mdicalExcusieInfoService;
  }

  public void setMdicalExcusieInfoService(MdicalExcusieInfoService mdicalExcusieInfoService) {
    this.mdicalExcusieInfoService = mdicalExcusieInfoService;
  }

  public MdicalExcusieInfo getMdicalExcusieInfo() {
    return mdicalExcusieInfo;
  }

  public void setMdicalExcusieInfo(MdicalExcusieInfo mdicalExcusieInfo) {
    this.mdicalExcusieInfo = mdicalExcusieInfo;
  }

  public CommercialTenantBasicInfoService getCommercialTenantBasicInfoService() {
    return commercialTenantBasicInfoService;
  }

  public void setCommercialTenantBasicInfoService(
      CommercialTenantBasicInfoService commercialTenantBasicInfoService) {
    this.commercialTenantBasicInfoService = commercialTenantBasicInfoService;
  }

  public UserLevel getUserLevel() {
    return userLevel;
  }

  public void setUserLevel(UserLevel userLevel) {
    this.userLevel = userLevel;
  }

  public List<UserLevel> getUserLevelList() {
    return userLevelList;
  }

  public void setUserLevelList(List<UserLevel> userLevelList) {
    this.userLevelList = userLevelList;
  }

  public LoginInfo getLoginInfo() {
    return loginInfo;
  }

  public void setLoginInfo(LoginInfo loginInfo) {
    this.loginInfo = loginInfo;
  }

  public Integer getPersonalId() {
    return personalId;
  }

  public List<Integer> getPersonalIds() {
    return personalIds;
  }

  public void setPersonalIds(List<Integer> personalIds) {
    this.personalIds = personalIds;
  }

  public void setPersonalId(Integer personalId) {
    this.personalId = personalId;
  }

  public int getCustomerTypeId() {
    return customerTypeId;
  }

  public void setCustomerTypeId(int customerTypeId) {
    this.customerTypeId = customerTypeId;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }

  public PersonalBasicInfo getPersonalBasicInfo() {
    return personalBasicInfo;
  }

  public void setPersonalBasicInfo(PersonalBasicInfo personalBasicInfo) {
    this.personalBasicInfo = personalBasicInfo;
  }

  public AccountInfo getAccountInfo() {
    return accountInfo;
  }

  public void setAccountInfo(AccountInfo accountInfo) {
    this.accountInfo = accountInfo;
  }

  public PersonalBasicInfoService getPersonalBasicInfoService() {
    return personalBasicInfoService;
  }

  public void setPersonalBasicInfoService(PersonalBasicInfoService personalBasicInfoService) {
    this.personalBasicInfoService = personalBasicInfoService;
  }

  public List<PersonalBasicInfo> getPersonalBasicInfoList() {
    return PersonalBasicInfoList;
  }

  public void setPersonalBasicInfoList(List<PersonalBasicInfo> personalBasicInfoList) {
    PersonalBasicInfoList = personalBasicInfoList;
  }

  @Override
  public Object getModel() {
    personalBasicInfo = new PersonalBasicInfo();
    return personalBasicInfo;
  }

  public void setMyFileFileName(String fileName) {
    this.fileName = fileName;
  }

  public void setMyFile(File myFile) {
    this.myFile = myFile;
  }

  public String getImageFileName() {
    return imageFileName;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public static int getBufferSize() {
    return BUFFER_SIZE;
  }

  public List<Rank> getRankList() {
    return rankList;
  }

  public void setRankList(List<Rank> rankList) {
    this.rankList = rankList;
  }

  public List<UserLevel> getLevelList() {
    return levelList;
  }

  public void setLevelList(List<UserLevel> levelList) {
    this.levelList = levelList;
  }

  public Integer getLoginId() {
    return loginId;
  }

  public void setLoginId(Integer loginId) {
    this.loginId = loginId;
  }

}
