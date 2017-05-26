package com.kmzyc.b2b.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.SiteNoticeDetail;
import com.kmzyc.b2b.service.MemberSiteNoticeService;
import com.kmzyc.framework.constants.Constants;
import com.km.framework.page.Pagination;

/**
 * 站内通知ACTION
 * 
 * @author luoyi update by lijianjun
 */
@Controller(value = "siteNoticeAction")
@Scope(value = "prototype")
public class SiteNoticeAction extends BaseAction {
  private static final long serialVersionUID = 372615613797097639L;

  // private static Logger logger = Logger.getLogger(SiteNoticeAction.class);
  private static Logger logger = LoggerFactory.getLogger(SiteNoticeAction.class);

  @Resource(name = "memberSiteNoticeService")
  private MemberSiteNoticeService memberSiteNoticeService;

  private SiteNoticeDetail siteNoticeDetail;

  private Integer messageId;// 消息ID

  /**
   * 到站内通知列表
   * 
   * @return
   * @throws Exception
   */
  public String toSiteNotice() {
    try {
      // 获取缓存loginId
      HttpServletRequest request = ServletActionContext.getRequest();
      Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
      logger.info("查询站内通知列表，参数：userID：" + userId);
      // 每页显示10条。
      Pagination page = this.getPagination(5, 10);
      // sql查询条件对象
      Map<String, Object> newConditon = new HashMap<String, Object>();
      newConditon.put("userId", userId);
      // 设置查询条件
      page.setObjCondition(newConditon);
      // 根据用户ID，查询他自己所有的信息列表
      this.pagintion = memberSiteNoticeService.findSiteNoticeByUserId(page);
    } catch (SQLException e) {
      logger.error("查询用户站内通知列表出错," + e.getMessage());
      return ERROR;
    } catch (Exception e) {
      logger.error("查询用户站内通知列表出错," + e.getMessage());
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 阅读站内通知
   * 
   * @return
   */
  public String readerSiteNotice() {
    // 获取缓存用户id
    HttpSession session = getSession();
    Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
    logger.info("查看站内通知，参数：userID：" + userId + ",messageId：" + messageId);
    try {
      // 查询站内通知详情，根据消息ID
      siteNoticeDetail = memberSiteNoticeService.findSiteNoticeDetail(messageId);
      if (userId.compareTo(siteNoticeDetail.getAccountId().longValue()) != 0) {
        logger.error("非当前用户，currentUserID：" + userId);
        return ERROR;
      }
    } catch (Exception e) {
      logger.error("阅读站内通知action出错," + e.getMessage());
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 删除站内通知消息
   * 
   * @return
   */
  public String deleteSiteNotice() {
    try {
      // 获取缓存登陆Id
      HttpServletRequest request = ServletActionContext.getRequest();
      Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
      logger.info("删除站内通知，参数：userID：" + userId + ",messageId：" + messageId);
      // new删除传入参数集合
      Map<String, Object> messageIdAndUserIdMap = new HashMap<String, Object>();
      messageIdAndUserIdMap.put("userId", userId);
      messageIdAndUserIdMap.put("messageId", messageId);
      // 删除站内通知
      int rows = memberSiteNoticeService.deleteSiteNoticeByMessageId(messageIdAndUserIdMap);
      // 重新查询
      /*
       * toSiteNotice(); if (rows == 0) {// 删除失败 return ERROR; }
       */
      getResponse().getWriter().print(rows);
    } catch (Exception e) {
      logger.error("删除站内通知的action失败" + e.getMessage());
    }
    return null;
  }

  /**
   * 以下为set和get方法
   */
  public Integer getMessageId() {
    return messageId;
  }

  public void setMessageId(Integer messageId) {
    this.messageId = messageId;
  }

  public SiteNoticeDetail getSiteNoticeDetail() {
    return siteNoticeDetail;
  }

  public void setSiteNoticeDetail(SiteNoticeDetail siteNoticeDetail) {
    this.siteNoticeDetail = siteNoticeDetail;
  }

}
