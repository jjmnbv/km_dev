package com.kmzyc.b2b.action.orderPay;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.Address;
import com.kmzyc.b2b.model.Coupon;
import com.kmzyc.b2b.model.InvoiceInfo;
import com.kmzyc.b2b.model.OrderDictionary;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.AccountInfoService;
import com.kmzyc.b2b.service.member.AddressService;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.b2b.vo.CarItemView;
import com.kmzyc.b2b.vo.CarProduct;
import com.kmzyc.b2b.vo.CompositionCarProduct;
import com.kmzyc.b2b.vo.PayMoney;
import com.kmzyc.b2b.vo.ProductPromotionInfo;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.kmzyc.framework.ajax.AjaxUtil;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.pltfm.app.vobject.SaltInfo;

import redis.clients.jedis.JedisCluster;

@Controller
@Scope("prototype")
@SuppressWarnings("unchecked")
public class RegfreeOrderInfoAction extends SettleMentBaseAction {
  private static Logger logger = LoggerFactory.getLogger(RegfreeOrderInfoAction.class);
  private static final long serialVersionUID = -656417878929304275L;

  @Resource(name = "accountInfoServiceImp")
  private AccountInfoService accountinfoService;

  @Resource(name = "addressServiceImpl")
  private AddressService addressService;

  @Resource(name = "jedisCluster")
 private JedisCluster jedisCluster;

  @Resource(name = "myOrderServiceImpl")
  private MyOrderService myOrderService;

    @Resource
    private CustomerRemoteService customerRemoteService;


  private List<Address> addressList;
  private List<OrderDictionary> payModelList = new ArrayList<OrderDictionary>();
  private List<OrderDictionary> deliveryTimeList = new ArrayList<OrderDictionary>();
  private Map<String, String> defaultPayAndDelivery = new HashMap<String, String>();

  private List<CarItemView> carItemViews = new ArrayList<CarItemView>();
  private Map<Long, CarProduct> carProductMap = null;
  private Map<Long, CompositionCarProduct> compositionCarProductMap = null;
  private Map<Long, Map<Long, CarProduct>> giftCarProductMap;

  private ProductPromotionInfo productPromotionInfo = null;

  private PayMoney payMoney = null;
  private InvoiceInfo invoiceInfo = null;
  private AccountInfo accountInfo = null;
  private Address address = null;

  private String province = null;
  private String city = null;
  private String area = null;
  private String name = "testName";
  private String detailedAddress = null;
  private String telephone = null;
  private String mobile = null;
  private String email = null;
  private String postalcode = null;
  private Integer naddressId = null;
  private Long loginID = null;
  private String action = null;
  private String defaultAddressId = null;
  private Double fare = null;

  private String paymodelvalue = null;
  private String deliveryTimeValue = null;
  private String isconfirmValue = null;
  private String paymodeltext = null;
  // 余额支付
  private Long balance = Constants.PAY_METHOD_BALANCE;
  private Long preferential = Constants.PAY_METHOD_PREFERENTIAL;
  private String password = null;

  private List<Coupon> couponList = new ArrayList<Coupon>();

  private Integer orderDescription_ = null;// checkbox
  private String orderDescription = null;// 订单备注
  private String couponId = null;// 选择使用哪种优惠券 优惠券ID

  private User user = new User();

  private String cheorder_discription = "";

  private String productName = "";
  private String buyType = "";
  private int productCount = 0;
  private Long productSkuID = null;
  private static final String VALID_COUNT = "valid_paymoney_count";


  /**
   * 保存支付信息到memcache 并刷新页面
   * 
   * @return
   */
  public String saveInvoiceInfo() {
    try {
      Long uid = super.getUserloginId();
      String usessinId = null == uid ? getSession().getId() : uid.toString();
      InvoiceInfo invoiceInfo = new InvoiceInfo();
      String invoicetitle = this.getRequest().getParameter("invoicetitle");
      String invoiceContent = this.getRequest().getParameter("invoiceContent");
      String invoiceType = this.getRequest().getParameter("invoiceType");
      if ((null != invoicetitle && (invoicetitle.indexOf("<") >= 0 || invoicetitle.indexOf("&gt") >= 0))
          || (null != invoiceContent && (invoiceContent.indexOf("<") >= 0 || invoiceContent
              .indexOf("&gt") >= 0))
          || (null != invoiceType && (invoiceType.indexOf("<") >= 0 || invoiceType.indexOf("&gt") >= 0))) {
        return null;
      }
      invoiceInfo.setInvoiceContent(invoiceContent);
      invoiceInfo.setInvoicetitle(invoicetitle);
      invoiceInfo.setInvoiceType(invoiceType);
      this.accountinfoService.saveInvoiceInfoToMemcache(usessinId, invoiceInfo);
      AjaxUtil.writeJSONStringToResponse("1");
    } catch (Exception e) {
      logger.error("", e);
    }
    return null;
  }

  /**
   * 从缓存移除发票信息
   * 
   * @return
   */
  public String removeInvoiceInfo() {
    Long uid = super.getUserloginId();
    String usessinId = null == uid ? getSession().getId() : uid.toString();
    accountinfoService.removeInvoiceInfoToMemcache(usessinId);
    AjaxUtil.writeJSONStringToResponse("1");
    return null;
  }

  public String checkisLogin() throws ServiceException {
    HttpServletRequest request = this.getRequest();
    Long uid = super.getUserloginId();
    String password = request.getParameter("password");
    String usemoney = request.getParameter("usemoney");

    try {


        if (null == uid) {
            AjaxUtil.writeJSONStringToResponse("1");
            return null;
        }

        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setLoginId(uid);
        userBaseInfo.setPassword(password);
        userBaseInfo = this.customerRemoteService.queryUserPasswordTwice(userBaseInfo,"pay");
        //SaltInfo saltInfo = this.saltInfoService.querySaltInfo(userBaseInfo);

        if (null == userBaseInfo) {
            AjaxUtil.writeJSONStringToResponse("1");
            return null;
        }


        // 支付密码在30分钟内输入错误次数超过10次
        if (!this.validCount(uid.toString(), 10)) {
            AjaxUtil.writeJSONStringToResponse("7");
            return null;
        }
        AccountInfo accountInfo = accountinfoService.findByLoginId(uid);
        // 密码正确
        if (null == accountInfo.getPaymentpwd() && 1 != myOrderService.enablePayPWD(uid)) {
            AjaxUtil.writeJSONStringToResponse("8");
            return null;
        }
        if (accountInfo.getPaymentpwd().equals(userBaseInfo.getPassword())) {
            PayMoney payMoney = this.accountinfoService.getPayMoneyFormMemCache(uid.toString());
            // mkw 20151224 add 查询为空，提示购物车信息变动
            if (null == payMoney) {
                AjaxUtil.writeJSONStringToResponse(Constants.SETTLEMENT_SHOPCART_CHANGE);
                return null;
            }
            // 选择的优惠券不能和余额一起使用
            if (payMoney.getCouponMoney() > 0 && "1".equals(payMoney.getUseLimitsType())) {
                AjaxUtil.writeJSONStringToResponse("6");
                return null;
            }
            // end
            if (new BigDecimal(usemoney).compareTo(BigDecimal.ZERO) < 0) {
                AjaxUtil.writeJSONStringToResponse("4");
                return null;
            }
            // 输入金额不能大于实际支付金额.
            if (new BigDecimal(usemoney).compareTo(new BigDecimal(payMoney.getPayMoney())) > 0) {
                AjaxUtil.writeJSONStringToResponse("4");
                return null;
            }
            // 输入金额不能大于账户金额.
            if (Double.valueOf(usemoney) > accountInfo.getAmountAvlibal()) {
                AjaxUtil.writeJSONStringToResponse("3");
                return null;
            }
            this.accountinfoService.updatePayMoneyToMemCache(uid.toString(), "balanceMoney",
                    new BigDecimal(usemoney).setScale(2, BigDecimal.ROUND_FLOOR).doubleValue());
            payMoney = this.accountinfoService.getPayMoneyFormMemCache(uid.toString());
            AjaxUtil.writeJSONToResponse(payMoney);
        } else {// 密码不正确。请重新输入。
            AjaxUtil.writeJSONStringToResponse("2");
            // 记录密码输入错误次数
            this.incrErrorCount(uid.toString(), 30 * 60);
        }
    }catch (Exception e){
        logger.error("checkisLogin 校验支付密码异常",e);
    }
    return null;
  }

  /**
   * 使用购物券调用此方法 1、验证购物券信息 2、修改memcache中的值
   * 
   * @return JSON 到页面 1:优惠券不可用 2:密码错误登录失败 payMoney : 页面使用 3:使用的优惠券金额大于订单总额，提示用户是否使用
   * @throws SQLException
   */
  public String getcouponMoneyByCouponId() throws ServiceException {
    String couponId = this.getRequest().getParameter("couponid");

    buyType = this.getRequest().getParameter("buyType");
    Long loginId = this.getUserloginId();
    // 支付密码在30分钟内输入错误次数超过10次
    if (!this.validCount(loginId.toString(), 10)) {
      AjaxUtil.writeJSONStringToResponse("7");
      return null;
    }

      UserBaseInfo userBaseInfo = new UserBaseInfo();
      userBaseInfo.setLoginId(loginId);
      SaltInfo saltInfo = null;
      try {
          userBaseInfo.setPassword(this.getRequest().getParameter("couponpasswd"));
          userBaseInfo = this.customerRemoteService.queryUserPasswordTwice(userBaseInfo,"pay");
          //saltInfo = this.saltInfoService.querySaltInfo(userBaseInfo);
          if(null == userBaseInfo ){
              AjaxUtil.writeJSONStringToResponse("2");
              // 记录密码输入错误次数
              this.incrErrorCount(loginId.toString(), 30 * 60);
              return null;
          }
      } catch (Exception e) {
          logger.error(e.getMessage(),e);
      }

        // String couponpasswd =
        // com.kmzyc.b2b.util.StringUtil.passwordTwiceEncryption(this.getRequest().getParameter("couponpasswd"),saltInfo.getPaySalt())
        // ;

    AccountInfo accountInfo = accountinfoService.findByLoginId(loginId);
    // 密码不正确
    if (accountInfo.getPayRange() != null && accountInfo.getPayRange().contains("2")
        && accountInfo.getPaymentpwd() != null && !accountInfo.getPaymentpwd().equals(userBaseInfo.getPassword())) {
      AjaxUtil.writeJSONStringToResponse("2");
      // 记录密码输入错误次数
      this.incrErrorCount(loginId.toString(), 30 * 60);
      return null;
    }
    PayMoney payMoney = this.accountinfoService.getPayMoneyFormMemCache(loginId.toString());
    BigDecimal orgPay = new BigDecimal(payMoney.getPayMoney());
    if (null != payMoney.getCouponMoney()) {
      orgPay = orgPay.add(new BigDecimal(payMoney.getCouponMoney()));
    }
    Coupon coupon = this.accountinfoService.getCouponByCouponGrantId(Long.parseLong(couponId));
    if (coupon == null) {
      // 选择的优惠券不可用
      AjaxUtil.writeJSONStringToResponse("1");
      return null;
    }
    // 如果memCached存在优惠券数据，从缓存中取，否则从数据库获取
    HashMap<String, List<HashMap<String, String>>> couponsMap =
        this.accountinfoService.getCouponsListCached(this.getRequest().getSession().getId());
    HashMap<String, String> couponMap = null;
    if (couponsMap != null) {
      // 获取可用优惠券
      List<HashMap<String, String>> couponList = couponsMap.get("canUseCouponList");
      String canUseCouponId = "";
      for (int i = 0; i < couponList.size(); i++) {
        canUseCouponId = couponList.get(i).get("couponId");
        if (canUseCouponId.equals(couponId)) {
          couponMap = couponList.get(i);
          break;
        }
      }
    }
    if (couponMap == null) {
      // 选择的优惠券不可用
      AjaxUtil.writeJSONStringToResponse("1");
      return null;
    }

    BigDecimal couponMoney = coupon.getCouponMoney();
    if (couponMoney == null || couponMoney.compareTo(BigDecimal.ZERO) == 0) {
      // 选择的优惠券不可用
      AjaxUtil.writeJSONStringToResponse("1");
      return null;
    }
    try {
      if (couponMoney.compareTo(orgPay) > 0) {
        AjaxUtil.writeJSONStringToResponse("3");
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      return null;
    }
    coupon.setCouponId(Long.parseLong(couponId));// 注意：coupon无CouponGrantId
    this.accountinfoService.updatePayMoneyToMemCache(loginId.toString(), coupon);
    payMoney = this.accountinfoService.getPayMoneyFormMemCache(loginId.toString());
    AjaxUtil.writeJSONToResponse(payMoney);
    return null;
  }

  public String removeCouponMoney() {
    if (!super.compareLoginFlag()) {
      ReturnResult returnResult = super.getUnloginReturnResult();
      AjaxUtil.writeJSONToResponse(returnResult);
      return null;
    }
    Long uid = super.getUserloginId();
    String usessinId = null == uid ? getSession().getId() : uid.toString();
    String filedName = this.getRequest().getParameter("filedName");
    if (filedName != null && filedName.equals("couponMoney")) {
      this.accountinfoService.updatePayMoneyToMemCache(usessinId, "couponid", "");
      this.accountinfoService.updatePayMoneyToMemCache(usessinId, "couponName", "");
    }
    this.accountinfoService.updatePayMoneyToMemCache(usessinId, filedName, 0d);

    PayMoney payMoney = this.accountinfoService.getPayMoneyFormMemCache(usessinId);

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("loginFlag", 1);
    map.put("payMoney", payMoney);
    ReturnResult returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", map);
    AjaxUtil.writeJSONToResponse(returnResult);
    return null;
  }


  /**
   * 验证失败次数
   * 
   * @param key 一般是loginName
   * @param max 最多失败次数 包含m
   * @return true合法；false非法
   */
  public boolean validCount(String key, int max) {
    String nkey = VALID_COUNT + key;
    String count = jedisCluster.get(nkey);
    if (count == null) {
      return true;
    }
    return Integer.parseInt(count) < max;
  }

  /***
   * 失败次数自增,失败后调用
   * 
   * @param key 一般是loginName
   * @param seconds 冻结多长时间，单位s
   */
  public void incrErrorCount(String key, int seconds) {
    String nkey = VALID_COUNT + key;
    if (1 == jedisCluster.incr(nkey)) {
        jedisCluster.expire(nkey, seconds);
    }
  }


  public List<Address> getAddressList() {
    return addressList;
  }

  public void setAddressList(List<Address> addressList) {
    this.addressList = addressList;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDetailedAddress() {
    return detailedAddress;
  }

  public void setDetailedAddress(String detailedAddress) {
    this.detailedAddress = detailedAddress;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public String getPostalcode() {
    return postalcode;
  }

  public void setPostalcode(String postalcode) {
    this.postalcode = postalcode;
  }

  public Integer getNaddressId() {
    return naddressId;
  }

  public void setNaddressId(Integer naddressId) {
    this.naddressId = naddressId;
  }

  public Long getLoginID() {
    return loginID;
  }

  public void setLoginID(Long loginID) {
    this.loginID = loginID;
  }

  public List<OrderDictionary> getPayModelList() {
    return payModelList;
  }

  public void setPayModelList(List<OrderDictionary> payModelList) {
    this.payModelList = payModelList;
  }

  public List<OrderDictionary> getDeliveryTimeList() {
    return deliveryTimeList;
  }

  public void setDeliveryTimeList(List<OrderDictionary> deliveryTimeList) {
    this.deliveryTimeList = deliveryTimeList;
  }

  public String getPaymodelvalue() {
    return paymodelvalue;
  }

  public void setPaymodelvalue(String paymodelvalue) {
    this.paymodelvalue = paymodelvalue;
  }

  public String getDeliveryTimeValue() {
    return deliveryTimeValue;
  }

  public void setDeliveryTimeValue(String deliveryTimeValue) {
    this.deliveryTimeValue = deliveryTimeValue;
  }

  public String getIsconfirmValue() {
    return isconfirmValue;
  }

  public void setIsconfirmValue(String isconfirmValue) {
    this.isconfirmValue = isconfirmValue;
  }

  public InvoiceInfo getInvoiceInfo() {
    return invoiceInfo;
  }

  public void setInvoiceInfo(InvoiceInfo invoiceInfo) {
    this.invoiceInfo = invoiceInfo;
  }

  public List<Coupon> getCouponList() {
    return couponList;
  }

  public void setCouponList(List<Coupon> couponList) {
    this.couponList = couponList;
  }

  public AccountInfo getAccountInfo() {
    return accountInfo;
  }

  public void setAccountInfo(AccountInfo accountInfo) {
    this.accountInfo = accountInfo;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getDefaultAddressId() {
    return defaultAddressId;
  }

  public void setDefaultAddressId(String defaultAddressId) {
    this.defaultAddressId = defaultAddressId;
  }

  public long getBalance() {
    return balance;
  }

  public void setBalance(long balance) {
    this.balance = balance;
  }

  public long getPreferential() {
    return preferential;
  }

  public void setPreferential(long preferential) {
    this.preferential = preferential;
  }

  public String getPaymodeltext() {
    return paymodeltext;
  }

  public void setPaymodeltext(String paymodeltext) {
    this.paymodeltext = paymodeltext;
  }

  public Map<String, String> getDefaultPayAndDelivery() {
    return defaultPayAndDelivery;
  }

  public void setDefaultPayAndDelivery(Map<String, String> defaultPayAndDelivery) {
    this.defaultPayAndDelivery = defaultPayAndDelivery;
  }

  public Integer getOrderDescription_() {
    return orderDescription_;
  }

  public void setOrderDescription_(Integer orderDescription_) {
    this.orderDescription_ = orderDescription_;
  }

  public String getOrderDescription() {
    return orderDescription;
  }

  public void setOrderDescription(String orderDescription) {
    this.orderDescription = orderDescription;
  }

  public String getCouponId() {
    return couponId;
  }

  public void setCouponId(String couponId) {
    this.couponId = couponId;
  }

  public List<CarItemView> getCarItemViews() {
    return carItemViews;
  }

  public void setCarItemViews(List<CarItemView> carItemViews) {
    this.carItemViews = carItemViews;
  }

  public ProductPromotionInfo getProductPromotionInfo() {
    return productPromotionInfo;
  }

  public void setProductPromotionInfo(ProductPromotionInfo productPromotionInfo) {
    this.productPromotionInfo = productPromotionInfo;
  }

  public Double getFare() {
    return fare;
  }

  public void setFare(Double fare) {
    this.fare = fare;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCheorder_discription() {
    return cheorder_discription;
  }

  public void setCheorder_discription(String cheorder_discription) {
    this.cheorder_discription = cheorder_discription;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Map<Long, CarProduct> getCarProductMap() {
    return carProductMap;
  }

  public void setCarProductMap(Map<Long, CarProduct> carProductMap) {
    this.carProductMap = carProductMap;
  }

  public Map<Long, CompositionCarProduct> getCompositionCarProductMap() {
    return compositionCarProductMap;
  }

  public void setCompositionCarProductMap(Map<Long, CompositionCarProduct> compositionCarProductMap) {
    this.compositionCarProductMap = compositionCarProductMap;
  }

  public String getBuyType() {
    return buyType;
  }

  public void setBuyType(String buyType) {
    this.buyType = buyType;
  }

  public int getProductCount() {
    return productCount;
  }

  public void setProductCount(int productCount) {
    this.productCount = productCount;
  }

  public Long getProductSkuID() {
    return productSkuID;
  }

  public void setProductSkuID(Long productSkuID) {
    this.productSkuID = productSkuID;
  }

  public PayMoney getPayMoney() {
    return payMoney;
  }

  public void setPayMoney(PayMoney payMoney) {
    this.payMoney = payMoney;
  }

  public Map<Long, Map<Long, CarProduct>> getGiftCarProductMap() {
    return giftCarProductMap;
  }

  public void setGiftCarProductMap(Map<Long, Map<Long, CarProduct>> giftCarProductMap) {
    this.giftCarProductMap = giftCarProductMap;
  }

}
