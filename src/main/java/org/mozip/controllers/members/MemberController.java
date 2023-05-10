package org.mozip.controllers.members;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.mozip.models.member.MemberJoinValidator;
import org.mozip.models.member.MemberSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberJoinValidator validator;
    private final MemberSaveService service;

    private JoinParam joinParam;
    /** 회원가입 화면이동 */
    @GetMapping("/join")
    public String join(Model model){
        commonProcess(model); // 공통 처리

        joinParam = new JoinParam();

        model.addAttribute("joinParam", joinParam);


        return "member/join";
    }

    /** 회원가입 저장 */
    @PostMapping("/join")
    public String joinPs(@Valid JoinParam joinParam, Errors errors, Model model){
        commonProcess(model); // 공통 처리

        validator.validate(joinParam, errors);

        if(errors.hasErrors()){
            return "member/join";
        }
        service.save(joinParam);
        return "redirect:/member/login";
    }

    /** 회원 로그인 처리 */
    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    private void commonProcess(Model model) {
        model.addAttribute("addScript", new String[] {"fileManager", "member/join"});
        model.addAttribute("addCss", new String[] {"member/join"});
    }

}
