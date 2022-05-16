package com.sahaj.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahaj.banking.entity.User;

@Repository 
public interface UserRepository extends JpaRepository<User,Long>{

}
