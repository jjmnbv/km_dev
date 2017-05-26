package com.kmzyc.framework.container.lang;


/**
 * 命令对象.
 * @author weishanyao
 */
public interface Command {
	
	/** 命令的宿主对象 */
	void setSource(Commands source);
	
	/** 命令是否可以重复执行 */
	boolean isRepeatable();
	
	/**
	 * 执行当前命令.
	 * 
	 * @param chain 命令链，方法必须调用{@link CommandChain#doProcess(Object...)}，并将参数传递给下游命令.
	 * @param args 命令参数.
	 * 
	 * @see {@link CommandChain#doProcess(Object...)}
	 * 
	 */
	void execute(CommandChain chain, Object... args) throws Exception;

}
