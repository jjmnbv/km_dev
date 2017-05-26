package com.pltfm.app.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.pltfm.app.entities.OrderRiskCondition;
import com.pltfm.app.service.OrderRiskSettingService;
import com.pltfm.app.util.OrderRiskKey;

/**
 * 风控设置
 * 
 * @author xlg
 * 
 */
@Controller
@Scope("prototype")
public class OrderRiskSettingAction extends BaseAction {
  private static final long serialVersionUID = 1L;
  private Logger logger = Logger.getLogger(OrderRiskSettingAction.class);
  @Resource
  private OrderRiskSettingService orderRiskSettingService;

  /**
   * 初始化风控设置
   */
  public String OrderRiskConditionInit() {
    try {
      List<OrderRiskCondition> list = new ArrayList<OrderRiskCondition>();
      String[] values = null;
      OrderRiskCondition orc = null;
      for (String setting : OrderRiskKey.ORDER_RISK_INIT_SETTINGS) {
        values = setting.split(",");
        if (null != values && values.length == 4 && null != values[0] && null != values[1]
            && null != values[2]) {
          orc = new OrderRiskCondition();
          orc.setKey(values[0]);
          orc.setCondition(new BigDecimal(values[1]));
          orc.setScore(Integer.parseInt(values[2]));
          orc.setMax(new BigDecimal(values[3]));
          list.add(orc);
        }
      }
      if (!list.isEmpty()) {
        orderRiskSettingService.saveOrderRiskSetting(list);
        getHttpServletResponse().getWriter().print(1);
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return SUCCESS;
  }

  /**
   * 查询风控设置
   */
  public String queryOrderRiskCondition() {
    try {
      HttpServletRequest request = getHttpServletRequest();
      request.setAttribute("result", orderRiskSettingService.queryOrderRiskCondition());
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return SUCCESS;
  }

  /**
   * 保存风控设置
   */
  public void saveOrderRiskCondition() {
    try {
      HttpServletRequest request = getHttpServletRequest();
      String settings = request.getParameter("setting");
      if (null != settings && settings.length() > 0) {
        List<OrderRiskCondition> list = new ArrayList<OrderRiskCondition>();
        String[] values = null;
        OrderRiskCondition orc = null;
        Integer score = 0;
        for (String setting : settings.split("@")) {
          values = setting.split(",");
          if (null != values && values.length == 4 && null != values[0] && null != values[1]
              && null != values[2] && (score = Integer.parseInt(values[2])) >= 0) {
            orc = new OrderRiskCondition();
            orc.setKey(values[0]);
            orc.setCondition(new BigDecimal(values[1]));
            orc.setScore(score);
            orc.setMax(new BigDecimal(values[3]));
            list.add(orc);
          }
        }
        if (!list.isEmpty()) {
          orderRiskSettingService.saveOrderRiskSetting(list);
          getHttpServletResponse().getWriter().print(1);
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }
}
