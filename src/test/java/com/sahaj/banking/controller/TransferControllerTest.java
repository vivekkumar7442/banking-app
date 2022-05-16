package com.sahaj.banking.controller;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.sahaj.banking.beans.ResponseResources;
import com.sahaj.banking.beans.Status;
import com.sahaj.banking.controller.v1.AccountDetailsController;
import com.sahaj.banking.controller.v1.DepositController;
import com.sahaj.banking.controller.v1.TransferController;
import com.sahaj.banking.exception.BankingSolutionCommonException;
import com.sahaj.banking.request.AccountCreationRequest;
import com.sahaj.banking.request.DepositRequest;
import com.sahaj.banking.request.TransferRequest;
import com.sahaj.banking.response.TransferResponse;

/**
 * @author vivek
 * This has all the positive and negative test cased for Transfer
 *         activity
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration
public class TransferControllerTest {

	@Autowired
	private DepositController depositController;

	@Autowired
	private TransferController transferController;

	@Autowired
	private AccountDetailsController accountDetailsController;

	@Before
	public void init() throws BankingSolutionCommonException {

		AccountCreationRequest sourceAccount = new AccountCreationRequest();
		sourceAccount.setAccountHolderFullName("Steve Martine");
		accountDetailsController.createAccount(sourceAccount);

		AccountCreationRequest destinationAccountRequest = new AccountCreationRequest();
		destinationAccountRequest.setAccountHolderFullName("Steve John");
		accountDetailsController.createAccount(destinationAccountRequest);

		DepositRequest depositRequest = new DepositRequest();
		depositRequest.setAccountNumber("1001");
		depositRequest.setDepositAmount(new BigDecimal(5000));
		depositController.depositAmount(depositRequest);

		DepositRequest depositTodestinationAccount = new DepositRequest();
		depositTodestinationAccount.setAccountNumber("1002");
		depositTodestinationAccount.setDepositAmount(new BigDecimal(5000));
		depositController.depositAmount(depositTodestinationAccount);
	}

	@Test
	public void testTransferAmount() throws BankingSolutionCommonException {
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setSourceAccountNumber("1001");
		transferRequest.setDestinationAccountNumber("1002");
		transferRequest.setTransferAmount(new BigDecimal(1000));
		ResponseResources<TransferResponse> response = transferController.transferAmount(transferRequest);
		assertTrue(response != null && response.getStatus() == Status.SUCCESS
				&& response.getData().getAccountBalance() != null);
	}

	@Test
	public void testTransferAmountLessThatMinimumThrushhold() throws BankingSolutionCommonException {
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setSourceAccountNumber("1001");
		transferRequest.setDestinationAccountNumber("1002");
		transferRequest.setTransferAmount(new BigDecimal(500));
		ResponseResources<TransferResponse> response = transferController.transferAmount(transferRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Minimum withdrawal amount is 1000"));
	}

	@Test
	public void testTransferNegativeAmount() throws BankingSolutionCommonException {
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setSourceAccountNumber("1001");
		transferRequest.setDestinationAccountNumber("1002");
		transferRequest.setTransferAmount(new BigDecimal(-300));
		ResponseResources<TransferResponse> response = transferController.transferAmount(transferRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Minimum withdrawal amount is 1000"));
	}

	@Test
	public void testTransferMaximumAmountThrushHold() throws BankingSolutionCommonException {
		TransferRequest firstTransferRequest = new TransferRequest();
		firstTransferRequest.setSourceAccountNumber("1001");
		firstTransferRequest.setDestinationAccountNumber("1002");
		firstTransferRequest.setTransferAmount(new BigDecimal(1000));
		transferController.transferAmount(firstTransferRequest);

		TransferRequest secondTransferRequest = new TransferRequest();
		secondTransferRequest.setSourceAccountNumber("1001");
		secondTransferRequest.setDestinationAccountNumber("1002");
		firstTransferRequest.setTransferAmount(new BigDecimal(1000));

		transferController.transferAmount(secondTransferRequest);

		TransferRequest maxLimitTransferRequest = new TransferRequest();
		maxLimitTransferRequest.setSourceAccountNumber("1001");
		maxLimitTransferRequest.setDestinationAccountNumber("1002");
		maxLimitTransferRequest.setTransferAmount(new BigDecimal(1000));
		ResponseResources<TransferResponse> response = transferController.transferAmount(maxLimitTransferRequest);

		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Only 3 withdrawal are allowed in a day"));
	}

	@Test
	public void testTransferMaximumAllowedLimit() throws BankingSolutionCommonException {
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setSourceAccountNumber("1001");
		transferRequest.setDestinationAccountNumber("1002");
		transferRequest.setTransferAmount(new BigDecimal(200000));
		ResponseResources<TransferResponse> response = transferController.transferAmount(transferRequest);

		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Maximum withdrawal amount is 25000"));
	}

	@Test
	public void testTransferWithEmptyRequest() throws BankingSolutionCommonException {
		TransferRequest transferRequest = new TransferRequest();
		ResponseResources<TransferResponse> response = transferController.transferAmount(transferRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Source Account Number cannot be Empty"));
	}

	@Test
	public void testTransferWithEmptySourceAccountNumber() throws BankingSolutionCommonException {
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setSourceAccountNumber("1001");
		transferRequest.setTransferAmount(new BigDecimal(1000));
		ResponseResources<TransferResponse> response = transferController.transferAmount(transferRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Source Account Number cannot be Empty"));
	}

	@Test
	public void testTransferWithEmptyDestinationAccountNumber() throws BankingSolutionCommonException {
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setSourceAccountNumber("1001");
		transferRequest.setTransferAmount(new BigDecimal(1000));
		ResponseResources<TransferResponse> response = transferController.transferAmount(transferRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Destination Account Number cannot be Empty"));
	}

	@Test
	public void testTransferWithNullAmount() throws BankingSolutionCommonException {
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setSourceAccountNumber("1001");
		transferRequest.setDestinationAccountNumber("1002");
		ResponseResources<TransferResponse> response = transferController.transferAmount(transferRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("ransfer Amount Cannot be Empty"));
	}

	@Test
	public void testTransferWithNullRequest() throws BankingSolutionCommonException {
		ResponseResources<TransferResponse> response = transferController.transferAmount(null);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Request cannot be null"));
	}

}