package org.mozip.commons.Validator;

/**
 * 공통 Validator
 * @param <T>
 */
public interface Validator<T> extends RequiredValidator {
    void check(T t);
}
