package com.kmzyc.framework.container.ejb;


/**
 * Meta-data about an EJB, discovered from a deployment
 * descriptor.
 *
 * @author Norman Richards
 */
public class EjbDescriptor {
    private String ejbName;
    private String ejbClassName;
    private String[] remoteInterfaces;
    private String[] localInterfaces;

    /**
     * 此值为Stateful/Steteless/Entity/DriverBean之一.
     */
    private Class<?> beanType;


    public Class<?> getBeanType() {
        return beanType;
    }

    public void setBeanType(Class<?> beanType) {
        this.beanType = beanType;
    }

    public String getEjbClassName() {
        return ejbClassName;
    }

    public void setEjbClassName(String ejbClass) {
        this.ejbClassName = ejbClass;
    }

    public String getEjbName() {
        return ejbName;
    }

    public void setEjbName(String name) {
        this.ejbName = name;
    }

    public String[] getRemoteInterfaces() {
        return remoteInterfaces;
    }

    public void setRemoteInterfaces(String[] remoteInterfaces) {
        this.remoteInterfaces = remoteInterfaces;
        if (null != remoteInterfaces) for (int i = 0; i < remoteInterfaces.length; i++) {
            remoteInterfaces[i] = remoteInterfaces[i].trim();
        }
    }

    public String[] getLocalInterfaces() {
        return localInterfaces;
    }

    public void setLocalInterfaces(String[] localInterfaces) {
        this.localInterfaces = localInterfaces;
        if (null != localInterfaces) for (int i = 0; i < localInterfaces.length; i++) {
            localInterfaces[i] = localInterfaces[i].trim();
        }
    }


}