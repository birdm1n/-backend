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
    @Column(name = "delivery_id")
    private Long deliveryId;

    /** 택배, 퀵, 직접전달 */
    @Column(name = "delivery_type")
    @Enumerated(EnumType.STRING)
    private WmsEnum.DeliveryType deliveryType;

    //택배사 codeSeq
    @Column(name = "courier")
    private Integer courier;

    //송장번호
    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "delivery_memo")
    private String deliveryMemo;

    @Column(name = "delivery_status")
    @Enumerated(EnumType.STRING)
    private WmsEnum.DeliveryStatus deliveryStatus;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private OutStock outStock;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private MoveStock moveStock;

    @Column(name = "cus_name")
    private String cusName;

    @Column(name = "cus_phone")
    private String cusPhone;

    @Column(name = "usim_full_barcode")
    private String usimFullBarcode;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "addr1")
    private String addr1;

    @Column(name = "addr2")
    private String addr2;

    @Builder
    public Delivery(Long deliveryId, WmsEnum.DeliveryType deliveryType, Integer courier, String invoiceNo, String deliveryMemo, WmsEnum.DeliveryStatus deliveryStatus, OutStock outStock, MoveStock moveStock, String cusName, String cusPhone, String usimFullBarcode, String zipCode, String addr1, String addr2) {
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
        this.usimFullBarcode = usimFullBarcode;
        this.zipCode = zipCode;
        this.addr1 = addr1;
        this.addr2 = addr2;
    }
}
