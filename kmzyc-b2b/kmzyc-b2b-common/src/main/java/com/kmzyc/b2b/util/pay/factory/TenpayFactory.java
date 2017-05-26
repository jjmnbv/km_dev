package com.kmzyc.b2b.util.pay.factory;

import com.kmzyc.b2b.util.pay.BasePayPlugIn;
import com.kmzyc.b2b.util.pay.BasePayPlugInFactory;
import com.kmzyc.b2b.util.pay.plugin.TenpayPlugIn;

public class TenpayFactory extends BasePayPlugInFactory {
  private int plugInType;

  public TenpayFactory(int plugInType) {
    this.plugInType = plugInType;
  }

  @Override
  public BasePayPlugIn createPayPlugIn() throws Exception {
    switch (this.plugInType) {
      case ACCESSTYPE_WEB: {
        return new TenpayPlugIn();
      }
      case ACCESSTYPE_WAP: {}
      case ACCESSTYPE_APP: {}
      default: {
        return new TenpayPlugIn();
      }
    }
  }
}
