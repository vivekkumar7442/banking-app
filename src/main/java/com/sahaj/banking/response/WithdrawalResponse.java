package com.sahaj.banking.response;

import java.math.BigDecimal;



/**
 * @author vivek
 * 
 * this class has all the attribute for Withdrawal response
 *
 */
public class WithdrawalResponse {

	private BigDecimal accountBalance;

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

}
