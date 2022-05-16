package com.sahaj.banking.validation;

import org.springframework.stereotype.Service;

import com.sahaj.banking.beans.ValidationContext;
import com.sahaj.banking.beans.ValidationResult;
import com.sahaj.banking.service.IValidationRules;


/**
 * @author vivek
 * 
 *  This class has the SourceAccountTransferValidation Validation Rules
 *
 */
@Service
public class SourceAccountTransferValidation extends WithDrawalValidation implements IValidationRules {

	@Override
	public ValidationResult validate(ValidationContext validationContext) {

		for (IValidationRules rules : getValidationRulesList()) {
			ValidationResult validationResult = rules.validate(validationContext);

			if (validationResult.notValid()) {
				return validationResult;
			}
		}
		return new ValidationResult(true, "VALID-SOURCE-TRANSFER");
	}

	
}
