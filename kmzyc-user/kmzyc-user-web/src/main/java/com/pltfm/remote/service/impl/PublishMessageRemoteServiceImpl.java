package com.pltfm.remote.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.user.remote.service.PublishMessageRemoteService;
import com.pltfm.app.dao.BnesMessageCenterDAO;
import com.pltfm.app.dao.BnesMessageTempDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dao.PublishMessageDAO;
import com.pltfm.app.util.Constants;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.vobject.BnesInfoPrompt;
import com.pltfm.app.vobject.BnesMessageCenter;
import com.pltfm.app.vobject.BnesMessageTemp;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.LoginInfoExample;

@Service(value = "publishMessageRemoteService")
public class PublishMessageRemoteServiceImpl implements PublishMessageRemoteService {
  // 消息中心
  @Resource(name = "bnesMessageCenterDAO")
  private BnesMessageCenterDAO bnesMessageCenterDAO;
  // 消息用户关系
  @Resource(name = "bnesMessageTempDAO")
  private BnesMessageTempDAO bnesMessageTempDAO;
  @Resource(name = "publishMessageDao")
  private PublishMessageDAO publishMessageDao;
  // 用户
  @Resource(name = "loginInfoDAO")
  private LoginInfoDAO loginInfoDAO;


  // 新建站内通知
  public void addMessage(BnesInfoPrompt infoPrompt) throws Exception {
    infoPrompt.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
    infoPrompt.setReleaseDate(DateTimeUtils.getCalendarInstance().getTime());
    infoPrompt.setCreateId(Constants.SYS_LOGIN_ID);// 系统23
    publishMessageDao.addMessage(infoPrompt);
    // 指定个人的情况
    String loginId = infoPrompt.getLoginId();
    infoPrompt.setLoginId("");
    if (null != loginId && !loginId.equals("")) {
      String[] args = loginId.split(",");
      for (int i = 0; i < args.length; i++) {
        BnesMessageTemp bnesMessageTemp = new BnesMessageTemp();
        bnesMessageTemp.setAccountId(Integer.valueOf(args[i]));
        bnesMessageTemp.setCreatedId(infoPrompt.getCreateId());
        bnesMessageTemp.setCreateDate(new Date());
        bnesMessageTemp.setInfoPromptId(infoPrompt.getInfoPromptId());
        bnesMessageTempDAO.insert(bnesMessageTemp);
      }
    }
    // 状态为已发布的情况
    if (infoPrompt.getStatus() == 1) {
      chageStatus(infoPrompt, loginId);
    }
  }

  // 状态为已发布的情况
  @SuppressWarnings("rawtypes")
  public void chageStatus(BnesInfoPrompt infoPrompt, String loginId) throws SQLException {
    BnesMessageCenter record = null;
    Integer customerType = infoPrompt.getCustomerType();
    // Integer
    Integer releaseObject = infoPrompt.getReleaseObject();
    // 1.指定个人的
    if (null != releaseObject && releaseObject == 2 && null != loginId && !loginId.equals("")) {
      String[] args = loginId.split(",");
      // String[] loginIds=args[0].split(",");
      for (int i = 0; i < args.length; i++) {
        record = new BnesMessageCenter();
        record.setAccountId(Integer.valueOf(args[i]));
        record.setInfoPromptId(infoPrompt.getInfoPromptId());
        // 未读
        record.setStatus(0);
        bnesMessageCenterDAO.insert(record);
      }
      // 2.指定客户类别的
    } else if (null != releaseObject && releaseObject == 1 && null != customerType
        && customerType != 0) {
      LoginInfoExample example = new LoginInfoExample();
      example.createCriteria().andn_CustomerTypeIdEqualTo(customerType);
      List list = loginInfoDAO.selectByExample(example);
      for (int i = 0; i < list.size(); i++) {
        record = new BnesMessageCenter();
        LoginInfo loginInfo = (LoginInfo) list.get(i);
        record.setAccountId(loginInfo.getN_LoginId());
        record.setInfoPromptId(infoPrompt.getInfoPromptId());
        // 未读
        record.setStatus(0);
        bnesMessageCenterDAO.insert(record);
      }
      // 3.所有人员
    } else {
      List list = loginInfoDAO.getLoginAll();
      for (int i = 0; i < list.size(); i++) {
        record = new BnesMessageCenter();
        LoginInfo loginInfo = (LoginInfo) list.get(i);
        record.setAccountId(loginInfo.getN_LoginId());
        record.setInfoPromptId(infoPrompt.getInfoPromptId());
        // 未读
        record.setStatus(0);
        bnesMessageCenterDAO.insert(record);
      }
    }
  }
}
