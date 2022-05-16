package com.sahaj.banking.controller;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.sahaj.banking.beans.ResponseResources;
import com.sahaj.banking.beans.Status;
import com.sahaj.banking.controller.v1.AccountDetailsController;
import com.sahaj.banking.controller.v1.DepositController;
import com.sahaj.banking.exception.BankingSolutionCommonException;
import com.sahaj.banking.request.AccountCreationRequest;
import com.sahaj.banking.request.DepositRequest;
import com.sahaj.banking.response.AccountInformationResponse;

/**
 * @author vivek 
 * This has all the positive and negative test cased for deposit
 *         activity
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration
public class DepositControllerTest {

	@Autowired
	private DepositController depositController;

	@Autowired
	private AccountDetailsController accountDetailsController;

	@Value("${maximum.deposit.perday}")
	Integer maximumDepositAmountPerDay;

	@Before
	public void init() throws BankingSolutionCommonException {
		AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
		accountCreationRequest.setAccountHolderFullName("Steve Martine");
		accountDetailsController.createAccount(accountCreationRequest);

	}

	@Test
	public void testDepositAmount() throws BankingSolutionCommonException {
		DepositRequest depositRequest = new DepositRequest();
		depositRequest.setAccountNumber("1001");
		depositRequest.setDepositAmount(new BigDecimal(5000));
		ResponseResources<AccountInformationResponse> response = depositController.depositAmount(depositRequest);
		assertTrue(response != null && response.getStatus() == Status.SUCCESS
				&& response.getData().getAccountBalance() != null);
	}

	@Test
	public void testDepositAmountLessThatMinimumThrushhold() throws BankingSolutionCommonException {
		DepositRequest depositRequest = new DepositRequest();
		depositRequest.setAccountNumber("1001");
		depositRequest.setDepositAmount(new BigDecimal(300));
		ResponseResources<AccountInformationResponse> response = depositController.depositAmount(depositRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Minimum deposit amount is 5000"));
	}

	@Test
	public void testDepositNegativeAmount() throws BankingSolutionCommonException {
		DepositRequest depositRequest = new DepositRequest();
		depositRequest.setAccountNumber("1001");
		depositRequest.setDepositAmount(new BigDecimal(-300));
		ResponseResources<AccountInformationResponse> response = depositController.depositAmount(depositRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Minimum deposit amount is 5000"));
	}

	@Test
	public void testDepositMaximumAmountThrushHold() throws BankingSolutionCommonException {
		DepositRequest firstDepositRequestepositRequest = new DepositRequest();
		firstDepositRequestepositRequest.setAccountNumber("1001");
		firstDepositRequestepositRequest.setDepositAmount(new BigDecimal(5000));
		depositController.depositAmount(firstDepositRequestepositRequest);

		DepositRequest secondDepositRequest = new DepositRequest();
		secondDepositRequest.setAccountNumber("1001");
		secondDepositRequest.setDepositAmount(new BigDecimal(5000));
		depositController.depositAmount(secondDepositRequest);

		DepositRequest maxLimitDepositRequest = new DepositRequest();
		maxLimitDepositRequest.setAccountNumber("1001");
		maxLimitDepositRequest.setDepositAmount(new BigDecimal(5000));
		depositController.depositAmount(maxLimitDepositRequest);

		maxLimitDepositRequest.setAccountNumber("1001");
		maxLimitDepositRequest.setDepositAmount(new BigDecimal(5000));
		ResponseResources<AccountInformationResponse> response = depositController
				.depositAmount(maxLimitDepositRequest);

		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Only 3 deposits are allowed in a day"));
	}

	@Test
	public void testDepositMaximumAllowedLimit() throws BankingSolutionCommonException {
		DepositRequest maxLimitDepositRequest = new DepositRequest();
		maxLimitDepositRequest.setAccountNumber("1001");
		maxLimitDepositRequest.setDepositAmount(new BigDecimal(200000));
		ResponseResources<AccountInformationResponse> response = depositController
				.depositAmount(maxLimitDepositRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Account balance cannot exceed 100000"));
	}

	@Test
	public void testDepositWithEmptyRequest() throws BankingSolutionCommonException {
		DepositRequest depositRequest = new DepositRequest();
		ResponseResources<AccountInformationResponse> response = depositController.depositAmount(depositRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Request cannot be Empty"));
	}

	@Test
	public void testDepositWithEmptyAccountNumber() throws BankingSolutionCommonException {
		DepositRequest depositRequest = new DepositRequest();
		depositRequest.setDepositAmount(new BigDecimal(5000));
		ResponseResources<AccountInformationResponse> response = depositController.depositAmount(depositRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Account Not exists"));
	}

	@Test
	public void testDepositWithNullBalance() throws BankingSolutionCommonException {
		DepositRequest depositRequest = new DepositRequest();
		depositRequest.setAccountNumber("1001");
		ResponseResources<AccountInformationResponse> response = depositController.depositAmount(depositRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Deposit Amount cannot be Empty"));
	}

}