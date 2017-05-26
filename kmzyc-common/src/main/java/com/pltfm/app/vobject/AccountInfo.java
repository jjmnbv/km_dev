package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户信息类
 * 
 * @author cjm
 * @since 2013-7-10
 */
public class AccountInfo implements Serializable {
    /**
     * 账户主键
     */
    private Integer n_AccountId;

    /**
     * 客户类别主键
     */
    private Integer n_CustomerTypeId;

    /**
     * 客户类别子类别id(只传值)
     **/
    private Integer customer_son_id;

    /**
     * 客户类别名称
     */
    private String customerName;

    /**
     * 登录主键
     */
    private Integer n_LoginId;
    /**
     * 登录账号
     */
    private String loginAccount;

    /**
     * 账户号
     */
    private String accountLogin;

    /**
     * 支付密码
     */
    private String paymentpwd;

    /**
     * 账户真实姓名
     */
    private String name;

    /**
     * 身份证号码
     */
    private String acconutId;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 账户创建日期
     */
    private Date d_CreateDate;

    private Date endDate;

    /**
     * 会员卡号
     * 
     * @return
     */
    private String cardNum;



    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 账户金额
     */
    private BigDecimal n_AccountAmount;

    /**
     * 账户冻结金额
     */
    private BigDecimal amountFrozen;

    /**
     * 账户可用金额
     */
    private BigDecimal amountAvlibal;

    /**
     * 账户状态
     */
    private Integer n_Status;

    /**
     * 修改日期
     */
    private Date d_ModifyDate;

    /**
     * 修改人
     */

    private Integer n_Modified;
    /**
     * 支付范围
     */
    private String pay_Range;
    /**
     * 总消费金额 CONSUMPTION_AMOUNT
     */
    private double consumption_Amount;
    private String corporateName;
    // 注册平台
    private Integer register_Platfrom;
    // 注册设备
    private Integer register_Device;


    // ------------ for page
    /**
     * 最小值
     */
    private Integer skip;
    /**
     * 最大值
     */
    private Integer max;



    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public double getConsumption_Amount() {
        return consumption_Amount;
    }

    public void setConsumption_Amount(double consumption_Amount) {
        this.consumption_Amount = consumption_Amount;
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

    public Integer getN_AccountId() {
        return n_AccountId;
    }

    public void setN_AccountId(Integer nAccountId) {
        n_AccountId = nAccountId;
    }

    public Integer getN_CustomerTypeId() {
        return n_CustomerTypeId;
    }

    public void setN_CustomerTypeId(Integer nCustomerTypeId) {
        n_CustomerTypeId = nCustomerTypeId;
    }

    public Integer getN_LoginId() {
        return n_LoginId;
    }

    public void setN_LoginId(Integer nLoginId) {
        n_LoginId = nLoginId;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    public String getPaymentpwd() {
        return paymentpwd;
    }

    public void setPaymentpwd(String paymentpwd) {
        this.paymentpwd = paymentpwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcconutId() {
        return acconutId;
    }

    public void setAcconutId(String acconutId) {
        this.acconutId = acconutId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getD_CreateDate() {
        return d_CreateDate;
    }

    public void setD_CreateDate(Date dCreateDate) {
        d_CreateDate = dCreateDate;
    }



    public Integer getN_Status() {
        return n_Status;
    }

    public void setN_Status(Integer nStatus) {
        n_Status = nStatus;
    }

    public Date getD_ModifyDate() {
        return d_ModifyDate;
    }

    public void setD_ModifyDate(Date dModifyDate) {
        d_ModifyDate = dModifyDate;
    }

    public Integer getN_Modified() {
        return n_Modified;
    }

    public void setN_Modified(Integer nModified) {
        n_Modified = nModified;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public Integer getCustomer_son_id() {
        return customer_son_id;
    }

    public void setCustomer_son_id(Integer customerSonId) {
        customer_son_id = customerSonId;
    }

    public String getPay_Range() {
        return pay_Range;
    }

    public void setPay_Range(String pay_Range) {
        this.pay_Range = pay_Range;
    }

    public Integer getRegister_Platfrom() {
        return register_Platfrom;
    }

    public void setRegister_Platfrom(Integer register_Platfrom) {
        this.register_Platfrom = register_Platfrom;
    }

    public Integer getRegister_Device() {
        return register_Device;
    }

    public void setRegister_Device(Integer register_Device) {
        this.register_Device = register_Device;
    }

    public BigDecimal getN_AccountAmount() {
        return n_AccountAmount;
    }

    public void setN_AccountAmount(BigDecimal n_AccountAmount) {
        this.n_AccountAmount = n_AccountAmount;
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

}
