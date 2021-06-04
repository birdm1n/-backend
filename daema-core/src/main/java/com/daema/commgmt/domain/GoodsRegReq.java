package com.daema.commgmt.domain;

import com.daema.commgmt.domain.attr.NetworkAttribute;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="goodsRegReqId")
@ToString
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "goods_reg_req", comment = "상품 등록 요청")
public class GoodsRegReq extends GoodsBase{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_reg_req_id", columnDefinition = "BIGINT unsigned comment '상품 등록 요청 아이디'")
    private long goodsRegReqId;

    @Column(name = "req_store_id" , columnDefinition = "BIGINT unsigned comment '요청 관리점 아이디'")
    private long reqStoreId;

    /**
     * 1 - 대기
     * 6 - 승인
     * 9 - 반려
     */
    @Column(name = "req_status", nullable = false, columnDefinition = "varchar(255) comment '요청 상태' default 1")
    private String reqStatus;

    @Transient
    private GoodsRegReqReject goodsRegReqReject;

    @Transient
    private String reqStoreName;

    @Builder
    public GoodsRegReq(long goodsRegReqId, long reqStoreId, String goodsName, String modelName
            , Long maker, Long telecom, Long network, LocalDateTime regiDateTime, String reqStatus
            , String makerName, String networkName, String telecomName
            ,String reqStoreName){
        this.goodsRegReqId = goodsRegReqId;
        this.reqStoreId = reqStoreId;
        this.goodsName = goodsName;
        this.modelName = modelName;
        this.maker = maker;
        this.networkAttribute = new NetworkAttribute(telecom, network);
        this.reqStatus = reqStatus;
        this.regiDateTime = regiDateTime;
        this.makerName = makerName;
        this.networkName = networkName;
        this.telecomName = telecomName;
        this.reqStoreName = reqStoreName;
    }

    public void updateReqStatus(GoodsRegReq goodsRegReq, String reqStatus){
        goodsRegReq.setReqStatus(reqStatus);
    }

}
