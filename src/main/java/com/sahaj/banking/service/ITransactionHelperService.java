package com.sahaj.banking.service;

/**
 * @author vivek
 * 
 * helper interface for the validation type locator
 *
 */
public interface ITransactionHelperService {
	
	ITransactionValidation getValidationType(String type);

}
