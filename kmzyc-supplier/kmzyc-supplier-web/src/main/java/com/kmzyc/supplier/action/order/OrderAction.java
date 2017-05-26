package com.kmzyc.supplier.action.order;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.common.collect.Lists;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.maps.OrderSyncFlagMap;
import com.kmzyc.supplier.maps.OrderTypeMap;
import com.kmzyc.supplier.model.OrderItems;
import com.kmzyc.supplier.model.OrderMain;
import com.kmzyc.supplier.service.OrderService;
import com.kmzyc.supplier.vo.ReturnResult;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.entities.Invoice;
import com.pltfm.app.entities.OrderAssessDetail;
import com.pltfm.app.entities.OrderAssessInfo;
import com.pltfm.app.entities.OrderCarry;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderSyncDetail;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.OrderDictionaryEnum.Order_Status;
import com.pltfm.app.vobject.InvoiceVo;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;
import com.pltfm.app.vobject.OrderMainVo;
import com.pltfm.app.vobject.OrderOperateStatementVo;
import com.pltfm.app.vobject.OrderPayStatementVo;
import com.pltfm.app.vobject.OrderPreferentialEVO;

@Controller("orderAction")
@Scope(value = "prototype")
public class OrderAction extends SupplierBaseAction {

    private static Logger logger = LoggerFactory.getLogger(OrderAction.class);

    @Resource
    private OrderService orderService;

    @Resource(name = "logisticsMap")
    private Map<String,String> logisticsMap;

    private Order_Status[] orderStatusMap;

    private String orderCode;

    private String orderRemark;

    private List<OrderCarry> orderCarryList;

    private List<String> orderMainCodes;

    private String loginAccount;

    private Map<String, String> infoMap;

    private OrderMainVo order;// 订单基本信息

    // 查询条件
    private String orderCodeForSearch; // 订单号

    private String orderAccount; // 下单账号

    private String consignee; // 收货人

    private String consigneePhone; // 收货人电话

    private String orderBeginDate; // 下单开始时间

    private String orderEndDate; // 下单结束时间

    private String payBeginDate; // 支付开始时间

    private String payEndDate; // 支付结束时间

    private String productCode; // 商品编号

    private String productName; // 商品名称

    private String orderStatus; // 订单状态

    private String channel; // 渠道

    private String nstatus; // 非XX状态

    private String orderStatusForMenuQuery; //分开菜单查询

    // 该值就代表具体的状态字符串
    private String titleValue; //菜单面包屑处标题内容

    // 查询条件（康美中药城）
    private String orderSyncCodeForSearch; // 订单号

    private String orderSyncAccount; // 下单账号

    private String outSyncCode; // 时代编号

    private String syncFlag; // 同步状态

    //查询订单详情
    private BigDecimal payable;// 应付

    private BigDecimal discount;// 优惠

    private BigDecimal fullDdiscount;// 满减

    private BigDecimal plusDiscount;// 加价购

    private BigDecimal actualpay;// 实付

    private BigDecimal orderpay;// 已付

    private BigDecimal couponpay;// 券付

    private BigDecimal bankpay;// 卡付

    private BigDecimal platformpay;// 第三方付

    private BigDecimal balancepay;// 余额付

    private BigDecimal onlinepay;// 在线付

    private BigDecimal notpay;// 未付

    private List<OrderItems> items;// 订单商品 将该实体新增优惠信息属性

    private List<OrderOperateStatementVo> operates;// 订单操作流水

    private List<OrderPayStatementVo> pays;// 订单支付流水

    private List<OrderPreferentialEVO> preferentials;// 订单优惠明细

    private Invoice invoice;// 发票基本信息

    private List<InvoiceVo> invoiceItems;// 订单发票明细

    private Boolean isAdditional;// 是否需要补单

    private Boolean isExt;// 是否需要审核

    private String cmsPagePath = ConfigurationUtil.getString("CMS_PRODUCT_PATH");

    private String logisticsCompany; // 物流公司

    private String logisticsNo; // 物流单号

    private String initStatus; // 订单列表初始状态

    private String assess; // 订单评价状态

    private String isUpdateExpressInfoAgain; //是否再次修改物流信息

    private OrderAssessInfo orderAssessInfo; // 订单评价信息

    private List<OrderAssessDetail> oadlist; // 订单评价详情


    private Map<String, Object> conditionMap = new HashMap<String, Object>();

    private String queryTypeForBuyer; // 买家信息查询类型,customerAccount 按买家账号

    // consigneeMobile 收货人电话 consigneeName收货人姓名
    private String queryBuyerValue; // 查询的值对应的value

    private String finishBeginDate;

    private String finishEndDate;

    private String queryTypeForTime; // 按时间类型查询,是按下单时间还是完成时间做查询

    private ExpressSubscription expressSubscription;// 物流明细

    private static final String RISK_PASS = "22"; // 风控通过

    private static final String STOCK_LOCK = "20"; // 已锁库存

    private BigDecimal finalPay;// 商品尾款

    private BigDecimal wasPayed;// 已付金额

    private static final long shouldPayStatus = 23L;

    /**
     * 所有的订单列表(改版后未启用)
     *
     * @return
     */
    public String showAllOrderList() {
        try {
            pagintion = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
            if (StringUtils.isNotEmpty(nstatus) && nstatus.indexOf(',') > 0) {
                List nsList = Lists.newArrayList(StringUtils.split(nstatus, ","));
                conditionMap.put("nstatus", nsList);
                conditionMap.put("byStatus", true);
            }
            if (StringUtils.isNotEmpty(initStatus)) {
                conditionMap.put("byStatus", true);
                conditionMap.put("status", initStatus);
            }
            if (StringUtils.isNotEmpty(orderCodeForSearch))
                conditionMap.put("orderCode", orderCodeForSearch);
            if (StringUtils.isNotEmpty(orderStatus))
                conditionMap.put("status", orderStatus);
            if (StringUtils.isNotEmpty(channel))
                conditionMap.put("channel", channel);
            if (StringUtils.isNotEmpty(orderAccount))
                conditionMap.put("customerAccount", orderAccount);
            if (StringUtils.isNotEmpty(consignee))
                conditionMap.put("consigneeName", consignee);
            if (StringUtils.isNotEmpty(consigneePhone))
                conditionMap.put("consigneeMobile", consigneePhone);
            if (StringUtils.isNotEmpty(orderBeginDate))
                conditionMap.put("createDateStart", orderBeginDate);
            if (StringUtils.isNotEmpty(orderEndDate))
                conditionMap.put("createDateEnd", orderEndDate);
            if (StringUtils.isNotEmpty(payBeginDate))
                conditionMap.put("payDateStart", payBeginDate);
            if (StringUtils.isNotEmpty(payEndDate))
                conditionMap.put("payDateEnd", payEndDate);
            if (StringUtils.isNotEmpty(productName))
                conditionMap.put("commodityName", productName);
            if (StringUtils.isNotEmpty(productCode))
                conditionMap.put("commodityCode", productCode);
            if (StringUtils.isNotEmpty(assess))
                conditionMap.put("assess", Boolean.valueOf(assess));
            conditionMap.put("commerceId", getSupplyId());

            Integer count = orderService.findOrderListCount(conditionMap);
            conditionMap.put("start", pagintion.getStartindex());
            conditionMap.put("end", pagintion.getEndindex());

            List<OrderMain> list = orderService.findOrderList(conditionMap);
            orderStatusMap = OrderDictionaryEnum.Order_Status.values();
            pagintion.setRecordList(list);
            pagintion.setTotalRecords(count);
        } catch (Exception e) {
            logger.error("showAllOrderList:" + e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 查询 待结转订单列表 --订单状态为已付款 状态为2
     * 查询 待配送订单列表 --订单状态为 已出库 4
     * 查询 已配送订单列表 --订单状态为 已完成和已配送 6,5 已取消-1
     * 几个订单列表的查询全部合并为这一个查询
     *
     * @return
     */
    public String showOrderListByStatus() {
        try {
            pagintion = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
            if (StringUtils.isNotEmpty(orderStatusForMenuQuery)) {
                List statusList = new ArrayList();
                if (orderStatusForMenuQuery.indexOf(',') > 0) {
                    statusList.addAll(Lists.newArrayList(StringUtils.split(orderStatusForMenuQuery, ",")));
                    conditionMap.put("statusArr", statusList);
                } else {
                    String finalStatus = orderStatusForMenuQuery;
                    if ("7".equals(orderStatusForMenuQuery)) {
                        finalStatus = "6";
                        assess = "true";
                    }
                    // 如果状态是待结转则只查询风控通过的
                    if (RISK_PASS.equals(orderStatusForMenuQuery) && titleValue.equals("shouldSetlle")) {
                        conditionMap.put("status", finalStatus);
                    } else if (RISK_PASS.equals(orderStatusForMenuQuery)) { // 如果状态为已锁库存,则查询风控通过和已锁库存
                        statusList.add(orderStatusForMenuQuery);
                        statusList.add(STOCK_LOCK);
                        conditionMap.put("statusArr", statusList);
                    } else {
                        conditionMap.put("status", finalStatus);
                    }
                }
            }
            // 订单号查询
            if (StringUtils.isNotEmpty(orderCodeForSearch))
                conditionMap.put("orderCode", orderCodeForSearch.trim());
            // 订单状态查询
            if (StringUtils.isNotEmpty(orderStatus))
                conditionMap.put("status", orderStatus);
            // 订单渠道查询
            if (StringUtils.isNotEmpty(channel))
                conditionMap.put("channel", channel);
            // 按买家信息种类来区分种类
            if (queryTypeForBuyer != null && StringUtils.isNotEmpty(queryTypeForBuyer)
                    && queryBuyerValue != null && StringUtils.isNotEmpty(queryBuyerValue)) {
                conditionMap.put(queryTypeForBuyer, queryBuyerValue.trim());
            }

            // 下单账号查询
            // if (StringUtils.isNotEmpty(orderAccount))
            // conditionMap.put("customerAccount", orderAccount.trim());
            // 收货人姓名查询
            // if (StringUtils.isNotEmpty(consignee))
            // conditionMap.put("consigneeName", consignee.trim());
            // 收货人手机查询
            // if (StringUtils.isNotEmpty(consigneePhone))
            // conditionMap.put("consigneeMobile", consigneePhone.trim());
            // 下单起始时间
            if (StringUtils.isNotEmpty(orderBeginDate))
                conditionMap.put("createDateStart", orderBeginDate);
            // 下单结束时间
            if (StringUtils.isNotEmpty(orderEndDate))
                conditionMap.put("createDateEnd", orderEndDate);
            // 支付开始时间
            if (StringUtils.isNotEmpty(payBeginDate))
                conditionMap.put("payDateStart", payBeginDate);
            // 支付结束时间
            if (StringUtils.isNotEmpty(payEndDate))
                conditionMap.put("payDateEnd", payEndDate);
            // 商品名称
            if (StringUtils.isNotEmpty(productName))
                conditionMap.put("commodityName", productName.trim());
            // 商品编码
            if (StringUtils.isNotEmpty(productCode))
                conditionMap.put("commodityCode", productCode.trim());
            // 是否已经评价
            if (StringUtils.isNotEmpty(assess))
                conditionMap.put("assess", Boolean.valueOf(assess));
            // 完成时间 开始时间
            if (StringUtils.isNotEmpty(finishBeginDate))
                conditionMap.put("finishBeginDate", finishBeginDate);
            // 完成时间 结束时间
            if (StringUtils.isNotEmpty(finishEndDate))
                conditionMap.put("finishEndDate", finishEndDate);

            // 供应商ID
            conditionMap.put("commerceId", super.getSupplyId());
            Integer count = orderService.findOrderListCount(conditionMap);

            conditionMap.put("start", pagintion.getStartindex());
            conditionMap.put("end", pagintion.getEndindex());

            List<OrderMain> list = orderService.findOrderList(conditionMap);
            if (CollectionUtils.isNotEmpty(list)) {
                list = list.stream().map((OrderMain order) -> {
                    if (shouldPayStatus == order.getOrderStatus().longValue()) {
                        String orderCode = order.getOrderCode();
                        Long orderStatus = order.getOrderStatus();
                        try {
                            order.setFinallyPayTime(orderService.getFinallyPayEndTime(orderCode));
                        } catch (Exception e) {
                            logger.error("获去预售订单截取时间失败。订单号[{}],订单状态[{}],错误信息{}。",
                                    new Object[]{orderCode, orderStatus, e});
                        }
                    }
                    return order;
                }).collect(Collectors.toList());
            }

            //订单状态可供查询不再来源于订单系统,而是自己这边塞入可供查询的状态
            setOrderStatusyMapForQuery();
            setBuyerQueryTypeMap();
            pagintion.setRecordList(list);
            pagintion.setTotalRecords(count);
        } catch (Exception e) {
            logger.error("showOrderListByStatus查询错误:" + e.getMessage() + "orderStatusForMenuQuery="
                    + orderStatusForMenuQuery, e);
        }
        return SUCCESS;
    }

    /**
     * 康美中药城同步订单列表
     *
     * @return
     */
    public String showOrderListForKMB2B() {
        try {
            pagintion = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
            if (StringUtils.isNotEmpty(orderSyncCodeForSearch))
                conditionMap.put("orderCode", orderSyncCodeForSearch);
            if (StringUtils.isNotEmpty(orderSyncAccount))
                conditionMap.put("customerAccount", orderSyncAccount);
            if (StringUtils.isNotEmpty(outSyncCode))
                conditionMap.put("outCode", outSyncCode);
            if (StringUtils.isNotEmpty(syncFlag))
                conditionMap.put("syncFlag", syncFlag);

            conditionMap.put("isFailOrder", "1");
            conditionMap.put("commerceId", super.getSupplyId());
            conditionMap.put("start", pagintion.getStartindex());
            conditionMap.put("end", pagintion.getEndindex());

            Map<String, Object> dataMap = orderService.findSyncOrderList(conditionMap);
            pagintion.setRecordList((List<OrderSyncDetail>) dataMap.get("list"));
            pagintion.setTotalRecords((Integer) dataMap.get("count"));
            getRequest().setAttribute("orderSyncFlagMap", OrderSyncFlagMap.getMap());
            getRequest().setAttribute("orderTypegMap", OrderTypeMap.getMap());
        } catch (Exception e) {
            logger.error("showAllOrderList:" + e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 有效的订单列表
     *
     * @return
     */
    public String showEffectiveOrderList() {
        return SUCCESS;
    }

    public String toOrderCarryOver() {
        return SUCCESS;
    }

    public String toOrderCarryOverList() {
        return SUCCESS;
    }

    public String orderCarryOverList() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            pagintion = this.getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
            conditionMap.put("operator", super.getSupplyId().toString());
            conditionMap.put("startDate", sdf.parse(orderBeginDate));
            conditionMap.put("endDate", sdf.parse(orderEndDate));
            conditionMap.put("start", pagintion.getStartindex());
            conditionMap.put("end", pagintion.getEndindex());
            Integer count = orderService.carryOverListCount(conditionMap);
            orderCarryList = orderService.carryOverList(conditionMap);

            pagintion.setRecordList(orderCarryList);
            pagintion.setTotalRecords(count);
        } catch (Exception e) {
            logger.error("orderCarryOverList:" + e.getMessage(), e);
        }
        return SUCCESS;
    }

    public String carryOver() {
        try {
            orderCarryList = orderService.carryOver(DateTimeUtils.parseDate(orderBeginDate, "yyyy-MM-dd HH:mm:ss"),
                    DateTimeUtils.parseDate(orderEndDate, "yyyy-MM-dd HH:mm:ss"), super.getSupplyId(), null);
        } catch (Exception e) {
            logger.error("carryOver:" + e.getMessage(), e);
        }

        return SUCCESS;
    }

    /**
     * 订单详情页面
     *
     * @return
     */
    public String showOrderItemDetail() {
        try {
            loginAccount = getLoginUserName();
            // 1.
            order = orderService.findOrderByOrderCode(orderCode);
            if (order.getOrderStatus().equals(4L)) {
                getRequest().setAttribute("logisticsCompanyMap", logisticsMap);
            }
            cmsPagePath = ConfigurationUtil.getString("CMS_PRODUCT_PATH");
            Map map = new HashMap();
            map.put("orderCode", orderCode);
            orderpay = orderService.getOrderPay(map);
            map.put("payway", OrderDictionaryEnum.OrderPayMethod.Coupon.getKey());
            couponpay = orderService.getOrderPay(map);
            map.put("payway", OrderDictionaryEnum.OrderPayMethod.Bank.getKey());
            bankpay = orderService.getOrderPay(map);
            map.put("payway", OrderDictionaryEnum.OrderPayMethod.Platform.getKey());
            platformpay = orderService.getOrderPay(map);
            map.put("payway", OrderDictionaryEnum.OrderPayMethod.Balance.getKey());
            balancepay = orderService.getOrderPay(map);
            map.put("payway", OrderDictionaryEnum.OrderPayMethod.OnLine.getKey());
            onlinepay = orderService.getOrderPay(map);
            notpay = order.getAmountPayable().subtract(orderpay);
            payable = order.getCommoditySum().add(order.getFare());
            // discount = order.getDiscountAmount().add(couponpay);
            discount = order.getDiscountAmount();
            actualpay = payable.subtract(discount);
            finalPay = order.getAmountPayable().subtract(order.getDepositSum());// 商品尾款

            //计算已付金额，根据支付情况计算
            BigDecimal paidDeposit = order.getPaidDeposit();
            BigDecimal noFinalPayment = order.getNoFinalPayment();
            if (paidDeposit == null) {
                wasPayed = new BigDecimal("0.00");
            } else if (noFinalPayment == null) {
                wasPayed = paidDeposit;
            } else {
                wasPayed = paidDeposit.add(noFinalPayment);
            }

            // orderpay = orderpay.subtract(couponpay);
            items = orderService.findOrderItemByOrderCode(orderCode);
            if (order.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Pay_Done.getKey()) {
                for (OrderItem item : items) {
                    if (null != item.getExtAttrType()
                            && item.getExtAttrType().intValue() == OrderDictionaryEnum.OrderItemExtAttrType.includeMedic.getKey()) {
                        isExt = true;
                        break;
                    }
                }
            }
            operates = orderService.findOrderOperateStatementByOrderCode(orderCode);
            if (operates != null && operates.size() > 0) {
                Collections.reverse(operates);
            }

            // pays = orderService.findOrderPayStatementByOrderCode(orderCode);
            preferentials = orderService.findOrderPreferentialByOrderCode(orderCode);
            fullDdiscount = BigDecimal.ZERO;
            plusDiscount = BigDecimal.ZERO;

            for (OrderPreferentialEVO pf : preferentials) {
                if (pf.getOrderPreferentialType().intValue() == 6) {
                    fullDdiscount = fullDdiscount.add(pf.getOrderPreferentialSum());
                } else if (pf.getOrderPreferentialType().intValue() == 5) {
                    plusDiscount = plusDiscount.add(pf.getOrderPreferentialSum());
                }
            }

            // 筛选重复的
            Map<String, OrderPreferentialEVO> filterMap = new HashMap();
            for (OrderPreferentialEVO p : preferentials) {
                if (filterMap.containsKey(p.getOrderPreferentialCode())) {
                    continue;
                }
                filterMap.put(p.getOrderPreferentialCode(), p);
            }
            preferentials = new ArrayList();
            preferentials.addAll(filterMap.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList()));

            actualpay = actualpay.add(plusDiscount);
            invoice = orderService.findInvoiceById(order.getInvoiceId());
            invoiceItems = orderService.findInvoiceItemsById(order.getInvoiceId());
            try {
                Map admap = new HashMap();
                admap.put("orderCode", orderCode);
                admap.put("flag", order.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()
                                ? OrderDictionaryEnum.OrderPayFlag.Refundment.getKey()
                                : OrderDictionaryEnum.OrderPayFlag.Payment.getKey());
                isAdditional = orderService.checkIsAdditional(admap);
            } catch (Exception e1) {
                isAdditional = Boolean.FALSE;
            }

            // 5代表的状态是已配送 针对已配送的订单供应商可以修改一次物流信息
            if (order.getOrderStatus().equals(5L)) {
                Integer count = orderService.queryUpdateLogisticCount(order.getOrderCode());
                if (count != null && count < 2) {
                    getRequest().setAttribute("logisticsCompanyMapForUpdate", logisticsMap);
                    getRequest().setAttribute("canUpdateLogistic", true);
                }
            }

            // 详情页新增订单物流追踪显示
            queryAndSetExpressInfo(order);
            getRequest().setAttribute("backType", getRequest().getParameter("backType"));
            // 物流状态
            setExpressStatusMap();
        } catch (Exception e) {
            logger.error("showOrderItemDetail:" + e.getMessage(), e);
        }

        return SUCCESS;
    }

    /**
     * 获取订单信息
     *
     * @return
     */
    public String toEditOrderInfo() {
        try {
            order = orderService.findOrderByOrderCode(orderCode);
        } catch (Exception e) {
            logger.error("toEditOrderInfo:" + e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 修改订单备注
     *
     * @return
     */
    public String editOrderRemark() {
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("orderCode", orderCode);
            map.put("orderOperationRemark", orderRemark);
            int result = orderService.editOrderRemark(map);
            writeStr(result + "");
        } catch (Exception e) {
            logger.error("editOrderRemark:" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 查看订单评价信息
     *
     * @return
     */
    public String toViewOrderAssess() {
        try {
            Map<String, Object> assessMap = orderService.queryAssessByOrderCode(orderCode);
            orderAssessInfo = (OrderAssessInfo) assessMap.get("assessInfo");
            oadlist = (List<OrderAssessDetail>) assessMap.get("assessDetailList");
            super.getRequest().setAttribute("prodAssessList", orderService.queryAppraiseByOrderCode(orderCode));
            super.getRequest().setAttribute("productViewPath", Constants.PRODUCT_VIEW_PATH);
        } catch (Exception e) {
            logger.error("editOrderRemark:" + e.getMessage(), e);
        }

        return SUCCESS;
    }

    /**
     * 修改订单的物流单
     *
     * @return
     */
    public String editOrderLogisticsInfo() {
        try {
            //当收货未刷新订单详情页面而直接点击修改信息时判断订单状态
            if (isUpdateExpressInfoAgain != null && "Y".equals(isUpdateExpressInfoAgain)) {
                order = orderService.findOrderByOrderCode(orderCode);
                if (!order.getOrderStatus().equals(5L)) {
                    writeStr("statusValid");
                    return null;
                }
            }

            LogisticAndDistributionInfoVO lv = new LogisticAndDistributionInfoVO();
            lv.setOrderCode(orderCode);
            lv.setLogisticsCode(logisticsCompany);
            lv.setLogisticsOrderNo(logisticsNo);
            lv.setLogisticsName(logisticsMap.get(logisticsCompany));
            String msg = orderService.sendLogisticsInfo(lv);
            writeStr(msg);
        } catch (Exception e) {
            logger.error("editOrderLogisticsInfo:" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 修改订单信息
     *
     * @return
     */
    public String modifyOrderInfo() {
        try {
            int result = orderService.editOrderRemark(infoMap);
            writeStr(result + "");
        } catch (Exception e) {
            logger.error("modifyOrderInfo:" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 同步康美中药城订单
     *
     * @return
     */
    public String sycOrderList() {
        List<ReturnResult> msgMap = new ArrayList();
        Map<String, String> map = new HashMap();
        ReturnResult result = null;
        try {
            Map<String, String> rtnMsg = orderService.sycOrderList(orderMainCodes);
            if (rtnMsg.isEmpty()) {
                throw new Exception("同步康美中药城订单失败");
            }

            map.put("0", "同步成功。");
            map.put("-1", "同步失败。");
            map.put("-2", "此订单已同步过。");
            for (Map.Entry<String, String> entry : rtnMsg.entrySet()) {
                result = new ReturnResult(entry.getKey(), map.get(entry.getValue()), null);
                msgMap.add(result);
            }

            writeJson(msgMap);
        } catch (Exception e) {
            logger.error("订单同步出错！", e);
            result = new ReturnResult("同步失败", "系统发生错误，请稍后再试！", null);
            msgMap.add(result);
            writeJson(msgMap);
        }

        return null;
    }

    /**
     * 同步所有失败的订单
     *
     * @return
     */
    public String sycFailedOrder() {
        List<ReturnResult> msgMap = new ArrayList();
        Map<String, String> map = new HashMap();
        ReturnResult rr = null;
        conditionMap.put("isFailOrder", "0");
        conditionMap.put("commerceId", super.getSupplyId());
        conditionMap.put("syncFlag", OrderDictionaryEnum.OrderSyncFlag.failed.getKey());
        try {
            Map<String, Object> dataMap = orderService.findSyncOrderList(conditionMap);
            if (((List<OrderSyncDetail>) dataMap.get("list")).isEmpty()) {
                rr = new ReturnResult("同步失败", "没有需要同步的订单！", null);
                msgMap.add(rr);
                writeJson(msgMap);
                return null;
            }

            List<String> failOrderMainCodes = new ArrayList<String>();
            for (OrderSyncDetail osd : (List<OrderSyncDetail>) dataMap.get("list")) {
                failOrderMainCodes.add(osd.getOrderCode());
            }

            Map<String, String> rtnMsg = orderService.sycOrderList(failOrderMainCodes);
            if (rtnMsg.isEmpty())
                throw new Exception("同步失败, 没有需要同步的订单！");

            map.put("0", "同步成功。");
            map.put("-1", "同步失败。");
            map.put("-2", "此订单已同步过。");
            for (Map.Entry<String, String> entry : rtnMsg.entrySet()) {
                rr = new ReturnResult(entry.getKey(), map.get(entry.getValue()), null);
                msgMap.add(rr);
            }
            writeJson(msgMap);
        } catch (Exception e) {
            logger.error("订单同步出错！", e);
            rr = new ReturnResult("同步失败", "系统发生错误，请稍后再试！", null);
            msgMap.add(rr);
            super.writeJson(msgMap);
        }

        return null;
    }

    /**
     * 查询订单的物流信息
     */
    private void queryAndSetExpressInfo(OrderMainVo order) {
        // 根据查询的快递公司,为代码(如yunda);
        String logisticsCode = order.getLogisticsCode();//快递公司编号
        String expressNo = order.getLogisticsOrderNo();//快递号
        String orderCode = order.getOrderCode();//订单号
        //1.参数校验
        if (StringUtils.isBlank(logisticsCode) || StringUtils.isBlank(expressNo)) {
            logger.error("查询订单[{}]的物流信息失败,参数有为空.快递公司编号[{}],快递号[{}].",
                    new Object[]{orderCode, logisticsCode, expressNo});
            return;
        }
        logger.info("开始查询订单[{}]的物流信息.快递公司编号[{}],快递号[{}].",
                new Object[]{orderCode, logisticsCode, expressNo});

        try {
            // 2.根据“快递单号,快递名称”查询快递信息
            expressSubscription = orderService.queryExpressPathInfo(logisticsCode, expressNo);
            if (null == expressSubscription) {// 如果查询快递有数据
                logger.error("查询订单[{}]的物流信息失败,没有找到快递数据.快递公司编号[{}],快递号[{}].",
                        new Object[]{orderCode, logisticsCode, expressNo});
            }
        } catch (Exception e) {
            logger.error("查询订单[{}]的物流信息失败,快递公司编号[{}],快递号[{}],失败信息{}.",
                    new Object[]{orderCode, logisticsCode, expressNo, e});
        }
    }

    /**
     * 修改运费
     *
     * @return
     */
    public String toEditOrderYunFei() {
        try {
            order = orderService.findOrderByOrderCode(orderCode);
        } catch (Exception e) {
            logger.error("toEditOrderYunFei:" + e.getMessage(), e);
        }
        return SUCCESS;
    }

    private BigDecimal newFare;// 新运费

    /**
     * 修改运费
     *
     * @return
     */
    public String updateFare() {
        int result = 0;
        try {
            boolean haveDone = orderService.updateFare(orderCode, super.getLoginUserName(), newFare);
            if (haveDone) {
                result = 1;
            }
            writeStr(result + "");
        } catch (Exception e) {
            logger.error("toEditOrderYunFei:" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 订单查询按买家信息类型
     */
    public void setBuyerQueryTypeMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("customerMobile", "买家电话");
        map.put("customerAccount", "买家账号");
        map.put("customerName", "买家姓名");
        super.getRequest().setAttribute("buyerQueryTypeMap", map);
    }

    /**
     * 单个订单结转 20150901 maliqun add
     *
     * @return
     */
    public String carryOverSingleByAjax() {
        try {
            if (StringUtils.isEmpty(orderCode)) {
                writeStr("nullValue");
                return null;
            }
            orderCarryList = orderService.carryOver(null, null, super.getSupplyId(), orderCode);

            //结转接口变更
            if (CollectionUtils.isEmpty(orderCarryList)) {
                writeStr("error");
                return null;
            }

            OrderCarry oc = orderCarryList.get(0);

            if (oc == null || oc.getHandleId() == null) {
                writeStr("error");
                return null;
            }

            writeStr(oc.getHandleId().toString());
            logger.info("单个订单" + orderCode + "结转结果提示=" + oc.getHandleId());
            // if (-1 == oc.getHandleId()) {
            // writeStr("正在结转,请稍后!");
            // } else if (0 == oc.getHandleId()) {
            // writeStr("无可结转订单!");
            // } else if (1 == oc.getHandleId()) {
            // writeStr("结转程序已启动,两分钟后可到结转列表查看结果!");
            // }
            return null;
            // noOrderSum大于0一般是存在库存不足导致结转不了的情况
            // Long noOrderSum = 0l;
            // for (OrderCarry orderCarry : orderCarryList) {
            // if (orderCarry.getNoOrderSum() != null) {
            // noOrderSum += orderCarry.getNoOrderSum();
            // }
            // }
            // if (noOrderSum > 0) {
            // writeStr("shortStock");
            // } else {
            // writeStr("success");
            // }
            // return null;

        } catch (Exception e) {
            logger.error("单个订单结转发生异常,订单号为=" + orderCode + ",异常详情如下:", e);
            writeStr("error");
            return null;
        }
    }

    /**
     * 订单导出功能 20151113 add
     *
     * @return
     */
    public String exportOrder() {
        try {
            // 订单号查询
            if (StringUtils.isNotEmpty(orderCodeForSearch))
                conditionMap.put("orderCode", orderCodeForSearch.trim());
            // 订单状态查询
            if (StringUtils.isNotEmpty(orderStatus))
                conditionMap.put("orderStatus", orderStatus);
            // 按买家信息种类来区分种类
            if (queryTypeForBuyer != null && StringUtils.isNotEmpty(queryTypeForBuyer)
                && queryBuyerValue != null && StringUtils.isNotEmpty(queryBuyerValue)) {
                conditionMap.put(queryTypeForBuyer, queryBuyerValue.trim());
            }

            String exportBeginDate = orderBeginDate;
            String exportEndDate = orderEndDate;
            if ("finishDate".equals(queryTypeForTime)) {
                exportBeginDate = finishBeginDate;
                exportEndDate = finishEndDate;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // 结束时间的判断,如果没有,默认当天
            if (StringUtils.isBlank(exportEndDate)) {
                exportEndDate = dateFormat.format(new Date());
            }

            // 默认只导出近三个月的 开始时间的判断
            if (StringUtils.isBlank(exportBeginDate)) {
                // 往前推三个月
                Calendar calendar = Calendar.getInstance(); // 得到日历
                calendar.setTime(dateFormat.parse(exportEndDate));// 把当前时间赋给日历
                calendar.add(calendar.MONTH, -3); // 设置为前3月
                Date beforThreeMonth = calendar.getTime();
                exportBeginDate = dateFormat.format(beforThreeMonth);
            }
            logger.info("导出时间基准类型=" + queryTypeForTime + ",导出开始时间=" + exportBeginDate
                    + ",导出结束时间=" + exportEndDate);

            String beginKey = "orderBeginDate";
            String endKey = "orderEndDate";
            if ("finishDate".equals(queryTypeForTime)) {
                beginKey = "finishBeginDate";
                endKey = "finishEndDate";
            }

            conditionMap.put(beginKey, exportBeginDate);
            conditionMap.put(endKey, exportEndDate);

            // // 下单起始时间
            // if (StringUtils.isNotEmpty(exportBeginDate))
            // conditionMap.put("orderBeginDate", exportBeginDate);

            // // 下单结束时间
            // if (StringUtils.isNotEmpty(orderEndDate))
            // conditionMap.put("orderEndDate", orderEndDate);

            // 是否已经评价
            if (StringUtils.isNotEmpty(assess))
                conditionMap.put("assess", Boolean.valueOf(assess));

            // // 完成时间 开始时间
            // if (StringUtils.isNotEmpty(finishBeginDate))
            // conditionMap.put("finishBeginDate", finishBeginDate);
            // // 完成时间 结束时间
            // if (StringUtils.isNotEmpty(finishEndDate))
            // conditionMap.put("finishEndDate",
            // finishEndDate);

            // 供应商ID
            conditionMap.put("commerceId", getSupplyId());

            conditionMap.put("titleValue", StringUtils.isBlank(titleValue) == true ? "所有订单" : titleValue);

            // 返回xls地址
            String excelAddress = orderService.exportOrder(conditionMap);
            logger.info("导出订单excel下载地址=" + excelAddress);
            writeStr(excelAddress);
        } catch (Exception e) {
            logger.error("导出订单发生异常,异常详情如下:", e);
            writeStr("-1");
        }

        return null;
    }

    public Order_Status[] getOrderStatusMap() {
        Order_Status[] temp = orderStatusMap;
        return temp;
    }

    public void setOrderStatusMap(Order_Status[] orderStatusMap) {
        this.orderStatusMap = orderStatusMap;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public OrderMainVo getOrder() {
        return order;
    }

    public void setOrder(OrderMainVo order) {
        this.order = order;
    }

    public BigDecimal getPayable() {
        return payable;
    }

    public void setPayable(BigDecimal payable) {
        this.payable = payable;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getFullDdiscount() {
        return fullDdiscount;
    }

    public void setFullDdiscount(BigDecimal fullDdiscount) {
        this.fullDdiscount = fullDdiscount;
    }

    public BigDecimal getPlusDiscount() {
        return plusDiscount;
    }

    public void setPlusDiscount(BigDecimal plusDiscount) {
        this.plusDiscount = plusDiscount;
    }

    public BigDecimal getActualpay() {
        return actualpay;
    }

    public void setActualpay(BigDecimal actualpay) {
        this.actualpay = actualpay;
    }

    public BigDecimal getOrderpay() {
        return orderpay;
    }

    public void setOrderpay(BigDecimal orderpay) {
        this.orderpay = orderpay;
    }

    public BigDecimal getCouponpay() {
        return couponpay;
    }

    public void setCouponpay(BigDecimal couponpay) {
        this.couponpay = couponpay;
    }

    public BigDecimal getBankpay() {
        return bankpay;
    }

    public void setBankpay(BigDecimal bankpay) {
        this.bankpay = bankpay;
    }

    public BigDecimal getPlatformpay() {
        return platformpay;
    }

    public void setPlatformpay(BigDecimal platformpay) {
        this.platformpay = platformpay;
    }

    public BigDecimal getBalancepay() {
        return balancepay;
    }

    public void setBalancepay(BigDecimal balancepay) {
        this.balancepay = balancepay;
    }

    public BigDecimal getOnlinepay() {
        return onlinepay;
    }

    public void setOnlinepay(BigDecimal onlinepay) {
        this.onlinepay = onlinepay;
    }

    public BigDecimal getNotpay() {
        return notpay;
    }

    public void setNotpay(BigDecimal notpay) {
        this.notpay = notpay;
    }

    public List<OrderOperateStatementVo> getOperates() {
        return operates;
    }

    public void setOperates(List<OrderOperateStatementVo> operates) {
        this.operates = operates;
    }

    public List<OrderPayStatementVo> getPays() {
        return pays;
    }

    public void setPays(List<OrderPayStatementVo> pays) {
        this.pays = pays;
    }

    public List<OrderPreferentialEVO> getPreferentials() {
        return preferentials;
    }

    public void setPreferentials(List<OrderPreferentialEVO> preferentials) {
        this.preferentials = preferentials;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public List<InvoiceVo> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceVo> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public Boolean getIsAdditional() {
        return isAdditional;
    }

    public void setIsAdditional(Boolean isAdditional) {
        this.isAdditional = isAdditional;
    }

    public String getCmsPagePath() {
        return cmsPagePath;
    }

    public void setCmsPagePath(String cmsPagePath) {
        this.cmsPagePath = cmsPagePath;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getOrderCodeForSearch() {
        return orderCodeForSearch;
    }

    public void setOrderCodeForSearch(String orderCodeForSearch) {
        this.orderCodeForSearch = orderCodeForSearch;
    }

    public String getOrderAccount() {
        return orderAccount;
    }

    public void setOrderAccount(String orderAccount) {
        this.orderAccount = orderAccount;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getOrderBeginDate() {
        return orderBeginDate;
    }

    public void setOrderBeginDate(String orderBeginDate) {
        this.orderBeginDate = orderBeginDate;
    }

    public String getOrderEndDate() {
        return orderEndDate;
    }

    public void setOrderEndDate(String orderEndDate) {
        this.orderEndDate = orderEndDate;
    }

    public String getPayBeginDate() {
        return payBeginDate;
    }

    public void setPayBeginDate(String payBeginDate) {
        this.payBeginDate = payBeginDate;
    }

    public String getPayEndDate() {
        return payEndDate;
    }

    public void setPayEndDate(String payEndDate) {
        this.payEndDate = payEndDate;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Map<String, String> getInfoMap() {
        return infoMap;
    }

    public void setInfoMap(Map<String, String> infoMap) {
        this.infoMap = infoMap;
    }

    public List<OrderCarry> getOrderCarryList() {
        return orderCarryList;
    }

    public void setOrderCarryList(List<OrderCarry> orderCarryList) {
        this.orderCarryList = orderCarryList;
    }

    public String getNstatus() {
        return nstatus;
    }

    public void setNstatus(String nstatus) {
        this.nstatus = nstatus;
    }

    public Boolean getIsExt() {
        return isExt;
    }

    public void setIsExt(Boolean isExt) {
        this.isExt = isExt;
    }

    public List<String> getOrderMainCodes() {
        return orderMainCodes;
    }

    public void setOrderMainCodes(List<String> orderMainCodes) {
        this.orderMainCodes = orderMainCodes;
    }

    public String getOrderSyncCodeForSearch() {
        return orderSyncCodeForSearch;
    }

    public void setOrderSyncCodeForSearch(String orderSyncCodeForSearch) {
        this.orderSyncCodeForSearch = orderSyncCodeForSearch;
    }

    public String getOrderSyncAccount() {
        return orderSyncAccount;
    }

    public void setOrderSyncAccount(String orderSyncAccount) {
        this.orderSyncAccount = orderSyncAccount;
    }

    public String getOutSyncCode() {
        return outSyncCode;
    }

    public void setOutSyncCode(String outSyncCode) {
        this.outSyncCode = outSyncCode;
    }

    public String getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(String syncFlag) {
        this.syncFlag = syncFlag;
    }

    public String getOrderStatusForMenuQuery() {
        return orderStatusForMenuQuery;
    }

    public String getInitStatus() {
        return initStatus;
    }

    public void setOrderStatusForMenuQuery(String orderStatusForMenuQuery) {
        this.orderStatusForMenuQuery = orderStatusForMenuQuery;
    }

    public String getTitleValue() {
        return titleValue;
    }

    public void setTitleValue(String titleValue) {
        this.titleValue = titleValue;
    }

    public void setInitStatus(String initStatus) {
        this.initStatus = initStatus;
    }

    public String getAssess() {
        return assess;
    }

    public void setAssess(String assess) {
        this.assess = assess;
    }

    public OrderAssessInfo getOrderAssessInfo() {
        return orderAssessInfo;
    }

    public void setOrderAssessInfo(OrderAssessInfo orderAssessInfo) {
        this.orderAssessInfo = orderAssessInfo;
    }

    public List<OrderAssessDetail> getOadlist() {
        return oadlist;
    }

    public void setOadlist(List<OrderAssessDetail> oadlist) {
        this.oadlist = oadlist;
    }

    public BigDecimal getNewFare() {
        return newFare;
    }

    public void setNewFare(BigDecimal newFare) {
        this.newFare = newFare;
    }

    public String getIsUpdateExpressInfoAgain() {
        return isUpdateExpressInfoAgain;
    }

    public void setIsUpdateExpressInfoAgain(String isUpdateExpressInfoAgain) {
        this.isUpdateExpressInfoAgain = isUpdateExpressInfoAgain;
    }

    public String getQueryTypeForBuyer() {
        return queryTypeForBuyer;
    }

    public void setQueryTypeForBuyer(String queryTypeForBuyer) {
        this.queryTypeForBuyer = queryTypeForBuyer;
    }

    public List<OrderItems> getItems() {
        return items;
    }

    public void setItems(List<OrderItems> items) {
        this.items = items;
    }

    public String getQueryBuyerValue() {
        return queryBuyerValue;
    }

    public void setQueryBuyerValue(String queryBuyerValue) {
        this.queryBuyerValue = queryBuyerValue;
    }

    public String getFinishBeginDate() {
        return finishBeginDate;
    }

    public void setFinishBeginDate(String finishBeginDate) {
        this.finishBeginDate = finishBeginDate;
    }

    public String getFinishEndDate() {
        return finishEndDate;
    }

    public void setFinishEndDate(String finishEndDate) {
        this.finishEndDate = finishEndDate;
    }

    public String getQueryTypeForTime() {
        return queryTypeForTime;
    }

    public void setQueryTypeForTime(String queryTypeForTime) {
        this.queryTypeForTime = queryTypeForTime;
    }

    public ExpressSubscription getExpressSubscription() {
        return expressSubscription;
    }

    public void setExpressSubscription(ExpressSubscription expressSubscription) {
        this.expressSubscription = expressSubscription;
    }

    public BigDecimal getFinalPay() {
        return finalPay;
    }

    public void setFinalPay(BigDecimal finalPay) {
        this.finalPay = finalPay;
    }

    public BigDecimal getWasPayed() {
        return wasPayed;
    }

    public void setWasPayed(BigDecimal wasPayed) {
        this.wasPayed = wasPayed;
    }

}
