package com.kmzyc.framework.rule;

import javax.annotation.Resource;
import javax.swing.event.MenuDragMouseListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;


/**
 * 基于Drools的消息监听器.
 *
 * @param<M> 消息的具体类型，根据{@link MessageObserver}具体类的不同而异.
 */
@Resource(name = "drools")
public abstract class ProcessListener implements MenuDragMouseListener {
    final private static Log log = LogFactory.getLog(ProcessListener.class);

    /**
     * 规则处理上下文.
     */
    protected ProcessContext processContext;


    public void receive(Message event) {

    }
}
