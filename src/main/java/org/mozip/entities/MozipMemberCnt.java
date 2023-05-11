package org.mozip.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class MozipMemberCnt extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; // 등록번호
    @NotBlank
    private String email;
}
