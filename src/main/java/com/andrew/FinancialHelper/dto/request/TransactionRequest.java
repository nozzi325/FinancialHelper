package com.andrew.FinancialHelper.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private Long id;
    private BigDecimal result;
    private Long accountId;
    private Long categoryId;
}
