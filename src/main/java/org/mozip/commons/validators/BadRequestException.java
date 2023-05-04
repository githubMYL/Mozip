package org.mozip.commons.validators;

import org.mozip.commons.CommonException;
import org.springframework.http.HttpStatus;

public class BadRequestException  extends CommonException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
