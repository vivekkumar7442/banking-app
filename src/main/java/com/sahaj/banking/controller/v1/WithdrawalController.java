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
import com.sahaj.banking.request.WithdrawalRequest;
import com.sahaj.banking.response.WithdrawalResponse;
import com.sahaj.banking.service.IWithdrawalService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * 
 * @author vivek
 * This class have all the api related to withdrawal information
 */
@RestController
@RequestMapping("/v1/transaction")
public class WithdrawalController {

	@Autowired
	private IWithdrawalService iWithdrawalService;

	/**
	 * This method is used to get account information
	 */
	@ApiOperation(value = SwaggerConstants.ApiOperations.ACCOUNTDETAILS.GET_ACCOUNT_INFO)
	@PostMapping(path = "/withdraw", consumes = "application/json", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = Constants.SUCCESS, response = WithdrawalResponse.class),
			@ApiResponse(code = 403, message = Constants.FORBIDDEN),
			@ApiResponse(code = 422, message = Constants.NOT_FOUND),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseResources<WithdrawalResponse> withdrawAmount(
			@RequestBody WithdrawalRequest withdrawalRequest)
			throws BankingSolutionCommonException {
		WithdrawalResponse response = iWithdrawalService.withdrawAmount(withdrawalRequest);
		return new ResponseResources<>(ResponseResources.R_CODE_OK, ResponseResources.RES_SUCCESS, response,
				Status.SUCCESS);

	}

}
