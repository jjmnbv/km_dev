package com.kmzyc.supplier.model;

import java.io.Serializable;

/**
 * 供应商仓库关系实体类
 *
 * @author luoyi
 * @createDate 2013/12/25
 */

public class SuppliersWarehouse implements Serializable {

    private static final long serialVersionUID = 3074386219990009748L;
    /**
     * 供应商仓库关系ID
     */
    private Long supWarehouseId;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 以下为set和get方法
     *
     * @return
     */
    public Long getSupWarehouseId() {
        return supWarehouseId;
    }

    public void setSupWarehouseId(Long supWarehouseId) {
        this.supWarehouseId = supWarehouseId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Override
    public String toString() {
        return "SuppliersWarehouse{" +
                "supWarehouseId=" + supWarehouseId +
                ", supplierId=" + supplierId +
                ", warehouseId=" + warehouseId +
                '}';
    }
}