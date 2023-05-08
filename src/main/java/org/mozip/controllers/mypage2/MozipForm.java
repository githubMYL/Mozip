package org.mozip.controllers.mypage2;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class MozipForm {

    private String mode;
    private Long id; // 등록번호

    private String gid = UUID.randomUUID().toString(); // 그룹 ID

    @NotBlank
    private String title; // 모임명

    @NotBlank
    private String description; // 모임설명
}
