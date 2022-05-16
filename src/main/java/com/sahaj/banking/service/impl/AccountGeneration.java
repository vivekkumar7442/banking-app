package com.sahaj.banking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sahaj.banking.entity.AccountSequence;
import com.sahaj.banking.repository.AccountGenerationRepository;
import com.sahaj.banking.service.IAccountGenerationService;

/**
 * @author vivek
 * 
 *  AccountGeneration implemenation class
 *
 */
@Component
public class AccountGeneration implements IAccountGenerationService {

	@Autowired
	private AccountGenerationRepository accountGenerationRepository;

	@Value("${initial.account.sequence}")
	Long initialSequence;

	@Override
	public String getAccounNumber() {

		synchronized (AccountGeneration.class) {
			AccountSequence accountSequence = accountGenerationRepository.findFirstByOrderBySequenceDesc();
			if (accountSequence != null) {
				Long accountNumber = accountSequence.getSequence() + 1L;
				accountSequence.setSequence(accountNumber);
				accountGenerationRepository.save(accountSequence);
				return String.valueOf(accountNumber);

			} else {
				Long accountNumber = initialSequence;

				AccountSequence accountSequenceInitialization = new AccountSequence();
				accountNumber = accountNumber + 1L;

				accountSequenceInitialization.setSequence(accountNumber);
				accountGenerationRepository.save(accountSequenceInitialization);

				return String.valueOf(accountNumber);

			}

		}
	}

}
