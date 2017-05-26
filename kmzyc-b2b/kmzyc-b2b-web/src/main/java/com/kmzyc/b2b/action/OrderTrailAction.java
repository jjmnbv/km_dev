package com.kmzyc.b2b.action;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.CouponAndPromotion;
import com.kmzyc.b2b.model.OrderAssessInfo;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.OrderOperateStatement;
import com.kmzyc.b2b.model.OrderPayStatement;
import com.kmzyc.b2b.service.MessageRemoteService;
import com.kmzyc.b2b.service.OrderAssessDetailService;
import com.kmzyc.b2b.service.OrderTrailEmailService;
import com.kmzyc.b2b.service.member.AccountService;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.b2b.vo.OrderTrailInfo;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.util.OrderDictionaryEnum;

@Controller("orderTrailAction")
@Scope("prototype")
public class OrderTrailAction extends BaseAction {

    private static final long serialVersionUID = -5026048035328246659L;

    // private static Logger logger = Logger.getLogger(OrderTrailAction.class);
    private static Logger logger = LoggerFactory.getLogger(OrderTrailAction.class);
    @Resource(name = "messageRemoteService")
    private MessageRemoteService messageRemoteService;

    @Resource(name = "orderTrailEmailService")
    private OrderTrailEmailService orderTrailEmailService;
    @Resource(name = "myOrderServiceImpl")
    private MyOrderService myOrderService;
    @Resource(name = "orderAssessDetailService")
    private OrderAssessDetailService orderAssessDetailService;
    @Resource(name = "accountServiceImpl")
    private AccountService accountService;

    private OrderMain orderMain;
    private List<OrderOperateStatement> listorder;

    private OrderTrailInfo orderTrailInfo;
    private static final int BEGIN_YEAR = 2012;
    // 订单id
    private Integer orderMainId;
    // 订单的子单
    private List<OrderMain> sonOrderMainList;
    private String message;
    private int isParentOrder = 0;// 是否为父订单0＝否,1＝是
    // 满额送券
    private List<CouponAndPromotion> couponList;

    // 标示订单是否打分
    private String isOrderAssess;
    // 评论信息
    private OrderAssessInfo orderAssessInfo;

    List<OrderPayStatement> orderPayStatementList;

    // private String productImgServerUrl;

    // private String cmsPagePath;

    /****** 订单列表页使用的变量 **********/
    private static final String KEYWORD_TIPS = "商品名称、商品编号、订单编号";
    // 订单的下单日期查询条件，需动态组装
    private Map<Integer, String> createDateOptionsMap;

    private String backFlag;
    // 加价
    private double addPrice;

    public double getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(double addPrice) {
        this.addPrice = addPrice;
    }

    private List<OrderOperateStatement> rootOrderOperateStateList;

    public String showExpressPath() {
        try {
            this.orderMain = myOrderService.findOrderMainById(orderMainId);
            // 订单编号
            String orderNo = orderMain.getOrderCode();
            // 系统操作流水
            listorder = myOrderService.findOrderById(orderNo);
            Collections.reverse(listorder);
        } catch (SQLException e) {
            logger.error("查询系统操作日志出错" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 格式化数字显示
     * 
     * @param
     * @return
     */
    public String formatDouble(double s) {
        DecimalFormat fmt = new DecimalFormat("0.00");
        return fmt.format(s);
    }

    /**
     * 查询订单的详细信息
     * 
     * @return
     */
    public String queryTrailOrderDetail() {
        try {
            this.orderMain = myOrderService.findOrderMainById(orderMainId);
            // 订单编号
            // this.orderMain =
            // myOrderService.findOrderByOrderCode(orderMainCode);
            orderMainId = orderMain.getOrderId().intValue();
            String orderNo = orderMain.getOrderCode();
            // 系统操作流水
            if (StringUtils.isNotBlank(orderMain.getParentOrderCode())) {
                // 获取订单的根订单号
                String rootOrderCode = myOrderService.findRootOrder(orderNo);
                // 获取根订单的操作流水
                rootOrderOperateStateList = myOrderService.findOrderById(rootOrderCode);
            }
            listorder = myOrderService.findOrderById(orderNo);
            // 看此订单有没有获得优惠券的信息
            couponList = myOrderService.findCouponByOrderCode(orderNo);
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

            // 如果无父订单,则查询支付信息(反之说明为拆分后的子单,不需要查询支付信息)
            if (StringUtils.isBlank(orderMain.getParentOrderCode())) {
                // sql查询条件对象
                Map<String, Object> newConditon = new HashMap<String, Object>();
                // 解析并组装查询条件
                // newConditon.put("userId", user.getLoginId());
                newConditon.put("orderCode", orderMain.getOrderCode());// 订单code
                // 支付信息
                orderPayStatementList =
                        accountService.findConsumptionDetailByOrderCode(newConditon);
                isParentOrder = 1;// 是主订单(父订单)

                List list = orderMain.getOrderItemList();
                OrderItem orderItem = null;
                addPrice = 0.0;
                try {
                    for (int i = 0; i < list.size(); i++) {
                        orderItem = (OrderItem) list.get(i);
                        if (orderItem.getCommodityType() != null
                                && orderItem.getCommodityType() == 5) { // 商品类型为5
                            // 加价购的价格总和
                            addPrice = addPrice + orderItem.getCommoditySum().doubleValue();
                        }
                    }
                } catch (Exception e) {
                    logger.error("加价购计算:" + e.getMessage(), e);
                }
            }

            // 如果为已拆分,则查询出他的子单号信息
            if (orderMain.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Split_Done
                    .getKey()) {
                sonOrderMainList =
                        myOrderService.findOrderListByParentCode(orderMain.getOrderCode());
            }

            if (orderAssessDetailService.orderAssessComplete(orderNo)) {
                isOrderAssess = "yes";
                // 查询评论信息
                orderAssessInfo = orderAssessDetailService.findAssessInfoByOrderCode(orderNo);
            }
            Collections.reverse(listorder);
            if (orderTrailInfo == null) {
                orderTrailInfo = new OrderTrailInfo();
                orderTrailInfo.setEmailAddress(orderMain.getEmail());
                orderTrailInfo.setMobileNumber(orderMain.getOrderPurchaserMobile());
            }
        } catch (SQLException e) {
            logger.error("查询订单SQL语句出错:" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("查询订单详细信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 显示订单跟踪
     * 
     * @return
     * @throws ServiceException
     */
    public String showOrderTrail() {
        return SUCCESS;
    }

    /*
     * /** 短信验证,发送短信 (订单跟踪\免注册转会员\重置密码\)
     * 
     * @return
     *//*
        * public String sendOrderTrailMobileVerifyCode() { HttpSession session = this.getSession();
        * // HttpServletRequest request = ServletActionContext.getRequest(); // User user = (User)
        * request.getSession().getAttribute( // Constants.SESSION_USER_KEY); boolean rs =
        * messageRemoteService.sendMobileVerifyCode(orderTrailInfo.getMobileNumber(), session,
        * MessageTypeEnum.ORDERTRAIL.getStatus()); AjaxUtil.writeJSONToResponse(rs); return NONE; }
        */

    /**
     * 订单跟踪
     * 
     * @return
     * @throws Exception
     */
    public String postOrderTrailStatus() {
        try {
            // backFlag,控制订单详细页面，是查询订单状态就退出到订单跟踪页面，查询历史订单就返回到订单列表
            backFlag = "1";
            String orderNos = orderTrailInfo.getOrderNo();
            // Integer orderNoInt = Integer.parseInt(orderNos);//从页面上传进来的订单号
            String mobile = orderTrailInfo.getMobileNumber();// 从页面上传进来的手机号码
            orderMain = orderTrailEmailService.findOrderMainByOrderNo(orderNos, mobile);// 根据订单号和手机号码进行查询

            // 如果无父订单,则查询支付信息(反之说明为拆分后的子单,不需要查询支付信息)
            if ( StringUtils.isBlank(orderMain.getParentOrderCode())) {
                // sql查询条件对象
                Map<String, Object> newConditon = new HashMap<String, Object>();
                // 解析并组装查询条件
                newConditon.put("orderCode", orderMain.getOrderCode());// 订单code
                // 支付信息
                orderPayStatementList =
                        accountService.findConsumptionDetailByOrderCode(newConditon);
                List list = orderMain.getOrderItemList();
                OrderItem orderItem = null;
                addPrice = 0.0;
                for (int i = 0; i < list.size(); i++) {
                    orderItem = (OrderItem) list.get(i);
                    if (orderItem.getCommodityType() == 5) { // 商品类型为5的价格总和
                        addPrice = addPrice + orderItem.getCommoditySum().doubleValue();
                    }
                }
                isParentOrder = 1;// 是主订单(父订单)

            }
            // 如果为已拆分,则查询出他的子单号信息
            if (orderMain.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Split_Done
                    .getKey()) {
                sonOrderMainList =
                        myOrderService.findOrderListByParentCode(orderMain.getOrderCode());
            }
            // 订单编号
            String orderNo = orderMain.getOrderCode();
            if (orderAssessDetailService.orderAssessComplete(orderNo)) {
                isOrderAssess = "yes";
                // 查询评论信息
                orderAssessInfo = orderAssessDetailService.findAssessInfoByOrderCode(orderNo);
            }
            // 系统操作流水
            listorder = myOrderService.findOrderById(orderNo);
            // boolean rs = orderTrailService.verifyMobileCode(orderTrailInfo,
            // session);//验证手机验证码是否输入正确
            orderMainId = orderMain.getOrderId().intValue();
            // 看此订单有没有获得优惠券的信息
            couponList = myOrderService.findCouponByOrderCode(orderNo);
            CouponAndPromotion coupon;
            int count;
            for (int i = 0; i < couponList.size(); i++) {
                coupon = couponList.get(i);
                count = myOrderService.findCouponById(coupon.getCouponId().intValue());
                if (coupon.getShopCode() == null && count == 0) {
                    coupon.setProductFilterTypeStr("全场可用");
                } else {
                    coupon.setProductFilterTypeStr("部分商品可用");
                }
            }
            Collections.reverse(listorder);
        } catch (SQLException e) {
            logger.error("订单跟踪信息查询SQL语句出错:" + e.getMessage(), e);
            return ERROR;
        } catch (IOException e) {
            logger.error("订单跟踪流出错：" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("订单跟踪信息查询出错:" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 根据手机、订单号查看订单明细
     * 
     * @return
     * @throws Exception
     */
    public String getOrderDetail() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            logger.info("==根据手机、订单号查看订单明细开始==" + df.format(new Date()));
            System.out.println("==根据手机、订单号查看订单明细开始==" + df.format(new Date()));
            String orderNos = orderTrailInfo.getOrderNo();
            String mobile = orderTrailInfo.getMobileNumber();
            orderMain = orderTrailEmailService.findOrderMainByOrderNo(orderNos, mobile);
            if (orderMain == null) {
                this.setMessage("2");
                getResponse().getWriter().print(this.getMessage());
                return null;
            } else {
                // 如果无父订单,则查询支付信息(反之说明为拆分后的子单,不需要查询支付信息)
                if (StringUtils.isBlank(orderMain.getParentOrderCode())) {
                    // sql查询条件对象
                    Map<String, Object> newConditon = new HashMap<String, Object>();
                    // 解析并组装查询条件
                    newConditon.put("orderCode", orderMain.getOrderCode());// 订单code
                    // 支付信息
                    orderPayStatementList =
                            accountService.findConsumptionDetailByOrderCode(newConditon);
                    isParentOrder = 1;// 是主订单(父订单)
                }
                // 如果为已拆分,则查询出他的子单号信息
                if (orderMain.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Split_Done
                        .getKey()) {
                    sonOrderMainList =
                            myOrderService.findOrderListByParentCode(orderMain.getOrderCode());
                }
                // 订单编号
                String orderNo = orderMain.getOrderCode();
                if (orderAssessDetailService.orderAssessComplete(orderNo)) {
                    isOrderAssess = "yes";
                    // 查询评论信息
                    orderAssessInfo = orderAssessDetailService.findAssessInfoByOrderCode(orderNo);
                }
                listorder = myOrderService.findOrderById(orderNo);
                orderMainId = orderMain.getOrderId().intValue();
                Collections.reverse(listorder);
                logger.info("==根据手机、订单号查看订单明细开始==" + df.format(new Date()));
                System.out.println("==根据手机、订单号查看订单明细开始==" + df.format(new Date()));
            }
        } catch (SQLException e) {
            logger.error("查询订单详细信息SQL语句出错：" + e.getMessage(), e);
            return ERROR;
        } catch (IOException e) {
            logger.error("查询订单详细信息流出错：" + e.getMessage(), e);
            return ERROR;
        } catch (Exception e) {
            logger.error("查询订单详细信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 根据订单号和手机号码进行查询判断是否有数据存在
     * 
     * @return
     */
    public String orderIdorMobile() {
        try {
            // 根据订单号和手机号码进行查询
            Integer orderMainCount =
                    orderTrailEmailService.findOrderMainByOrderNoOrMobile(
                            orderTrailInfo.getOrderNo(), orderTrailInfo.getMobileNumber());
            if (orderMainCount == null || orderMainCount == 0) {// 没有查询到记录
                this.setMessage("2");
                getResponse().getWriter().print(this.getMessage());
            } else {
                this.setMessage("1");
                getResponse().getWriter().print(this.getMessage());
            }
        } catch (Exception e) {
            logger.error("根据订单号和手机号出错" + e.getMessage(), e);
            return ERROR;
        }
        return null;
    }

    /**
     * 判断短信验证码是否正确
     * 
     * @return
     */
    public String mobileCode() {
        HttpSession session = this.getSession();
        try {// 短信码输入正确
            boolean rs =
                    messageRemoteService.verifyMobileCode(orderTrailInfo.getMobileVerifyCode(),
                            session);// 验证手机验证码是否输入正确
            if (rs) {
                this.setMessage("2");
                getResponse().getWriter().print(this.getMessage());
            } else {// 短信验证码错误
                this.setMessage("1");
                getResponse().getWriter().print(this.getMessage());
            }
        } catch (IOException e) {
            logger.error("判断手机短信验证码出错" + e.getMessage(), e);
            return ERROR;
        }
        return null;
    }

    /**
     * 比较手机验证码
     * 
     * @return
     * @throws ServiceException
     */
    public String postQueryOrder() {
        HttpSession session = this.getSession();
        if (orderTrailInfo.getBackFlag() != null) {
            return queryTrailOrderList();
        }
        boolean rs =
                messageRemoteService
                        .verifyMobileCode(orderTrailInfo.getMobileVerifyCode(), session);
        if (rs) {
            this.setMessage("0");
        } else {// 短信验证码输入不正确
            this.setMessage("1");
            try {
                getResponse().getWriter().print(this.getMessage());
            } catch (IOException e) {
                logger.error("返回短信验证码错误：" + e.getMessage(), e);
                return ERROR;
            }
        }
        return null;
    }

    /**
     * 查询会员的订单列表
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public String queryTrailOrderList() {
        backFlag = "2";
        // 初始化下单日期的查询条件项
        initCreateDateOptions();

        Pagination page = this.getPagination(5, 10);
        // 页面传入的查询条件
        Map<String, String> objContion = (Map<String, String>) page.getObjCondition();
        // sql查询条件对象
        Map<String, Object> newConditon = new HashMap<String, Object>();

        newConditon.put("email", orderTrailInfo.getEmailAddress());
        newConditon.put("customerMobile", orderTrailInfo.getMobileNumber());
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
                // 解析并组装订单状态查询条件
                parseOrderStatus(orderStatusFlag, newConditon);
            }
            if (KEYWORD_TIPS.equals(objContion.get("keyword"))) {
                newConditon.put("keyword", "");
            } else {
                newConditon.put("keyword", objContion.get("keyword"));
            }
        } else { // 如果没有查询条件，则也需过滤掉订单状态为"已拆分"、"已拆分未结转"等的订单
            // 解析并组装订单状态查询条件
            parseOrderStatus(0, newConditon);
        }
        // 设置查询条件
        page.setObjCondition(newConditon);
        try {
            this.pagintion = orderTrailEmailService.findOrderMainByPage(page);
        } catch (SQLException e) {
            logger.error("查询会员的订单列表信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 根据邮箱和手机号查询
     * 
     * @return
     * @throws IOException
     */
    public String orderTrailEmailServicess() {
        String info;
        try {
            Integer ordermain =
                    orderTrailEmailService.findByorderEmailorMobile(
                            orderTrailInfo.getEmailAddress(), orderTrailInfo.getMobileNumber());
            if (ordermain == 0) {// 没有查询到记录
                info = "2";
                getResponse().getWriter().print(info);
            } else {
                info = "1";// 查询到记录了
                getResponse().getWriter().print(info);
            }
        } catch (SQLException e) {
            logger.error("根据手机和邮箱查询出错：" + e.getMessage(), e);
        } catch (IOException e) {
            logger.error("返回流错误：" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 初始化下单日期查询条件
     */
    private void initCreateDateOptions() {
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
     * 
     * @param createDateFlag
     * @param newConditon
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
    private void parseOrderStatus(int orderStatusFlag, Map<String, Object> newConditon) {
        switch (orderStatusFlag) {
            case 0: // 全部状态
                List statusList =
                        Arrays.asList(OrderDictionaryEnum.Order_Status.Cancel_Done.getKey(),
                                OrderDictionaryEnum.Order_Status.Not_Pay.getKey(),
                                OrderDictionaryEnum.Order_Status.Pay_Done.getKey(),
                                OrderDictionaryEnum.Order_Status.Settle_Done.getKey(),
                                OrderDictionaryEnum.Order_Status.Stock_Done.getKey(),
                                OrderDictionaryEnum.Order_Status.Ship_Done.getKey(),
                                OrderDictionaryEnum.Order_Status.Order_Done.getKey(),
                                OrderDictionaryEnum.Order_Status.Settle_Not_Stock.getKey(),
                                OrderDictionaryEnum.Order_Status.Splited_Not_Settle.getKey());
                newConditon.put("orderStatus", StringUtils.join(statusList, ","));
                break;
            case 1: // 未付款
                newConditon.put("orderStatus", OrderDictionaryEnum.Order_Status.Not_Pay.getKey());
                break;
            case 2: // 已付款
                newConditon.put("orderStatus", OrderDictionaryEnum.Order_Status.Pay_Done.getKey());
                break;
            case 3: // 配货中
                statusList =
                        Arrays.asList(OrderDictionaryEnum.Order_Status.Settle_Done.getKey(),
                                OrderDictionaryEnum.Order_Status.Settle_Not_Stock.getKey(),
                                OrderDictionaryEnum.Order_Status.Splited_Not_Settle.getKey(),
                                OrderDictionaryEnum.Order_Status.Stock_Done.getKey());
                newConditon.put("orderStatus", StringUtils.join(statusList, ","));
                break;
            case 4: // 已发货
                newConditon.put("orderStatus", OrderDictionaryEnum.Order_Status.Ship_Done.getKey());
                break;
            case 5: // 已完成
                // statusList = Arrays.asList(
                // OrderDictionaryEnum.Order_Status.Order_Done.getKey(),
                // OrderDictionaryEnum.Order_Status.Assess_Done.getKey());
                newConditon
                        .put("orderStatus", OrderDictionaryEnum.Order_Status.Order_Done.getKey());
                break;
            // case 6: // 已评价
            // newConditon.put("orderStatus",
            // OrderDictionaryEnum.Order_Status.Assess_Done.getKey());
            // break;
            case 7: // 已取消
                newConditon.put("orderStatus",
                        OrderDictionaryEnum.Order_Status.Cancel_Done.getKey());
                break;
            default:
                break;
        }
    }

    public OrderTrailInfo getOrderTrailInfo() {
        return orderTrailInfo;
    }

    public void setOrderTrailInfo(OrderTrailInfo orderTrailInfo) {
        this.orderTrailInfo = orderTrailInfo;
    }

    public MessageRemoteService getMessageRemoteService() {
        return messageRemoteService;
    }

    public void setOrderTrailService(MessageRemoteService messageRemoteService) {
        this.messageRemoteService = messageRemoteService;
    }

    public OrderMain getOrderMain() {
        return orderMain;
    }

    public void setOrderMain(OrderMain orderMain) {
        this.orderMain = orderMain;
    }

    public String getProductImgServerUrl() {
        return ConfigurationUtil.getString("PRODUCT_IMG_PATH");
    }

    public Map<Integer, String> getCreateDateOptionsMap() {
        return createDateOptionsMap;
    }

    public void setCreateDateOptionsMap(Map<Integer, String> createDateOptionsMap) {
        this.createDateOptionsMap = createDateOptionsMap;
    }

    public static String getKeywordTips() {
        return KEYWORD_TIPS;
    }

    public OrderTrailEmailService getOrderTrailEmailService() {
        return orderTrailEmailService;
    }

    public void setOrderTrailEmailService(OrderTrailEmailService orderTrailEmailService) {
        this.orderTrailEmailService = orderTrailEmailService;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<OrderOperateStatement> getListorder() {
        return listorder;
    }

    public void setListorder(List<OrderOperateStatement> listorder) {
        this.listorder = listorder;
    }

    public Integer getOrderMainId() {
        return orderMainId;
    }

    public void setOrderMainId(Integer orderMainId) {
        this.orderMainId = orderMainId;
    }

    public List<OrderMain> getSonOrderMainList() {
        return sonOrderMainList;
    }

    public void setSonOrderMainList(List<OrderMain> sonOrderMainList) {
        this.sonOrderMainList = sonOrderMainList;
    }

    public String getIsOrderAssess() {
        return isOrderAssess;
    }

    public void setIsOrderAssess(String isOrderAssess) {
        this.isOrderAssess = isOrderAssess;
    }

    public String getCmsPagePath() {
        return ConfigurationUtil.getString("CMS_PAGE_PATH");
    }

    public List<OrderPayStatement> getOrderPayStatementList() {
        return orderPayStatementList;
    }

    public void setOrderPayStatementList(List<OrderPayStatement> orderPayStatementList) {
        this.orderPayStatementList = orderPayStatementList;
    }

    public String getBackFlag() {
        return backFlag;
    }

    public void setBackFlag(String backFlag) {
        this.backFlag = backFlag;
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

    public void setRootOrderOperateStateList(List<OrderOperateStatement> rootOrderOperateStateList) {
        this.rootOrderOperateStateList = rootOrderOperateStateList;
    }

    public OrderAssessInfo getOrderAssessInfo() {
        return orderAssessInfo;
    }

    public void setOrderAssessInfo(OrderAssessInfo orderAssessInfo) {
        this.orderAssessInfo = orderAssessInfo;
    }

}
