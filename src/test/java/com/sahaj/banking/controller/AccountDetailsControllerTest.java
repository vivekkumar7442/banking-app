package com.sahaj.banking.controller;

import static org.junit.Assert.assertTrue;

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
import com.sahaj.banking.exception.BankingSolutionCommonException;
import com.sahaj.banking.request.AccountCreationRequest;
import com.sahaj.banking.request.AccountInformationRequest;
import com.sahaj.banking.response.AccountInformationResponse;

/**
 * @author vivek
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration
public class AccountDetailsControllerTest {

	@Autowired
	private AccountDetailsController accountDetailsController;

	@Before
	public void init() throws BankingSolutionCommonException {
		AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
		accountCreationRequest.setAccountHolderFullName("Steve Martine");
		accountDetailsController.createAccount(accountCreationRequest);
	}

	@Test
	public void testAccountCreationWithFullName() throws BankingSolutionCommonException {
		AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
		accountCreationRequest.setAccountHolderFullName("Steve Rogers");
		ResponseResources<AccountInformationResponse> response = accountDetailsController
				.createAccount(accountCreationRequest);
		assertTrue(response != null && response.getStatus() == Status.SUCCESS
				&& response.getData().getAccountNumber() != null);
	}

	@Test
	public void testAccountCreationWithEmptyRequest() throws BankingSolutionCommonException {
		AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
		ResponseResources<AccountInformationResponse> response = accountDetailsController
				.createAccount(accountCreationRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Account Name cannot be Empty"));
	}

	@Test
	public void testAccountCreationWithNullRequest() throws BankingSolutionCommonException {
		ResponseResources<AccountInformationResponse> response = accountDetailsController.createAccount(null);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Request cannot be null"));
	}

	@Test
	public void testAccountInformation() throws BankingSolutionCommonException {
		AccountInformationRequest accountInformationRequest = new AccountInformationRequest();
		accountInformationRequest.setAccountNumber("1001");
		ResponseResources<AccountInformationResponse> response = accountDetailsController
				.getAccountInformation(accountInformationRequest);
		assertTrue(response != null && response.getStatus() == Status.SUCCESS);
	}

	@Test
	public void testAccountWithInvalidAccountNumber() throws BankingSolutionCommonException {
		AccountInformationRequest accountInformationRequest = new AccountInformationRequest();
		accountInformationRequest.setAccountNumber("INVALD100");
		ResponseResources<AccountInformationResponse> response = accountDetailsController
				.getAccountInformation(accountInformationRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Account Not exists"));
	}

	@Test
	public void testAccountInformationWithEmptyRequest() throws BankingSolutionCommonException {
		AccountInformationRequest accountInformationRequest = new AccountInformationRequest();
		ResponseResources<AccountInformationResponse> response = accountDetailsController
				.getAccountInformation(accountInformationRequest);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Account Number cannot be Empty"));
	}

	@Test
	public void testAccountInformationWithNullRequest() throws BankingSolutionCommonException {
		ResponseResources<AccountInformationResponse> response = accountDetailsController.getAccountInformation(null);
		assertTrue(response != null && response.getStatus() == Status.FAILURE
				&& response.getMessage().equalsIgnoreCase("Request cannot be null"));
	}

}