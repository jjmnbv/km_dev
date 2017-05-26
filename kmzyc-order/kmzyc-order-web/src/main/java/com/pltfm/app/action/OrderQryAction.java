package com.pltfm.app.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.commons.exception.ServiceException;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.service.OrderOperateStatementService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.OrderRiskKey;
import com.pltfm.sys.model.SysUser;
import com.pltfm.sys.util.StringUtil;

@Controller("orderQryAction")
@Scope("prototype")
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderQryAction extends BaseAction {
  private static final long serialVersionUID = 1L;
  private static final String SOURCE = "FANLI";
  private Logger log = Logger.getLogger(OrderQryAction.class);

  @Resource
  private OrderQryService orderQryService;

  @Resource
  private OrderOperateStatementService orderOperateStatementService;

  @Resource
  private OrderMainDAO orderMainDAO;

  private Map<String, String> map = new HashMap<String, String>();// 查询条件map
  
  private Map<String, String> preSearchMap;// 保存前一次查询条件
  
  private String preSearchMapStr;
  
  private int prePageNo;
  
  private int prePageSize;

  private Long orderId;

  private String orderCode;

  private String orderOperationRemark;

  private BigDecimal totalMoney;// 订单总金额

  private BigDecimal totalActual;// 实付总额

  private BigDecimal RebateMoney;

  private String loginAccount;

  private static final OrderDictionaryEnum.Order_Status[] orderStatus =
      OrderDictionaryEnum.Order_Status.values();// 订单状态

  private static final OrderDictionaryEnum.OrderPurchaserType[] purchaserTypes =
      OrderDictionaryEnum.OrderPurchaserType.values();// 下单类型

  private static final OrderDictionaryEnum.OrderSaleTypes[] salesTypes =
      OrderDictionaryEnum.OrderSaleTypes.values();// 销售类型

  private static final OrderDictionaryEnum.Order_Type[] orderType =
      OrderDictionaryEnum.Order_Type.values();

  /*
   * 到订单列表查询页
   */
  public String show() throws ActionException {
    return "orderList";
  }

  /**
   * 到订单列表查询页(按状态)
   */
  public String showByStatus() throws ActionException {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.putAll(map);
    Map session = ActionContext.getContext().getSession();
    SysUser sysuser = (SysUser) session.get("sysUser");
    paramMap.put("sysuser", sysuser);
    try {
      paramMap.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      paramMap.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
      getPage().setRecordCount(orderQryService.listCount(paramMap));
      getPage().setDataList(orderQryService.listOrder(paramMap));
      totalMoney = orderQryService.listCountMoney(paramMap);
      totalActual = orderQryService.listCountActual(paramMap);
    } catch (Exception e) {
      log.info(e.getMessage());
      throw new ActionException();
    }
    return "orderList";
  }

  /**
   * 查询订单列表ByMap
   */
  public String listByMap() throws ActionException {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    Map<String, String> tempMap = new HashMap<String, String>();
    String nstatus = getHttpServletRequest().getParameter("nstatus");// 非XX状态
    if (StringUtil.gbStrLen(nstatus) > 0 && nstatus.indexOf(',') > 0) {
      List nsList = new ArrayList();
      for (String temp : nstatus.split(",")) {
        nsList.add(temp);
      }
      paramMap.put("nstatus", nsList);
    }
    // 没有指定日期，根据日期范围查找，有开始结束日期，按日期查找
    if (StringUtils.isEmpty(map.get("payDateStart"))
        && StringUtils.isEmpty(map.get("payDateEnd"))) {
      DateTimeUtils.genDateQuery(map.get("payDateRange"), "payDateStart", "payDateEnd", map);
    } else {
        tempMap.put("payDateRange",map.get("payDateRange"));
      map.remove("payDateRange");
    }
    
    // 没有指定日期，根据日期范围查找，有开始结束日期，按日期查找
    if (StringUtils.isEmpty(map.get("createDateStart"))
        && StringUtils.isEmpty(map.get("createDateEnd"))) {
      DateTimeUtils.genDateQuery(map.get("createDateRange"), "createDateStart", "createDateEnd",
          map);
    } else {
      tempMap.put("createDateRange",map.get("createDateRange"));
      map.remove("createDateRange");
    }

    paramMap.putAll(map);
    try {
      Map session = ActionContext.getContext().getSession();
      SysUser sysuser = (SysUser) session.get("sysUser");
      loginAccount = sysuser.getUserName();
      paramMap.put("sysuser", sysuser);
      paramMap.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      String pageSize = map.get("pageSize");// 有效订单默认pageSize 50
      if (null != pageSize && !"".equals(pageSize)) {
        paramMap.put("end", pageSize);
        map.put("p50", "true");
      } else {
        paramMap.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
        map.put("p50", "false");
      }
      getPage().setRecordCount(orderQryService.listCountFuzzyOrder(paramMap));
      getPage().setDataList(orderQryService.listFuzzyOrder(paramMap));
      HashMap<String, Object> moneyM =

          (HashMap<String, Object>) orderQryService.listCountFuzzy(paramMap);
      totalMoney = (BigDecimal) moneyM.get("ORDERMONEY");
      totalActual = (BigDecimal) moneyM.get("ACTUALMONEY");
      map.put("nstatus", nstatus);
      if(!map.containsKey("createDateRange")){
          map.put("createDateRange",tempMap.get("createDateRange"));
      }
      if(!map.containsKey("payDateRange")){
          map.put("payDateRange",tempMap.get("payDateRange"));
      }
      //查询结果返回，保持页面当初查询条件
    } catch (Exception e) {
      log.error("查询订单列表发生错误", e);
      throw new ActionException();
    }
    return "orderList";
  }

  /**
   * 风控订单列表
   * 
   * @return
   * @throws ActionException
   */
  public String gotoOrderRiskList() throws ActionException {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    String from = getHttpServletRequest().getParameter("from");
    if(from!=null){
        //来自风控通过或不通过
      String mapStr=getHttpServletRequest().getParameter("mapStr");
      Map jsonMap = JSON.parseObject(mapStr);
      map = jsonMap;
    }
    preSearchMap = map;
    preSearchMapStr =JSON.toJSONString(preSearchMap);
    
   /* System.out.println("--------"+preSearchMap.toString());
    System.out.println("+++++++"+JSON.toJSONString(preSearchMap));
    Map jsonMap = JSON.parseObject(JSON.toJSONString(preSearchMap));
    System.out.println("sssss------------"+jsonMap.toString());*/
    
    DateTimeUtils.genDateQuery(map.get("payDateRange"), "payDateStart", "payDateEnd", map);
    DateTimeUtils.genDateQuery(map.get("createDateRange"), "createDateStart", "createDateEnd", map);
    String statusStr = null;
    if (null != (statusStr = map.get("status")) && !"null".equals(statusStr)
        && statusStr.length() > 0) {
      Integer status = Integer.parseInt(statusStr);
      if (OrderRiskKey.RISK_STATUS.indexOf(status) < 0) {
        paramMap.put("statusList", OrderRiskKey.RISK_STATUS);
      }
    } else {
      paramMap.put("statusList", OrderRiskKey.RISK_STATUS);
    }
    paramMap.putAll(map);
    try {
      Map session = ActionContext.getContext().getSession();
      SysUser sysuser = (SysUser) session.get("sysUser");
      loginAccount = sysuser.getUserName();
      paramMap.put("sysuser", sysuser);
     //if(from==null){
         //非从风控详情返回
         prePageNo = getPage().getPageNo();
         prePageSize =  getPage().getPageSize();
      //}
      paramMap.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      String pageSize = map.get("pageSize");
      if (null != pageSize && !"".equals(pageSize)) {
        //?
        paramMap.put("end", pageSize);
        map.put("p50", "true");
      } else {
        paramMap.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
        map.put("p50", "false");
      }
      int count = orderQryService.listCountFuzzyOrder(paramMap);
      System.out.println(count+"---before---"+getPage().getPageNo());
      if(from!=null&&(count%getPage().getPageSize())==0&&getPage().getPageNo()>1){
          //来自风控通过或不通过
          System.out.println(count+"------"+getPage().getPageNo());
          getPage().setPageNo(getPage().getPageNo()-1);
          paramMap.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
          if (null != pageSize && !"".equals(pageSize)) {
              paramMap.put("end", pageSize);
              map.put("p50", "true");
            } else {
              paramMap.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
              map.put("p50", "false");
            }
          count = orderQryService.listCountFuzzyOrder(paramMap);
      }
      getPage().setRecordCount(count);
      paramMap.put("fkChange", "changeSort");
      getPage().setDataList(orderQryService.listFuzzyOrder(paramMap));
      HashMap<String, Object> moneyM =
          (HashMap<String, Object>) orderQryService.listCountFuzzy(paramMap);
      totalMoney = (BigDecimal) moneyM.get("ORDERMONEY");
      totalActual = (BigDecimal) moneyM.get("ACTUALMONEY");     
      
    } catch (Exception e) {
      log.error("风控订单列表发生错误", e);
      throw new ActionException();
    }
    return "orderRiskList";
  }

  /**
   * 查询订单列表ByMap
   */
 /*删除返利网业务  public String order51FanliListByMap() throws ActionException {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    String nstatus = getHttpServletRequest().getParameter("nstatus");// 非XX状态
    if (StringUtil.gbStrLen(nstatus) > 0 && nstatus.indexOf(',') > 0) {
      List nsList = new ArrayList();
      for (String temp : nstatus.split(",")) {
        nsList.add(temp);
      }
      paramMap.put("nstatus", nsList);
    }
    // 没有指定日期，根据日期范围查找，有开始结束日期，按日期查找
    if (StringUtils.isEmpty(map.get("payDateStart"))
        && StringUtils.isEmpty(map.get("payDateEnd"))) {
      DateTimeUtils.genDateQuery(map.get("payDateRange"), "payDateStart", "payDateEnd", map);
    } else {
      map.remove("payDateRange");
    }
    // 没有指定日期，根据日期范围查找，有开始结束日期，按日期查找
    if (StringUtils.isEmpty(map.get("createDateStart"))
        && StringUtils.isEmpty(map.get("createDateEnd"))) {
      DateTimeUtils.genDateQuery(map.get("createDateRange"), "createDateStart", "createDateEnd",
          map);
    } else {
      map.remove("createDateRange");
    }

    map.put("source", SOURCE);
    paramMap.putAll(map);
    try {
      Map session = ActionContext.getContext().getSession();
      SysUser sysuser = (SysUser) session.get("sysUser");
      loginAccount = sysuser.getUserName();
      paramMap.put("sysuser", sysuser);
      paramMap.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      paramMap.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
      getPage().setRecordCount(orderQryService.listCountFuzzyOrder(paramMap));
      getPage().setDataList(orderQryService.listFuzzyOrder(paramMap));
      totalMoney = orderQryService.listCountFuzzyOrderMoney(paramMap);
      totalActual = orderQryService.listCountFuzzyOrderActual(paramMap);
      RebateMoney = orderQryService.listCountFuzzyOrderRebateMoney(paramMap);
      map.put("nstatus", nstatus);
    } catch (Exception e) {
      log.error("查询订单列表发生错误", e);
      throw new ActionException();
    }
    return "orderList";
  }*/

  /**
   * 进入批量同步订单信息页
   * 
   * @return
   */
  /*删除总部会员对接 public String preBatchSyncOrderInfo() {
    return "preBatchSyncSuccess";
  }*/

  /**
   * 查询预售订单信息列表
   * 
   * @return
   * @throws ActionException
   */
  public String qryYsOrderPageList() throws ActionException {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    Map session = ActionContext.getContext().getSession();
    SysUser sysuser = (SysUser) session.get("sysUser");
    try {
      // 查询取现记录数
      paramMap.putAll(map);
      paramMap.put("sysuser", sysuser);
      paramMap.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1));
      paramMap.put("end", (getPage().getPageNo() * getPage().getPageSize()));
      int count = orderQryService.listCountFuzzyOrder(paramMap);
      getPage().setRecordCount(count);
      getPage().setDataList(orderQryService.listFuzzyOrder(paramMap));
    } catch (Exception ex) {
      log.error("查询预售订单信息列表异常:" + ex.getMessage());
      throw new ActionException("预售订单信息列表异常：" + ex.getMessage());
    }
    return "PageList";
  }


  /**
   * 同步已支付订单信息
   */
 /*删除总部会员对接 public void syncOrder2Base() {
    JSONObject jsonResult = new JSONObject();
    String splitChar = "，";
    int result = 0;
    try {
      String orderCode = map.get("orderCode");
      List<String> lstOrderCode = new ArrayList<String>();
      // 批量
      if (StringUtils.isNotEmpty(orderCode)) {
        if (orderCode.indexOf(splitChar) != -1) {
          lstOrderCode = Arrays.asList(orderCode.split(splitChar));
        } else {
          lstOrderCode.add(orderCode);
        }
        result = orderQryService.syncOrderInfo2Base(lstOrderCode);
      }
      if (result > 0) {
        jsonResult.put("syncResult", "SUCESS"); // 同步结果
      } else {
        jsonResult.put("syncResult", "FAIL"); // 同步结果
      }

      writeJson(jsonResult);
    } catch (Exception e) {
      log.error("++++++++++++++" + e);
    }
  }*/

  /**
   * 查询异常订单列表
   * 
   * @return
   * @throws ActionException
   */
  public String qryExceptionOrderPageList() throws ActionException {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    Map session = ActionContext.getContext().getSession();
    SysUser sysuser = (SysUser) session.get("sysUser");
    try {
      // 查询取现记录数
      paramMap.putAll(map);
      paramMap.put("sysuser", sysuser);
      paramMap.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1));
      paramMap.put("end", (getPage().getPageNo() * getPage().getPageSize()));
      int count = orderQryService.listCountFuzzyOrder(paramMap);
      getPage().setRecordCount(count);
      getPage().setDataList(orderQryService.listFuzzyOrder(paramMap));
    } catch (Exception ex) {
      log.error("查询物流订阅列表异常:" + ex.getMessage());
      throw new ActionException("物流信息订阅异常：" + ex.getMessage());
    }
    return "PageList";
  }


  /**
   * 用于添加异常订单
   * 
   * @return
   * @throws ActionException
   */
  public String initAddExceptionOrder() throws ActionException {
    return "InsertPage";
  }

  /**
   * 用于添加异常订单
   * 
   * @return
   * @throws ActionException
   */
  public String addExceptionOrder() throws ActionException {
    Map session = ActionContext.getContext().getSession();
    SysUser sysuser = (SysUser) session.get("sysUser");
    try {
      // 根据订单号查询订单
      OrderMain orderMain = orderQryService.getOrderByCode(orderCode);
      if (orderMain != null) {
        orderMain
            .setOrderStatus((long) (OrderDictionaryEnum.Order_Status.Exception_Order.getKey()));
        orderMain.setOrderOperationRemark(orderOperationRemark);
        orderOperateStatementService.saveExceptionOrder(sysuser.getUserName(), orderMain);
      }
      map.put("orderCode", orderCode);
    } catch (Exception ex) {
      log.error("恢复异常订单失败" + ex.getMessage());
    }
    return this.qryExceptionOrderPageList();
  }

  /**
   * 恢复异常订单
   * 
   * @return
   * @throws ActionException
   */
  public void renewExceptionOrder() throws ActionException {
    Map session = ActionContext.getContext().getSession();
    SysUser sysuser = (SysUser) session.get("sysUser");
    JSONObject jsonResult = new JSONObject();
    int result = 0;
    try {
      result = orderOperateStatementService.renewExceptionOrder(sysuser.getUserName(),
          map.get("orderCode"));
      if (result > 0) {
        jsonResult.put("renewResult", "SUCESS");
      } else {
        jsonResult.put("renewResult", "FAIL");
      }
      writeJson(jsonResult);
    } catch (Exception ex) {
      log.error("恢复异常订单失败" + ex.getMessage());
    }
  }

  public void checkExceptionOrderCode() {
    try {
      String checkResult = "FLASE";
      HttpServletRequest request = super.getHttpServletRequest();
      String strOrderCode = request.getParameter("orderCode");
      if (StringUtils.isNotEmpty(strOrderCode)) {
        OrderMain orderMain = orderMainDAO.selectByOrderCode(strOrderCode);
        // 如果订单存在，并且不是父订单,不是被取消的订单
        if (orderMain != null
            && orderMain.getOrderStatus() != OrderDictionaryEnum.Order_Status.Split_Done.getKey()
            && orderMain.getOrderStatus() != OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()) {
          if (orderMain.getOrderStatus() == OrderDictionaryEnum.Order_Status.Exception_Order
              .getKey()) {
            checkResult = "DUPLICATE";
          } else {
            checkResult = "TRUE";
          }
        }
      }
      JSONObject jsonResult = new JSONObject();
      jsonResult.put("checkResult", String.valueOf(checkResult));
      writeJson(jsonResult);
    } catch (Exception ex) {
      log.error("校验新增的跟踪单据是否重复失败," + ex.getMessage());
    }
  }

  /**
   * 统计sku
   * 
   * @return
   */
  private Map skuMap;

  public Map getSkuMap() {
    return skuMap;
  }

  public void setSkuMap(Map skuMap) {
    this.skuMap = skuMap;
  }

  public Date getBegin() {
    return begin;
  }

  public void setBegin(Date begin) {
    this.begin = begin;
  }

  public Date getEnd() {
    return end;
  }

  public void setEnd(Date end) {
    this.end = end;
  }

  public String getSKU() {
    return SKU;
  }

  public void setSKU(String sKU) {
    SKU = sKU;
  }

  private Date begin;
  private Date end;
  private String SKU;

  public String countSKU() throws ActionException {
    try {
      if (null != SKU) {
        skuMap = orderQryService.countSKU(begin, end, Arrays.asList(SKU.split(",")));
      }
    } catch (ServiceException e) {
      log.error("发生错误", e);
      throw new ActionException();
    }
    return "skuList";
  }

  public String findMerchant() throws ActionException {
    Map<String, Object> commap = new LinkedHashMap<String, Object>();
    try {
      Map mapp = orderQryService.listCommercial(commap);
      writeJson(mapp);
    } catch (ServiceException e) {
      log.error("数据查询出错", e);
    }
    return null;
  }

  @Override
public void writeJson(Object obj) {
    String json = JSON.toJSONString(obj);
    strWriteJson(json);
  }

  @Override
protected void strWriteJson(String strJson) {
    ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
    PrintWriter pw = null;
    try {
      pw = ServletActionContext.getResponse().getWriter();
      pw.write(strJson);
      pw.flush();
    } catch (IOException e) {
      log.error("json输出异常", e);
    } finally {
      if (pw != null) {
        pw.close();
      }
    }
  }

  public Map<String, String> getMap() {
    return map;
  }

  public void setMap(Map<String, String> map) {
    this.map = map;
  }

  public Map<String, String> getPreSearchMap() {
    return preSearchMap;
}

public void setPreSearchMap(Map<String, String> preSearchMap) {
    this.preSearchMap = preSearchMap;
}

public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public OrderDictionaryEnum.Order_Status[] getOrderStatus() {
    return orderStatus;
  }

  public OrderDictionaryEnum.OrderPurchaserType[] getPurchaserTypes() {
    return purchaserTypes;
  }

  public OrderDictionaryEnum.OrderSaleTypes[] getSalestypes() {
    return salesTypes;
  }

  public OrderDictionaryEnum.Order_Type[] getOrderType() {
    return orderType;
  }

  public BigDecimal getTotalMoney() {
    return totalMoney;
  }

  public void setTotalMoney(BigDecimal totalMoney) {
    this.totalMoney = totalMoney;
  }

  public String getLoginAccount() {
    return loginAccount;
  }

  public void setLoginAccount(String loginAccount) {
    this.loginAccount = loginAccount;
  }

  public BigDecimal getTotalActual() {
    return totalActual;
  }

  public void setTotalActual(BigDecimal totalActual) {
    this.totalActual = totalActual;
  }

  public BigDecimal getRebateMoney() {
    return RebateMoney;
  }

  public void setRebateMoney(BigDecimal rebateMoney) {
    RebateMoney = rebateMoney;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getOrderOperationRemark() {
    return orderOperationRemark;
  }

  public void setOrderOperationRemark(String orderOperationRemark) {
    this.orderOperationRemark = orderOperationRemark;
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

}
