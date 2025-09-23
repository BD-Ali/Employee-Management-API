package com.api.employeemanagementapi.service;

import com.api.employeemanagementapi.entity.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> all();
    Employee get(Long id);
    Employee create(Employee p);
    Employee update(Long id, Employee p);
    void delete(Long id);
}
