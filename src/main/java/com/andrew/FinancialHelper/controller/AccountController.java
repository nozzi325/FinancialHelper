package com.andrew.FinancialHelper.controller;

import com.andrew.FinancialHelper.db.entity.Account;
import com.andrew.FinancialHelper.db.entity.User;
import com.andrew.FinancialHelper.dto.request.AccountRequest;
import com.andrew.FinancialHelper.dto.response.AccountResponse;
import com.andrew.FinancialHelper.service.AccountService;
import com.andrew.FinancialHelper.service.UserService;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public List<AccountResponse> getAllAccounts(){
        return accountService.findAllAccounts()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @GetMapping("{id}")
    public AccountResponse getAccountById(@PathVariable Long id){
        return convertToResponse(accountService.findAccountById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createAccount(@RequestBody @Valid AccountRequest request){
        User user = userService.findUserById(request.getOwnerId());
        Account account = convertToEntity(request);
        account.setUser(user);
        accountService.createAccount(account);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateAccount(@RequestBody @Valid AccountRequest request){
        User user = userService.findUserById(request.getOwnerId());
        Account account = convertToEntity(request);
        account.setUser(user);
        accountService.updateAccount(account);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Account convertToEntity(AccountRequest request){
        return modelMapper.map(request,Account.class);
    }

    private AccountResponse convertToResponse(Account account){
        return modelMapper.map(account,AccountResponse.class);
    }
}
