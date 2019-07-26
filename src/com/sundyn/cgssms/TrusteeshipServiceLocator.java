/**
 * TrusteeshipServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sundyn.cgssms;

import com.sundyn.cgssms.TrusteeshipSoapBindingStub;
import com.sundyn.cgssms.Trusteeship_PortType;

public class TrusteeshipServiceLocator extends org.apache.axis.client.Service implements com.sundyn.cgssms.TrusteeshipService {

    public TrusteeshipServiceLocator() {
    }


    public TrusteeshipServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TrusteeshipServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Trusteeship
    private java.lang.String Trusteeship_address = "http://10.41.168.20:9080/NWTrusteeship/services/Trusteeship";

    public java.lang.String getTrusteeshipAddress() {
        return Trusteeship_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TrusteeshipWSDDServiceName = "Trusteeship";

    public java.lang.String getTrusteeshipWSDDServiceName() {
        return TrusteeshipWSDDServiceName;
    }

    public void setTrusteeshipWSDDServiceName(java.lang.String name) {
        TrusteeshipWSDDServiceName = name;
    }

    public Trusteeship_PortType getTrusteeship() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Trusteeship_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTrusteeship(endpoint);
    }

    public Trusteeship_PortType getTrusteeship(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            TrusteeshipSoapBindingStub _stub = new TrusteeshipSoapBindingStub(portAddress, this);
            _stub.setPortName(getTrusteeshipWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTrusteeshipEndpointAddress(java.lang.String address) {
        Trusteeship_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (Trusteeship_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                TrusteeshipSoapBindingStub _stub = new TrusteeshipSoapBindingStub(new java.net.URL(Trusteeship_address), this);
                _stub.setPortName(getTrusteeshipWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Trusteeship".equals(inputPortName)) {
            return getTrusteeship();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.41.168.20:9080/NWTrusteeship/services/Trusteeship", "TrusteeshipService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://10.41.168.20:9080/NWTrusteeship/services/Trusteeship", "Trusteeship"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Trusteeship".equals(portName)) {
            setTrusteeshipEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
