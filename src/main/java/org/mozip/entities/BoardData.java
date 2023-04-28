package org.mozip.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @Entity
@NoArgsConstructor @AllArgsConstructor
public class BoardData extends BaseEntity {


    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Size(min = 4)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String content;

}
