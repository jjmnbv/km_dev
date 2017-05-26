package com.kmzyc.framework.container.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * 命令的宿主对象（或称命令组对象）.
 *
 * @author weishanyao
 */
public class Commands implements Configurable {
    final private static Logger log = LoggerFactory.getLogger(Commands.class);

    protected final LinkedList<Command> commands = new LinkedList<Command>();
    protected final boolean repeatable;

    protected UossContext context;
    protected String name;

    /**
     * 延时实例化的命令
     */
    private String[] cmdns;

    /**
     * 规则属性组，可在一个规则的处理周期内共享数据.
     */
    protected final Map<String, Object> attrs = Collections.synchronizedMap(new HashMap<String, Object>());

    public Commands() {
        this(null, false);
    }

    public Commands(UossContext context) {
        this(context, false);
    }

    public Commands(UossContext context, boolean repeatable) {
        super();
        this.repeatable = repeatable;
        this.context = context;
    }

    public void init(UossContext context, String name) throws UossException {
        if (null == this.context) this.context = context;
        this.name = name;
    }

    public Commands addCommand(Command... cmds) {
        if (null != cmds) {
            for (Command cmd : cmds) {
                commands.add(cmd);
                cmd.setSource(this);
            }
        }
        return this;
    }

    public Commands addCommand(int index, Command cmd) {
        commands.add(index, cmd);
        cmd.setSource(this);
        return this;
    }

    public Commands addCommand(Object... cmds) {
        if (null != cmds && cmds.length > 0) {
            Command cmd = null;
            for (Object o : cmds) {
                if (null == o) continue;
                else if (o instanceof Command) {
                    cmd = (Command) o;
                } else if (null != context) {
                    try {
                        cmd = context.getInstance(Command.class, o.toString());
                    } catch (UossException e) {
                        log.error("Fail to instante Command of name: " + o, e);
                        continue;
                    }
                } else {
                    addCommandName(o.toString());
                }
                commands.add(cmd);
                cmd.setSource(this);
            }
        }
        return this;
    }

    public Commands addCommand(int index, Object cmd) {
        Command c = null;
        if (null != cmd) {
            if (cmd instanceof Command) {
                c = (Command) cmd;
            } else if (null != context) {
                try {
                    c = context.getInstance(Command.class, cmd.toString());
                } catch (UossException e) {
                    log.error("Fail to instante Command of name: " + cmd, e);
                }
            } else {
                addCommandName(index, cmd.toString());
            }
        }
        if (null != c) {
            commands.add(index, c);
            c.setSource(this);
        }
        return this;
    }

    public List<Command> getCommands() {
        return Collections.unmodifiableList(commands);
    }


    public Object getAttribute(String name) {
        return attrs.get(name);
    }

    public Object setAttribute(String name, Object value) {
        return attrs.put(name, value);
    }

    public Object removeAttribute(String name) {
        return attrs.remove(name);
    }

    public void clearAttribute() {
        attrs.clear();
    }


    public Object process(final Object... args) throws Exception {
        PC pc = new PC(commands.iterator());
        pc.doProcess(args);
        return pc.result;
    }

    /**
     * 重新实例化(只供CompositeCommand调用)
     */
    void reInstantiate(UossContext context) {
        this.context = context;
        if (null != cmdns) {
            boolean insert = false;
            String cmdn;
            Command cmd;
            for (int i = 0; i < cmdns.length; i++) {
                cmdn = cmdns[i];
                try {
                    cmd = context.getInstance(Command.class, cmdn);
                } catch (UossException e) {
                    log.error("Fail to instante Command of name: " + cmdn, e);
                    continue;
                }
                if (null == cmdn) insert = true;
                if (insert) addCommand(i, cmd);
                else addCommand(cmd);
            }
            cmdns = null;
        }
    }

    private void addCommandName(String cmdn) {
        if (null == cmdns) {
            cmdns = new String[]{cmdn};
        } else {
            String[] cs = new String[cmdns.length + 1];
            System.arraycopy(cmdns, 0, cs, 0, cmdns.length);
            cs[cmdns.length] = cmdn;
            cmdns = cs;
        }
    }

    private void addCommandName(int index, String cmdn) {
        if (null == cmdns || cmdns.length <= index) {
            String[] cs = new String[index + 1];
            if (null != cmdns) System.arraycopy(cmdns, 0, cs, 0, cmdns.length);
            cs[index] = cmdn;
            cmdns = cs;
        } else {
            String[] cs = new String[cmdns.length + 1];
            System.arraycopy(cmdns, 0, cs, 0, index);
            System.arraycopy(cmdns, index, cs, index + 1, cmdns.length - index);
            cs[index] = cmdn;
            cmdns = cs;
        }
    }

    private class PC implements CommandChain {
        final private Iterator<Command> itr;
        Object result;

        PC(Iterator<Command> itr) {
            this.itr = itr;
        }

        public Commands getSource() {
            return Commands.this;
        }

        public void doProcess(Object... args) throws Exception {
            if (itr.hasNext()) {
                Command cmd = itr.next();
                if (!repeatable && !cmd.isRepeatable()) itr.remove();
                try {
                    if (cmd instanceof CompositeCommand) {
                        CompositeCommand ccmd = (CompositeCommand) cmd;
                        ccmd.beforeExecute(args);
                        if (ccmd.cascade && null != ccmd.commands) ccmd.commands.process(args);
                    }
                    cmd.execute(this, args);
                } catch (Exception e) {
                    if (!repeatable) while (itr.hasNext()) {
                        cmd = itr.next();
                        if (!cmd.isRepeatable()) itr.remove();
                    }
                    throw e;
                }
            } else result = null == args || args.length != 1 ? args : args[0];
        }
    }
}
