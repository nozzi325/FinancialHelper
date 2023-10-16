package com.andrew.FinancialHelper.service;

import com.andrew.FinancialHelper.db.entity.Account;
import com.andrew.FinancialHelper.db.entity.Category;
import com.andrew.FinancialHelper.db.repository.AccountRepository;
import com.andrew.FinancialHelper.dto.request.TransactionRequest;
import com.andrew.FinancialHelper.exception.InsufficientFundsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService {
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final CategoryService categoryService;
    private final AccountRepository accountRepository;
    private static final Long TRANSFER_CATEGORY_ID = 1L;

    public TransferService(AccountService accountService,
                           TransactionService transactionService,
                           CategoryService categoryService,
                           AccountRepository accountRepository) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.categoryService = categoryService;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transferMoney(Long senderId, Long receiverId, BigDecimal amount) {
        Account sender = accountService.findAccountById(senderId);
        Account receiver = accountService.findAccountById(receiverId);

        BigDecimal senderBalance = sender.getBalance();
        BigDecimal receiverBalance = receiver.getBalance();

        if (senderBalance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds on the account: " + senderId);
        }

        BigDecimal senderNewBalance = senderBalance.subtract(amount);
        BigDecimal receiverNewBalance = receiverBalance.add(amount);

        sender.setBalance(senderNewBalance);
        receiver.setBalance(receiverNewBalance);

        accountRepository.saveAll(List.of(sender, receiver));

        Category transferCategory = categoryService.findCategoryById(TRANSFER_CATEGORY_ID);

        TransactionRequest transactionFromRequest = new TransactionRequest();
        transactionFromRequest.setAccountId(senderId);
        transactionFromRequest.setCategoryId(transferCategory.getId());
        transactionFromRequest.setResult(amount.negate());
        transactionService.createTransaction(transactionFromRequest);

        TransactionRequest transactionToRequest = new TransactionRequest();
        transactionToRequest.setAccountId(receiverId);
        transactionToRequest.setCategoryId(transferCategory.getId());
        transactionToRequest.setResult(amount);
        transactionService.createTransaction(transactionToRequest);
    }
}
