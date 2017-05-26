package com.pltfm.app.enums;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/10 16:07
 */
public enum ProductOperateType {
    UP(1, "上架"),
    DOWN(2, "下架"),
    ILLEGAL_DOWN(3, "违规下架"),
    SYSTEM_DOWN(4, "系统下架"),
    RELEASE_PRODUCT_PRICE(5, "发布价格");

    private Integer status;
    private String title = null;

    ProductOperateType(int status, String title) {
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
        strBuilder.append("ProductOperateType[status=").append(this.status).append(",title=").append(this.title).append("]");
        return strBuilder.toString();
    }
}
