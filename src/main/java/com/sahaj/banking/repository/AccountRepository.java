package com.sahaj.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahaj.banking.entity.AccountDetails;

@Repository
public interface AccountRepository extends JpaRepository<AccountDetails,Long>{
	
	AccountDetails findByUser_userName(String userName);
	
	AccountDetails findByAccountNumber(String accountNumber);


}
