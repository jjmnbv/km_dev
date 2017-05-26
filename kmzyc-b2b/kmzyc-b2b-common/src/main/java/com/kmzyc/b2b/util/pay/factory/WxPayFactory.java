package com.kmzyc.b2b.util.pay.factory;

import com.kmzyc.b2b.util.pay.BasePayPlugIn;
import com.kmzyc.b2b.util.pay.BasePayPlugInFactory;
import com.kmzyc.b2b.util.pay.plugin.WxPayPlugIn;

/**
 * 微信支付工厂
 * 
 * @author cjm
 * @since 2015-1-22
 */
public class WxPayFactory extends BasePayPlugInFactory {

  /**
   * 支付组件类型
   */
  private int plugInType;

  public WxPayFactory(int plugInType) {
    this.plugInType = plugInType;
  }

  /**
   * 生成微信支付组件
   */
  @Override
  public BasePayPlugIn createPayPlugIn() throws Exception {
    switch (this.plugInType) {
      case ACCESSTYPE_WEB: {}
      case ACCESSTYPE_WAP: {}
      case ACCESSTYPE_APP: {}
      case ACCESSTYPE_WX: {
        return new WxPayPlugIn();
      }
      default: {
        return new WxPayPlugIn();
      }
    }
  }

}
