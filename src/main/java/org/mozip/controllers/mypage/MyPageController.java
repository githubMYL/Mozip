package org.mozip.controllers.mypage;

import lombok.extern.java.Log;
import org.mozip.controllers.entities.BoardData;
import org.mozip.controllers.members.JoinParam;
import org.mozip.models.member.MemberInfo;
import org.mozip.models.member.MemberInfoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Member;
import java.util.List;

@Controller @Log
@RequestMapping("/mypage")
public class MyPageController {
    @GetMapping("/{memberNick}") //마이페이지->회원명
    public String myPage( Model model){

        //정보 수정






        return "mypage/index";
    }

}
