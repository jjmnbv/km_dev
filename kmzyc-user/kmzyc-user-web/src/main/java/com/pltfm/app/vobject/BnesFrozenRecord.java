package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户冻结ID实体
 * 
 */
public class BnesFrozenRecord implements Serializable {
    /**
     * 账户冻结ID
     * 
     */
    private Integer frozenRecordId;

    /**
     * 账户ID
     * 
     */
    private Integer accountId;

    /**
     * 登录ID
     * 
     */
    private Integer loginId;
    private Integer customerType;

    /**
     * 冻结类别，登录账号和账户冻结两类
     * 
     */
    private Integer frozenType;

    /**
     * 登录账号
     * 
     */
    private String username;

    /**
     * 真实姓名
     * 
     */
    private String realName;

    /**
     * 账户
     * 
     */
    private String account;

    /**
     * 投诉人账号
     * 
     */
    private String sueName;

    /**
     * 投诉人姓名
     * 
     */
    private String sueRealName;

    /**
     * 投诉日期
     * 
     */
    private Date sueDate;
    private Date sueDateStart;
    private Date sueDateEnd;

    /**
     * 投诉原因
     * 
     */
    private String sueReason;

    /**
     * 冻结原因
     * 
     */
    private String frozenReason;
    /**
     * 解冻原因
     * 
     */
    private String thawReason;

    /**
     * 冻结截图
     * 
     */
    private String frozenPicture;

    /**
     * 冻结金额
     * 
     */
    private BigDecimal frozenNumber;

    /**
     * 操作人
     * 
     */

    private Integer operator;
    /**
     * 操作人姓名
     * 
     */
    private String sysUserName;

    /**
     * 操作日期
     * 
     */
    private Date operatorDate;
    private Date operatorDateStart;
    private Date operatorDateEnd;
    /**
     * 操作日期格式化成（to_char）
     * 
     */
    private String charOperatorDate;

    /**
     * 创建日期
     * 
     */
    private Date createDate;

    /**
     * 创建人
     * 
     */
    private Integer created;

    /**
     * 修改日期
     * 
     */
    private Date modifyDate;

    /**
     * 修改人
     * 
     */
    private Integer modified;
    /**
     * 账号状态
     */
    private Integer n_Status;
    /** 账户金额冻结状态 **/
    private Integer status;
    
    /**冻结时间开始*/
    private Date createDateStart;
    
    /**冻结时间结束*/
    private Date createDateEnd;
    

    public Date getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public String getSysUserName() {
        return sysUserName;
    }

    public void setSysUserName(String sysUserName) {
        this.sysUserName = sysUserName;
    }

    public Integer getN_Status() {
        return n_Status;
    }

    public void setN_Status(Integer nStatus) {
        n_Status = nStatus;
    }

    public Integer getFrozenRecordId() {
        return frozenRecordId;
    }

    public String getThawReason() {
        return thawReason;
    }

    public void setThawReason(String thawReason) {
        this.thawReason = thawReason;
    }

    public void setFrozenRecordId(Integer frozenRecordId) {
        this.frozenRecordId = frozenRecordId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public Integer getFrozenType() {
        return frozenType;
    }

    public void setFrozenType(Integer frozenType) {
        this.frozenType = frozenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSueName() {
        return sueName;
    }

    public void setSueName(String sueName) {
        this.sueName = sueName;
    }

    public String getSueRealName() {
        return sueRealName;
    }

    public void setSueRealName(String sueRealName) {
        this.sueRealName = sueRealName;
    }

    public Date getSueDate() {
        return sueDate;
    }

    public void setSueDate(Date sueDate) {
        this.sueDate = sueDate;
    }

    public String getSueReason() {
        return sueReason;
    }

    public void setSueReason(String sueReason) {
        this.sueReason = sueReason;
    }

    public String getFrozenReason() {
        return frozenReason;
    }

    public void setFrozenReason(String frozenReason) {
        this.frozenReason = frozenReason;
    }

    public String getFrozenPicture() {
        return frozenPicture;
    }

    public void setFrozenPicture(String frozenPicture) {
        this.frozenPicture = frozenPicture;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Date getOperatorDate() {
        return operatorDate;
    }

    public void setOperatorDate(Date operatorDate) {
        this.operatorDate = operatorDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getModified() {
        return modified;
    }

    public void setModified(Integer modified) {
        this.modified = modified;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    /**
     * 最小值
     */
    private Integer skip;
    /**
     * 最大值
     */
    private Integer max;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }


    public String getCharOperatorDate() {
        return charOperatorDate;
    }

    public void setCharOperatorDate(String charOperatorDate) {
        this.charOperatorDate = charOperatorDate;
    }

    public Date getOperatorDateStart() {
        return operatorDateStart;
    }

    public void setOperatorDateStart(Date operatorDateStart) {
        this.operatorDateStart = operatorDateStart;
    }

    public Date getOperatorDateEnd() {
        return operatorDateEnd;
    }

    public void setOperatorDateEnd(Date operatorDateEnd) {
        this.operatorDateEnd = operatorDateEnd;
    }

    public Date getSueDateStart() {
        return sueDateStart;
    }

    public void setSueDateStart(Date sueDateStart) {
        this.sueDateStart = sueDateStart;
    }

    public Date getSueDateEnd() {
        return sueDateEnd;
    }

    public void setSueDateEnd(Date sueDateEnd) {
        this.sueDateEnd = sueDateEnd;
    }

    public BigDecimal getFrozenNumber() {
        return frozenNumber;
    }

    public void setFrozenNumber(BigDecimal frozenNumber) {
        this.frozenNumber = frozenNumber;
    }
    
}
