package com.example.springauth.repository;

import com.example.springauth.domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<UserDomain, Long>, JpaSpecificationExecutor<UserDomain> {

}
