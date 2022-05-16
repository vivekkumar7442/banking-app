package com.sahaj.banking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sahaj.banking.constant.AccountStatusConstant;
import com.sahaj.banking.service.ITransactionHelperService;
import com.sahaj.banking.service.ITransactionValidation;
import com.sahaj.banking.validation.DepositValidation;
import com.sahaj.banking.validation.TransferValidation;
import com.sahaj.banking.validation.WithDrawalValidation;
/**
 * @author vivek
 * 
 *  TransactionFactory to give the respective class to hanlde the validation
 *
 */

@Component
public class TransactionFactory implements ITransactionHelperService {

	@Autowired
	private DepositValidation depositValidation;

	@Autowired
	private WithDrawalValidation withDrawalValidation;

	@Autowired
	private TransferValidation transferValidation;

	@Override
	public ITransactionValidation getValidationType(String type) {

		switch (type) {
		case AccountStatusConstant.DEPOSIT:

			return depositValidation;

		case AccountStatusConstant.WITHDRAWAL:

			return withDrawalValidation;

		case AccountStatusConstant.TRANSFER:

			return transferValidation;

		default:
			break;
		}

		return null;
	}

}
