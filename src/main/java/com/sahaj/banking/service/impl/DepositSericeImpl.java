package com.sahaj.banking.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaj.banking.beans.ResponseResources;
import com.sahaj.banking.beans.ValidationContext;
import com.sahaj.banking.beans.ValidationResult;
import com.sahaj.banking.constant.AccountStatusConstant;
import com.sahaj.banking.entity.AccountDetails;
import com.sahaj.banking.entity.TransactionDetails;
import com.sahaj.banking.exception.BankingSolutionCommonException;
import com.sahaj.banking.exception.ExceptionConstants;
import com.sahaj.banking.repository.AccountRepository;
import com.sahaj.banking.repository.TransactionRepository;
import com.sahaj.banking.request.DepositRequest;
import com.sahaj.banking.response.AccountInformationResponse;
import com.sahaj.banking.service.IDepositService;
import com.sahaj.banking.service.ITransactionHelperService;
import com.sahaj.banking.service.ITransactionValidation;

/**
 * @author vivek
 * 
 *  DepositSericeImpl for deposit activity
 *
 */
@Service
public class DepositSericeImpl implements IDepositService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ITransactionHelperService iTransactionHelperService;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public synchronized AccountInformationResponse depositAmount(DepositRequest depositRequest)
			throws BankingSolutionCommonException {

		AccountInformationResponse accountInformationResponse = new AccountInformationResponse();

		depositAmountInputValidation(depositRequest);

		AccountDetails accountDetails = accountRepository.findByAccountNumber(depositRequest.getAccountNumber());

		if (accountDetails == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.INVALID_ACCOUNT_NUMBER,
					ResponseResources.R_CODE_ERROR);
		}
		BigDecimal currentAccountBalance = accountDetails.getAccountBalance() == null ? new BigDecimal(0.0)
				: accountDetails.getAccountBalance();
		BigDecimal updatedAccountBalance = currentAccountBalance.add(depositRequest.getDepositAmount());

		ValidationContext validationContext = constructDepositContext(accountDetails,
				depositRequest.getDepositAmount());

		ITransactionValidation transactionValidation = iTransactionHelperService
				.getValidationType(validationContext.getTransactionType());

		ValidationResult validationResult = transactionValidation.validate(validationContext);

		if (validationResult.notValid()) {
			throw new BankingSolutionCommonException(validationResult.getErrorMsg(), ResponseResources.R_CODE_ERROR);
		}
		accountDetails.setAccountBalance(updatedAccountBalance);
		accountRepository.save(accountDetails);
		accountInformationResponse.setAccountNumber(accountDetails.getAccountNumber());
		TransactionDetails transactionDetails = createTransactionDetails(accountDetails.getAccountNumber(),
				depositRequest.getDepositAmount(), AccountStatusConstant.DEPOSIT, AccountStatusConstant.SUCCESS);
		transactionRepository.save(transactionDetails);
		accountInformationResponse.setAccountBalance(accountDetails.getAccountBalance());
		return accountInformationResponse;
	}
	
	

	private ValidationContext constructDepositContext(AccountDetails accountDetails, BigDecimal transferAmount) {
		ValidationContext validationContext = new ValidationContext();
		validationContext.setAccountNumber(accountDetails.getAccountNumber());
		validationContext.setTransactionType(AccountStatusConstant.DEPOSIT);
		validationContext.setUserAccountBalance(
				accountDetails.getAccountBalance() == null ? new BigDecimal(0.0) : accountDetails.getAccountBalance());
		validationContext.setTransactionAmount(transferAmount);
		validationContext.setCurrentDate(new Date());
		return validationContext;
	}

	private TransactionDetails createTransactionDetails(String accountNumber, BigDecimal transactionAmount,
			String transactionType, String status) {
		TransactionDetails transactionDetails = new TransactionDetails();
		transactionDetails.setTransactionType(transactionType);
		transactionDetails.setTransactionAmount(transactionAmount);
		transactionDetails.setStatus(status);
		transactionDetails.setTransactionDate(new Date());
		transactionDetails.setTransactionDateTime(new Timestamp(System.currentTimeMillis()));
		transactionDetails.setAccountNumber(accountNumber);
		return transactionDetails;
	}

	private void depositAmountInputValidation(DepositRequest depositRequest) throws BankingSolutionCommonException {

		if (depositRequest == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.REQUEST_CANNOT_BE_NULL,
					ResponseResources.R_CODE_BAD_REQUEST);
		}

		if (depositRequest.getAccountNumber() == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.ACCOUNT_NUMBER_CANNOT_BE_NULL,
					ResponseResources.R_CODE_ERROR);
		}

		if (depositRequest.getDepositAmount() == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.DEPOSIT_CANNOT_EMPTY,
					ResponseResources.R_CODE_ERROR);
		}
	}

}
