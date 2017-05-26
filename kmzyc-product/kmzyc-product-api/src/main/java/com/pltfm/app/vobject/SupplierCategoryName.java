package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/9/6 16:35
 */
public class SupplierCategoryName implements Serializable {

    private static final long serialVersionUID = 2222196181115704091L;

    private Long supplierId;

    private String categoryName;

    private Integer levelType;

    private List<String> categoryNameList;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getLevelType() {
        return levelType;
    }

    public void setLevelType(Integer levelType) {
        this.levelType = levelType;
    }

    public List<String> getCategoryNameList() {
        return categoryNameList;
    }

    public void setCategoryNameList(List<String> categoryNameList) {
        this.categoryNameList = categoryNameList;
    }

    @Override
    public String toString() {
        return "SupplierCategoryName{" +
                "supplierId=" + supplierId +
                ", categoryName='" + categoryName + '\'' +
                ", levelType=" + levelType +
                ", categoryNameList=" + categoryNameList +
                '}';
    }
}