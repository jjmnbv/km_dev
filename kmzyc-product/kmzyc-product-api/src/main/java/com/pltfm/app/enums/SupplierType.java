package com.pltfm.app.enums;

/**
 * 功能：企业类型枚举
 *
 * @author Zhoujiwei
 * @since 2016/8/30 15:22
 */
public enum SupplierType {

    BUSINESS(1, "食品经营者"),
    PRODUCTION(2, "食品生产者"),
    AGRICULTURAL(3, "食品农产品");

    private Integer status;
    private String title;

    SupplierType(Integer status, String title) {
        this.status = status;
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    private void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("SupplierType[status=").append(this.status).append(",title=").append(this.title).append("]");
        return strBuilder.toString();
    }
}
