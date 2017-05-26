package com.kmzyc.express.vobject;

import java.util.HashMap;

import com.kmzyc.express.util.JacksonHelper;

// { "company":"yuantong",//订阅的快递公司的编码，一律用小写字母，见章五《快递公司编码》
// "number":"12345678",//订阅的快递单号，单号的最大长度是32个字符
// "from":"广东省深圳市南山区",//出发地城市
// "to":"北京市朝阳区",//目的地城市，到达目的地后会加大监控频率
// "key":"*********",//授权码，签订合同后发放
// "parameters":{
// "callbackurl":"http://www.您的域名.com/kuaidi?callbackid=...",//回调地址
// "salt":"any string", //签名用随机字符串（可选）
// "resultv2":"1" //添加此字段表示开通行政区域解析功能（仅对开通签收状态服务用户有效），见章3.1《推送请求》
// }
// }

/**
 * 快递订阅请求信息
 * 
 * @author hekai
 * @since JDK1.6
 * @history 2015-12-15 hekai create
 */
public class ExpressSubRequestVO {
  // 快递公司编码
  private String company;
  // 快递单号
  private String number;
  // 出发城市
  private String from;
  // 目的城市
  private String to;
  // 授权码(合同后索取)
  private String key;

  // 其他参数，包括回调地址，签名等信息
  private HashMap<String, String> parameters = new HashMap<String, String>();

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public HashMap<String, String> getParameters() {
    return parameters;
  }

  public void setParameters(HashMap<String, String> parameters) {
    this.parameters = parameters;
  }

  // 返回JSON字符串
  public String toString() {
    return JacksonHelper.toJSON(this);
  }

}
