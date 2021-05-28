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
@EqualsAndHashCode(of="goodsRegReqRejectId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="goods_reg_req_reject")
public class GoodsRegReqReject {

    @Id
    @NotNull
    @Column(name = "goods_reg_req_id", columnDefinition = "BIGINT unsigned comment ''")
    private long goodsRegReqId;

    @Column(name = "reject_comment", columnDefinition = "varchar(255) comment '반려 코멘트'")
    private String rejectComment;

    @Column(name = "reject_datetime", columnDefinition = "DATETIME(6) comment '반려 날짜시간'")
    private LocalDateTime rejectDateTime;

    @Column(name = "reject_user_id", columnDefinition = "BIGINT unsigned comment '반려 유저 아이디'")
    private Long rejectUserId;

    @Builder
    public GoodsRegReqReject(long goodsRegReqId, String rejectComment, LocalDateTime rejectDateTime, Long rejectUserId){
        this.goodsRegReqId = goodsRegReqId;
        this.rejectComment = rejectComment;
        this.rejectDateTime = rejectDateTime;
        this.rejectUserId = rejectUserId;
    }
}
