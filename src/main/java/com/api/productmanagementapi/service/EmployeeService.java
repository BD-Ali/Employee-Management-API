package com.api.productmanagementapi.service;

import com.api.productmanagementapi.entity.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> all();
    Employee get(Long id);
    Employee create(Employee p);
    Employee update(Long id, Employee p);
    void delete(Long id);
}
