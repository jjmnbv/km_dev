package com.kmzyc.b2b.action.orderPay;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.common.util.MD5;
import com.km.framework.mq.bean.KmMsg;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.BnesAcctTransaction;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.PayCommonObject;
import com.kmzyc.b2b.model.RefundParamInfo;
import com.kmzyc.b2b.model.RefundResult;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.AccountInfoService;
import com.kmzyc.b2b.service.BnesAcctTransactionService;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.OrderItemService;
import com.kmzyc.b2b.service.OrderMainService;
import com.kmzyc.b2b.service.OrderPayService;
import com.kmzyc.b2b.service.OrderPayStatementService;
import com.kmzyc.b2b.service.ProductStockService;
import com.kmzyc.b2b.service.PromotionStatisticsService;
import com.kmzyc.b2b.service.SendKmMsgService;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.b2b.util.CPSUtils;
import com.kmzyc.b2b.util.pay.BasePayPlugIn;
import com.kmzyc.b2b.util.pay.BasePayPlugInFactory;
import com.kmzyc.b2b.util.pay.BuildParam;
import com.kmzyc.b2b.util.pay.PayPlugInManger;
import com.kmzyc.b2b.util.pay.util.PaymentUtil;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.EmailSendType;
import com.kmzyc.framework.constants.MessageConstants;
import com.kmzyc.framework.constants.MessageTypeEnum;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.order.remote.OrderCallBackRemoteService;
import com.kmzyc.order.remote.RefundMentRemoteService;
import com.kmzyc.user.remote.service.TrationListRemoteService;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.entities.OrderOutSideExtInfo;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;

// import com.km.framework.common.util.RemoteTool;

@Controller
@Scope("prototype")
@SuppressWarnings("unchecked")
public class PayResultAction extends SettleMentBaseAction {
    private static final long serialVersionUID = -9187722614322065174L;
    private static Logger log = LoggerFactory.getLogger(PayResultAction.class);
    @Resource(name = "orderMainServiceImpl")
    private OrderMainService orderMainService;

    @Resource(name = "accountInfoServiceImp")
    private AccountInfoService accountinfoService;

    @Resource(name = "bnesAcctTransactionServiceImpl")
    private BnesAcctTransactionService bnesAcctTransactionService;

    @Resource(name = "loginServiceImp")
    private LoginService loginService;

    @Resource
    private OrderItemService orderItemService;

    @Resource(name = "sendKmMsgService")
    private SendKmMsgService sendKmMsgService;

    @Resource(name = "promotionStatisticsService")
    private PromotionStatisticsService promotionStatisticsService;

    @Resource(name = "productStockServiceImpl")
    private ProductStockService productStockService;

    @Resource
    private OrderPayService orderPayService;
    @Resource
    private MyOrderService myOrderService;
    @Resource(name = "orderPayStatementServiceImpl")
    private OrderPayStatementService orderPayStatementService;

    @Resource
    private OrderCallBackRemoteService orderCallBackRemoteService;

    @Resource
    private TrationListRemoteService trationListRemoteService;

    @Resource
    private RefundMentRemoteService refundMentRemoteService;

    private String mobile;
    private String orderCode;
    private Long orderMainId;
    private Double payMoney;
    private Integer payMethod = null;
    private String pageMsg = null;
    // 1:充值业务 2：订单业务 3:预备金还款
    private Integer rechargeOrOrderFlag;
    private boolean payFlag = false;
    private boolean orderTimeOut = true;
    // 是否是微信支付 0是
    private String isWxPay;
    // 微信的openId
    private String openid;
    // 订单积分
    private Long credits;
    // 优惠券金额
    private String couponMoney;
    private PayCommonObject payCommon;
    // 订单或充值成功失败标志
    private Integer orderPayFlag;
    private User user = new User();

    // 银行列表标签
    private String bankList;

    // 时代银行列表标签
    private String sdBankList;

    // 支付信息,,即支付页面点选的radio的value属性
    private String payMessage;
    // 支付类型 目前包括 saveCard：储蓄卡 pay:支付平台 accessCard：信用卡
    private String payType;
    // 单个银行的中文名
    private String bankChineseName;

    private String errorMessage;
    private boolean errorMessageFlag = false;

    private static final Integer B2BCHANNEL = 1;
    // 订单类型
    private Integer orderType;
    // 预售订单尾款支付标识
    private String payWkFlage;

    private Object checkLoginAndOrderCode(String orderCode, Integer rechargeOrOrderFlag) {
        Long loginId = getUserloginId();
        if (rechargeOrOrderFlag == 1) {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("loginId", "" + loginId);
            paramMap.put("accountTransactionId", orderCode);
            try {
                return this.bnesAcctTransactionService.queryTransationExist(paramMap);
            } catch (ServiceException e) {
                log.error(e.getMessage());
                return null;
            }
        }
        return null;
    }

    /**
     * 支付入口
     */
    public String payOrderInit() {
        try {
            if (null != orderCode && orderCode.length() > 0) {
                HttpServletRequest request = getRequest();
                String wp = request.getParameter("wapPay");
                boolean wapPay = "1".equals(wp);
                this.payFlag = false;
                isWxPay = wapPay && request.getHeader("user-agent").indexOf("MicroMessenger") > 0
                        ? "0" : "1";// 当从微信过来时的wap版页面为:0
                orderCode = null == request.getAttribute("orderCode")
                        ? request.getParameter("orderCode")
                        : request.getAttribute("orderCode").toString();
                rechargeOrOrderFlag = Integer.parseInt(request.getParameter("rechargeOrOrderFlag"));
                Long loginId = this.getUserloginId();

                // 判断是否是当前用户订单 orderMainService.checkOrderUser(loginId, orderCode,
                // rechargeOrOrderFlag);
                execCps(getRequest(), orderCode, request.getHeader("host")); // cps
                // 订单支付
                if (rechargeOrOrderFlag == 2) {
                    Map<String, String> paramMap = new HashMap<String, String>();
                    paramMap.put("orderCode", orderCode);
                    paramMap.put("orderStatus", Constants.ORDER_STATUS_NOT_PAY.toString());
                    OrderMain orderMain = null;
                    List<OrderMain> orderList = null;
                    BigDecimal needToPayMoney = BigDecimal.ONE.negate();
                    try {
                        orderList = orderMainService.findByOrderCode(paramMap);
                        payCommon = new PayCommonObject();
                        payCommon.setOrderCode(orderCode);
                        needToPayMoney = this.orderMainService.findNeedToPayMoney(orderCode);
                        payMoney = needToPayMoney.doubleValue();
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        return ERROR;
                    }
                    payCommon.setMoneyStr(needToPayMoney.toString());
                    // 减少活动库存
                    if (null != orderList && orderList.size() > 0) {
                        orderMain = orderList.get(0);
                        this.orderType = orderMain.getOrderType();
                        this.mobile = orderMain.getOrderPurchaserMobile();
                        this.credits = orderMain.getTotalCredit();
                        if (needToPayMoney.compareTo(BigDecimal.ZERO) == 0 && loginId != null
                                && loginId != 0) {
                            try {
                                this.payFlag =
                                        orderPayService.orderRemotePay(orderCode, loginId, null);
                                if (payFlag) {
                                    // myOrderService.pushOrderUserSource(orderCode);
                                    try {
                                        if (!productStockService.updateProductOrderQuantityCache(
                                                loginId, true, orderCode)) {
                                            log.error("订单" + orderCode + "减少活动库存失败");
                                        }
                                    } catch (Exception e) {
                                        log.error("", e);
                                    }
                                }
                            } catch (Exception e) {
                                log.error("", e);
                            }
                        }
                    } else if (BigDecimal.ZERO.compareTo(needToPayMoney) == 0) {
                        this.payFlag = true;
                    }
                    if (payFlag && !wapPay) {
                        return "paySuccess";
                    } else if (payFlag) {
                        return "wapPaySuccess";
                    }
                    // 未支付完成
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(orderMain.getCreateDate());
                    calendar.add(Calendar.HOUR, Constants.ORDER_DISABLED_TIME);
                    // 订单时间
                    Calendar orderTime = Calendar.getInstance();
                    orderTime.setTime(orderMain.getCreateDate());
                    // 支付时间
                    Calendar payTime = Calendar.getInstance();
                    payTime.setTime(new Date());
                    // 订单过期或时代订单生成时间到当前时间是否跨越周二
                    this.orderTimeOut = calendar.getTime().getTime() <= new Date().getTime()
                            || (1 == (orderTime.get(Calendar.DAY_OF_WEEK) - 1)
                                    && 2 == (payTime.get(Calendar.DAY_OF_WEEK) - 1)
                                    && (orderMain.getOrderType() == 3
                                            || orderMain.getOrderType() == 4
                                            || orderMain.getOrderType() == 5));
                    if (!wapPay) {
                        return SUCCESS;
                    } else {
                        if ("0".equals(isWxPay)) {
                            try {
                                getResponse().sendRedirect(
                                        PaymentUtil.getOauth2ByRedirectUri(URLEncoder.encode(
                                                ConfigurationUtil.getString("wx_redirect_uri")
                                                        + "?orderTimeOut=" + orderTimeOut
                                                        + "&rechargeOrOrderFlag="
                                                        + rechargeOrOrderFlag + "&orderCode="
                                                        + orderCode + "&payMoney=" + payMoney
                                                        + "&isWxPay=" + isWxPay + "&errorMessage="
                                                        + errorMessage,
                                                "utf-8")));
                            } catch (Exception e) {
                                log.error("微信支付异常", e);
                            }
                        }
                        return "wapPay";
                    }
                } else {
                    // 充值支付
                    try {
                        payMoney = this.orderItemService.getTotalCredits(orderCode);
                    } catch (ServiceException e) {
                        log.error(e.getMessage(), e);
                        return ERROR;
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 生成cps信息并设置到request中
     */
    private void execCps(HttpServletRequest request, String orderCode, String host) {
        boolean isCpsWeb = false;
        String iscraete = request.getParameter("isCreate");
        if (!"1".equals(iscraete) || null == host) {
            // 订单列表的付款
            return;
        }
        Map<String, String> cookieMap = genCPSInfoFromCookie(getRequest());
        String str = "订单号：" + orderCode + ",所获cookieMap:"
                + (cookieMap == null ? "" : cookieMap.toString());
        log.info(str);
        String type = null;// 推广类型
        String[] cpsInfo = null;
        try {
            String[] keys = null;
            if (null != cookieMap && cookieMap.keySet().size() >= 4) {
                isCpsWeb = true;
                keys = CPSUtils.B2B_CPS_YQF_COOKIE_KEYS;
                type = cookieMap.get(keys[5]);
                if (type == null) {
                    type = "0";
                }
                cpsInfo = CPSUtils.B2B_CPS_INFO
                        .get(CPSUtils.B2B_PROMOTION_TYPE.get(Integer.parseInt(type)));
                OrderOutSideExtInfo orderInfo = new OrderOutSideExtInfo();
                orderInfo.setSource(cookieMap.get(keys[0]));
                orderInfo.setChannel(cookieMap.get(keys[1]));
                orderInfo.setCampaignid(cookieMap.get(keys[2]));
                orderInfo.setFeedback(cookieMap.get(keys[3]));
                orderInfo.setOrderCode(orderCode);
                orderInfo.setPromotionType(Integer.parseInt(type));
                if (cpsInfo != null)
                    orderInfo.setCommissionType(cpsInfo[3]);
                promotionStatisticsService.insertOrderFlagInfo(orderInfo);
            }
        } catch (Exception e) {
            log.error("新增订单标识数据异常", e);
        }
        try {
            if (isCpsWeb && null != cookieMap
                    && !cookieMap.get("cps_yqf_source").startsWith("LTINFO")) {
                List<Map> list =
                        promotionStatisticsService.queryOrderDetailInfoByOrderCode(orderCode);
                if (null != list && !list.isEmpty()) {
                    Map orderData = list.get(0);
                    String[] keys = CPSUtils.B2B_CPS_YQF_COOKIE_KEYS;
                    if ("chengguowang".equals(cookieMap.get(keys[0]))) {// 成果网
                        StringBuilder sbCgw = new StringBuilder();
                        // t值为成果网固定
                        String sign = "t=8815&id=" + cookieMap.get(keys[2]) + "&i=" + orderCode
                                + "&key=DIiXCIMjZAboMMbM";
                        // 加密后的字符串
                        String newsign = MD5.getMD5Str(sign);
                        sbCgw.append("?t=8815").append("&id=").append(cookieMap.get(keys[2]))
                                .append("&i=").append(orderCode).append("&sign=").append(newsign)
                                .append("&o=");
                        for (int i = 0; i < list.size(); i++) {
                            orderData = list.get(i);
                            if (i == 0) {
                                sbCgw.append("kangmei").append("/").append(orderData.get("price"))
                                        .append("/").append(orderData.get("amount")).append("/")
                                        .append(orderData.get("name"));
                            } else {
                                sbCgw.append(":").append("kangmei").append("/")
                                        .append(orderData.get("price")).append("/")
                                        .append(orderData.get("amount")).append("/")
                                        .append(orderData.get("name"));
                            }
                        }
                        sbCgw.append("&pm=2&st=0&ps=0&ot=").append(orderData.get("orderTime"));
                        request.setAttribute("cgw_url", sbCgw.toString());
                        request.setAttribute("source", "chengguowang");
                        request.setAttribute("type", type);
                    } else if ("duomai".equals(cookieMap.get(keys[0]))) {// 多麦网
                        StringBuilder sbDM = new StringBuilder();
                        sbDM.append("?hash=a4d91417600b7a0079baa83612c3ad43").append("&euid=")
                                .append(cookieMap.get(keys[3])).append("&order_sn=")
                                .append(orderCode).append("&order_time=")
                                .append(orderData.get("orderTime")).append("&click_time=")
                                .append(orderData.get("clickTime")).append("&discount_amount=0")
                                .append("&is_new_custom=0").append("&order_status=")
                                .append(orderData.get("orderStatus")).append("&referer=")
                                .append("&goods_id=");
                        StringBuilder productNo = new StringBuilder();// 商品编号
                        StringBuilder price = new StringBuilder();// 商品单价
                        StringBuilder productAccount = new StringBuilder();// 数量
                        StringBuilder productName = new StringBuilder();// 商品名称
                        StringBuilder productTa = new StringBuilder();// 商品分类编号
                        StringBuilder sumPrice = new StringBuilder();// 商品总金额
                        StringBuilder commission = new StringBuilder();// 商品佣金金额
                        List<OrderItem> itemList = orderItemService.findByOrderCode(orderCode);
                        Double orderSum = 0.00;
                        for (int i = 0; i < itemList.size(); i++) {
                            OrderItem oi = itemList.get(i);
                            if (i == 0) {
                                productNo.append(oi.getCommoditySku() + "_" + oi.getOrderItemId());
                                price.append(oi.getCommodityUnitIncoming());
                                productAccount.append(oi.getCommodityNumber());
                                productName.append(oi.getCommodityName());
                                productTa.append("1");// 没有此参数，给默认值
                                sumPrice.append(
                                        Double.valueOf(oi.getCommodityUnitIncoming().toString())
                                                * oi.getCommodityNumber());
                                orderSum = orderSum
                                        + Double.valueOf(oi.getCommodityUnitIncoming().toString())
                                                * oi.getCommodityNumber();
                                commission.append("0");
                            } else {
                                productNo.append("|")
                                        .append(oi.getCommoditySku() + "_" + oi.getOrderItemId());
                                price.append("|").append(oi.getCommodityUnitIncoming());
                                productAccount.append("|").append(oi.getCommodityNumber());
                                productName.append("|").append(oi.getCommodityName());
                                productTa.append("|").append("1");// 没有此参数，给默认值
                                sumPrice.append("|")
                                        .append(Double
                                                .valueOf(oi.getCommodityUnitIncoming().toString())
                                                * oi.getCommodityNumber());
                                commission.append("|").append("0");
                                orderSum = orderSum
                                        + Double.valueOf(oi.getCommodityUnitIncoming().toString())
                                                * oi.getCommodityNumber();
                            }
                        }
                        sbDM.append(productNo.toString()).append("&goods_name=")
                                .append(productName.toString()).append("&goods_price=")
                                .append(price.toString()).append("&goods_ta=")
                                .append(productAccount.toString()).append("&goods_cate=")
                                .append(productTa.toString()).append("&totalPrice=")
                                .append(sumPrice.toString()).append("&commission=")
                                .append(commission.toString()).append("&orders_price=")
                                .append(orderSum);
                        request.setAttribute("duomai_url", sbDM.toString());
                        request.setAttribute("source", "duomai");
                        log.info("推送多麦:" + sbDM.toString());
                    } else if ("emar".equals(cookieMap.get(keys[0]))) {
                        StringBuilder sb = new StringBuilder();
                        String ecoding = null == cpsInfo[2] ? "UTF-8" : cpsInfo[2];
                        String UAccount = (String) request.getSession()
                                .getAttribute(Constants.SESSION_USER_NAME);
                        sb.append("{\"orders\":[{\"encoding\":\"").append(ecoding)
                                .append("\",\"orderNo\":\"").append(orderCode)
                                .append("\",\"UAccount\":\"").append(UAccount)
                                .append("\",\"orderTime\":\"").append(orderData.get("orderTime"))
                                .append("\",\"updateTime\":\"\",\"campaignId\":\"")
                                .append(cookieMap.get(keys[2])).append("\",\"feedback\":\"")
                                .append(cookieMap.get(keys[3])).append("\",\"fare\":\"")
                                .append(orderData.get("fare")).append("\",\"favorable\":\"")
                                .append(orderData.get("favorable"))
                                .append("\",\"favorableCode\":\"")
                                .append(orderData.get("favorableCode"))
                                .append("\",\"orderStatus\":\"\",\"paymentStatus\":\"\",\"paymentType\":\"\"")
                                .append(",\"products\":[");
                        for (int i = 0, len = list.size(); i < len; i++) {
                            orderData = list.get(i);
                            sb.append("{\"productNo\":\"").append(orderData.get("productNo"))
                                    .append("\",\"name\":\"").append(orderData.get("name") + "")
                                    .append("\",\"amount\":\"").append(orderData.get("amount"))
                                    .append("\",\"price\":\"").append(orderData.get("price"))
                                    .append("\",\"category\":\"\",\"commissionType\":\"")
                                    .append(orderData.get("commissionType")).append("\"},");
                        }
                        request.setAttribute("yqf_json", URLEncoder
                                .encode(sb.substring(0, sb.length() - 1) + "]}]}", ecoding));
                        request.setAttribute("yqf_interId", cpsInfo[1]);
                        request.setAttribute("source", cookieMap.get(keys[0]));
                        request.setAttribute("type", type);
                    }
                }
            } else if (isCpsWeb && null != cookieMap
                    && cookieMap.get("cps_yqf_source").startsWith("LTINFO")) {// 领克特
                List<OrderItem> list = orderItemService.findByOrderCode(orderCode);
                OrderItem orderData = null;
                if (list != null && list.size() > 0) {
                    orderData = list.get(0);
                } else {
                    return;
                }
                StringBuilder sbf = new StringBuilder();
                sbf.append("?a_id=").append(cookieMap.get("cps_yqf_cid")).append("&m_id=")
                        .append((cookieMap.get("cps_yqf_source")).replace("LTINFO_", ""))
                        .append("&mbr_id=usr").append("&o_cd=").append(orderCode).append("&p_cd=");
                StringBuilder productNo = new StringBuilder();// 商品编号
                StringBuilder price = new StringBuilder();// 价格
                StringBuilder productAccount = new StringBuilder();// 数量
                StringBuilder productCcd = new StringBuilder();// 分类
                for (int i = 0; i < list.size(); i++) {
                    orderData = list.get(i);
                    if (i == 0) {
                        productNo.append(
                                orderData.getCommoditySku() + "_" + orderData.getOrderItemId());
                        price.append(orderData.getCommodityUnitIncoming());
                        productAccount.append(orderData.getCommodityNumber());
                        productCcd.append(orderData.getCommodityType());
                    } else {
                        productNo.append("||").append(
                                orderData.getCommoditySku() + "_" + orderData.getOrderItemId());
                        price.append("||").append(orderData.getCommodityUnitIncoming());
                        productAccount.append("||").append(orderData.getCommodityNumber());
                        productCcd.append("||").append(orderData.getCommodityType());
                    }
                }
                sbf.append(productNo.toString()).append("&price=").append(price.toString())
                        .append("&it_cnt=").append(productAccount.toString()).append("&c_cd=")
                        .append(productCcd.toString());
                request.setAttribute("lkt_url", sbf.toString());
                request.setAttribute("source", "LTINFO");
            }
            // else {
            // log.error("cps订单推送数据异常");
            // }
        } catch (Exception e) {
            log.error("查询订单标识数据异常", e);
        }
    }

    /**
     * 从cookie中获取cps信息
     */
    private Map<String, String> genCPSInfoFromCookie(HttpServletRequest request) {
        Map<String, String> map = null;
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length > 0) {
            map = new HashMap<String, String>();
            final String key = Arrays.toString(CPSUtils.B2B_CPS_YQF_COOKIE_KEYS);
            for (Cookie c : cookies) {
                if (key.indexOf(c.getName()) > 0) {
                    try {
                        map.put(c.getName(), URLDecoder.decode(c.getValue(), "UTF-8"));
                    } catch (Exception e) {
                    }
                }
            }
        }
        return map;
    }


    /**
     * 支付订单前 检查订单信息是否正确 1 ：订单不存在 2 ：订单已经过期 3：订单已经支付
     */
    public String checkOrder() throws ServiceException {
        this.pageMsg = null;
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderCode", orderCode);
        List<OrderMain> orderMainList = orderMainService.findByOrderCode(map);
        // 订单不存在
        if (null == orderMainList || orderMainList.size() == 0) {
            this.pageMsg = "1";
            return SUCCESS;
        } else {
            for (OrderMain o : orderMainList) {
                if (Objects.equals(o.getOrderStatus(), Constants.ORDER_STATUS_PAY)) {
                    this.pageMsg = "3";
                    return SUCCESS;
                }
            }
        }
        OrderMain orderMain = orderMainList.get(0);

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(orderMain.getCreateDate());
        calendar.add(Calendar.HOUR, Constants.ORDER_DISABLED_TIME);
        // 订单已经过期
        if (calendar.getTime().getTime() <= new Date().getTime()) {
            this.pageMsg = "2";
            return SUCCESS;
        }

        return SUCCESS;
    }

    /**
     * 调用第三方支付接口
     */
    public String payorder() throws Exception {
        String result = null;
        HttpServletRequest request = getRequest();
        String wp = request.getParameter("wapPay");
        int wapPay = "1".equals(wp) ? 1 : 0;
        this.orderCode = request.getParameter("orderCode");
        this.rechargeOrOrderFlag = Integer.parseInt(request.getParameter("rechargeOrOrderFlag"));
        Long loginId = super.getUserloginId();
        OrderMain orderMain = new OrderMain();
        String loginAccount = "";
        // 为订单支付
        if (rechargeOrOrderFlag == 2) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("orderCode", orderCode);
            String orderStatus = String.valueOf(Constants.ORDER_STATUS_NOT_PAY);
            map.put("orderStatus", orderStatus);
            List<OrderMain> orderMainList = orderMainService.findByOrderCode(map);
            if (null != orderMainList && orderMainList.size() > 0) {
                orderMain = orderMainList.get(0);
                loginAccount = orderMain.getCustomerAccount();
            } else {
                result = "<script type='text/javascript'>alert('订单号不存在或已支付！');window.close();</script>";
                this.showpage(result);
                return null;
            }
        }

        BnesAcctTransaction btransaction = null;
        if (rechargeOrOrderFlag == 1 || rechargeOrOrderFlag == 3) {
            if (loginId == null) {
                loginId = Long.parseLong(request.getParameter("loginId"));
            }
            if (loginId == null) {
                result = "<script type='text/javascript'>alert('请重新登录后再支付！');window.close();</script>";
                this.showpage(result);
                return null;
            }
            if (null != this.getLoginUser()) {
                loginAccount = this.getLoginUser().getLoginAccount();
            } else {
                loginAccount = request.getParameter("loginAccount");
            }
            btransaction = bnesAcctTransactionService.findByAccountNumber(this.orderCode);
            String content = "充值单";
            if (rechargeOrOrderFlag == 3) {
                content = "预备金还款单";
            }
            if (null == btransaction) {
                result = "<script type='text/javascript'>alert('" + content
                        + "号不存在!');window.close();</script>";
                this.showpage(result);
                return null;
            }
        }
        if (null == result) {
            List<Long> stocks = orderItemService.queryOrderItemStock(orderCode);
            for (Long stock : stocks) {
                if (stock < 0) {
                    result = "商品库存不足，请重新购买并支付！";
                    break;
                }
            }
        }
        // 构造订单对象，主要是为支付组件注入订单号、金额等关键信息
        PayCommonObject order = new PayCommonObject();
        String platformCode = "", platformName = "";
        // 根据约定类型创建不同的支付组件工厂类
        BasePayPlugInFactory factory = null;
        BuildParam bd = new BuildParam();
        if (payType.trim().equals("saveCard") && !StringUtil.isEmpty(payMessage)) { // 支付宝网银
            /**
             * 财付通网银 bd.setFactoryType(PayPlugInManger.PLATFORM_TEN_PAY); platformCode =
             * ConfigurationUtil.getString("ten_pay_code"); request.setAttribute("bank_type",
             * payMessage);
             */
            platformCode = ConfigurationUtil.getString("ali_pay_code");
            bd.setFactoryType(PayPlugInManger.PLATFORM_ALI_PAY);
            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WEB);
            order.setBankId(payMessage);
        } else if (payType.trim().equals("pay")) { // 通过支付平台
            if (ConfigurationUtil.getString("ali_pay_code").equals(payMessage.trim())) {// 支付宝
                bd.setFactoryType(PayPlugInManger.PLATFORM_ALI_PAY);
                if (Constants.WAP_PAY_FLAG != wapPay) {
                    bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WEB);
                } else {
                    bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WAP);
                }
            } else if (ConfigurationUtil.getString("ten_pay_code").equals(payMessage.trim())) {// 财付通支付
                bd.setFactoryType(PayPlugInManger.PLATFORM_TEN_PAY);
            } else if ("2".equals(payMessage.trim())) {// 易宝支付
                bd.setFactoryType(PayPlugInManger.PLATFORM_YEE_PAY);
            } else if ("5".equals(payMessage.trim())) {// 微信支付
                bd.setFactoryType(PayPlugInManger.PLATFORM_WX_PAY);
            } else if ("7".equals(payMessage.trim())) {// 康美通支付
                bd.setFactoryType(PayPlugInManger.PLATFORM_KMT_PAY);// 康美通
                if (Constants.WAP_PAY_FLAG != wapPay) {
                    bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WEB);
                } else {
                    bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WAP);
                }
            } else {
                result = "<script type='text/javascript'>alert('支付方式不存在！');window.close();</script>";
                this.showpage(result);
                return null;
            }
            platformCode = payMessage;
        } else if ("sdPay".equals(payType.trim())) {// 时代支付
            bd.setFactoryType(PayPlugInManger.PLATFORM_SD_PAY);
            platformCode = ConfigurationUtil.getString("sd_pay_code");
            order.setPayDateStr(StringUtil.getSimpleJoin(orderMain.getCreateDate()));// 时代支付设置下单时间
        } else if (payType.trim().equals("accessCard")) {// 通过信用卡
            // 待扩展
        } else {
            result = "<script type='text/javascript'>alert('支付方式不存在！');window.close();</script>";
            this.showpage(result);
            return null;
        }
        // 由支付组件工厂类创建支付组件
        BasePayPlugIn plugIn = null;
        try {
            platformName = Constants.getPlatformName(platformCode);
            factory = PayPlugInManger.createFactory(bd);
            plugIn = factory.createPayPlugIn();
            plugIn.setCallBackRequest(request);
            plugIn.setCallBackResponse(this.getResponse());
        } catch (NullPointerException e) {
            log.error("", e);
            result = "<script type='text/javascript'>alert('支付失败！');window.close();</script>";
            this.showpage(result);
            return null;
        }
        BigDecimal orderMoney = orderMainService.queryPayMoney(orderCode);
        order.setCurrencyCode("RMB");
        if (rechargeOrOrderFlag == 2) {
            // 0.01
            order.setMoneyStr(orderMoney.toString());
        } else {
            order.setMoneyStr(btransaction.getAmount().toString());
        }
        User u = new User();
        u.setLoginAccount(loginAccount);
        if (orderMain != null && orderMain.getCustomerId() != null) {
            user = loginService.queryUserByLoginId(orderMain.getCustomerId().toString());
        } else {
            user = this.getLoginUser();
        }
        if (null != user) {
            order.setExtInfo(
                    rechargeOrOrderFlag.toString() + "|" + user.getLoginId() + "|" + platformCode);
        } else {
            order.setExtInfo(rechargeOrOrderFlag.toString() + "|" + loginId + "|" + platformCode);
        }
        order.setOrderCode(orderCode);
        plugIn.setOrder(order);
        plugIn.setUser(user);
        result = plugIn.onPay();
        // 写入支付流水，为防止调单后边处理
        if (rechargeOrOrderFlag == 2) {
            Map<String, String> paramMap = new HashMap<String, String>();
            String orderStatus = String.valueOf(OrderDictionaryEnum.OrderPayState.Ready.getKey());
            paramMap.put("orderCode", orderCode);
            paramMap.put("state", orderStatus);
            paramMap.put("paymentWay", Constants.PAY_METHOD_ONLINE_PLATFORM.toString());
            // 流水是否已经写入
            // OrderCallBackRemoteService orderCallBackRemoteService =
            // (OrderCallBackRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_ORDER,
            // "callBackService");
            List<com.pltfm.app.entities.OrderPayStatement> paystatementList =
                    new ArrayList<com.pltfm.app.entities.OrderPayStatement>();
            com.pltfm.app.entities.OrderPayStatement paystatement =
                    new com.pltfm.app.entities.OrderPayStatement();
            paystatement.setPaymentWay(Constants.PAY_METHOD_ONLINE_PLATFORM);
            paystatement.setAccount(orderMain.getCustomerAccount());
            paystatement.setOrderMoney(orderMoney);
            paystatement.setCreateDate(new Date());
            paystatement.setEndDate(new Date());
            paystatement.setFlag(Constants.ORDER_PAY_FLAG_PAYMENT);
            paystatement.setOrderCode(orderCode);
            paystatement.setPlatFormCode(platformCode);
            paystatement.setPlatFormName(platformName);
            paystatement.setBankCode(order.getBankId());
            // 支付状态：准备
            paystatement.setState(Long.valueOf(OrderDictionaryEnum.OrderPayState.Ready.getKey()));
            paystatementList.add(paystatement);
            orderCallBackRemoteService.insertPayStatement(paystatementList);
        }
        if (rechargeOrOrderFlag == 3) {
            try {
                // TrationListRemoteService trationListRemoteService =
                // (TrationListRemoteService)
                // RemoteTool.getRemote(Constants.REMOTE_SERVICE_CUSTOMER,
                // "trationListRemoteService");
                trationListRemoteService.updateTransaction(orderCode, 3);
            } catch (Exception e) {
                log.error("", e);
                log.error("写入预备金准备中支付流水失败", e);
            }
        }
        this.showpage(result);

        return null;
    }

    /**
     * 回调方法
     *
     * @return
     */
    /**
     * @return
     */
    public String payCallBack() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            payCommon = null;
            HttpServletRequest request = getRequest();
            HttpServletResponse response = getResponse();
            boolean isWap = false, isPaded = false;
            orderCode = request.getParameter("out_trade_no");

            String orderCodeForLog =
                    null != orderCode ? orderCode : request.getParameter("outTradeNo");
            log.info("==订单" + orderCodeForLog + "支付回调开始==" + df.format(new Date()));


            BuildParam bd = new BuildParam();
            Long platformCode = null;
            BasePayPlugIn plugIn = null;
            BasePayPlugInFactory factory = null;
            String returnURL = request.getRequestURL().toString();// 请求地址
            BnesAcctTransaction bat = null;// 充值
            String wap = request.getParameter("wap");
            String app = request.getParameter("app");

            // 订单支付流水与实际支付方式不一样时，调用
            String tradeNot = request.getParameter("trade_no");// 支付宝专用参数
            String tradeModet = request.getParameter("trade_mode");// 财付通专业参数
            String secIdt = request.getParameter("sec_id");// 支付宝专用参数
            if (!StringUtil.isEmpty(tradeModet)) {
                platformCode = (long) PayPlugInManger.PLATFORM_TEN_PAY;
            } else if (!StringUtil.isEmpty(tradeNot) || !StringUtil.isEmpty(secIdt)) {
                platformCode = (long) PayPlugInManger.PLATFORM_ALI_PAY;

            } else if (!StringUtil.isEmpty(returnURL) && returnURL.indexOf("wxpay") != -1) {
                platformCode = (long) PayPlugInManger.PLATFORM_WX_PAY;
            } else if (null != request.getParameter("tradeNo")) {
                // 康美通专用参数
                platformCode = (long) PayPlugInManger.PLATFORM_KMT_PAY;
            }

            if (StringUtil.isEmpty(orderCode)
                    && StringUtil.isEmpty(orderCode = request.getParameter("outTradeNo"))
                    && StringUtil.isEmpty(orderCode = request.getParameter("r6_Order"))) {
                // 流获取订单号

                if (!StringUtil.isEmpty(request.getParameter("sec_id"))
                        || !StringUtil.isEmpty(request.getParameter("request_token"))) {
                    bd.setFactoryType(PayPlugInManger.PLATFORM_ALI_PAY);
                    bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WAP); // 支付宝WAP回调
                    isWap = true;
                } else if (!StringUtil.isEmpty(returnURL) && returnURL.indexOf("wxpay") != -1) {
                    bd.setFactoryType(PayPlugInManger.PLATFORM_WX_PAY); // 微信回调
                    isWap = true;
                }
                factory = PayPlugInManger.createFactory(bd);
                platformCode = Long.valueOf(bd.getFactoryType());
                try {
                    plugIn = factory.createPayPlugIn();
                    plugIn.setCallBackRequest(request);
                    plugIn.setCallBackResponse(response);
                    payCommon = (PayCommonObject) plugIn.onReturn();
                    orderCode = null != payCommon ? payCommon.getOrderCode() : null;

                    log.info("订单:" + orderCode + "支付回调时间：" + df.format(new Date()));

                    if (null != payCommon && null != payCommon.getPayStateCode()) {
                        orderPayFlag = Integer.parseInt(payCommon.getPayStateCode());
                    }
                } catch (Exception e) {
                    log.error("", e);
                }
            }
            if (!StringUtil.isEmpty(orderCode)) {
                String requestToken = request.getParameter("request_token");
                String secId = request.getParameter("sec_id");// 支付宝专用参数
                // 流获取订单号
                if (null != orderCode && !"".equals(orderCode.trim()) && null != platformCode) {
                    log.info("==订单支付回调:修改为实际的交易平台开始==" + df.format(new Date()));
                    System.out.println("==订单支付回调:修改为实际的交易平台开始==" + df.format(new Date()));
                    // 修改为实际的交易平台
                    orderPayStatementService.insertOrUpdateOrderPayStatement(orderCode,
                            platformCode.toString());

                    log.info("==订单支付回调:修改为实际的交易平台结束==" + df.format(new Date()));
                    System.out.println("==订单支付回调:修改为实际的交易平台结束==" + df.format(new Date()));
                }

                Map<String, Object> orderInfo =
                        orderMainService.queryOrderMainForPayCall(orderCode);// 查询状态为【准备】的支付流水
                if (null != orderInfo && !orderInfo.isEmpty()) {
                    // platformCode = Long.parseLong(orderInfo.get("PLATFORMCODE").toString());
                    if (platformCode == PayPlugInManger.PLATFORM_ALI_PAY) {
                        if (!StringUtil.isEmpty(secId) || !StringUtil.isEmpty(requestToken)) {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WAP);
                        } else {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WEB);
                        }
                    }

                    if (platformCode == PayPlugInManger.PLATFORM_KMT_PAY) { // 康美通
                        if (!StringUtil.isEmpty(wap)) {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WAP);
                        } else if (!StringUtil.isEmpty(app)) {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_APP);
                        } else {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WEB);
                        }
                    }
                } else {
                    String tradeNo = request.getParameter("trade_no");// 支付宝专用参数
                    String tradeMode = request.getParameter("trade_mode");// 财付通专业参数
                    if (!StringUtil.isEmpty(tradeMode)) {
                        platformCode = (long) PayPlugInManger.PLATFORM_TEN_PAY;
                    } else if (!StringUtil.isEmpty(tradeNo) || !StringUtil.isEmpty(secId)) {
                        platformCode = (long) PayPlugInManger.PLATFORM_ALI_PAY;
                        if (!StringUtil.isEmpty(secId) || !StringUtil.isEmpty(requestToken)) {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WAP);
                        } else {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WEB);
                        }
                    } else if (!StringUtil.isEmpty(returnURL) && returnURL.indexOf("wxpay") != -1) {
                        platformCode = (long) PayPlugInManger.PLATFORM_WX_PAY;
                    } else if (null != request.getParameter("tradeNo")) {
                        // 康美通专用参数
                        platformCode = (long) PayPlugInManger.PLATFORM_KMT_PAY;
                        if (!StringUtil.isEmpty(wap)) {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WAP);
                        } else if (!StringUtil.isEmpty(app)) {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_APP);
                        } else {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WEB);
                        }

                    } else if (null != request.getParameter("isSdPay")) {
                        // 时代支付专用参数
                        platformCode = (long) PayPlugInManger.PLATFORM_SD_PAY;
                    } else {
                        platformCode = (long) PayPlugInManger.PLATFORM_YEE_PAY;
                    }
                }
                if (null != orderCode && !"".equals(orderCode.trim()) && null != platformCode) {
                    log.info("==订单支付回调:修改为实际的交易平台开始==" + df.format(new Date()));
                    System.out.println("==订单支付回调:修改为实际的交易平台开始==" + df.format(new Date()));
                    // 修改为实际的交易平台
                    orderPayStatementService.insertOrUpdateOrderPayStatement(orderCode,
                            platformCode.toString());

                    log.info("==订单支付回调:修改为实际的交易平台结束==" + df.format(new Date()));
                    System.out.println("==订单支付回调:修改为实际的交易平台结束==" + df.format(new Date()));
                }
                if (null == payCommon) {
                    bd.setFactoryType(platformCode.intValue());
                    factory = PayPlugInManger.createFactory(bd);
                    plugIn = factory.createPayPlugIn();
                    plugIn.setCallBackRequest(request);
                    plugIn.setCallBackResponse(response);
                    payCommon = (PayCommonObject) plugIn.onReturn();
                    if (null != payCommon && null != payCommon.getPayStateCode()) {
                        orderPayFlag = Integer.parseInt(payCommon.getPayStateCode());
                    }
                    if (!isWap && 5 == platformCode) {
                        isWap = true;
                    }
                }
                if (null != payCommon && null != orderInfo && !orderInfo.isEmpty()) {
                    Long orderStatus = null, loginId = null;
                    loginId = Long.parseLong(orderInfo.get("CUSTOMERID").toString());
                    BigDecimal needpay = null;// 需支付
                    if ((null != (orderStatus =
                            Long.parseLong(orderInfo.get("ORDERSTATUS").toString()))
                            && orderStatus >= OrderDictionaryEnum.Order_Status.Pay_Done.getKey())
                            || (needpay = (BigDecimal) orderInfo.get("NEEDPAY"))
                                    .compareTo(BigDecimal.ZERO) == 0
                            || (null != orderInfo.get("OPSNO")
                                    && orderInfo.get("OPSNO").toString()
                                            .indexOf(payCommon.getTransitionNO()) >= 0
                                    && needpay.compareTo(BigDecimal.ZERO) == 0)) {
                        payFlag = true;
                        isPaded = true;
                    } else if (null != orderStatus && orderStatus
                            .intValue() == OrderDictionaryEnum.Order_Status.Not_Pay.getKey()) {

                        // log.info("==订单支付回调:修改为实际的交易平台开始==" + df.format(new Date()));
                        // System.out.println("==订单支付回调:修改为实际的交易平台开始==" + df.format(new Date()));
                        // // 修改为实际的交易平台
                        // orderPayStatementService.insertOrUpdateOrderPayStatement(orderCode,
                        // platformCode.toString());
                        //
                        // log.info("==订单支付回调:修改为实际的交易平台结束==" + df.format(new Date()));
                        // System.out.println("==订单支付回调:修改为实际的交易平台结束==" + df.format(new Date()));

                        rechargeOrOrderFlag = 2;
                        payCommon.setExtInfo("2|" + loginId + '|' + platformCode);
                        // String loginAccount = (String) orderInfo.get("CUSTOMERACCOUNT");
                        if (orderPayService.orderRemotePay(orderCode, loginId, payCommon)
                                && null != payCommon.getMoneyStr() && needpay
                                        .compareTo(new BigDecimal(payCommon.getMoneyStr())) <= 0) {
                            // myOrderService.pushOrderUserSource(orderCode);
                            payFlag = true;
                            try {
                                log.info("==订单支付回调:更新活动库存开始==" + df.format(new Date()));
                                System.out.println("==订单支付回调:更新活动库存开始==" + df.format(new Date()));
                                if (!productStockService.updateProductOrderQuantityCache(loginId,
                                        true, orderCode)) {
                                    log.error("订单" + orderCode + "减少活动库存失败");
                                }
                                log.info("==订单支付回调:更新活动库存结束==" + df.format(new Date()));
                                System.out.println("==订单支付回调:更新活动库存结束==" + df.format(new Date()));
                                // 发送支付成功短信
                                log.info("==订单支付回调:发送支付成功短信,邮件开始==" + df.format(new Date()));
                                System.out.println(
                                        "==订单支付回调:发送支付成功短信，邮件开始==" + df.format(new Date()));
                                List<Long> uidList = new ArrayList<Long>();
                                uidList.add(loginId);
                                List<String> phones = new ArrayList<String>();
                                phones.add(mobile);
                                KmMsg kmMsg = new KmMsg("6000", false);
                                kmMsg.getMsgData().put("kmMsgType", MessageConstants.KMMSG_MOBIL);
                                kmMsg.getMsgData().put("smsmailType",
                                        EmailSendType.MSGPAYSUCCESS.getStatus());
                                kmMsg.getMsgData().put("systemType", 1);
                                kmMsg.getMsgData().put("mobilePhones", phones);
                                kmMsg.getMsgData().put("msgSendType",
                                        MessageConstants.EM_SEND_TYPE_IMM);
                                kmMsg.getMsgData().put("uidList", uidList);
                                kmMsg.getMsgData().put("orderCode", orderCode);
                                kmMsg.getMsgData().put("modelType",
                                        MessageTypeEnum.ORDERPAYSUCCESS.getStatus());
                                sendKmMsgService.sendKmMsg(kmMsg);
                            } catch (Exception e) {
                                log.error("", e);
                            }
                        }
                    }
                } else if (null != payCommon && null != (bat =
                        bnesAcctTransactionService.findByAccountNumber(orderCode))) {

                    String[] extInfo = null == payCommon.getExtInfo() ? null
                            : payCommon.getExtInfo().split("\\|");
                    if (null != extInfo && extInfo.length >= 3) {
                        rechargeOrOrderFlag = Integer.parseInt(extInfo[0]);
                    } else {
                        rechargeOrOrderFlag = 1;// 个别无扩展参数的支付方默认为充值
                    }

                    if (bat.getStatus().intValue() == Constants.RECHARGE_OK) {
                        payFlag = true;
                        isPaded = true;
                    } else {
                        // String[] extInfo =
                        // null == payCommon.getExtInfo() ? null :
                        // payCommon.getExtInfo().split("\\|");
                        // if (null != extInfo && extInfo.length >= 3) {
                        // rechargeOrOrderFlag = Integer.parseInt(extInfo[0]);
                        // } else {
                        // rechargeOrOrderFlag = 1;// 个别无扩展参数的支付方默认为充值
                        // }
                        BnesAcctTransactionQuery nbat = new BnesAcctTransactionQuery();
                        nbat.setAccountTransactionId(bat.getAccountTransactionId().intValue());
                        nbat.setAccountId(bat.getAccountId().intValue());
                        nbat.setType(1);
                        nbat.setAmount(new BigDecimal(payCommon.getMoneyStr()));
                        if ("1".equals(payCommon.getPayStateCode())) {
                            payFlag = true;
                            nbat.setStatus(Constants.RECHARGE_OK);
                        } else {
                            nbat.setStatus(Constants.RECHARGE_FALSE);
                        }
                        nbat.setContent(rechargeOrOrderFlag == 3 ? "预备金还款" : "用户充值");
                        nbat.setOtherOrder(payCommon.getTransitionNO());
                        nbat.setTrasObject(platformCode.intValue());
                        // TrationListRemoteService trationListRemoteService =
                        // (TrationListRemoteService)
                        // RemoteTool.getRemote(Constants.REMOTE_SERVICE_CUSTOMER,
                        // "trationListRemoteService");
                        if (this.rechargeOrOrderFlag == 1) {
                            trationListRemoteService.updatePaymentAccountList(nbat);
                        } else if (this.rechargeOrOrderFlag == 3) {
                            trationListRemoteService.paySubmitRemitted(nbat, 1);
                        }
                        /*
                         * try { if (rechargeOrOrderFlag == 1 &&
                         * bnesAcctTransactionService.isFirstRecharge(bat
                         * .getAccountId().intValue())) { userGrowingService.updateUserScoreInfo(
                         * Constants.FIRST_RECHARGE_SEND_INTEGRATION, 1, bat.getLoginAccount(), new
                         * HashMap<String, String>()); // 0- // 1+ } } catch (Exception e) {
                         * log.error("", e); }
                         */
                    }
                }
            }

            log.info("==订单支付回调结束==" + df.format(new Date()));
            System.out.println("==订单支付回调结束==" + df.format(new Date()));
            if (isWap) {
                return "wapPaySuccess";
            } else if (null != payCommon && payCommon.isSynchCall()) {
                return SUCCESS;
            } else if ((null != payCommon && !payCommon.isSynchCall()) || isPaded) {
                // 异步或者已支付回调，回复第三方
                PrintWriter outter = response.getWriter();
                outter.write("success");
                outter.flush();
                outter.close();
                return null;
            } else {
                return SUCCESS;
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 退款回调
     */
    public String refundCallBack() {
        HttpServletRequest request = getRequest();
        HttpServletResponse response = this.getResponse();
        if (!StringUtil.isEmpty(request.getParameter("success_num"))) {
            // 支付宝退款
            BuildParam bd = new BuildParam();
            bd.setFactoryType(PayPlugInManger.PLATFORM_ALI_PAY);
            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WEB);
            BasePayPlugInFactory factory = PayPlugInManger.createFactory(bd);
            BasePayPlugIn plugIn = null;
            try {
                plugIn = factory.createPayPlugIn();
                plugIn.setCallBackRequest(request);
                plugIn.setCallBackResponse(response);
                RefundResult refundInfo = (RefundResult) plugIn.onReturn();
                if (null != refundInfo) {
                    // RefundMentRemoteService refundMentService =
                    // (RefundMentRemoteService)
                    // RemoteTool.getRemote(Constants.REMOTE_SERVICE_ORDER,
                    // "refundMentService");
                    refundMentRemoteService.refundCallBack(refundInfo, 3);
                    response.getWriter().print("success");
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
        // else if 其它异步回调方式
        return null;
    }

    /**
     * 充值入口<br>
     * 从充值页面点击充值转入
     */
    public String rechargeValue() throws IOException {
        String samount = getRequest().getParameter("amount").toString();
        Double amount = Double.valueOf(samount);
        // add by zhuyanling 20151120 不符合充值范围(大于等于10，小于等于50000的整数金额),直接返回
        if (amount < 10 || amount > 50000) {
            return INPUT;
        }
        if (samount.indexOf(".") > -1) {
            return INPUT;
        }
        Integer type = 2, status = Constants.RECHARGE_FALSE;
        String content = "用户充值";

        String typeflag = getRequest().getParameter("type");
        if (!StringUtil.isEmpty(typeflag)) {
            type = Integer.valueOf(getRequest().getParameter("type").toString());
            content = "预备金还款";
            status = Constants.RECHARGE_UN_PAY;
        }
        try {
            // TrationListRemoteService trationListRemoteService =
            // (TrationListRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_CUSTOMER,
            // "trationListRemoteService");

            AccountInfo accountInfo = accountinfoService.findByLoginId(this.getUserloginId());
            BnesAcctTransactionQuery b = new BnesAcctTransactionQuery();
            b.setAccountId(accountInfo.getNaccountId());
            b.setType(type);
            b.setContent(content);
            b.setAmount(new BigDecimal(amount));
            b.setStatus(status);
            b.setCreateDate(new Date());
            b.setModifyDate(new Date());
            Integer rechargeCode;
            if (!StringUtil.isEmpty(typeflag)) {
                rechargeCode = trationListRemoteService.paySubmit(b, 1);
            } else {
                rechargeCode = trationListRemoteService.paymentAccount(b);
            }
            this.orderCode = rechargeCode.toString();
            payMethod = Integer.parseInt(Constants.PAY_METHOD_ONLINE.toString());
            payMoney = amount;

            getRequest().setAttribute("orderCode", orderCode);
            getRequest().setAttribute("payMethod", Constants.PAY_METHOD_ONLINE);
            // getRequest().setAttribute("payMoney", payMoney);
            getRequest().setAttribute("rechargeOrOrderFlag", rechargeOrOrderFlag);
        } catch (Exception e) {
            log.error("账户充值失败", e);
            return INPUT;
        }
        rechargeOrOrderFlag = 1;
        orderTimeOut = false;
        return SUCCESS;
    }

    public String rechargeResult() {
        if (rechargeOrOrderFlag == null || rechargeOrOrderFlag == 0) {
            this.rechargeOrOrderFlag = 1;
        }
        String loginId = String.valueOf(super.getUserloginId());
        BnesAcctTransaction bnesAcctTransaction = new BnesAcctTransaction();
        if ((rechargeOrOrderFlag == 3 || rechargeOrOrderFlag == 1) && !orderMainService
                .checkOrderUser(Long.parseLong(loginId), orderCode, rechargeOrOrderFlag)) {
            orderTimeOut = true;
            this.errorMessageFlag = true;
            errorMessage = "请确认充值单是否已经支付或订单存在！";
            return SUCCESS;
        }
        if (rechargeOrOrderFlag == 1) {
            bnesAcctTransaction =
                    (BnesAcctTransaction) checkLoginAndOrderCode(orderCode, rechargeOrOrderFlag);
            // 订单创建人和登陆人为同一个人
            if (bnesAcctTransaction == null) {
                orderTimeOut = true;
                this.errorMessageFlag = true;
                errorMessage = "请确认充值单是否已经支付或订单存在！";
                return SUCCESS;
            }
        }
        try {
            user = this.loginService.queryUserByLoginId(loginId);
        } catch (ServiceException e1) {
            log.error(e1.getMessage());
        }
        if (this.orderCode != null && !this.orderCode.equals("")) {
            Map<String, String> map = new HashMap<String, String>();

            map.put("loginId", loginId);
            map.put("accountTransactionId", orderCode);
            map.put("status", Constants.RECHARGE_FALSE.toString());
            try {
                List<BnesAcctTransaction> list =
                        this.bnesAcctTransactionService.queryTransaction(map);
                if (null != list && list.size() > 0) {
                    BnesAcctTransaction bts = list.get(0);
                    this.payMoney = bts.getAmount().doubleValue();
                    orderTimeOut = false;
                }
            } catch (ServiceException e) {
                log.error("", e);
                return ERROR;
            }
        }
        this.orderCode = bnesAcctTransaction.getAccountNumber();
        return SUCCESS;
    }

    /**
     * 支付中心跳转
     */
    public String orderPayRedirect() {
        HttpServletRequest request = getRequest();
        String params = null;
        String html = "";
        try {
            params = URLDecoder.decode(request.getParameter("result"), "utf-8");
            Map<String, String> map = (Map) JSONObject.parse(params);
            String[] extInfo = map.get("pa_MP").split("\\|");
            if ("3".equals(extInfo[2])) {
                String reqUrl = ConfigurationUtil.getString("ali_pay_request_url_prex");
                html = PaymentUtil.buildAliPayRequest(map, reqUrl, "post", "确定");
            } else if ("2".equals(extInfo[2])) {
                String yeeURL = ConfigurationUtil.getString("onlinePaymentReqURL");
                html = PaymentUtil.buildForm(map, yeeURL, "post",
                        ConfigurationUtil.getString("keyValue"));
            }
        } catch (UnsupportedEncodingException e) {
            log.error("支付方式解析json异常", e);
        } catch (Exception e) {
            log.error("支付中心跳转发生异常", e);
        }
        this.showpage(html);
        return null;
    }

    /**
     * 退款中转
     */
    public String refundTrack() throws Exception {
        HttpServletRequest request = getRequest();
        String platformCode = request.getParameter("platformCode");
        BuildParam bd = null;
        log.info("支付宝退款中转refundTrack：kmRefundSign=" + request.getParameter("kmRefundSign")
                + ";refundNo=" + request.getParameter("refundNo") + ";platformCode="
                + request.getParameter("platformCode"));
        if ("3".equals(platformCode) && "0d36faa47670a361d807b046d9229eaf"
                .equals(request.getParameter("kmRefundSign"))) {
            bd = new BuildParam();
            bd.setFactoryType(PayPlugInManger.PLATFORM_ALI_PAY);
            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WEB);
        }
        if (null != bd) {
            BasePayPlugInFactory factory = PayPlugInManger.createFactory(bd);
            BasePayPlugIn plugIn = null;
            try {
                String refundNo = request.getParameter("refundNo");
                plugIn = factory.createPayPlugIn();
                RefundParamInfo rpi = new RefundParamInfo();
                rpi.setBatchNo(refundNo);
                rpi.setExtParmas(request.getParameter("reqData"));
                plugIn.setRefundInfo(rpi);
                plugIn.setCallBackRequest(request);
                plugIn.setCallBackResponse(getResponse());
                this.showpage(plugIn.onPay());
            } catch (Exception e) {
                log.error("refundNo:" + request.getParameter("refundNo") + "支付宝退款失败"
                        + e.getMessage());
            }
        }
        return null;
    }

    /**
     * 回调获取微信的openid
     */
    public String getRedirectUriOpenId() {
        HttpServletRequest req = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        String code = req.getParameter("code");

        // 未获取到code
        if (null == code) {
            log.error("和微信链接异常,未获取到code!");
        }
        net.sf.json.JSONObject jsonObject = PaymentUtil.getAccessTokenByCode(code);
        openid = jsonObject.getString("openid");
        return "wapPay";
    }

    /**
     * TODO 描述这个方法的作用 <br/>
     * 预售支付
     *
     * @author KM
     */
    public String payorderForYs() throws Exception {

        String result = null;
        HttpServletRequest request = getRequest();
        String wp = request.getParameter("wapPay");
        int wapPay = "1".equals(wp) ? 1 : 0;
        this.orderCode = request.getParameter("orderCode");
        Long loginId = super.getUserloginId();
        OrderMain orderMain;
        String loginAccount;

        Map<String, String> map = new HashMap<String, String>();
        map.put("orderCode", orderCode);
        String notPay = String.valueOf(Constants.ORDER_STATUS_NOT_PAY);
        String waitPay = String.valueOf(Constants.ORDER_STATUS_NOT_PAY_RETAINAGE);
        if (null != payWkFlage && !"".equals(payWkFlage.trim())) {
            map.put("orderStatus", waitPay);
        } else {
            map.put("orderStatus", notPay);
        }
        List<OrderMain> orderMainList = orderMainService.findByOrderCode(map);
        if (null != orderMainList && orderMainList.size() > 0) {
            orderMain = orderMainList.get(0);
            loginAccount = orderMain.getCustomerAccount();
        } else {
            result = "<script type='text/javascript'>alert('订单号不存在或已支付！');window.close();</script>";
            this.showpage(result);
            return null;
        }


        // 构造订单对象，主要是为支付组件注入订单号、金额等关键信息
        PayCommonObject order = new PayCommonObject();
        // 设置支付来源为预售
        order.setPaySource("YS");
        String platformCode = "", platformName = "";
        // 根据约定类型创建不同的支付组件工厂类
        BasePayPlugInFactory factory = null;
        BuildParam bd = new BuildParam();
        if (payType.trim().equals("saveCard") && !StringUtil.isEmpty(payMessage)) {// 支付宝网银
            /**
             * 财付通网银 bd.setFactoryType(PayPlugInManger.PLATFORM_TEN_PAY); platformCode =
             * ConfigurationUtil.getString("ten_pay_code"); request.setAttribute("bank_type",
             * payMessage);
             */
            platformCode = ConfigurationUtil.getString("ali_pay_code");
            bd.setFactoryType(PayPlugInManger.PLATFORM_ALI_PAY);
            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WEB);
            order.setBankId(payMessage);
        } else if (payType.trim().equals("pay")) { // 通过支付平台
            if (ConfigurationUtil.getString("ali_pay_code").equals(payMessage.trim())) {// 支付宝
                bd.setFactoryType(PayPlugInManger.PLATFORM_ALI_PAY);
                if (Constants.WAP_PAY_FLAG != wapPay) {
                    bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WEB);
                } else {
                    bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WAP);
                }
            } else if (ConfigurationUtil.getString("ten_pay_code").equals(payMessage.trim())) {// 财付通支付
                bd.setFactoryType(PayPlugInManger.PLATFORM_TEN_PAY);
            } else if ("2".equals(payMessage.trim())) {// 易宝支付
                bd.setFactoryType(PayPlugInManger.PLATFORM_YEE_PAY);
            } else if ("5".equals(payMessage.trim())) {// 微信支付
                bd.setFactoryType(PayPlugInManger.PLATFORM_WX_PAY);
            } else if ("7".equals(payMessage.trim())) {// 康美通支付
                bd.setFactoryType(PayPlugInManger.PLATFORM_KMT_PAY);// 康美通
                if (Constants.WAP_PAY_FLAG != wapPay) {
                    bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WEB);
                } else {
                    bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WAP);
                }
            } else {
                result = "<script type='text/javascript'>alert('支付方式不存在！');window.close();</script>";
                this.showpage(result);
                return null;
            }
            platformCode = payMessage;
        } else if ("sdPay".equals(payType.trim())) {// 时代支付
            bd.setFactoryType(PayPlugInManger.PLATFORM_SD_PAY);
            platformCode = ConfigurationUtil.getString("sd_pay_code");
            order.setPayDateStr(StringUtil.getSimpleJoin(orderMain.getCreateDate()));// 时代支付设置下单时间
        } else if (payType.trim().equals("accessCard")) {// 通过信用卡
            // 待扩展
        } else {
            result = "<script type='text/javascript'>alert('支付方式不存在！');window.close();</script>";
            this.showpage(result);
            return null;
        }
        // 由支付组件工厂类创建支付组件
        BasePayPlugIn plugIn = null;
        try {
            platformName = Constants.getPlatformName(platformCode);
            factory = PayPlugInManger.createFactory(bd);
            plugIn = factory.createPayPlugIn();
            plugIn.setCallBackRequest(request);
            plugIn.setCallBackResponse(this.getResponse());
        } catch (NullPointerException e) {
            log.error("", e);
            result = "<script type='text/javascript'>alert('支付失败！');window.close();</script>";
            this.showpage(result);
            return null;
        }

        BigDecimal orderMoney = orderMain.getDepositSum();// 预售定金
        if (null == orderMoney || orderMoney.compareTo(BigDecimal.ZERO) <= 0) {
            result = "<script type='text/javascript'>alert('预售订单定金为0！');window.close();</script>";
        } else {
            if (null != payWkFlage && !"".equals(payWkFlage.trim())) {// 支付尾款
                orderMoney = orderMain.getAmountPayable().subtract(orderMoney); // 尾款 = 订单应付金额 - 定金
                if (null == orderMoney || orderMoney.compareTo(BigDecimal.ZERO) <= 0) {
                    result = "<script type='text/javascript'>alert('预售订单需支付的尾款为0！');window.close();</script>";
                } else {
                    order.setCurrencyCode("RMB");
                    order.setMoneyStr(orderMoney.toString());

                    User u = new User();
                    u.setLoginAccount(loginAccount);
                    if (orderMain.getCustomerId() != null) {
                        user = loginService
                                .queryUserByLoginId(orderMain.getCustomerId().toString());
                    } else {
                        user = this.getLoginUser();
                    }

                    rechargeOrOrderFlag = 2; // 支付标识

                    if (null != user) {
                        order.setExtInfo(rechargeOrOrderFlag.toString() + "|" + user.getLoginId()
                                + "|" + platformCode);
                    } else {
                        order.setExtInfo(rechargeOrOrderFlag.toString() + "|" + loginId + "|"
                                + platformCode);
                    }
                    if (null != payWkFlage && !"".equals(payWkFlage.trim())) {// 支付尾款
                        order.setOrderCode(orderCode + "a");// 支付尾款时，订单号最后加上字母"a",用于实现一单第二次成功支付
                    } else {// 支付定金
                        order.setOrderCode(orderCode);
                    }
                    plugIn.setOrder(order);
                    plugIn.setUser(user);
                    result = plugIn.onPay();

                    // 写入支付流水，为防止调单后边处理
                    Map<String, String> paramMap = new HashMap<String, String>();
                    String orderStatus =
                            String.valueOf(OrderDictionaryEnum.OrderPayState.Ready.getKey());
                    paramMap.put("orderCode", orderCode);
                    paramMap.put("state", orderStatus);
                    paramMap.put("paymentWay", Constants.PAY_METHOD_ONLINE_PLATFORM.toString());
                    // 流水是否已经写入
                    List<com.pltfm.app.entities.OrderPayStatement> paystatementList =
                            new ArrayList<com.pltfm.app.entities.OrderPayStatement>();
                    com.pltfm.app.entities.OrderPayStatement paystatement =
                            new com.pltfm.app.entities.OrderPayStatement();
                    paystatement.setPaymentWay(Constants.PAY_METHOD_ONLINE_PLATFORM);
                    paystatement.setAccount(orderMain.getCustomerAccount());
                    paystatement.setOrderMoney(orderMoney);
                    paystatement.setCreateDate(new Date());
                    paystatement.setEndDate(new Date());
                    paystatement.setFlag(Constants.ORDER_PAY_FLAG_PAYMENT);
                    paystatement.setOrderCode(orderCode);
                    paystatement.setPlatFormCode(platformCode);
                    paystatement.setPlatFormName(platformName);
                    paystatement.setBankCode(order.getBankId());
                    // 支付状态：准备
                    paystatement.setState(
                            Long.valueOf(OrderDictionaryEnum.OrderPayState.Ready.getKey()));
                    if (null != payWkFlage && !"".equals(payWkFlage.trim())) {// 支付尾款
                        paystatement.setYsFlage("2");// 支付尾款
                    } else {
                        paystatement.setYsFlage("1");// 支付定金
                    }
                    paystatementList.add(paystatement);
                    orderCallBackRemoteService.insertPayStatement(paystatementList);
                }
            }


        }

        this.showpage(result);
        return null;

    }

    /**
     * 预售订单支付回调方法
     *
     * @author KM
     */
    public String payCallBackForYs() {

        try {
            payCommon = null;
            HttpServletRequest request = getRequest();
            HttpServletResponse response = getResponse();
            boolean isWap = false, isPaded = false;
            orderCode = request.getParameter("out_trade_no");
            BuildParam bd = new BuildParam();
            Long platformCode = null;
            BasePayPlugIn plugIn = null;
            BasePayPlugInFactory factory = null;
            String returnURL = request.getRequestURL().toString();// 请求地址
            String wap = request.getParameter("wap");
            String app = request.getParameter("app");

            // 订单支付流水与实际支付方式不一样时，调用
            String tradeNot = request.getParameter("trade_no");// 支付宝专用参数
            String tradeModet = request.getParameter("trade_mode");// 财付通专业参数
            String secIdt = request.getParameter("sec_id");// 支付宝专用参数
            if (!StringUtil.isEmpty(tradeModet)) {
                platformCode = (long) PayPlugInManger.PLATFORM_TEN_PAY;
            } else if (!StringUtil.isEmpty(tradeNot) || !StringUtil.isEmpty(secIdt)) {
                platformCode = (long) PayPlugInManger.PLATFORM_ALI_PAY;

            } else if (!StringUtil.isEmpty(returnURL) && returnURL.indexOf("wxpay") != -1) {
                platformCode = (long) PayPlugInManger.PLATFORM_WX_PAY;
            } else if (null != request.getParameter("tradeNo")) {
                // 康美通专用参数
                platformCode = (long) PayPlugInManger.PLATFORM_KMT_PAY;
            }
            // 修改为实际的交易平台
            // orderPayStatementService.insertOrUpdateOrderPayStatement(orderCode,platformCode.toString());

            if (StringUtil.isEmpty(orderCode)
                    && StringUtil.isEmpty(orderCode = request.getParameter("outTradeNo"))
                    && StringUtil.isEmpty(orderCode = request.getParameter("r6_Order"))) {
                // 流获取订单号
                if (!StringUtil.isEmpty(request.getParameter("sec_id"))
                        || !StringUtil.isEmpty(request.getParameter("request_token"))) {
                    bd.setFactoryType(PayPlugInManger.PLATFORM_ALI_PAY);
                    bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WAP); // 支付宝WAP回调
                    isWap = true;
                } else if (!StringUtil.isEmpty(returnURL) && returnURL.indexOf("wxpay") != -1) {
                    bd.setFactoryType(PayPlugInManger.PLATFORM_WX_PAY); // 微信回调
                    isWap = true;
                }
                factory = PayPlugInManger.createFactory(bd);
                platformCode = Long.valueOf(bd.getFactoryType());
                try {
                    plugIn = factory.createPayPlugIn();
                    plugIn.setCallBackRequest(request);
                    plugIn.setCallBackResponse(response);
                    payCommon = (PayCommonObject) plugIn.onReturn();
                    orderCode = null != payCommon ? payCommon.getOrderCode() : null;
                    if (null != payCommon && null != payCommon.getPayStateCode()) {
                        orderPayFlag = Integer.parseInt(payCommon.getPayStateCode());
                    }
                } catch (Exception e) {
                    log.error("", e);
                }
            }
            if (!StringUtil.isEmpty(orderCode)) {
                String requestToken = request.getParameter("request_token");
                String secId = request.getParameter("sec_id");// 支付宝专用参数
                String ysFlage = "0";// 预售订单支付类型，
                if (orderCode.contains("a")) {// 如果订单号包含字母a,则说明是尾款支付回调
                    orderCode = orderCode.replace("a", "");
                    ysFlage = "2";
                } else {// 如果订单号不包含字母a,则说明是定金支付回调
                    ysFlage = "1";
                }

                Map<String, String> queryc = new HashMap<String, String>();
                queryc.put("orderCode", orderCode);
                queryc.put("ysFlage", ysFlage);
                Map<String, Object> orderInfo = orderMainService.queryPayCallForYs(queryc);// 查询状态为【准备】的支付流水
                if (null != orderInfo && !orderInfo.isEmpty()) {
                    platformCode = Long.parseLong(orderInfo.get("PLATFORMCODE").toString());
                    if (platformCode == PayPlugInManger.PLATFORM_ALI_PAY) {
                        if (!StringUtil.isEmpty(secId) || !StringUtil.isEmpty(requestToken)) {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WAP);
                            isWap = true;
                        } else {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WEB);
                        }
                    }

                    if (platformCode == PayPlugInManger.PLATFORM_KMT_PAY) { // 康美通
                        if (!StringUtil.isEmpty(wap)) {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WAP);
                            isWap = true;
                        } else if (!StringUtil.isEmpty(app)) {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_APP);
                        } else {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WEB);
                        }
                    }
                } else {
                    String tradeNo = request.getParameter("trade_no");// 支付宝专用参数
                    String tradeMode = request.getParameter("trade_mode");// 财付通专业参数
                    if (!StringUtil.isEmpty(tradeMode)) {
                        platformCode = (long) PayPlugInManger.PLATFORM_TEN_PAY;
                    } else if (!StringUtil.isEmpty(tradeNo) || !StringUtil.isEmpty(secId)) {
                        platformCode = (long) PayPlugInManger.PLATFORM_ALI_PAY;
                        if (!StringUtil.isEmpty(secId) || !StringUtil.isEmpty(requestToken)) {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WAP);
                            isWap = true;
                        } else {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WEB);
                        }
                    } else if (!StringUtil.isEmpty(returnURL) && returnURL.indexOf("wxpay") != -1) {
                        platformCode = (long) PayPlugInManger.PLATFORM_WX_PAY;
                    } else if (null != request.getParameter("tradeNo")) {
                        // 康美通专用参数
                        platformCode = (long) PayPlugInManger.PLATFORM_KMT_PAY;
                        if (!StringUtil.isEmpty(wap)) {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WAP);
                            isWap = true;
                        } else if (!StringUtil.isEmpty(app)) {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_APP);
                        } else {
                            bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WEB);
                        }

                    } else if (null != request.getParameter("isSdPay")) {
                        // 时代支付专用参数
                        platformCode = (long) PayPlugInManger.PLATFORM_SD_PAY;
                    } else {
                        platformCode = (long) PayPlugInManger.PLATFORM_YEE_PAY;
                    }
                }

                if (null == payCommon) {
                    bd.setFactoryType(platformCode.intValue());
                    factory = PayPlugInManger.createFactory(bd);
                    plugIn = factory.createPayPlugIn();
                    plugIn.setCallBackRequest(request);
                    plugIn.setCallBackResponse(response);
                    payCommon = (PayCommonObject) plugIn.onReturn();
                    if (null != payCommon && null != payCommon.getPayStateCode()) {
                        orderPayFlag = Integer.parseInt(payCommon.getPayStateCode());
                    }
                    if (!isWap && 5 == platformCode) {
                        isWap = true;
                    }
                }
                if (null != payCommon && null != orderInfo && !orderInfo.isEmpty()) {
                    Long orderStatus = null, loginId = null;
                    loginId = Long.parseLong(orderInfo.get("CUSTOMERID").toString());
                    BigDecimal needpay = null;// 需支付

                    if ((null != (orderStatus =
                            Long.parseLong(orderInfo.get("ORDERSTATUS").toString()))
                            && orderStatus >= OrderDictionaryEnum.Order_Status.Pay_Done.getKey()
                            && orderStatus != OrderDictionaryEnum.Order_Status.Nopay_FinalMoney
                                    .getKey())
                            || (needpay = (BigDecimal) orderInfo.get("NEEDPAY"))
                                    .compareTo(BigDecimal.ZERO) == 0
                            || (null != orderInfo.get("OPSNO")
                                    && orderInfo.get("OPSNO").toString()
                                            .indexOf(payCommon.getTransitionNO()) >= 0
                                    && needpay.compareTo(BigDecimal.ZERO) == 0)) {
                        payFlag = true;
                        isPaded = true;
                    } else if (null != orderStatus && orderStatus
                            .intValue() == OrderDictionaryEnum.Order_Status.Not_Pay.getKey()
                            && ysFlage.equals("1")) {// 预售支付定金
                        payCommon.setExtInfo("2|" + loginId + '|' + platformCode + '|' + ysFlage);
                        // 新增支付流水和操作流水
                        orderPayService.orderRemotePayForYsDeposit(orderCode, loginId, payCommon);
                        // 发送支付成功短信
                        List<Long> uidList = new ArrayList<Long>();
                        uidList.add(loginId);
                        List<String> phones = new ArrayList<String>();
                        phones.add(mobile);
                        KmMsg kmMsg = new KmMsg("6000", false);
                        kmMsg.getMsgData().put("kmMsgType", MessageConstants.KMMSG_MOBIL);
                        kmMsg.getMsgData().put("smsmailType",
                                EmailSendType.MSGPAYSUCCESS.getStatus());
                        kmMsg.getMsgData().put("systemType",
                                MessageConstants.KMMSG_SYSTEM_TYPE_B2B);
                        kmMsg.getMsgData().put("mobilePhones", phones);
                        kmMsg.getMsgData().put("msgSendType", MessageConstants.EM_SEND_TYPE_IMM);
                        kmMsg.getMsgData().put("uidList", uidList);
                        kmMsg.getMsgData().put("orderCode", orderCode);
                        kmMsg.getMsgData().put("modelType",
                                MessageTypeEnum.ORDERPAYSUCCESS.getStatus());
                        payFlag = true;
                        sendKmMsgService.sendKmMsg(kmMsg);
                    } else if (null != orderStatus && orderStatus
                            .intValue() == OrderDictionaryEnum.Order_Status.Nopay_FinalMoney
                                    .getKey()
                            && ysFlage.equals("2")) {// 预售支付尾款
                        rechargeOrOrderFlag = 2;
                        payFlag = true;
                        String extInfo = "2|" + loginId + "|" + platformCode + "|" + ysFlage;
                        payCommon.setExtInfo("");
                        payCommon.setExtInfo(extInfo);
                        if (payCommon.getOrderCode().contains("a")) {// 如果订单号包含字母a,则说明是尾款支付回调
                            orderCode = orderCode.replace("a", "");
                        }
                        payCommon.setOrderCode(orderCode);
                        loginId = Long.parseLong(orderInfo.get("CUSTOMERID").toString());
                        if (orderPayService.orderRemotePay(orderCode, loginId, payCommon)
                                && null != payCommon.getMoneyStr() && needpay
                                        .compareTo(new BigDecimal(payCommon.getMoneyStr())) <= 0) {
                            // myOrderService.pushOrderUserSource(orderCode);

                            try {
                                if (!productStockService.updateProductOrderQuantityCache(loginId,
                                        true, orderCode)) {
                                    log.error("订单" + orderCode + "减少活动库存失败");
                                }
                                // 发送支付成功短信
                                List<Long> uidList = new ArrayList<Long>();
                                uidList.add(loginId);
                                List<String> phones = new ArrayList<String>();
                                phones.add(mobile);
                                KmMsg kmMsg = new KmMsg("6000", false);
                                kmMsg.getMsgData().put("kmMsgType", MessageConstants.KMMSG_MOBIL);
                                kmMsg.getMsgData().put("smsmailType",
                                        EmailSendType.MSGPAYSUCCESS.getStatus());
                                kmMsg.getMsgData().put("systemType",
                                        MessageConstants.KMMSG_SYSTEM_TYPE_B2B);
                                kmMsg.getMsgData().put("mobilePhones", phones);
                                kmMsg.getMsgData().put("msgSendType",
                                        MessageConstants.EM_SEND_TYPE_IMM);
                                kmMsg.getMsgData().put("uidList", uidList);
                                kmMsg.getMsgData().put("orderCode", orderCode);
                                kmMsg.getMsgData().put("modelType",
                                        MessageTypeEnum.ORDERPAYSUCCESS.getStatus());
                                sendKmMsgService.sendKmMsg(kmMsg);

                            } catch (Exception e) {
                                log.error("", e);
                            }
                        }
                        // 如果订金支付成功，把支付标志设置为true
                    } else if (orderStatus
                            .intValue() == OrderDictionaryEnum.Order_Status.Nopay_FinalMoney
                                    .getKey()
                            && ysFlage.equals("1")) {
                        payFlag = true;
                    }
                }
            }
            if (isWap) {
                return "wapPaySuccess";
            } else if (null != payCommon && payCommon.isSynchCall()) {
                return SUCCESS;
            } else if ((null != payCommon && !payCommon.isSynchCall()) || isPaded) {
                // 异步或者已支付回调，回复第三方
                PrintWriter outter = response.getWriter();
                outter.write("success");
                outter.flush();
                outter.close();
                return null;
            } else {
                return SUCCESS;
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return null;

    }

    /**
     * 预售支付定金入口
     */
    public String payYSOrderInit() {
        try {
            if (null != orderCode && orderCode.length() > 0) {
                HttpServletRequest request = getRequest();
                String wp = request.getParameter("wapPay");
                boolean wapPay = "1".equals(wp);
                this.payFlag = false;
                // 是否微信支付
                isWxPay = wapPay && request.getHeader("user-agent").indexOf("MicroMessenger") > 0
                        ? "0" : "1";// 当从微信过来时的wap版页面为:0
                orderCode = null == request.getAttribute("orderCode")
                        ? request.getParameter("orderCode")
                        : request.getAttribute("orderCode").toString();
                rechargeOrOrderFlag = Integer.parseInt(request.getParameter("rechargeOrOrderFlag"));

                // 判断是否是当前用户订单 orderMainService.checkOrderUser(loginId, orderCode,
                // rechargeOrOrderFlag);
                execCps(getRequest(), orderCode, request.getHeader("host")); // cps
                // 订单支付
                if (rechargeOrOrderFlag == 2) {
                    Map<String, String> paramMap = new HashMap<String, String>();
                    paramMap.put("orderCode", orderCode);
                    paramMap.put("orderStatus", Constants.ORDER_STATUS_NOT_PAY.toString());
                    OrderMain orderMain = null;
                    List<OrderMain> orderList = null;
                    BigDecimal needToPayDeposit = BigDecimal.ONE.negate();
                    try {
                        orderList = orderMainService.findByOrderCode(paramMap);
                        payCommon = new PayCommonObject();
                        payCommon.setOrderCode(orderCode);
                        needToPayDeposit = this.orderMainService.findNeedToPayDeposit(orderCode);
                        payMoney = needToPayDeposit.doubleValue();
                    } catch (Exception e) {
                        log.error("获取订单定金出错：" + e.getMessage());
                        return ERROR;
                    }
                    payCommon.setMoneyStr(needToPayDeposit.toString());

                    if (null != orderList && orderList.size() > 0) {
                        orderMain = orderList.get(0);
                        this.orderType = orderMain.getOrderType();
                        this.mobile = orderMain.getOrderPurchaserMobile();
                        this.credits = orderMain.getTotalCredit();
                        // if (needToPayDeposit != null &&
                        // needToPayDeposit.compareTo(BigDecimal.ZERO) == 0
                        // && loginId != null && loginId != 0) {
                        // try {
                        // // 调用远程支付接口进行支付
                        // this.payFlag = orderPayService.orderRemotePay(orderCode, loginId, null);
                        // if (payFlag) {
                        // // 订单用户来源推送
                        // // myOrderService.pushOrderUserSource(orderCode);
                        // try {
                        // // 减少实际库存
                        // // if (!productStockService.updateProductOrderQuantityCache(loginId,
                        // true,
                        // // orderCode)) {
                        // // log.error("订单" + orderCode + "减少活动库存失败");
                        // // }
                        // } catch (Exception e) {
                        // log.error("", e);
                        // }
                        // }
                        // } catch (Exception e) {
                        // log.error("", e);
                        // }
                        // }
                    } else if (BigDecimal.ZERO.compareTo(needToPayDeposit) == 0) {// 需要支付定金为0
                        this.payFlag = true;
                    }
                    if (payFlag && !wapPay) {
                        return "paySuccess";
                    } else if (payFlag) {
                        return "wapPaySuccess";
                    }
                    // 订单时间+1h
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(orderMain.getCreateDate());
                    calendar.add(Calendar.HOUR, Constants.PRESELL_ORDER_DISABLED_TIME);

                    // 订单是否过期
                    this.orderTimeOut = (calendar.getTime().getTime() < new Date().getTime()
                            && orderMain.getOrderType() == 7);
                    if (!wapPay) {
                        return SUCCESS;
                    } else {// 微信支付
                        if ("0".equals(isWxPay)) {
                            try {
                                getResponse().sendRedirect(
                                        PaymentUtil.getOauth2ByRedirectUri(URLEncoder.encode(
                                                ConfigurationUtil.getString("wx_redirect_uri")
                                                        + "?orderTimeOut=" + orderTimeOut
                                                        + "&rechargeOrOrderFlag="
                                                        + rechargeOrOrderFlag + "&orderCode="
                                                        + orderCode + "&payMoney=" + payMoney
                                                        + "&isWxPay=" + isWxPay + "&errorMessage="
                                                        + errorMessage,
                                                "utf-8")));
                            } catch (Exception e) {
                                log.error("微信支付异常", e);
                            }
                        }
                        return "wapDepositPay";
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 预售支付尾款入口
     */
    public String payWKOrderInit() {
        try {
            if (null != orderCode && orderCode.length() > 0) {
                HttpServletRequest request = getRequest();
                String wp = request.getParameter("wapPay");
                boolean wapPay = "1".equals(wp);
                this.payFlag = false;
                // 是否微信支付
                isWxPay = wapPay && request.getHeader("user-agent").indexOf("MicroMessenger") > 0
                        ? "0" : "1";// 当从微信过来时的wap版页面为:0
                orderCode = null == request.getAttribute("orderCode")
                        ? request.getParameter("orderCode")
                        : request.getAttribute("orderCode").toString();
                rechargeOrOrderFlag = Integer.parseInt(request.getParameter("rechargeOrOrderFlag"));

                // 判断是否是当前用户订单 orderMainService.checkOrderUser(loginId, orderCode,
                // rechargeOrOrderFlag);
                execCps(getRequest(), orderCode, request.getHeader("host")); // cps
                // 订单支付
                if (rechargeOrOrderFlag == 2) {
                    Map<String, String> paramMap = new HashMap<String, String>();
                    paramMap.put("orderCode", orderCode);
                    paramMap.put("orderStatus",
                            Constants.ORDER_STATUS_NOT_PAY_RETAINAGE.toString());
                    OrderMain orderMain = null;
                    List<OrderMain> orderList = null;
                    BigDecimal needToPayRetainage = BigDecimal.ONE.negate();
                    orderList = orderMainService.findByOrderCode(paramMap);

                    if (null != orderList && orderList.size() > 0) {
                        orderMain = orderList.get(0);
                        this.orderType = orderMain.getOrderType();
                        this.mobile = orderMain.getOrderPurchaserMobile();
                        this.credits = orderMain.getTotalCredit();
                    } else if (BigDecimal.ZERO.compareTo(needToPayRetainage) == 0) {// 需要支付的尾款为0
                        this.payFlag = true;
                    }
                    if (payFlag && !wapPay) {
                        return "paySuccess";
                    } else if (payFlag) {
                        return "wapPaySuccess";
                    }

                    // 获取订单的待付尾款金额
                    try {
                        payCommon = new PayCommonObject();
                        payCommon.setOrderCode(orderCode);
                        BigDecimal orderMoney = orderMain.getDepositSum();// 预售定金
                        payMoney = orderMain.getAmountPayable().subtract(orderMoney).doubleValue(); // 尾款
                        // =
                        // 订单应付金额
                        // -
                        // 定金
                    } catch (Exception e) {
                        log.error("获取订单待付尾款出错：" + e.getMessage());
                        return ERROR;
                    }
                    payCommon.setMoneyStr(needToPayRetainage.toString());

                    // 获取当前时间与尾款支付开始时间，截止时间的差值
                    Map<String, Object> timeMap = myOrderService.findReserveByOrderCode(orderCode);
                    BigDecimal start = (BigDecimal) timeMap.get("FINALPAYSTARTTIME");
                    BigDecimal end = (BigDecimal) timeMap.get("FINALPAYENDTIME");

                    // 是否提前支付尾款
                    boolean isAdvance = start.longValue() < 0;
                    // 是否逾期支付尾款
                    boolean isOverdue = end.longValue() > 0;

                    this.orderTimeOut = (isAdvance && isOverdue && orderMain.getOrderType() == 7);
                    if (!wapPay) {
                        return SUCCESS;
                    } else {// 微信支付
                        if ("0".equals(isWxPay)) {
                            try {
                                getResponse().sendRedirect(
                                        PaymentUtil.getOauth2ByRedirectUri(URLEncoder.encode(
                                                ConfigurationUtil.getString("wx_redirect_uri")
                                                        + "?orderTimeOut=" + orderTimeOut
                                                        + "&rechargeOrOrderFlag="
                                                        + rechargeOrOrderFlag + "&orderCode="
                                                        + orderCode + "&payMoney=" + payMoney
                                                        + "&isWxPay=" + isWxPay + "&errorMessage="
                                                        + errorMessage,
                                                "utf-8")));
                            } catch (Exception e) {
                                log.error("微信支付异常", e);
                            }
                        }
                        return "wapRetainagePay";
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getPageMsg() {
        return pageMsg;
    }

    public void setPageMsg(String pageMsg) {
        this.pageMsg = pageMsg;
    }

    public boolean isPayFlag() {
        return payFlag;
    }

    public void setPayFlag(boolean payFlag) {
        this.payFlag = payFlag;
    }

    public Integer getRechargeOrOrderFlag() {
        return rechargeOrOrderFlag;
    }

    public void setRechargeOrOrderFlag(Integer rechargeOrOrderFlag) {
        this.rechargeOrOrderFlag = rechargeOrOrderFlag;
    }

    public PayCommonObject getPayCommon() {
        return payCommon;
    }

    public void setPayCommon(PayCommonObject payCommon) {
        this.payCommon = payCommon;
    }

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
        this.credits = credits;
    }

    public String getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(String couponMoney) {
        this.couponMoney = couponMoney;
    }

    public boolean isOrderTimeOut() {
        return orderTimeOut;
    }

    public void setOrderTimeOut(boolean orderTimeOut) {
        this.orderTimeOut = orderTimeOut;
    }

    public Long getOrderMainId() {
        return orderMainId;
    }

    public void setOrderMainId(Long orderMainId) {
        this.orderMainId = orderMainId;
    }

    public Integer getOrderPayFlag() {
        return orderPayFlag;
    }

    public void setOrderPayFlag(Integer orderPayFlag) {
        this.orderPayFlag = orderPayFlag;
    }

    public String getBankList() {
        return bankList;
    }

    public void setBankList(String bankList) {
        this.bankList = bankList;
    }

    public String getPayMessage() {
        return payMessage;
    }

    public void setPayMessage(String payMessage) {
        this.payMessage = payMessage;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getBankChineseName() {
        return bankChineseName;
    }

    public void setBankChineseName(String bankChineseName) {
        this.bankChineseName = bankChineseName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isErrorMessageFlag() {
        return errorMessageFlag;
    }

    public void setErrorMessageFlag(boolean errorMessageFlag) {
        this.errorMessageFlag = errorMessageFlag;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getIsWxPay() {
        return isWxPay;
    }

    public void setIsWxPay(String isWxPay) {
        this.isWxPay = isWxPay;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSdBankList() {
        return sdBankList;
    }

    public void setSdBankList(String sdBankList) {
        this.sdBankList = sdBankList;
    }

    public String getPayWkFlage() {
        return payWkFlage;
    }

    public void setPayWkFlage(String payWkFlage) {
        this.payWkFlage = payWkFlage;
    }

}
