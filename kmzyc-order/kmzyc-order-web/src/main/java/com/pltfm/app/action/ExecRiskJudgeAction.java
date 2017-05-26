package com.pltfm.app.action;


import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.pltfm.app.service.RiskManagementService;

/**
 * 风控Action
 * 
 * @author leihq
 * @version 1.0
 * @since 2016-04-26
 */
@Controller("execRiskJudgeAction")
@Scope("prototype")
public class ExecRiskJudgeAction extends BaseAction {

  private static final long serialVersionUID = 1L;
  private Logger log = Logger.getLogger(ExecRiskJudgeAction.class);
  @Resource
  private RiskManagementService risk;

  private String value;

  public void execRiskJudge() {
    try {
      if (value.length() > 0) {
        risk.execRiskJudge(value);
        getHttpServletResponse().getWriter().print("finish");
      }
    } catch (Exception e) {
      log.error("风控发生异常" + e.getMessage());
    }
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
