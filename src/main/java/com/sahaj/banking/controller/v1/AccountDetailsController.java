package com.sahaj.banking.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahaj.banking.beans.ResponseResources;
import com.sahaj.banking.beans.Status;
import com.sahaj.banking.constant.Constants;
import com.sahaj.banking.constant.SwaggerConstants;
import com.sahaj.banking.exception.BankingSolutionCommonException;
import com.sahaj.banking.request.AccountCreationRequest;
import com.sahaj.banking.request.AccountInformationRequest;
import com.sahaj.banking.response.AccountInformationResponse;
import com.sahaj.banking.service.IAccountInformationSerive;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author vivek
 * This class have all the api related to account information
 */
@RestController
@RequestMapping("/v1/account")
public class AccountDetailsController {

	@Autowired
	IAccountInformationSerive iAccountInformationSerive;

	/**
	 * This method is used to get the user details getUserDetails
	 * 
	 * @throws BankingSolutionCommonException
	 * 
	 */
	@ApiOperation(value = SwaggerConstants.ApiOperations.ACCOUNTDETAILS.GET_ACCOUNT_INFO)
	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = Constants.SUCCESS, response = AccountInformationResponse.class),
			@ApiResponse(code = 403, message = Constants.FORBIDDEN),
			@ApiResponse(code = 422, message = Constants.NOT_FOUND),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseResources<AccountInformationResponse> createAccount(
			@RequestBody AccountCreationRequest accountCreationRequest) throws BankingSolutionCommonException {
		AccountInformationResponse response = iAccountInformationSerive.createAccount(accountCreationRequest);
		return new ResponseResources<>(ResponseResources.R_CODE_OK, ResponseResources.RES_SUCCESS, response,
				Status.SUCCESS);

	}

	/**
	 * This method is used to get account information
	 * 
	 * @throws BankingSolutionCommonException
	 */
	@ApiOperation(value = SwaggerConstants.ApiOperations.ACCOUNTDETAILS.GET_ACCOUNT_INFO)
	@PostMapping(path = "/details", consumes = "application/json", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = Constants.SUCCESS, response = AccountInformationResponse.class),
			@ApiResponse(code = 403, message = Constants.FORBIDDEN),
			@ApiResponse(code = 422, message = Constants.NOT_FOUND),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseResources<AccountInformationResponse> getAccountInformation(
			@RequestBody AccountInformationRequest accountInformationRequest) throws BankingSolutionCommonException {
		AccountInformationResponse response = iAccountInformationSerive
				.getAccountInformation(accountInformationRequest);
		return new ResponseResources<>(ResponseResources.R_CODE_OK, ResponseResources.RES_SUCCESS, response,
				Status.SUCCESS);

	}

}
