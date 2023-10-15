package com.andrew.FinancialHelper.service;


import com.andrew.FinancialHelper.db.entity.Account;
import com.andrew.FinancialHelper.db.entity.User;
import com.andrew.FinancialHelper.db.repository.AccountRepository;
import com.andrew.FinancialHelper.dto.request.AccountRequest;
import com.andrew.FinancialHelper.exception.UserAccountLimitExceededException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserService userService;

    private static final int MAX_ACCOUNTS_PER_USER = 3;

    public AccountService(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    public Account findAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account with id " + id + " not found"));
    }

    @Transactional
    public Account createAccountFromRequest(AccountRequest request) {
        User user = userService.findUserById(request.getOwnerId());

        if (user.getAccounts().size() >= MAX_ACCOUNTS_PER_USER) {
            throw new UserAccountLimitExceededException("User has reached the maximum account limit.");
        }

        Account account = new Account();
        account.setName(request.getName());
        account.setBalance(request.getBalance());
        account.setUser(user);

        return accountRepository.save(account);
    }

    @Transactional
    public Account updateAccountFromRequest(AccountRequest request) {
        Account account = findAccountById(request.getId());
        account.setBalance(request.getBalance());
        account.setName(request.getName());

        return accountRepository.save(account);
    }

    @Transactional
    public void deleteAccountById(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new EntityNotFoundException("Account with id " + id + " not found");
        }
        accountRepository.deleteById(id);
    }
}
