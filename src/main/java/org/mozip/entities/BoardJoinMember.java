package org.mozip.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
public class BoardJoinMember {
    @Id
    private Long Id;

    @Column(nullable = false, length = 40)
    private String email;

    private String activeYn = "Y"; //활동여부

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime regDt;
}
