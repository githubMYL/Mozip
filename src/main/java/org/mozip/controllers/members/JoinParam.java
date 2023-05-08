package org.mozip.controllers.members;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mozip.entities.BaseEntity;
import org.mozip.entities.Members;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class JoinParam extends BaseEntity {
    @NotBlank @Email
    private String email;
    @NotBlank @Size(min = 8, max = 16)
    private String memberPw;
    @NotBlank
    private String memberPwRe;
    @NotBlank
    private String memberNick;
    @NotBlank
    private String memberNm;
    private String mobile;

    @AssertTrue
    private boolean agree;

    public static Members of(JoinParam joinParam) {
        return new ModelMapper().map(joinParam, Members.class);
    }
}
