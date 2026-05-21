package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    @Positive(message = "Amount must be greater than 0")
    private double amount;

    @NotBlank(message = "Category is required")
    private String category;
}
