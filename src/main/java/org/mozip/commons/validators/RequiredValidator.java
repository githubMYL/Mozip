package org.mozip.commons.validators;

public interface RequiredValidator {
<<<<<<< HEAD
    default void requiredCheck(String str, RuntimeException e) {
        if (str == null || str.isBlank()) {
            throw e;
        }
    }

    default void nullCheck(Object obj, RuntimeException e) {
        if (obj == null) {
=======

    //필수항목 체크
    default void requiredCheck(String str, RuntimeException e){
        if(str == null || str.isBlank()){
            throw e;
        }
    }
    
    default void nullCheck(Object obj, RuntimeException e){
        if(obj == null){
>>>>>>> 144a6cb1824e09c1ec1452afbec2c7ef0623b5af
            throw e;
        }
    }
}
