package com.api.employeemanagementapi.repository;

import com.api.employeemanagementapi.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface  UserAccountRepository extends JpaRepository<UserAccount, UUID> {
    boolean existsByUsername(String username);

    UserAccount findByUsername(String username);
}
