package com.kmzyc.supplier.action.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.page.Pagination;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.model.OrderItems;
import com.kmzyc.supplier.service.ActivityPromotionEffectService;
import com.kmzyc.supplier.service.ActivityService;
import com.kmzyc.supplier.service.ActivitySkuService;
import com.kmzyc.supplier.service.OrderService;
import com.kmzyc.supplier.vo.ActivitySkuVo;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.entities.Invoice;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.maps.ActivityChargeTypeMap;
import com.pltfm.app.maps.ActivityLevelMap;
import com.pltfm.app.maps.ActivityTypeMap;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.InvoiceVo;
import com.pltfm.app.vobject.OrderMainVo;
import com.pltfm.app.vobject.OrderOperateStatementVo;
import com.pltfm.app.vobject.OrderPayStatementVo;
import com.pltfm.app.vobject.OrderPreferentialEVO;

/**
 * 活动推广效果控制类
 *
 * @author wanwen
 */
@Controller("supplierActivityResultAction")
@Scope(value = "prototype")
public class ActivityPromotionEffectAction extends SupplierBaseAction {

    @Resource
    private ActivityPromotionEffectService activityPromotionEffectService;

    private ActivityInfo activityParam;

    private ActivitySku activitySkuParam;

    private OrderMain orderMainParam;

    private Logger logger = LoggerFactory.getLogger(ActivityPromotionEffectAction.class);

    @Resource
    private ActivityService activityService;

    @Resource
    private ActivitySkuService activitySkuService;

    @Resource(name = "logisticsMap")
    private Map<String,String> logisticsMap;

    private List<ActivitySkuVo> existActivitySkuList;

    private String activityId;

    private ActivityInfo activity;

    private BigDecimal activityTotalPrice = new BigDecimal("0.00");//活动总费用

    private String supplierEntryId;

    private String skuId;

    private String orderStatusForMenuQuery;

    private String orderCodeForSearch;

    private String orderBeginDate; // 下单开始时间

    private String orderEndDate; // 下单结束时间

    private String orderStatus;

    private String cmsPagePath = ConfigurationUtil.getString("CMS_PRODUCT_PATH");

    private static final String RISK_PASS = "22"; // 风控通过

    private static final String STOCK_LOCK = "20"; // 已锁库存

    private String loginAccount;

    private OrderMainVo order;// 订单基本信息

    @Resource
    private OrderService orderService;

    private String orderCode;

    private BigDecimal payable;// 应付

    private BigDecimal discount;// 优惠

    private BigDecimal fullDdiscount;// 满减

    private BigDecimal plusDiscount;// 加价购

    private BigDecimal actualpay;// 实付

    private BigDecimal orderpay;// 已付

    private BigDecimal couponpay;// 券付

    private BigDecimal bankpay;// 卡付

    private BigDecimal platformpay;// 第三方付

    private BigDecimal reservepay;// 预备金付

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

    private ExpressSubscription expressSubscription;// 物流明细

    private String productSkuId;

    /**
     * 分页获取参与的活动推广效果列表
     *
     * @return
     */
    public String findMyPromotionEffectList() {
        if (activityParam == null) {
            activityParam = new ActivityInfo();
        }
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
        try {
            pagintion = activityPromotionEffectService.queryPromotionEffectList(getSupplyId(), activityParam, page);
        } catch (Exception e) {
            logger.error("查询活动推广效果列表出错:", e);
            return ERROR;
        }
        // 活动类型
        getRequest().setAttribute("activityTypeMap", ActivityTypeMap.getMap());
        // 活动收费类型
        getRequest().setAttribute("activityChargeTypeMap", ActivityChargeTypeMap.getMap());
        return SUCCESS;
    }

    /**
     * 分页查询参与的促销活动销量统计
     *
     * @return
     */
    public String findMyPromotionActivitySales() {
        if (activitySkuParam == null) {
            activitySkuParam = new ActivitySku();
        }
        activitySkuParam.setActivityId(Long.valueOf(activityId));
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
        try {
            pagintion = activityPromotionEffectService.querySPAndTxtActivitySalesList(getSupplyId(), page, activitySkuParam);
        } catch (Exception e) {
            logger.error("查询参与的促销活动销量出错:", e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 分页查询参与的图文活动销量
     *
     * @return
     */
    public String findMyTeletextActivitySales() {
        if (activitySkuParam == null) {
            activitySkuParam = new ActivitySku();
        }
        activitySkuParam.setActivityId(Long.valueOf(activityId));
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
        try {
            pagintion = activityPromotionEffectService.querySPAndTxtActivitySalesList(getSupplyId(), page, activitySkuParam);
        } catch (Exception e) {
            logger.error("查询参与的图文活动销量出错:", e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 分页查询参与渠道活动销量列表
     *
     * @return
     */
    public String findMyChannelActivitySalesList() {
        if (activitySkuParam == null) {
            activitySkuParam = new ActivitySku();
        }
        activitySkuParam.setActivityId(Long.valueOf(activityId));
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
        try {
            pagintion = activityPromotionEffectService.queryChannelActivitySalesList(getSupplyId(), page, activitySkuParam);
        } catch (Exception e) {
            logger.error("查询参与的渠道活动销量出错:", e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 分页查询参与活动的追加推广明细列表
     *
     * @return
     */
    public String findAppendProductDetail() {
        if (activitySkuParam == null) {
            activitySkuParam = new ActivitySku();
        }
        activitySkuParam.setActivityId(Long.valueOf(activityId));
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
        try {
            pagintion = activityPromotionEffectService.queryAppendProductDetail(getSupplyId(), page, activitySkuParam);
        } catch (Exception e) {
            logger.error("查询渠道活动销量出错:", e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 查询活动SKU销售明细
     *
     * @return
     */
    public String findMyActivitySkuSalesDetail() {
        logger.debug("开始请求查询活动SKU销售明细,参数{}", JSONObject.toJSONString(orderMainParam));
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
        Map<String, Object> conditionMap = new HashMap();
        if (StringUtils.isNotEmpty(orderStatusForMenuQuery)) {
            String finalStatus = orderStatusForMenuQuery;
            List<String> statusList = new ArrayList();
            if (RISK_PASS.equals(orderStatusForMenuQuery)) {
                statusList.add(orderStatusForMenuQuery);
                statusList.add(STOCK_LOCK);
                conditionMap.put("statusArr", statusList);
            } else {
                conditionMap.put("status", finalStatus);
            }
        }
        if (StringUtils.isNotEmpty(orderCodeForSearch))
            conditionMap.put("orderCode", orderCodeForSearch.trim());
        // 订单状态查询
        if (StringUtils.isNotEmpty(orderStatus))
            conditionMap.put("status", orderStatus);
        if (StringUtils.isNotEmpty(orderBeginDate))
            conditionMap.put("createDateStart", orderBeginDate);
        // 下单结束时间
        if (StringUtils.isNotEmpty(orderEndDate))
            conditionMap.put("createDateEnd", orderEndDate);

        try {
            pagintion = activityPromotionEffectService.querySkuSalesDetail(supplierEntryId, skuId, page, conditionMap);
        } catch (Exception e) {
            logger.error("查询活动SKU销售明细出错:", e);
            return ERROR;
        }

        setOrderStatusyMapForOrderQuery();
        getRequest().setAttribute("supplierEntryId", supplierEntryId);
        getRequest().setAttribute("skuId", skuId);
        return SUCCESS;
    }

    public String findActivityDetail() {
        try {
            // 获取活动信息
            activity = activityService.getActivityById(activityId);
            // 获取当前的sku信息
            existActivitySkuList = activitySkuService.getActivitySku(getSupplyId(), activityId);
            // 获取报名总费用
            if (CollectionUtils.isNotEmpty(existActivitySkuList)) {
                for (ActivitySkuVo skuVo : existActivitySkuList) {
                    activityTotalPrice = activityTotalPrice.add(skuVo.getSkuTotalPrice());
                }
            }
            getRequest().setAttribute("activityTypeMap", ActivityTypeMap.getMap());
            getRequest().setAttribute("activityLevelMap", ActivityLevelMap.getMap());
            return SUCCESS;
        } catch (ServiceException e) {
            return ERROR;
        }
    }

    /**
     * 订单详情
     *
     * @return
     */
    public String showOrderDetail() {
        try {
            loginAccount = getLoginUserName();
            order = orderService.findOrderByOrderCode(orderCode);

            if (order.getOrderStatus().equals(4L)) {
                getRequest().setAttribute("logisticsCompanyMap", logisticsMap);
            }

            Map map = new HashMap();
            map.put("orderCode", orderCode);
            orderpay = orderService.getOrderPay(map);
            map.put("payway", OrderDictionaryEnum.OrderPayMethod.Coupon.getKey());
            couponpay = orderService.getOrderPay(map);
            map.put("payway", OrderDictionaryEnum.OrderPayMethod.Bank.getKey());
            bankpay = orderService.getOrderPay(map);
            map.put("payway", OrderDictionaryEnum.OrderPayMethod.Platform.getKey());
            platformpay = orderService.getOrderPay(map);
            /*map.put("payway", OrderDictionaryEnum.OrderPayMethod.Reserve.getKey());
			reservepay = orderService.getOrderPay(map);*/
            map.put("payway", OrderDictionaryEnum.OrderPayMethod.Balance.getKey());
            balancepay = orderService.getOrderPay(map);
            map.put("payway", OrderDictionaryEnum.OrderPayMethod.OnLine.getKey());
            onlinepay = orderService.getOrderPay(map);
            notpay = order.getAmountPayable().subtract(orderpay);
            payable = order.getCommoditySum().add(order.getFare());
            discount = order.getDiscountAmount();
            actualpay = payable.subtract(discount);
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
            if (CollectionUtils.isNotEmpty(operates)) {
                Collections.reverse(operates);
            }

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
            if (CollectionUtils.isNotEmpty(preferentials)) {
                Map<String, OrderPreferentialEVO> filterMap = new HashMap();
                for (OrderPreferentialEVO p : preferentials) {
                    if (filterMap.containsKey(p.getOrderPreferentialCode())) {
                        continue;
                    }
                    filterMap.put(p.getOrderPreferentialCode(), p);
                }
                preferentials = new ArrayList();
                preferentials.addAll(filterMap.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList()));
                /*for (String key : filterMap.keySet()) {
                    preferentials.add(filterMap.get(key));
                }*/
            }

            actualpay = actualpay.add(plusDiscount);
            invoice = orderService.findInvoiceById(order.getInvoiceId());
            invoiceItems = orderService.findInvoiceItemsById(order.getInvoiceId());
            try {
                Map paramMap = new HashMap();
                paramMap.put("orderCode", orderCode);
                paramMap.put("flag", order.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()
                        ? OrderDictionaryEnum.OrderPayFlag.Refundment.getKey()
                        : OrderDictionaryEnum.OrderPayFlag.Payment.getKey());
                isAdditional = orderService.checkIsAdditional(paramMap);
            } catch (Exception e1) {
                logger.error("是否需要补单出错：", e1);
                isAdditional = Boolean.FALSE;
            }

            // 5 代表的状态是已配送 针对已配送的订单供应商可以修改一次物流信息
            if (order.getOrderStatus().equals(5L)) {
                Integer count = orderService.queryUpdateLogisticCount(order.getOrderCode());
                if (count != null && count < 2) {
                    getRequest().setAttribute("logisticsCompanyMapForUpdate", logisticsMap);
                    getRequest().setAttribute("canUpdateLogistic", true);
                }
            }
            queryAndSetExpressInfo(order);
            getRequest().setAttribute("backType", getRequest().getParameter("backType"));
            setExpressStatusMap();
        } catch (Exception e) {
            logger.error("showOrderItemDetail:" + e.getMessage(), e);
        }
        getRequest().setAttribute("supplierEntryId", supplierEntryId);
        getRequest().setAttribute("skuId", productSkuId);
        return SUCCESS;
    }

    private void queryAndSetExpressInfo(OrderMainVo order) {
        // 根据查询的快递公司,为代码(如yunda);
        String logisticsCode = order.getLogisticsCode();
        String expressNo = order.getLogisticsOrderNo() != null ? order.getLogisticsOrderNo().toString() : null;
        logger.info("logisticsCode=" + logisticsCode + ",expressNo=" + expressNo);
        try {
            if (StringUtils.isNotBlank(logisticsCode) && StringUtils.isNotBlank(expressNo)) {
                expressSubscription = orderService.queryExpressPathInfo(logisticsCode, expressNo);
                if (null == expressSubscription) {// 如果查询快递有数据
                    logger.error("showOrderItemDetail()查询快递有数据有误：无快递信息,订单号=" + order.getOrderCode() + ",快递data为null");
                }
            }
        } catch (Exception e) {
            logger.error("showOrderItemDetail()查询快递有数据有误,订单号=" + order.getOrderCode(), e);
        }
    }

    public ActivityInfo getActivityParam() {
        return activityParam;
    }

    public void setActivityParam(ActivityInfo activityParam) {
        this.activityParam = activityParam;
    }

    public ActivitySku getActivitySkuParam() {
        return activitySkuParam;
    }

    public void setActivitySkuParam(ActivitySku activitySkuParam) {
        this.activitySkuParam = activitySkuParam;
    }

    public List<ActivitySkuVo> getExistActivitySkuList() {
        return existActivitySkuList;
    }

    public void setExistActivitySkuList(List<ActivitySkuVo> existActivitySkuList) {
        this.existActivitySkuList = existActivitySkuList;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public ActivityInfo getActivity() {
        return activity;
    }

    public void setActivity(ActivityInfo activity) {
        this.activity = activity;
    }

    public BigDecimal getActivityTotalPrice() {
        return activityTotalPrice;
    }

    public void setActivityTotalPrice(BigDecimal activityTotalPrice) {
        this.activityTotalPrice = activityTotalPrice;
    }

    public OrderMain getOrderMainParam() {
        return orderMainParam;
    }

    public void setOrderMainParam(OrderMain orderMainParam) {
        this.orderMainParam = orderMainParam;
    }

    public String getSupplierEntryId() {
        return supplierEntryId;
    }

    public void setSupplierEntryId(String supplierEntryId) {
        this.supplierEntryId = supplierEntryId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getOrderStatusForMenuQuery() {
        return orderStatusForMenuQuery;
    }

    public void setOrderStatusForMenuQuery(String orderStatusForMenuQuery) {
        this.orderStatusForMenuQuery = orderStatusForMenuQuery;
    }

    public String getOrderCodeForSearch() {
        return orderCodeForSearch;
    }

    public void setOrderCodeForSearch(String orderCodeForSearch) {
        this.orderCodeForSearch = orderCodeForSearch;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCmsPagePath() {
        return cmsPagePath;
    }

    public void setCmsPagePath(String cmsPagePath) {
        this.cmsPagePath = cmsPagePath;
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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public BigDecimal getReservepay() {
        return reservepay;
    }

    public void setReservepay(BigDecimal reservepay) {
        this.reservepay = reservepay;
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

    public List<OrderItems> getItems() {
        return items;
    }

    public void setItems(List<OrderItems> items) {
        this.items = items;
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

    public Boolean getIsExt() {
        return isExt;
    }

    public void setIsExt(Boolean isExt) {
        this.isExt = isExt;
    }

    public ExpressSubscription getExpressSubscription() {
        return expressSubscription;
    }

    public void setExpressSubscription(ExpressSubscription expressSubscription) {
        this.expressSubscription = expressSubscription;
    }

    public String getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(String productSkuId) {
        this.productSkuId = productSkuId;
    }

}
