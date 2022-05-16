package com.sahaj.banking.exception;

/**
 * @author vivek
 *
 */
public class BankingSolutionCommonException extends Exception {

	private int statusCode = 500;
	private String errorCode;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BankingSolutionCommonException(String message) {
		super(message);
	}

	public BankingSolutionCommonException(String message, Throwable cause) {
		super(message, cause);

	}

	public BankingSolutionCommonException(String message, int statusCode, String errorCode, Throwable cause) {
		super(message, cause);
		this.statusCode = statusCode;
		this.errorCode = errorCode;

	}
	
	public BankingSolutionCommonException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;

	}

	public BankingSolutionCommonException(Throwable cause) {
		super(cause);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}