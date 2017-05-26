package com.kmzyc.framework.container.deployment;

import java.util.ArrayList;
import java.util.Hashtable;

import javassist.bytecode.ClassFile;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kmzyc.framework.container.annotations.Component;
import com.kmzyc.framework.container.core.Instances;


/**
 * 类扫描处理器
 * @author weishanyao
 *
 */
public class ClassScanHandler extends AbstractScanHandler {
    private static final Log log = LogFactory.getLog(ClassScanHandler.class);
    
    /** 键：组件标识（注解） */
    public final static String KEY_CLASS_FROM_ANNOTATIONS = "com.km.framework.deployment.classFromAnnotations";

    protected final ArrayList<ClassScannable> listeners = new ArrayList<ClassScannable>();

    private Instances[] instanceFactories;
    private Class<?>[] annotationTypes;

    public ClassScanHandler(Hashtable<?,?> env) {
        super();
        init(env);
        initAnnotationTypes();
        initListeners();
    }
    
    public Object getResult() {
        return instanceFactories;
    }

    public void handle(String name, final ClassLoader classLoader) {
        try {
            final ClassFile classFile = getClassFile(name,classLoader);
            final String classname = classFile.getName();
            Instances ins;
            if (null!=(ins=getInstances(getAnnotation(classFile,annotationTypes)))) {
                log.debug("Found Component - "+ classname);
                ins.parse(classLoader.loadClass(classname),env);
            }
            fireClassScannerListeners(
                new ScanContext() {
                    public Hashtable<?,?> getEnvironment() {return env;}
                    public String getClassName() {return classname;}
                    public Class<?> loadClass() throws ClassNotFoundException {
                        return classLoader.loadClass(classname);
                    }
                    public boolean checkAnnotation(final Class<?>... annos) {
                        return hasAnnotation(classFile, annos);
                    }
                }
            );

         }
         catch (Throwable e) {
             log.warn("Failed to load classfile: \"" + name+"\" - "+ e.getMessage());
          }
      }


    protected void fireClassScannerListeners(ScanContext event) {
        for (ClassScannable s: listeners) {
            s.parse(event);
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
                    c = Class.forName(ss[i].trim());
                    if (ClassScannable.class.isAssignableFrom(c)) {
                        addListener((ClassScannable)c.newInstance());
                    }
                }
                catch (Exception e) {e.printStackTrace();
                    log.error("Can't register listener("+ss[i]+") for ClassScanHandler!",e);
                }
            }
        }
    }

    private void initAnnotationTypes() {
        String[] ss = null;
        if (null!=env) ss = StringUtils.split((String)env.get(KEY_CLASS_FROM_ANNOTATIONS),",");
        if (null==ss || ss.length<1) {
            annotationTypes = new Class[] {Resource.class,Component.class,null,null};
            try {annotationTypes[2] = Class.forName("javax.ejb.Stateless");}
            catch (ClassNotFoundException e) {/*log.error("Can't load class javax.ejb.Stateful",e);*/}
            try {annotationTypes[3] = Class.forName("javax.ejb.Stateful");}
            catch (ClassNotFoundException e) {/*log.error("Can't load class javax.ejb.Stateless",e);*/}
        }
        else {
            annotationTypes = new Class[ss.length];
            for (int i=0; i<ss.length; i++) {
                try {annotationTypes[i] = Class.forName(ss[i].trim());}
                catch (ClassNotFoundException e) {log.error("Can't load class "+ss[i],e);}
            }
        }
        
        instanceFactories = new Instances[annotationTypes.length];
        for (int i=0; i<annotationTypes.length; i++) {
            if (null==annotationTypes[i]) continue;
            String s = (String)env.get(annotationTypes[i].getName()+".instances");
            if (StringUtils.isNotBlank(s)) {
                try {
                    Instances.DEFAULT_INSTANCES.put(annotationTypes[i], instanceFactories[i]=(Instances)Class.forName(s.trim()).newInstance());
                }
                catch(Exception e) {log.error("Can't create Instances "+s,e);}
            }
            else instanceFactories[i]=Instances.DEFAULT_INSTANCES.get(annotationTypes[i]);
        }
    }

    private void addListener(ClassScannable listener) {
        if (null!=listener) {
            listeners.add(listener);
        }
    }
    
    private Instances getInstances(Class<?> annoClass) {
        if (null==annoClass || null==annotationTypes) return null;
        for (int i=0; i<annotationTypes.length; i++) {
            if (annoClass.equals(annotationTypes[i])) return instanceFactories[i];
        }
        return null;
    }


    // --
}
