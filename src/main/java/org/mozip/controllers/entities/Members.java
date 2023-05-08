package org.mozip.controllers.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mozip.constants.MemberType;

@Data @Entity @Builder
@NoArgsConstructor @AllArgsConstructor
public class Members extends BaseEntity {
    @Id // 기본 키
    @Column(length=40)
    private String email; // 아이디(이메일)
    @Column(length=65, nullable = false)
    private String memberPw; // 비밀번호
    @Column(length=45, nullable = false)
    private String memberNm; // 회원명
    @Column(length=45)
    private String memberNick; // 닉네임
    @Column(length=11)
    private String mobile; // 휴대전화번호

    @Enumerated(EnumType.STRING) // 권한설정
    @Column(length=20, nullable = false)
    private MemberType type = MemberType.MEMBER;
}
