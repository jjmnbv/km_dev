package com.kmzyc.framework.container.lang;


/**
 * 复合命令抽象基类.
 * @author weishanyao
 */
public abstract class CompositeCommand extends AbstractCommand {
	
	/**
	 * 子命令队列.
	 * 
	 */
	final protected Commands commands;
	
    /**
     * 是否自动传播子命令队列(默认为true).
     * 
     */
    protected final boolean cascade;
    
	public CompositeCommand() {
		super();
		commands = null;
		cascade = false;
	}
	public CompositeCommand(boolean repeatable) {
		super(repeatable);
		commands = null;
        cascade = false;
	}
	public CompositeCommand(Command... cmds) {
		this(true,cmds);
	}
    public CompositeCommand(Object... cmds) {
        this(true,cmds);
    }
	
	/**
	 * 构造方法.
	 * 
	 * @param cascade 是否级联传播子命令队列，缺省为true.
	 * @param cmds 子命令队列.
	 * 
	 */
    public CompositeCommand(boolean cascade, Command... cmds) {
        super();
        commands = new Commands(null,true);
        commands.addCommand(cmds);
        this.cascade = cascade;
    }
    public CompositeCommand(boolean cascade, Object... cmds) {
        super();
        commands = new Commands(null,true);
        commands.addCommand(cmds);
        this.cascade = cascade;
    }

    @Override
	public void setSource(Commands source) {
        super.setSource(source);
		if (null!=commands) {
			for (Command cmd: commands.getCommands()) {
				cmd.setSource(source);
			}
			//延时实例化.
			commands.reInstantiate(context);
		}
	}
	
	/**
	 * 执行前动作，包括在级联之前执行此方法.
	 * 
	 */
	protected void beforeExecute(Object... args) {}

}
