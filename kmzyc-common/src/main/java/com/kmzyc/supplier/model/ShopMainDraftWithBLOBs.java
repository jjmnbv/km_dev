package com.kmzyc.supplier.model;

public class ShopMainDraftWithBLOBs extends ShopMainDraft {

    private String describe;

    private String describeLazy;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDescribeLazy() {
        return describeLazy;
    }

    public void setDescribeLazy(String describeLazy) {
        this.describeLazy = describeLazy;
    }
}