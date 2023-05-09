package org.mozip.controllers.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.mozip.commons.Pagination;
import org.mozip.entities.BoardData;
import org.mozip.models.board.BoardListService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 게시판 관련 컨트롤러
 *
 */
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardListService listService;

    @GetMapping("/list")
    public String list(@ModelAttribute BoardSearch search, Model model, HttpServletRequest request) {

        Page<BoardData> data = listService.gets(search);
        String url = request.getContextPath() + "/board/list";
        Pagination<BoardData> pagination = new Pagination<>(data, url);

        model.addAttribute("items", data.getContent());
        model.addAttribute("pagination", pagination);

        return "board/list";
    }
}