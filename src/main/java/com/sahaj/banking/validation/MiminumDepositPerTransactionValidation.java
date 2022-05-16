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
 *  This class has the MiminumDepositPerTransactionValidation Validation Rules
 *
 */
@Service
public class MiminumDepositPerTransactionValidation implements IValidationRules {

	@Value("${minimum.deposit.amount.pertransaction}")
	BigDecimal minimumDepositAmountPerTransaction;

	@Override
	public ValidationResult validate(ValidationContext validationContext) {

		if (validationContext.getTransactionAmount().compareTo(minimumDepositAmountPerTransaction) == 0) {
			return new ValidationResult(true,  AccountStatusConstant.SUCCESS);

		}

		if (validationContext.getTransactionAmount().compareTo(minimumDepositAmountPerTransaction) < 1) {
			return new ValidationResult(false, "Minimum deposit amount is " + minimumDepositAmountPerTransaction);

		} else {
			return new ValidationResult(true,  AccountStatusConstant.SUCCESS);

		}

	}

}
