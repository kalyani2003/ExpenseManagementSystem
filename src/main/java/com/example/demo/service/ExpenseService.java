package com.example.demo.service;

import com.example.demo.dto.ExpenseRequestDto;
import com.example.demo.dto.ExpenseResponseDto;
import com.example.demo.model.Expense;
import com.example.demo.repository.ExpenseRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private static final Logger logger =
            LoggerFactory.getLogger(ExpenseService.class);

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    // ✅ GET ALL EXPENSES
    @Cacheable("allExpenses")
    public List<ExpenseResponseDto> getAllExpenses() {

        logger.info("Fetching all expenses from MongoDB");

        List<Expense> expenses = expenseRepository.findAll();

        return expenses.stream()
                .map(expense -> new ExpenseResponseDto(
                        expense.getId(),
                        expense.getTitle(),
                        expense.getAmount(),
                        expense.getCategory()
                ))
                .collect(Collectors.toList());
    }

    // ✅ GET EXPENSE BY ID
    @Cacheable(value = "expenses", key = "#id")
    public ExpenseResponseDto getExpenseById(String id) {

        logger.info("Fetching expense by id: {}", id);

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Expense not found"));

        return new ExpenseResponseDto(
                expense.getId(),
                expense.getTitle(),
                expense.getAmount(),
                expense.getCategory()
        );
    }

    // ✅ CREATE EXPENSE
    @CacheEvict(value = {"expenses", "allExpenses"}, allEntries = true)
    public ExpenseResponseDto createExpense(
            ExpenseRequestDto dto
    ) {

        logger.info("Creating new expense");

        Expense expense = new Expense();

        expense.setTitle(dto.getTitle());
        expense.setAmount(dto.getAmount());
        expense.setCategory(dto.getCategory());

        Expense savedExpense =
                expenseRepository.save(expense);

        // ✅ SEND MESSAGE TO KAFKA
        kafkaProducerService.sendMessage(
                "New Expense Added: " + savedExpense.getTitle()
        );

        return new ExpenseResponseDto(
                savedExpense.getId(),
                savedExpense.getTitle(),
                savedExpense.getAmount(),
                savedExpense.getCategory()
        );
    }

    // ✅ UPDATE EXPENSE
    @CacheEvict(value = {"expenses", "allExpenses"}, allEntries = true)
    public ExpenseResponseDto updateExpense(
            String id,
            ExpenseRequestDto dto
    ) {

        logger.info("Updating expense with id: {}", id);

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Expense not found"));

        expense.setTitle(dto.getTitle());
        expense.setAmount(dto.getAmount());
        expense.setCategory(dto.getCategory());

        Expense updatedExpense =
                expenseRepository.save(expense);

        return new ExpenseResponseDto(
                updatedExpense.getId(),
                updatedExpense.getTitle(),
                updatedExpense.getAmount(),
                updatedExpense.getCategory()
        );
    }

    // ✅ DELETE EXPENSE
    @CacheEvict(value = {"expenses", "allExpenses"}, allEntries = true)
    public void deleteExpense(String id) {

        logger.info("Deleting expense with id: {}", id);

        if (!expenseRepository.existsById(id)) {
            throw new RuntimeException("Expense not found");
        }

        expenseRepository.deleteById(id);
    }

    // ✅ SEARCH BY CATEGORY
    public List<ExpenseResponseDto> getExpensesByCategory(
            String category
    ) {

        logger.info("Fetching expenses by category: {}", category);

        List<Expense> expenses =
                expenseRepository.findByCategory(category);

        return expenses.stream()
                .map(expense -> new ExpenseResponseDto(
                        expense.getId(),
                        expense.getTitle(),
                        expense.getAmount(),
                        expense.getCategory()
                ))
                .collect(Collectors.toList());
    }

    // ✅ PAGINATION + SORTING
    public Page<ExpenseResponseDto> getExpensesWithPagination(
            int page,
            int size,
            String sortBy
    ) {

        logger.info("Fetching expenses with pagination");

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).ascending()
        );

        Page<Expense> expensePage =
                expenseRepository.findAll(pageable);

        return expensePage.map(expense ->
                new ExpenseResponseDto(
                        expense.getId(),
                        expense.getTitle(),
                        expense.getAmount(),
                        expense.getCategory()
                )
        );
    }
}
