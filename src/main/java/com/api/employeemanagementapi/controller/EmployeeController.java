package com.api.employeemanagementapi.controller;

import com.api.employeemanagementapi.dtos.EmployeeCreate;
import com.api.employeemanagementapi.dtos.EmployeePatch;
import com.api.employeemanagementapi.dtos.EmployeeUpdate;
import com.api.employeemanagementapi.entity.Employee;
import com.api.employeemanagementapi.service.EmployeeService;
import com.api.employeemanagementapi.shared.GlobalResponse;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public GlobalResponse<List<Employee>> all() {
        return GlobalResponse.success(service.all());
    }

    @GetMapping("/{id}")
    public GlobalResponse<Employee> get(@PathVariable UUID id) {
        return GlobalResponse.success(service.get(id));
    }

    @PostMapping
    public GlobalResponse<Employee> create(@Valid @RequestBody EmployeeCreate req) {
        Employee toSave = req.toEntity();
        Employee saved = service.create(toSave);

        return GlobalResponse.success(saved);
    }

    @PutMapping("/{id}")
    public GlobalResponse<Employee> update(@PathVariable UUID id,
                                           @Valid @RequestBody EmployeeUpdate req) {
        Employee toUpdate = new Employee();
        req.applyTo(toUpdate);
        Employee updated = service.update(id, toUpdate);

        return GlobalResponse.success(updated);
    }

    @PatchMapping("/{id}")
    public GlobalResponse<Employee> patch(@PathVariable UUID id,
                                          @Valid @RequestBody EmployeePatch req) {
        Employee existing = service.get(id);
        req.applyPartially(existing);
        Employee updated = service.update(id, existing);

        return GlobalResponse.success(updated);
    }

    @DeleteMapping("/{id}")
    public GlobalResponse<Map<String, String>> delete(@PathVariable UUID id) {
        service.delete(id);

        return GlobalResponse.successMessage("Employee deleted");
    }
}
