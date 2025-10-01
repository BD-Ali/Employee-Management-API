package com.api.employeemanagementapi.service;

import com.api.employeemanagementapi.entity.Employee;
import com.api.employeemanagementapi.repository.EmployeeRepository;
import com.api.employeemanagementapi.shared.CustomResponseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repo;

    public EmployeeServiceImpl(EmployeeRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Employee> all() {
        return repo.findAll();
    }

    @Override
    public Employee get(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                        "Employee with id " + id + " not found"));
    }

    @Override
    public Employee create(Employee employee) {
        validateEmployee(employee);
        validateUniqueConstraints(employee);
        employee.setId(UUID.randomUUID());
        return repo.save(employee);
    }

    @Override
    public Employee update(UUID id, Employee employee) {
        Employee existing = get(id);
        validateUpdateFields(employee);
        validateUniqueConstraintsForUpdate(existing, employee);

        updateEmployeeFields(existing, employee);
        return repo.save(existing);
    }

    @Override
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw CustomResponseException.ResourceNotFound("Employee with id " + id + " not found");
        }
        repo.deleteById(id);
    }

    private void validateEmployee(Employee employee) {
        if (isNullOrBlank(employee.getFirstName())) {
            throw CustomResponseException.BadRequest("First name cannot be null or blank");
        }
        if (isNullOrBlank(employee.getLastName())) {
            throw CustomResponseException.BadRequest("Last name cannot be null or blank");
        }
        if (isNullOrBlank(employee.getEmail())) {
            throw CustomResponseException.BadRequest("Email cannot be null or blank");
        }
        if (isNullOrBlank(employee.getPosition())) {
            throw CustomResponseException.BadRequest("Position cannot be null or blank");
        }
        if (isNullOrBlank(employee.getPhoneNumber())) {
            throw CustomResponseException.BadRequest("Phone number cannot be null or blank");
        }
        if (employee.getSalary() == null || employee.getSalary().doubleValue() < 0) {
            throw CustomResponseException.BadRequest("Salary must be non-negative");
        }
        if (employee.getHireDate() != null && employee.getHireDate().isAfter(java.time.LocalDate.now())) {
            throw CustomResponseException.BadRequest("Hire date cannot be in the future");
        }
    }

    private void validateUpdateFields(Employee employee) {
        if (isNullOrBlank(employee.getEmail())) {
            throw CustomResponseException.BadRequest("Email cannot be null or blank");
        }
        if (isNullOrBlank(employee.getPosition())) {
            throw CustomResponseException.BadRequest("Position cannot be null or blank");
        }
        if (isNullOrBlank(employee.getPhoneNumber())) {
            throw CustomResponseException.BadRequest("Phone number cannot be null or blank");
        }
        if (employee.getSalary() == null || employee.getSalary().doubleValue() < 0) {
            throw CustomResponseException.BadRequest("Salary must be non-negative");
        }
    }

    private void validateUniqueConstraints(Employee employee) {
        if (repo.existsByEmail(employee.getEmail())) {
            throw CustomResponseException.Conflict("Email " + employee.getEmail() + " is already in use");
        }
        if (repo.existsByPhoneNumber(employee.getPhoneNumber())) {
            throw CustomResponseException.Conflict("Phone number " + employee.getPhoneNumber() + " is already in use");
        }
    }

    private void validateUniqueConstraintsForUpdate(Employee existing, Employee updated) {
        if (!existing.getEmail().equals(updated.getEmail()) && repo.existsByEmail(updated.getEmail())) {
            throw CustomResponseException.Conflict("Email " + updated.getEmail() + " is already in use");
        }
        if (!existing.getPhoneNumber().equals(updated.getPhoneNumber()) &&
            repo.existsByPhoneNumber(updated.getPhoneNumber())) {
            throw CustomResponseException.Conflict("Phone number " + updated.getPhoneNumber() + " is already in use");
        }
    }

    private void updateEmployeeFields(Employee existing, Employee updated) {
        existing.setEmail(updated.getEmail());
        existing.setPosition(updated.getPosition());
        existing.setSalary(updated.getSalary());
        existing.setPhoneNumber(updated.getPhoneNumber());
    }

    private boolean isNullOrBlank(String str) {
        return str == null || str.isBlank();
    }
}
