package com.kmzyc.promotion.app.vobject;

import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;

public class MyMerchantInfoOrSuppliers extends MerchantInfoOrSuppliers {
    private static final long serialVersionUID = -6897296304931483459L;
    private Integer skip;
    private Integer max;

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

}
