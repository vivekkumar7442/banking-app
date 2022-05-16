package com.sahaj.banking.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * @author vivek
 * This class is used to maintain the transaction details
 */

@Entity
@Table(name = "transaction_details")
@NamedQuery(name = "TransactionDetails.findAll", query = "SELECT a FROM TransactionDetails a")
public class TransactionDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "transaction_id")
	private Long transactionId;

	@Column(name = "transaction_type")
	private String transactionType;

	@Column(name = "transaction_amount")
	private BigDecimal transactionAmount;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "transaction_date")
	private Date transactionDate;

	@Column(name = "transaction_date_time")
	private Timestamp transactionDateTime;

	private String status;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Timestamp getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(Timestamp transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
