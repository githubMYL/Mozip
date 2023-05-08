package org.mozip.entities;

import jakarta.persistence.*;

import lombok.*;

@Entity @Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class BoardData extends BaseEntity{

    @Id @GeneratedValue
    private Long Id;        // 게시글번호


    @Column(nullable = false)
    private String subject; // 게시글 제목

    @Lob
    @Column(nullable = false)
    private String content; // 게시글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo")
    @ToString.Exclude
    private Members member; // id_memberNo 외래키 생성

}

