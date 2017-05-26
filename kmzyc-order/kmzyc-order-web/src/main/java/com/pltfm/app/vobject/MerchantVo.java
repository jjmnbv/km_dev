package com.pltfm.app.vobject;

import java.io.Serializable;

public class MerchantVo implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 812715224282094617L;

  private String merchantId;// 商家编号
  private String merchantName;// 商家名称

  public String getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

}
