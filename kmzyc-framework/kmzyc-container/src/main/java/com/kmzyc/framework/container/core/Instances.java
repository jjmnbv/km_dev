package com.kmzyc.framework.container.core;

import com.kmzyc.framework.container.annotations.Component;
import com.kmzyc.framework.container.deployment.ScanHandler;
import com.kmzyc.framework.container.lang.Container;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


/**
 * 实例管理器.
 * <p>负责细化对象工厂（ObjectFactory）的实例管理工作</p>
 * @author weishanyao
 */
public interface Instances extends Container {
    
    /** 实例管理器 */
    Map<Class<?>,Instances> DEFAULT_INSTANCES = new HashMap<Class<?>,Instances>() {
        private static final long serialVersionUID = 1L;
        {
            put(Resource.class, new ComponentInstances());
            put(Component.class, new ComponentInstances(Component.class));
            try{put(Class.forName("javax.ejb.Stateless"),new StatelessInstances());}catch(Exception e){}
            try{put(Class.forName("javax.ejb.Stateful"),new StatefulInstances());}catch(Exception e){}
        }
    };
    
    /**
     * 扫描处理器{@link ScanHandler#handle(String, ClassLoader)}扫描到类implClass时调用.
     * 
     * @param implClass 扫描处理器传入的实现类.
     * @param env 上下文环境.
     *
     */
    void parse(Class<?> implClass, Hashtable<?,?> env);
    
}
