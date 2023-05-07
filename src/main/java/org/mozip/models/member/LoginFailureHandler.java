package org.mozip.models.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.ResourceBundle;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        String message = exception.getMessage();
//        System.out.println(message);

        // 메시지 조회 :: ResourceBundle ** validations.properties 에 있는 메시지를 가져옴.
        ResourceBundle bundle = ResourceBundle.getBundle("messages.validations");
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String memberPw = request.getParameter("memberPw");

        session.removeAttribute("field");
        session.removeAttribute("message");
        session.removeAttribute("email");
        try {
            if(email == null || email.isBlank()) {
                throw new LoginValidationException(bundle.getString("NotBlank.email"), "email");
            }

            if(memberPw == null || memberPw.isBlank()) {
                throw new LoginValidationException(bundle.getString("NotBlank.memberPw"), "memberPw");
            }
            throw new LoginValidationException(bundle.getString("Validation.incorrect.login"), "global");
        }catch (LoginValidationException e) {
            String field = e.getField(); // 어떤 항목에서검증 실패
            String message = e.getMessage();

            session.setAttribute("field", field);
            session.setAttribute("message", message);
        }
        session.setAttribute("email", email);

        String url = request.getContextPath() + "/member/login";
        response.sendRedirect(url);
    }
}
