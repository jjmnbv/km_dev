package com.kmzyc.framework.container.lang;

import java.util.Map;

/**
 * 加密解密.
 * @author weishanyao
 */
public interface Encryption {

    /**
     * 加密.
     * 
     * @param info 明文.
     * @param config 相关配置.
     * 
     * @return 返回密文.
     * 
     */
    Object encrypt(Object info, Map<?,?> config) throws EncryptionException;
    
    /**
     * 解密.
     * 
     * @param info 密文.
     * @param config 相关配置.
     * 
     * @return 返回明文.
     * 
     */
    Object decrypt(Object encode, Map<?,?> config) throws EncryptionException;
    
    
    
}
