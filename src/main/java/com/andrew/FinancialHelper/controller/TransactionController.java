package com.andrew.FinancialHelper.controller;

import com.andrew.FinancialHelper.db.entity.Transaction;
import com.andrew.FinancialHelper.dto.request.TransactionRequest;
import com.andrew.FinancialHelper.dto.response.TransactionResponse;
import com.andrew.FinancialHelper.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final ModelMapper modelMapper;

    public TransactionController(TransactionService transactionService, ModelMapper modelMapper) {
        this.transactionService = transactionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<TransactionResponse> getAllTransactions() {
        List<Transaction> transactions = transactionService.getTransactions();
        return transactions.stream()
                .map(this::convertToResponse)
                .toList();
    }

    @GetMapping("{id}")
    public TransactionResponse getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return convertToResponse(transaction);
    }

    @GetMapping(params = {"start", "end"})
    public List<TransactionResponse> getTransactionsByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        List<Transaction> transactions = transactionService.getTransactionsByPeriod(start, end);
        return transactions.stream()
                .map(this::convertToResponse)
                .toList();
    }

    @GetMapping(params = "categoryId")
    public List<TransactionResponse> getTransactionsByCategory(@RequestParam("categoryId") Long id) {
        List<Transaction> transactions = transactionService.getTransactionsByCategoryId(id);
        return transactions.stream()
                .map(this::convertToResponse)
                .toList();
    }

    @GetMapping(params = "accountId")
    public List<TransactionResponse> getTransactionsByAccountId(@RequestParam("accountId") Long id) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(id);
        return transactions.stream()
                .map(this::convertToResponse)
                .toList();
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        Transaction transaction = transactionService.createTransaction(transactionRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(convertToResponse(transaction));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }

    private TransactionResponse convertToResponse(Transaction transaction) {
        return modelMapper.map(transaction, TransactionResponse.class);
    }

    private Transaction convertToEntity(TransactionRequest request) {
        return modelMapper.map(request, Transaction.class);
    }
}
