package com.kmzyc.b2b.action.orderPay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.model.Address;
import com.kmzyc.b2b.model.PromotionInfo;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.AccountInfoService;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.OrderMainService;
import com.kmzyc.b2b.service.member.AddressService;
import com.kmzyc.b2b.vo.PayMoney;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;

@Controller
@Scope("prototype")
@SuppressWarnings("unchecked")
public class SettlementAjaxAction extends SettleMentBaseAction {
  // private static Logger log = LoggerFactory.getLogger(SettlementAjaxAction.class);
  private static Logger log = LoggerFactory.getLogger(SettlementAjaxAction.class);

  private static final long serialVersionUID = -656417878929304275L;

  @Resource(name = "loginServiceImp")
  private LoginService loginService;

  @Resource(name = "accountInfoServiceImp")
  private AccountInfoService accountinfoService;

  @Resource(name = "orderMainServiceImpl")
  private OrderMainService orderMainService;

  @Resource(name = "addressServiceImpl")
  private AddressService addressService;

  private User user = new User();

  private boolean checkedFlag = true;

  private PayMoney payMoney;

  private String result = null;
  private ReturnResult<HashMap<String, Object>> returnResult =
      new ReturnResult<HashMap<String, Object>>();

  private Map<String, Object> map = new HashMap<String, Object>();

  /**
   * 判断用户是否登陆（免注册登陆的用户也算是已经登陆），只用于支付验证用户是否存在
   * 
   * @return
   */
  public String checkUserIsLogin() {
    this.checkedFlag = false;
    Object obj = this.getSession().getAttribute(Constants.SESSION_USER_ID);
    Long loginId = obj == null ? null : Long.parseLong(obj.toString());
    if (loginId == null) {
      user = (User) this.getSession().getAttribute(Constants.UNLOGIN_SESSION_USER_KEY);
      if (null != user) {
        this.checkedFlag = true;
      }
    } else {
      this.checkedFlag = true;
    }
    // this.checkedFlag = this.isLogin();
    return SUCCESS;
  }

  /**
   * 检查验证码是否正确
   * 
   * @return
   */
  public String checkMobileExist() {
    String mobile = this.getRequest().getParameter("mobile");
    Integer flag = Integer.parseInt(this.getRequest().getParameter("flag"));
    List<User> userList = this.loginService.checkMobileExist(mobile);

    if (null != userList && userList.size() > 0) {
      for (User user : userList) {
        if (flag == 2) {
          if (user.getCustomerTypeId().intValue() == Constants.CUSTOMER_TYPE_TOURIST.intValue()) {
            this.checkedFlag = false;;
          } else {
            this.checkedFlag = true;
          }
        }
        if (flag == 1) {
          if (user.getCustomerTypeId().intValue() != Constants.CUSTOMER_TYPE_TOURIST.intValue()) {
            this.checkedFlag = false;
          } else {
            this.checkedFlag = true;
          }
        }
      }
    } else {
      this.checkedFlag = true;

    }
    return SUCCESS;
  }

  public String checkEmailExist() {

    String email = this.getRequest().getParameter("email");
    // 1:已经被注册 2：已经被使用

    Integer flag = Integer.parseInt(this.getRequest().getParameter("flag"));

    // this.checkedFlag =!this.loginService.checkEmailExist(email);
    List<User> userList = this.loginService.checkEmailExist(email);
    if (null != userList && userList.size() > 0) {
      for (User user : userList) {
        if (flag == 2) {
          if (user.getCustomerTypeId().intValue() == Constants.CUSTOMER_TYPE_TOURIST.intValue()) {
            this.checkedFlag = false;
          } else {
            this.checkedFlag = true;
          }
        }
        if (flag == 1) {
          if (user.getCustomerTypeId().intValue() != Constants.CUSTOMER_TYPE_TOURIST.intValue()) {
            this.checkedFlag = false;
          } else {
            this.checkedFlag = true;
          }
        }
      }
    } else {
      this.checkedFlag = true;
    }
    return SUCCESS;
  }

  public String updateAndQueryPayMoney() {
    if (!super.compareLoginFlag()) {
      this.returnResult = super.getUnloginReturnResult();
      return SUCCESS;
    }
    Object obj = this.getSession().getAttribute(Constants.SESSION_USER_ID);
    String usessinId = null == obj ? getSession().getId() : obj.toString();
    accountinfoService.updatePayMoneyToMemCache(usessinId, "balanceMoney", 0d);
    payMoney = this.accountinfoService.getPayMoneyFormMemCache(usessinId);

    map.put("loginFlag", 1);
    map.put("payMoney", payMoney);
    this.returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", map);
    return SUCCESS;
  }

  public String queryPayMoney() {
    Object obj = this.getSession().getAttribute(Constants.SESSION_USER_ID);
    String usessinId = null == obj ? getSession().getId() : obj.toString();
    payMoney = this.accountinfoService.getPayMoneyFormMemCache(usessinId);
    return SUCCESS;
  }

  public String deleteCoupon() {
    Object obj = this.getSession().getAttribute(Constants.SESSION_USER_ID);
    String usessinId = null == obj ? getSession().getId() : obj.toString();
    accountinfoService.updatePayMoneyToMemCache(usessinId, "couponid", "");
    accountinfoService.updatePayMoneyToMemCache(usessinId, "couponMoney", 0d);
    payMoney = this.accountinfoService.getPayMoneyFormMemCache(usessinId);
    return SUCCESS;
  }

  public String checkPayMethodUsed() {
    checkedFlag = true;
    String coupon = this.getRequest().getParameter("coupon");
    String bleance = this.getRequest().getParameter("balance");
    Object obj = this.getSession().getAttribute(Constants.SESSION_USER_ID);
    String usessinId = null == obj ? getSession().getId() : obj.toString();
    PayMoney payMoney = this.accountinfoService.getPayMoneyFormMemCache(usessinId);
    if (coupon != null && coupon.equalsIgnoreCase("checked")) {
      if (payMoney.getCouponid() == null || payMoney.getCouponid().equals("")
          || payMoney.getCouponMoney() == 0) {
        checkedFlag = false;
        return SUCCESS;
      }
    }
    if (null != bleance && bleance.equalsIgnoreCase("checked")) {
      if (payMoney.getBalanceMoney() == 0) {
        checkedFlag = false;
        return SUCCESS;
      }
    }
    return SUCCESS;
  }

  /**
   * 支付前校验订单产品
   * 
   * @return
   */
  public String checkOrderOnpay() {
    result = null;
    try {
      Long loginId = null;
      String orderCode = this.getRequest().getParameter("orderCode");
      String orderAmount = this.getRequest().getParameter("orderAmount");
      if (null == orderCode || null == orderAmount) {
        result = "传入参数错误";
      } else if (null == (loginId = super.getUserloginId())) {
        result = "用户未登录或登录超时";
      } else {
        result = orderMainService.checkPayOrder(loginId, orderCode, new BigDecimal(orderAmount));
      }
    } catch (Exception e) {
      log.error(e.getMessage());
      result = "系统错误，请稍后重试！";
    }
    return SUCCESS;
  }

  /**
   * 支付定金前校验订单产品
   * 
   * @return
   */
  public String checkOrderOnpayDeposit() {
    result = null;
    try {
      Long loginId = null;
      String orderCode = this.getRequest().getParameter("orderCode");
      String orderAmount = this.getRequest().getParameter("orderAmount");
      if (null == orderCode || null == orderAmount) {
        result = "传入参数错误";
      } else if (null == (loginId = super.getUserloginId())) {
        result = "用户未登录或登录超时";
      } else {
        result = orderMainService.checkPayDeposit(loginId, orderCode, new BigDecimal(orderAmount));
      }
    } catch (Exception e) {
      log.error("checkOrderOnpayDeposit", e);
      result = "系统错误，请稍后重试！";
    }
    return SUCCESS;
  }

  /**
   * 支付尾款前校验订单产品
   * 
   * @return
   */
  public String checkOrderOnpayRetainage() {
    result = null;
    try {
      Long loginId = null;
      String orderCode = this.getRequest().getParameter("orderCode");
      String orderAmount = this.getRequest().getParameter("orderAmount");
      if (null == orderCode || null == orderAmount) {
        result = "传入参数错误";
      } else if (null == (loginId = super.getUserloginId())) {
        result = "用户未登录或登录超时";
      } else {
        result =
            orderMainService.checkPayRetainage(loginId, orderCode, new BigDecimal(orderAmount));
      }
    } catch (Exception e) {
      log.error(e.getMessage());
      result = "系统错误，请稍后重试！";
    }
    return SUCCESS;
  }


  /**
   * 
   * 购买数量是否适合促销活动
   * 
   * @param promotionInfo
   * @param number
   * @param type 是否为会员
   * @return
   */
  public String checkOrderPromotion(PromotionInfo promotionInfo, Long number) {
    String msg = null;

    if (promotionInfo.getMaxBuy() != 0 && promotionInfo.getMaxBuy() < number) {
      msg = "购买数量不能大于限购数量。请重新购买!";
      return msg;
    }
    if (promotionInfo.getMinBuy() != 0 && promotionInfo.getMinBuy() > number) {
      msg = "购买数量不能小于最小购买数量。请重新购买!";
      return msg;
    }

    if (promotionInfo.getPromotionSell() != 0
        && (promotionInfo.getPromotionStock() - promotionInfo.getPromotionSell()) < number) {
      msg = "购买数量不能大于您参加限购活动的购买数量。请重新购买!";
      return msg;
    }
    if (promotionInfo.getPromotionStock() != 0 && promotionInfo.getPromotionStock() < number) {
      msg = "购买数量不能大于活动库存的库存剩余数量。请重新购买!";
      return msg;
    }
    return msg;
  }

  public String getPayMoneyInfo() {
    if (!super.compareLoginFlag()) {
      this.returnResult = super.getUnloginReturnResult();
      return SUCCESS;
    }
    Object obj = this.getSession().getAttribute(Constants.SESSION_USER_ID);
    String usessinId = null == obj ? getSession().getId() : obj.toString();
    this.payMoney = this.accountinfoService.getPayMoneyFormMemCache(usessinId);
    map.put("loginFlag", 1);
    map.put("payMoney", payMoney);
    this.returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", map);
    return SUCCESS;
  }

  public String checkFareChange() {
    if (!super.compareLoginFlag()) {
      this.returnResult = getUnloginReturnResult();
      return SUCCESS;
    }
    checkedFlag = true;
    boolean isguangdong = Boolean.parseBoolean(this.getRequest().getParameter("isguangdong"));
    String defaultAddresstmp = this.getRequest().getParameter("defaultAddressvalue");
    Integer defaultAddressId =
        Integer.parseInt(defaultAddresstmp.equals("") ? "0" : defaultAddresstmp);
    String naddressIdtmp = this.getRequest().getParameter("naddressId");
    Integer naddressId = Integer.parseInt(naddressIdtmp.equals("") ? "0" : naddressIdtmp);
    Long uid = super.getUserloginId();
    String usessinId = null == uid ? getSession().getId() : uid.toString();
    Address memaddress = this.addressService.getAddressFromMem(usessinId);

    int memProvinceIsGuangdong = 0;// 缓存中存放的收货地址是不是广东省(0:不是 1：是)
    if (memaddress != null && memaddress.getProvince().equals("广东")) {
      memProvinceIsGuangdong = 1;
    }
    // 首次新增收货地址
    if (defaultAddressId == null || defaultAddressId == 0) {
      if (memaddress != null && (isguangdong == (memProvinceIsGuangdong == 1))) {
        checkedFlag = false;
      } else {
        if (memaddress.getProvince().equals("广东") && isguangdong) {
          checkedFlag = false;
        }
        if (!memaddress.getProvince().equals("广东") && !isguangdong) {
          checkedFlag = false;
        }
      }
    } else {// 修改默认收货地址
      if (memaddress.getAddressId() == defaultAddressId) {// 没有选择其他的收货地址
        if (naddressId == null || naddressId == 0) {// 不修改默认收货地址
          checkedFlag = false;
        } else {// 修改默认收货地址
          try {
            Address address = this.addressService.findByNAddressID(uid, defaultAddressId);
            if (address.getProvince().equals("广东") && isguangdong) {
              checkedFlag = false;
            }
            if (!address.getProvince().equals("广东") && !isguangdong) {
              checkedFlag = false;
            }
            /*
             * if(isguangdongOrnotguangdong(memaddress.getProvince(), address.getProvince())){
             * checkedFlag = false; }
             */
          } catch (Exception e) {
            log.error(e.getMessage(),e);
          }
        }
      } else {// 选择其他的收货地址
        if (naddressId == null || naddressId == 0) {// 不修改选择收货地址
          try {
            Address address = this.addressService.findByNAddressID(uid, defaultAddressId);
            if (isguangdongOrnotguangdong(memaddress.getProvince(), address.getProvince())) {
              checkedFlag = false;
            }
          } catch (Exception e) {
            log.error(e.getMessage(),e);
          }
        } else {// 修改默认收货地址
          try {
            Address address = this.addressService.findByNAddressID(uid, defaultAddressId);
            if (address.getProvince().equals("广东") && isguangdong) {
              checkedFlag = false;
            }
            if (!address.getProvince().equals("广东") && !isguangdong) {
              checkedFlag = false;
            }
            /*
             * if(isguangdongOrnotguangdong(memaddress.getProvince(), address.getProvince())){
             * checkedFlag = false; }
             */
          } catch (Exception e) {
            log.error(e.getMessage(),e);
          }
        }
      }

    }
    map.put("checkedFlag", checkedFlag);
    map.put("loginFlag", 1);
    this.returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", map);
    return SUCCESS;
  }

  public boolean isguangdongOrnotguangdong(String pro1, String pro2) {
    if (pro1.equals("广东") && pro2.equals("广东")) {
      return true;
    }
    if (!pro1.equals("广东") && !pro2.equals("广东")) {
      return true;
    }
    return false;
  }

  public String isNumberOfAddressOk() {
    if (!super.compareLoginFlag()) {
      this.returnResult = super.getUnloginReturnResult();
      return SUCCESS;
    }
    Long loginId = super.getUserloginId();
    if (loginId == null || loginId == 0) {
      this.checkedFlag = true;
    } else {
      try {
        // 从cache 中查找
        // AccountInfo accountInfo = accountinfoService.findByLoginId(loginId);
        // checkedFlag = super.isNumberOfAddressOk(accountInfo.getNaccountId());
        checkedFlag = super.isNumberOfAddressOk(loginId);
      } catch (Exception e) {
        log.error(e.getMessage(),e);
        this.checkedFlag = false;
      }
    }
    map.put("checkedFlag", checkedFlag);
    map.put("loginFlag", 1);
    this.returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", map);
    return SUCCESS;
  }


  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public boolean isCheckedFlag() {
    return checkedFlag;
  }

  public void setCheckedFlag(boolean checkedFlag) {
    this.checkedFlag = checkedFlag;
  }

  public PayMoney getPayMoney() {
    return payMoney;
  }

  public void setPayMoney(PayMoney payMoney) {
    this.payMoney = payMoney;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public ReturnResult<HashMap<String, Object>> getReturnResult() {
    return returnResult;
  }

  public void setReturnResult(ReturnResult<HashMap<String, Object>> returnResult) {
    this.returnResult = returnResult;
  }

}
