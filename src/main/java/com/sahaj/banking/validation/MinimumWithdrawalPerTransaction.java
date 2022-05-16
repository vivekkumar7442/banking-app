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
 *  This class has the MinimumWithdrawalPerTransaction Validation Rules
 *
 */
@Service
public class MinimumWithdrawalPerTransaction implements IValidationRules{

	@Value("${minimum.withdrawal.amount.pertransaction}")
	BigDecimal minimumWithdrawalAmountPerTransaction;

	@Override
	public ValidationResult validate(ValidationContext validationContext) {
		
		if (validationContext.getTransactionAmount().compareTo(minimumWithdrawalAmountPerTransaction) == 0) {
			return new ValidationResult(true,  AccountStatusConstant.SUCCESS);

		}

		if (validationContext.getTransactionAmount().compareTo(minimumWithdrawalAmountPerTransaction) < 1) {
			return new ValidationResult(false, "Minimum withdrawal amount is " + minimumWithdrawalAmountPerTransaction);

		} else {
			return new ValidationResult(true,  AccountStatusConstant.SUCCESS);

		}

	}

}
