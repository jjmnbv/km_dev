package com.kmzyc.framework.container.ejb;

import java.util.Hashtable;


/**
 * EJB接口定义了可以根据部署描述符(ejb-jar.xml)来配置EJB属性.
 * 
 * @author weishanyao
 *
 */
public interface EJB {
    
    /** EJB jndi 模式(缺省模式,当Local或Remote未配置时使用此模式)，不同的EJB实现其JNDI策略不同 */
    String KEY_JNDI_PATTERN = "com.km.framework.ejb.jndiPattern";
    
    /** EJB jndi Local模式，不同的EJB实现其JNDI策略不同 */
    String KEY_JNDI_LOCAL_PATTERN = "com.km.framework.ejb.localJndiPattern";
    
    /** EJB jndi Remote模式，不同的EJB实现其JNDI策略不同 */
    String KEY_JNDI_REMOTE_PATTERN = "com.km.framework.ejb.remoteJndiPattern";

    enum Range {REMOTE,LOCAL}
    
    /**
     * 根据部署描述符(ejb-jar.xml)来配置EJB.
     * 
     * @param descriptor 部署描述符对象.
     * @param env 上下文.
     * 
     */
    void configure(EjbDescriptor descriptor, Hashtable<?,?> env);
}
