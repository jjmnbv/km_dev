package com.kmzyc.framework.container.lang;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class AbstractCommand implements Command {
    final private static Log log = LogFactory.getLog(AbstractCommand.class);

    protected Commands source;
    protected String name;
    protected final boolean repeatable;

    @Resource
    protected UossContext context;

    public AbstractCommand() {
        this(false);
    }

    public AbstractCommand(boolean repeatable) {
        super();
        this.repeatable = repeatable;
    }

    @Override
    public void setSource(Commands source) {
        this.source = source;
        if (null == context) {
            try {
                source.context.initInstance(this, name);
            } catch (UossException e) {
                log.error("Fail to init instance " + this + " (name: " + name + ")", e);
            }
        }
        context = source.context;
    }

    @Override
    public boolean isRepeatable() {
        return repeatable;
    }
}
