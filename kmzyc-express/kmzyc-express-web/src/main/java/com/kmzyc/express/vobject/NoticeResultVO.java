package com.kmzyc.express.vobject;

import java.util.ArrayList;

import com.kmzyc.express.util.JacksonHelper;



// "lastResult":{ /*最新查询结果，全量，倒序（即时间最新的在最前）*/
// "message":"ok",/*消息体，请忽略*/
// "state":"0",/*快递单当前签收状态，包括0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单等7个状态，其中4-7需要另外开通才有效，详见章3.3 */
// "status":"200",/*通讯状态，请忽略*/
// "condition":"F00", /*快递单明细状态标记，暂未实现，请忽略*/
// "ischeck":"0", /*是否签收标记，明细状态请参考state字段*/
// "com":"yuantong", /*快递公司编码,一律用小写字母，见章五《快递公司编码》*/
// "nu":"V030344422", /*单号*/
// "data":[

/**
 * 快递100推送的订单物流信息
 * 
 * @author Administrator
 * 
 */
public class NoticeResultVO {
  // 通讯状态
  private String status = "0";
  // 快递跟踪状态
  private String state = "0";
  // 监控消息
  private String message = "";
  // 快递单明细状态标记
  private String condition = "";
  // 是否签收标记
  private String ischeck = "0";
  // 快递单号
  private String nu = "";
  // 快递公司编码
  private String com = "";
  // 快递跟踪明细
  private ArrayList<NoticeResultItemVO> data = new ArrayList<NoticeResultItemVO>();

  @SuppressWarnings("unchecked")
  public NoticeResultVO clone() {
    NoticeResultVO r = new NoticeResultVO();
    r.setCom(this.getCom());
    r.setIscheck(this.getIscheck());
    r.setMessage(this.getMessage());
    r.setNu(this.getNu());
    r.setState(this.getState());
    r.setStatus(this.getStatus());
    r.setCondition(this.getCondition());
    r.setData((ArrayList<NoticeResultItemVO>) this.getData().clone());

    return r;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getNu() {
    return nu;
  }

  public void setNu(String nu) {
    this.nu = nu;
  }

  public String getCom() {
    return com;
  }

  public void setCom(String com) {
    this.com = com;
  }

  public ArrayList<NoticeResultItemVO> getData() {
    return data;
  }

  public void setData(ArrayList<NoticeResultItemVO> data) {
    this.data = data;
  }

  public String getIscheck() {
    return ischeck;
  }

  public void setIscheck(String ischeck) {
    this.ischeck = ischeck;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  @Override
  public String toString() {
    return JacksonHelper.toJSON(this);
  }
}
