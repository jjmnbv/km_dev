package com.kmzyc.supplier.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kmzyc.supplier.enums.SupplierEntryStatus;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/3/22 16:48
 */
public class SupplierEntryStatusMap {
    private static Map<Integer,String> map = null;

    private static Map<Integer, SupplierEntryStatus> statusMap = null;

    public static Map<Integer,String> getMap(){
        if(map == null) {
            Map<Integer, String> maps = new LinkedHashMap<Integer, String>();
            maps.put(SupplierEntryStatus.REVOKE.getStatus(), SupplierEntryStatus.REVOKE.getTitle());
            maps.put(SupplierEntryStatus.NOT_PAY.getStatus(), SupplierEntryStatus.NOT_PAY.getTitle());
            maps.put(SupplierEntryStatus.NOT_AUDIT.getStatus(), SupplierEntryStatus.NOT_AUDIT.getTitle());
            maps.put(SupplierEntryStatus.NOT_PASS.getStatus(), SupplierEntryStatus.NOT_PASS.getTitle());
            maps.put(SupplierEntryStatus.PASS.getStatus(), SupplierEntryStatus.PASS.getTitle());
            maps.put(SupplierEntryStatus.ENTRY_FAIL.getStatus(), SupplierEntryStatus.ENTRY_FAIL.getTitle());
            map = maps;
        }
        return map;
    }

    public static Map<Integer, SupplierEntryStatus> getStatusMap(){
        if(statusMap == null) {
            Map<Integer,SupplierEntryStatus> maps = new LinkedHashMap<Integer,SupplierEntryStatus>();
            maps.put(SupplierEntryStatus.NOT_ENTRY.getStatus(), SupplierEntryStatus.NOT_ENTRY);
            maps.put(SupplierEntryStatus.PASS.getStatus(), SupplierEntryStatus.PASS);
            maps.put(SupplierEntryStatus.REVOKE.getStatus(), SupplierEntryStatus.REVOKE);
            maps.put(SupplierEntryStatus.NOT_PAY.getStatus(), SupplierEntryStatus.NOT_PAY);
            maps.put(SupplierEntryStatus.NOT_AUDIT.getStatus(), SupplierEntryStatus.NOT_AUDIT);
            maps.put(SupplierEntryStatus.NOT_PASS.getStatus(), SupplierEntryStatus.NOT_PASS);
            maps.put(SupplierEntryStatus.ENTRY_FAIL.getStatus(), SupplierEntryStatus.ENTRY_FAIL);
            statusMap = maps;
        }

        return statusMap;
    }


    /**
     * 根据类型查看当前用户供应商报名活动的状态
     *
     * @param key
     * @return
     */
    public static SupplierEntryStatus getSupplierEntryStatus(Integer key){
        if(statusMap == null) {
            getStatusMap();
        }
        return statusMap.get(key);
    }
}
