package com.api.employeemanagementapi.service;

import com.api.employeemanagementapi.entity.Employee;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    List<Employee> all();
    Employee get(UUID id);
    Employee create(Employee p);
    Employee update(UUID id, Employee p);
    void delete(UUID id);
}
