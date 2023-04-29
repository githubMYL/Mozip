package org.mozip.commons;

import org.springframework.http.HttpStatus;

public class CommonException extends RuntimeException{
    //모든 예외 처리(공통 에러)

    private HttpStatus status;  //상태소스

    //상태 코드가 없으면 500
    public CommonException(String message){
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public CommonException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    //응답
    public HttpStatus getStatus(){
        return status;
    }
}
