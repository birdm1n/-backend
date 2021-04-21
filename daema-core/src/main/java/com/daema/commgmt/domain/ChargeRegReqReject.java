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
@Table(name="charge_reg_req_reject")
public class ChargeRegReqReject {

    @Id
    @NotNull
    @Column(name = "charge_reg_req_id")
    private long chargeRegReqId;

    @Column(name = "reject_comment")
    private String rejectComment;

    @Column(name = "reject_datetime")
    private LocalDateTime rejectDateTime;

    @Column(name = "reject_user_id")
    private Long rejectUserId;

    @Builder
    public ChargeRegReqReject(long chargeRegReqId, String rejectComment, LocalDateTime rejectDateTime, Long rejectUserId){
        this.chargeRegReqId = chargeRegReqId;
        this.rejectComment = rejectComment;
        this.rejectDateTime = rejectDateTime;
        this.rejectUserId = rejectUserId;
    }
}
