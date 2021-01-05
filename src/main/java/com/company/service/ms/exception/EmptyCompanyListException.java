package com.company.service.ms.exception;

public class EmptyCompanyListException extends RuntimeException {
    public EmptyCompanyListException(String message) {
        super(message);
    }
}
