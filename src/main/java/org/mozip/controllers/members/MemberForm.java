package org.mozip.controllers.members;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "닉네임은 필수입니다.")
    private String name;
    @NotEmpty(message = "아이디(이메일)은 필수입니다.")
    private String email;
}