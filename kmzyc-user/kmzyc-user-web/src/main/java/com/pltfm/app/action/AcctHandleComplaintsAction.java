package com.pltfm.app.action;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pltfm.app.service.BnesAcctAppealInfoService;
import com.pltfm.app.service.BnesAcctHandleComplaintsService;
import com.pltfm.app.vobject.BnesAcctHandleComplaints;

@Component(value = "acctHandleComplaintsAction")
@Scope(value = "prototype")
public class AcctHandleComplaintsAction {
  @Resource(name = "bnesAcctHandleComplaintsService")
  BnesAcctHandleComplaintsService bnesAcctHandleComplaintsService;
  @Resource(name = "bnesAcctAppealInfoService")
  BnesAcctAppealInfoService bnesAcctAppealInfoService;
  BnesAcctHandleComplaints bnesAcctHandleComplaints;

  // 处理申诉
  public String add() {
    try {
      bnesAcctHandleComplaintsService.insert(bnesAcctHandleComplaints);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "";
  }

  // 申诉列表
  public String list() {
    // bnesAcctAppealInfoService.
    return "";
  }

  public BnesAcctHandleComplaintsService getBnesAcctHandleComplaintsService() {
    return bnesAcctHandleComplaintsService;
  }

  public void setBnesAcctHandleComplaintsService(
      BnesAcctHandleComplaintsService bnesAcctHandleComplaintsService) {
    this.bnesAcctHandleComplaintsService = bnesAcctHandleComplaintsService;
  }

  public BnesAcctHandleComplaints getBnesAcctHandleComplaints() {
    return bnesAcctHandleComplaints;
  }

  public void setBnesAcctHandleComplaints(BnesAcctHandleComplaints bnesAcctHandleComplaints) {
    this.bnesAcctHandleComplaints = bnesAcctHandleComplaints;
  }

  public BnesAcctAppealInfoService getBnesAcctAppealInfoService() {
    return bnesAcctAppealInfoService;
  }

  public void setBnesAcctAppealInfoService(BnesAcctAppealInfoService bnesAcctAppealInfoService) {
    this.bnesAcctAppealInfoService = bnesAcctAppealInfoService;
  }


}
