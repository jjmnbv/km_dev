package com.kmzyc.express.vobject;

// {
// "context":"上海分拨中心/装件入车扫描 ",/*内容*/
// "time":"2012-08-28 16:33:19", /*时间，原始格式*/
// "ftime":"2012-08-28 16:33:19", /*格式化后时间*/
// "status":"在途", /*本数据元对应的签收状态。只有在开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现*/
// "areaCode":"310000000000",/*本数据元对应的行政区域的编码，只有在开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现*/
// "areaName":"上海市", /*本数据元对应的行政区域的名称，开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现*/
//
// }


/**
 * 快递100推送的物流跟踪明细
 * 
 * @author hekai
 * @since JDK1.6
 * @history 2015-12-15 hekai create
 */
public class NoticeResultItemVO {
  // 原始时间
  String time;
  // 物流跟踪消息
  String context;
  // 格式化时间
  String ftime;

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  public String getFtime() {
    return ftime;
  }

  public void setFtime(String ftime) {
    this.ftime = ftime;
  }

  public String toString() {
    return "{time=" + this.time + ",context=" + this.context + ",ftime=" + this.ftime + "}";
  }
}
