package com.sahaj.banking.service;

import com.sahaj.banking.exception.BankingSolutionCommonException;
import com.sahaj.banking.request.TransferRequest;
import com.sahaj.banking.response.TransferResponse;

/**
 * @author vivek
 * 
 *  interface for Transfer Activity
 *
 */
public interface ITransferService {
	
	TransferResponse transferAmount(TransferRequest transferRequest) throws BankingSolutionCommonException;
}
