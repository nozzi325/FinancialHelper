package com.andrew.FinancialHelper.controller;

import com.andrew.FinancialHelper.dto.request.TransferRequest;
import com.andrew.FinancialHelper.service.TransferService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class TransferController {
    private final TransferService transferService;

    @PostMapping("/api/transfer")
    public void transferMoney(@RequestBody @Valid TransferRequest request) {
        transferService.transferMoney(
                request.getSenderId(),
                request.getReceiverId(),
                request.getAmount());
    }
}
