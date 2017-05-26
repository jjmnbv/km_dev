package com.kmzyc.mframework.constants;


/**
 * 邮件订阅类型枚举
 * 
 * @author luoyi
 * @createDate 2013/10/17
 * 
 */
public enum SystemEnums {
    SYSTEMB2B(1, "b2b");

    private int key;
    private String value = null;



    SystemEnums(int key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 根据索引查找key键
     * 
     * @param index
     * @return
     */
    public static String getKeyByIndex(int index) {
        for (SystemEnums system : SystemEnums.values()) {
            if (system.getKey() == index) {
                return system.getValue();
            }
        }
        return "failed";
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
