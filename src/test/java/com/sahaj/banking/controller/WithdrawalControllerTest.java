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
import com.sahaj.banking.controller.v1.WithdrawalController;
import com.sahaj.banking.exception.BankingSolutionCommonException;
import com.sahaj.banking.request.AccountCreationRequest;
import com.sahaj.banking.request.DepositRequest;
import com.sahaj.banking.request.WithdrawalRequest;
import com.sahaj.banking.response.WithdrawalResponse;

/**
 * @author vivek
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration
public class WithdrawalControllerTest {

	@Autowired
	private WithdrawalController withdrawalController;

	@Autowired
	private AccountDetailsController accountDetailsController;

	@Autowired
	private DepositController depositController;

	@Value("${maximum.deposit.perday}")
	Integer maximumDepositAmountPerDay;

	@Before
	public void init() throws BankingSolutionCommonException {
		AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
		accountCreationRequest.setAccountHolderFullName("Steve Martine");
		accountDetailsController.createAccount(accountCreationRequest);
		DepositRequest depositRequest = new DepositRequest();
		depositRequest.setAccountNumber("1001");
		depositRequest.setDepositAmount(new BigDecimal(25000));
		depositController.depositAmount(depositRequest);

	}

	@Test
	public void testWithdrawalRequest() throws BankingSolutionCommonException {
		WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
		withdrawalRequest.setAccountNumber("1001");
		withdrawalRequest.setWithdrawalAmount(new BigDecimal(1000));
		ResponseResources<WithdrawalResponse> response = withdrawalController.withdrawAmount(withdrawalRequest);
		assertTrue(response != null && response.getStatus() == Status.SUCCESS
				&& response.getData().getAccountBalance() != null);
	}

	@Test
	public void testDepositAmountLessThatMinimumThrushhold() throws BankingSolutionCommonException {
		WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
		withdrawalRequest.setAccountNumber("1001");
		withdrawalRequest.setWithdrawalAmount(new BigDecimal(300));
		ResponseResources<WithdrawalResponse> response = withdrawalController.withdrawAmount(withdrawalRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Minimum withdrawal amount is 1000"));
	}

	@Test
	public void testDepositNegativeAmount() throws BankingSolutionCommonException {
		WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
		withdrawalRequest.setAccountNumber("1001");
		withdrawalRequest.setWithdrawalAmount(new BigDecimal(-300));
		ResponseResources<WithdrawalResponse> response = withdrawalController.withdrawAmount(withdrawalRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Minimum withdrawal amount is 1000"));
	}

	@Test
	public void testDepositMaximumAmountThrushHold() throws BankingSolutionCommonException {
		WithdrawalRequest firstwithdrawalRequest = new WithdrawalRequest();
		firstwithdrawalRequest.setAccountNumber("1001");
		firstwithdrawalRequest.setWithdrawalAmount(new BigDecimal(1000));
		withdrawalController.withdrawAmount(firstwithdrawalRequest);

		WithdrawalRequest secondwithdrawalRequest = new WithdrawalRequest();
		secondwithdrawalRequest.setAccountNumber("1001");
		secondwithdrawalRequest.setWithdrawalAmount(new BigDecimal(1000));
		withdrawalController.withdrawAmount(secondwithdrawalRequest);

		WithdrawalRequest maxLimitwithdrawalRequest = new WithdrawalRequest();
		maxLimitwithdrawalRequest.setAccountNumber("1001");
		maxLimitwithdrawalRequest.setWithdrawalAmount(new BigDecimal(1000));
		withdrawalController.withdrawAmount(maxLimitwithdrawalRequest);

		ResponseResources<WithdrawalResponse> response = withdrawalController.withdrawAmount(maxLimitwithdrawalRequest);

		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Only 3 withdrawals are allowed in a day"));
	}

	@Test
	public void testMaximumWithdrawalAmount() throws BankingSolutionCommonException {
		WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
		withdrawalRequest.setAccountNumber("1001");
		withdrawalRequest.setWithdrawalAmount(new BigDecimal(Integer.MAX_VALUE));
		ResponseResources<WithdrawalResponse> response = withdrawalController.withdrawAmount(withdrawalRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Maximum withdrawal amount is 25000"));
	}

	@Test
	public void testWithdrawalWithEmptyRequest() throws BankingSolutionCommonException {
		WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
		ResponseResources<WithdrawalResponse> response = withdrawalController.withdrawAmount(withdrawalRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Request cannot be Empty"));
	}

	@Test
	public void testWithdrawalWithEmptyAccountNumber() throws BankingSolutionCommonException {
		WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
		withdrawalRequest.setWithdrawalAmount(new BigDecimal(5000));
		ResponseResources<WithdrawalResponse> response = withdrawalController.withdrawAmount(withdrawalRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Withdrawal Amount cannot be Empty"));
	}

	@Test
	public void testWithdrawalWithNullBalance() throws BankingSolutionCommonException {
		WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
		withdrawalRequest.setAccountNumber("1001");
		ResponseResources<WithdrawalResponse> response = withdrawalController.withdrawAmount(withdrawalRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Withdrawal Amount cannot be Empty"));
	}

}