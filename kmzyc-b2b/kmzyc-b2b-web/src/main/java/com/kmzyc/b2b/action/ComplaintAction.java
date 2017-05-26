package com.kmzyc.b2b.action;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.MemberComplaintService;
import com.kmzyc.b2b.service.SecurityCentreService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.sensitive.SensitiveType;
import com.kmzyc.framework.sensitive.SensitiveWordFilter;
import com.pltfm.app.vobject.BnesAcctAppealInfo;

/**
 * 建议投诉ACTION
 * 
 * @author luoyi update bu lijianjun
 * @createDate 2013/10/10
 */
@Controller(value = "complaintAction")
@Scope(value = "prototype")
@SuppressWarnings({"rawtypes", "unchecked"})
public class ComplaintAction extends BaseAction {
  private static final long serialVersionUID = -2019246333918038723L;

  // private static Logger logger = Logger.getLogger(ComplaintAction.class);
  private static Logger logger = LoggerFactory.getLogger(ComplaintAction.class);

  // 建议投诉
  public BnesAcctAppealInfo bnesAcctAppealInfo = new BnesAcctAppealInfo();

  @Resource(name = "memberComplaintService")
  private MemberComplaintService memberComplaintService;

  @Resource(name = "sensitiveWordFilter")
  private SensitiveWordFilter sensitiveWordFilter;

  @SuppressWarnings("unused")
  @Resource(name = "securityCentreServiceImpl")
  private SecurityCentreService securityCentreService;

  private User user;
  // 是否为保存成功,-1＝到建议和投诉页面,0＝保存失败,1=保存成功
  private int saveOks;
  // 返回至页面的对象
  private ReturnResult returnResult;

  /**
   * 到建议和投诉页面
   * 
   * @return
   */
  public String toComplaint() {
    /*
     * HttpServletRequest request = ServletActionContext.getRequest(); Long userId = (Long)
     * request.getSession().getAttribute(Constants.SESSION_USER_ID); //user =
     * securityCentreService.getUserByLoginId(userId);
     */
    saveOks = -1;
    return SUCCESS;
  }

  /**
   * 添加建议投诉
   * 
   * @return
   * @throws ClassNotFoundException
   * @throws MalformedURLException
   */
  public String addComplaint() {
    HttpServletRequest request = ServletActionContext.getRequest();
    Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
    logger.info("新增建议投诉，参数：userID：" + userId);
    if (userId == null) {
      returnResult = new ReturnResult(InterfaceResultCode.NOT_LOGIN, "未登录！", null);
    } else {
      try {
        if (userId.compareTo(user.getLoginId()) != 0) {
          returnResult = new ReturnResult(InterfaceResultCode.FAILED, "非当前用户！", null);
        } else {
          bnesAcctAppealInfo.setAccountId(userId.intValue());// 用户ID
          bnesAcctAppealInfo.setCreateDate(new Date());// 创建日期
          bnesAcctAppealInfo.setnLoginId(userId.intValue());// 创建人
          memberComplaintService.saveComplaintsByUser(bnesAcctAppealInfo);
          saveOks = 1;// 保存成功
          logger.info("添加建议投诉记录成功!");
          returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "提交建议投诉成功", null);
        }
      } catch (SQLException e) {
        saveOks = 0;// 保存失败
        logger.error("添加建议投诉记录出错：" + e.getMessage(), e);
        returnResult = new ReturnResult(InterfaceResultCode.FAILED, "提交建议投诉失败", null);
      } catch (Exception e) {
        saveOks = 0;// 保存失败
        logger.error("添加建议投诉记录出错：" + e.getMessage(), e);
        returnResult = new ReturnResult(InterfaceResultCode.FAILED, "提交建议投诉失败", null);
      }
    }
    return SUCCESS;
  }

  /**
   * ajax校验传过来的内容是否包含敏感词汇
   * 
   * @return
   * @throws Exception datamap中的0,1,2主要是用于建议投诉checkContentRequest返回的json中进行判断
   */
  public String checkComplaintContent() throws Exception {
    logger.info("ajax调用验证敏感词汇");
    Map<String, Object> datamap = new HashMap<String, Object>();
    boolean result = false;
    // 评论主题较验
    if (StringUtils.isNotBlank(bnesAcctAppealInfo.getAppealTitle())) {
      result =
          sensitiveWordFilter.doFilt(bnesAcctAppealInfo.getAppealTitle(), SensitiveType.commit);
      if (result) {
        datamap.put("resultMessage", 0);
        returnResult = new ReturnResult(InterfaceResultCode.FAILED, "验证标题失败", datamap);
      }
    }
    // 评论相关订单较验
    if (!result && StringUtils.isNotBlank(bnesAcctAppealInfo.getOrder_Desc())) {
      result = sensitiveWordFilter.doFilt(bnesAcctAppealInfo.getOrder_Desc(), SensitiveType.commit);
      if (result) {
        datamap.put("resultMessage", 1);
        returnResult = new ReturnResult(InterfaceResultCode.FAILED, "验证相关订单号失败", datamap);
      }
    }
    // 评论内容较验
    if (!result && StringUtils.isNotBlank(bnesAcctAppealInfo.getAppealContent())) {
      result =
          sensitiveWordFilter.doFilt(bnesAcctAppealInfo.getAppealContent(), SensitiveType.commit);
      if (result) {
        datamap.put("resultMessage", 2);
        returnResult = new ReturnResult(InterfaceResultCode.FAILED, "验证内容失败", datamap);
      }
    }

    if (!result) {
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "验证通过", null);
    }
    return SUCCESS;
  }

  /**
   * 以下为set和get方法
   */
  public BnesAcctAppealInfo getBnesAcctAppealInfo() {
    return bnesAcctAppealInfo;
  }

  public void setBnesAcctAppealInfo(BnesAcctAppealInfo bnesAcctAppealInfo) {
    this.bnesAcctAppealInfo = bnesAcctAppealInfo;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getSaveOks() {
    return saveOks;
  }

  public void setSaveOks(int saveOks) {
    this.saveOks = saveOks;
  }

  public ReturnResult getReturnResult() {
    return returnResult;
  }

  public void setReturnResult(ReturnResult returnResult) {
    this.returnResult = returnResult;
  }

}
