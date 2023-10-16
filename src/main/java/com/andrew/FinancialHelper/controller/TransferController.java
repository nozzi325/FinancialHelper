package com.andrew.FinancialHelper.controller;

import com.andrew.FinancialHelper.dto.request.TransferRequest;
import com.andrew.FinancialHelper.service.TransferService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/api/transfer")
    public void transferMoney(@RequestBody @Valid TransferRequest request) {
        transferService.transferMoney(
                request.senderId(),
                request.receiverId(),
                request.amount());
    }
}
