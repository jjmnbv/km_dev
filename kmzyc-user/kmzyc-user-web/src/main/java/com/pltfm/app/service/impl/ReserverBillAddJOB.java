package com.pltfm.app.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pltfm.app.service.ReserverBillService;

@Component(value = "reserverBillAddJOB")
@Scope(value = "prototype")
public class ReserverBillAddJOB {
  Logger logger = LoggerFactory.getLogger(this.getClass());
  @Resource(name = "reserverBillService")
  private ReserverBillService reserverBillService;

  /**
   * 定时账单生成
   */
  public void reserverBillAdd() {
    try {
      reserverBillService.insert(null);
    } catch (Exception e) {
      logger.error("自动生成账单失败" + e.getMessage(), e);
    }
  }
}
