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
 *  This class has the MinimumAccountBalanceValidation Validation Rules
 *
 */
@Service
public class MinimumAccountBalanceValidation implements IValidationRules {

	@Value("${minimum.account.balance}")
	BigDecimal minimumAccountBalance;

	@Override
	public ValidationResult validate(ValidationContext validationContext) {
		BigDecimal transactionAmount = validationContext.getTransactionAmount();
		BigDecimal userAccountBalance = validationContext.getUserAccountBalance();

		if (userAccountBalance.subtract(transactionAmount).compareTo(minimumAccountBalance) < 0) {
			return new ValidationResult(false, " Insufficient balance");

		} else {
			return new ValidationResult(true,  AccountStatusConstant.SUCCESS);

		}

	}

}
