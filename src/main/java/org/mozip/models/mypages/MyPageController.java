package org.mozip.models.mypages;

import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;

import org.modelmapper.ModelMapper;
import org.mozip.controllers.entities.BoardData;
import org.mozip.controllers.entities.Members;
import org.mozip.controllers.members.JoinParam;
import org.mozip.models.member.MemberInfo;
import org.mozip.models.member.MemberInfoService;
import org.mozip.repositories.BoardDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.List;


@Controller @Log
@RequestMapping("/mypage")
public class MyPageController {
    @Autowired
    private MemberInfoService infoService;
    @Autowired
    private BoardDataRepository repository;

    private MyPageService service;

    @GetMapping("/{memberNick}") //마이페이지->회원명
    public String myInfo() {
        //회원 정보 조회
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberInfo member = (MemberInfo) principal;
        System.out.println("userInfo : " + member);
        return "mypage/index";
    }

    public String infoUpdate(){
        service.infoUpdate();
       return "mypage/infoUpload";
    }

    //회원 정보 수정, 자기소개 게시글,
    @ResponseBody
    public void profile(){
        BoardData intro = BoardData.builder()
                .content("자기소개 글")
                .build();

        intro = repository.saveAndFlush(intro);

        intro.setContent("자기소개 글 수정");

        repository.flush();

    }


}
