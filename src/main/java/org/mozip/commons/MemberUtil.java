package org.mozip.commons;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mozip.controllers.entities.Members;
import org.mozip.models.member.MemberInfo;
import org.springframework.stereotype.Component;

/**
 * 회원관련 편의 기능
 */
@Component
@RequiredArgsConstructor
public class MemberUtil {
    private final HttpSession session;

    /**
     * 로그인여부 체크
     * @return
     */
    public boolean isLogin() {

        return getMember() != null;
    }

    /**
     * 로그인한 회원 정보 조회
     * @return
     */
    public MemberInfo getMember() {
        MemberInfo memberInfo = (MemberInfo) session.getAttribute("memberInfo");

        return memberInfo;
    }

    public Members getEntity() {
        if (!isLogin())
            return null;

        return new ModelMapper().map(getMember(), Members.class);
    }
}
