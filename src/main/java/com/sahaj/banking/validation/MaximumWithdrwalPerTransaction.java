package com.sahaj.banking.validation;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sahaj.banking.beans.ValidationContext;
import com.sahaj.banking.beans.ValidationResult;
import com.sahaj.banking.constant.AccountStatusConstant;
import com.sahaj.banking.service.IValidationRules;

/**
 * @author vivek
 * 
 *  This class has the MaximumWithdrwalPerTransaction Validation Rules
 *
 */
@Service
public class MaximumWithdrwalPerTransaction implements IValidationRules {

	@Value("${maximum.withdrawal.amount.pertransaction}")
	BigDecimal maximumWitdrawalAmountPerTransaction;

	@Override
	public ValidationResult validate(ValidationContext validationContext) {
		if (validationContext.getTransactionAmount().compareTo(maximumWitdrawalAmountPerTransaction) == 0) {
			return new ValidationResult(true, AccountStatusConstant.SUCCESS);

		}
		
		if (validationContext.getTransactionAmount().compareTo(maximumWitdrawalAmountPerTransaction) >= 1) {
			return new ValidationResult(false, ": Maximum withdrawal amount is " + maximumWitdrawalAmountPerTransaction);

		} else {
			return new ValidationResult(true,  AccountStatusConstant.SUCCESS);

		}

	}

}
