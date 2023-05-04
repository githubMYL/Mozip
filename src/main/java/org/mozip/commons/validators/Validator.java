package org.mozip.commons.validators;

public interface Validator<T> extends RequiredValidator{
    void check(T t);

}
