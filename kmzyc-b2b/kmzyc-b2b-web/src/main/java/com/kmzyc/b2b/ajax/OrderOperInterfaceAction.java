package com.kmzyc.b2b.ajax;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.service.OrderMainService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;

@SuppressWarnings("serial")
@Controller("orderOperInterfaceAction")
@Scope("prototype")
public class OrderOperInterfaceAction extends BaseAction {
  // private static Logger logger = Logger.getLogger(ViewProductRelationPurchaseAction.class);
  private static Logger logger = LoggerFactory.getLogger(OrderOperInterfaceAction.class);
  @SuppressWarnings("unchecked")
  private ReturnResult returnResult;

  @Resource(name = "orderMainServiceImpl")
  private OrderMainService OrderMainService;

  /**
   * 获取购买行为商品
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public String queryYesterdayOrderCount() {
    try {
      int orderCount = OrderMainService.queryYesterdayOrderCount();
      returnResult =
          new ReturnResult(InterfaceResultCode.SUCCESS, "成功", Integer.valueOf(orderCount));
    } catch (Exception e) {
      logger.error("++++++++++++++获取昨天的订单数量失败" + e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  @SuppressWarnings("rawtypes")
  public ReturnResult getReturnResult() {
    return returnResult;
  }

  @SuppressWarnings("rawtypes")
  public void setReturnResult(ReturnResult returnResult) {
    this.returnResult = returnResult;
  }

}
