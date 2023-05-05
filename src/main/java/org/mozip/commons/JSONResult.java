package org.mozip.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data@Builder
@NoArgsConstructor@AllArgsConstructor
public class JSONResult<T> {

    private boolean success;
    
    //기본 응답 상태 코드 200
    private HttpStatus status = HttpStatus.OK;
    
    //실패시 에러 메세지
    private String messages;
    
    //성공시 응답 데이터
    private T data;
}
