package com.kmzyc.express.vobject;

import com.kmzyc.express.util.JacksonHelper;


/**
 * 快递订阅响应信息
 * 
 * @author hekai
 * @since JDK1.6
 * @history 2015-12-15 hekai create
 */
public class ExpressSubResponseVO {

  // 订阅结果
  private Boolean result;
  // 订阅返回的服务状态码
  private String returnCode;
  // 订阅返回的消息
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
