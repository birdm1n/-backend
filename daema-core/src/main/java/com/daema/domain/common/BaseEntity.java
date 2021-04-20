package com.daema.domain.common;

import com.daema.domain.Member;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
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
public abstract class BaseEntity {

    @CreatedDate // 생성한 날
    @Column(name = "regi_datetime", updatable = false)
    private LocalDateTime regiDateTime;

    @CreatedBy  // 최초 생성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regi_user_id", referencedColumnName = "seq", updatable = false)
    @Audited(targetAuditMode = NOT_AUDITED) // User 테이블까지 이력을 추적하지 않겠다는 설정 필수
    private Member regiUserId;

    @LastModifiedDate  // 마지막 수정한 날
    @Column(name = "last_upd_datetime")
    private LocalDateTime lastUpdDateTime;

    @LastModifiedBy // 마지막 수정자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_upd_user_id", referencedColumnName = "seq")
    @Audited(targetAuditMode = NOT_AUDITED) // User 테이블까지 이력을 추적하지 않겠다는 설정 필수
    private Member lastUpdUserId;

    @Column(name = "del_yn")
    private boolean delYn;

}