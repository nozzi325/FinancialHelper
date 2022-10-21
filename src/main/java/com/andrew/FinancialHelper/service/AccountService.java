package com.andrew.FinancialHelper.service;


import com.andrew.FinancialHelper.db.entity.Account;
import com.andrew.FinancialHelper.db.repository.AccountRepository;
import com.andrew.FinancialHelper.exception.AccountNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    public Account findAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account with id %d not found", id)));
    }
    @Transactional
    public void createAccount(Account account){
        accountRepository.save(account);
    }

    @Transactional
    public void deleteAccount(Long id){
        var exist = accountRepository.existsById(id);
        if (!exist){
            throw new AccountNotFoundException(String.format("Account with id %d not found", id));
        }
        accountRepository.deleteById(id);
    }

    @Transactional
    public void updateAccount(Account account){
        var exist = accountRepository.existsById(account.getId());
        if (!exist){
            throw new AccountNotFoundException(String.format("Account with id %d not found", account.getId()));
        }
        accountRepository.save(account);
    }
}
