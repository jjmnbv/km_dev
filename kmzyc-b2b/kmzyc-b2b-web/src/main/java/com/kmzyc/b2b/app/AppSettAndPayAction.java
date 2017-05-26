package com.kmzyc.b2b.app;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.util.AlipayCore;
import com.km.framework.common.util.MD5;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.Address;
import com.kmzyc.b2b.model.Coupon;
import com.kmzyc.b2b.model.PayCommonObject;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.AccountInfoService;
import com.kmzyc.b2b.service.CarProductService;
import com.kmzyc.b2b.service.EraInfoService;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.OrderItemService;
import com.kmzyc.b2b.service.OrderMainService;
import com.kmzyc.b2b.service.OrderPayService;
import com.kmzyc.b2b.service.ProductSkuService;
import com.kmzyc.b2b.service.ProductStockService;
import com.kmzyc.b2b.service.ShopCartInfoService;
import com.kmzyc.b2b.service.member.AddressService;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.b2b.shopcart.util.ShopCartUtil;
import com.kmzyc.b2b.shopcart.vo.CartProduct;
import com.kmzyc.b2b.shopcart.vo.Gift;
import com.kmzyc.b2b.shopcart.vo.NormalCartProduct;
import com.kmzyc.b2b.shopcart.vo.SellerShop;
import com.kmzyc.b2b.shopcart.vo.SettlementInfo;
import com.kmzyc.b2b.shopcart.vo.ShopCart;
import com.kmzyc.b2b.shopcart.vo.ShopCartItem;
import com.kmzyc.b2b.shopcart.vo.ShopCartProduct;
import com.kmzyc.b2b.shopcart.vo.ShopCartProductReminder;
import com.kmzyc.b2b.shopcart.vo.ShopCartUserInfo;
import com.kmzyc.b2b.shopcart.vo.ShopcartCacheProduct;
import com.kmzyc.b2b.util.CollectionUtils;
import com.kmzyc.b2b.util.SupplierType;
import com.kmzyc.b2b.util.pay.plugin.AliAppPayPlugIn;
import com.kmzyc.b2b.util.pay.plugin.KMTAppPayPlugIn;
import com.kmzyc.b2b.util.pay.util.PaymentUtil;
import com.kmzyc.b2b.util.pay.util.RSA;
import com.kmzyc.b2b.vo.PayMoneyPresell;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.b2b.vo.ShopCar;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.order.remote.OrderCallBackRemoteService;
import com.kmzyc.order.remote.OrderCreateRemoteService;
import com.kmzyc.order.remote.OrderRiskRemoteService;
import com.kmzyc.promotion.app.vobject.OrderVo;
import com.kmzyc.promotion.optimization.vo.PromotionProductData;
import com.kmzyc.promotion.util.RedisTemplate;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderMainExt;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.vobject.OrderPreferentialVO;
import com.whalin.MemCached.MemCachedClient;

/**
 * APP结算/支付
 * 
 * @author xlg
 * 
 */
@SuppressWarnings({"deprecation", "unchecked", "BigDecimalMethodWithoutRoundingCalled"})
@Scope("prototype")
@Controller("appSettAndPayAction")
public class AppSettAndPayAction extends AppBaseAction {
    private static final long serialVersionUID = -8842926142956855956L;

    private static Logger logger = LoggerFactory.getLogger(AppSettAndPayAction.class);

    private static final String DISTRI_MODE =
            "[{\"key\":\"1\",\"value\":\"快递\",deliveryTime:[{\"1\":\"工作日送货\"},{\"2\":\"休息日送货\"},{\"3\":\"工作日/休息日皆可送货\"}],\"confirmValue\":[{\"0\":\"否\"},{\"1\":\"是\"}]}]";// 配送方式
    private static final String PAY_MODEL = "[{\"key\":5,\"value\":\"在线支付\"}]";// 支付方式
    private ReturnResult<HashMap<String, Object>> returnResult;
    public static Date settementInfoCahceTime = new Date(36001000);// 1个小时

    @Resource
    private AccountInfoService accountInfoService;

    @Resource(name = "addressServiceImpl")
    private AddressService addressService;

    @Resource
    private MemCachedClient memCachedClient;

    @Resource
    private MyOrderService myOrderService;

    @Resource
    private ShopCartInfoService shopCartInfoService;

    @Resource
    private OrderItemService orderItemService;

    @Resource
    private LoginService loginService;

    @Resource(name = "carProductService")
    private CarProductService carProductService;

    @Resource
    private EraInfoService eraInfoService;

    @Resource(name = "productSkuServiceImpl")
    private ProductSkuService productSkuService;

    @Resource(name = "productStockServiceImpl")
    private ProductStockService productStockService;

    @Resource
    private OrderPayService orderPayService;

    @Autowired
    public OrderCreateRemoteService orderCreateRemoteService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource(name = "orderMainServiceImpl")
    private OrderMainService orderMainService;

    @Resource
    private OrderCallBackRemoteService orderCallBackRemoteService;

    @Resource
    private OrderRiskRemoteService orderRiskRemoteService;

    @Resource
    private CustomerRemoteService customerRemoteService;

    /**
     * 获取配送费用 null:error;
     * 
     * @return
     */
    public void getDistriCharge() {
        String rs = InterfaceResultCode.FAILED;
        Double fare = null;
        try {
            String uid = getUserid();
            SettlementInfo settlementInfo = (SettlementInfo) memCachedClient
                    .get(ConfigurationUtil.getString("b2b_sett_info_prex").concat(uid.toString()));
            if (settlementInfo == null) {
                returnResult = new ReturnResult(rs, "结算信息为空", null);
            } else {
                ShopCart shopcart = (ShopCart) settlementInfo.getSubTotal();
                fare = shopcart.getFreight().doubleValue();
                rs = InterfaceResultCode.SUCCESS;
                returnResult = new ReturnResult(rs, "获取成功",
                        JSONObject.parse("{\"fare\":\"" + fare + "\"}"));
            }
        } catch (Exception e) {
            logger.error("", e);
            returnResult = new ReturnResult(rs, "系统繁忙，请稍后再试！", null);
        }
        printJsonString(returnResult);
    }

    /**
     * 获取可用优惠券
     * 
     * @return
     */
    public void getAvaCoupon() {
        String rs = InterfaceResultCode.FAILED;
        try {
            Long uid = Long.parseLong(getUserid());
            SettlementInfo settlementInfo = (SettlementInfo) memCachedClient
                    .get(ConfigurationUtil.getString("b2b_sett_info_prex").concat(uid.toString()));
            if (settlementInfo != null) {
                ShopCart shopcart = (ShopCart) settlementInfo.getSubTotal();
                Long checkSellerId =
                        shopcart.getSellerShopList().getSellerShopMap().keySet().iterator().next();
                if (0 == ShopCar.SELF_AND_PROXY_KEY.compareTo(checkSellerId)) {
                    checkSellerId = 221L;// 生产库自营为221
                }
                Map<String, Object> couponParams = new HashMap<String, Object>();
                List<OrderVo> orderVoList = ShopCartUtil.getShopCarOrderList(shopcart);
                couponParams.put("customId", uid);// 用户ID
                couponParams.put("checkSellerId", checkSellerId);// 选中的商家ID
                couponParams.put("orderList", orderVoList);// 产品List
                couponParams.put("moneyCount", shopcart.getUncalculateMoney());// 金额
                couponParams.put("channel", ConfigurationUtil.getString("CHANNEL"));// 渠道
                // 可用
                HashMap<String, List<Coupon>> map =
                        accountInfoService.findCouponGrants(orderVoList, uid.toString(),
                                shopcart.getUncalculateMoney(), Long.parseLong(checkSellerId + ""));
                rs = InterfaceResultCode.SUCCESS;
                returnResult = new ReturnResult(rs, null, map.get("canUseCouponList"));
            } else {
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, "结算信息为空", null);
            }
        } catch (Exception e) {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "系统异常,请稍候再试", null);
            logger.error("获取不可用优惠券发生异常！", e);
        }
        printJsonString(returnResult);
    }

    /**
     * 获取订单结算信息-1:无结算商品;0:无法结算;-2：风控黑名单
     * 
     * @return
     */
    public void getSettInfo() {
        String rs = InterfaceResultCode.FAILED;
        JSONObject json = new JSONObject();
        String result = null;
        try {
            Long uid = Long.parseLong(getUserid());
            // 加载结算时，删除用户优惠券缓存数据 add by zhuyanling
            accountInfoService.delCouponsListCached(uid.toString());
            String loginType = Constants.KM_USER_TYPE;
            if (null != getRequest().getAttribute(APP_LOGIN_USER_TYPE)) {
                loginType = getRequest().getAttribute(APP_LOGIN_USER_TYPE).toString();
            }
            JSONObject params = AppJsonUtils.getJsonObject(getRequest());
            String buyType = params.containsKey("buyType") ? params.getString("buyType") : null;
            ShopCartUserInfo user = new ShopCartUserInfo(uid.toString(), true, loginType, buyType);
            ShopCart shopCart = null;
            if (Constants.EASY_BUY.equals(buyType)) {// 立即购
                String skuId = params.getString("skuIds").replace("n_", "");
                int number = params.containsKey("number")
                        ? Integer.parseInt(params.getString("number")) : 1;// 购买数量
                ShopcartCacheProduct prodcut =
                        new ShopcartCacheProduct(skuId, number, new Date(), true, "APP");
                shopCart = shopCartInfoService.generateSettlement(user, prodcut);
                shopCartInfoService.addProduct(user, "APP", number, skuId);// 添加商品到购物车
            } else if (buyType != null && buyType.equals(Constants.PRESCRIPTION_BUY)) {
                // 处方药（立即购买，不经过购物车页面直接到结算页面）
            } else {// 普通购买流程
                String idsCacheKey = ConfigurationUtil.getString("b2b_sett_ids_prex") + uid;
                String[] productIdArray = null;
                String ids = params.containsKey("skuIds") ? params.getString("skuIds") : null;
                productIdArray = StringUtil.isEmpty(ids)
                        ? (String[]) memCachedClient.get(idsCacheKey) : ids.split(",");
                if (null != productIdArray) {
                    memCachedClient.set(idsCacheKey, productIdArray);
                    shopCart = shopCartInfoService.generateSettlement(user, productIdArray);
                }
            }
            Map<String, ShopCartProductReminder> scpr = null;
            SellerShop sellerShop = null;
            if (null != shopCart && shopCart.getSellerShopList() != null
                    && !shopCart.getSellerShopList().isEmpty()) {
                sellerShop = shopCart.getSellerShopList().getSellerShopMap().entrySet().iterator()
                        .next().getValue();
            }
            if (orderRiskRemoteService.queryOrderRisk(uid)) {
                rs = "-2";
                result = "抱歉，当前账号已被锁定无法下单！如有疑问，请与商城客服联系！";
            } else if (null == shopCart || null == sellerShop) {
                result = "没有结算商品，请返回购物车！";
            } else if (null != (scpr = shopCart.getProductErrorReminder()) && !scpr.isEmpty()) {
                result = scpr.values().iterator().next().getMessage();
            } else {
                AccountInfo accountInfo = accountInfoService.findByLoginId(uid);
                json.put("result", "1");
                json.put("picPath", ConfigurationUtil.getString("CMS_PAGE_PATH"));// 图片路径
                json.put("balance", accountInfo.getAmountAvlibal());// 余额
                json.put("payRange", accountInfo.getPayRange());// 余额支付范围
                json.put("paymentPwdStatus", null != accountInfo.getPaymentpwd() ? "1" : "0");// 支付密码状态
                json.put("fare", shopCart.getFreight().doubleValue());// 运费
                json.put("sumMoney", shopCart.getUncalculateMoney());// 商品总金额
                json.put("discountMoney", shopCart.getDiscountMoney());// 打折金额
                json.put("reductionMoney", shopCart.getReductionMoney());// 满减金额
                json.put("additionalMoney", shopCart.getAdditionalMoney());// 加价购金额
                json.put("payAmount",
                        shopCart.getUncalculateMoney().subtract(shopCart.getReductionMoney())
                                .add(shopCart.getFreight()).add(shopCart.getAdditionalMoney())
                                .subtract(shopCart.getDiscountMoney()));// 应付款
                json.put("addressList", addressService.findByLoginId(uid.intValue()));// 收货地址
                json.put("distriMode", JSONObject.parse(DISTRI_MODE));// 配送方式
                json.put("payModel", JSONObject.parse(PAY_MODEL));// 支付方式
                BigDecimal score = new BigDecimal(0.00);
                int productNum = 0;

                if (Constants.LOGINTYPE.equals(loginType)) {
                    score = null != sellerShop.getProductPvalue() ? sellerShop.getProductPvalue()
                            : new BigDecimal(0.00);
                } /*
                   * else { 普通会员不计算积分 try { score = new
                   * BigDecimal(scoreRuleService.getBuyScore(shopCart .getCheckTotalMoney())); }
                   * catch (Exception e) { score = shopCart.getCheckTotalMoney();
                   * logger.error("查询积分规则报错：" + e); } }
                   */
                json.put("score", score);// 积分
                Map<String, Integer> giftStockMap =
                        CollectionUtils.deepCopy(shopCart.getGiftStockMap());
                Map<String, CartProduct> cpMap = shopCart.getCartProductMap();
                List<Map<String, Object>> productList = new ArrayList<Map<String, Object>>();
                Map<String, Object> product = null;
                Map<Long, Map<String, Object>> affixMap = new HashMap<Long, Map<String, Object>>();
                CartProduct cp = null;
                BigDecimal suitPriceSum = null;
                List<PromotionProductData> ppdList = null;
                Integer stock = 0, amount = 0;
                for (String key : shopCart.getProductMap().keySet()) {
                    ShopCartProduct scp = shopCart.getProductMap().get(key);
                    if (key.indexOf("n_") >= 0) {
                        product = new HashMap<String, Object>();
                        cp = cpMap.get(key);
                        product.put("skuId", scp.getId());// SKUID
                        product.put("productType", 1);// 类型1:普通产品;3:套餐;4:赠品;5:加价购;
                        product.put("title", scp.getTitle());// 标题
                        product.put("amount", scp.getAmount());// 数量
                        product.put("img", scp.getImg());// 图片
                        product.put("costPrice", cp.getCostPrice());// 成本价
                        product.put("price", cp.getPrice());// 销售单价
                        product.put("finalPrice", scp.getFinalPrice());// 实际单价
                        product.put("unitWeight", cp.getUnitWeight());// 单件重量
                        product.put("PrefType",
                                (null != scp.getProductPromotion()
                                        && null != scp.getProductPromotion().getPricePromotion())
                                                ? scp.getProductPromotion().getPricePromotion()
                                                        .getType()
                                                : 0);// 优惠类型
                                                     // 10:特价;8打折
                        product.put("totalPrice", scp.getProductPriceTotal());// 总价
                        productNum += scp.getAmount().intValue();
                        productList.add(product);
                        if (null != scp.getProductPromotion()
                                && null != (ppdList =
                                        scp.getProductPromotion().getAffixProductList())
                                && !ppdList.isEmpty()) {
                            for (PromotionProductData pd : ppdList) {
                                if (affixMap.containsKey(pd.getProductSkuId())) {
                                    product = affixMap.get(pd.getProductSkuId());
                                    product.put("amount", pd.getNum() * scp.getAmount().longValue()
                                            + (null == product.get("amount") ? 0
                                                    : Long.parseLong(
                                                            product.get("amount").toString())));// 数量
                                } else if (giftStockMap.containsKey(pd.getProductSkuId().toString())
                                        && null != (stock =
                                                giftStockMap.get(pd.getProductSkuId().toString()))
                                        && stock > 0) {
                                    amount = pd.getNum().intValue() * scp.getAmount().intValue();
                                    amount = stock > amount ? amount : stock;
                                    product = new HashMap<String, Object>();
                                    product.put("skuId", pd.getProductSkuId());// SKUID
                                    product.put("productType", 6);// 类型1:普通产品;3:套餐;4:赠品;5:加价购;6:附赠
                                    product.put("title",
                                            pd.getProductTitle() + " " + pd.getSkuAttrValue());// 标题
                                    product.put("amount", amount);// 数量
                                    product.put("img", scp.getImgConnectPath(pd.getImagePath()));// 图片
                                    product.put("costPrice", 0);// 成本价
                                    product.put("price", 0);// 销售单价
                                    product.put("finalPrice", 0);// 实际单价
                                    product.put("PrefType", 0);// 优惠类型 10:特价;8打折
                                    product.put("unitWeight", 0);// 单件重量
                                    product.put("totalPrice", 0);// 总价
                                    affixMap.put(pd.getProductSkuId(), product);
                                    productNum += amount;
                                    giftStockMap.put(pd.getProductSkuId().toString(),
                                            stock - amount);
                                }
                            }
                        }
                    } else if (key.indexOf("c_") >= 0) {
                        suitPriceSum = BigDecimal.ZERO;
                        for (NormalCartProduct ncp : scp.getProductList()) {
                            suitPriceSum = suitPriceSum.add(
                                    ncp.getFinalPrice().multiply(new BigDecimal(ncp.getAmount()))
                                            .multiply(scp.getAmount()));
                        }
                        for (int i = 0, len = scp.getProductList().size(); i < len; i++) {
                            product = new HashMap<String, Object>();
                            NormalCartProduct ncp = scp.getProductList().get(i);
                            product.put("suitId", scp.getId());// suitId
                            product.put("suitTitle", scp.getTitle());// suitId
                            product.put("skuId", ncp.getProductSkuId());// SKUID
                            product.put("productType", 3);// 类型1:普通产品;3:套餐;4:赠品;5:加价购;
                            product.put("title", ncp.getTitle());// 标题
                            product.put("amount", ncp.getAmount() * scp.getAmount().intValue());// 数量
                            product.put("img", ncp.getImg());// 图片
                            product.put("costPrice", ncp.getCostPrice());// 成本价
                            product.put("price", ncp.getPrice());// 销售单价
                            product.put("finalPrice", ncp.getFinalPrice());// 实际单价
                            product.put("unitWeight", ncp.getUnitWeight());// 单件重量
                            product.put("PrefType",
                                    (null != scp.getProductPromotion() && null != scp
                                            .getProductPromotion().getPricePromotion())
                                                    ? scp.getProductPromotion().getPricePromotion()
                                                            .getType()
                                                    : 0);// 优惠类型 10:特价;8打折
                            product.put("totalPrice",
                                    ncp.getFinalPrice().multiply(new BigDecimal(ncp.getAmount()))
                                            .multiply(scp.getAmount()));// 总价
                            productNum += ncp.getAmount() * scp.getAmount().intValue();
                            productList.add(product);
                        }
                    }
                }
                String present = null;
                List<Long> promotionSkuIds = new ArrayList<Long>();
                List<String> couponList = new ArrayList<String>();
                if (null != shopCart.getShopCartItemMap()
                        && !shopCart.getShopCartItemMap().isEmpty()) {
                    for (Iterator<ShopCartItem> sciIt =
                            shopCart.getShopCartItemMap().values().iterator(); sciIt.hasNext();) {
                        ShopCartItem sci = sciIt.next();
                        if (null != sci.getGifts() && !sci.getGifts().isEmpty()) {
                            for (Gift g : sci.getGifts()) {
                                promotionSkuIds.add(Long.parseLong(g.getId()));
                            }
                        } else if (null != (present = sci.getDefaultPresents())
                                && null != sci.getRulePresents()
                                && sci.getRulePresents().containsKey(present)) {
                            for (Gift g : sci.getRulePresents().get(present)) {
                                promotionSkuIds.add(Long.parseLong(g.getId()));
                            }
                        }
                    }
                }
                Map<Long, CartProduct> promotionProducts = promotionSkuIds.isEmpty() ? null
                        : productSkuService.queryPromotionProducts(promotionSkuIds);
                if (null != shopCart.getShopCartItemMap()
                        && !shopCart.getShopCartItemMap().isEmpty()) {
                    for (Iterator<ShopCartItem> sciIt =
                            shopCart.getShopCartItemMap().values().iterator(); sciIt.hasNext();) {
                        ShopCartItem sci = sciIt.next();
                        if (null != sci.getGifts() && !sci.getGifts().isEmpty()) {
                            for (Gift g : sci.getGifts()) {
                                promotionSkuIds.add(Long.parseLong(g.getId()));
                            }
                        } else if (null != (present = sci.getDefaultPresents())
                                && null != sci.getRulePresents()
                                && sci.getRulePresents().containsKey(present)) {
                            for (Gift g : sci.getRulePresents().get(present)) {
                                promotionSkuIds.add(Long.parseLong(g.getId()));
                            }
                        }
                    }
                }
                if (null != shopCart.getShopCartItemMap()
                        && !shopCart.getShopCartItemMap().isEmpty()) {
                    for (Iterator<ShopCartItem> sciIt =
                            shopCart.getShopCartItemMap().values().iterator(); sciIt.hasNext();) {
                        ShopCartItem sci = sciIt.next();
                        if (null != promotionProducts && null != sci.getGifts()
                                && !sci.getGifts().isEmpty()) {
                            for (Gift g : sci.getGifts()) {
                                stock = giftStockMap.get(g.getId());
                                stock = null == stock ? 0 : stock;
                                if (null == (cp =
                                        promotionProducts.get(Long.parseLong(g.getId())))) {
                                    continue;
                                }
                                product = new HashMap<String, Object>();
                                giftStockMap.put(g.getId(), stock - g.getAmount());
                                product.put("skuId", g.getId());// SKUID
                                product.put("productType", 5);// 类型1:普通产品;3:套餐;4:赠品;5:加价购;
                                product.put("title", g.getName());// 标题
                                product.put("amount", g.getAmount());// 数量
                                product.put("img", g.getAppImg());// 图片
                                product.put("costPrice", cp.getCostPrice());// 成本价
                                product.put("price", cp.getPrice());// 销售单价
                                product.put("finalPrice", g.getPrice());// 实际单价
                                product.put("PrefType", 0);// 优惠类型 10:特价;8打折
                                product.put("unitWeight", cp.getUnitWeight());// 单件重量
                                product.put("totalPrice",
                                        g.getPrice().multiply(new BigDecimal(g.getAmount())));// 总价
                                productNum += g.getAmount();
                                productList.add(product);
                            }
                        } else if (null != promotionProducts
                                && null != (present = sci.getDefaultPresents())
                                && null != sci.getRulePresents()
                                && sci.getRulePresents().containsKey(present)) {
                            for (Gift g : sci.getRulePresents().get(present)) {
                                stock = giftStockMap.get(g.getId());
                                stock = null == stock ? 0 : stock;
                                if (null == (cp = promotionProducts.get(Long.parseLong(g.getId())))
                                        || g.getAmount() > stock) {
                                    continue;
                                }
                                product = new HashMap<String, Object>();
                                giftStockMap.put(g.getId(), stock - g.getAmount());
                                product.put("skuId", g.getId());// SKUID
                                product.put("productType", 4);// 类型1:普通产品;3:套餐;4:赠品;5:加价购;
                                product.put("title", g.getName());// 标题
                                product.put("amount", g.getAmount());// 数量
                                product.put("img", g.getAppImg());// 图片
                                product.put("costPrice", cp.getCostPrice());// 成本价
                                product.put("price", 0);// 销售单价
                                product.put("finalPrice", 0);// 实际单价
                                product.put("PrefType", 0);// 优惠类型 10:特价;8打折
                                product.put("unitWeight", cp.getUnitWeight());// 单件重量
                                product.put("totalPrice",
                                        g.getPrice().multiply(new BigDecimal(g.getAmount())));// 总价
                                productNum += g.getAmount();
                                productList.add(product);
                            }
                        } else if (null != sci.getCoupon()) {
                            couponList.add(sci.getCoupon().getId());
                        }
                    }
                }
                if (null != affixMap && !affixMap.isEmpty()) {
                    productList.addAll(affixMap.values());
                }
                json.put("productList", productList);// 产品列表
                json.put("couponList", couponList);// 优惠券列表
                json.put("productCount", productNum);// 产品数量
            }
            SettlementInfo info = new SettlementInfo();
            info.setSubTotal(shopCart);
            info.setOrderVoList(ShopCartUtil.getShopCarOrderList(shopCart));
            memCachedClient.set(
                    ConfigurationUtil.getString("b2b_sett_info_prex").concat(uid.toString()), info,
                    settementInfoCahceTime);
            if (null == result) {
                rs = InterfaceResultCode.SUCCESS;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        returnResult = new ReturnResult(rs, result, json);
        printJsonString(returnResult);
    }

    /**
     * 提交订单
     * 
     * @return
     */
    public void saveOrder() {
        String rs = InterfaceResultCode.FAILED;
        String result = null, flag = null;
        Map<String, String> rMap = new HashMap<String, String>();
        Long loginId = Long.parseLong(getRequest().getAttribute(APP_LOGIN_FLAG).toString());
        // 加锁成功的标识，true:标识加锁成功，false:表示加锁失败
        boolean tryLockFlag = false;
        try {
            tryLockFlag = redisTemplate.tryLock(loginId.toString());
            if (!tryLockFlag) {
                flag = "-1";
                result = "系统繁忙，请稍后再试！";
                if (null != flag) {
                    rMap.put("flag", flag);
                }
                returnResult = new ReturnResult(rs, result, rMap);
                printJsonString(returnResult);
                return;
            }
            String loginType = Constants.KM_USER_TYPE;
            if (null != getRequest().getAttribute(APP_LOGIN_USER_TYPE)) {
                loginType = getRequest().getAttribute(APP_LOGIN_USER_TYPE).toString();
            }
            JSONObject params = AppJsonUtils.getJsonObject(getRequest());
            String buyType = getJsonStr(params, "buyType");
            String outStockConfirm = getJsonStr(params, "outStockConfirm");
            String addressId = getJsonStr(params, "addressId");
            ShopCartUserInfo settShop =
                    new ShopCartUserInfo(loginId.toString(), true, loginType, buyType);
            ShopCart settShopCart = null;
            String[] productIdArray = null;
            if (Constants.PRESCRIPTION_BUY.equals(buyType) || Constants.EASY_BUY.equals(buyType)) {
                String skuId = params.getString("skuIds").replace("n_", "");
                int number = params.containsKey("number")
                        ? Integer.parseInt(params.getString("number")) : 1;// 购买数量
                ShopcartCacheProduct prodcut =
                        new ShopcartCacheProduct(skuId, number, new Date(), true, "APP");
                settShopCart = shopCartInfoService.generateSettlement(settShop, prodcut);
            } else {
                productIdArray = (String[]) memCachedClient.get(ConfigurationUtil
                        .getString("b2b_sett_ids_prex").concat(loginId.toString()));
                settShopCart = shopCartInfoService.generateSettlement(settShop, productIdArray);
            }
            Address address = null;
            if (null != addressId) {
                address = addressService.findByNAddressID(loginId, Integer.parseInt(addressId));
            }
            AccountInfo settAccountInfo = null;
            BigDecimal balance = null != params.getString("balance")
                    ? new BigDecimal(params.getString("balance")) : BigDecimal.ZERO;
            balance = balance.setScale(2, BigDecimal.ROUND_FLOOR);
            String couponid = getJsonStr(params, "couponid");


            UserBaseInfo userBaseInfo = new UserBaseInfo();
            userBaseInfo.setLoginId(loginId);
            userBaseInfo.setPassword(getJsonStr(params, "payPwd"));

            /*
             * userBaseInfo = this.customerRemoteService.queryUserPasswordTwice(userBaseInfo,"pay");
             * 
             * if(null == userBaseInfo ){ flag = "-1"; result = "系统繁忙，请稍后再试！"; if (null != flag) {
             * rMap.put("flag", flag); } returnResult = new ReturnResult(rs, result, rMap);
             * printJsonString(returnResult); return; }
             */

            // String payPwd = StringUtil.passwordTwiceEncryption(getJsonStr(params,
            // "payPwd"),saltInfo.getPaySalt());

            // 优惠券
            Coupon coupon = null;
            if (null == addressId || null == address || StringUtil.isEmpty(address.getCellphone())
                    || StringUtil.isEmpty(address.getDetailedAddress())) {
                flag = "4";
                result = "收货地址为空！";
            } else if (null == (settAccountInfo =
                    accountInfoService.getAccountInfoByLoginIdForSett(loginId))) {
                flag = "7";
                result = "帐号不存在！";
            } else if (Constants.PRESCRIPTION_BUY.equals(buyType) && params.containsKey("hduid")
                    && !MD5.getMD5Str(Constants.PRESCRIPTION_BUY + loginId)
                            .equals(params.getString("hduid"))) {
                flag = "6";
                result = "请重新购买！";
            } else if (null == settShopCart || null != settShopCart.getProductErrorReminder()) {
                flag = "5";
                if (null != settShopCart) {
                    result = settShopCart.getProductErrorReminder().values().iterator().next()
                            .getMessage();
                    if (result.indexOf("库存不足") >= 0 || result.indexOf("无库存") >= 0) {
                        flag = "3";
                        result = "存在库存不足的商品,无法进行购买！";
                    } else if (result.indexOf("已下架") >= 0 || result.indexOf("存在已下架") >= 0) {
                        flag = "2";
                        result = "存在已下架的商品,无法进行购买！";
                    }
                } else {
                    result = "购物车为空！";
                }
            } else if (settAccountInfo.getAmountAvlibal() < balance.doubleValue()) {
                flag = "8";
                result = "余额不足！";
            } else if ((balance.compareTo(BigDecimal.ZERO) > 0
                    || (null != couponid && null != settAccountInfo.getPayRange()
                            && settAccountInfo.getPayRange().indexOf(",2") >= 0))
                    && (null == (userBaseInfo =
                            this.customerRemoteService.queryUserPasswordTwice(userBaseInfo, "pay")))
                    && (null == userBaseInfo.getPassword() || !userBaseInfo.getPassword()
                            .equalsIgnoreCase(settAccountInfo.getPaymentpwd()))) {
                flag = "10";
                result = "支付密码不正确！";
            } else if (balance.compareTo(BigDecimal.ZERO) > 0
                    && (null != settAccountInfo.getPaymentpwd()
                            && 1 != myOrderService.enablePayPWD(loginId))) {
                flag = "10";
                result = "支付密码不正确！";
            } else if (couponid != null && null == (coupon =
                    accountInfoService.getCouponByCouponGrantId(Long.parseLong(couponid)))) {
                flag = "9";
                result = "优惠券不存在！";
            } else if (coupon != null
                    && !accountInfoService.checkCoupon(couponid, loginId.toString())) {
                flag = "9";
                result = "选择的优惠券不可用！";
            } else if (coupon != null && balance.compareTo(BigDecimal.ZERO) > 0
                    && "1".equals(coupon.getUseLimitsType())) {
                flag = "11";
                result = "选择的优惠券不能和余额一起使用！";
            } else if ((rMap = orderItemService.compareSett(settShopCart,
                    (ShopCart) ((SettlementInfo) memCachedClient.get(ConfigurationUtil
                            .getString("b2b_sett_info_prex").concat(loginId.toString())))
                                    .getSubTotal(),
                    outStockConfirm)).isEmpty()) {
                OrderMainExt orderMainExt = null;
                OrderMain orderMain = new OrderMain();
                // orderMain.setPrescriptionAttachment(this.showPath +
                // prescriptionAttachmentFile);// 药方图片路径
                orderMain.setOrderSource(Constants.ORDER_SOURCE_APP);
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
                /*
                 * orderMain.setOrderPurchaserType(Long.valueOf(
                 * OrderDictionaryEnum.OrderPurchaserType.Register.getKey()));
                 * orderMain.setOrderPurchaserType(Constants.ORDER_PURCHASER_TYPE_CUSTOMER);
                 */
                orderMain.setConsigneeName(address.getName());
                orderMain.setConsigneeMobile(address.getCellphone());
                orderMain.setConsigneeAddr(address.getProvince() + address.getCity()
                        + address.getArea() + address.getDetailedAddress());
                orderMain.setAddressId(BigDecimal.valueOf(address.getAddressId()));
                orderMain.setCreateDate(new Date());
                orderMain.setCustomerId(new BigDecimal(loginId));
                orderMain.setDisabled(1l);// 可用
                orderMain.setOrderStatus(Constants.ORDER_STATUS_NOT_PAY);
                orderMain.setOrderChannel(ConfigurationUtil.getString("CHANNEL")); // 渠道
                orderMain.setOrderDescription(getJsonStr(params, "orderRemark"));

                // 订单扩展表
                orderMainExt = new OrderMainExt();
                SellerShop sellerShop = null;
                try {
                    sellerShop = settShopCart.getSellerShopList().getSellerShopMap().values()
                            .iterator().next();
                } catch (Exception e) {
                    logger.error("商家购物车产品发生异常", e);
                }
                orderMain.setOrderMainext(orderMainExt);
                boolean isTimeLogin = null != loginType && Constants.LOGINTYPE.equals(loginType);
                if (isTimeLogin && null != sellerShop && null != sellerShop.getProductPvalue()) {
                    orderMain.setOrderPv(sellerShop.getProductPvalue().floatValue());
                }
                boolean isTimesShop = false, isEnterSale = false;// 时代购物/入驻标识

                if (isTimeLogin) {
                    orderMain.setOrderPurchaserType(Long
                            .valueOf(OrderDictionaryEnum.OrderPurchaserType.TimeMember.getKey()));
                } else {
                    orderMain.setOrderPurchaserType(
                            Long.valueOf(OrderDictionaryEnum.OrderPurchaserType.Register.getKey()));
                    // orderMain.setOrderPurchaserType(Constants.ORDER_PURCHASER_TYPE_CUSTOMER);
                }

                if (null != sellerShop && null != sellerShop.getSupplierType()
                        && (SupplierType.SELLER_TYPE_ENTER_SALE.getIndex() == sellerShop
                                .getSupplierType()
                                || SupplierType.SELLER_TYPE_ENTER_SALE_FOR_TIMES
                                        .getIndex() == sellerShop.getSupplierType())) {
                    isEnterSale = true;
                    orderMain.setCommerceId("" + sellerShop.getSellerId());
                    orderMain.setCommerceName(carProductService
                            .queryCorporateNameBySupplierId(sellerShop.getSellerId()));
                    if (SupplierType.SELLER_TYPE_ENTER_SALE_FOR_TIMES.getIndex() == sellerShop
                            .getSupplierType()) {
                        isTimesShop = true;
                        orderMain.setOrderType(
                                Long.valueOf(OrderDictionaryEnum.Order_Type.Normal.getKey()));
                    }
                }
                BigDecimal rate = BigDecimal.ONE, disCount = BigDecimal.ONE;// 满减均摊比例/折扣
                try {
                    if (isTimeLogin && isTimesShop) {
                        disCount = loginService.getactualDiscount(eraInfoService
                                .queryEraInfoByLoginId(loginId).getEraNo().toString());
                        if (null != disCount) {
                            disCount = disCount.divide(BigDecimal.TEN).divide(BigDecimal.TEN);
                        }
                    }
                } catch (Exception e) {
                    logger.error("获取时代会员折扣发生异常", e);
                }

                // 配送
                Long deliveryTime = null != params.getString("deliveryTime")
                        ? Long.parseLong(params.getString("deliveryTime")) : 1l;
                Short isconfirm = null != params.getString("isconfirm")
                        ? Short.parseShort(params.getString("isconfirm")) : 0;
                // String distriMode=getJsonStr(params, "distriMode");//配送方式
                orderMain.setPayMethod(
                        params.containsKey("payMothed") ? params.getLong("payMothed") : 5l);// 在线支付
                orderMain.setDeliveryDateType(deliveryTime);
                orderMain.setConfirmDelivery(isconfirm);
                // 发票
                orderMain.setInvoiceInfoType(getJsonStr(params, "invoiceType"));
                orderMain.setInvoiceInfoContent(getJsonStr(params, "invoiceContent"));
                orderMain.setInvoiceInfoTitle(getJsonStr(params, "invoicetitle"));
                orderMain.setAccountBalance(balance);
                BigDecimal fare = settShopCart.getFreight();
                orderMain.setCommoditySum(settShopCart.getUncalculateMoney());
                orderMain.setOriginalOrderSum(settShopCart.getUncalculateMoney());
                orderMain.setDiscountAmount(settShopCart.getReductionMoney());
                orderMain.setFare(fare);
                orderMain.setOrderDiscount(settShopCart.getDiscountMoney());// 会员打折
                orderMain.setWeight(settShopCart.getWeight());
                // 应付金额=商品金额+运费-优惠金额+加价购金额-时代会员利率
                orderMain.setAmountPayable(settShopCart.getUncalculateMoney()
                        .add(fare.subtract(settShopCart.getReductionMoney())).add(settShopCart
                                .getAdditionalMoney().subtract(settShopCart.getDiscountMoney())));
                // 商品
                BigDecimal payableMoney =
                        settShopCart.getUncalculateMoney().add(settShopCart.getAdditionalMoney());// 促销活动后商品总金额
                BigDecimal realMoney =
                        settShopCart.getUncalculateMoney().add(settShopCart.getAdditionalMoney())
                                .subtract(settShopCart.getReductionMoney())
                                .subtract((null == coupon || null == coupon.getCouponMoney())
                                        ? BigDecimal.ZERO : coupon.getCouponMoney());// 实付金额=总额+加价购-满减-优惠券
                if (realMoney.compareTo(BigDecimal.ZERO) < 0) {
                    realMoney = BigDecimal.ZERO;// 使用超额优惠券
                    logger.info(settAccountInfo.getAccountLogin() + "使用超额优惠券" + couponid + "\t总额:"
                            + settShopCart.getUncalculateMoney() + "加价购:"
                            + settShopCart.getAdditionalMoney() + "满减:"
                            + settShopCart.getReductionMoney() + "优惠券:"
                            + (null != coupon ? coupon.getCouponMoney() : 0));
                }
                if (payableMoney.compareTo(BigDecimal.ZERO) > 0) {
                    rate = realMoney.divide(payableMoney, 5, BigDecimal.ROUND_HALF_UP);
                }
                if (realMoney.add(fare).subtract(balance).compareTo(BigDecimal.ZERO) < 0) {
                    flag = "-1";
                    result = "余额不足！";
                    returnResult = new ReturnResult(rs, result, null);
                    printJsonString(returnResult);
                    return;
                }
                List<OrderItem> oiList = new ArrayList<OrderItem>();// 订单明细
                List<OrderPreferentialVO> opList = new ArrayList<OrderPreferentialVO>();// 优惠明细
                boolean[] identity = {isEnterSale, isTimeLogin, isTimesShop};
                BigDecimal[] moneyInfo = {rate, disCount, realMoney, payableMoney};
                orderItemService.generateOrderItem(settAccountInfo, orderMain, settShopCart, oiList,
                        opList, moneyInfo, identity, new PayMoneyPresell());
                if (null == oiList || oiList.isEmpty()) {
                    flag = "5";
                    result = "没有可结算商品！";
                } else {
                    List<OrderPayStatement> opsList = new ArrayList<OrderPayStatement>();// 支付明细
                    OrderPayStatement ops = null;
                    Date now = new Date();

                    if (null != coupon) {
                        ops = new OrderPayStatement();
                        ops.setPaymentWay(Constants.PAY_METHOD_PREFERENTIAL);
                        ops.setAccount(settAccountInfo.getAccountLogin());
                        ops.setOrderMoney(coupon.getCouponMoney());
                        ops.setCreateDate(now);
                        ops.setPreferentialNo(new BigDecimal(coupon.getCouponId()));
                        ops.setPreferentialGrantId(new BigDecimal(couponid));
                        ops.setPreferentialName(coupon.getCouponName());
                        ops.setFlag(Constants.ORDER_PAY_FLAG_PAYMENT);
                        opsList.add(ops);
                    }
                    // 余额
                    if (balance.compareTo(BigDecimal.ZERO) > 0) {
                        ops = new OrderPayStatement();
                        ops.setPaymentWay(Constants.PAY_METHOD_BALANCE);
                        ops.setAccount(settAccountInfo.getAccountLogin());
                        ops.setOrderMoney(balance);
                        ops.setCreateDate(now);
                        ops.setFlag(Constants.ORDER_PAY_FLAG_PAYMENT);
                        opsList.add(ops);
                    }
                    /*
                     * int score = 0; try { score =
                     * scoreRuleService.getBuyScore(settShopCart.getCheckTotalMoney()); } catch
                     * (Exception e) { score = settShopCart.getCheckTotalMoney().intValue();
                     * logger.error("查询积分规则报错：" + e); }
                     * 
                     * if (!isTimeLogin) { // 积分 orderMain.setTotalCredit(score +
                     * orderMain.getTotalCredit()); } else {
                     */
                    orderMain.setTotalCredit(0l);
                    // }

                    String orderCode = orderCreateRemoteService.createOrder(orderMain, oiList,
                            opList, opsList);
                    orderMain.setOrderCode(orderCode);
                    myOrderService.orderUserSourceType("", orderMain, oiList);
                    if (null != productIdArray) {
                        shopCartInfoService.deleteProduct(loginId.toString(), productIdArray);
                    }

                    rMap.put("orderCode", orderCode);
                    rMap.put("payMoney", realMoney.add(fare).subtract(balance).toString());
                    rMap.put("rechargeOrOrderFlag", "2");
                    rMap.put("isCreate", "1");

                    String startDate =
                            ConfigurationUtil.getString("APP_KMT_PAY_PRIVILEGE_STARTTIME");
                    String endDate = ConfigurationUtil.getString("APP_KMT_PAY_PRIVILEGE_ENDTIME");
                    // 一次性活动，活动结束代码可以删除
                    if (!StringUtil.isEmpty(startDate)
                            && new Date().after(DateTimeUtils.parseDate(startDate))
                            && !StringUtil.isEmpty(endDate)
                            && new Date().before(DateTimeUtils.parseDate(endDate))) {

                        rMap.put("privilegeInfo",
                                StringUtil.isEmpty(
                                        ConfigurationUtil.getString("APP_KMT_PAY_PRIVILEGE_INFO"))
                                                ? ""
                                                : ConfigurationUtil
                                                        .getString("APP_KMT_PAY_PRIVILEGE_INFO"));
                    }

                    if (realMoney.add(fare).subtract(balance).compareTo(BigDecimal.ZERO) <= 0) {
                        try {
                            if (orderPayService.orderRemotePay(orderCode, loginId, null)) {
                                // myOrderService.pushOrderUserSource(orderCode);
                                try {
                                    if (!productStockService.updateProductOrderQuantityCache(
                                            loginId, true, orderCode)) {
                                        logger.error("订单" + orderCode + "减少活动库存失败");
                                    }
                                } catch (Exception e) {
                                    logger.error("", e);
                                }
                            }
                        } catch (Exception e) {
                            logger.error("", e);
                        }
                    }
                    rs = InterfaceResultCode.SUCCESS;
                }
            }
            // 删除用户优惠券缓存数据 add by zhuyanling
            accountInfoService.delCouponsListCached(loginId.toString());
            String temp = null;
            if (null != rMap && null != (temp = rMap.get("flag"))) {
                flag = temp;
                if ("1".equals(temp)) {
                    result = "购物车发生变化！";
                } else if ("2".equals(temp)) {
                    result = "结算商品已下架！";
                } else if ("3".equals(temp)) {
                    result = rMap.get("msg") + "库存不足！";
                } else if ("-2".equals(temp)) {
                    flag = "3";// @Todo 后续需将返回附赠异常 rMap.get("msg")
                    result = "存在附赠产品库存不足！";
                }
            }
        } catch (Exception e) {
            logger.error("", e);
            flag = "-1";
            result = "系统繁忙，请稍后再试！";
        } finally {
            if (tryLockFlag) {
                redisTemplate.release(loginId.toString());
            }
        }
        if (null != flag) {
            rMap.put("flag", flag);
        }
        returnResult = new ReturnResult(rs, result, rMap);
        printJsonString(returnResult);
    }

    /**
     * 校验支付密码 0:error;1:success;-1:overbalan
     * 
     * @return
     */
    public void checkPayPWD() {
        String rs = InterfaceResultCode.FAILED;
        String result = "系统繁忙，请稍后再试！";
        String flag = "0";
        try {
            Long uid = Long.parseLong(getRequest().getAttribute(APP_LOGIN_FLAG).toString());
            JSONObject params = AppJsonUtils.getJsonObject(getRequest());
            UserBaseInfo userBaseInfo = new UserBaseInfo();
            userBaseInfo.setLoginId(uid);
            userBaseInfo.setPassword(params.getString("payPWD"));
            userBaseInfo = this.customerRemoteService.queryUserPasswordTwice(userBaseInfo, "pay");
            // SaltInfo saltInfo = this.saltInfoService.querySaltInfo(userBaseInfo);
            if (null == userBaseInfo) {
                flag = "3";
                result = "支付密码不正确！";
            } else {

                // String payPWD =
                // StringUtil.passwordTwiceEncryption(params.getString("payPWD"),saltInfo.getPaySalt());
                String payMoney =
                        params.containsKey("payMoney") ? params.getString("payMoney") : null;
                AccountInfo accountInfo = accountInfoService.findByLoginId(uid);
                if (null != accountInfo && userBaseInfo.getPassword()
                        .equalsIgnoreCase(accountInfo.getPaymentpwd())) {
                    result = null == payMoney ? "-1"
                            : (Double.parseDouble(payMoney) <= accountInfo.getAmountAvlibal() ? "1"
                                    : "-1");
                }
                if ("1".equals(result)) {
                    rs = InterfaceResultCode.SUCCESS;
                    result = "验证成功！";
                    flag = "1";
                } else if ("-1".equals(result)) {
                    result = "余额不足！";
                    flag = "2";
                } else {
                    flag = "3";
                    result = "支付密码不正确！";
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        returnResult = new ReturnResult(rs, result, flag);
        printJsonString(returnResult);
    }

    /**
     * 记录/更新支付准备流水 0:error;1:success;-1:notcurrenUser;2:outDate
     * 
     * @return
     */
    public void mergeReadyPayStat() {
        String rs = InterfaceResultCode.FAILED;
        String result = "系统繁忙，请稍后再试！";
        try {
            String uid = (String) getRequest().getAttribute(APP_LOGIN_FLAG);
            JSONObject params = AppJsonUtils.getJsonObject(getRequest());
            String orderCode = params.getString("orderCode");
            com.kmzyc.b2b.model.OrderMain orderMain =
                    myOrderService.findOrderByOrderCode(orderCode);
            Date date = new Date();
            if (null != orderMain && null != orderMain.getCreateDate() && date.getTime() - orderMain
                    .getCreateDate().getTime() > (3600000 * Constants.ORDER_DISABLED_TIME)) {
                result = "订单超时，请取消后重新下单！";
            } else if (null != orderMain && null != orderMain.getCustomerId()
                    && null != orderMain.getCreateDate()
                    && uid.equals(orderMain.getCustomerId().toString())) {
                List<OrderPayStatement> paystatementList = new ArrayList<OrderPayStatement>();
                OrderPayStatement paystatement = new OrderPayStatement();
                paystatement.setPaymentWay(Constants.PAY_METHOD_ONLINE_PLATFORM);
                paystatement.setAccount(orderMain.getCustomerAccount());
                paystatement.setOrderMoney(params.getBigDecimal("orderMoney"));
                paystatement.setCreateDate(date);
                paystatement.setEndDate(date);
                paystatement.setFlag(Constants.ORDER_PAY_FLAG_PAYMENT);
                paystatement.setOrderCode(orderCode);
                paystatement.setPlatFormCode(params.getString("platformCode"));
                paystatement.setPlatFormName(params.containsKey("platformName")
                        ? params.getString("platformName") : null);
                paystatement.setBankCode(
                        params.containsKey("bankCode") ? params.getString("bankCode") : null);
                paystatement
                        .setState(Long.valueOf(OrderDictionaryEnum.OrderPayState.Ready.getKey()));// 支付状态：准备
                paystatementList.add(paystatement);
                // OrderCallBackRemoteService orderCallBackRemoteService =
                // (OrderCallBackRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_ORDER,
                // "callBackService");
                orderCallBackRemoteService.insertPayStatement(paystatementList);
                result = null;
                rs = InterfaceResultCode.SUCCESS;
            } else if (null != orderMain) {
                // 非当前用户订单
                result = "非当前用户！";
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        returnResult = new ReturnResult(rs, result, null);
        printJsonString(returnResult);
    }

    /*
    *//** 获取推广信息 *//*
                    * private String getMarketingInfo(String skuId) {
                    * com.alibaba.fastjson.JSONObject rulejson =
                    * com.alibaba.fastjson.JSONObject.parseObject(ShopCartUtil.getCookieValue(
                    * this.getRequest(), "sruleId")); if (rulejson == null) { return null; } return
                    * rulejson.getString(skuId); }
                    */

    public void getAppPaySign() {
        try {
            JSONObject params = AppJsonUtils.getJsonObject(this.getRequest());
            String orderCode =
                    params.containsKey("orderCode") ? params.getString("orderCode") : null; // 订单编号
            String payMothed =
                    params.containsKey("payMothed") ? params.getString("payMothed") : null; // 支付方式
            String unionLogin =
                    params.containsKey("unionLogin") ? params.getString("unionLogin") : null; // 联合登录标志
                                                                                              // 0-不支付
                                                                                              // 1-支付（为了APP升级能平滑过度使用，后期可以作废）
            if (null == orderCode || "".equals(orderCode)) {
                String errorStr = "传入的参数orderCode为空";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }
            if (null == payMothed || "".equals(payMothed)) {
                String errorStr = "传入的参数payMothed为空";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            String uid = this.getUserid();// 获取用户id
            if (null == uid || "".equals(uid)) {
                String errorStr = "传入的用户Id为空";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            BigDecimal orderMoney = orderMainService.queryPayMoney(orderCode);
            if (null == orderMoney || BigDecimal.ZERO.compareTo(orderMoney) == 0) {
                String errorStr = "支付金额为0";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            DecimalFormat myformat = new java.text.DecimalFormat("0.00");
            String moneyStr = myformat.format(orderMoney);
            Map<String, String> requestMap = null;
            PayCommonObject order = new PayCommonObject();
            order.setOrderCode(orderCode);
            order.setMoneyStr(moneyStr);

            String sign = null;
            String timestamp = String.valueOf(new Date().getTime());// 时间戳
            if (ConfigurationUtil.getString("ali_pay_code").equals(payMothed.trim())) {// 支付宝支付
                AliAppPayPlugIn aliPay = new AliAppPayPlugIn();
                // requestMap = aliPay.genAliAppPayMap(order);
                // Map<String, String> sPara = AlipayCore.paraFilter(requestMap);
                // String linkStr = AlipayCore.createLinkString(sPara);
                String linkStr = aliPay.genAliAppPayString(order);
                sign = RSA.sign(linkStr, ConfigurationUtil.getString("ali_private_key"), "utf-8");
                System.out.println("ali" + linkStr);
            } else if (ConfigurationUtil.getString("kmt_pay_code").equals(payMothed.trim())) {// 康美通支付
                KMTAppPayPlugIn kmtPay = new KMTAppPayPlugIn();
                User user = null;
                try {
                    user = loginService.queryUserByLoginId(uid);
                } catch (Exception e) {
                    logger.error("getAppPaySign 查询用户信息异常：", e);
                }
                kmtPay.setUser(user);

                requestMap = kmtPay.genKMTAppPayMap(order, timestamp, null, unionLogin);
                Map<String, String> sPara = AlipayCore.paraFilter(requestMap);
                String linkStr = PaymentUtil.simpleCreateLinkString(sPara);
                sign = RSA.sign(linkStr, ConfigurationUtil.getString("kmt_private_key"),
                        sPara.get("inputCharset"));
                System.out.println("KMT" + linkStr);
            }
            Map<String, String> resMap = new HashMap<String, String>();
            resMap.put("sign", sign);
            resMap.put("signType", "RSA");
            resMap.put("subject", "康美中药城商城");
            resMap.put("body", "康美中药城商城");
            resMap.put("timestamp", timestamp);


            returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "签名返回值", resMap);
            printJsonString(returnResult);
        } catch (Exception e) {
            logger.error("getAppPaySign：", e);
        }
    }
}
