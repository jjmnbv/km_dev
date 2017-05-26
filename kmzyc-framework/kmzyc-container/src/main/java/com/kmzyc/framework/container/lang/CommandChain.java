package com.kmzyc.framework.container.lang;

/**
 * 命令链.
 * @author weishanyao
 */
public interface CommandChain {
	
    /**
     * 取回命令组对象，此方法在线程不安全的环中使，否则可以使用{@link Command}.source取回命令组对象.
     * 
     * <p>注：在线程不安全的环境中，{@link Command}.source不确定能够取得当前线程的命令组对象</p>
     * 
     *
     */
    Commands getSource();
    
	/**
	 * 执行当前命令，并将控制权向下转移.
	 * 
	 * @param args 下一节点命令的执行参数。
	 * 
	 * @see {@link Command#execute(CommandChain, Object...)}.
	 * 
	 */
	void doProcess(Object... args) throws Exception;
}
