package org.mozip.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity @Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BoardMemberEntity extends BaseEntity{

    @CreatedBy
    @Column(length = 40, updatable = false)
    private  String createdBy;

    @LastModifiedBy
    @Column(length = 40, insertable = false)
    private  String modifiedBy;

}
