package com.daema.core.sms.domain;

import com.daema.core.sms.domain.VO.Address;
import com.daema.core.sms.dto.GiroDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "giroId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "giro", comment = "지로")
public class Giro{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "giro_id", columnDefinition = "BIGINT UNSIGNED comment '지로 아이디'")
    private Long giroId;

    @Embedded
    private Address paymentAddress;

    @OneToOne(mappedBy = "giro")
    private Payment payment;

    public static GiroDto from(Giro giro) {
        return GiroDto.builder()
                .giroId(giro.getGiroId())
                .paymentAddress(giro.getPaymentAddress())
                .build();
    }

    public static Giro toEntity(GiroDto giroDto) {
        return Giro.builder()
                .giroId(giroDto.getGiroId())
                .paymentAddress(giroDto.getPaymentAddress())
                .build();
    }
}
