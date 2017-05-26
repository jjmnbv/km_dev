package com.pltfm.app.enums;

/**
 * 
 * @ClassName: EnterpriseType
 * @Description: TODO(企业状态)
 * @author pengbo
 * @date 2016年8月30日 下午1:59:45
 */
public enum EnterpriseType {
    OPEN("0", "关闭"), CLOSE("1", "开启");
    private String status;
    private String title = null;

    EnterpriseType(String status, String title) {
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
        strBuilder.append("EnterpriseType[status=").append(this.status).append(",title=")
                .append(this.title).append("]");
        return strBuilder.toString();
    }

}
