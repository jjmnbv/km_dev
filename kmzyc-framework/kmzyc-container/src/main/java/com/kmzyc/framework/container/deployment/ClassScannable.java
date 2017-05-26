package com.kmzyc.framework.container.deployment;

/**
 * 类扫描处理器.
 *
 * @author weishanyao
 */
public interface ClassScannable extends Scannable {

    /**
     * 处理捕获的一个文件或类.
     * <p>注意：捕获之前文件或类并未载入内存.</p>
     *
     * @param event 扫描事件.
     */
    void parse(ScanContext event);
}
