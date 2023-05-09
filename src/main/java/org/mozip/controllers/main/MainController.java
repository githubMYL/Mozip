package org.mozip.controllers.main;

import lombok.extern.java.Log;
import org.mozip.commons.MemberUtil;
import org.mozip.models.member.MemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mozip")
@Log
public class MainController {

    @Autowired
    private MemberUtil memberUtil;
    @GetMapping
    public String main(Model model){

        MemberInfo memberInfo = memberUtil.getMember();
        if(memberInfo != null){
            String memberNick = memberInfo.getMemberNick();
            System.out.println("memberNick ::::::::::::: " + memberInfo.getMemberNick());
            model.addAttribute("memberNick", memberNick);
        }
        model.addAttribute("addCss", new String[] {"main/style"});

        return "mozip";

    }
    public String mypageMove(Model model){

        return "/mypage/index";
    }
}
