package com.kmzyc.zkconfig;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

public class DefaultCuratorWatcher implements CuratorWatcher {
    private CuratorFramework zkClient;
    private final Logger logger = LoggerFactory.getLogger(DefaultCuratorWatcher.class);

    public DefaultCuratorWatcher(CuratorFramework zkClient) {
        this.zkClient = zkClient;
    }

    @Override
    public void process(WatchedEvent event) throws Exception {
        logger.info("DefaultCuratorWatcher event.type({}) event.state({})", event.getType(),
                event.getState());
        String path = event.getPath();
        if (path == null) {
            return;
        }
        try {
            Watcher.Event.EventType type = event.getType();
            if (Watcher.Event.EventType.NodeDataChanged.equals(type)) {
                byte[] data = zkClient.getData().forPath(path);
                ByteArrayInputStream in = new ByteArrayInputStream(data, 0, data.length);
                Properties props = new Properties();
                Reader reader = new InputStreamReader(in, "UTF-8");
                props.load(reader);
                ConfigurationUtil.buildConfig(props);
                logger.info("reload 配置 {} success.", path);
            }
        }finally {
            zkClient.checkExists().usingWatcher(this).forPath(path);
        }


    }
}
