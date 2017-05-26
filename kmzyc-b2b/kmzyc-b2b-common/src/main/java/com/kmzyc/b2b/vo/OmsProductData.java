package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.util.List;

import com.pltfm.app.vobject.Product;

/**
 * Oms系统对接单个商品实体
 * 
 * @author KM
 * 
 */
public class OmsProductData extends Product implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private List<OmsProductSkuData> omsProductSkuList;

  public List<OmsProductSkuData> getOmsProductSkuList() {
    return omsProductSkuList;
  }

  public void setOmsProductSkuList(List<OmsProductSkuData> omsProductSkuList) {
    this.omsProductSkuList = omsProductSkuList;
  }

}
