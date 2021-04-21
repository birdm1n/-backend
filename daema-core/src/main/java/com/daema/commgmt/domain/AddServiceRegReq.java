package com.daema.commgmt.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="addSvcRegReqId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="add_service_reg_req")
public class AddServiceRegReq extends AddServiceBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "add_svc_reg_req_id")
    private long addSvcRegReqId;

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
    private AddServiceRegReqReject addServiceRegReqReject;

    @Transient
    private String reqStoreName;

    @Builder
    public AddServiceRegReq(long addSvcRegReqId, long reqStoreId, String addSvcName, int addSvcCharge, int telecom
            ,LocalDateTime regiDateTime, String addSvcMemo, int reqStatus, String telecomName
            ,String reqStoreName) {
        this.addSvcRegReqId = addSvcRegReqId;
        this.addSvcName = addSvcName;
        this.addSvcCharge = addSvcCharge;
        this.telecom = telecom;
        this.reqStoreId = reqStoreId;
        this.reqStatus = reqStatus;
        this.addSvcMemo = addSvcMemo;
        this.regiDateTime = regiDateTime;
        this.telecomName = telecomName;
        this.reqStoreName = reqStoreName;
    }

    public void updateReqStatus(AddServiceRegReq addServiceRegReq, int reqStatus){
        addServiceRegReq.setReqStatus(reqStatus);
    }

}
