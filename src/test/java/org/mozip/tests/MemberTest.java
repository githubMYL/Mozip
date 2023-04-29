package org.mozip.tests;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("회원가입 및 로그인 통합테스트")
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberTest {

    private MockMvc mockMvc;

    @Test
    @DisplayName("회원가입이 통과하면 성공")
    void joinSuccessTest() throws Exception {

    }

    @Test
    @DisplayName("필수항목이 null값이거나 조건에 충족하지 않으면 예외발생")
    void passwordFailTest() throws Exception {

        // 주석으로 내용을 써주기
    }


    @Test
    @DisplayName("로그인이 통과하면 성공")
    void loginSuccessTest() throws Exception {

    }

    @Test
    @DisplayName("로그아웃이 통과하면 성공")
    void logoutSuccessTest() throws Exception {

    }

    @Test
    @DisplayName("회원가입 후 7일 이전에 멤버전용게시물을 볼 수 없으면 통과")
    void after7dayFileSuccess() throws Exception {

    }

}
