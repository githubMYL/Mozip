package org.mozip.models.board;

import org.mozip.commons.CommonException;
import org.springframework.http.HttpStatus;

public class BoardDataNotExistException extends CommonException {
    public BoardDataNotExistException() {
        super("게시글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
    }
}
