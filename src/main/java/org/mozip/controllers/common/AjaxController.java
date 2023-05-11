package org.mozip.controllers.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.mozip.commons.Pagination;
import org.mozip.controllers.mypage2.MozipSearch;
import org.mozip.entities.Mozip;
import org.mozip.models.mozip.MozipListService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ajax")
@RequiredArgsConstructor
public class AjaxController {

    private final MozipListService listService;
    private final HttpServletRequest request;

    @GetMapping("/mozip/{page}")
    public String mozip(@PathVariable int page, Model model) {

        MozipSearch search = new MozipSearch();
        search.setPage(page);
        search.setLimit(5);
        Page<Mozip> data = listService.gets(search);
        List<Mozip> items = data.getContent();
        String url = request.getContextPath() + "/";

        Pagination<Mozip> pagination = new Pagination<>(data, url);
        model.addAttribute("items", items);
        model.addAttribute("pagination", pagination);

        return "ajax/mozip_items";
    }
}
