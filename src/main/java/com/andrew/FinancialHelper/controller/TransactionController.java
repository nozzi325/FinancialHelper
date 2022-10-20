package com.andrew.FinancialHelper.controller;

import com.andrew.FinancialHelper.dto.request.AccountRequest;
import com.andrew.FinancialHelper.dto.request.TransactionRequest;
import com.andrew.FinancialHelper.dto.response.TransactionResponse;
import com.andrew.FinancialHelper.db.entity.Account;
import com.andrew.FinancialHelper.db.entity.Category;
import com.andrew.FinancialHelper.db.entity.Transaction;
import com.andrew.FinancialHelper.service.AccountService;
import com.andrew.FinancialHelper.service.CategoryService;
import com.andrew.FinancialHelper.service.TransactionService;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public List<TransactionResponse> getAllTransactions(){
        return transactionService.getTransactions().stream()
                .map(this::convertToResponse)
                .toList();
    }

    @GetMapping("{id}")
    public TransactionResponse getTransactionById(@PathVariable Long id){
        return convertToResponse(transactionService.getTransaction(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createTransaction(@RequestBody TransactionRequest transactionRequest){
        Transaction transaction = convertToEntity(transactionRequest);
        Account account = accountService.findAccountById(transactionRequest.getAccountId());
        transaction.setAccount(account);
        Category category = categoryService.findCategoryById(transactionRequest.getCategoryId());
        transaction.setCategory(category);
        transactionService.createTransaction(transaction);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateAccount(@RequestBody @Valid AccountRequest accountRequest){

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTrasnaction(@PathVariable Long id){
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private TransactionResponse convertToResponse(Transaction transaction){
        return modelMapper.map(transaction, TransactionResponse.class);
    }

    private Transaction convertToEntity(TransactionRequest request){
        return modelMapper.map(request,Transaction.class);
    }
}