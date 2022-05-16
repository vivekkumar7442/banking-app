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
 *  This class has the MaximumWithdrawalPerDayTransaction Validation Rules
 *
 */
@Service
public class MaximumWithdrawalPerDayTransaction implements IValidationRules {

	@Value("${maximum.withdrawal.perday}")
	Integer maximumWithdrawalAmountPerTransaction;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public ValidationResult validate(ValidationContext validationContext) {

		List<TransactionDetails> transactionDetails = transactionRepository
				.findByAccountNumberAndTransactionTypeAndStatusAndTransactionDateBetween(validationContext.getAccountNumber(), AccountStatusConstant.WITHDRAWAL, AccountStatusConstant.ACTIVE,
						validationContext.getCurrentDate(), validationContext.getCurrentDate());

		if (transactionDetails.size() < maximumWithdrawalAmountPerTransaction) {

			return new ValidationResult(true,  AccountStatusConstant.SUCCESS);

		} else {
			return new ValidationResult(false,
					"Only" + maximumWithdrawalAmountPerTransaction + " withdrawals are allowed in a day");

		}

	}

}
