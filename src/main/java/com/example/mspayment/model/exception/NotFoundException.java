package com.example.mspayment.model.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class NotFoundException extends RuntimeException{
    private final String code;
    public NotFoundException(String message, String code) {
        super(message);
        this.code=code;
    }
}
