package com.kmzyc.b2b.webservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.kmzyc.b2b.service.impl.AccountInfoServiceImp;
import com.kmzyc.b2b.util.wxpay.MD5Util;

/**
 * 电商和总部会员关系对接第一期 webservice调用返回结果
 * 
 * @author maliqun 20141030
 * 
 */
public class WsReturnResult {
  //private static final Logger logger = Logger.getLogger(WsReturnResult.class);
  private static Logger logger = LoggerFactory.getLogger(WsReturnResult.class);
  // 编号
  private String billNum;
  // 处理状态 1:成功 2:失败
  private String returnCode;
  // 信息说明
  private String messageContent;

  public static List<WsReturnResult> readerReturnXmlStr(String xmlStr) {
    Document document = null;
    try {
      // 转化为dom格式
      document = DocumentHelper.parseText(xmlStr);

      // 获得根节点
      Element rootElement = document.getRootElement();

      // 获得根节点下的子节点row
      Iterator iterForRow = rootElement.elementIterator();

      List<WsReturnResult> resultList = new ArrayList<WsReturnResult>();

      // 遍历根节点下的子节点
      while (iterForRow.hasNext()) {
        Element childElement = (Element) iterForRow.next();

        WsReturnResult result = new WsReturnResult();
        result.setBillNum(childElement.elementTextTrim("BILLNUM"));
        result.setReturnCode(childElement.elementTextTrim("RETURNCODE"));
        result.setMessageContent(childElement.elementTextTrim("MESSAGECONTENT"));
        resultList.add(result);
      }
      return resultList;
    } catch (DocumentException e) {
      logger.error(e.getMessage(),e);
      return null;
    }
  }



  public static String returnXmlStr(String cardId, String mobile) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");// 时间 格式
    String sDate = sdf.format(new Date());
    String sign = sDate + "KANGMEI_#@￥&@_@!!_2015";
    // (MD5Util.MD5(sign))

    sign = (MD5Util.MD5(sign, "utf-8")).toLowerCase();

    // sign = (MD5Util.MD5(sign)).toLowerCase();
    String xml = "<?xml version='1.0' encoding='UTF-8'?>" + "<IMessage>" + // <!--消息头可以不填-->
        "<IMessageHeade></IMessageHeade>" + // <!--消息头基本信息-->
        "<MessageBody>" + // <!--消息内容主体-->
        "<MARK>" + // <!--秘钥主体-->
        "<sDate>" + sDate + "</sDate>" + // <!--时间戳-->
        "<sign>" + sign + "</sign>" + // <!--秘钥-->
        "</MARK>" + "<DATA>" + // <!--数据行（多行同时传输）-->
        "<cardId>" + cardId + "</cardId>" + // <!--卡号/用户名-->
        "<mobile>" + mobile + "</mobile>" + // <!--手机号码/联系电话/手机-->
        "</DATA>" + "</MessageBody>" + "</IMessage>";
    logger.info("查询总部是否有此用户,xml内容：" + xml);
    return xml;
  }

  public String getBillNum() {
    return billNum;
  }

  public void setBillNum(String billNum) {
    this.billNum = billNum;
  }

  public String getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(String returnCode) {
    this.returnCode = returnCode;
  }

  public String getMessageContent() {
    return messageContent;
  }

  public void setMessageContent(String messageContent) {
    this.messageContent = messageContent;
  }

}
