package com.pltfm.app.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.EraInfoService;
import com.pltfm.app.service.PersonalBasicInfoService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.MD5;
import com.pltfm.app.util.RegisterDeviceMap;
import com.pltfm.app.util.RegisterPlatformMap;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.HealthYgenericInfo;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.PersonalBasicInfo;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.Rank;
/*import com.pltfm.app.vobject.UserLevel;*/
import com.pltfm.sys.model.SysUser;


/**
 * 个人信息Action类
 * 
 * @author cjm
 * @since 2013-7-18
 */
@SuppressWarnings("rawtypes")
@Component(value = "personalBasicInfosAction")
@Scope(value = "prototype")
public class PersonalBasicInfosAction extends BaseAction implements ModelDriven {
    private static final long serialVersionUID = -3990609773322878451L;
    private static final Logger logger = LoggerFactory.getLogger(PersonalBasicInfosAction.class);

    private static final String WEBSERVICEURL = ConfigurationUtil.getString("KM_WEBSERVICE_SCORE");
    /**
     * 个人信息业务逻辑接口
     */
    @Resource(name = "personalBasicInfoService")
    private PersonalBasicInfoService personalBasicInfoService;

    @Resource(name = "eraInfoServiceImpl")
    private EraInfoService eraInfoService;

    /**
     * 分页类
     */
    private Page page;

    /**
     * 个人信息实体
     */
    private PersonalBasicInfo personalBasicInfo;

    /**
     * 健康信息实体
     */
    private HealthYgenericInfo healthYgenericInfo;

    /**
     * 个人个人信息实体
     */
    private PersonalityInfo personalityInfo;

    private EraInfo eraInfo;

    /**
     * 登录信息实体
     */
    private LoginInfo loginInfo;

    /**
     * 账户信息实体
     */
    private AccountInfo accountInfo = new AccountInfo();

    /**
     * 个人客户的头衔
     */
    private List<Rank> rankList;

    /**
     * 个人客户的等级
     */
  /*  private List<UserLevel> levelList;*/

    private Integer personalId;

    private Integer export;

    /**
     * 需要同步的登录账号
     */
    private String syncAccountLogin;

    private static final int BUFFER_SIZE = 16 * 1024;
    private File myFile; // 上传文件
    private String fileName; // 上传文件名
    private String imageFileName; // 修改后的文件
    private String caption;// 文件说明，与页面属性绑定
    private static final String uploadImages = ConfigurationUtil.getString("uploadImages");
    private static final String userImageUrl = ConfigurationUtil.getString("userImageUrl");
    private static final String userImage = ConfigurationUtil.getString("UserImage");
    /**
     * 多条删除个人信息主键集合
     */
    private List<Integer> n_PersonalIds;
    private Integer loginId;
    private List<PersonalBasicInfo> personalBasicInfoList;

    // 个人客户导出excel列表
    public String exportPersonalInfoList() {
        // 获取要导出的用户列表
        try {
            DateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String time = format2.format(new Date());
            super.getRequest().setAttribute("time", time);
            personalBasicInfoList =
                    personalBasicInfoService.queryPersonalInfoList(personalBasicInfo);
            super.getRequest().setAttribute("personalBasicInfoList", personalBasicInfoList);
        } catch (Exception e) {
            logger.error("获取导出用户列表失败" + e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 查询登录账号是否重复
     * 
     * @return
     */
    public void checkLoginAccount() {
        try {
            super.writeJson(personalBasicInfoService
                    .checkLoginAccount(personalBasicInfo.getLoginAccount()));
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * 查询登录账号邮件是否重复
     * 
     * @return
     */
    public void checkLoginEail() {
        try {
            super.writeJson(personalBasicInfoService.checkLoginEmail(personalBasicInfo.getEmail(),
                    personalBasicInfo.getN_LoginId()));
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * 查询手机号是否重复
     * 
     * @return
     */
    public void checkLoginMobile() {
        try {
            super.writeJson(personalBasicInfoService.checkLoginMobile(personalBasicInfo.getMobile(),
                    personalBasicInfo.getN_LoginId()));
        } catch (Exception e) {
            logger.error("", e);
        }
    }


    /**
     * 查询个人信息
     * 
     * @return
     */
    public String pageList() {
        
    
        try {
            String isMenu = getRequest().getParameter("isMenu");
            if(!"true".equals(isMenu)){
            Integer[] identityArr = personalBasicInfo.getIdentity();
            if (identityArr != null && identityArr.length > 0 &&identityArr.length!=5) {
                for (Integer tempIdentity : identityArr) {
                    if (tempIdentity == 2) {
                        personalBasicInfo.setIsSupplier(1);
                    } else if (tempIdentity == 3) {
                        personalBasicInfo.setIsPurchaser(1);
                    } else if (tempIdentity == 4) {
                        personalBasicInfo.setIsEra(1);
                    } else if (tempIdentity == 5) {
                        personalBasicInfo.setIsSpreader(1);
                    } else if (tempIdentity == 6) {
                        personalBasicInfo.setIsCrowder(1);
                    }
                }
            }

            page = personalBasicInfoService.selectPageByVo(page, personalBasicInfo);
            }else{
                getRequest().setAttribute("isMenu", "true");
            }
          /*  levelList = personalBasicInfoService.queryByPersonalUserLevel();
            Map<Integer, String> levelMap = new HashMap<Integer, String>();
            if (levelList != null && !levelList.isEmpty()) {
                for (UserLevel level : levelList) {
                    levelMap.put(level.getN_level_id(), level.getLevel_name());
                }
            }
            getRequest().setAttribute("levelMap", levelMap);*/
            getRequest().setAttribute("registerPlatformMap", RegisterPlatformMap.getMap());
            getRequest().setAttribute("registerDeviceMap", RegisterDeviceMap.getMap());
        
            return "pageSuccess";
        } catch (Exception e) {
            logger.error("", e);
            return "INPUT";
        }
    }

    /**
     * 进入添加个人信息页面
     * 
     * @return
     */
    public String preAdd() {
        return "preAddSuccess";
    }

    /**
     * 查询登录账号状态
     */
    public void checkStatus() {
        try {
            loginInfo = personalBasicInfoService.selectByLoginInfo(loginId);
            super.writeJson(loginInfo.getN_Status());
        } catch (Exception e) {
            logger.error("", e);
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
            logger.error("", e);
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
     * 添加个人信息
     * 
     * @return
     */
    @Token
    public String add() {

        try {
            if (fileName != null) {
                imageFileName = new Date().getTime() + getExtention(fileName);
                File imageFile = new File(uploadImages + imageFileName);
                copy(myFile, imageFile);
                personalityInfo.setHeadSculpture(imageFileName);
            }

            SysUser sysuser = (SysUser) session.get("sysUser");
            personalBasicInfo.setN_Created(sysuser.getUserId());

            loginInfo.setN_Created(sysuser.getUserId());

            personalityInfo.setN_Created(sysuser.getUserId());

            if (healthYgenericInfo == null) {
                healthYgenericInfo = new HealthYgenericInfo();
            }

            healthYgenericInfo.setN_Created(sysuser.getUserId());


            personalBasicInfoService.insertPersonalBasicInfos(personalBasicInfo, loginInfo,
                    personalityInfo, healthYgenericInfo, accountInfo);

           /*删除同步总部会员业务  if (rows > 0) {

                // 更新同步到总部会员管理系统
                List<String> lstAccountLogin = new ArrayList<String>();
                lstAccountLogin.add(loginInfo.getLoginAccount());
                personalBasicInfoService.syncPersonalBasicInfo2Base(lstAccountLogin);

                this.addActionMessage(ConfigureUtils.getMessageConfig("personalbasic.add.success"));
                personalBasicInfo = new PersonalBasicInfo();
                personalityInfo = new PersonalityInfo();
                loginInfo = new LoginInfo();
                healthYgenericInfo = new HealthYgenericInfo();
                accountInfo = new AccountInfo();
                return pageList();
            }*/
        } catch (Exception e) {
            this.addActionMessage(ConfigureUtils.getMessageConfig("personalbasic.add.fail"));
            logger.error("", e);
        }
        return this.preAdd();
    }

    /**
     * 删除个人信息
     * 
     * @return
     */
    @Token
    public String delete() {
        try {
            personalBasicInfoService.deletePersonalBasicInfoList(n_PersonalIds);
            this.addActionMessage(ConfigureUtils.getMessageConfig("personalbasic.delete.success"));
            return pageList();
        } catch (Exception e) {
            this.addActionMessage(ConfigureUtils.getMessageConfig("personalbasic.delete.fail"));
            logger.error("", e);
        }
        return pageList();
    }

    /**
     * 修改个人信息
     * 
     * @return
     */
    @Token
    public String update() {
        try {
            if (fileName != null) {
                imageFileName = new Date().getTime() + getExtention(fileName);
                File imageFile = new File(uploadImages + imageFileName);
                copy(myFile, imageFile);
                personalityInfo.setHeadSculpture(imageFileName);
            }
            SysUser sysuser = (SysUser) session.get("sysUser");
            personalBasicInfo.setN_Modified(sysuser.getUserId());
            loginInfo.setN_Modified(sysuser.getUserId());
            personalityInfo.setN_Modified(sysuser.getUserId());
            healthYgenericInfo.setN_Modified(sysuser.getUserId());
            personalBasicInfoService.udpatePersonalBasicInfos(personalBasicInfo, loginInfo,
                    personalityInfo, healthYgenericInfo, accountInfo);
       /*删除同步总部会员系统     if (rows > 0) {
                // 更新同步到总部会员管理系统
                List<String> lstAccountLogin = new ArrayList<String>();
                lstAccountLogin.add(loginInfo.getLoginAccount());
                personalBasicInfoService.syncPersonalBasicInfo2Base(lstAccountLogin);

                this.addActionMessage(
                        ConfigureUtils.getMessageConfig("personalbasic.update.success"));
                personalBasicInfo = new PersonalBasicInfo();
                personalityInfo = new PersonalityInfo();
                loginInfo = new LoginInfo();
                healthYgenericInfo = new HealthYgenericInfo();
                accountInfo = new AccountInfo();
                return pageList();
            }*/


        } catch (Exception e) {
            this.addActionMessage(ConfigureUtils.getMessageConfig("personalbasic.update.fail"));
            logger.error("", e);
        }
        return this.preUpdate();
    }

    /**
     * 进入个人信息修改页面
     * 
     * @return
     */
    public String preUpdate() {
        try {
            loginInfo = personalBasicInfoService.selectByLoginInfo(loginInfo.getN_LoginId());
            personalBasicInfo =
                    personalBasicInfoService.getPersonalInfoByLogId(loginInfo.getN_LoginId());
            personalityInfo =
                    personalBasicInfoService.selectByPersonalityInfo(loginInfo.getN_LoginId());
            healthYgenericInfo =
                    personalBasicInfoService.selectByHealthYgenericInfo(loginInfo.getN_LoginId());
            accountInfo = personalBasicInfoService.selectByAccountInfo(loginInfo.getN_LoginId());
            if (personalityInfo.getHeadSculpture() != null) {
                personalityInfo.setHeadSculpture(userImageUrl + personalityInfo.getHeadSculpture());
            } else {
                personalityInfo.setHeadSculpture(userImage);
            }

            if (personalBasicInfo.getIsEra() > 0) {
                eraInfo = eraInfoService.selectEraInfoDetail(personalBasicInfo.getEraNo());
            }
            getRequest().setAttribute("isThird", getRequest().getParameter("isThird"));
            getRequest().setAttribute("registerPlatformMap", RegisterPlatformMap.getMap());
            getRequest().setAttribute("registerDeviceMap", RegisterDeviceMap.getMap());
        } catch (Exception e) {
            logger.error("初始更新错误", e);
        }
        return "preUpdateSuccess";
    }

    /**
     * 根据登录用户ID 得到个人信息
     * 
     * @return
     */
    public String prePersonDetail() {
        try {
            personalBasicInfo = personalBasicInfoService.getLogin(loginId);
            personalBasicInfo =
                    personalBasicInfoService.getPersonalId(personalBasicInfo.getN_PersonalId());
            personalityInfo = personalBasicInfoService
                    .selectByPersonalityInfo(personalBasicInfo.getN_LoginId());

            healthYgenericInfo = personalBasicInfoService
                    .selectByHealthYgenericInfo(personalBasicInfo.getN_LoginId());
            loginInfo =
                    personalBasicInfoService.selectByLoginInfo(personalBasicInfo.getN_LoginId());
            accountInfo =
                    personalBasicInfoService.selectByAccountInfo(personalBasicInfo.getN_LoginId());
        } catch (Exception e) {
            logger.error("", e);
        }
        return "preDetailSuccess";

    }

    /**
     * 进入个人信息详情页面
     * 
     * @return
     */
    public String preDetail() {
        try {
            loginInfo = personalBasicInfoService.selectByLoginInfo(loginInfo.getN_LoginId());
            personalBasicInfo =
                    personalBasicInfoService.getPersonalInfoByLogId(loginInfo.getN_LoginId());
            personalityInfo =
                    personalBasicInfoService.selectByPersonalityInfo(loginInfo.getN_LoginId());
            healthYgenericInfo =
                    personalBasicInfoService.selectByHealthYgenericInfo(loginInfo.getN_LoginId());
            accountInfo = personalBasicInfoService.selectByAccountInfo(loginInfo.getN_LoginId());
            if (personalityInfo.getHeadSculpture() != null) {
                personalityInfo.setHeadSculpture(userImageUrl + personalityInfo.getHeadSculpture());
            } else {
                personalityInfo.setHeadSculpture(userImage);
            }

            if (personalBasicInfo.getIsEra() > 0) {
                eraInfo = eraInfoService.selectEraInfoDetail(personalBasicInfo.getEraNo());
            }
            
            getRequest().setAttribute("isThird", getRequest().getParameter("isThird"));
            getRequest().setAttribute("registerPlatformMap", RegisterPlatformMap.getMap());
            getRequest().setAttribute("registerDeviceMap", RegisterDeviceMap.getMap());
        } catch (Exception e) {
            logger.error("", e);
        }
        return "preDetailSuccess";
    }

    /**
     * 返利网用户信息查询
     * 
     * @return
     */
   /*删除返利网业务  public String queryRebateUserInfo() {
        try {
            page = personalBasicInfoService.searchPageByVoForRebate(page, personalBasicInfo);
            rankList = personalBasicInfoService.queryByPersonalRank();
            levelList = personalBasicInfoService.queryByPersonalUserLevel();
            return "pageSuccess";
        } catch (Exception e) {
            logger.error("", e);
            return "INPUT";
        }
    }
*/


    /**
     * 进入批量同步用户信息页
     * 
     * @return
     */
    public String preBatchSyncUserInfo() {
        return "preBatchSyncSuccess";
    }

    /**
     * 同步用户信息
     * 
     * @return
     */
  /*删除同步总部会员系统  public void syncUser2Base() {
        JSONObject jsonResult = new JSONObject();
        int result = 0;
        try {
            String accountLogin = personalBasicInfo.getLoginAccount().replace(StringUtil.COMMA,
                    StringUtil.COMMA_CHINESE);// 将逗号转换为一种，之后统一处理
            List<String> lstAccountLogin = new ArrayList<String>();
            // 批量
            if (StringUtils.isNotEmpty(accountLogin)) {
                if (accountLogin.indexOf(StringUtil.COMMA_CHINESE) != -1) {
                    lstAccountLogin =
                            StringUtil.asListExcludeBlank(accountLogin, StringUtil.COMMA_CHINESE);
                } else {
                    lstAccountLogin.add(accountLogin);
                }
                result = personalBasicInfoService.syncPersonalBasicInfo2Base(lstAccountLogin);
            }
            if (result > 0) {
                jsonResult.put("syncResult", "SUCESS"); // 同步结果
            } else {
                jsonResult.put("syncResult", "FAIL"); // 同步结果
            }
            super.getResponse().getWriter().print(jsonResult.toString());
        } catch (Exception e) {
            logger.error("++++++++++++++" + e);
        }
    }*/



    // 更新时代会员信息
    public String refreshErainfo() {
        try {
            RPCServiceClient client = new RPCServiceClient();
            Options options = client.getOptions();
            EndpointReference end = new EndpointReference(WEBSERVICEURL);
            options.setTo(end);
            options.setTimeOutInMilliSeconds(10000l);
            Object[] obj = new Object[] {returnXmlStr("123456789012")};
            Class<?>[] classes = new Class[] {String.class};
            QName qname = new QName("http://service.web.kmd.org/", "getMemberInfoID");
            String result = (String) client.invokeBlocking(qname, obj, classes)[0];
            System.out.println(result);
        } catch (Exception e) {

        }
        return null;
    }

    public static String returnXmlStr(String cardId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");// 时间 格式
        String sDate = sdf.format(new Date());
        String sign = sDate + "KANGMEI_#@￥&@_@!!_2015";
        sign = (MD5.md5crypt(sign)).toLowerCase();
        String xml = "<?xml version='1.0' encoding='UTF-8'?>" + "<IMessage>"
                + "<IMessageHeade></IMessageHeade>" + "<MessageBody><MARK>" + "<sDate>" + sdf
                + "</sDate>" // 时间戳
                + "<sign>" + sign + "</sign>" // 秘钥=MD5(时间戳+私定秘钥)
                + "</MARK><DATA><cardNum>" + cardId + "</cardNum>" // 会员卡号
                + "</DATA>" + "</MessageBody></IMessage>";
        logger.info("查询总部积分信息xml内容：" + xml);
        return xml;
    }

    @Override
    public Object getModel() {
        personalBasicInfo = new PersonalBasicInfo();
        return personalBasicInfo;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public EraInfo getEraInfo() {
        return eraInfo;
    }

    public void setEraInfo(EraInfo eraInfo) {
        this.eraInfo = eraInfo;
    }

    public PersonalBasicInfo getPersonalBasicInfo() {
        return personalBasicInfo;
    }

    public void setPersonalBasicInfo(PersonalBasicInfo personalBasicInfo) {
        this.personalBasicInfo = personalBasicInfo;
    }

    public HealthYgenericInfo getHealthYgenericInfo() {
        return healthYgenericInfo;
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

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public List<Rank> getRankList() {
        return rankList;
    }

    public void setRankList(List<Rank> rankList) {
        this.rankList = rankList;
    }

   /* public List<UserLevel> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<UserLevel> levelList) {
        this.levelList = levelList;
    }*/

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

    public List<Integer> getN_PersonalIds() {
        return n_PersonalIds;
    }

    public void setN_PersonalIds(List<Integer> nPersonalIds) {
        n_PersonalIds = nPersonalIds;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public Integer getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Integer personalId) {
        this.personalId = personalId;
    }

    public List<PersonalBasicInfo> getPersonalBasicInfoList() {
        return personalBasicInfoList;
    }

    public void setPersonalBasicInfoList(List<PersonalBasicInfo> personalBasicInfoList) {
        this.personalBasicInfoList = personalBasicInfoList;
    }

    public Integer getExport() {
        return export;
    }

    public void setExport(Integer export) {
        this.export = export;
    }

    public String getSyncAccountLogin() {
        return syncAccountLogin;
    }

    public void setSyncAccountLogin(String syncAccountLogin) {
        this.syncAccountLogin = syncAccountLogin;
    }
}
