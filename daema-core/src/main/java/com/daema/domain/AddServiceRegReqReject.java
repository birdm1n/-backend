package com.daema.domain;

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
    @Column(name = "add_svc_reg_req_id")
    private long addSvcRegReqId;

    @Column(name = "reject_comment")
    private String rejectComment;

    @Column(name = "reject_datetime")
    private LocalDateTime rejectDateTime;

    @Column(name = "reject_user_id")
    private Long rejectUserId;

    @Builder
    public AddServiceRegReqReject(long addSvcRegReqId, String rejectComment, LocalDateTime rejectDateTime, Long rejectUserId){
        this.addSvcRegReqId = addSvcRegReqId;
        this.rejectComment = rejectComment;
        this.rejectDateTime = rejectDateTime;
        this.rejectUserId = rejectUserId;
    }
}
