package org.mozip.controllers.category;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Entity @Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class CateCode {
    @Id
    private String code;
    private String codeName;

}
