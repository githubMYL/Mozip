package org.mozip.controllers.main;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.mozip.controllers.category.CateCode;
import org.mozip.controllers.entities.MainList;
import org.mozip.controllers.entities.Members;
import org.mozip.models.member.MemberInfo;
import org.mozip.repositories.CategoryRepository;
import org.mozip.repositories.MainCreateRepository;
import org.mozip.repositories.MembersRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestScope;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mozip")
@RequiredArgsConstructor
@Log
public class MainDetailController {

    private MainList mainList;
    private CateCode cateCode;
    private MainCreateParam mainCreateParam;
    private final EntityManager em;
    private final CategoryRepository categoryRepository;
    private final MainCreateRepository mainCreateRepository;
    private final MembersRepository membersRepository;
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

        // 세션 값 넣기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails detailList = (UserDetails) principal;
        String email = detailList.getUsername();
        Members member = membersRepository.findByEmail(email);

        // 카테고리 코드값 넣기
        cateCode = categoryRepository.findById(mainCreateParam.getCode()).orElse(null);

        mainList = mainCreateParam.of(mainCreateParam);
        mainList.setCateCode(cateCode);
        mainList.setMember(member);
        mainCreateRepository.saveAndFlush(mainList);

        return "redirect:/mozip";
    }
}
