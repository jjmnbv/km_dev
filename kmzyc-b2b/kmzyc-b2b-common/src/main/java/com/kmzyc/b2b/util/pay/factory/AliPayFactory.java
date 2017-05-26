package com.kmzyc.b2b.util.pay.factory;

import com.kmzyc.b2b.util.pay.BasePayPlugIn;
import com.kmzyc.b2b.util.pay.BasePayPlugInFactory;
import com.kmzyc.b2b.util.pay.plugin.AliPayPlugIn;
import com.kmzyc.b2b.util.pay.plugin.AliWapPayPlugIn;

/**
 * 支付宝
 * 
 * @author xiongliguang
 * @modifier zengming
 * @version 1.1
 * @since 2014-09-03 为满足支付宝的wap端接口增加plugInType
 */
public class AliPayFactory extends BasePayPlugInFactory {

  private int plugInType;

  public AliPayFactory(int plugInType) {
    this.plugInType = plugInType;
  }

  @Override
  public BasePayPlugIn createPayPlugIn() throws Exception {
    switch (this.plugInType) {
      case ACCESSTYPE_WEB: {
        return new AliPayPlugIn();
      }
      case ACCESSTYPE_WAP: {
        return new AliWapPayPlugIn();
      }
      case ACCESSTYPE_APP: {

      }
      default: {
        return null;
      }
    }
  }
}
