package com.daema.commgmt.domain;

import com.daema.commgmt.domain.attr.NetworkAttribute;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="chargeRegReqId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="charge_reg_req")
public class ChargeRegReq extends ChargeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charge_reg_req_id", columnDefinition = "BIGINT UNSIGNED comment '요금 등록 요청 아이디'")
    private long chargeRegReqId;

    @Column(name = "req_store_id", columnDefinition = "BIGINT UNSIGNED comment '요청 관리점 아이디'")
    private long reqStoreId;

    /**
     * 1 - 대기
     * 6 - 승인
     * 9 - 반려
     */
    @Column(name = "req_status", nullable = false, columnDefinition = "INT comment '요청 상태' default 1")
    private int reqStatus;

    @Transient
    private ChargeRegReqReject chargeRegReqReject;

    @Transient
    private String reqStoreName;

    @Builder
    public ChargeRegReq(long chargeRegReqId, long reqStoreId, String chargeName, int chargeAmt, String category
            , int telecom, int network, String chargeCode, LocalDateTime regiDateTime, int reqStatus
            , String makerName, String networkName, String telecomName
            ,String reqStoreName){
        this.chargeRegReqId = chargeRegReqId;
        this.reqStoreId = reqStoreId;
        this.chargeName = chargeName;
        this.chargeAmt = chargeAmt;
        this.category = category;
        this.networkAttribute = new NetworkAttribute(telecom, network);
        this.chargeCode = chargeCode;
        this.reqStatus = reqStatus;
        this.regiDateTime = regiDateTime;
        this.makerName = makerName;
        this.networkName = networkName;
        this.telecomName = telecomName;
        this.reqStoreName = reqStoreName;
    }

    public void updateReqStatus(ChargeRegReq chargeRegReq, int reqStatus){
        chargeRegReq.setReqStatus(reqStatus);
    }

}
