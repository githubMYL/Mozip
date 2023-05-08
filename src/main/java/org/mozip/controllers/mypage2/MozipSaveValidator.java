package org.mozip.controllers.mypage2;

import org.mozip.models.mozip.MozipValidationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MozipSaveValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MozipForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MozipForm mozipForm = (MozipForm) target;
        String mode = mozipForm.getMode();
        Long id = mozipForm.getId();
        if (mode == "update") {
            if (id != null) {
                throw new MozipValidationException("BadRequest");
            }
        }

    }
}
