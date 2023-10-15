package com.andrew.FinancialHelper.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public record TransferRequest(
        @NotNull
        Long senderId,
        @NotNull
        Long receiverId,
        @Positive(message = "Transfer amount should be more than 0")
        BigDecimal amount
) {
}
