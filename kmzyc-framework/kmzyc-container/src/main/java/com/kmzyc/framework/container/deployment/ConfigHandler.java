package com.kmzyc.framework.container.deployment;

import java.net.URL;
import java.util.Hashtable;


/**
 * 资源配置处理器.
 * 
 * @author weishanyao
 *
 */
public interface ConfigHandler {

    void init(Hashtable<Object,Object> env);
    
    void handle(URL url);
}
