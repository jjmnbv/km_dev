package com.kmzyc.framework.container.deployment;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;

import com.kmzyc.framework.container.core.Instances;
import com.kmzyc.framework.container.core.SessionBeanInstances;
import com.kmzyc.framework.container.ejb.EjbDescriptor;
import com.kmzyc.framework.container.utils.XML;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Element;


/**
 * EJB资源文件(ejb-jar.xml)配置处理器.
 * 
 * @author weishanyao
 *
 */
public class EJBConfigHandler implements ConfigHandler {
    private static final Log log = LogFactory.getLog(EJBConfigHandler.class);
    
    private Hashtable<Object,Object> props;
    
    public EJBConfigHandler(Hashtable<Object,Object> env) {
        super();
        init(env);
    }

    public void init(Hashtable<Object,Object> env) {
        props = env;
    }

    public void handle(URL url) {
        InputStream ejbJarXml = null;
        try {
            ejbJarXml = url.openStream();
            if (ejbJarXml!=null) {
                parseEjbJarXml(XML.getRootElementSafely(ejbJarXml));
             }
        }
        catch (IOException e) {
            log.error("Fatal to load META-INFO/ejb-jar.xml!", e);
        }
        catch (Exception e) {
            log.error("Fatal to read META-INFO/ejb-jar.xml!", e);
        }
        finally {
            if (null!=ejbJarXml) {
                try {ejbJarXml.close();}
                catch (IOException e) {}
            }
        }
    }

    
    @SuppressWarnings("unchecked")
    private void parseEjbJarXml(Element root) {
        Element beans = root.element("enterprise-beans");
        if (null != beans) {
            Class<?> type;
            SessionBeanInstances sbis;
            for (Element bean: (List<Element>) beans.elements("session")) {
                Element ejbClass = bean.element("ejb-class");
                if (null!=ejbClass) {
                    EjbDescriptor descriptor = new EjbDescriptor();
                    descriptor.setEjbName(bean.element("ejb-name").getTextTrim());
                    descriptor.setEjbClassName(ejbClass.getTextTrim());
                    Element sessionType = bean.element("session-type");
                    if (null!=sessionType && sessionType.getTextTrim().equalsIgnoreCase("Stateful")) {
                        descriptor.setBeanType(type=javax.ejb.Stateful.class);
                    }
                    else {
                        descriptor.setBeanType(type=javax.ejb.Stateless.class);
                    }
                    Element bi = bean.element("remote");
                    if (null!=bi) {
                        String s = bi.getTextTrim();
                        if (StringUtils.isNotBlank(s)) {
                            descriptor.setRemoteInterfaces(StringUtils.split(s,","));
                        }
                    }
                    bi = bean.element("local");
                    if (null != bi) {
                        String s = bi.getTextTrim();
                        if (StringUtils.isNotBlank(s)) {
                            descriptor.setLocalInterfaces(StringUtils.split(s,","));
                        }
                    }
                    sbis = (SessionBeanInstances) Instances.DEFAULT_INSTANCES.get(type);
                    if (null!=sbis) sbis.configure(descriptor,props);
                }
            }
            for (Element bean: (List<Element>) beans.elements("message-driven")) {
                Element ejbClass = bean.element("ejb-class");
                if (null != ejbClass) {
                    EjbDescriptor descriptor = new EjbDescriptor();
                    descriptor.setEjbName(bean.element("ejb-name").getTextTrim());
                    descriptor.setEjbClassName(ejbClass.getTextTrim());
                    descriptor.setBeanType(javax.ejb.MessageDriven.class);
                    //TODO: 未处理驱动BEAN.
                }
            }
        }
    }
    
}
