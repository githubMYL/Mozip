package org.mozip.controllers.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class BoardData {

    @Id @GeneratedValue
    private Long Id;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String content;
}
