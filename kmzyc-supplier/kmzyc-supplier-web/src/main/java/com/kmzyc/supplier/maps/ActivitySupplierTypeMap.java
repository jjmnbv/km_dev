package com.kmzyc.supplier.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kmzyc.supplier.enums.ActivitySupplierStatus;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/3/18 10:27
 */
public class ActivitySupplierTypeMap {

    private static Map<String,String> map = null;

    private static Map<String, ActivitySupplierStatus> statusMap = null;

    public static Map<String,String> getMap(){
        if(map == null) {
            Map<String,String> maps = new LinkedHashMap<String,String>();
            maps.put(ActivitySupplierStatus.ALL.getValue(), ActivitySupplierStatus.ALL.getName());
            maps.put(ActivitySupplierStatus.INVITED.getValue(), ActivitySupplierStatus.INVITED.getName());
            maps.put(ActivitySupplierStatus.MY.getValue(), ActivitySupplierStatus.MY.getName());
            map = maps;
        }
        return map;
    }

    public static Map<String,ActivitySupplierStatus> getStatusMap(){
        if(statusMap == null) {
            Map<String,ActivitySupplierStatus> maps = new LinkedHashMap<String,ActivitySupplierStatus>();
            maps.put(ActivitySupplierStatus.ALL.getValue(), ActivitySupplierStatus.ALL);
            maps.put(ActivitySupplierStatus.INVITED.getValue(), ActivitySupplierStatus.INVITED);
            maps.put(ActivitySupplierStatus.MY.getValue(), ActivitySupplierStatus.MY);
            statusMap = maps;
        }
        return statusMap;
    }


    /**
     * 根据类型查看当前用户查看哪个活动产品
     *
     * @param key
     * @return
     */
    public static ActivitySupplierStatus getActivitySupplierType(String key){
        if(statusMap == null) {
            getStatusMap();
        }
        return statusMap.get(key);
    }
}
