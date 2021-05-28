package com.daema.commgmt.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="chargeRegReqRejectId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="add_service_reg_req_reject")
public class AddServiceRegReqReject {

    @Id
    @NotNull
    @Column(name = "add_svc_reg_req_id", columnDefinition = "BIGINT UNSIGNED comment '부가 서비스 등록 요청 아이디'")
    private long addSvcRegReqId;

    @Column(name = "reject_comment", columnDefinition = "varchar(255) comment '반려 의견'")
    private String rejectComment;

    @Column(name = "reject_datetime", columnDefinition = "DATETIME(6) comment '반려 날짜시간'")
    private LocalDateTime rejectDateTime;

    @Column(name = "reject_user_id", columnDefinition = "BIGINT UNSIGNED comment '반려 유저 아이디'")
    private Long rejectUserId;

    @Builder
    public AddServiceRegReqReject(long addSvcRegReqId, String rejectComment, LocalDateTime rejectDateTime, Long rejectUserId){
        this.addSvcRegReqId = addSvcRegReqId;
        this.rejectComment = rejectComment;
        this.rejectDateTime = rejectDateTime;
        this.rejectUserId = rejectUserId;
    }
}
