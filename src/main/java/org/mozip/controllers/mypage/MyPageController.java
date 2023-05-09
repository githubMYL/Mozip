package org.mozip.controllers.mypage;
import groovy.util.logging.Log;
import lombok.RequiredArgsConstructor;
import org.mozip.models.member.MemberInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.nio.file.Paths;


@Controller
@Log
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
    @Value("${fileupload.path}")
    private String fileUploadPath;

    @GetMapping("/{memberNick}") //마이페이지->회원명
    public String myInfo(Model model){
        //회원 정보 조회
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberInfo member = (MemberInfo) principal;
        System.out.println("userInfo : " + member);

        model.addAttribute("profilePath", Paths.get(fileUploadPath.replace("**",fileUploadPath).toString()));
        model.addAttribute("member",member);

        return "mypage/index";
    }

}
