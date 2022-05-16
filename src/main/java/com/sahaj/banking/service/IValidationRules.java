package com.sahaj.banking.service;

import com.sahaj.banking.beans.ValidationContext;
import com.sahaj.banking.beans.ValidationResult;

/**
 * @author vivek
 * 
 *  interface for validation Rules
 *
 */
public interface IValidationRules {

	ValidationResult validate(ValidationContext validationContext);

}
