package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户等级信息类
 * 
 * @author zhl
 * @since 2013-07-07
 */
public class UserLevel implements Serializable {
    /** 客户等级主键 **/
    private Integer n_level_id;
    /** 客户等级编号 **/
    private String code;
    /** 客户等级名称 **/
    private String level_name;
    /** 客户类型 **/
    private Integer n_customer_type_id;
    /** 客户类别子类别id(只传值) **/
    private Integer customer_son_id;
    /** 客户类型名称 **/
    private String customerName;
    /** 消费额最小值 **/
    private BigDecimal expend_min;
    /** 消费额最大值 **/
    private BigDecimal expend_max;
    /** 积分最小值 **/
    private Long score_min;
    /** 积分最大值 **/
    private Long score_max;
    /** 有效期 **/
    private Integer valid;
    /** 创建人 **/
    private Integer n_created;
    /** 创建时间 **/
    private Date d_create_date;
    /** 修改人 **/
    private Integer n_modified;
    /** 修改时间 **/
    private Date d_modify_date;
    /** 开始索引值 **/
    private Integer startIndex;
    /** 结束索引值 **/
    private Integer endIndex;
    /** 上年消费最小金额 **/
    private Double yearMin;
    /** 用户等级状态 */
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getYearMin() {
        return yearMin;
    }

    public void setYearMin(Double yearMin) {
        this.yearMin = yearMin;
    }

    /** 等级享受服务 **/
    private String remark;



    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getN_level_id() {
        return n_level_id;
    }

    public void setN_level_id(Integer nLevelId) {
        n_level_id = nLevelId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String levelName) {
        level_name = levelName;
    }

    public Integer getN_customer_type_id() {
        return n_customer_type_id;
    }

    public void setN_customer_type_id(Integer nCustomerTypeId) {
        n_customer_type_id = nCustomerTypeId;
    }

    public Integer getCustomer_son_id() {
        return customer_son_id;
    }

    public void setCustomer_son_id(Integer customerSonId) {
        customer_son_id = customerSonId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getN_created() {
        return n_created;
    }

    public void setN_created(Integer nCreated) {
        n_created = nCreated;
    }

    public Date getD_create_date() {
        return d_create_date;
    }

    public void setD_create_date(Date dCreateDate) {
        d_create_date = dCreateDate;
    }

    public Integer getN_modified() {
        return n_modified;
    }

    public void setN_modified(Integer nModified) {
        n_modified = nModified;
    }

    public Date getD_modify_date() {
        return d_modify_date;
    }

    public void setD_modify_date(Date dModifyDate) {
        d_modify_date = dModifyDate;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    public Long getScore_min() {
        return score_min;
    }

    public void setScore_min(Long scoreMin) {
        score_min = scoreMin;
    }

    public Long getScore_max() {
        return score_max;
    }

    public void setScore_max(Long scoreMax) {
        score_max = scoreMax;
    }

    public BigDecimal getExpend_min() {
        return expend_min;
    }

    public void setExpend_min(BigDecimal expend_min) {
        this.expend_min = expend_min;
    }

    public BigDecimal getExpend_max() {
        return expend_max;
    }

    public void setExpend_max(BigDecimal expend_max) {
        this.expend_max = expend_max;
    }
}
