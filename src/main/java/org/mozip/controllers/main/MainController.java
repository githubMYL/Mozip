package org.mozip.controllers.main;

import lombok.extern.java.Log;
import org.mozip.models.member.MemberInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Member;

@Controller
@RequestMapping("/mozip")
@Log
public class MainController {
    @GetMapping
    public String main(Model model){

        try{
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            MemberInfo memberInfo = (MemberInfo)principal;
            if(memberInfo != null){
                String memberNick = memberInfo.getMemberNick();
                System.out.println("memberNick ::::::::::::: " + memberInfo.getMemberNick());
                model.addAttribute("memberNick", memberNick);
            }
            return "mozip";
        } catch(ClassCastException e){
            return "mozip";
        }
    }
}
