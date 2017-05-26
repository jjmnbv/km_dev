package com.pltfm.app.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 数据查询类
 * 
 * @author zhl
 * @since 2013-07-25
 */
public class UserLevelDO implements Serializable {
    /** 客户信息主键 **/
    private Integer loginId;
    /** 客户类别主键 **/
    private Integer customerTypeId;
    /** 消费金额 **/
    private BigDecimal expend;
    // 年消费金额
    private Double yearMin;

    public Double getYearMin() {
        return yearMin;
    }

    public void setYearMin(Double yearMin) {
        this.yearMin = yearMin;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public Integer getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(Integer customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public BigDecimal getExpend() {
        return expend;
    }

    public void setExpend(BigDecimal expend) {
        this.expend = expend;
    }

}
