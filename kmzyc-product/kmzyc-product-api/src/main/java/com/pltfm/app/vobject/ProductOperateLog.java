package com.pltfm.app.vobject;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/10 15:57
 */
public class ProductOperateLog implements Serializable {

    private static final long serialVersionUID = -4742346081089720306L;

    /**
     * 操作id
     */
    private Long operateId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 操作人Id
     */
    private Long operateUser;

    /**
     * 操作人
     */
    private String operateUserName;

    /**
     * 操作时间
     */
    private Timestamp operateTime;

    /**
     * 操作类型 1：上架 2：下架 3：违规下架 4：系统下架
     */
    private Integer operateType;

    /**
     * 备注
     */
    private String remark;

    @Override
    public String toString() {
        return "ProductOperateLog{" +
                "operateId=" + operateId +
                ", productId=" + productId +
                ", operateUser=" + operateUser +
                ", operateUserName='" + operateUserName + '\'' +
                ", operateTime=" + operateTime +
                ", operateType=" + operateType +
                ", remark='" + remark + '\'' +
                '}';
    }

    public Long getOperateId() {
        return operateId;
    }

    public void setOperateId(Long operateId) {
        this.operateId = operateId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(Long operateUser) {
        this.operateUser = operateUser;
    }

    public Timestamp getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Timestamp operateTime) {
        this.operateTime = operateTime;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
    }

    public ProductOperateLog() {
    }

    public ProductOperateLog(Long productId, Long operateUser, Integer operateType) {
        this.productId = productId;
        this.operateUser = operateUser;
        this.operateType = operateType;
    }
}
