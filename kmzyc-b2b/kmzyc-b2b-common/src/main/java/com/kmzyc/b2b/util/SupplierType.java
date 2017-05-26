package com.kmzyc.b2b.util;

/**
 * 供应商类型 1：自营 2：入驻 3：代销
 *
 * @author xlg
 */
public enum SupplierType {
    SELLER_TYPE_SELF_SUPPORT(1, "自营"), SELLER_TYPE_SALE_PROXY(3, "代销"), SELLER_TYPE_ENTER_SALE(2,
            "入驻"), SELLER_TYPE_ENTER_SALE_FOR_TIMES(4, "时代入驻");
    private int index;
    private String title;

    SupplierType(int index, String title) {
        this.index = index;
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
