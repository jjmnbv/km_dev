/*
 * package com.pltfm.sys.util;
 * 
 * import org.springframework.context.ApplicationContext; import
 * org.springframework.context.ConfigurableApplicationContext; import
 * org.springframework.context.support.FileSystemXmlApplicationContext;
 * 
 * import com.kmzyc.commons.exception.ServiceException;
 * 
 * public class BeanFactory {
 * 
 *//**
   * SpringContext的实例，使用加锁双检查（Double-checked locking）的SingleTon实现
   * 参见：http://www.ibm.com/developerworks/java/library/j-dcl.html
   */
/*
 * static private ConfigurableApplicationContext beanContext = null;
 * 
 *//**
   * 配置文件
   *//*
     * static private String[] config = new String[] { //
     * "classpath:spring-context-application.xml",
     * "classpath:config/spring-context-application.xml", };
     * 
     * static public ApplicationContext getBeanContext() {
     * 
     * if (beanContext == null) { synchronized(BeanFactory.class) { if (beanContext == null) {
     * //System.out.println("系统加载配置文件，加载一次" + config.toString()); beanContext = new
     * FileSystemXmlApplicationContext(config); } } } return beanContext; }
     * 
     * static public void setBeanContext(ConfigurableApplicationContext context) { beanContext =
     * context; }
     * 
     * static public Object getBean(String beanName) throws ServiceException { ApplicationContext
     * ctx = getBeanContext();
     * 
     * if (ctx != null) { return getBeanContext().getBean(beanName); } else { throw new
     * ServiceException(ErrorCode.NULL_INPUT, "无法获得ApplicationContext"); } }
     * 
     * static public void destroyAllBeans() { ApplicationContext ctx = getBeanContext(); if (ctx !=
     * null) { ((ConfigurableApplicationContext) getBeanContext()).close(); setBeanContext(null); }
     * }
     * 
     * 
     * }
     */
