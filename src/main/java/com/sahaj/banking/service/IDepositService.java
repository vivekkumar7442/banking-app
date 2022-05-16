package com.sahaj.banking.service;

import com.sahaj.banking.exception.BankingSolutionCommonException;
import com.sahaj.banking.request.DepositRequest;
import com.sahaj.banking.response.AccountInformationResponse;

/**
 * @author vivek
 * 
 * interface for Account Deposit Activity
 *
 */
public interface IDepositService {
	
	AccountInformationResponse depositAmount(DepositRequest depositRequest) throws BankingSolutionCommonException;


}
