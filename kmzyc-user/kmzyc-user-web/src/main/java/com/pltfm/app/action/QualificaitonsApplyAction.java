package com.pltfm.app.action;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
/* 删除邮件业务 import com.kmzyc.mailmobile.service.EmailSubscriptionRemoteService; */
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.AccountInfoService;
import com.pltfm.app.service.QualificaitonsApplyService;
import com.pltfm.app.service.QualificaitonsFileService;
import com.pltfm.app.service.QualificaitonsService;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.QualificaitonsFileDO;
import com.pltfm.app.vobject.QualificationsApplyDO;
import com.pltfm.app.vobject.QualificationsDO;



@Component(value = "qualificaitonsApplyAction")
@Scope("prototype")
// 采购资格申请
public class QualificaitonsApplyAction extends BaseAction implements ModelDriven {

  private static Logger logger = LoggerFactory.getLogger(QualificaitonsApplyAction.class);
  private QualificationsApplyDO qualificationsApplyDO;
  private String QualificaitonsFileImage =
      ConfigurationUtil.getString("QualificaitonsFileImage");
  private QualificaitonsFileDO qualificaitonsFileDO;
  private QualificationsDO qualificationsDO;
  @Resource(name = "accountInfoService")
  private AccountInfoService accountInfoService;
  private AccountInfo accountInfo;

  private Date validDate;

  /*删除邮件业务  @Autowired
  EmailSubscriptionRemoteService emailSubscriptionRemoteService;*/

  public Date getValidDate() {
    return validDate;
  }



  public void setValidDate(Date validDate) {
    this.validDate = validDate;
  }



  public AccountInfoService getAccountInfoService() {
    return accountInfoService;
  }



  public void setAccountInfoService(AccountInfoService accountInfoService) {
    this.accountInfoService = accountInfoService;
  }



  public AccountInfo getAccountInfo() {
    return accountInfo;
  }



  public void setAccountInfo(AccountInfo accountInfo) {
    this.accountInfo = accountInfo;
  }



  public String[] qualificationName;
  String applyTypes = "";
  String applyTypes1 = "";
  String applyTypes2 = "";

  @Resource(name = "qualificaitonsFileService")
  private QualificaitonsFileService qualificaitonsFileService;


  @Resource(name = "qualificaitonsService")
  private QualificaitonsService qualificaitonsService;

  /**
   * 分页类
   */
  private Page page;
  @Resource(name = "qualificaitonsApplyService")
  private QualificaitonsApplyService qualificaitonsApplyService;


  @Override
public Object getModel() {


    qualificationsApplyDO = new QualificationsApplyDO();
    return qualificationsApplyDO;
  }



  public QualificationsApplyDO getQualificationsApplyDO() {
    return qualificationsApplyDO;
  }



  public void setQualificationsApplyDO(QualificationsApplyDO qualificationsApplyDO) {
    this.qualificationsApplyDO = qualificationsApplyDO;
  }



  public QualificaitonsFileDO getQualificaitonsFileDO() {
    return qualificaitonsFileDO;
  }



  public void setQualificaitonsFileDO(QualificaitonsFileDO qualificaitonsFileDO) {
    this.qualificaitonsFileDO = qualificaitonsFileDO;
  }



  public QualificaitonsFileService getQualificaitonsFileService() {
    return qualificaitonsFileService;
  }



  public void setQualificaitonsFileService(QualificaitonsFileService qualificaitonsFileService) {
    this.qualificaitonsFileService = qualificaitonsFileService;
  }



  public QualificaitonsService getQualificaitonsService() {
    return qualificaitonsService;
  }



  public void setQualificaitonsService(QualificaitonsService qualificaitonsService) {
    this.qualificaitonsService = qualificaitonsService;
  }



  public QualificationsDO getQualificationsDO() {
    return qualificationsDO;
  }



  public void setQualificationsDO(QualificationsDO qualificationsDO) {
    this.qualificationsDO = qualificationsDO;
  }



  /***
   * 
   * 显示采购资格申请
   * 
   * @return
   */
 /*删除采购商相关  public String pageList() {
    try {
      if (page == null) {
        page = new Page();
      }

      Integer count =
          qualificaitonsApplyService.selectListQualificaitonsApplyCount(qualificationsApplyDO);
      page.setRecordCount(count);
      qualificationsApplyDO.setSkip(page.getStartIndex());
      qualificationsApplyDO.setMax(page.getStartIndex() + page.getPageSize());

      String applyType = "";
      if (qualificationName != null) {
        for (int i = 0; i < qualificationName.length; i++) {
          if (i < qualificationName.length - 1) {
            applyType = applyType + qualificationName[i] + "|";
            qualificationsApplyDO.setApplyType(applyType);
          } else {
            applyType = applyType + qualificationName[i];
            qualificationsApplyDO.setApplyType(applyType);
          }
        }
      }
      List list = qualificaitonsApplyService.selectListQualificaitonsApply(qualificationsApplyDO);
      page.setDataList(list);
    } catch (Exception e) {
      logger.error("获取采购资格申请列表异常" + e.getMessage(), e);
    }
    return "pageList";
  }

  // // 进入修改
  public String qualificaitonsApplyUpdate() throws Exception {
    try {
      Map session = ActionContext.getContext().getSession();
      qualificationsApplyDO =
          qualificaitonsApplyService.queryQualificaitonsApply(qualificationsApplyDO.getId());
      if (qualificationsApplyDO.getApplyType() != null) {
        qualificationName = qualificationsApplyDO.getApplyType().split("\\|");
        Integer i = qualificationName.length;
        if (i == 1) {

          applyTypes = qualificationName[0];
          if (applyTypes.equals("2")) {
            applyTypes1 = applyTypes;

          } else {

            applyTypes2 = applyTypes;
          }


        } else if (i == 2) {
          if (qualificationName[0].equals("1") && qualificationName[1].equals("2")) {

            applyTypes = qualificationName[0];
            applyTypes1 = qualificationName[1];
          } else if (qualificationName[0].equals("1") && qualificationName[1].equals("3")) {

            applyTypes = qualificationName[0];
            applyTypes2 = qualificationName[1];
          } else {

            applyTypes1 = qualificationName[0];
            applyTypes2 = qualificationName[1];
          }
        } else if (i == 3) {
          if (qualificationName[0].equals("1") && qualificationName[1].equals("2")
              && qualificationName[2].equals("3")) {
            applyTypes = qualificationName[0];
            applyTypes1 = qualificationName[1];
            applyTypes2 = qualificationName[2];
          }



        }
      }

      List list = (List) qualificaitonsFileService
          .queryListQualificaitonsFile(qualificationsApplyDO.getUserId());
      session.put("list", list);
    } catch (SQLException e) {
      logger.error("采购资格管理修改" + e.getMessage(), e);
    }
    return "qualificaitonsApplyUpdate";
  }

  // //删除
  //
  public String qualificaitonsApplyDelete() {

    try {
      Integer id = qualificationsApplyDO.getId();
      if (null != id) {
        // 删除推荐短信
        qualificaitonsApplyService.deleteById(id);
      }
      this.addActionMessage("删除成功！");
    } catch (SQLException e) {
      logger.error("删除采购资格管理成功" + e.getMessage(), e);
      this.addActionMessage("删除采购资格管理失败");
    }
    return pageList();
  }

  // 修改
  public String qualificaitonsApplyEdit() {
    try {
      String applyType = "";
      if (qualificationName != null) {
        for (int i = 0; i < qualificationName.length; i++) {
          if (i < qualificationName.length - 1) {
            applyType = applyType + qualificationName[i] + "|";
            qualificationsApplyDO.setApplyType(applyType);
          } else {
            applyType = applyType + qualificationName[i];
            qualificationsApplyDO.setApplyType(applyType);
          }
        }
      }
      qualificaitonsApplyService.updateQualificaitonsApply(qualificationsApplyDO);


      this.addActionMessage("采购资格管理修改成功");
    } catch (SQLException e) {
      logger.error("采购资格管理修改失败" + e.getMessage(), e);
      this.addActionMessage("采购资格管理修改成功");
    }


    return pageList();
  }


  // 进入详情页面
  public String qualificaitonsApplyDetail() throws SQLException {
    Map session = ActionContext.getContext().getSession();
    qualificationsApplyDO =
        qualificaitonsApplyService.queryQualificaitonsApply(qualificationsApplyDO.getId());



    if (qualificationsApplyDO.getApplyType() != null)
      if (qualificationsApplyDO.getApplyType() != null) {
      qualificationName = qualificationsApplyDO.getApplyType().split("\\|");
      Integer i = qualificationName.length;
      if (i == 1) {

      applyTypes = qualificationName[0];
      if (applyTypes.equals("2")) {
      applyTypes1 = applyTypes;

      } else {

      applyTypes2 = applyTypes;
      }
      } else if (i == 2) {
      if (qualificationName[0].equals("1") && qualificationName[1].equals("2")) {

      applyTypes = qualificationName[0];
      applyTypes1 = qualificationName[1];
      } else if (qualificationName[0].equals("1") && qualificationName[1].equals("3")) {

      applyTypes = qualificationName[0];
      applyTypes2 = qualificationName[1];
      } else {

      applyTypes1 = qualificationName[0];
      applyTypes2 = qualificationName[1];
      }
      } else if (i == 3) {
      if (qualificationName[0].equals("1") && qualificationName[1].equals("2") && qualificationName[2].equals("3")) {
      applyTypes = qualificationName[0];
      applyTypes1 = qualificationName[1];
      applyTypes2 = qualificationName[2];
      }



      }
      }
    List list = (List) qualificaitonsFileService
        .queryListQualificaitonsFile(qualificationsApplyDO.getUserId());

    session.put("list", list);


    return "qualificaitonsApplyDetail";
  }

  // 进入审核页面


  public String qualificaitonsApplyVerify() throws SQLException {
    Map session = ActionContext.getContext().getSession();
    qualificationsApplyDO =
        qualificaitonsApplyService.queryQualificaitonsApply(qualificationsApplyDO.getId());
    if (qualificationsApplyDO.getApplyType() != null) {
      qualificationName = qualificationsApplyDO.getApplyType().split("\\|");
      Integer i = qualificationName.length;
      if (i == 1) {

        applyTypes = qualificationName[0];
        if (applyTypes.equals("2")) {
          applyTypes1 = applyTypes;

        } else {

          applyTypes2 = applyTypes;
        }
      } else if (i == 2) {
        if (qualificationName[0].equals("1") && qualificationName[1].equals("2")) {

          applyTypes = qualificationName[0];
          applyTypes1 = qualificationName[1];
        } else if (qualificationName[0].equals("1") && qualificationName[1].equals("3")) {

          applyTypes = qualificationName[0];
          applyTypes2 = qualificationName[1];
        } else {

          applyTypes1 = qualificationName[0];
          applyTypes2 = qualificationName[1];
        }
      } else if (i == 3) {
        if (qualificationName[0].equals("1") && qualificationName[1].equals("2")
            && qualificationName[2].equals("3")) {
          applyTypes = qualificationName[0];
          applyTypes1 = qualificationName[1];
          applyTypes2 = qualificationName[2];
        }



      }
    }
    List list = (List) qualificaitonsFileService
        .queryListQualificaitonsFile(qualificationsApplyDO.getUserId());
    session.put("list", list);
    return "qualificaitonsApplyVerify";

  }

  // 进入审核通过
  public String qualificaitonsApplyPass() throws SQLException {


    try {
      SysUser sysuser = (SysUser) session.get("sysUser");

      String applyType = "";
      if (qualificationName != null) {
        for (int i = 0; i < qualificationName.length; i++) {
          if (i < qualificationName.length - 1) {
            applyType = applyType + qualificationName[i] + "|";
            qualificationsApplyDO.setApplyType(applyType);
          } else {
            applyType = applyType + qualificationName[i];
            qualificationsApplyDO.setApplyType(applyType);
          }
        }
      }
      qualificationsApplyDO.setAuditingDate(new Date());
      qualificationsApplyDO.setAuditingId(sysuser.getUserId());
      qualificationsApplyDO.setStatus(new Short((short) 2));
      qualificationsApplyDO.setId(qualificationsApplyDO.getId());
      qualificaitonsApplyService.updateQualificaitonsApply(qualificationsApplyDO);



      // 清空所有数据
      // qualificaitonsService.deleteById(qualificationsApplyDO.getUserId());

      // 插入采购资格表
      qualificationsDO = new QualificationsDO();
      if (qualificationName != null) {
        String types = "";
        for (int i = 0; i < qualificationName.length; i++) {

          types = qualificationName[i];
          int type = Integer.parseInt(types);
          short a = (short) type;
          qualificationsDO.setUserId(qualificationsApplyDO.getUserId());
          qualificationsDO.setType(new Short((short) a));
          List list = qualificaitonsService.getQualificaitonsList(qualificationsDO);


          if (list != null && list.size() > 0) {
            for (int j = 0; j < list.size(); j++) {
              QualificationsDO qualificationsDO1 = (QualificationsDO) list.get(j);
              qualificationsDO1.setUserId(qualificationsDO.getUserId());
              qualificationsDO1.setType(qualificationsDO.getType());
              qualificationsDO1.setModifyDate(new Date());
              qualificationsDO1.setStatus(new Short((short) 1));

              if (validDate != null) {
                qualificationsDO1.setValidDate(validDate);
              } else {
                qualificationsDO1.setValidDate(validDate);

              }


              qualificaitonsService.updateQualificaitons(qualificationsDO1);
            }
          } else {
            qualificationsDO.setUserId(qualificationsApplyDO.getUserId());
            qualificationsDO.setType(new Short((short) a));
            qualificationsDO.setStatus(new Short((short) 1));
            qualificationsDO.setCreateDate(new Date());
            if (validDate != null) {
              qualificationsDO.setValidDate(validDate);
            }

            qualificaitonsService.insertQualifications(qualificationsDO);
          }



        }


      }
      accountInfo = accountInfoService.selectByPrimaryLoginInfo(qualificationsApplyDO.getUserId());

      if (accountInfo.getEmail() != null) {
        List emailLists = new ArrayList();
        List<Long> uidLists = new ArrayList<Long>();
        List loginNameList = new ArrayList();
        Integer uid = qualificationsApplyDO.getUserId();
        String uids = uid.toString(uid);
        Long uidss = Long.valueOf(uids).longValue();
        uidLists.add(uidss);
        emailLists.add(accountInfo.getEmail());
        loginNameList.add(accountInfo.getLoginAccount());
        String emailContext1 = "您提交的采购资格变更申请已经审核通过并生效了，请进入我的采购资格查看您最新的资格信息";
        String title = "采购资格审核通知";
        if (uidLists != null && emailLists != null && loginNameList != null) {
          Map<String, Object> sendEmailMap = emailSubscriptionRemoteService.emailBySpread(uidLists,
              emailLists, loginNameList, title, emailContext1, 2);

        }
        // 发送邮件
      }
      List mobileList = new ArrayList();
      List<Long> uidLists = new ArrayList<Long>();
      if (accountInfo.getMobile() != null) {
        mobileList.add(accountInfo.getMobile());
        Integer uid = qualificationsApplyDO.getUserId();
        String uids = uid.toString(uid);
        Long uidss = Long.valueOf(uids).longValue();
        uidLists.add(uidss);
        String smsContext = "您提交的采购资格变更申请已经审核通过并生效了，请进入我的采购资格查看您最新的资格信息";

        Map<String, Object> sendMap =
            messageRemoteService.sendMsgBySpread(uidLists, mobileList, smsContext, 3, 2);
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return pageList();
  }

  // 审核不通过
  public String qualificaitonsApplyNopass() throws SQLException {

    try {
      SysUser sysuser = (SysUser) session.get("sysUser");
      String applyType = "";
      if (qualificationName != null) {
        for (int i = 0; i < qualificationName.length; i++) {
          if (i < qualificationName.length - 1) {
            applyType = applyType + qualificationName[i] + "|";
            qualificationsApplyDO.setApplyType(applyType);
          } else {
            applyType = applyType + qualificationName[i];
            qualificationsApplyDO.setApplyType(applyType);
          }
        }
      }
      qualificationsApplyDO.setAuditingDate(new Date());
      qualificationsApplyDO.setAuditingId(sysuser.getUserId());
      qualificationsApplyDO.setStatus(new Short((short) 3));
      qualificaitonsApplyService.updateQualificaitonsApply(qualificationsApplyDO);
      accountInfo = accountInfoService.selectByPrimaryLoginInfo(qualificationsApplyDO.getUserId());
      if (accountInfo.getEmail() != null) {
        List emailLists = new ArrayList();
        List<Long> uidLists = new ArrayList<Long>();
        List loginNameList = new ArrayList();
        Integer uid = qualificationsApplyDO.getUserId();
        String uids = uid.toString(uid);
        Long uidss = Long.valueOf(uids).longValue();
        uidLists.add(uidss);
        emailLists.add(accountInfo.getEmail());
        loginNameList.add(accountInfo.getLoginAccount());
        String emailContext1 = "很遗憾，您提交的采购资格变更申请被拒绝了，具体原因请联系客服人员";
        String title = "采购资格审核通知";
        if (uidLists != null && emailLists != null && loginNameList != null) {
          Map<String, Object> sendEmailMap = emailSubscriptionRemoteService.emailBySpread(uidLists,
              emailLists, loginNameList, title, emailContext1, 2);
        }
        // 发送邮件
      }
      List mobileList = new ArrayList();
      List<Long> uidLists = new ArrayList<Long>();
      if (accountInfo.getMobile() != null) {
        // 发送手机短信


        mobileList.add(accountInfo.getMobile());
        Integer uid = qualificationsApplyDO.getUserId();
        String uids = uid.toString(uid);
        Long uidss = Long.valueOf(uids).longValue();
        uidLists.add(uidss);
        String smsContext = "	很遗憾，您提交的采购资格变更申请被拒绝了，具体原因请联系客服人员";
        // 发送短信
        Map<String, Object> sendMap =
            messageRemoteService.sendMsgBySpread(uidLists, mobileList, smsContext, 3, 2);
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


    return pageList();
  }

*/

  public static Logger getLogger() {
    return logger;
  }

  public static void setLogger(Logger logger) {
    QualificaitonsApplyAction.logger = logger;
  }


  public QualificaitonsApplyService getQualificaitonsApplyService() {
    return qualificaitonsApplyService;
  }



  public void setQualificaitonsApplyService(QualificaitonsApplyService qualificaitonsApplyService) {
    this.qualificaitonsApplyService = qualificaitonsApplyService;
  }



  @Override
public Page getPage() {
    return page;
  }


  @Override
public void setPage(Page page) {
    this.page = page;
  }



  public String[] getQualificationName() {
    return qualificationName;
  }



  public void setQualificationName(String[] qualificationName) {
    this.qualificationName = qualificationName;
  }



  public String getApplyTypes() {
    return applyTypes;
  }



  public void setApplyTypes(String applyTypes) {
    this.applyTypes = applyTypes;
  }



  public String getApplyTypes1() {
    return applyTypes1;
  }



  public void setApplyTypes1(String applyTypes1) {
    this.applyTypes1 = applyTypes1;
  }



  public String getApplyTypes2() {
    return applyTypes2;
  }



  public void setApplyTypes2(String applyTypes2) {
    this.applyTypes2 = applyTypes2;
  }



  public String getQualificaitonsFileImage() {
    return QualificaitonsFileImage;
  }



  public void setQualificaitonsFileImage(String qualificaitonsFileImage) {
    QualificaitonsFileImage = qualificaitonsFileImage;
  }



}
