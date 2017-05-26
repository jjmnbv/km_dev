package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pltfm.app.dao.PurchaseListDAO;
import com.pltfm.app.dao.PurchaseListDetailDAO;
import com.pltfm.app.service.PurchaseListService;
import com.pltfm.app.vobject.PurchaseListDO;

@Component(value = "purchaseListService")
public class PurchaseListServiceImpl implements PurchaseListService {

  @Resource(name = "purchaseListDAO")
  private PurchaseListDAO purchaseListDAO;

  @Resource(name = "purchaseListDetailDAO")
  private PurchaseListDetailDAO purchaseListDetailDAO;


  public PurchaseListDetailDAO getPurchaseListDetailDAO() {
    return purchaseListDetailDAO;
  }

  public void setPurchaseListDetailDAO(PurchaseListDetailDAO purchaseListDetailDAO) {
    this.purchaseListDetailDAO = purchaseListDetailDAO;
  }

  @Override
  public Integer selectListPurchaseListCount(PurchaseListDO purchaseListDO) throws SQLException {
    // TODO Auto-generated method stub

    return purchaseListDAO.countPurchaseListDOByExample(purchaseListDO);
  }

  @Override
  public List selectListPurchaseListDO(PurchaseListDO purchaseListDO) throws SQLException {
    // TODO Auto-generated method stub
    return purchaseListDAO.findListByExample(purchaseListDO);
  }

  public PurchaseListDAO getPurchaseListDAO() {
    return purchaseListDAO;
  }

  public void setPurchaseListDAO(PurchaseListDAO purchaseListDAO) {
    this.purchaseListDAO = purchaseListDAO;
  }

  @Override
  public PurchaseListDO findPurchaseListDOByPrimaryKey(BigDecimal purchaseId) throws SQLException {
    // TODO Auto-generated method stub
    return purchaseListDAO.findPurchaseListDOByPrimaryKey(purchaseId);
  }

  @Override
  public Integer updatePurchaseListDO(PurchaseListDO purchaseListDO) throws SQLException {
    // TODO Auto-generated method stub
    return purchaseListDAO.updatePurchaseListDO(purchaseListDO);
  }

  @Override
  public Integer deletePurchaseListDOByPrimaryKey(BigDecimal purchaseId) throws SQLException {
    // TODO Auto-generated method stub
    int rowNum = 0;
    rowNum = purchaseListDAO.deletePurchaseListDOByPrimaryKey(purchaseId);
    rowNum = purchaseListDetailDAO.deletePurchaseListDetailDOByPurchaseIdKey(purchaseId);
    return rowNum;
  }

}
