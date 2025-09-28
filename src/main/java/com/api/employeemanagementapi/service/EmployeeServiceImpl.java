package com.api.employeemanagementapi.service;

import com.api.employeemanagementapi.entity.Employee;
import com.api.employeemanagementapi.repository.EmployeeRepository;
import com.api.employeemanagementapi.shared.CustomResponseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

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
    public Employee get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                        "Employee with id " + id + " not found"));
    }

    @Override
    public Employee create(Employee p) {
        if (p.getFirstName() == null || p.getFirstName().isBlank()) {
            throw CustomResponseException.BadRequest("Name cannot be null or blank");
        }
        if (p.getLastName() == null || p.getLastName().isBlank()) {
            throw CustomResponseException.BadRequest("Name cannot be null or blank");
        }
        if (p.getEmail() == null || p.getEmail().isBlank()) {
            throw CustomResponseException.BadRequest("Email cannot be null or blank");
        }
        if (p.getPosition() == null || p.getPosition().isBlank()) {
            throw CustomResponseException.BadRequest("Position cannot be null or blank");
        }
        if (p.getSalary() == null) {
            throw CustomResponseException.BadRequest("Salary cannot be null");
        }
        if (p.getSalary().doubleValue() < 0) {
            throw CustomResponseException.BadRequest("Salary cannot be negative");
        }
        if (p.getHireDate().isAfter(java.time.LocalDate.now())) {
            throw CustomResponseException.BadRequest("Hire date cannot be in the future");
        }
        if (p.getPhoneNumber() == null || p.getPhoneNumber().isBlank()) {
            throw CustomResponseException.BadRequest("Phone number cannot be null or blank");
        }
        if (repo.existsByEmail(p.getEmail())) {
            throw CustomResponseException.Conflict("Email " + p.getEmail() + " is already in use");
        }
        if (repo.existsByPhoneNumber(p.getPhoneNumber())) {
            throw CustomResponseException.Conflict("Phone number " + p.getPhoneNumber() + " is already in use");
        }



        return repo.save(p);
    }

    @Override
    public Employee update(Long id, Employee p) {
        Employee existing = get(id);

        if (p.getEmail() == null || p.getEmail().isBlank()) {
            throw CustomResponseException.BadRequest("Email cannot be null or blank");
        }
        if (p.getPosition() == null || p.getPosition().isBlank()) {
            throw CustomResponseException.BadRequest("Position cannot be null or blank");
        }
        if (p.getSalary() == null) {
            throw CustomResponseException.BadRequest("Salary cannot b3e null");
        }
        if (p.getSalary().doubleValue() < 0) {
            throw CustomResponseException.BadRequest("Salary cannot be negative");
        }
        if (p.getPhoneNumber() == null || p.getPhoneNumber().isBlank()) {
            throw CustomResponseException.BadRequest("Phone number cannot be null or blank");
        }

        existing.setEmail(p.getEmail());
        existing.setPosition(p.getPosition());
        existing.setSalary(p.getSalary());
        existing.setPhoneNumber(p.getPhoneNumber());

        return repo.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw CustomResponseException.ResourceNotFound("Employee with id " + id + " not found");        }
        repo.deleteById(id);
    }
}
