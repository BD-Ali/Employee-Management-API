package com.api.employeemanagementapi.service;

import com.api.employeemanagementapi.entity.Employee;
import com.api.employeemanagementapi.entity.UserAccount;
import com.api.employeemanagementapi.repository.UserAccountRepository;
import com.api.employeemanagementapi.shared.CustomResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserAccount> getAllUsers() {
        return userAccountRepository.findAll();
    }

    public UserAccount getUserById(UUID id) {
        return userAccountRepository.findById(id)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                        "User account with id " + id + " not found"));
    }

    public UserAccount createUser(UserAccount userAccount, Employee employee) {
        if (userAccountRepository.existsByUsername(userAccount.getUsername())) {
            throw CustomResponseException.Conflict("Username already exists");
        }

        userAccount.setId(UUID.randomUUID());
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        userAccount.setEmployee(employee);

        if (userAccount.getRole() == null || userAccount.getRole().isEmpty()) {
            userAccount.setRole("USER");
        }

        return userAccountRepository.save(userAccount);
    }

    public UserAccount updateUser(UUID id, UserAccount userAccount) {
        UserAccount existingUser = getUserById(id);

        if (!existingUser.getUsername().equals(userAccount.getUsername()) &&
                userAccountRepository.existsByUsername(userAccount.getUsername())) {
            throw CustomResponseException.Conflict("Username already exists");
        }

        existingUser.setUsername(userAccount.getUsername());
        if (userAccount.getPassword() != null && !userAccount.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        }
        if (userAccount.getRole() != null && !userAccount.getRole().isEmpty()) {
            existingUser.setRole(userAccount.getRole());
        }

        return userAccountRepository.save(existingUser);
    }

    public void deleteUser(UUID id) {
        if (!userAccountRepository.existsById(id)) {
            throw CustomResponseException.ResourceNotFound("User account with id " + id + " not found");
        }
        userAccountRepository.deleteById(id);
    }

    public UserAccount findByUsername(String username) {
        UserAccount user = userAccountRepository.findByUsername(username);
        if (user == null) {
            throw CustomResponseException.ResourceNotFound("User not found with username: " + username);
        }
        return user;
    }

    public boolean verifyPassword(UserAccount user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}
