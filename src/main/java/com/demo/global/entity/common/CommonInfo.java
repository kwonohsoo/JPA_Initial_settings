package com.demo.global.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CommonInfo {

    @ColumnDefault("0")
    @Comment("사용 여부 0:사용, 9:삭제")
    @Column(name = "use_yn")
    private int useYn;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP", name = "created_dt")
    @Comment("생성일")
    private LocalDateTime createdDt;

    @LastModifiedDate
    @Column(columnDefinition = "TIMESTAMP", name = "modified_dt")
    private LocalDateTime modifiedDt;
}