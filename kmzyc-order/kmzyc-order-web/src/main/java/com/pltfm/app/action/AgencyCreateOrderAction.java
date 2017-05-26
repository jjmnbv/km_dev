package com.pltfm.app.action;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.exception.ServiceException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.service.AgencyCreateOrderService;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.vobject.OrderPreferentialVO;

/** 
 * 订单生成Action
 * 
 * @author lvxingxing
 * @version 1.0
 * @since 2013-7-26
 */
@Controller("agencyCreateOrderAction")
@Scope("prototype")
public class AgencyCreateOrderAction extends ActionSupport {

  private static final long serialVersionUID = 1L;
  private Logger log = Logger.getLogger(AgencyCreateOrderAction.class);

  private OrderMain order;

  @Resource
  private AgencyCreateOrderService orderService;

  public OrderMain getOrder() {
    return order;
  }

  public void setOrder(OrderMain order) {
    this.order = order;
  }

  public String agencyCreateOrder() {
    OrderItem orderItem1;
    List<OrderItem> orderItemList = new ArrayList<OrderItem>();
    ActionContext ctx = ActionContext.getContext();
    HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    String[] commodityName = request.getParameterValues("commodityName");
    String[] commodityCode = request.getParameterValues("commodityCode");
    String[] commoditySku = request.getParameterValues("commoditySku");
    String[] warehouseId = request.getParameterValues("warehouseId");
    String[] commodityUnitPrice = request.getParameterValues("commodityUnitPrice");
    String[] commodityNumber = request.getParameterValues("commodityNumber");
    String[] credits = request.getParameterValues("credits");
    String[] commoditySum = request.getParameterValues("commoditySum");
    for (int i = 0; i < commodityName.length; i++) {
      orderItem1 = new OrderItem();
      orderItem1.setWarehouseId(new BigDecimal(warehouseId[i]));// 设置仓库
      orderItem1.setCommodityName(commodityName[i]);
      orderItem1.setCommodityNumber(Long.parseLong(commodityNumber[i]));
      orderItem1.setCommoditySku(commoditySku[i]);
      orderItem1.setCommodityUnitPrice(new BigDecimal(commodityUnitPrice[i]));
      orderItem1.setCommoditySum(new BigDecimal(commoditySum[i]));
      orderItem1.setCommodityCode(commodityCode[i]);
      orderItem1.setCredits(Long.parseLong(credits[i]));
      orderItemList.add(orderItem1);
    }
    // OrderPreferential orderP=new OrderPreferential();;
    List<OrderPreferentialVO> orderPlist = new ArrayList<OrderPreferentialVO>();
    OrderPreferentialVO opVO = null;
    for (int i = 0; i < commoditySku.length; i++) {
      opVO = new OrderPreferentialVO();
      opVO.setPreferentialSKU(commoditySku[i]);
      opVO.setOrderPreferentialSum(BigDecimal.valueOf(20.00));
      opVO.setOrderPreferentialType((long) OrderDictionaryEnum.OrderPreferentialType.SPECIAL
          .getKey());
      orderPlist.add(opVO);
    }

    // 优惠券
    List<OrderPayStatement> orderPayStatementList = new ArrayList<OrderPayStatement>();
    OrderPayStatement orderPayStatement;
    String index = request.getParameter("cgindex");
    if (index != null && !"".equals(index)) {
      String[] couponID = request.getParameterValues("couponID");
      String[] couponName = request.getParameterValues("couponName");
      String[] couponSum = request.getParameterValues("couponSum");
      if (couponID != null) {
        for (int i = 0; i < couponID.length; i++) {
          orderPayStatement = new OrderPayStatement();
          // 优惠券编号
          orderPayStatement.setPreferentialNo(new BigDecimal(couponID[i]));
          orderPayStatement.setPreferentialName(couponName[i]);
          orderPayStatement.setOrderMoney(new BigDecimal(couponSum[i]));
          // 支付方式
          orderPayStatement
              .setPaymentWay((long) OrderDictionaryEnum.OrderPayMethod.Coupon.getKey());
          orderPayStatementList.add(orderPayStatement);
        }
      }
    }

    String result = null;
    try {
      result =
          orderService.agencyCreateOrder(order, orderItemList, orderPlist, orderPayStatementList);
    } catch (ServiceException e) {
      log.error("订单生成报错！", e);
    }
    if ("0".equalsIgnoreCase(result) || "-1".equalsIgnoreCase(result)) {
      return "fail";
    }
    return "sccusess";

  }

  public String fareExecute() throws SQLException {
    // 调用计算运费接口

    return null;
  }

}
