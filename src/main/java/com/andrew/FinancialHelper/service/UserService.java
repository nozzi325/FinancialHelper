package com.andrew.FinancialHelper.service;

import com.andrew.FinancialHelper.db.entity.User;
import com.andrew.FinancialHelper.db.repository.UserRepository;
import com.andrew.FinancialHelper.exception.UserEmailAlreadyTakenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final DigestService digestService;

    public UserService(UserRepository userRepository, DigestService digestService) {
        this.userRepository = userRepository;
        this.digestService = digestService;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %d not found", id)));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User createUser(User user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new UserEmailAlreadyTakenException("User with this email already exists");
        }
        String hashedPassword = digestService.hash(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("User with id %d not found", id));
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public User updateUser(User user) {
        User existingUser = findUserById(user.getId());

        String currentPassword = existingUser.getPassword();
        String newPassword = digestService.hash(user.getPassword());
        if (!currentPassword.equals(newPassword)) {
            existingUser.setPassword(newPassword);
        }

        String currentEmail = existingUser.getEmail();
        String newEmail = user.getEmail();
        if (!currentEmail.equals(newEmail)) {
            existingUser.setEmail(newEmail);
        }

       return userRepository.save(existingUser);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with email %s not found", email)));
    }
}