package com.sahaj.banking.service;

import com.sahaj.banking.exception.BankingSolutionCommonException;
import com.sahaj.banking.request.WithdrawalRequest;
import com.sahaj.banking.response.WithdrawalResponse;

/**
 * @author vivek
 * 
 *  interface for withdrawal service
 *
 */
public interface IWithdrawalService {
	
	WithdrawalResponse withdrawAmount(WithdrawalRequest withdrawalRequest) throws BankingSolutionCommonException;
}
