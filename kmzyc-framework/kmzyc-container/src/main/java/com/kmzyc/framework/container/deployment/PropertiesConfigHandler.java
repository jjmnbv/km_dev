package com.kmzyc.framework.container.deployment;

import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 属性文件资源配置处理器.
 * 
 * @author weishanyao
 *
 */
public class PropertiesConfigHandler implements ConfigHandler {
    private static final Log log = LogFactory.getLog(PropertiesConfigHandler.class);
    private final Hashtable<Object,Object> env;
    
    public PropertiesConfigHandler(Hashtable<Object,Object> env) {
        super();
        this.env = env;
    }

    public void handle(URL url) {
        if (null!=env && null!=url) {
            try {
                if (env instanceof Properties) {
                    ((Properties)env).load(url.openStream());
                }
                else {
                    Properties props = new Properties();
                    props.load(url.openStream());
                    env.putAll(props);
                }
            }
            catch (IOException e) {
                log.error("Can't load resource from "+url.getPath(), e);
            }
        }
    }

    public void init(Hashtable<Object,Object> env) {/*do nothing*/}

}
