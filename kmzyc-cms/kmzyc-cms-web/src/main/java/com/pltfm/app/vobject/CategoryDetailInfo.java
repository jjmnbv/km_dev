package com.pltfm.app.vobject;

import java.io.Serializable;

/**
 * 产品物理类目层级信息
 *
 * @author zhl
 */
public class CategoryDetailInfo implements Serializable {
    private static final long serialVersionUID = 198757794712018315L;
    private Integer oneCode;
    private Integer twoCode;
    private Integer threeCode;
    private String oneCategoryName;
    private String twoCategoryName;
    private String threeCategoryName;

    public Integer getOneCode() {
        return oneCode;
    }

    public void setOneCode(Integer oneCode) {
        this.oneCode = oneCode;
    }

    public Integer getTwoCode() {
        return twoCode;
    }

    public void setTwoCode(Integer twoCode) {
        this.twoCode = twoCode;
    }

    public Integer getThreeCode() {
        return threeCode;
    }

    public void setThreeCode(Integer threeCode) {
        this.threeCode = threeCode;
    }

    public String getOneCategoryName() {
        return oneCategoryName;
    }

    public void setOneCategoryName(String oneCategoryName) {
        this.oneCategoryName = oneCategoryName;
    }

    public String getTwoCategoryName() {
        return twoCategoryName;
    }

    public void setTwoCategoryName(String twoCategoryName) {
        this.twoCategoryName = twoCategoryName;
    }

    public String getThreeCategoryName() {
        return threeCategoryName;
    }

    public void setThreeCategoryName(String threeCategoryName) {
        this.threeCategoryName = threeCategoryName;
    }

}
