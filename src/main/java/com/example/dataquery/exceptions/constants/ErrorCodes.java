package com.example.dataquery.exceptions.constants;

import com.example.dataquery.exceptions.GenericException;

public enum ErrorCodes {

    NOT_VALID_OPERATOR_FOR_KEY("Operator for this key is not valid"),
    NOT_VALID_OPERATOR("Operator is not valid");

    private final String message;

    ErrorCodes(String message) {
        this.message = message;
    }


    public void throwException(){
        throw new GenericException(this.message, this.name());
    }
    public GenericException getException(){
        return new GenericException(this.message, this.name());
    }
}
