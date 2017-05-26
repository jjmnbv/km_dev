/**
 * project :CMS系统 module : km-product-web package : com.pltfm.app.enums date: 2016年8月30日下午5:11:32
 * Copyright (c) 2016, KM@kmzyc.com All Rights Reserved.
 */ 
package com.pltfm.app.enums; 

/** 
 * TODO 功能描述<br/> 
 *
 * @author   gongyan 
 * @date   2016年8月30日 下午5:11:32 
 * @version   
 * @see       
 */
public enum CategoryAudit {
    INCORRECT("0","需资质认证审核"),
    CORRECT("1","无需资质认证审核");
    
    
    private String key;
    private String value;
    
    CategoryAudit(String key, String value) {
        this.key = key;
        this.value = value;
    }
    
    
    public String getKey() {
        return key;
    }

    private void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    private void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("CategoryAudit[key=").append(this.key)
        .append(",value=").append(this.value).append("]");
        return strBuilder.toString();
    }
    
}
  