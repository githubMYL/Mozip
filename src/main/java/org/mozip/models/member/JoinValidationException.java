package org.mozip.models.member;

import org.mozip.commons.CommonException;
import org.springframework.http.HttpStatus;

public class JoinValidationException extends CommonException {
    public JoinValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
