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
import com.sahaj.banking.request.TransferRequest;
import com.sahaj.banking.response.TransferResponse;
import com.sahaj.banking.service.ITransactionHelperService;
import com.sahaj.banking.service.ITransactionValidation;
import com.sahaj.banking.service.ITransferService;
/**
 * @author vivek
 * 
 *  TransferSericeImpl for Transfer Activity
 *
 */

@Service
public class TransferSericeImpl implements ITransferService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ITransactionHelperService iTransactionHelperService;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public synchronized TransferResponse transferAmount(TransferRequest transferRequest) throws BankingSolutionCommonException {
		TransferResponse transferResponse = new TransferResponse();

		transferInputValidation(transferRequest);

		AccountDetails sourceAccount = accountRepository.findByAccountNumber(transferRequest.getSourceAccountNumber());

		if (sourceAccount == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.INVALID_SOURCE_ACCOUNT_NUMBER,
					ResponseResources.R_CODE_ERROR);
		}

		AccountDetails destinationAccountNumber = accountRepository
				.findByAccountNumber(transferRequest.getDestinationAccountNumber());

		if (destinationAccountNumber == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.INVALID_DESTINATION_ACCOUNT_NUMBER,
					ResponseResources.R_CODE_ERROR);
		}

		BigDecimal currentAccountBalance = sourceAccount.getAccountBalance();
		BigDecimal updatedAccountBalance = currentAccountBalance.subtract(transferRequest.getTransferAmount());

		ValidationContext validationContext = constructTransferContest(sourceAccount, destinationAccountNumber,
				transferRequest.getTransferAmount());

		ITransactionValidation transactionValidation = iTransactionHelperService
				.getValidationType(validationContext.getTransactionType());

		ValidationResult validationResult = transactionValidation.validate(validationContext);

		if (validationResult.notValid()) {
			throw new BankingSolutionCommonException(validationResult.getErrorMsg(), ResponseResources.R_CODE_ERROR);
		}
		sourceAccount.setAccountBalance(updatedAccountBalance);
		accountRepository.save(sourceAccount);

		TransactionDetails sourceDebitTransactionDetails = createTransactionDetails(sourceAccount.getAccountNumber(),
				transferRequest.getTransferAmount(), AccountStatusConstant.WITHDRAWAL, AccountStatusConstant.SUCCESS);
		transactionRepository.save(sourceDebitTransactionDetails);

		TransactionDetails destinationCreditDetails = createTransactionDetails(
				destinationAccountNumber.getAccountNumber(), transferRequest.getTransferAmount(), AccountStatusConstant.DEPOSIT, AccountStatusConstant.SUCCESS);
		transactionRepository.save(destinationCreditDetails);
		transferResponse.setAccountBalance(updatedAccountBalance);

		return transferResponse;
	}

	private ValidationContext constructTransferContest(AccountDetails sourceAccount, AccountDetails destinationAccount,
			BigDecimal transferAmount) {
		ValidationContext validationContext = new ValidationContext();
		validationContext.setAccountNumber(sourceAccount.getAccountNumber());
		validationContext.setTransactionType(AccountStatusConstant.TRANSFER);
		validationContext.setUserAccountBalance(
				sourceAccount.getAccountBalance() == null ? new BigDecimal(0.0) : sourceAccount.getAccountBalance());
		validationContext.setDestinationAccountNumber(destinationAccount.getAccountNumber());
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

	private void transferInputValidation(TransferRequest transferRequest) throws BankingSolutionCommonException {

		if (transferRequest == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.REQUEST_CANNOT_BE_NULL,
					ResponseResources.R_CODE_BAD_REQUEST);
		}

		else if (transferRequest.getSourceAccountNumber() == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.SOURCE_ACCOUNT_CANNOT_BE_NULL,
					ResponseResources.R_CODE_ERROR);
		}

		else if (transferRequest.getDestinationAccountNumber() == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.DESTINATION_ACCOUNT_CANNOT_BE_NULL,
					ResponseResources.R_CODE_ERROR);
		} else if (transferRequest.getTransferAmount() == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.TRANSFER_AMOUNT_CANNOT_BE_NULL,
					ResponseResources.R_CODE_ERROR);
		}
	}

}
