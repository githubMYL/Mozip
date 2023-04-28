package org.mozip.controllers.members;

import lombok.RequiredArgsConstructor;
import org.mozip.repositories.MembersRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator {

    private final MembersRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinParam.class.isAssignableFrom(clazz);
    }

    boolean mobileCheck(String mobile){
        mobile = mobile.replaceAll("\\D", "");
        String pattern = "^01[016]\\d{3,4}\\d{4}$";

        return mobile.matches(pattern);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()){ // Bean Validation 검증 실패가 있는 경우..
            return;
        }
        JoinParam joinParam = (JoinParam)target;

        String memberId = joinParam.getEmail();
        String memberPw = joinParam.getMemberPw();
        String memberPwRe = joinParam.getMemberPwRe();
        String mobile = joinParam.getMobile();

        // 1. 아이디 중복여부
        if(repository.memberExists(memberId)){
            errors.rejectValue("memberId", "Validation.duplicate.memberId");
        }

        // 2. memberPw, memberPwRe 일치 여부
        if(!memberPw.equals(memberPwRe)){
            errors.rejectValue("memberPwRe", "Validation.incorrect.memberPwRe");
        }

        // 3. 휴대전화번호(선택)가 있으면 형식체크
        if(mobile != null && !mobile.isBlank()){
            if(!mobileCheck(mobile)) { // 휴대전화번호 형식이 아닌 경우
                errors.rejectValue("mobile", "Validation.mobile");
            }

            mobile = mobile.replaceAll("\\D", "");
            joinParam.setMobile(mobile);;
        }
    }
}
