package org.mozip.controllers.admin.member;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.mozip.commons.Pagination;
import org.mozip.entities.Members;
import org.mozip.models.member.MemberListService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberListService listService;
    private final HttpServletRequest request;

    @GetMapping
    public String index(@ModelAttribute MemberSearch search, Model model) {

        Page<Members> data = listService.gets(search);
        String url = request.getContextPath() + "/admin/member";
        Pagination<Members> pagination = new Pagination<>(data, url);

        model.addAttribute("items", data.getContent());
        model.addAttribute("pagination", pagination);
        System.out.println(data.getContent());
        return "admin/member/index";
    }

    @GetMapping("/{memberNo}")
    public String info(@PathVariable Long memberNo) {

        return "admin/member/info";
    }

}
