package com.andrew.FinancialHelper.service;

import com.andrew.FinancialHelper.db.entity.Account;
import com.andrew.FinancialHelper.db.entity.Category;
import com.andrew.FinancialHelper.db.entity.Transaction;
import com.andrew.FinancialHelper.db.repository.TransactionRepository;
import com.andrew.FinancialHelper.dto.request.TransactionRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final CategoryService categoryService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, CategoryService categoryService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction with id " + id + " not found"));
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    @Transactional
    public Transaction createTransaction(TransactionRequest request) {
        Account account = accountService.findAccountById(request.getAccountId());
        Category category = categoryService.findCategoryById(request.getCategoryId());

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setResult(request.getResult());
        transaction.setLocalDate(LocalDate.now());

        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new EntityNotFoundException("Transaction with id " + id + " not found");
        }
        transactionRepository.deleteById(id);
    }

    public List<Transaction> getTransactionsByPeriod(LocalDate start, LocalDate end) {
        return transactionRepository.findTransactionsByLocalDateBetween(start, end);
    }

    public List<Transaction> getTransactionsByCategoryId(Long id) {
        return transactionRepository.findTransactionsByCategory_Id(id);
    }

    public List<Transaction> getTransactionsByAccountId(Long id) {
        return transactionRepository.findTransactionsByAccountId(id);
    }
}
