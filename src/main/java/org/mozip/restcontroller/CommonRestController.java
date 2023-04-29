package org.mozip.restcontroller;

import org.mozip.commons.JSONResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonRestController {
    //에러 핸들러(공통 에러페이지)

    public ResponseEntity<JSONResult<Object>> errorHandler(Exception e){
        JSONResult<Object> jsonResult = new JSONResult<>();
        jsonResult.setSuccess(false);
        jsonResult.setMessages(e.getMessage());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(status).body(jsonResult);
    }
}
