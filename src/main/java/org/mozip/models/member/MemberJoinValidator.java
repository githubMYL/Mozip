package org.mozip.models.member;

import lombok.RequiredArgsConstructor;
import org.mozip.commons.validators.EmailValidator;
import org.mozip.commons.validators.MobileValidator;
import org.mozip.commons.validators.RequiredValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.mozip.controllers.members.JoinParam;
import org.mozip.repositories.MembersRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberJoinValidator implements Validator, MobileValidator, EmailValidator, RequiredValidator {

    private final MembersRepository repository;


    public boolean supports(Class<?>clazz) {
        return JoinParam.class.isAssignableFrom(clazz);
    }

    public void validate(Object target){
        validate(target, null);
    }

    @Override
    public void validate(Object target, Errors errors) {
        /** Bean Validation 기본 에러 검사 */
        if(errors != null && errors.hasErrors()) {
            return;
        }
        JoinParam joinParam = (JoinParam) target;

        nullCheck(joinParam, new JoinValidationException("잘못된 접근입니다."));

        /** Email 중복 검증 */
        String email = joinParam.getEmail();
        /** 비밀번호 검증 */
        String memberPw = joinParam.getMemberPw();
        /** 비밀번호 확인 */
        String memberPwRe = joinParam.getMemberPwRe();

        String memberNm = joinParam.getMemberNm();
        /** 휴대전화 검증*/
        String mobile = joinParam.getMobile();

        requiredCheck(email, new JoinValidationException("이메일(아이디)를 입력하세요."));

        requiredCheck(memberPw, new JoinValidationException("비밀번호를 확인하세요."));

        requiredCheck(memberPwRe, new JoinValidationException("비밀번호와 일치하지 않습니다"));

        requiredCheck(memberNm, new JoinValidationException("이름을 입력하세요."));

        if (repository.memberExists(email)) {
            errors.rejectValue("email", "Validation.duplicate.email");
        }

        if (!memberPw.equals(memberPwRe)) {
            errors.rejectValue("memberPwRe", "Validation.incorrect.memberPwRe");

        }

        if (mobile != null && !mobile.isBlank()) {
            if (!mobileCheck(mobile)) {
                errors.rejectValue("mobile", "Validation.mobile");
            }

            mobile = mobile.replace("\\D", "");
            joinParam.setMobile(mobile);
        }

        if (email != null && !email.isBlank()) {
            if (!emailCheck(email)){
                errors.rejectValue("email", "Validation.email");
            }
        }
    }
}
