package com.example.dataquery.exception;

public class GenericException extends RuntimeException {
    final String code;

    public GenericException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
