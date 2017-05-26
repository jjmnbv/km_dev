package com.kmzyc.zkconfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

public class CuratorFrameworkFactoryBean implements FactoryBean<CuratorFramework>

{

    private final static Logger logger = LoggerFactory
            .getLogger(CuratorFrameworkFactoryBean.class);

    private CuratorFramework zkClient;




    private CuratorFrameworkFactoryBean() {
        super();
    }



    public CuratorFramework getObject() {
        if (zkClient == null || zkClient.getState() == CuratorFrameworkState.STOPPED) {
            synchronized (CuratorFrameworkFactoryBean.class) {
                if (zkClient == null || zkClient.getState() == CuratorFrameworkState.STOPPED) {
                    zkClient = initZkClient();
                }
            }
        }
        return zkClient;
    }

    public Class<?> getObjectType() {
        return CuratorFramework.class;
    }

    public boolean isSingleton() {
        return true;
    }

    private CuratorFramework initZkClient() {
        // 1000 是重试间隔时间基数，3 是重试次数
        String zkConnectionString = getSystemPropertiesValue("zk.host");
        String sleepTime = getSystemPropertiesValue("zk.sleepTime", "1000");
        int baseSleepTimeMs = Integer.parseInt(sleepTime);

        String retryTimes = getSystemPropertiesValue("zk.retryTimes", "3");
        int maxRetries = Integer.parseInt(retryTimes);

        String connTimeout = getSystemPropertiesValue("zk.connect.timeout", "10000");
        int connectionTimeoutMs = Integer.parseInt(connTimeout);

        String sessionTimeout = getSystemPropertiesValue("zk.session.timeout", "10000");
        int sessionTimeoutMs = Integer.parseInt(sessionTimeout);
        return initZkClient(zkConnectionString, baseSleepTimeMs, maxRetries, connectionTimeoutMs, sessionTimeoutMs);
    }

    private CuratorFramework initZkClient(String connectString, int sleepTime, int reties, int connectTimeout, int sessionTimeout) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(sleepTime, reties);
        CuratorFramework zkClient =
                createWithOptions(connectString, retryPolicy, connectTimeout, sessionTimeout);
        zkClient.start();
        return zkClient;
    }

    private String getSystemPropertiesValue(String key) {
        // 尝试系统变量中找
        String value = System.getProperty(key);
        if (StringUtils.isBlank(value)) {
            logger.error("无法初始化zk客户端，系统变量未配置{}！", key);
            throw new RuntimeException("无法初始化zk客户端，未配置" + key + "值！");
        }
        ConfigurationUtil.put(key, value);
        return value;
    }

    private String getSystemPropertiesValue(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        ConfigurationUtil.put(key, value);
        return value;
    }

    public void destroy() throws Exception {
        zkClient.close();
    }


    /**
     * 通过自定义参数创建
     */
    public CuratorFramework createWithOptions(String connectionString,
                                              RetryPolicy retryPolicy, int connectionTimeoutMs, int sessionTimeoutMs) {
        return CuratorFrameworkFactory.builder().connectString(connectionString)
                .retryPolicy(retryPolicy).connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs).build();
    }




}
