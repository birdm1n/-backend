package com.daema.core.sms.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "callingPlanId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "calling_plan", comment = "요금제")
public class CallingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calling_plan_id", columnDefinition = "BIGINT UNSIGNED comment '요금제 아이디'")
    private Long callingPlanId;

    @Column(name = "name", columnDefinition = "varchar(255) comment '요금제 명'")
    private String name;

    @OneToOne(mappedBy = "callingPlan")
    private JoinInfo joinInfo;




}
