package org.mozip.controllers.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class BoardData extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String content;
}

