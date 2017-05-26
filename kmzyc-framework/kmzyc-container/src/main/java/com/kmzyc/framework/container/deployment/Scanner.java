package com.kmzyc.framework.container.deployment;

import java.io.File;
import java.util.Map;

/**
 * 对象扫描器.
 *
 * @author weishanyao
 *
 */
public interface Scanner {
    
    /**
     * 根据提供的文件扫描目录.
     *
     *
     */
    void scanDirectories(Iterable<File> directories);
    
    /**
     * 根据提供的文件名扫描资源.
     * 
     * @param configs 一个路径及其处理器的映射.
     * 
     */
    void scanResources(Map<String,ConfigHandler> configs);

}
