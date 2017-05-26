package com.kmzyc.b2b.webservice;

import java.util.List;

/**
 * 总部和电商会员关系系统对接 第一期,提供对外的webservice调用
 * 
 * @author maliqun 20141030
 * 
 */
public class WebServiceUtil {

  /**
   * 
   * @param endPoint
   * @param nameSpace
   * @param methodName
   * @param paraContent
   * @param paraName 动态参数
   * @return 1为失败,2为成功
   */
  public static List<WsReturnResult> sendWebService(String endPoint, String nameSpace,
      String methodName, Object paraContent, String... paraName) {
    return null;
  }

}
