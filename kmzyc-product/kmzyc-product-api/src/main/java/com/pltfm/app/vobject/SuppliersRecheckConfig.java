package com.pltfm.app.vobject;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/30 15:06
 */
public class SuppliersRecheckConfig implements Serializable {

    private static final long serialVersionUID = 8081489502342867263L;

    private Long id;

    private Timestamp endTime;

    /**
     * 状态：0：无效，1：有效
     */
    private Integer status;

    private Long createUser;

    private Timestamp createTime;

    @Override
    public String toString() {
        return "SuppliersRecheckConfig{" +
                "id=" + id +
                ", endTime=" + endTime +
                ", status=" + status +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}