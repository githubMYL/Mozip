package org.mozip.controllers.main;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MainCreateParam {
    @NotBlank
    private String mName; // 모임 명
    @NotBlank
    private String mComment; // 모임 소개글

    private String code;
}
