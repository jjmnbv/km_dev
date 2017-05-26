package com.pltfm.app.util;

/**
 * 远程接口调用方类
 * 
 * @author cjm
 * @since 2013-8-9
 */
public class RemoteInvoking {
    /**
     * 远程接口调用方类型
     * 
     * @param type
     * @return
     */
    public static String remoteInvokingType(Integer type) {
        String typeName;
        switch (type) {
            case 1:
                typeName = "产品 ";
                break;
            case 2:
                typeName = "订单 ";
                break;
            case 3:
                typeName = "B2B";
                break;
            default:
                typeName = null;
        }

        return typeName;
    }
}
