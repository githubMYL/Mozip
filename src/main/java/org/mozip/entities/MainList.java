package org.mozip.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Entity @Builder
@NoArgsConstructor @AllArgsConstructor
public class MainList extends BaseEntity {

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long mListNo; // 리스트 순번
    private String mImage; // 메인 이미지
    private String mName; // 모임 명
    private String mComment; // 모임 소개글

    // 외래키
    @OneToOne
    @JoinColumn(name = "email")
    private Members member;
    // 외래키
    @OneToOne
    @JoinColumn(name = "code")
    private CateCode cateCode;
}
