package com.andrew.FinancialHelper.dto;

import java.time.LocalDateTime;

public record ErrorDetails(
        String message,
        LocalDateTime timestamp
) {
}
