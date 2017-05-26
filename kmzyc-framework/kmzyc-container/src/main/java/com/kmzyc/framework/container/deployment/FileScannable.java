package com.kmzyc.framework.container.deployment;

/**
 * 文件扫描处理器.
 *
 * @author weishanyao
 */
public interface FileScannable extends Scannable {

    /**
     * 处理捕获的一个文件或类.
     * <p>注意：捕获之前文件或类并未载入内存.</p>
     *
     * @param name 捕获的文件名或类名.
     */
    void parse(String name);
}
