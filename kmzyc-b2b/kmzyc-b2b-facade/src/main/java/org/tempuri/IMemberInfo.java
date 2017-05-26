/**
 * IMemberInfo.java
 *
 * This file was auto-generated from WSDL by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT)
 * WSDL2Java emitter.
 */

package org.tempuri;

import java.rmi.RemoteException;

public interface IMemberInfo extends java.rmi.Remote {

    public java.lang.String memberLoginValid(java.lang.String number,java.lang.String password) throws RemoteException;

    public java.lang.String memberGetInfo(java.lang.String number) throws RemoteException;

    public java.lang.String memberOrderCreate(java.lang.String orderID, java.lang.String ordertype,
                                              java.lang.String number, java.lang.Double totalMoney,
                                              java.lang.Double totalPv,
                                              java.lang.Double orderTotalMoney,
                                              java.lang.Double carryMoney,
                                              java.lang.String consignee, java.lang.String ccpcCode,
                                              java.lang.String conAddress,
                                              java.lang.String conZipCode,
                                              java.lang.String conTelphone,
                                              java.lang.String detailList,
                                              java.lang.String paymentDate,
                                              java.lang.String completeDate) throws RemoteException;


    public String orderCancel(String orderID, String cancelReason, String ordertype,
                              int TLOrderAmount, String TLOrderDatetime) throws RemoteException;
}
