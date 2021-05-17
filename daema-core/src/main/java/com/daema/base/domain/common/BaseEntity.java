package com.daema.base.domain.common;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity extends BaseUserInfoEntity {
    @NotAudited
    @Column(nullable = false, name = "del_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String delYn = "N";
}