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
import com.sahaj.banking.request.TransferRequest;
import com.sahaj.banking.response.TransferResponse;
import com.sahaj.banking.service.ITransferService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * 
 * @author vivek
 * This class have all the api related to transfer information
 */
@RestController
@RequestMapping("/v1/transfer")
public class TransferController {

	@Autowired
	private ITransferService iTransferService;

	/**
	 * This method is used to transfer amount to respective account
	 */
	@ApiOperation(value = SwaggerConstants.ApiOperations.ACCOUNTDETAILS.GET_ACCOUNT_INFO)
	@PostMapping(consumes = "application/json", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.SUCCESS, response = TransferResponse.class),
			@ApiResponse(code = 403, message = Constants.FORBIDDEN),
			@ApiResponse(code = 422, message = Constants.NOT_FOUND),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseResources<TransferResponse> transferAmount(@RequestBody TransferRequest transferRequest)
			throws BankingSolutionCommonException {
		TransferResponse response = iTransferService.transferAmount(transferRequest);
		return new ResponseResources<>(ResponseResources.R_CODE_OK, ResponseResources.RES_SUCCESS, response,
				Status.SUCCESS);

	}

}
