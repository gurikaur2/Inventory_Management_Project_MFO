package com.gurnoor.inventory.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 * DoD 2: Global exception handler — one class maps every exception to the
 *         correct HTTP status code across all controllers.
 *
 * HTTP Status mapping:
 *   404 NOT_FOUND          → Item / Supplier / Warehouse not found
 *   400 BAD_REQUEST        → Validation failure, illegal argument, duplicate issue
 *   500 INTERNAL_SERVER_ERROR → Any unhandled exception
 */
@RestControllerAdvice
public class GlobalException {
 
    // ── 404 handlers ──────────────────────────────────────────────────
 
    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)                    // 404
    public Map<String, String> handleItemNotFound(ItemNotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }
 
    @ExceptionHandler(SupplierNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)                    // 404
    public Map<String, String> handleSupplierNotFound(SupplierNotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }
 
    @ExceptionHandler(WarehouseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)                    // 404
    public Map<String, String> handleWarehouseNotFound(WarehouseNotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }
 
    // Thrown by queryForObject() when no DB row matches
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)                    // 404
    public Map<String, String> handleEmptyResult(EmptyResultDataAccessException ex) {
        return Map.of("error", "Record not found in the database.");
    }
 
    // ── 400 handlers ──────────────────────────────────────────────────
 
    // DoD 1: combined-quantity-exceeds-stock scenario
    @ExceptionHandler(DuplicateItemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)                  // 400
    public Map<String, String> handleDuplicate(DuplicateItemException ex) {
        return Map.of("error", ex.getMessage());
    }
 
    // Thrown when @Valid fails on a @RequestBody object
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)                  // 400
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }
 
    // Thrown for bad path/query params (e.g. negative quantity, invalid sortBy value)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)                  // 400
    public Map<String, String> handleIllegalArgument(IllegalArgumentException ex) {
        return Map.of("error", ex.getMessage());
    }
 
    // ── 500 catch-all ─────────────────────────────────────────────────
 
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)        // 500
    public Map<String, String> handleGenericException(Exception ex) {
        return Map.of("error", "An unexpected error occurred: " + ex.getMessage());
    }
}
 
