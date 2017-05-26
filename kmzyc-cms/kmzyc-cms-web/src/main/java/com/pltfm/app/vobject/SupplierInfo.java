package com.pltfm.app.vobject;

import java.io.Serializable;

public class SupplierInfo implements Serializable {
    Integer supplierId;
    Integer merchantId;
    String saleProductDescribe;
    Integer settlementCycle;
    Integer settlementWay;
    Integer supplierType;
    Integer status;
    String describe;

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getSaleProductDescribe() {
        return saleProductDescribe;
    }

    public void setSaleProductDescribe(String saleProductDescribe) {
        this.saleProductDescribe = saleProductDescribe;
    }

    public Integer getSettlementCycle() {
        return settlementCycle;
    }

    public void setSettlementCycle(Integer settlementCycle) {
        this.settlementCycle = settlementCycle;
    }

    public Integer getSettlementWay() {
        return settlementWay;
    }

    public void setSettlementWay(Integer settlementWay) {
        this.settlementWay = settlementWay;
    }

    public Integer getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(Integer supplierType) {
        this.supplierType = supplierType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

}
