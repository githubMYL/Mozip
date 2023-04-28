package org.mozip.models.member;

public class LoginValidationException extends RuntimeException{

    private String field;
    public LoginValidationException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
