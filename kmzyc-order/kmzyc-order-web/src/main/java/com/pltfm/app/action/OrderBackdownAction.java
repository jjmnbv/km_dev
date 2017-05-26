package com.pltfm.app.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.commons.exception.ServiceException;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.dao.OrderAlterPayStatementDAO;
import com.pltfm.app.entities.OrderAlter;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderMainExt;
import com.pltfm.app.service.OrderAlterOperateStatementService;
import com.pltfm.app.service.OrderItemQryService;
import com.pltfm.app.service.OrderOperateStatementService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.service.OrderReturnService;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.SysConstants;
import com.pltfm.sys.model.SysUser;

@Controller("orderBackdownAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class OrderBackdownAction extends BaseAction {
  private static final long serialVersionUID = 1L;

  private Logger log = Logger.getLogger(OrderBackdownAction.class);

  @Resource
  private OrderQryService orderQryService;
  @Resource
  private OrderItemQryService orderItemQryService;
  @Resource
  private OrderReturnService orderReturnService;
  @Resource
  private OrderOperateStatementService orderOperateStatementService;
  @Resource
  private OrderAlterOperateStatementService orderAlterOperateStatementService;
  @Resource
  private OrderAlterPayStatementDAO orderAlterPayStatementDAO;
  // 修改退换货单状态
  @Resource(name = "logisticsMap")
  private Map<String,String> logisticsMapt;
  
  private Map<String,String> logisticsMap;

  private final String showPath = SysConstants.RETURNSHOP_PHOTO_PATH;

  private OrderAlter alter;

  private String operator;

  private String orderCode;

  private String alterCode;

  private Integer returnPage;

  private Integer fromType;

  private Integer orderType;// 订单类型(预售工具时加入)

  private Long status;// 审批状态

  private Short type;// 通过、驳回

  private String name;// 联系人

  private String address;// 地址

  private BigDecimal addressId;

  private String phone;// 手机

  private Short backType;// 商品返回方式

  private String alterComment;// 问题描述

  private Short alterType;// 退换货标志

  private Long alterNum;// 更改数量

  private Long itemId;// 订单明细号

  private List items;// 订单商品

  private OrderItem item;

  private List operates;// 退换货单操作流水

  private List pays;// 退换货单支付流水

  private String batchNo;// 图片批次号

  private List photoList;

  private Long evidence;// 凭据类型

/*删除邮件功能  private String email;// 电子邮件
*/
  private String no;// 物流单号

  // private Address faddress;//地址

  private Map<String, String> map = new HashMap<String, String>();// 查询条件map

  private BigDecimal returnMoney;// 审核后退款金额
  private BigDecimal fareSubsidy;// 审核后运费补贴
  private BigDecimal preferentialAmount;// 审核后返回优惠券面值
  private BigDecimal returnFare; // 退货返运费
  private BigDecimal returnSum; // 退还总金额

  private String code;

  private String comment;// 审核说明

  private Boolean isSuit;// 当前订单是否包含活动商品

  private Boolean isAdditional;// 是否需要补单

  private static final OrderAlterDictionaryEnum.Propose_Status[] proposeStatus =
      OrderAlterDictionaryEnum.Propose_Status.values();// 退换货状态

  private static final OrderAlterDictionaryEnum.AlterTypes[] returnType =
      OrderAlterDictionaryEnum.AlterTypes.values();// 退换货类型

  private static final OrderAlterDictionaryEnum.BackTypes[] backTypes =
      OrderAlterDictionaryEnum.BackTypes.values();// 商品返回方式

  private Long maxnum;
  private String cmsPagePath = null;
  private OrderMain order;

  public Long getMaxnum() {
    return maxnum;
  }

  public void setMaxnum(Long maxnum) {
    this.maxnum = maxnum;
  }

  public String getCmsPagePath() {
    return cmsPagePath;
  }

  public void setCmsPagePath(String cmsPagePath) {
    this.cmsPagePath = cmsPagePath;
  }

  public OrderMain getOrder() {
    return order;
  }

  public void setOrder(OrderMain order) {
    this.order = order;
  }

  public String show() throws ActionException {
    try {
      item = orderItemQryService.getOrderItemById(itemId);
      order = orderQryService.getOrderByCode(item.getOrderCode());
      String pathfix = null;
      pathfix = SysConstants.sysChannelToWebsiteMap.get(order.getOrderChannel()) + "/products/";
      this.setCmsPagePath(pathfix);
      maxnum = item.getCommodityNumber() - orderItemQryService.selectOverplusNum(itemId);// 未退换数量
    } catch (ServiceException e) {
      log.error("发生错误！", e);
      throw new ActionException();
    }
    // return "backdown";
    return "apply";
  }

  /*
   * 到退换货申请
   */
  public String showBackDown() throws ActionException {
    try {
      items = orderItemQryService.listOrderItems(orderCode);
    } catch (Exception e) {
      log.error("到退换货申请发生错误！", e);
      throw new ActionException();
    }
    return "orderAlterItem";
  }

  // /*
  // * 后台到退换货申请
  // */
  // public String showBackDownBG() throws ActionException {
  // try {
  // //orderOperateStatementService.changeOrderStatus(SysConstants.SYS,
  // orderCode, (long) OrderDictionaryEnum.Order_Status.Order_Done.getKey(),
  // null);
  // items = orderItemQryService.listOrderItems(orderCode);
  // } catch (Exception e) {
  // log.error("到退换货申请发生错误！", e);
  // throw new ActionException();
  // }
  // return "orderAlterItem";
  // }

  /*
   * 查询退换货列表ByExample
   */
  public String listByMap() throws ActionException {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.putAll(map);
    Map session = ActionContext.getContext().getSession();
    SysUser sysuser = (SysUser) session.get("sysUser");
    paramMap.put("sysuser", sysuser);
    try {
      paramMap.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      paramMap.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
      getPage().setRecordCount(orderReturnService.listCount(paramMap));
      getPage().setDataList(orderReturnService.listAlter(paramMap));
    } catch (Exception e) {
      log.error("查询退换货列表发生错误！", e);
      throw new ActionException();
    }
    return "returnList";
  }

  /**
   * 
   * @return自营退换货列表
   * @throws ActionException
   */
  public String listBySelfMap() throws ActionException {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.putAll(map);
    Map session = ActionContext.getContext().getSession();
    SysUser sysuser = (SysUser) session.get("sysUser");
    paramMap.put("sysuser", sysuser);
    paramMap.put("kmself", 1);
    try {
      paramMap.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      paramMap.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
      getPage().setRecordCount(orderReturnService.listCount(paramMap));
      getPage().setDataList(orderReturnService.listAlter(paramMap));
    } catch (Exception e) {
      log.error("查询退换货列表发生错误！", e);
      throw new ActionException();
    }
    return "returnList";
  }

  /*
   * 到审核页
   */
  public String showBackdownCK() throws ActionException {
    try {
      alter = orderAlterOperateStatementService.getOrderAlterByCode(alterCode);
      operates = orderAlterOperateStatementService.listOrderAlterOperates(alterCode);
      pays = orderAlterOperateStatementService.listOrderAlterPays(alterCode);
      item = orderItemQryService.getOrderItemById(alter.getOrderItemId());
      order = orderQryService.getOrderByCode(item.getOrderCode());

      if (fromType != null && fromType == 2) {
        // 预售超时
        // 不需要操作什么，因为前台数据提交的时候已做好保存，查出直接用

      } else {
        if (alter.getProposeStatus()
            .intValue() == OrderAlterDictionaryEnum.Propose_Status.ExchangeToReturn.getKey()
            || alter.getProposeStatus().intValue() == OrderAlterDictionaryEnum.Propose_Status.Audit
                .getKey()) {
          // Map map =
          // orderReturnService.compute(alter.getOrderCode(), alter.getOrderItemId(), alter
          // .getAlterNum());
          // BigDecimal tmp = null == map.get("returnSum") ? null : (BigDecimal)
          // map.get("returnSum");
          // 商品退款金额 = 商品单件实收金额(订单明细表取值)*退货商品数量 (退换货表) //OrderReturnServiceImpl.compute() 方法中已做修改
          // OrderItem oi = orderItemQryService.getOrderItemById(alter.getOrderItemId());
          // BigDecimal tmp = BigDecimal.ZERO;
          // if(null != oi && null != alter){
          // BigDecimal unitIncoming = oi.getCommodityUnitIncoming() ==
          // null?BigDecimal.valueOf(0):oi.getCommodityUnitIncoming();
          // long alterNum = alter.getAlterNum() == null?0L:alter.getAlterNum();
          // tmp = oi.getCommodityUnitIncoming().multiply(new BigDecimal(oi.getCommodityNumber()));
          // }
          // alter.setRuturnMoney(tmp);
          alter.setFareSubsidy(BigDecimal.ZERO); // 补贴运费
          // alter.setRuturnSum(tmp);

          BigDecimal returnfareSum =
              orderAlterOperateStatementService.selectReturnFare(alter.getOrderCode());

          // 换货转退货需从新计算 (RuturnMoney) 商品退款金额 = 单品实收*退货数量
          // if (alter.getProposeStatus().intValue() ==
          // OrderAlterDictionaryEnum.Propose_Status.ExchangeToReturn.getKey()){
          BigDecimal unitIncoming = item.getCommodityUnitIncoming() == null
                  ? BigDecimal.valueOf(0)
                  : item.getCommodityUnitIncoming();
          long alterNum = alter.getAlterNum() == null ? 0L : alter.getAlterNum();
          BigDecimal rem = unitIncoming.multiply(new BigDecimal(alterNum));
          alter.setRuturnMoney(rem); // ----设置退还金额
          // }

          BigDecimal returnFare = BigDecimal.ZERO;
          if (null != order.getFare()) {
            returnFare = order.getFare().subtract(returnfareSum);
          }
          alter.setReturnFare(returnFare);
          BigDecimal temp =
              alter.getRuturnMoney() == null ? BigDecimal.ZERO : alter.getRuturnMoney();
          // alter.setRuturnSum(temp.add(returnFare));
          alter.setRuturnSum(temp); // ----退还总金额


        }
      }


      String pathfix = null;
      pathfix = SysConstants.sysChannelToWebsiteMap.get(order.getOrderChannel()) + "/products/";
      this.setCmsPagePath(pathfix);
      try {
        // 图片批次号
        photoList = orderReturnService.getPhotoByBatchNo(alter.getPhotoBatchNo());
      } catch (Exception e) {
        log.error("到审核页发生错误！", e);
      }
      isSuit = orderItemQryService.isSuit(alter.getOrderCode()); // 是否有套餐
      try {
        isAdditional = orderAlterPayStatementDAO.checkIsAdditional(alterCode); // 检查是否需要补单
      } catch (SQLException e1) {
        isAdditional = Boolean.FALSE;
      }
    } catch (Exception e) {
      log.error("到审核页发生错误！", e);
      throw new ActionException();
    }
    return "orderAlterItem";
  }

  // 退换货申请
  public void backDown() throws IOException {
    try {
      order = orderQryService.getOrderByCode(alter.getOrderCode());
      if (order.getOrderStatus().intValue() < 3) {
        log.error("订单此状态下不能申请退换货！");
        return;
      }
      boolean flg = true;
      if (order.getOrderStatus()
          .intValue() != (OrderDictionaryEnum.Order_Status.Order_Done.getKey())) {

        /*
         * orderOperateStatementService.changeOrderStatus(SysConstants.SYS_KEY,
         * alter.getOrderCode(), (long) OrderDictionaryEnum.Order_Status.Order_Done.getKey(), null);
         */
        log.info("订单号：" + order.getOrderCode());
        flg = orderOperateStatementService.OrderAutoSure(order.getOrderCode());// 调用自动确认方法同时添加同步数据
      }
      if (flg) {
        int rs = 0;
        rs = orderReturnService.alter(alter);
        if (rs == 1) {
          msg = "退换货申请成功！";
        } else {
          msg = "退换货申请失败！";
        }
      } else {
        msg = "退换货申请失败！原因修改订单状态未通过";
      }
    } catch (ServiceException e) {
      log.info(e.getMessage());
      msg = "退换货申请失败！";
    }
  }

  // 退换货审核
  public void backDownCK() throws IOException {
    try {
      orderReturnService.alterCK(SysConstants.SYS, alterCode, type, fareSubsidy, returnMoney,
          returnFare, returnSum, preferentialAmount, comment);
      msg = "退换货审核成功！";
    } catch (ServiceException e) {
      log.error("退换货审核发生错误！", e);
      msg = "退换货审核失败！";
    }

  }

  // 超时未发货赔付审核
  public void backDownCKYS() throws IOException {
    try {
      int result=orderReturnService.alterCKYS(SysConstants.SYS, alterCode, type, comment);
      if(result==1)
        msg = "超时未发货赔付审核成功！";
      else if(result==-1)
        msg = "因订单状态为已配送，不能通过超时未发货赔付审核";
    } catch (ServiceException e) {
      log.error("超时未发货赔付审核发生错误！", e);
      msg = "超时未发货赔付审核失败！";
    }

  }

  /**
   * 进入批量同步退换货单信息页
   * 
   * @return
   */
  /*删除总部会员对接  public String preBatchSyncOrderAlterInfo() {
    return "preBatchSyncSuccess";
  }


   public void syncOrderAlterInfo2Base() {
    JSONObject jsonResult = new JSONObject();
    String splitChar = "，";
    int result = 0;
    try {
      List<String> lstOrderAlterCode = new ArrayList<String>();
      // 批量
      if (StringUtils.isNotEmpty(alterCode)) {
        if (alterCode.indexOf(splitChar) != -1) {
          lstOrderAlterCode = Arrays.asList(alterCode.split(splitChar));
        } else {
          lstOrderAlterCode.add(alterCode);
        }
        result = orderReturnService.syncAlterOrderInfo2Base(lstOrderAlterCode);
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

  // 到换货物流信息页
  public String returnCode() {
    try {
      logisticsMap = logisticsMapt;
        
    } catch (Exception e) {
      log.error("到换货物流信息页发生错误！", e);
    }
    return "returnCode";
  }


  private String logisticsName;

  public void changeStatus() throws IOException {
    try {
      if (null != code) {
//        logisticsMap = LogisticsCompanyMap.getMap();
        logisticsName = logisticsMapt.get(code);
      }
      int i = orderAlterOperateStatementService.changeAlterStatus(SysConstants.SYS, setOA());
      if (1 == i) {
        msg = "更改退换货单状态成功！";
      } else {
        msg = "更改退换货单状态失败！";
      }
    } catch (ServiceException e) {
      log.error("更改退换货单状态发生错误！", e);
      msg = "更改退换货单状态失败！";
    }

  }

  // 预售超时及预售普通退换货-----确认退款
  public void changeYSSta() throws IOException {
    try {
      if (null != code) {
//        logisticsMap = LogisticsCompanyMap.getMap();
        logisticsName = logisticsMapt.get(code);
      }
      Map<String,String> map = orderAlterOperateStatementService.changeAlterStatusYS(SysConstants.SYS, setOA());
      if (map!=null&&map.containsKey("1")) {
        //msg = "确认退款成功！");
        msg=map.get("1").toString();
        System.out.println("-----------------"+map.get("1"));
      } else {
        msg = "确认退款失败！";
      }
    } catch (ServiceException e) {
      log.error("确认退款发生错误！", e);
      msg = "确认退款失败！";
    }

  }

  // 补单
  public void additional() {
    int i = -1;
    try {
      i = orderAlterOperateStatementService.additional(alterCode);
    } catch (Exception e) {
      log.error("补单发生错误！", e);
    }
    msg = 1 == i ? "补单成功！" : "补单失败！";
  }

  /**
   * 延迟收货
   * 
   * @return
   * @throws Exception
   */
  public void delayReceipt() throws Exception {
    SysUser sysuser = (SysUser) ActionContext.getContext().getSession().get("sysUser");
    HttpServletRequest request = getHttpServletRequest();
    String OrderCode = request.getParameter("OrderCode");
    String date = request.getParameter("date");
    OrderMainExt ome = new OrderMainExt();
    ome.setOrderCode(OrderCode);
    ome.setDelayReceipt(Integer.parseInt(date));
    msg = orderOperateStatementService.delayReceipt(sysuser.getUserName(), ome)
        ? "延迟收货成功！"
        : "延迟收货失败！";
  }

  private OrderAlter setOA() {
    // (SysConstants.SYS,alterCode, status,code,no,null,null,null,null,null
    // ,null,null,null,comment,logisticsName);
    OrderAlter oa = new OrderAlter();
    oa.setOrderAlterCode(alterCode);
    oa.setProposeStatus(status.shortValue());
    oa.setLogisticsCode(code);
    oa.setLogisticsOrderNo(no);
    oa.setLogisticsName(logisticsName);
    oa.setAuditComment(comment);
    return oa;
  }

  public OrderAlter getAlter() {
    return alter;
  }

  public void setAlter(OrderAlter alter) {
    this.alter = alter;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getAlterCode() {
    return alterCode;
  }

  public void setAlterCode(String alterCode) {
    this.alterCode = alterCode;
  }

  public Integer getReturnPage() {
    return returnPage;
  }

  public void setReturnPage(Integer returnPage) {
    this.returnPage = returnPage;
  }

  public Integer getFromType() {
    return fromType;
  }

  public void setFromType(Integer fromType) {
    this.fromType = fromType;
  }

  public Integer getOrderType() {
    return orderType;
  }

  public void setOrderType(Integer orderType) {
    this.orderType = orderType;
  }

  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

  public Short getType() {
    return type;
  }

  public void setType(Short type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Short getBackType() {
    return backType;
  }

  public void setBackType(Short backType) {
    this.backType = backType;
  }

  public String getAlterComment() {
    return alterComment;
  }

  public void setAlterComment(String alterComment) {
    this.alterComment = alterComment;
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

  public Map<String, String> getMap() {
    return map;
  }

  public void setMap(Map<String, String> map) {
    this.map = map;
  }

  public OrderAlterDictionaryEnum.Propose_Status[] getProposeStatus() {
    return proposeStatus;
  }

  public OrderAlterDictionaryEnum.AlterTypes[] getReturnType() {
    return returnType;
  }

  public OrderAlterDictionaryEnum.BackTypes[] getBackTypes() {
    return backTypes;
  }

  public Short getAlterType() {
    return alterType;
  }

  public void setAlterType(Short alterType) {
    this.alterType = alterType;
  }

  public Long getAlterNum() {
    return alterNum;
  }

  public void setAlterNum(Long alterNum) {
    this.alterNum = alterNum;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public OrderItem getItem() {
    return item;
  }

  public void setItem(OrderItem item) {
    this.item = item;
  }

  public String getBatchNo() {
    return batchNo;
  }

  public void setBatchNo(String batchNo) {
    this.batchNo = batchNo;
  }

  public Long getEvidence() {
    return evidence;
  }

  public void setEvidence(Long evidence) {
    this.evidence = evidence;
  }

  /*删除邮件功能  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
*/
  public BigDecimal getAddressId() {
    return addressId;
  }

  public void setAddressId(BigDecimal addressId) {
    this.addressId = addressId;
  }

  public String getNo() {
    return no;
  }

  public void setNo(String no) {
    this.no = no;
  }

  public BigDecimal getReturnMoney() {
    return returnMoney;
  }

  public void setReturnMoney(BigDecimal returnMoney) {
    this.returnMoney = returnMoney;
  }

  public BigDecimal getFareSubsidy() {
    return fareSubsidy;
  }

  public void setFareSubsidy(BigDecimal fareSubsidy) {
    this.fareSubsidy = fareSubsidy;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public List getPhotoList() {
    return photoList;
  }

  public void setPhotoList(List photoList) {
    this.photoList = photoList;
  }

  public String getShowPath() {
    return showPath;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public BigDecimal getPreferentialAmount() {
    return preferentialAmount;
  }

  public void setPreferentialAmount(BigDecimal preferentialAmount) {
    this.preferentialAmount = preferentialAmount;
  }

  public Map<String, String> getLogisticsMap() {
    return logisticsMap;
  }

  public void setLogisticsMap(Map<String, String> logisticsMap) {
    this.logisticsMap = logisticsMap;
  }

  public String getLogisticsName() {
    return logisticsName;
  }

  public void setLogisticsName(String logisticsName) {
    this.logisticsName = logisticsName;
  }

  public Boolean getIsSuit() {
    return isSuit;
  }

  public void setIsSuit(Boolean isSuit) {
    this.isSuit = isSuit;
  }

  public Boolean getIsAdditional() {
    return isAdditional;
  }

  public void setIsAdditional(Boolean isAdditional) {
    this.isAdditional = isAdditional;
  }

  public BigDecimal getReturnFare() {
    return returnFare;
  }

  public void setReturnFare(BigDecimal returnFare) {
    this.returnFare = returnFare;
  }

  public BigDecimal getReturnSum() {
    return returnSum;
  }

  public void setReturnSum(BigDecimal returnSum) {
    this.returnSum = returnSum;
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


  /*
   * ------------------------------------------------------------------------
   */
  /**
   * 计算
   */
  public void compute() {
    int i = -1;
    try {
      map = orderReturnService.compute(orderCode, itemId, alterNum);
      i = 1;
    } catch (Exception e) {
      log.error("退换货计算异常！", e);
    }
    msg = 1 == i ? "计算成功！" : "计算失败！";
  }
}
