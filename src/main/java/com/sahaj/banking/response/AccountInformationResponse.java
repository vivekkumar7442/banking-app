package com.sahaj.banking.response;

import java.math.BigDecimal;


/**
 * @author vivek
 * 
 * this class has all the attribute for AccountInformationResponse response
 *
 */
public class AccountInformationResponse extends BaseResponse {

	private String accountNumber;

	private BigDecimal accountBalance;

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

}
