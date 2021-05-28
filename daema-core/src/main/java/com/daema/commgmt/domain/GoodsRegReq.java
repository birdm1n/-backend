package com.daema.commgmt.domain;

import com.daema.commgmt.domain.attr.NetworkAttribute;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="goodsRegReqId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="goods_reg_req")
public class GoodsRegReq extends GoodsBase{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_reg_req_id", columnDefinition = "BIGINT unsigned comment '상품 등록 요청 아이디'")
    private long goodsRegReqId;

    @Column(name = "req_store_id" , columnDefinition = "BIGINT unsigned comment '요청 관리자 아이디'")
    private long reqStoreId;

    /**
     * 1 - 대기
     * 6 - 승인
     * 9 - 반려
     */
    @Column(name = "req_status", nullable = false, columnDefinition = "INT comment '요청 상태' default 1")
    @ColumnDefault("1")
    private int reqStatus;

    @Transient
    private GoodsRegReqReject goodsRegReqReject;

    @Transient
    private String reqStoreName;

    @Builder
    public GoodsRegReq(long goodsRegReqId, long reqStoreId, String goodsName, String modelName
            , int maker, int telecom, int network, LocalDateTime regiDateTime, int reqStatus
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

    public void updateReqStatus(GoodsRegReq goodsRegReq, int reqStatus){
        goodsRegReq.setReqStatus(reqStatus);
    }

}
