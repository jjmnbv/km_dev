package com.kmzyc.b2b.action.orderPay;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.Address;
import com.kmzyc.b2b.model.OrderDictionary;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.AccountInfoService;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.OrderDictonaryService;
import com.kmzyc.b2b.service.ShopCartInfoService;
import com.kmzyc.b2b.service.member.AddressService;
import com.kmzyc.b2b.shopcart.util.ShopCartUtil;
import com.kmzyc.b2b.shopcart.vo.Gift;
import com.kmzyc.b2b.shopcart.vo.SellerShop;
import com.kmzyc.b2b.shopcart.vo.SettlementInfo;
import com.kmzyc.b2b.shopcart.vo.ShopCart;
import com.kmzyc.b2b.shopcart.vo.ShopCartItem;
import com.kmzyc.b2b.shopcart.vo.ShopCartProduct;
import com.kmzyc.b2b.shopcart.vo.ShopCartProductReminder;
import com.kmzyc.b2b.shopcart.vo.ShopCartUserInfo;
import com.kmzyc.b2b.shopcart.vo.ShopcartCacheProduct;
import com.kmzyc.b2b.third.util.BaseUtil;
import com.kmzyc.b2b.util.wxpay.CommonUtil;
import com.kmzyc.b2b.util.wxpay.Sha1Util;
import com.kmzyc.b2b.vo.CarProduct;
import com.kmzyc.b2b.vo.PayMoney;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.b2b.vo.ShopCar;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.order.remote.OrderRiskRemoteService;
import com.kmzyc.promotion.app.vobject.OrderVo;
import com.kmzyc.promotion.optimization.vo.PresellProductVO;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.kmzyc.promotion.util.PresellCacheUtil;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

import net.sf.json.JSONObject;

// import com.km.framework.common.util.RemoteTool;

@Controller
@Scope("prototype")
@SuppressWarnings("unchecked")
public class WapSettlementAction extends SettleMentBaseAction {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(WapSettlementAction.class);
    @Resource(name = "accountInfoServiceImp")
    private AccountInfoService accountinfoService;

    @Resource(name = "addressServiceImpl")
    private AddressService addressService;

    @Resource(name = "orderDictonaryServiceImpl")
    private OrderDictonaryService OrderDictonaryService;

    @Resource(name = "loginServiceImp")
    private LoginService loginService;

    @Resource(name = "shopCartInfoService")
    private ShopCartInfoService shopCartInfoService;

    @Resource
    private PresellCacheUtil presellCacheUtil;

    @Resource
    private CouponRemoteService couponRemoteService;

    @Resource
    private OrderRiskRemoteService orderRiskRemoteService;

    private List<OrderDictionary> deliveryTimeList = new ArrayList<OrderDictionary>();
    private List<OrderDictionary> payModelList = new ArrayList<OrderDictionary>();
    private ReturnResult<HashMap<String, Object>> returnResult =
            new ReturnResult<HashMap<String, Object>>();
    private int productCount = 0;
    private Long productSkuID = null;

    private PayMoney payMoney = null;
    private String paymodelvalue = null;
    private String deliveryTimeValue = null;
    private String isconfirmValue = null;
    private List<HashMap<String, String>> couponList = new ArrayList<HashMap<String, String>>();
    private String buyType = "";
    private Long productSkuId = null;
    private Map<Long, CarProduct> carProductMap = new HashMap<Long, CarProduct>();
    private AccountInfo accountInfo = new AccountInfo();
    private Long loginID = null;
    private String productSkuIDs = null;
    private Boolean isLoginType;
    private PayMoney payMoneyInfo = new PayMoney();;
    // 药方图片路径
    private String prescriptionAttachmentFile = null;
    private List<String> payRangeList = new ArrayList<String>();
    private User user = new User();
    private List<Address> addressList = new ArrayList<Address>();
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

    private String defaultAddressId = null;

    public String wapSettlement() {
        // Long userId = super.getUserloginId();
        user = super.getLoginUser();
        if (user == null) {
            return LOGIN;
        }

        HttpServletRequest request = this.getRequest();
        buyType = request.getParameter("type");

        try {
            // 风控黑名单控制
            if (orderRiskRemoteService.queryOrderRisk(user.getLoginId())) {

                String result = null;
                if (buyType != null && Constants.EASY_BUY.equals(buyType)) {
                    result = "<script type='text/javascript'>alert('抱歉，当前账号已被锁定无法下单！如有疑问，请与商城客服联系！');history.go(-1);</script>";
                } else {
                    result = "<script type='text/javascript'>alert('抱歉，当前账号已被锁定无法下单！如有疑问，请与商城客服联系！');"
                            + "window.location.href='/cart/listWapShopCar.action';</script>";
                }

                this.showpage(result);
                return null;
            }
        } catch (Exception e) {
            logger.error("查询风控黑名单异常！", e);
        }

        HttpSession session = getSession();
        // 加载结算时，删除优惠券缓存数据 add by zhuyanling
        this.accountinfoService.delCouponsListCached(session.getId());

        this.defaultAddressId = null;
        this.naddressId = null;


        String activityChannel = request.getParameter("activityChannel");
        if (StringUtil.isEmpty(activityChannel)) {
            activityChannel = "";
        }

        ShopCart shopCart = null;
        Long loginID = user.getLoginId();
        String uid = loginID.toString();
        // 判断登陆类型，01表示普通会员，02 表示康美中药城会员
        String loginType = (String) session.getAttribute(Constants.SESSION_B2B_OR_ERA);
        ShopCartUserInfo user = new ShopCartUserInfo(uid, isLogin(), loginType, buyType);
        // 轻松购（立即购买，不经过购物车页面直接到结算页面）
        if (buyType != null
                && (Constants.EASY_BUY.equals(buyType) || Constants.PRESELL_BUY.equals(buyType))) {
            String id = request.getParameter("productSkuId");
            String productCountString = request.getParameter("productVary");
            productCount = 1;
            if (!StringUtil.isEmpty(productCountString)) {
                productCount = Integer.parseInt(productCountString);
            }

            ShopcartCacheProduct prodcut =
                    new ShopcartCacheProduct(id, productCount, new Date(), true, activityChannel);

            if (Constants.PRESELL_BUY.equals(buyType)) {
                PresellProductVO vo = presellCacheUtil.getPresellCache(Long.valueOf(id));
                if (null == vo || new Date().after(vo.getDepositEndTime())) {
                    String result =
                            "<script type='text/javascript'>alert('预售活动已结束');history.go(-1);</script>";
                    this.showpage(result);
                    return null;
                } else {
                    BigDecimal depositTotalPrice =
                            vo.getDepositPrice().multiply(new BigDecimal(productCount));
                    BigDecimal finalTotalPrice =
                            vo.getFinalPrice().multiply(new BigDecimal(productCount));
                    request.setAttribute("depositPrice", depositTotalPrice);
                    request.setAttribute("finalPrice", finalTotalPrice);
                    payMoneyInfo.getPayMoneyPresell().setDepositPrice(vo.getDepositPrice());
                    payMoneyInfo.getPayMoneyPresell().setFinalPrice(vo.getFinalPrice());
                    payMoneyInfo.getPayMoneyPresell().setDepositTotalPrice(depositTotalPrice);
                    payMoneyInfo.getPayMoneyPresell().setFinalTotalPrice(finalTotalPrice);
                    payMoneyInfo.getPayMoneyPresell().setPresellId(vo.getPresellId());
                }
            }

            shopCart = shopCartInfoService.generateSettlementForPressell(user, prodcut, buyType);


        } else if (buyType != null && buyType.equals(Constants.PRESCRIPTION_BUY)) {

        } else {
            // buyType为null，说明是普通购买流程
            String idsCacheKey = ConfigurationUtil.getString("b2b_sett_ids_prex") + uid;
            String[] productIdArray = null;
            String ids = request.getParameter("ids");
            if (StringUtil.isEmpty(ids)) {
                productIdArray = (String[]) memCachedClient.get(idsCacheKey);
            } else {
                productIdArray = ids.split(",");
            }
            if (StringUtil.isEmpty(productIdArray)) {
                return INPUT;
            }
            memCachedClient.set(idsCacheKey, productIdArray);
            shopCart = shopCartInfoService.generateSettlement(user, productIdArray);
        }

        if (shopCart == null) {
            return INPUT;
        }

        Map<String, ShopCartProduct> productMap = shopCart.getProductMap();
        if (productMap == null || productMap.isEmpty()) {
            return ERROR;
        } else {
            for (ShopCartProduct product : productMap.values()) {
                if (null != product && !StringUtil.isEmpty(product.getActivityChannel())
                        && "OTC".equals(product.getActivityChannel())) {
                    request.setAttribute("activityChannel", product.getActivityChannel());
                    break;
                }
            }
        }


        request.setAttribute("productMap", shopCart.getProductMap());

        // 商品校验异常
        Map<String, ShopCartProductReminder> map = shopCart.getProductErrorReminder();
        if (map != null && !map.isEmpty()) {
            String result = "";
            if (buyType != null && (Constants.EASY_BUY.equals(buyType)
                    || Constants.PRESELL_BUY.equals(buyType))) {
                ShopCartProductReminder sp = map.values().iterator().next();
                String message = sp.getMessage();
                result = "<script type='text/javascript'>alert('" + message
                        + "');history.go(-1);</script>";
            } else {
                result = "<script type='text/javascript'>alert('存在无法购买的商品，请在购物车页面检查');"
                        + "window.location.href='/cart/listWapShopCar.action';</script>";
            }
            this.showpage(result);
            return null;
        }

        if (shopCart.getMeetOrderPromotionList() != null) {
            Map<String, Integer> giftStockMap = shopCart.getGiftStockMap();
            for (ShopCartItem item : shopCart.getMeetOrderPromotionList()) {
                String result = "";
                if (item.getGifts() != null) {
                    Iterator<Gift> giftItertor = item.getGifts().iterator();
                    while (giftItertor.hasNext()) {
                        Gift gift = giftItertor.next();
                        String id = gift.getId();
                        if (giftStockMap.get(id) == null
                                || giftStockMap.get(id).compareTo(gift.getAmount()) < 0) {
                            result = "<script type='text/javascript'>alert('存在无法购买的加价购商品，请在购物车页面检查');"
                                    + "window.location.href='/cart/listWapShopCar.action';</script>";
                            break;
                        }
                    }
                }

                if (!result.equals("")) {
                    this.showpage(result);
                    return null;
                }
            }
        }

        // 每次只能结算一个商家
        SellerShop sellerShop = null;
        if (shopCart.getSellerShopList() == null || shopCart.getSellerShopList().size() <= 0) {
            return ERROR;
        } else {
            Iterator<Entry<Long, SellerShop>> it =
                    shopCart.getSellerShopList().getSellerShopMap().entrySet().iterator();
            while (it.hasNext()) {
                Entry<Long, SellerShop> entry = it.next();
                sellerShop = entry.getValue();
                break;
            }
        }
        Assert.notNull(sellerShop, "结算商家为空");
        Long checkSellerId = sellerShop.getSellerId();
        boolean isTimeLogin = Constants.LOGINTYPE.equals(loginType);// 时代用户登录
        /*
         * boolean isTimesShop = sellerShop.getSupplierType() == Constants.SUPPLIERTYPE;// 时代购物 try
         * { // 当结算商家类型为时代会员，重新设置折扣率和折扣金额 if (isTimeLogin && isTimesShop) {} } catch (Exception e) {
         * logger.error("",e); }
         */

        if (!isTimeLogin && sellerShop.getSupplierType() == Constants.SUPPLIERTYPE) {
            this.showpage(
                    "<script type='text/javascript'>alert('普通会员不能购买康美中药城产品！');history.go(-1);</script>");
            return null;
        }

        isLoginType = isTimeLogin;
        request.setAttribute("priceInfo", sellerShop);
        Double fare = 0d;

        try {
            if (0 == ShopCar.SELF_AND_PROXY_KEY.compareTo(checkSellerId)) {
                checkSellerId = Constants.SELF_SELLER_ID;// 生产库自营为221
            }
            // 判断是否登录
            accountInfo = accountinfoService.findByLoginId(loginID);
            // 获取收货人信息

            // Boolean defaultAddressFlag = false;
            addressList = this.addressService.findByLoginId(loginID.intValue());
            Address defaultAddr = addressService.findDefaultAddressByLoginId(loginID);
            if (defaultAddr != null) {
                this.addressService.addAddressTomem(loginID.toString(), defaultAddr);
            }
            //
            // if (null != defaultAddr && !StringUtil.isEmpty(defaultAddr.getProvince())
            // && defaultAddr.getProvince().equals("广东")) {
            // // TODO 设置了未使用？
            // defaultAddressFlag = true;
            // }

            Map<String, Object> couponParams = new HashMap<String, Object>();
            List<OrderVo> orderVoList = ShopCartUtil.getShopCarOrderList(shopCart);
            couponParams.put("customId", loginID);// 用户ID
            couponParams.put("checkSellerId", checkSellerId);// 选中的商家ID
            couponParams.put("orderList", orderVoList);// 产品List
            couponParams.put("moneyCount", sellerShop.getUncalculateMoney().doubleValue());// 金额
            couponParams.put("channel", ConfigurationUtil.getString("CHANNEL"));// 渠道
            try {
                // 可用
                // couponList = this.accountinfoService.findCouponGrants(couponParams,
                // loginID.toString());
                // CouponRemoteService couponRemoteService =
                // (CouponRemoteService) RemoteTool.getRemote(Constants.PROMOTION_SYSTEM_CODE,
                // "couponService", 120000l, 120000l);
                HashMap<String, List<HashMap<String, String>>> couponsMap =
                        couponRemoteService.getCanUseAndUnUseCoupon(orderVoList, loginID.toString(),
                                new BigDecimal(sellerShop.getUncalculateMoney() + ""),
                                Long.parseLong(checkSellerId + ""));
                // 将优惠券数据缓存到memCached中，以sessionId为维度,提交订单的时候判断选择的优惠券是否在可以使用范围内
                this.accountinfoService.savaCouponsListCached(couponsMap, session.getId());
                // 获取可用优惠券
                couponList = couponsMap.get("canUseCouponList");
            } catch (Exception e) {
                logger.error("获取可用优惠券发生异常！", e);
            }
            payRangeList = new ArrayList<String>();
            if (null != accountInfo.getPayRange()) {
                String str[] = accountInfo.getPayRange().split(",");
                payRangeList = Arrays.asList(str);
            }
            // 支付信息
            payModelList = this.OrderDictonaryService.getPaymentModel();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ERROR;
        }

        // 查询活动积分规则
        // int score = 0;
        /*
         * try { score = scoreRuleService.getBuyScore(shopCart.getCheckTotalMoney()); } catch
         * (Exception e) { score = shopCart.getCheckTotalMoney().intValue();
         * logger.error("查询积分规则报错：" + e); }
         */

        // 运费
        fare = shopCart.getFreight().doubleValue();
        // 付款金额 = 总金额-满减+运费-使用优惠券-余额支付-康美中药城会员率

        payMoneyInfo.setScore(0);
        payMoneyInfo.setMoneyCount(sellerShop.getUncalculateMoney().doubleValue());

        if (Constants.PRESELL_BUY.equals(buyType)) {
            // 预售订单运费为0
            payMoneyInfo.setFare(new Double(0));
        } else {
            payMoneyInfo.setFare(fare);
        }

        payMoneyInfo.setReductMoney(sellerShop.getReductionMoney().doubleValue());
        payMoneyInfo.setAmountAvlibal(accountInfo.getAmountAvlibal());
        payMoneyInfo.setAdditionalMoney(sellerShop.getAdditionalMoney());
        // 康美中药城折扣率
        payMoneyInfo.setDiscountMoney(sellerShop.getDiscountMoney());


        this.accountinfoService.savePayMoneyToMemCache(loginID.toString(), payMoneyInfo);
        try {
            setDeliveryInfo("5", "3", "0");;
        } catch (Exception e) {
            logger.error("", e);
        }

        // 保存结算信息
        SettlementInfo info = new SettlementInfo();
        info.setSubTotal(shopCart);
        // mkw 20151019 modiy 激活优惠失败，产品信息为空
        info.setOrderVoList(ShopCartUtil.getShopCarOrderList(shopCart));
        // end
        boolean success = super.saveSettlementInfo(info);
        if (!success) {
            logger.error("结算页面保存结算信息到memcache失败");
            return ERROR;
            // log.error("结算页面保存结算信息到memcache失败");
        }

        String queryString = request.getQueryString();
        boolean isWxRequest = false;// 微信版本
        boolean isWxBrowse = false; // 微信浏览器标识
        String userAgent = request.getHeader("user-agent");
        if (userAgent.indexOf("MicroMessenger") > 0) {
            isWxBrowse = true;
            char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15); // 微信版本号
            if (String.valueOf(agent).compareTo("5") >= 0) {
                isWxRequest = true;
            }
        }

        if (isWxRequest && queryString != null && !queryString.isEmpty()
                && queryString.contains("code") && queryString.contains("state")) {
            this.prepareSignForWxAddress(request, ServletActionContext.getResponse());
        }

        request.setAttribute("isWxBrowse", isWxBrowse);
        request.setAttribute("appId", ConfigurationUtil.getString("wx_appid")); // 此字段为appId唯一标识
        request.setAttribute("shopCart", shopCart);
        return SUCCESS;
    }

    /**
     * 
     * <p>
     * Title: addAddressInfo
     * </p>
     * <p>
     * Description: 添加和编辑收货地址
     * </p>
     * 
     * @return
     */
    public String addWapAddressInfo() {
        boolean isTimeLogin =
                Constants.LOGINTYPE.equals(getSession().getAttribute(Constants.SESSION_B2B_OR_ERA));// 时代用户登录
        try {
            Address address = null;
            Long loginId = super.getUserloginId();
            addressList = this.addressService.findByLoginId(loginId.intValue());
            // 获取account
            int accountId = 0;
            if (null == addressList || addressList.isEmpty()) {
                AccountInfo ai = accountinfoService.findByLoginId(loginId);
                accountId = ai.getNaccountId();
            } else {
                accountId = addressList.get(0).getAccountId();
            }

            if (null == addressList) {
                addressList = new ArrayList<Address>();
            }

            Date date = new Date();
            int defaultId = 0;
            if (StringUtil.isEmpty(defaultAddressId)) {
                // 新增
                address = new Address();
                address.setProvince(province);
                address.setCity(city);
                address.setArea(area);
                address.setName(name);
                address.setPostalcode(postalcode);
                address.setDetailedAddress(detailedAddress);
                address.setTelephone(telephone);
                address.setAccountId(accountId);
                address.setLoginId(loginId.intValue());
                address.setCreatedate(date);
                address.setLastupdate(date);
                address.setCellphone(mobile);
                address.setEmail(email);
                address.setIsChecked(true);
                address.setStatus(addressList.isEmpty() ? 0 : 1);
                Integer addressId = addressService.save(address);
                address.setAddressId(addressId);
                addressList.add(address);
                defaultId = addressId;
            } else {
                defaultId = Integer.parseInt(defaultAddressId);
                if (null != naddressId) {
                    for (int i = 0, len = addressList.size(); i < len; i++) {
                        Address addr = addressList.get(i);
                        if (null != naddressId && naddressId == addr.getAddressId()) {
                            address = addr;
                            address.setIsChecked(true);
                            if (null != naddressId) {
                                address.setProvince(province);
                                address.setCity(city);
                                address.setArea(area);
                                address.setName(name);
                                address.setPostalcode(postalcode);
                                address.setDetailedAddress(detailedAddress);
                                address.setTelephone(telephone);
                                address.setAccountId(accountId);
                                address.setLoginId(loginId.intValue());
                                address.setAddressId(naddressId);
                                address.setCellphone(mobile);
                                address.setEmail(email);
                                address.setLastupdate(date);
                                addressService.update(address);
                            }
                            addressList.set(i, address);
                            break;
                        }
                    }
                }
            }


            for (int i = 0, len = addressList.size(); i < len; i++) {
                Address addr = addressList.get(i);
                addr.setIsChecked(false);
                addr.setStatus(1);
                if (defaultId == addr.getAddressId()) {
                    // 新的选中
                    address = addr;
                    address.setIsChecked(true);
                    addressList.set(i, address);
                }
            }

            if (null != address) {
                addressService.addAddressTomem(String.valueOf(loginId), address);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        isLoginType = isTimeLogin;

        // 20150716 mlq add begin 用于结算页是否显示微信地址获取按钮
        boolean isWxBrowse = false; // 该参数用来标识是否是微信浏览器,如果是微信浏览器,则结算页的使用微信浏览器会做显示,反之不做显示
        String userAgent = this.getRequest().getHeader("user-agent");
        if (userAgent.indexOf("MicroMessenger") > 0) { // 是微信浏览器
            isWxBrowse = true;
        }
        this.getRequest().setAttribute("isWxBrowse", isWxBrowse);
        this.getRequest().setAttribute("appId", ConfigurationUtil.getString("wx_appid")); // 此字段为appId唯一标识
        // 20150716 mlq add end
        return SUCCESS;
    }


    /**
     * 修改配送和支付信息
     * 
     * @return
     */
    public String savePaymodelDeliveryInfo() {
        HttpServletRequest request = this.getRequest();
        paymodelvalue = request.getParameter("paymodelvalue");
        deliveryTimeValue = request.getParameter("deliveryTimeValue");
        isconfirmValue = request.getParameter("isconfirmValue");
        setDeliveryInfo(paymodelvalue, deliveryTimeValue, isconfirmValue);
        return SUCCESS;
    }

    /**
     * 保存配送和支付信息到缓存中
     * 
     * @param paymodelvalue
     * @param deliveryTimeValue
     * @param isconfirmValue
     */
    private void setDeliveryInfo(String paymodelvalue, String deliveryTimeValue,
            String isconfirmValue) {
        OrderDictionary orderDict = new OrderDictionary();
        orderDict.setOrderDictionaryType(Constants.PAY_METHOD);
        orderDict.setOrderDictionaryKey(Long.parseLong(paymodelvalue));
        Long uid = super.getUserloginId();
        String usessinId = null == uid ? getSession().getId() : uid.toString();
        Map<String, String> map = new HashMap<String, String>();
        try {
            OrderDictionary dict =
                    this.OrderDictonaryService.getOrderDictionaryByOrderDict(orderDict);
            map.put("orderDictionaryValue", dict.getOrderDictionaryValue());

            orderDict.setOrderDictionaryType(Constants.DELIVERY_DATE_TYPE);
            orderDict.setOrderDictionaryKey(Long.parseLong(deliveryTimeValue));

            dict = this.OrderDictonaryService.getOrderDictionaryByOrderDict(orderDict);

            map.put("deliveryTimeValue", dict.getOrderDictionaryValue());
            map.put("deliveryTimeValue", dict.getOrderDictionaryValue());
        } catch (SQLException e) {
            logger.error("查询订单支付信息错误. OrderDictionaryType=" + orderDict.getOrderDictionaryType()
                    + ",OrderDictionaryKey=" + orderDict.getOrderDictionaryKey(), e);
        }

        try {
            map.put("orderDictionaryKey",
                    new String(paymodelvalue.getBytes("ISO-8859-1"), "UTF-8"));
            map.put("deliveryTime", new String(deliveryTimeValue.getBytes("ISO-8859-1"), "UTF-8"));
            map.put("isconfirm", new String(isconfirmValue.getBytes("ISO-8859-1"), "UTF-8"));
            accountinfoService.savePaymodelDeliveryInfo(usessinId, map);
        } catch (Exception e) {
            logger.error("保存配送和支付信息失败！");
            this.returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
        }
        this.returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", null);
    }

    /**
     * 20150709 mlq add begin 为微信地址签名以及config首次签名认证做准备
     * 
     * @return
     */
    public String prepareSignForWxAddress(HttpServletRequest request,
            HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        String code = request.getParameter("code"); // 用户授权获得的code
        String state = request.getParameter("state");// 标识微信wap登录来源
        logger.info("WxAddressAction   getAccessTokenByCode() code=" + code + ",state=" + state);
        // 用户取消授权未获取到code
        if (code == null || code.isEmpty()) {
            logger.error("code is null");
            String result =
                    "<script type='text/javascript'>alert('调用地址api前需要用户授权,请求用户授权过程中code为空');history.go(-1);</script>";
            this.showpage(result);
            return null;
        }
        logger.info("获取微信返回回来的code=" + code);
        /******* 用于config签名begin ********/
        // 待完善的地方1,将access_token进行缓存,调用次数过于频繁可能会导致api调用受限制
        String accessToken = (String) getSession().getAttribute("baseAccessToken");
        if (accessToken == null || accessToken.isEmpty() || "".equals(accessToken)
                || "null".equals(accessToken)) {
            accessToken = BaseUtil.getToken();
            if (accessToken == null) {
                String result =
                        "<script type='text/javascript'>alert('获取基础调用accessToken发生错误!');history.go(-1);</script>";
                this.showpage(result);
                return null;
            }
            getSession().setAttribute("baseAccessToken", accessToken); // 待完善的地方,需要判断该token是否已经过期
        }
        // 用accessToken获取jsapi_ticket
        // //待完善的地方2,将access_token进行缓存,调用次数过于频繁可能会导致api调用受限制
        String jsApiTicket = (String) getSession().getAttribute("jsApiTicket");
        if (jsApiTicket == null || jsApiTicket.isEmpty() || "".equals(jsApiTicket)
                || "null".equals(jsApiTicket)) {
            JSONObject jsonObj = BaseUtil.getJsApiTicket(accessToken);
            if (jsonObj.get("ticket") == null || "".equals(jsonObj.get("ticket").toString())
                    || jsonObj.get("ticket").toString().isEmpty()) {
                String result =
                        "<script type='text/javascript'>alert('使用token去获取jsapi_ticket发生错误,详情如下='"
                                + jsonObj.get("errcode") + "');history.go(-1);</script>";
                this.showpage(result);
                return null;
            }
            String jsapiTicket = jsonObj.getString("ticket");
            getSession().setAttribute("jsApiTicket", jsapiTicket);
        }
        String nonceStr = CommonUtil.CreateNoncestr();
        String timeStamp = Long.toString(new Date().getTime());
        // 获取当前请求的url
        String signUrl = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        // 获取需要参与签名的调用获取地址的页面的url
        logger.info("获取参与签名的signUrl=" + signUrl + ",该参数所带的queryString字符串=" + queryString);
        // 拼上参数参与签名
        if (queryString != null && !queryString.isEmpty() && queryString.length() > 0) {
            signUrl = signUrl + "?" + queryString;
        }
        logger.info("获取参与签名的signUrl拼接queryString完整的显示=" + signUrl);
        /******* 用于config签名begin ********/
        SortedMap<String, String> signParamsForConfig = new TreeMap<String, String>();
        signParamsForConfig.put("url", signUrl);
        signParamsForConfig.put("nonceStr", nonceStr);
        signParamsForConfig.put("timeStamp", timeStamp);
        signParamsForConfig.put("jsapi_ticket", jsApiTicket);

        request.setAttribute("appId", ConfigurationUtil.getString("wx_appid"));
        request.setAttribute("nonceStr", nonceStr);
        request.setAttribute("timeStamp", timeStamp);
        request.setAttribute("signature", getSign(signParamsForConfig));

        /******* 用于config签名end ********/
        // 获取授权用的accessToken
        JSONObject jsonObject = BaseUtil.getAccessTokenByCode(code);
        if (jsonObject.get("errcode") != null) {
            logger.error("用code获取accessToken错误详情如下,errcode=" + jsonObject.get("errcode"));
            this.showpage("用code获取accessToken错误详情如下,errcode=" + jsonObject.get("errcode"));
            return null;
        }
        String token = jsonObject.getString("access_token");
        // 使用排序的map把需要签名的参数拼接成url键值对的形式
        SortedMap<String, String> signParamsForAddr = new TreeMap<String, String>();
        signParamsForAddr.put("appId", ConfigurationUtil.getString("wx_appid"));
        signParamsForAddr.put("url", signUrl);
        signParamsForAddr.put("timeStamp", timeStamp);
        signParamsForAddr.put("nonceStr", nonceStr);
        signParamsForAddr.put("accessToken", token);

        request.setAttribute("addrSign", getSign(signParamsForAddr));
        request.setAttribute("signUrl", signUrl);
        request.setAttribute("scope", "jsapi_address");
        request.setAttribute("signType", "sha1");
        request.setAttribute("isWxAddrRequest", state); // 标识是第一次请求,之后再次请求就直接从那个隐藏域中拿
        return SUCCESS;
    }

    /**
     * 20150709 add mlq 返回sha1加密后的参与签名的字符串 记得参与签名的参数名都应变成小写
     */
    public String getSign(Map<String, String> reqMap) {
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<String, String>> keysIt = reqMap.entrySet().iterator();
        while (keysIt.hasNext()) {
            Entry<String, String> entry = keysIt.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (!StringUtil.isEmpty(value)) {
                sb.append(key.toLowerCase()).append('=').append(value).append('&');
            }
        }
        String signAfterSha1 = Sha1Util.sha1(sb.substring(0, sb.length() - 1));
        return signAfterSha1;
    }

    /**
     * 20150714 add begin 当用户是微信环境并且将该地址放入到缓存中
     * 
     * @return
     */
    public String useWxAddress() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long loginId = super.getUserloginId();
        if (loginId == null) {
            logger.error("useWxAddress用户尚未登录,将微信地址放入到缓存中失败");
            return ERROR;
        }

        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String area = request.getParameter("area");
        String detailedAddress = request.getParameter("detailedAddress");
        String mobile = request.getParameter("mobile");
        String name = request.getParameter("receiver");
        String postalcode = request.getParameter("postalcode");

        Address wxAddr = new Address();
        wxAddr.setName(name);
        wxAddr.setProvince(province);
        wxAddr.setCity(city);
        wxAddr.setArea(area);
        wxAddr.setDetailedAddress(detailedAddress);
        wxAddr.setCellphone(mobile);
        wxAddr.setPostalcode(postalcode);
        wxAddr.setAccountId(loginId.intValue());

        this.addressService.addAddressTomem(loginId.toString(), wxAddr);
        PrintWriter out = null;
        try {
            out = getResponse().getWriter();
            out.print("ok");
            out.flush();
        } catch (IOException e) {
            logger.error("useWxAddress==>像客户端写入提示信息失败,详情如下=" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /** 获取推广信息 */
    /*
     * private String getMarketingInfo(String skuId) { com.alibaba.fastjson.JSONObject rulejson =
     * com.alibaba.fastjson.JSONObject.parseObject(ShopCartUtil.getCookieValue( this.getRequest(),
     * "sruleId")); if (rulejson == null) { return null; } return rulejson.getString(skuId); }
     */

    public ReturnResult<HashMap<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<HashMap<String, Object>> returnResult) {
        this.returnResult = returnResult;
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

    public List<OrderDictionary> getDeliveryTimeList() {
        return deliveryTimeList;
    }

    public void setDeliveryTimeList(List<OrderDictionary> deliveryTimeList) {
        this.deliveryTimeList = deliveryTimeList;
    }

    public List<OrderDictionary> getPayModelList() {
        return payModelList;
    }

    public void setPayModelList(List<OrderDictionary> payModelList) {
        this.payModelList = payModelList;
    }

    public PayMoney getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(PayMoney payMoney) {
        this.payMoney = payMoney;
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

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public String getIsconfirmValue() {
        return isconfirmValue;
    }

    public void setIsconfirmValue(String isconfirmValue) {
        this.isconfirmValue = isconfirmValue;
    }

    public List<HashMap<String, String>> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<HashMap<String, String>> couponList) {
        this.couponList = couponList;
    }

    public String getBuyType() {
        return buyType;
    }

    public void setBuyType(String buyType) {
        this.buyType = buyType;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public String getProductSkuIDs() {
        return productSkuIDs;
    }

    public void setProductSkuIDs(String productSkuIDs) {
        this.productSkuIDs = productSkuIDs;
    }

    public PayMoney getPayMoneyInfo() {
        return payMoneyInfo;
    }

    public void setPayMoneyInfo(PayMoney payMoneyInfo) {
        this.payMoneyInfo = payMoneyInfo;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public Long getLoginID() {
        return loginID;
    }

    public void setLoginID(Long loginID) {
        this.loginID = loginID;
    }

    public Boolean getIsLoginType() {
        return isLoginType;
    }

    public void setIsLoginType(Boolean isLoginType) {
        this.isLoginType = isLoginType;
    }

    public List<String> getPayRangeList() {
        return payRangeList;
    }

    public void setPayRangeList(List<String> payRangeList) {
        this.payRangeList = payRangeList;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getDefaultAddressId() {
        return defaultAddressId;
    }

    public void setDefaultAddressId(String defaultAddressId) {
        this.defaultAddressId = defaultAddressId;
    }

    public String getPrescriptionAttachmentFile() {
        return prescriptionAttachmentFile;
    }

    public void setPrescriptionAttachmentFile(String prescriptionAttachmentFile) {
        this.prescriptionAttachmentFile = prescriptionAttachmentFile;
    }

    public Map<Long, CarProduct> getCarProductMap() {
        return carProductMap;
    }

    public void setCarProductMap(Map<Long, CarProduct> carProductMap) {
        this.carProductMap = carProductMap;
    }
}
