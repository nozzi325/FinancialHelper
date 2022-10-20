package com.andrew.FinancialHelper.service;

import com.andrew.FinancialHelper.db.entity.User;
import com.andrew.FinancialHelper.db.repository.UserRepository;
import com.andrew.FinancialHelper.exception.UserEmailAlreadyTakenException;
import com.andrew.FinancialHelper.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private final DigestService digestService;

    public User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %d not found", id)));
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public void createUser(User user){
        boolean isExist = userRepository.findUserByEmail(user.getEmail()).isPresent();
        if (isExist){
            throw new UserEmailAlreadyTakenException("User with this email already exists");
        }
        String passwordHash = digestService.hash(user.getPassword());
        user.setPassword(passwordHash);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id){
        findUserById(id);
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(User user){
        User oldUser = findUserById(user.getId());
        String passwordHash = digestService.hash(user.getPassword());
        user.setPassword(passwordHash);
        user.setAccounts(oldUser.getAccounts());
        userRepository.save(user);
    }

    public User findUser(String email){
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email %s not found", email)));
    }

}
