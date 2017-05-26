package com.kmzyc.b2b.model;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.framework.exception.ObjectTransformException;

public class AccountInfo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    // private static Logger logger = Logger.getLogger(AccountInfo.class);
    private static Logger logger = LoggerFactory.getLogger(AccountInfo.class);
    private Integer naccountId;
    private Integer customerTypeId;
    private Long loginId;
    private String accountLogin;
    private String paymentpwd;
    private String name;
    private String acconutId;
    private String mobile;
    private String email;
    private String address;
    private Date createDate;
    private Double accountAmount;
    private Double amountFrozen;
    private Double amountAvlibal;
    private Integer status;
    private Date modifyDate;
    private Integer modified;

    private String payRange;
    /**
     * 机构编码
     */
    private String organCode;
    /**
     * 机构描述
     */
    private String organDes;
    /**
     * 推广者
     */
    private Long promId;

    public com.pltfm.app.vobject.AccountInfo transFormToRemoteAddress()
            throws ObjectTransformException {
        com.pltfm.app.vobject.AccountInfo accountInfo = new com.pltfm.app.vobject.AccountInfo();

        // 手动转换名称不相同的属性
        accountInfo.setN_AccountId(naccountId);
        accountInfo.setN_CustomerTypeId(customerTypeId);
        accountInfo.setN_LoginId(Integer.parseInt("" + loginId));
        accountInfo.setD_CreateDate(createDate);
        accountInfo.setN_AccountAmount(new BigDecimal(accountAmount));
        accountInfo.setN_Status(status);
        accountInfo.setN_Modified(modified);
        try {
            // 转换名称相同的属性
            BeanUtils.copyProperties(accountInfo, this);
        } catch (IllegalAccessException e) {
            logger.error("将本地AccountInfo对象转换为远程对象出错：" + e.getMessage(), e);
            throw new ObjectTransformException("将本地AccountInfo对象转换为远程对象出错！");
        } catch (InvocationTargetException e) {
            logger.error("将本地AccountInfo对象转换为远程对象出错：" + e.getMessage(), e);
            throw new ObjectTransformException("将本地AccountInfo对象转换为远程对象出错！");
        }

        return accountInfo;
    }

    public Integer getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(Integer customerTypeId) {
        this.customerTypeId = customerTypeId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Double getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(Double accountAmount) {
        this.accountAmount = accountAmount;
    }

    public Double getAmountFrozen() {
        return amountFrozen;
    }

    public void setAmountFrozen(Double amountFrozen) {
        this.amountFrozen = amountFrozen;
    }

    public Double getAmountAvlibal() {
        return amountAvlibal;
    }

    public void setAmountAvlibal(Double amountAvlibal) {
        this.amountAvlibal = amountAvlibal;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getNaccountId() {
        return naccountId;
    }

    public void setNaccountId(Integer naccountId) {
        this.naccountId = naccountId;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getPayRange() {
        return payRange;
    }

    public void setPayRange(String payRange) {
        this.payRange = payRange;
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getOrganDes() {
        return organDes;
    }

    public void setOrganDes(String organDes) {
        this.organDes = organDes;
    }

    public Long getPromId() {
        return promId;
    }

    public void setPromId(Long promId) {
        this.promId = promId;
    }
}
