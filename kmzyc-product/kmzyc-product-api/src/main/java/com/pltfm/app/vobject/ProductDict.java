package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能：产品字典类
 *
 * @author Zhoujiwei
 * @since 2016/10/31 10:21
 */
public class ProductDict implements Serializable {

    private static final long serialVersionUID = -3987901663802262542L;

    private Long id;

    private Integer dictType;

    private String remark;

    private BigDecimal numberValue;

    private String StringValue;

    private Date dateValue;

    private Integer createUser;

    private Date createTime;

    private Integer modifyUser;

    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDictType() {
        return dictType;
    }

    public void setDictType(Integer dictType) {
        this.dictType = dictType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(BigDecimal numberValue) {
        this.numberValue = numberValue;
    }

    public String getStringValue() {
        return StringValue;
    }

    public void setStringValue(String stringValue) {
        StringValue = stringValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
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

    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "ProductDict{" +
                "id=" + id +
                ", dictType=" + dictType +
                ", remark='" + remark + '\'' +
                ", numberValue=" + numberValue +
                ", StringValue='" + StringValue + '\'' +
                ", dateValue=" + dateValue +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", modifyUser=" + modifyUser +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
