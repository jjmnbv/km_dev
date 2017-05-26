package com.pltfm.app.vobject;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2013-07-17
 */
public class BnesAcctTransactionQuery implements Serializable {

    private static final long serialVersionUID = 137404448360675922L;

    /**
     * 交易id column BNES_ACCT_TRANSACTION.ACCOUNT_TRANSACTION_ID
     */
    private Integer accountTransactionId;

    /**
     * 账户id column BNES_ACCT_TRANSACTION.ACCOUNT_ID
     */
    private Integer accountId;

    /**
     * 交易流水号 column BNES_ACCT_TRANSACTION.ACCOUNT_NUMBER
     */
    private String accountNumber;

    /**
     * 交易类型 1--充值;2--账户金额修改;3--订单交易 column BNES_ACCT_TRANSACTION.TYPE
     */
    private Integer type;

    /**
     * 交易内容 column BNES_ACCT_TRANSACTION.CONTENT
     */
    private String content;

    /**
     * 交易金额 column BNES_ACCT_TRANSACTION.AMOUNT
     */
    private BigDecimal amount;

    /**
     * 
     * 交易状态 0--充值退回失败;1--充值成功;2--充值处理中;3--充值全部退回; column BNES_ACCT_TRANSACTION.STATUS
     */
    private Integer status;

    /**
     * 交易创建日期 column BNES_ACCT_TRANSACTION.CREATE_DATE
     */
    private Date createDate;

    /**
     * 交易创建人 column BNES_ACCT_TRANSACTION.CREATED_ID
     */
    private Integer createdId;

    /**
     * 交易修改日期 column BNES_ACCT_TRANSACTION.MODIFY_DATE
     */
    private Date modifyDate;

    /**
     * 交易修改人 column BNES_ACCT_TRANSACTION.MODIFIE_ID
     */
    private Integer modifieId;

    /**
     * 账户号
     */
    private String accountLogin;

    /**
     * 账户金额
     */
    private BigDecimal AccountAmount;

    /**
     * 账户冻结金额
     */
    private BigDecimal amountFrozen;

    /**
     * 账户可用金额
     */

    private BigDecimal amountAvlibal;

    private Date endDate;

    // 外部单号
    private String otherOrder;
    // 交易对象
    private Integer trasObject;
    /**
     * 最小值
     */
    private Integer mixNum;
    /**
     * 最大值
     */
    private Integer maxNum;

    // 交易类型list
    private String arrayList;

    // 登陆Id
    private Integer loginId;

    // 手机号
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public String getArrayList() {
        return arrayList;
    }

    public void setArrayList(String arrayList) {
        this.arrayList = arrayList;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }


    public Integer getMixNum() {
        return mixNum;
    }

    public void setMixNum(Integer mixNum) {
        this.mixNum = mixNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public BnesAcctTransactionQuery() {
        super();
    }

    public BnesAcctTransactionQuery(Integer accountTransactionId, Integer accountId,
            String accountNumber, Integer type, String content, BigDecimal amount, Integer status,
            Date createDate, Integer createdId, Date modifyDate, Integer modifieId,
            String otherOrder, Integer trasObject) {
        this.accountTransactionId = accountTransactionId;
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.content = content;
        this.amount = amount;
        this.status = status;
        this.createDate = createDate;
        this.createdId = createdId;
        this.modifyDate = modifyDate;
        this.modifieId = modifieId;
        this.otherOrder = otherOrder;
        this.trasObject = trasObject;
    }

    /**
     * getter for Column BNES_ACCT_TRANSACTION.ACCOUNT_TRANSACTION_ID
     */
    public Integer getAccountTransactionId() {
        return accountTransactionId;
    }

    /**
     * setter for Column BNES_ACCT_TRANSACTION.ACCOUNT_TRANSACTION_ID
     * 
     * @param accountTransactionId
     */
    public void setAccountTransactionId(Integer accountTransactionId) {
        this.accountTransactionId = accountTransactionId;
    }

    /**
     * getter for Column BNES_ACCT_TRANSACTION.ACCOUNT_ID
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * setter for Column BNES_ACCT_TRANSACTION.ACCOUNT_ID
     * 
     * @param accountId
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * getter for Column BNES_ACCT_TRANSACTION.ACCOUNT_NUMBER
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * setter for Column BNES_ACCT_TRANSACTION.ACCOUNT_NUMBER
     * 
     * @param accountNumber
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * getter for Column BNES_ACCT_TRANSACTION.TYPE
     */
    public Integer getType() {
        return type;
    }

    /**
     * setter for Column BNES_ACCT_TRANSACTION.TYPE
     * 
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * getter for Column BNES_ACCT_TRANSACTION.CONTENT
     */
    public String getContent() {
        return content;
    }

    /**
     * setter for Column BNES_ACCT_TRANSACTION.CONTENT
     * 
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    

    /**
     * getter for Column BNES_ACCT_TRANSACTION.STATUS
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * setter for Column BNES_ACCT_TRANSACTION.STATUS
     * 
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * getter for Column BNES_ACCT_TRANSACTION.CREATE_DATE
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * setter for Column BNES_ACCT_TRANSACTION.CREATE_DATE
     * 
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * getter for Column BNES_ACCT_TRANSACTION.CREATED_ID
     */
    public Integer getCreatedId() {
        return createdId;
    }

    /**
     * setter for Column BNES_ACCT_TRANSACTION.CREATED_ID
     * 
     * @param createdId
     */
    public void setCreatedId(Integer createdId) {
        this.createdId = createdId;
    }

    /**
     * getter for Column BNES_ACCT_TRANSACTION.MODIFY_DATE
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * setter for Column BNES_ACCT_TRANSACTION.MODIFY_DATE
     * 
     * @param modifyDate
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * getter for Column BNES_ACCT_TRANSACTION.MODIFIE_ID
     */
    public Integer getModifieId() {
        return modifieId;
    }

    /**
     * setter for Column BNES_ACCT_TRANSACTION.MODIFIE_ID
     * 
     * @param modifieId
     */
    public void setModifieId(Integer modifieId) {
        this.modifieId = modifieId;
    }

    public String getOtherOrder() {
        return otherOrder;
    }

    public void setOtherOrder(String otherOrder) {
        this.otherOrder = otherOrder;
    }

    public Integer getTrasObject() {
        return trasObject;
    }

    public void setTrasObject(Integer trasObject) {
        this.trasObject = trasObject;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getAccountAmount() {
        return AccountAmount;
    }

    public void setAccountAmount(BigDecimal accountAmount) {
        AccountAmount = accountAmount;
    }

    public BigDecimal getAmountFrozen() {
        return amountFrozen;
    }

    public void setAmountFrozen(BigDecimal amountFrozen) {
        this.amountFrozen = amountFrozen;
    }

    public BigDecimal getAmountAvlibal() {
        return amountAvlibal;
    }

    public void setAmountAvlibal(BigDecimal amountAvlibal) {
        this.amountAvlibal = amountAvlibal;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
}
