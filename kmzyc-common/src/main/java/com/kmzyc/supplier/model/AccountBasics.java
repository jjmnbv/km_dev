package com.kmzyc.supplier.model;

public class AccountBasics {
	
   private String bankAccountName;

   private String bankOfDeposit;

   private String bankOfDepositBranchName;

   private String companyAccount;

   private Double amountAvlibal;

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankOfDeposit() {
		return bankOfDeposit;
	}

	public void setBankOfDeposit(String bankOfDeposit) {
		this.bankOfDeposit = bankOfDeposit;
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

	public Double getAmountAvlibal() {
		return amountAvlibal;
	}

	public void setAmountAvlibal(Double amountAvlibal) {
		this.amountAvlibal = amountAvlibal;
	}
	
	
}
