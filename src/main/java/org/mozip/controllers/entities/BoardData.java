package org.mozip.controllers.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class BoardData extends BaseEntity{

    @Id @GeneratedValue
    private Long Id;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String content;
}
