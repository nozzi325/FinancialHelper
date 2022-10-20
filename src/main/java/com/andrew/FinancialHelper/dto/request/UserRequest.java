package com.andrew.FinancialHelper.dto.request;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserRequest {
    private Long id;

    @NotEmpty(message = "Email field can't be blank")
    private String email;

    @Size(min = 8, max = 32, message = "Password length should be between 8 and 32 characters")
    private String password;
}
