package com.daema.core.base.domain.common;

import com.daema.core.base.domain.Members;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseUserInfoEntity {
    @NotAudited
    @CreatedDate // 생성한 날
    @Column(name = "regi_datetime", updatable = false, columnDefinition = "DATETIME(6) comment '등록 일시'" )
    private LocalDateTime regiDateTime;

    @NotAudited
    @CreatedBy  // 최초 생성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regi_user_id", referencedColumnName = "member_id", updatable = false, columnDefinition = "BIGINT UNSIGNED comment '등록 유저 아이디'")
    @Audited(targetAuditMode = NOT_AUDITED) // User 테이블까지 이력을 추적하지 않겠다는 설정 필수
    private Members regiUserId;

    @LastModifiedDate  // 마지막 수정한 날
    @Column(name = "upd_datetime", columnDefinition = "DATETIME(6) comment '수정 일시'")
    private LocalDateTime updDateTime;

    @LastModifiedBy // 마지막 수정자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upd_user_id", referencedColumnName = "member_id", columnDefinition = "BIGINT UNSIGNED comment '수정 유저 아이디'")
    @Audited(targetAuditMode = NOT_AUDITED) // User 테이블까지 이력을 추적하지 않겠다는 설정 필수
    private Members updUserId;
}