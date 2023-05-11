package org.mozip.controllers.main;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.mozip.commons.MemberUtil;
import org.mozip.entities.Mozip;
import org.mozip.models.member.MemberInfo;
import org.mozip.models.mozip.MozipInfoService;
import org.mozip.repositories.MozipRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/mozip")
@RequiredArgsConstructor
public class MainController {

    private final MozipRepository repository;
    @Autowired
    private MemberUtil memberUtil;

    @GetMapping
    public String main(Model model, HttpServletRequest request){

        MemberInfo memberInfo = memberUtil.getMember();
        if(memberInfo != null){
            String memberNick = memberInfo.getMemberNick();
            model.addAttribute("memberNick", memberNick);
        }
        MozipSearch search = new MozipSearch();
        search.setLimit(5);
        Page<Mozip> data = listService.gets(search);
        List<Mozip> items = data.getContent();
        String url = request.getContextPath() + "/";
        Pagination<Mozip> pagination = new Pagination<>(data, url);


        model.addAttribute("items", items);
        model.addAttribute("pagination", pagination);

        model.addAttribute("addCss", new String[] {"main/style"});
        model.addAttribute("addScript", new String[] {"main/mozip"});
        model.addAttribute("pageTitle", "MOZIP");
        return "main/mozip";


        List<Mozip> mozips = repository.findAll();
        System.out.println("mozips ::::::::::::::::::: >> " + mozips);
        model.addAttribute("mozips", mozips);

        return "mozip";

    }
}
