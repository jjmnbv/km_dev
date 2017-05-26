package com.kmzyc.b2b.util.hessian;

import javax.servlet.ServletResponse;

/**
 * hessian上下文
 * 
 * @author xiongliguang
 * 
 */
public class HessianContext {

  private ServletResponse _hresponse;
  private static final ThreadLocal<HessianContext> _localContext =
      new ThreadLocal<HessianContext>() {
        @Override
        public HessianContext initialValue() {
          return new HessianContext();
        }
      };

  private HessianContext() {}

  public static void setResponse(ServletResponse response) {
    _localContext.get()._hresponse = response;
  }

  public static ServletResponse getResponse() {
    return _localContext.get()._hresponse;
  }

  public static void clear() {
    _localContext.get()._hresponse = null;
  }
}
