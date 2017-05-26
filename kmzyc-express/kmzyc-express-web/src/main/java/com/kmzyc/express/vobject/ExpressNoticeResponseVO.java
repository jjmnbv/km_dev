package com.kmzyc.express.vobject;

import com.kmzyc.express.util.JacksonHelper;


// {
// "result":"true",
// "returnCode":"200",
// "message":"成功"
// }


/**
 * 快递100推送的响应信息
 * 
 * @author Administrator
 * 
 */
public class ExpressNoticeResponseVO {
  // 响应结果 true表示推送成功，false表示推送失败
  private Boolean result;
  // 响应状态码
  private String returnCode;
  // 响应消息
  private String message;

  public Boolean getResult() {
    return result;
  }

  public void setResult(Boolean result) {
    this.result = result;
  }

  public String getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(String returnCode) {
    this.returnCode = returnCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String toJsonString() {
    return JacksonHelper.toJSON(this);
  }
}
