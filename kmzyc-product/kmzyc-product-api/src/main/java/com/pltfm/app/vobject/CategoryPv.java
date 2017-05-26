package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/10/27 15:03
 */
public class CategoryPv implements Serializable{

    private static final long serialVersionUID = -282713953831700162L;

    private Long id;

    private Long categoryId;

    private Long parentId;

    private String categoryName;

    private Integer createUser;

    private Date createTime;

    private BigDecimal defaultRebate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getDefaultRebate() {
        return defaultRebate;
    }

    public void setDefaultRebate(BigDecimal defaultRebate) {
        this.defaultRebate = defaultRebate;
    }

    @Override
    public String toString() {
        return "CategoryPv{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", parentId=" + parentId +
                ", categoryName='" + categoryName + '\'' +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", defaultRebate=" + defaultRebate +
                '}';
    }
}