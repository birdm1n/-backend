package com.daema.core.sms.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "applicationFormId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "application_form", comment = "신청서 폼")
public class ApplicationForm {

    @Id
    @Column(name = "application_form_id", columnDefinition = "BIGINT UNSIGNED '신청서 폼 아이디'")
    private Long applicationFormId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "join_info_id")
    private JoinInfo joinInfo;



}
