package com.pltfm.app.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.PurchaseListDetailDO;

public interface PurchaseListDetailService {

  List<PurchaseListDetailDO> findListByExample(PurchaseListDetailDO purchaseListDetailDO)
      throws SQLException;

  List<PurchaseListDetailDO> findListByPurchaseList(PurchaseListDetailDO purchaseListDetailDO);

  Integer countListByPurchaseList(PurchaseListDetailDO purchaseListDetailDO);

  Integer insertPurchaseList(List<String> skuCodeLists, BigDecimal purchaseId);

  Integer deletePurchaseListDetailDOByPrimaryKey(BigDecimal purchaseDetailId);

  Integer updatePurchaseListDetailDO(PurchaseListDetailDO purchaseListDetailDO);

}
