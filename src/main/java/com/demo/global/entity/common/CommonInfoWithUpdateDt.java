package com.demo.global.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CommonInfoWithUpdateDt extends CommonInfo {

    @LastModifiedDate
    @Column(columnDefinition = "TIMESTAMP", name = "updated_dt")
    @Comment("수정일")
    private LocalDateTime updatedDt;

}