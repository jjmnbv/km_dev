package com.pltfm.app.enums;

/**
 * 功能：品牌草稿状态
 *
 * @author Zhoujiwei
 * @since 2015/11/24 11:01
 */
public enum ProdBrandDraftStatus {
    DELETE("0","删除"),
    UNAUDIT("1","审核中"),
    AUDITUNPASS("2","审核未通过"),
    AUDIT("3","审核通过");

    private String status;

    private String title = null;


    ProdBrandDraftStatus(String status, String title) {
        this.status = status;
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("ProductStatus[status=").append(this.status)
                .append(",title=").append(this.title).append("]");
        return strBuilder.toString();
    }
}
