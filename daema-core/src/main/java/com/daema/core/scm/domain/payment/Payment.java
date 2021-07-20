package com.daema.core.scm.domain.payment;

import com.daema.core.scm.domain.appform.AppForm;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.PaymentDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "paymentId")
@NoArgsConstructor
@AllArgsConstructor
@Entity  //  aggregate ...
@org.hibernate.annotations.Table(appliesTo = "payment", comment = "납부")
public class Payment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", columnDefinition = "BIGINT UNSIGNED comment '납부 아이디'")
    private Long paymentId;


    @Enumerated(EnumType.STRING)
    @Column(name = "payment_way", columnDefinition = "varchar(255) comment '납부 방법'")
    private ScmEnum.PaymentWay paymentWay;

    @Embedded
    private Card card;

    @Embedded
    private Giro giro;

    @Embedded
    private Account account;

    @OneToOne(mappedBy = "payment")
    private AppForm appForm;

    public static PaymentDto from(Payment payment) {
        return PaymentDto.builder()
                .paymentId(payment.getPaymentId())
                .paymentWay(payment.getPaymentWay())
                .account(payment.getAccount())
                .giro(payment.getGiro())
                .card(payment.getCard())
                .build();
    }

    public static Payment create(PaymentDto paymentDto) {
        

        return Payment.builder()
                .paymentId(paymentDto.getPaymentId())
                .paymentWay(paymentDto.getPaymentWay())
                .account(paymentDto.getAccount())
                .card(paymentDto.getCard())
                .giro(paymentDto.getGiro())
                .build();
    }

    public static void update(Payment payment, PaymentDto paymentDto) {

        if(payment != null) {
            ScmEnum.PaymentWay paymentWay = paymentDto.getPaymentWay();
            payment.setPaymentWay(paymentWay);

            switch (paymentWay) {
                case A:
                    payment.setAccount(paymentDto.getAccount());
                    break;
                case G:
                    payment.setGiro(paymentDto.getGiro());
                    break;
                case C:
                    payment.setCard(paymentDto.getCard());
                    break;

            }
        }


    }


}

