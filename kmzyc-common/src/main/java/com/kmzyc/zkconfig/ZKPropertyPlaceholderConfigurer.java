package com.kmzyc.zkconfig;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.api.UnhandledErrorListener;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.kmzyc.zkconfig.exception.ZkException;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * SPRING启动时从ZK集群加载配置文件
 *
 * @author river
 */
public class ZKPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private static final Logger logger = LoggerFactory
            .getLogger(ZKPropertyPlaceholderConfigurer.class);

    // ZooKeeper 客户端
    private CuratorFramework zkClient;

    // 配置文件存放节点路径
    private List<String> zkPaths;

    private CuratorWatcher watcher;

    public ZKPropertyPlaceholderConfigurer(CuratorFramework zkClient) {
        this.zkClient = zkClient;
        this.watcher = new DefaultCuratorWatcher(zkClient);
    }

    private List<ConnectionStateListener> listeners;

    // 设置Zookeeper启动后需要调用的监听或者，或者需要做的初始化工作。
    public void setListeners(List<ConnectionStateListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
                                     Properties props) throws BeansException {
        logger.info("loading zookeeper configuration……");
        // 保存本地配置信息
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ConfigurationUtil.put(keyStr, value);
        }
        if (zkClient.getState() != CuratorFrameworkState.STARTED)
            zkClient.start();
        registerListeners(props);

        if (zkPaths != null) {
            loadConfigFromZK(props, zkPaths, watcher);
        }

        // 执行 SPRING 后续工作
        super.processProperties(beanFactoryToProcess, props);
    }


    /**
     * 断开后重连 重新加载配置
     */
//    public void reLoadConfigFromZK() {
//        loadConfigFromZK(new Properties(), zkPaths, watcher);
//    }
    private void loadConfigFromZK(Properties props, List<String> paths, CuratorWatcher watcher) {
        for (String path : paths) {
            props.putAll(loadConfigFromZK(path,watcher));
        }
        ConfigurationUtil.buildConfig(props);
    }

    private Properties loadConfigFromZK( String path, CuratorWatcher watcher) {
        Properties props=new Properties();
        try {
            Stat stat = zkClient.checkExists().forPath(path);
            if (null != stat) {
                byte[] data = zkClient.getData().usingWatcher(watcher).forPath(path);
                ByteArrayInputStream in = new ByteArrayInputStream(data, 0, data.length);
                Reader reader=new InputStreamReader(in,"UTF-8");
                props.load(reader);

                logger.info("加载ZK:{},{}配置文件成功", zkClient.getZookeeperClient().getCurrentConnectionString(), path);
//                if (watcher != null) {
//                    zkClient.checkExists().usingWatcher(watcher).inBackground().forPath(path);
//                    logger.info("ZK集群中{}配置文件添加Watcher成功", path);
//                }
            } else {
                throw new ZkException(String.format("ZK集群不存在%s这一路径的节点", path));
            }
        } catch (Exception e) {
            logger.error("加载ZooKeeper集群{}节点配置信息失败！", path, e);
            throw new ZkException("加载ZooKeeper集群" + path + "节点配置信息失败！", e);
        }
        return props;
    }


    // 注册需要监听的监听者对像.
    private void registerListeners(final Properties props) {
        if (listeners == null) {
            synchronized (CuratorFrameworkFactoryBean.class) {
                if (listeners == null) {
                    listeners = new ArrayList<>();
                    listeners.add(new ConnectionStateListener() {
                        @Override
                        public void stateChanged(CuratorFramework client, ConnectionState newState) {
                            if (newState == ConnectionState.RECONNECTED) {
                                ZKPropertyPlaceholderConfigurer.this.loadConfigFromZK(props, zkPaths, watcher);
                            }
                            logger.info("com.kmzyc.zkconfig.DefaultZkStartListener executor success.newState={}",
                                    newState);
                        }
                    });
                }
            }
        }
        for (ConnectionStateListener listener : listeners) {
            zkClient.getConnectionStateListenable().addListener(listener);
        }


        zkClient.getUnhandledErrorListenable().addListener(new UnhandledErrorListener() {
            @Override
            public void unhandledError(String message, Throwable e) {
                logger.info("CuratorFramework unhandledError: {}", message);
            }
        });

    }


    public void setZkPaths(List<String> zkPaths) {
        this.zkPaths = zkPaths;
    }


    public void setZkClient(CuratorFramework zkClient) {
        this.zkClient = zkClient;
    }

    public List<String> getZkPaths() {
        return zkPaths;
    }

    public CuratorFramework getZkClient() {
        return zkClient;
    }

    public void setWatcher(CuratorWatcher watcher) {
        this.watcher = watcher;
    }

}
