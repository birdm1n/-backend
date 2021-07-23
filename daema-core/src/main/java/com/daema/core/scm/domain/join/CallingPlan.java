package com.daema.core.scm.domain.join;

import com.daema.core.scm.dto.CallingPlanDto;
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
    private ApplicationJoin applicationJoin;


    public static CallingPlanDto from(CallingPlan callingPlan) {
        return CallingPlanDto.builder()
                .callingPlanId(callingPlan.getCallingPlanId())
                .name(callingPlan.getName())
                .build();
    }

    public static CallingPlan toEntity(CallingPlanDto callingPlanDto) {
        return CallingPlan.builder()
                .callingPlanId(callingPlanDto.getCallingPlanId())
                .name(callingPlanDto.getName())
                .build();
    }


}
