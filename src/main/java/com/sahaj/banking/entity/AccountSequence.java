package com.sahaj.banking.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * 
 * @author vivek
 * This class is used to store the unique account
 */

@Entity
@Table(name = "account_sequence")
@NamedQuery(name = "AccountSequence.findAll", query = "SELECT a FROM AccountSequence a")
public class AccountSequence implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "sequence_id")
	private Long sequenceId;

	private Long sequence;

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

}
