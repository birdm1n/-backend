package com.daema.core.commgmt.domain;

import com.daema.core.base.enums.TypeEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="addSvcRegReqId")
@ToString
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "add_service_reg_req", comment = "부가 서비스 등록 요청")
public class AddServiceRegReq extends AddServiceBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "add_svc_reg_req_id", columnDefinition = "BIGINT UNSIGNED comment '부가 서비스 등록 요청 아이디'")
    private long addSvcRegReqId;

    @Column(name = "req_store_id", columnDefinition = "BIGINT UNSIGNED comment '요청 관리점 아이디'")
    private long reqStoreId;

    /**
     * 1 - 대기
     * 6 - 승인
     * 9 - 반려
     */
    @Column(name = "req_status", nullable = false, columnDefinition = "varchar(255) comment '요청 상태' default 1")
    private String reqStatus;

    @Transient
    private AddServiceRegReqReject addServiceRegReqReject;

    @Transient
    private String reqStoreName;

    @Builder
    public AddServiceRegReq(long addSvcRegReqId, long reqStoreId, String addSvcName, int addSvcCharge, Long telecom
            ,TypeEnum.AddSvcType addSvcType, LocalDateTime regiDateTime, String addSvcMemo, String reqStatus, String telecomName
            ,String reqStoreName) {
        this.addSvcRegReqId = addSvcRegReqId;
        this.addSvcName = addSvcName;
        this.addSvcCharge = addSvcCharge;
        this.telecom = telecom;
        this.addSvcType = addSvcType;
        this.reqStoreId = reqStoreId;
        this.reqStatus = reqStatus;
        this.addSvcMemo = addSvcMemo;
        this.regiDateTime = regiDateTime;
        this.telecomName = telecomName;
        this.reqStoreName = reqStoreName;
    }

    public void updateReqStatus(AddServiceRegReq addServiceRegReq, String reqStatus){
        addServiceRegReq.setReqStatus(reqStatus);
    }

}
