package com.pltfm.app.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.service.OrderOperateStatementService;
import com.pltfm.app.service.OrderPayStatementService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.util.SysConstants;
import com.pltfm.sys.model.SysUser;

import redis.clients.jedis.JedisCluster;


@Controller("orderPayAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class OrderPayAction extends BaseAction {
  private Logger log = Logger.getLogger(OrderPayAction.class);
  private static final long serialVersionUID = -8830703460290463030L;
  @Resource
  private OrderPayStatementService orderPayStatementService;
  @Resource
  private OrderOperateStatementService orderOperateStatementService;
  //客户系统远程方法
  @Resource
  private CustomerRemoteService customerRemoteService;
  @Resource
  private OrderQryService orderQryService;
  private String operator;
  private Long paymentWay;
  private String account;
  private String orderCode;
  private BigDecimal notPay;
  private BigDecimal orderMoney;
  private String outsidePayStatementNo;
  private Long flag;
  private BigDecimal preferentialNo;
  private BigDecimal grantID;
  private String bankName;
  private String platFormName;
  private String bankCode;
  private String platFormCode;
  private Long status;// 订单状态
  private Long disabled;// 订单可见状态
  private BigDecimal no;// 出库单号、物流单号

  private Map<String, String> infoMap = new HashMap<String, String>();// 订单信息
  
  @Resource(name = "jedisCluster")
  private JedisCluster jedisCluster;

  public String show() {
    try {
      notPay = orderPayStatementService.getNotPay(orderCode);
    } catch (ServiceException e) {
      log.error("发生错误", e);
    }
    return "pay";
  }

  public String showStockout() {
    return "stockout";
  }

  // 修改订单状态
  public void changeStaus() {
    int i = -1;
    try {
      i = orderOperateStatementService.changeOrderStatus(SysConstants.SYS, orderCode, status, no);
    } catch (ServiceException e) {
      log.error("修改订单状态发生错误", e);
    } catch (Exception e) {
      log.error("修改订单状态发生错误", e);
    }
    msg = 1 == i ? "更改订单状态成功！" : "更改订单状态失败！";
  }

  // 修改订单信息
  public void changeInfo() {
    int i = -1;
    try {
      i = orderOperateStatementService.changeOrderInfo(infoMap);
    } catch (ServiceException e) {
      log.error("修改订单信息发生错误", e);
    } catch (Exception e) {
      log.error("修改订单信息发生错误", e);
    }
    msg = 1 == i ? "更改订单信息成功！" : "更改订单信息失败！";
  }

  // 修改订单可见状态
  public void changeDisabled() {
    int i = -1;
    try {
      i = orderOperateStatementService.changeOrderDisabled(SysConstants.SYS, orderCode, disabled);
    } catch (Exception e) {
      log.info(e.getMessage());
    }
    msg = 1 == i ? "更改订单可见状态成功！" : "更改订单可见状态失败！";
  }

  // 补单
  public void additional() {
    int i = -1;
    try {
      //补单控制 -- 一个订单防止重复提交
      if(!jedisCluster.sismember("additionalOrderCode", orderCode)){
        jedisCluster.sadd("additionalOrderCode", orderCode);
      i = orderOperateStatementService.additional(orderCode);
//      if (1 == i) {
//        try {
//          String rsStr = orderQryService.queryUserBuyNumByOrderCode(orderCode);
//          if (null != rsStr) {
//            String[] rsArray = rsStr.split("_");
//            if (2 == rsArray.length && 1 == Integer.parseInt(rsArray[1])) {
//              Integer loginId = Integer.parseInt(rsArray[0]);
//              if (null != loginId) {
//                int rs = customerRemoteService.userFisrtShop(loginId.intValue());
//                if (rs == 1) {
//                  log.info("受邀用户" + loginId + "首次购物分销业务处理成功");
//                } else if (rs == -1) {
//                  log.error("受邀用户" + loginId + "首次购物分销业务处理发生异常");
//                } else {
//                  log.info(loginId + "不是受邀用户");
//                }
//              }
//            }
//          }
//        } catch (Exception e) {
//          log.error("受邀用户首次购物分销业务处理发生异常", e);
//        }
//      }
    }else{
      log.error("订单"+orderCode+"正在补单");
    }
    } catch (Exception e) {
      log.error("补单发生错误", e);
    }finally{
        jedisCluster.srem("additionalOrderCode", orderCode);
    }
    msg = 1 == i ? "补单成功！" : "补单失败！";
  }

  public void handle() {
    int i = -1;
    try {
      Map session = ActionContext.getContext().getSession();
      SysUser sysuser = (SysUser) session.get("sysUser");
      account = sysuser.getUserName();
      i = orderOperateStatementService.handle(orderCode, state, account, new Date());
    } catch (Exception e) {
      log.error("标记发生错误", e);
    }
    msg = 1 == i ? "标记成功！" : "标记失败！";
  }

  public void medicCheck() {
    int i = -1;
    try {
      Map session = ActionContext.getContext().getSession();
      SysUser sysuser = (SysUser) session.get("sysUser");
      account = sysuser.getUserName();
      i = orderOperateStatementService.medicCheck(orderCode, checkFlag, account, new Date());
    } catch (Exception e) {
      log.error("审核发生错误", e);
    }
    msg = 1 == i ? "审核操作成功！" : "审核操作失败！";
  }

  /**
   * 修改运费
   * 
   * @throws Exception
   */
  public void updateFare() throws Exception {
    boolean result = false;
    try {
      SysUser sysuser = (SysUser) ActionContext.getContext().getSession().get("sysUser");
      HttpServletRequest request = getHttpServletRequest();
      String fareStr = request.getParameter("fare");
      BigDecimal fare = null;
      if (null != orderCode && null != fareStr
          && (fare = new BigDecimal(fareStr)).compareTo(BigDecimal.ZERO) >= 0) {
        result = orderOperateStatementService.updateFare(orderCode, sysuser.getUserName(), fare);
      }
    } catch (Exception e) {
      log.error("修改订单运费发生异常", e);
    }
    msg = result ? "订单运费修改成功！" : "订单运费修改失败！";
  }

  // 处理标记
  private Short state;

  public Short getState() {
    return state;
  }

  public void setState(Short state) {
    this.state = state;
  }

  // 处理标记
  private Long checkFlag;

  public Long getCheckFlag() {
    return checkFlag;
  }

  public void setCheckFlag(Long checkFlag) {
    this.checkFlag = checkFlag;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public Long getPaymentWay() {
    return paymentWay;
  }

  public void setPaymentWay(Long paymentWay) {
    this.paymentWay = paymentWay;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public BigDecimal getOrderMoney() {
    return orderMoney;
  }

  public void setOrderMoney(BigDecimal orderMoney) {
    this.orderMoney = orderMoney;
  }

  public String getOutsidePayStatementNo() {
    return outsidePayStatementNo;
  }

  public void setOutsidePayStatementNo(String outsidePayStatementNo) {
    this.outsidePayStatementNo = outsidePayStatementNo;
  }

  public Long getFlag() {
    return flag;
  }

  public void setFlag(Long flag) {
    this.flag = flag;
  }

  public BigDecimal getPreferentialNo() {
    return preferentialNo;
  }

  public void setPreferentialNo(BigDecimal preferentialNo) {
    this.preferentialNo = preferentialNo;
  }

  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

  public BigDecimal getNo() {
    return no;
  }

  public void setNo(BigDecimal no) {
    this.no = no;
  }

  public BigDecimal getNotPay() {
    return notPay;
  }

  public void setNotPay(BigDecimal notPay) {
    this.notPay = notPay;
  }

  public Long getDisabled() {
    return disabled;
  }

  public void setDisabled(Long disabled) {
    this.disabled = disabled;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getPlatFormName() {
    return platFormName;
  }

  public void setPlatFormName(String platFormName) {
    this.platFormName = platFormName;
  }

  public String getBankCode() {
    return bankCode;
  }

  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }

  public String getPlatFormCode() {
    return platFormCode;
  }

  public void setPlatFormCode(String platFormCode) {
    this.platFormCode = platFormCode;
  }

  public BigDecimal getGrantID() {
    return grantID;
  }

  public void setGrantID(BigDecimal grantID) {
    this.grantID = grantID;
  }

  public Map<String, String> getInfoMap() {
    return infoMap;
  }

  public void setInfoMap(Map<String, String> infoMap) {
    this.infoMap = infoMap;
  }

  /*
   * ------------------------------------------------------------------------
   */
}
