package com.pltfm.app.enums;


/**
 * 产品投放渠道
 * 
 * @author xkj
 *
 */
public enum ProductChannel {
    B2B("B2B", "B2B渠道");
    // B2B_WAP("B2B_WAP","B2B_WAP渠道")
    // B2B2B("B2B2B","B2B&B2B双渠道"),
    // ZYC("ZYC","中药材网");

    private String status;
    private String title = null;

    ProductChannel(String status, String title) {
        this.status = status;
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("ProductChannle[status=").append(this.status).append(",title=")
                .append(this.title).append("]");
        return strBuilder.toString();
    }
}
