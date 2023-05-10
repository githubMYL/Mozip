package org.mozip.entities;

import jakarta.persistence.*;

import lombok.*;
import org.mozip.repositories.MozipRepository;

import java.util.List;



@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Mozip extends BaseEntity {



    @Id @GeneratedValue
    private Long id; // 등록번호

    @Column(length=40, nullable = false)
    private String gid; // 그룹 ID - 이미지용

    @Column(nullable=false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name="memberNo")
    private Members member;

    @Transient
    private List<FileInfo> mainPhotos;

    @Transient
    private List<FileInfo> editorPhotos;

    @Transient
    private boolean editable;
}
