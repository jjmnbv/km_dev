package com.kmzyc.framework.container.deployment;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/** 处理XML文件 */
public class XmlScanHandler extends AbstractScanHandler {
    private static final Log log = LogFactory.getLog(XmlScanHandler.class);
    
    protected final ArrayList<FileScannable> listeners = new ArrayList<FileScannable>();

    public XmlScanHandler(Hashtable<?,?> env) {
        super();
        init(env);
        initListeners();
    }

    public Object getResult() {
        return null;
    }

    public void handle(String name, ClassLoader classLoader) {
        fireFileScannerListeners(name);
    }


    protected void fireFileScannerListeners(String name) {
        for (FileScannable s: listeners) {
            s.parse(name);
        }
    }
    
    // --
    
    private void initListeners() {
        String[] ss = null;
        if (null!=env) ss = StringUtils.split((String)env.get(KEY_SCANHANDLER_LISTENERS),",");
        if (null!=ss && ss.length>0) {
            Class<?> c;
            for (int i=0; i<ss.length; i++) {
                try {
                    c = Class.forName(ss[i]);
                    if (FileScannable.class.isAssignableFrom(c)) {
                        addListener((FileScannable)c.newInstance());
                    }
                }
                catch (Exception e) {
                    log.error("Can't register listener("+ss[i]+") for ClassScanHandler!",e);
                }
            }
        }
    }

    private void addListener(FileScannable listener) {
        if (null!=listener) listeners.add(listener);
    }
}
