package com.kmzyc.b2b.third.action;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.third.model.wxCard.WxConsumeCodeRecord;
import com.kmzyc.b2b.third.model.wxCard.WxKmrsShopInfo;
import com.kmzyc.b2b.third.model.wxCard.WxReservation;
import com.kmzyc.b2b.third.service.WxCardManageService;
import com.kmzyc.b2b.third.util.BaseUtil;
import com.kmzyc.b2b.third.util.WxHttpUtil;
import com.km.framework.action.BaseAction;

/**
 * 处理春节微信摇一摇 微信线下核销以及线上预约功能 20150126
 * 
 * @author maliqun
 * 
 */
@Controller("WxCardCodeManageAction")
@Scope("prototype")
public class WxCardCodeManageAction extends BaseAction {


  /**
	 * 
	 */
  private static final long serialVersionUID = 2335912050239068634L;
  private static Logger log = LoggerFactory.getLogger(WxCardCodeManageAction.class);

  @Resource(name = "wxCardManageService")
  private WxCardManageService cardManageService;

  /**
   * 店铺验证码
   */
  private String shopVerifyCode;

  /**
   * 错误码
   */
  private String errorCode;
  /**
   * 错误
   */
  private String errorMsg;

  /**
   * 微信方传入的参数请求
   */
  private String encyptCode;
  private String signature;
  private String cardId;

  /**
   * 是否重复提交 如果为1,则提示用户
   */
  private String isRepeatSubmit;

  /**
   * 礼品券的ID,当时没有考虑到这个需求就已经提交卡券,于是只能写死判断 pFXg-t-cfhlMHKsWY0WmRj_RzMXM 正式券的card_id
   * pFXg-txTj-8IJU6WtAF9yfXu-0Y0
   */
  private String giftCardId = "pFXg-txTj-8IJU6WtAF9yfXu-0Y0";

  /**
   * 预约信息参数实体
   */
  private WxReservation reservation;

  /**
   * 预留参数,表示卡券类型,如gift,cash
   */
  private String cardType;

  /**
   * 处理卡券核销请求
   * 
   * @return
   */
  public String wxCardConsumeCode() {

    // 首先判断请求是否来自微信(是需要使用签名方式做判断的)
    if (encyptCode == null || cardId == null || signature == null) {
      errorCode = "-3";
      errorMsg = "this request is not come from weixin!";
      log.error("卡券核销~ this request is not come from weixin!");
      return "error";
    }

    // 将店铺验证码放到数据库当中查,若是没有,则提示用户
    try {
      boolean flag = cardManageService.queryShopVerifyCode(shopVerifyCode.trim());
      if (!flag) {
        errorCode = "-2";
        errorMsg = "this shopVerifyCode is not exist!";
        log.error("卡券核销~ this shopVerifyCode is not exist!");
        return "error";
      }
    } catch (SQLException e1) {
      errorCode = "-1";
      errorMsg = "query shopVerifyCode occured exception!";
      log.error("卡券核销~ query shopVerifyCode occured exception!" + e1.getMessage(), e1);
      return "error";
    }

    // 查完之后调用解码接口
    String sendUrl =
        "https://api.weixin.qq.com/card/code/decrypt?access_token=" + BaseUtil.getToken();
    String strData = "{'encrypt_code': '" + encyptCode + "'}";
    JSONObject jsonData =
        WxHttpUtil.httpRequest(sendUrl, "POST", JSONObject.fromObject(strData).toString());

    // System.out.println("返回的解码结果="+jsonData);

    String errCode = jsonData.getString("errcode");

    if (!"0".equals(errCode)) {
      errorCode = errCode;
      errorMsg = jsonData.getString("errmsg");
      log.error("微信解码接口发生异常~!" + jsonData.getString("errmsg"));
      return "error";
    }

    String code = jsonData.getString("code");

    // 如果是重复提交,则提示其是重复提交
    try {
      Integer count = cardManageService.queryConsumeByCardAndCode(cardId, code);
      if (count != null && count > 0) {
        isRepeatSubmit = "1";
        return "success";
      }

      // 调用核销接口
      sendUrl = "https://api.weixin.qq.com/card/code/consume?access_token=" + BaseUtil.getToken();
      strData = "{'card_id': '" + cardId + "','code':'" + code + "'}";
      jsonData = WxHttpUtil.httpRequest(sendUrl, "POST", JSONObject.fromObject(strData).toString());
      System.out.println("核销code返回的结果json数据===" + jsonData);

      errCode = jsonData.getString("errcode");

      if (!"0".equals(errCode)) {
        errorCode = errCode;

        // 在未生效期间或者已经过期核销提示
        if ("40079".equals(errCode)) {
          errorCode = "timeError";
          log.info("微信返回结果40079===卡券未生效或已过期~!" + jsonData.getString("errmsg"));
          return "error";
        }

        // 已被核销 40099
        if ("40099".equals(errCode)) {
          errorCode = "alreadyConsume";
          log.info("微信返回结果40099===卡券已被核销~!" + jsonData.getString("errmsg"));
          return "error";
        }
        errorMsg = jsonData.getString("errmsg");
        log.info("核销卡券发生异常~!errorCode=" + errCode + ",errMsg=" + jsonData.getString("errmsg"));
        return "error";
      }

      // 若是核销成功,则录入数据库
      String openId = jsonData.getString("openid");
      WxConsumeCodeRecord record = new WxConsumeCodeRecord();
      record.setCardId(cardId);
      record.setOpenId(openId);
      record.setShopVerifyCode(shopVerifyCode);
      record.setCode(code);

      cardManageService.saveRecord(record);
      System.out.println("核销成功");
      log.info("核销成功card_id=" + record.getCardId() + ",code=" + record.getCode() + ",核销人openid="
          + record.getOpenId());
    } catch (SQLException e) {
      errorCode = "-1";
      errorMsg = "插入sql出现异常=" + e.getMessage();
      log.error("插入sql出现异常!errorMsg=" + errorMsg,e);
      return "error";
    }
    return "success";

  }

  /**
   * 创建卡券前做logo的上传,通过表单形式
   * 
   * @return
   */
  public String toUploadImage() {
    String accessToken = BaseUtil.getToken();
    super.getRequest().setAttribute("access_token", accessToken);
    return "success";
  }

  /**
   * 去到预约页面 just for test
   * 
   * @return
   */
  public String toReservation() {
    return "success";
  }

  /**
   * 去到卡券核销页面
   * 
   * @return
   */
  public String toWxConsumeCode() {
    String encyptCodeFromWeixin = super.getRequest().getParameter("encrypt_code");
    String cardIdFromWeixin = super.getRequest().getParameter("card_id");
    String signatureFromWeixin = super.getRequest().getParameter("signature");
    String cardTypeFromWeixin = super.getRequest().getParameter("cardType");

    // 将其放入到值栈中
    this.encyptCode = encyptCodeFromWeixin;
    this.cardId = cardIdFromWeixin;
    this.signature = signatureFromWeixin;
    this.cardType = cardTypeFromWeixin;

    System.out.println("cardType=" + super.getRequest().getParameter("cardType"));

    // 首先判断请求是否来自微信(是需要使用签名方式做判断的)
    if (encyptCodeFromWeixin == null || cardIdFromWeixin == null || signatureFromWeixin == null) {
      errorCode = "-3";
      errorMsg = "this request is not come from weixin!";
      log.error("this request is not come from weixin!");
      return "error";
    }

    // 判断券的ID是不是礼品券,如果是礼品券,判断卡券的类型是否是gift
    // 解码code后用code还有card_id去后台验证该用户是否有预约信息如果有预约信息,则显示他的预约详情,如果没有,则显示可供提交的预约表单
    if (this.giftCardId.equals(cardIdFromWeixin) || "gift".equals(cardTypeFromWeixin)) {
      // 解码接口
      String sendUrl =
          "https://api.weixin.qq.com/card/code/decrypt?access_token=" + BaseUtil.getToken();
      String strData = "{'encrypt_code': '" + encyptCodeFromWeixin + "'}";
      JSONObject jsonData =
          WxHttpUtil.httpRequest(sendUrl, "POST", JSONObject.fromObject(strData).toString());
      System.out.println("解码后的code" + jsonData);

      if (!"0".equals(jsonData.getString("errcode"))) {
        errorCode = jsonData.getString("errcode");
        errorMsg = jsonData.getString("errmsg");
        log.error("微信解码接口发生异常~!errorcode=" + jsonData.getString("errcode") + ",errorMsg="
            + jsonData.getString("errmsg"));
        return "error";
      }
      String code = jsonData.getString("code");

      try {
        WxReservation obj = cardManageService.queryReservationByCardAndCode(cardIdFromWeixin, code);
        // 如果该code尚未预约,则其可以预约
        if (obj == null) {
          List<WxKmrsShopInfo> kmrsShopList = cardManageService.queryAllKmrsShopInfo();
          super.getRequest().setAttribute("kmrsShopList", kmrsShopList);
          return "reservationSave";
        }

        // 显示预约详情
        reservation = obj;
        return "reservationDetail";
      } catch (SQLException e) {
        errorCode = "-1";
        errorMsg = "按code查询预约情况出现异常!card_id=" + this.cardId + ",code=" + this.encyptCode;
        log.error(errorMsg, e);
        return "error";
      }

    }

    return "success";
  }

  /**
   * 保存预约信息
   * 
   * @return
   */
  public String saveReservation() {

    /**
     * 调用解码接口测试
     */
    String sendUrl =
        "https://api.weixin.qq.com/card/code/decrypt?access_token=" + BaseUtil.getToken();
    String strData = "{'encrypt_code': '" + encyptCode + "'}";
    JSONObject jsonData =
        WxHttpUtil.httpRequest(sendUrl, "POST", JSONObject.fromObject(strData).toString());

    // 如果解码接口调用失败,返回error页面
    if (!"0".equals(jsonData.getString("errcode"))) {
      errorCode = jsonData.getString("errcode");
      errorMsg = jsonData.getString("errmsg");
      log.error("微信解码接口发生异常~!" + jsonData.getString("errmsg"));
      return "error";
    }

    reservation.setCardId(this.cardId);
    reservation.setCode(jsonData.getString("code")); // 设置为解码后的code

    try {
      // 如果重复提交,则通知用户
      WxReservation obj =
          cardManageService.queryReservationByCardAndCode(reservation.getCardId(), reservation
              .getCode());
      if (obj != null) {
        reservation = obj;
        isRepeatSubmit = "1"; // 标识是否是重复提交
        return "reservationDetail";
      }
      cardManageService.saveReservation(reservation);
      reservation =
          cardManageService.queryReservationByCardAndCode(reservation.getCardId(), reservation
              .getCode());
      System.out.println("微信卡券礼品券客户预约成功!card_id=" + reservation.getCardId() + ",code="
          + reservation.getCode() + ",shop_id=" + reservation.getShopId());
      log.info("微信卡券礼品券客户预约成功!card_id=" + reservation.getCardId() + ",code="
          + reservation.getCode() + ",shop_id=" + reservation.getShopId());
      return "success";
    } catch (SQLException e) {
      errorCode = "-4";
      errorMsg = "sql执行异常";
      log.error(errorMsg,e);
      return "error";
    }
  }

  public String getShopVerifyCode() {
    return shopVerifyCode;
  }

  public void setShopVerifyCode(String shopVerifyCode) {
    this.shopVerifyCode = shopVerifyCode;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  public String getEncyptCode() {
    return encyptCode;
  }

  public void setEncyptCode(String encyptCode) {
    this.encyptCode = encyptCode;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getCardId() {
    return cardId;
  }

  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

  public WxReservation getReservation() {
    return reservation;
  }

  public void setReservation(WxReservation reservation) {
    this.reservation = reservation;
  }

  public String getIsRepeatSubmit() {
    return isRepeatSubmit;
  }

  public void setIsRepeatSubmit(String isRepeatSubmit) {
    this.isRepeatSubmit = isRepeatSubmit;
  }

  public String getCardType() {
    return cardType;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }
}
