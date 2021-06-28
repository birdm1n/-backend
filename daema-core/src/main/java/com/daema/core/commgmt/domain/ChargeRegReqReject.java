package com.daema.core.commgmt.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="chargeRegReqRejectId")
@ToString
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "charge_reg_req_reject", comment = "요금 등록 요청 거절")
public class ChargeRegReqReject {

    @Id
    @NotNull
    @Column(name = "charge_reg_req_id", columnDefinition = "BIGINT UNSIGNED comment '요금 등록 요청 아이디'")
    private long chargeRegReqId;

    @Column(name = "reject_comment", columnDefinition = "varchar(255) comment '반려 코멘트'")
    private String rejectComment;

    @Column(name = "reject_datetime", columnDefinition = "DATETIME(6) comment '반려 일시'")
    private LocalDateTime rejectDateTime;

    @Column(name = "reject_user_id", columnDefinition = "BIGINT UNSIGNED comment '반려 유저 아이디'")
    private Long rejectUserId;

    @Builder
    public ChargeRegReqReject(long chargeRegReqId, String rejectComment, LocalDateTime rejectDateTime, Long rejectUserId){
        this.chargeRegReqId = chargeRegReqId;
        this.rejectComment = rejectComment;
        this.rejectDateTime = rejectDateTime;
        this.rejectUserId = rejectUserId;
    }
}
