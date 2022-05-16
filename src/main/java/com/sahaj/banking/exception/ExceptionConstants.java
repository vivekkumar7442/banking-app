package com.sahaj.banking.exception;

/**
 * @author vivek
 * 
 * this class has all the exception messages
 *
 */
public class ExceptionConstants {

	private ExceptionConstants() {

	}

	public static final String REQUEST_CANNOT_BE_NULL = "Request cannot be null";

	public static final String ACCOUNT_ALREADY_EXIST = "Account Already exists";

	public static final String INVALID_ACCOUNT_NUMBER = "Account Not exists";

	public static final String INVALID_SOURCE_ACCOUNT_NUMBER = "Invalid Source Account Number";

	public static final String INVALID_DESTINATION_ACCOUNT_NUMBER = "Invalid destination Account Number";

	public static final String SOURCE_ACCOUNT_CANNOT_BE_NULL = "Source Account Number cannot be Empty";

	public static final String DESTINATION_ACCOUNT_CANNOT_BE_NULL = "Destination Account Number cannot be Empty";

	public static final String TRANSFER_AMOUNT_CANNOT_BE_NULL = "Transfer Amount Cannot be Empty";

	public static final String ACCOUNT_NAME_CANNOT_BE_NULL = "Account Name cannot be Empty";

	public static final String ACCOUNT_NUMBER_CANNOT_BE_NULL = "Account Number cannot be Empty";

	public static final String DEPOSIT_CANNOT_EMPTY = "Deposit Amount cannot be Empty";

	public static final String WITHDRAWAL_CANNOT_EMPTY = "Withdrawal Amount cannot be Empty";

}