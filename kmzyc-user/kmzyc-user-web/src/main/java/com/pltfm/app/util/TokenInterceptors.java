package com.pltfm.app.util;

import com.opensymphony.xwork2.ActionInvocation;

import java.lang.reflect.Method;

/**
 * token拦截器，对加有token注解的方法进行token验证
 * 
 * @author zhl
 */
public class TokenInterceptors extends org.apache.struts2.interceptor.TokenInterceptor {
  private static final long serialVersionUID = 8350356127998541171L;

  /**
   * 对查看token是否存在，存在则对token进行验证。
   */
  @Override
  protected String doIntercept(ActionInvocation invocation) throws Exception {
    Object action = invocation.getAction();
    if (action != null) {
      Method method = getActionMethod(action.getClass(), invocation.getProxy().getMethod());
      Token token = method.getAnnotation(Token.class);
      if (token != null) {
        return super.doIntercept(invocation);
      }
    }
    return invocation.invoke();
  }

  /**
   * 获得一个有token注解的method对象
   * 
   * @param actionClass
   * @param methodName
   * @return
   * @throws NoSuchMethodException
   */
  public static final Method getActionMethod(Class<? extends Object> actionClass, String methodName)
      throws NoSuchMethodException {
    Method method;
    try {
      method = actionClass.getMethod(methodName, new Class[0]);
    } catch (NoSuchMethodException e) {
      String altMethodName = methodName.substring(0, methodName.indexOf("."));
      method = actionClass.getMethod(altMethodName, new Class[0]);
      e.printStackTrace();
    }
    return method;
  }
}
