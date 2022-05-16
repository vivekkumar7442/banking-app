package com.sahaj.banking.validation;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sahaj.banking.beans.ValidationContext;
import com.sahaj.banking.beans.ValidationResult;
import com.sahaj.banking.constant.AccountStatusConstant;
import com.sahaj.banking.service.IValidationRules;

@Service
public class MaximumAccountBalanceValidation implements IValidationRules {

	@Value("${maximum.account.balance}")
	BigDecimal maximumAccountBalance;

	@Override
	public ValidationResult validate(ValidationContext validationContext) {
		BigDecimal transactionAmount = validationContext.getTransactionAmount();
		BigDecimal currentAccountBalance = validationContext.getUserAccountBalance();
		if (transactionAmount.add(currentAccountBalance).compareTo(maximumAccountBalance) >= 1) {
			return new ValidationResult(false, "Account balance cannot exceed " + maximumAccountBalance);

		} else {
			return new ValidationResult(true, AccountStatusConstant.SUCCESS);

		}

	}

}
