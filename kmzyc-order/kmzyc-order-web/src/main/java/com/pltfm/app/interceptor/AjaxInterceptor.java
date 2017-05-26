package com.pltfm.app.interceptor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Repository;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.pltfm.app.action.BaseAction;

@SuppressWarnings("unchecked")
@Repository("ajaxInterceptor")
public class AjaxInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = 581009487438542722L;
  private Logger logger = Logger.getLogger(AjaxInterceptor.class);

  public void init() {

  }

  public void destroy() {

  }

  public String intercept(ActionInvocation invocation) throws Exception {
    Map map = invocation.getInvocationContext().getParameters();
    Set keys = map.keySet();
    Iterator iters = keys.iterator();
    while (iters.hasNext()) {
      Object key = iters.next();
      Object value = map.get(key);
      map.put(key, transfer((String[]) value));
    }
    String result = invocation.invoke();
    if (null == result) {
      /*
       * 在调用getWriter之前未设置编码(既调用setContentType或者setCharacterEncoding方法设置编码) ,
       * HttpServletResponse则会返回一个用默认的编码(既ISO-8859-1)编码的PrintWriter实例。这样就会
       * 造成中文乱码。而且设置编码时必须在调用getWriter之前设置,不然是无效的。
       */
      HttpServletResponse response = ServletActionContext.getResponse();
      response.reset();
      response.setContentType("text/html;charset=utf-8");
      // response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      out.println(((BaseAction) invocation.getAction()).getMsg());
      // logger.debug("----------返回视图：" + result + "----------");
      logger.debug("----------消息：" + ((BaseAction) invocation.getAction()).getMsg() + "----------");
      out.flush();
      out.close();
    }
    return result;

  }

  /**
   * @Description: 遍历参数数组里面的数据，去掉空格
   * @param params
   * @return
   */
  private String[] transfer(String[] params) {
    ArrayList resultList = new ArrayList();
    for (int i = 0; i < params.length; i++) {
      if (StringUtils.isEmpty(params[i])) continue;
      params[i] = params[i].trim();
      resultList.add(params[i]);
    }
    return params;
  }

}
