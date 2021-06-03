package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of = "deliveryId")
@ToString(exclude = {"outStock", "moveStock"})
@NoArgsConstructor
@Entity
@Table(name = "delivery")
public class Delivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id", columnDefinition = "BIGINT UNSIGNED comment '배송 아이디'")
    private Long deliveryId;

    /** 택배, 퀵, 직접전달 */
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type", columnDefinition = "varchar(255) comment '배송 타입'")
    private WmsEnum.DeliveryType deliveryType;

    //택배사 codeSeq
    @Column(name = "courier_code_id", columnDefinition = "INT comment '택배사 코드 아이디'")
    private Integer courier;

    //송장번호
    @Column(name = "invoice_no", columnDefinition = "varchar(255) comment '송장 번호'")
    private String invoiceNo;

    @Column(name = "delivery_memo", columnDefinition = "varchar(255) comment '배송 메모'")
    private String deliveryMemo;


    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status", columnDefinition = "varchar(255) comment '배송 상태'")
    private WmsEnum.DeliveryStatus deliveryStatus;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private OutStock outStock;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private MoveStock moveStock;

    @Column(name = "cus_name", columnDefinition = "varchar(255) comment '고객 이름'")
    private String cusName;

    @Column(name = "cus_phone", columnDefinition = "varchar(255) comment '고객 연락처'")
    private String cusPhone;

    @Column(name = "cus_phone1", columnDefinition = "varchar(255) comment '고객 연락처1'")
    private String cusPhone1;

    @Column(name = "cus_phone2", columnDefinition = "varchar(255) comment '고객 연락처2'")
    private String cusPhone2;

    @Column(name = "cus_phone3", columnDefinition = "varchar(255) comment '고객 연락처3'")
    private String cusPhone3;

    @Column(name = "usim_full_barcode", columnDefinition = "varchar(255) comment '유심 전체 바코드'")
    private String usimFullBarcode;

    @Column(name = "delivery_zip_code", columnDefinition = "varchar(255) comment '우편번호'")
    private String zipCode;

    @Column(name = "delivery_addr", columnDefinition = "varchar(255) comment '배송 주소'")
    private String addr1;

    @Column(name = "delivery_addr_detail", columnDefinition = "varchar(255) comment '배송 주소 상세'")
    private String addr2;

    @Builder
    public Delivery(Long deliveryId, WmsEnum.DeliveryType deliveryType, Integer courier, String invoiceNo, String deliveryMemo, WmsEnum.DeliveryStatus deliveryStatus, OutStock outStock, MoveStock moveStock, String cusName
            , String cusPhone, String cusPhone1, String cusPhone2, String cusPhone3
            , String usimFullBarcode, String zipCode, String addr1, String addr2) {
        this.deliveryId = deliveryId;
        this.deliveryType = deliveryType;
        this.courier = courier;
        this.invoiceNo = invoiceNo;
        this.deliveryMemo = deliveryMemo;
        this.deliveryStatus = deliveryStatus;
        this.outStock = outStock;
        this.moveStock = moveStock;
        this.cusName = cusName;
        this.cusPhone = cusPhone;
        this.cusPhone1 = cusPhone1;
        this.cusPhone2 = cusPhone2;
        this.cusPhone3 = cusPhone3;
        this.usimFullBarcode = usimFullBarcode;
        this.zipCode = zipCode;
        this.addr1 = addr1;
        this.addr2 = addr2;
    }

}
