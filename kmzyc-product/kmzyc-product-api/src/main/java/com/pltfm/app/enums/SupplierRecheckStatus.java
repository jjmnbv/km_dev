package com.pltfm.app.enums;

/**
 * 功能：供应商重审状态枚举
 *
 * @author Zhoujiwei
 * @since 2016/8/30 10:25
 */
public enum SupplierRecheckStatus {

    TO_RECHECK(0, "待申请重审"),
    RECHECKING(1, "重审中"),
    PASS(2, "重审通过"),
    NOT_PASS(3, "重审不通过");

    private Integer status;
    private String title;

    SupplierRecheckStatus(Integer status, String title) {
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
        strBuilder.append("SupplierRecheckStatus[status=").append(this.status).append(",title=").append(this.title).append("]");
        return strBuilder.toString();
    }

}