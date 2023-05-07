package org.mozip.models.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Log
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();
        /** 세션 지우기 */
        session.removeAttribute("email");
        session.removeAttribute("memberPw");
        session.removeAttribute("field");
        session.removeAttribute("message");

        /** 쿠키저장 */
        String saveEmail = request.getParameter("saveEmail");
        String email = request.getParameter("email");
        Cookie cookie = new Cookie("saveEmail", email);
        if(saveEmail == null) { // 쿠키 삭제
            cookie.setMaxAge(0);
        }else { // 쿠키 등록
            cookie.setMaxAge(60 * 60 * 24 * 365);
        }

        /**
         * 로그인 정보 세션 유지 - 간편하게 회원 정보 조회 목작
         */

        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
        session.setAttribute("memberInfo", memberInfo);

        String url = request.getContextPath() + "/mozip";
        response.sendRedirect(url);

    }
}