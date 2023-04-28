package org.mozip.controllers.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mozip.entities.BoardData;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class BoardParam {

    private Long id;

    private String mode;

    @NotBlank
    @Size(min = 6)
    private String subject;

    @NotBlank
    @Size(min = 10)
    private String content;

    public static BoardData of(BoardParam boardParam) {
        return new ModelMapper().map(boardParam, BoardData.class);
    }
}
