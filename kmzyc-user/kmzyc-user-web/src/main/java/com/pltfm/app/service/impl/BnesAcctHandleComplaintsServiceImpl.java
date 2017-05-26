package com.pltfm.app.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pltfm.app.dao.BnesAcctHandleComplaintsDAO;
import com.pltfm.app.service.BnesAcctHandleComplaintsService;
import com.pltfm.app.vobject.BnesAcctHandleComplaints;



@Component(value = "bnesAcctHandleComplaintsService")
public class BnesAcctHandleComplaintsServiceImpl implements BnesAcctHandleComplaintsService {
  @Resource(name = "bnesAcctHandleComplaintsDAO")
  BnesAcctHandleComplaintsDAO bnesAcctHandleComplaintsDAO;

  // 处理申诉
  public void insert(BnesAcctHandleComplaints record) throws SQLException {
    bnesAcctHandleComplaintsDAO.insert(record);
  }

  public BnesAcctHandleComplaintsDAO getBnesAcctHandleComplaintsDAO() {
    return bnesAcctHandleComplaintsDAO;
  }

  public void setBnesAcctHandleComplaintsDAO(
      BnesAcctHandleComplaintsDAO bnesAcctHandleComplaintsDAO) {
    this.bnesAcctHandleComplaintsDAO = bnesAcctHandleComplaintsDAO;
  }



}
