package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/9/6 16:36
 */
public class SupplierWarehouseName implements Serializable {

    private static final long serialVersionUID = -5144707020787729191L;

    private Long supplierId;

    private String warehouseName;

    private List<String> warehouseNameList;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public List<String> getWarehouseNameList() {
        return warehouseNameList;
    }

    public void setWarehouseNameList(List<String> warehouseNameList) {
        this.warehouseNameList = warehouseNameList;
    }

    @Override
    public String toString() {
        return "SupplierWarehouseName{" +
                "supplierId=" + supplierId +
                ", warehouseName='" + warehouseName + '\'' +
                ", warehouseNameList=" + warehouseNameList +
                '}';
    }
}