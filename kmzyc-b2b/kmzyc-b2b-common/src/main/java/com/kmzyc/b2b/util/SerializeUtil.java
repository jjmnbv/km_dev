package com.kmzyc.b2b.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
    static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

    /**
     * 序列化
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            if (baos != null) baos.close();
            if (oos != null) oos.close();
        } catch (Exception ignored) {

        }
        return null;
    }

    /**
     * 反序列化
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (bais != null) bais.close();
                if (ois != null) ois.close();
            } catch (Exception e) {

            }

        }
        return null;
    }
}
