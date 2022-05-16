package com.sahaj.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahaj.banking.entity.AccountSequence;

@Repository
public interface AccountGenerationRepository extends JpaRepository<AccountSequence, Long> {

	AccountSequence findFirstByOrderBySequenceDesc();

}
