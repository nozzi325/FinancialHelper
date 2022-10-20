package com.andrew.FinancialHelper.dto.response;

import com.andrew.FinancialHelper.db.entity.Account;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private List<Account> accounts;
}