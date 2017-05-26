package com.kmzyc.framework.container.deployment;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.kmzyc.framework.container.lang.Destroyable;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * 容器启动监听器。
 *
 * @author weishanyao
 */
public class DeploymentListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent context) {
        ServletContext sctx = context.getServletContext();
        Object o = sctx.getAttribute(DeploymentUtil.key);
        if (o instanceof Destroyable) {
            ((Destroyable) o).destroy();
        }
    }

    public void contextInitialized(ServletContextEvent context) {
        ServletContext sctx = context.getServletContext();
        sctx.setAttribute(DeploymentUtil.key, DeploymentUtil.deploy(getInitParameters(sctx)));
    }

    private Hashtable<String, String> getInitParameters(ServletContext sctx) {
        Hashtable<String, String> table = new Hashtable<String, String>();
        String key;
        for (Enumeration<?> e = sctx.getInitParameterNames(); e.hasMoreElements(); ) {
            table.put(key = (String) e.nextElement(), sctx.getInitParameter(key));
        }
        // String s = table.get("com.km.classScanner.classes");//兼容旧版本.
//        if (StringUtils.isNotBlank(s)) {
//            String l = table.get(ScanHandler.KEY_SCANHANDLER_LISTENERS);
//            if (StringUtils.isNotBlank(l)) {
//                s = s.trim();
//                if (!s.endsWith(",")) s += ",";
//                s += l.trim();
//            }
//            table.put(ScanHandler.KEY_SCANHANDLER_LISTENERS, s);
//        }
//        s = sctx.getRealPath("/");
//        if (StringUtils.isNotBlank(s)) table.put(DeploymentUtil.root, s);
        return table;
    }
}
