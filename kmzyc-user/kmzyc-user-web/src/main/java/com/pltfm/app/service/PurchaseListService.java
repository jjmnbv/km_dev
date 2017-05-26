package com.pltfm.app.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.PurchaseListDO;

public interface PurchaseListService {

  Integer selectListPurchaseListCount(PurchaseListDO purchaseListDO) throws SQLException;

  List selectListPurchaseListDO(PurchaseListDO purchaseListDO) throws SQLException;

  PurchaseListDO findPurchaseListDOByPrimaryKey(BigDecimal purchaseId) throws SQLException;

  Integer updatePurchaseListDO(PurchaseListDO purchaseListDO) throws SQLException;

  Integer deletePurchaseListDOByPrimaryKey(BigDecimal purchaseId) throws SQLException;

}
