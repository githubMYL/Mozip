package org.mozip.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mozip.controllers.board.BoardParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("게시글 저장 단위 및 통합 테스트")
@TestPropertySource(locations = "classpath:application-test.properties")
public class BoardSaveTest {

    @Autowired
    private MockMvc mockMvc;

    private BoardParam boardParam;

    @BeforeEach
    void init() {
        boardParam = new BoardParam();
        boardParam.setSubject("제목 테스트");
        boardParam.setContent("내용 테스트");
    }

    @Test
    @DisplayName("게시물 전체 조회")
    void saveSuccessInfoAll() throws Exception {
        mockMvc.perform(get("/mozip/board/infoall"))
                .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("게시물 상세조회")
    void saveSuccessInfo() throws Exception {
        
    }
    
    @Test
    @DisplayName("게시글이 정상적으로 등록, 수정되면 예외가 발생하지 않음")
    void saveSuccessUpdate() throws Exception {
        String content = String.format("{\"subject\":\"%s\",\"content\":\"%s\"}"
                            , boardParam.getSubject()
                            , boardParam.getContent());
        
        mockMvc.perform(post("/mozip/board/write")
                .content(content).contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시글 삭제되면 성공")
    void saveDeleteSuccess() throws Exception {

    }
}
