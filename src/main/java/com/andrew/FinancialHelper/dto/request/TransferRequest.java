package com.andrew.FinancialHelper.dto.request;

import lombok.Data;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class TransferRequest {
    private Long senderId;
    private Long receiverId;
    @Positive(message = "Transfer amount should be more than 0")
    private BigDecimal amount;
}
