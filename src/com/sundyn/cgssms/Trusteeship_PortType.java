/**
 * Trusteeship_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sundyn.cgssms;

public interface Trusteeship_PortType extends java.rmi.Remote {
    public int sendMessage(java.lang.String mobile, java.lang.String content, long smID, int flag, java.lang.String apiCode) throws java.rmi.RemoteException;
    public int sendMessagebyprior(java.lang.String mobile, java.lang.String content, long smID, int flag, java.lang.String apiCode, java.lang.String prior) throws java.rmi.RemoteException;
    public int sendMessages(java.lang.String[] mobiles, java.lang.String content, long smID, int flag, java.lang.String apiCode) throws java.rmi.RemoteException;
    public int sendMessages(java.lang.String[] mobiles, java.lang.String content, java.lang.String sendTime, long smID, int flag, java.lang.String apiCode) throws java.rmi.RemoteException;
    public int sendMessagesbyprior(java.lang.String[] mobiles, java.lang.String content, long smID, int flag, java.lang.String apiCode, java.lang.String prior) throws java.rmi.RemoteException;
    public MOItem[] receiveMS(int flag, java.lang.String apiCode) throws java.rmi.RemoteException;
    public RPTItem[] receiveRPT(int flag, java.lang.String apiCode) throws java.rmi.RemoteException;
}
