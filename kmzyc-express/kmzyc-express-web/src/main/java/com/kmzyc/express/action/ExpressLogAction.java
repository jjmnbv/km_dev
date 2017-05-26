package com.kmzyc.express.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.express.service.ExpressLogService;
import com.kmzyc.express.util.Page;

/**
 * @author hekai
 * @since JDK1.6
 * @history 2015-12-8 快递信息同步日志ACTION
 */
@Controller("expressLogAction")
@Scope("prototype")
public class ExpressLogAction extends BaseAction {

  /**
   * UID
   */
  private static final long serialVersionUID = 1432296410720642079L;

  @Resource
  private ExpressLogService expressLogService;

  private Map<String, String> map = new HashMap<String, String>();

  // 分页对象
  private Page page;

  /**
   * 日志列表
   * 
   * @return
   * @throws ActionException
   */
  public String pageList() throws ActionException {
    if (page == null) {
      page = new Page();
    }
    try {
      // 查询取现记录数
      int count = expressLogService.queryExpressLogCount(map);
      // page赋值
      page.setRecordCount(count);
      // 分页查询参数
      map.put("startRow", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      map.put("endRow", (getPage().getPageNo() * getPage().getPageSize()) + "");
      // 设置datalist
      page.setDataList(expressLogService.queryExpressLogListByPage(map));
    } catch (Exception ex) {
      throw new ActionException("物流同步日志异常：" + ex.getMessage());
    }
    return "PageList";
  }

  public Map<String, String> getMap() {
    return map;
  }

  public void setMap(Map<String, String> map) {
    this.map = map;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }


  //
  // // 余额提现列表
  // public String PageList() {
  // // 审核通过，拒绝跳转
  // String ifBack = (String) this.httpServletRequest.getSession().getAttribute("ifback");
  // if (null == ifBack) {
  // this.httpServletRequest.getSession().setAttribute("queryC", bnesAcctEnchashment);
  // }
  //
  // if (null != ifBack && "back".equals(ifBack)) {
  // bnesAcctEnchashment =
  // (BnesAcctEnchashment) this.httpServletRequest.getSession().getAttribute("queryC");
  // this.httpServletRequest.getSession().setAttribute("ifback", null);
  // this.httpServletRequest.getSession().setAttribute("queryC", new BnesAcctEnchashment());
  // }
  //
  // try {
  // if (page == null) {
  // page = new Page();
  // }
  // // 余额提现审核状态map绑定到request对象
  // super.getRequest()
  // .setAttribute("enchashmentStatusEnumMap", EnchashmentStatusEnumMap.getMap());
  // super.getRequest().setAttribute("enchashmentResourceEnumMap",
  // EnchashmentResourceEnumMap.getMap());
  // // 提现确认后返回处理，提现确认页面只需要显示审核通过的
  // if (StringUtils.isNotBlank(fromConfirmMenu) && "yes".equals(fromConfirmMenu)) {
  // bnesAcctEnchashment.setEnchashmentStatus(Short
  // .valueOf(EnchashmentStatusEnumType.Status_Pass.getType()));
  // }
  // // 条件分页查询取现列表
  // bnesAcctEnchashmentService.queryBnesAcctEnchashmentListByPage(bnesAcctEnchashment, page);
  // this.setTotalAmount(bnesAcctEnchashmentService
  // .queryEnchashmentTotalAmount(bnesAcctEnchashment));
  // } catch (Exception e) {
  // logger.error("获取取现信息列表失败" + e.getMessage(), e);
  // }
  // return "PageList";
  // }


  //
  // private Long orderId;
  //
  // private BigDecimal totalMoney;
  //
  // private BigDecimal totalActual;
  //
  // private String loginAccount;
  //
  // private static final OrderDictionaryEnum.Order_Status[] orderStatus =
  // OrderDictionaryEnum.Order_Status.values();// 订单状态
  //
  // private static final OrderDictionaryEnum.OrderPurchaserType[] purchaserTypes =
  // OrderDictionaryEnum.OrderPurchaserType.values();// 下单类型
  //
  // private static final OrderDictionaryEnum.OrderSaleTypes[] salesTypes =
  // OrderDictionaryEnum.OrderSaleTypes.values();// 销售类型
  //
  // /*
  // * 到订单列表查询页
  // */
  // public String show() throws ActionException {
  // return "orderList";
  // }
  //
  // /*
  // * 到订单列表查询页(按状态)
  // */
  // public String showByStatus() throws ActionException {
  // Map<String, Object> paramMap = new HashMap<String, Object>();
  // paramMap.putAll(map);
  // Map session = ActionContext.getContext().getSession();
  // SysUser sysuser = (SysUser) session.get("sysUser");
  // paramMap.put("sysuser", sysuser);
  // try {
  // paramMap.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
  // paramMap.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
  // getPage().setRecordCount(orderQryService.listCount(paramMap));
  // getPage().setDataList(orderQryService.listOrder(paramMap));
  // totalMoney = orderQryService.listCountMoney(paramMap);
  // totalActual = orderQryService.listCountActual(paramMap);
  // } catch (Exception e) {
  // log.info(e.getMessage());
  // throw new ActionException();
  // }
  // return "orderList";
  // }
  //
  // /*
  // * 查询订单列表ByMap
  // */
  // public String listByMap() throws ActionException {
  // Map<String, Object> paramMap = new HashMap<String, Object>();
  // String nstatus = getHttpServletRequest().getParameter("nstatus");// 非XX状态
  // if (StringUtil.gbStrLen(nstatus) > 0 && nstatus.indexOf(',') > 0) {
  // List nsList = new ArrayList();
  // for (String temp : nstatus.split(",")) {
  // nsList.add(temp);
  // }
  // paramMap.put("nstatus", nsList);
  // }
  //
  // String rangeP = (String) map.get("payDateRange");
  // if (null != rangeP && !"".equals(rangeP)) {
  // String start = "";
  // String end = "";
  // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
  // Calendar c = Calendar.getInstance();
  // c.setTime(new Date());
  // if (("today").equals(rangeP)) {
  // start = format.format(c.getTime());
  // end = start;
  // } else if (("ysday").equals(rangeP)) {
  // c.add(Calendar.DATE, -1);
  // start = format.format(c.getTime());
  // end = start;
  // } else if (("tsweek").equals(rangeP)) {
  // c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
  // start = format.format(c.getTime());
  // c.add(Calendar.DATE, 6);
  // end = format.format(c.getTime());
  // } else if (("lsweek").equals(rangeP)) {
  // c.add(Calendar.DATE, -7);
  // c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
  // start = format.format(c.getTime());
  // c.add(Calendar.DATE, 6);
  // end = format.format(c.getTime());
  // } else if (("tsmonth").equals(rangeP)) {
  // c.set(Calendar.DATE, 1);
  // start = format.format(c.getTime());
  // int MaxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
  // c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), MaxDay, 23, 59, 59);
  // end = format.format(c.getTime());
  // } else if (("lsmonth").equals(rangeP)) {
  // c.add(Calendar.MONDAY, -1);
  // c.set(Calendar.DATE, 1);
  // start = format.format(c.getTime());
  // int MaxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
  // c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), MaxDay, 23, 59, 59);
  // end = format.format(c.getTime());
  // }
  // map.put("payDateStart", start + " 00:00:00");
  // map.put("payDateEnd", end + " 23:59:59");
  // }
  //
  // String rangeC = (String) map.get("createDateRange");
  // if (null != rangeC && !"".equals(rangeC)) {
  // String start = "";
  // String end = "";
  // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
  // Calendar c = Calendar.getInstance();
  // c.setTime(new Date());
  // if (("today").equals(rangeC)) {
  // start = format.format(c.getTime());
  // end = start;
  // } else if (("ysday").equals(rangeC)) {
  // c.add(Calendar.DATE, -1);
  // start = format.format(c.getTime());
  // end = start;
  // } else if (("tsweek").equals(rangeC)) {
  // c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
  // start = format.format(c.getTime());
  // c.add(Calendar.DATE, 6);
  // end = format.format(c.getTime());
  // } else if (("lsweek").equals(rangeC)) {
  // c.add(Calendar.DATE, -7);
  // c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
  // start = format.format(c.getTime());
  // c.add(Calendar.DATE, 6);
  // end = format.format(c.getTime());
  // } else if (("tsmonth").equals(rangeC)) {
  // c.set(Calendar.DATE, 1);
  // start = format.format(c.getTime());
  // int MaxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
  // c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), MaxDay, 23, 59, 59);
  // end = format.format(c.getTime());
  // } else if (("lsmonth").equals(rangeC)) {
  // c.add(Calendar.MONDAY, -1);
  // c.set(Calendar.DATE, 1);
  // start = format.format(c.getTime());
  // int MaxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
  // c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), MaxDay, 23, 59, 59);
  // end = format.format(c.getTime());
  // }
  // map.put("createDateStart", start + " 00:00:00");
  // map.put("createDateEnd", end + " 23:59:59");
  // }
  //
  // paramMap.putAll(map);
  // // String nstatus = getHttpServletRequest().getParameter("nstatus");//
  // // 非XX状态
  // if (StringUtil.gbStrLen(nstatus) > 0 && nstatus.indexOf(',') > 0) {
  // List nsList = new ArrayList();
  // for (String temp : nstatus.split(",")) {
  // nsList.add(temp);
  // }
  // paramMap.put("nstatus", nsList);
  // }
  // try {
  // Map session = ActionContext.getContext().getSession();
  // SysUser sysuser = (SysUser) session.get("sysUser");
  // loginAccount = sysuser.getUserName();
  // paramMap.put("sysuser", sysuser);
  // paramMap.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
  // paramMap.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
  // getPage().setRecordCount(orderQryService.listCountFuzzyOrder(paramMap));
  // getPage().setDataList(orderQryService.listFuzzyOrder(paramMap));
  // totalMoney = orderQryService.listCountFuzzyOrderMoney(paramMap);
  // totalActual = orderQryService.listCountFuzzyOrderActual(paramMap);
  // map.put("nstatus", nstatus);
  // } catch (Exception e) {
  // log.error("查询订单列表发生错误", e);
  // throw new ActionException();
  // }
  // return "orderList";
  // }
  //
  // /**
  // * 统计sku
  // *
  // * @return
  // */
  // private Map skuMap;
  //
  // public Map getSkuMap() {
  // return skuMap;
  // }
  //
  // public void setSkuMap(Map skuMap) {
  // this.skuMap = skuMap;
  // }
  //
  // public Date getBegin() {
  // return begin;
  // }
  //
  // public void setBegin(Date begin) {
  // this.begin = begin;
  // }
  //
  // public Date getEnd() {
  // return end;
  // }
  //
  // public void setEnd(Date end) {
  // this.end = end;
  // }
  //
  // public String getSKU() {
  // return SKU;
  // }
  //
  // public void setSKU(String sKU) {
  // SKU = sKU;
  // }
  //
  // private Date begin;
  // private Date end;
  // private String SKU;
  //
  // public String countSKU() throws ActionException {
  // try {
  // if (null != SKU) {
  // skuMap = orderQryService.countSKU(begin, end, Arrays.asList(SKU.split(",")));
  // }
  // } catch (ServiceException e) {
  // log.error("发生错误", e);
  // throw new ActionException();
  // }
  // return "skuList";
  // }
  //
  // public String findMerchant() throws ActionException {
  // Map<String, Object> commap = new LinkedHashMap<String, Object>();
  // try {
  // Map mapp = orderQryService.listCommercial(commap);
  // writeJson(mapp);
  // } catch (ServiceException e) {
  // log.error("数据查询出错", e);
  // }
  // return null;
  // }
  //
  // public void writeJson(Object obj) {
  // String json = JSON.toJSONString(obj);
  // strWriteJson(json);
  // }
  //
  // protected void strWriteJson(String strJson) {
  // ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
  // PrintWriter pw = null;
  // try {
  // pw = ServletActionContext.getResponse().getWriter();
  // pw.write(strJson);
  // pw.flush();
  // } catch (IOException e) {
  // log.error("json输出异常", e);
  // } finally {
  // if (pw != null) {
  // pw.close();
  // }
  // }
  // }
  //
  // public String testExport() {
  // Map<String, Object> params = new HashMap<String, Object>();
  // try {
  // // params.put("orderCode", "1142");
  // // params.put("customerAccount", "randy");
  // // params.put("consigneeName", "兰");
  // // params.put("consigneeMobile", "13670");
  // // params.put("commerceId", "9982");
  // // params.put("orderStatus", 6);
  //
  // // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  // // params.put("orderBeginDate", df.parse("2015-5-19 11:16:12"));
  // // params.put("orderEndDate", df.parse("2015-5-19 11:17:51"));
  // // params.put("finishBeginDate", df.parse("2015-5-19 11:27:37"));
  // // params.put("finishEndDate", df.parse("2015-5-19 11:30:39"));
  //
  // // params.put("orderBeginDate", "2015-5-19 11:16:12");
  // // params.put("orderEndDate", "2015-5-19 11:17:51");
  // // params.put("finishBeginDate", "2015-5-19 11:27:37");
  // // params.put("finishEndDate", "2015-5-19 11:30:39");
  //
  // // orderBeginDate=2015-07-13 16:20:25, commerceId=880, orderEndDate=2015-10-13 16:20:25
  //
  // // and order_main.commerce_id = '301'
  // // and order_main.CREATE_DATE >= to_date('2015-10-18 09:17:21','yyyy-MM-dd HH24:mi:ss')
  // // and order_main.CREATE_DATE <= to_date('2015-10-20 09:17:26','yyyy-MM-dd HH24:mi:ss')
  //
  // params.put("titleValue", "所有订单");
  // params.put("orderBeginDate", "2015-10-18 09:17:53");
  // params.put("orderEndDate", "2015-10-20 09:27:10");
  // params.put("commerceId", "301");
  // // params.put("assess", "true");
  //
  //
  // String path = orderQryService.exportSellerOrders(params);
  // System.out.println(path);
  // } catch (Exception ex) {
  // log.error(ex.getMessage());
  // }
  // return null;
  // }
  //
  // /*
  // * getters and setters ------------------------------------------------------------------------
  // */
  // public Map<String, String> getMap() {
  // return map;
  // }
  //
  // public void setMap(Map<String, String> map) {
  // this.map = map;
  // }
  //
  // public Long getOrderId() {
  // return orderId;
  // }
  //
  // public void setOrderId(Long orderId) {
  // this.orderId = orderId;
  // }
  //
  // public OrderDictionaryEnum.Order_Status[] getOrderStatus() {
  // return orderStatus;
  // }
  //
  // public OrderDictionaryEnum.OrderPurchaserType[] getPurchaserTypes() {
  // return purchaserTypes;
  // }
  //
  // public OrderDictionaryEnum.OrderSaleTypes[] getSalestypes() {
  // return salesTypes;
  // }
  //
  // public BigDecimal getTotalMoney() {
  // return totalMoney;
  // }
  //
  // public void setTotalMoney(BigDecimal totalMoney) {
  // this.totalMoney = totalMoney;
  // }
  //
  // public String getLoginAccount() {
  // return loginAccount;
  // }
  //
  // public void setLoginAccount(String loginAccount) {
  // this.loginAccount = loginAccount;
  // }
  //
  // public BigDecimal getTotalActual() {
  // return totalActual;
  // }
  //
  // public void setTotalActual(BigDecimal totalActual) {
  // this.totalActual = totalActual;
  // }

}
