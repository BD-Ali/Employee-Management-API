package com.api.employeemanagementapi.repository;

import com.api.employeemanagementapi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);
}
