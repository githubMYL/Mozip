package org.mozip.controllers.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    //게시글 작성 양식 페이지
    @GetMapping("/write")
    public String write(){

        return "board/write";
    }

    //게시글 목록 페이지
    @GetMapping("/list")
    public String list(){

        return "board/list";
    }

    //게시글 삭제 페이지
    @GetMapping("/delete")
    public String delete(){

        return "redirect:/board/list";
    }
}
