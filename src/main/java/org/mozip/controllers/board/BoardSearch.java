package org.mozip.controllers.board;

import lombok.Data;

@Data
public class BoardSearch {

    private int page = 1;
    private int limit = 20;

    private String sopt; // 검색 옵션
    private String skey; // 검색 키워드

    private String sort; // 정렬 조건

    private String email; // 회원 이메일
    private String memberNm; // 회원명

}
