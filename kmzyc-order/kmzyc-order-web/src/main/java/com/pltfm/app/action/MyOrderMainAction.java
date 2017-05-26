package com.pltfm.app.action;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.commons.exception.ServiceException;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.dao.OrderPayStatementDAO;
import com.pltfm.app.entities.Invoice;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderPreferential;
import com.pltfm.app.service.MyOrderService;
import com.pltfm.app.service.OrderItemQryService;
import com.pltfm.app.service.OrderOperateStatementService;
import com.pltfm.app.service.OrderPayStatementService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.SysConstants;
import com.pltfm.sys.model.SysUser;

@Controller("myOrderAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class MyOrderMainAction extends BaseAction {
    private static final long serialVersionUID = -6242987825376233501L;
    private Logger log = Logger.getLogger(MyOrderMainAction.class);
    private String orderCode;// 订单号
    private String orderStatus;// 订单状态
    private OrderMain order;// 订单基本信息
    private Invoice invoice;// 发票基本信息
    private List items;// 订单商品
    private List operates;// 订单操作流水
    private List pays;// 订单支付流水
    private List preferentials;// 订单优惠明细
    private List invoiceItems;// 订单发票明细
    private List routers;// 订单路由
    private Boolean isAdditional;// 是否需要补单
    private Boolean isExt;// 是否需要审核
    private String loginAccount;

    /*
     * 查询订单详情
     */

    private BigDecimal payable;// 应付s
    private BigDecimal discount;// 优惠
    private BigDecimal fullDdiscount;// 满减
    private BigDecimal plusDiscount;// 加价购
    private BigDecimal actualpay;// 实付
    private BigDecimal orderpay;// 已付
    private BigDecimal couponpay;// 券付
    private BigDecimal bankpay;// 卡付
    private BigDecimal platformpay;// 第三方付
/*删除    private BigDecimal reservepay;// 预备金付*/ 
   private BigDecimal balancepay;// 余额付
    private BigDecimal onlinepay;// 在线付
    private BigDecimal notpay;// 未付
    private BigDecimal finalPayment;// 尾款
    private BigDecimal paidDeposit;// 已付定金
    private BigDecimal noFinalPayment;// 已付尾款

    private String orderMainCode;
    private long orderMainStatus;
    @Resource
    private OrderItemQryService orderItemQryService;
    @Resource
    private OrderQryService orderQryService;
    @Resource
    private OrderPayStatementService orderPayStatementService;
    @Resource
    OrderPayStatementDAO orderPayStatementDAO;

    @Resource
    private OrderOperateStatementService orderOperateStatementService;
    @Resource(name = "myOrderService")
    private MyOrderService myOrderService;

    /**
     * 转向订单查询页面
     * 
     * @return
     * @throws ActionException
     */
    public String show() throws ActionException {
        return "cancleOrder";
    }

    public String query() throws ActionException {
        try {
            // 用于查询加价购商品
            Long orderItemId = null;
            // 用于查询加价购商品
            OrderItem orderItem = null;
            // 加价购的中间变量，装载订单明细中的金额总和
            BigDecimal plusDiscountTemp = BigDecimal.ZERO;
            Map session = ActionContext.getContext().getSession();
            SysUser sysuser = (SysUser) session.get("sysUser");
            loginAccount = sysuser.getUserName();
            order = orderQryService.getOrderByCode(orderCode);
            String pathfix = null;
            if (order != null) {
                pathfix = SysConstants.sysChannelToWebsiteMap.get(order.getOrderChannel())
                        + "/products/";
                this.setCmsPagePath(pathfix);
                Map map = new HashMap();
                map.put("orderCode", orderCode);
                orderpay = orderPayStatementService.getOrderPay(map);
                map.put("payway", OrderDictionaryEnum.OrderPayMethod.Coupon.getKey());
                couponpay = orderPayStatementService.getOrderPay(map);
                map.put("payway", OrderDictionaryEnum.OrderPayMethod.Bank.getKey());
                bankpay = orderPayStatementService.getOrderPay(map);
                map.put("payway", OrderDictionaryEnum.OrderPayMethod.Platform.getKey());
                platformpay = orderPayStatementService.getOrderPay(map);
                /*删除预备金 map.put("payway", OrderDictionaryEnum.OrderPayMethod.Reserve.getKey());
                reservepay = orderPayStatementService.getOrderPay(map);*/
                map.put("payway", OrderDictionaryEnum.OrderPayMethod.Balance.getKey());
                balancepay = orderPayStatementService.getOrderPay(map);
                map.put("payway", OrderDictionaryEnum.OrderPayMethod.OnLine.getKey());
                onlinepay = orderPayStatementService.getOrderPay(map);
                notpay = order.getAmountPayable().subtract(orderpay);
                payable = order.getCommoditySum().add(order.getFare());
                // discount = order.getDiscountAmount().add(couponpay);
                discount = order.getDiscountAmount();
                // 此处不能用payable去减discount，由于payable是此类的成员变量的问题，会改变payable的值
                actualpay = order.getCommoditySum().add(order.getFare()).subtract(discount);
                // orderpay = orderpay.subtract(couponpay);
                // 待支付尾款状态： 尾款 = （应付金额） - （预留定金）
                if (order.getOrderStatus()
                        .intValue() == OrderDictionaryEnum.Order_Status.Nopay_FinalMoney.getKey()
                        || order.getOrderType().intValue() == 7) {
                    finalPayment = order.getAmountPayable().subtract(order.getDepositSum());
                    noFinalPayment = orderQryService.getOrderMoneyByCode(orderCode);
                    paidDeposit = orderQryService.getOrderPaidDepositByCode(orderCode);
                }
                items = orderItemQryService.listOrderItems(orderCode);
                if (order.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Pay_Done
                        .getKey()) {
                    for (Object it : items) {
                        OrderItem item = (OrderItem) it;
                        if (null != item.getExtAttrType()) {
                            if (item.getExtAttrType()
                                    .intValue() == OrderDictionaryEnum.OrderItemExtAttrType.includeMedic
                                            .getKey()) {
                                isExt = true;
                                break;
                            }
                        }
                    }
                }
                operates = orderItemQryService.listOrderOperates(orderCode);
                pays = orderItemQryService.listOrderPays(orderCode);
                preferentials = orderItemQryService.listOrderPreferentials(orderCode, null);
                fullDdiscount = BigDecimal.ZERO;
                plusDiscount = BigDecimal.ZERO;
                BigDecimal orderPreferentialSum = null;
                for (OrderPreferential pf : (List<OrderPreferential>) preferentials) {
                    orderPreferentialSum = BigDecimal.ZERO;
                    if (null != pf.getOrderPreferentialSum()) {
                        orderPreferentialSum = pf.getOrderPreferentialSum();
                    }
                    if (pf.getOrderPreferentialType()
                            .intValue() == OrderDictionaryEnum.OrderPreferentialType.Cut.getKey()) {
                        fullDdiscount = fullDdiscount.add(orderPreferentialSum);
                    } else if (pf.getOrderPreferentialType()
                            .intValue() == OrderDictionaryEnum.OrderPreferentialType.INCREASE
                                    .getKey()) {
                        // 从加价购的优惠明细中获取对应的订单明细id
                        orderItemId = pf.getOrderItemId();
                        // 如果订单明细id不为空，根据订单明细id查询得到订单明细
                        if (null != orderItemId) {
                            orderItem = orderItemQryService.getOrderItemById(orderItemId);
                        }
                        // 如果订单明细不为空，则取商品原价总和
                        if (null != orderItem) {
                            plusDiscountTemp = orderItem.getCommodityCalledSum();
                        }
                        plusDiscount = plusDiscount.add(plusDiscountTemp);
                    }
                }
                actualpay = actualpay.add(plusDiscount);
                invoice = orderItemQryService.getInvoiceById(order.getInvoiceId());
                invoiceItems = orderItemQryService.listOrderInvoiceItems(order.getInvoiceId());
                try {
                    Map admap = new HashMap();
                    admap.put("orderCode", orderCode);
                    admap.put("flag", order.getOrderStatus()
                            .intValue() == OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()
                                    ? OrderDictionaryEnum.OrderPayFlag.Refundment.getKey()
                                    : OrderDictionaryEnum.OrderPayFlag.Payment.getKey());
                    isAdditional = orderPayStatementDAO.checkIsAdditional(admap);
                } catch (SQLException e1) {
                    log.error("查询订单" + orderCode + "是否需要补单异常：", e1);
                    isAdditional = Boolean.FALSE;
                }
            }
        } catch (Exception e) {
            log.error("查询订单" + orderCode + "订单明细异常：", e);
            throw new ActionException();
        }
        return "cancleOrder";
    }

    /**
     * 取消订单，分两种情况： 1.付款前； 2.付款后送货前，以及送货失败；
     * 
     * @return
     */
    public void cancel() {
        int i = -1;
        try {
            if(orderStatus.equals("1")){//如果是未付款订单，先检查第三方是否支付成功
                orderOperateStatementService.noPayOrderCheck(orderCode);
            }
            
            i = orderOperateStatementService.changeOrderStatus(SysConstants.SYS, orderCode, -1l,
                    null);
        } catch (ServiceException e) {
            log.error("取消订单"+orderCode+"出错！"+e.getMessage());
        } catch (Exception e) {
            log.error("取消订单"+orderCode+"出错"+e.getMessage());
        }
        msg = 1 == i ? "订单 " + orderCode + " 已经成功取消！" : "订单  " + orderCode + "取消失败！";
    }

    private String cmsPagePath = null;

    public String getCmsPagePath() {
        return cmsPagePath;
    }

    public void setCmsPagePath(String cmsPagePath) {
        this.cmsPagePath = cmsPagePath;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Logger getLog() {
        return log;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public OrderMain getOrder() {
        return order;
    }

    public void setOrder(OrderMain order) {
        this.order = order;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public List getOperates() {
        return operates;
    }

    public void setOperates(List operates) {
        this.operates = operates;
    }

    public List getPays() {
        return pays;
    }

    public void setPays(List pays) {
        this.pays = pays;
    }

    public List getPreferentials() {
        return preferentials;
    }

    public void setPreferentials(List preferentials) {
        this.preferentials = preferentials;
    }

    public List getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public List getRouters() {
        return routers;
    }

    public void setRouters(List routers) {
        this.routers = routers;
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

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
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

    /*删除预备金 public BigDecimal getReservepay() {
        return reservepay;
    }

    public void setReservepay(BigDecimal reservepay) {
        this.reservepay = reservepay;
    }
*/
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

    public OrderItemQryService getOrderItemQryService() {
        return orderItemQryService;
    }

    public void setOrderItemQryService(OrderItemQryService orderItemQryService) {
        this.orderItemQryService = orderItemQryService;
    }

    public OrderQryService getOrderQryService() {
        return orderQryService;
    }

    public void setOrderQryService(OrderQryService orderQryService) {
        this.orderQryService = orderQryService;
    }

    public OrderPayStatementService getOrderPayStatementService() {
        return orderPayStatementService;
    }

    public void setOrderPayStatementService(OrderPayStatementService orderPayStatementService) {
        this.orderPayStatementService = orderPayStatementService;
    }

    public OrderPayStatementDAO getOrderPayStatementDAO() {
        return orderPayStatementDAO;
    }

    public void setOrderPayStatementDAO(OrderPayStatementDAO orderPayStatementDAO) {
        this.orderPayStatementDAO = orderPayStatementDAO;
    }

    public String getOrderMainCode() {
        return orderMainCode;
    }

    public void setOrderMainCode(String orderMainCode) {
        this.orderMainCode = orderMainCode;
    }

    public long getOrderMainStatus() {
        return orderMainStatus;
    }

    public void setOrderMainStatus(long orderMainStatus) {
        this.orderMainStatus = orderMainStatus;
    }

    public BigDecimal getFinalPayment() {
        return finalPayment;
    }

    public void setFinalPayment(BigDecimal finalPayment) {
        this.finalPayment = finalPayment;
    }

    public BigDecimal getPaidDeposit() {
        return paidDeposit;
    }

    public void setPaidDeposit(BigDecimal paidDeposit) {
        this.paidDeposit = paidDeposit;
    }

    public BigDecimal getNoFinalPayment() {
        return noFinalPayment;
    }

    public void setNoFinalPayment(BigDecimal noFinalPayment) {
        this.noFinalPayment = noFinalPayment;
    }

    // 格式化数字显示
    public String formatDouble(double s) {
        DecimalFormat fmt = new DecimalFormat("##");
        return fmt.format(s);
    }
    
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
