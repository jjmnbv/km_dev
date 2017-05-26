package com.kmzyc.b2b.third.action;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.third.service.WxCardManageService;

@Controller("WxCodeImportManageAction")
@Scope("prototype")
public class WxCodeImportManageAction extends BaseAction {

  @Resource(name = "wxCardManageService")
  private WxCardManageService cardManageService;
  private static Logger log = LoggerFactory.getLogger(WxCodeImportManageAction.class);

  private String cardId;
  private String couponId;
  private int num;
  private String tableName;

  private String successCount;
  private String flag;

  public String toImpotJsp() {
    return "success";
  }

  /**
   * 导入code
   * 
   * @return
   */
  public String importCode() {
    try {
          cardManageService.importCode(cardId, couponId, num, tableName);
      this.successCount = "0";
      this.flag = "0";
    } catch (SQLException e) {
      log.error("导入code发生异常~", e);
    }
    return "success";

  }

  public String getCardId() {
    return cardId;
  }

  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

  public String getCouponId() {
    return couponId;
  }

  public void setCouponId(String couponId) {
    this.couponId = couponId;
  }

  public String getSuccessCount() {
    return successCount;
  }

  public void setSuccessCount(String successCount) {
    this.successCount = successCount;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
}
