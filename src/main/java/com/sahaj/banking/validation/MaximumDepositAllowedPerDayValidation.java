package com.sahaj.banking.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sahaj.banking.beans.ValidationContext;
import com.sahaj.banking.beans.ValidationResult;
import com.sahaj.banking.constant.AccountStatusConstant;
import com.sahaj.banking.entity.TransactionDetails;
import com.sahaj.banking.repository.TransactionRepository;
import com.sahaj.banking.service.IValidationRules;

/**
 * @author vivek
 * 
 *  This class has the MaximumDepositAllowedPerDayValidation Validation Rules
 *
 */
@Service
public class MaximumDepositAllowedPerDayValidation implements IValidationRules {

	@Value("${maximum.deposit.perday}")
	Integer maximumDepositAmountPerDay;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public ValidationResult validate(ValidationContext validationContext) {

		List<TransactionDetails> transactionDetails = transactionRepository
				.findByAccountNumberAndTransactionTypeAndStatusAndTransactionDateBetween(validationContext.getAccountNumber(), AccountStatusConstant.DEPOSIT, AccountStatusConstant.SUCCESS,
						validationContext.getCurrentDate(), validationContext.getCurrentDate());

		if (transactionDetails.size() < maximumDepositAmountPerDay) {

			return new ValidationResult(true,  AccountStatusConstant.SUCCESS);

		} else {
			return new ValidationResult(false,
					"Only" + maximumDepositAmountPerDay + " deposits are allowed in a day");

		}

	}

}
