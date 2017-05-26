package com.kmzyc.express.vobject;

import com.kmzyc.express.util.JacksonHelper;



// {
// "status":"polling",
// /*监控状态:polling:监控中，shutdown:结束，abort:中止，updateall：重新推送。其中当快递单为已签收时status=shutdown，当message为“3天查询无记录”或“60天无变化时”status=
// abort ，对于stuatus=abort的状度，需要增加额外的处理逻辑，详见本节最后的说明 */
// "billstatus":"got", /*包括got、sending、check三个状态，由于意义不大，已弃用，请忽略*/
// "message":"", /*监控状态相关消息，如:3天查询无记录，60天无变化*/
//
// "lastResult":{ /*最新查询结果，全量，倒序（即时间最新的在最前）*/
// "message":"ok",/*消息体，请忽略*/
// "state":"0",/*快递单当前签收状态，包括0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单等7个状态，其中4-7需要另外开通才有效，详见章3.3 */
// "status":"200",/*通讯状态，请忽略*/
// "condition":"F00", /*快递单明细状态标记，暂未实现，请忽略*/
// "ischeck":"0", /*是否签收标记，明细状态请参考state字段*/
// "com":"yuantong", /*快递公司编码,一律用小写字母，见章五《快递公司编码》*/
// "nu":"V030344422", /*单号*/
// "data":[
// {
// "context":"上海分拨中心/装件入车扫描 ",/*内容*/
// "time":"2012-08-28 16:33:19", /*时间，原始格式*/
// "ftime":"2012-08-28 16:33:19", /*格式化后时间*/
// "status":"在途", /*本数据元对应的签收状态。只有在开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现*/
// "areaCode":"310000000000",/*本数据元对应的行政区域的编码，只有在开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现*/
// "areaName":"上海市", /*本数据元对应的行政区域的名称，开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现*/
//
// },{
// "context":"上海分拨中心/下车扫描 ", /*内容*/
// "time":"2012-08-27 23:22:42", /*时间，原始格式*/
// "ftime":"2012-08-27 23:22:42", /*格式化后时间*/
// "status":"在途", /*本数据元对应的签收状态。只有在开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现*/
// "areaCode":"310000000000",/*本数据元对应的行政区域的编码，只有在开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现*/
// "areaName":"上海市",/*本数据元对应的行政区域的名称，开通签收状态服务（见上面"status"后后的说明）且在订阅接口中提交resultv2标记后才会出现*/
// }
// ]
// }
// }

/**
 * 快递100推送的订阅监控请求信息
 * 
 * @author hekai
 * @since JDK1.6
 * @history 2015-12-15 hekai create
 */
public class ExpressNoticeRequestVO {
  // 监控状态
  private String status = "";
  // 快递单据状态(暂无用)
  private String billstatus = "";
  // 监控消息
  private String message = "";
  // 最近的监控结果
  private NoticeResultVO lastResult = new NoticeResultVO();

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getBillstatus() {
    return billstatus;
  }

  public void setBillstatus(String billstatus) {
    this.billstatus = billstatus;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public NoticeResultVO getLastResult() {
    return lastResult;
  }

  public void setLastResult(NoticeResultVO lastResult) {
    this.lastResult = lastResult;
  }

  public String toJsonString() {
    return JacksonHelper.toJSON(this);
  }

  public static void main(String[] args) {
  // ExpressNoticeRequestVO req = new ExpressNoticeRequestVO();
  // req.setBillstatus("polling");
  // req.setMessage("到达");
  // req.setStatus("check");
  // req.getLastResult().setCom("yauntong");
  // req.getLastResult().setCondition("F00");
  // req.getLastResult().setIscheck("0");
  // req.getLastResult().setNu("V030344422");
  // req.getLastResult().setState("0");
  // req.getLastResult().setStatus("200");
  // req.getLastResult().setMessage("ok");
  // NoticeResultItemVO item = new NoticeResultItemVO();
  // item.setContext("上海分拨中心/装件入车扫描 ");
  // item.setFtime("2012-08-28 16:33:19");
  // item.setTime("2012-08-28 16:33:19");
  // req.getLastResult().getData().add(item);
  // item = new NoticeResultItemVO();
  // item.setContext("上海分拨中心/下车扫描");
  // item.setFtime("2012-08-27 23:22:42");
  // item.setTime("2012-08-27 23:22:42");
  // req.getLastResult().getData().add(item);
  // // 转JSON字符串
  // System.out.println(JacksonHelper.toJSON(req));
  // // 转对象
  // ExpressNoticeRequestVO req2 =
  // JacksonHelper.fromJSON(JacksonHelper.toJSON(req), ExpressNoticeRequestVO.class);
  // System.out.println(req2.toString());
  }
}
