package com.daema.core.sms.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "insuranceId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "insurance", comment = "보험")
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insurance_id", columnDefinition = "BIGINT UNSIGNED comment '보험 아이디'")
    private Long insuranceId;

}
