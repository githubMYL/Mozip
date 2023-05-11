package org.mozip.controllers.board;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class BoardForm {
    private Long id;

    private String regUser;

    @NotBlank
    private String subject;

    @NotBlank
    private String content;
}
