package com.daema.core.scm.domain.delivery;


import com.daema.core.base.enums.TypeEnum;
import com.daema.core.scm.domain.appform.AppForm;
import com.daema.core.scm.domain.vo.Address;
import com.daema.core.scm.dto.AppFormDeliveryDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "appFormId")
@ToString(exclude = {"appForm"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "app_form_delivery", comment = "신청서 배송")
public class AppFormDelivery {

    @Id
    private Long appFormId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_form_id", columnDefinition = "BIGINT UNSIGNED comment '신청서 아이디'")
    @MapsId
    private AppForm appForm;

    @Column(name = "delivery_type", columnDefinition = "varchar(255) comment '배송 타입'")
    @Enumerated(EnumType.STRING)
    private TypeEnum.DeliveryType deliveryType;

    //택배사 codeSeq
    @Column(name = "courier_code_id", columnDefinition = "BIGINT unsigned comment '택배사 코드 아이디'")
    private Long courier;

    //송장번호
    @Column(name = "invoice_num", columnDefinition = "varchar(255) comment '송장 번호'")
    private String invoiceNum;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "zipCode", column = @Column(name = "delivery_zip_code", columnDefinition = "varchar(255) comment '배송 우편 코드'")),
            @AttributeOverride(name = "addr", column = @Column(name = "delivery_addr", columnDefinition = "varchar(255) comment '배송 주소'")),
            @AttributeOverride(name = "addrDetail", column = @Column(name = "delivery_addr_detail", columnDefinition = "varchar(255) comment '배송 주소 상세'"))
    })
    private Address deliveryAddressAttribute;

    public static AppFormDelivery create(AppForm appForm, AppFormDeliveryDto delivery){
        return AppFormDelivery.builder()
                .appForm(appForm)
                .deliveryType(delivery.getDeliveryType())
                .courier(delivery.getCourierCodeId())
                .invoiceNum(delivery.getInvoiceNum())
                .deliveryAddressAttribute(Address.create(delivery.getDeliveryZipCode(), delivery.getDeliveryAddr(), delivery.getDeliveryAddrDetail()))
                .build();
    }
}
