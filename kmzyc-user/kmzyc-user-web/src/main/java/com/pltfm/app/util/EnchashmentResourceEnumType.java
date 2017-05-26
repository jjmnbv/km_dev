package com.pltfm.app.util;

import java.io.Serializable;

/**
 * 余额提现申请来源枚举类
 * 
 * @author lijianjun
 * @since 15-06-12
 */
public enum EnchashmentResourceEnumType implements Serializable {
    ENCHASHMENT_RESOURCE_GYS("0", "供应商")/*, ENCHASHMENT_RESOURCE_VS("1", "云商"), ENCHASHMENT_RESOURCE_JG(
            "2", "机构")*/;

    private String type;
    private String title;

    // 添加构造函数
    private EnchashmentResourceEnumType(String type, String title) {
        this.type = type;
        this.title = title;
    }

    public String getType() {
        return type;
    }


    public String getTitle() {
        return title;
    }


    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("EnchashmentResourceEnumType[type=").append(this.type).append(",title=")
                .append(this.title).append("]");
        return strBuilder.toString();
    }
}
