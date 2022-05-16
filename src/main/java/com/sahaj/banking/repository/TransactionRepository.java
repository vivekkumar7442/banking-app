package com.sahaj.banking.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahaj.banking.entity.TransactionDetails;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionDetails, Long> {

	List<TransactionDetails> findByAccountNumberAndTransactionTypeAndStatusAndTransactionDateBetween(String accountNumber,String transactionType, String status,
			Date startDate, Date endDate);

}
