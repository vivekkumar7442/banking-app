package com.sahaj.banking.request;

import java.math.BigDecimal;

/**
 * @author vivek
 * 
 * this class has all the attribute for withdrawal Transaction
 *
 */
public class WithdrawalRequest {

	private String accountNumber;

	private BigDecimal withdrawalAmount;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getWithdrawalAmount() {
		return withdrawalAmount;
	}

	public void setWithdrawalAmount(BigDecimal withdrawalAmount) {
		this.withdrawalAmount = withdrawalAmount;
	}

}
