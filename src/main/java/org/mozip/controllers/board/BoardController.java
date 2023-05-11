package org.mozip.controllers.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mozip.commons.CommonException;
import org.mozip.commons.MemberUtil;
import org.mozip.commons.Pagination;
import org.mozip.entities.BoardData;
import org.mozip.models.board.BoardInfoService;
import org.mozip.models.board.BoardListService;
import org.mozip.models.board.BoardSaveService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

/**
 * 게시판 관련 컨트롤러
 *
 */
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardListService listService;
    private final BoardSaveService saveService;

    private final BoardInfoService infoService;

    private final MemberUtil memberUtil;

    @GetMapping("/list")
    public String list(@ModelAttribute BoardSearch search, Model model, HttpServletRequest request) {

        Page<BoardData> data = listService.gets(search);
        String url = request.getContextPath() + "/board/list";
        Pagination<BoardData> pagination = new Pagination<>(data, url);

        model.addAttribute("items", data.getContent());
        model.addAttribute("pagination", pagination);

        return "board/list";
    }

    @PostMapping("/save")
    public String save(@Valid BoardForm boardForm, Errors errors) {
        Long id = boardForm.getId();
        if (errors.hasErrors()) {
            return id == null ? "board/write" :"board/update";
        }


        saveService.save(boardForm);

        return "redirect:/board/list";
    }

    @GetMapping("/write")
    public String write(Model model) {
        BoardForm boardForm = new BoardForm();
        if (memberUtil.isLogin()) {
            boardForm.setRegUser(memberUtil.getMember().getMemberNm());
        }
        model.addAttribute("boardForm", boardForm);

        return "board/write";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        BoardData boardData = infoService.get(id);

        BoardForm boardForm = new ModelMapper().map(boardData, BoardForm.class);
        model.addAttribute("boardForm", boardForm);

        return "board/update";
    }

    @ExceptionHandler(CommonException.class)
    public String errorHandler(CommonException e, HttpServletResponse response, Model model) {

        HttpStatus status = e.getStatus();
        status = status == null ? HttpStatus.INTERNAL_SERVER_ERROR : status;

        response.setStatus(status.value());

        String script = String.format("alert('%s');history.back();", e.getMessage());
        model.addAttribute("script", script);
        return "commons/execute_script";
    }

}