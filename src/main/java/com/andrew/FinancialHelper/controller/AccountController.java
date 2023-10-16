package com.andrew.FinancialHelper.controller;

import com.andrew.FinancialHelper.db.entity.Account;
import com.andrew.FinancialHelper.dto.request.AccountRequest;
import com.andrew.FinancialHelper.dto.response.AccountResponse;
import com.andrew.FinancialHelper.service.AccountService;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final ModelMapper modelMapper;

    public AccountController(AccountService accountService, ModelMapper modelMapper) {
        this.accountService = accountService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<AccountResponse> getAllAccounts() {
        return accountService.findAllAccounts()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @GetMapping("{id}")
    public AccountResponse getAccountById(@PathVariable Long id) {
        return convertToResponse(accountService.findAccountById(id));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid AccountRequest request) {
        Account account = accountService.createAccountFromRequest(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(convertToResponse(account));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@RequestBody @Valid AccountRequest request) {
        Account account = accountService.updateAccountFromRequest(request);
        return ResponseEntity
                .ok()
                .body(convertToResponse(account));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccountById(id);
    }

    private AccountResponse convertToResponse(Account account) {
        return modelMapper.map(account, AccountResponse.class);
    }
}
