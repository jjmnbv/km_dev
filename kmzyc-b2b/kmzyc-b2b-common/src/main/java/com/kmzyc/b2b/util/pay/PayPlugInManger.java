package com.kmzyc.b2b.util.pay;

import com.kmzyc.b2b.util.pay.factory.*;

public class PayPlugInManger {
  // 易宝
  public static final int PLATFORM_YEE_PAY = 1;
  // 快汇宝
  public static final int PLATFORM_KUAI_PAY = 2;
  // 支付宝
  public static final int PLATFORM_ALI_PAY = 3;
  // 财付通
  public static final int PLATFORM_TEN_PAY = 4;
  // 微信
  public static final int PLATFORM_WX_PAY = 5;
  // 时代
  public static final int PLATFORM_SD_PAY = 6;
  // 康美通
  public static final int PLATFORM_KMT_PAY = 7;

  private static BasePayPlugInFactory factory = null;

  public static BasePayPlugInFactory createFactory(BuildParam param) {
    int factoryType = -1;
    int plugInType = -1;
    if (param != null) {
      factoryType = param.getFactoryType();
      plugInType = param.getPlugInType();
    }
    switch (factoryType) {
      case PLATFORM_ALI_PAY: {
        factory = new AliPayFactory(plugInType);
        break;
      }
      case PLATFORM_TEN_PAY: {
        factory = new TenpayFactory(plugInType);
        break;
      }
      case PLATFORM_WX_PAY: {
        factory = new WxPayFactory(plugInType);
        break;
      }
      case PLATFORM_KMT_PAY: {
        factory = new KMTPayFactory(plugInType);
        break;
      }
      default: {
        break;
      }
    }
    return factory;
  }
}
