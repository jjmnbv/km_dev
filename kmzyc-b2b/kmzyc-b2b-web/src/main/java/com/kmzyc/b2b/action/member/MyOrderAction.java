package com.kmzyc.b2b.action.member;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.common.collect.Maps;
import com.km.framework.action.BaseAction;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.CouponAndPromotion;
import com.kmzyc.b2b.model.OrderAssessInfo;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.OrderOperateStatement;
import com.kmzyc.b2b.model.OrderPayStatement;
import com.kmzyc.b2b.model.OrderSync;
import com.kmzyc.b2b.model.QueryResult;
import com.kmzyc.b2b.service.EraInfoService;
import com.kmzyc.b2b.service.OrderCancelService;
import com.kmzyc.b2b.service.OrderTrailService;
import com.kmzyc.b2b.service.QryOrderOnLineService;
import com.kmzyc.b2b.service.member.AccountService;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.b2b.util.OrderStatusUtil;
import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.b2b.vo.OrderTrailInfo;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.entities.ExpressTrack;
import com.kmzyc.express.remote.ExpressSubscriptionRemoteService;
import com.kmzyc.framework.ajax.AjaxUtil;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.order.remote.OrderCancelRemoteService;
import com.kmzyc.order.remote.OrderChangeStatusRemoteService;
import com.kmzyc.order.remote.OrderQryRemoteService;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.entities.OrderCancelReason;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.whalin.MemCached.MemCachedClient;

import redis.clients.jedis.JedisCluster;

// import com.km.framework.common.util.RemoteTool;

/**
 * Description:会员中心-我的订单访问入口 User: lishiming Date: 13-10-15 下午3:45 Since: 1.0
 */
@SuppressWarnings({"serial", "unchecked"})
@Controller
@Scope("prototype")
public class MyOrderAction extends BaseAction {
    // private static Logger logger = Logger.getLogger(MyOrderAction.class);
    private static Logger logger = LoggerFactory.getLogger(MyOrderAction.class);
    private Long time;// 时间戳用于wap
    private String order_operat_key = "order_operat_key";
    @Resource(name = "myOrderServiceImpl")
    private MyOrderService myOrderService;

    @Resource(name = "accountServiceImpl")
    private AccountService accountService;

    @Resource(name = "orderTrailService")
    private OrderTrailService orderTrailService;

    @Autowired
    private ExpressSubscriptionRemoteService expressSubscriptionRemoteService;

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;
    @Resource(name = "eraInfoServiceImpl")
    private EraInfoService eraInfoService;

    @Resource(name = "orderCancelServiceImpl")
    private OrderCancelService orderCancelService;

    @Resource
    private OrderCancelRemoteService orderCancelRemoteService;

    @Resource
    private OrderQryRemoteService orderQryRemoteService;

    @Resource
    private OrderChangeStatusRemoteService orderChangeStatusRemoteService;

    @Resource(name = "qryOrderOnLineService")
    private QryOrderOnLineService qry;


    private int selectOrderStatus = 0;

    public Map<String, Object> orderTime;

    private static final int BEGIN_YEAR = 2012;

    /****** 订单列表页使用的变量 **********/
    private static final String KEYWORD_TIPS = "商品名称、商品编号、订单编号";
    // 订单的下单日期查询条件，需动态组装
    private Map<Integer, String> createDateOptionsMap;
    private String orderMainCode;
    private Long orderMainStatus;
    private List<OrderPayStatement> orderPayStatementList;

    /****** 订单详细信息页使用的变量 **********/
    // 订单id
    private Integer orderMainId;
    // 订单
    private OrderMain orderMain;
    // 订单的子单
    private List<OrderMain> sonOrderMainList;
    // 满额送券
    private List<CouponAndPromotion> couponList;
    // 标示订单是否已完成
    private String isSureOk;
    private int isParentOrder = 0;// 是否为父订单0＝否,1＝是
    private int isMainOrder = 0;// 是否为主订单0＝否,1＝是
    // 标示订单是否打分
    private String isOrderAssess;
    // 订单操作日志
    private List<OrderOperateStatement> listorder;

    private OrderTrailInfo orderTrailInfo;
    // 返回至页面的对象
    private ReturnResult returnResult;
    // 主订单（根订单）操作流水
    private List<OrderOperateStatement> rootOrderOperateStateList;
    // 评论信息
    private OrderAssessInfo orderAssessInfo;

    // 返回按钮
    private int backFlag;
    // 订单用户ID
    private Integer orderUserid;

    // 详细页面用户ID
    private int detailUserId;
    private int isOrderList;

    // 标示页码
    private int pagenumber;

    // 判断过期是否是从订单详情进入的
    private String isMyOrder;

    private String applyContent;

    // 加价
    private double addPrice;

    private Map assessMap;
    private Map<Long, Double> consumptionRateMap;

    /**
     * 查询会员的订单列表
     */
    public String queryOrderList() {
        // 初始化下单日期的查询条件项
        initCreateDateOptions();
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        Pagination page = this.getPagination(5, 10);
        // 页面传入的查询条件
        Map<String, String> objContion = (Map<String, String>) page.getObjCondition();
        // sql查询条件对象
        Map<String, Object> newConditon = new HashMap<String, Object>();
        // 解析并组装查询条件
        newConditon.put("userId", userId);
        // 添加渠道查询条件
        String channel = ConfigurationUtil.getString("CHANNEL");
        newConditon.put("channel", channel);
        if (objContion != null) {
            if (StringUtils.isNotEmpty(objContion.get("createDate"))) {
                // 解析并组装订单日期查询条件
                int createDateFlag = Integer.parseInt(objContion.get("createDate"));
                parseOrderCreateDate(createDateFlag, newConditon);
            }
            if (StringUtils.isNotEmpty(objContion.get("orderStatus"))) {
                // 解析并组装订单状态查询条件
                int orderStatusFlag = Integer.parseInt(objContion.get("orderStatus"));
                OrderStatusUtil.parseOrderStatus(orderStatusFlag, newConditon);
                Object orderStatusObj = newConditon.get("orderStatus");
                if (null != orderStatusObj && orderStatusObj.toString().indexOf(",") < 0) {
                    newConditon.remove("orderStatus");
                    newConditon.put("orderState", orderStatusObj);
                }
            }
            if (KEYWORD_TIPS.equals(objContion.get("keyword")) ||
                    StringUtils.isEmpty(objContion.get("keyword"))) {
                newConditon.put("keyword", "");
            } else {
                newConditon.put("keyword", objContion.get("keyword"));
            }
        } else { // 如果没有查询条件，则也需过滤掉订单状态为"已拆分"、"已拆分未结转"等的订单
            OrderStatusUtil.parseOrderStatus(0, newConditon);
        }
        // 设置查询条件
        page.setObjCondition(newConditon);
        try {
            this.pagintion = myOrderService.findOrderMainByPage(page);
        } catch (Exception e) {
            logger.error("查询会员的订单列表信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 删除订单
     */
    public String deleteOrderMain() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        String userAccount = (String) request.getSession()
                .getAttribute(Constants.SESSION_USER_NAME);
        if (userId == null) {
            logger.error("Session过期");
            return ERROR;
        }
        if (null == orderUserid || orderUserid.intValue() != userId.intValue()) {
            logger.error("Session过期,该订单不是当前用户的订单");
            return ERROR;
        }
        try {
            myOrderService.deleteOrderMain(userAccount, this.orderMainCode);
        } catch (Exception e) {
            logger.error("删除订单出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 取消订单，分两种情况： 1.付款前； 2.付款后送货前，以及送货失败；
     */
    public String cancelOrderMain() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        String userAccount = (String) request.getSession()
                .getAttribute(Constants.SESSION_USER_NAME);
        if (userId == null) {
            logger.error("Session过期");
            return ERROR;
        }
        if (null == orderUserid || orderUserid.intValue() != userId.intValue()) {
            logger.error("Session过期,该订单不是当前用户的订单");
            return ERROR;
        }
        try {
            if (null != session.getAttribute(order_operat_key)) {// 重复提交
                return null;
            }
            session.setAttribute(order_operat_key, "1");// 存储非空即可
            myOrderService.cancelOrderMain(userAccount, this.orderMainCode, this.orderMainStatus);
        } catch (Exception e) {
            session.removeAttribute(order_operat_key);
            logger.error("取消订单出错：" + e.getMessage(), e);
            return ERROR;
        }
        session.removeAttribute(order_operat_key);
        return SUCCESS;
    }

    /**
     * 取消订单wap，分两种情况： 1.付款前； 2.付款后送货前，以及送货失败；
     */
    public String cancelWapOrderMain() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
        if (userId == null) {
            logger.error("Session过期");
            return ERROR;
        }
        if (null == orderUserid || orderUserid.intValue() != userId.intValue()) {
            logger.error("Session过期,该订单不是当前用户的订单");
            return ERROR;
        }
        try {
            if (null != session.getAttribute(order_operat_key)) {
                return null;
            }
            session.setAttribute(order_operat_key, "1");// 存储非空即可
            String userAccount = (String) session.getAttribute(Constants.SESSION_USER_NAME);
            myOrderService.cancelOrderMain(userAccount, this.orderMainCode, this.orderMainStatus);
        } catch (Exception e) {
            session.removeAttribute(order_operat_key);
            logger.error("取消订单出错：" + e.getMessage(), e);
            return ERROR;
        }
        session.removeAttribute(order_operat_key);
        time = new Date().getTime();
        return SUCCESS;
    }

    public String cancelZxOrderMain() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        if (userId == null) {
            logger.error("Session过期");
            return ERROR;
        }
        if (null == orderUserid || orderUserid.intValue() != userId.intValue()) {
            logger.error("Session过期,该订单不是当前用户的订单");
            return ERROR;
        }

        String key = "cancelOrderMain" + orderMainCode;
        try {


            if (null != session.getAttribute(order_operat_key)) {
                return ERROR;
            }
            if (null == jedisCluster.get(key)) {
                jedisCluster.set(key, "1");
                jedisCluster.expire(key, 600);
                session.setAttribute(order_operat_key, "1");// 存储非空即可
                EraInfo era = eraInfoService.queryEraInfoByLoginId(userId);
                OrderCancelReason orderCancelReason = new OrderCancelReason();
                // OrderCancelRemoteService orderCancelRemoteService =
                // (OrderCancelRemoteService) RemoteTool
                // .getRemote(Constants.REMOTE_SERVICE_ORDER,
                // "orderCancelService");
                orderCancelReason.setOrderCode(this.orderMainCode);// 订单号
                orderCancelReason.setMemberNo(era.getEraNo().toString());// 直销会员编号
                orderCancelReason.setUserAccount(era.getLoginAccount());//
                orderCancelReason.setCreateDate(new Date());// 申请时间
                orderCancelReason.setReason(applyContent);// 取消原因
                // orderCancelReason.setRemark();//备注
                orderCancelReason.setHandleStatus(1);// 1，申请中，2失败，3成功
                // 返回值0表示成功，1表示失败
                int re = orderCancelRemoteService.saveOrderCancelReason(orderCancelReason);
                logger.info("结果: " + re + "，记录申请返回结果，返回值0表示成功，1表示失败   ");
                if (re == 0) {
                    // OrderQryRemoteService orderQryRemoteService =
                    // (OrderQryRemoteService) RemoteTool
                    // .getRemote(Constants.REMOTE_SERVICE_ORDER,
                    // "orderQryService");
                    com.pltfm.app.entities.OrderMain om = orderQryRemoteService
                            .getOrderByCode(this.orderMainCode);
                    String orderType = null;
                    int wsCancelResult = -1;
                    if (om.getOrderType().equals(Long.valueOf(3)) ||
                            om.getOrderType().equals(Long.valueOf(4)) ||
                            om.getOrderType().equals(Long.valueOf(5))) {
                        orderType = "54";
                        int orderSum = om.getOriginalOrderSum().multiply(new BigDecimal(100))
                                .intValue();
                        wsCancelResult = orderCancelService
                                .cancelOrder(this.orderMainCode, applyContent, orderType, orderSum,
                                        om.getCreateDate().toString());
                    } else {
                        orderType = "53";
                        wsCancelResult = orderCancelService
                                .cancelOrder(this.orderMainCode, applyContent, orderType, 0, null);
                    }

                    if (wsCancelResult == 1) {
                        logger.info("取消订单webservice调用成功！");
                        // OrderChangeStatusRemoteService
                        // orderChangeStatusRemoteService =
                        // (OrderChangeStatusRemoteService) RemoteTool
                        // .getRemote(Constants.REMOTE_SERVICE_ORDER,
                        // "orderChangeStatusService");
                        QueryResult qr = null;
                        if (orderMain.getOrderStatus().intValue() ==
                                OrderDictionaryEnum.Order_Status.Not_Pay.getKey()) {
                            // 查询第三方支付情况
                            qr = qry.queryByOrder(this.orderMainCode);
                        }
                        orderChangeStatusRemoteService
                                .changeOrderStatus(era.getEraNo().toString(), this.orderMainCode,
                                        (long) (OrderDictionaryEnum.Order_Status.Cancel_Done
                                                .getKey()), null, qr);
                    } else {
                        logger.info("取消订单webservice调用失败！");
                    }
                }
            }
        } catch (Exception e) {
            session.removeAttribute(order_operat_key);
            logger.error("取消订单出错：" + e.getMessage(), e);
            return ERROR;
        }
        session.removeAttribute(order_operat_key);
        time = new Date().getTime();
        return SUCCESS;
    }

    /**
     * 确认收货 1 操作成功 -1 未知请求 -2 此状态下不能进行此操作 -4 调用远程接口异常 -5 数据查询异常
     */
    public String sureHaveProduct() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String userAccount = (String) request.getSession()
                .getAttribute(Constants.SESSION_USER_NAME);
        Map map = new HashMap();
        try {
            // 判断是否登陆（在订单列表）
            if (isOrderList != 0 && StringUtils.isBlank(userAccount)) {
                map.put("isOk", "noSession");
                AjaxUtil.writeJSONToResponse(map);
                return NONE;
            } else {
                // 如果此订单状态为5才执行
                if (myOrderService.findOrderByOrderCode(orderMainCode).getOrderStatus()
                        .intValue() == OrderDictionaryEnum.Order_Status.Ship_Done.getKey()) {
                    int result = myOrderService
                            .sureOrderMain(userAccount, orderMainCode, this.orderMainStatus);
                    result = 1;
                    if (result != 1) {
                        map.put("isOk", "wrong");
                        AjaxUtil.writeJSONToResponse(map);
                        return NONE;
                    } else {
                        map.put("isOk", "right");
                        AjaxUtil.writeJSONToResponse(map);
                    }
                } else {
                    map.put("isOk", "wrong");
                    AjaxUtil.writeJSONToResponse(map);
                    return NONE;
                }
            }
        } catch (Exception e) {
            logger.error("确认订单出错：" + e.getMessage(), e);
            map.put("isOk", "wrong");
            AjaxUtil.writeJSONToResponse(map);
            return NONE;
        }
        return NONE;
    }

    /**
     * 订单详情页的确认收货 ajax
     *
     * @author luoyi
     * @createDate 2013/12/03
     */
    public String confirmReceiptOrder() {
        Map<String, Object> datamap = new HashMap<String, Object>();
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        try {
            if (userId == null) {
                logger.error("session过期" +  ",订单:" + orderMainCode);
                datamap.put("orderMainCode", 0);
                datamap.put("errorMessage", "非该订单用户在确认订单");
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, "确认收货失败，非该订单用户在确认订单",
                        datamap);

            } else {
                if (detailUserId != userId) {
                    logger.error("非该订单用户在确认订单，currentUserID：" + userId + ",订单:" + orderMainCode);
                    datamap.put("orderMainCode", 0);
                    datamap.put("errorMessage", "非该订单用户在确认订单");
                    returnResult = new ReturnResult(InterfaceResultCode.FAILED,
                            "确认收货失败，非该订单用户在确认订单", datamap);
                } else {
                    String userAccount = (String) request.getSession()
                            .getAttribute(Constants.SESSION_USER_NAME);
                    myOrderService.sureOrderMain(userAccount, orderMainCode, orderMainStatus);
                    datamap.put("orderMainCode", orderMainCode);
                    returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "确认收货成功", datamap);

                }
            }
        } catch (Exception e) {
            logger.error("确认订单出错：" + e.getMessage(), e);
            datamap.put("orderMainCode", orderMainCode);
            datamap.put("errorMessage", e.getMessage());
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "确认收货失败", datamap);
        }
        return SUCCESS;
    }

    /**
     * 格式化数字显示
     */
    public String formatDouble(double s) {
        DecimalFormat fmt = new DecimalFormat("0.00");
        return fmt.format(s);
    }

    /**
     * 查询订单的详细信息
     */
    public String queryOrderDetail() {
        if (StringUtil.isEmpty(orderMainCode)) {
            return ERROR;
        }
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        try {
            this.orderMain = myOrderService.findOrderByOrderCode(orderMainCode);// 订单信息
            // 判断该订单是否为当前用户的订单，如果不是则跳转到error页面 add by lishiming
            if (orderMain.getCustomerId().compareTo(new BigDecimal(userId)) != 0) {
                logger.error(
                        "非当前用户查询该订单详情，currentUserID：" + userId + ",订单id:" + orderMain.getOrderId());
                return ERROR;
            }
            // 订单编号
            String orderNo = orderMain.getOrderCode();
            String parentOrderCode = orderMain.getParentOrderCode();

            // 获取订单的操作流水
            // 去除流水中出库的，和部分支付的
            listorder = myOrderService.findOperateByNo(orderNo);
            Collections.reverse(listorder);

            // pv值、返利信息、优惠券信息、加价购金额、根订单的操作流水
            Map<String, Object> map = getOrderInfoFormBuffer(orderNo, parentOrderCode);
            // pv值
            BigDecimal prderPv = (BigDecimal) map.get("pv");
            orderMain.setPv(prderPv);
            // 返利信息
            consumptionRateMap = (Map<Long, Double>) map.get("consumptionRateMap");
            orderMain.setLogisticsName(ConfigurationUtil.getString(orderMain.getLogisticsCode()));
            orderMainId = orderMain.getOrderId().intValue();
            // 看此订单有没有获得优惠券的信息 可做缓存
            couponList = (List<CouponAndPromotion>) map.get("couponList");
            // 根订单操作信息
            rootOrderOperateStateList = (List<OrderOperateStatement>) map
                    .get("rootOrderOperateStateList");
            // 加价购 可做缓存
            List<OrderItem> list = orderMain.getOrderItemList();
            addPrice = 0.0;
            for (int i = 0; i < list.size(); i++) {
                OrderItem orderItem = list.get(i);
                if (orderItem.getCommodityType() != null &&
                        orderItem.getCommodityType() == 5) { // 商品类型为5
                    // 加价购的价格总和
                    addPrice = addPrice + orderItem.getCommoditySum().doubleValue();
                }
            }

            // 如果无父订单,(反之说明为拆分后的子单,不需要查询支付信息)
            if (StringUtils.isBlank(parentOrderCode)) {
                isParentOrder = 1;// 是主订单(父订单)
                // sql查询条件对象
                Map<String, Object> newConditon = new HashMap<String, Object>();
                // 解析并组装查询条件
                newConditon.put("orderCode", orderMain.getOrderCode());// 订单code
                // 支付信息
                orderPayStatementList = accountService
                        .findConsumptionDetailByOrderCode(newConditon);
            }

            // 如果为已拆分,则查询出他的子单号信息
            if (orderMain.getOrderStatus().intValue() ==
                    OrderDictionaryEnum.Order_Status.Split_Done.getKey()) {
                sonOrderMainList = myOrderService
                        .findOrderListByParentCode(orderMain.getOrderCode());
            }
            if (orderMain.getAssessStatus() == 2) {// 已评价
                isOrderAssess = "yes"; // 改动大了 保留
            }

        } catch (Exception e1) {
            logger.error("根据orderMianCode查询订单详情信息时获取订单的订单操作日志出错：" + e1.getMessage(), e1);
            return ERROR;
        }
        return SUCCESS;
    }

    @Resource
    private MemCachedClient memCachedClient;
    private static final Date EXPIRE_DATE = new Date(1000 * 60 * 60 * 24);

    // pv值、返利信息、优惠券信息、加价购金额、根订单的操作流水
    private Map<String, Object> getOrderInfoFormBuffer(String orderNo, String parentOrderCode) {
        String key = "orderInfo_" + orderNo;
        Map<String, Object> cacheMap = (Map<String, Object>) memCachedClient.get(key);
        if (cacheMap == null) {
            cacheMap = Maps.newHashMap();
            // pv
            List<OrderSync> list = myOrderService.selectOrderSyncByOrderCode(orderMainCode);// 订单同步
            // pv
            if (null != list && list.size() > 0) {
                OrderSync orderSync = list.get(0);
                cacheMap.put("pv", orderSync.getOrderPv());
            }

            // 优惠券
            List<CouponAndPromotion> couponList = myOrderService.findCouponByOrderCode(orderNo);
            if (!StringUtil.isEmpty(couponList)) {
                cacheMap.put("couponList", couponList);
            }
            if (!StringUtil.isEmpty(parentOrderCode)) {
                // Map<Long, Double> consumptionRateMap = Maps.newHashMap();
                // OrderMain order;
                List<OrderOperateStatement> rootOrderOperateStateList;
                try {
                    // order = myOrderService.findOrderByOrderCode(parentOrderCode);
                    rootOrderOperateStateList = myOrderService.findOrderById(parentOrderCode);
                } catch (SQLException e) {
                    throw new RuntimeException("根据订单code查询订单实体异常", e);
                }
                // for (OrderItem oi : order.getOrderItemList()) {
                // consumptionRateMap.put(oi.getDefaultProductImage().getSkuId(), oi
                // .getSpreadEffect().getConsumptionAmount());// skuid:
                // // 消费返利
                // }
                // // 消费返利
                // if (!consumptionRateMap.isEmpty()) {
                // cacheMap.put("consumptionRateMap", consumptionRateMap);
                // }
                // 根订单 系统操作流水(子订单的操作流水付款之前查询根订单的流水)
                if (!StringUtil.isEmpty(rootOrderOperateStateList)) {
                    cacheMap.put("rootOrderOperateStateList", rootOrderOperateStateList);
                }
            }
            if (!memCachedClient.set(key, cacheMap, EXPIRE_DATE)) {
                logger.warn("保存订单pv值、返利信息、优惠券信息、加价购金额、根订单的操作流水信息到缓存失败");
            }
        }
        return cacheMap;

    }

    /**
     * ajax查询快递单信息
     *
     * @author luoyi
     * @createDate 2013/10/30
     */
    public String queryExpressOrderInfo() {
        ExpressSubscription expressSubscription = null;
        if (null == orderMain) {
            logger.error("获取订单物流信息出错orderMain为 null");
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
            return SUCCESS;
        }
        String logisticsCode = orderMain.getLogisticsCode();
        String expressNo = orderMain.getLogisticsOrderNo();
        try {
            if (StringUtils.isNotEmpty(logisticsCode) && StringUtils.isNotEmpty(expressNo)) {
                expressSubscription = expressSubscriptionRemoteService
                        .queryOrderExpressInfo(logisticsCode, expressNo);
                if (null != expressSubscription) {
                    List<ExpressTrack> trackList = expressSubscription.getExpressTrackList();
                    if (trackList != null && trackList.size() > 0) {
                        for (ExpressTrack track : trackList) {
                            track.setOper(orderMain.getLogisticsName());
                        }
                        // 重新
                        listorder = myOrderService.findOperateByNo(orderMain.getOrderCode());
                        orderTrailService.mergeOrderAndExpress(listorder, expressSubscription);
                        Collections.reverse(expressSubscription.getExpressTrackList());
                        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功",
                                expressSubscription);
                    } else { // 获取物流跟踪信息不存在
                        returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("获取订单物流信息出错" + ex.getMessage(), ex);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
        }
        return SUCCESS;
    }

    /**
     * ajax检查订单是否过期
     *
     * @author luoyi
     * @createDate 2013/10/30
     */
    public void checkOverdue() {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        String result = "success";
        try {
            // 从我的订单进入的
            String userAccount = (String) request.getSession()
                    .getAttribute(Constants.SESSION_USER_NAME);
            if (isMyOrder != null && isMyOrder.equals("1")) {
                if (StringUtils.isBlank(userAccount)) {
                    result = "noSession";
                } else {
                    orderMain = myOrderService.findOrderByOrderCode(orderMainCode);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(orderMain.getCreateDate());
                    calendar.add(Calendar.HOUR, Constants.ORDER_DISABLED_TIME);

                    // 订单时间
                    Calendar orderTime = Calendar.getInstance();
                    orderTime.setTime(orderMain.getCreateDate());
                    // 支付时间
                    Calendar payTime = Calendar.getInstance();
                    payTime.setTime(new Date());
                    /**
                     * 判断支付期限 自创建日期 24小时过期
                     */
                    if (calendar.getTime().getTime() < new Date().getTime()) {
                        result = "fail";
                        myOrderService.cancelOrderMain(userAccount, orderMain.getOrderCode(),
                                orderMain.getOrderStatus());
                    }
                    // 判断订单生成时间到当前时间是否跨越周二0:00，如果是，则该订单不能支付。
                    if (1 == (orderTime.get(Calendar.DAY_OF_WEEK) - 1) &&
                            2 == (payTime.get(Calendar.DAY_OF_WEEK) - 1) &&
                            (orderMain.getOrderType() == 3 || orderMain.getOrderType() == 4 ||
                                    orderMain.getOrderType() == 5)) {
                        result = "fail";
                        myOrderService.cancelOrderMain(userAccount, orderMain.getOrderCode(),
                                orderMain.getOrderStatus());

                    }

                }
            } else {
                orderMain = myOrderService.findOrderByOrderCode(orderMainCode);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(orderMain.getCreateDate());
                calendar.add(Calendar.HOUR, Constants.ORDER_DISABLED_TIME);
                /**
                 * 判断支付期限 自创建日期 24小时过期
                 */
                if (calendar.getTime().getTime() < new Date().getTime()) {
                    result = "fail";
                    if (StringUtils.isBlank(userAccount)) {
                        userAccount = "游客";
                    }
                    myOrderService.cancelOrderMain(userAccount, orderMain.getOrderCode(),
                            orderMain.getOrderStatus());
                }
            }
        } catch (Exception e) {
            result = "fail";
            logger.info("查询订单付款信息出错" + e.getMessage(), e);
        }
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            logger.error("计算退换货退款信息出错：" + e.getMessage(), e);
        }
    }

    /**
     * 支付定金前检查预售订单是否过期
     *
     * @author Yang
     * @createDate 2016/07/07
     */
    public void checkPresellOverdue() {
        HttpServletResponse response = null;
        String result = "success";
        try {
            response = ServletActionContext.getResponse();
            // 获取预售活动状态
            Integer presellStatus = myOrderService.findPresellStatusByOrderCode(orderMainCode);
            // 从我的订单进入的
            HttpServletRequest request = ServletActionContext.getRequest();
            String userAccount = (String) request.getSession()
                    .getAttribute(Constants.SESSION_USER_NAME);
            orderMain = myOrderService.findOrderByOrderCode(orderMainCode);
            if (StringUtils.isBlank(userAccount)) {
                result = "noSession";
            } else if (presellStatus == 3) {// 判断预售活动是否已终止
                result = "finish";
                if (orderMain.getOrderStatus() == 1) {
                    myOrderService.cancelOrderMain(userAccount, orderMain.getOrderCode(),
                            orderMain.getOrderStatus());
                }
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(orderMain.getCreateDate());
                calendar.add(Calendar.HOUR, Constants.PRESELL_ORDER_DISABLED_TIME);

                // 订单时间
                Calendar orderTime = Calendar.getInstance();
                orderTime.setTime(orderMain.getCreateDate());
                // 支付时间
                Calendar payTime = Calendar.getInstance();
                payTime.setTime(new Date());
                /**
                 * 判断支付期限 自创建日期 1小时过期
                 */
                if (calendar.getTime().getTime() < new Date().getTime()) {
                    result = "fail";
                    if (orderMain.getOrderStatus() == 1) {
                        myOrderService.cancelOrderMain(userAccount, orderMain.getOrderCode(),
                                orderMain.getOrderStatus());
                    }
                }

            }
        } catch (Exception e) {
            result = "fail";
            logger.info("支付定金前检查预售订单出错" + e.getMessage(), e);
        }
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            logger.error("检查预售订单回写json出错：" + e.getMessage(), e);
        }
    }

    /**
     * 检查当前时间是否符合尾款支付时间范围
     *
     * @author Yang
     * @createDate 2016/07/07
     */
    public void checkRetainagePay() {
        HttpServletResponse response = null;
        String result = "success";
        try {
            // 从我的订单进入的
            response = ServletActionContext.getResponse();
            HttpServletRequest request = ServletActionContext.getRequest();
            String userAccount = (String) request.getSession()
                    .getAttribute(Constants.SESSION_USER_NAME);
            if (StringUtils.isBlank(userAccount)) {
                result = "noSession";
            } else {

                orderMain = myOrderService.findOrderByOrderCode(orderMainCode);

                // 获取当前时间与尾款支付开始时间，截止时间的差值
                Map<String, Object> timeMap = myOrderService.findReserveByOrderCode(orderMainCode);
                BigDecimal start = (BigDecimal) timeMap.get("FINALPAYSTARTTIME");
                BigDecimal end = (BigDecimal) timeMap.get("FINALPAYENDTIME");

                // 提前支付尾款
                if (start.longValue() < 0) {
                    // result = "advance";
                    result = "success";

                }
                // 逾期支付尾款
                if (end.longValue() > 0) {
                    result = "overdue";
                    if (orderMain.getOrderStatus() == 23) {
                        myOrderService.cancelOrderMain(userAccount, orderMain.getOrderCode(),
                                orderMain.getOrderStatus());
                    }
                }
            }
        } catch (Exception e) {
            result = "fail";
            logger.info("检查尾款支付时间出错" + e.getMessage(), e);
        }
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            logger.error("检查尾款支付时间回写json出错：" + e.getMessage(), e);
        }
    }

    /**
     * 初始化下单日期查询条件
     */
    private void initCreateDateOptions() {
        // TODO
        createDateOptionsMap = new TreeMap<Integer, String>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        createDateOptionsMap.put(0, "全部时间");
        createDateOptionsMap.put(1, "最近三个月");
        createDateOptionsMap.put(2, "今年内");
        int thisYear = calendar.get(Calendar.YEAR);
        int optionIndex = 3;
        for (int i = thisYear - 1; i >= BEGIN_YEAR; i--) {
            createDateOptionsMap.put(optionIndex, i + "年");
            optionIndex++;
        }
        createDateOptionsMap.put(optionIndex, BEGIN_YEAR + "年以前");
    }

    /**
     * 解析并组装订单日期查询条件
     */
    private void parseOrderCreateDate(int createDateFlag, Map<String, Object> newConditon) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int thisYear = calendar.get(Calendar.YEAR);
        switch (createDateFlag) {
            case 0: // 全部时间
                newConditon.put("createDateBegin", null);
                newConditon.put("createDateEnd", null);
                break;
            case 1: // 最近三个月
                calendar.add(Calendar.MONTH, -3);
                newConditon.put("createDateBegin", calendar.getTime());
                newConditon.put("createDateEnd", null);
                break;
            case 2: // 今年内
                calendar.set(thisYear, 0, 1, 0, 0, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                newConditon.put("createDateBegin", calendar.getTime());
                newConditon.put("createDateEnd", null);
                break;

            default:
                thisYear = calendar.get(Calendar.YEAR);
                int toSubtractValue = createDateFlag - 2;
                if (thisYear - toSubtractValue >= BEGIN_YEAR) {
                    calendar.set(thisYear - toSubtractValue, 0, 1, 0, 0, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    newConditon.put("createDateBegin", calendar.getTime());
                    calendar.set(thisYear + 1 - toSubtractValue, 0, 1, 0, 0, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    newConditon.put("createDateEnd", calendar.getTime());
                } else if (thisYear - toSubtractValue < BEGIN_YEAR) { // BEGIN_YEAR以前
                    calendar.set(BEGIN_YEAR, 0, 1, 0, 0, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    newConditon.put("createDateBegin", null);
                    newConditon.put("createDateEnd", calendar.getTime());
                }
                break;
        }
    }

    /**
     * 解析并组装订单状态查询条件
     *
     * @param orderStatusFlag
     * @param newConditon
     */
    /*
     * private void parseOrderStatus(int orderStatusFlag, Map<String, Object> newConditon) { switch
     * (orderStatusFlag) { case 0: // 全部状态 List statusList = Arrays.asList(
     * OrderDictionaryEnum.Order_Status.Cancel_Done.getKey(),
     * OrderDictionaryEnum.Order_Status.Not_Pay.getKey(),
     * OrderDictionaryEnum.Order_Status.Pay_Done.getKey(),
     * OrderDictionaryEnum.Order_Status.Settle_Done.getKey(),
     * OrderDictionaryEnum.Order_Status.Stock_Done.getKey(),
     * OrderDictionaryEnum.Order_Status.Ship_Done.getKey(),
     * OrderDictionaryEnum.Order_Status.Order_Done.getKey(),
     * OrderDictionaryEnum.Order_Status.Ship_Fail.getKey(),
     * OrderDictionaryEnum.Order_Status.Settle_Not_Stock.getKey(),
     * OrderDictionaryEnum.Order_Status.Splited_Not_Settle .getKey());
     * 
     * newConditon.put("orderStatus", StringUtils.join(statusList, ",")); break; case 1: // 未付款
     * newConditon.put("orderStatus", OrderDictionaryEnum.Order_Status.Not_Pay.getKey()); break;
     * case 2: // 已付款 newConditon.put("orderStatus",
     * OrderDictionaryEnum.Order_Status.Pay_Done.getKey()); break; case 3: // 配货中 statusList =
     * Arrays.asList( OrderDictionaryEnum.Order_Status.Settle_Done.getKey(),
     * OrderDictionaryEnum.Order_Status.Settle_Not_Stock.getKey(),
     * OrderDictionaryEnum.Order_Status.Splited_Not_Settle .getKey(),
     * OrderDictionaryEnum.Order_Status.Stock_Done.getKey()); newConditon.put("orderStatus",
     * StringUtils.join(statusList, ",")); break; case 4: // 已发货 newConditon.put("orderStatus",
     * OrderDictionaryEnum.Order_Status.Ship_Done.getKey()); break; case 5: // 已完成 // statusList =
     * Arrays.asList( // OrderDictionaryEnum.Order_Status.Order_Done.getKey(), //
     * OrderDictionaryEnum.Order_Status.Assess_Done.getKey()); newConditon.put("orderStatus",
     * OrderDictionaryEnum.Order_Status.Order_Done.getKey()); break; // case 6: // 已评价 //
     * newConditon.put("orderStatus", // OrderDictionaryEnum.Order_Status.Assess_Done.getKey()); //
     * break; case 7: // 已取消 newConditon.put("orderStatus",OrderDictionaryEnum.Order_Status.
     * Cancel_Done .getKey()); break; default: break; } }
     */

    /**
     * 订单跟踪 create by lijianjun 此处只查系统操作流水 modify by luoyi
     */

    public String initOrderLogistics() {
        // 订单编号
        /* String orderNo = orderTrailInfo.getOrderNo(); */

        // 订单状态
        try {
            /* orderMain = myOrderService.findOrderByOrderCode(orderNo); */

            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
            // 判断该订单是否为当前用户的订单，如果不是则跳转到error页面 add by lishiming
            if (orderMain.getCustomerId().compareTo(new BigDecimal(userId)) != 0) {
                logger.error(
                        "非当前用户查询该订单详情，currentUserID：" + userId + ",订单id:" + orderMain.getOrderId());
                return ERROR;
            }
            // 系统操作流水
            listorder = myOrderService.findOperateByNo(orderMain.getOrderCode());

            Collections.reverse(listorder);
        } catch (Exception e) {
            logger.error("获取订单系统消息失败" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * ajax查询会员的订单列表wap
     */
    public String ajaxQueryWapOrderList() {
        // 初始化下单日期的查询条件项
        initCreateDateOptions();
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        Pagination page = this.getPagination(5, 10);
        page.setPage(pagenumber);
        page.setStartindex(((pagenumber - 1) * page.getNumperpage()) + 1);
        page.setEndindex(pagenumber * page.getNumperpage());
        // sql查询条件对象
        Map<String, Object> newConditon = new HashMap<String, Object>();
        // 解析并组装查询条件
        newConditon.put("userId", userId);
        // 添加渠道查询条件
        String channel = ConfigurationUtil.getString("CHANNEL");
        newConditon.put("channel", channel);
        // 设置查询条件
        if (0 != selectOrderStatus) {
            OrderStatusUtil.parseWapOrderStatus(selectOrderStatus, newConditon);
        }
        page.setObjCondition(newConditon);

        try {
            this.pagintion = myOrderService.wapFindOrderMainByPage(page);
            List list = pagintion.getRecordList();
            OrderMain orderMain = null;
            int type = 0;
            assessMap = new HashMap();
            for (int i = 0; i < list.size(); i++) {
                orderMain = (OrderMain) list.get(i);
                // r_prodappraise:订单是否完成KEY
                String AssessStauts = jedisCluster
                        .get(ConfigurationUtil.getString("r_prodappraise") +
                                orderMain.getOrderCode());
                if (AssessStauts != null) {
                    assessMap.put(orderMain.getOrderCode(), AssessStauts);
                } else {
                    // 如果评价未完成
                    assessMap.put(orderMain.getOrderCode(), type);
                }
            }
            if (pagenumber > pagintion.getTotalpage()) {
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", null);
            } else {
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", list);
            }
        } catch (SQLException e) {
            logger.error("查询会员的订单列表信息出错：" + e.getMessage(), e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
            return SUCCESS;
        }
        return SUCCESS;
    }

    /**
     * 查询会员的订单列表wap
     */
    public String queryWapOrderList() {
        // 初始化下单日期的查询条件项
        initCreateDateOptions();
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        Pagination page = this.getPagination(5, 10);
        // 页面传入的查询条件
        Map<String, String> objContion = (Map<String, String>) page.getObjCondition();
        // sql查询条件对象
        Map<String, Object> newConditon = new HashMap<String, Object>();
        // 解析并组装查询条件
        newConditon.put("userId", userId);
        // 添加渠道查询条件
        String channel = ConfigurationUtil.getString("CHANNEL");
        newConditon.put("channel", channel);
        // 设置查询条件
        if (objContion != null) {
            if (StringUtils.isNotEmpty(objContion.get("orderStatus"))) {
                // 解析并组装订单状态查询条件
                int orderStatusFlag = Integer.parseInt(objContion.get("orderStatus"));
                OrderStatusUtil.parseWapOrderStatus(orderStatusFlag, newConditon);

            }
            if (KEYWORD_TIPS.equals(objContion.get("keyword")) ||
                    StringUtils.isEmpty(objContion.get("keyword"))) {
                newConditon.put("keyword", "");
            }
        }

        page.setObjCondition(newConditon);

        try {
            this.pagintion = myOrderService.wapFindOrderMainByPage(page);
            List list = pagintion.getRecordList();
            OrderMain orderMain = null;
            int type = 0;
            assessMap = new HashMap();
            for (int i = 0; i < list.size(); i++) {
                orderMain = (OrderMain) list.get(i);
                // r_prodappraise:订单是否完成KEY
                String AssessStauts = jedisCluster
                        .get(ConfigurationUtil.getString("r_prodappraise") +
                                orderMain.getOrderCode());
                if (AssessStauts != null) {
                    assessMap.put(orderMain.getOrderCode(), AssessStauts);
                } else {
                    // 如果评价未完成
                    assessMap.put(orderMain.getOrderCode(), type);
                }
            }
            List<OrderMain> recordList = pagintion.getRecordList();
            List reserveList = new ArrayList();
            for (OrderMain order : recordList) {
                if (order.getOrderType() == 7) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("orederCode", order.getOrderCode());
                    String[] moneys = myOrderService
                            .findDepositTailByOrderCode(order.getOrderCode(), order.getOrderType());
                    map.put("depositSum", moneys[0]);
                    map.put("amountPayable", moneys[1]);
                    if (order.getOrderStatus() == 1 || order.getOrderStatus() == 23) {
                        map.put("time", myOrderService.findReserveByOrderCode(order.getOrderCode(),
                                order.getOrderStatus()));
                    } else {
                        map.put("time", "预售订单有误");
                    }
                    String[] stateStr = myOrderService
                            .findReserveByOrderState(order.getOrderCode());
                    map.put("depositEndTime", stateStr[0]);
                    map.put("finalpayStartTime", stateStr[1]);
                    map.put("finalpayEndTime", stateStr[2]);
                    map.put("deliveryEndTime", stateStr[3]);
                    map.put("finishDate", stateStr[4]);
                    map.put("judgeSalesReturn", stateStr[5]);
                    map.put("judgeCompensate", stateStr[6]);
                    reserveList.add(map);
                }
            }
            request.setAttribute("reserveList", reserveList);
        } catch (SQLException e) {
            logger.error("查询会员的订单列表信息出错：" + e.getMessage(), e);
            return SUCCESS;
        }
        return SUCCESS;
    }

    /**
     * 查询订单的详细信息wap
     */
    public String queryWapOrderDetail() {

        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        try {
            this.orderMain = myOrderService.findOrderByOrderCode(orderMainCode);
        } catch (SQLException e2) {
            logger.error("根据orderMianCode查询订单详情信息出错：" + e2.getMessage(), e2);
            return ERROR;
        }
        orderMain.setLogisticsName(ConfigurationUtil.getString(orderMain.getLogisticsCode()));
        // 判断该订单是否为当前用户的订单，如果不是则跳转到error页面 add by lishiming
        if (orderMain.getCustomerId().compareTo(new BigDecimal(userId)) != 0) {
            logger.error(
                    "非当前用户查询该订单详情，currentUserID：" + userId + ",订单id:" + orderMain.getOrderId());
            return ERROR;
        }
        orderMainId = orderMain.getOrderId().intValue();
        // 看此订单有没有获得优惠券的信息
        couponList = myOrderService.findCouponByOrderCode(orderMain.getOrderCode());
        CouponAndPromotion coupon = null;
        int count = 0;
        for (int i = 0; i < couponList.size(); i++) {
            coupon = couponList.get(i);
            count = myOrderService.findCouponById(coupon.getCouponId().intValue());
            if (coupon.getShopCode() == null && count == 0) {
                coupon.setProductFilterTypeStr("全场可用");
            } else {
                coupon.setProductFilterTypeStr("部分商品可用");
            }
        }

        // 订单编号
        String orderNo = orderMain.getOrderCode();
        // 系统操作流水(子订单的操作流水付款之前查询根订单的流水)
        if (StringUtils.isNotBlank(orderMain.getParentOrderCode())) {
            // 获取订单的根订单号
            String rootOrderCode;
            try {
                rootOrderCode = myOrderService.findRootOrder(orderNo);

                // 获取根订单的操作流水
                rootOrderOperateStateList = myOrderService.findOrderById(rootOrderCode);
            } catch (SQLException e) {
                logger.error("根据orderMianCode查询订单详情信息时获取订单的根订单号或获取根订单的操作流水出错：" + e.getMessage(), e);
            }
        }
        try {
            listorder = myOrderService.findOperateByNo(orderNo);
        } catch (SQLException e1) {
            logger.error("根据orderMianCode查询订单详情信息时获取订单的订单操作日志出错：" + e1.getMessage(), e1);
            return ERROR;
        }
        consumptionRateMap = new HashMap<Long, Double>();
        // 如果为已拆分,则查询出他的子单号信息
        if (orderMain.getOrderStatus().intValue() ==
                OrderDictionaryEnum.Order_Status.Split_Done.getKey()) {
            // for (OrderItem oi : orderMain.getOrderItemList()) {
            // consumptionRateMap.put(oi.getDefaultProductImage().getSkuId(), oi.getSpreadEffect()
            // .getConsumptionAmount());// sku:消费返利
            // }
            sonOrderMainList = myOrderService
                    .wapFindOrderListByParentCode(orderMain.getOrderCode());
        }
        // 如果无父订单,(反之说明为拆分后的子单,不需要查询支付信息)
        if (StringUtils.isBlank(orderMain.getParentOrderCode())) {
            // sql查询条件对象
            Map<String, Object> newConditon = new HashMap<String, Object>();
            // 解析并组装查询条件
            newConditon.put("orderCode", orderMain.getOrderCode());// 订单code
            // 支付信息
            try {
                orderPayStatementList = accountService
                        .findConsumptionDetailByOrderCode(newConditon);
            } catch (SQLException e) {
                logger.error("查询订单支付信息出错：" + e.getMessage(), e);
            }

            List list = orderMain.getOrderItemList();
            OrderItem orderItem = null;
            addPrice = 0.0;
            try {
                for (int i = 0; i < list.size(); i++) {
                    orderItem = (OrderItem) list.get(i);
                    if (orderItem.getCommodityType() != null &&
                            orderItem.getCommodityType() == 5) { // 商品类型为5
                        // 加价购的价格总和
                        addPrice = addPrice + orderItem.getCommoditySum().doubleValue();
                    }
                }
            } catch (Exception e) {
                logger.error("加价购计算:" + e.getMessage(), e);
            }

            isParentOrder = 1;// 是主订单(父订单)
        }
        try {
            BigDecimal countOrder = myOrderService.findIsMainOrder(orderMain.getOrderCode());
            int r = countOrder.compareTo(BigDecimal.ZERO);
            // if(r==0)等于 if(r==1)大于 表示大于0 主订单
            if (r == 1) {
                isMainOrder = 1;
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("depositSum", orderMain.getDepositSum());
            map.put("finalPayment", new BigDecimal(Double.toString(
                    orderMain.getAmountPayable().subtract(orderMain.getDepositSum())
                            .doubleValue())));
            map.put("originalOrderSum", orderMain.getOriginalOrderSum());
            map.put("amountPayable", orderMain.getAmountPayable());
            map.put("payStates",
                    myOrderService.findDepositTailStateByOrderCode(orderMain.getOrderCode()));
            request.setAttribute("moneyStates", map);
            request.setAttribute("informpayTels",
                    myOrderService.findReserveByOrderPhone(orderMain.getOrderCode()));
            Map<String, Object> maps = new HashMap<String, Object>();
            String[] stateStr = myOrderService.findReserveByOrderState(orderMain.getOrderCode());
            maps.put("depositEndTime", stateStr[0]);
            maps.put("finalpayStartTime", stateStr[1]);
            maps.put("finalpayEndTime", stateStr[2]);
            maps.put("deliveryEndTime", stateStr[3]);
            maps.put("finishDate", stateStr[4]);
            maps.put("judgeSalesReturn", stateStr[5]);
            maps.put("judgeCompensate", stateStr[6]);
            maps.put("countDown", myOrderService
                    .findReserveByOrderCode(orderMain.getOrderCode(), orderMain.getOrderStatus()));
            request.setAttribute("allState", maps);
            request.setAttribute("orderAllStates",
                    myOrderService.findStateByOrderCode(orderMain.getOrderCode()));
        } catch (SQLException e1) {
            logger.error("查询是否主订单信息出错:" + e1.getMessage(), e1);
        }
        if (orderMain.getAssessStatus() == 2) {// 已评价
            isOrderAssess = "yes"; // 改动大了 保留
        }
        /*
         * try { if (orderAssessDetailService.orderAssessComplete(orderNo)) { isOrderAssess = "yes";
         * // 查询评论的时间 orderAssessInfo = orderAssessDetailService.findAssessInfoByOrderCode(orderNo);
         * } } catch (Exception e) { logger.error("查询订单查询评论的时间出错：" + e.getMessage(), e);
         * 
         * return ERROR; }
         */
        Collections.reverse(listorder);

        return SUCCESS;
    }

    /**
     * 订单跟踪 create by luoyi 此处只查系统操作流水 modify by wangkai
     */
    public String initWapOrderLogistics() {
        // 订单编号
        /* String orderNo = orderTrailInfo.getOrderNo(); */

        // 订单状态
        try {
            /* orderMain = myOrderService.findOrderByOrderCode(orderNo); */

            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
            // 判断该订单是否为当前用户的订单，如果不是则跳转到error页面 add by lishiming
            if (orderMain.getCustomerId().compareTo(new BigDecimal(userId)) != 0) {
                logger.error(
                        "非当前用户查询该订单详情，currentUserID：" + userId + ",订单id:" + orderMain.getOrderId());
                return ERROR;
            }
            // 系统操作流水
            listorder = myOrderService.findOperateByNo(orderMain.getOrderCode());
            request.setAttribute("orderAllStates",
                    myOrderService.findStateByOrderCode(orderMain.getOrderCode()));
            Collections.reverse(listorder);
        } catch (Exception e) {
            logger.error("获取订单系统消息失败" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 删除订单
     */
    public String deleteWapOrderMain() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        String userAccount = (String) request.getSession()
                .getAttribute(Constants.SESSION_USER_NAME);
        if (userId == null) {
            logger.error("Session过期");
            return ERROR;
        }
        if (null == orderUserid || orderUserid.intValue() != userId.intValue()) {
            logger.error("Session过期,该订单不是当前用户的订单");
            return ERROR;
        }
        try {
            myOrderService.deleteOrderMain(userAccount, this.orderMainCode);
        } catch (Exception e) {
            logger.error("删除订单出错：" + e.getMessage(), e);
            return ERROR;
        }
        time = new Date().getTime();
        return SUCCESS;
    }

    /**
     * 根据订单编码获得对应支付时间
     */
    public String findReserveByOrderCode() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String orderCode = request.getParameter("orderCode");
        Long orderStatus = Long.valueOf(request.getParameter("orderStatus"));
        try {
            orderTime = new HashMap<>();
            orderTime.put("resultTime",
                    myOrderService.findReserveByOrderCode(orderCode, orderStatus));
            Map<String, Object> map = new HashMap<>();
            String[] stateStr = myOrderService.findReserveByOrderState(orderCode);
            map.put("depositEndTime", stateStr[0]);
            map.put("finalpayStartTime", stateStr[1]);
            map.put("finalpayEndTime", stateStr[2]);
            map.put("deliveryEndTime", stateStr[3]);
            map.put("finishDate", stateStr[4]);
            map.put("judgeSalesReturn", stateStr[5]);
            map.put("judgeCompensate", stateStr[6]);
            orderTime.put("stateStr", map);
            String[] moneys = myOrderService.findDepositTailByOrderCode(orderCode, 7);
            orderTime.put("depositSum", moneys[0]);
            orderTime.put("amountPayable", moneys[1]);
        } catch (SQLException e) {
            logger.error("根据订单编码获得对应支付时间出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }


    /**
     * 根据订单号获得尾款支付截止时间
     */
    public String findPayEndTimeByOrderCode() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String orderCode = request.getParameter("orderCode");
        try {
            orderTime = new HashMap<String, Object>();
            orderTime.put("resultTime", myOrderService.findPayEndTimeByOrderCode(orderCode));
        } catch (SQLException e) {
            logger.error("根据订单号获得尾款支付截止时间出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 根据订单编码判断是否可以支付定金
     */
    public String judgeDepositTimeByOrderMoney() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String orderCode = request.getParameter("orderCode");
        try {
            orderTime = new HashMap<String, Object>();
            orderTime.put("resultTime", myOrderService.judgeDepositTimeByOrderMoney(orderCode));
        } catch (SQLException e) {
            logger.error("根据订单编码判断是否可以支付定金：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    public OrderTrailInfo getOrderTrailInfo() {
        return orderTrailInfo;
    }

    public void setOrderTrailInfo(OrderTrailInfo orderTrailInfo) {
        this.orderTrailInfo = orderTrailInfo;
    }

    public Map<Integer, String> getCreateDateOptionsMap() {
        return createDateOptionsMap;
    }

    public void setCreateDateOptionsMap(Map<Integer, String> createDateOptionsMap) {
        this.createDateOptionsMap = createDateOptionsMap;
    }

    public Integer getOrderMainId() {
        return orderMainId;
    }

    public void setOrderMainId(Integer orderMainId) {
        this.orderMainId = orderMainId;
    }

    public OrderMain getOrderMain() {
        return orderMain;
    }

    public void setOrderMain(OrderMain orderMain) {
        this.orderMain = orderMain;
    }

    public String getOrderMainCode() {
        return orderMainCode;
    }

    public void setOrderMainCode(String orderMainCode) {
        this.orderMainCode = orderMainCode;
    }

    public Long getOrderMainStatus() {
        return orderMainStatus;
    }

    public void setOrderMainStatus(Long orderMainStatus) {
        this.orderMainStatus = orderMainStatus;
    }

    public String getProductImgServerUrl() {
        return ConfigurationUtil.getString("PRODUCT_IMG_PATH");
    }

    public String getIsSureOk() {
        return isSureOk;
    }

    public void setIsSureOk(String isSureOk) {
        this.isSureOk = isSureOk;
    }

    public String getCmsPagePath() {
        return ConfigurationUtil.getString("CMS_PAGE_PATH");
    }

    public List<OrderOperateStatement> getListorder() {
        return listorder;
    }

    public void setListorder(List<OrderOperateStatement> listorder) {
        this.listorder = listorder;
    }

    public ReturnResult getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult returnResult) {
        this.returnResult = returnResult;
    }

    public String getIsOrderAssess() {
        return isOrderAssess;
    }

    public void setIsOrderAssess(String isOrderAssess) {
        this.isOrderAssess = isOrderAssess;
    }

    public List<OrderMain> getSonOrderMainList() {
        return sonOrderMainList;
    }

    public void setSonOrderMainList(List<OrderMain> sonOrderMainList) {
        this.sonOrderMainList = sonOrderMainList;
    }

    public List<OrderPayStatement> getOrderPayStatementList() {
        return orderPayStatementList;
    }

    public void setOrderPayStatementList(List<OrderPayStatement> orderPayStatementList) {
        this.orderPayStatementList = orderPayStatementList;
    }

    public int getIsParentOrder() {
        return isParentOrder;
    }

    public void setIsParentOrder(int isParentOrder) {
        this.isParentOrder = isParentOrder;
    }

    public List<CouponAndPromotion> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponAndPromotion> couponList) {
        this.couponList = couponList;
    }

    public List<OrderOperateStatement> getRootOrderOperateStateList() {
        return rootOrderOperateStateList;
    }

    public void setRootOrderOperateStateList(
            List<OrderOperateStatement> rootOrderOperateStateList) {
        this.rootOrderOperateStateList = rootOrderOperateStateList;
    }

    public OrderAssessInfo getOrderAssessInfo() {
        return orderAssessInfo;
    }

    public void setOrderAssessInfo(OrderAssessInfo orderAssessInfo) {
        this.orderAssessInfo = orderAssessInfo;
    }

    public int getBackFlag() {
        return backFlag;
    }

    public void setBackFlag(int backFlag) {
        this.backFlag = backFlag;
    }

    public int getIsOrderList() {
        return isOrderList;
    }

    public void setIsOrderList(int isOrderList) {
        this.isOrderList = isOrderList;
    }

    public int getPagenumber() {
        return pagenumber;
    }

    public void setPagenumber(int pagenumber) {
        this.pagenumber = pagenumber;
    }

    public String getIsMyOrder() {
        return isMyOrder;
    }

    public void setIsMyOrder(String isMyOrder) {
        this.isMyOrder = isMyOrder;
    }

    public int getDetailUserId() {
        return detailUserId;
    }

    public void setDetailUserId(int detailUserId) {
        this.detailUserId = detailUserId;
    }

    public Map getAssessMap() {
        return assessMap;
    }

    public void setAssessMap(Map assessMap) {
        this.assessMap = assessMap;
    }

    public Integer getOrderUserid() {
        return orderUserid;
    }

    public void setOrderUserid(Integer orderUserid) {
        this.orderUserid = orderUserid;
    }

    public double getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(double addPrice) {
        this.addPrice = addPrice;
    }

    public String getApplyContent() {
        return applyContent;
    }

    public void setApplyContent(String applyContent) {
        this.applyContent = applyContent;
    }

    public String getProductPicPathWAP() {
        return ConfigurationUtil.getString("productPicPath_WAP");
    }

    public int getSelectOrderStatus() {
        return selectOrderStatus;
    }

    public void setSelectOrderStatus(int selectOrderStatus) {
        this.selectOrderStatus = selectOrderStatus;
    }

    public int getIsMainOrder() {
        return isMainOrder;
    }

    public void setIsMainOrder(int isMainOrder) {
        this.isMainOrder = isMainOrder;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Map getConsumptionRateMap() {
        return consumptionRateMap;
    }

    public void setConsumptionRateMap(Map consumptionRateMap) {
        this.consumptionRateMap = consumptionRateMap;
    }

    public Map<String, Object> getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Map<String, Object> orderTime) {
        this.orderTime = orderTime;
    }


}
