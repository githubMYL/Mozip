package org.mozip.controllers.main;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.mozip.commons.MemberUtil;
import org.mozip.entities.CateCode;
import org.mozip.entities.MainList;
import org.mozip.repositories.CategoryRepository;
import org.mozip.repositories.MainCreateRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller("mainDetailController2")
@RequestMapping("/mozip")
@RequiredArgsConstructor
@Log
public class MainDetailController {

    private MainList mainList;
    private CateCode cateCode;
    private MainCreateParam mainCreateParam;
    private final CategoryRepository categoryRepository;
    private final MainCreateRepository mainCreateRepository;
    private final MemberUtil memberUtil;

    //상세페이지
    @GetMapping("/maindetail")
    public String index() {

        return "detail/maindetail";
    }

    //사진첩
    @GetMapping("/detailPT")
    public String scheduleDetail(){

        return "detail/detailPT";  //임시
    }

    /** 모집 모임생성 GET */
    @GetMapping("/mozipCreate")
    public String mozipCreate(Model model) {

        mainCreateParam = new MainCreateParam();
        model.addAttribute("mainCreateParam", mainCreateParam);

        // 카테고리 select box
        List<CateCode> categoryList = categoryRepository.findAll();
        model.addAttribute("categorys", categoryList.stream().collect(Collectors.toMap(CateCode::getCode, CateCode::getCodeName)));

        return "main/mozipCreate";
    }
    /** 모집 모임생성 POST */
    @PostMapping("/mozipCreate")
    public String mozipCreatePs(@Valid MainCreateParam mainCreateParam, Error error) {

        // 카테고리 코드값 넣기
        cateCode = categoryRepository.findById(mainCreateParam.getCode()).orElse(null);

        mainList = mainCreateParam.of(mainCreateParam);
        mainList.setCateCode(cateCode);
        mainList.setMember(memberUtil.getEntity());
        mainCreateRepository.saveAndFlush(mainList);

        return "redirect:/mozip";
    }
}
