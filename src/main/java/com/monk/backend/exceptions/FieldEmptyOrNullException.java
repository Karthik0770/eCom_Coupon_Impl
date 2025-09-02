package com.monk.backend.exceptions;

public class FieldEmptyOrNullException extends RuntimeException {
    public FieldEmptyOrNullException(String fieldName) {
        super("Field :"+ fieldName +"is empty or null. Please provide a value for it");
    }
}
