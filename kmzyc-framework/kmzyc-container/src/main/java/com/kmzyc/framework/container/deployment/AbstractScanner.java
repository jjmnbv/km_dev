package com.kmzyc.framework.container.deployment;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 对象扫描器.
 *
 * @author weishanyao
 */
public abstract class AbstractScanner implements Scanner {
    private static final Log log = LogFactory.getLog(AbstractScanner.class);

    protected final Map<String, ScanHandler> handlers;
    protected final ClassLoader classLoader;

    public AbstractScanner(ClassLoader classLoader, Map<String, ScanHandler> handlers) {
        this.classLoader = null != classLoader ? classLoader : Thread.currentThread().getContextClassLoader();
        this.handlers = handlers;
    }

    protected final void handleItem(String name) {
        if (log.isDebugEnabled()) log.debug("Found " + name);
        if (StringUtils.isNotEmpty(name)) {
            int dot = name.lastIndexOf('.');
            if (dot > -1) {
                ScanHandler handler = handlers.get(name.substring(dot + 1).toLowerCase());
                if (null != handler) handler.handle(name, classLoader);
            }
        }
    }
}
