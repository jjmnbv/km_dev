package com.pltfm.app.enums;

/**
 * 功能：供应商资质验证状态枚举
 *
 * @author Zhoujiwei
 * @since 2016/8/30 15:34
 */
public enum CertificateStatus {

    TO_CHECK(0, "未验证"),
    PASS(1, "验证通过"),
    NOT_PASS(2, "验证不通过");

    private Integer status;
    private String title;

    CertificateStatus(Integer status, String title) {
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
        strBuilder.append("CertificateStatus[status=").append(this.status).append(",title=").append(this.title).append("]");
        return strBuilder.toString();
    }
}