package org.mozip.commons;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mozip.constants.MemberType;
import org.mozip.entities.Members;
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
     * 관리자 여부 체크
     *
     * @return
     */
    public boolean isAdmin() {
        Members members = getEntity();

        if (members != null && members.getType() == MemberType.ADMIN) {
            return true;
        }

        return false;
    }

    /**
     * 회원번호와 로그인한 회원번호가 일치 체크
     *
     * @param memberNo
     * @return
     */
    public boolean isMine(Long memberNo) {

       return isLogin() && getMember().getMemberNo() == memberNo;
    }

    /**
     * 이메일과 로그인회원의 이메일이 일치 체크
     *
     * @param email
     * @return
     */
    public boolean isMine(String email) {
        return isLogin() && getMember().getEmail().equals(email);
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
