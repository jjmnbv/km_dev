package com.kmzyc.b2b.action.orderPay;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;

import com.km.framework.common.util.MD5;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.Address;
import com.kmzyc.b2b.model.CommercialTenantBasicInfo;
import com.kmzyc.b2b.model.Coupon;
import com.kmzyc.b2b.model.CouponGrant;
import com.kmzyc.b2b.model.InvoiceInfo;
import com.kmzyc.b2b.model.OrderDictionary;
import com.kmzyc.b2b.model.PayCommonObject;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.AccountInfoService;
import com.kmzyc.b2b.service.CarProductService;
import com.kmzyc.b2b.service.CommercialTenantBasicInfoService;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.OrderDictonaryService;
import com.kmzyc.b2b.service.OrderItemService;
import com.kmzyc.b2b.service.ShopCartInfoService;
import com.kmzyc.b2b.service.member.AddressService;
import com.kmzyc.b2b.service.member.MyCouponService;
import com.kmzyc.b2b.service.member.MyOrderService;
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
import com.kmzyc.b2b.util.SupplierType;
import com.kmzyc.b2b.util.redis.ProductRedisUtil;
import com.kmzyc.b2b.vo.CarItemView;
import com.kmzyc.b2b.vo.CarProduct;
import com.kmzyc.b2b.vo.PayMoney;
import com.kmzyc.b2b.vo.PayMoneyPresell;
import com.kmzyc.b2b.vo.ProductPromotionInfo;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.b2b.vo.SellerPriceInfo;
import com.kmzyc.framework.ajax.AjaxUtil;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.order.remote.OrderCreateRemoteService;
import com.kmzyc.order.remote.OrderRiskRemoteService;
import com.kmzyc.promotion.app.vobject.OrderVo;
import com.kmzyc.promotion.optimization.vo.PresellProductVO;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.kmzyc.promotion.util.PresellCacheUtil;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderMainExt;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.vobject.OrderPreferentialVO;

import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
@SuppressWarnings("unchecked")
public class SettlementAction extends SettleMentBaseAction {
    private static Logger log = LoggerFactory.getLogger(SettlementAction.class);
    private static final long serialVersionUID = -656417878929304275L;
    private static final String ACCOUNT_INFO = "b2b_com_km_b2b_model_Account_ID_";

    @Resource(name = "accountInfoServiceImp")
    private AccountInfoService accountinfoService;
    @Resource(name = "addressServiceImpl")
    private AddressService addressService;
    @Resource(name = "orderDictonaryServiceImpl")
    private OrderDictonaryService OrderDictonaryService;
    @Resource(name = "loginServiceImp")
    private LoginService loginService;
    @Resource(name = "commercialTenantBasicInfoService")
    private CommercialTenantBasicInfoService commercialTenantBasicInfoService;
    @Resource(name = "carProductService")
    private CarProductService carProductService;
    @Resource(name = "myCouponServiceImpl")
    private MyCouponService couponService;
    @Resource
    private OrderItemService orderItemService;

    public static AtomicInteger ORDER_COUNT = new AtomicInteger(0);

    @Autowired
    public OrderCreateRemoteService orderCreateRemoteService;

    @Resource(name = "shopCartInfoService")
    private ShopCartInfoService shopCartInfoService;

    @Resource
    private CouponRemoteService couponRemoteService;

    @Resource
    private PresellCacheUtil presellCacheUtil;

    @Resource
    private ProductRedisUtil productRedisUtil;

    private List<Address> addressList = new ArrayList<Address>();
    @Resource
    private MyOrderService myOrderService;

    @Resource
    private OrderRiskRemoteService orderRiskRemoteService;


    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    private List<Address> isCheckAddressList = new ArrayList<Address>();
    private Map<String, String> defaultPayAndDelivery = new HashMap<String, String>();

    private List<CarItemView> carItemViews = new ArrayList<CarItemView>();
    private ProductPromotionInfo productPromotionInfo = new ProductPromotionInfo();
    private Map<String, ShopCartProduct> carProductMap1 = new HashMap<String, ShopCartProduct>();
    private Map<String, ShopCartProduct> carPromotionProductMap =
            new HashMap<String, ShopCartProduct>();
    Map<Long, CarProduct> timeProductMap = new HashMap<Long, CarProduct>();

    private List<String> payRangeList = new ArrayList<String>();

    private InvoiceInfo invoiceInfo = new InvoiceInfo();
    private AccountInfo accountInfo = new AccountInfo();
    private Address address = new Address();
    private Boolean isLoginType;

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
    // private Long loginID = null;
    private String action = null;
    private String defaultAddressId = null;
    private Double fare = 0d;

    private String paymodelvalue = null;
    private String deliveryTimeValue = null;
    private String isconfirmValue = null;
    private String paymodeltext = null;
    // 余额支付
    private Long balance = Constants.PAY_METHOD_BALANCE;
    private Long preferential = Constants.PAY_METHOD_PREFERENTIAL;
    private String password = null;

    private String orderDescription = null;// 订单备注
    private User user = new User();

    private Integer wapPay = 0;
    private String img_path;

    private String productName = "";

    private boolean payFlag = false;
    // 订单获取的优惠券金额
    private String couponMoney;
    private PayCommonObject payCommon = null;
    // 订单积分
    private Integer credits = null;

    private String buyType = "";
    private int productCount = 0;
    private Long productSkuId = null;
    private String productSkuIDs = null;
    private PayMoney payMoneyInfo = null;
    private CommercialTenantBasicInfo commercialTenantBasicInfo = null;

    // 显示路径
    // private String showPath = ConfigurationUtil.getString("IMG_SHOW_PATH");

    // 药方图片路径
    private String prescriptionAttachmentFile = null;

    // 返回至页面的对象
    private ReturnResult returnResult;
    // 是否是默认的收货地址
    private String isMoren;

    private String type;

    public String gotoSettlement() {
        Long userId = super.getUserloginId();
        if (userId == null) {
            return LOGIN;
        }
        HttpServletRequest request = this.getRequest();
        String buyType = request.getParameter("type");// 请求参数
        request.setAttribute("buyType", buyType);
        // 风控黑名单控制
        try {
            if (orderRiskRemoteService.queryOrderRisk(userId)) {

                String result = null;
                if (buyType != null && Constants.EASY_BUY.equals(buyType)) {
                    result = "<script type='text/javascript'>alert('抱歉，当前账号已被锁定无法下单！如有疑问，请与商城客服联系！');history.go(-1);</script>";
                } else {
                    result = "<script type='text/javascript'>alert('抱歉，当前账号已被锁定无法下单！如有疑问，请与商城客服联系！');"
                            + "window.location.href='/cart/listShopCar.action';</script>";
                }

                this.showpage(result);
                return null;
            }
        } catch (Exception e) {
            log.error("查询风控黑名单异常！", e);
        }


        String userIdStr = String.valueOf(userId);
        // 加载结算时，删除优惠券缓存数据
        this.accountinfoService.delCouponsListCached(getSession().getId());

        this.defaultAddressId = null;
        this.naddressId = null;

        String activityChannel = request.getParameter("activityChannel");
        if (StringUtil.isEmpty(activityChannel)) {
            activityChannel = "";
        }


        ShopCart shopCart = null;
        // 判断登陆类型，01表示普通会员，02 表示康美中药城会员
        String loginType = (String) getSession().getAttribute(Constants.SESSION_B2B_OR_ERA);

        ShopCartUserInfo shopCartUser =
                new ShopCartUserInfo(userIdStr, isLogin(), loginType, buyType);
        // 轻松购（立即购买，不经过购物车页面直接到结算页面）
        if (buyType != null && Constants.EASY_BUY.equals(buyType)
                || Constants.PRESELL_BUY.equals(buyType)) {
            String id = request.getParameter("productSkuId");
            String productCountString = request.getParameter("productVary");
            productCount = 1;
            if (!StringUtil.isEmpty(productCountString)) {
                productCount = Integer.parseInt(productCountString);
            }

            ShopcartCacheProduct prodcut =
                    new ShopcartCacheProduct(id, productCount, new Date(), true, activityChannel);
            shopCart = shopCartInfoService.generateSettlement(shopCartUser, prodcut);
        } else if (buyType != null && buyType.equals(Constants.PRESCRIPTION_BUY)) {
            // 处方药（立即购买，不经过购物车页面直接到结算页面）
        } else {// buyType为null，说明是普通购买流程
            String idsCacheKey = ConfigurationUtil.getString("b2b_sett_ids_prex").concat(userIdStr);
            String[] productIdArray = null;
            String ids = request.getParameter("ids");
            if (StringUtil.isEmpty(ids)) {
                productIdArray = (String[]) memCachedClient.get(idsCacheKey);
            } else {
                productIdArray = ids.split(",");
            }
            if (productIdArray == null || productIdArray.length == 0) {
                return INPUT;
            }
            memCachedClient.set(idsCacheKey, productIdArray);
            shopCart = shopCartInfoService.generateSettlement(shopCartUser, productIdArray);
        }

        if (shopCart == null) {
            return INPUT;
        }
        // 商品列表
        carPromotionProductMap = shopCart.getProductMap();
        if (carPromotionProductMap == null || carPromotionProductMap.isEmpty()) {
            return ERROR;
        } else {
            for (ShopCartProduct product : carPromotionProductMap.values()) {
                if (null != product && !StringUtil.isEmpty(product.getActivityChannel())
                        && "OTC".equals(product.getActivityChannel())) {
                    request.setAttribute("activityChannel", product.getActivityChannel());
                    break;
                }
            }
        }

        // 商品校验异常
        Map<String, ShopCartProductReminder> map = shopCart.getProductErrorReminder();
        if (map != null && !map.isEmpty()) {
            String result = null;
            if (buyType != null && Constants.EASY_BUY.equals(buyType)) {
                ShopCartProductReminder sp = map.values().iterator().next();
                result = "<script type='text/javascript'>alert('" + sp.getMessage()
                        + "');history.go(-1);</script>";
            } else {
                result = "<script type='text/javascript'>alert('存在无法购买的商品，请在购物车页面检查');"
                        + "window.location.href='/cart/listShopCar.action';</script>";
            }

            this.showpage(result);
            return null;
        }

        if (shopCart.getMeetOrderPromotionList() != null) {
            Map<String, Integer> giftStockMap = shopCart.getGiftStockMap();
            for (ShopCartItem item : shopCart.getMeetOrderPromotionList()) {
                String result = null;
                if (item.getGifts() != null) {
                    Iterator<Gift> giftItertor = item.getGifts().iterator();
                    while (giftItertor.hasNext()) {
                        Gift gift = giftItertor.next();
                        String id = gift.getId();
                        if (giftStockMap.get(id) == null
                                || giftStockMap.get(id).compareTo(gift.getAmount()) < 0) {
                            result = "<script type='text/javascript'>alert('存在无法购买的加价购商品，请在购物车页面检查');"
                                    + "window.location.href='/cart/listShopCar.action';</script>";
                            break;
                        }
                    }
                }
                if (result != null) {
                    this.showpage(result);
                    return null;
                }
            }
        }

        // 发票
        commercialTenantBasicInfo = commercialTenantBasicInfoService.queryByLoginIdCache(userId);
        SellerShop sellerShop = null;
        if (shopCart.getSellerShopList() == null || shopCart.getSellerShopList().size() <= 0) {
            return ERROR;
        } else {
            Iterator<Entry<Long, SellerShop>> it =
                    shopCart.getSellerShopList().getSellerShopMap().entrySet().iterator();
            while (it.hasNext()) {
                sellerShop = it.next().getValue();
                break;
            }
        }
        if (sellerShop == null) {
            return ERROR;
        }

        Long checkSellerId = sellerShop.getSellerId();
        boolean isTimeLogin = Constants.LOGINTYPE.equals(loginType);// 时代用户登录
        boolean isTimesShop = sellerShop.getSupplierType() == Constants.SUPPLIERTYPE;// 时代购物
        try {
            // 当结算商家类型为时代会员，重新设置折扣率和折扣金额
            if (isTimeLogin && isTimesShop) {
            }
        } catch (Exception e) {
            log.error("", e);
        }
        isLoginType = isTimeLogin;
        request.setAttribute("priceInfo", sellerShop);
        try {
            // 配送时段

            if (0 == Constants.SELF_AND_PROXY_KEY.compareTo(checkSellerId)) {
                checkSellerId = Constants.SELF_SELLER_ID;// 生产库自营为221
            }

            // 查询用户信息，需实时获取最新的 (只使用到3个属性：NaccountId、payRange、amountAvlibal)
            accountInfo = accountinfoService.findByLoginId(userId);
            // 将用户信息添加到缓存中，供结算页查询优惠券使用 add by zhuyanling 20160113
            accountinfoService.savaAccountInfoCached(accountInfo, userIdStr);

            // 获取收货人信息
            addressList = addressService.findByLoginId(userId.intValue());
            Address defaultAddr = addressService.findDefaultAddressByLoginId(userId);
            if (defaultAddr != null) {
                this.addressService.addAddressTomem(userIdStr, defaultAddr);
            }

            /* 加载优惠券 */
            // 加载优惠券getCouponList();独立为一个另一个请求
            /* 加载优惠券 */
            payRangeList = new ArrayList<String>();
            if (null != accountInfo.getPayRange()) {
                String str[] = accountInfo.getPayRange().split(",");
                payRangeList = Arrays.asList(str);
            }

            // 支付信息
            // payModelList = this.OrderDictonaryService.getPaymentModel();
            // 查询活动积分规则
            /*
             * int score = 0; try { score =
             * scoreRuleService.getBuyScore(shopCart.getCheckTotalMoney()); } catch (Exception e) {
             * score = shopCart.getCheckTotalMoney().intValue(); log.error("查询积分规则报错：" + e); }
             */

            // 运费
            fare = shopCart.getFreight().doubleValue();
            // 付款金额 = 总金额-满减+运费-使用优惠券-余额支付-康美中药城会员率
            payMoneyInfo = new PayMoney();
            // payMoneyInfo.setScore(score);
            payMoneyInfo.setMoneyCount(sellerShop.getUncalculateMoney().doubleValue());
            payMoneyInfo.setFare(fare);
            payMoneyInfo.setReductMoney(sellerShop.getReductionMoney().doubleValue());
            payMoneyInfo.setAmountAvlibal(accountInfo.getAmountAvlibal());
            payMoneyInfo.setAdditionalMoney(sellerShop.getAdditionalMoney());
            // 康美中药城折扣率
            payMoneyInfo.setDiscountMoney(sellerShop.getDiscountMoney());

            this.accountinfoService.savePayMoneyToMemCache(userIdStr, payMoneyInfo);
            // 保存结算信息
            SettlementInfo info = new SettlementInfo();
            info.setSubTotal(shopCart);
            info.setOrderVoList(ShopCartUtil.getShopCarOrderList(shopCart));
            boolean success = super.saveSettlementInfo(info);
            if (!success) {
                throw new Exception("结算页面保存结算信息到memcache失败");
                // log.error("结算页面保存结算信息到memcache失败");
            }
            request.setAttribute("shopCart", shopCart);

            if (!StringUtil.isEmpty(request.getParameter("activityChannel"))) {
                request.setAttribute("activityChannel", request.getParameter("activityChannel"));
            }

        } catch (Exception e) {
            log.error("", e);
            return INPUT;
        }

        return SUCCESS;
    }
    /*
    *//** 获取推广信息 *//*
                    * private String getMarketingInfo(String skuId) {
                    * com.alibaba.fastjson.JSONObject rulejson =
                    * com.alibaba.fastjson.JSONObject.parseObject(ShopCartUtil.getCookieValue(
                    * this.getRequest(), "sruleId")); if (rulejson == null) { return null; } return
                    * rulejson.getString(skuId); }
                    */

    /**
     * 加载优惠券
     */
    public String getCouponList() {
        HttpServletRequest request = getRequest();
        try {
            /* 加载优惠券 */
            Object sessionUserId = getSession().getAttribute(Constants.SESSION_USER_ID);
            Long loginID = (Long) sessionUserId;
            SettlementInfo settlementInfo = super.getSettlementInfoCache();
            ShopCart shopcart = (ShopCart) settlementInfo.getSubTotal();

            Long checkSellerId =
                    shopcart.getSellerShopList().getSellerShopMap().keySet().iterator().next();
            if (0 == Constants.SELF_AND_PROXY_KEY.compareTo(checkSellerId)) {
                checkSellerId = 221L;// 生产库自营为221
            }
            Map<String, Object> couponParams = new HashMap<String, Object>();
            List<OrderVo> orderVoList = ShopCartUtil.getShopCarOrderList(shopcart);
            couponParams.put("customId", loginID);// 用户ID
            couponParams.put("checkSellerId", checkSellerId);// 选中的商家ID
            couponParams.put("orderList", orderVoList);// 产品List
            couponParams.put("moneyCount", shopcart.getUncalculateMoney());// 金额
            couponParams.put("channel", ConfigurationUtil.getString("CHANNEL"));// 渠道
            // 可用
            List<Coupon> couponList =
                    this.accountinfoService.findCouponGrants(couponParams, loginID.toString());
            Map<String, Object> couponResult = new HashMap<String, Object>();
            couponResult.put("inUseCouponList", couponList);
            couponResult.put("inUseCouponSize", null == couponList ? 0 : couponList.size());
            // 不可用
            List<HashMap<String, String>> outUseCoupon =
                    accountinfoService.getUnavailableCoupon(couponParams);
            couponResult.put("outUseCouponList", outUseCoupon);
            couponResult.put("outUseCouponSize", null == outUseCoupon ? 0 : outUseCoupon.size());
            request.setAttribute("couponResult", couponResult);
            accountInfo = accountinfoService.findByLoginId(loginID);
            payRangeList = new ArrayList<String>();
            if (null != accountInfo.getPayRange()) {
                String str[] = accountInfo.getPayRange().split(",");
                payRangeList = Arrays.asList(str);
            }
            request.setAttribute("accountInfo", accountInfo);
        } catch (Exception e) {
            log.error("获取不可用优惠券发生异常！", e);
            Map<String, Object> couponResult = new HashMap<String, Object>();
            couponResult.put("outUseCouponSize", 0);
            couponResult.put("inUseCouponSize", 0);
            request.setAttribute("couponResult", couponResult);
        }
        return SUCCESS;
        /* 加载优惠券 */
    }

    /**
     * 获取用户有效优惠券，返回当前订单商品可用和不可用优惠券
     * 
     * @return
     */
    public String getCouponsList() {
        HttpServletRequest request = getRequest();
        try {
            // 可用优惠券
            List<HashMap<String, String>> couponList;
            // 不可用优惠券
            List<HashMap<String, String>> outUseCoupon;
            String sessionId = request.getSession().getId();
            Object sessionUserId = getSession().getAttribute(Constants.SESSION_USER_ID);
            Long loginID = (Long) sessionUserId;
            // 如果memCached存在优惠券数据，从缓存中取，否则从数据库获取
            HashMap<String, List<HashMap<String, String>>> couponsMap =
                    this.accountinfoService.getCouponsListCached(sessionId);
            if (couponsMap != null) {
                // 获取可用优惠券
                couponList = couponsMap.get("canUseCouponList");
                // 获取不可用优惠券
                outUseCoupon = couponsMap.get("unUseCouponList");
            } else {
                SettlementInfo settlementInfo = super.getSettlementInfoCache();
                if (settlementInfo == null) {
                    // 说明结算页商品信息不存在，直接返回
                    log.error("查询优惠券信息时，结算页商品信息不存在！");
                    Map<String, Object> couponResult = new HashMap<String, Object>();
                    couponResult.put("outUseCouponSize", 0);
                    couponResult.put("inUseCouponSize", 0);
                    request.setAttribute("couponResult", couponResult);
                    return SUCCESS;
                }
                ShopCart shopcart = (ShopCart) settlementInfo.getSubTotal();

                Long checkSellerId =
                        shopcart.getSellerShopList().getSellerShopMap().keySet().iterator().next();
                if (0 == Constants.SELF_AND_PROXY_KEY.compareTo(checkSellerId)) {
                    checkSellerId = 221L;// 生产库自营为221
                }
                // List<OrderVo> orderVoList = ShopCartUtil.getShopCarOrderList(shopcart);
                List<OrderVo> orderVoList = settlementInfo.getOrderVoList();
                // couponParams.put("customId", loginID);// 用户ID
                // couponParams.put("checkSellerId", checkSellerId);// 选中的商家ID
                // couponParams.put("orderList", orderVoList);// 产品List
                // couponParams.put("moneyCount", shopcart.getUncalculateMoney());// 金额
                // couponParams.put("channel",
                // ConfigurationUtil.getString("CHANNEL"));// 渠道
                // 返回可以及不可用优惠券
                try {
                    couponsMap = couponRemoteService.getCanUseAndUnUseCoupon(orderVoList,
                            loginID.toString(), new BigDecimal(shopcart.getUncalculateMoney() + ""),
                            Long.parseLong(checkSellerId + ""));
                } catch (Exception e) {
                    log.error("远程调用促销系统，获取可用及不可用优惠券发生异常！", e);
                    throw new ServiceException(e);
                }
                // 将优惠券信息缓存到MemCachedClient中，结算当前页面点击“使用优惠券”不需要重新从数据库加载，直接从缓存读取，加载结算时，删除缓存数据
                // 将优惠券数据缓存到memCached中，以sessionId为维度
                this.accountinfoService.savaCouponsListCached(couponsMap, sessionId);
                // 获取可用优惠券
                couponList = couponsMap.get("canUseCouponList");
                // 获取不可用优惠券
                outUseCoupon = couponsMap.get("unUseCouponList");
            }
            Map<String, Object> couponResult = new HashMap<String, Object>();
            couponResult.put("inUseCouponList", couponList);
            couponResult.put("inUseCouponSize", null == couponList ? 0 : couponList.size());
            couponResult.put("outUseCouponList", outUseCoupon);
            couponResult.put("outUseCouponSize", null == outUseCoupon ? 0 : outUseCoupon.size());
            request.setAttribute("couponResult", couponResult);
            // 从缓存中获取用户信息
            accountInfo =
                    (AccountInfo) this.memCachedClient.get(ACCOUNT_INFO.concat(loginID.toString()));
            if (accountInfo == null) {
                accountInfo = accountinfoService.findByLoginId(loginID);
            }
            payRangeList = new ArrayList<String>();
            if (null != accountInfo.getPayRange()) {
                String str[] = accountInfo.getPayRange().split(",");
                payRangeList = Arrays.asList(str);
            }
            request.setAttribute("accountInfo", accountInfo);
        } catch (Exception e) {
            log.error("获取可用及不可用优惠券发生异常！", e);
            Map<String, Object> couponResult = new HashMap<String, Object>();
            couponResult.put("outUseCouponSize", 0);
            couponResult.put("inUseCouponSize", 0);
            request.setAttribute("couponResult", couponResult);
        }
        return SUCCESS;
    }

    /**
     * 激活并使用优惠券
     * 
     * @return
     */
    public String activatAndUseCoupon() {
        String msg = "0";// 消息，6未登录,1购物车异常,2购物车已变化,3获取可用优惠券发生异常、4激活优惠券异常,5不可用
        HttpSession session = this.getSession();
        Object obj = session.getAttribute(Constants.SESSION_USER_ID);
        Map<String, Object> rsMap = new HashMap<String, Object>();// 返回结果
        if (null == obj) {
            msg = "6";
        } else {
            // try {
            // User user = loginService.queryUserByLoginId(obj.toString());
            // if (null == user || StringUtils.isEmpty(user.getMobile())) {
            // msg = "6.1";
            // }
            // } catch (ServiceException e) {
            // msg = "6.1";
            // log.error("",e);
            // }
        }
        if (!"0".equals(msg)) {
            rsMap.put("result", msg);
            AjaxUtil.writeJSONToResponse(rsMap);
            return null;
        }

        HttpServletRequest request = this.getRequest();
        Long uid = Long.parseLong(obj.toString());
        String grantCode = request.getParameter("grantCode");
        CouponGrant grant = null;
        try {
            int activeResult = couponService.activitionCoupon(grantCode, uid.intValue());
            if (Constants.ACTIVE_SUCCESS == activeResult) {
                grant = couponService.getCouponByGrantCode(grantCode);
            } else if (Constants.ACTIVE_NOUSE == activeResult) {
                msg = "4.1";
            } else if (Constants.ACTIVE_HAVEUSE == activeResult) {
                msg = "4.2";
            } else if (Constants.ACTIVE_OUTTIME == activeResult) {
                msg = "4.3";
            } else {
                msg = "4.4";
            }
        } catch (Exception e1) {
            log.error("", e1);
            msg = "4.5";
        }
        if ("0".equals(msg)) {
            String type = request.getParameter("type");
            // ShopCar shopCar = new ShopCar();
            if (StringUtil.isEmpty(type)) {
            } else if (Constants.EASY_BUY.equals(type)) {
            } else if (Constants.PRESCRIPTION_BUY.equals(type)) {
            }
            SettlementInfo settlementInfo = super.getSettlementInfoCache();
            if (settlementInfo.getSubTotal().getCheckedProductCount() == 0) {
                msg = "2";
            } else {

                ShopCart shopcart = (ShopCart) settlementInfo.getSubTotal();
                Long checkSellerId =
                        shopcart.getSellerShopList().getSellerShopMap().keySet().iterator().next();
                SellerPriceInfo priceInfo = null;
                if (0 == Constants.SELF_AND_PROXY_KEY.compareTo(checkSellerId)) {
                    checkSellerId = 221L;// 生产库自营为221
                }
                Map<String, Object> couponParams = new HashMap<String, Object>();
                couponParams.put("customId", uid);// 用户ID
                couponParams.put("checkSellerId", checkSellerId);// 选中的商家ID
                couponParams.put("orderList", settlementInfo.getOrderVoList());// 产品List
                // couponParams.put("moneyCount", priceInfo.getSumMoney());// 金额
                couponParams.put("moneyCount", shopcart.getUncalculateMoney());// 金额
                couponParams.put("channel", ConfigurationUtil.getString("CHANNEL"));// 渠道
                try {
                    List<Coupon> couponList =
                            this.accountinfoService.findCouponGrants(couponParams, obj.toString());
                    if (null != couponList && !couponList.isEmpty()) {
                        boolean hasCoupon = false;
                        for (Coupon c : couponList) {
                            if (0 == c.getCouponId().compareTo(grant.getCouponGrantId())) {
                                hasCoupon = true;
                                break;
                            }
                        }
                        if (hasCoupon) {
                            PayMoney payMoney =
                                    this.accountinfoService.getPayMoneyFormMemCache(obj.toString());
                            BigDecimal orgPay = new BigDecimal(payMoney.getPayMoney());
                            if (null != payMoney.getCouponMoney()) {
                                orgPay = orgPay.add(new BigDecimal(payMoney.getCouponMoney()));
                            }
                            // BigDecimal couponMoney =
                            // accountinfoService.getcouponMoneyByCouponGrantId(grant.getCouponGrantId(),
                            // session);
                            Coupon coupon = accountinfoService
                                    .getCouponByCouponGrantId(grant.getCouponGrantId());
                            BigDecimal couponMoney = coupon.getCouponMoney();
                            coupon.setCouponId(grant.getCouponGrantId());// 注意：coupon无CouponGrantId
                            accountinfoService.updatePayMoneyToMemCache(obj.toString(), coupon);
                            // accountinfoService.updatePayMoneyToMemCache(session,
                            // "couponName",
                            // coupon.getCouponName());
                            // accountinfoService.updatePayMoneyToMemCache(session,
                            // "couponMoney",
                            // couponMoney.doubleValue());
                            // accountinfoService.updatePayMoneyToMemCache(session,
                            // "couponid", grant
                            // .getCouponGrantId().toString());
                            if (couponMoney == null
                                    || couponMoney.compareTo(BigDecimal.ZERO) == 0) {
                                msg = "5.1";
                            } else if (couponMoney.compareTo(orgPay) > 0) {
                                msg = "5.2";
                            }
                            payMoney = accountinfoService.getPayMoneyFormMemCache(obj.toString());
                            rsMap.put("gouponGrantId", grant.getCouponGrantId());
                            rsMap.put("couponName", coupon.getCouponName());
                            rsMap.put("payMoney", payMoney);
                            rsMap.put("useLimitsType", coupon.getUseLimitsType() == null ? ""
                                    : coupon.getUseLimitsType());
                            if ("0".equals(msg) || "5.2".equals(msg)) {
                                // 更新缓存中当前用户可用优惠券
                                HashMap<String, List<HashMap<String, String>>> couponsMap =
                                        this.accountinfoService.getCouponsListCached(
                                                this.getRequest().getSession().getId());
                                HashMap<String, String> couponMap = null;
                                if (couponsMap != null) {
                                    // 获取可用优惠券
                                    List<HashMap<String, String>> canUseCouponList =
                                            couponsMap.get("canUseCouponList");
                                    if (canUseCouponList != null) {
                                        String sessionId = request.getSession().getId();
                                        HashMap<String, String> couponInfo = new HashMap();
                                        couponInfo.put("couponName", coupon.getCouponName());
                                        couponInfo.put("couponId",
                                                grant.getCouponGrantId().toString());
                                        couponInfo.put("useLimitsType",
                                                coupon.getUseLimitsType() == null ? ""
                                                        : coupon.getUseLimitsType());
                                        canUseCouponList.add(couponInfo);
                                        this.accountinfoService.savaCouponsListCached(couponsMap,
                                                sessionId);
                                    }
                                }
                            }
                        } else {
                            msg = "5.3";
                        }
                    } else {
                        msg = "5.3";
                    }
                } catch (ServiceException e) {
                    log.error("", e);
                    msg = "3";
                }
            }
        }
        rsMap.put("result", msg);
        AjaxUtil.writeJSONToResponse(rsMap);
        return null;
    }

    /**
     * 添加和编辑收货地址
     * 
     * @return
     */
    public String addAddressInfo() {
        JSONObject json = new JSONObject();
        json.put("message", "成功.");
        json.put("result", "0");
        HttpServletRequest request = getRequest();
        // AddressRemoteService addressRemoteService;
        try {
            // addressRemoteService =
            // (AddressRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_CUSTOMER,
            // "addressService");
            Long loginId = super.getUserloginId();
            addressList = this.addressService.findByLoginId(loginId.intValue());// queryAddressByLoginId(loginId);
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
            if (StringUtil.isEmpty(defaultAddressId)) {
                if (addressList.size() >= 10) {
                    log.info("新增wap端我的收货地址出错 数量已达10个！");
                    return SUCCESS;
                }
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
                Integer addressId = addressService.save(address);// addressRemoteService.addAddress(address.transFormToRemoteAddress());
                address.setAddressId(addressId);
                addressList.add(0, address);
                // mkw add 20150923 新增加收货地址再修改，无法提交
                naddressId = addressId;
                // end
            } else {
                int defaultId = Integer.parseInt(defaultAddressId);
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
                                // addressRemoteService.updateAddress(address.transFormToRemoteAddress());
                            }
                            addressList.set(i, address);
                            break;
                        }
                    }
                } else {
                    for (int i = 0, len = addressList.size(); i < len; i++) {
                        Address addr = addressList.get(i);
                        if (defaultId == addr.getAddressId()) {
                            address = addr;
                            address.setIsChecked(true);
                            addressList.set(i, address);
                            break;
                        }
                    }
                }
            }
            if (null != address) {
                addressService.addAddressTomem(String.valueOf(loginId), address);
                StringBuilder sf = new StringBuilder();
                sf.append("<div class='s-content'><p><label><strong>").append(address.getName())
                        .append("</strong>&nbsp;").append(address.getProvince())
                        .append(address.getCity()).append(address.getArea())
                        .append(address.getDetailedAddress()).append("&nbsp;")
                        .append(address.getCellphone()).append("&nbsp;")
                        .append("</label></p></div>");
                json.put("addressValue", sf.toString());
                json.put("naddressId", naddressId);
            }
            boolean isWxBrowse = false;
            String userAgent = request.getHeader("user-agent");
            if (null != userAgent && userAgent.indexOf("MicroMessenger") > 0) { // 是微信浏览器
                isWxBrowse = true;
                json.put("appId", ConfigurationUtil.getString("wx_appid")); // 此字段为appId唯一标识
            }
            json.put("isWxBrowse", isWxBrowse);
        } catch (Exception e) {
            log.error("", e);
        }
        AjaxUtil.writeJSONToResponse(json);
        return SUCCESS;
    }

    /**
     * 删除我的收货地址,0失败1成功-1参数错误
     * 
     * @return
     */
    public String deleteMyAddress() {
        int rs = 0;
        if (null != naddressId) {
            Long userId = (Long) this.getSession().getAttribute(Constants.SESSION_USER_ID);
            // Map<String, Long> params = new HashMap<String, Long>();
            // params.put("addressId", naddressId.longValue());
            // params.put("loginId", userId);
            try {
                if (addressService.delete(userId,
                        naddressId)/*
                                    * addressService.deleteMyAddress(params )
                                    */) {
                    rs = 1;
                }
            } catch (Exception e) {
                log.error("", e);
            }
        } else {
            rs = -1;
        }
        AjaxUtil.writeJSONToResponse("{'result':" + rs + "}");
        return null;
    }

    /**
     * 删除收货地址
     * 
     * @return
     */
    public String deleteAddress() {
        HttpServletRequest request = getRequest();
        JSONObject json = new JSONObject();
        StringBuilder sf = new StringBuilder();
        json.put("message", "成功.");
        json.put("result", "0");
        try {
            int addressId = Integer.parseInt(request.getParameter("addressId"));
            Long loginId = super.getUserloginId();
            addressList = addressService.findByLoginId(loginId.intValue());
            boolean hasDefault = false;
            Object obj = this.getSession().getAttribute(Constants.SESSION_USER_ID);
            String usessinId = null == obj ? getSession().getId() : obj.toString();
            if (null != addressList && !addressList.isEmpty()) {
                Address mAddr = addressService.getAddressFromMem(usessinId);
                Address temp = null;
                for (int i = 0, len = addressList.size(); i < len; i++) {
                    temp = addressList.get(i);
                    temp.setLoginId(loginId.intValue());
                    if (addressId == temp.getAddressId()) {
                        addressService.delete(loginId, addressId);
                        addressList.remove(i);
                        if (temp.getStatus() == 0) {
                            // 删除默认
                            temp = addressList.get(0);
                            temp.setStatus(0);
                            addressService.update(temp);
                        }
                        break;
                    } else if (null != mAddr && mAddr.getAddressId() == temp.getAddressId()) {
                        temp.setIsChecked(true);
                        addressList.set(i, temp);
                        hasDefault = true;
                        sf.append("<div class='s-content'><p><label><strong>")
                                .append(temp.getName()).append("</strong>&nbsp;")
                                .append(temp.getProvince()).append(temp.getCity())
                                .append(temp.getArea()).append(temp.getDetailedAddress())
                                .append("&nbsp;").append(temp.getCellphone()).append("&nbsp;")
                                .append("</label></p></div>");
                    }
                }
                if (null != addressList && !addressList.isEmpty() && !hasDefault) {
                    temp = addressList.get(0);
                    temp.setIsChecked(true);
                    addressList.set(0, temp);
                    addressService.addAddressTomem(usessinId, temp);
                }
            }
            boolean isWxBrowse = false;
            String userAgent = request.getHeader("user-agent");
            if (null != userAgent && userAgent.indexOf("MicroMessenger") > 0) { // 是微信浏览器
                isWxBrowse = true;
                json.put("appId", ConfigurationUtil.getString("wx_appid")); // 此字段为appId唯一标识
            }
            json.put("isWxBrowse", isWxBrowse);
        } catch (Exception e) {
            log.error("删除收货地址出错" + e.getMessage(), e);
        }
        json.put("addressValue", sf.toString());
        AjaxUtil.writeJSONToResponse(json);
        return SUCCESS;

    }

    /**
     * 保存支付信息到memcache 并刷新页面
     * 
     * @return
     */
    public String savePaymodelDeliveryInfo() {
        JSONObject json = new JSONObject();
        json.put("message", "成功.");
        json.put("result", "0");
        OrderDictionary orderDict = new OrderDictionary();
        orderDict.setOrderDictionaryType(Constants.PAY_METHOD);
        orderDict.setOrderDictionaryKey(Long.parseLong(paymodelvalue));

        Map<String, String> map = new HashMap<String, String>();
        try {
            OrderDictionary dict =
                    this.OrderDictonaryService.getOrderDictionaryByOrderDict(orderDict);
            map.put("orderDictionaryValue", dict.getOrderDictionaryValue());

            orderDict.setOrderDictionaryType(Constants.DELIVERY_DATE_TYPE);
            orderDict.setOrderDictionaryKey(Long.parseLong(deliveryTimeValue));

            dict = new OrderDictionary();
            dict = this.OrderDictonaryService.getOrderDictionaryByOrderDict(orderDict);

            map.put("deliveryTimeValue", dict.getOrderDictionaryValue());
        } catch (Exception e) {
            log.error("查询订单支付信息错误. OrderDictionaryType=" + orderDict.getOrderDictionaryType()
                    + ",OrderDictionaryKey=" + orderDict.getOrderDictionaryKey(), e);
            json.put("result", "1");
            json.put("message", "查询订单支付信息错误.");
            log.error("", e);
        }

        try {
            Object obj = this.getSession().getAttribute(Constants.SESSION_USER_ID);
            String usessinId = null == obj ? getSession().getId() : obj.toString();
            map.put("orderDictionaryKey",
                    new String(paymodelvalue.getBytes("ISO-8859-1"), "UTF-8"));
            map.put("deliveryTime", new String(deliveryTimeValue.getBytes("ISO-8859-1"), "UTF-8"));
            map.put("isconfirm", new String(isconfirmValue.getBytes("ISO-8859-1"), "UTF-8"));
            accountinfoService.savePaymodelDeliveryInfo(usessinId, map);
            this.defaultPayAndDelivery = map;

            // payModelList = this.OrderDictonaryService.getPaymentModel();

            PayMoney payMoney = this.accountinfoService.getPayMoneyFormMemCache(usessinId);

            if (null != payMoney) {
                fare = payMoney.getFare();
                if (map.get("isconfirm") != null && map.get("isconfirm").equals("0")) {
                    json.put("isConfirmValue", "送货前不用电话确认");
                } else {
                    json.put("isConfirmValue", "送货前电话确认");
                }
                json.putAll(map);
            } else {
                // mkw 20151224 add 查询为空，提示购物车信息变动
                json.put("result", Constants.SETTLEMENT_SHOPCART_CHANGE);
                json.put("message", "购物车信息变动");
                // end
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            json.put("message", "保存失败.");
            json.put("result", "1");
            return ERROR;
        }
        AjaxUtil.writeJSONToResponse(json);
        return SUCCESS;
    }

    /**
     * 保存订单-1:error;0:unsett;1:changed;2:offshelf;3:outstock;4:noaddr;5:noitem;6
     * :rebuypre;7:unlogin;8: overbalan;9:necoupon overbalan 10：尾款通知手机号错误 11:预售活动已结束
     * 
     * @return
     */
    public String saveAndPayOrder() {
        if (null != orderDescription
                && (orderDescription.indexOf("<") >= 0 || orderDescription.indexOf("&gt") >= 0)) {
            return null;
        }
        Map<String, String> result = new HashMap<String, String>();
        String SETT_UID_KEY = "SETT_UID_KEY";
        final HttpServletRequest request = super.getRequest();
        HttpSession session = request.getSession();
        Long uid = null;
        // 加锁成功的标识，true:标识加锁成功，false:表示加锁失败
        try {
            final Long loginId = super.getUserloginId();
            uid = loginId;
            // b2b_max_concurrency_order:下单最大并发量
            if (ORDER_COUNT.incrementAndGet() > Integer
                    .parseInt(ConfigurationUtil.getString("b2b_max_concurrency_order"))) {// 超过并发数
                result.put("flag", "1.1");
                writeJSONToResponse(result);
                return null;
            }
            if (null != session.getAttribute(SETT_UID_KEY)) {// 重复提交
                result.put("flag", "1");
                writeJSONToResponse(result);
                return null;
            }

            if (StringUtil.isEmpty(loginId)) {
                result.put("flag", "7");
                writeJSONToResponse(result);
                return null;
            }

            session.setAttribute(SETT_UID_KEY, "1");// 存储非空即可
            String loginType = (String) session.getAttribute(Constants.SESSION_B2B_OR_ERA);
            String buyType = request.getParameter("type");
            String outStockConfirm = request.getParameter("outStockConfirm");// 赠品库存超出确认
            ShopCartUserInfo settShop =
                    new ShopCartUserInfo(loginId.toString(), true, loginType, buyType);
            ShopCart settShopCart = null;
            String informpaytel = request.getParameter("informpaytel");;// 尾款支付手机号 预售购买使用
            String[] productIdArray = null;
            if (Constants.PRESCRIPTION_BUY.equals(buyType) || Constants.EASY_BUY.equals(buyType)
                    || Constants.PRESELL_BUY.equals(buyType)) {
                int count = 1;
                String skuIds = request.getParameter("skuId");
                String productCount =
                        Constants.EASY_BUY.equals(buyType) || Constants.PRESELL_BUY.equals(buyType)
                                ? request.getParameter("productCount")
                                : request.getParameter("productSkuIDs");
                if (null != productCount) {
                    count = Integer.parseInt(productCount);
                }

                // 预售校验
                if (Constants.PRESELL_BUY.equals(buyType)) {

                    if (!StringUtil.isMobile(informpaytel)) {
                        result.put("flag", "10");
                        result.put("msg", "尾款通知手机号错误");
                        writeJSONToResponse(result);
                        return null;
                    }
                    PresellProductVO vo = presellCacheUtil.getPresellCache(Long.valueOf(skuIds));
                    com.alibaba.fastjson.JSONObject productJson =
                            productRedisUtil.getProductSkuDetail(skuIds);
                    productJson = productJson == null ? new com.alibaba.fastjson.JSONObject()
                            : productJson;
                    if (null == vo || new Date().after(vo.getDepositEndTime())) {
                        result.put("flag", "11");
                        result.put("msg", "预售活动已结束");
                        writeJSONToResponse(result);
                        return null;
                    }
                    if (count > vo.getAvailableQuantity()) {
                        result.put("flag", "3");
                        result.put("msg", productJson.getString("title"));
                        writeJSONToResponse(result);
                        return null;
                    }

                    // 根据用户及预售活动查询该用户已买多少件
                    Integer alreadyBuyCount =
                            this.orderItemService.queryUserBuyPromotionNumForPressell(uid,
                                    Long.valueOf(skuIds), String.valueOf(vo.getPresellId()));


                    if (vo.getByLimit() != null && vo.getByLimit() != 0
                            && vo.getByLimit() < alreadyBuyCount + count) {
                        result.put("flag", "12");
                        result.put("msg", "超出限购!限购 " + vo.getByLimit() + "件,已买 " + alreadyBuyCount
                                + "件,可买 " + (vo.getByLimit() - alreadyBuyCount) + "件");
                        writeJSONToResponse(result);
                        return null;
                    }

                }

                settShopCart = shopCartInfoService.generateSettlementForPressell(settShop,
                        new ShopcartCacheProduct(skuIds, count, new Date(), true, ""), buyType);

            } else {
                productIdArray = (String[]) memCachedClient.get(ConfigurationUtil
                        .getString("b2b_sett_ids_prex").concat(loginId.toString()));
                settShopCart = shopCartInfoService.generateSettlement(settShop, productIdArray);
            }
            Address address = addressService.getAddressFromMem(loginId.toString());
            // 金额
            PayMoney paymoney = accountinfoService.getPayMoneyFormMemCache(loginId.toString());

            if (null == paymoney) {
                result.put("flag", "1");
                writeJSONToResponse(result);
                log.info("({})生成订单失败:获取支付信息为空", loginId);
                return null;
            }

            PayMoneyPresell payMoneyPresell = paymoney.getPayMoneyPresell();

            final AccountInfo settAccountInfo =
                    accountinfoService.getAccountInfoByLoginIdForSett(loginId);
            // 优惠券
            Coupon coupon = null;
            if (null == settAccountInfo) {
                result.put("flag", "7");
            } else if (Constants.PRESCRIPTION_BUY.equals(buyType) && !MD5
                    .getMD5Str(Constants.PRESCRIPTION_BUY
                            + getSession().getAttribute(Constants.SESSION_USER_ID))
                    .equals(request.getParameter("hduid"))) {
                result.put("flag", "6");
            } else if (null == settShopCart || null != settShopCart.getProductErrorReminder()) {
                result.put("flag", "0");
                if (null != settShopCart) {
                    result.put("msg", settShopCart.getProductErrorReminder().values().iterator()
                            .next().getMessage());
                } else {
                    result.put("msg", "结算商品为空！");
                }
            } else if (null == address || StringUtil.isEmpty(address.getCellphone())
                    || StringUtil.isEmpty(address.getDetailedAddress())) {
                result.put("flag", "4");
                result.put("msg", "收货地址为空！");
            } else if ((paymoney.getBalanceMoney() != 0
                    && settAccountInfo.getAmountAvlibal() < paymoney.getBalanceMoney())) {
                result.put("flag", "8");
            } else if (paymoney.getCouponid() != null && !"".equals(paymoney.getCouponid())
                    && null == (coupon = accountinfoService
                            .getCouponByCouponGrantId(Long.parseLong(paymoney.getCouponid())))) {
                result.put("flag", "9");
            } else if (Constants.PRESELL_BUY.equals(buyType)
                    || (result = orderItemService.compareSett(settShopCart,
                            (ShopCart) super.getSettlementInfoCache().getSubTotal(),
                            outStockConfirm)).isEmpty()) {
                OrderMainExt orderMainExt = null;



                final OrderMain orderMain = new OrderMain();
                orderMain.setPrescriptionAttachment(
                        ConfigurationUtil.getString("IMG_SHOW_PATH") + prescriptionAttachmentFile);// 药方图片路径
                orderMain.setOrderSource(
                        1 == wapPay ? Constants.ORDER_SOURCE_WAP : Constants.ORDER_SOURCE_WEB);
                orderMain.setCustomerAccount(settAccountInfo.getAccountLogin());
                orderMain.setCustomerName(settAccountInfo.getName());
                orderMain.setOrderPurchaserName(settAccountInfo.getName());
                orderMain.setOrderPurchaserMobile(settAccountInfo.getMobile());
                orderMain.setOrderPurchaserAddr(settAccountInfo.getAddress());
                orderMain.setProvince(address.getProvince());
                orderMain.setCity(address.getCity());
                orderMain.setArea(address.getArea());
                orderMain.setZipcode(StringUtil.isEmpty(address.getPostalcode()) ? null
                        : Integer.parseInt(address.getPostalcode()));
                orderMain.setConsigneeName(address.getName());
                orderMain.setConsigneeMobile(address.getCellphone());
                orderMain.setConsigneeAddr(address.getProvince() + address.getCity()
                        + address.getArea() + address.getDetailedAddress());
                orderMain.setAddressId(BigDecimal.valueOf(address.getAddressId()));
                orderMain.setCreateDate(new Date());
                orderMain.setCustomerId(new BigDecimal(loginId));
                orderMain.setDisabled(1l);// 可用
                orderMain.setOrderStatus(Constants.ORDER_STATUS_NOT_PAY);
                orderMain.setOrderDescription(
                        (orderDescription == null || "请输入订单备注信息".equals(orderDescription)) ? null
                                : orderDescription);

                // 订单扩展表
                orderMainExt = new OrderMainExt();
                orderMain.setOrderMainext(orderMainExt);
                SellerShop sellerShop = settShopCart.getSellerShopList().getSellerShopMap().values()
                        .iterator().next();
                boolean isTimeLogin = Constants.LOGINTYPE
                        .equals(getSession().getAttribute(Constants.SESSION_B2B_OR_ERA));

                if (isTimeLogin) {
                    orderMain.setOrderPurchaserType(Long
                            .valueOf(OrderDictionaryEnum.OrderPurchaserType.TimeMember.getKey()));
                } else {
                    orderMain.setOrderPurchaserType(
                            Long.valueOf(OrderDictionaryEnum.OrderPurchaserType.Register.getKey()));
                    // orderMain.setOrderPurchaserType(Constants.ORDER_PURCHASER_TYPE_CUSTOMER);
                }

                if (isTimeLogin && null != sellerShop && null != sellerShop.getProductPvalue()) {
                    orderMain.setOrderPv(sellerShop.getProductPvalue().floatValue());
                }
                boolean isTimesShop = false, isEnterSale = false;// 时代购物/入驻标识
                if (null != sellerShop && null != sellerShop.getSupplierType()) {

                    if (SupplierType.SELLER_TYPE_ENTER_SALE.getIndex() == sellerShop
                            .getSupplierType()) {
                        isEnterSale = true;
                        orderMain.setCommerceId(String.valueOf(sellerShop.getSellerId()));
                        orderMain.setCommerceName(carProductService
                                .queryCorporateNameBySupplierId(sellerShop.getSellerId()));
                    } else {
                        orderMain.setCommerceName(sellerShop.getShopName());
                    }
                }
                BigDecimal rate = BigDecimal.ONE, disCount = BigDecimal.ONE;// 满减均摊比例/折扣

                if (isTimeLogin && isTimesShop) {
                    disCount = loginService.getactualDiscount(
                            (String) getSession().getAttribute(Constants.SESSION_USER_NAME));
                }

                // 配送
                Map<String, String> delivery = this.accountinfoService
                        .getPaymodelDeliveryInfoFromMemcache(loginId.toString());
                orderMain.setPayMethod(Long.parseLong(delivery.get("orderDictionaryKey")));
                orderMain.setDeliveryDateType(Long.parseLong(delivery.get("deliveryTime")));
                orderMain.setConfirmDelivery(Short.valueOf(delivery.get("isconfirm")));
                // 发票
                InvoiceInfo invoiceInfo =
                        accountinfoService.getInvoiceInfoFormMemcache(loginId.toString());
                if (invoiceInfo != null) {
                    orderMain.setInvoiceInfoType(invoiceInfo.getInvoiceType());
                    orderMain.setInvoiceInfoContent(invoiceInfo.getInvoiceContent());
                    orderMain.setInvoiceInfoTitle(invoiceInfo.getInvoicetitle());
                }
                orderMain.setAccountBalance(new BigDecimal(paymoney.getBalanceMoney()));
                fare = paymoney.getFare();// 运费计算
                Double payMoney = Double.valueOf(paymoney.getPayMoney());

                orderMain.setDiscountAmount(settShopCart.getReductionMoney());
                orderMain.setFare(new BigDecimal(fare));
                orderMain.setOrderDiscount(settShopCart.getDiscountMoney());// 会员打折
                orderMain.setWeight(settShopCart.getWeight());

                if (Constants.PRESELL_BUY.equals(buyType)) {
                    orderMain.setInformPayTel(informpaytel);
                    orderMain.setDepositSum(payMoneyPresell.getDepositTotalPrice());
                    orderMain.setOrderType(
                            Long.valueOf(OrderDictionaryEnum.Order_Type.YsOrder.getKey()));
                    orderMain.setAmountPayable(payMoneyPresell.getDepositTotalPrice()
                            .add(payMoneyPresell.getFinalTotalPrice()));
                    orderMain.setOriginalOrderSum(payMoneyPresell.getDepositTotalPrice()
                            .add(payMoneyPresell.getFinalTotalPrice()));
                    orderMain.setCommoditySum(payMoneyPresell.getDepositTotalPrice()
                            .add(payMoneyPresell.getFinalTotalPrice()));
                    result.put("isPressell", "1");// 不为空就行
                } else {
                    // 应付金额=商品金额+运费-优惠金额+加价购金额-时代会员利率
                    orderMain
                            .setAmountPayable(settShopCart.getUncalculateMoney()
                                    .add(new BigDecimal(fare)
                                            .subtract(settShopCart.getReductionMoney()))
                                    .add(settShopCart.getAdditionalMoney()
                                            .subtract(settShopCart.getDiscountMoney())));

                    orderMain.setOriginalOrderSum(settShopCart.getUncalculateMoney());
                    orderMain.setCommoditySum(settShopCart.getUncalculateMoney());
                    orderMain.setOrderType(
                            Long.valueOf(OrderDictionaryEnum.Order_Type.Normal.getKey()));
                }

                // 商品
                BigDecimal payableMoney =
                        settShopCart.getUncalculateMoney().add(settShopCart.getAdditionalMoney());// 促销活动后商品总金额
                BigDecimal realMoney =
                        settShopCart.getUncalculateMoney().add(settShopCart.getAdditionalMoney())
                                .subtract(settShopCart.getReductionMoney())
                                .subtract(new BigDecimal(paymoney.getCouponMoney()));// 实付金额=总额+加价购-满减-优惠券
                if (payableMoney.compareTo(BigDecimal.ZERO) > 0) {
                    rate = realMoney.divide(payableMoney, 5, BigDecimal.ROUND_HALF_UP);
                }
                final List<OrderItem> oiList = new ArrayList<OrderItem>();// 订单明细
                List<OrderPreferentialVO> opList = new ArrayList<OrderPreferentialVO>();// 优惠明细
                boolean[] identity = {isEnterSale, isTimeLogin, isTimesShop};
                BigDecimal[] moneyInfo = {rate, disCount, realMoney, payableMoney};

                orderItemService.generateOrderItem(settAccountInfo, orderMain, settShopCart, oiList,
                        opList, moneyInfo, identity, payMoneyPresell);
                orderMain.setOrderChannel(ConfigurationUtil.getString("CHANNEL")); // 渠道
                if (null == oiList || oiList.isEmpty()) {
                    result.put("flag", "5");
                } else {
                    List<OrderPayStatement> opsList = new ArrayList<OrderPayStatement>();// 支付明细
                    OrderPayStatement ops = null;
                    Date now = new Date();

                    if (null != coupon) {
                        ops = new OrderPayStatement();
                        ops.setPaymentWay(Constants.PAY_METHOD_PREFERENTIAL);
                        ops.setAccount(settAccountInfo.getAccountLogin());
                        ops.setOrderMoney(new BigDecimal(paymoney.getCouponMoney()));
                        ops.setCreateDate(now);
                        ops.setPreferentialNo(new BigDecimal(coupon.getCouponId()));
                        ops.setPreferentialGrantId(new BigDecimal(paymoney.getCouponid()));
                        ops.setPreferentialName(coupon.getCouponName());
                        ops.setFlag(Constants.ORDER_PAY_FLAG_PAYMENT);
                        opsList.add(ops);
                    }
                    // 余额
                    if (paymoney.getBalanceMoney() != null && paymoney.getBalanceMoney() > 0) {
                        ops = new OrderPayStatement();
                        ops.setPaymentWay(Constants.PAY_METHOD_BALANCE);
                        ops.setAccount(settAccountInfo.getAccountLogin());
                        ops.setOrderMoney(new BigDecimal(paymoney.getBalanceMoney()));
                        ops.setCreateDate(now);
                        ops.setFlag(Constants.ORDER_PAY_FLAG_PAYMENT);
                        opsList.add(ops);
                    }
                    if (!isTimeLogin && paymoney.getScore() > 0) {
                        // 积分
                        orderMain.setTotalCredit(paymoney.getScore() + orderMain.getTotalCredit());
                    } else {
                        orderMain.setTotalCredit(0l);
                    }
                    final String orderCode = orderCreateRemoteService.createOrder(orderMain, oiList,
                            opList, opsList);
                    orderMain.setOrderCode(orderCode);
                    result.put("orderCode", orderCode);
                    result.put("payMoney", payMoney.toString());
                    result.put("rechargeOrOrderFlag", "2");
                    result.put("wapPay", wapPay.toString());
                    result.put("isCreate", "1");

                    String tc = null, cpsValue = null;
                    final Map<String, String> map = new HashMap<String, String>();
                    if (request.getCookies() != null) {
                        for (Cookie c : request.getCookies()) {
                            /*
                             * if (Constants.COOKIE_FANLI_TRACKING_CODE.equals(c.getName())) { tc =
                             * c.getValue(); map.put("tc", tc); } else if
                             * (Constants.COOKIE_FANLI_CHANNEL_ID.equals(c.getName())) { channelId =
                             * c.getValue(); map.put("channelId", channelId);
                             * orderMain.setOrderChannel(c.getName());// 需覆盖 } else if
                             * (Constants.COOKIE_FANLI_U_ID.equals(c.getName())) { fanliuid =
                             * c.getValue(); map.put("fanliuid", fanliuid); } else
                             */
                            if ("cps_yqf_source".equalsIgnoreCase(c.getName())) {
                                cpsValue = c.getValue().toString();
                                map.put("cpsValue", cpsValue);
                            }
                        }
                    }
                    myOrderService.orderUserSourceType(map.get("cpsValue"), orderMain, oiList);
                    // 订单生成完成以后 可以异步 1、推送 2、清理缓存 3、发送邮件
                    final String[] runnableSkuIds = productIdArray;
                    taskExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            accountinfoService.clearMemcache(loginId.toString());
                            if (null != runnableSkuIds) {
                                String uid = loginId.toString();
                                String settlementIdsCacheKey = ConfigurationUtil
                                        .getString("b2b_sett_ids_prex").concat(uid);
                                memCachedClient.delete(settlementIdsCacheKey);
                                shopCartInfoService.deleteProduct(uid, runnableSkuIds);
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            result.put("flag", "-1");
            log.error("生成订单异常", e);
        } finally {
            ORDER_COUNT.decrementAndGet();
            session.removeAttribute(SETT_UID_KEY);
        }
        writeJSONToResponse(result);
        return null;
    }

    private static void writeJSONToResponse(Object object) {
        PrintWriter out = null;
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("application/json;charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expires", "0");
            response.setHeader("Pragma", "No-cache");
            JSONObject jsONObject = JSONObject.fromObject(object);
            String json = jsONObject.toString();
            response.setContentLength(json.getBytes("utf-8").length);
            out = response.getWriter();
            out.print(json);
        } catch (Exception e) {
            log.error("erro", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 
     * @return
     */

    public String consigneeInfo() {
        user = super.getLoginUser();
        if (user == null) {
            return LOGIN;
        }
        Long loginID = super.getUserloginId();
        if (null != loginID) {
            try {
                addressList = this.addressService.findByLoginId(loginID.intValue(), false);
            } catch (Exception e) {
                log.error(e.getMessage());
                return ERROR;
            }
        }
        return SUCCESS;
    }

    /**
     * 删除收货人，wap版
     * 
     * @return
     */
    public String deleteConsigneeInfo() {
        boolean isTimeLogin =
                Constants.LOGINTYPE.equals(getSession().getAttribute(Constants.SESSION_B2B_OR_ERA));// 时代用户登录
        Long userId = (Long) this.getSession().getAttribute(Constants.SESSION_USER_ID);
        try {
            String addressIdStr = this.getRequest().getParameter("addressId");
            int addressId = Integer.parseInt(addressIdStr);

            Address address = addressService.findByNAddressID(userId, addressId);
            boolean isDeleteDefault = false;
            if (address.getStatus() == 0) { // 如果是默认地址
                addressService.deleteAddressFormmem(userId.toString());
                isDeleteDefault = true;
            }
            // 删除我的收货地址
            boolean resutl = addressService.delete(userId, addressId);
            if (resutl) { // 删除失败
                // returnResult = new ReturnResult(InterfaceResultCode.FAILED, "调用远程接口删除我的收货地址错误",
                // null);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, "不能删除默认收货地址", null);
                return ERROR;
            }
            if (isDeleteDefault && null != addressList && addressList.size() > 0) { // 默认地址删除完了之后取第一个为默认地址
                addressList = this.addressService.findByLoginId(userId.intValue());
                address = addressList.get(0);
                address.setStatus(0);
                address.setLoginId(userId.intValue());
                addressService.update(address);
                addressService.addAddressTomem(userId.toString(), address);
            }
        } catch (Exception e) {
            log.error(
                    "删除我的地址错误，id为：" + this.getRequest().getParameter("addressId") + e.getMessage(),
                    e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "删除我的收货地址错误", null);
            return ERROR;
        }
        isLoginType = isTimeLogin;
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", null);
        return SUCCESS;
    }

    /**
     * wap端我的收货地址的修改和保存
     * 
     * @return
     */
    public String wapaddAddressInfo() {
        boolean isTimeLogin =
                Constants.LOGINTYPE.equals(getSession().getAttribute(Constants.SESSION_B2B_OR_ERA));// 时代用户登录
        try {
            User userObj = super.getLoginUser();
            Long loginId = super.getUserloginId();
            AccountInfo accountInfo = accountinfoService.findByLoginId(loginId);
            Address address = new Address();
            if ((this.defaultAddressId == null || this.defaultAddressId.equals(""))) {
                // 新增 并且设置默认的收货地址
                address = new Address();
                address.setProvince(province);
                address.setCity(city);
                address.setArea(area);
                address.setName(name);
                address.setPostalcode(postalcode);
                address.setDetailedAddress(detailedAddress);
                address.setTelephone(telephone);
                address.setAccountId(accountInfo.getNaccountId());
                address.setLoginId(loginId.intValue());
                address.setCreatedate(new Date());
                address.setLastupdate(new Date());
                address.setCellphone(mobile);
                address.setEmail(email);
                // address.setStatus(0);
                // 如果存在默认地址//设置为普通地址
                addressList = this.addressService.findByLoginId(loginId.intValue(), false);
                if (addressList.size() == 0) {
                    address.setStatus(0);// 如果没有收货地址，默认设置为默认收货地址
                    address.setIsChecked(true);
                } else {
                    address.setStatus(1);// 设置为普通收货地址
                }
                Integer addressId = addressService.save(address);
                address.setAddressId(addressId);
                address.setLoginId(loginId.intValue());
                if ("0".equals(isMoren)) {
                    address.setStatus(0);
                    addressService.update(address);
                }

            } else {
                // 更新收货地址
                if (null != naddressId) {
                    address = this.addressService.findByNAddressID(loginId, naddressId);
                    address.setProvince(province);
                    address.setCity(city);
                    address.setArea(area);
                    address.setName(name);
                    address.setPostalcode(postalcode);
                    address.setDetailedAddress(detailedAddress);
                    address.setTelephone(telephone);
                    address.setAccountId(accountInfo.getNaccountId());
                    address.setLoginId(loginId.intValue());
                    address.setAddressId(naddressId);
                    address.setCellphone(mobile);
                    if ("0".equals(isMoren)) {
                        address.setStatus(0);
                    } else {
                        address.setStatus(1);
                    }
                    if (null == userObj || userObj.getLoginId() == null) {
                        address.setEmail(email);
                    }
                    address.setLastupdate(new Date());
                    addressService.update(address);
                }
            }

            // 获取收货人信息
            addressList = addressService.findByLoginId(loginId.intValue());
            for (Address addr : addressList) {
                if (addr.getStatus() == 0) {
                    addr.setIsChecked(true);// 默认选中
                }
                if (defaultAddressId != null) {
                    if (addr.getAddressId() == Integer.parseInt(defaultAddressId)) {
                        addr.setIsChecked(true);// 重新勾选设置选中
                        address = addr;
                    } else {
                        addr.setIsChecked(false);
                    }
                }
                isCheckAddressList.add(addr);
            }
            Long uid = super.getUserloginId();
            String usessinId = null == uid ? getSession().getId() : uid.toString();
            addressList = isCheckAddressList;
            // 添加地址信息到数据库，并缓存到memcache
            addressService.addAddressTomem(usessinId, address);
        } catch (Exception e) {
            log.error("", e);
        }

        isLoginType = isTimeLogin;
        return SUCCESS;
    }

    /**
     * 跳转进去添加收货人页面、跳去修改收货人的页面
     * 
     * @return
     */
    public String addConsigneeInfo() {
        if (StringUtils.isNotBlank(this.isMoren)) { // 跳入编辑
            String addressIdStr = this.isMoren;
            Long userId = (Long) this.getSession().getAttribute(Constants.SESSION_USER_ID);
            try {
                address = addressService.findByNAddressID(userId, Integer.parseInt(addressIdStr));
                this.type = "edit";
                return SUCCESS;
            } catch (Exception e) {
                log.error("跳转去编辑收货人的页面出错" + e.getMessage(), e);
                return ERROR;
            }
        } else {
            this.type = "add";
            return SUCCESS;
        }

    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public List<Address> getIsCheckAddressList() {
        return isCheckAddressList;
    }

    public void setIsCheckAddressList(List<Address> isCheckAddressList) {
        this.isCheckAddressList = isCheckAddressList;
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

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
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

    public String getImg_path() {
        return ConfigurationUtil.getString("PRODUCT_IMG_PATH");
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<String> getPayRangeList() {
        return payRangeList;
    }

    public void setPayRangeList(List<String> payRangeList) {
        this.payRangeList = payRangeList;
    }

    public boolean isPayFlag() {
        return payFlag;
    }

    public void setPayFlag(boolean payFlag) {
        this.payFlag = payFlag;
    }

    public PayCommonObject getPayCommon() {
        return payCommon;
    }

    public void setPayCommon(PayCommonObject payCommon) {
        this.payCommon = payCommon;
    }

    public String getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(String couponMoney) {
        this.couponMoney = couponMoney;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
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

    public PayMoney getPayMoneyInfo() {
        return payMoneyInfo;
    }

    public void setPayMoneyInfo(PayMoney payMoneyInfo) {
        this.payMoneyInfo = payMoneyInfo;
    }

    public CommercialTenantBasicInfo getCommercialTenantBasicInfo() {
        return commercialTenantBasicInfo;
    }

    public void setCommercialTenantBasicInfo(CommercialTenantBasicInfo commercialTenantBasicInfo) {
        this.commercialTenantBasicInfo = commercialTenantBasicInfo;
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

    public Integer getWapPay() {
        return wapPay;
    }

    public void setWapPay(Integer wapPay) {
        this.wapPay = wapPay;
    }

    public String getPrescriptionAttachmentFile() {
        return prescriptionAttachmentFile;
    }

    public void setPrescriptionAttachmentFile(String prescriptionAttachmentFile) {
        this.prescriptionAttachmentFile = prescriptionAttachmentFile;
    }

    public Boolean getIsLoginType() {
        return isLoginType;
    }

    public void setIsLoginType(Boolean isLoginType) {
        this.isLoginType = isLoginType;
    }

    public ReturnResult getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult returnResult) {
        this.returnResult = returnResult;
    }

    public String getIsMoren() {
        return isMoren;
    }

    public void setIsMoren(String isMoren) {
        this.isMoren = isMoren;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, ShopCartProduct> getCarProductMap1() {
        return carProductMap1;
    }

    public void setCarProductMap1(Map<String, ShopCartProduct> carProductMap1) {
        this.carProductMap1 = carProductMap1;
    }

    public Map<String, ShopCartProduct> getCarPromotionProductMap() {
        return carPromotionProductMap;
    }

    public void setCarPromotionProductMap(Map<String, ShopCartProduct> carPromotionProductMap) {
        this.carPromotionProductMap = carPromotionProductMap;
    }

    public Map<Long, CarProduct> getTimeProductMap() {
        return timeProductMap;
    }

    public void setTimeProductMap(Map<Long, CarProduct> timeProductMap) {
        this.timeProductMap = timeProductMap;
    }
}
