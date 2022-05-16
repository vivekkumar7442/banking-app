package com.sahaj.banking.beans;

/**
 * @author vivek
 * ValidationResult used to store the transaction valid details
 *
 */
public class ValidationResult {
	private final boolean isValid;
	private final String errorMsg;

	public ValidationResult(boolean isValid, String errorMsg) {
		this.errorMsg = errorMsg;
		this.isValid = isValid;
	}

	public static ValidationResult valid() {
		return new ValidationResult(true, null);
	}

	public static ValidationResult invalid(String errorMsg) {
		return new ValidationResult(false, errorMsg);
	}

	public boolean notValid() {
		return !isValid;
	}

	public boolean isValid() {
		return isValid;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	
	
}