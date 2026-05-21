package com.example.demo.controller;

import com.example.demo.dto.ExpenseRequestDto;
import com.example.demo.dto.ExpenseResponseDto;
import com.example.demo.service.ExpenseService;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // GET ALL EXPENSES
    @Operation(summary = "Get all expenses")
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<ExpenseResponseDto> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    // GET EXPENSE BY ID
    @Operation(summary = "Get expense by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ExpenseResponseDto getExpenseById(@PathVariable String id) {
        return expenseService.getExpenseById(id);
    }

    // CREATE EXPENSE
    @Operation(summary = "Create new expense")
    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ExpenseResponseDto createExpense(
            @Valid @RequestBody ExpenseRequestDto dto) {

        return expenseService.createExpense(dto);
    }

    // UPDATE EXPENSE
    @Operation(summary = "Update expense")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ExpenseResponseDto updateExpense(
            @PathVariable String id,
            @Valid @RequestBody ExpenseRequestDto dto) {

        return expenseService.updateExpense(id, dto);
    }

    // DELETE EXPENSE (ONLY ADMIN)
    @Operation(summary = "Delete expense")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteExpense(@PathVariable String id) {
        expenseService.deleteExpense(id);
    }

    // PAGINATION + SORTING
    @Operation(summary = "Get expenses with pagination and sorting")
    @GetMapping("/pagination")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Page<ExpenseResponseDto> getExpensesWithPagination(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "amount") String sortBy
    ) {

        return expenseService.getExpensesWithPagination(
                page,
                size,
                sortBy
        );
    }
}