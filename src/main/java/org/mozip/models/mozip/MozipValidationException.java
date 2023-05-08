package org.mozip.models.mozip;

import org.mozip.commons.CommonException;
import org.springframework.http.HttpStatus;

import java.util.ResourceBundle;

public class MozipValidationException extends CommonException  {
    private static ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle("messages.validations");
    }

    public MozipValidationException(String message) {
        super(bundle.getString(message), HttpStatus.BAD_REQUEST);
    }
}
