package com.sahaj.banking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaj.banking.beans.ResponseResources;
import com.sahaj.banking.entity.AccountDetails;
import com.sahaj.banking.exception.BankingSolutionCommonException;
import com.sahaj.banking.exception.ExceptionConstants;
import com.sahaj.banking.request.AccountCreationRequest;
import com.sahaj.banking.request.AccountInformationRequest;
import com.sahaj.banking.response.AccountInformationResponse;
import com.sahaj.banking.service.IAccountGenerationService;
import com.sahaj.banking.service.IAccountInformationSerive;

/**
 * @author vivek
 * 
 *  AccountInformationServiceImpl for exposing account related information
 *
 */
@Service
public class AccountInformationServiceImpl extends AccountInformationAbstractService
		implements IAccountInformationSerive {

	@Autowired
	private IAccountGenerationService iAccountGenerationService;

	@Override
	public AccountInformationResponse createAccount(AccountCreationRequest accountCreationRequest)
			throws BankingSolutionCommonException {

		AccountInformationResponse accountInformationResponse = new AccountInformationResponse();

		accountCreationInputValidation(accountCreationRequest);

		AccountDetails existingAccountDetails = accountRepository
				.findByUser_userName(accountCreationRequest.getAccountHolderFullName());

		if (existingAccountDetails != null) {
			throw new BankingSolutionCommonException(ExceptionConstants.ACCOUNT_ALREADY_EXIST,
					ResponseResources.R_CODE_ERROR);
		}

		AccountDetails accountDetails = createUserAccount(accountCreationRequest.getAccountHolderFullName(),
				iAccountGenerationService.getAccounNumber());
		accountRepository.save(accountDetails);
		accountInformationResponse.setAccountNumber(accountDetails.getAccountNumber());

		return accountInformationResponse;
	}

	@Override
	public AccountInformationResponse getAccountInformation(AccountInformationRequest accountInformationRequest)
			throws BankingSolutionCommonException {

		AccountInformationResponse accountInformationResponse = new AccountInformationResponse();

		getAccountInformationInputValidation(accountInformationRequest);

		AccountDetails accountDetails = accountRepository
				.findByAccountNumber(accountInformationRequest.getAccountNumber());

		if (accountDetails == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.INVALID_ACCOUNT_NUMBER,
					ResponseResources.R_CODE_BAD_REQUEST);
		}

		accountInformationResponse.setAccountBalance(accountDetails.getAccountBalance());
		return accountInformationResponse;
	}

	private void accountCreationInputValidation(AccountCreationRequest accountCreationRequest)
			throws BankingSolutionCommonException {

		if (accountCreationRequest == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.REQUEST_CANNOT_BE_NULL,
					ResponseResources.R_CODE_BAD_REQUEST);
		}

		if (accountCreationRequest.getAccountHolderFullName() == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.ACCOUNT_NAME_CANNOT_BE_NULL,
					ResponseResources.R_CODE_ERROR);
		}
	}
	
	private void getAccountInformationInputValidation(AccountInformationRequest accountInformationRequest)
			throws BankingSolutionCommonException {

		if (accountInformationRequest == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.REQUEST_CANNOT_BE_NULL,
					ResponseResources.R_CODE_BAD_REQUEST);
		}

		if (accountInformationRequest.getAccountNumber() == null) {
			throw new BankingSolutionCommonException(ExceptionConstants.ACCOUNT_NUMBER_CANNOT_BE_NULL,
					ResponseResources.R_CODE_ERROR);
		}
	}
	
	
	

}
