package org.mozip.models.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        /**
         * 성공 시 유입되는 핸들러
         * 1. 실패 시 저장된 세션 지우기
         * 2. 아이디 저장 - 쿠키 저장
         * 3. 성공 시 이동할 URL
          */
        HttpSession session = request.getSession();
        /** 세션 지우기 START */
        session.removeAttribute("email");
        session.removeAttribute("memberPw");
        session.removeAttribute("field");
        session.removeAttribute("message");
        /** 세션 지우기 END */

        String saveEmail = request.getParameter("saveEmail");
        String email = request.getParameter("email");
        Cookie cookie = new Cookie("saveEmail", email);
        if(saveEmail == null) { // 쿠키 삭제
            cookie.setMaxAge(0);
        }else { // 쿠키 등록
            cookie.setMaxAge(60 * 60 * 24 * 365);
        }


        String url = request.getContextPath();
        response.sendRedirect(url);

    }
}
