package com.pltfm.app.action;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressSubscription;
import com.pltfm.app.entities.Invoice;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderOperateStatement;
import com.pltfm.app.entities.OrderPreferential;
import com.pltfm.app.entities.OrderRiskScore;
import com.pltfm.app.service.OrderItemQryService;
import com.pltfm.app.service.OrderOperateStatementService;
import com.pltfm.app.service.OrderPayStatementService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.service.OrderRiskScoreService;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.SysConstants;
import com.pltfm.app.vobject.OrderPayStatementVo;
import com.pltfm.sys.model.SysUser;

@Controller("orderItemQryAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class OrderItemQryAction extends BaseAction {
  private static final long serialVersionUID = 1L;
  private Logger log = Logger.getLogger(OrderItemQryAction.class);
  @Resource
  private OrderItemQryService orderItemQryService;
  @Resource
  private OrderQryService orderQryService;
  @Resource
  private OrderPayStatementService orderPayStatementService;
  @Resource
  private OrderRiskScoreService orderRiskScoreService;
  @Resource
  private OrderOperateStatementService orderOperateStatementService;
  private String cmsPagePath = null;

  public String getCmsPagePath() {
    return cmsPagePath;
  }

  public void setCmsPagePath(String cmsPagePath) {
    this.cmsPagePath = cmsPagePath;
  }

  private Map<String, String> map = new HashMap<String, String>();// 查询条件map

  private String orderCode;// 订单号
  private OrderMain order;// 订单基本信息
  private Invoice invoice;// 发票基本信息
  private List items;// 订单商品
  private List operates;// 订单操作流水
  private List<OrderPayStatementVo> pays;// 订单支付流水
  private List preferentials;// 订单优惠明细
  private List invoiceItems;// 订单发票明细
  private List routers;// 订单路由
  private ExpressSubscription expressSubscription;// 订单物流信息
  private Boolean isAdditional;// 是否需要补单
  private Boolean isExt;// 是否需要审核
  private String loginAccount;
  
  private String preSearchMapStr;  
  private int prePageNo;  
  private int prePageSize;

  /*
   * 到订单详情查询页
   */
  public String show() throws ActionException {
    return "orderItem";
  }

  /*
   * 查询订单详情
   */
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
  private BigDecimal paidDeposit;// 已付定金
  private BigDecimal finalPayment;// 尾款
  private BigDecimal noFinalPayment;// 已付尾款

  public String detail() throws ActionException {
    try {
      HttpSession session = getHttpServletRequest().getSession();
      SysUser sysuser = (SysUser) session.getAttribute("sysUser");
      loginAccount = sysuser.getUserName();
      order = orderQryService.getOrderByCode(orderCode);
      String pathfix = null;
      pathfix = SysConstants.sysChannelToWebsiteMap.get(order.getOrderChannel()) + "/products/";
      this.setCmsPagePath(pathfix);
      Map<String, BigDecimal> payInfo = orderPayStatementService.getOrderPayInfo(orderCode);
      orderpay = payInfo.get("ORDERPAY");
      couponpay = payInfo.get("COUPONPAY");
      bankpay = payInfo.get("BANKPAY");
      platformpay = payInfo.get("PLATFORMPAY");
      reservepay = payInfo.get("RESERVEPAY");
      balancepay = payInfo.get("BALANCEPAY");
      onlinepay = payInfo.get("ONLINEPAY");
      notpay = order.getAmountPayable().subtract(orderpay);
      payable = order.getCommoditySum().add(order.getFare());
      // discount = order.getDiscountAmount().add(couponpay);
      discount = order.getDiscountAmount();
      // 此处不能用payable去减discount，由于payable是此类的成员变量的问题，会改变payable的值
      actualpay = order.getCommoditySum().add(order.getFare()).subtract(discount)
          .subtract(order.getOrderDiscount());
      // 待支付尾款状态： 尾款 = （应付金额） - （预留定金）
      if (order.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Nopay_FinalMoney
          .getKey() || order.getOrderType().intValue() == 7) {

        finalPayment = order.getAmountPayable().subtract(order.getDepositSum());
        paidDeposit = orderQryService.getOrderPaidDepositByCode(orderCode);
        noFinalPayment = orderQryService.getOrderMoneyByCode(orderCode);
      }
      // orderpay = orderpay.subtract(couponpay);
      items = orderItemQryService.listOrderItems(orderCode);
      if (order.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Pay_Done.getKey()) {
        for (Object it : items) {
          OrderItem item = (OrderItem) it;
          if (null != item.getExtAttrType() && item.getExtAttrType()
              .intValue() == OrderDictionaryEnum.OrderItemExtAttrType.includeMedic.getKey()) {
            isExt = true;
            break;
          }
        }
      }
      operates = orderItemQryService.listOrderOperates(orderCode);
      pays = orderItemQryService.listOrderPays(orderCode);
      preferentials = orderItemQryService.listOrderPreferentials(orderCode, null);
      fullDdiscount = BigDecimal.ZERO;// 满减
      plusDiscount = BigDecimal.ZERO;// 加价购
      for (OrderPreferential pf : (List<OrderPreferential>) preferentials) {
        if (null != pf.getOrderPreferentialSum()) {
          if (pf.getOrderPreferentialType()
              .intValue() == OrderDictionaryEnum.OrderPreferentialType.Cut.getKey()) {
            fullDdiscount = fullDdiscount.add(pf.getOrderPreferentialSum());
          } else if (pf.getOrderPreferentialType()
              .intValue() == OrderDictionaryEnum.OrderPreferentialType.INCREASE.getKey()) {
            plusDiscount = plusDiscount.add(pf.getOrderPreferentialSum());
          }
        }
      }
      actualpay = actualpay.add(plusDiscount);
      invoice = orderItemQryService.getInvoiceById(order.getInvoiceId());
      invoiceItems = orderItemQryService.listOrderInvoiceItems(order.getInvoiceId());
      // 预售补单
      int orderStatus = order.getOrderStatus().intValue(); // 订单状态
      List<String> States = new ArrayList<String>();
      if (orderStatus == OrderDictionaryEnum.Order_Status.Nopay_FinalMoney.getKey()) {// 尾款支付补单
        for (OrderPayStatementVo ps : pays) {
          if (null != ps.getYsFlage() && "2".equals(ps.getYsFlage())) {
            States.add(ps.getState().toString());
          }
        }
        if (States.size() > 1) { // 即如果尾款支付有两条支付流水，则不能补单
          isAdditional = false;
        } else if (States.size() == 1 && States.get(0).equals("4")) {
          isAdditional = true;
        }

      } else {
        try {
          Map admap = new HashMap();
          admap.put("orderCode", orderCode);
          admap.put("flag",
              order.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Cancel_Done
                  .getKey()
                      ? OrderDictionaryEnum.OrderPayFlag.Refundment.getKey()
                      : OrderDictionaryEnum.OrderPayFlag.Payment.getKey());
          isAdditional = orderPayStatementService.checkIsAdditional(admap);
        } catch (Exception e1) {
          log.error("查询订单" + orderCode + "是否需要补单异常：", e1);
          isAdditional = Boolean.FALSE;
        }
      }

    } catch (Exception e) {
      log.error("查询订单" + orderCode + "订单明细异常：", e);
      throw new ActionException();
    }

    try {
      expressSubscription = null;
      expressSubscription = orderItemQryService
          .queryOrderItemLogisticsInfo(order.getLogisticsCode(), order.getLogisticsOrderNo());
    } catch (Exception e) {
      log.error("查询订单物流信息发生异常：", e);
    }
    if (null == expressSubscription) {
      expressSubscription = new ExpressSubscription();
      expressSubscription.setLogisticsName(order.getLogisticsName());
      expressSubscription.setLogisticsCode(order.getLogisticsCode());
      expressSubscription.setLogisticsNo(order.getLogisticsOrderNo());
    }
    return "orderItem";
  }

  /**
   * 风险评估详情
   * 
   * @return
   */
  public String gotoOrderRiskDetail() {
    try {
      HttpServletRequest request = getHttpServletRequest();
      SysUser sysuser = (SysUser) request.getSession().getAttribute("sysUser");
      request.setAttribute("sysuser", sysuser);
      Date now = new Date();
      request.setAttribute("now", now);
      order = orderQryService.getOrderByCode(orderCode);
      String pathfix = null;
      pathfix = SysConstants.sysChannelToWebsiteMap.get(order.getOrderChannel()) + "/products/";
      this.setCmsPagePath(pathfix);
      Map<String, BigDecimal> payInfo = orderPayStatementService.getOrderPayInfo(orderCode);
      orderpay = payInfo.get("ORDERPAY");
      couponpay = payInfo.get("COUPONPAY");
      bankpay = payInfo.get("BANKPAY");
      platformpay = payInfo.get("PLATFORMPAY");
      reservepay = payInfo.get("RESERVEPAY");
      balancepay = payInfo.get("BALANCEPAY");
      onlinepay = payInfo.get("ONLINEPAY");
      notpay = order.getAmountPayable().subtract(orderpay);
      payable = order.getCommoditySum().add(order.getFare());
      discount = order.getDiscountAmount();
      actualpay = order.getCommoditySum().add(order.getFare()).subtract(discount)
          .subtract(order.getOrderDiscount());
      items = orderItemQryService.listOrderItems(orderCode);
      preferentials = orderItemQryService.listOrderPreferentials(orderCode, null);
      invoice = orderItemQryService.getInvoiceById(order.getInvoiceId());
      List<OrderRiskScore> orsList = orderRiskScoreService.queryOrderRiskScore(orderCode);
      getHttpServletRequest().setAttribute("orderRiskScoreList", orsList);
      fullDdiscount = BigDecimal.ZERO;// 满减
      plusDiscount = BigDecimal.ZERO;// 加价购
      for (OrderPreferential pf : (List<OrderPreferential>) preferentials) {
        if (null != pf.getOrderPreferentialSum()) {
          if (pf.getOrderPreferentialType()
              .intValue() == OrderDictionaryEnum.OrderPreferentialType.Cut.getKey()) {
            fullDdiscount = fullDdiscount.add(pf.getOrderPreferentialSum());
          } else if (pf.getOrderPreferentialType()
              .intValue() == OrderDictionaryEnum.OrderPreferentialType.INCREASE.getKey()) {
            plusDiscount = plusDiscount.add(pf.getOrderPreferentialSum());
          }
        }
      }
      actualpay = actualpay.add(plusDiscount);
      System.out.println("before---------"+preSearchMapStr);
      preSearchMapStr =  preSearchMapStr.replaceAll("\"", "%22");
      System.out.println("after---------"+preSearchMapStr);
    } catch (Exception e) {
      log.error("查询订单" + orderCode + "订单明细异常：", e);
    }
    return SUCCESS;
  }

  /**
   * 风险评估
   * 
   * @return
   */
  public void orderRisk() {
    try {
      HttpServletRequest request = getHttpServletRequest();
      boolean success = "1".equals(request.getParameter("checkResult"));
      String estimateContent = request.getParameter("estimateContent");
      if (!success && (null == estimateContent || estimateContent.length() == 0)) {
        return;// 异常时评估内容非空
      }
      long status = success
          ? OrderDictionaryEnum.Order_Status.Risk_Pass.getKey()
          : OrderDictionaryEnum.Order_Status.Exception_Order.getKey();
      HttpSession session = request.getSession();
      SysUser sysuser = (SysUser) session.getAttribute("sysUser");
      OrderOperateStatement ops = new OrderOperateStatement();
      ops.setOrderCode(orderCode);
      ops.setNowOperateDate(new Date());
      ops.setNowOperateType(
          (long) OrderDictionaryEnum.OrderOperateType.ORDERRISK_EVALUATE.getKey());
      ops.setNowOperator(sysuser.getUserName());
      ops.setNowOrderStatus(status);
      ops.setPreviousOrderStatus((long) OrderDictionaryEnum.Order_Status.Risk_Appraise.getKey());
      ops.setPreviousOperator(sysuser.getUserName());
      // ops.setOperateInfo("执行订单风控，结果为" + (success ? "风控通过" : "异常订单"));
      ops.setOperateInfo(success ? "订单人工风险评估通过" : "订单人工风险评估不通过");
      if (orderOperateStatementService.orderRiskCheckUpdateOrder(orderCode, status, estimateContent,
          ops)) {
        getHttpServletResponse().getWriter().print(1);
      }
    } catch (Exception e) {
      log.error("风险评估发生异常", e);
    }
  }

  public String showRemark() throws ActionException {
    try {
      order = orderQryService.getOrderByCode(orderCode);
    } catch (ServiceException e) {
      log.error("查询订单" + orderCode + "订单备注异常：", e);
      throw new ActionException();
    }
    return "remark";
  }



  public String orderItemOutAction() throws ActionException {
    try {
      order = orderQryService.getOrderByCode(orderCode);
      String pathfix = null;
      pathfix = SysConstants.sysChannelToWebsiteMap.get(order.getOrderChannel()) + "/products/";
      this.setCmsPagePath(pathfix);
      Map<String, BigDecimal> payInfo = orderPayStatementService.getOrderPayInfo(orderCode);
      orderpay = payInfo.get("ORDERPAY");
      couponpay = payInfo.get("COUPONPAY");
      bankpay = payInfo.get("BANKPAY");
      platformpay = payInfo.get("PLATFORMPAY");
      reservepay = payInfo.get("RESERVEPAY");
      balancepay = payInfo.get("BALANCEPAY");
      onlinepay = payInfo.get("ONLINEPAY");
      notpay = order.getAmountPayable().subtract(orderpay);
      payable = order.getCommoditySum().add(order.getFare());
      discount = order.getDiscountAmount();
      actualpay = order.getCommoditySum().add(order.getFare()).subtract(discount)
          .subtract(order.getOrderDiscount());
      items = orderItemQryService.listOrderItemsOut(orderCode);
      fullDdiscount = BigDecimal.ZERO;// 满减
      plusDiscount = BigDecimal.ZERO;// 加价购
      preferentials = orderItemQryService.listOrderPreferentials(orderCode, null);
      for (OrderPreferential pf : (List<OrderPreferential>) preferentials) {
        if (null != pf.getOrderPreferentialSum()) {
          if (pf.getOrderPreferentialType()
              .intValue() == OrderDictionaryEnum.OrderPreferentialType.Cut.getKey()) {
            fullDdiscount = fullDdiscount.add(pf.getOrderPreferentialSum());
          } else if (pf.getOrderPreferentialType()
              .intValue() == OrderDictionaryEnum.OrderPreferentialType.INCREASE.getKey()) {
            plusDiscount = plusDiscount.add(pf.getOrderPreferentialSum());
          }
        }
      }
      actualpay = actualpay.add(plusDiscount);
    } catch (Exception e) {
      log.error("查询订单" + orderCode + "订单明细异常：", e);
      throw new ActionException();
    }
    return "orderItem";
  }

  // 到修改订单信息页
  public String toChangeInfo() throws ActionException {
    try {
      order = orderQryService.getOrderByCode(orderCode);
    } catch (ServiceException e) {
      log.error("查询需要修改的订单" + orderCode + "订单信息异常：", e);
      throw new ActionException();
    }
    return "orderInfo";
  }

  // 到修改运费
  public String toChangeFee() throws ActionException {
    try {
      order = orderQryService.getOrderByCode(orderCode);
    } catch (ServiceException e) {
      log.error("查询需要修改的订单" + orderCode + "订单信息异常：", e);
      throw new ActionException();
    }
    return "orderLogisticsFee";
  }

  // 到审核页
  public String toAudit() throws ActionException {
    try {
      order = orderQryService.getOrderByCode(orderCode);
    } catch (ServiceException e) {
      log.error("查询需要审核的订单" + orderCode + "订单信息异常：", e);
      throw new ActionException();
    }
    return "audit";
  }

  // 格式化数字显示
  public String formatDouble(double s) {
    DecimalFormat fmt = new DecimalFormat("##");
    return fmt.format(s);
  }

  /*
   * ------------------------------------------------------------------------ getters and setters
   */
  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
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

  public ExpressSubscription getExpressSubscription() {
    return expressSubscription;
  }

  public void setExpressSubscription(ExpressSubscription expressSubscription) {
    this.expressSubscription = expressSubscription;
  }

  public List getRouters() {
    return routers;
  }

  public void setRouters(List routers) {
    this.routers = routers;
  }

  public Map<String, String> getMap() {
    return map;
  }

  public void setMap(Map<String, String> map) {
    this.map = map;
  }

  public Boolean getIsAdditional() {
    return isAdditional;
  }

  public void setIsAdditional(Boolean isAdditional) {
    this.isAdditional = isAdditional;
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

  public BigDecimal getActualpay() {
    return actualpay;
  }

  public void setActualpay(BigDecimal actualpay) {
    this.actualpay = actualpay;
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

  public BigDecimal getCouponpay() {
    return couponpay;
  }

  public void setCouponpay(BigDecimal couponpay) {
    this.couponpay = couponpay;
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

  public BigDecimal getOrderpay() {
    return orderpay;
  }

  public void setOrderpay(BigDecimal orderpay) {
    this.orderpay = orderpay;
  }

  public BigDecimal getNotpay() {
    return notpay;
  }

  public void setNotpay(BigDecimal notpay) {
    this.notpay = notpay;
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

  public BigDecimal getReservepay() {
    return reservepay;
  }

  public void setReservepay(BigDecimal reservepay) {
    this.reservepay = reservepay;
  }

  public Boolean getIsExt() {
    return isExt;
  }

  public void setIsExt(Boolean isExt) {
    this.isExt = isExt;
  }

  public BigDecimal getPaidDeposit() {
    return paidDeposit;
  }

  public void setPaidDeposit(BigDecimal paidDeposit) {
    this.paidDeposit = paidDeposit;
  }

  public BigDecimal getFinalPayment() {
    return finalPayment;
  }

  public void setFinalPayment(BigDecimal finalPayment) {
    this.finalPayment = finalPayment;
  }

  public BigDecimal getNoFinalPayment() {
    return noFinalPayment;
  }

  public void setNoFinalPayment(BigDecimal noFinalPayment) {
    this.noFinalPayment = noFinalPayment;
  }

public String getPreSearchMapStr() {
    return preSearchMapStr;
}

public void setPreSearchMapStr(String preSearchMapStr) {
    this.preSearchMapStr = preSearchMapStr;
}

public int getPrePageNo() {
    return prePageNo;
}

public void setPrePageNo(int prePageNo) {
    this.prePageNo = prePageNo;
}

public int getPrePageSize() {
    return prePageSize;
}

public void setPrePageSize(int prePageSize) {
    this.prePageSize = prePageSize;
}

  
  /*
   * ------------------------------------------------------------------------
   */
}
