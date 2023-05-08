package org.mozip.controllers.main;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.modelmapper.ModelMapper;

import org.mozip.entities.MainList;
import org.mozip.entities.Members;
import org.mozip.controllers.members.JoinParam;

@Data
public class MainCreateParam {
    @NotBlank
    private String mName; // 모임 명
    @NotBlank
    private String mComment; // 모임 소개글
    @NotNull
    private String code; // 카테고리 코드

    public static MainList of(MainCreateParam mainCreateParam) {
        MainList mainList = new ModelMapper().map(mainCreateParam, MainList.class);

        return mainList;
    }
}
