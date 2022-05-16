package com.sahaj.banking.beans;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author vivek
 * Validation contest input used store the input for validation rules
 *
 */
public class ValidationContext {

	private String accountNumber;

	private BigDecimal transactionAmount;

	private BigDecimal userAccountBalance;

	private String destinationAccountNumber;

	private String transactionType;
	
	private Date currentDate;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public BigDecimal getUserAccountBalance() {
		return userAccountBalance;
	}

	public void setUserAccountBalance(BigDecimal userAccountBalance) {
		this.userAccountBalance = userAccountBalance;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getDestinationAccountNumber() {
		return destinationAccountNumber;
	}

	public void setDestinationAccountNumber(String destinationAccountNumber) {
		this.destinationAccountNumber = destinationAccountNumber;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	
	

}
