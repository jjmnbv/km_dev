package com.kmzyc.framework.drools;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 规则处理导向类
 *
 * @author Administrator
 */

public class CmdsBase {

    //public static ApplicationContext application = new ClassPathXmlApplicationContext("spring/applicationContext-common.xml");
    public static ApplicationContext application = new ClassPathXmlApplicationContext(new String[]{"spring/*.xml"});

    public static Object getBean(String beanName) {
        return application.getBean(beanName);
    }

}



