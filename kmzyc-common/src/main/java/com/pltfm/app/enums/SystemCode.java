package com.pltfm.app.enums;


/**
 * 外部对接系统编码
 * 
 * @author xkj
 * 
 */
public enum SystemCode {
    JIEKE("JIEKE", "捷科系统"), KMB2B("KMB2B", "康美中药城"), JXC("JXC", "C端进销存系统"), FANLI("FANLI", "返利网系统");

    private String code;
    private String title = null;

    SystemCode(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("SystemCode[code=").append(this.code).append(",title=").append(this.title)
                .append("]");
        return strBuilder.toString();
    }
}
