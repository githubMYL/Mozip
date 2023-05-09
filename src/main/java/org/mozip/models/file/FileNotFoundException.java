package org.mozip.models.file;

import org.mozip.commons.CommonException;
import org.springframework.http.HttpStatus;

import java.util.ResourceBundle;

public class FileNotFoundException extends CommonException {
    private static ResourceBundle bundle = ResourceBundle.getBundle("messages.errors");

    public FileNotFoundException() {
        super(bundle.getString("errors.file.notFound"), HttpStatus.BAD_REQUEST);
    }
}
