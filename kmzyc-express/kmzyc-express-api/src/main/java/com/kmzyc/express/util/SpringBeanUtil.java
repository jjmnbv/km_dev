package com.kmzyc.express.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取spring容器内java bean的工具类
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {

  private static ApplicationContext _context = null;

  @Override
  public void setApplicationContext(ApplicationContext context){
    _context = context;
  }

  /**
   * 获取bean
   * 
   * @param <T> 泛型类型
   * @param name bean name
   * @param clz bean 类型
   * @param context servlet上下文
   * @return bean实例
   */
  public static <T> T getBean(String name, Class<T> clz) {
      _context.getBean(name);
    return clz.cast(_context.getBean(name));
  }
}
