package com.daema.domain;

import com.daema.domain.attr.NetworkAttribute;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="chargeRegReqId")
@ToString
@Entity
@Table(name="charge_reg_req")
public class ChargeRegReq extends ChargeBase {

    public ChargeRegReq() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charge_reg_req_id")
    private long chargeRegReqId;

    @Column(name = "req_store_id")
    private long reqStoreId;

    /**
     * 1 - 대기
     * 6 - 승인
     * 9 - 반려
     */
    @Column(name = "req_status", nullable = false)
    @ColumnDefault("1")
    private int reqStatus;

    @Transient
    private ChargeRegReqReject chargeRegReqReject;

    @Builder
    public ChargeRegReq(long chargeRegReqId, long reqStoreId, String chargeName, int chargeAmt, String category, int telecom, int network, LocalDateTime regiDateTime, int reqStatus){
        this.chargeRegReqId = chargeRegReqId;
        this.reqStoreId = reqStoreId;
        this.chargeName = chargeName;
        this.chargeAmt = chargeAmt;
        this.category = category;
        this.networkAttribute = new NetworkAttribute(telecom, network);
        this.reqStatus = reqStatus;
        this.regiDateTime = regiDateTime;
    }

    public void updateReqStatus(ChargeRegReq chargeRegReq, int reqStatus){
        chargeRegReq.setReqStatus(reqStatus);
    }

}
