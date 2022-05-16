package com.sahaj.banking.service;

import java.util.List;

import com.sahaj.banking.beans.ValidationContext;
import com.sahaj.banking.beans.ValidationResult;

/**
 * @author vivek
 * 
 *  interface for Transfer Service
 *
 */
public interface ITransactionValidation {
	
	 ValidationResult validate(ValidationContext validationContext);
	 
	 List<IValidationRules> getValidationRulesList();
	 

}
