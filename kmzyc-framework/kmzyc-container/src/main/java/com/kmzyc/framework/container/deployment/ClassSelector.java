package com.kmzyc.framework.container.deployment;

/**
 * 扫描时选择一个类.
 *
 * @author weishanyao
 * @version $2010-8-7 下午06:04:51$
 *
 */
public interface ClassSelector {

    boolean match();
}
