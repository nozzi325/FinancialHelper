package com.andrew.FinancialHelper.service;

import com.andrew.FinancialHelper.db.entity.Transaction;
import com.andrew.FinancialHelper.db.repository.TransactionRepository;
import com.andrew.FinancialHelper.exception.TransactionNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public Transaction getTransaction(Long id){
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(String.format("Transaction with id %d not found", id)));
    }

    public List<Transaction> getTransactions(){
        return transactionRepository.findAll();
    }

    @Transactional
    public void createTransaction(Transaction transaction){
        if (transaction.getLocalDate() == null){
            transaction.setLocalDate(LocalDate.now());
        }
        transactionRepository.save(transaction);
    }
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}