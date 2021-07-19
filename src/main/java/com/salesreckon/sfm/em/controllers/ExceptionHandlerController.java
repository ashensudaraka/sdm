package com.salesreckon.sfm.em.controllers;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import com.salesreckon.sfm.em.exceptions.BadRequestException;
import com.salesreckon.sfm.em.exceptions.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Set<String>> handleValidationExceptions(ConstraintViolationException ex) {
        return ResponseEntity.badRequest()
                .body(ex.getConstraintViolations().stream().map(it -> it.getMessage()).collect(Collectors.toSet()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> entityNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> dataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.badRequest().body("This record is related to other records");
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> badRequest(BadRequestException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
