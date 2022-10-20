package com.andrew.FinancialHelper.dto.request;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class AccountRequest {
    private Long id;

    @NotEmpty(message = "Account name can't be blank")
    private String name;

    @PositiveOrZero(message = "Balance can't be less 0")
    @NotNull(message = "Balance can't be null")
    private BigDecimal balance;

    @NotNull(message = "OwnerId can't be null")
    private Long ownerId;
}
