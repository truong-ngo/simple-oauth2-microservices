package com.example.springauth.repository;

import com.example.springauth.domain.RoleDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleDomain, Long> {
}
