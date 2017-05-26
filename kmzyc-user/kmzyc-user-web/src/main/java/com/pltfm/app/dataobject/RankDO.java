package com.pltfm.app.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 数据查询类
 * 
 * @author
 * @since 2013-07-25
 */
public class RankDO implements Serializable {
    /** 客户个性信息主键 **/
    private Integer personalityId;
    /** 客户类别主键 **/
    private Integer customerTypeId;
    /** 积分 **/
    private BigDecimal expend;
    /** 经验 **/
    private Integer integralnumber;

    public Integer getIntegralnumber() {
        return integralnumber;
    }

    public void setIntegralnumber(Integer integralnumber) {
        this.integralnumber = integralnumber;
    }

    public Integer getPersonalityId() {
        return personalityId;
    }

    public void setPersonalityId(Integer personalityId) {
        this.personalityId = personalityId;
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
