package org.mozip.controllers.mypage;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mozip.entities.BoardData;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class MypageBoardForm {

    private Long id;
    
    private String remode;  //수정시
    
    @NotBlank
    private String subject;  //모임명

    @NotBlank
    private String content;  //모임 소개글

    public static BoardData of(MypageBoardForm mypageBoardForm){
        return new ModelMapper().map(mypageBoardForm, BoardData.class);
    }
    
}

