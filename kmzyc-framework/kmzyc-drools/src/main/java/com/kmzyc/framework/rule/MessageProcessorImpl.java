package com.kmzyc.framework.rule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.MessageCreator;

import com.kmzyc.framework.container.aop.MethodDescription;
import com.kmzyc.framework.container.lang.UossContext;
import com.kmzyc.framework.container.utils.TypeHelper;

@Resource
public abstract class MessageProcessorImpl implements MessageCreator {
    private static final Log log = LogFactory.getLog(MessageProcessorImpl.class);

    @Resource private RuleLauncher rule;
    @Resource private UossContext context;
    
    public Object process(Object... input) throws Exception {
        if (null!=input && input.length>0) {
            if (input[0] instanceof Class || input[0] instanceof MethodDescription) {
                Class<?> api = null;
                Method m = null;
                if (input[0] instanceof Class) api = (Class<?>)input[0];
                else {
                    MethodDescription md = (MethodDescription)input[0];
                    api = md.getDecClass();
                    m = api.getDeclaredMethod(md.getName(), md.getArgTypes());
                }
                if (api.isInterface()) {
                    Object[] params = new Object[input.length-1];
                    System.arraycopy(input, 1, params, 0, params.length);
                    if (null==m) m = findTargetMethod(api,params);
                    if (null!=m) {
                        if (log.isDebugEnabled()) log.debug("Invoking remote method 【"+m+"】 on remote interface ...");
                        Object impl = context.getInstance(api);
                        try {
                            return m.invoke(impl,params);
                        }
                        catch (InvocationTargetException e) {
                            Exception cause = (Exception)e.getCause();
                            if (null!=cause) throw cause;
                            else throw e;
                        }
                    }
                }
            }
            
        }
        return null;
    }

    private Method findTargetMethod(Class<?> api, Object[] params) {
        Method[] ms = api.getMethods();
        for (Method m: ms) {
            Class<?>[] ts = m.getParameterTypes();
            if (ts.length!=params.length) continue;
            if (matchParam(ts,params)) return m;
        }
        return null;
    }

    private boolean matchParam(Class<?>[] types, Object[] params) {
        for (int i=0; i<types.length; i++) {
            if (!TypeHelper.checkAssignable(types[i], params[i])) return false;;
        }
        return true;
    }
}
