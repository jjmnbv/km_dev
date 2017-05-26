package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 余额取现信息
 * 
 * @author lijianjun
 * @since 15-4-22
 * 
 */
public class BnesAcctEnchashment implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 6268308325737218548L;

    // 取现信息ID *
    private BigDecimal enchashmentId;

    // 登陆ID *
    private BigDecimal nLoginId;

    // 登陆账号*
    private String loginAccount;

    // 交易ID*
    private String accountTransactionId;

    // 账户ID*
    private BigDecimal accountId;

    // 提现人说明*
    private String enchashmentDepict;

    // 取现审核状态;0----待审核;1---提现完成;2---审核拒绝;3--审核通过 *
    private Short enchashmentStatus;

    // 取现审核状态说明
    private String enchashmentStatusRemarks;

    // 申请时间*
    private Date createDate;

    // 申请时间begin*
    private Date createDateBegin;

    // 申请时间end*
    private Date createDateEnd;

    // 审核时间*
    private Date modifyDate;

    // 提现完成时间*
    private Date finishDate;

    // 提现完成时间begin*
    private Date finishDateBegin;

    // 提现完成时间end*
    private Date finishDateEnd;

    // 提现金额*
    private BigDecimal enchashmentAmount;

    // 申请来源*
    private String enchashmentFrom;

    // 审核备注*
    private String remarks;

    // 提现方式*
    private BigDecimal enchashmentType;

    // 提现来源（0：供应商；1：微商）*
    private BigDecimal enchashmentResource;
    /*
     * // 公司名 private String corporateName;
     * 
     * // 公司对公账户（卡号） private String corporateAccount;
     * 
     * // 银行账号 private String bankAccount; // 开户行 private String bankName; // 银行账户名 private String
     * bankUname;
     */
    // 微商号
    private BigDecimal vsNumber;
    // 微信账号
    private String vxAccount;
    // 公司名称
    private String corporateName;
    // 可用余额
    private BigDecimal amountAavlibal;
    // 审核人ID*
    private String auditId;
    // 审核人姓名（账号）*
    private String auditName;
    // 收款机构分支行*
    private String bankBranchName;
    // 提现操作人ID*
    private String enchashmentOperId;
    // 提现操作人姓名（账号）*
    private String enchashmentOperName;
    // 提现确认备注信息*
    private String confirmRemarks;
    // 提现完成时间*
    private Date finashDate;

    /**** 20151211wangkai 提现记录新加字段不再关联查询 ****/
    private String bankAccountName;// 银行账户名
    private String bankCardNumber;// 银行卡号
    private String bankOfDeposit; // 开户行
    /**** 20151211wangkai ****/



    // 分页参数
    private Integer minNum;
    private Integer maxNum;



    public BigDecimal getVsNumber() {
        return vsNumber;
    }

    public void setVsNumber(BigDecimal vsNumber) {
        this.vsNumber = vsNumber;
    }

    public String getVxAccount() {
        return vxAccount;
    }

    public void setVxAccount(String vxAccount) {
        this.vxAccount = vxAccount;
    }



    public BigDecimal getEnchashmentResource() {
        return enchashmentResource;
    }

    public void setEnchashmentResource(BigDecimal enchashmentResource) {
        this.enchashmentResource = enchashmentResource;
    }



    public String getBankOfDeposit() {
        return bankOfDeposit;
    }

    public void setBankOfDeposit(String bankOfDeposit) {
        this.bankOfDeposit = bankOfDeposit;
    }


    public BigDecimal getEnchashmentType() {
        return enchashmentType;
    }

    public void setEnchashmentType(BigDecimal enchashmentType) {
        this.enchashmentType = enchashmentType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public Date getCreateDateBegin() {
        return createDateBegin;
    }

    public void setCreateDateBegin(Date createDateBegin) {
        this.createDateBegin = createDateBegin;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public BigDecimal getEnchashmentId() {
        return enchashmentId;
    }

    public void setEnchashmentId(BigDecimal enchashmentId) {
        this.enchashmentId = enchashmentId;
    }

    public BigDecimal getnLoginId() {
        return nLoginId;
    }

    public void setnLoginId(BigDecimal nLoginId) {
        this.nLoginId = nLoginId;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getAccountTransactionId() {
        return accountTransactionId;
    }

    public void setAccountTransactionId(String accountTransactionId) {
        this.accountTransactionId = accountTransactionId;
    }

    public BigDecimal getAccountId() {
        return accountId;
    }

    public void setAccountId(BigDecimal accountId) {
        this.accountId = accountId;
    }

    public String getEnchashmentDepict() {
        return enchashmentDepict;
    }

    public void setEnchashmentDepict(String enchashmentDepict) {
        this.enchashmentDepict = enchashmentDepict;
    }

    public Short getEnchashmentStatus() {
        return enchashmentStatus;
    }

    public void setEnchashmentStatus(Short enchashmentStatus) {
        this.enchashmentStatus = enchashmentStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public BigDecimal getEnchashmentAmount() {
        return enchashmentAmount;
    }

    public void setEnchashmentAmount(BigDecimal enchashmentAmount) {
        this.enchashmentAmount = enchashmentAmount;
    }

    public String getEnchashmentFrom() {
        return enchashmentFrom;
    }

    public void setEnchashmentFrom(String enchashmentFrom) {
        this.enchashmentFrom = enchashmentFrom;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getEnchashmentOperId() {
        return enchashmentOperId;
    }

    public void setEnchashmentOperId(String enchashmentOperId) {
        this.enchashmentOperId = enchashmentOperId;
    }

    public String getEnchashmentOperName() {
        return enchashmentOperName;
    }

    public void setEnchashmentOperName(String enchashmentOperName) {
        this.enchashmentOperName = enchashmentOperName;
    }

    public String getConfirmRemarks() {
        return confirmRemarks;
    }

    public void setConfirmRemarks(String confirmRemarks) {
        this.confirmRemarks = confirmRemarks;
    }

    public Date getFinashDate() {
        return finashDate;
    }

    public void setFinashDate(Date finashDate) {
        this.finashDate = finashDate;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public BigDecimal getAmountAavlibal() {
        return amountAavlibal;
    }

    public void setAmountAavlibal(BigDecimal amountAavlibal) {
        this.amountAavlibal = amountAavlibal;
    }

    public String getEnchashmentRemarks() {
        return enchashmentStatusRemarks;
    }

    public void setEnchashmentRemarks(String enchashmentStatusRemarks) {
        this.enchashmentStatusRemarks = enchashmentStatusRemarks;
    }

    @Override
    public String toString() {
        return "BnesAcctEnchashment [enchashmentId=" + enchashmentId + ", nLoginId=" + nLoginId
                + ", loginAccount=" + loginAccount + ", accountTransactionId="
                + accountTransactionId + ", accountId=" + accountId + ", enchashmentDepict="
                + enchashmentDepict + ", enchashmentStatus=" + enchashmentStatus
                + ", enchashmentStatusRemarks=" + enchashmentStatusRemarks + ", createDate="
                + createDate + ", createDateBegin=" + createDateBegin + ", createDateEnd="
                + createDateEnd + ", modifyDate=" + modifyDate + ", enchashmentAmount="
                + enchashmentAmount + ", enchashmentFrom=" + enchashmentFrom + ", remarks="
                + remarks + ", enchashmentType=" + enchashmentType + ", enchashmentResource="
                + enchashmentResource + ", vsNumber=" + vsNumber + ", vxAccount=" + vxAccount
                + ", corporateName=" + corporateName + ", amountAavlibal=" + amountAavlibal
                + ", auditId=" + auditId + ", auditName=" + auditName + ", bankBranchName="
                + bankBranchName + ", enchashmentOperId=" + enchashmentOperId
                + ", enchashmentOperName=" + enchashmentOperName + ", confirmRemarks="
                + confirmRemarks + ", finashDate=" + finashDate + ", bankAccountName="
                + bankAccountName + ", bankCardNumber=" + bankCardNumber + ", bankOfDeposit="
                + bankOfDeposit + ", minNum=" + minNum + ", maxNum=" + maxNum + "]";
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getFinishDateBegin() {
        return finishDateBegin;
    }

    public void setFinishDateBegin(Date finishDateBegin) {
        this.finishDateBegin = finishDateBegin;
    }

    public Date getFinishDateEnd() {
        return finishDateEnd;
    }

    public void setFinishDateEnd(Date finishDateEnd) {
        this.finishDateEnd = finishDateEnd;
    }



}
