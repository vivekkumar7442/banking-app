package com.sahaj.banking.request;

import java.math.BigDecimal;

/**
 * @author vivek
 * 
 * this class has all the attribute for Deposit Transaction
 *
 */
public class DepositRequest {

	private String accountNumber;

	private BigDecimal depositAmount;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}

}
