package org.mozip.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    // 등록일자
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime regDt;

    // 수정일자
    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = false)
    @LastModifiedDate
    private LocalDateTime modDt;

    // 삭제유무
    @Column(length = 1, nullable = false)
    private String delYn = "N";
}
