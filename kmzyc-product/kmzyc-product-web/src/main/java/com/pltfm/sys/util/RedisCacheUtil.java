package com.pltfm.sys.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisCacheUtil {

    private static Logger logger = LoggerFactory.getLogger(RedisCacheUtil.class);

    /**
     * 序列化对象
     */
    public static byte[] serialize(Object obj) throws IOException {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } finally {
            if (baos != null) baos.close();
            if (oos != null) oos.close();
        }
    }

    /**
     * 反序列化对象
     */
    public static Object unserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        if (bytes == null) return null;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            if (bais != null) bais.close();
            if (ois != null) ois.close();

        }
    }

}