package com.pltfm.app.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2013-07-17
 */
public class BnesAcctTransListDO implements Serializable {

    private static final long serialVersionUID = 137404451947571252L;


    /**
     * column BNES_ACCT_TRANS_LIST.TRANS_LIST_ID
     */
    private Integer transListId;

    /**
     * column BNES_ACCT_TRANS_LIST.ACCOUNT_ID
     */
    private Integer accountId;

    /**
     * column BNES_ACCT_TRANS_LIST.ACCOUNT_TRANSACTION_ID
     */
    private Integer accountTransactionId;

    /**
     * column BNES_ACCT_TRANS_LIST.BEFORE_AMOUNT
     */
    private BigDecimal beforeAmount;

    /**
     * column BNES_ACCT_TRANS_LIST.AFTER_AMOUNT
     */
    private BigDecimal afterAmount;

    /**
     * column BNES_ACCT_TRANS_LIST.MONEY_AMOUNT
     */
    private BigDecimal moneyAmount;

    /**
     * column BNES_ACCT_TRANS_LIST.CREATE_DATE
     */
    private Date createDate;

    /**
     * column BNES_ACCT_TRANS_LIST.CREATED_ID
     */
    private Integer createdId;

    /**
     * column BNES_ACCT_TRANS_LIST.MODIFY_DATE
     */
    private Date modifyDate;

    /**
     * column BNES_ACCT_TRANS_LIST.MODIFIE_ID
     */
    private Integer modifieId;

    public BnesAcctTransListDO() {
        super();
    }

    public BnesAcctTransListDO(Integer transListId, Integer accountId, Integer accountTransactionId,
            BigDecimal beforeAmount, BigDecimal afterAmount, BigDecimal moneyAmount,
            Date createDate, Integer createdId, Date modifyDate, Integer modifieId) {
        this.transListId = transListId;
        this.accountId = accountId;
        this.accountTransactionId = accountTransactionId;
        this.beforeAmount = beforeAmount;
        this.afterAmount = afterAmount;
        this.moneyAmount = moneyAmount;
        this.createDate = createDate;
        this.createdId = createdId;
        this.modifyDate = modifyDate;
        this.modifieId = modifieId;
    }

    /**
     * getter for Column BNES_ACCT_TRANS_LIST.TRANS_LIST_ID
     */
    public Integer getTransListId() {
        return transListId;
    }

    /**
     * setter for Column BNES_ACCT_TRANS_LIST.TRANS_LIST_ID
     * 
     * @param transListId
     */
    public void setTransListId(Integer transListId) {
        this.transListId = transListId;
    }

    /**
     * getter for Column BNES_ACCT_TRANS_LIST.ACCOUNT_ID
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * setter for Column BNES_ACCT_TRANS_LIST.ACCOUNT_ID
     * 
     * @param accountId
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * getter for Column BNES_ACCT_TRANS_LIST.ACCOUNT_TRANSACTION_ID
     */
    public Integer getAccountTransactionId() {
        return accountTransactionId;
    }

    /**
     * setter for Column BNES_ACCT_TRANS_LIST.ACCOUNT_TRANSACTION_ID
     * 
     * @param accountTransactionId
     */
    public void setAccountTransactionId(Integer accountTransactionId) {
        this.accountTransactionId = accountTransactionId;
    }

    /**
     * getter for Column BNES_ACCT_TRANS_LIST.BEFORE_AMOUNT
     */


    /**
     * getter for Column BNES_ACCT_TRANS_LIST.CREATE_DATE
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * setter for Column BNES_ACCT_TRANS_LIST.CREATE_DATE
     * 
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * getter for Column BNES_ACCT_TRANS_LIST.CREATED_ID
     */
    public Integer getCreatedId() {
        return createdId;
    }

    /**
     * setter for Column BNES_ACCT_TRANS_LIST.CREATED_ID
     * 
     * @param createdId
     */
    public void setCreatedId(Integer createdId) {
        this.createdId = createdId;
    }

    /**
     * getter for Column BNES_ACCT_TRANS_LIST.MODIFY_DATE
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * setter for Column BNES_ACCT_TRANS_LIST.MODIFY_DATE
     * 
     * @param modifyDate
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * getter for Column BNES_ACCT_TRANS_LIST.MODIFIE_ID
     */
    public Integer getModifieId() {
        return modifieId;
    }

    /**
     * setter for Column BNES_ACCT_TRANS_LIST.MODIFIE_ID
     * 
     * @param modifieId
     */
    public void setModifieId(Integer modifieId) {
        this.modifieId = modifieId;
    }

    public BigDecimal getBeforeAmount() {
        return beforeAmount;
    }

    public void setBeforeAmount(BigDecimal beforeAmount) {
        this.beforeAmount = beforeAmount;
    }

    public BigDecimal getAfterAmount() {
        return afterAmount;
    }

    public void setAfterAmount(BigDecimal afterAmount) {
        this.afterAmount = afterAmount;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

}
