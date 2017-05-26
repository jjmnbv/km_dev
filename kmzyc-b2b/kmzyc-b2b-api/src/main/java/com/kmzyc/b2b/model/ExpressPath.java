package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.util.List;

public class ExpressPath implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private String nu;
    private String ischeck;
    private String com;
    private String updatetime;
    private String status;
    private String condition;
    private String state;
    List<ExpressPathItem> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ExpressPathItem> getData() {
        return data;
    }

    public void setData(List<ExpressPathItem> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ExpressPath [message=" + message + ", nu=" + nu + ", ischeck=" + ischeck + ", com="
                + com + ", updatetime=" + updatetime + ", status=" + status + ", condition="
                + condition + ", state=" + state + ", data=" + data + "]";
    }

}
