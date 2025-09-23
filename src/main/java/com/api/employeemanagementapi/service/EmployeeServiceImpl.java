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
                        "Product with id " + id + " not found"));
    }

    @Override
    public Employee create(Employee p) {
        if (p.getName() == null || p.getName().isBlank()) {
            throw CustomResponseException.BadRequest("Name cannot be null or blank");
        }
        if (p.getPrice() == null) {
            throw CustomResponseException.BadRequest("Price cannot be null");
        }
        if (p.getPrice().doubleValue() < 0) {
            throw CustomResponseException.BadRequest("Price cannot be negative");
        }
        if (p.getQuantity() == null) {
            throw CustomResponseException.BadRequest("Quantity cannot be null");
        }
        if (p.getQuantity() < 0) {
            throw CustomResponseException.BadRequest("Quantity cannot be negative");
        }

        return repo.save(p);
    }

    @Override
    public Employee update(Long id, Employee p) {
        Employee existing = get(id);

        if (p.getName() == null || p.getName().isBlank()) {
            throw CustomResponseException.BadRequest("Name cannot be null or blank");
        }
        existing.setName(p.getName());

        if (p.getPrice() == null) {
            throw CustomResponseException.BadRequest("Price cannot be null");
        }
        if (p.getPrice().doubleValue() < 0) {
            throw CustomResponseException.BadRequest("Price cannot be negative");
        }
        existing.setPrice(p.getPrice());

        if (p.getQuantity() == null) {
            throw CustomResponseException.BadRequest("Quantity cannot be null");
        }
        if (p.getQuantity() < 0) {
            throw CustomResponseException.BadRequest("Quantity cannot be negative");
        }
        existing.setQuantity(p.getQuantity());

        return repo.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw CustomResponseException.ResourceNotFound("Product with id " + id + " not found");        }
        repo.deleteById(id);
    }
}
