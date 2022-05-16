package com.sahaj.banking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.sahaj.banking.entity.AccountDetails;
import com.sahaj.banking.entity.User;
import com.sahaj.banking.repository.AccountRepository;
import com.sahaj.banking.repository.UserRepository;

/**
 * @author vivek
 * 
 *  AccountInformationAbstractService for reusing the common methods
 *
 */
public abstract class AccountInformationAbstractService {

	@Autowired
	protected AccountRepository accountRepository;

	@Autowired
	protected UserRepository userRepository;

	protected AccountDetails createUserAccount(String userName, String accountNumber) {
		User user = createdUser(userName);
		AccountDetails accountDetails = createAccounDetails(accountNumber);
		accountDetails.setUser(user);
		accountRepository.save(accountDetails);
		return accountDetails;
	}

	protected User createdUser(String userName) {
		User user = new User();
		user.setUserName(userName);
		userRepository.save(user);
		return user;

	}

	protected AccountDetails createAccounDetails(String accountNumber) {
		AccountDetails accountDetails = new AccountDetails();
		accountDetails.setAccountNumber(accountNumber);
		return accountDetails;
	}

}
