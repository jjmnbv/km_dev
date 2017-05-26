/**
 * BasicHttpBinding_IMemberInfoStub.java
 * 
 * This file was auto-generated from WSDL by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT)
 * WSDL2Java emitter.
 */

package org.tempuri;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.namespace.QName;
import javax.xml.rpc.Service;

import org.apache.axis.AxisEngine;
import org.apache.axis.AxisFault;
import org.apache.axis.NoEndPointException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Stub;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.b2b.util.pay.util.RSA;
import com.kmzyc.util.StringUtil;

public class BasicHttpBinding_IMemberInfoStub extends Stub implements IMemberInfo {

    private static Logger logger = LoggerFactory.getLogger(BasicHttpBinding_IMemberInfoStub.class);

    static OperationDesc[] _operations;

    static {
        _operations = new OperationDesc[7];
        _initOperationDesc1();
    }

    private static final String XMLSCHEMA = "";
    private static final String TEMPURI = "";
    private static final String SOAPACTIONURI =  "";

    private static void _initOperationDesc1() {

        List<Object[]> parameterDescsList = new ArrayList<>();

        // 登录验证
        parameterDescsList.add(new Object[] {"number", "string", String.class});
        parameterDescsList.add(new Object[] {"password", "string", String.class});
        parameterDescsList.add(new Object[] {"sign", "string", String.class});
        _operations[0] = createOperationDesc("方法名", "string", String.class,
                "方法名", parameterDescsList);

        // 获取用户信息
        parameterDescsList = new ArrayList<>();
        parameterDescsList.add(new Object[] {"number", "string", String.class});
        parameterDescsList.add(new Object[] {"sign", "string", String.class});
        _operations[1] = createOperationDesc("方法名", "string", String.class,
                "方法名", parameterDescsList);


        // 创建订单
        parameterDescsList = new ArrayList<>();
        parameterDescsList.add(new Object[] {"orderID", "string", String.class});
        parameterDescsList.add(new Object[] {"ordertype", "string", String.class});
        parameterDescsList.add(new Object[] {"number", "string", String.class});
        parameterDescsList.add(new Object[] {"totalMoney", "string", String.class});
        parameterDescsList.add(new Object[] {"totalPv", "string", String.class});
        parameterDescsList.add(new Object[] {"orderTotalMoney", "string", String.class});
        parameterDescsList.add(new Object[] {"carryMoney", "string", String.class});
        parameterDescsList.add(new Object[] {"consignee", "string", String.class});
        parameterDescsList.add(new Object[] {"ccpcCode", "string", String.class});
        parameterDescsList.add(new Object[] {"conAddress", "string", String.class});
        parameterDescsList.add(new Object[] {"conZipCode", "string", String.class});
        parameterDescsList.add(new Object[] {"conTelphone", "string", String.class});
        parameterDescsList.add(new Object[] {"detailList", "string", String.class});
        parameterDescsList.add(new Object[] {"paymentDate", "string", String.class});
        parameterDescsList.add(new Object[] {"completeDate", "string", String.class});
        parameterDescsList.add(new Object[] {"sign", "string", String.class});
        _operations[2] = createOperationDesc("方法名", "string", String.class,
                "方法名", parameterDescsList);

        // OrderCancel
        parameterDescsList = new ArrayList<>();
        parameterDescsList.add(new Object[] {"orderID", "string", String.class});
        parameterDescsList.add(new Object[] {"cancelReason", "string", String.class});
        parameterDescsList.add(new Object[] {"ordertype", "string", String.class});
        parameterDescsList.add(new Object[] {"TLOrderAmount", "string", String.class});
        parameterDescsList.add(new Object[] {"TLOrderDatetime", "string", String.class});
        parameterDescsList.add(new Object[] {"sign", "string", String.class});
        _operations[3] = createOperationDesc("方法名", "string", String.class, "方法名",
                parameterDescsList);

    }

    public BasicHttpBinding_IMemberInfoStub() throws AxisFault {
        this(null);
    }

    public BasicHttpBinding_IMemberInfoStub(URL endpointURL, Service service) throws AxisFault {
        this(service);
        super.cachedEndpoint = endpointURL;
    }

    public BasicHttpBinding_IMemberInfoStub(Service service) throws AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service) super.service).setTypeMappingVersion("1.2");
    }

    protected Call createCall() throws RemoteException {
        try {
            Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            return _call;
        } catch (Throwable _t) {
            throw new AxisFault("Failure trying to get the Call object", _t);
        }
    }

    @Override
    public String memberLoginValid(String number, String password) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }

        Map<String, String> map = new TreeMap<>(
                new Comparator<String>() {
                    @Override
                    public int compare(String obj1, String obj2) {

                        return obj1.toLowerCase().compareToIgnoreCase(obj2.toLowerCase());
                    }
                });
        map.put("number",number);
        map.put("password",password);

        return this.invoke(_operations[0], "方法名",new String[] {number, password,sign(map)});
    }

    @Override
    public String memberGetInfo(String number) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }

        Map<String, String> map = new TreeMap<>(
                new Comparator<String>() {
                    @Override
                    public int compare(String obj1, String obj2) {

                        return obj1.toLowerCase().compareToIgnoreCase(obj2.toLowerCase());
                    }
                });

        map.put("number",number);

        return this.invoke(_operations[1], "方法名",new String[] {number,sign(map)});

    }

    @Override
    public String memberOrderCreate(String orderID, String ordertype, String number,
            java.lang.Double totalMoney, java.lang.Double totalPv, java.lang.Double orderTotalMoney,
            java.lang.Double carryMoney, String consignee, String ccpcCode, String conAddress,
            String conZipCode, String conTelphone, String detailList, String paymentDate,
            String completeDate) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }

        Map<String, String> map = new TreeMap<>(
            new Comparator<String>() {
                @Override
                public int compare(String obj1, String obj2) {

                    return obj1.toLowerCase().compareToIgnoreCase(obj2.toLowerCase());
                }
            });

        map.put("orderID",orderID);
        map.put("number",number);
        map.put("ordertype",ordertype);
        map.put("totalMoney",String.valueOf(totalMoney));
        map.put("totalPv",String.valueOf(totalPv));
        map.put("orderTotalMoney",String.valueOf(orderTotalMoney));
        map.put("carryMoney",String.valueOf(carryMoney));
        map.put("consignee",consignee);
        map.put("ccpcCode",ccpcCode);
        map.put("conAddress",conAddress);
        map.put("conZipCode",conZipCode);
        map.put("conTelphone",conTelphone);
        map.put("detailList",detailList);
        map.put("paymentDate",paymentDate);
        map.put("completeDate",completeDate);

        return this.invoke(_operations[2], "方法名",
                new String[] {orderID, ordertype, number, String.valueOf(totalMoney), String.valueOf(totalPv), String.valueOf(orderTotalMoney),
                        String.valueOf(carryMoney), consignee, ccpcCode, conAddress, conZipCode, conTelphone,
                        detailList, paymentDate, completeDate,sign(map)});
    }

    @Override
    public String orderCancel(String orderID, String cancelReason, String ordertype,
            int TLOrderAmount, String TLOrderDatetime) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }

        Map<String, String> map = new TreeMap<>(
        new Comparator<String>() {
            @Override
            public int compare(String obj1, String obj2) {

                return obj1.toLowerCase().compareToIgnoreCase(obj2.toLowerCase());
            }
        });

        map.put("orderID",orderID);
        map.put("cancelReason",cancelReason);
        map.put("ordertype",ordertype);
        map.put("TLOrderAmount",String.valueOf(TLOrderAmount));
        map.put("TLOrderDatetime",TLOrderDatetime);

        return this.invoke(_operations[3],"方法名",
                new String[] {orderID, cancelReason, ordertype, String.valueOf(TLOrderAmount), TLOrderDatetime,sign(map)});
    }

    /**
     * 调用 接口
     * 
     * @param opertion* @param perationName
     * @param objects
     * @return
     * @throws RemoteException
     */
    private String invoke(OperationDesc opertion, String perationName,
            String... objects) throws RemoteException {
        try {

            Call _call = createCall();
            _call.setOperation(opertion);
            _call.setUseSOAPAction(true);
            _call.setSOAPActionURI(SOAPACTIONURI+perationName);
            _call.setEncodingStyle(null);
            _call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
            _call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
            _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
            _call.setOperationName(new QName(TEMPURI, perationName));
            setRequestHeaders(_call);
            setAttachments(_call);

            Object _resp = _call.invoke(objects);

            if (_resp instanceof RemoteException) {
                throw (RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (String) _resp;
                } catch (Exception _exception) {
                    return (String) JavaUtils.convert(_resp, String.class);
                }
            }
        } catch (AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    private static String sign(Map<String,String> map){
        StringBuffer sb = new StringBuffer();

        if(null != map && map.size() > 0) {

            Set<String> keySet = map.keySet();
            Iterator<String> iter = keySet.iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                if(!StringUtil.isEmpty(map.get(key))) {
                    sb.append(key).append("=").append(map.get(key)).append("&");
                }
            }
            if(sb.lastIndexOf("&")> 0){
                sb.delete(sb.length()-1,sb.length());
            }
            logger.info("签名字符串："+sb.toString());
            String str = RSA.sign(sb.toString(),"","utf-8");
            logger.info("字符串:"+sb.toString()+" RSA结果"+str);
            return str;

        }
        return sb.toString();
    }

    private static ParameterDesc createParameterDesc(String key, String type, Class typeClass) {
        ParameterDesc param = new ParameterDesc(new QName(TEMPURI, key), ParameterDesc.IN,
                new QName(XMLSCHEMA, type), typeClass, false, false);
        param.setOmittable(true);
        if ("string".equalsIgnoreCase(type)) {
            param.setNillable(true);
        }
        return param;
    }

    private static OperationDesc createOperationDesc(String name, String returnType,
                                                     Class returnClass, String returnQName, List<Object[]> parameterDescsList) {
        OperationDesc oper = new OperationDesc();
        oper.setName(name);
        oper.setReturnType(new QName(XMLSCHEMA, returnType));
        oper.setReturnClass(returnClass);
        oper.setReturnQName(new QName(TEMPURI, returnQName));
        oper.setStyle(Style.WRAPPED);
        oper.setUse(Use.LITERAL);

        if (null != parameterDescsList && parameterDescsList.size() > 0) {
            for (int i = 0; i < parameterDescsList.size(); i++) {
                oper.addParameter(createParameterDesc((String) parameterDescsList.get(i)[0],
                        (String) parameterDescsList.get(i)[1],
                        (Class) parameterDescsList.get(i)[2]));
            }
        }
        return oper;
    }

}
