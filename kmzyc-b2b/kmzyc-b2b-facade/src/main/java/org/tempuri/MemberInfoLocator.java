/**
 * MemberInfoLocator.java
 * 
 * This file was auto-generated from WSDL by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT)
 * WSDL2Java emitter.
 */

package org.tempuri;


public class MemberInfoLocator extends org.apache.axis.client.Service
    implements
      org.tempuri.MemberInfo {

  private String linkUrl = "";

  public MemberInfoLocator() {}

  public MemberInfoLocator(org.apache.axis.EngineConfiguration config) {
    super(config);
  }

  public MemberInfoLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName)
      throws javax.xml.rpc.ServiceException {
    super(wsdlLoc, sName);
  }

  // Use to get a proxy class for BasicHttpBinding_IMemberInfo8077-测试 ， 8088
  // ：正式
  private java.lang.String BasicHttpBinding_IMemberInfo_address = linkUrl;

  public java.lang.String getBasicHttpBinding_IMemberInfoAddress() {
    return BasicHttpBinding_IMemberInfo_address;
  }

  // The WSDD service name defaults to the port name.
  private java.lang.String BasicHttpBinding_IMemberInfoWSDDServiceName =
      "BasicHttpBinding_IMemberInfo";

  public java.lang.String getBasicHttpBinding_IMemberInfoWSDDServiceName() {
    return BasicHttpBinding_IMemberInfoWSDDServiceName;
  }

  public void setBasicHttpBinding_IMemberInfoWSDDServiceName(java.lang.String name) {
    BasicHttpBinding_IMemberInfoWSDDServiceName = name;
  }

  public org.tempuri.IMemberInfo getBasicHttpBinding_IMemberInfo()
      throws javax.xml.rpc.ServiceException {
    java.net.URL endpoint;
    try {
      endpoint = new java.net.URL(BasicHttpBinding_IMemberInfo_address);
    } catch (java.net.MalformedURLException e) {
      throw new javax.xml.rpc.ServiceException(e);
    }
    return getBasicHttpBinding_IMemberInfo(endpoint);
  }

  public org.tempuri.IMemberInfo getBasicHttpBinding_IMemberInfo(java.net.URL portAddress)
      throws javax.xml.rpc.ServiceException {
    try {
      org.tempuri.BasicHttpBinding_IMemberInfoStub _stub =
          new org.tempuri.BasicHttpBinding_IMemberInfoStub(portAddress, this);
      _stub.setPortName(getBasicHttpBinding_IMemberInfoWSDDServiceName());
      return _stub;
    } catch (org.apache.axis.AxisFault e) {
      return null;
    }
  }

  public void setBasicHttpBinding_IMemberInfoEndpointAddress(java.lang.String address) {
    BasicHttpBinding_IMemberInfo_address = address;
  }

  /**
   * For the given interface, get the stub implementation. If this service has no port for the given
   * interface, then ServiceException is thrown.
   */
  public java.rmi.Remote getPort(Class serviceEndpointInterface)
      throws javax.xml.rpc.ServiceException {
    try {
      if (org.tempuri.IMemberInfo.class.isAssignableFrom(serviceEndpointInterface)) {
        org.tempuri.BasicHttpBinding_IMemberInfoStub _stub =
            new org.tempuri.BasicHttpBinding_IMemberInfoStub(new java.net.URL(
                BasicHttpBinding_IMemberInfo_address), this);
        _stub.setPortName(getBasicHttpBinding_IMemberInfoWSDDServiceName());
        return _stub;
      }
    } catch (java.lang.Throwable t) {
      throw new javax.xml.rpc.ServiceException(t);
    }
    throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  "
        + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
  }

  /**
   * For the given interface, get the stub implementation. If this service has no port for the given
   * interface, then ServiceException is thrown.
   */
  public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface)
      throws javax.xml.rpc.ServiceException {
    if (portName == null) {
      return getPort(serviceEndpointInterface);
    }
    java.lang.String inputPortName = portName.getLocalPart();
    if ("BasicHttpBinding_IMemberInfo".equals(inputPortName)) {
      return getBasicHttpBinding_IMemberInfo();
    } else {
      java.rmi.Remote _stub = getPort(serviceEndpointInterface);
      ((org.apache.axis.client.Stub) _stub).setPortName(portName);
      return _stub;
    }
  }

  public javax.xml.namespace.QName getServiceName() {
    return new javax.xml.namespace.QName("http://tempuri.org/", "MemberInfo");
  }

  private java.util.HashSet ports = null;

  public java.util.Iterator getPorts() {
    if (ports == null) {
      ports = new java.util.HashSet();
      ports
          .add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IMemberInfo"));
    }
    return ports.iterator();
  }

  /**
   * Set the endpoint address for the specified port name.
   */
  public void setEndpointAddress(java.lang.String portName, java.lang.String address)
      throws javax.xml.rpc.ServiceException {

    if ("BasicHttpBinding_IMemberInfo".equals(portName)) {
      setBasicHttpBinding_IMemberInfoEndpointAddress(address);
    } else { // Unknown Port Name
      throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port"
          + portName);
    }
  }

  /**
   * Set the endpoint address for the specified port name.
   */
  public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address)
      throws javax.xml.rpc.ServiceException {
    setEndpointAddress(portName.getLocalPart(), address);
  }

  public String getLinkUrl() {
    return linkUrl;
  }

  public void setLinkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
  }

}
