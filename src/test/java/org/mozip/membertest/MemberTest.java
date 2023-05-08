package org.mozip.membertest;


import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mozip.entities.Members;
import org.mozip.controllers.members.JoinParam;
import org.mozip.controllers.members.MemberController;
import org.mozip.models.member.JoinValidationException;
import org.mozip.models.member.MemberSaveService;
import org.mozip.repositories.MembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
//@DisplayName("회원가입 및 로그인 통합테스트")
@TestPropertySource(locations = "classpath:application-test.properties")
@ExtendWith(MockitoExtension.class)
@Transactional
public class MemberTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MembersRepository repository;
    @Autowired
    private MemberSaveService service;

    @Mock
    private UserDetailsService memberDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WebApplicationContext context;

    @InjectMocks
    private MemberController memberController;

    private JoinParam joinParam;
    private Members member;



    @BeforeEach
    public void init() {    // 빌드패턴으로 생성
        joinParam = JoinParam.builder()
                .email("test01@test.org")
                .memberPw("12345678")
                .memberPwRe("12345678")
                .memberNm("테스트멤버01")
                .memberNick("닉네임테스트01")
                .agree(true)
                .build();

        System.out.println("regDt :" + LocalDate.now());


        LocalDateTime currentDateTime = LocalDateTime.now();
        joinParam.setRegDt(currentDateTime.minusDays(8));

        System.out.println("7일 전 :" + joinParam.getRegDt());
    }

    @Test
    @DisplayName("회원가입이 통과하면 성공")
    @WithMockUser("test01@test.org")
    void joinSuccessTest() throws Exception {
        assertDoesNotThrow(()->{
            service.save(joinParam);
        });
    }


    @Test
    @DisplayName("필수항목이 null값이거나 조건에 충족하지 않으면 예외발생")
    @WithMockUser("test01@test.org")
    void joinNullTest() throws Exception {
        JoinValidationException exception =
                assertThrows(JoinValidationException.class, ()->{
           service.save(null);
        });
    }

    @Test
    @DisplayName("필수항목 체크 - userId")
    @WithMockUser("user01")
    void userJoinNullTest1(){
        for(int i = 0; i < 2; i++){
            if(i == 0){
                joinParam.setEmail(null);
            } else{
                joinParam.setEmail(" ");
            }
            JoinValidationException exception =
                    assertThrows(JoinValidationException.class, ()->{
                service.save(joinParam);
            });
            System.out.println(exception.getMessage());
            assertTrue(exception.getMessage().contains("이메일(아이디)를 입력하세요."));
            assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        }
    }

    @Test
    @DisplayName("memberPw 유효성 검사")
    @WithMockUser("test01@test.org")
    void memberPwTest2(){

        for(int i = 0; i < 2; i++){
            if(i == 0){
                joinParam.setMemberPw(null);
            } else{
                joinParam.setMemberPw(" ");
            }
            JoinValidationException exception =
                    assertThrows(JoinValidationException.class, ()->{
                service.save(joinParam);
            });

            System.out.println(exception.getMessage());
            assertTrue(exception.getMessage().contains("비밀번호를 확인하세요."));

            assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        }
    }

    @Test
    @DisplayName("memberPw 와 memberPwRe가 동일한지 체크")
    void pwReCheckTest() throws Exception {

        joinParam.setMemberPw("12345678");
        joinParam.setMemberPwRe("12345678");

        String response = mockMvc.perform(post("/user/join")
                        .param("Email", joinParam.getEmail())
                        .param("userPw", joinParam.getMemberPw())
                        .param("userPwRe", joinParam.getMemberPwRe())
                        .param("userNm", joinParam.getMemberNm())
                        .param("agree", String.valueOf(joinParam.isAgree())))
                        .andReturn().getResponse().getContentAsString();

        response.contains("비밀번호 확인란이 일치하지 않습니다");
    }

    @Test
    @DisplayName("memberNm이 공백이거나 null이면 예외발생")
    void memberNameTest() throws Exception {
        System.out.println(">>>"+joinParam.toString());

        if(StringUtils.isBlank(joinParam.getMemberNm())){
            System.out.println("이름은 필수값 입니다.");
        }else{
            System.out.println("이름값이 존재 합니다.");
        }

        service.save(joinParam);
    }



    @Test
    @DisplayName("회원가입 후 7일 이전에 멤버전용게시물을 볼 수 없으면 통과")
    void after7dayFileSuccess() throws Exception {
        service.save(joinParam);

        LocalDateTime currentDateTime = LocalDateTime.now();
        joinParam.setRegDt(currentDateTime.minusDays(8));
        Members info = service.getData(joinParam);
        info.setRegDt(joinParam.getRegDt());
        System.out.println("DB에 저장된 값 확인 : " + info.getRegDt());


        long day = ChronoUnit.DAYS.between(info.getRegDt(), LocalDateTime.now());

        if(day > 7){
            System.out.println("게시글 열람 권한 OK.");
        }else{
            System.out.println("일주일이전으로 게시글 조회권한이 없습니다");
        }
    }

    @Test
    @DisplayName("로그인 성공시 redirect로 이동")
    @WithMockUser("test01@test.org")
    void LoginTest() throws Exception {
        String email = "test01@test.org";
        String password = "12345678";

        service.save(joinParam);

        mockMvc.perform(formLogin("/user/login")
                .user("email", email)
                .password("userPw", password))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("다른 아이디일 경우 에러메세지 출력")
    @WithMockUser("test01@test.org")
    void LoginTest2() throws Exception {
        String email = "test02@test.org";
        String password = "12345678";

        mockMvc.perform(formLogin("/member/login")
                .user("email", email)
                .password("memberPw", password))
                .andDo(print())
                .andExpect(request().sessionAttribute("message", "아이디 및 비밀번호를 확인하세요."));
    }

}
