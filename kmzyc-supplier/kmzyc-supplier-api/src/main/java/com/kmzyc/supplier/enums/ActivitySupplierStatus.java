package com.kmzyc.supplier.enums;

/**
 * 功能：供应商对应活动状态
 *
 * @author Zhoujiwei
 * @since 2016/3/18 10:13
 */
public enum ActivitySupplierStatus {

    ALL("平台活动","1"),
    INVITED("邀请我参加的活动","2"),
    MY("我已参加的活动","3");

    private String name;

    private String value;

    public String getValue() {
        return value;
    }

    private void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    ActivitySupplierStatus(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ActivitySupplierStatus{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
