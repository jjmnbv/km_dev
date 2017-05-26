package com.pltfm.app.enums;

/**
 * 功能：资质文件类型枚举
 *
 * @author Zhoujiwei
 * @since 2016/8/30 15:12
 */
public enum CertificateType {

    THREE(1, "三证合一"),
    BUSINESS_LICENCE(2, "营业执照电子版"),
    ORGANIZATION(3, "组织机构代码证电子版"),
    TAX_REG_CERTIFICATE(4, "税务登记证电子版"),
    ID_CARD(5, "法人身份证电子版"),
    FOOD_BUSINESS_LICENSE(6, "食品经营许可证电子版"),
    FOOD_SAFETY_MANAGEMENT_LICENSE(7, "食品安全管理人员相关证件电子版"),
    FOOD_CIRCULATION_LICENSE(8, "食品流通许可证电子版"),
    HYGIENE_LICENSE(9, "卫生许可证"),
    FOOD_PRODUCTION_LICENSE(10, "食品生产许可证或GMP资质认证");

    private Integer status;
    private String title;

    CertificateType(Integer status, String title) {
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
        strBuilder.append("CertificateType[status=").append(this.status).append(",title=").append(this.title).append("]");
        return strBuilder.toString();
    }
}