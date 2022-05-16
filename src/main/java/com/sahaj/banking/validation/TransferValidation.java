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

/**
 * @author vivek
 * 
 *  This class has the TransferValidation Validation Rules
 *
 */
@Service
public class TransferValidation implements ITransactionValidation {

	@Autowired
	private SourceAccountTransferValidation sourceAccountTransferValidation;

	@Autowired
	private DestinationAccountIncomingTransactionValidation destinationAccountIncomingTransactionValidation;

	@Override
	public ValidationResult validate(ValidationContext validationContext) {

		for (IValidationRules rules : getValidationRulesList()) {
			ValidationResult validationResult = rules.validate(validationContext);

			if (validationResult.notValid()) {
				return validationResult;
			}
		}
		return new ValidationResult(true,ValidationConstants.VALID_DEPOSIT "VALID-TRANSFER");
	}

	@Override
	public List<IValidationRules> getValidationRulesList() {

		return Arrays.asList(sourceAccountTransferValidation, destinationAccountIncomingTransactionValidation);
	}

}
