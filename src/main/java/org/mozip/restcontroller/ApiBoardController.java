package org.mozip.restcontroller;

import org.mozip.commons.JSONResult;
import org.mozip.controllers.board.BoardForm;
import org.mozip.models.board.BoardInfoService;
import org.mozip.models.board.BoardListService;
import org.mozip.models.board.BoardSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class ApiBoardController {
    @Autowired
    private BoardSaveService saveService;
    @Autowired
    private BoardListService listService;
    @Autowired
    private BoardInfoService infoService;

    @PostMapping("/account")
    public ResponseEntity<Object> account(@RequestBody BoardForm boardForm) {

        if (boardForm.getSubject() == null || boardForm.getSubject().isBlank()  ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 제목 null, 공백일시 400 코드
        }
        if (boardForm.getContent() == null || boardForm.getContent().isBlank() ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 내용 null, 공백일시 400 코드
        }

        saveService.save(boardForm);
        return ResponseEntity.ok().build(); // 성공시 200 코드

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {

        JSONResult<Object> jsonResult = JSONResult.builder()
                .success(true)
                .status(HttpStatus.OK)
                .messages("삭제 성공")
                .build();

        return ResponseEntity.ok(jsonResult);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> boardInfo(@PathVariable Long id) {

        infoService.get(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/gets")
    public ResponseEntity<Object> boardList() {

        listService.gets();
        return ResponseEntity.ok().build();
    }
}
