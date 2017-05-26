          package com.kmzyc.supplier.model;
  
          import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

          public class BnesAcctEnchashment
            implements Serializable
          {
            private static final long serialVersionUID = 6268308325737218548L;
            private BigDecimal enchashmentId;
            private BigDecimal nLoginId;
            private String loginAccount;
            private String accountTransactionId;
            private BigDecimal accountId;
            private String enchashmentDepict;
            private Short enchashmentStatus;
            private Date createDate;
            private Date createDateBegin;
            private Date createDateEnd;
            private Date modifyDate;
            private BigDecimal enchashmentAmount;
            private String enchashmentFrom;
            private String remarks;
            private BigDecimal enchashmentType;
            private Short enchashmentResource;
            private String corporateName;
            private String bankOfDeposit;
            private String corporateAccount;
            private String bankAccountName;
            private String bankOfDepositBranchName;
            private String companyAccount;
            private Integer minNum;
            private Integer maxNum;
            private String confirmRemarks;
         public String getConfirmRemarks() {
             return confirmRemarks;
           }
           public void setConfirmRemarks(String confirmRemarks) {
             this.confirmRemarks = confirmRemarks;
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
         public String getRemarks() {
             return remarks;
         }
         public void setRemarks(String remarks) {
             this.remarks = remarks;
         }
         public BigDecimal getEnchashmentType() {
             return enchashmentType;
         }
         public void setEnchashmentType(BigDecimal enchashmentType) {
             this.enchashmentType = enchashmentType;
         }
         public String getCorporateName() {
             return corporateName;
         }
         public void setCorporateName(String corporateName) {
             this.corporateName = corporateName;
         }
         public String getBankOfDeposit() {
             return bankOfDeposit;
         }
         public void setBankOfDeposit(String bankOfDeposit) {
             this.bankOfDeposit = bankOfDeposit;
         }
         public String getCorporateAccount() {
             return corporateAccount;
         }
         public void setCorporateAccount(String corporateAccount) {
             this.corporateAccount = corporateAccount;
         }
         public String getBankOfDepositBranchName() {
             return bankOfDepositBranchName;
         }
         public void setBankOfDepositBranchName(String bankOfDepositBranchName) {
             this.bankOfDepositBranchName = bankOfDepositBranchName;
         }
         public String getCompanyAccount() {
             return companyAccount;
         }
         public void setCompanyAccount(String companyAccount) {
             this.companyAccount = companyAccount;
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
         public Short getEnchashmentResource() {
             return enchashmentResource;
         }
         public void setEnchashmentResource(Short enchashmentResource) {
             this.enchashmentResource = enchashmentResource;
         }
         public String getBankAccountName() {
             return bankAccountName;
         }
         public void setBankAccountName(String bankAccountName) {
             this.bankAccountName = bankAccountName;
         }


          }

