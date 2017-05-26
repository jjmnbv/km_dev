package com.pltfm.app.service.impl;


import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.dao.BnesMessageCenterDAO;
import com.pltfm.app.dao.BnesMessageTempDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dao.PublishMessageDAO;
import com.pltfm.app.service.PublishMessageService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.BnesInfoPrompt;
import com.pltfm.app.vobject.BnesMessageCenter;
import com.pltfm.app.vobject.BnesMessageTemp;
import com.pltfm.app.vobject.BnesMessageTempQry;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.LoginInfoExample;


@Component(value = "publishMessageService")
public class PublishMessageServiceImpl implements PublishMessageService {
  @Resource(name = "publishMessageDao")
  private PublishMessageDAO publishMessageDao;
  // 用户
  @Resource(name = "loginInfoDAO")
  private LoginInfoDAO loginInfoDAO;
  // 消息中心
  @Resource(name = "bnesMessageCenterDAO")
  private BnesMessageCenterDAO bnesMessageCenterDAO;
  // 消息用户关系
  @Resource(name = "bnesMessageTempDAO")
  private BnesMessageTempDAO bnesMessageTempDAO;



  // 查询列表
  public List selectList(BnesInfoPrompt infoPrompt) throws SQLException {
    return publishMessageDao.selectList(infoPrompt);
  }

  // 发布消息

  @Transactional(rollbackFor = Exception.class)
  public void addMessage(BnesInfoPrompt infoPrompt) throws SQLException {
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

  // 修改未发布的消息
  public void updateMessage(BnesInfoPrompt infoPrompt, String loginIds) throws SQLException {
    publishMessageDao.updateMessage(infoPrompt);
    // 指定人有更新
    if (null != loginIds && !loginIds.equals("")) {
      BnesMessageTempQry qry = new BnesMessageTempQry();
      qry.setInfoPromptId(infoPrompt.getInfoPromptId());
      List list = bnesMessageTempDAO.selectByExample(qry);

      if (null != list && list.size() > 0) {
        BnesMessageTempQry BnesMessageTempQry = null;
        for (int i = 0; i < list.size(); i++) {
          BnesMessageTempQry = (BnesMessageTempQry) list.get(i);
          bnesMessageTempDAO.deleteByPrimaryKey(BnesMessageTempQry.getMessageTempId());
        }

      }
      BnesMessageCenter record = null;
      String[] args = loginIds.split(",");
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
      chageStatus(infoPrompt, loginIds);
    }

  }

  // 状态为已发布的情况
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

  // 删除单条
  public void deleteById(Integer infoPromptId) throws SQLException {

    BnesInfoPrompt infoPrompt = new BnesInfoPrompt();
    infoPrompt.setInfoPromptId(infoPromptId);
    publishMessageDao.delete(infoPrompt);


  }

  // 删除多条
  public void delete(List<Integer> infoPromptIds) throws SQLException {


    if (ListUtils.isNotEmpty(infoPromptIds)) {
      for (Integer id : infoPromptIds) {
        BnesInfoPrompt infoPrompt = new BnesInfoPrompt();
        infoPrompt.setInfoPromptId(id);
        publishMessageDao.delete(infoPrompt);
      }
    }

  }

  // 记录数
  public Integer selectListCount(BnesInfoPrompt infoPrompt) throws SQLException {
    return publishMessageDao.selectListCount(infoPrompt);
  }


  public BnesInfoPrompt queryPrompt(BnesInfoPrompt infoPrompt) throws SQLException {
    return publishMessageDao.queryPrompt(infoPrompt);
  }



  public PublishMessageDAO getPublishMessageDao() {
    return publishMessageDao;
  }

  public void setPublishMessageDao(PublishMessageDAO publishMessageDao) {
    this.publishMessageDao = publishMessageDao;
  }

  public LoginInfoDAO getLoginInfoDAO() {
    return loginInfoDAO;
  }

  public void setLoginInfoDAO(LoginInfoDAO loginInfoDAO) {
    this.loginInfoDAO = loginInfoDAO;
  }

  public BnesMessageCenterDAO getBnesMessageCenterDAO() {
    return bnesMessageCenterDAO;
  }

  public void setBnesMessageCenterDAO(BnesMessageCenterDAO bnesMessageCenterDAO) {
    this.bnesMessageCenterDAO = bnesMessageCenterDAO;
  }

  public BnesMessageTempDAO getBnesMessageTempDAO() {
    return bnesMessageTempDAO;
  }

  public void setBnesMessageTempDAO(BnesMessageTempDAO bnesMessageTempDAO) {
    this.bnesMessageTempDAO = bnesMessageTempDAO;
  }



}
