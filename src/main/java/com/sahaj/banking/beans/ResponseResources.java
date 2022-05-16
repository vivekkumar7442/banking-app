package com.sahaj.banking.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Custom Rest Response class
 * 
 *  @author vivek
 *
 * @param <T>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseResources<T> {

	public ResponseResources() {
		this.message = ResponseResources.R_MSG_EMPTY;
		this.responseCode = ResponseResources.R_CODE_OK;
		this.status = Status.SUCCESS;
		this.setData(data);
	}

	public static final String R_MSG_EMPTY = "";
	public static final Integer R_CODE_OK = 200;
	public static final Integer R_CODE_ERROR = 500;
	public static final Integer R_CODE_NOT_FOUND = 404;
	public static final Integer R_CODE_BAD_REQUEST = 400;
	public static final Integer R_CODE_INTERNAL_SERVER_ERROR = 500;
	public static final Integer R_CODE_UNAUTH_ERROR = 401;
	public static final Integer R_CODE_FORBIDDEN_ERROR = 403;
	public static final String RES_SUCCESS = "OK";
	public static final String RES_FAILURE = "OPERATION FAILED";
	public static final String RES_NO_DATA = "NO DATA";
	private final Integer responseCode;
	private final String message;
	private final Status status;
	private T data;

	/**
	 * Creates a new instance of ResponseResource
	 *
	 * @param code
	 * @param message
	 * @param execDt
	 */
	public ResponseResources(final Integer code, final String message, final Status status) {
		this.message = message == null ? ResponseResources.R_MSG_EMPTY : message;
		this.responseCode = code == null ? ResponseResources.R_CODE_OK : code;
		this.status = status == null ? Status.SUCCESS : status;
	}

	public ResponseResources(final Integer code, final String message, T data, final Status status) {
		this.message = message == null ? ResponseResources.R_MSG_EMPTY : message;
		this.responseCode = code == null ? ResponseResources.R_CODE_OK : code;
		this.status = status == null ? Status.SUCCESS : status;
		this.setData(data);
	}

	public ResponseResources(T data) {
		this.message = ResponseResources.R_MSG_EMPTY;
		this.responseCode = ResponseResources.R_CODE_OK;
		this.data = data;
		this.status = Status.SUCCESS;
	}

	public String getMessage() {

		return this.message;
	}

	public Integer getResponseCode() {

		return this.responseCode;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Status getStatus() {
		return status;
	}

}