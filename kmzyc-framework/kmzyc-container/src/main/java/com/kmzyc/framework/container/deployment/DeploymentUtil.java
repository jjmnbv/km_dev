package com.kmzyc.framework.container.deployment;

import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.Properties;


/**
 * 组件部署工具.
 *
 * @author weishanyao
 */
public class DeploymentUtil {
    static final String key = Deployments.KEY_FACTORY;
    static final String root = Deployments.KEY_APP_ROOT;

    public static Object deploy(String url, String driver, String user, String pwd) {
        Properties ps = new Properties();
        if (null != url) ps.setProperty("com.km.connection.url", url);
        if (null != driver) ps.setProperty("com.km.connection.driver", driver);
        if (null != user) ps.setProperty("com.km.connection.user", user);
        if (null != pwd) ps.setProperty("com.km.connection.pwd", pwd);
        return deploy(ps);
    }

    public static Object deploy(Hashtable<?, ?> env) {
        Properties ps = new Properties();
        if (null != env) {
            String s = (String) env.remove("uoss.config");
            ps.putAll(env);
            if (null != s) {
                File f = new File(s);
                if (f.isFile()) {
                    try {
                        ps.load(new FileInputStream(f));
                    } catch (Exception e) {
                        throw new IllegalArgumentException(s + " specified in uoss.config property is invalid properties file!");
                    }
                }
            }
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return new Deployments(classLoader, ps).scan();
    }
}
