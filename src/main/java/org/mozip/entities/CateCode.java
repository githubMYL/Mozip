package org.mozip.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@Entity @Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class CateCode {
    @Id
    private String code;
    private String codeName;

}
