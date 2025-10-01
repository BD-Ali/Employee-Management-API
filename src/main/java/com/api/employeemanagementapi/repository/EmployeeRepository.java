package com.api.employeemanagementapi.repository;

import com.api.employeemanagementapi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);
}
