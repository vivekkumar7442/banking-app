package com.sahaj.banking.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * 
 * @author vivek
 * This class is used to store the account information
 */

@Entity
@Table(name = "account_details")
@NamedQuery(name = "AccountDetails.findAll", query = "SELECT a FROM AccountDetails a")
public class AccountDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "account_id")
	private Long accountId;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "account_balance")
	private BigDecimal accountBalance;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
	

}
