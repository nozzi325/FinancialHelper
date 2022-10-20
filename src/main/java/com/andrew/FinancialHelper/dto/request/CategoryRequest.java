package com.andrew.FinancialHelper.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryRequest {
    private Long id;

    @NotBlank(message = "Category name field can't be blank")
    private String name;
}
