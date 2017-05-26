package com.kmzyc.b2b.vo;

import java.io.Serializable;

import com.pltfm.app.vobject.ProductSku;

/**
 * 
 * @author KM
 * 
 */
public class OmsProductSkuData extends ProductSku implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -7120127536476998566L;

  private String erpProCodeForOms; // 20151028 add 对应的捷科系统的产品编码



  public String getErpProCodeForOms() {
    return erpProCodeForOms;
  }

  public void setErpProCodeForOms(String erpProCodeForOms) {
    this.erpProCodeForOms = erpProCodeForOms;
  }

}
