package org.tempuri;

public class IMemberInfoProxy implements org.tempuri.IMemberInfo {
    private String _endpoint = null;
    private org.tempuri.IMemberInfo iMemberInfo = null;

    public IMemberInfoProxy() {
        _initIMemberInfoProxy();
    }

    public IMemberInfoProxy(String endpoint) {
        _endpoint = endpoint;
        _initIMemberInfoProxy();
    }

    private void _initIMemberInfoProxy() {
        try {
            iMemberInfo = (new org.tempuri.MemberInfoLocator()).getBasicHttpBinding_IMemberInfo();
            if (iMemberInfo != null) {
                if (_endpoint != null) {
                    ((javax.xml.rpc.Stub) iMemberInfo)
                            ._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
                } else {
                    _endpoint = (String) ((javax.xml.rpc.Stub) iMemberInfo)
                            ._getProperty("javax.xml.rpc.service.endpoint.address");
                }
            }

        } catch (javax.xml.rpc.ServiceException serviceException) {
        }
    }

    public String getEndpoint() {
        return _endpoint;
    }

    public void setEndpoint(String endpoint) {
        _endpoint = endpoint;
        if (iMemberInfo != null) {
            ((javax.xml.rpc.Stub) iMemberInfo)
                    ._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        }

    }

    public org.tempuri.IMemberInfo getIMemberInfo() {
        if (iMemberInfo == null) _initIMemberInfoProxy();
        return iMemberInfo;
    }

    public java.lang.String memberLoginValid(java.lang.String number,
                                             java.lang.String password) throws
            java.rmi.RemoteException {
        if (iMemberInfo == null) _initIMemberInfoProxy();
        return iMemberInfo.memberLoginValid(number, password);
    }

    public java.lang.String memberGetInfo(java.lang.String number) throws java.rmi.RemoteException {
        if (iMemberInfo == null) _initIMemberInfoProxy();
        return iMemberInfo.memberGetInfo(number);
    }

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
                                              java.lang.String completeDate) throws
            java.rmi.RemoteException {
        if (iMemberInfo == null) _initIMemberInfoProxy();
        return iMemberInfo
                .memberOrderCreate(orderID, ordertype, number, totalMoney, totalPv, orderTotalMoney,
                        carryMoney, consignee, ccpcCode, conAddress, conZipCode, conTelphone,
                        detailList, paymentDate, completeDate);
    }

    @Override
    public String orderCancel(String orderID, String cancelReason, String ordertype,
                              int TLOrderAmount, String TLOrderDatetime) throws
            java.rmi.RemoteException {
        if (iMemberInfo == null) _initIMemberInfoProxy();
        return iMemberInfo
                .orderCancel(orderID, cancelReason, ordertype, TLOrderAmount, TLOrderDatetime);
    }

}
