package com.kmzyc.framework.container.deployment;

import java.util.Hashtable;


/**
 * 扫描处理器.
 * 
 * @author weishanyao
 *
 */
public interface ScanHandler {
    
    /** 扫描处理器之监听器类(可以注册多个,中间用逗号分隔) */
    String KEY_SCANHANDLER_LISTENERS = "com.km.framework.deployment.ScanHandler.listeners";

    void init(Hashtable<?,?> env);
    
    
    void handle(String name, ClassLoader classLoader);
    
    
    Object getResult();
}
