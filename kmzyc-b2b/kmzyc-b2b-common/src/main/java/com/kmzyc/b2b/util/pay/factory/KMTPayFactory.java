package com.kmzyc.b2b.util.pay.factory;

import com.kmzyc.b2b.util.pay.BasePayPlugIn;
import com.kmzyc.b2b.util.pay.BasePayPlugInFactory;
import com.kmzyc.b2b.util.pay.plugin.KMTAppPayPlugIn;
import com.kmzyc.b2b.util.pay.plugin.KMTPayPlugIn;
import com.kmzyc.b2b.util.pay.plugin.KMTWapPayPlugIn;

/**
 * 康美通
 * 
 * @author xlg
 * 
 */
@SuppressWarnings("unused")
public class KMTPayFactory extends BasePayPlugInFactory {

  private int plugInType;

  public KMTPayFactory(int plugInType) {
    this.plugInType = plugInType;
  }

  @Override
  public BasePayPlugIn createPayPlugIn() throws Exception {
//    return new KMTPayPlugIn();
    switch (this.plugInType) {
      case ACCESSTYPE_WEB: {
        return new KMTPayPlugIn();
      }
      case ACCESSTYPE_WAP: {
        return new KMTWapPayPlugIn();
      }
      case ACCESSTYPE_APP: {
        return new KMTAppPayPlugIn();
      }
      default: {
        return null;
      }
    }
    
  }
}
