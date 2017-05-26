package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.BnesAnswerInfoDAO;
import com.pltfm.app.dao.BnesCustomerTypeDAO;
import com.pltfm.app.dao.BnesMessageCenterDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dao.PublishMessageDAO;
import com.pltfm.app.service.BnesMessageCenterService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.BnesInfoPrompt;
import com.pltfm.app.vobject.BnesMessageCenter;
import com.pltfm.app.vobject.LoginInfo;

/**
 * 
 * 
 * 消息Service
 * 
 */
@Component(value = "bnesMessageCenterService")
public class BnesMessageCenterServiceImpl implements BnesMessageCenterService {
  @Resource(name = "bnesMessageCenterDAO")
  private BnesMessageCenterDAO bnesMessageCenterDAO;
  @Resource(name = "loginInfoDAO")
  private LoginInfoDAO loginInfoDAO;
  // @Resource(name="bnesInfoPromptDAO")
  // private BnesInfoPromptDAO bnesInfoPromptDAO;
  @Resource(name = "publishMessageDao")
  private PublishMessageDAO publishMessageDAO;

  /**
   * 类别DAO
   */
  @Resource(name = "bnesCustomerTypeDAO")
  private BnesCustomerTypeDAO bnesCustomerTypeDAO;

  /**
   * 安全问题答案信息DAO接口
   */
  @Resource(name = "bnesAnswerInfoDAO")
  private BnesAnswerInfoDAO bnesAnswerInfoDAO;

  /**
   * 
   * 
   * 添加消息
   * 
   */
  @Override
public Integer insert(BnesMessageCenter record) throws SQLException {
    return bnesMessageCenterDAO.insert(record);
  }

  /**
   * 
   * 
   * 删除消息
   * 
   */
  @Override
public int delete(List<Integer> messageId) throws SQLException {

    Integer count = 0;
    if (ListUtils.isNotEmpty(messageId)) {
      for (Integer id : messageId) {
        BnesMessageCenter b = new BnesMessageCenter();
        b.setMessageId(id);
        count += bnesMessageCenterDAO.delete(b);
      }
    }
    return count;
  }

  /**
   * 添加消息
   */
  @Override
public void insertAll(int infoPromptId) throws SQLException {
    List list = null;
    BnesInfoPrompt infoPrompt = new BnesInfoPrompt();
    infoPrompt.setInfoPromptId(infoPromptId);
    BnesInfoPrompt bnesInfoPrompt = publishMessageDAO.queryPrompt(infoPrompt);
    if (bnesInfoPrompt.getCustomerType() != null) {
      list = loginInfoDAO.selectCustomerTypeId(bnesInfoPrompt.getCustomerType());
    } else {
      list = loginInfoDAO.getLoginAll();
    }
    for (int i = 0; i < list.size(); i++) {
      LoginInfo l = (LoginInfo) list.get(i);
      BnesMessageCenter b = new BnesMessageCenter();
      b.setCustomerTypeId(l.getN_CustomerTypeId());
      b.setStatus(1);
      b.setAccountId(l.getN_LoginId());
      b.setInfoPromptId(infoPromptId);
      bnesMessageCenterDAO.insert(b);
    }
  }

  /**
   * 
   * 
   * 修改消息
   * 
   */
  @Override
public int update(BnesMessageCenter record) throws SQLException {
    return bnesMessageCenterDAO.update(record);
  }

  /**
   * 分页查询消息信息
   * 
   * @param 消息信息实体
   * @return
   * @throws Exception 异常
   */
  public Page searchPageByVo(Page pageParam, BnesMessageCenter vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new BnesMessageCenter();
    }
    int totalNum = bnesMessageCenterDAO.selectCountByVo(vo);
    pageParam.setRecordCount(totalNum);
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());

    pageParam.setDataList(bnesMessageCenterDAO.selectPageByVo(vo));
    return pageParam;
  }

  /**
   * 跟据信息id查询消息信息
   * 
   * @param 消息信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public BnesMessageCenter getMessageId(Integer messageId) throws SQLException {
    return bnesMessageCenterDAO.getMessageId(messageId);
  }

  /**
   * 取得登录名
   * 
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public List getLoginAll() throws SQLException {
    return loginInfoDAO.getLoginAll();
  }

  /**
   * 跟据登录ID取得登录名
   * 
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public LoginInfo getLoginName(Integer n_LoginId) throws SQLException {
    return loginInfoDAO.selectByPrimaryKey(n_LoginId);
  }

  @Override
public List getBnesInfoPrompAll() throws SQLException {
    return publishMessageDAO.selectBnesInfoPromp();
  }

  public PublishMessageDAO getPublishMessageDAO() {
    return publishMessageDAO;
  }

  public void setPublishMessageDAO(PublishMessageDAO publishMessageDAO) {
    this.publishMessageDAO = publishMessageDAO;
  }

  public BnesCustomerTypeDAO getBnesCustomerTypeDAO() {
    return bnesCustomerTypeDAO;
  }

  public void setBnesCustomerTypeDAO(BnesCustomerTypeDAO bnesCustomerTypeDAO) {
    this.bnesCustomerTypeDAO = bnesCustomerTypeDAO;
  }

  public BnesMessageCenterDAO getBnesMessageCenterDAO() {
    return bnesMessageCenterDAO;
  }

  public void setBnesMessageCenterDAO(BnesMessageCenterDAO bnesMessageCenterDAO) {
    this.bnesMessageCenterDAO = bnesMessageCenterDAO;
  }

  public LoginInfoDAO getLoginInfoDAO() {
    return loginInfoDAO;
  }

  public void setLoginInfoDAO(LoginInfoDAO loginInfoDAO) {
    this.loginInfoDAO = loginInfoDAO;
  }
}
