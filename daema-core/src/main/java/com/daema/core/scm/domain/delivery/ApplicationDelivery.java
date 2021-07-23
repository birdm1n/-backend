package com.daema.core.scm.domain.delivery;


import com.daema.core.base.enums.TypeEnum;
import com.daema.core.scm.domain.application.Application;
import com.daema.core.scm.domain.vo.Address;
import com.daema.core.scm.dto.ApplicationDeliveryDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "applId")
@ToString(exclude = {"application"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "application_delivery", comment = "신청서 배송")
public class ApplicationDelivery {

    @Id
    private Long applId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_form_id", columnDefinition = "BIGINT UNSIGNED comment '신청서 아이디'")
    @MapsId
    private Application application;

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

    public static ApplicationDelivery create(Application application, ApplicationDeliveryDto delivery){
        return ApplicationDelivery.builder()
                .application(application)
                .deliveryType(delivery.getDeliveryType())
                .courier(delivery.getCourierCodeId())
                .invoiceNum(delivery.getInvoiceNum())
                .deliveryAddressAttribute(Address.create(delivery.getDeliveryZipCode(), delivery.getDeliveryAddr(), delivery.getDeliveryAddrDetail()))
                .build();
    }
}
