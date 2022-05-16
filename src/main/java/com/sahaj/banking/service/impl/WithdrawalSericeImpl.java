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
import com.sahaj.banking.request.WithdrawalRequest;
import com.sahaj.banking.response.WithdrawalResponse;
import com.sahaj.banking.service.ITransactionHelperService;
import com.sahaj.banking.service.ITransactionValidation;
import com.sahaj.banking.service.IWithdrawalService;

/**
 * @author vivek
 * 
 *  WithdrawalSericeImpl for withdrawal Activity
 *
 */
@Service
public class WithdrawalSericeImpl implements IWithdrawalService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ITransactionHelperService iTransactionHelperService;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public synchronized WithdrawalResponse withdrawAmount(WithdrawalRequest withdrawalRequest)
			throws BankingSolutionCommonException {
		WithdrawalResponse withdrawalResponse = new WithdrawalResponse();

		withdrawAmountInputValidation(withdrawalRequest);

		AccountDetails accountDetails = accountRepository.findByAccountNumber(withdrawalRequest.getAccountNumber());

		if (accountDetails == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.INVALID_ACCOUNT_NUMBER,
					ResponseResources.R_CODE_ERROR);
		}
		
		BigDecimal currentAccountBalance = accountDetails.getAccountBalance();
		BigDecimal updatedAccountBalance = currentAccountBalance.subtract(withdrawalRequest.getWithdrawalAmount());

		ValidationContext validationContext = constructWithdrawaltContext(accountDetails,
				withdrawalRequest.getWithdrawalAmount());
		ITransactionValidation transactionValidation = iTransactionHelperService
				.getValidationType(validationContext.getTransactionType());

		ValidationResult validationResult = transactionValidation.validate(validationContext);

		if (validationResult.notValid()) {
			throw new BankingSolutionCommonException(validationResult.getErrorMsg(), ResponseResources.R_CODE_ERROR);
		}
		accountDetails.setAccountBalance(updatedAccountBalance);
		accountRepository.save(accountDetails);
		TransactionDetails transactionDetails = createTransactionDetails(accountDetails.getAccountNumber(),
				withdrawalRequest.getWithdrawalAmount(), AccountStatusConstant.WITHDRAWAL, AccountStatusConstant.SUCCESS);
		transactionRepository.save(transactionDetails);
		withdrawalResponse.setAccountBalance(updatedAccountBalance);

		return withdrawalResponse;
	}

	private ValidationContext constructWithdrawaltContext(AccountDetails accountDetails, BigDecimal transferAmount) {
		ValidationContext validationContext = new ValidationContext();
		validationContext.setAccountNumber(accountDetails.getAccountNumber());
		validationContext.setTransactionType(AccountStatusConstant.WITHDRAWAL);
		validationContext.setUserAccountBalance(
				accountDetails.getAccountBalance() == null ? new BigDecimal(0.0) : accountDetails.getAccountBalance());
		validationContext.setCurrentDate(new Date());
		validationContext.setTransactionAmount(transferAmount);
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

	private void withdrawAmountInputValidation(WithdrawalRequest withdrawalRequest)
			throws BankingSolutionCommonException {

		if (withdrawalRequest == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.REQUEST_CANNOT_BE_NULL,
					ResponseResources.R_CODE_BAD_REQUEST);
		}

		else if (withdrawalRequest.getAccountNumber() == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.ACCOUNT_NUMBER_CANNOT_BE_NULL,
					ResponseResources.R_CODE_ERROR);
		}

		else if (withdrawalRequest.getWithdrawalAmount() == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.WITHDRAWAL_CANNOT_EMPTY,
					ResponseResources.R_CODE_ERROR);
		}
	}

}
