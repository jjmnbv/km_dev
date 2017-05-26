package com.pltfm.app.enums;

/**
 * 功能：项目来源
 *
 * @author Zhoujiwei
 * @since 2016/4/23 11:51
 */
public enum RegisterPlatform {

    /*
     * 删除 JG(1, "机构项目"),
     * 
     * YD(2, "康美云店"),
     */

    KMB2B(3, "康美中药城"),

    /* 删除 ZBHY(4, "总部会员系统"), */

    KJ(5, "砍价项目"),

    /*
     * 删除 FL(6, "返利网"),
     * 
     * CPS(7, "常规CPS"),
     */

    ZRZC(8, "自然注册"),

    /* 删除 KMYF(9, "康美云服务"), */

    SQDL(99, "授权登录");

    private int type;

    private String title;

    RegisterPlatform(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public int getType() {
        return type;
    }


    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "registerDevice{" + "type=" + type + ", title='" + title + '\'' + '}';
    }
}
