package com.sahaj.banking.validation;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaj.banking.beans.ValidationContext;
import com.sahaj.banking.beans.ValidationResult;
import com.sahaj.banking.constant.ValidationConstants;
import com.sahaj.banking.service.ITransactionValidation;
import com.sahaj.banking.service.IValidationRules;

@Service
public class WithDrawalValidation implements ITransactionValidation {

	@Autowired
	private MinimumAccountBalanceValidation minimumAccountBalanceValidation;

	@Autowired
	private MinimumWithdrawalPerTransaction minimumWithdrawalPerTransaction;

	@Autowired
	private MaximumWithdrwalPerTransaction maximumWithdrwalPerTransaction;

	@Autowired
	private MaximumWithdrawalPerDayTransaction maximumWithdrawalPerDayTransaction;

	@Override
	public ValidationResult validate(ValidationContext validationContext) {

		for (IValidationRules rules : getValidationRulesList()) {
			ValidationResult validationResult = rules.validate(validationContext);

			if (validationResult.notValid()) {
				return validationResult;
			}
		}
		return new ValidationResult(true, ValidationConstants.VALID_TRANSFER);
	}

	@Override
	public List<IValidationRules> getValidationRulesList() {

		return Arrays.asList(minimumWithdrawalPerTransaction, maximumWithdrwalPerTransaction,
				maximumWithdrawalPerDayTransaction, minimumAccountBalanceValidation);
	}

}
