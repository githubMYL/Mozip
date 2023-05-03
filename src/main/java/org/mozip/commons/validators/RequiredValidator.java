package org.mozip.commons.validators;

public interface RequiredValidator {

    //필수항목 체크
    default void requiredCheck(String str, RuntimeException e){
        if(str == null || str.isBlank()){
            throw e;
        }
    }
    
    default void nullCheck(Object obj, RuntimeException e){
        if(obj == null){
            throw e;
        }
    }
}
