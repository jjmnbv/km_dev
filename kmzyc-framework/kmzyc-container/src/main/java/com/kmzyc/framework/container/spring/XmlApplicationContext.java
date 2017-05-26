package com.kmzyc.framework.container.spring;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderSupport;

import com.kmzyc.framework.container.deployment.Deployments;

public class XmlApplicationContext extends AbstractXmlApplicationContext {

    private final String[] configLocations;
    private final Properties env;
    private final String appRoot;

    /**
     * Create a new ClassPathXmlApplicationContext, loading the definitions
     * from the given XML files and automatically refreshing the context.
     *
     * @param configLocations array of resource locations
     * @throws BeansException if context creation failed
     */
    XmlApplicationContext(String[] configLocations, Properties env) throws BeansException {
        this(configLocations, (ApplicationContext) null, env);
    }

    /**
     * Create a new ClassPathXmlApplicationContext with the given parent,
     * loading the definitions from the given XML files and automatically
     * refreshing the context.
     *
     * @param configLocations array of resource locations
     * @param parent          the parent context
     * @throws BeansException if context creation failed
     */
    XmlApplicationContext(String[] configLocations, ApplicationContext parent, Properties env) throws BeansException {
        super(parent);
        this.appRoot = env.getProperty(Deployments.KEY_APP_ROOT);
        this.configLocations = parseConfigLocations(configLocations);
        this.env = env;
        refresh();
    }


    @Override
    protected String[] getConfigLocations() {
        return this.configLocations;
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (null != env) {
            String names[] = beanFactory.getBeanNamesForType(PropertiesLoaderSupport.class, true, false);
            if (null != names && names.length > 0) {
                for (String name : names) {
                    PropertiesLoaderSupport ps = (PropertiesLoaderSupport) beanFactory.getBean(name);
                    if (null != ps) ps.setProperties(env);
                }
            } else {
                PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
                ppc.setProperties(env);
                addBeanFactoryPostProcessor(ppc);
            }
        }
        super.postProcessBeanFactory(beanFactory);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        if (null == appRoot) return super.getResourceByPath(path);
        if (path != null && path.startsWith("/")) {
            path = path.substring(1);
        }
        return new FileSystemResource(path);
    }

    protected String[] parseConfigLocations(String[] paths) {
        if (null == appRoot || null == paths || paths.length == 0) return paths;
        String ss[] = new String[paths.length];
        for (int i = 0; i < ss.length; i++) {
            ss[i] = appRoot + paths[i];
        }
        return ss;
    }

}
