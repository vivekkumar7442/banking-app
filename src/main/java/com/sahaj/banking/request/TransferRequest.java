package com.sahaj.banking.request;

import java.math.BigDecimal;

/**
 * @author vivek
 * 
 * this class has all the attribute for Transfer Transaction
 *
 */
public class TransferRequest {

	private String sourceAccountNumber;

	private String destinationAccountNumber;

	private BigDecimal accountBalance;

	private BigDecimal transferAmount;

	public String getSourceAccountNumber() {
		return sourceAccountNumber;
	}

	public void setSourceAccountNumber(String sourceAccountNumber) {
		this.sourceAccountNumber = sourceAccountNumber;
	}

	public String getDestinationAccountNumber() {
		return destinationAccountNumber;
	}

	public void setDestinationAccountNumber(String destinationAccountNumber) {
		this.destinationAccountNumber = destinationAccountNumber;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

}
