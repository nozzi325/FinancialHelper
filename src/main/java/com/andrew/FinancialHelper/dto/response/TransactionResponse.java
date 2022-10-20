package com.andrew.FinancialHelper.dto.response;

import com.andrew.FinancialHelper.db.entity.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionResponse {
    private Long id;
    private BigDecimal result;
    private Long accountId;
    private LocalDate localDate;
    private Category category;
}
