package com.kmzyc.b2b.app;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.service.MemberComplaintService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.pltfm.app.vobject.BnesAcctAppealInfo;

@SuppressWarnings("unchecked")
@Scope("prototype")
@Controller("appComplaintAction")
public class AppComplaintAction extends AppBaseAction {

  /**
     *
     */
  private static final long serialVersionUID = 757774110654828999L;
  // private static Logger logger = Logger.getLogger(AppComplaintAction.class);
  private static Logger logger = LoggerFactory.getLogger(AppComplaintAction.class);

  private ReturnResult<Map<String, Object>> returnResult;

  public ReturnResult<Map<String, Object>> getReturnResult() {
    return returnResult;
  }

  public void setReturnResult(ReturnResult<Map<String, Object>> returnResult) {
    this.returnResult = returnResult;
  }

  @Resource(name = "memberComplaintService")
  private MemberComplaintService memberComplaintService;

  /**
   * 提交投诉
   * 
   * @return
   */
  public void submitIdeas() {
    JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
    BnesAcctAppealInfo bnesAcctAppealInfo = new BnesAcctAppealInfo();
    if (null != jsonParams && !jsonParams.isEmpty()) {
      try {
        bnesAcctAppealInfo.setnLoginId(jsonParams.getInteger("uid"));
        bnesAcctAppealInfo.setAppealTitle(jsonParams.getString("appealTitle"));
        bnesAcctAppealInfo.setType(jsonParams.getInteger("type"));// 反馈类型
        bnesAcctAppealInfo.setAppealContent(jsonParams.getString("appealContent"));// 反馈内容APPEAL_CONTENT
        bnesAcctAppealInfo.setMobile(jsonParams.getString("mobile"));// 用户联系方式MOBILE
        memberComplaintService.saveComplaintsByUser(bnesAcctAppealInfo);
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", null);
      } catch (Exception e) {
        logger.error("提交客户投诉失败：" + e.getMessage(), e);
        returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
      }

    } else {
      logger.error("获取jsonParams为null");
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    printJsonString(returnResult);
  }

  /**
   * 获取投诉类型信息 由于目前系统中投诉类型是写死的，这里就直接组装一个Map返回给手机端
   * 
   * @return
   */
  public void getReplyType() {
    Map<String, String> map = new HashMap<String, String>();
    try {
      map.put("1", "产品相关");
      map.put("2", "价格相关");
      map.put("3", "物流相关");
      map.put("4", "服务相关");
      map.put("5", "其他问题");
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", map);
    } catch (Exception e) {
      logger.error("提交客户投诉失败：" + e.getMessage(), e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    printJsonString(returnResult);
  }
}
