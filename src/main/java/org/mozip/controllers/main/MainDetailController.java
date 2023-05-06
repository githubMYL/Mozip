package org.mozip.controllers.main;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.mozip.controllers.category.CateCode;
import org.mozip.repositories.CategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mozip")
@RequiredArgsConstructor
@Log
public class MainDetailController {

    private MainCreateParam mainCreateParam;
    private final CategoryRepository categoryRepository;

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
//        System.out.println(categoryRepository.findById(1l));
//        categoryList.stream().forEach(System.out::println);
        return "main/mozipCreate";
    }
    /** 모집 모임생성 POST */
    @PostMapping("/mozipCreate")
    public String mozipCreatePs(@Valid MainCreateParam mainCreateParam, Error error, @RequestParam(name="cateCd") String cateCd, Model model) {

        log.info("cateCd :: " + cateCd);

        //categoryRepository.findById(productForm.getCategoryId()).orElse(null);

        log.info("mainCreateParam :: " + mainCreateParam.getMName() + " /// " + mainCreateParam.getMComment());
        return "redirect:/mozip";
    }
}
