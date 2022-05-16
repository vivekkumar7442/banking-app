package com.sahaj.banking.service;

import com.sahaj.banking.exception.BankingSolutionCommonException;
import com.sahaj.banking.request.AccountCreationRequest;
import com.sahaj.banking.request.AccountInformationRequest;
import com.sahaj.banking.response.AccountInformationResponse;

/**
 * @author vivek
 * 
 * interface for Account information details
 *
 */
public interface IAccountInformationSerive {

	AccountInformationResponse getAccountInformation(AccountInformationRequest accountInformationRequest)throws BankingSolutionCommonException;

	AccountInformationResponse createAccount(AccountCreationRequest accountCreationRequest)throws BankingSolutionCommonException;

}
