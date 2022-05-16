package com.sahaj.banking.response;

import java.math.BigDecimal;

/**
 * @author vivek
 * 
 * this class has all the attribute for Transfer API response
 *
 */
public class TransferResponse {

	private BigDecimal accountBalance;

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}



}
