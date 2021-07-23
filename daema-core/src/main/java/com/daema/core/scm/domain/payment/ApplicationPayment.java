package com.daema.core.scm.domain.payment;

import com.daema.core.base.domain.common.BaseUserInfoEntity;
import com.daema.core.scm.domain.application.Application;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.ApplicationPaymentDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "applId")
@ToString(exclude = {"application"})
@NoArgsConstructor
@AllArgsConstructor
@Entity  //  aggregate ...
@org.hibernate.annotations.Table(appliesTo = "application_payment", comment = "신청서 납부")
public class ApplicationPayment extends BaseUserInfoEntity {


    @Id
    private Long applId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appl_id", columnDefinition = "BIGINT UNSIGNED comment '신청서 아이디'")
    @MapsId
    private Application application;



    @Enumerated(EnumType.STRING)
    @Column(name = "payment_way", columnDefinition = "varchar(255) comment '납부 방법'")
    private ScmEnum.PaymentWay paymentWay;

    @Embedded
    private CardAttribute cardAttribute;

    @Embedded
    private GiroAttribute giroAttribute;

    @Embedded
    private AccountAttribute accountAttribute;

    public static ApplicationPaymentDto from(ApplicationPayment applicationPayment) {
        return ApplicationPaymentDto.builder()
                .applId(applicationPayment.getApplId())
                .paymentWay(applicationPayment.getPaymentWay())
                .accountAttribute(applicationPayment.getAccountAttribute())
                .giroAttribute(applicationPayment.getGiroAttribute())
                .cardAttribute(applicationPayment.getCardAttribute())
                .build();
    }

    public static ApplicationPayment create(Application application, ApplicationPaymentDto applicationPaymentDto) {
        

        return ApplicationPayment.builder()
                .application(application)
                .paymentWay(applicationPaymentDto.getPaymentWay())
                .accountAttribute(applicationPaymentDto.getAccountAttribute())
                .cardAttribute(applicationPaymentDto.getCardAttribute())
                .giroAttribute(applicationPaymentDto.getGiroAttribute())
                .build();
    }

    public static void update(ApplicationPayment applicationPayment, ApplicationPaymentDto applicationPaymentDto) {

        if(applicationPayment != null) {
            ScmEnum.PaymentWay paymentWay = applicationPaymentDto.getPaymentWay();
            applicationPayment.setPaymentWay(paymentWay);

            switch (paymentWay) {
                case A:
                    applicationPayment.setAccountAttribute(applicationPaymentDto.getAccountAttribute());
                    break;
                case G:
                    applicationPayment.setGiroAttribute(applicationPaymentDto.getGiroAttribute());
                    break;
                case C:
                    applicationPayment.setCardAttribute(applicationPaymentDto.getCardAttribute());
                    break;

            }
        }


    }


}

