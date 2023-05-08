package org.mozip.controllers.mypage;

import jakarta.validation.Valid;
import lombok.extern.java.Log;

import org.modelmapper.ModelMapper;
import org.mozip.controllers.members.JoinParam;
import org.mozip.entities.BoardData;
import org.mozip.models.member.*;
import org.mozip.models.mypages.*;
import org.mozip.repositories.BoardDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@Controller @Log
@RequestMapping("/mypage")
public class MyPageController {
    @Autowired
    private MemberInfoService infoService;
    @Autowired
    private BoardDataRepository repository;
    @Autowired
    private MypageInfoService mypageInfoService;
    @Autowired
    private MypageListService listService;
    @Autowired
    private MypageSaveService saveService;
    @Autowired
    private MypageDeleteService deleteService;



    @GetMapping("/{memberNick}") //마이페이지->회원명
    public String mypage() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberInfo member = (MemberInfo) principal;

        JoinParam joinParam = new ModelMapper().map(member, JoinParam.class);

        //log.info(" member.toString() : " + member.toString());
        return "mypage/index";
    }


    //모임생성 페이지로 이동 => 모임 생성 양식
    @GetMapping("/myadd")
    public String myadd(Model model) {

        MypageBoardForm mypageBoardForm = new MypageBoardForm();
        model.addAttribute("mypageBoardForm", mypageBoardForm);
        model.addAttribute("addScript", new String[]{"ckeditor/ckeditor", "form"});

        return "mypage/myadd";
    }


    //모임 생성 및 저장
    @PostMapping("/save")
    public String save(@Valid MypageBoardForm mypageBoardForm, Errors errors){

        try {
            saveService.save(mypageBoardForm, errors);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/mypage/index";
    }

    //업데이트
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model){

        BoardData boardData = mypageInfoService.get(id);

        MypageBoardForm mypageBoardForm = new ModelMapper().map(boardData, MypageBoardForm.class);
        model.addAttribute("mypageBoardForm", mypageBoardForm);
        model.addAttribute("addScript", new String[]{"ckeditor/ckeditor", "form"});

        return "mypage/update";
    }

    //모임 상세보기
    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Long id, Model model){

        BoardData boardData = mypageInfoService.get(id);
        model.addAttribute("boardData", boardData);

        return "mypage/view";
    }


    //모임 생성후 마이페이지로 이동 -> 모임 생성 게시판목록
    @GetMapping("/index")
    public String list(Model model){

        List<BoardData> list = listService.mylist();
        model.addAttribute("list", list);

        return "mypage/index";
    }

    //모임 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){

        deleteService.delete(id);

        return "redirect:/mypage/index";
    }


    //회원 정보 수정, 자기소개 게시글,
    @ResponseBody
    public void profile(){

    }
}
