package org.mozip.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class DetailPhoto extends BaseEntity{
    //상세페이지 사진 업로드 엔티티

    @Id
    @GeneratedValue
    private Long photoNo;  //사진번호
    @NotNull
    private String gid;  //그룹 ID - 하나로 묶어서 사용

    private String URL;
    @NotEmpty
    private String original_file_name;  //원본 사진
    @NotEmpty
    private String stored_file_path;

    //private long file_size;
}
