package com.kmzyc.framework;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;

public class WebContextListener  extends org.springframework.web.context.ContextLoaderListener {
  
  private static final String LOG_MODE = "slf4j";
  
  @Override
  public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
      //设置dubbo使用slf4j来记录日志  
      //详见 com.alibaba.dubbo.common.logger.LoggerFactory static代码块
      System.setProperty("dubbo.application.logger", LOG_MODE);  
      
      // 详见 org.apache.cxf.common.logging.LogUtils
      System.setProperty(org.apache.cxf.common.logging.LogUtils.KEY, LOG_MODE);  
      
      //详见com.alibaba.druid.support.logging.LogFactory
      System.setProperty("druid.logType", LOG_MODE);
      return super.initWebApplicationContext(servletContext);
  }

}
