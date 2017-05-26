package com.pltfm.app.action;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.exception.ActionException;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.entities.OrderAssessDetail;
import com.pltfm.app.entities.OrderAssessInfo;
import com.pltfm.app.service.OrderAssessService;
import com.pltfm.app.service.OrderDictionaryService;
import com.pltfm.sys.model.SysUser;

/**
 * 订单评价
 * 
 * @author lvxingxing
 * @version 1.0
 * @since 2013-9-10
 */
@Scope("prototype")
@Controller("orderAssessInfoAction")
@SuppressWarnings("unchecked")
public class OrderAssessInfoAction extends BaseAction {
  private Logger log = Logger.getLogger(OrderAssessInfoAction.class);
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;
  @Resource
  private OrderAssessService orderAssessService;
  @Resource
  private OrderDictionaryService orderDictionaryService;

  private Map<String, String> map = new HashMap<String, String>();// 查询条件map

  public Map<String, String> getMap() {
    return map;
  }

  public void setMap(Map<String, String> map) {
    this.map = map;
  }

  private OrderAssessInfo orderAssessInfo;

  public OrderAssessInfo getOrderAssessInfo() {
    return orderAssessInfo;
  }

  public void setOrderAssessInfo(OrderAssessInfo orderAssessInfo) {
    this.orderAssessInfo = orderAssessInfo;
  }

  private List assessTypeList;
  private List assessGradeList;

  public List getAssessTypeList() {
    return assessTypeList;
  }

  public void setAssessTypeList(List assessTypeList) {
    this.assessTypeList = assessTypeList;
  }

  public List getAssessGradeList() {
    return assessGradeList;
  }

  public void setAssessGradeList(List assessGradeList) {
    this.assessGradeList = assessGradeList;
  }

  private List<OrderAssessDetail> oadlist;

  public List<OrderAssessDetail> getOadlist() {
    return oadlist;
  }

  public void setOadlist(List<OrderAssessDetail> oadlist) {
    this.oadlist = oadlist;
  }

  public String qryAssessInfo() throws ActionException {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Map session = ActionContext.getContext().getSession();
      SysUser sysuser = (SysUser) session.get("sysUser");

      Map mapPa = new HashMap();
      mapPa.put("sysuser", sysuser);
      if ((map.get("startDate") != null && !"".equals(map.get("startDate")))
          && (map.get("endDate") != null && !"".equals(map.get("endDate")))) {
        mapPa.put("startDate", sdf.parse(map.get("startDate")));
        mapPa.put("endDate", sdf.parse(map.get("endDate")));
      } else if (map.get("startDate") != null && !"".equals(map.get("startDate"))) {
        mapPa.put("startDate", sdf.parse(map.get("startDate")));
      } else if (map.get("endDate") != null && !"".equals(map.get("endDate"))) {
        mapPa.put("endDate", sdf.parse(map.get("endDate")));
      }

      if (map.get("orderCode") != null && !"".equals(map.get("orderCode"))) {
        mapPa.put("orderCode", map.get("orderCode").trim());
      }
      if (map.get("customerAccount") != null && !"".equals(map.get("customerAccount"))) {
        mapPa.put("guestNum", map.get("customerAccount").trim());
      }
      mapPa.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      mapPa.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
      getPage().setRecordCount(orderAssessService.listCount(mapPa));
      getPage().setDataList(orderAssessService.listOrder(mapPa));
    } catch (Exception e) {
      log.error("订单税查询异常", e);
      throw new ActionException();
    }
    return "qryList";
  }

  public String qry() throws ActionException {
    Map session = ActionContext.getContext().getSession();
    SysUser sysuser = (SysUser) session.get("sysUser");

    try {
      Map mapPa = new HashMap();
      mapPa.put("sysuser", sysuser);
      mapPa.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      mapPa.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
      getPage().setRecordCount(orderAssessService.listCount(mapPa));
      getPage().setDataList(orderAssessService.listOrder(mapPa));
    } catch (Exception e) {
      log.error("异常", e);
      throw new ActionException();
    }

    return "qryList";
  }

  public String fillOutAssess() throws UnsupportedEncodingException {
    ActionContext ctx = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    String orderCode = request.getParameter("orderCode");
    String customerAccountFromPage = request.getParameter("customerAccount");
    String customerAccount = null;
    if (customerAccountFromPage != null) {
      customerAccount = new String(customerAccountFromPage.getBytes("UTF-8"), "UTF-8");
    }
    // String customerAccount=new
    // String((request.getParameter("customerAccount")).getBytes("ISO-8859-1"),"UTF-8");
    String orderId = request.getParameter("orderId");
    if (orderAssessInfo != null) {
      orderAssessInfo.setGuestNum(customerAccount);
      orderAssessInfo.setOrderCode(orderCode);
      orderAssessInfo.setOrderId(Long.valueOf(orderId));
    } else {
      orderAssessInfo = new OrderAssessInfo();
      orderAssessInfo.setGuestNum(customerAccount);
      orderAssessInfo.setOrderCode(orderCode);
      orderAssessInfo.setOrderId(Long.valueOf(orderId));
    }

    assessTypeList = orderDictionaryService.getDictionaryByType("Assess_Type");
    assessGradeList = orderDictionaryService.getDictionaryByType("Assess_Grade");
    return "success";
  }

  public String save() throws ActionException {
    try {
      ActionContext ctx = ActionContext.getContext();
      HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
      String[] assessType = request.getParameterValues("assessType");
      String[] assessName = request.getParameterValues("assessName");
      String state1 = request.getParameter("state1");
      String state2 = request.getParameter("state2");
      String state3 = request.getParameter("state3");
      String[] state = new String[] {state1, state2, state3};
      Long mark = 0L;
      if (state1 != null && state2 != null && state3 != null) {
        mark = mark + Long.parseLong(state1) + Long.parseLong(state2) + Long.parseLong(state3);
      }
      orderAssessInfo.setAssessMark(mark);
      orderAssessInfo.setCreateDate(new Date());
      // 获取订单编码
      String orderCode = orderAssessInfo.getOrderCode();

      orderAssessService
          .createAssessInfo(orderAssessInfo, assessType, assessName, state, orderCode);

    } catch (Exception e) {
      log.error("订单税保存异常！", e);
      throw new ActionException();
    }
    qry();
    return "qryList";

  }

  public String assessShow() throws ActionException {

    try {
      ActionContext ctx = ActionContext.getContext();
      HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
      String orderCode = request.getParameter("orderCode");
      orderAssessInfo = orderAssessService.selectByOrderCode(orderCode);

      oadlist = orderAssessService.selectDetailByAssessID(orderAssessInfo.getAssessInfoId());
    } catch (Exception e) {
      log.error("订单税展示异常！", e);
      throw new ActionException();
    }
    return "assessShow";
  }

  public String delete() throws ActionException {
    try {
      ActionContext ctx = ActionContext.getContext();
      HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
      String orderCode = request.getParameter("orderCode");
      orderAssessService.deleteAssessInfo(orderCode);
      qry();
    } catch (Exception e) {
      log.error("订单税删除", e);
      throw new ActionException();
    }
    return "qryList";
  }
}
