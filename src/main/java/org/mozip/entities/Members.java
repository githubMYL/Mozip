package org.mozip.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mozip.constants.MemberType;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Members extends BaseEntity {
    @Id // 기본 키
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long memberNo; // 회원번호

    @Email
    @Column(length=40, unique = true, nullable = false)
    private String email; // 아이디(이메일)

    @Column(length=65, nullable = false)
    private String memberPw; // 비밀번호

    @Column(length=45, nullable = false)
    private String memberNm; // 회원명

    @Column(length=45)
    private String memberNick; // 닉네임

    @Column(length=11)
    private String mobile; // 휴대전화번호

    @AssertTrue
    private boolean agree;

    @Enumerated(EnumType.STRING) // 권한설정
    @Column(length=20, nullable = false)
    private MemberType type = MemberType.MEMBER;

//    @OneToMany(mappedBy = "members")
//    private List<BoardData> boardData = new ArrayList<>();
}
