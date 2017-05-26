package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pltfm.app.dao.PurchaseListDetailDAO;
import com.pltfm.app.service.PurchaseListDetailService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.PurchaseListDetailDO;

@Component(value = "purchaseListDetailService")
public class PurchaseListDetailServiceImpl implements PurchaseListDetailService {



  @Resource(name = "purchaseListDetailDAO")
  private PurchaseListDetailDAO purchaseListDetailDAO;

  @Override
  public List<PurchaseListDetailDO> findListByExample(PurchaseListDetailDO purchaseListDetailDO)
      throws SQLException {
    // TODO Auto-generated method stub
    return purchaseListDetailDAO.findListByExample(purchaseListDetailDO);
  }

  public PurchaseListDetailDAO getPurchaseListDetailDAO() {
    return purchaseListDetailDAO;
  }

  public void setPurchaseListDetailDAO(PurchaseListDetailDAO purchaseListDetailDAO) {
    this.purchaseListDetailDAO = purchaseListDetailDAO;
  }

  @Override
  public List<PurchaseListDetailDO> findListByPurchaseList(
      PurchaseListDetailDO purchaseListDetailDO) {
    // TODO Auto-generated method stub
    return purchaseListDetailDAO.findListByPurchaseList(purchaseListDetailDO);
  }

  @Override
  public Integer countListByPurchaseList(PurchaseListDetailDO purchaseListDetailDO) {
    // TODO Auto-generated method stub
    return purchaseListDetailDAO.countListByPurchaseList(purchaseListDetailDO);
  }

  @Override
  public Integer insertPurchaseList(List<String> skuCodeLists, BigDecimal purchaseId) {
    // TODO Auto-generated method stub

    Integer count = 0;
    if (ListUtils.isNotEmpty(skuCodeLists)) {
      for (String skuCodes : skuCodeLists) {
        // System.out.println("-----------skuCode+"+skuCode+"++++++---purchaseId:"+purchaseId);
        String[] spl   = skuCodes.split(",");

        PurchaseListDetailDO purchaseListDetailDO = new PurchaseListDetailDO();
        purchaseListDetailDO.setPurchaseId(purchaseId);
        purchaseListDetailDO.setSkuCode(spl[0]);
        purchaseListDetailDO.setProductPrice(new BigDecimal(spl[1]));
        purchaseListDetailDO.setProductCount(new BigDecimal(1));
        purchaseListDetailDO.setAmount(new BigDecimal(spl[1]));
        purchaseListDetailDAO.insertPurchaseListDetailDO(purchaseListDetailDO);
        count += count;
        // count+=addressDAO.deleteByPrimaryKey(Integer.valueOf(id));
      }
    }
    return count;

  }

  @Override
  public Integer deletePurchaseListDetailDOByPrimaryKey(BigDecimal purchaseDetailId) {
    // TODO Auto-generated method stub
    return purchaseListDetailDAO.deletePurchaseListDetailDOByPrimaryKey(purchaseDetailId);
  }

  @Override
  public Integer updatePurchaseListDetailDO(PurchaseListDetailDO purchaseListDetailDO) {
    // TODO Auto-generated method stub
    return purchaseListDetailDAO.updatePurchaseListDetailDO(purchaseListDetailDO);
  }

}
