package com.kmzyc.framework.container.deployment;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;


/**
 * 扫描处理器的抽象实现.
 * 
 * @author weishanyao
 *
 */
public abstract class AbstractScanHandler implements ScanHandler {
    protected Hashtable<?,?> env;

    public void init(Hashtable<?,?> env) {
        this.env = env;
    }


    protected static boolean hasAnnotation(ClassFile cf, Class<?>... annotationClasses) {
        return null!=getAnnotation(cf,annotationClasses);
    }  
    protected static Class<?> getAnnotation(ClassFile cf, Class<?>... annotationClasses) {
        if (null==annotationClasses) return null;
        AnnotationsAttribute visible = 
            (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.visibleTag);
        if (null==visible) return null;
        for (Class<?> anno: annotationClasses) {
            if (null!=anno && visible.getAnnotation(anno.getName())!=null) return anno;
        }
        return null;
    }  
    protected ClassFile getClassFile(String name, ClassLoader classLoader) throws IOException {
        InputStream stream = classLoader.getResourceAsStream(name);
        DataInputStream dstream = new DataInputStream(stream);
        try {
            return new ClassFile(dstream);
        }
        finally {
            dstream.close();
            stream.close();
        }
    }
}
