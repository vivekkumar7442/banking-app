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
 *  This class has the MaximumDepositPerTransactionValidation Validation Rules
 *
 */
@Service
public class MaximumDepositPerTransactionValidation implements IValidationRules {

	@Value("${maximum.deposit.amount.pertransaction}")
	BigDecimal maximumDepositAmountPerTransaction;

	@Override
	public ValidationResult validate(ValidationContext validationContext) {
		if (validationContext.getTransactionAmount().compareTo(maximumDepositAmountPerTransaction) > 1) {
			return new ValidationResult(false, "Maximum deposit amount is " + maximumDepositAmountPerTransaction);

		} else {
			return new ValidationResult(true,  AccountStatusConstant.SUCCESS);

		}

	}

}
